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
	 * �ж��Ƿ����
	 * @param  
	 * @return
	 */
	public boolean Exsit(String cityCode) {
		boolean result = dal.Exsit(cityCode);
		return result;
	}
	/**
	 * ����
	 * @param  
	 * @return
	 */
	public long Add(com.cocopass.model.Weather weather) {
		long id = dal.Add(weather);
		return (id);
	}
	
	/**
	 * ����
	 * @param  
	 * @return
	 */
	public int Update(com.cocopass.model.Weather weather) {
		int rows = dal.Update(weather);
		return rows;
	}
	
	/**
	 * ���ݳ���ID��ȡ����ʵ����Ϣ
	 * @param  
	 * @return
	 */
	public com.cocopass.model.Weather GetModelByCityCode(String cityCode)
	{
		return dal.GetModelByCityCode(cityCode);
	}
	
 
	
	/**
	 * ��ѯ����������Ϣ
	 * @param  
	 * @return
	 */
	public List<com.cocopass.model.Weather> GetList(String condition)
	{
		return dal.GetList(condition);
	}
}
