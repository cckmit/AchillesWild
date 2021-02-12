package com.achilles.wild.server.model.response.code;

import java.io.Serializable;

public enum UserResultCode implements Serializable {

    NOT_LOGIN("-300", "NOT LOGIN ~"),
    EMAIL_PASSWORD_IS_NECESSARY("-301", "email and password is necessary !"),
    USER_IS_MISSING("-302", "user is missing !"),
    PASSWORD_IS_WRONG("-301", "password is wrong !"),;

    public final String code;
    public final String message;

    UserResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static BaseResultCode codeOf(String code, BaseResultCode r) {
        for (BaseResultCode rc : BaseResultCode.values()) {
            if (rc.code.equals(code)) {
                return rc;
            }
        }
        return r;
    }
}
