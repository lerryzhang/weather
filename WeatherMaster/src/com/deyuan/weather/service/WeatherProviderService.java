package com.deyuan.weather.service;

import com.deyuan.weather.model.Weather;

public interface WeatherProviderService {
	String getWeatherDataByRpc(String cityName);

	void updateByCityName(Weather weather);

	int insert(Weather weather);

}
