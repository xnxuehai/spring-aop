package com.park.proxyfactory;

import com.park.proxyfactory.domain.ISubject;
import com.park.proxyfactory.domain.impl.SubjectImpl;
import com.park.proxyfactory.advice.MyAdvice;
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

        // 切点
        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.addMethodName("doService");

        Advisor advisor = new DefaultPointcutAdvisor(nameMatchMethodPointcut, new MyAdvice());

        proxyFactory.addAdvisor(advisor);

        ISubject subject = (ISubject) proxyFactory.getProxy();

        subject.doService();
    }
}
