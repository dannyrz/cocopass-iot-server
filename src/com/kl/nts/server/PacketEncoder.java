package com.kl.nts.server;

import com.cocopass.helper.CByte;
import com.cocopass.helper.CProperties;
import com.cocopass.helper.CRedis;
import com.google.gson.Gson;
import com.kl.nts.json.Token;
import com.kl.nts.server.tcp.NettyTCPServer;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.Calendar;
import java.util.List;

/**
 * Json Encoder for incoming messages
 */
public class PacketEncoder extends MessageToMessageEncoder<byte[]> {
	
	private static final Logger LOG = Logger.getLogger(PacketEncoder.class.getName());
    
    @Override
    protected void encode(ChannelHandlerContext ctx,  byte[] responseObj , List<Object> out) throws Exception {
        //byte[] buffer = jsonMapper.writeValueAsBytes(token);
//        if (responsePacket.length == 0) {
//            return;
//        }
//        
//       
//       
////        com.kl.nts.json.CRouteInfo cRouteInfo=new com.kl.nts.json.CRouteInfo();
////        com.kl.nts.json.ReceiveInfo receiveInfo=new  com.kl.nts.json.ReceiveInfo();
////        receiveInfo.SetIP("112.12.79.58");
////        receiveInfo.SetTCPPort(6060);
////        receiveInfo.SetUDPPort(6060);
////        cRouteInfo.SetReceiveInfo(receiveInfo);
////        
////        String s=gson.toJson(cRouteInfo);
////        
////        int i=0;
//        
//       
//
    	//LOG.info("encode ctx hc:"+ctx.hashCode());
        //LOG.info("应答包HEX:"+CByte.BytesToHexString(responseObj));      
        out.add(Unpooled.wrappedBuffer(responseObj)); //Unpooled.wrappedBuffer 非常重要
//        
   }
}
