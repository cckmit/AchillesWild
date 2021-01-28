package com.achilles.wild.server.common.annotations;

import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.model.response.ResultCode;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
@Order(0)
public class RequestLimitAspect {

        private final static Logger log = LoggerFactory.getLogger(RequestLimitAspect.class);

        private final static String LOG_PREFIX = "requestLimit";

        private Cache<String, AtomicInteger> cache = CacheBuilder.newBuilder().concurrencyLevel(10000).maximumSize(500).expireAfterWrite(30, TimeUnit.SECONDS).build();

        private final RateLimiter rateLimiter = RateLimiter.create(1);

        @Value("${request.limit.open}")
        private Boolean openRequestLimit;

        @Value("${request.limit.method.time.consuming.limit}")
        private Integer time;

        @Value("${request.limit.method.count.limit.per.second}")
        private Integer countLimit;

        /** 以 @requestLimit注解为切入点 */
        @Pointcut("@annotation(com.achilles.wild.server.common.annotations.RequestLimit)")
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

            String method = proceedingJoinPoint.getSignature().getDeclaringTypeName()+"#"+proceedingJoinPoint.getSignature().getName();
            log.info(LOG_PREFIX+"#method : "+method);

            if(!rateLimiter.tryAcquire()){
                return DataResult.baseFail(ResultCode.REQUESTS_TOO_FREQUENT);
            }

            method+="#requestLimit";
            AtomicInteger atomicInteger = cache.getIfPresent(method)==null ? new AtomicInteger():cache.getIfPresent(method);
            int count = atomicInteger.get();
            if(count>countLimit){
                return DataResult.baseFail(ResultCode.TOO_MANY_REQUESTS);
            }

            atomicInteger.incrementAndGet();
            cache.put(method,atomicInteger);

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
            log.info(LOG_PREFIX+"#-------------------------------afterThrowing---------------------------------------");
        }
}
