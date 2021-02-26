package com.achilles.wild.server.common.aop.log;

import com.achilles.wild.server.business.manager.common.LogControllerManager;
import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.aop.listener.event.EventListeners;
import com.achilles.wild.server.common.aop.listener.event.ExceptionLogsEvent;
import com.achilles.wild.server.common.config.params.ControllerLogParamsConfig;
import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.entity.common.LogException;
import com.achilles.wild.server.entity.common.LogController;
import com.achilles.wild.server.enums.account.ExceptionTypeEnum;
import com.achilles.wild.server.tool.bean.AspectUtil;
import com.achilles.wild.server.tool.json.JsonUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
@Order(1)
public class LogControllerAspect {

    private final static Logger log = LoggerFactory.getLogger(LogControllerAspect.class);

    private final static String PREFIX = "";

    private Cache<String,AtomicInteger> integerCache = CacheBuilder.newBuilder().concurrencyLevel(5000).maximumSize(500).expireAfterWrite(10, TimeUnit.SECONDS).build();

    private Cache<String, RateLimiter> rateLimiterCache = CacheBuilder.newBuilder().concurrencyLevel(5000).maximumSize(500).expireAfterWrite(10, TimeUnit.SECONDS).build();

    @Autowired
    private ControllerLogParamsConfig controllerLogParamsConfig;

    @Autowired
    private LogControllerManager logControllerManager;

    @Autowired
    private EventListeners eventListeners;

    @Autowired
    private ApplicationContext applicationContext;

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
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String clz = methodSignature.getDeclaringTypeName();
        String method = methodSignature.getName();
        Map<String,Object> paramsMap = AspectUtil.getParamsMap(proceedingJoinPoint);
        String params = null;
        if(paramsMap.size()!=0){
            params = JsonUtil.toJsonString(paramsMap);
        }

        Object result = proceedingJoinPoint.proceed();

        long duration = System.currentTimeMillis() - startTime;
        String path = clz+"#"+method;
        log.debug(PREFIX +"#result : "+path+"-->"+ JsonUtil.toJsonString(result));
        log.debug(PREFIX +"#time-consuming : "+path+"-->("+duration+"ms)");

        if(!controllerLogParamsConfig.getIfTimeLogInsertDb() || duration<=controllerLogParamsConfig.getTimeLimit()){
            return result;
        }

        String countLimitKey = path+"_CountLimit";
        AtomicInteger atomicInteger = integerCache.getIfPresent(countLimitKey)==null?new AtomicInteger():integerCache.getIfPresent(countLimitKey);
        int count = atomicInteger.get();
        if(count>=controllerLogParamsConfig.getCountOfInsertDBInTime()){
            return result;
        }
        count = atomicInteger.incrementAndGet();
        if(count>controllerLogParamsConfig.getCountOfInsertDBInTime()){
            return result;
        }
        integerCache.put(countLimitKey,atomicInteger);

        String rateLimiterKey = path+"_RateLimiter";
        RateLimiter rateLimiter = rateLimiterCache.getIfPresent(rateLimiterKey)==null ?
                                  RateLimiter.create(controllerLogParamsConfig.getRateOfInsertDBPerSecond()):rateLimiterCache.getIfPresent(rateLimiterKey);
        rateLimiterCache.put(rateLimiterKey,rateLimiter);
        if(!rateLimiter.tryAcquire()){
            return result;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();
        String type = request.getMethod();

        log.debug(PREFIX +"#insert slow log into db start, method : "+path+"-->"+ params+""+"--->"+duration+"ms");
        LogController controllerLogs = new LogController();
        controllerLogs.setClz(clz);
        controllerLogs.setMethod(method);
        controllerLogs.setParams(params);
        controllerLogs.setTime((int)duration);
        controllerLogs.setTraceId(MDC.get(CommonConstant.TRACE_ID));
        controllerLogs.setUri(uri);
        controllerLogs.setType(type);
        logControllerManager.addLog(controllerLogs);

        return result;
    }

    @AfterThrowing(pointcut="controllerLog()", throwing= "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable){

        if(!controllerLogParamsConfig.getIfExceptionLogInsertDb()){
            log.debug("insert into DB  BizException has been closed");
           return;
        }

        String clz = joinPoint.getSignature().getDeclaringTypeName();
        String method = joinPoint.getSignature().getName();
        Map<String,Object> paramsMap = AspectUtil.getParamsMap(joinPoint);
        String params = null;
        if(paramsMap.size()!=0){
            params = JsonUtil.toJsonString(paramsMap);
        }

        LogException logException = new LogException();
        logException.setClz(clz);
        logException.setMethod(method);
        logException.setParams(params);
        logException.setTraceId(MDC.get(CommonConstant.TRACE_ID));

        if(throwable instanceof BizException){
            log.debug("----------------------insert into DB  BizException ");
            logException.setMessage(((BizException) throwable).getMessage());
            logException.setType(ExceptionTypeEnum.BIZ_EXCEPTION.toNumbericValue());
        }else {
            log.debug("----------------------insert into DB other Exception ");
            logException.setMessage(throwable.toString());
            logException.setType(ExceptionTypeEnum.OTHER_EXCEPTION.toNumbericValue());
        }
//        eventListeners.addExceptionLogsEvent(new ExceptionLogsEvent(exceptionLogs));
        applicationContext.publishEvent(new ExceptionLogsEvent(logException));

    }

    @After("controllerLog()")
    public void doAfter() throws Throwable {

    }

}
