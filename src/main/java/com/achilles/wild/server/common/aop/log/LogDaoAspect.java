package com.achilles.wild.server.common.aop.log;

import com.achilles.wild.server.entity.common.LogBizInfo;
import com.achilles.wild.server.business.manager.common.LogBizInfoManager;
import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.tool.bean.AspectUtil;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@Aspect
@Component
@Order(6)
public class LogDaoAspect {

    private final static Logger log = LoggerFactory.getLogger(LogDaoAspect.class);

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
    private LogBizInfoManager logBizInfoManager;

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
        Map<String,Object> paramsMap = AspectUtil.getParamsMap(proceedingJoinPoint);
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
        log.debug(PREFIX +"#insert slow log into db start, method : "+path+"-->"+ params+""+"--->"+duration+"ms");
        LogBizInfo logBizInfo = new LogBizInfo();
        logBizInfo.setClz(clz);
        logBizInfo.setMethod(method);
        logBizInfo.setParams(params);
        logBizInfo.setTime((int)duration);
        logBizInfo.setTraceId(MDC.get(CommonConstant.TRACE_ID));
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();
        String type = request.getMethod();
        logBizInfo.setUri(uri);
        logBizInfo.setType(type);
        logBizInfoManager.addLog(logBizInfo);
//      log.info(PREFIX +"#insert slow log into db over, method : "+path);

        return result;
    }

}
