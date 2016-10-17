package com.cocopass.helper;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class CSocket {
    public static boolean UDPSend(byte[] packet,String ip,int port)
    {
    	boolean result=false;
    	DatagramSocket client = null;
		try {
			InetAddress addr = InetAddress.getByName(ip);
			client = new DatagramSocket();
	        DatagramPacket sendPacket 
	            = new DatagramPacket(packet ,packet.length , addr , port);
	        client.send(sendPacket);
	        result=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(client!=null)
			   client.close();
		}
		return result;
    }
}
