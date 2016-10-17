package com.kl.nts.server.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.log4j.Logger;

import com.cocopass.helper.CByte;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

/**
 * Json Decoder fpr outgoing messages
 */
public class HttpDecoder extends MessageToMessageDecoder<HttpMessage> {
   // private static final Logger LOG = Logger.getLogger(HttpDecoder.class.getName());
    Gson gson=new Gson();
    @Override
    protected void decode(ChannelHandlerContext ctx, HttpMessage msg, List<Object> out) throws Exception {
        if (msg instanceof FullHttpRequest && msg.getDecoderResult().isSuccess()) {
            FullHttpRequest request = (FullHttpRequest) msg;
            if (request.content().isReadable()) {
                ByteBuf buffer = new UnpooledByteBufAllocator(false).buffer(request.content().readableBytes());
                request.content().getBytes(0, buffer);
                out.add(new String(buffer.array()));
            } else {
                QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
                Map<String, List<String>> params = queryStringDecoder.parameters();
                if (params.containsKey("body")) {
                    List<String> bodys = params.get("body");
                    if (bodys.size() > 0) {
                        String body = bodys.get(0);                   
                        out.add(body);
                    } else {
                        com.cocopass.helper.CLoger.Warn("body content is empty.");
                    }
                } else {
                	com.cocopass.helper.CLoger.Warn("body content is empty.");
                }
            }
        } else {
        	com.cocopass.helper.CLoger.Warn(msg.getDecoderResult().cause().toString());
        }
    }
}
