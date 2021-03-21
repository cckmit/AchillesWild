package com.achilles.wild.server.common.aop.limit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FallBackHandler {

    private final static Logger log = LoggerFactory.getLogger(FallBackHandler.class);


    public static String queryOrderInfo2Fallback(String name, Throwable e) {
        log.info("==================queryOrderInfo2Fallback ==fallback=========="+name);
        return "-----------------queryOrderInfo2Fallback fallback : " + name;
    }
}
