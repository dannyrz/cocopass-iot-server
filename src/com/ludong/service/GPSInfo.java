package com.ludong.service;

import java.sql.Connection;
import java.util.List;

import org.dave.common.BaseService;
import org.dave.common.database.DatabaseTransaction;
import org.dave.common.database.access.DataAccess;

public class GPSInfo  extends BaseService {
	
	com.ludong.dal.GPSInfo gpsInfoDAL=null;
	public GPSInfo(DatabaseTransaction trans) {
		super(trans);
		gpsInfoDAL=new com.ludong.dal.GPSInfo(super.getConnection());
	}

	public GPSInfo() {
		super();
	}
	
	
	/**
	 * 增加
	 * @param tableName,gpsInfo
	 * @return
	 */
	public long Add(String tableName,com.ludong.model.GPSInfo gpsInfo) {
		//com.cocopass.iot.dal.GPSInfo dao = new com.cocopass.iot.dal.GPSInfo(getConnection());
		long id = gpsInfoDAL.Add(tableName,gpsInfo);
		
		return (id);
	}
	
	/**
	 * 增加
	 * @param tableName,gpsInfo
	 * @return
	 */
	public void AddMutilList(String tableName,List<com.ludong.model.GPSInfo> list) {
		  gpsInfoDAL.AddMutilList(tableName, list);
	
	}
	
	
	/**
	 * 统计条件
	 * @param tableName,gpsInfo
	 * @return
	 */
	public long GetCount(String condition,List<String> listTable) {
		long result = gpsInfoDAL.GetCount(condition,listTable);
		return result;
	}
	
	/**
	 * 增加
	 * @param tableName,gpsInfo
	 * @return
	 */
	public List<com.ludong.model.GPSInfo> GetList(String condition, int page, int pageNum,List<String> tables)
	{
		List<com.ludong.model.GPSInfo> list=gpsInfoDAL.GetList(condition, page, pageNum, tables);
		return list;
	}
}
