package com.cocopass.bll;

import java.text.ParseException;
import java.util.List;

import org.dave.common.database.DatabaseTransaction;

import com.cocopass.iot.model.CPageRecord;

public class AuthInfo {
	
	public long Add(com.cocopass.iot.model.AuthInfo model) {
		long result = 0;
		DatabaseTransaction trans = new DatabaseTransaction(false);
		com.cocopass.dal.service.AuthInfo service = new com.cocopass.dal.service.AuthInfo(trans);
		try {
			result = service.Add(model);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			trans.close();
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	public <T> CPageRecord<T> GetPageRecord(String condition, int page, int pageNum,boolean isCount) throws ParseException{
		
		DatabaseTransaction trans = new DatabaseTransaction(false);
		 com.cocopass.dal.service.AuthInfo service=new com.cocopass.dal.service.AuthInfo(trans);
		 List<com.cocopass.iot.model.AuthInfo> list=service.GetList(condition, page, pageNum);
		 long count = 0;
		 if(isCount){
		  count=service.GetCount(condition);
		 }
		 trans.close();
		 CPageRecord<T> result=new  CPageRecord<T>();
		 result.setRecordsCount(count);
		 result.setRecords((List<T>) list);
		 
		 return result;
		
	}
	
	 public List<com.cocopass.iot.model.AuthInfo> GetList(String condition,int page,int pageNum) throws ParseException
	    {
		 DatabaseTransaction trans = new DatabaseTransaction(false);
		 com.cocopass.dal.service.AuthInfo service=new com.cocopass.dal.service.AuthInfo(trans);
		 List<com.cocopass.iot.model.AuthInfo> list=service.GetList(condition, page, pageNum);
		 trans.close();
		 return list;
	    	
	    }
}
