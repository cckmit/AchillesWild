package com.achilles.wild.server.common.aop;

import com.achilles.wild.server.model.response.BaseResult;
import com.achilles.wild.server.model.response.ResultCode;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(5)
public class GlobalControllerExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public BaseResult defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        BaseResult baseResult = BaseResult.fail(ResultCode.EXCEPTION_TO_CLIENT);
        return baseResult;
    }

}
