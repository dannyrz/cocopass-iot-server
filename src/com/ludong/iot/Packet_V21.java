package com.ludong.iot;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cocopass.helper.CByte;
import com.cocopass.helper.CDate;
import com.ludong.model.Alarm;
import com.ludong.model.ControllerInfo;
import com.cocopass.iot.model.DecodeDataRouter;
import com.cocopass.iot.model.DownMessageResult;

public class Packet_V21 extends Packet_V20 {

	// int childSignaling=0;
	// float childVersion=0;
	// int childPacketHeader=0;
	public Packet_V21(byte[] buffer) {
		super(buffer);
		if (buffer != null) {

		}
	}

	/**
	 * 创建询问网络是否正常应答包
	 */
	public byte[] CreateNetNormalPacket(long timestamp) {
		byte[] childPacket = new byte[12];
		childPacket[0] = 0x4a;
		childPacket[1] = 0x10;
		childPacket[2] = 0x00;
		childPacket[3] = 0x06;
		childPacket[4] = 0x0C;
		childPacket[5] = 0x00;
		childPacket[6] = 0x00;
		childPacket[7] = 0x00;
		childPacket[8] = 0x00;
		childPacket[9] = 0x00;
		childPacket[10] = CreateCheckValue(childPacket);
		childPacket[11] = 0x0d;
		byte[] packet = AddLuYuanHeartShellToPacket(timestamp, childPacket, terminalID);
		return packet;
	}

	@Override
	public byte[] CreateResponsePacket() {
		byte[] packet = null;
		switch (signaling) {
		case 0x65:
			int childDataLength = length - 18;
			byte[] data = new byte[childDataLength];
			System.arraycopy(buffer, 16, data, 0, childDataLength);
			int childSignaling = CByte.byteToUnit(buffer[18]);
			int childVersion = buffer[17];
			int childPacketHeader = buffer[16];
			if (childPacketHeader == 0x4B && childVersion == 0x10 && childSignaling == 0x11) {
				int childCHeckValue = CreateCheckValue(data);
				if (data[childDataLength - 2] == childCHeckValue) {
					long timestamp = new Date().getTime() / 1000;
					packet = CreateNetNormalPacket(timestamp);
				}
			}
			// 临时处理请求锁车

			// else if(buffer[16]==0x4B&&buffer[17]==0x10&&buffer[18]==0x32)
			// {
			// byte[]
			// switchEBikePileLockPacket=CreateSwitchEBikePileLockPacket(0,terminalID,1,(byte)1);
			// packet=AddGeneralShellToPacket(0,switchEBikePileLockPacket,terminalID);
			// }

			else {
				packet = CreateNormalResponsePacket();
			}
			break;
		default:
			packet = CreateNormalResponsePacket();
			break;
		}
		return packet;
	}

	/**
	 * 解码控制器信息
	 */
	@Override
	public ControllerInfo GetControllerInfo() {
		ControllerInfo ci = new ControllerInfo();
		float turnHandleScale = CByte.byteToUnit(buffer[19]) * 0.4f;
		ci.SetTurnHandleScale(turnHandleScale);
		float grade = buffer[21] * 1.4f;
		ci.SetGrade(grade);
		float speed = CByte.ByteArrayToInt(new byte[] { 0, 0, buffer[25], buffer[24] }) * 0.1f;
		ci.SetSpeed(speed);
		float rideCurrent = CByte.ByteArrayToInt(new byte[] { 0, 0, buffer[27], buffer[26] }) * 0.2f;
		ci.SetRideCurrent(rideCurrent);
		float rideCurrentScale = CByte.byteToUnit(buffer[28]) * 0.4f;
		ci.SetRideCurrentScale(rideCurrentScale);
		float temperature = CByte.byteToUnit(buffer[29]) - 40f;
		ci.SetTemperature(temperature);
		float rideVoltage = CByte.ByteArrayToInt(new byte[] { 0, 0, buffer[31], buffer[30] }) * 0.01f;
		ci.SetRideVoltage(rideVoltage);
		int vin = CByte.ByteArrayToInt(new byte[] { buffer[35], buffer[34], buffer[33], buffer[32] });
		ci.SetVIN(vin);
		float electrisityPower = CByte.byteToUnit(buffer[36]) * 1.0f;
		ci.SetElectrisityPower(electrisityPower);

		// 整车状态信号1
		boolean[] ebikeStatus1 = CByte.ToBooleanArray(buffer[20]);

		boolean isInRunMode = ebikeStatus1[7];
		ci.setIsInRunMode(isInRunMode);
		boolean isDefence = ebikeStatus1[6];
		ci.setIsDefence(isDefence);
		boolean isNightLightOn = ebikeStatus1[5];
		ci.setIsNightLightOn(isNightLightOn);
		boolean isTurnRightLightOn = ebikeStatus1[4];
		ci.SetIsTurnRightLightOn(isTurnRightLightOn);
		boolean isTurnLeftLightOn = ebikeStatus1[3];
		ci.SetIsTurnLeftLightOn(isTurnLeftLightOn);
		boolean isBrakeHandle = ebikeStatus1[2];
		ci.SetIsBrakeHandle(isBrakeHandle);
		int transmissionMode = 0;
		if (ebikeStatus1[2] == false && ebikeStatus1[1] == false)
			transmissionMode = 0;
		else if (ebikeStatus1[2] == false && ebikeStatus1[1] == true)
			transmissionMode = 1;
		else if (ebikeStatus1[2] == true && ebikeStatus1[1] == false)
			transmissionMode = 2;
		else if (ebikeStatus1[2] == true && ebikeStatus1[1] == true)
			transmissionMode = 3;
		ci.setTransmissionMode(transmissionMode);

		// 整车状态信号2
		boolean[] ebikeStatus2 = CByte.ToBooleanArray(buffer[22]);
		boolean isCurveLackenSpeed = ebikeStatus2[7];
		ci.setIsCurveLackenSpeed(isCurveLackenSpeed);
		boolean isOverNormalSpeed = ebikeStatus2[6];
		ci.setIsOverNormalSpeed(isOverNormalSpeed);
		int electricityQuantityScale = 0;
		if (ebikeStatus2[4] == false && ebikeStatus2[3] == false && ebikeStatus2[2] == false)
			electricityQuantityScale = 0;
		else if (ebikeStatus2[4] == false && ebikeStatus2[3] == false && ebikeStatus2[2] == true)
			electricityQuantityScale = 1;
		else if (ebikeStatus2[4] == false && ebikeStatus2[3] == true && ebikeStatus2[2] == false)
			electricityQuantityScale = 2;
		else if (ebikeStatus2[4] == false && ebikeStatus2[3] == true && ebikeStatus2[2] == true)
			electricityQuantityScale = 3;
		else if (ebikeStatus2[4] == true && ebikeStatus2[3] == false && ebikeStatus2[2] == false)
			electricityQuantityScale = 4;
		else if (ebikeStatus2[4] == true && ebikeStatus2[3] == false && ebikeStatus2[2] == true)
			electricityQuantityScale = 5;
		else if (ebikeStatus2[4] == true && ebikeStatus2[3] == true && ebikeStatus2[2] == false)
			electricityQuantityScale = 6;
		else if (ebikeStatus2[4] == true && ebikeStatus2[3] == true && ebikeStatus2[2] == true)
			electricityQuantityScale = 7;
		ci.setElectricityQuantityScale(electricityQuantityScale);
		int cyclingEvent = CByte.byteToUnit(buffer[23]);
		ci.setCyclingEvent(cyclingEvent);
		return ci;
	}

