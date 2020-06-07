package com.park.proxyfactory;

import com.park.domain.ISubject;
import com.park.domain.impl.SubjectImpl;
import com.park.proxyfactory.advice.MyAdvice;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * 本代码实例将介绍 {@link ProxyFactory} 工厂
 *
 * @author Aaron
 * @since
 */
public class ProxyFactoryDemo {

	public static void main(String[] args) {

		// 拦截上方法标有@AfterTransaction此注解的任意方法们
		String pointcutExpression = "@annotation(com.park.annotation.AaronCache)";
		//声明一个aspectj切点,一张切面
		AspectJExpressionPointcut cut = new AspectJExpressionPointcut();
		// 设置切点表达式
		cut.setExpression(pointcutExpression);

		ProxyFactory proxyFactory = new ProxyFactory(new SubjectImpl());

		Advisor advisor = new DefaultPointcutAdvisor(cut, new MyAdvice());

		proxyFactory.addAdvisor(advisor);

		ISubject subject = (ISubject) proxyFactory.getProxy();

		subject.doService();
	}
}
