package com.achilles.wild.server.common.annotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Aspect
//@Component
//@Order(3)
public class DaoLogAspect {

    private final static Logger log = LoggerFactory.getLogger(DaoLogAspect.class);

    private final static String LOG_PREFIX = "DaoLogAspect";

    /**
     * 环绕
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("within(com.achilles.wild.server.dao.account.AccountDao+)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        log.info(LOG_PREFIX+"# ------------------------ doAround");

        return proceedingJoinPoint.proceed();
    }

}
