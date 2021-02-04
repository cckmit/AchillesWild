package com.achilles.wild.server.common.aop.exception;

import com.achilles.wild.server.model.response.BaseResult;
import com.achilles.wild.server.model.response.ResultCode;
import com.achilles.wild.server.tool.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(basePackages = "com.achilles.wild.server.controller")
@Order(5)
public class GlobalControllerExceptionAdvice {

    private final static Logger log = LoggerFactory.getLogger(GlobalControllerExceptionAdvice.class);


    @ExceptionHandler(value = MyException.class)
    public BaseResult myExceptionHandler(MyException e) throws Exception {
        ResultCode resultCode = e.getResultCode();
        log.error(JsonUtil.toJsonString(resultCode));
        BaseResult baseResult = BaseResult.fail(resultCode);
        return baseResult;
    }

    @ExceptionHandler(value = Exception.class)
    public BaseResult defaultExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        log.error(e.getMessage());
        BaseResult baseResult = BaseResult.fail(ResultCode.EXCEPTION_TO_CLIENT);
        return baseResult;
    }

    @ExceptionHandler(value = Throwable.class)
    public BaseResult throwableHandler(HttpServletRequest req, Throwable e) throws Exception {
        e.printStackTrace();
        log.error(e.getMessage());
        BaseResult baseResult = BaseResult.fail(ResultCode.EXCEPTION_TO_CLIENT);
        return baseResult;
    }

}
