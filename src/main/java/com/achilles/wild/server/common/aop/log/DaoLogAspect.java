package com.achilles.wild.server.common.aop.log;

import com.achilles.wild.server.business.entity.TimeLogs;
import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.business.manager.common.TimeLogsManager;
import com.achilles.wild.server.tool.json.JsonUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
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
@Order(6)
public class DaoLogAspect {

    private final static Logger log = LoggerFactory.getLogger(DaoLogAspect.class);

    private final static String PREFIX = "";

    private Cache<String,AtomicInteger> integerCache = CacheBuilder.newBuilder().concurrencyLevel(5000).maximumSize(500).expireAfterWrite(10, TimeUnit.SECONDS).build();

    private Cache<String, RateLimiter> rateLimiterCache = CacheBuilder.newBuilder().concurrencyLevel(5000).maximumSize(500).expireAfterWrite(10, TimeUnit.SECONDS).build();

    @Value("${dao.log.time.open}")
    private Boolean openLog;

    @Value("${dao.log.time.insert.db}")
    private Boolean ifInsertDb;

    @Value("${dao.log.time.of.time.consuming.limit}")
    private Integer timeLimit;

    @Value("${dao.log.time.of.count.limit.in.time}")
    private Integer countOfInsertDBInTime;

    @Autowired
    private TimeLogsManager timeLogsManager;

//    @Pointcut("within(com.achilles.wild.server.business.dao.account.AccountDao+)")
    @Pointcut("execution(* com.achilles.wild.server.business.dao.account..*.*(..))")
    public void daoLog() {}

    /**
     * 在切点之前织入
     * @param joinPoint
     * @throws Throwable
     */
    @Before("daoLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

//        if(!openLog){
//            return;
//        }
//
//        String method = joinPoint.getSignature().getDeclaringTypeName()+"#"+joinPoint.getSignature().getName();
//
//        Map<String,Object> paramsMap =  getParamsMap(joinPoint);
//
//        log.info(PREFIX +"#params : "+method+"("+paramsMap+")");
    }

    /**
     * 环绕
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("daoLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        if(!openLog){
            return proceedingJoinPoint.proceed();
        }

        long startTime = System.currentTimeMillis();
        String clz = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();
        Map<String,Object> paramsMap = getParamsMap(proceedingJoinPoint);
        String params = null;
        if(paramsMap.size()!=0){
            params = JsonUtil.toJsonString(paramsMap);
        }

        Object result = proceedingJoinPoint.proceed();

        long duration = System.currentTimeMillis() - startTime;
        String path = clz+"#"+method;

        if(!ifInsertDb || duration<=timeLimit){
            return result;
        }

        String countLimitKey = path+"_CountLimit";
        AtomicInteger atomicInteger = integerCache.getIfPresent(countLimitKey)==null?new AtomicInteger():integerCache.getIfPresent(countLimitKey);
        int count = atomicInteger.get();
        if(count>=countOfInsertDBInTime){
            return result;
        }
        count = atomicInteger.incrementAndGet();
        if(count>countOfInsertDBInTime){
            return result;
        }
        integerCache.put(countLimitKey,atomicInteger);
//      log.info(PREFIX +"#insert slow log into db start, method : "+path+"-->"+ params+""+"--->"+duration+"ms");
        TimeLogs timeLogs = new TimeLogs();
        timeLogs.setClz(clz);
        timeLogs.setMethod(method);
        timeLogs.setParams(params);
        timeLogs.setTime((int)duration);
        timeLogs.setTraceId(MDC.get(CommonConstant.TRACE_ID));
        timeLogsManager.addLog(timeLogs);
//      log.info(PREFIX +"#insert slow log into db over, method : "+path);

        return result;
    }

    /**
     * 在切点之后织入
     * @throws Throwable
     */
//    @After("daoLog()")
//    public void doAfter() throws Throwable {
//
//    }

    /**
     * get params
     *
     * @param joinPoint
     * @return
     */
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
            paramsMap.put(key,value);
            if(value==null){
                continue;
            }
            Object val = value;
            boolean isSynthetic = value.getClass().isSynthetic();
            if(isSynthetic){
                val = JsonUtil.toJsonString(value);
            }
            paramsMap.put(key,val);
        }

        return paramsMap;
    }
}
