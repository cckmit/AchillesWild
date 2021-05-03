package com.achilles.wild.server.common.aop.limit;

import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.aop.limit.annotation.QpsLimit;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(0)
public class QpsLimitAspect {

        private final static Logger log = LoggerFactory.getLogger(QpsLimitAspect.class);

        private final static String LOG_PREFIX = "";

//        private final RateLimiter rateLimiter = RateLimiter.create(1);

        @Value("${request.limit.open}")
        private Boolean openRequestLimit;

        @Autowired
        ApplicationContext applicationContext;

        /** 以 @qpsLimit注解为切入点 */
        @Pointcut("@annotation(com.achilles.wild.server.common.aop.limit.annotation.QpsLimit)")
        public void qpsLimit() {}

        /**
         * 在切点之前织入
         * @param joinPoint
         * @throws Throwable
         */
        @Before("qpsLimit()")
        public void doBefore(JoinPoint joinPoint) throws Throwable {
            if(!openRequestLimit){
                return;
            }
        }

        /**
         * 环绕
         * @param proceedingJoinPoint
         * @return
         * @throws Throwable
         */
        @Around("qpsLimit()")
        public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

            if(!openRequestLimit){
                return proceedingJoinPoint.proceed();
            }

            Signature signature = proceedingJoinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            String methodName= methodSignature.getName();

            Method currentMethod = proceedingJoinPoint.getTarget().getClass().getMethod(methodName,methodSignature.getParameterTypes());

            QpsLimit annotation = currentMethod.getAnnotation(QpsLimit.class);
            BaseRateLimiterService rateLimiterConfig = (BaseRateLimiterService) applicationContext.getBean(annotation.limitClass());
            Double permitsPerSecond = rateLimiterConfig.getPermitsPerSecond();
            if (permitsPerSecond == null) {
                permitsPerSecond = annotation.permitsPerSecond();
            }
            RateLimiter rateLimiter = rateLimiterConfig.getRateLimiter(permitsPerSecond);
            if (!rateLimiter.tryAcquire()) {
                throw new BizException(BaseResultCode.REQUESTS_TOO_FREQUENT.code,BaseResultCode.REQUESTS_TOO_FREQUENT.message);
            }

            return proceedingJoinPoint.proceed();
        }

        /**
         * 在切点之后织入
         * @throws Throwable
         */
        @After("qpsLimit()")
        public void doAfter() throws Throwable {

        }

        /**
         * 在切点之后织入
         * @throws Throwable
         */
        @AfterThrowing("qpsLimit()")
        public void afterThrowing() throws Throwable {
//            log.info(LOG_PREFIX+"#-------------------------------afterThrowing---------------------------------------");
        }
}
