package com.park.cache.service.impl;

import com.park.cache.annotation.AddCache;
import com.park.cache.domain.CacheData;
import com.park.cache.service.ICacheService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

/**
 * @author Aaron
 * @date 2020/6/6 21:49
 */
@Service
public class CacheServiceImpl implements ICacheService {

    @Override
//	@Cacheable(value = "addCache#50", key = "#id")
    @AddCache(field = {"name", "pwd"}, value = "addCache", key = "#id")
    public CacheData getData(String id) {
        System.out.println("模拟从 db 查询数据..." + id);
        CacheData cacheData = new CacheData();
        cacheData.setName("Aaron");
        cacheData.setPwd(20);
        return cacheData;
    }

    @Override
    @CacheEvict(value = "addCache", key = "#id")
    public void delData(String id) {
        System.out.println("执行删除缓存..." + id);
    }

    @Override
    @CachePut(value = "addCache", key = "#id")
    public CacheData updateData(String id) {
        System.out.println("执行修改缓存..." + id);
        CacheData cacheData = new CacheData();
        cacheData.setName("Janet");
        cacheData.setPwd(18);
        return cacheData;
    }

}
