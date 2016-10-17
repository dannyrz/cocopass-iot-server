package com.kl.nts;


import com.cocopass.helper.CProperties;
import com.cocopass.helper.CRedis;
import com.cocopass.helper.CMQ.MQFactory;
import com.cocopass.iot.work.SendMessageToTerminal;
import com.kl.nts.server.http.NettyHTTPServer;
import com.kl.nts.server.tcp.NettyTCPServer;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class LaunchPad {

	static CProperties cp=new CProperties(); 
    public static void main(String[] args) throws Exception {
         
        Init();

        NettyTCPServer tcpAuthServer_V1X = null,tcpAuthServer_V2 = null,tcpReceiveServer = null;
        
        Config.NettyBossTreadNum=Runtime.getRuntime().availableProcessors()*2;
        //1.x版本鉴权
        if(Config.IsRunTCPAuthServerV1)
        {
        	tcpAuthServer_V1X = new NettyTCPServer(Config.AuthTCPPort_V1X,Config.NettyBossTreadNum,Config.NettyWorkerThreadNum,"1X");
        	tcpAuthServer_V1X.start();
        }
        //2.x版本鉴权
        if(Config.IsRunTCPAuthServerV2)
        {
        	tcpAuthServer_V2 = new NettyTCPServer(Config.AuthTCPPort_V2,Config.NettyBossTreadNum,Config.NettyWorkerThreadNum,"2");
        	tcpAuthServer_V2.start();
        }
        //2.x接收数据
        if(Config.IsRunTCPReceiveServer)
        {
        	tcpReceiveServer = new NettyTCPServer(Config.ReceiveTCPPort,Config.NettyBossTreadNum,Config.NettyWorkerThreadNum,"2");
        	tcpReceiveServer.start();
        }
        //1.x接收数据
        if(Config.IsRunUDPReceiveServer)
        {
        
        }
        
        //http服务
        if(Config.IsRunHTTPServer)
        {
        	 NettyHTTPServer httpServer = new NettyHTTPServer(Config.HTTPPort );
             httpServer.start();
        }

//        NettySSLServer sslServer = new NettySSLServer(sslPort);
//        sslServer.start();
//
 
//        sslServer.join();
//        httpServer.join();
        
//        if(tcpAuthServer_V1X!=null) {
//        	System.out.println("wait tcpAuthServer_V1X die");
//        	tcpAuthServer_V1X.join();
//        	
//        }
//        if(tcpAuthServer_V2!=null) {
//        	System.out.println("wait tcpAuthServer_V2 die");
//        	tcpAuthServer_V2.join();
//        	
//        }
//        if(tcpAuthServer_V2!=null) {
//        	System.out.println("wait tCPReceiveServer die");
//        	tcpReceiveServer.join();
//        	
//        }
    }
    
    static void Init()
    {
    	 
    	 try {
    		            // Load app.properties
    		 cp.SetProperties();
    		 
    		 Config.AuthTCPPort_V1X= Integer.parseInt(cp.GetValue("AuthTCPPort_V1X"));
    		 Config.AuthTCPPort_V2   = Integer.parseInt(cp.GetValue("AuthTCPPort_V2"));
    		 Config.ReceiveTCPPort   = Integer.parseInt(cp.GetValue("ReceiveTCPPort"));
    		 Config.HTTPPort = Integer.parseInt(cp.GetValue("HTTPPort"));
    		 Config.NettyBossTreadNum= Integer.parseInt(cp.GetValue("NettyBossTreadNum"));
    		 Config.NettyWorkerThreadNum=Integer.parseInt(cp.GetValue("NettyWorkerThreadNum"));
    		 //Config.CRouteInfo=cp.GetValue("CRouteInfo");
    		 Config.ReceiveHost=cp.GetValue("ReceiveHost");
    		 Config.ReceiveTCPPort = Integer.parseInt(cp.GetValue("ReceiveTCPPort"));
    		 Config.ReceiveUDPPort = Integer.parseInt(cp.GetValue("ReceiveUDPPort"));
    		 Config.TranseURL=cp.GetValue("TranseURL");
    		 
    		 Config.IsRunUDPReceiveServer=Boolean.valueOf(cp.GetValue("IsRunUDPReceiveServer")).booleanValue();
    		 Config.IsRunTCPReceiveServer=Boolean.valueOf(cp.GetValue("IsRunTCPReceiveServer")).booleanValue();
    		 Config.IsRunTCPAuthServerV1=Boolean.valueOf(cp.GetValue("IsRunTCPAuthServerV1")).booleanValue();
    		 Config.IsRunTCPAuthServerV2=Boolean.valueOf(cp.GetValue("IsRunTCPAuthServerV2")).booleanValue();
    		 Config.IsRunHTTPServer=Boolean.valueOf(cp.GetValue("IsRunHTTPServer")).booleanValue();
    		 
    		 if(Config.IsRunTCPAuthServerV2)
    		 {
	    		 Config.RedisPort=Integer.parseInt(cp.GetValue("RedisPort"));
	    		 Config.RedisHost=cp.GetValue("RedisHost");
	    		 Config.RedisPassword=cp.GetValue("RedisPassword");
	    		 Config.RedisPoolMaxActive=Integer.parseInt(cp.GetValue("RedisPoolMaxActive"));
	    		 Config.RedisPoolMaxIdle=Integer.parseInt(cp.GetValue("RedisPoolMaxIdle"));
	    		 Config.RedisPoolMaxWaitMillis= Long.parseLong(cp.GetValue("RedisPoolMaxWaitMillis"));
	    		 Config.RedisPoolTimeOut=Integer.parseInt(cp.GetValue("RedisPoolTimeOut"));
	    		 CRedis.StartPool( Config.RedisPoolMaxActive, Config.RedisPoolMaxIdle, Config.RedisPoolMaxWaitMillis, Config.RedisHost, Config.RedisPort, Config.RedisPoolTimeOut, Config.RedisPassword);
    		 }
    		 
    		 
    		 Config.ECReceivedTerminalBytes = cp.GetValue("ECReceivedTerminalBytes");
    		 
    		 Config.MQName	=	cp.GetValue("MQName");
    		 Config.MQAliasName	=	cp.GetValue("MQAliasName");
    		 Config.MQHost	=	cp.GetValue("MQHost");
    		 Config.MQUserName	=	cp.GetValue("MQUserName");
    		 Config.MQPassword	=	cp.GetValue("MQPassword");
    		 Config.MQPort	= Integer.parseInt(cp.GetValue("MQPort"));
    		 
    		 Global.CMQ	= MQFactory.createMQ(Config.MQName);
    		 Global.CMQ.SetConnection(Config.MQAliasName, Config.MQHost, Config.MQUserName, Config.MQPassword, Config.MQPort);
    		 Global.CMQ.SetService();
    		 
    		 
    		      } catch (Exception e) {
    		           // logger.error(e.getMessage(), e);
    		            e.printStackTrace();
    		        }
    	 
    	 
 
    	 
    }
}