	String GetActionIndexCode() {
		String indexActionCode = "";
		// buffer[23]==0x10&&
		String hexSignaling = "85";
		String hexVersion = "97";
		String hexChildPacketHeader = "5A";
		String hexChildPacketVersion = CByte.ByteToHexString(buffer[23]);// 子包版本
		String hexChildPacketSignaling = CByte.ByteToHexString(buffer[24]);// 子包信令
		String hexChildPacketFunCode = CByte.ByteToHexString(buffer[26]);// 子包功能码
		indexActionCode = hexSignaling + hexVersion + hexChildPacketHeader + hexChildPacketVersion
				+ hexChildPacketSignaling + hexChildPacketFunCode;
		return indexActionCode;
	}

	/**
	 * 解码绿源芯数据
	 * 
	 * @return
	 */
	public List<Object> GetLuYuanHeartInfo() {
		List<Object> list = new ArrayList<Object>();
		com.ludong.model.ControllerInfo controllerInfo = GetControllerInfo();
		// DecodeDataRouter ddr=new DecodeDataRouter();
		// ddr.SetDataObj(controllerInfo);
		// ddr.SetExchangeName("ECJsonControllerInfo");
		list.add(controllerInfo);
		// 判断是否有报警
		int cyclingEvent = controllerInfo.getCyclingEvent();
		if (cyclingEvent == 1 || cyclingEvent == 2) {
			int alarmNO = 20 + cyclingEvent;
			Alarm alarm = new Alarm();

			alarm.SetAlarmNO(alarmNO);
			alarm.SetAlarmParam(0);
			alarm.SetIsRealTime(isRealTime);
			alarm.SetSamplingTime(samplingTime);
			alarm.SetTerminalID(terminalID);
			alarm.SetWrittenIntoSystemTime(writtenIntoSystemTime);
			alarm.SetVersion(version);

			// ddr=new DecodeDataRouter();
			// ddr.SetDataObj(alarm);
			// ddr.SetExchangeName("ECJsonAlarm");
			list.add(alarm);
		}
		return list;
	}

	/**
	 * 获取TCP通信版本的充电站数据V2.0
	 * 
	 * @return
	 */
	public List<Object> GetV20CharingStationInfo() {
		List<Object> list = new ArrayList<Object>();
		// 29299765002c252239d5001466010036
		// 4b1060
		// 1a 数据长度
		// 0A 端口数量
		// 03 00 00 00 00 00 00 00 00 00 状态
		// 00 00 00 00 57 61 f7 ca 时间戳
		// 02 48 充电时长
		// 00 00 充电电压
		// 01 充电电流
		// 00 00 功耗
		// 68
		// 0D
		// 7d0D

		List<com.ludong.model.PileInfo> listPileInfo = new ArrayList<com.ludong.model.PileInfo>();
		int portNum = buffer[20];
		int k = 0;
		int index = 20 + portNum + 1;
		for (int i = 0; i < portNum; i++) {
			com.ludong.model.PileInfo pinfo = new com.ludong.model.PileInfo();
			int chargingPortStatus = buffer[20 + i + 1];
			int pilePort = i + 1;
			pinfo.setPort(pilePort);
			pinfo.setChargingPortStatus(chargingPortStatus);
			pinfo.setStationTerminalID(terminalID);
			pinfo.setSamplingTime(samplingTime);
			com.ludong.model.BatteryInfo bi = null;
			if (chargingPortStatus > 0) {
				bi = new com.ludong.model.BatteryInfo();
				long sessionID = CByte.bytesToLong(new byte[] { buffer[index + 15 * k], buffer[index + 15 * k + 1],
						buffer[index + 15 * k + 2], buffer[index + 15 * k + 3], buffer[index + 15 * k + 4],
						buffer[index + 15 * k + 5], buffer[index + 15 * k + 6], buffer[index + 15 * k + 7] });
				int chargingDuration = CByte
						.ByteArrayToInt(new byte[] { buffer[index + 15 * k + 9], buffer[index + 15 * k + 8], 0, 0 });
				float rechargeVoltage = ((float) CByte
						.ByteArrayToInt(new byte[] { buffer[index + 15 * k + 11], buffer[index + 15 * k + 10], 0, 0 }))
						/ 10;
				float rechargeCurrent = ((float) ((int) buffer[index + 15 * k + 12])) / 10;
				float chargingPower = CByte
						.ByteArrayToInt(new byte[] { buffer[index + 15 * k + 14], buffer[index + 15 * k + 13], 0, 0, });
				bi.setChargingPower(chargingPower);
				bi.SetRechargeCurrent(rechargeCurrent);
				bi.SetRechargeVoltage(rechargeVoltage);
				bi.setChargingDuration(chargingDuration);
				bi.setSessionID(sessionID);
				bi.SetTerminalID(terminalID);
				bi.setPilePort(pilePort);
				bi.setChargingPortStatus(chargingPortStatus);
				bi.SetSamplingTime(samplingTime);

				list.add(bi);

				k += 1;
			}

			pinfo.setBatteryData(bi);
			listPileInfo.add(pinfo);
		}

		com.ludong.model.Station station = new com.ludong.model.Station();

		station.setTerminalID(terminalID);
		station.setPortNum(portNum);
		station.setPileData(listPileInfo);

		list.add(station);

		return list;

	}

