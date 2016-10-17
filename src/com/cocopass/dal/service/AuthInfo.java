package com.cocopass.dal.service;

import java.util.List;

import org.dave.common.BaseService;
import org.dave.common.database.DatabaseTransaction;

public class AuthInfo  extends BaseService{
	com.cocopass.dal.AuthInfo dal=null;
	public AuthInfo(DatabaseTransaction trans) {
		super(trans);
		dal=new com.cocopass.dal.AuthInfo(super.getConnection());
	}
	
	
	/**
	 * 增加
	 * @param tableName,gpsInfo
	 * @return
	 */

	public long Add(com.cocopass.iot.model.AuthInfo model)
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
	public List<com.cocopass.iot.model.AuthInfo> GetList(String condition,int page,int pageNum)
	{
		List<com.cocopass.iot.model.AuthInfo> list=dal.GetList(condition, page, pageNum);
		return list;
	}
}
