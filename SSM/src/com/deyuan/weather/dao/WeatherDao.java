package com.deyuan.weather.dao;

public interface WeatherDao {
	String findJson(final String cityName);

	public boolean add(final String cityName, final String citydata);

	public boolean update(final String cityName, final String citydata);
}
