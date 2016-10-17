package com.ludong.service;

import java.util.List;

import org.dave.common.BaseService;
import org.dave.common.database.DatabaseTransaction;

public class ActionLog  extends BaseService{
	com.ludong.dal.ActionLog dal=null;
	public ActionLog(DatabaseTransaction trans) {
		super(trans);
		dal=new com.ludong.dal.ActionLog(super.getConnection());
	}
	
	
	/**
	 * 增加
	 * @param tableName,gpsInfo
	 * @return
	 */
	public long Add(com.ludong.model.ActionLog model)
	{
		long id = dal.Add(model);
		return id;
	}
	
	/**
	 * 统计条件
	 * @param tableName,gpsInfo
	 * @return
	 */
	public long GetCount(String condition) {
		long result = dal.GetCount(condition);
		return result;
	}
	
	/**
	 * 增加
	 * @param tableName,gpsInfo
	 * @return
	 */
	public List<com.ludong.model.ActionLog> GetList(String condition,int page, int pageNum)
	{
		List<com.ludong.model.ActionLog> list=dal.GetList(condition, page, pageNum);
		return list;
	}
}
