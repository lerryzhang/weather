package com.deyuan.weather.model;

public class Weather {

	private String cityName;
	private String ename;
	private String citydata;
	private String lasttime;
	private int count;

	public Weather(String cityName, String ename, String citydata,
			String lasttime) {

		this.cityName = cityName;
		this.ename = ename;
		this.citydata = citydata;
		this.lasttime = lasttime;
	}

	public String getCityName() {
		return cityName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getCitydata() {
		return citydata;
	}

	public void setCitydata(String citydata) {
		this.citydata = citydata;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

}
