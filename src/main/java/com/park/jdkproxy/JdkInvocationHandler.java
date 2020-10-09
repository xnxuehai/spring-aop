package com.park.jdkproxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类
 *
 * @author Aaron
 * @date 2020/10/1 16:35
 */
public class JdkInvocationHandler implements InvocationHandler, Serializable {
    /**
     * 被代理对象
     */
    private Object target;

    public <T> T getInstance(T target) {
        this.target = target;
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforeMethod();
        Object invoke = method.invoke(target, args);
        afterMethod();
        return invoke;
    }

    private void afterMethod() {
        System.out.println("后置执行的方法");
    }

    private void beforeMethod() {
        System.out.println("前置执行的方法");
    }

}
