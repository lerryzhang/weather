package com.deyuan.weather.dao;

import com.deyuan.weather.model.Weather;

public interface WeatherDao {
	int insert(Weather weather);

	void updateByCityName(Weather weather);

	String findJson(String cityName);
}
