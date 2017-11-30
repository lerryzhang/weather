package com.deyuan.weather.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deyuan.weather.service.WeatherProviderService;

public class MyTest {
	/*
	 * static {
	 * PropertyConfigurator.configure("E:/SSM/src/resource/log4j.properties");
	 * 
	 * }
	 * 
	 * private static Logger logger = Logger.getLogger(MyTest.class);
	 */
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "file:E:/SSM/src/resource/weather-dubbo-consumer.xml" });

		context.start();

		WeatherProviderService weatherProviderService = (WeatherProviderService) context
				.getBean("weatherProviderService");
		weatherProviderService.getWeatherDataByRpc("…œ∫£");

	}
}
