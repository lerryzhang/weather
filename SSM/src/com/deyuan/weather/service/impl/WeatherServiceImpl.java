package com.deyuan.weather.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.deyuan.weather.dao.WeatherDao;
import com.deyuan.weather.service.WeatherService;

@Service("weatherService")
public class WeatherServiceImpl implements WeatherService {

	@Resource
	public WeatherDao weatherDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deyuan.weather.service.WeatherService#findJson(java.lang.String)
	 */
	@Override
	public String findJson(String cityName) {
		// TODO Auto-generated method stub
		return weatherDao.findJson(cityName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deyuan.weather.service.WeatherService#add(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean add(String cityName, String citydata) {
		// TODO Auto-generated method stub
		return weatherDao.add(cityName, citydata);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deyuan.weather.service.WeatherService#update(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean update(String cityName, String citydata) {
		// TODO Auto-generated method stub
		return weatherDao.update(cityName, citydata);
	}
}
