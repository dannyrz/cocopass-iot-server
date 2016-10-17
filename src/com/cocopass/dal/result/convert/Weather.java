package com.cocopass.dal.result.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dave.common.database.convert.ResultConverter;

import com.cocopass.helper.CDate;

public class Weather implements ResultConverter<com.cocopass.model.Weather>{

	@Override
	public com.cocopass.model.Weather convert(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		com.cocopass.model.Weather model = new com.cocopass.model.Weather();
		model.setAirPressure(rs.getInt("AirPressure"));
		model.setCityCode(rs.getString("CityCode"));
		model.setHumidity(rs.getFloat("Humidity"));
		model.setID(rs.getInt("ID"));
		model.setIsRadar(rs.getInt("IsRadar"));
		model.setRadar(rs.getString("Radar"));
		model.setRain(rs.getInt("Rain"));
		model.setReportTime(rs.getString("Direction"));
		model.setTemperature(rs.getFloat("Temperature"));
		model.setUpdateTime(rs.getString("UpdateTime"));
		model.setVisibility(rs.getString("Visibility"));
		model.setWindDirection(rs.getString("WindDirection"));
		model.setWindPower(rs.getInt("WindPower"));
		model.setWindSpeed(rs.getString("WindSpeed"));
		
		return model;
	}

}
