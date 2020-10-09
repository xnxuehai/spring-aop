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
public class JdkProxy implements InvocationHandler, Serializable {
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
        Object invoke = null;
        if (beforeMethod(args[0], args[1])) {
            invoke = method.invoke(target, args);
            afterMethod();
        }
        invoke = false;
        return invoke;
    }

    /**
     * 通知用户注册成功
     */
    private void afterMethod() {
        System.out.println("登录成功，发送短信提示用户");
    }

    /**
     * 验证用户名和密码合不合法
     *
     * @param name 用户名
     * @param pwd  密码
     * @return true or false
     */
    private boolean beforeMethod(Object name, Object pwd) {
        System.out.println("开始验证参数的正确性");
        if ("Aaron".equals(name.toString()) && "123456".equals(pwd.toString())) {
            System.out.println("用户名和密码合法");
            return true;
        } else {
            System.out.println("用户名和密码不合法");
            return false;
        }
    }

}