	/**
	 * 获取TCP协议的充电站版本数据V2.1
	 * 
	 * @return
	 */
	public List<Object> GetV21CharingStationInfo() {
		// 29 29 97 65 00 36 1e 65 fb 89 00 14 74 91 42 60 //16
		// 4b 20 60 //3
		// 24 //1
		// 0A //1
		// 15 00 16 01 17 00 18 00 19 00 1a 00 1b 00 1c 00 1d 00 1e 00 //20
		// 00 00 00 00 57 e9 d8 39 00 1d 08 98 01 00 00 //15
		// fc 0D //2
		// 13 0D //2

		List<Object> list = new ArrayList<Object>();
		List<com.ludong.model.PileInfo> listPileInfo = new ArrayList<com.ludong.model.PileInfo>();
		int portNum = buffer[20];
		int k = 0;
		int index = 20 + portNum * 2 + 1;// 电池数据索引
		for (int i = 0; i < portNum; i++) {
			com.ludong.model.PileInfo pinfo = new com.ludong.model.PileInfo();

			int pilePort = buffer[20 + i * 2 + 1];
			int chargingPortStatus = buffer[20 + i * 2 + 2];

			// System.out.println("["+20 + i + 2+":"+chargingPortStatus+"]");

			pinfo.setPort(pilePort);
			pinfo.setChargingPortStatus(chargingPortStatus);
			pinfo.setStationTerminalID(terminalID);
			pinfo.setSamplingTime(samplingTime);
			com.ludong.model.BatteryInfo bi = null;
			if (chargingPortStatus > 0) {
				bi = new com.ludong.model.BatteryInfo();
				long sessionID = CByte.bytesToLong(new byte[] { buffer[index + 15 * k], buffer[index + 15 * k + 1],
						buffer[index + 15 * k + 2], buffer[index + 15 * k + 3], buffer[index + 15 * k + 4],
						buffer[index + 15 * k + 5], buffer[index + 15 * k + 6], buffer[index + 15 * k + 7] });
				int chargingDuration = CByte
						.ByteArrayToInt(new byte[] { buffer[index + 15 * k + 9], buffer[index + 15 * k + 8], 0, 0 });
				float rechargeVoltage = ((float) CByte
						.ByteArrayToInt(new byte[] { buffer[index + 15 * k + 11], buffer[index + 15 * k + 10], 0, 0 }))
						/ 10;

				float rechargeCurrent = ((float) ((int) buffer[index + 15 * k + 12])) / 10;
				float chargingPower = CByte
						.ByteArrayToInt(new byte[] { buffer[index + 15 * k + 14], buffer[index + 15 * k + 13], 0, 0, });
				bi.setChargingPower(chargingPower);
				bi.SetRechargeCurrent(rechargeCurrent);
				bi.SetRechargeVoltage(rechargeVoltage);
				bi.setChargingDuration(chargingDuration);
				bi.setSessionID(sessionID);
				bi.SetTerminalID(terminalID);
				bi.setPilePort(pilePort);
				bi.setChargingPortStatus(chargingPortStatus);
				bi.SetSamplingTime(samplingTime);

				list.add(bi);

				k += 1;
			}

			pinfo.setBatteryData(bi);
			listPileInfo.add(pinfo);
		}

		com.ludong.model.Station station = new com.ludong.model.Station();

		station.setTerminalID(terminalID);
		station.setPortNum(portNum);
		station.setPileData(listPileInfo);

		list.add(station);
		return list;
	}

	/**
	 * 获取带锁车机构的充电站数据
	 * 
	 * @return
	 */
	public List<Object> GetWithLockCharingStationInfo() {
		List<Object> list = new ArrayList<Object>();
		// 网点车桩数据
		// com.cocopass.iot.model.StationInfo stationInfo=new
		// com.cocopass.iot.model.StationInfo();
		int childDataContentLength = buffer[25];
		int portNum = buffer[26];
		// stationInfo.setTerminalID(terminalID);
		// stationInfo.setPortNum(portNum);
		List<com.ludong.model.PileInfo> listPileInfo = new ArrayList<com.ludong.model.PileInfo>();

		for (int i = 0; i < portNum; i++) {
			boolean[] pileStatus = CByte.ToBooleanArray(buffer[26 + i + 1]);
			com.ludong.model.PileInfo pileInfo = new com.ludong.model.PileInfo();
			pileInfo.setStationTerminalID(terminalID);
			pileInfo.setHaveEBike(pileStatus[7]);
			pileInfo.setLocked(pileStatus[6]);
			pileInfo.setPileFault(pileStatus[5]);
			pileInfo.setLockFault(pileStatus[4]);
			pileInfo.setRFIDReaderMachineFault(pileStatus[3]);
			pileInfo.setStationTerminalID(terminalID);

			boolean[] rechargerStatus = CByte.ToBooleanArray(buffer[26 + i + 2]);
			pileInfo.setCharging(rechargerStatus[7]);
			pileInfo.setRechargerConstructionFault(rechargerStatus[6]);
			pileInfo.setRechargerFault(rechargerStatus[5]);

			int index = 26 + portNum + 1 + i * 16; // RFID索引
													// //26+2+i*14;
			long eBikeRFID = CByte.bytesToLong(new byte[] { 0, 0, 0, 0, buffer[index + 3], buffer[index + 4],
					buffer[index + 5], buffer[index + 6] });
			pileInfo.setEBikeRFID(eBikeRFID);

			listPileInfo.add(pileInfo);// 增加车桩状态数据

			if (eBikeRFID == 0)
				continue;
			com.ludong.model.BatteryInfo batteryInfo = new com.ludong.model.BatteryInfo();
			batteryInfo.setEBikeRFID(eBikeRFID);
			int chargingDuration = CByte.ByteArrayToInt(new byte[] { 0, 0, buffer[index + 7], buffer[index + 8] });
			batteryInfo.setChargingDuration(chargingDuration);
			// batteryInfo.setChargeTotalPower(0);
			float rechargeVoltage = CByte.ByteArrayToInt(new byte[] { 0, 0, buffer[index + 9], buffer[index + 10] })
					* 0.1f;
			batteryInfo.SetRechargeVoltage(rechargeVoltage);
			float rechargeCurrent = CByte.ByteArrayToInt(new byte[] { 0, 0, buffer[index + 11], buffer[index + 12] })
					* 0.1f;
			batteryInfo.SetRechargeCurrent(rechargeCurrent);
			float residualCapacity = CByte.ByteArrayToInt(new byte[] { 0, 0, buffer[index + 13], buffer[index + 14] })
					* 0.01f;
			batteryInfo.SetResidualCapacity(residualCapacity);
			float temperature = CByte.ByteArrayToInt(new byte[] { 0, 0, buffer[index + 15], buffer[index + 16] })
					* 0.1f;
			batteryInfo.SetTemperature(temperature);
			batteryInfo.SetSamplingTime(samplingTime);
			batteryInfo.SetVersion(version);
			float chargeNumeric = 0;
			float anticipateKilometre = com.ludong.bll.Battery.CalculateKilometreByEBikeRFID(eBikeRFID, chargeNumeric); // 计算预估里程
			batteryInfo.setAnticipateKilometre(anticipateKilometre);

			list.add(batteryInfo); // 增加单口充电数据

			pileInfo.setBatteryData(batteryInfo);

		}
		// stationInfo.setPileData(listPileInfo); //将充电桩详细数据附加到站点数据

		list.add(listPileInfo);

		return list;

	}

