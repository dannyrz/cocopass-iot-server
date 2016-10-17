package com.cocopass.iot.model;

public class Enum {
	 private int value;
//	 
//	 public enum InstructionEnum {
//		 
//	        WebServiceReceivedRequest("1"),
//	        
//	   
//	    }
     private Enum(int value) {
	        this.value = value;
	    }
	 
	    public int getValue() {
	        return value;
	    }		
}
