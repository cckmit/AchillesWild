package com.achilles.wild.server.common.annotations;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonLogAspect {


    private final static Logger log = LoggerFactory.getLogger(CommonLogAspect.class);

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
        log.info("==========================================LOG Start ==========================================");
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求入参
        log.info("Request Args   : {}", new Gson().toJson(joinPoint.getArgs()));
    }

    /**
     * 在切点之后织入
     * @throws Throwable
     */
    @After("commonLog()")
    public void doAfter() throws Throwable {
        log.info("===========================================LOG End ===========================================");
        // 每个请求之间空一行
        log.info("");
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
        // 打印出参
        log.info("Response Args  : {}", new Gson().toJson(result));
        // 执行耗时
        log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
        return result;
    }

}