	/**
	 * 获取解码陀螺仪数据
	 * 
	 * @return
	 */
	public List<Object> GetGyroscopeInfo() {
		List<Object> list = new ArrayList<Object>();
		com.ludong.model.GyroscopeInfo gi = new com.ludong.model.GyroscopeInfo();

		boolean[] d26 = com.cocopass.helper.CByte.ToBooleanArray(buffer[20]);

		gi.setTerminalID(terminalID);
		gi.setSamplingTime(samplingTime);
		gi.setNC0(d26[0]);
		gi.setNC1(d26[1]);
		gi.setTurnRight(d26[2]);
		gi.setCalibration(d26[3]);
		gi.setStaticRollover(d26[4]);
		gi.setDynamicRollover(d26[5]);
		gi.setBumpyRoad(d26[6]);
		gi.setTurnLeft(d26[7]);

		boolean[] d27 = com.cocopass.helper.CByte.ToBooleanArray(buffer[21]);

		gi.setSlowBeforeTurn(d27[0]);
		gi.setDownhillAcceleration(d27[1]);
		gi.setDownhillSlowDown(d27[2]);
		gi.setBrakeHard(d27[3]);
		gi.setDownTurn(d27[4]);
		gi.setSRouteCycling(d27[5]);
		gi.setSlewHard(d27[6]);
		gi.setNightOffLight(d27[7]);

		// float
		// xAngle=Integer.parseInt((com.cocopass.helper.CByte.ByteToHexString(buffer[23])+com.cocopass.helper.CByte.ByteToHexString(buffer[22])),16);
		float xAngle = buffer[23] << 8 | buffer[22];
		xAngle = xAngle / 100;
		float yAngle = buffer[25] << 8 | buffer[24];
		// float
		// yAngle=Integer.parseInt((com.cocopass.helper.CByte.ByteToHexString(buffer[25])+com.cocopass.helper.CByte.ByteToHexString(buffer[24])),16);
		yAngle = yAngle / 100;
		// float
		// zAngle=Integer.parseInt((com.cocopass.helper.CByte.ByteToHexString(buffer[27])+com.cocopass.helper.CByte.ByteToHexString(buffer[26])),16);
		float zAngle = buffer[27] << 8 | buffer[26];
		zAngle = zAngle / 100;

		// float
		// xAcceleratedSpeed=Integer.parseInt((com.cocopass.helper.CByte.ByteToHexString(buffer[29])+com.cocopass.helper.CByte.ByteToHexString(buffer[28])),16);
		float xAcceleratedSpeed = buffer[29] << 8 | buffer[28];
		xAcceleratedSpeed = xAcceleratedSpeed / 100;
		float yAcceleratedSpeed = buffer[31] << 8 | buffer[30];
		// float
		// yAcceleratedSpeed=Integer.parseInt((com.cocopass.helper.CByte.ByteToHexString(buffer[31])+com.cocopass.helper.CByte.ByteToHexString(buffer[30])),16);
		yAcceleratedSpeed = yAcceleratedSpeed / 100;
		// float
		// zAcceleratedSpeed=Integer.parseInt((com.cocopass.helper.CByte.ByteToHexString(buffer[33])+com.cocopass.helper.CByte.ByteToHexString(buffer[32])),16);
		float zAcceleratedSpeed = buffer[33] << 8 | buffer[32];
		zAcceleratedSpeed = zAcceleratedSpeed / 100;

		gi.setXAngle(xAngle);
		gi.setXAcceleratedSpeed(xAcceleratedSpeed);

		gi.setYAngle(yAngle);
		gi.setYAcceleratedSpeed(yAcceleratedSpeed);

		gi.setZAngle(zAngle);
		gi.setZAcceleratedSpeed(zAcceleratedSpeed);

		list.add(gi);
		return list;
	}

