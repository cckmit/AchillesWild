package com.achilles.wild.server.common.aop.filter;

import com.achilles.wild.server.business.manager.common.LogFilterInfoManager;
import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.entity.common.LogTimeInfo;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.achilles.wild.server.tool.date.DateUtil;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.lmax.disruptor.RingBuffer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;


@WebFilter(filterName = "initFilter", urlPatterns = "/*" , initParams = {@WebInitParam(name = "loginUri", value = "/login")})
public class InitFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(InitFilter.class);

    private String loginUri;

    @Value("${if.verify.trace.id:false}")
    private Boolean verifyTraceId;

    @Value("${filter.log.time.insert.db.open:true}")
    private Boolean ifOpenInsertDb;

    @Value("${filter.log.time.of.count.limit.in.time:10000}")
    private Integer countOfInsertDBInTime;

    @Autowired
    private LogFilterInfoManager logFilterInfoManager;

    @Autowired
    private Cache<String, AtomicInteger> caffeineCacheAtomicInteger;

    @Autowired
    Queue<LogTimeInfo> logInfoConcurrentLinkedQueue;

    @Autowired
    private RingBuffer<LogTimeInfo> messageModelRingBuffer;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.loginUri = filterConfig.getInitParameter("loginUri");
        log.debug("-------------------------------------init-----------------------------------URL=" + this.loginUri);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        long startTime = System.currentTimeMillis();

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceId = request.getHeader(CommonConstant.TRACE_ID);
        if(verifyTraceId){
            log.debug("---------------traceId  from  client---------------------:" + traceId);
            checkTraceId(traceId);
        }else{
            traceId = GenerateUniqueUtil.getTraceId(CommonConstant.SYSTEM_CODE);
            log.debug("---------------traceId  generate by  system---------------------:" + traceId);
        }

        MDC.put(CommonConstant.TRACE_ID,traceId);

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers","token,Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        filterChain.doFilter(servletRequest,servletResponse);

        String uri = request.getRequestURI();
        long duration = System.currentTimeMillis() - startTime;
        log.debug(" ----------- time-consuming : ("+uri+")-->("+duration+"ms)");

        if (!ifOpenInsertDb) {
            log.debug("-----------------remove traceId from Thread-----");
            MDC.remove(CommonConstant.TRACE_ID);
            return;
        }

        uri = uri.intern();
        AtomicInteger atomicInteger;
        synchronized (uri) {
            atomicInteger = caffeineCacheAtomicInteger.getIfPresent(uri);
            log.debug(" -----------("+uri+") already insert into DB count : "+atomicInteger);
            if (atomicInteger == null){
                atomicInteger = new AtomicInteger();
                caffeineCacheAtomicInteger.put(uri,atomicInteger);
            }
        }

        atomicInteger.incrementAndGet();
        if(atomicInteger.get() > countOfInsertDBInTime) {
            log.debug(" -----------"+uri+"-- insert into DB count out of limit ");
            MDC.remove(CommonConstant.TRACE_ID);
            return;
        }

        long sequence = messageModelRingBuffer.next();
        LogTimeInfo logTimeInfo = messageModelRingBuffer.get(sequence);
        LogTimeInfo.clear(logTimeInfo);
        logTimeInfo.setUri(uri);
        String type = request.getMethod();
        logTimeInfo.setType(type);
        logTimeInfo.setLayer(0);
        logTimeInfo.setTime((int)duration);
        logTimeInfo.setTraceId(MDC.get(CommonConstant.TRACE_ID));
        logTimeInfo.setCreateDate(DateUtil.getCurrentDate());
        logTimeInfo.setUpdateDate(logTimeInfo.getCreateDate());
//        boolean add = logInfoConcurrentLinkedQueue.offer(logTimeInfo);
//        log.debug("#---------filter add to queue success : "+add);

        messageModelRingBuffer.publish(sequence);

        MDC.remove(CommonConstant.TRACE_ID);

//        String uri = request.getRequestURI();
//        log.info("--------------------------------------uri:"+ uri);
//        if(uri.endsWith(loginUri)){
//            filterChain.doFilter(servletRequest,servletResponse);
//            return;
//        }
//
//        String token = request.getHeader(CommonConstant.TOKEN);
//        if(StringUtils.isNotBlank(token)){
//            log.info("------------------------------------token:"+ token);
//            //todo
//            filterChain.doFilter(servletRequest,servletResponse);
//            return;
//        }
//        PrintWriter printWriter = servletResponse.getWriter();
//        String result = JsonUtil.toJsonString(BaseResult.fail(ResultCode.NOT_LOGIN));
//        printWriter.write(result);
//        printWriter.flush();
//        printWriter.close();
    }

    @Override
    public void destroy() {
        log.debug("-------------------------------------destroy-----------------------------------");
    }

    private void checkTraceId(String traceId) {
        if(StringUtils.isBlank(traceId)){
            throw new BizException(BaseResultCode.TRACE_ID_NECESSARY.code,BaseResultCode.TRACE_ID_NECESSARY.message);
        }
        if (traceId.length()<20 || traceId.length()>64){
            throw new BizException(BaseResultCode.TRACE_ID_LENGTH_ILLEGAL.code,BaseResultCode.TRACE_ID_LENGTH_ILLEGAL.message);
        }
        String prefix = traceId.substring(0,17);
        Date submitDate = null;
        try {
            submitDate = DateUtil.getDateFormat(DateUtil.YYYY_MM_DD_HH_MM_SS_SSS,prefix);
        } catch (BizException e) {
            throw new BizException(BaseResultCode.TRACE_ID_PREFIX_ILLEGAL.code,BaseResultCode.TRACE_ID_PREFIX_ILLEGAL.message);
        }
        if (submitDate==null){
            throw new BizException(BaseResultCode.TRACE_ID_PREFIX_ILLEGAL.code,BaseResultCode.TRACE_ID_PREFIX_ILLEGAL.message);
        }

        int seconds = DateUtil.getGapSeconds(submitDate);
        if(seconds>300){
            throw new BizException(BaseResultCode.TRACE_ID_CONTENT_EXPIRED.code,BaseResultCode.TRACE_ID_CONTENT_EXPIRED.message);
        }

        if(seconds<-5){
            throw new BizException(BaseResultCode.TRACE_ID_CONTENT_EXCEED_CURRENT.code,BaseResultCode.TRACE_ID_CONTENT_EXCEED_CURRENT.message);
        }
    }
}
