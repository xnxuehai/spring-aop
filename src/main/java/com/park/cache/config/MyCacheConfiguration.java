package com.park.cache.config;

import com.park.cache.annotation.EnableMyCaching;
import com.park.cache.annotation.MyAnnotationCacheOperationSource;
import com.park.cache.annotation.MyCacheAnnotationParser;
import com.park.cache.interceptor.MyCacheInterceptor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.annotation.AbstractCachingConfiguration;
import org.springframework.cache.annotation.CacheAnnotationParser;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.config.CacheManagementConfigUtils;
import org.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.CacheOperationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Collections;
import java.util.Set;

/**
 * @author Aaron
 * @date 2020/6/7 20:29
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class MyCacheConfiguration extends AbstractCachingConfiguration {

	@Bean(name = CacheManagementConfigUtils.CACHE_ADVISOR_BEAN_NAME)
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public BeanFactoryCacheOperationSourceAdvisor cacheAdvisor() {
		BeanFactoryCacheOperationSourceAdvisor advisor = new BeanFactoryCacheOperationSourceAdvisor();
		advisor.setCacheOperationSource(cacheOperationSource());
		advisor.setAdvice(myCacheInterceptor());
		if (this.enableCaching != null) {
			advisor.setOrder(this.enableCaching.<Integer>getNumber("order"));
		}
		return advisor;
	}

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public CacheOperationSource cacheOperationSource() {
		Set<CacheAnnotationParser> annotationParsers = Collections.singleton(new MyCacheAnnotationParser());
		MyAnnotationCacheOperationSource annotationCacheOperationSource = new MyAnnotationCacheOperationSource(annotationParsers);
		return annotationCacheOperationSource;
	}

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public MyCacheInterceptor myCacheInterceptor() {
		MyCacheInterceptor interceptor = new MyCacheInterceptor();
		interceptor.configure(this.errorHandler, this.keyGenerator, this.cacheResolver, this.cacheManager);
		interceptor.setCacheOperationSource(cacheOperationSource());
		return interceptor;
	}


	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		this.enableCaching = AnnotationAttributes.fromMap(
				importMetadata.getAnnotationAttributes(EnableMyCaching.class.getName(), false));
		if (this.enableCaching == null) {
			throw new IllegalArgumentException(
					"@EnableMyCaching is not present on importing class " + importMetadata.getClassName());
		}
	}
}
