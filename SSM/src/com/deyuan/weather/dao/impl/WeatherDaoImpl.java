/**
 * 
 */
package com.deyuan.weather.dao.impl;

/**
 * @ClassName WeatherDaoImpl
 * @Description TODO
 * @author lerry.zhang
 * @date  2017-11-27 下午12:37:10
 */
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import com.deyuan.weather.dao.RedisGeneratorDao;
import com.deyuan.weather.dao.WeatherDao;

@Repository(value = "memberDao")
public class WeatherDaoImpl extends RedisGeneratorDao<String, String> implements
		WeatherDao {

	/**
	 * 添加对象
	 */
	@Override
	public boolean add(final String cityName, final String citydata) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(cityName);
				byte[] name = serializer.serialize(citydata);
				return connection.setNX(key, name);
			}
		});
		return result;
	}

	/**
	 * 删除对象 ,依赖key
	 */
	public void delete(String key) {
		List<String> list = new ArrayList<String>();
		list.add(key);
		delete(list);
	}

	/**
	 * 删除集合 ,依赖key集合
	 */
	public void delete(List<String> keys) {
		redisTemplate.delete(keys);
	}

	/**
	 * 修改对象
	 */
	public boolean update(final String cityName, final String citydata) {

		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(cityName);
				byte[] name = serializer.serialize(citydata);
				connection.set(key, name);
				return true;
			}
		});
		return result;
	}

	/**
	 * 根据key获取对象
	 */
	@Override
	public String findJson(final String cityName) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(cityName);
				byte[] value = connection.get(key);
				if (value == null) {
					return null;
				}
				String citydata = serializer.deserialize(value);
				return citydata;
			}
		});
		return result;
	}
}