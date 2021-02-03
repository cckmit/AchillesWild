package com.achilles.wild.server.common.filter;

import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.model.response.BaseResult;
import com.achilles.wild.server.model.response.ResultCode;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.achilles.wild.server.tool.json.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@WebFilter(filterName = "myFilter", urlPatterns = "/*" , initParams = {
        @WebInitParam(name = "loginUri", value = "/login")})
public class MyFilter implements Filter {

    @Value("${filter.if.verify.login}")
    private Boolean verifyLogin;

    private final static Logger log = LoggerFactory.getLogger(MyFilter.class);

    private String loginUri;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.loginUri = filterConfig.getInitParameter("loginUri");
        log.info("-------------------------------------init-----------------------------------URL=" + this.loginUri);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceId = request.getHeader(CommonConstant.TRACE_ID);
        if(StringUtils.isBlank(traceId)){
            traceId = GenerateUniqueUtil.getRandomUUID();
        }
        MDC.put(CommonConstant.TRACE_ID,traceId);

        if(!verifyLogin){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        String uri = request.getRequestURI();
        log.info("--------------------------------------uri:"+ uri);
        if(uri.endsWith(loginUri)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        String token = request.getHeader(CommonConstant.TOKEN);
        if(StringUtils.isNotBlank(token)){
            log.info("------------------------------------token:"+ token);
            //todo
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        servletResponse.setContentType("application/json");
        servletResponse.setCharacterEncoding("utf-8");
        PrintWriter printWriter = servletResponse.getWriter();
        String result = JsonUtil.toJsonString(BaseResult.fail(ResultCode.NOT_LOGIN));
        printWriter.write(result);
        printWriter.flush();
        printWriter.close();
    }

    @Override
    public void destroy() {
        log.info("-------------------------------------destroy-----------------------------------");
    }
}
