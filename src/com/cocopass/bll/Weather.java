package com.cocopass.bll;

import java.util.Date;
import java.util.List;

import org.dave.common.database.DatabaseTransaction;

public class Weather {
	DatabaseTransaction trans = new DatabaseTransaction(false,"connectionString2");
	com.cocopass.dal.service.Weather weatherService=new com.cocopass.dal.service.Weather(trans);
	
		/**
		 * ����һ����������
		 * @param �����ֶβ���
		 * @return
		 */
	    public long Add(com.cocopass.model.Weather weather)
		{
	    	long result=weatherService.Add(weather);
	    	return result;
		}
	    /**
		 * �޸Ļ�����һ����������
		 * @param �����ֶβ���
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
		 * ��ѯ����������Ϣ
		 * @param  
		 * @return
		 */
		public List<com.cocopass.model.Weather> GetAllList()
		{
			String condition="ID>0";
			return GetList(condition);
		}
		
		/**
		 * ��ѯ����������Ϣ
		 * @param  
		 * @return
		 */
		public List<com.cocopass.model.Weather> GetList(String condition)
		{
			return weatherService.GetList(condition);
		}
	    
}
