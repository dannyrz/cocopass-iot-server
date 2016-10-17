package com.cocopass.iot.model;

import java.util.Date;

public class LoginInfo {
	long ID;
	long TerminalID;
	long SamplingTime;
	String ReceiveHost;
	String ReceiveLocalHost;
	int ReceiveTCPPort;
	int ReceiveUDPPort;
	long AddTime ;
	long ServerSamplingTime;
	
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
	public String getReceiveHost() {
		return ReceiveHost;
	}
	public void setReceiveHost(String receiveHost) {
		ReceiveHost = receiveHost;
	}
	public String getReceiveLocalHost() {
		return ReceiveLocalHost;
	}
	public void setReceiveLocalHost(String receiveLocalHost) {
		ReceiveLocalHost = receiveLocalHost;
	}
	public int getReceiveTCPPort() {
		return ReceiveTCPPort;
	}
	public void setReceiveTCPPort(int receiveTCPPort) {
		ReceiveTCPPort = receiveTCPPort;
	}
	public int getReceiveUDPPort() {
		return ReceiveUDPPort;
	}
	public void setReceiveUDPPort(int receiveUDPPort) {
		ReceiveUDPPort = receiveUDPPort;
	}
	public long getAddTime() {
		return AddTime;
	}
	public void setAddTime(long addTime) {
		AddTime = addTime;
	}
	public long getServerSamplingTime() {
		return ServerSamplingTime;
	}
	public void setServerSamplingTime(long serverSamplingTime) {
		ServerSamplingTime = serverSamplingTime;
	}
	

}
