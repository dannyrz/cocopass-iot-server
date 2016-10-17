package com.cocopass.iot.model;

import java.util.Date;

public class AuthInfo {
	long ID;
	long TerminalID;
	long SamplingTime;
	long ServerSamplingTime;
	String IMEI;
	String IMSI;
	String ICCID;
	String FirmwareVersion;
	long AddTime;
	
	public long getServerSamplingTime() {
		return ServerSamplingTime;
	}
	public void setServerSamplingTime(long serverSamplingTime) {
		ServerSamplingTime = serverSamplingTime;
	}
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public long getTerminalID() {
		return TerminalID;
	}
	public void setTerminalID(long terminalID) {
		TerminalID = terminalID;
	}
	public long getSamplingTime() {
		return SamplingTime;
	}
	public void setSamplingTime(long samplingTime) {
		SamplingTime = samplingTime;
	}
	public String getIMEI() {
		return IMEI;
	}
	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
	public String getIMSI() {
		return IMSI;
	}
	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
	public String getFirmwareVersion() {
		return FirmwareVersion;
	}
	public void setFirmwareVersion(String firmwareVersion) {
		FirmwareVersion = firmwareVersion;
	}
	public long getAddTime() {
		return AddTime;
	}
	public void setAddTime(long addTime) {
		AddTime = addTime;
	}


}
