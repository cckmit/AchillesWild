package com.achilles.wild.server.common.annotations;

import com.achilles.wild.server.tool.json.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class CommonLogAspect {


    private final static Logger log = LoggerFactory.getLogger(CommonLogAspect.class);

    private final static String LOG_PREFIX = "commonLog";

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

        String method = joinPoint.getSignature().getDeclaringTypeName()+"#"+joinPoint.getSignature().getName();

        Map<String,Object> paramsMap =  getParamsMap(joinPoint);

        log.info(LOG_PREFIX+"#params : "+method+"("+paramsMap+")");
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

    /**
     * 在切点之后织入
     * @throws Throwable
     */
    @After("commonLog()")
    public void doAfter() throws Throwable {

    }

    /**
     * 环绕
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("commonLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        Object result = proceedingJoinPoint.proceed();
        String method = proceedingJoinPoint.getSignature().getDeclaringTypeName()+"#"+proceedingJoinPoint.getSignature().getName();

//        Map<String,Object> paramsMap =  getParamsMap(proceedingJoinPoint);
//        log.info(LOG_PREFIX+"#params bb : "+method+"("+paramsMap+")");

        log.info(LOG_PREFIX+"#result : "+method+"-->("+ JsonUtil.toJsonString(result)+")");

        long duration = System.currentTimeMillis() - startTime;
        log.info(LOG_PREFIX+"#Time-Consuming : "+method+"-->("+duration+"ms)");

        return result;
    }

}
