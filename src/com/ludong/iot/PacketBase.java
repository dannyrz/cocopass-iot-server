package com.ludong.iot;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.cocopass.helper.CByte;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ludong.model.GEOConvert;
import com.ludong.model.Location;

public class PacketBase {

	Gson gson=new Gson();
	/*
	 * 根据数据包索引3的位置判断版本号。	
	 */
	byte[] packet;
	public PacketBase(byte[] buffer)
	{
		this.packet=buffer;
	}
	
	public  float GetVersion()
	{
		byte b=packet[2];
		float ver=0f;
		int b0=CByte.ByteArrayToInt(new byte[]{b,0,0,0})-118;
		if((0x86-118)>b0)
		{
			ver=1.0f;
		}
		else
		{
			int mainVer=b0/16;
			int deputyVer=b0%16;
			String strVer=String.valueOf(mainVer)+"."+ String.valueOf(deputyVer);
			ver= Float.parseFloat(strVer);
		}
		return ver;
	}
	
	public static float GetVersionByTerminalID(long terminalID){
		float version=0;
		if(terminalID>0){ //只要是2.0的车 配的肯定是2.0的桩
			//直接用规则判断减少查询，有特殊例外直接在配置文件读取
			//version=com.cocopass.iot.cache.Terminal.GetVersion(String.valueOf(terminalID));
			if(version<1){
				if(terminalID>623000000&&terminalID<624000000)
					version=2.3f;
				else if(terminalID>600000000&&terminalID<700000000)
					version=2.1f;
				else if(terminalID>800000000&&terminalID<900000000)
					version=1.1f;
				else if(terminalID>200000000&&terminalID<300000000)
					version=1.1f;
				else if(terminalID>500000000&&terminalID<600000000)
					version=2.1f;
		}
		}
		return version;
	}
	
 	public   byte GetTail()
	{
		return packet[packet.length-1];
	}

	/**
     * {获取数据包异或累加校验值}
     *
     * @param        {packet}    {数据包字节数组}
     * @return       {result}    {结果值}
     * @exception    {不能为空值且数组长度不能小于3}
     */  
	public   byte GetCheckValue()
    {
        int result = packet[0] ^ packet[1];
        for (int g = 2; g < packet.length-2; g++)
        {
        	result = result ^ packet[g];
        }
        return (byte)result;
    }
	
	public   byte CreateCheckValue(byte[] packet)
    {
        int result = packet[0] ^ packet[1];
        for (int g = 2; g < packet.length-2; g++)
        {
        	result = result ^ packet[g];
        }
        return (byte)result;
    }
	

	public   byte CreateLuYuanHeartCheckValue(byte[] packet)
    {
        byte[] newPacket=new byte[packet.length-1];
        System.arraycopy(packet, 1, newPacket, 0, packet.length-1);
        byte result=CreateCheckValue(newPacket);
        return  result;
    }
	
	public   String CreateServerAuthCode(byte[] gpsID)
	{
		int c1=0,c2=0,c3=0,c4=0,m1=0,m2=0;

		 c1 = (gpsID[0] * 20) % 256;
         c2 = (gpsID[1] * 21) % 256;
         c3 = (gpsID[2] * 22) % 256;
         c4 = (gpsID[3] * 23) % 256;

		 m1 = (c1 + c3) % 256;
         m2 = (c2 + c4) % 256;
        String ServerAuthCode = CByte.IntToHexString(m1) + CByte.IntToHexString(m2);
        return ServerAuthCode;
	}
	
	public   String GetClientAuthCode()
	{ 
      int len=packet.length;
      String ClientAuthCode = CByte.ByteToHexString(packet[len-4]) + CByte.ByteToHexString(packet[len-3]);
      return ClientAuthCode;
	}
	
	
	public   String GetHex() {
		 
		return CByte.BytesToHexString(packet);
	}
	
	public   int GetLength() {
		// TODO Auto-generated method stub
		 
		return packet.length;
	}
	
	public   int GetChildLength(int version) {
		// TODO Auto-generated method stub
		int length=0;
		if(version<2){
			length=com.cocopass.helper.CByte.ByteArrayToInt(new byte[]{packet[4],packet[3],0,0});
		}
		else{
			length=com.cocopass.helper.CByte.ByteArrayToInt(new byte[]{packet[5],packet[4],0,0});
		}
		return length;
	}
	
	public   boolean GetIsRealTime() {
		// TODO Auto-generated method stub
		int irt=packet[GetLength()-3];
		return (irt==1);
	}

   public long GetSamplingTime()
   {
	   return 0;
   }
   
   public byte[] GetTimestampByteArray()
   {
	   byte[] result=new byte[8];
	   Calendar now = Calendar.getInstance();
	   now.add(Calendar.HOUR,-8);
	   byte[] year=CByte.IntToByteArray(now.get(Calendar.YEAR));
	   byte[] month =CByte.IntToByteArray(now.get(Calendar.MONTH) + 1);
	   byte[] day =CByte.IntToByteArray(now.get(Calendar.DAY_OF_MONTH) );
	   byte[] hour =CByte.IntToByteArray(now.get(Calendar.HOUR_OF_DAY));
	   byte[] minutes =CByte.IntToByteArray(now.get(Calendar.MINUTE) );
	   byte[] seconds =CByte.IntToByteArray(now.get(Calendar.SECOND) );
	   byte millis =0;
	   
	   result[0] = year[2];
	   result[1] = year[3];
	   result[2] = month[3];    
	   result[3] = day[3]; 
	   result[4] = hour[3]; 
	   result[5] = minutes[3]; 
	   result[6] = seconds[3]; 
	   result[7] = millis; 
	   
	   return result;
   }
   
   public String GetBDLocation(String xy)  {
	   String result="";
	   String url="http://api.map.baidu.com/geoconv/v1/?ak=DEe7abb12ba344a6fcf4dc27c2044c45&coords="+xy+"&from=1&to=5";
	   String response = null;
	try {
		response = com.cocopass.helper.CHttp.GetResponseBody(url, null, null);
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   GEOConvert geo=gson.fromJson(response, GEOConvert.class);
	   if(geo.getStatus()==0)
	   {
		    //Type listType=new TypeToken<ArrayList<Location>>(){}.getType();//TypeToken内的泛型就是Json数据中的类型
		    ArrayList  list = (ArrayList<Location>)geo.getResult();
	        Location location=(Location)list.get(0);
	        result=location.getX()+","+location.getY();
	   }
	   return result;
   }
   public String GetGDLocation(String xy) {
	   String result="";
	   String url="http://restapi.amap.com/v3/assistant/coordinate/convert?locations="+xy+"&coordsys=gps&output=json&key=9045ef455a7f759781bb8f28f998c3ad";
	   String response = null;
	try {
		response = com.cocopass.helper.CHttp.GetResponseBody(url, null, null);
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   GEOConvert geo=gson.fromJson(response, GEOConvert.class);
	   if(geo.getStatus()==1)
	   {
	        result=geo.getLocations();
	   }
	   return result;
   }
   
 
}
