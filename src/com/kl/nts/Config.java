package com.kl.nts;

 

public class Config {
    public static int AuthTCPPort_V1X; 
    public static int AuthTCPPort_V2; 
    
    public static int HTTPPort;
    public static int NettyBossTreadNum;
    public static int NettyWorkerThreadNum;
    public static String CRouteInfo;
    public static String ReceiveHost;
    public static int ReceiveTCPPort;
    public static int ReceiveUDPPort;
    public static String TranseURL="http://open.lydong.com:9090";
    
    public static int RedisPort;
    public static String RedisHost;
    public static String  RedisPassword;
    public static int RedisPoolMaxActive=500;
    public static int		RedisPoolMaxIdle=5;
    public static long		RedisPoolMaxWaitMillis=100000;
    public static int		RedisPoolTimeOut=10000;
    public static boolean IsRunUDPReceiveServer=false;
    public static boolean IsRunTCPReceiveServer=false;
    public static boolean IsRunTCPAuthServerV1=true;
    public static boolean IsRunTCPAuthServerV2=true;
    public static boolean IsRunHTTPServer=true;
    
    public static String MQName ;
    public static String MQHost ;
    public static String MQUserName ;
    public static String MQPassword ;
    public static int MQPort  ;
    public static String MQAliasName= "";
    public static String AzureBusMQ= "";
    public static String ECReceivedTerminalBytes = "";
    public static String QReceivedTerminalBytes;
}
