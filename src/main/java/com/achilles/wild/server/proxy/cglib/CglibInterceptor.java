package com.achilles.wild.server.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CglibInterceptor implements MethodInterceptor {

    private final static Logger LOG = LoggerFactory.getLogger(CglibInterceptor.class);

    /**
     * CGLIB ��ǿ����󣬴������������ Enhancer �ഴ���ģ�
     * Enhancer �� CGLIB ���ֽ�����ǿ�������Ժܷ���Ķ��������չ
     */
    private Enhancer enhancer = new Enhancer();

    /**
     *
     * @param obj  ������Ķ���
     * @param method ����ķ���
     * @param args �����Ĳ���
     * @param proxy CGLIB�����������
     * @return  cglib������������Method�����һ������ʹ��MethodProxy�ȵ���JDK�����Methodֱ��ִ�з���Ч�ʻ�������
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        LOG.info("proxy---CGLIB---------------------------------- invoke -------------------------before");
        Object o = proxy.invokeSuper(obj, args);
        LOG.info("proxy---CGLIB---------------------------------- invoke -------------------------after");
        return o;
    }


    /**
     * ʹ�ö�̬������һ���������
     * @param c
     * @return
     */
    public  Object newProxyInstance(Class<?> c) {
        /**
         * ���ò����Ĵ������ĸ���,��ǿ����
         */
        enhancer.setSuperclass(c);
        /**
         * ��������߼�����Ϊ��ǰ����Ҫ��ǰ����ʵ�� MethodInterceptor �ӿ�
         */
        enhancer.setCallback(this);
        /**
         * ʹ��Ĭ���޲����Ĺ��캯������Ŀ�����,����һ��ǰ��,���������Ҫ�ṩ�޲ι��췽��
         */
        return enhancer.create();
    }
}
