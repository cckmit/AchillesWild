package com.achilles.wild.server.business.controller.user;

import com.achilles.wild.server.business.biz.user.UserBiz;
import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.aop.interceptor.NoCheckToken;
import com.achilles.wild.server.common.aop.log.annotation.IgnoreParams;
import com.achilles.wild.server.model.request.user.UserRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.model.response.code.UserResultCode;
import com.achilles.wild.server.model.response.user.UserResponse;
import com.achilles.wild.server.tool.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user",produces = {"application/json;charset=UTF-8"})
@Slf4j
public class UserController {

    @Autowired
    private UserBiz userBiz;

    @NoCheckToken
    @IgnoreParams
    @PostMapping("/login")
    public DataResult<UserResponse> login(@RequestBody(required = true) UserRequest request){

        log.info("login -----------------  " + JsonUtil.toJsonString(request));

        String email = request.getEmail();
        String password = request.getPassword();
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)){
            throw new BizException(UserResultCode.EMAIL_PASSWORD_IS_NECESSARY);
        }

        DataResult<String> dataResult = userBiz.login(email,password);
        if(dataResult ==null){
            return DataResult.baseFail(dataResult.getCode(),dataResult.getMessage());
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setToken(dataResult.getData());

        return DataResult.success(userResponse);
    }

}
