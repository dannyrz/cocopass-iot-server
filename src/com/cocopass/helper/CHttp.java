package com.cocopass.helper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.xml.namespace.QName;  
import org.apache.axis.client.Call;  
import org.apache.axis.client.Service; 

public class CHttp {
	
	 public static HttpResponse GetResponse(String url, String postData,String cookie) throws ClientProtocolException, IOException  {
    	 HttpClient httpClient = HttpClientBuilder.create().build();
    	 HttpResponse httpresponse;
    	 
    	 String userAgent="Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
    	// ��������   
    	 if(postData!=null&&!postData.equals(""))
    	 {
    		 HttpPost request = new HttpPost(url);
    		 request.setHeader("User-Agent", userAgent);
    		 StringEntity entity = new StringEntity(postData);   
    		 entity.setContentType("application/x-www-form-urlencoded");    
    	     // �������������    
    	     request.setEntity(entity);
    	     if(cookie!=null&&!cookie.equals(""))
        	 {
        	   request.setHeader("Cookie", cookie);
        	 }
    	     // ִ��    
             httpresponse = httpClient.execute(request);  
    	 }
    	 else
    	 {
    		 HttpGet httpGet = new HttpGet(url);  
    		 httpGet.setHeader("User-Agent", userAgent);
    		 httpresponse = httpClient.execute(httpGet);  
    	 }
    	 
        //String body = EntityUtils.toString(httpresponse.getEntity());   
         return httpresponse;
    	 
 	}
	 
	 public static String GetResponseBody(String url, String postData,String cookie) throws ClientProtocolException, IOException
	 {
		 HttpResponse httpresponse=GetResponse(url, postData, cookie);
		 String body=EntityUtils.toString(httpresponse.getEntity(),Charset.forName("utf-8"));   
		 return body;
	 }
	 
	 
	 /**
	  * RequestDoNetSoap
	  * ����.NET����ӿ�
	  */	 
	 public static String RequestDoNetSoap(String url,String soapAction,String operationName,Map<?, ?> mapParms)
	 {
		 String result=null;
		 Service service=new Service();  
		 try{  
             Call call=(Call)service.createCall();       
             call.setTargetEndpointAddress(url);              
             call.setOperationName(new QName(soapAction,operationName)); //����Ҫ�����ĸ����� 
             call.setReturnType(new QName(soapAction,operationName),String.class); //Ҫ���ص��������ͣ��Զ������ͣ�  
//           call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//����׼�����ͣ�  
             call.setUseSOAPAction(true);  
             call.setSOAPActionURI(soapAction + operationName);   
             Object[] parmValues=new Object[mapParms.size()];
             
             for(Object key : mapParms.keySet()){
            	 int i=0;
            	 
            	 call.addParameter(new QName(soapAction,key.toString()), //����Ҫ���ݵĲ���  
                 		 org.apache.axis.encoding.XMLType.XSD_STRING,  
                         javax.xml.rpc.ParameterMode.IN);  
            	 Object value = mapParms.get(key);
            	 parmValues[i]=value;
            	 i++;
            	 }
                 
             result=(String)call.invoke(parmValues);//���÷��������ݲ���          
                    
         }	catch(Exception ex)  
		 {  
        	 ex.printStackTrace();  
         }         
		 return result;
	 }
}
