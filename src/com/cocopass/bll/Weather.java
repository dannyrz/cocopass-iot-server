package com.cocopass.bll;

import java.util.Date;
import java.util.List;

import org.dave.common.database.DatabaseTransaction;

public class Weather {
	DatabaseTransaction trans = new DatabaseTransaction(false,"connectionString2");
	com.cocopass.dal.service.Weather weatherService=new com.cocopass.dal.service.Weather(trans);
	
		/**
		 * 增加一行天气数据
		 * @param 所有字段参数
		 * @return
		 */
	    public long Add(com.cocopass.model.Weather weather)
		{
	    	long result=weatherService.Add(weather);
	    	return result;
		}
	    /**
		 * 修改或增加一行天气数据
		 * @param 所有字段参数
		 * @return
		 */
	    public long Update(com.cocopass.model.Weather weather)
		{
	    	if(weatherService.Exsit(weather.getCityCode()))
	    	{
	    		int rows=weatherService.Update(weather);
	    		return rows;
	    	}
	    	else
	    	{
	    		long id=weatherService.Add(weather);
	    		return id;
	    	}
		}
	    

		/**
		 * 查询所有天气信息
		 * @param  
		 * @return
		 */
		public List<com.cocopass.model.Weather> GetAllList()
		{
			String condition="ID>0";
			return GetList(condition);
		}
		
		/**
		 * 查询所有天气信息
		 * @param  
		 * @return
		 */
		public List<com.cocopass.model.Weather> GetList(String condition)
		{
			return weatherService.GetList(condition);
		}
	    
}
