package com.park.cache.interceptor;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * @author Aaron
 * @date 2020/6/7 21:27
 */
public class MySimpleCacheResolver extends AbstractCacheResolver {
	/**
	 * Construct a new {@code SimpleCacheResolver}.
	 *
	 * @see #setCacheManager
	 */
	public MySimpleCacheResolver() {
	}

	/**
	 * Construct a new {@code SimpleCacheResolver} for the given {@link CacheManager}.
	 *
	 * @param cacheManager the CacheManager to use
	 */
	public MySimpleCacheResolver(CacheManager cacheManager) {
		super(cacheManager);
	}


	@Override
	protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
		return context.getOperation().getCacheNames();
	}


	/**
	 * Return a {@code SimpleCacheResolver} for the given {@link CacheManager}.
	 *
	 * @param cacheManager the CacheManager (potentially {@code null})
	 * @return the SimpleCacheResolver ({@code null} if the CacheManager was {@code null})
	 * @since 5.1
	 */
	@Nullable
	static SimpleCacheResolver of(@Nullable CacheManager cacheManager) {
		return (cacheManager != null ? new SimpleCacheResolver(cacheManager) : null);
	}
}
