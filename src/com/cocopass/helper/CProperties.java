package com.cocopass.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

/*
 * 
 * ���������Լ�������ϣ���Ժ��ȡʱ�ӹ�ϣ���ȡ��
 * 
 * */

public class CProperties {
	 public static Map<String, Properties> pMap = new Hashtable<String, Properties> ();
	 public  String filePath="app.properties";
	 InputStream is = null;
	 public CProperties(InputStream is)
	 {
		 this.is=is;
//		 try {
//			is=new FileInputStream(this.filePath);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	 }
	 public CProperties()
	 {
	 
	 }
	 public CProperties(String filePath)
	 {
		 try {
			is=new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	  public CProperties(String filePath, boolean isWebApp)
	  {
		  this.filePath=filePath;
		  if(isWebApp)
		  {is=Thread.currentThread().getContextClassLoader().getResourceAsStream("HanLP.properties");}
		  else
		  {try {
			is=new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
	  }
	    /**
	     * Set properties to properties cache
	     * @param pName
	     * @throws IOException
	     */
	    public  void SetProperties() throws IOException {
	        
	        Properties properties = new Properties();
	        String path =  getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
	    	String[] arrayPath=path.split("/");
	    	String jarName=arrayPath[arrayPath.length-1];
	    	String propertiesPath=path.replace(jarName, "app.properties");
	    	java.io.File ifile=new java.io.File(propertiesPath);
	    	System.out.println("����ʱ�����������ļ�·��:"+propertiesPath);
	    	if(ifile.exists())
	    	{
	    		is=new FileInputStream(propertiesPath);
	    	}
	    	
	    	ifile=new java.io.File(filePath);
	    	if(ifile.exists())
	    	{
	    		is=new FileInputStream(filePath);
	    	}
	    	
	        try {
	        	if(is==null){
	        		System.out.println("����ʱ�����������ļ������ڻ��߶�ȡʧ�ܣ�����JAR�ڲ����ɶ�ȡ��");
	        		
	        		is=Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
	        		
	        }
	        } finally {
	            
	            if (is != null) {
	            	properties.load(is);
	                is.close();
	            }
	            else{
	            	 //System.out.println("is= null");
	            }
	    	}
	        
	        //logger.info("Load to properties cache : " + pName);
	        pMap.put(filePath, properties);
	    }
	    
	    /**
	     * Get properties by properties path
	     * @param pName
	     * @return
	     */
	    public    Properties GetInstance() {
	        return pMap.get(filePath);
	    }
	    
	    /**
	     * Get properties value by properties path & key
	     * @param pName
	     * @param key
	     * @return
	     */
	    public   String GetValue(  String key) {
	        if (pMap.get(filePath) == null) {
	            return "";
	        }
	        
	        return pMap.get(filePath).getProperty(key);
	    }
	    
	    
	    /*
	    public  Properties GetProperties()
	    {
	    	    Properties properties = new Properties();
		        String path =  getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		    	String[] arrayPath=path.split("/");
		    	String jarName=arrayPath[arrayPath.length-1];
		    	String propertiesPath=path.replace(jarName, "app.properties");
		    	java.io.File ifile=new java.io.File(propertiesPath);

		        try {
		        	    if(ifile.exists()){
			    	    	//System.out.println("����ʱ�����������ļ����ڣ�");
		        	    	is=new FileInputStream(filePath);
			    	    }
		        	    if(is==null){
		        	    	//System.out.println("����ʱ�����������ļ������ڻ��߶�ȡʧ�ܣ�����JAR�ڲ����ɶ�ȡ��");
		        	    	is=Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		        	    }
		        	    properties.load(is);
		        	    is.close();
		        }
		        	catch(Exception er){
		        } 
		        	finally {
		             
		    	}
		        
		        //logger.info("Load to properties cache : " + pName);
		       return properties;
	    }
	    */
}

