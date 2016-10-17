package com.cocopass.helper.CMQ;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.kl.nts.Config;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;

public class RabbitMQ extends  CMessageQueue{
	
	Channel rabbitMQChannel=null;
	Connection connection=null;
	QueueingConsumer consumer=null;

	@Override
	public void SetConnection(String aliasName, String host, String userName, String password, int port) {
		// TODO Auto-generated method stub
		
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost(host);
		factory.setUsername(userName);
		factory.setPassword(password);
		factory.setPort(port);
		try {
			connection = factory.newConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	@Override
	public Object GetNextMessage(String exchangeName,String queueName,Object option) {
		// TODO Auto-generated method stub
		Message msg=null;
		
		try {
			
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			msg=new Message();
			msg.content=delivery.getBody();
			msg.messageID= String.valueOf(delivery.getEnvelope().getDeliveryTag());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		return msg;
	}

	@Override
	public void PublishMessage(String exchangeName, String publishKey, byte[] bytes) {
		// TODO Auto-generated method stub
		try {
			rabbitMQChannel.basicPublish(exchangeName, publishKey,
					MessageProperties.PERSISTENT_TEXT_PLAIN, bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void SetService() {
		// TODO Auto-generated method stub
		try {
			rabbitMQChannel = connection.createChannel();
			rabbitMQChannel.basicQos(0, 100, false);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 

	@Override
	public void InitService() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AckMessage(Object message, String tag, boolean requeue) {
		// TODO Auto-generated method stub
		try {
			long itag=Long.parseLong(tag);
			rabbitMQChannel.basicAck(itag,false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Message TranseMessage(Object msg) {
		// TODO Auto-generated method stub
		return (Message)msg;
	}

	@Override
	public void SetConsumer(String queueName) {
		// TODO Auto-generated method stub
	    consumer = new QueueingConsumer(rabbitMQChannel);
		try {
			rabbitMQChannel.basicConsume(queueName, false, consumer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void NAckMessage(Object message, String tag, boolean requeue) {
		// TODO Auto-generated method stub
		long itag=Long.parseLong(tag);
		try {
			rabbitMQChannel.basicNack(itag, false, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

 
 
}
