package com.achilles.wild.server.common.aop.interceptor;

import com.achilles.wild.server.business.manager.user.UserTokenManager;
import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.entity.user.UserToken;
import com.achilles.wild.server.model.response.code.UserResultCode;
import com.achilles.wild.server.tool.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final static Logger log = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Value("${if.verify.login:false}")
    private Boolean verifyLogin;

    @Autowired
    private UserTokenManager userTokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!verifyLogin){
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;
        NoCheckToken noCheckToken = method.getMethodAnnotation(NoCheckToken.class);
        if (noCheckToken !=null && noCheckToken.value()){
            return true;
        }

        String token = request.getHeader(CommonConstant.TOKEN);
        log.debug("------------------------------------token:"+ token);

        if(StringUtils.isBlank(token)){
            throw new BizException(UserResultCode.NOT_LOGIN.code,UserResultCode.NOT_LOGIN.message);
        }

        UserToken userToken = userTokenManager.getByToken(token);
        if(userToken ==null){
            throw new BizException(UserResultCode.NOT_LOGIN.code,UserResultCode.NOT_LOGIN.message);
        }
        int seconds = DateUtil.getGapSeconds(userToken.getUpdateDate());
        if(seconds>1800){
            throw new BizException(UserResultCode.LOGIN_EXPIRED.code,UserResultCode.LOGIN_EXPIRED.message);
        }

        UserToken userTokenUpdate = new UserToken();
        userTokenUpdate.setId(userToken.getId());
        Boolean update = userTokenManager.updateById(userToken);
        if(!update){
            throw new Exception("update token time fail");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
