package com.cocopass.iot.cache;

import org.apache.log4j.Logger;
import com.cocopass.helper.CProperties;
import com.cocopass.helper.CRedis;
import com.kl.nts.server.tcp.NettyTCPServer;
import redis.clients.jedis.Jedis;

public class GPSInfo {
	
	//private static Jedis jedis=CRedis.GetJedis();
	private static final Logger LOG = Logger.getLogger(GPSInfo.class.getName());
	
	public static String GetValue(String gpsid)
	{
		CProperties cp=new CProperties();
		String key="GPSInfo:"+gpsid;
		String jsonGPSInfo="{\"ReceiveIP\":\""+cp.GetValue("ReceiveIP")+"\",\"ReceiveTCPPort\":\""+cp.GetValue("ReceiveTCPPort")+"\",\"ReceiveTCPPort\":\""+cp.GetValue("ReceiveUDPPort")+"\"}";
//		if(jedis==null)
//		{
//			LOG.info("�����ӳػ�ȡJedisʧ�ܡ�");
//			return jsonGPSInfo;
//		}
		String tjsonGPSInfo=CRedis.get(key);
		
		if(tjsonGPSInfo==null)
		{
		  LOG.info("Redis�������ݿⲻ���ڸ�GPS������Ϣ��ʹ��Ĭ�ϱ������á�");
		 }
		LOG.info(jsonGPSInfo);
		return jsonGPSInfo;
	}
}
