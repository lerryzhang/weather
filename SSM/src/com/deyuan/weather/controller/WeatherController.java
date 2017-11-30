package com.deyuan.weather.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deyuan.weather.service.WeatherProviderService;
import com.deyuan.weather.service.WeatherService;

@Controller
@RequestMapping("/")
public class WeatherController {
	// private Logger logger = Logger.getLogger(WeatherController.class);

	@Resource
	private WeatherService weatherService;

	@Resource
	private WeatherProviderService weatherProviderService;

	@RequestMapping(value = "/getData", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public String getJson(@RequestParam("cityName") String cityName) {
		String json = null;

		try {
			cityName = new String(cityName.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			return json;
		}
		json = weatherService.findJson(cityName);
		if (json == null)
			json = weatherProviderService.getWeatherDataByRpc(cityName);

		weatherService.add(cityName, json);
		return json;
	}
}
