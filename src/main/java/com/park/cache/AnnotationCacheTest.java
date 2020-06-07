package com.park.cache;

import com.park.cache.annotation.EnableMyCaching;
import com.park.cache.cachewriter.HashRedisCacheWriter;
import com.park.cache.serializer.FastJsonSerializer;
import com.park.cache.service.ICacheService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * 基于注解方式 实现缓存
 *
 * @author Aaron
 * @date 2020/6/7 12:27
 */
@EnableMyCaching
@ComponentScan(basePackages = "com.park.cache")
public class AnnotationCacheTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(AnnotationCacheTest.class);
		applicationContext.refresh();

		ICacheService bean = applicationContext.getBean(ICacheService.class);

		System.out.println(bean.getData("1"));

		System.out.println(bean.getData("1"));

		System.out.println(bean.updateData("1"));

		System.out.println(bean.getData("1"));

		bean.delData("1");

	}

	@Bean
	public CacheManager cacheManager() {

		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
				//Duration.ZERO表示永不过期（此值一般建议必须设置）
				.entryTtl(Duration.ofDays(1))
				//.disableKeyPrefix() // 禁用key的前缀
				//.disableCachingNullValues() //禁止缓存null值
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonSerializer<>(String.class)))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonSerializer<>(Object.class)))

				//=== 前缀我个人觉得是非常重要的，建议约定：注解缓存一个统一前缀、RedisTemplate直接操作的缓存一个统一前缀===
				//.prefixKeysWith("baidu:") // 底层其实调用的还是computePrefixWith() 方法，只是它的前缀是固定的（默认前缀是cacheName，此方法是把它固定住，一般不建议使用固定的）
				//.computePrefixWith(CacheKeyPrefix.simple()); // 使用内置的实现
				// 自己实现，建议这么使用(cacheName也保留下来了)
				.computePrefixWith(cacheName -> "caching:" + cacheName);


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
				.cacheWriter(new HashRedisCacheWriter(lettuceConnectionFactory))
				.build();

		return redisCacheManager;
	}
}
