package com.park.jdkproxy;

import sun.misc.ProxyGenerator;

import java.io.*;
import java.lang.reflect.Proxy;

/**
 * {@link Proxy} JDK 动态代理 demo
 *
 * @author Aaron
 * @date 2020/10/1 16:24
 */
public class JdkProxyDemo {

    public static void main(String[] args) {
        // 真实类
        RelInterface relInterface = new RelObject();

        // 代理类
        JdkInvocationHandler jdkInvocationHandler = new JdkInvocationHandler();
        RelInterface instance = jdkInvocationHandler.getInstance(relInterface);

        instance.sendMessage("Aaron");


    }

    /**
     * 将代理对象输出
     *
     * @param instance
     */
    private static void save(RelInterface instance) {
        byte[] proxyClass = ProxyGenerator.generateProxyClass(instance.getClass()
                .getSimpleName(), instance.getClass().getInterfaces());

        try {
            FileOutputStream fos = new FileOutputStream("D:\\java_work\\spring-aop\\target\\proxy.class");
            fos.write(proxyClass);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
