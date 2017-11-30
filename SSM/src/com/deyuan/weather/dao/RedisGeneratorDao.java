/**
 * 
 */
package com.deyuan.weather.dao;

/**
 * @ClassName RedisGeneratorDao
 * @Description TODO
 * @author lerry.zhang
 * @date  2017-11-27 ����12:35:50
 */
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public abstract class RedisGeneratorDao<K extends Serializable, V extends Serializable> {

	@Autowired
	protected RedisTemplate<K, V> redisTemplate;

	/**
	 * ����redisTemplate
	 * 
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * ��ȡ RedisSerializer <br>
	 * ------------------------------<br>
	 */
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

}