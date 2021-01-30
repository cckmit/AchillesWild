package com.achilles.wild.server.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Aspect
//@Component
//@Order(2)
public class ManagerLogAspect {

    private final static Logger log = LoggerFactory.getLogger(ManagerLogAspect.class);

    private final static String LOG_PREFIX = "ManagerLogAspect";

    /**
     * 在切点之前织入
     * @param joinPoint
     * @throws Throwable
     */
    @Before("execution(* com.achilles.wild.server.manager.account..*.*(..))")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        log.info(LOG_PREFIX+"# ------------------------ doBefore");
    }

    /**
     * 环绕
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.achilles.wild.server.manager.account..*.*(..))")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        log.info(LOG_PREFIX+"# ------------------------ doAround");

        return proceedingJoinPoint.proceed();
    }

    /**
     * 在切点之后织入
     * @throws Throwable
     */
    @After("execution(public * com.achilles.wild.server.manager.account..*.*(..))")
    public void doAfter() throws Throwable {
        log.info(LOG_PREFIX+"# ------------------------ doAfter");
    }
}
