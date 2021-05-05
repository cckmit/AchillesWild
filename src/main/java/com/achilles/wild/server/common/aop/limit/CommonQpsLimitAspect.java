package com.achilles.wild.server.common.aop.limit;

import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.aop.limit.annotation.CommonQpsLimit;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(0)
public class CommonQpsLimitAspect {

        private final static Logger log = LoggerFactory.getLogger(CommonQpsLimitAspect.class);

        private final static String LOG_PREFIX = "";

        @Autowired
        CommonRateLimiterConfig commonRateLimiterConfig;

        @Pointcut("@annotation(com.achilles.wild.server.common.aop.limit.annotation.CommonQpsLimit)")
        public void commonQpsLimit() {}

        /**
         * 在切点之前织入
         * @param joinPoint
         * @throws Throwable
         */
        @Before("commonQpsLimit()")
        public void doBefore(JoinPoint joinPoint) throws Throwable {

        }

        /**
         * 环绕
         * @param proceedingJoinPoint
         * @return
         * @throws Throwable
         */
        @Around("commonQpsLimit()")
        public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

            Signature signature = proceedingJoinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            String methodName= methodSignature.getName();

            Method currentMethod = proceedingJoinPoint.getTarget().getClass().getMethod(methodName,methodSignature.getParameterTypes());

            CommonQpsLimit annotation = currentMethod.getAnnotation(CommonQpsLimit.class);
            Double permitsPerSecond = annotation.permitsPerSecond();
            if (permitsPerSecond == null) {
                throw new BizException(BaseResultCode.ILLEGAL_PARAM.code,BaseResultCode.ILLEGAL_PARAM.message);
            }
            String path = signature.getDeclaringTypeName()+"#"+methodName;
            RateLimiter rateLimiter = commonRateLimiterConfig.getRateLimiter(path,permitsPerSecond);
            if (!rateLimiter.tryAcquire()) {
                if (StringUtils.isNotEmpty(annotation.code()) || StringUtils.isNotEmpty(annotation.message()) ) {
                    throw new BizException(annotation.code(),annotation.message());
                } else {
                    throw new BizException(BaseResultCode.REQUESTS_TOO_FREQUENT.code,BaseResultCode.REQUESTS_TOO_FREQUENT.message);
                }
            }

            return proceedingJoinPoint.proceed();
        }

        /**
         * 在切点之后织入
         * @throws Throwable
         */
        @After("commonQpsLimit()")
        public void doAfter() throws Throwable {

        }

        /**
         * 在切点之后织入
         * @throws Throwable
         */
        @AfterThrowing("commonQpsLimit()")
        public void afterThrowing() throws Throwable {
//            log.info(LOG_PREFIX+"#-------------------------------afterThrowing---------------------------------------");
        }
}
