package com.deyuan.weather.util;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {

	public static void main(String[] args) throws IOException {

		// com.alibaba.dubbo.container.Main.main(args);

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "file:E:/WeatherMaster/src/resource/*.xml" });
		context.start();
		System.in.read(); // press any key to exit

	}

}
