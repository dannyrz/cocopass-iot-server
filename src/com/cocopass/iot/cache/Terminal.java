package com.cocopass.iot.cache;

import org.apache.log4j.Logger;

import com.cocopass.helper.CRedis;
import com.cocopass.iot.model.EndPoint;
import com.google.gson.Gson;
import com.kl.nts.server.tcp.NettyTCPServer;

public class Terminal {

   static	Gson gson=new Gson();
   private static final Logger LOG = Logger.getLogger(Terminal.class.getName());
   /**
    * 
    * @param terminalID
    * @return 如果查询不到默认为1.0老版本硬件
    */
   public static float GetVersion(String terminalID)
   {
	   float ver=0.0f;
	   try{
		   String key="Version:"+terminalID;
		   String strVer= CRedis.get(key);
		   if(strVer!=null&&!strVer.equals(""))
		   {
			   ver=Float.parseFloat(strVer);
		   }
	   }
	   catch(Exception er)
	   {
		   LOG.error(er.getStackTrace());
	   }
	   return ver;
   }
   
   public static EndPoint GetEndPoit(String terminalID)
   {
	   EndPoint ep=null;
	   try{
		   String key="EndPoit:"+terminalID;
		   String strEP= CRedis.get(key);
		   if(strEP!=null&&!strEP.equals(""))
		   {
			   ep=gson.fromJson(strEP, EndPoint.class);
		   }
	   }
	   catch(Exception er)
	   {
		   throw er;
	   }
	   return ep;
   }
}
