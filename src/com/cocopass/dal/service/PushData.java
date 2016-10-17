package com.cocopass.dal.service;

import java.sql.Connection;
import java.util.List;

import org.dave.common.BaseService;
import org.dave.common.database.DatabaseTransaction;
import org.dave.common.database.access.DataAccess;

public class PushData  extends BaseService {
	
	com.cocopass.dal.PushData dal=null;
	public PushData(DatabaseTransaction trans) {
		super(trans);
		dal=new com.cocopass.dal.PushData(super.getConnection());
	}

 
	
	/**
	 * 增加
	 * @param tableName,gpsInfo
	 * @return
	 */
	/*
	public long Add(String tableName,com.ludong.model.BatteryInfo model) {
		long id = dal.Add(tableName,model);
		return (id);
	}
	*/
	
	/**
	 * 增加
	 * @param tableName,gpsInfo
	 * @return
	 */
	public void AddMutilList(String tableName,List<com.cocopass.iot.model.PushData> list) {
		  dal.AddMutilList(tableName, list);
	
	}
	
	/**
	 * 统计条件
	 * @param tableName,gpsInfo
	 * @return
	 */
	public long GetCount(String condition,List<String> listTable) {
		long result = dal.GetCount(condition,listTable);
		return result;
	}
	
	/**
	 * 增加
	 * @param tableName,gpsInfo
	 * @return
	 */
	public List<com.cocopass.iot.model.PushData> GetList(String condition,int page,int pageNum,List<String> tables)
	{
		List<com.cocopass.iot.model.PushData> list=dal.GetList(condition, page, pageNum, tables);
		return list;
	}
}
