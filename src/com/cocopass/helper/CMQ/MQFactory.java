package com.cocopass.helper.CMQ;

public class MQFactory {
	    public static CMessageQueue createMQ(String mqName){  
	    	CMessageQueue cmq = null;  
//	        if("RabbitMQ".equalsIgnoreCase(mqName))  
//	        	cmq = new RabbitMQ();  
//	        else 
	        	if("AzureBusMQ".equalsIgnoreCase(mqName))  
	            cmq = new AzureDataBusMQ();  
	        	else if("RabbitMQ".equalsIgnoreCase(mqName)){
	        		cmq = new RabbitMQ();  
	        	}
	        return cmq;  
	    }  
	}  