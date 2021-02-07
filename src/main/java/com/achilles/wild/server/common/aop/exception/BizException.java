package com.achilles.wild.server.common.aop.exception;

import com.achilles.wild.server.model.response.ResultCode;

public class BizException extends RuntimeException{


    private ResultCode resultCode;

    public BizException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
