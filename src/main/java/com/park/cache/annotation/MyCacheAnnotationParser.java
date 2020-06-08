package com.park.cache.annotation;

import com.park.cache.interceptor.AddCacheOperation;
import org.springframework.cache.annotation.*;
import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.cache.interceptor.CacheableOperation;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Aaron
 * @date 2020/6/7 20:27
 */
public class MyCacheAnnotationParser extends SpringCacheAnnotationParser {

    private static final Set<Class<? extends Annotation>> CACHE_OPERATION_ANNOTATIONS = new LinkedHashSet<>(1);

    static {
        CACHE_OPERATION_ANNOTATIONS.add(AddCache.class);
    }

    @Override
    @Nullable
    public Collection<CacheOperation> parseCacheAnnotations(Class<?> type) {
        DefaultCacheConfig defaultConfig = new DefaultCacheConfig(type);
        Collection<CacheOperation> cacheOperations = parseCacheAnnotations(defaultConfig, type);
        if (cacheOperations == null || cacheOperations.size() == 0) {
            // 委派给父类进行解析
            return super.parseCacheAnnotations(type);
        }
        return cacheOperations;
    }

    @Override
    @Nullable
    public Collection<CacheOperation> parseCacheAnnotations(Method method) {
        DefaultCacheConfig defaultConfig = new DefaultCacheConfig(method.getDeclaringClass());
        Collection<CacheOperation> cacheOperations = parseCacheAnnotations(defaultConfig, method);
        if (cacheOperations == null || cacheOperations.size() == 0) {
            // 委派给父类进行解析
            return super.parseCacheAnnotations(method);
        }
        return parseCacheAnnotations(defaultConfig, method);
    }

    @Nullable
    private Collection<CacheOperation> parseCacheAnnotations(DefaultCacheConfig cachingConfig, AnnotatedElement ae) {
        Collection<CacheOperation> ops = parseCacheAnnotations(cachingConfig, ae, false);
        if (ops != null && ops.size() > 1) {
            // More than one operation found -> local declarations override interface-declared ones...
            Collection<CacheOperation> localOps = parseCacheAnnotations(cachingConfig, ae, true);
            if (localOps != null) {
                return localOps;
            }
        }
        return ops;
    }

    @Nullable
    private Collection<CacheOperation> parseCacheAnnotations(DefaultCacheConfig cachingConfig, AnnotatedElement ae, boolean localOnly) {

        Collection<? extends Annotation> anns = (localOnly ?
                AnnotatedElementUtils.getAllMergedAnnotations(ae, CACHE_OPERATION_ANNOTATIONS) :
                AnnotatedElementUtils.findAllMergedAnnotations(ae, CACHE_OPERATION_ANNOTATIONS));
        if (anns.isEmpty()) {
            return null;
        }

        final Collection<CacheOperation> ops = new ArrayList<>(1);
        anns.stream().filter(ann -> ann instanceof AddCache).forEach(ann -> ops.add(parseAddCacheOperation(ae, cachingConfig, (AddCache) ann)));
        return ops;
    }

    /**
     * 解析注解逻辑
     *
     * @param ae
     * @param defaultConfig
     * @param addCache
     * @return
     */
    private AddCacheOperation parseAddCacheOperation(
            AnnotatedElement ae, DefaultCacheConfig defaultConfig, AddCache addCache) {

        AddCacheOperation.Builder builder = new AddCacheOperation.Builder();

        builder.setName(ae.toString());
        builder.setField(addCache.field());
        builder.setCacheNames(addCache.cacheNames());
        builder.setCondition(addCache.condition());
        builder.setUnless(addCache.unless());
        builder.setKey(addCache.key());
        builder.setKeyGenerator(addCache.keyGenerator());
        builder.setCacheManager(addCache.cacheManager());
        builder.setCacheResolver(addCache.cacheResolver());
        builder.setSync(addCache.sync());

        defaultConfig.applyDefault(builder);
        AddCacheOperation op = builder.build();
        validateCacheOperation(ae, op);

        return op;
    }

    private void validateCacheOperation(AnnotatedElement ae, CacheOperation operation) {
        System.out.println("------------ 执行 validateCacheOperation ------------");
    }

    private static class DefaultCacheConfig {

        private final Class<?> target;

        @Nullable
        private String[] cacheNames;

        @Nullable
        private String keyGenerator;

        @Nullable
        private String cacheManager;

        @Nullable
        private String cacheResolver;

        private boolean initialized = false;

        public DefaultCacheConfig(Class<?> target) {
            this.target = target;
        }

        /**
         * Apply the defaults to the specified {@link CacheOperation.Builder}.
         *
         * @param builder the operation builder to update
         */
        public void applyDefault(CacheOperation.Builder builder) {
            if (!this.initialized) {
                CacheConfig annotation = AnnotatedElementUtils.findMergedAnnotation(this.target, CacheConfig.class);
                if (annotation != null) {
                    this.cacheNames = annotation.cacheNames();
                    this.keyGenerator = annotation.keyGenerator();
                    this.cacheManager = annotation.cacheManager();
                    this.cacheResolver = annotation.cacheResolver();
                }
                this.initialized = true;
            }

            if (builder.getCacheNames().isEmpty() && this.cacheNames != null) {
                builder.setCacheNames(this.cacheNames);
            }
            if (!StringUtils.hasText(builder.getKey()) && !StringUtils.hasText(builder.getKeyGenerator()) &&
                    StringUtils.hasText(this.keyGenerator)) {
                builder.setKeyGenerator(this.keyGenerator);
            }

            if (StringUtils.hasText(builder.getCacheManager()) || StringUtils.hasText(builder.getCacheResolver())) {
                // One of these is set so we should not inherit anything
            } else if (StringUtils.hasText(this.cacheResolver)) {
                builder.setCacheResolver(this.cacheResolver);
            } else if (StringUtils.hasText(this.cacheManager)) {
                builder.setCacheManager(this.cacheManager);
            }
        }
    }
}