	/**
	 * 获取蓝叮站点数据
	 * 
	 * @return
	 */
	public List<Object> GetBluetoothStationInfo() {
		List<Object> list = new ArrayList<Object>();
		com.ludong.model.BluetoothStation bs = new com.ludong.model.BluetoothStation();

		byte[] b = new byte[] { buffer[20], buffer[21], buffer[22], buffer[23], buffer[24], buffer[25], buffer[26],
				buffer[27], buffer[28], buffer[29], buffer[30], buffer[31], buffer[32], buffer[33], buffer[34],
				buffer[35] };// 字节数组
		String bluetoothID = "";
		try {
			bluetoothID = new String(b, "ascii");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 第二个参数指定编码方式

		int bikeEventID = buffer[36];
		int batteryLevel = buffer[37];
		String mac = com.cocopass.helper.CByte.bytesToHexString(
				new byte[] { buffer[38], buffer[39], buffer[40], buffer[41], buffer[42], buffer[43] });
		int rSSILatch = buffer[44];
		int rSSIL = buffer[45];
		int rSSIH = buffer[46];
		int broadcastFrequent = buffer[47];
		int broadcastPower = buffer[48];
		int samplBatteryLevelFrequent = com.cocopass.helper.CByte
				.ByteArrayToInt(new byte[] { buffer[50], buffer[49], 0, 0 });

		bs.setTerminalID(terminalID);
		bs.setID(bluetoothID);
		bs.setBikeEventID(bikeEventID);
		bs.setBatteryLevel(batteryLevel);
		bs.setMAC(mac);
		bs.setRSSILatch(rSSILatch);
		bs.setRSSIL(rSSIL);
		bs.setRSSIH(rSSIH);
		bs.setBroadcastFrequent(broadcastFrequent);
		bs.setBroadcastPower(broadcastPower);
		bs.setSamplBatteryLevelFrequent(samplBatteryLevelFrequent);
		bs.setServerSamplingTime(serverSamplingTime);
		bs.setSamplingTime(samplingTime);

		list.add(bs);
		return list;
	}

	/**
	 * 获取解码下行指令的应答
	 * 
	 * @return
	 */
	public List<Object> GetInstructionResponseInfo() {
		// 29299785002223c3460307e00113023a001453143508
		// 5a10000706000000000002490D bf5501d00D

		List<Object> list = new ArrayList<Object>();
		int childPacketHeader = CByte.byteToUnit(buffer[22]);
		switch (childPacketHeader) {
		
			case 0x5A:

			String indexActionCode = "";
			// buffer[23]==0x10&&
			String hexSignaling = "85";
			String hexVersion = "97";
			String hexChildPacketHeader = "5A";
			String hexChildPacketVersion = CByte.ByteToHexString(buffer[23]);// 子包版本
			String hexChildPacketSignaling = CByte.ByteToHexString(buffer[24]);// 子包信令
			String hexChildPacketFunCode = CByte.ByteToHexString(buffer[26]);// 子包功能码
			indexActionCode = hexSignaling + hexVersion + hexChildPacketHeader + hexChildPacketVersion
					+ hexChildPacketSignaling + hexChildPacketFunCode;
			// String[]
			// arrayAction=com.cocopass.helper.CRedis.get("Action:"+indexActionCode).split("|");
			// 注意分站模式下的问题以及是否有必要到缓存服务器读取？
			String actionCode = com.cocopass.helper.CRedis.getMapValue("Action:" + indexActionCode, "Code");
			String actionName = com.cocopass.helper.CRedis.getMapValue("Action:" + indexActionCode, "Name");
			// if(buffer[24]==0x00){
			// indexActionCode=CByte.ByteToHexString(buffer[24])+CByte.ByteToHexString(buffer[26]);
			// }
			// else
			// {
			// indexActionCode=CByte.ByteToHexString(buffer[24]);
			// }

			int childPacketLen = buffer[25];
			// int functionCode=buffer[26];
			int istatus = buffer[childPacketLen + 25];
			com.ludong.model.ActionLog al = new com.ludong.model.ActionLog();
			al.setActionName(actionName);
			al.SetActionCode(actionCode);
			// al.SetAddTime(CDate.GetNow());
			al.SetSamplingTime(samplingTime);///////////
			String serialNumber = String.valueOf(terminalID) + "-" + String.valueOf(downTimestamp);
			al.SetSerialNumber(serialNumber);

			int status = 7;
			if (istatus == 1)// 指令执行成功
				status = 3;
			else if (istatus == 0)// 指令执行失败
				status = 7;
			else if (istatus == 2)// 执行设备无应答
				status = 8;
			al.SetStatus(status);
			al.SetTerminalID(terminalID);
			al.SetTimestamp(downTimestamp);
			// DecodeDataRouter ddr=new DecodeDataRouter();
			// ddr.SetDataObj(al);
			// ddr.SetExchangeName("ECActionLog");
			list.add(al);

			break;
		}
		return list;
	}

	
	/**
	 * 解码BMS电池数据
	 * @return
	 */
	public List<Object>  GetBMSInfo(){
		List<Object> list = new ArrayList<Object>();
		com.ludong.model.BatteryInfo bi=new com.ludong.model.BatteryInfo();
		int healthCondition=buffer[20];
		float residualCapacity =(float) ((int)buffer[21] * 0.4);
		int typeID=buffer[22];
		float standardVoltage=buffer[23];
		float voltage=  (float)(com.cocopass.helper.CByte.ByteArrayToInt(new byte[]{ buffer[25],buffer[24],0,0})*0.01);
		int batteryItemNO=buffer[26];
		String voltageValueList="";
		int index=26;
		for(int i=0;i<batteryItemNO;i++){
			float itemVoltage=  (float)(com.cocopass.helper.CByte.ByteArrayToInt(new byte[]{ buffer[index+i*2+2],buffer[index+i*2+1],0,0})*0.01);
			voltageValueList+=" , "+itemVoltage;
		}
		voltageValueList=voltageValueList.substring(1);
		int indexK=index+batteryItemNO*2;
		float dischargeCurrent = (float)(buffer[indexK+1]*0.2);
		float rechargeCurrent = (float)(buffer[indexK+2]*0.1);
		int chargeNumeric=buffer[indexK+3];
		float temperature=buffer[indexK+4];
		int rechargeTimes=com.cocopass.helper.CByte.ByteArrayToInt(new byte[]{ buffer[indexK+6],buffer[indexK+5],0,0});
		int chargingDuration=com.cocopass.helper.CByte.ByteArrayToInt(new byte[]{ buffer[indexK+8],buffer[indexK+7],0,0});
		int residualChargeDuration=com.cocopass.helper.CByte.ByteArrayToInt(new byte[]{ buffer[indexK+10],buffer[indexK+9],0,0});
		boolean[] FalutStatus=com.cocopass.helper.CByte.ToBooleanArray(buffer[indexK+11]);
		boolean[] BatteryStatus=com.cocopass.helper.CByte.ToBooleanArray(buffer[indexK+12]);
		
		bi.SetTerminalID(terminalID);
		bi.SetSamplingTime(samplingTime);
		bi.setServerSamplingTime(serverSamplingTime);
		bi.SetHealthCondition(healthCondition);
		bi.SetResidualCapacity(residualCapacity);
		bi.SetTypeID(typeID);
		bi.setStandardVoltage(standardVoltage);
		bi.setVoltage(voltage);
		bi.setBatteryItemNO(batteryItemNO);
		bi.SetVoltageValueList(voltageValueList);
		bi.SetRechargeCurrent(rechargeCurrent);
		bi.setDischargeCurrent(dischargeCurrent);
		bi.SetChargeNumeric(chargeNumeric);
		bi.SetTemperature(temperature);
		bi.SetRechargeTimes(rechargeTimes);
		bi.setChargingDuration(chargingDuration);
		bi.setResidualChargeDuration(residualChargeDuration);
		
		boolean externalCircuitFused=FalutStatus[0];
		boolean overCurrent=FalutStatus[1];
		boolean lowVoltageProtected=FalutStatus[2];
		boolean overVoltageProtected=FalutStatus[3];
		boolean overTemperature=FalutStatus[4];
		boolean chargeOverCurrent=FalutStatus[5];
		boolean mosBroken=FalutStatus[6];
		
		boolean reChargeOn=BatteryStatus[0];
		boolean dischargeOn=BatteryStatus[1];
		
		bi.setExternalCircuitFused(externalCircuitFused);
		bi.setOverCurrent(overCurrent);
		bi.setLowVoltageProtected(lowVoltageProtected);
		bi.setOverVoltageProtected(overVoltageProtected);
		bi.setOverTemperature(overTemperature);
		bi.setChargeOverCurrent(chargeOverCurrent);
		bi.setMosBroken(mosBroken);
		
		bi.setReChargeOn(reChargeOn);
		bi.setDischargeOn(dischargeOn);
		
		list.add(bi);

		return list;
	}
	
	// 4B透传采样型数据 ****目前貌似没有用到
				/**
				else if (buffer[16] == 0x4B && buffer[17] == 0x10 && buffer[18] == 0x32) {

					String indexActionCode = "";
					// buffer[23]==0x10&&
					String hexSignaling = "65";
					String hexVersion = "97";
					String hexChildPacketHeader = "4B";
					String hexChildPacketVersion = CByte.ByteToHexString(buffer[17]);// 子包版本
					String hexChildPacketSignaling = CByte.ByteToHexString(buffer[18]);// 子包信令
					String hexChildPacketFunCode = "00";// 子包功能码
					indexActionCode = hexSignaling + hexVersion + hexChildPacketHeader + hexChildPacketVersion
							+ hexChildPacketSignaling + hexChildPacketFunCode;
					// String[]
					// arrayAction=com.cocopass.helper.CRedis.get("Action:"+indexActionCode).split("|");
					String actionCode = com.cocopass.helper.CRedis.getMapValue("Action:" + indexActionCode, "Code");
					String actionName = com.cocopass.helper.CRedis.getMapValue("Action:" + indexActionCode, "Name");
					String serialNumber = String.valueOf(samplingTime) + String.valueOf(terminalID);
					com.ludong.model.ActionLog al = new com.ludong.model.ActionLog();
					al.SetTerminalID(terminalID);
					al.SetSamplingTime(samplingTime);
					al.SetTimestamp(samplingTime);
					al.setActionName(actionName);
					al.SetActionCode(actionCode);
					al.SetSerialNumber(serialNumber);
					al.SetAddTime(new Date().getTime());
					// al.SetParameters(parameters);
					
					list.add(al);

				}
				*/
	
	/**
	 * 解码方法
	 */
	@Override
	public List<Object> Decode() {
		// List<com.cocopass.iot.model.DecodeDataRouter> list=new
		// ArrayList<com.cocopass.iot.model.DecodeDataRouter>();
		List<Object> list = new ArrayList<Object>();
		switch (super.signaling) {
		case 0x65: // 采样型数据
			int childDataLength = length - 18;
			byte[] data = new byte[childDataLength];
			System.arraycopy(buffer, 16, data, 0, childDataLength);

			// 绿源芯数据
			if (buffer[22] == 0x4B && buffer[23] == 0x10 && buffer[24] == 0x10) {
				list=GetLuYuanHeartInfo();
			}
			// 充电桩2.0版本数据
			else if (buffer[16] == 0x4B && buffer[17] == 0x10 && buffer[18] == 0x60) {
				list=GetV20CharingStationInfo();
			}
			// 充电桩数据V2.1
			else if (buffer[16] == 0x4B && buffer[17] == 0x20 && buffer[18] == 0x60) {
				list=GetV21CharingStationInfo();
			}
			// 充电锁车桩数据
			else if (buffer[16] == 0x4B && buffer[17] == 0x10 && buffer[18] == 0x30) {
				list=GetWithLockCharingStationInfo();
			}
			// 陀螺仪数据
			else if (buffer[16] == 0x4B && buffer[17] == 0x10 && buffer[18] == 0x50) {
				list=GetGyroscopeInfo();
			}
			//蓝钉数据解析
			else if (buffer[16] == 0x4B && buffer[17] == 0x10 && buffer[18] == 0x40) {
				list= GetBluetoothStationInfo();
			}
			//BMS电池数据
			else if(buffer[16] == 0x4B && buffer[17] == 0x10 && buffer[18] == 0x20){
				list=GetBMSInfo();
			}
			break;
			
		case 0x85:
				list=GetInstructionResponseInfo();
				break;

		default:
			break;
		}
		
		return list;

	}

	public byte[] AddLuYuanHeartShellToPacket(long timestamp, byte[] luYuanHeartPacket, long terminalID) {
		// long lTerminalID=Long.parseLong(terminalID);
		// byte[] byteArrayTerminalID=CByte.longToBytes(lTerminalID);
		// byte[] packet=new byte[28];
		// packet[0] = 0x29;
		// packet[1] = 0x29;
		// packet[2] = (byte) 0x97;
		// packet[3] = 0x55;
		// packet[4] = 0;
		// packet[5] = 0X16;
		// packet[6] = byteArrayTerminalID[4];
		// packet[7] = byteArrayTerminalID[5];
		// packet[8] = byteArrayTerminalID[6];
		// packet[9] = byteArrayTerminalID[7];
		//
		// byte[] timestampByteArray=GetTimestampByteArray();
		//
		// packet[10] = timestampByteArray[0];
		// packet[11] = timestampByteArray[1];
		// packet[12] = timestampByteArray[2];
		// packet[13] = timestampByteArray[3];
		// packet[14] = timestampByteArray[4];
		// packet[15] = timestampByteArray[5];
		//
		// packet[16] = luYuanHeartPacket[0];
		// packet[17] = luYuanHeartPacket[1];
		// packet[18] = luYuanHeartPacket[2];
		// packet[19] = luYuanHeartPacket[3];
		// packet[20] = luYuanHeartPacket[4];
		// packet[21] = luYuanHeartPacket[5];
		// packet[22] = luYuanHeartPacket[6];
		// packet[23] = luYuanHeartPacket[7];
		// packet[24] = luYuanHeartPacket[8];
		// packet[25] = luYuanHeartPacket[9];
		//
		// packet[26] = CreateCheckValue(packet);
		// packet[27] = 0x0D;
		byte[] packet = AddGeneralShellToPacket(timestamp, luYuanHeartPacket, terminalID);
		return packet;
	}

	public byte[] CreateSwitchEBikePowerPacket(long timestamp, long terminalID, byte onORoff) {
		// TODO Auto-generated method stub

		byte[] luYuanHeartSwitchEBikePowerPacket = CreateLuYuanHeartSwitchEBikePowerPacket(onORoff);
		byte[] packet = AddLuYuanHeartShellToPacket(timestamp, luYuanHeartSwitchEBikePowerPacket, terminalID);
		return packet;
	}

	public byte[] CreateLuYuanHeartSwitchEBikePowerPacket(byte onORoff) {
		if (onORoff == 1)
			onORoff = 0x06;
		else
			onORoff = 0x07;
		byte[] packet = new byte[12];
		packet[0] = 0x4A;
		packet[1] = 0x10;
		packet[2] = 0x00;
		packet[3] = 0x06;
		packet[4] = onORoff;
		packet[5] = 0;
		packet[6] = 0;
		packet[7] = 0;
		packet[8] = 0;
		packet[9] = 0;
		packet[10] = CreateCheckValue(packet);
		packet[11] = 0x0D;
		return packet;
	}

	public byte[] CreateSwitchEBikeTailLightPowerPacket(long timestamp, long terminalID, byte onORoff) {
		// TODO Auto-generated method stub

		byte[] luYuanHeartSwitchTailLightPowerPacket = CreateLuYuanHeartSwitchEBikeTailLightPowerPacket(onORoff);
		byte[] packet = AddLuYuanHeartShellToPacket(timestamp, luYuanHeartSwitchTailLightPowerPacket, terminalID);
		return packet;
	}

	public byte[] CreateLuYuanHeartSwitchEBikeTailLightPowerPacket(byte onORoff) {
		if (onORoff == 1)
			onORoff = 0x08;
		else
			onORoff = 0x09;
		byte[] packet = new byte[12];
		packet[0] = 0x4A;
		packet[1] = 0x10;
		packet[2] = 0x00;
		packet[3] = 0x06;
		packet[4] = onORoff;
		packet[5] = 0;
		packet[6] = 0;
		packet[7] = 0;
		packet[8] = 0;
		packet[9] = 0;
		packet[10] = CreateCheckValue(packet);
		packet[11] = 0x0D;
		return packet;
	}

	public byte[] CreateSwitchEBikeTurnLightFlashPacket(long timestamp, long terminalID, byte onORoff) {
		// TODO Auto-generated method stub
		byte[] luYuanHeartSwitchTurnLightPowerPacket = CreateLuYuanHeartSwitchEBikeTurnLightFlashPacket(onORoff);
		byte[] packet = AddLuYuanHeartShellToPacket(timestamp, luYuanHeartSwitchTurnLightPowerPacket, terminalID);
		return packet;
	}

	public byte[] CreateLuYuanHeartSwitchEBikeTurnLightFlashPacket(byte onORoff) {
		if (onORoff == 1)
			onORoff = 0x04;
		else
			onORoff = 0x05;
		byte[] packet = new byte[12];
		packet[0] = 0x4A;
		packet[1] = 0x10;
		packet[2] = 0x00;
		packet[3] = 0x06;
		packet[4] = onORoff;
		packet[5] = 0;
		packet[6] = 0;
		packet[7] = 0;
		packet[8] = 0;
		packet[9] = 0;
		packet[10] = CreateCheckValue(packet);
		packet[11] = 0x0D;
		return packet;
	}

	public byte[] CreateSwitchEBikeSaddleLockPacket(long timestamp, long terminalID, byte openOrLock) {
		// TODO Auto-generated method stub

		byte[] luYuanHeartSwitchEBikeSaddleLockPacket = CreateLuYuanHeartSwitchEBikeSaddleLockPacket(openOrLock);
		byte[] packet = AddLuYuanHeartShellToPacket(timestamp, luYuanHeartSwitchEBikeSaddleLockPacket, terminalID);
		return packet;
	}

	public byte[] CreateLuYuanHeartSwitchEBikeSaddleLockPacket(byte openOrLock) {
		if (openOrLock == 1)
			openOrLock = 0x02;
		else
			openOrLock = 0x03;
		byte[] packet = new byte[12];
		packet[0] = 0x4A;
		packet[1] = 0x10;
		packet[2] = 0x00;
		packet[3] = 0x06;
		packet[4] = openOrLock;
		packet[5] = 0;
		packet[6] = 0;
		packet[7] = 0;
		packet[8] = 0;
		packet[9] = 0;
		packet[10] = CreateCheckValue(packet);
		packet[11] = 0x0D;
		return packet;
	}

	public byte[] CreateResetEBikePacket(long timestamp, long terminalID) {
		// TODO Auto-generated method stub

		byte[] luYuanHeartResetEBikePacket = CreateLuYuanHeartResetEBikePacket();
		byte[] packet = AddLuYuanHeartShellToPacket(timestamp, luYuanHeartResetEBikePacket, terminalID);
		return packet;
	}

	public byte[] CreateLuYuanHeartResetEBikePacket() {

		byte[] packet = new byte[12];
		packet[0] = 0x4A;
		packet[1] = 0x10;
		packet[2] = 0x00;
		packet[3] = 0x06;
		packet[4] = 0x01;
		packet[5] = 0;
		packet[6] = 0;
		packet[7] = 0;
		packet[8] = 0;
		packet[9] = 0;
		packet[10] = CreateCheckValue(packet);
		packet[11] = 0x0D;
		return packet;
	}

	public byte[] CreateSetEBikeMaxSpeedPacket(long timestamp, long terminalID, int speed) {
		// TODO Auto-generated method stub

		byte[] luYuanHeartSetEBikeMaxSpeedPacket = CreateLuYuanHeartSetEBikeMaxSpeedPacket(terminalID, speed);
		byte[] packet = AddLuYuanHeartShellToPacket(timestamp, luYuanHeartSetEBikeMaxSpeedPacket, terminalID);
		return packet;
	}

	public byte[] CreateLuYuanHeartSetEBikeMaxSpeedPacket(long terminalID, int speed) {
		if (speed < 4)
			speed = 0x0D;
		else
			speed = 0x0E;
		byte[] packet = new byte[12];
		packet[0] = 0x4A;
		packet[1] = 0x10;
		packet[2] = 0x00;
		packet[3] = 0x06;
		packet[4] = (byte) speed;
		packet[5] = 0;
		packet[6] = 0;
		packet[7] = 0;
		packet[8] = 0;
		packet[9] = 0;
		packet[10] = CreateCheckValue(packet);
		packet[11] = 0x0D;
		return packet;
	}

	byte[] CreateSwitchEBikePileLockPacket(long timestamp, long terminalID, int port, byte openOrLock) {

		byte[] childSwitchEBikePileLockPacket = new byte[8];

		childSwitchEBikePileLockPacket[0] = 0x4A;
		childSwitchEBikePileLockPacket[1] = 0x10;
		childSwitchEBikePileLockPacket[2] = 0x20;
		childSwitchEBikePileLockPacket[3] = 0x02;
		childSwitchEBikePileLockPacket[4] = (byte) port;
		childSwitchEBikePileLockPacket[5] = openOrLock;
		childSwitchEBikePileLockPacket[6] = CreateCheckValue(childSwitchEBikePileLockPacket);
		;
		childSwitchEBikePileLockPacket[7] = 0x0D;

		byte[] packet = AddGeneralShellToPacket(timestamp, childSwitchEBikePileLockPacket, terminalID);
		return packet;
	}

	// 创建充电桩开启指令包
	byte[] CreateSwitchPileChargerPacket(long timestamp, long terminalID, long sessionID, int batteryVoltage,
			int batteryCapacity, int port, byte onOrOff) {

		byte[] childSwitchPileChargerPacket = new byte[18];

		byte[] byteArraySessionID = CByte.longToBytes(sessionID);

		childSwitchPileChargerPacket[0] = 0x4A;
		childSwitchPileChargerPacket[1] = 0x10;
		childSwitchPileChargerPacket[2] = 0x10;
		childSwitchPileChargerPacket[3] = 0x0C;
		childSwitchPileChargerPacket[4] = byteArraySessionID[0];
		childSwitchPileChargerPacket[5] = byteArraySessionID[1];
		childSwitchPileChargerPacket[6] = byteArraySessionID[2];
		childSwitchPileChargerPacket[7] = byteArraySessionID[3];
		childSwitchPileChargerPacket[8] = byteArraySessionID[4];
		childSwitchPileChargerPacket[9] = byteArraySessionID[5];
		childSwitchPileChargerPacket[10] = byteArraySessionID[6];
		childSwitchPileChargerPacket[11] = byteArraySessionID[7];
		childSwitchPileChargerPacket[14] = (byte) batteryVoltage;
		childSwitchPileChargerPacket[15] = (byte) batteryCapacity;
		childSwitchPileChargerPacket[12] = (byte) port;
		childSwitchPileChargerPacket[13] = onOrOff;
		childSwitchPileChargerPacket[16] = CreateCheckValue(childSwitchPileChargerPacket);
		childSwitchPileChargerPacket[17] = 0x0D;

		byte[] packet = AddGeneralShellToPacket(timestamp, childSwitchPileChargerPacket, terminalID);
		return packet;
	}

	byte[] CreateSwitchPileLightPacket(long timestamp, long terminalID, int port, int lightNum, byte onOrOff) {
		byte[] childSwitchPileLightPacket = new byte[9];

		childSwitchPileLightPacket[0] = 0x4A;
		childSwitchPileLightPacket[1] = 0x10;
		childSwitchPileLightPacket[2] = 0x11;
		childSwitchPileLightPacket[3] = 0x03;
		childSwitchPileLightPacket[4] = (byte) port;
		childSwitchPileLightPacket[5] = (byte) lightNum;
		childSwitchPileLightPacket[6] = onOrOff;
		childSwitchPileLightPacket[7] = CreateCheckValue(childSwitchPileLightPacket);
		childSwitchPileLightPacket[8] = 0x0D;

		byte[] packet = AddGeneralShellToPacket(timestamp, childSwitchPileLightPacket, terminalID);
		return packet;
	}

	byte[] CreateSetStationPileNumPacket(long timestamp, long terminalID, int pileNumber) {

		byte[] thisPacket = new byte[7];

		thisPacket[0] = 0x4A;
		thisPacket[1] = 0x10;
		thisPacket[2] = 0x12;
		thisPacket[3] = 0x01;
		thisPacket[4] = (byte) pileNumber;
		thisPacket[5] = CreateCheckValue(thisPacket);
		thisPacket[6] = 0x0D;

		byte[] packet = AddGeneralShellToPacket(timestamp, thisPacket, terminalID);
		return packet;
	}

	byte[] CreateReSetPilePortPacket(long timestamp, long terminalID, int port) {

		byte[] thisPacket = new byte[7];

		thisPacket[0] = 0x4A;
		thisPacket[1] = 0x10;
		thisPacket[2] = 0x13;
		thisPacket[3] = 0x01;
		thisPacket[4] = (byte) port;
		thisPacket[5] = CreateCheckValue(thisPacket);
		thisPacket[6] = 0x0D;

		byte[] packet = AddGeneralShellToPacket(timestamp, thisPacket, terminalID);
		return packet;
	}

	byte[] CreateSetDataIntervalPacket(long timestamp, long terminalID, int interval) {

		byte[] thisPacket = new byte[7];

		thisPacket[0] = 0x4A;
		thisPacket[1] = 0x10;
		thisPacket[2] = 0x14;
		thisPacket[3] = 0x01;
		thisPacket[4] = (byte) interval;
		thisPacket[5] = CreateCheckValue(thisPacket);
		thisPacket[6] = 0x0D;

		byte[] packet = AddGeneralShellToPacket(timestamp, thisPacket, terminalID);
		return packet;
	}
	
	
	byte[] CreateSetSecondDeviceFactoryModePacket(long timestamp, long terminalID ) {

		byte[] thisPacket = new byte[12];

		thisPacket[0] = 0x4A;
		thisPacket[1] = 0x10;
		thisPacket[2] = 0x00;
		thisPacket[3] = 0x06;
		thisPacket[4] = 0x0C;
		thisPacket[5] = 0x00;
		thisPacket[6] = 0x00;
		thisPacket[7] = 0x00;
		thisPacket[8] = 0x00;
		thisPacket[9] = 0x00;
		thisPacket[10] = CreateCheckValue(thisPacket);
		thisPacket[11] = 0x0D;

		byte[] packet = AddGeneralShellToPacket(timestamp, thisPacket, terminalID);
		return packet;
	}

	@Override
	public com.cocopass.iot.model.DownMessageResult SendSwitchEBikePowerPacket(long timestamp, long terminalID,
			byte onORoff, String url) {
		byte[] packet = CreateSwitchEBikePowerPacket(timestamp, terminalID, onORoff);
		com.cocopass.iot.model.DownMessageResult result = SendPacket(terminalID, packet, url);
		return result;
	}

	@Override
	public DownMessageResult SendSwitchEBikeTailLightPowerPacket(long timestamp, long terminalID, byte onORoff,
			String url) {
		byte[] packet = CreateSwitchEBikeTailLightPowerPacket(timestamp, terminalID, onORoff);
		com.cocopass.iot.model.DownMessageResult result = SendPacket(terminalID, packet, url);
		return result;
	}

	@Override
	public DownMessageResult SendSwitchEBikeTurnLightFlashPacket(long timestamp, long terminalID, byte onORoff,
			String url) {
		byte[] packet = CreateSwitchEBikeTurnLightFlashPacket(timestamp, terminalID, onORoff);
		com.cocopass.iot.model.DownMessageResult result = SendPacket(terminalID, packet, url);
		return result;
	}

	@Override
	public DownMessageResult SendSwitchEBikeSaddleLockPacket(long timestamp, long terminalID, byte onORoff,
			String url) {
		byte[] packet = CreateSwitchEBikeSaddleLockPacket(timestamp, terminalID, onORoff);
		com.cocopass.iot.model.DownMessageResult result = SendPacket(terminalID, packet, url);
		return result;
	}

	@Override
	public DownMessageResult SendResetEBikePacket(long timestamp, long terminalID, String url) {
		byte[] packet = CreateResetEBikePacket(timestamp, terminalID);
		com.cocopass.iot.model.DownMessageResult result = SendPacket(terminalID, packet, url);
		return result;
	}

	@Override
	public DownMessageResult SendSetEBikeMaxSpeedPacket(long timestamp, long terminalID, int speed, String url) {
		byte[] packet = CreateSetEBikeMaxSpeedPacket(timestamp, terminalID, speed);
		com.cocopass.iot.model.DownMessageResult result = SendPacket(terminalID, packet, url);
		return result;
	}

	@Override
	public DownMessageResult SendSwitchEBikePileLockPacket(long timestamp, long terminalID, int port, byte openOrLock,
			String url) {
		byte[] switchEBikePileLockPacket = CreateSwitchEBikePileLockPacket(timestamp, terminalID, port, openOrLock);

		com.cocopass.iot.model.DownMessageResult result = SendPacket(terminalID, switchEBikePileLockPacket, url);
		return result;
	}

	@Override
	public DownMessageResult SendSwitchPileChargerPacket(long timestamp, long terminalID, long sessionID,
			int batteryVoltage, int batteryCapacity, int port, byte onOroff, String url) {
		byte[] switchPileChargerPacket = CreateSwitchPileChargerPacket(timestamp, terminalID, sessionID, batteryVoltage,
				batteryCapacity, port, onOroff);
		com.cocopass.iot.model.DownMessageResult result = SendPacket(terminalID, switchPileChargerPacket, url);
		return result;
	}

	@Override
	public DownMessageResult SendSwitchPileLightPacket(long timestamp, long terminalID, int port, int lightNumber,
			byte onORoff, String url) {
		byte[] switchPileLightPacket = CreateSwitchPileLightPacket(timestamp, terminalID, port, lightNumber, onORoff);
		com.cocopass.iot.model.DownMessageResult result = SendPacket(terminalID, switchPileLightPacket, url);
		return result;
	}

	@Override
	public DownMessageResult SendSetStationPileNumPacket(long timestamp, long terminalID, int pileNumber, String url) {
		// TODO Auto-generated method stub
		byte[] setStationPileNumPacket = CreateSetStationPileNumPacket(timestamp, terminalID, pileNumber);
		com.cocopass.iot.model.DownMessageResult result = SendPacket(terminalID, setStationPileNumPacket, url);
		return result;
	}

	@Override
	public DownMessageResult SendReSetPilePortPacket(long timestamp, long terminalID, int port, String url) {
		// TODO Auto-generated method stub
		byte[] reSetPilePortPacket = CreateReSetPilePortPacket(timestamp, terminalID, port);
		com.cocopass.iot.model.DownMessageResult result = SendPacket(terminalID, reSetPilePortPacket, url);
		return result;
	}

	@Override
	public DownMessageResult SendSetDataIntervalPacket(long timestamp, long terminalID, int interval, String url) {
		// TODO Auto-generated method stub
		byte[] setDataIntervalPacket = CreateSetDataIntervalPacket(timestamp, terminalID, interval);
		com.cocopass.iot.model.DownMessageResult result = SendPacket(terminalID, setDataIntervalPacket, url);
		return result;
	}
	
	@Override
	public DownMessageResult SendSetSecondDeviceFactoryModePacket(long timestamp, long terminalID,  String url) {
		// TODO Auto-generated method stub
		byte[] setSecondDeviceFactoryModePacket = CreateSetSecondDeviceFactoryModePacket(timestamp, terminalID);
		com.cocopass.iot.model.DownMessageResult result = SendPacket(terminalID, setSecondDeviceFactoryModePacket, url);
		return result;
	}

}
