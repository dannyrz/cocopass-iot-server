package com.ludong.bll;

import java.io.IOException;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Queen {
public static boolean PublishToExchange(ConnectionFactory factory,byte[] buffer,String exchangeName){
boolean result=false;
try {
	 Connection connection = factory.newConnection();
	 Channel rabbitMQChannel=connection.createChannel();
	 rabbitMQChannel.basicPublish(exchangeName,  "",  MessageProperties.PERSISTENT_TEXT_PLAIN,buffer);
	 rabbitMQChannel.close();
	 connection.close();
	 result=true;
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return result;
}
}