package com.park.proxyfactory.advice;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 基于实现接口的方式实现通知
 *
 * @author Aaron
 * @date 2020/6/6 15:46
 */
public class MyAdvice implements MethodBeforeAdvice, AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		System.out.printf("在方法%s执行之后执行%s方法\n", method.getName(), "afterReturning");
	}

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.printf("在方法%s执行之前执行%s方法\n", method.getName(), "before");
	}

}
