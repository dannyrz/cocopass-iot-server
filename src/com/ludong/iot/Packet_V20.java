package com.ludong.iot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.cocopass.helper.CByte;
import com.cocopass.helper.CDate;
import com.cocopass.helper.CHttp;
import com.cocopass.helper.CString;
import com.ludong.model.Alarm;
import com.ludong.model.BatteryInfo;
import com.ludong.model.ControllerInfo;
import com.cocopass.iot.model.DecodeDataRouter;
import com.cocopass.iot.model.DownMessageResult;
import com.ludong.model.GPSInfo;
import com.cocopass.iot.model.Packet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

//时间戳不是用户给的时间戳，需要修正

public class Packet_V20 extends PacketBase implements IPacket {
	
	byte[] buffer=null;
	long terminalID=0;
    byte[] byteArrayTerminalID=new byte[]{0,0,0,0};
	float version=1.0f;
	int length=0;   
	int childLength=0;
	boolean isRealTime=true;
	long samplingTime=0;
	long writtenIntoSystemTime=0;
	int signaling;
	byte checkValue;
	byte childCheckValue;
	byte tail;
	String hex;
	byte[] timestampByteArray;
	
	long downTimestamp;
	long serverSamplingTime=0;

	
	Gson gson=new Gson();
	
	public Packet_V20(byte[] buffer) {
		super(buffer);
		 
		if(buffer!=null)
		{
			this.buffer=buffer;
 
			this.terminalID= GetV2TerminalID();
			this.byteArrayTerminalID=new byte[]{buffer[6],buffer[7],buffer[8],buffer[9]};
			this.length=GetLength();
			this.childLength=GetChildLength(2);
			this.signaling=GetSignalling();
			this.checkValue=GetCheckValue();
			this.childCheckValue=buffer[9];
			this.tail=GetTail();
			this.hex=GetHex();
			this.version=GetVersion();
			this.isRealTime=GetIsRealTime(); 
			this.writtenIntoSystemTime=new Date().getTime();
			this.samplingTime=GetSamplingTime();
			this.timestampByteArray=GetTimestampByteArray(); 
			this.downTimestamp=GetDownTimestamp();
			if(buffer[this.length - 9] == 0x0D && 6+8+this.childLength==this.length ){
				this.serverSamplingTime=com.cocopass.helper.CByte.bytesToLong(
						new byte[]{
								buffer[length - 8],
								buffer[length - 7],
								buffer[length - 6],
								buffer[length - 5],
								buffer[length - 4],
								buffer[length - 3],
								buffer[length - 2],
								buffer[length - 1]
									});
			}
		}
		
	}

	@Override
	public long GetTerminalID() {

			// TODO Auto-generated method stub
		    
			return this.terminalID;

	}
	
	public long GetV2TerminalID() {

		// TODO Auto-generated method stub
	    long gpsID=0;
	  
		byte[] byteArrayGPSID=new byte[]{0,0,0,0,buffer[6],buffer[7],buffer[8],buffer[9]};
	    
		//System.out.println(CByte.BytesToHexString(byteArrayGPSID));
		
		 gpsID=CByte.bytesToLong(byteArrayGPSID);
		
		return gpsID;

}
	 

	public int GetSignalling() {
		// TODO Auto-generated method stub
		int signalling=CByte.byteToUnit(buffer[3]);
		return signalling;
	}

	/** 
	* GetDownTimestamp  
	* 获取上行应答信令0x85指令的来源指令时间戳
	*/ 
	public long GetDownTimestamp()
	{
		long l=0;
		if(this.signaling==0x85)
		{
			//2929 97 85 001e 252239d5 001464653181 0000574d45fb 5a1020030100026a0D9455012b0D
			//2929 97 85 001e 252239d5 001464653828 0000574d4882 5a1020030100026a0De055018b0D
			//2929 97 85 001e 252239d5 001464654094 0000574d498d 5a1020030100026a0Dee55014f0D
		    l=com.cocopass.helper.CByte.bytesToLong(new byte[]{0,0,buffer[16],buffer[17],buffer[18],buffer[19],buffer[20],buffer[21]});
		}
		return l;
	}
	
	
	/**
	 * 加壳数据到绿动网壳
	 * @param timestamp
	 * @param lucencyPacket
	 * @param terminalID
	 * @return
	 */
	public byte[] AddGeneralShellToPacket(long timestamp,byte[] lucencyPacket,long terminalID)
	{
	    //System.out.println("AddGeneralShellToPacket"+ terminalID);
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);//从左到右 0-7   7是个位
		// System.out.println("AddGeneralShellToPacket"+ CByte.BytesToHexString(byteArrayTerminalID));
		int lpLength=lucencyPacket.length;
		
		byte[] packet=new byte[18+lpLength];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = (byte) 0x97;
        packet[3] = 0x55;
        packet[4] = 0;
        packet[5] = (byte)(18+lpLength-6);
        packet[6] = byteArrayTerminalID[4];
        packet[7] = byteArrayTerminalID[5];
        packet[8] = byteArrayTerminalID[6];
        packet[9] = byteArrayTerminalID[7];
        
        byte[] timestampByteArray=com.cocopass.helper.CByte.longToBytes(timestamp); //从左到右 0-7   7是个位
        
        packet[10] =  timestampByteArray[2];
        packet[11] =  timestampByteArray[3];
        packet[12] =  timestampByteArray[4];
	    packet[13] =  timestampByteArray[5];
	    packet[14] =  timestampByteArray[6];
	    packet[15] =  timestampByteArray[7];
	    
	    for(int i=0;i<lpLength;i++)
	    {
	    	packet[15+i+1]=lucencyPacket[i];
	    }

        packet[18+lpLength-2] = CreateCheckValue(packet);
        packet[18+lpLength-1] = 0x0D;
        
		return packet;
	}
	
