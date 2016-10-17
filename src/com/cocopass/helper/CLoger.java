package com.cocopass.helper;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class CLoger {
	private static  Logger LOG =null;
	 static {
	 
		 
		 PropertyConfigurator.configure("log4j.properties");
		 
		 LOG = Logger.getLogger("");
		  
	 }
	 
	 public static void Debug(String txt){
		 LOG.debug(txt);
	 }
	 
	 public static void Info(String txt){
		 LOG.info(txt);
	 }
	 
	 public static void Warn(String txt){
		 LOG.warn(txt);
	 }
	 
	 public static void Error(String txt){
		 LOG.error(txt);
	 }
	 
}
