package com.kl.nts.task;

import com.kl.nts.json.AuthMessage;
import com.kl.nts.json.Token;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

public class ClientTask {
    //private static final Logger LOG = Logger.getLogger(ClientTask.class.getName());

    private Channel channel;

    public ClientTask(Channel channel) {
        this.channel = channel;
    }

    public void acceptRequest(final com.cocopass.iot.model.DownMessage dm) {
        try {
        	com.cocopass.helper.CLoger.Info(dm.GetTerminalID()+":New execution started in thread pool:"+dm.GetHexPacket());
            //LOG.info(dm.GetTerminalID());
            ChannelHandlerContext tmpCtx=null;
            if(!com.kl.nts.Global.HT_TID_CHC.containsKey(dm.GetTerminalID()))
            {
            	 channel.write("{\"Status\":4}");
            	 return;
            }
            else
            {
                tmpCtx=com.kl.nts.Global.HT_TID_CHC.get(dm.GetTerminalID());
                if(tmpCtx==null)
                {
                	 channel.write("{\"Status\":4}");
                	 return;
                }
            }
            ChannelFuture cf=tmpCtx.writeAndFlush(dm.GetPacket());
            cf.addListener(new ChannelFutureListener() {
            	 @Override
                 public void operationComplete(ChannelFuture future) throws Exception {
            		 String result="";
            	 
                     if (!future.isSuccess()) {
                         //System.out.println("handshake failed(" + future.getCause() + ")");
                    	 result=("{\"Status\":6}");
                     } else {
                    	 result=("{\"Status\":2}");
                     }
                     if(channel.isOpen()) {
                    	 channel.writeAndFlush(result); //在监听器里必须使用writeandflush （因为是异步？？）
                     }
                 }
            });

           
        } catch (Exception e) {
            com.cocopass.helper.CLoger.Error(e.getMessage());
        }
    }

    public void unregisteredChannel() {
    	com.cocopass.helper.CLoger.Info("Channel unregistered.");
    }
}
