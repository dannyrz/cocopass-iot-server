package com.cocopass.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CString {
	public static String GetNumber(String text)
	{
		 
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(text);   
		return ( m.replaceAll("").trim());
	}
	
	
	/**  
	 * Java������ת�ַ���ǰ���Զ���0��ʵ�֡�  
	 * 0 ����ǰ�油��0   
	   4 ������Ϊ4   
	   d�������Ϊ������  
	 */  
	 
	  public static String FormatFixLong(long num,int lng) {   	   
	    String str = String.format("%0"+lng+"d", num);   
	    return (str); 
	  }   
	  
	  
	  public static boolean IsNullOrEmpty(String text){
		  boolean result=false;
		  if(text==null||"".equals(text))
			  result=true;
		  else{
			  text=text.trim();
			  if(text.length()<=0)
				  result=true;
		  }
		  return result;
	  }
	  
	  
	  public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	  
}
