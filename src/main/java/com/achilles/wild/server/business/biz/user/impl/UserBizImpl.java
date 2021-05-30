package com.achilles.wild.server.business.biz.user.impl;

import com.achilles.wild.server.business.biz.user.UserBiz;
import com.achilles.wild.server.business.manager.user.UserManager;
import com.achilles.wild.server.business.manager.user.UserTokenManager;
import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.entity.user.User;
import com.achilles.wild.server.entity.user.UserToken;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.model.response.code.UserResultCode;
import com.achilles.wild.server.tool.date.DateUtil;
import com.achilles.wild.server.tool.generate.encrypt.MD5Util;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBizImpl implements UserBiz {

    private final static Logger log = LoggerFactory.getLogger(UserBizImpl.class);

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserTokenManager userTokenManager;


    @Override
    public DataResult<String> login(String email,String password) {

        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)){
            throw new BizException(UserResultCode.EMAIL_PASSWORD_IS_NECESSARY.code,UserResultCode.EMAIL_PASSWORD_IS_NECESSARY.message);
        }
        User user = userManager.getUserByEmail(email);
        if (user==null) {
            log.warn("user is missing;   email="+email);
            throw new BizException(UserResultCode.USER_IS_MISSING.code,UserResultCode.USER_IS_MISSING.message);
        }

        String encipherPassword = MD5Util.get(password);
        if (!encipherPassword.equals(user.getPassword())){
            log.warn("password is wrong;   email="+email);
            throw new BizException(UserResultCode.PASSWORD_IS_WRONG.code,UserResultCode.PASSWORD_IS_WRONG.message);
        }
        UserToken userToken = new UserToken();
        userToken.setUserUuid(user.getUuid());
        userToken.setToken(GenerateUniqueUtil.getUuId());
        userToken.setExpirationTime(DateUtil.getDateByAddMill(CommonConstant.TOKEN_EXPIRATION_TIME));
        userTokenManager.addTokenRecord(userToken);

        return DataResult.success(userToken.getToken());
    }
}
