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
public class Client {

    public static void main(String[] args) {
        // 真实类
        BusinessInterface relInterface = new SaveBusiness();

        // 代理类
        JdkProxy jdkInvocationHandler = new JdkProxy();
        BusinessInterface instance = jdkInvocationHandler.getInstance(relInterface);

        instance.login("Aaron", "123456");

        save(instance);
    }

    /**
     * 将代理对象输出
     *
     * @param instance
     */
    private static void save(BusinessInterface instance) {
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
