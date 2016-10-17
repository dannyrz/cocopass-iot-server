package com.ludong.dal;

public class EBikeCache {
	
	public Object GetFieldValueByRFID(String rfid, String field){
		Object result=com.cocopass.helper.CRedis.getMapValue(rfid, field);
		return result;
	}
	public Object GetFieldValueByBSM(String bsm, String field){
		Object result=com.cocopass.helper.CRedis.getMapValue(bsm, field);
		return result;
	}
	 

}
