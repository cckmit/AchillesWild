package com.achilles.wild.server.design.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class CglibInterceptor implements MethodInterceptor {

    private final static Logger LOG = LoggerFactory.getLogger(CglibInterceptor.class);


    private Enhancer enhancer = new Enhancer();


    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        LOG.info("proxy---CGLIB---------------------------------- invoke -------------------------before");
        Object o = proxy.invokeSuper(obj, args);
        LOG.info("proxy---CGLIB---------------------------------- invoke -------------------------after");
        return o;
    }


    public  Object newProxyInstance(Class<?> c) {

        enhancer.setSuperclass(c);

        enhancer.setCallback(this);

        return enhancer.create();
    }
}
