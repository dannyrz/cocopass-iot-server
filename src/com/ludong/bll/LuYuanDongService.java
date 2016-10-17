package com.ludong.bll;

import java.io.IOException;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.cocopass.helper.CHttp;
 

public class LuYuanDongService {
     public static String GetAuthCookie(String url,String authJsonText)  
     {
    	 String result="";
    	 try
    	 {
    	 HttpResponse response=CHttp.GetResponse(url,authJsonText,"ASP.NET_SessionId=0tmatx45c2pbrlnfy41yrz3f; path=/; HttpOnly");
    	 HeaderIterator it = response.headerIterator("Set-Cookie");
    	 while (it.hasNext()) {
    	 System.out.println(it.next());
    	 }
    	 HttpEntity entity=response.getEntity();
    	
    	 String body=new String(EntityUtils.toByteArray(entity), "utf-8");
    	 System.out.println(body);
    	 }
    	 catch(Exception e)
    	 {
    		 System.out.println(e.getStackTrace());
    	 }
    	 return result;
     }
     
    
 
}
