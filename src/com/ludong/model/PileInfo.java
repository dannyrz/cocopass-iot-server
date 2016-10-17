package com.ludong.model;

public class PileInfo {
	
	int ChargingPortStatus=0;

	 

	public int getChargingPortStatus() {
		return ChargingPortStatus;
	}

	public void setChargingPortStatus(int chargingPortStatus) {
		ChargingPortStatus = chargingPortStatus;
	}

	long StationTerminalID=0;
	public long getStationTerminalID() {
		return StationTerminalID;
	}

	public void setStationTerminalID(long stationTerminalID) {
		StationTerminalID = stationTerminalID;
	}

	public boolean isHaveEBike() {
		return HaveEBike;
	}

	public void setHaveEBike(boolean haveEBike) {
		HaveEBike = haveEBike;
	}

	public boolean isLocked() {
		return Locked;
	}

	public void setLocked(boolean locked) {
		Locked = locked;
	}

	public boolean isPileFault() {
		return PileFault;
	}

	public void setPileFault(boolean pileFault) {
		PileFault = pileFault;
	}

	public boolean isLockFault() {
		return LockFault;
	}

	public void setLockFault(boolean lockFault) {
		LockFault = lockFault;
	}

	public boolean isRFIDReaderMachineFault() {
		return RFIDReaderMachineFault;
	}

	public void setRFIDReaderMachineFault(boolean rFIDReaderMachineFault) {
		RFIDReaderMachineFault = rFIDReaderMachineFault;
	}

	public boolean isCharging() {
		return Charging;
	}

	public void setCharging(boolean charging) {
		Charging = charging;
	}

	public boolean isRechargerConstructionFault() {
		return RechargerConstructionFault;
	}

	public void setRechargerConstructionFault(boolean rechargerConstructionFault) {
		RechargerConstructionFault = rechargerConstructionFault;
	}

	public boolean isRechargerFault() {
		return RechargerFault;
	}

	public void setRechargerFault(boolean rechargerFault) {
		RechargerFault = rechargerFault;
	}

	public long getEBikeRFID() {
		return EBikeRFID;
	}

	public void setEBikeRFID(long eBikeRFID) {
		EBikeRFID = eBikeRFID;
	}

	public BatteryInfo getBatteryData() {
		return BatteryData;
	}

	public void setBatteryData(BatteryInfo batteryData) {
		BatteryData = batteryData;
	}

	boolean HaveEBike=false;
	boolean Locked=false;
	boolean PileFault=false;
	boolean LockFault=false;
	boolean RFIDReaderMachineFault=false;
	int Port=0;
	
	public int getPort() {
		return Port;
	}

	public void setPort(int port) {
		Port = port;
	}

	boolean Charging=false;
	boolean RechargerConstructionFault=false;
	boolean RechargerFault=false;
	
	long EBikeRFID=0;
	
	public long getSamplingTime() {
		return SamplingTime;
	}

	public void setSamplingTime(long samplingTime) {
		SamplingTime = samplingTime;
	}

	BatteryInfo BatteryData ;
	
	long SamplingTime =0;
}
