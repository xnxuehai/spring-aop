package com.park.cache.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * @author Aaron
 * @date 2020/6/7 17:10
 */
public class FastJsonSerializer<T>  implements RedisSerializer<T> {

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private Class<T> clazz;

	public FastJsonSerializer(Class<T> clazz) {
		super();
		this.clazz = clazz;
		// 解决 autoType is not support. 问题
		ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
	}

	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (null == t) {
			return new byte[0];
		}
		return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (null == bytes || bytes.length <= 0) {
			return null;
		}
		String str = new String(bytes, DEFAULT_CHARSET);
		return (T) JSON.parseObject(str, clazz);
	}
}
