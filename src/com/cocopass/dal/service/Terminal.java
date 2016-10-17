package com.cocopass.dal.service;

import java.util.List;

import org.dave.common.BaseService;
import org.dave.common.database.DatabaseTransaction;

public class Terminal  extends BaseService{
	
	com.cocopass.dal.Terminal dal=null;
	public Terminal(DatabaseTransaction trans) {
		super(trans);
		dal=new com.cocopass.dal.Terminal(super.getConnection());
	}
	
	/**
	 * Ôö¼Ó
	 * @param tableName,gpsInfo
	 * @return
	 */
	public List<com.cocopass.iot.model.Terminal> GetList(String condition,int page,int pageNum)
	{
		List<com.cocopass.iot.model.Terminal> list=dal.GetList(condition, page, pageNum);
		return list;
	}
}
