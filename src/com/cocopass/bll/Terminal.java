package com.cocopass.bll;

import java.text.ParseException;
import java.util.List;

import org.dave.common.database.DatabaseTransaction;

public class Terminal {
	 public List<com.cocopass.iot.model.Terminal> GetList(String condition,int page,int pageNum) throws ParseException
	    {
		 DatabaseTransaction trans = new DatabaseTransaction(false,"connectionString2");
		 com.cocopass.dal.service.Terminal terminalService=new com.cocopass.dal.service.Terminal(trans);
		 List<com.cocopass.iot.model.Terminal> list=terminalService.GetList(condition, page, pageNum);
		 trans.close();
		 return list;
	    	
	    }
}
