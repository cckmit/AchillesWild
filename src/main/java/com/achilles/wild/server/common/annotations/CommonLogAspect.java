package com.achilles.wild.server.common.annotations;

import com.achilles.wild.server.entity.Logs;
import com.achilles.wild.server.manager.common.LogsManager;
import com.achilles.wild.server.tool.json.JsonUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@Aspect
@Component
@Order(1)
public class CommonLogAspect {

    private final static Logger log = LoggerFactory.getLogger(CommonLogAspect.class);

    private final static String LOG_PREFIX = "commonLog";

    private Cache<String,AtomicInteger> cache = CacheBuilder.newBuilder().concurrencyLevel(10000).maximumSize(500).expireAfterWrite(30, TimeUnit.SECONDS).build();

    private final RateLimiter rateLimiter = RateLimiter.create(1);

    @Value("${common.log.open}")
    private Boolean openLog;

    @Value("${common.log.method.time.consuming.exceed.limit.insert.db}")
    private Boolean ifInsertDb;

    @Value("${common.log.method.time.consuming.limit}")
    private Integer time;

    @Value("${common.log.method.count.limit.per.second}")
    private Integer countLimit;

    @Autowired
    private LogsManager logsManager;

    /** 以 @CommonLog注解为切入点 */
    @Pointcut("@annotation(com.achilles.wild.server.common.annotations.CommonLog)")
    public void commonLog() {}

    /**
     * 在切点之前织入
     * @param joinPoint
     * @throws Throwable
     */
    @Before("commonLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        if(!openLog){
            return;
        }

        String method = joinPoint.getSignature().getDeclaringTypeName()+"#"+joinPoint.getSignature().getName();

        Map<String,Object> paramsMap =  getParamsMap(joinPoint);

        log.info(LOG_PREFIX+"#params : "+method+"("+paramsMap+")");
    }

    /**
     * 环绕
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("commonLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        if(!openLog){
            return proceedingJoinPoint.proceed();
        }

        long startTime = System.currentTimeMillis();
        String clz = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();
        Object result = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;
        String path = clz+"#"+method;
        log.info(LOG_PREFIX+"#result : "+path+"-->("+ JsonUtil.toJsonString(result)+")");
        log.info(LOG_PREFIX+"#Time-Consuming : "+path+"-->("+duration+"ms)");

        if(!ifInsertDb || duration<=time){
            return result;
        }

        path+="_commonLog";
        AtomicInteger atomicInteger = cache.getIfPresent(path)==null?new AtomicInteger():cache.getIfPresent(path);
        int count = atomicInteger.get();
        if(count<countLimit){
            count = atomicInteger.incrementAndGet();
            if(count>countLimit){
                return result;
            }
            cache.put(path,atomicInteger);
            if(count<=countLimit){
                String params = JsonUtil.toJsonString(getParamsMap(proceedingJoinPoint));
                log.info(LOG_PREFIX+"#insert slow log into db start, method : "+path+"-->("+ params+")"+"--->"+duration+"ms");
                Logs logs = new Logs();
                logs.setClz(clz);
                logs.setMethod(method);
                logs.setParams(params);
                logs.setTime((int)duration);
                logsManager.addLog(logs);
                log.info(LOG_PREFIX+"#insert slow log into db, method : "+path+"----------->  SUCCESS ---------------------  ");
            }
        }

        return result;
    }




    /**
     * 在切点之后织入
     * @throws Throwable
     */
    @After("commonLog()")
    public void doAfter() throws Throwable {

    }

    private Map<String,Object> getParamsMap(JoinPoint joinPoint){

        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        if(paramNames.length==0){
            return new HashMap<>();
        }

        Object[] paramValues = joinPoint.getArgs();

        Map<String,Object> paramsMap = new HashMap<>();
        for(int i=0;i<paramNames.length;i++){
            String key = paramNames[i];
            Object value = paramValues[i];
            String val = JsonUtil.toJsonString(value);
            paramsMap.put(key,val);
        }

        return paramsMap;
    }
}
