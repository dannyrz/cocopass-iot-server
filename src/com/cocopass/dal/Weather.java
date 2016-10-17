package com.cocopass.dal;

import java.sql.Connection;
import java.util.List;

import org.dave.common.database.access.DataAccess;
import org.dave.common.database.convert.IntegerConverter;
import org.dave.common.database.convert.LongConverter;

public class Weather extends DataAccess{

	public Weather(Connection conn){
	super(conn);
		// TODO Auto-generated constructor stub
	}


/**
 * 根据城市编码判断行是否存在
 * @param 所有字段参数
 * @return
 */
	public boolean Exsit(String cityCode)
	{
		Integer rows=super.queryForObject("SELECT COUNT(0) FROM Weather WHERE CityCode=?", new IntegerConverter(),cityCode);
		return (rows>0);
	}

	/**
	 * 增加一行城市天气数据
	 * @param 所有字段参数
	 * @return
	 */
	public  long Add(com.cocopass.model.Weather weather)
	{
		String sql="INSERT INTO Weather(CityCode,Temperature,WindDirection,WindSpeed,Humidity,ReportTime,Radar,AirPressure,Rain,Visibility,IsRadar,WindPower,UpdateTime) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.insert(sql, 
				new LongConverter(), 
				weather.getCityCode(), 
				weather.getTemperature(), 
				weather.getWindDirection(),
				weather.getWindSpeed(),
				weather.getHumidity(),
				weather.getReportTime(),
				weather.getRadar(),
				weather.getAirPressure(),
				weather.getRain(),
				weather.getVisibility(),
				weather.getIsRadar(),
				weather.getWindPower(),
				weather.getUpdateTime()
				);
	}
	
	/**
	 * 更新一行城市天气数据
	 * @param 所有字段参数
	 * 其中CityCode作为更新条件
	 * @return
	 */
	public int Update(com.cocopass.model.Weather weather)
	{
		String sql="UPDATE Weather SET "
				//+ "CityCode=?,"
				+ "Temperature=?,"
				+ "WindDirection=?,"
				+ "WindSpeed=?,"
				+ "Humidity=?,"
				+ "ReportTime=?,"
				+ "Radar=?,"
				+ "AirPressure=?,"
				+ "Rain=?,"
				+ "Visibility=?,"
				+ "IsRadar=?,"
				+ "WindPower=?,"
				+ "UpdateTime=?"
				+ " WHERE "
				+ "CityCode=?";
		  return super.update(sql,
				weather.getTemperature(),
				weather.getWindDirection(),
				weather.getWindSpeed(),
				weather.getHumidity(),
				weather.getReportTime(),
				weather.getRadar(),
				weather.getAirPressure(),
				weather.getRain(),
				weather.getVisibility(),
				weather.getIsRadar(),
				weather.getWindPower(),
				weather.getUpdateTime(),
				weather.getCityCode());
	}
	
	/**
	 * 根据城市ID查询
	 * @param cityCode
	 * @return
	 */
	public com.cocopass.model.Weather GetModelByCityCode(String cityCode) {
		return super.queryForObject("SELECT * FROM Weather WHERE CityCode=?", 
				new com.cocopass.dal.result.convert.Weather(), cityCode);
	}



	/**
	 * 查询符合条件的天气信息
	 * @return
	 */
	public List<com.cocopass.model.Weather> GetList(String condition)
	{
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * FROM Weather ");
		if(condition!=null&&!condition.trim().equals(""))
		{
			sql.append(" WHERE  "+ condition);
		}
		return super.queryForList(sql.toString(), new com.cocopass.dal.result.convert.Weather());
	}

}
