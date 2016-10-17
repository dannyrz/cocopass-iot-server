package com.ludong.service;

import java.sql.Connection;
import java.util.List;

import org.dave.common.BaseService;
import org.dave.common.database.DatabaseTransaction;
import org.dave.common.database.access.DataAccess;

public class BatteryInfo  extends BaseService {
	
	com.ludong.dal.BatteryInfo dal=null;
	public BatteryInfo(DatabaseTransaction trans) {
		super(trans);
		dal=new com.ludong.dal.BatteryInfo(super.getConnection());
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
	public void AddMutilList(String tableName,List<com.ludong.model.BatteryInfo> list) {
		  dal.AddMutilList(tableName, list);
	
	}
	
	
	/**
	 * 统计条件
	 * @param tableName,gpsInfo
	 * @return
	 */
	public long GetCount(String condition,List<String> listBatteryInfoTable) {
		long result = dal.GetCount(condition,listBatteryInfoTable);
		return result;
	}
	
	
	/**
	 * 增加
	 * @param tableName,gpsInfo
	 * @return
	 */
	public List<com.ludong.model.BatteryInfo> GetList(String condition, int page, int pageNum,List<String> tables)
	{
		List<com.ludong.model.BatteryInfo> list=dal.GetList(condition, page, pageNum, tables);
		return list;
	}
}
