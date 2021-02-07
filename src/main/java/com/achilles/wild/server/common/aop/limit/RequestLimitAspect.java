package com.achilles.wild.server.common.aop.limit;

import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.model.response.ResultCode;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
@Order(0)
public class RequestLimitAspect {

        private final static Logger log = LoggerFactory.getLogger(RequestLimitAspect.class);

        private final static String LOG_PREFIX = "";

        private Cache<String, AtomicInteger> integerCache = CacheBuilder.newBuilder().concurrencyLevel(5000).maximumSize(500).expireAfterWrite(10, TimeUnit.SECONDS).build();

        private Cache<String, RateLimiter> rateLimiterCache = CacheBuilder.newBuilder().concurrencyLevel(5000).maximumSize(500).expireAfterWrite(10, TimeUnit.SECONDS).build();

//        private final RateLimiter rateLimiter = RateLimiter.create(1);

        @Value("${request.limit.open}")
        private Boolean openRequestLimit;

//        @Value("${request.limit.of.time.consuming.limit}")
//        private Integer time;

//        @Value("${request.limit.of.count.limit.in.time}")
//        private Integer countLimit;

        /** 以 @requestLimit注解为切入点 */
        @Pointcut("@annotation(com.achilles.wild.server.common.aop.limit.RequestLimit)")
        public void requestLimit() {}

        /**
         * 在切点之前织入
         * @param joinPoint
         * @throws Throwable
         */
        @Before("requestLimit()")
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
        @Around("requestLimit()")
        public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

            if(!openRequestLimit){
                return proceedingJoinPoint.proceed();
            }

            Signature signature = proceedingJoinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            String methodName= methodSignature.getName();

            String path = signature.getDeclaringTypeName()+"#"+methodName;
            log.info(LOG_PREFIX+"#method : "+path);
            Method currentMethod = proceedingJoinPoint.getTarget().getClass().getMethod(methodName,methodSignature.getParameterTypes());
            RequestLimit annotation = currentMethod.getAnnotation(RequestLimit.class);
            double rateLimit = annotation.rateLimit();
            String rateLimiterKey = path+"_RateLimiter";
            RateLimiter rateLimiter = rateLimiterCache.getIfPresent(rateLimiterKey)==null ?
                                      RateLimiter.create(rateLimit):rateLimiterCache.getIfPresent(rateLimiterKey);
            rateLimiterCache.put(rateLimiterKey,rateLimiter);
            if(!rateLimiter.tryAcquire()){
                throw new BizException(ResultCode.REQUESTS_TOO_FREQUENT);
            }

            String countLimitKey = path+"_CountLimit";
            AtomicInteger atomicInteger = integerCache.getIfPresent(countLimitKey)==null ? new AtomicInteger():integerCache.getIfPresent(countLimitKey);
            int count = atomicInteger.get();
            int countLimit = annotation.countLimit();
            if(count>countLimit){
                throw new BizException(ResultCode.TOO_MANY_REQUESTS);
            }

            atomicInteger.incrementAndGet();
            integerCache.put(countLimitKey,atomicInteger);

            return proceedingJoinPoint.proceed();
        }

        /**
         * 在切点之后织入
         * @throws Throwable
         */
        @After("requestLimit()")
        public void doAfter() throws Throwable {

        }

        /**
         * 在切点之后织入
         * @throws Throwable
         */
        @AfterThrowing("requestLimit()")
        public void afterThrowing() throws Throwable {
//            log.info(LOG_PREFIX+"#-------------------------------afterThrowing---------------------------------------");
        }
}
