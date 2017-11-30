package com.deyuan.weather.service;

public interface WeatherService {
	String findJson(final String cityName);

	public boolean add(final String cityName, final String citydata);

	public boolean update(final String cityName, final String citydata);
}
