package com.park.cache.annotation;

import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.annotation.CacheAnnotationParser;

import java.util.Set;

/**
 * @author Aaron
 * @date 2020/6/7 20:36
 */
public class MyAnnotationCacheOperationSource extends AnnotationCacheOperationSource {
	public MyAnnotationCacheOperationSource(Set<CacheAnnotationParser> annotationParsers) {
		super(annotationParsers);
	}
}
