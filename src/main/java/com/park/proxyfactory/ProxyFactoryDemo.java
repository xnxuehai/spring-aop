package com.park.proxyfactory;

import com.park.proxyfactory.domain.ISubject;
import com.park.proxyfactory.domain.impl.SubjectImpl;
import com.park.proxyfactory.advice.MyAdvice;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

/**
 * 本代码实例将介绍 {@link ProxyFactory} 工厂
 *
 * @author Aaron
 * @since
 */
public class ProxyFactoryDemo {

    public static void main(String[] args) {
        // 创建一个代理类的工厂
        ProxyFactory proxyFactory = new ProxyFactory(new SubjectImpl());

        // 实例化一个切点
        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        // 设置需要匹配的方法名称
        nameMatchMethodPointcut.addMethodName("doService");

        // 创建自定义的通知
        Advice myAdvice = new MyAdvice();

        // 创建一个切面，并且设置切点和通知
        Advisor advisor = new DefaultPointcutAdvisor(nameMatchMethodPointcut, myAdvice);

        // 将切面设置到代理工厂中
        proxyFactory.addAdvisor(advisor);

        // 通过代理工厂生成一个代理对象
        ISubject subject = (ISubject) proxyFactory.getProxy();

        // 调用代理对象的方法
        subject.doService();
    }
}
