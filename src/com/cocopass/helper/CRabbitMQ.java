package com.cocopass.helper;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

public class CRabbitMQ {
 
	public static boolean PublishToExchange(Channel rabbitMQChannel,String exchangeName,byte[] bytes){
		boolean success=false;
		try {
			rabbitMQChannel.basicPublish(exchangeName,  "",  MessageProperties.PERSISTENT_TEXT_PLAIN, bytes);
			//?�Ƿ��лص��������ظ���״̬����
			success=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
}
