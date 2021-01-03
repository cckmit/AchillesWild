package com.achilles.wild.server.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaProxyInvocationHandler  implements InvocationHandler {

    private final static Logger LOG = LoggerFactory.getLogger(JavaProxyInvocationHandler.class);

    /**
     * �м������ί������������,����ṹ��һ�־�̬�����ϵ
     */
    private Object obj ;

    /**
     * �вι�����,����ί����Ķ���
     * @param obj ί����Ķ���
     */
    public JavaProxyInvocationHandler(Object obj){
        this.obj = obj;

    }

    /**
     * ��̬���ɴ��������,Proxy.newProxyInstance
     * @return ���ش������ʵ��
     */
    public Object newProxyInstance() {
        return Proxy.newProxyInstance(
            //ָ�����������������
            obj.getClass().getClassLoader(),
            //���������Ҫʵ�ֵĽӿڣ�����ͬʱָ������ӿ�
            obj.getClass().getInterfaces(),
            //�������õ�ʵ�ʴ����ߣ��������ķ������ö���ת��������
            this);
    }


    /**
     *
     * @param proxy �������
     * @param method ������
     * @param args �����Ĳ���
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOG.info("proxy---JDK---------------------------------- invoke -------------------------before");
        Object result = method.invoke(obj, args);
        LOG.info("proxy----JDK--------------------------------- invoke -------------------------after");
        return result;
    }
}
