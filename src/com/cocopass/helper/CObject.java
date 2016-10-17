package com.cocopass.helper;

import java.lang.reflect.Field;

public class CObject {

	 public static void ReflectPrintln(Object e) throws Exception{  
	        Class cls = e.getClass();  
	        Field[] fields = cls.getDeclaredFields();  
	        for(int i=0; i<fields.length; i++){  
	            Field f = fields[i];  
	            f.setAccessible(true);  
	            System.out.println("  "+f.getName()+" : " + f.get(e));  
	        }   
	    }  
}
