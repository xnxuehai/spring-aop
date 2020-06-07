package com.park.cache.service;

import com.park.cache.domain.CacheData;
import com.park.cache.service.impl.CacheServiceImpl;

/**
 * @author Aaron
 * @date 2020/6/6 22:57
 */
public interface ICacheService {
	/**
	 * 获取数据
	 *
	 * @param id
	 * @return
	 */
	CacheData getData(String id);

	/**
	 * 删除缓存
	 *
	 * @param id
	 */
	void delData(String id);

	/**
	 * 修改缓存
	 *
	 * @param id
	 * @return
	 */
	CacheData updateData(String id);

}
