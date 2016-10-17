package com.cocopass.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
   public  Map<String, String> GetProperties(String[] arrayKey)
   {
	   Map<String, String> map = new HashMap<String, String>();
       Properties prop = new Properties();
       try {
    	   String path =  getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    	   String[] arrayPath=path.split("/");
    	   String jarName=arrayPath[arrayPath.length-1];
    	   String propertiesPath=path.replace(jarName, "app.properties");
           FileInputStream in = new FileInputStream(propertiesPath);
           prop.load(in);
           for(String key:arrayKey)
           {
             map.put(key, prop.getProperty(key).trim());
           }
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       return map;
   }
}
