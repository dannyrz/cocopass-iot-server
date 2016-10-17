package com.cocopass.dal.service;

import java.util.List;

import org.dave.common.BaseService;
import org.dave.common.database.DatabaseTransaction;

public class Weather  extends BaseService {
	com.cocopass.dal.Weather dal=null;
	public Weather(DatabaseTransaction trans) {
		super(trans);
		dal=new com.cocopass.dal.Weather(getConnection());
	}

	public Weather() {
		super();
	}
	

	/**
	 * 判断是否存在
	 * @param  
	 * @return
	 */
	public boolean Exsit(String cityCode) {
		boolean result = dal.Exsit(cityCode);
		return result;
	}
	/**
	 * 增加
	 * @param  
	 * @return
	 */
	public long Add(com.cocopass.model.Weather weather) {
		long id = dal.Add(weather);
		return (id);
	}
	
	/**
	 * 更新
	 * @param  
	 * @return
	 */
	public int Update(com.cocopass.model.Weather weather) {
		int rows = dal.Update(weather);
		return rows;
	}
	
	/**
	 * 根据城市ID获取天气实体信息
	 * @param  
	 * @return
	 */
	public com.cocopass.model.Weather GetModelByCityCode(String cityCode)
	{
		return dal.GetModelByCityCode(cityCode);
	}
	
 
	
	/**
	 * 查询所有天气信息
	 * @param  
	 * @return
	 */
	public List<com.cocopass.model.Weather> GetList(String condition)
	{
		return dal.GetList(condition);
	}
}
