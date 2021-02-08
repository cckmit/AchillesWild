package com.achilles.wild.server.common.aop.log;

import com.achilles.wild.server.business.entity.ExceptionLogs;
import com.achilles.wild.server.business.entity.TimeLogs;
import com.achilles.wild.server.business.manager.common.TimeLogsManager;
import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.aop.listener.event.EventListeners;
import com.achilles.wild.server.common.aop.listener.event.ExceptionLogsEvent;
import com.achilles.wild.server.common.config.params.ControllerLogParamsConfig;
import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.enums.account.ExceptionTypeEnum;
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
@Order(1)
public class ControllerLogAspect {

    private final static Logger log = LoggerFactory.getLogger(ControllerLogAspect.class);

    private final static String PREFIX = "";

    private Cache<String,AtomicInteger> integerCache = CacheBuilder.newBuilder().concurrencyLevel(5000).maximumSize(500).expireAfterWrite(10, TimeUnit.SECONDS).build();

    private Cache<String, RateLimiter> rateLimiterCache = CacheBuilder.newBuilder().concurrencyLevel(5000).maximumSize(500).expireAfterWrite(10, TimeUnit.SECONDS).build();

    @Value("${controller.log.time.open}")
    private Boolean openLog;

    @Value("${controller.log.time.insert.db}")
    private Boolean ifInsertDb;

    @Value("${controller.log.time.of.time.consuming.limit}")
    private Integer timeLimit;

    @Value("${controller.log.time.of.count.limit.in.time}")
    private Integer countOfInsertDBInTime;

    @Value("${controller.log.time.of.insert.db.rate.per.second}")
    private Double rateOfInsertDBPerSecond;

    @Value("${controller.log.exception.insert.db}")
    private Boolean ifExceptionLogInsertDb;

    @Autowired
    private ControllerLogParamsConfig controllerLogParamsConfig;

    @Autowired
    private TimeLogsManager timeLogsManager;

    @Autowired
    private EventListeners eventListeners;

    @Pointcut("execution(* com.achilles.wild.server.business.controller..*.*(..))")
    public void controllerLog() {}


    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

//        if(!openLog){
//            return;
//        }
//        String method = joinPoint.getSignature().getDeclaringTypeName()+"#"+joinPoint.getSignature().getName();
//        Map<String,Object> paramsMap =  getParamsMap(joinPoint);
//        log.info(PREFIX +"#params : "+method+"("+paramsMap+")");
    }


    @Around("controllerLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        if(!controllerLogParamsConfig.getIfOpenLog()){
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
//        log.info(PREFIX +"#result : "+path+"-->"+ JsonUtil.toJsonString(result));
//        log.info(PREFIX +"#time-consuming : "+path+"-->("+duration+"ms)");

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

        String rateLimiterKey = path+"_RateLimiter";
        RateLimiter rateLimiter = rateLimiterCache.getIfPresent(rateLimiterKey)==null ?
                                  RateLimiter.create(rateOfInsertDBPerSecond):rateLimiterCache.getIfPresent(rateLimiterKey);
        rateLimiterCache.put(rateLimiterKey,rateLimiter);
        if(!rateLimiter.tryAcquire()){
            return result;
        }

        log.debug(PREFIX +"#insert slow log into db start, method : "+path+"-->"+ params+""+"--->"+duration+"ms");
        TimeLogs timeLogs = new TimeLogs();
        timeLogs.setClz(clz);
        timeLogs.setMethod(method);
        timeLogs.setParams(params);
        timeLogs.setTime((int)duration);
        timeLogs.setTraceId(MDC.get(CommonConstant.TRACE_ID));
        timeLogsManager.addLog(timeLogs);

        return result;
    }

    @AfterThrowing(pointcut="controllerLog()", throwing= "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable){

        if(!ifExceptionLogInsertDb){
           return;
        }

        String clz = joinPoint.getSignature().getDeclaringTypeName();
        String method = joinPoint.getSignature().getName();
        Map<String,Object> paramsMap = getParamsMap(joinPoint);
        String params = null;
        if(paramsMap.size()!=0){
            params = JsonUtil.toJsonString(paramsMap);
        }

        ExceptionLogs exceptionLogs = new ExceptionLogs();

        exceptionLogs.setClz(clz);
        exceptionLogs.setMethod(method);
        exceptionLogs.setParams(params);
        exceptionLogs.setTraceId(MDC.get(CommonConstant.TRACE_ID));

        if(throwable instanceof BizException){
            log.debug("----------------------insert into DB  BizException ");
            exceptionLogs.setMessage(((BizException) throwable).getResultCode().message);
            exceptionLogs.setType(ExceptionTypeEnum.BIZ_EXCEPTION.toNumbericValue());
        }else {
            log.debug("----------------------insert into DB other Exception ");
            exceptionLogs.setMessage(throwable.toString());
            exceptionLogs.setType(ExceptionTypeEnum.OTHER_EXCEPTION.toNumbericValue());
        }
        eventListeners.addExceptionLogsEvent(new ExceptionLogsEvent(exceptionLogs));
    }

    @After("controllerLog()")
    public void doAfter() throws Throwable {

    }

//    @AfterThrowing(pointcut="commonLog()", throwing= "exception")
//    public void afterThrowing(JoinPoint joinPoint, Exception exception){
//        log.info("[afterThrowingAdvice] exception: "+exception);
//    }
//
//    @AfterThrowing(pointcut="commonLog()", throwing= "myException")
//    public void afterThrowing(JoinPoint joinPoint, MyException myException){
//        log.info("[afterThrowingAdvice] myException: "+myException);
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
