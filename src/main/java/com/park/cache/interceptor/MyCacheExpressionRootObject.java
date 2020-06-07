package com.park.cache.interceptor;

import org.springframework.cache.Cache;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author Aaron
 * @date 2020/6/7 21:09
 */
public class MyCacheExpressionRootObject {

	private final Collection<? extends Cache> caches;

	private final Method method;

	private final Object[] args;

	private final Object target;

	private final Class<?> targetClass;


	public MyCacheExpressionRootObject(
			Collection<? extends Cache> caches, Method method, Object[] args, Object target, Class<?> targetClass) {

		this.method = method;
		this.target = target;
		this.targetClass = targetClass;
		this.args = args;
		this.caches = caches;
	}


	public Collection<? extends Cache> getCaches() {
		return this.caches;
	}

	public Method getMethod() {
		return this.method;
	}

	public String getMethodName() {
		return this.method.getName();
	}

	public Object[] getArgs() {
		return this.args;
	}

	public Object getTarget() {
		return this.target;
	}

	public Class<?> getTargetClass() {
		return this.targetClass;
	}
}
