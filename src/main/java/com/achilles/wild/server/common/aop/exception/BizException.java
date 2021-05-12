package com.achilles.wild.server.common.aop.exception;

import com.achilles.wild.server.model.response.code.BaseResultCode;

public class BizException extends RuntimeException{

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private BaseResultCode baseResultCode;

    public BizException(BaseResultCode baseResultCode) {
        this.baseResultCode = baseResultCode;
    }

    public BizException(String code, String message) {
        this.code = code;
        this.message = message;
    }

//    public BaseResultCode getResultCode() {
//        return baseResultCode;
//    }
}
