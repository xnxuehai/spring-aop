package com.park.cache.base;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

/**
 * 最顶层的缓存管理器
 *
 * @author Aaron
 * @date 2020/6/6 21:47
 */
public class CacheDemo {

	public static void main(String[] args) {
//		concurrentMapCacheManager();
		redisCacheManager();
	}

	public static void concurrentMapCacheManager() {
		//使用ConcurrentMapCacheManager可以不用初始化指定，可以get的时候动态创建Cache
		CacheManager cacheManager = new ConcurrentMapCacheManager();
		//CacheManager cacheManager = new ConcurrentMapCacheManager("car");

		// 即使我们上面没有放进去名字为car的Cache，此处也会帮我们自动生成~~~
		Cache carCache = cacheManager.getCache("car");
		// 向缓存里加数据
		carCache.put("benz", "奔驰");
		carCache.put("bmw", "宝马");
		carCache.put("audi", "奥迪");

		Cache javaCache = cacheManager.getCache("java");

		javaCache.put("j2ee", "1");
		javaCache.put("j2ee", "2");
		javaCache.put("3", "2");

		//class org.springframework.cache.concurrent.ConcurrentMapCache
		System.out.println(carCache.getClass());
		// 从缓存里获取数据
		System.out.println(carCache.get("benz").get());
		System.out.println(carCache.get("benz", String.class));
	}

	public static void redisCacheManager() {

		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofDays(1)) //Duration.ZERO表示永不过期（此值一般建议必须设置）
				//.disableKeyPrefix() // 禁用key的前缀
				//.disableCachingNullValues() //禁止缓存null值

				//=== 前缀我个人觉得是非常重要的，建议约定：注解缓存一个统一前缀、RedisTemplate直接操作的缓存一个统一前缀===
				//.prefixKeysWith("baidu:") // 底层其实调用的还是computePrefixWith() 方法，只是它的前缀是固定的（默认前缀是cacheName，此方法是把它固定住，一般不建议使用固定的）
				//.computePrefixWith(CacheKeyPrefix.simple()); // 使用内置的实现
				.computePrefixWith(cacheName -> "caching:" + cacheName); // 自己实现，建议这么使用(cacheName也保留下来了)


		RedisStandaloneConfiguration poolConfig = new RedisStandaloneConfiguration();
		poolConfig.setDatabase(0);
		poolConfig.setHostName("121.43.181.38");
		poolConfig.setPassword(RedisPassword.of("123456"));
		poolConfig.setPort(6379);
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(poolConfig);
		//必须初始化实例
		lettuceConnectionFactory.afterPropertiesSet();

		RedisCacheManager redisCacheManager = RedisCacheManager.builder(lettuceConnectionFactory)
				// .disableCreateOnMissingCache() // 关闭动态创建Cache
				//.initialCacheNames() // 初始化时候就放进去的cacheNames（若关闭了动态创建，这个就是必须的）
				.cacheDefaults(configuration) // 默认配置（强烈建议配置上）。  比如动态创建出来的都会走此默认配置
				//.withInitialCacheConfigurations() // 个性化配置  可以提供一个Map，针对每个Cache都进行个性化的配置（否则是默认配置）
				//.transactionAware() // 支持事务
				.build();

		// 即使我们上面没有放进去名字为car的Cache，此处也会帮我们自动生成~~~
		Cache carCache = redisCacheManager.getCache("car");
		// 向缓存里加数据
		carCache.put("benz", "奔驰");
		carCache.put("bmw", "宝马");
		carCache.put("audi", "奥迪");

//		Cache javaCache = redisCacheManager.getCache("java");
//
//		javaCache.put("j2ee", "1");
//		javaCache.put("j2ee", "2");
//		javaCache.put("3", "2");

		//class org.springframework.cache.concurrent.ConcurrentMapCache
//		System.out.println(carCache.getClass());
		// 从缓存里获取数据
		System.out.println(carCache.get("benz").get());
//		System.out.println(carCache.get("benz", String.class));
	}
}
