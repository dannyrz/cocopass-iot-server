package com.cocopass.bll;

import java.text.ParseException;
import java.util.List;

import org.dave.common.database.DatabaseTransaction;

import com.cocopass.iot.model.CPageRecord;

public class OnlineShaft {
	/**
	 * ����һ��GPS����,2016��д���������°汾�ӿ��в��ô˷��� ä�����������Ҳ���ڱ����¼�����Ǽ�¼��һ��ı�
	 * 
	 * @param �����ֶβ���
	 * @return
	 */
	public long Add(com.cocopass.iot.model.OnLineShaft model) {
		long result = 0;
		DatabaseTransaction trans = new DatabaseTransaction(false,"connectionString2");
		com.cocopass.dal.service.OnLineShaft service = new com.cocopass.dal.service.OnLineShaft(trans);
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
		
		DatabaseTransaction trans = new DatabaseTransaction(false,"connectionString2");
		com.cocopass.dal.service.OnLineShaft service=new com.cocopass.dal.service.OnLineShaft(trans);
		 List<com.cocopass.iot.model.OnLineShaft> list=service.GetList(condition, page, pageNum);
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
	
	 public List<com.cocopass.iot.model.OnLineShaft> GetList(String condition,int page,int pageNum) throws ParseException
	    {
		 DatabaseTransaction trans = new DatabaseTransaction(false,"connectionString2");
		 com.cocopass.dal.service.OnLineShaft service=new com.cocopass.dal.service.OnLineShaft(trans);
		 List<com.cocopass.iot.model.OnLineShaft> list=service.GetList(condition, page, pageNum);
		 trans.close();
		 return list;
	    	
	    }
}
