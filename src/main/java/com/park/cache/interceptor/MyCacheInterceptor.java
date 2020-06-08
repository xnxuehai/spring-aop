package com.park.cache.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author Aaron
 * @date 2020/6/7 21:28
 */
public class MyCacheInterceptor extends MyCacheAspectSupport implements MethodInterceptor, Serializable {

	@Override
	@Nullable
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();

		CacheOperationInvoker aopAllianceInvoker = () -> {
			try {
				return invocation.proceed();
			} catch (Throwable ex) {
				throw new CacheOperationInvoker.ThrowableWrapper(ex);
			}
		};

		try {
			return execute(aopAllianceInvoker, invocation.getThis(), method, invocation.getArguments());
		} catch (CacheOperationInvoker.ThrowableWrapper th) {
			throw th.getOriginal();
		}
	}


}
