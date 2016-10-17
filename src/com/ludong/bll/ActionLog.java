package com.ludong.bll;

import java.text.ParseException;
import java.util.List;

import org.dave.common.database.DatabaseTransaction;

import com.cocopass.iot.model.CPageRecord;

public class ActionLog {
	
	
	public long GetCount(String condition) {
		long result = 0;
		DatabaseTransaction trans = new DatabaseTransaction(false,"connectionString2");
		com.ludong.service.ActionLog service = new com.ludong.service.ActionLog(trans);
		try {
			result = service.GetCount(condition);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			trans.close();
		}

		return result;
	}
	
	

	
	public long Add(com.ludong.model.ActionLog model) {
		long result = 0;
		DatabaseTransaction trans = new DatabaseTransaction(false,"connectionString2");
		com.ludong.service.ActionLog service = new com.ludong.service.ActionLog(trans);
		try {
			String condition="	SerialNumber='"+model.GetSerialNumber()+"' AND Status="+model.GetStatus();
			if(service.GetCount(condition)==0){
				result = service.Add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			trans.close();
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	public <T> CPageRecord<T> GetPageRecord(String condition, int page, int pageNum,boolean isCount) throws ParseException{
		
		DatabaseTransaction trans = new DatabaseTransaction(false,"connectionString2");
		 com.ludong.service.ActionLog service=new com.ludong.service.ActionLog(trans);
		 List<com.ludong.model.ActionLog> list=service.GetList(condition, page, pageNum);
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
	
	 public List<com.ludong.model.ActionLog> GetList(String condition, int page, int pageNum) throws ParseException
	    {
		 DatabaseTransaction trans = new DatabaseTransaction(false,"connectionString2");
		 com.ludong.service.ActionLog service=new com.ludong.service.ActionLog(trans);
		 List<com.ludong.model.ActionLog> list=service.GetList(condition, page, pageNum);
		 trans.close();
		 return list;
	    	
	    }
}
