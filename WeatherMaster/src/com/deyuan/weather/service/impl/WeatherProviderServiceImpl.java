package com.deyuan.weather.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.deyuan.weather.dao.WeatherDao;
import com.deyuan.weather.model.Weather;
import com.deyuan.weather.service.WeatherProviderService;
import com.deyuan.weather.util.WeatherDataServer;

public class WeatherProviderServiceImpl implements WeatherProviderService {
	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private Logger logger = Logger.getLogger(WeatherProviderServiceImpl.class);

	@Resource
	private WeatherDao weatherDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deyuan.weather.service.WeatherService#findJson(java.lang.String)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deyuan.weather.service.WeatherProviderService#getWeatherDataByRpc
	 * (java.lang.String)
	 */
	@Override
	public String getWeatherDataByRpc(String cityName) {
		// TODO Auto-generated method stub
		String citydata = weatherDao.findJson(cityName);
		if (null != citydata)
			return citydata;
		citydata = WeatherDataServer.getWeatherInform(cityName);
		String lasttime = format.format(new Date());
		Weather weather = new Weather(cityName, null, citydata, lasttime);
		try {
			weatherDao.insert(weather);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.info(String.format("[lerry.zhang]:%s_%s_%s",
					this.getClass(), Thread.currentThread().getStackTrace()[1]
							.getMethodName(), e.getMessage()));
			return citydata;
		}
		try {
			WeatherDataServer.saveWeatherCityFile(cityName);
		} catch (Exception e) {
			logger.info(String.format("[lerry.zhang]:%s_%s_%s",
					this.getClass(), Thread.currentThread().getStackTrace()[1]
							.getMethodName(), e.getMessage()));
			return citydata;
		}
		return citydata;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deyuan.weather.service.WeatherProviderService#saveWeather(com.deyuan
	 * .weather.model.Weather)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deyuan.weather.service.WeatherProviderService#updateByCityName(com
	 * .deyuan.weather.model.Weather)
	 */
	@Override
	public void updateByCityName(Weather weather) {
		// TODO Auto-generated method stub
		weatherDao.updateByCityName(weather);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deyuan.weather.service.WeatherProviderService#insert(com.deyuan.weather
	 * .model.Weather)
	 */
	@Override
	public int insert(Weather weather) {
		// TODO Auto-generated method stub
		return weatherDao.insert(weather);
	}
}
