package com.cocopass.dal.service;

import java.util.List;

import org.dave.common.BaseService;
import org.dave.common.database.DatabaseTransaction;

public class LoginInfo  extends BaseService{
	com.cocopass.dal.LoginInfo dal=null;
	public LoginInfo(DatabaseTransaction trans) {
		super(trans);
		dal=new com.cocopass.dal.LoginInfo(super.getConnection());
	}
	
	
	/**
	 * 增加
	 * @param tableName,gpsInfo
	 * @return
	 */
	public long Add(com.cocopass.iot.model.LoginInfo model)
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
	public List<com.cocopass.iot.model.LoginInfo> GetList(String condition,int page,int pageNum)
	{
		List<com.cocopass.iot.model.LoginInfo> list=dal.GetList(condition, page, pageNum);
		return list;
	}
}
