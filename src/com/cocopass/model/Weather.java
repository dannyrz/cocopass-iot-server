package com.cocopass.model;

import java.util.Date;

public class Weather {
	int ID=0;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getCityCode() {
		return CityCode;
	}
	public void setCityCode(String cityCode) {
		CityCode = cityCode;
	}
	public float getTemperature() {
		return Temperature;
	}
	public void setTemperature(float temperature) {
		Temperature = temperature;
	}
	public String getWindDirection() {
		return WindDirection;
	}
	public void setWindDirection(String windDirection) {
		WindDirection = windDirection;
	}
	public String getWindSpeed() {
		return WindSpeed;
	}
	public void setWindSpeed(String windSpeed) {
		WindSpeed = windSpeed;
	}
	public float getHumidity() {
		return Humidity;
	}
	public void setHumidity(float humidity) {
		Humidity = humidity;
	}
	public String getReportTime() {
		return ReportTime;
	}
	public void setReportTime(String reportTime) {
		ReportTime = reportTime;
	}
	public String getUpdateTime() {
		return UpdateTime;
	}
	public void setUpdateTime(String updateTime) {
		UpdateTime = updateTime;
	}
	public String getRadar() {
		return Radar;
	}
	public void setRadar(String radar) {
		Radar = radar;
	}
	public int getAirPressure() {
		return AirPressure;
	}
	public void setAirPressure(int airPressure) {
		AirPressure = airPressure;
	}
	public int getRain() {
		return Rain;
	}
	public void setRain(int rain) {
		Rain = rain;
	}
	public String getVisibility() {
		return Visibility;
	}
	public void setVisibility(String visibility) {
		Visibility = visibility;
	}
	public int getIsRadar() {
		return IsRadar;
	}
	public void setIsRadar(int isRadar) {
		IsRadar = isRadar;
	}
	public int getWindPower() {
		return WindPower;
	}
	public void setWindPower(int windPower) {
		WindPower = windPower;
	}
	String CityCode="";
	float Temperature=20.0f;
	String WindDirection =""; 
	String WindSpeed ="";
	float Humidity  =0;
	String ReportTime=""  ;
	String UpdateTime  ="";
	String Radar ="";
	int AirPressure =0;
	int Rain =0;
	String Visibility ="";
	int IsRadar =1;
	int WindPower =0;
	String date="";
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
