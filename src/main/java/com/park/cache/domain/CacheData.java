package com.park.cache.domain;


/**
 * 数据缓存对象
 *
 * @author Aaron
 * @date 2020/6/7 17:07
 */
public class CacheData {
	private String name;
	private Integer pwd;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPwd() {
		return pwd;
	}

	public void setPwd(Integer pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "CacheData{" +
				"name='" + name + '\'' +
				", pwd=" + pwd +
				'}';
	}
}