package com.kl.nts.server;

import com.kl.nts.Config;
import com.kl.nts.Global;
import com.kl.nts.server.tcp.NettyTCPServerHandler;
import com.ludong.iot.*;
import com.rabbitmq.client.MessageProperties;
import com.google.gson.Gson;

import org.apache.log4j.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import redis.clients.jedis.Jedis;

import org.codehaus.jackson.map.ObjectMapper;
import com.cocopass.helper.CByte;
import com.cocopass.helper.CProperties;
import com.cocopass.helper.CRedis;
import com.cocopass.helper.CString;

import java.util.Date;
import java.util.List;

/**
 * Packet Decoder fpr outgoing messages out为要往外发的对象
 */
public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {
	// private static final Logger LOG =
	// Logger.getLogger(PacketDecoder.class.getName());

	// private static final ObjectMapper jsonMapper = new ObjectMapper();
	// private static Jedis jedis= com.cocopass.helper.Redis.GetJedis();
	private static Gson gson = new Gson();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {

		if (msg == null) {
			return;
		}

		int len = msg.readableBytes();
		byte[] buffer = new byte[len];
		msg.getBytes(0, buffer);

		String packetHex = CByte.BytesToHexString(buffer);

		// 包头包尾结构验证
		if (len < 11 || buffer[0] != 0x29 || buffer[1] != 0x29 || buffer[len - 1] != 0x0D) {
			com.cocopass.helper.CLoger.Warn("packet struct dont fit the protocol defines:" + packetHex);
			return; // 是否会把终端T下线
		}

		PacketBase pb = new PacketBase(buffer);
		// 校验码验证
		byte checkValue0 = buffer[len - 2];
		byte checkValue1 = pb.GetCheckValue();

		if (checkValue0 != checkValue1) {
			com.cocopass.helper.CLoger.Warn("Client checkValue is not equal Server checkValue:" + packetHex);
			return;
		}
		// declare queue
		// Global.RabbitMQChannel.queueDeclare(Config.QReceivedTerminalBytes,
		// true, false, false, null);
		// send data to exchange

		// Global.RabbitMQChannel.basicPublish(Config.ECReceivedTerminalBytes,
		// "",MessageProperties.PERSISTENT_TEXT_PLAIN, buffer);

		// add by zhurizhao 附加了系统接收时间
	
			byte[] bufferNew = new byte[len + 8];
			System.arraycopy(buffer, 0, bufferNew, 0, len);
			byte[] arrayServerSamplingTime = com.cocopass.helper.CByte.longToBytes(new Date().getTime());
			bufferNew[len] = arrayServerSamplingTime[0];
			bufferNew[len + 1] = arrayServerSamplingTime[1];
			bufferNew[len + 2] = arrayServerSamplingTime[2];
			bufferNew[len + 3] = arrayServerSamplingTime[3];
			bufferNew[len + 4] = arrayServerSamplingTime[4];
			bufferNew[len + 5] = arrayServerSamplingTime[5];
			bufferNew[len + 6] = arrayServerSamplingTime[6];
			bufferNew[len + 7] = arrayServerSamplingTime[7];
			Global.CMQ.PublishMessage(Config.ECReceivedTerminalBytes, "", bufferNew);
	
		// add end

		// Send data to queue
		// Global.RabbitMQChannel.basicPublish("",
		// Config.QReceivedTerminalBytes, null, buffer);

 
			
		float version = pb.GetVersion();

		if (buffer[3] == (byte) 0x85)
			return;

		IPacket packetInstance = PacketFactory.GetPacketInstance(version, buffer);

		long terminalID = packetInstance.GetTerminalID();

		int signalling = packetInstance.GetSignalling();
		// byte[]
		// responsePacket=packetInstance.CreateResponsePacket(signalling);
		byte[] responsePacket = null;

		// add 加入心跳包判断终端状态处理
		// String tkey=terminalID+"-"+
		// Global.HT_TID_PONG.put(terminalID+,);
		// add end

		switch (signalling) {

		case 0x85:
			break;
		case 0x70:
			byte[] byteArrayGPSID = CByte.longToBytes(terminalID);

			// 鉴权码验证
			String serverAuthCode = "";// pb.GetServerAuthCode(byteArrayGPSID);
			String clientAuthCode = "";// PacketBase.GetClientAuthCode(buffer);

			if (!serverAuthCode.equals(clientAuthCode)) {
				com.cocopass.helper.CLoger.Warn("ServerAuthCode:" + serverAuthCode + "is not equal ClientAuthCode:"
						+ clientAuthCode + ":" + packetHex);
				return;
			}

			// String CRouteInfo = null;
			// String key = "CRouteInfo:" + terminalID;
			//
			// try {
			// CRouteInfo = CRedis.get(key);
			// if (LOG.isInfoEnabled()) {
			// logInfo = "get routeInfo from redis:" + CRouteInfo;
			// LOG.info(logInfo);
			// }
			// } catch (Exception e) {
			// LOG.error(e.getMessage());
			// }
			//
			// if (CRouteInfo == null || CRouteInfo.equals("")) {
			// CRouteInfo = Config.CRouteInfo;
			// if (LOG.isInfoEnabled()) {
			// logInfo = "get routeInfo from config:" + CRouteInfo;
			// LOG.info(logInfo);
			// }
			// }
			//
			// logInfo = null;
			//
			// com.kl.nts.json.CRouteInfo cRouteInfo = gson.fromJson(CRouteInfo,
			// com.kl.nts.json.CRouteInfo.class);
			// com.kl.nts.json.ReceiveInfo
			// receiveInfo=cRouteInfo.GetReceiveInfo();

			String key = "Terminal:" + terminalID;
			String strReceiveIP = com.cocopass.helper.CRedis.getMapValue(key, "ReceiveHost");
			String strReceiveTCPPort = com.cocopass.helper.CRedis.getMapValue(key, "ReceiveTCPPort");
			String strReceiveUDPPort = com.cocopass.helper.CRedis.getMapValue(key, "ReceiveUDPPort");
			int receiveTCPPort = 0;
			int receiveUDPPort = 0;
			if (CString.IsNullOrEmpty(strReceiveIP) || CString.IsNullOrEmpty(strReceiveTCPPort)
					|| CString.IsNullOrEmpty(strReceiveUDPPort)) {
				strReceiveIP = Config.ReceiveHost;
				receiveTCPPort = Config.ReceiveTCPPort;
				receiveUDPPort = Config.ReceiveUDPPort;
			} else {
				receiveTCPPort = Integer.parseInt(strReceiveTCPPort);
				receiveUDPPort = Integer.parseInt(strReceiveUDPPort);
			}
			// LOG.info(receiveIP);

			String[] arraryReceiveIP = strReceiveIP.split("\\.");// “.”和“|”都是转义字符，必须得加"\\";

			// LOG.info(arraryReceiveIP[0]+"-"+arraryReceiveIP[1]+"-"+arraryReceiveIP[2]+"-"+arraryReceiveIP[3]);

			byte[] byteArraryReceiveIP = new byte[4];
			byteArraryReceiveIP[0] = (byte) Integer.parseInt(arraryReceiveIP[0]);
			byteArraryReceiveIP[1] = (byte) Integer.parseInt(arraryReceiveIP[1]);
			byteArraryReceiveIP[2] = (byte) Integer.parseInt(arraryReceiveIP[2]);
			byteArraryReceiveIP[3] = (byte) Integer.parseInt(arraryReceiveIP[3]);

			byte[] byteArraryReceiveTCPPort = CByte.IntToByteArray(receiveTCPPort);
			byte[] byteArraryReceiveUDPPort = CByte.IntToByteArray(receiveUDPPort);

			responsePacket = packetInstance.CreateAuthResponsePacket(byteArraryReceiveIP, byteArraryReceiveTCPPort,
					byteArraryReceiveUDPPort);

			// com.cocopass.helper.CRedis.SetMapValue("Terminal:"+terminalID,
			// "HostIP", receiveIP);

			break;

		default:
			responsePacket = packetInstance.CreateResponsePacket();
			break;
		}

		if (responsePacket == null)
			return;

		com.cocopass.iot.model.DownMessage dm = new com.cocopass.iot.model.DownMessage();
		dm.SetTerminalID(terminalID);
		dm.SetPacket(responsePacket);
		// LOG.info(dm.GetTerminalID());
		// LOG.info("decode ctx hc:"+ctx.hashCode() );
		out.add(dm);
 
 
	}

}
