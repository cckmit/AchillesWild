package com.achilles.wild.server.common.aop.exception;

import com.achilles.wild.server.model.response.ResultCode;

public class MyException extends RuntimeException{


    private ResultCode resultCode;

    public MyException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
