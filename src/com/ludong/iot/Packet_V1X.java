package com.ludong.iot;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.encoding.Base64;

import com.cocopass.helper.*;
import com.ludong.model.Alarm;
import com.ludong.model.BatteryInfo;
import com.ludong.model.ControllerInfo;
import com.cocopass.iot.model.AuthInfo;
import com.cocopass.iot.model.DecodeDataRouter;
import com.cocopass.iot.model.DownMessageResult;
import com.cocopass.iot.model.LoginInfo;
import com.ludong.model.GPSInfo;
import com.google.gson.Gson;
public class Packet_V1X extends PacketBase implements  IPacket {

	byte[] buffer=null;
	long terminalID=0;
	float version=1.0f;
	int length=0;  //47 或者 54
	boolean isRealTime=true;
	long samplingTime=0;
	long writtenIntoSystemTime=0;
	int signaling;
	byte checkValue;
	byte tail;
	String hex;
	
	public Packet_V1X(byte[] buffer)  {
		// TODO Auto-generated constructor stub
		super(buffer);
		if(buffer!=null )
		{
		this.buffer=buffer;
		this.terminalID= GetTerminalID();
		this.length=GetLength();
		this.signaling=GetSignalling();
		this.checkValue=GetCheckValue();
		this.tail=GetTail();
		this.hex=GetHex();
		this.version=GetVersion();
		/*
		else if((signaling==0x64||signaling==0x65)&&this.length==60)
		{
			this.version=1.1f;
		}
		*/
		
		
		this.writtenIntoSystemTime=new Date().getTime();
		this.samplingTime=writtenIntoSystemTime;
		if(this.signaling==0x60||this.signaling==0x61||this.signaling==0x62||this.signaling==0x64||this.signaling==0x65||this.signaling==0x67)
		{
			String strSamplingTime="20"+CByte.ByteToHexString(buffer[15])+"-"+CByte.ByteToHexString(buffer[16])+"-"+CByte.ByteToHexString(buffer[17])+" "+CByte.ByteToHexString(buffer[18])+":"+CByte.ByteToHexString(buffer[19])+":"+CByte.ByteToHexString(buffer[20]);
			try {
				this.samplingTime=com.cocopass.helper.CDate.ToMillions(strSamplingTime, "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		
	}

	
	@Override
	public long GetTerminalID() {
		// TODO Auto-generated method stub
		byte[] byteArrayGPSID=new byte[]{0,0,0,0,buffer[5],buffer[6],buffer[7],buffer[8]};
		long gpsID=CByte.bytesToLong(byteArrayGPSID);
		return gpsID;
	}
	
	
 
	public int GetSignalling() {
		// TODO Auto-generated method stub
		int signalling=buffer[2];
		return signalling;
	}
	
	@Override
	public boolean GetIsRealTime()
	{
		boolean rt=true;
		if(this.signaling==0x61||this.signaling==0x65)
		{
			rt=false;
		}
		return rt;
	}
	

	public static float GetStandardFormatLocation(String bcdFormatValue)//03108883
	{
		float result=0;
		float r1=Float.parseFloat(bcdFormatValue.substring(0,3));
		String txt=bcdFormatValue.substring(3,5)+"."+bcdFormatValue.substring(5,8);
		float r2=Float.parseFloat(txt); 
		r2=r2/ 60f;
		result=r1+r2;
		return result;
	}

	public com.cocopass.iot.model.DownMessageResult  SendPacket(byte[] packet,String url){
		com.cocopass.iot.model.DownMessageResult result=new com.cocopass.iot.model.DownMessageResult();
		org.tempuri.IServiceProxy service=new org.tempuri.IServiceProxy(url);

		String response="0";
		try {
			response = service.addData_ZL(packet);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("指令接口返回：" + response);
		if(response.equals("1"))
			result.SetStatus(2);
		else
			result.SetStatus(5);
		return result;
	}

	@Override
	public byte[] CreateAuthResponsePacket(byte[] byteArraryReceiveIP,byte[] byteArraryReceiveTCPPort,byte[] byteArraryReceiveUDPPort) {
		// TODO Auto-generated method stub
      byte[] responsePacket=new byte[19];
      responsePacket[0] = 0x29;
      responsePacket[1] = 0x29;
      responsePacket[2] = (byte) 0x9f;
      responsePacket[3] = 0x00;
      responsePacket[4] = 0x0e;
      responsePacket[5] = buffer[5];
      responsePacket[6] = buffer[6];
      responsePacket[7] = buffer[7];
      responsePacket[8] = buffer[8];
      responsePacket[9] = byteArraryReceiveIP[0];
      responsePacket[10] = byteArraryReceiveIP[1];
      responsePacket[11] = byteArraryReceiveIP[2];
      responsePacket[12] = byteArraryReceiveIP[3];
      responsePacket[13] = byteArraryReceiveTCPPort[2];//因为只有两个字节，所以去掉2个高位
      responsePacket[14] = byteArraryReceiveTCPPort[3];
      responsePacket[15] = byteArraryReceiveUDPPort[2];
      responsePacket[16] = byteArraryReceiveUDPPort[3];
      responsePacket[17] = CreateCheckValue(responsePacket);
      responsePacket[18] = 0x0D;
	  return responsePacket;
	}


	@Override
	public GPSInfo GetGPSInfo() {
		// TODO Auto-generated method stub
		byte[] packet=buffer;
		boolean[] arrayState=CByte.ToBooleanArray(packet[33]);
		boolean	positionState=arrayState[0];
		
		int	antennaState= 0;
		if(arrayState[1]&&arrayState[2])
			antennaState=3;
		else if(arrayState[1]&&!arrayState[2])
			antennaState=2;
		else if(!arrayState[1]&&arrayState[2])
			antennaState=1;
	
		
		int powerState=0;
		if(arrayState[3]&&arrayState[4]&&arrayState[5])
			powerState=7;
		if(arrayState[3]&&!arrayState[4]&&arrayState[5])
			powerState=5;
		if(arrayState[3]&&arrayState[4]&&!arrayState[5])
			powerState=6;
		if(!arrayState[3]&&arrayState[4]&&arrayState[5])
			powerState=3;
		
		String bcdSpeed=CByte.ByteToHexString(packet[29])+CByte.ByteToHexString(packet[30]);
		int speed=Integer.parseInt(bcdSpeed);
		
		String bcdDirection=CByte.ByteToHexString(packet[31])+CByte.ByteToHexString(packet[32]);
		int direction=Integer.parseInt(bcdDirection);
		
		float externalVoltage=0;
		boolean isDefence=false;
		int length =packet.length;
		if(version==1.1f)
		{
			isDefence=(packet[length-1-2-2]==1);
			externalVoltage=packet[length-1-2-1]+0.01f*packet[length-1-2];
		}
		float latitude= GetStandardFormatLocation(CByte.ByteToHexString(packet[21])+CByte.ByteToHexString(packet[22])+CByte.ByteToHexString(packet[23])+CByte.ByteToHexString(packet[24]));
		float longitude= GetStandardFormatLocation(CByte.ByteToHexString(packet[25])+CByte.ByteToHexString(packet[26])+CByte.ByteToHexString(packet[27])+CByte.ByteToHexString(packet[28]));
		//long lSamplingTime=Date.parse();
		//Date samplingTime=CDate.ToDate("20"+CByte.ByteToHexString(packet[15])+"-"+CByte.ByteToHexString(packet[16])+"-"+CByte.ByteToHexString(packet[17])+" "+CByte.ByteToHexString(packet[18])+":"+CByte.ByteToHexString(packet[19])+":"+CByte.ByteToHexString(packet[20]));
		float totalMileage=(packet[34]*256*256+packet[35]*256+packet[36])*100f/1000;
		
 
		
		GPSInfo gpsInfo=new GPSInfo();
		
		gpsInfo.SetTotalMileage(totalMileage);
		gpsInfo.SetWrittenIntoSystemTime(writtenIntoSystemTime);
		gpsInfo.SetLatitude(latitude);
		gpsInfo.SetLongitude(longitude);
		gpsInfo.SetSamplingTime(samplingTime);
		gpsInfo.SetExternalVoltage(externalVoltage);
		gpsInfo.SetIsDefence(isDefence);
		gpsInfo.SetPowerState(powerState);
		gpsInfo.SetPositionState(positionState);
		gpsInfo.SetTerminalID(terminalID);
		gpsInfo.SetVersion(version);	
		gpsInfo.SetBuiltInBatteryVoltageQuantity(0);
		gpsInfo.SetAntennaState(antennaState);
		gpsInfo.SetDirection(direction);
		gpsInfo.SetGSMSignalValue(0);
		gpsInfo.SetGSMStationAreaID("");
		gpsInfo.SetGSMStationID("");
		gpsInfo.SetGPSSignalValue(0);
		gpsInfo.SetIsRealTime(isRealTime);
		gpsInfo.SetSpeed(speed);
		gpsInfo.SetAlarmNO(255);
		gpsInfo.SetAlarmParam(0);
		
		if(gpsInfo.GetPositionState()||latitude>0)
		{
			double[] gdbd=com.cocopass.bll.GPSLocationTranse.WGS2GCJAndBD(latitude, longitude);
			String bDLocation=gdbd[3]+","+gdbd[2];
			gpsInfo.setBDLocation(bDLocation);
			String gDLocation=gdbd[1]+","+gdbd[0];
			gpsInfo.setGDLocation(gDLocation);

		}
		
		return gpsInfo;
 
	}


	@Override
	public BatteryInfo GetBatteryInfo() {
		// TODO Auto-generated method stub
		byte[] packet=buffer;
		com.ludong.model.BatteryInfo batteryInfo=null;
		
		if(signaling==0x64||signaling==0x65)
		{
			batteryInfo=new com.ludong.model.BatteryInfo();
			batteryInfo.SetWrittenIntoSystemTime(writtenIntoSystemTime);
			batteryInfo.SetSamplingTime(samplingTime);
			batteryInfo.SetTerminalID(terminalID);
			batteryInfo.SetIsRealTime(isRealTime);
			batteryInfo.SetVersion(version);	
			int typeID=packet[37];
			batteryInfo.SetTypeID(typeID);
			
			//com.cocopass.iot.model.Battery battery=com.cocopass.bll.Battery.GetItemNum(typeID);
			int batteryItemNum=com.ludong.bll.Battery.GetItemNum(typeID);
			StringBuilder  voltageValueList=new StringBuilder();
			float cardinality =0.01f;
			if(typeID>206)
				cardinality=0.1f;
			for(int i=0;i<batteryItemNum;i++)
			{
				float voltage=packet[37+1+i]*cardinality;
				voltageValueList.append(","+ Float.toString(voltage));
			}
			batteryInfo.SetVoltageValueList(voltageValueList.toString().substring(1));
			int chargeNumeric=packet[37+batteryItemNum+10];
			batteryInfo.SetChargeNumeric(chargeNumeric);
			int healthCondition=packet[37+batteryItemNum+11];
			batteryInfo.SetHealthCondition(healthCondition);
			float rechargeCurrent=0;
			int rechargeCurrentIndex=37 + batteryItemNum + 3;
			 if ((packet[rechargeCurrentIndex] & 0x80) == 0x80)
             {
				 packet[rechargeCurrentIndex] = (byte) ( packet[rechargeCurrentIndex] & 0x7f);
				 rechargeCurrent = -0.01f*Integer.parseInt(CByte.ByteToHexString(packet[rechargeCurrentIndex])+  CByte.ByteToHexString(packet[rechargeCurrentIndex+1]), 16) ;
             }
             else
             {
            	 rechargeCurrent = 0.01f*Integer.parseInt(CByte.ByteToHexString(packet[rechargeCurrentIndex])+  CByte.ByteToHexString(packet[rechargeCurrentIndex+1]), 16) ;
             }
			batteryInfo.SetRechargeCurrent(rechargeCurrent);
			int rechargeTimes=CByte.ByteArrayToInt(new byte[]{0,0,packet[37+batteryItemNum+7],packet[37+batteryItemNum+8]});
			batteryInfo.SetRechargeTimes(rechargeTimes);
			float rechargeVoltage=0.01f*CByte.ByteArrayToInt(new byte[]{0,0,packet[37+batteryItemNum+1],packet[37+batteryItemNum+2]});
			batteryInfo.SetRechargeVoltage(rechargeVoltage);
			float residualCapacity=0.01f*CByte.ByteArrayToInt(new byte[]{0,0,packet[37+batteryItemNum+5],packet[37+batteryItemNum+6]});
			batteryInfo.SetResidualCapacity(residualCapacity);
			float temperature=packet[37+batteryItemNum+9]*1f-40;
			batteryInfo.SetTemperature(temperature);
		
		}
		return batteryInfo;
	}


	@Override
	public ControllerInfo GetControllerInfo() {
		// TODO Auto-generated method stub
 
			byte[] packet=buffer;
				
			com.ludong.model.ControllerInfo controllerInfo=null;
			if(signaling==0x60||signaling==0x61)
			{
			boolean[] controllerState0=CByte.ToBooleanArray(packet[40]);
			boolean[] controllerState1=CByte.ToBooleanArray(packet[44]);
			boolean isBrakeHandle=controllerState0[4];
			boolean isBrakeHandleFault=controllerState1[3];
			boolean isControllerFault=controllerState1[5];
			boolean isHeadLightOn=controllerState0[1];
			boolean isMotorFault=controllerState1[6];
			boolean isOverLoad=controllerState0[5];
			boolean isOverTemperature=controllerState0[6];
			boolean isRunning=(packet[43]==1);
			boolean isTrumpet=controllerState0[2];
			boolean isTurnHandle=controllerState0[3];
			boolean isTurnHandleFault=controllerState1[4];
			boolean isTurnLeftLightOn=controllerState0[0];
			boolean isTurnRightLightOn=controllerState0[0];  //当前只有转向灯状态，不区分左右转向
			boolean isUnderLimitVoltage=controllerState0[7]; 
			float rideVoltage= Integer.parseInt(CByte.ByteToHexString(packet[37])+CByte.ByteToHexString(packet[41]), 16)*0.1f;
			float rideCurrent=  Integer.parseInt(CByte.ByteToHexString(packet[38])+CByte.ByteToHexString(packet[42]), 16)*0.01f;
			
			float temperature=  Integer.parseInt(CByte.ByteToHexString(packet[39]),16)-40;
			int vin=0;
			if (this.version==1.1f)
			{
				vin= CByte.ByteArrayToInt(new byte[]{0,0,packet[45],packet[46]});
			}
			
			if(rideCurrent>0&&rideVoltage>0&&isRunning)
			{
			controllerInfo=new com.ludong.model.ControllerInfo();
			controllerInfo.SetIsBrakeHandle(isBrakeHandle);
			controllerInfo.SetIsBrakeHandleFault(isBrakeHandleFault);
			controllerInfo.SetIsControllerFault(isControllerFault);
			controllerInfo.SetIsHeadLightOn(isHeadLightOn);
			controllerInfo.SetIsMotorFault(isMotorFault);
			controllerInfo.SetIsOverLoad(isOverLoad);
			controllerInfo.SetIsOverTemperature(isOverTemperature);
			controllerInfo.SetIsRunning(isRunning);
			controllerInfo.SetIsTrumpet(isTrumpet);
			controllerInfo.SetIsTurnHandle(isTurnHandle);
			controllerInfo.SetIsTurnHandleFault(isTurnHandleFault);
			controllerInfo.SetIsTurnLeftLightOn(isTurnLeftLightOn);
			controllerInfo.SetIsTurnRightLightOn(isTurnRightLightOn);
			controllerInfo.SetIsUnderLimitVoltage(isUnderLimitVoltage);
			controllerInfo.SetRideCurrent(rideCurrent);
			controllerInfo.SetRideVoltage(rideVoltage);
			controllerInfo.SetSamplingTime(samplingTime);
			controllerInfo.SetWrittenIntoSystemTime(writtenIntoSystemTime);
			controllerInfo.SetTerminalID(terminalID);
			controllerInfo.SetTemperature(temperature);
			controllerInfo.SetVIN(vin);
			controllerInfo.SetIsRealTime(isRealTime);
			controllerInfo.SetVersion(version);	
			}
			}
			return controllerInfo;
 

	}


	@Override
	public List<Object> Decode() { //AndPublishToExchange
		// TODO Auto-generated method stub
		List<Object> list=new ArrayList<Object>();
		com.cocopass.iot.model.Packet packet=new com.cocopass.iot.model.Packet();
		 
		 
		byte[] bytesBody=new byte[length-11];
		for(int i=0;i<length-11;i++)
		{
			bytesBody[i]=buffer[i+9];
		}
		packet.SetHead(new byte[]{buffer[0],buffer[1]});
		packet.SetBody(bytesBody);
		packet.SetCheckValue(checkValue);
		packet.SetGPSID(String.valueOf(terminalID));
		packet.SetLength(length);
		packet.SetSignaling(signaling);
		packet.SetTail(tail);
		packet.SetHex(hex);
		
		Object model=null; 
		
		if(signaling==0x60||signaling==0x61){
			//******此处有时间建立内存队列中心，由独立线程完成发布任务******
			model=GetGPSInfo();
//			DecodeDataRouter ddr=new DecodeDataRouter();
//			ddr.SetDataObj(model);
//			ddr.SetExchangeName("ECJsonGPSInfo");
			list.add(model);
			model=GetControllerInfo(); 
			if(model!=null)
			{
//				ddr=new DecodeDataRouter();
//				ddr.SetDataObj(model);
//				ddr.SetExchangeName("ECJsonControllerInfo");
				list.add(model);
			}
		}
		else if(signaling==0x64||signaling==0x65){
			model=GetBatteryInfo();
//			DecodeDataRouter ddr=new DecodeDataRouter();
//			ddr.SetDataObj(model);
//			ddr.SetExchangeName("ECJsonBatteryInfo");
			list.add(model);
		}
		else if(signaling==0x62||signaling==0x67){
			Alarm alarm=GetAlarm();
			GPSInfo giModel=GetGPSInfo();
			giModel.SetAlarmNO(alarm.GetAlarmNO());
			giModel.SetAlarmParam(alarm.GetAlarmParam());
//			DecodeDataRouter ddr=new DecodeDataRouter();
//			ddr.SetDataObj(giModel);
//			ddr.SetExchangeName("ECJsonGPSInfo");
			list.add(giModel);
			
//			ddr=new DecodeDataRouter();
//			ddr.SetDataObj(alarm);
//			ddr.SetExchangeName("ECJsonAlarm");
			list.add(alarm);
		}
		else if(signaling==0x63){
			
		}
		else if(signaling==0x70){
			
		}
		else if(signaling==0x03){
			////29-29-03-00-1C-2F-AF-1D-13(ID)-0A(口数量)-0A-00-00-00-00-00-00-00-00-00 -00-00-00-00-00-62-08-AD-00-00-00-56-0D
			List<com.ludong.model.PileInfo> listPileInfo=new ArrayList<com.ludong.model.PileInfo>();
			com.ludong.model.Station station=new com.ludong.model.Station();
			int portNum=buffer[9]; 
			int k=0;
			int index=9+portNum+1;
			for(int i=0;i<portNum;i++){
				com.ludong.model.PileInfo pinfo=new com.ludong.model.PileInfo();
				int pilePort=i+1;
				pinfo.setPort(pilePort);
				int chargingPortStatus=buffer[9+i+1];
				pinfo.setChargingPortStatus(chargingPortStatus);
				pinfo.setStationTerminalID(terminalID);
				
				com.ludong.model.BatteryInfo bi= null;
				if(chargingPortStatus>0){
					bi= new com.ludong.model.BatteryInfo();
					long sessionID=CByte.bytesToLong(new byte[]{0,0,0,0,buffer[index+11*k],buffer[index+11*k+1],buffer[index+11*k+2],buffer[index+11*k+3]});
					int chargingDuration=CByte.ByteArrayToInt(new byte[]{buffer[index+11*k+5],buffer[index+11*k+4],0,0});
					float rechargeVoltage=((float)CByte.ByteArrayToInt(new byte[]{buffer[index+11*k+7],buffer[index+11*k+6],0,0}))/10;
					float rechargeCurrent=((float)((int)buffer[index+11*k+8]))/10;
					float ChargingPower =CByte.ByteArrayToInt(new byte[]{buffer[index+11*k+10],buffer[index+11*k+9],0,0,});
					bi.setChargingDuration(chargingDuration); 
					bi.SetRechargeCurrent(rechargeCurrent);
					bi.SetRechargeVoltage(rechargeVoltage);
					bi.setChargingPower(ChargingPower); 
					bi.setSessionID(sessionID);
					bi.SetTerminalID(terminalID);
					bi.setPilePort(i+1);
					bi.setChargingPortStatus(chargingPortStatus);		
					//bi.SetTerminalID(terminalID);
					list.add(bi);	
					k+=1;
				}
				
				pinfo.setBatteryData(bi);
				listPileInfo.add(pinfo);
			}
			 
			
			station.setTerminalID(terminalID);
			station.setPortNum(portNum);
			station.setPileData(listPileInfo);
			
			list.add(station);
			 
		}
		else if(signaling==0x85){
			//29-29-85-00-09-2F-AF-1B-C8-40-02-00-9D-0D
		}
		
		return list;
	}


	@Override
	public Alarm GetAlarm() {
		// TODO Auto-generated method stub
		byte[] packet=buffer;
		com.ludong.model.Alarm alarm = null; 
		if(signaling==0x62||signaling==0x67)
		{
			alarm=new com.ludong.model.Alarm ();
			
		int alarmNO=255;
		int alarmParam=0;
		if(signaling==0x62)
		{
			alarmNO=packet[length-1-2-4-1]*256+packet[length-1-2-4];
			alarmParam=CByte.ByteArrayToInt(new byte[]{packet[length-1-2-3],packet[length-1-2-2],packet[length-1-2-1],packet[length-1-2]});
			 
		}
		else if(signaling==0x67)
		{
			alarmNO=packet[length-1-2-4-1]*256+packet[length-1-2-4];
			alarmParam=CByte.ByteArrayToInt(new byte[]{packet[length-1-2-3],packet[length-1-2-2],packet[length-1-2-1],packet[length-1-2]});
			if(alarmNO==0)
				alarmNO=14;
			if(alarmNO==1)
				alarmNO=13;
		}
		alarm.SetAlarmNO(alarmNO);
		alarm.SetAlarmParam(alarmParam);
		alarm.SetIsRealTime(isRealTime);
		alarm.SetSamplingTime(samplingTime);
		alarm.SetTerminalID(terminalID);
		alarm.SetWrittenIntoSystemTime(writtenIntoSystemTime);
		alarm.SetVersion(version);	
		}
		return alarm;
	}

	 
	public float GetVersion() {
		// TODO Auto-generated method stub
		float ver=1.0f;
		if((signaling==0x60||signaling==0x61)&&this.length==54)
		{
			ver=1.1f;
		}
		else if((signaling==0x62||signaling==0x67)&&this.length==60)
		{
			ver=1.1f;
		}
		return ver;
	}


	 


 

	//@Override
	public byte[] CreateResponsePacket(int signalling) {
		// TODO Auto-generated method stub
		return null;
	}


	//@Override
	public byte[] CreateSwitchEBikePowerPacket(long terminalID, byte onORoff) {
		// TODO Auto-generated method stub
		 
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		byte[] packet=new byte[12];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = 0x58;
        packet[3] = 0;
        packet[4] = 0X07;
        packet[5] = byteArrayTerminalID[4];
        packet[6] = byteArrayTerminalID[5];
        packet[7] = byteArrayTerminalID[6];
        packet[8] = byteArrayTerminalID[7];
        packet[9] =  onORoff;
        packet[10] = CreateCheckValue(packet);
        packet[11] = 0x0D;
		return packet;
	}


	@Override
	public com.cocopass.iot.model.DownMessageResult SendSwitchEBikePowerPacket(long timestamp,long terminalID, byte onORoff,String url) {
		// TODO Auto-generated method stub
		/**
		 * 下面直接发送UDP的方法暂时注释，先采用调用原有接口的方式
		 */
		/*
	   com.cocopass.iot.model.DownMessageResult result=new com.cocopass.iot.model.DownMessageResult();
	   byte[] packet=CreateSwitchEBikePowerPacket(terminalID,onORoff);
	   Gson gson=new Gson();
	   String key="UDPEndPoint:"+terminalID;
	   String strEP=CRedis.get(key);
	   if(strEP==null||strEP.equals(""))
	   {
		   long lngTime=gson.fromJson(strEP, com.cocopass.iot.model.EndPoint.class).GetTime();
		   Date now=new Date();
		   if(Math.abs(now.getTime()-lngTime)>6000)
		   {
			   result.SetStatus(3);
			   return result;
		   }
	   }
	   com.cocopass.iot.model.EndPoint ep= gson.fromJson(strEP, com.cocopass.iot.model.EndPoint.class);
	   boolean success = CSocket.UDPSend(packet, ep.GetIP(), ep.GetPort());
	   if(success)
		   result.SetStatus(4);
	   return result;
	   */
		
		//com.cocopass.iot.model.DownMessageResult result=new com.cocopass.iot.model.DownMessageResult();
		//org.tempuri.IServiceProxy service=new org.tempuri.IServiceProxy(url);
		byte[] packet=CreateSwitchEBikePowerPacket(terminalID,onORoff);
		DownMessageResult result =SendPacket(packet,url);
        return result;
		
	}


 


	@Override
	public DownMessageResult SendSetTerminalIDPacket(long timestamp, long terminalID, long newTerminalID, String url) {
		// TODO Auto-generated method stub
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		byte[] byteArrayNewTerminalID=CByte.longToBytes(newTerminalID);
		byte[] packet=new byte[15];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = 0x45;
        packet[3] = 0;
        packet[4] = 0X0A;
        packet[5] = byteArrayTerminalID[4];
        packet[6] = byteArrayTerminalID[5];
        packet[7] = byteArrayTerminalID[6];
        packet[8] = byteArrayTerminalID[7];      
	    packet[9] = byteArrayNewTerminalID[4];
        packet[10] = byteArrayNewTerminalID[5];
        packet[11] = byteArrayNewTerminalID[6];
        packet[12] = byteArrayNewTerminalID[7];
        packet[13] = CreateCheckValue(packet);
        packet[14] = 0x0D;

        DownMessageResult result =SendPacket(packet,url);
        return result;
	}


	@Override
	public DownMessageResult SendSetTerminalAPNPacket(long timestamp, long terminalID, String apnName,
			String apnUserName, String apnPassword, String url) {
		
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		//11 固定长度 包头2+信令1+包长2+ID4+校验1+包尾1   3 name长度1+username长度1+password长度1 
		int apnNameLen=apnName.length();
		int apnUserNameLen=apnUserName.length() ;
		int apnPasswordLen=apnPassword.length() ;
		int packLength = 11 + 3 + apnNameLen + apnUserNameLen + apnPasswordLen;
		
		byte[] bytePackLength=CByte.IntToByteArray(packLength-5);//包长定义长度
		
		byte[] packet=new byte[packLength];
		packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = 0x48;
        packet[3] = bytePackLength[2];
        packet[4] = bytePackLength[3];
        packet[5] = byteArrayTerminalID[4];
        packet[6] = byteArrayTerminalID[5];
        packet[7] = byteArrayTerminalID[6];
        packet[8] = byteArrayTerminalID[7];
        packet[9] = (byte)apnNameLen;
        char[] chApnName = apnName.toCharArray();
        for (int i = 0; i < apnNameLen; i++)
        {
            packet[9 + 1 + i] = (byte)(chApnName[i]);
        }
        packet[9 +1+ apnNameLen] = (byte)apnUserNameLen; 
        char[] chApnUserName = apnUserName.toCharArray();
        for (int i = 0; i < apnUserNameLen; i++)
        {
            packet[9 +1+ apnNameLen + 1 + i] = (byte)(chApnUserName[i]);
        }
        packet[9 +1+ apnNameLen +1+ apnUserNameLen] = (byte)(apnPasswordLen);
        char[] chApnPassword = apnPassword.toCharArray();
        for (int i = 0; i < apnPasswordLen; i++)
        {
            packet[9 +1+ apnNameLen + 1+apnUserNameLen  +1+ i] = (byte)(chApnPassword[i]);
        }

        packet[packLength - 2] = CreateCheckValue(packet);
        packet[packLength-1]=0x0D;
        
        DownMessageResult result =SendPacket(packet,url);
        return result;
	}


	@Override
	public DownMessageResult SendReBootTerminalPacket(long timestamp, long terminalID, String url) {
		// TODO Auto-generated method stub
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		byte[] packet=new byte[11];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = 0x50;
        packet[3] = 0;
        packet[4] = 0X06;
        packet[5] = byteArrayTerminalID[4];
        packet[6] = byteArrayTerminalID[5];
        packet[7] = byteArrayTerminalID[6];
        packet[8] = byteArrayTerminalID[7];
        packet[9] = CreateCheckValue(packet);
        packet[10] = 0x0D;
        
        DownMessageResult result =SendPacket(packet,url);
        return result;
	}


	@Override
	public DownMessageResult SendSetTerminalAuthHostAndPort(long timestamp, long terminalID, String host, int port,
			String url) {
		// TODO Auto-generated method stub
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		int hostLen=host.length();
		
		byte[] portArrayByte=CByte.IntToByteArray(port);
		
		byte[] packet=new byte[17];
		
		
        
        String[] arrayHost=host.split("\\.");
        
        if(		arrayHost.length==4&&
        		CString.isNumeric(arrayHost[0])&&CString.isNumeric(arrayHost[1])
        		&&CString.isNumeric(arrayHost[2])&&CString.isNumeric(arrayHost[3])
          )
        {
        	packet[0] = 0x29;
            packet[1] = 0x29;
            packet[2] = 0x49;
            packet[3] = 0;
            packet[4] = 0x0C;
            packet[5] = byteArrayTerminalID[4];
            packet[6] = byteArrayTerminalID[5];
            packet[7] = byteArrayTerminalID[6];
            packet[8] = byteArrayTerminalID[7];
		    packet[9] = (byte) Integer.parseInt(arrayHost[0]);
		    packet[10] = (byte) Integer.parseInt(arrayHost[1]);
		    packet[11] = (byte) Integer.parseInt(arrayHost[2]);
		    packet[12] = (byte) Integer.parseInt(arrayHost[3]);
		    packet[13] = portArrayByte[2];
		    packet[14]=  portArrayByte[3];
		    packet[15] = CreateCheckValue(packet);
	        packet[16] = 0x0D;
        }
        else{
        		int valueLegth=1+hostLen+2;
        		packet=new byte[11+valueLegth];
        		
        		packet[0] = 0x29;
                packet[1] = 0x29;
                packet[2] = 0x06;
                packet[3] = 0;
                packet[4] = (byte)(11+valueLegth-5);
                packet[5] = byteArrayTerminalID[4];
                packet[6] = byteArrayTerminalID[5];
                packet[7] = byteArrayTerminalID[6];
                packet[8] = byteArrayTerminalID[7];
	        	packet[9] = (byte) hostLen;
	     	    char[] charArrayHost=host.toCharArray();
	     	    for(int i=0;i<hostLen;i++)
	     	    {
	     	    	packet[9+1+i] = (byte) charArrayHost[i];  
	     	    }
	     	   packet[9+hostLen+1] = portArrayByte[2];
	     	   packet[9+hostLen+2]=  portArrayByte[3];
	   	    
	           packet[11+valueLegth-2] = CreateCheckValue(packet);
	           packet[11+valueLegth-1] = 0x0D;
        	
        }
	    
        DownMessageResult result = SendPacket(packet,url);
        return result;
	}


	@Override
	public DownMessageResult SendSetEBikeMaxSpeedPacket(long timestamp, long terminalID, int speed, String url) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSwitchEBikeTailLightPowerPacket(long timestamp, long terminalID, byte onORoff,
			String url) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSwitchEBikeTailLightFlashPacket(long timestamp, long terminalID, byte onORoff,
			String url) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSwitchEBikeSaddleLockPacket(long timestamp, long terminalID, byte openOrLock,
			String url) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendResetEBikePacket(long timestamp, long terminalID, String url) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSwitchEBikeTurnLightFlashPacket(long timestamp, long terminalID, byte onORoff,
			String url) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSwitchPileLightPacket(long timestamp, long terminalID, int port, int lightNumber,
			byte onORoff, String url) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSwitchEBikePileLockPacket(long timestamp, long terminalID, int port, byte openOrLock,
			String url) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public byte[] CreateNormalResponsePacket() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public byte[] CreateResponsePacket() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSetHeartPongIntervalPacket(long timestamp, long terminalID, int heartPongInterval,
			String url) {
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		byte[] byteArrayHeartPongInterval=CByte.IntToByteArray(heartPongInterval);
		byte[] packet=new byte[13];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = 0x42;
        packet[3] = 0;
        packet[4] = 0X08;
        packet[5] = byteArrayTerminalID[4];
        packet[6] = byteArrayTerminalID[5];
        packet[7] = byteArrayTerminalID[6];
        packet[8] = byteArrayTerminalID[7];
	    packet[9] = byteArrayHeartPongInterval[2];
        packet[10] = byteArrayHeartPongInterval[3];  
        packet[11] = CreateCheckValue(packet);
        packet[12] = 0x0D;
        
        DownMessageResult result =SendPacket(packet,url);
        return result;
	}


	@Override
	public DownMessageResult SendPacket(long terminalID, byte[] packet, String url) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSetStopedIntervalPacket(long timestamp, long terminalID, int stopedInterval,
			String url) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSetRunningIntervalPacket(long timestamp, long terminalID, int runningInterval,
			String url) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSetShakeSensitivityPacket(long timestamp, long terminalID, int level, int rate,
			String url) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSetAlarmMobileNOPacket(long timestamp, long terminalID, long mobileNO, String url) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSetTotalMileagePacket(long timestamp, long terminalID, float totalMileage, String url) {
		// TODO Auto-generated method stub
		return null;
	}

 


	@Override
	public DownMessageResult SendSwitchPileChargerPacket(long timestamp, long terminalID, long sessionID,int batteryVoltage,
			int batteryCapacity, int port, byte onOroff, String url) {
		// TODO Auto-generated method stub
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		byte[] byteArraySessionID=CByte.longToBytes(sessionID);
		
		byte[] packet=new byte[17];
		packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = 0x02;
        packet[3] = 0;
        packet[4] = 0X0C;
        packet[5] = byteArrayTerminalID[4];
        packet[6] = byteArrayTerminalID[5];
        packet[7] = byteArrayTerminalID[6];
        packet[8] = byteArrayTerminalID[7];
        packet[9] = byteArraySessionID[4];
        packet[10] = byteArraySessionID[5];
        packet[11] = byteArraySessionID[6];
        packet[12] = byteArraySessionID[7];
        packet[13] = (byte)port;
        packet[14] = onOroff; ;
        packet[15] = CreateCheckValue(packet);
        packet[16] = 0x0D;
        DownMessageResult result =SendPacket(packet,url);
        return result;
	}


	@Override
	public DownMessageResult SendSetStationPileNumPacket(long timestamp, long terminalID, int pileNumber, String url) {
		// TODO Auto-generated method stub
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		byte[] byteArrayPileNum=CByte.IntToByteArray(pileNumber);
		
		byte[] packet=new byte[13];
		packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = 0x05;
        packet[3] = 0;
        packet[4] = 0X08;
        packet[5] = byteArrayTerminalID[4];
        packet[6] = byteArrayTerminalID[5];
        packet[7] = byteArrayTerminalID[6];
        packet[8] = byteArrayTerminalID[7];
        packet[9] = byteArrayPileNum[2];
        packet[10] = byteArrayPileNum[3];
        packet[11] = CreateCheckValue(packet);
        packet[12] = 0x0D;
        DownMessageResult result =SendPacket(packet,url);
        return result;
	}


	@Override
	public DownMessageResult SendReSetPilePortPacket(long timestamp, long terminalID, int port, String url) {
		// TODO Auto-generated method stub
		return null;
	}



	public AuthInfo GetAuthInfo() {
		// TODO Auto-generated method stub
		return null;
	}



	public LoginInfo GetLoginInfo() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSetDataIntervalPacket(long timestamp, long terminalID, int interval, String url) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DownMessageResult SendSetSecondDeviceFactoryModePacket(long timestamp, long terminalID, 
			String url) {
		// TODO Auto-generated method stub
		return null;
	}



}
