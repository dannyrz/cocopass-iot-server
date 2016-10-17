package com.cocopass.helper.CMQ;

import java.io.IOException;

import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.services.servicebus.ServiceBusConfiguration;
import com.microsoft.windowsazure.services.servicebus.ServiceBusContract;
import com.microsoft.windowsazure.services.servicebus.ServiceBusService;
import com.microsoft.windowsazure.services.servicebus.models.BrokeredMessage;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveMessageOptions;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveMode;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveSubscriptionMessageResult;
 

public class AzureDataBusMQ extends  CMessageQueue{

	
	ServiceBusContract service;
	ReceiveMessageOptions options;
//	Configuration config;
	 
	public ServiceBusContract getService() {
		return this.service;
	}
		

	@Override
	public Object GetNextMessage(String exchangeName,String queueName,Object option) {
		// TODO Auto-generated method stub
		
	    ReceiveSubscriptionMessageResult resultSubMsg = null;
	    BrokeredMessage message = null;
		try {
			resultSubMsg = this.service.receiveSubscriptionMessage(exchangeName, queueName, this.options);
		    message = resultSubMsg.getValue();
		    
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	         
	        
	       return message; 
	}


	@Override
	public void PublishMessage(String exchangeName, String publishKey, byte[] bytes) {
		// TODO Auto-generated method stub
		//String message=new String(bytes);
		BrokeredMessage brokeredMessage = new BrokeredMessage(bytes);
		try {
			this.getService().sendTopicMessage(exchangeName, brokeredMessage);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void SetConnection(String aliasName, String host, String userName, String password, int port) {
		// TODO Auto-generated method stub
		Configuration config =
			    ServiceBusConfiguration.configureWithSASAuthentication(
			      aliasName,
			      userName,
			      password,
			      host
			      );
		
		super.conn=config;
	}

	@Override
	public void SetService() {
		// TODO Auto-generated method stub
		ServiceBusContract service = ServiceBusService.create((Configuration)super.conn);
		this.service=service;
	}


	@Override
	public void InitService() {
		// TODO Auto-generated method stub
		ReceiveMessageOptions opts = ReceiveMessageOptions.DEFAULT;
	    opts.setReceiveMode(ReceiveMode.PEEK_LOCK);
	    this.options=opts;
	}

 


	@Override
	public Message TranseMessage(Object message) {
		// TODO Auto-generated method stub
		Message msg=new Message();
		BrokeredMessage bmesg=(BrokeredMessage)message;
		String messageID=bmesg.getMessageId();
		msg.setMessageID(messageID);
		byte[] content = null;
		try {
			 content=com.cocopass.helper.CByte.ReadInputStreamBytes(bmesg.getBody());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg.setContent(content);
		return msg;
	}


	@Override
	public void AckMessage(Object message, String tag, boolean requeue) {
		// TODO Auto-generated method stub
		BrokeredMessage bmsg=(BrokeredMessage)message;
		try {
			this.service.deleteMessage(bmsg);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void SetConsumer(String queueName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void NAckMessage(Object message, String tag, boolean requeue) {
		// TODO Auto-generated method stub
		BrokeredMessage bmsg=(BrokeredMessage)message;
		try {
			this.service.unlockMessage(bmsg);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}