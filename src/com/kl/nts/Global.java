package com.kl.nts;

import java.util.Hashtable;
import com.rabbitmq.client.Channel;
import io.netty.channel.ChannelHandlerContext;



/**
 * 
 * @author Administrator
 *
 *TID:�ն�ID
 *HC:ͨ��������
 *PONG:����ʱ��
 */
public class Global {
	public static Channel RabbitMQChannel=null;
	public static com.cocopass.helper.CMQ.CMessageQueue CMQ=null;
	public static Hashtable<Long, ChannelHandlerContext> HT_TID_CHC = new Hashtable<Long, ChannelHandlerContext>();
	public static Hashtable<Integer,Long> HT_HC_TID = new Hashtable<Integer,Long>();
	public static Hashtable<String,Long> HT_TIDHC_PONG = new Hashtable<String,Long>();
	
}
