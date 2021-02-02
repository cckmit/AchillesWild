package com.achilles.wild.server.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(filterName = "myFilter", urlPatterns = "/*" , initParams = {
        @WebInitParam(name = "URL", value = "http://localhost:8080")})
public class MyFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(MyFilter.class);

    private String url;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.url = filterConfig.getInitParameter("URL");
        log.info("-------------------------------------init-----------------------------------URL=" + this.url);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("--------------------------"+ request.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("-------------------------------------destroy-----------------------------------");
    }
}
