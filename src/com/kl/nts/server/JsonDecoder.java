package com.kl.nts.server;

import com.cocopass.helper.CByte;
import com.google.gson.Gson;
import com.kl.nts.json.AuthMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;

/**
 * Json Decoder fpr outgoing messages
 */
public class JsonDecoder extends MessageToMessageDecoder<String> {
    //private static final ObjectMapper jsonMapper = new ObjectMapper();
    Gson gson=new Gson();
    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        if (msg == null) {
            return;
        }
         com.cocopass.iot.model.DownMessage dm=gson.fromJson(msg, com.cocopass.iot.model.DownMessage.class);
         byte[] packet=CByte.hexStringToBytes(dm.GetHexPacket());
         dm.SetPacket(packet);
         out.add(dm);
    }
}
