/**
 * 
 */
package com.deyuan.weather.quartz;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.deyuan.weather.model.Weather;
import com.deyuan.weather.service.WeatherProviderService;
import com.deyuan.weather.util.WeatherDataServer;
import com.deyuan.weather.util.WeatherUtils;

/**
 * @ClassName WeatherDataRefresh
 * @Description TODO
 * @author lerry.zhang
 * @date 2017-11-27 ÏÂÎç01:45:52
 */
public class WeatherDataRefreshJob implements Job {

	private Logger logger = Logger.getLogger(WeatherDataRefreshJob.class);

	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Autowired
	private WeatherProviderService weatherProviderService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		List<String> list = null;
		String json = null;
		Map<String, String> map = new HashMap<String, String>();
		logger.info(String.format("[lerry.zhang]:%s_%s", this.getClass(),
				Thread.currentThread().getStackTrace()[1].getMethodName()));
		try {
			list = WeatherUtils.getWeatherCity();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(String.format("[lerry.zhang]:%s_%s_%s", this
					.getClass(), Thread.currentThread().getStackTrace()[1]
					.getMethodName(), e.getMessage()));
		}

		for (int i = 0; i < list.size(); i++) {
			String cityName = list.get(i).trim();
			if (null != cityName && !"".equals(cityName))
				json = WeatherDataServer.getWeatherInform(list.get(i));

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.error(String.format("[lerry.zhang]:%s_%s_%s", this
						.getClass(), Thread.currentThread().getStackTrace()[1]
						.getMethodName(), e.getMessage()));
			}
			map.put(cityName, json);

			Weather weather = new Weather(cityName, null, json, format
					.format(new Date()));

			weatherProviderService.insert(weather);

		}
		logger.info("[lerry.zhang]:start refresh redis clients");
		for (int i = 0; i < WeatherUtils.redisList.size(); i++) {
			String host = WeatherUtils.redisList.get(i);
			hmSet(host, map);
		}
		logger.info("[lerry.zhang]:refresh redis clients  success");
	}

	public boolean hmSet(String host, Map<String, String> map) {
		Jedis jedis = new Jedis(host);
		try {
			Pipeline pipeline = jedis.pipelined();
			for (String key : map.keySet()) {
				String value = map.get(key);
				pipeline.set(key, value);
			}
			pipeline.sync();
		} catch (Exception e) {
			logger.error(String.format("[lerry.zhang]:%s_%s_%s", this
					.getClass(), Thread.currentThread().getStackTrace()[1]
					.getMethodName(), e.getMessage()));
		} finally {
			jedis.close();
		}
		return true;
	}

}
