package com.deyuan.weather.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class WeatherUtils {

	private static Logger logger = Logger.getLogger(WeatherUtils.class);
	public static String iniPath;
	public static String defaultAk;
	public static String defaultInterval;
	public static String cityFile;
	public static List<String> redisList;
	static {
		Properties prop = new Properties();
		InputStream in = WeatherUtils.class.getClassLoader()
				.getResourceAsStream("config.properties");
		try {
			prop.load(in);
			iniPath = prop.getProperty("setting").trim();
			defaultAk = prop.getProperty("ak").trim();
			defaultInterval = prop.getProperty("interval").trim();
			cityFile = prop.getProperty("city").trim();
			String client = prop.getProperty("clients").trim();
			redisList = Arrays.asList(client.split(";"));
		} catch (IOException e) {
			logger.info(String.format("[lerry.zhang]:%s_%s_%s", Thread
					.currentThread().getStackTrace()[1].getClassName(), Thread
					.currentThread().getStackTrace()[1].getMethodName(), e
					.getMessage()));
		}
	}

	public static List<String> getWeatherCity() throws IOException {

		ConfigReader configReader = new ConfigReader(iniPath);
		List<String> lst = new LinkedList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(new File(
				configReader.getValue("Filepath", "path"))));
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!"".equals(line))
					lst.add(line.trim());
			}
		} finally {
			reader.close();
		}
		return lst;

	}

	public static String getAk() throws IOException {

		ConfigReader configReader = new ConfigReader(iniPath);
		String Ak = configReader.getValue("ak", "ak");
		if (Ak == null || "".equals(Ak))
			Ak = defaultAk;
		return Ak;

	}

	public static int getInterval() {
		ConfigReader configReader = null;
		try {
			configReader = new ConfigReader(iniPath);
		} catch (Exception e) {
			return Integer.parseInt(defaultInterval);
		}
		String interval = configReader.getValue("interval", "interval");
		if (interval == null || "".equals(interval))
			interval = defaultInterval;
		return Integer.parseInt(interval);
	}

}
