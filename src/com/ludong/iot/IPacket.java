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
 * �������ݰ����ն�
 * @param terminalID
 * @param packet
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendPacket(long terminalID,byte[] packet,String url);

/**
 * ���Ϳ��Ƶ綯����Դ
 * @param timestamp
 * @param terminalID
 * @param onORoff
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchEBikePowerPacket(long timestamp,long terminalID, byte onORoff,String url);

/**
 * �����ն��ϴ����ݰ�
 * @return
 */
public List<Object> Decode();
//public byte[] CreateSwitchEBikePowerPacket(String terminalID, byte onORoff);

/**
 * �����ն����
 * @param timestamp
 * @param terminalID
 * @param totalMileage
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetTotalMileagePacket(long timestamp,long terminalID,  float totalMileage,String url);

/**
 * ���ñ����ֻ��ŵ��ն�
 * @param timestamp
 * @param terminalID
 * @param mobileNO
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetAlarmMobileNOPacket(long timestamp,long terminalID,  long mobileNO,String url);

/**
 * ���������ж�
 * @param timestamp
 * @param terminalID
 * @param level
 * @param rate
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetShakeSensitivityPacket(long timestamp,long terminalID,  int level,int rate,String url);

/**
 * ����ͣ���ش����
 * @param timestamp
 * @param terminalID
 * @param stopedInterval
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetStopedIntervalPacket(long timestamp,long terminalID,  int stopedInterval,String url);

/**
 * �������лش����
 * @param timestamp
 * @param terminalID
 * @param runningInterval
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetRunningIntervalPacket(long timestamp,long terminalID,  int runningInterval,String url);

/**
 * �����������
 * @param timestamp
 * @param terminalID
 * @param heartPongInterval
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetHeartPongIntervalPacket(long timestamp,long terminalID,  int heartPongInterval,String url);

/**
 * �����ն�ID
 * @param timestamp
 * @param terminalID
 * @param newTerminalID
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetTerminalIDPacket(long timestamp,long terminalID,  long newTerminalID,String url);

/**
 * �����ն�APN
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
 * �����ն�����
 * @param timestamp
 * @param terminalID
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendReBootTerminalPacket(long timestamp,long terminalID, String url);

/**
 * �����ն˼�Ȩ�����˿� ����/IP
 * @param timestamp
 * @param terminalID
 * @param host
 * @param port
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetTerminalAuthHostAndPort(long timestamp,long terminalID,String host,int port, String url);

/**
 * ���õ綯����������ٶ�
 * @param timestamp
 * @param terminalID
 * @param speed
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetEBikeMaxSpeedPacket(long timestamp,long terminalID, int speed,String url);

/**
 * ���õ綯��β�ƿ���
 * @param timestamp
 * @param terminalID
 * @param onORoff
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchEBikeTailLightPowerPacket(long timestamp,long terminalID, byte onORoff,String url);

/**
 * ���õ綯��β����˸
 * @param timestamp
 * @param terminalID
 * @param onORoff
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchEBikeTailLightFlashPacket(long timestamp,long terminalID, byte onORoff,String url);

/**
 * ���õ綯��β�俪��
 * @param timestamp
 * @param terminalID
 * @param openOrLock
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchEBikeSaddleLockPacket(long timestamp,long terminalID, byte openOrLock,String url);

/**
 * ���õ綯����λ
 * @param timestamp
 * @param terminalID
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendResetEBikePacket(long timestamp,long terminalID,String url);

/**
 * ����ת�����˸
 * @param timestamp
 * @param terminalID
 * @param onORoff
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchEBikeTurnLightFlashPacket(long timestamp,long terminalID, byte onORoff,String url);

/**
 * ���ÿ�������׮������
 * @param timestamp
 * @param terminalID
 * @param port
 * @param openOrLock
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSwitchEBikePileLockPacket(long timestamp,long terminalID,int port,byte openOrLock,String url);

/**
 * ���ó�׮ָʾ��
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
 * ����վ��׮����
 * @param timestamp
 * @param terminalID
 * @param pileNumber
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetStationPileNumPacket(long timestamp,long terminalID,  int pileNumber, String url);

/**
 * ���ø�λ׮�˿ڿ���
 * @param timestamp
 * @param terminalID
 * @param port
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendReSetPilePortPacket(long timestamp,long terminalID,  int port, String url);

/**
 * ���ÿ��س��׮��翪��
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
 * �����ն���ת�����ϴ�ʱ����
 * @param timestamp
 * @param terminalID
 * @param interval
 * @param url
 * @return
 */
public com.cocopass.iot.model.DownMessageResult SendSetDataIntervalPacket(long timestamp,long terminalID,  int interval, String url);


public com.cocopass.iot.model.DownMessageResult SendSetSecondDeviceFactoryModePacket(long timestamp,long terminalID,  String url);

}