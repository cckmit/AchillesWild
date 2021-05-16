package com.achilles.wild.server.common.aop.exception;

import com.achilles.wild.server.model.response.BaseResult;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.achilles.wild.server.tool.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(basePackages = "com.achilles.wild.server.business.controller")
@Order(5)
public class GlobalControllerExceptionAdvice {

    private final static Logger log = LoggerFactory.getLogger(GlobalControllerExceptionAdvice.class);

//    @ExceptionHandler(value = BlockException.class)
//    public BaseResult blockExceptionHandler(BlockException e){
//        log.error(JsonUtil.toJsonString(e));
//        BaseResult baseResult = BaseResult.fail(BaseResultCode.FAIL.code,e.getMessage());
//        return baseResult;
//    }
//
//    @ExceptionHandler(value = FlowException.class)
//    public BaseResult flowExceptionHandler(FlowException e){
//        log.error(JsonUtil.toJsonString(e));
//        BaseResult baseResult = BaseResult.fail(BaseResultCode.FAIL.code,e.getMessage());
//        return baseResult;
//    }

    @ExceptionHandler(value = BizException.class)
    public BaseResult bizExceptionHandler(BizException e) throws Exception {
        log.error(JsonUtil.toJsonString(e));
        BaseResult baseResult = BaseResult.fail(e.getResultCode().code,e.getResultCode().message);
        return baseResult;
    }

//    @ExceptionHandler(value = RejectedExecutionException.class)
//    public BaseResult rejectedExecutionExceptionHandler(RejectedExecutionException e) throws Exception {
//        e.printStackTrace();
//        log.error(e.getMessage());
//        BaseResult baseResult = BaseResult.fail(BaseResultCode.EXCEPTION_TO_CLIENT);
//        return baseResult;
//    }

    @ExceptionHandler(value = Exception.class)
    public BaseResult defaultExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        log.error(JsonUtil.toJsonString(e));
        BaseResult baseResult = BaseResult.fail(BaseResultCode.EXCEPTION_TO_CLIENT);
        return baseResult;
    }

    @ExceptionHandler(value = Throwable.class)
    public BaseResult throwableHandler(HttpServletRequest req, Throwable e) throws Exception {
        e.printStackTrace();
        log.error(JsonUtil.toJsonString(e));
        BaseResult baseResult = BaseResult.fail(BaseResultCode.EXCEPTION_TO_CLIENT);
        return baseResult;
    }

}
