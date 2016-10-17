package com.cocopass.dal.service;

import java.util.List;

import org.dave.common.BaseService;
import org.dave.common.database.DatabaseTransaction;

public class OnLineShaft  extends BaseService{
	
	com.cocopass.dal.OnLineShaft dal=null;
	public OnLineShaft(DatabaseTransaction trans) {
		super(trans);
		dal=new com.cocopass.dal.OnLineShaft(super.getConnection());
	}
	
	/**
	 * 增加
	 * @param tableName,gpsInfo
	 * @return
	 */
	public long Add(com.cocopass.iot.model.OnLineShaft model)
	{
		long id = dal.Add(model);
		return id;
	}
	
	public long GetCount(String condition) {
		long result = dal.GetCount(condition);
		return result;
	}
	
	/**
	 * 增加
	 * @param tableName,gpsInfo
	 * @return
	 */
	public List<com.cocopass.iot.model.OnLineShaft> GetList(String condition,int page,int pageNum)
	{
		List<com.cocopass.iot.model.OnLineShaft> list=dal.GetList(condition, page, pageNum);
		return list;
	}
}
