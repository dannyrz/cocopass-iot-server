package com.ludong.model;

public class BluetoothStation {

	long SamplingTime=0;
	long ServerSamplingTime=0;
	String ID="";
	int BikeEventID=0;
	int BatteryLevel=0;
	long TerminalID=0;
	String MAC="";
	int RSSILatch=0;
	int RSSIL=0;
	int RSSIH=0;
	int BroadcastFrequent=0;
	int BroadcastPower=0;
	int SamplBatteryLevelFrequent=0;
	
	
	
	public int getRSSIL() {
		return RSSIL;
	}
	public void setRSSIL(int rSSIL) {
		RSSIL = rSSIL;
	}
	public int getRSSIH() {
		return RSSIH;
	}
	public void setRSSIH(int rSSIH) {
		RSSIH = rSSIH;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public int getBikeEventID() {
		return BikeEventID;
	}
	public void setBikeEventID(int bikeEventID) {
		BikeEventID = bikeEventID;
	}
	public int getBatteryLevel() {
		return BatteryLevel;
	}
	public void setBatteryLevel(int batteryLevel) {
		BatteryLevel = batteryLevel;
	}
	public long getTerminalID() {
		return TerminalID;
	}
	public void setTerminalID(long terminalID) {
		TerminalID = terminalID;
	}
	public String getMAC() {
		return MAC;
	}
	public void setMAC(String mAC) {
		MAC = mAC;
	}
	public int getRSSILatch() {
		return RSSILatch;
	}
	public void setRSSILatch(int rSSILatch) {
		RSSILatch = rSSILatch;
	}
	 
	public int getBroadcastFrequent() {
		return BroadcastFrequent;
	}
	public void setBroadcastFrequent(int broadcastFrequent) {
		BroadcastFrequent = broadcastFrequent;
	}
	public int getBroadcastPower() {
		return BroadcastPower;
	}
	public void setBroadcastPower(int broadcastPower) {
		BroadcastPower = broadcastPower;
	}
	public int getSamplBatteryLevelFrequent() {
		return SamplBatteryLevelFrequent;
	}
	public void setSamplBatteryLevelFrequent(int samplBatteryLevelFrequent) {
		SamplBatteryLevelFrequent = samplBatteryLevelFrequent;
	}
	public long getSamplingTime() {
		return SamplingTime;
	}
	public void setSamplingTime(long samplingTime) {
		SamplingTime = samplingTime;
	}
	public long getServerSamplingTime() {
		return ServerSamplingTime;
	}
	public void setServerSamplingTime(long serverSamplingTime) {
		ServerSamplingTime = serverSamplingTime;
	}
	
	
}
