package com.cocopass.helper.CMQ;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

public abstract class CMessageQueue {
 
	    Object conn=null;
	    Object channel = null;
	    Object consumer = null;
	    Object service= null;

	    public Object getConn() {
			return conn;
		}

		public void setConn(Object conn) {
			this.conn = conn;
		}

		public Object getChannel() {
			return channel;
		}

		public void setChannel(Object channel) {
			this.channel = channel;
		}

		public Object getConsumer() {
			return consumer;
		}

//		public void setConsumer(Object consumer) {
//			this.consumer = consumer;
//		}

		public Object getService() {
			return service;
		}

		public void setService(Object service) {
			this.service = service;
		}

		public abstract void SetConnection(String aliasName, String host, String userName, String password, int port); 
	    
	    public abstract Object GetNextMessage(String exchangeName,String queueName,Object option) ; 
	      
		public abstract void PublishMessage(String exchangeName, String publishKey, byte[] bytes) ;

		public abstract void SetService();
		
		public abstract void SetConsumer(String queueName);
		
		public abstract void InitService();
		
		public abstract void AckMessage(Object message,String tag,boolean requeue);
		
		public abstract void NAckMessage(Object message,String tag,boolean requeue);
		
		public abstract Message TranseMessage(Object msg);
				
}
