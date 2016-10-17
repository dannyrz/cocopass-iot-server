package com.cocopass.iot.work;

import org.apache.log4j.Logger;

import com.kl.nts.server.tcp.NettyTCPServerHandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class SendMessageToTerminal implements Runnable{

//	 
//	    private String name;
//	    public SendMessageToTerminal(String name) {
//	        this.name = name;
//	    } 
	    private static final Logger LOG = Logger.getLogger(SendMessageToTerminal.class.getName());
	    public void run() {
	    	
	    	ChannelHandlerContext ctx=null;
//	        for (int i = 0; i < 5000; i++) {
//	            try {
//	            	if(com.kl.nts.Global.HT_TID_CHC.containsKey("600000001"))
//	            	{
//	            		 ctx=com.kl.nts.Global.HT_TID_CHC.get("600000001");
//	            		 //ctx.channel().writeAndFlush("AABBCCDDEEFFGG");
//	            		 //ctx.pipeline().writeAndFlush("AABBCCDDEEFFGG");
//	            		 // Unpooled.copiedBuffer("AABBCCDDEEFFGG",CharsetUtil.UTF_8);
//	            		  ctx.writeAndFlush("AABBCCDDEEFFGG".getBytes());
//	            		  //LOG.info("SMTT ctx hc:"+ctx.hashCode());
//	            	}
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	    } 
	}
}