//	byte[] CreateSwitchEBikePileLightPacket(long timestamp,long terminalID,int port,int lightNumber,byte onORoff)
//	{
//		
//		byte[]  childSwitchEBikePileLockPacket=new byte[8];
//		
//		childSwitchEBikePileLockPacket[0]=0x4a;
//		childSwitchEBikePileLockPacket[1]=0x00;
//		childSwitchEBikePileLockPacket[2]=0x02;
//		childSwitchEBikePileLockPacket[3]=(byte)port;
//		childSwitchEBikePileLockPacket[4]=(byte)lightNumber;
//		childSwitchEBikePileLockPacket[5]=onORoff;
//		childSwitchEBikePileLockPacket[6]=CreateCheckValue(childSwitchEBikePileLockPacket);;
//		childSwitchEBikePileLockPacket[7]=0x0D;
//				
//		byte[] packet=AddGeneralShellToPacket(timestamp,childSwitchEBikePileLockPacket,terminalID);
//		return packet;
//	}
	
 
	/**
	 * 获取当前实例的采样时间戳
	 * (non-Javadoc)
	 * @see com.ludong.iot.PacketBase#GetSamplingTime()
	 */
	@Override
	public long GetSamplingTime() {  //存在问题，需要考虑  AD等字符情况
		//String st=writtenIntoSystemTime;
		String bcdTimeCode=CByte.GetRemoveFirstZeroString(new byte[]{buffer[10],buffer[11],buffer[12],buffer[13],buffer[14],buffer[15]});
		long time=Long.parseLong(bcdTimeCode)*1000L+8*3600*1000;
//		java.util.Date date1 = new java.util.Date(time);    
//		st=CDate.ToString("yyyy-MM-dd HH:mm:ss", date1);
		return time;
	} 


	/**
	 * 创建鉴权应答包
	 * (non-Javadoc)
	 * @see com.ludong.iot.IPacket#CreateAuthResponsePacket(byte[], byte[], byte[])
	 */
	@Override
	public byte[] CreateAuthResponsePacket(byte[] byteArraryReceiveIP, byte[] byteArraryReceiveTCPPort,
		  byte[] byteArraryReceiveUDPPort) {
		  byte[] responsePacket=new byte[34];
	      responsePacket[0] = 0x29;
	      responsePacket[1] = 0x29;
	      responsePacket[2] = (byte) 0x96;
	      responsePacket[3] = (byte) 0x9f;
	      responsePacket[4] = 0x00;
	      responsePacket[5] = 0x1C;
	      responsePacket[6] = buffer[6];
	      responsePacket[7] = buffer[7];
	      responsePacket[8] = buffer[8];
	      responsePacket[9] = buffer[9];
	      responsePacket[10] = buffer[10];
	      responsePacket[11] = buffer[11];
	      responsePacket[12] = buffer[12];
	      responsePacket[13] = buffer[13];
	      responsePacket[14] = buffer[14];
	      responsePacket[15] = buffer[15];
	      responsePacket[16] = byteArraryReceiveIP[0];
	      responsePacket[17] = byteArraryReceiveIP[1];
	      responsePacket[18] = byteArraryReceiveIP[2];
	      responsePacket[19] = byteArraryReceiveIP[3];
	      responsePacket[20] = byteArraryReceiveTCPPort[2];//因为只有两个字节，所以去掉2个高位
	      responsePacket[21] = byteArraryReceiveTCPPort[3];
	      responsePacket[22] = byteArraryReceiveUDPPort[2];
	      responsePacket[23] = byteArraryReceiveUDPPort[3];
	      
	      responsePacket[24] = timestampByteArray[0];
	      responsePacket[25] = timestampByteArray[1];
	      responsePacket[26] = timestampByteArray[2];    
	      responsePacket[27] = timestampByteArray[3]; 
	      responsePacket[28] = timestampByteArray[4]; 
	      responsePacket[29] = timestampByteArray[5]; 
	      responsePacket[30] = timestampByteArray[6]; 
	      responsePacket[31] = timestampByteArray[7]; 
	      responsePacket[32] = CreateCheckValue(responsePacket);
	      responsePacket[33] = 0x0D;
	      return responsePacket;
	}


	/**
	 * 解析GPS定位数据包
	 */
	@Override
	public GPSInfo GetGPSInfo() {

		com.ludong.model.GPSInfo gpsInfo=new com.ludong.model.GPSInfo();
		String bcdtm=CByte.GetRemoveFirstZeroString(new byte[]{buffer[35],buffer[36],buffer[37]});
		if(bcdtm.equals("")) bcdtm="0";
		float totalMileage=Float.parseFloat(bcdtm)/10;
		gpsInfo.SetTotalMileage(totalMileage);
		gpsInfo.SetWrittenIntoSystemTime(writtenIntoSystemTime);
		String bcdLatitude=CByte.BytesToHexString(new byte[]{buffer[22],buffer[23],buffer[24],buffer[25]});
		String bcdLongitude=CByte.BytesToHexString(new byte[]{buffer[26],buffer[27],buffer[28],buffer[29]});
		bcdLatitude=bcdLatitude.substring(0, 3)+"."+bcdLatitude.substring(3);
		bcdLongitude=bcdLongitude.substring(0, 3)+"."+bcdLongitude.substring(3);
		bcdLatitude=bcdLatitude.replaceFirst("^0*", "");
		bcdLongitude=bcdLongitude.replaceFirst("^0*", "");
		float latitude=Float.parseFloat(bcdLatitude);
		float longitude=Float.parseFloat(bcdLongitude);
		gpsInfo.SetLatitude(latitude);
		gpsInfo.SetLongitude(longitude);
		gpsInfo.SetSamplingTime(samplingTime);
		/*
		String bcdExternalVoltage=CByte.BytesToHexString(new byte[]{buffer[38],buffer[39]});
		bcdExternalVoltage=bcdExternalVoltage.substring(0, 2)+"."+bcdExternalVoltage.substring(2);
		bcdExternalVoltage.replaceFirst("^0*", "");
		float externalVoltage=Float.parseFloat(bcdExternalVoltage);
		*/
		float externalVoltage=(buffer[38]*100+buffer[39])/100;
		
		gpsInfo.SetExternalVoltage(externalVoltage);
		boolean isDefence=buffer[42]==1;
		gpsInfo.SetIsDefence(isDefence);
		boolean isMosOpen=buffer[43]==1;
		gpsInfo.SetIsMosOpen(isMosOpen);
		
		boolean[] arrayState=CByte.ToBooleanArray(packet[34]);
		
		gpsInfo.SetPowerState(arrayState[7]==true?7:5); //重新解析，考虑BIT顺序
		gpsInfo.SetPositionState(arrayState[5]);
		gpsInfo.SetAntennaState(arrayState[6]==true?3:1);
		
		gpsInfo.SetTerminalID(terminalID);
		gpsInfo.SetVersion(version);	
		gpsInfo.SetBuiltInBatteryVoltageQuantity(0);
		String bcdSpeed=CByte.GetRemoveFirstZeroString(new byte[]{buffer[30],buffer[31]});
		
		if(bcdSpeed.equals("")) bcdSpeed="0";
		int speed=Integer.parseInt(bcdSpeed);
		String bcdDirection=CByte.GetRemoveFirstZeroString(new byte[]{buffer[32],buffer[33]});
		if(bcdDirection.equals("")) bcdDirection="0";
		int direction=Integer.parseInt(bcdDirection);
		gpsInfo.SetSpeed(speed);
		gpsInfo.SetDirection(direction);
		int gsmSignalValue=Integer.parseInt(CByte.ByteToHexString(buffer[20]));//bcd
		int gpsSignalValue=Integer.parseInt(CByte.ByteToHexString(buffer[21]));//bcd
		gpsInfo.SetGSMSignalValue(gsmSignalValue);
		String gsmStationAreaID=CByte.BytesToHexString(new byte[]{buffer[18],buffer[19]} );
		String gsmStationID=CByte.BytesToHexString(new byte[]{buffer[16],buffer[17]} );
		gpsInfo.SetGSMStationAreaID(gsmStationAreaID);
		gpsInfo.SetGSMStationID(gsmStationID);
		gpsInfo.SetGPSSignalValue(gpsSignalValue);
		gpsInfo.SetIsRealTime(isRealTime);
		
		int alarmNO=CByte.ByteArrayToInt(new byte[]{buffer[44],0,0,0});
		//System.out.println(alarmNO);
		String bcdAlarmParam =CByte.GetRemoveFirstZeroString(new byte[]{buffer[45],buffer[46],buffer[47],buffer[48]});
		int alarmParam=Integer.parseInt(bcdAlarmParam);
		gpsInfo.SetAlarmNO(alarmNO);
		gpsInfo.SetAlarmParam(alarmParam);
		
		if(gpsInfo.GetPositionState()||latitude>0)
		{
			double[] gdbd=com.cocopass.bll.GPSLocationTranse.WGS2GCJAndBD(latitude, longitude);
			String bDLocation=gdbd[3]+","+gdbd[2];
			gpsInfo.setBDLocation(bDLocation);
			
//		String xy=String.valueOf(longitude)+","+String.valueOf(latitude);
//		//String bDLocation=GetBDLocation(xy);
//		
//		String gDLocation=GetGDLocation(xy);
			String gDLocation=gdbd[1]+","+gdbd[0];
		gpsInfo.setGDLocation(gDLocation);

		}
		
		
		return gpsInfo;
	}

	/**
	 * 解析电池数据包
	 * 2929 9660 002e 0000 001a 0014 4918 3043 286a 0012 2900 0000 0000 0000 0000 0000 0000 0100 0000 0000 0000 01ff 0000 0000 0101 720D 
	 */	
	@Override
	public BatteryInfo GetBatteryInfo() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * 解析控制器数据包
	 */
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




   /**
    * 解包入口
    */
	@Override
	public List<Object> Decode() {
		// TODO Auto-generated method stub
		List<Object> list=new ArrayList<Object>();
//		com.cocopass.iot.model.Packet packet=new com.cocopass.iot.model.Packet();
		Object model=null; 
//		switch(this.signaling)
//		{
//		case 0x60:
//			model=GetGPSInfo();
//			break;
//			default :
//			break;
//		}
		if(this.signaling==0x60)
		{
			
			GPSInfo gpsInfo=GetGPSInfo();
			//DecodeDataRouter ddr=new DecodeDataRouter();
//			ddr.SetDataObj(gpsInfo);
//			ddr.SetExchangeName("ECJsonGPSInfo");
			list.add(gpsInfo);
			
			//判断是否有报警
			if(gpsInfo.GetAlarmNO()!=255)
			{
				Alarm alarm=new Alarm();
				 
				alarm.SetAlarmNO(gpsInfo.GetAlarmNO());
				alarm.SetAlarmParam(gpsInfo.GetAlarmParam());
				alarm.SetIsRealTime(isRealTime);
				alarm.SetSamplingTime(samplingTime);
				alarm.SetTerminalID(terminalID);
				alarm.SetWrittenIntoSystemTime(writtenIntoSystemTime);
				alarm.SetVersion(version);	
			 
				
//				ddr=new DecodeDataRouter();
//				ddr.SetDataObj(alarm);
//				ddr.SetExchangeName("ECJsonAlarm");
				list.add(alarm);
			}
		}
		else if(this.signaling==0x64)
		{
			com.ludong.model.ControllerInfo ci= GetControllerInfo();
			list.add(ci);
		}
		else if(this.signaling==0x85)
		{
			//2929 96 85 0015 252239d5 001464722156 4e5700000000 31 58 00 e8 0D
			//2929 96 85 0015 252239d5 001451598808 0000574e7855 1d 58 00 00 0D
			com.ludong.model.ActionLog al=new com.ludong.model.ActionLog();
    		al.SetSamplingTime(samplingTime);///////////
    		String serialNumber = String.valueOf(terminalID)+"-"+String.valueOf(downTimestamp);
    		al.SetSerialNumber(serialNumber);
    		int istatus=buffer[24];
    		int status=7;
    		if(istatus==0)//指令执行成功
    			status=3;
    		//else // if(istatus==1)//指令执行失败
    		//	status=7;
    	
    		al.SetStatus(status);
    		al.SetTerminalID(terminalID);
    		al.SetTimestamp(downTimestamp);
//    		DecodeDataRouter ddr=new DecodeDataRouter();
//			ddr.SetDataObj(al);
//			ddr.SetExchangeName("ECActionLog");
			list.add(al);
			
		}
		else if(this.signaling==0x70){
			com.cocopass.iot.model.AuthInfo ai=GetAuthInfo()  ;
			list.add(ai);
		}
		else if(this.signaling==0x01){
			com.cocopass.iot.model.LoginInfo li=GetLoginInfo()  ;
			list.add(li);
		}
		return list;
	}
	
	
	
	/**
	 * 解析鉴权信息
	 * (non-Javadoc)
	 * @see com.ludong.iot.IPacket#GetAlarm()
	 */


	public com.cocopass.iot.model.AuthInfo GetAuthInfo() {
		// TODO Auto-generated method stub
		String iMEI=com.cocopass.helper.CByte.BytesToHexString(new byte[]{buffer[16],buffer[17],buffer[18],buffer[19],buffer[20],buffer[21],buffer[22],buffer[23]});
		String iMSI=com.cocopass.helper.CByte.BytesToHexString(new byte[]{buffer[24],buffer[25],buffer[26],buffer[27],buffer[28],buffer[29],buffer[30],buffer[31]});
		String iCCID=com.cocopass.helper.CByte.BytesToHexString(new byte[]{buffer[32],buffer[33],buffer[34],buffer[35],buffer[36],buffer[37],buffer[38],buffer[39],buffer[40],buffer[41]});
		String firmwareVersion=com.cocopass.helper.CByte.ByteToHexString(buffer[44]);
		com.cocopass.iot.model.AuthInfo ai=new com.cocopass.iot.model.AuthInfo();
		ai.setServerSamplingTime(serverSamplingTime);
		ai.setSamplingTime(samplingTime);
		ai.setTerminalID(terminalID);
		ai.setIMEI(iMEI);
		ai.setIMSI(iMSI);
		ai.setICCID(iCCID);
		ai.setFirmwareVersion(firmwareVersion);
		return ai;
	}
	
	

	public com.cocopass.iot.model.LoginInfo GetLoginInfo() {
		// TODO Auto-generated method stub
		String receiveHost=com.cocopass.helper.CByte.byteToUnit(buffer[16])+"."+com.cocopass.helper.CByte.byteToUnit(buffer[17])+"."+com.cocopass.helper.CByte.byteToUnit(buffer[18])+"."+com.cocopass.helper.CByte.byteToUnit(buffer[19]);
		String receiveLocalHost=com.cocopass.helper.CByte.byteToUnit(buffer[20])+"."+com.cocopass.helper.CByte.byteToUnit(buffer[21])+"."+com.cocopass.helper.CByte.byteToUnit(buffer[22])+"."+com.cocopass.helper.CByte.byteToUnit(buffer[23]);
		int receiveTCPPort=com.cocopass.helper.CByte.byte2ToUnsignedShort(new byte[]{buffer[24],buffer[25]});
		int receiveUDPPort=com.cocopass.helper.CByte.byte2ToUnsignedShort(new byte[]{buffer[26],buffer[27]});
		
		com.cocopass.iot.model.LoginInfo li=new com.cocopass.iot.model.LoginInfo();
		
		li.setServerSamplingTime(serverSamplingTime);
		li.setSamplingTime(samplingTime);
		li.setTerminalID(terminalID);
		li.setReceiveHost(receiveHost);
		li.setReceiveLocalHost(receiveLocalHost);
		li.setReceiveTCPPort(receiveTCPPort);
		li.setReceiveUDPPort(receiveUDPPort);

		return li;
	}
	

	/**
	 * 解析报警数据
	 * (non-Javadoc)
	 * @see com.ludong.iot.IPacket#GetAlarm()
	 */

	@Override
	public Alarm GetAlarm() {
		// TODO Auto-generated method stub
		return null;
	}

 

	/**
	 * 创建通用应答包
	 */
	@Override
	public byte[] CreateNormalResponsePacket() {
		// TODO Auto-generated method stub
		  byte[] responsePacket=new byte[23];
		  responsePacket[0] = 0x29;
	      responsePacket[1] = 0x29;
	      responsePacket[2] = (byte) 0x96;
	      responsePacket[3] = (byte) 0x21;
	      responsePacket[4] = 0x00;
	      responsePacket[5] = 0x11;
	      responsePacket[6] = buffer[6];
	      responsePacket[7] = buffer[7];
	      responsePacket[8] = buffer[8];
	      responsePacket[9] = buffer[9];
	      responsePacket[10] =  timestampByteArray[0];
	      responsePacket[11] =  timestampByteArray[1];
	      responsePacket[12] =  timestampByteArray[2];
	      responsePacket[13] =  timestampByteArray[3];
	      responsePacket[14] =  timestampByteArray[4];
	      responsePacket[15] =  timestampByteArray[5];
	      responsePacket[16] =  timestampByteArray[6];
	      responsePacket[17] =  timestampByteArray[7];
	      responsePacket[18] = this.checkValue;
	      responsePacket[19]= (byte)this.signaling;
	      responsePacket[20]= this.childCheckValue;
	      responsePacket[21] = CreateCheckValue(responsePacket);
	      responsePacket[22] = 0x0D;
		return responsePacket;
	}

	//@Override
	public byte[] CreateResponsePacket(int signalling) {
		// TODO Auto-generated method stub
		byte[] responsePacket=null;
		switch(signalling)
		{
		case 0x70:
			//CreateAuthResponsePacket();
			break;
		default :
			CreateNormalResponsePacket();
			break;
		}
		return null;
	}


	/**
	 * 创建设置终端ID包
	 * @param timestamp
	 * @param terminalID
	 * @param newTerminalID
	 * @return
	 */
	public byte[] CreateSetTerminalIDPacket(long timestamp,long terminalID, long newTerminalID) {
		// TODO Auto-generated method stub
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		byte[] byteArrayNewTerminalID=CByte.longToBytes(newTerminalID);
		byte[] packet=new byte[22];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = (byte) 0x96;
        packet[3] = 0x45;
        packet[4] = 0;
        packet[5] = 0X10;
        packet[6] = byteArrayTerminalID[4];
        packet[7] = byteArrayTerminalID[5];
        packet[8] = byteArrayTerminalID[6];
        packet[9] = byteArrayTerminalID[7];
        
        byte[] timestampByteArray=com.cocopass.helper.CByte.longToBytes(timestamp);
        
        packet[10] =  timestampByteArray[2];
        packet[11] =  timestampByteArray[3];
        packet[12] =  timestampByteArray[4];
	    packet[13] =  timestampByteArray[5];
	    packet[14] =  timestampByteArray[6];
	    packet[15] =  timestampByteArray[7];

	    packet[16] = byteArrayNewTerminalID[4];
        packet[17] = byteArrayNewTerminalID[5];
        packet[18] = byteArrayNewTerminalID[6];
        packet[19] = byteArrayNewTerminalID[7];
	    
        packet[20] = CreateCheckValue(packet);
        packet[21] = 0x0D;
		return packet;
	}
	
	
	/**
	 * 创建设置心跳间隔包
	 * @param timestamp
	 * @param terminalID
	 * @param heartPongInterval
	 * @return
	 */
	public byte[] CreateSetHeartPongIntervalPacket(long timestamp,long terminalID, int heartPongInterval) {
		// TODO Auto-generated method stub
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		byte[] byteArrayHeartPongInterval=CByte.IntToByteArray(heartPongInterval);
		byte[] packet=new byte[20];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = (byte) 0x96;
        packet[3] = 0x42;
        packet[4] = 0;
        packet[5] = 0X0E;
        packet[6] = byteArrayTerminalID[4];
        packet[7] = byteArrayTerminalID[5];
        packet[8] = byteArrayTerminalID[6];
        packet[9] = byteArrayTerminalID[7];
        
        byte[] timestampByteArray=com.cocopass.helper.CByte.longToBytes(timestamp);
        
        packet[10] =  timestampByteArray[2];
        packet[11] =  timestampByteArray[3];
        packet[12] =  timestampByteArray[4];
	    packet[13] =  timestampByteArray[5];
	    packet[14] =  timestampByteArray[6];
	    packet[15] =  timestampByteArray[7];

	    packet[16] = byteArrayHeartPongInterval[2];
        packet[17] = byteArrayHeartPongInterval[3];
       
	    
        packet[18] = CreateCheckValue(packet);
        packet[19] = 0x0D;
		return packet;
	}
	
	/**
	 * 篡改见设置骑行回传间隔包
	 * @param timestamp
	 * @param terminalID
	 * @param runningInterval
	 * @return
	 */
	public byte[] CreateSetRunningIntervalPacket(long timestamp,long terminalID, int runningInterval) {
		// TODO Auto-generated method stub
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		byte[] byteArrayHeartPongInterval=CByte.IntToByteArray(runningInterval);
		byte[] packet=new byte[20];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = (byte) 0x96;
        packet[3] = 0x40;
        packet[4] = 0;
        packet[5] = 0X0E;
        packet[6] = byteArrayTerminalID[4];
        packet[7] = byteArrayTerminalID[5];
        packet[8] = byteArrayTerminalID[6];
        packet[9] = byteArrayTerminalID[7];
        
        byte[] timestampByteArray=com.cocopass.helper.CByte.longToBytes(timestamp);
        
        packet[10] =  timestampByteArray[2];
        packet[11] =  timestampByteArray[3];
        packet[12] =  timestampByteArray[4];
	    packet[13] =  timestampByteArray[5];
	    packet[14] =  timestampByteArray[6];
	    packet[15] =  timestampByteArray[7];

	    packet[16] = byteArrayHeartPongInterval[2];
        packet[17] = byteArrayHeartPongInterval[3];
       
	    
        packet[18] = CreateCheckValue(packet);
        packet[19] = 0x0D;
		return packet;
	}
	
	/**
	 * 创建停车回传间隔包
	 * @param timestamp
	 * @param terminalID
	 * @param stopedInterval
	 * @return
	 */
	public byte[] CreateSetStopedIntervalPacket(long timestamp,long terminalID, int stopedInterval) {
		// TODO Auto-generated method stub
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		byte[] byteArrayHeartPongInterval=CByte.IntToByteArray(stopedInterval);
		byte[] packet=new byte[20];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = (byte) 0x96;
        packet[3] = 0x41;
        packet[4] = 0;
        packet[5] = 0X0E;
        packet[6] = byteArrayTerminalID[4];
        packet[7] = byteArrayTerminalID[5];
        packet[8] = byteArrayTerminalID[6];
        packet[9] = byteArrayTerminalID[7];
        
        byte[] timestampByteArray=com.cocopass.helper.CByte.longToBytes(timestamp);
        
        packet[10] =  timestampByteArray[2];
        packet[11] =  timestampByteArray[3];
        packet[12] =  timestampByteArray[4];
	    packet[13] =  timestampByteArray[5];
	    packet[14] =  timestampByteArray[6];
	    packet[15] =  timestampByteArray[7];

	    packet[16] = byteArrayHeartPongInterval[2];
        packet[17] = byteArrayHeartPongInterval[3];
       
	    
        packet[18] = CreateCheckValue(packet);
        packet[19] = 0x0D;
		return packet;
	}
	
	/**
	 * 创建重启包
	 * @param timestamp
	 * @param terminalID
	 * @return
	 */
	public byte[] CreateReBootTerminalPacket(long timestamp,long terminalID) {
		// TODO Auto-generated method stub

		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);

		byte[] packet=new byte[18];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = (byte) 0x96;
        packet[3] = 0x50;
        packet[4] = 0;
        packet[5] = 18-6;
        packet[6] = byteArrayTerminalID[4];
        packet[7] = byteArrayTerminalID[5];
        packet[8] = byteArrayTerminalID[6];
        packet[9] = byteArrayTerminalID[7];
        
        byte[] timestampByteArray=com.cocopass.helper.CByte.longToBytes(timestamp);
        
        packet[10] =  timestampByteArray[2];
        packet[11] =  timestampByteArray[3];
        packet[12] =  timestampByteArray[4];
	    packet[13] =  timestampByteArray[5];
	    packet[14] =  timestampByteArray[6];
	    packet[15] =  timestampByteArray[7];
	    
        packet[16] = CreateCheckValue(packet);
        packet[17] = 0x0D;
		return packet;
	}
	
	/**
	 * 创建设置APN包
	 * @param timestamp
	 * @param terminalID
	 * @param apnName
	 * @param apnUserName
	 * @param apnPassword
	 * @return
	 */
	byte[] CreateSetTerminalAPNPacket(long timestamp,long terminalID, String apnName, String apnUserName,
			String apnPassword){
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		int apnNameLen=apnName.length();
		int apnUserNameLen=apnUserName.length();
		int apnPasswordLen=apnPassword.length();
		int valueLegth=3+apnNameLen+apnUserNameLen+apnPasswordLen;
		byte[] packet=new byte[18+valueLegth];
		
		packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = (byte) 0x96;
        packet[3] = 0x48;
        packet[4] = 0;
        packet[5] = (byte) (18+valueLegth-6);
        packet[6] = byteArrayTerminalID[4];
        packet[7] = byteArrayTerminalID[5];
        packet[8] = byteArrayTerminalID[6];
        packet[9] = byteArrayTerminalID[7];
        
        byte[] timestampByteArray=com.cocopass.helper.CByte.longToBytes(timestamp);
        
        packet[10] =  timestampByteArray[2];
        packet[11] =  timestampByteArray[3];
        packet[12] =  timestampByteArray[4];
	    packet[13] =  timestampByteArray[5];
	    packet[14] =  timestampByteArray[6];
	    packet[15] =  timestampByteArray[7];

	    packet[16] = (byte) apnNameLen;
	    char[] charArrayAPNName=apnName.toCharArray();
	    for(int i=0;i<apnNameLen;i++)
	    {
	    	packet[16+1+i] = (byte) charArrayAPNName[i]; //17
	    }
	    
	    packet[16+apnNameLen+1] = (byte) apnUserNameLen; //18
	    char[] charArrayUserName=apnUserName.toCharArray();
	    for(int i=0;i<apnUserNameLen;i++)
	    {
	    	packet[16+1+apnNameLen+1+i] = (byte) charArrayUserName[i]; //19
	    }
	    
	    packet[16+apnNameLen+1+apnUserNameLen+1] = (byte) apnPasswordLen; //20
	    char[] charArrayAPNPassword=apnPassword.toCharArray();
	    for(int i=0;i<apnPasswordLen;i++)
	    {
	    	packet[16+apnNameLen+1+apnUserNameLen+1+1+i] = (byte) charArrayAPNPassword[i]; //21
	    }
      
        packet[18+valueLegth-2] = CreateCheckValue(packet);
        packet[18+valueLegth-1] = 0x0D;
		
		return packet;
	}
	
	/**
	 * 创建设置鉴权域名端口包
	 * @param timestamp
	 * @param terminalID
	 * @param host
	 * @param port
	 * @return
	 */
	byte[] CreateSetTerminalAuthHostAndPortPacket(long timestamp,long terminalID, String host, int port){
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		int hostLen=host.length();
		int valueLegth=1+hostLen+2;
		byte[] packet=new byte[18+valueLegth];
		
		packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = (byte) 0x96;
        packet[3] = 0x06;
        packet[4] = 0;
        packet[5] = (byte) (18+valueLegth-6);
        packet[6] = byteArrayTerminalID[4];
        packet[7] = byteArrayTerminalID[5];
        packet[8] = byteArrayTerminalID[6];
        packet[9] = byteArrayTerminalID[7];
        
        byte[] timestampByteArray=com.cocopass.helper.CByte.longToBytes(timestamp);
        
        packet[10] =  timestampByteArray[2];
        packet[11] =  timestampByteArray[3];
        packet[12] =  timestampByteArray[4];
	    packet[13] =  timestampByteArray[5];
	    packet[14] =  timestampByteArray[6];
	    packet[15] =  timestampByteArray[7];

	    packet[16] = (byte) hostLen;
	    char[] charArrayHost=host.toCharArray();
	    for(int i=0;i<hostLen;i++)
	    {
	    	packet[16+1+i] = (byte) charArrayHost[i]; //17
	    }
	    byte[] portArrayByte=CByte.IntToByteArray(port);
	    packet[16+hostLen+1] = portArrayByte[2];
	    packet[16+hostLen+2]=  portArrayByte[3];
	    
        packet[18+valueLegth-2] = CreateCheckValue(packet);
        packet[18+valueLegth-1] = 0x0D;
		
		return packet;
	}
	
	/**
	 * 创建停车回传间隔包
	 * @param timestamp
	 * @param terminalID
	 * @param stopedInterval
	 * @return
	 */
	public byte[] CreateSetShakeSensitivityPacket(long timestamp,long terminalID, int level,int rate) {
		// TODO Auto-generated method stub
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		byte[] byteArrayLevel=CByte.IntToByteArray(level);
		byte[] byteArrayRate=CByte.IntToByteArray(rate);
		byte[] packet=new byte[20];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = (byte) 0x96;
        packet[3] = 0x47;
        packet[4] = 0;
        packet[5] = 0X0E;
        packet[6] = byteArrayTerminalID[4];
        packet[7] = byteArrayTerminalID[5];
        packet[8] = byteArrayTerminalID[6];
        packet[9] = byteArrayTerminalID[7];
        
        byte[] timestampByteArray=com.cocopass.helper.CByte.longToBytes(timestamp);
        
        packet[10] =  timestampByteArray[2];
        packet[11] =  timestampByteArray[3];
        packet[12] =  timestampByteArray[4];
	    packet[13] =  timestampByteArray[5];
	    packet[14] =  timestampByteArray[6];
	    packet[15] =  timestampByteArray[7];

	    packet[16] = byteArrayRate[3];
        packet[17] = byteArrayLevel[3];
       
	    
        packet[18] = CreateCheckValue(packet);
        packet[19] = 0x0D;
		return packet;
	}
	

	/**
	 * 创建控制MOS电源开关包
	 * @param timestamp
	 * @param terminalID
	 * @param onORoff
	 * @return
	 */
	@Deprecated
	public byte[] CreateSwitchEBikePowerPacket(long timestamp,long terminalID, byte onORoff) {
		// TODO Auto-generated method stub
		 
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		byte[] packet=new byte[18];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = (byte) 0x96;
        packet[3] = 0x58;
        packet[4] = 0;
        packet[5] = 0X0C;
        packet[6] = byteArrayTerminalID[4];
        packet[7] = byteArrayTerminalID[5];
        packet[8] = byteArrayTerminalID[6];
        packet[9] = byteArrayTerminalID[7];
        
        byte[] timestampByteArray=com.cocopass.helper.CByte.longToBytes(timestamp);
        
        packet[10] =  timestampByteArray[2];
        packet[11] =  timestampByteArray[3];
        packet[12] =  timestampByteArray[4];
	    packet[13] =  timestampByteArray[5];
	    packet[14] =  timestampByteArray[6];
	    packet[15] =  timestampByteArray[7];
        packet[16] =  onORoff;
        packet[17] = CreateCheckValue(packet);
        packet[18] = 0x0D;
		return packet;
	}

	/**
	 * 发送控MOS开关包
	 */
	@Deprecated
	@Override
	public com.cocopass.iot.model.DownMessageResult SendSwitchEBikePowerPacket( long timestamp,long terminalID, byte onORoff,String url) {
		// TODO Auto-generated method stub
		
		byte[] packet=CreateSwitchEBikePowerPacket(timestamp,terminalID,onORoff);
		com.cocopass.iot.model.DownMessageResult result=SendPacket(terminalID,packet,url);
		return result;

	}

	
	/**
	 * 创建设置终端ID包
	 * @param timestamp
	 * @param terminalID
	 * @param newTerminalID
	 * @return
	 */
	public byte[] CreateSetAlarmMobileNOPacket(long timestamp,long terminalID, long mobileNO) {
		// TODO Auto-generated method stub
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		//byte[] byteArrayNewTerminalID=CByte.longToBytes(mobileNO);
		String strMobileNO=CString.FormatFixLong(mobileNO, 12);
		//byte[] arrayMobileNO=CByte.hexStringToBytes(strMobileNO);
		
		byte[] packet=new byte[24];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = (byte) 0x96;
        packet[3] = 0x46;
        packet[4] = 0;
        packet[5] = 0X12;
        packet[6] = byteArrayTerminalID[4];
        packet[7] = byteArrayTerminalID[5];
        packet[8] = byteArrayTerminalID[6];
        packet[9] = byteArrayTerminalID[7];
        
        byte[] timestampByteArray=com.cocopass.helper.CByte.longToBytes(timestamp);
        
        packet[10] =  timestampByteArray[2];
        packet[11] =  timestampByteArray[3];
        packet[12] =  timestampByteArray[4];
	    packet[13] =  timestampByteArray[5];
	    packet[14] =  timestampByteArray[6];
	    packet[15] =  timestampByteArray[7];

	    packet[16] = (byte)Integer.parseInt(strMobileNO.substring(0, 2));
        packet[17] = (byte)Integer.parseInt(strMobileNO.substring(2, 4));
        packet[18] = (byte)Integer.parseInt(strMobileNO.substring(4, 6));
        packet[19] = (byte)Integer.parseInt(strMobileNO.substring(6, 8));
        packet[20] = (byte)Integer.parseInt(strMobileNO.substring(8, 10));
        packet[21] = (byte)Integer.parseInt(strMobileNO.substring(10, 12));
	    
        packet[22] = CreateCheckValue(packet);
        packet[23] = 0x0D;
		return packet;
	}
	
	/**
	 * 创建设置终端里程
	 * @param timestamp
	 * @param terminalID
	 * @param TotalMileage 单位KM
	 * @return
	 */
	public byte[] CreateSetTotalMileagePacket(long timestamp,long terminalID, float totalMileage){
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		int intTotalMileage=(int) (totalMileage*10);
		byte[] arrayTotalMileage=CByte.IntToByteArray(intTotalMileage);

		byte[] packet=new byte[21];	
		packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = (byte) 0x96;
        packet[3] = 0x54;
        packet[4] = 0;
        packet[5] = 0x0F;
        packet[6] = byteArrayTerminalID[4];
        packet[7] = byteArrayTerminalID[5];
        packet[8] = byteArrayTerminalID[6];
        packet[9] = byteArrayTerminalID[7];
        
        byte[] timestampByteArray=com.cocopass.helper.CByte.longToBytes(timestamp);
        
        packet[10] =  timestampByteArray[2];
        packet[11] =  timestampByteArray[3];
        packet[12] =  timestampByteArray[4];
	    packet[13] =  timestampByteArray[5];
	    packet[14] =  timestampByteArray[6];
	    packet[15] =  timestampByteArray[7];

	    packet[16] = arrayTotalMileage[1];
	    packet[17]=  arrayTotalMileage[2];
	    packet[18]=  arrayTotalMileage[3];
	    
        packet[19] = CreateCheckValue(packet);
        packet[20] = 0x0D;
		
		return packet;
	}
	
	
 

	/**
	 * 发送设置终端ID包
	 */
	@Override
	public DownMessageResult SendSetTerminalIDPacket(long timestamp,long terminalID, long newTerminalID, String url) {
		byte[] packet=CreateSetTerminalIDPacket(timestamp,terminalID,newTerminalID);
		com.cocopass.iot.model.DownMessageResult result=SendPacket(terminalID,packet,url);
		return result;
	}

	/**
	 * 发送指令包基础方法
	 */
	@Override
	public com.cocopass.iot.model.DownMessageResult SendPacket(long terminalID,byte[] packet,String url)
	{
		com.cocopass.iot.model.DownMessageResult result=new com.cocopass.iot.model.DownMessageResult();
		String hexPacket=CByte.BytesToHexString(packet);
		
		
		com.cocopass.iot.model.DownMessage messgae=new com.cocopass.iot.model.DownMessage();
		messgae.SetHexPacket(hexPacket);
		messgae.SetPacket(packet);
		messgae.SetTerminalID(terminalID);/////////////
		
		String postData=gson.toJson(messgae);
		try {
			String response=CHttp.GetResponseBody(url, postData, null);
			//System.out.println(response);
			result=gson.fromJson(response, com.cocopass.iot.model.DownMessageResult.class);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.SetStatus(5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.SetStatus(5);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.SetStatus(5);
		}
		return result;
	}

	@Override
	public DownMessageResult SendSwitchEBikeTailLightPowerPacket(long timestamp,long terminalID,byte onORoff, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DownMessageResult SendSwitchEBikeTailLightFlashPacket(long timestamp,long terminalID, byte onORoff, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DownMessageResult SendSwitchEBikeSaddleLockPacket(long timestamp,long terminalID, byte openOrLock, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DownMessageResult SendResetEBikePacket(long timestamp,long terminalID, String url) {
		// TODO Auto-generated method stub
		return null;
	}
 

	@Override
	public DownMessageResult SendSwitchPileLightPacket(long timestamp,long terminalID, int port, int lightNumber,byte onORoff, String url) {
//		 byte[] switchEBikePileLightPacket=CreateSwitchEBikePileLightPacket(timestamp,terminalID,port,lightNumber,onORoff);
//		 byte[] packet=AddGeneralShellToPacket(timestamp,switchEBikePileLightPacket,terminalID);
//		 com.cocopass.iot.model.DownMessageResult result=SendPacket(terminalID,packet,url);
//		 return result;
		return null;
	}

	@Override
	public byte[] CreateResponsePacket() {
		// TODO Auto-generated method stub
		byte[] packet=CreateNormalResponsePacket();
		return packet;
	}

	@Override
	public DownMessageResult SendSetTerminalAPNPacket(long timestamp,long terminalID, String apnName, String apnUserName,
			String apnPassword, String url) {
		byte[] packet=CreateSetTerminalAPNPacket(timestamp,terminalID,apnName,apnUserName,apnPassword);
		com.cocopass.iot.model.DownMessageResult result=SendPacket(terminalID,packet,url);
		return result;
	}

	@Override
	public DownMessageResult SendReBootTerminalPacket(long timestamp,long terminalID, String url) {
		// TODO Auto-generated method stub
		byte[] packet=CreateReBootTerminalPacket(timestamp,terminalID);
		com.cocopass.iot.model.DownMessageResult result=SendPacket(terminalID,packet,url);
		return result;
	}

	@Override
	public DownMessageResult SendSetTerminalAuthHostAndPort(long timestamp,long terminalID, String host, int port, String url) {
		// TODO Auto-generated method stub
		
		byte[] packet=CreateSetTerminalAuthHostAndPortPacket(timestamp,terminalID,host,port);
		com.cocopass.iot.model.DownMessageResult result=SendPacket(terminalID,packet,url);
		return result;
	}

	@Override
	public DownMessageResult SendSwitchEBikeTurnLightFlashPacket(long timestamp,long terminalID, byte onORoff, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DownMessageResult SendSetEBikeMaxSpeedPacket(long timestamp,long terminalID, int speed, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DownMessageResult SendSwitchEBikePileLockPacket(long timestamp,long terminalID, int port, byte openOrLock, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DownMessageResult SendSetHeartPongIntervalPacket(long timestamp, long terminalID, int heartPonginterval,
			String url) {
		byte[] packet=CreateSetHeartPongIntervalPacket(timestamp,terminalID,heartPonginterval);
		com.cocopass.iot.model.DownMessageResult result=SendPacket(terminalID,packet,url);
		return result;
	}

	@Override
	public DownMessageResult SendSetStopedIntervalPacket(long timestamp, long terminalID, int stopedInterval,
			String url) {
		byte[] packet=CreateSetStopedIntervalPacket(timestamp,terminalID,stopedInterval);
		com.cocopass.iot.model.DownMessageResult result=SendPacket(terminalID,packet,url);
		return result;
	}

	@Override
	public DownMessageResult SendSetRunningIntervalPacket(long timestamp, long terminalID, int runningInterval,
			String url) {
		byte[] packet=CreateSetRunningIntervalPacket(timestamp,terminalID,runningInterval);
		com.cocopass.iot.model.DownMessageResult result=SendPacket(terminalID,packet,url);
		return result;
	}

	@Override
	public DownMessageResult SendSetShakeSensitivityPacket(long timestamp, long terminalID, int level, int rate,
			String url) {
		byte[] packet=CreateSetShakeSensitivityPacket(timestamp,terminalID,level,rate);
		com.cocopass.iot.model.DownMessageResult result=SendPacket(terminalID,packet,url);
		return result;
	}

	@Override
	public DownMessageResult SendSetAlarmMobileNOPacket(long timestamp, long terminalID, long mobileNO, String url) {
		// TODO Auto-generated method stub
		byte[] packet=CreateSetAlarmMobileNOPacket(timestamp,terminalID,mobileNO);
		com.cocopass.iot.model.DownMessageResult result=SendPacket(terminalID,packet,url);
		return result;
	}

	@Override
	public DownMessageResult SendSetTotalMileagePacket(long timestamp, long terminalID, float totalMileage, String url) {
		// TODO Auto-generated method stub
		
		byte[] packet=CreateSetTotalMileagePacket(timestamp,terminalID,totalMileage);
		com.cocopass.iot.model.DownMessageResult result=SendPacket(terminalID,packet,url);
		return result;
	}

	@Override
	public DownMessageResult SendSwitchPileChargerPacket(long timestamp, long terminalID, long sessionID,int batteryVoltage,
			int batteryCapacity, int port, byte onOroff, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DownMessageResult SendSetStationPileNumPacket(long timestamp, long terminalID, int pileNumber, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DownMessageResult SendReSetPilePortPacket(long timestamp, long terminalID, int port, String url) {
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
