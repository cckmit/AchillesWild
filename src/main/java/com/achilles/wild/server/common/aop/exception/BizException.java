package com.achilles.wild.server.common.aop.exception;

import com.achilles.wild.server.model.response.code.BaseResultCode;

public class BizException extends RuntimeException{


    private BaseResultCode baseResultCode;

    public BizException(BaseResultCode baseResultCode) {
        this.baseResultCode = baseResultCode;
    }

    public BaseResultCode getResultCode() {
        return baseResultCode;
    }
}
