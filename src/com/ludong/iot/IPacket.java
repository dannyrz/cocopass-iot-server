package com.ludong.iot;

import java.util.List;

import com.cocopass.iot.model.AuthInfo;

public interface IPacket {
public   long GetTerminalID();
public   int GetSignalling();
//public   float GetVersion();
public   com.ludong.model.GPSInfo GetGPSInfo();
public   com.ludong.model.BatteryInfo GetBatteryInfo();
public   com.ludong.model.ControllerInfo GetControllerInfo();
public   com.ludong.model.Alarm GetAlarm();
public byte[] CreateAuthResponsePacket(byte[] byteArraryReceiveIP, byte[] byteArraryReceiveTCPPort,
		byte[] byteArraryReceiveUDPPort);
public byte[] CreateNormalResponsePacket();
public byte[] CreateResponsePacket();


/**
 * 发送数据包到终端
 * @param terminalID
 * @param packet
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendPacket(long terminalID,byte[] packet,String url);

/**
 * 发送控制电动车电源
 * @param timestamp
 * @param terminalID
 * @param onORoff
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchEBikePowerPacket(long timestamp,long terminalID, byte onORoff,String url);

/**
 * 解码终端上传数据包
 * @return
 */
public List<Object> Decode();
//public byte[] CreateSwitchEBikePowerPacket(String terminalID, byte onORoff);

/**
 * 设置终端里程
 * @param timestamp
 * @param terminalID
 * @param totalMileage
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetTotalMileagePacket(long timestamp,long terminalID,  float totalMileage,String url);

/**
 * 设置报警手机号到终端
 * @param timestamp
 * @param terminalID
 * @param mobileNO
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetAlarmMobileNOPacket(long timestamp,long terminalID,  long mobileNO,String url);

/**
 * 设置震动敏感度
 * @param timestamp
 * @param terminalID
 * @param level
 * @param rate
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetShakeSensitivityPacket(long timestamp,long terminalID,  int level,int rate,String url);

/**
 * 设置停车回传间隔
 * @param timestamp
 * @param terminalID
 * @param stopedInterval
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetStopedIntervalPacket(long timestamp,long terminalID,  int stopedInterval,String url);

/**
 * 设置骑行回传间隔
 * @param timestamp
 * @param terminalID
 * @param runningInterval
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetRunningIntervalPacket(long timestamp,long terminalID,  int runningInterval,String url);

/**
 * 设置心跳间隔
 * @param timestamp
 * @param terminalID
 * @param heartPongInterval
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetHeartPongIntervalPacket(long timestamp,long terminalID,  int heartPongInterval,String url);

/**
 * 设置终端ID
 * @param timestamp
 * @param terminalID
 * @param newTerminalID
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetTerminalIDPacket(long timestamp,long terminalID,  long newTerminalID,String url);

/**
 * 设置终端APN
 * @param timestamp
 * @param terminalID
 * @param apnName
 * @param apnUserName
 * @param apnPassword
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetTerminalAPNPacket(long timestamp,long terminalID, String apnName,String apnUserName,String apnPassword,String url);

/**
 * 设置终端重启
 * @param timestamp
 * @param terminalID
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendReBootTerminalPacket(long timestamp,long terminalID, String url);

/**
 * 设置终端鉴权主机端口 域名/IP
 * @param timestamp
 * @param terminalID
 * @param host
 * @param port
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetTerminalAuthHostAndPort(long timestamp,long terminalID,String host,int port, String url);

/**
 * 设置电动车骑行最大速度
 * @param timestamp
 * @param terminalID
 * @param speed
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetEBikeMaxSpeedPacket(long timestamp,long terminalID, int speed,String url);

/**
 * 设置电动车尾灯开关
 * @param timestamp
 * @param terminalID
 * @param onORoff
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchEBikeTailLightPowerPacket(long timestamp,long terminalID, byte onORoff,String url);

/**
 * 设置电动车尾灯闪烁
 * @param timestamp
 * @param terminalID
 * @param onORoff
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchEBikeTailLightFlashPacket(long timestamp,long terminalID, byte onORoff,String url);

/**
 * 设置电动车尾箱开关
 * @param timestamp
 * @param terminalID
 * @param openOrLock
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchEBikeSaddleLockPacket(long timestamp,long terminalID, byte openOrLock,String url);

/**
 * 设置电动车复位
 * @param timestamp
 * @param terminalID
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendResetEBikePacket(long timestamp,long terminalID,String url);

/**
 * 设置转向灯闪烁
 * @param timestamp
 * @param terminalID
 * @param onORoff
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchEBikeTurnLightFlashPacket(long timestamp,long terminalID, byte onORoff,String url);

/**
 * 设置开关锁车桩锁开关
 * @param timestamp
 * @param terminalID
 * @param port
 * @param openOrLock
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchEBikePileLockPacket(long timestamp,long terminalID,int port,byte openOrLock,String url);

/**
 * 设置车桩指示灯
 * @param timestamp
 * @param terminalID
 * @param port
 * @param lightNumber
 * @param onORoff
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchPileLightPacket(long timestamp,long terminalID,int port,int lightNumber,byte onORoff,String url);

/**
 * 设置站点桩数量
 * @param timestamp
 * @param terminalID
 * @param pileNumber
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetStationPileNumPacket(long timestamp,long terminalID,  int pileNumber, String url);

/**
 * 设置复位桩端口开关
 * @param timestamp
 * @param terminalID
 * @param port
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendReSetPilePortPacket(long timestamp,long terminalID,  int port, String url);

/**
 * 设置开关充电桩充电开关
 * @param timestamp
 * @param terminalID
 * @param sessionID
 * @param batteryVoltage
 * @param batteryCapacity
 * @param port
 * @param onOroff
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchPileChargerPacket(long timestamp,long terminalID,long sessionID,int batteryVoltage,int batteryCapacity,int port,byte onOroff,String url);


/**
 * 设置终端中转数据上传时间间隔
 * @param timestamp
 * @param terminalID
 * @param interval
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetDataIntervalPacket(long timestamp,long terminalID,  int interval, String url);


public com.cocopass.iot.model.DownMessageResult SendSetSecondDeviceFactoryModePacket(long timestamp,long terminalID,  String url);

}