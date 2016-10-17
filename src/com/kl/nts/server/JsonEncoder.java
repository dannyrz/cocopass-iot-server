package com.kl.nts.server;

import com.google.gson.Gson;
import com.kl.nts.json.Token;
import com.kl.nts.task.ClientTask;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;

/**
 * Json Encoder for incoming messages
 */
public class JsonEncoder extends MessageToMessageEncoder<String> {
    private static final Gson gson = new Gson();
    private static final Logger LOG = Logger.getLogger(JsonEncoder.class.getName());
    
    @Override
    protected void encode(ChannelHandlerContext ctx, String result, List<Object> out) throws Exception {
        byte[] buffer = result.getBytes();
        if (buffer.length == 0) {
            return;
        }
        out.add(Unpooled.wrappedBuffer(buffer));
        
    }
}
