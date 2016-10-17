package com.kl.nts.server.tcp;

import com.cocopass.helper.CByte;
import com.kl.nts.Config;
import com.kl.nts.Global;
import com.kl.nts.task.ClientTask;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import org.apache.log4j.Logger;

public class NettyTCPServerHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger LOG = Logger.getLogger(NettyTCPServerHandler.class.getName());

    private ClientTask task;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
       
        if (msg instanceof com.cocopass.iot.model.DownMessage) {
    	   //if (msg instanceof byte[]) {
        	com.cocopass.iot.model.DownMessage dm=(com.cocopass.iot.model.DownMessage) msg;
            Global.HT_TID_CHC.put(dm.GetTerminalID(), ctx);
            
            //Global.HT_HC_TID.put(ctx.hashCode(), dm.GetTerminalID());
            ctx.writeAndFlush(dm.GetPacket());   
            
            
            // LOG.info("ServerHandler ctx hc:"+ctx.hashCode());
           // LOG.info("ServerHandler ctx hc:"+System.identityHashCode(ctx));
        } else {
            LOG.warn("Message is not valid.");
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    	//LOG.info("channelRegistered ctx hc:"+ctx.hashCode());
        task = new ClientTask(ctx.channel());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        task.unregisteredChannel();
        super.channelUnregistered(ctx);
        //Global.HT_TID_CHC.remove( Global.HT_HC_TID.get(ctx.hashCode()));
        //Global.HT_HC_TID.remove(ctx.hashCode());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.warn("Unexpected exception from channel. " + cause.getMessage());
        ctx.close();
    }
    
    /*
     * 处理闲置未有收发数据的连接
     * (non-Javadoc)
     * @see io.netty.channel.ChannelInboundHandlerAdapter#userEventTriggered(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
            	LOG.warn("READER_IDLE");
                // 超时关闭channel
                ctx.close();
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
            	LOG.warn("WRITER_IDLE");
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                //System.out.println("ALL_IDLE");
               
            }
        }
        super.userEventTriggered(ctx, evt); 
    }
}
