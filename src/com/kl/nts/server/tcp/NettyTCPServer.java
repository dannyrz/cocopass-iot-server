package com.kl.nts.server.tcp;

import com.kl.nts.Config;
import com.kl.nts.server.JsonDecoder;
import com.kl.nts.server.JsonEncoder;
import com.kl.nts.server.PacketDecoder;
import com.kl.nts.server.PacketEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import org.apache.log4j.Logger;

public class NettyTCPServer extends Thread {

    //private static final Logger LOG = Logger.getLogger(NettyTCPServer.class.getName());
    private int port=5005;
    private int nettyBossTreadNum=2;
    private int nettyWorkerThreadNum=4;
    public String version="1.X";

    public NettyTCPServer(int port) {
        this.port = port;
    }
    
    public NettyTCPServer(int port,int nettyBossTreadNum,int nettyWorkerThreadNum,String version) {
        this.port = port;
        this.nettyBossTreadNum = nettyBossTreadNum;
        this.nettyWorkerThreadNum = nettyWorkerThreadNum;
        this.version=version;
    }

    public void run() {
    	/*
    	 * 优化BOSS线程数量
    	 */
        EventLoopGroup bossGroup = new NioEventLoopGroup(nettyBossTreadNum /* number of threads */ );
        EventLoopGroup workerGroup = new NioEventLoopGroup(nettyWorkerThreadNum /* number of threads */);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
            	 
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                	 
                	 ChannelPipeline pipeline =  ch.pipeline();
                 	 //pipeline.addLast(new LoggingHandler(LogLevel.ERROR)); //当创 建一个新的TCP连接进入或者断开时，执行此步骤。
                	 //pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(256,3,2,0,0));//旧版设备报文
                 	 int lengthFieldOffset=3;
                 	 if(version.equals("2"))
                 	 {
                 		lengthFieldOffset=4;
                 	 }
                 	 //LOG.info("lengthFieldOffset:"+ lengthFieldOffset);
                 	 
                 	 pipeline.addLast("idleStateHandler", new IdleStateHandler(60,60,0));
                 	 pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(256,lengthFieldOffset,2,0,0));

                 	 //最哈的做法是从这里开始分协议版本解析!
                 	 
                	 pipeline.addLast("decoder", new PacketDecoder());  
                	 pipeline.addLast("encoder", new PacketEncoder());  //一定要在NettyTCPServerHandler之前
                	 pipeline.addLast(new NettyTCPServerHandler());
                	 
                }
            });
            
            b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

            //Bind and start to accept incoming connections.
            Channel ch = b.bind(port).sync().channel();
            com.cocopass.helper.CLoger.Info("TCP server packet version "+version+" started on port " + port );
//            Channel ch2 = b.bind( Config.ReceiveTCPPort).sync().channel();
//            LOG.info("TCP Receive Server "+version+" started on port ["+ Config.ReceiveTCPPort+"]");
            ch.closeFuture().sync();
//           LOG.info("TCP Server stoped on port [" + port + "]");
//            ch2.closeFuture().sync();
//            LOG.info("TCP Server stoped on port [" + port + "]");
        } catch (Exception e) {
        	com.cocopass.helper.CLoger.Error(e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
