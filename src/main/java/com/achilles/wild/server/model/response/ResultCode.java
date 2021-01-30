package com.achilles.wild.server.model.response;

import java.io.Serializable;

public enum ResultCode implements Serializable {

    SUCCESS("1", "success"),
    FAIL("0", "fail"),

    EXCEPTION("-100", "server exception, msg:%s"),
    ILLEGAL_PARAM("-101", "illegal parameter: %s"),
    ERROR("-102", "unsuccessful call, msg:%s"),
    MISSING_PARAMETER("-103", "missing parameter: %s"),
    SERVICE_UNAVAILABLE("-105", "service unavailable: %s"),
    DATA_NOT_EXISTS("-106", "data not exists: %s"),
    DATA_HAS_EXISTS("-107", "data has exists: %s"),


    BALANCE_NOT_ENOUGH("600", "balance not enough ~"),


    EXCEPTION_TO_CLIENT("-1000", "the server is too busy ~ : %s"),
    TOO_MANY_REQUESTS("-1001", "too many requests ~ : %s"),
    REQUESTS_TOO_FREQUENT("-1002", "requests are too frequent ~ : %s");


    public final String code;
    public final String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultCode codeOf(String code, ResultCode r) {
        for (ResultCode rc : ResultCode.values()) {
            if (rc.code.equals(code)) {
                return rc;
            }
        }
        return r;
    }
}
