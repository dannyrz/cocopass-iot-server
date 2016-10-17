package com.ludong.iot;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PacketFactory {
 
		    public static IPacket GetPacketInstance(float version,byte[] buffer) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		    	IPacket packet = null;
//				if(version<2.0f){		 
//				    packet= new Packet_V1X(buffer);
//				}
//				else if(version==2.0f){ 
//					packet=new Packet_V20(buffer);
//				}
//				else if(version==2.1f){
//					 
//					packet=new Packet_V21(buffer);
//				}
		    	String typeName = "com.ludong.iot.Packet_V" + String.valueOf(version).replaceAll("\\.", "");
//		    	System.out.println(typeName);
		    	Class<?> iClass = Class.forName(typeName); //Object instance=
	            Constructor<?> cons[]=iClass.getConstructors();
	            Object instance=cons[0].newInstance(buffer);
	            packet=(IPacket)instance;
				return packet;
		    }
		}
 
