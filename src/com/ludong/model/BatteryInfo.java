package com.ludong.model;

import java.util.Date;

public class BatteryInfo {
	private long ID = 0;
	private long TerminalID = 0;
	private int PilePort = 0;
	private long SessionID = 0;
	int ChargingPortStatus = 0;
	private long EBikeRFID = 0;
	private int TypeID = 0;// 电池类型
	float StandardVoltage=0;
	float Voltage=0; //实测电压
	int BatteryItemNO=0;
	private String VoltageValueList = "";// 电池组电压
	private float RechargeVoltage = 0;// 充电电压
	private float RechargeCurrent = 0;// 充电电流
	private float DischargeCurrent=0;//放电电流
	private float ResidualCapacity = 0;// 剩余容量 安时
	private int RechargeTimes = 0;// 充电次数
	private float Temperature = 0;// 电池温度
	private int ChargeNumeric = 0; // 电量百分比 充电与非充电时都传吗？
	private int HealthCondition = 0;// 健康状况
	private long SamplingTime;
	private long ServerSamplingTime;
	private long WrittenIntoSystemTime;
	private float Version = 0;
	private int ChargingDuration = 0; // 充电时长
	float ChargingPower = 0; // 充电瓦时
	float AnticipateKilometre = 0;// 预估里程
	private boolean IsRealTme = true;
	int ResidualChargeDuration=0; //剩余充电时长

	boolean ExternalCircuitFused=false;
	boolean OverCurrent=false;
	boolean LowVoltageProtected=false;
	boolean OverVoltageProtected=false;
	boolean OverTemperature=false;
	boolean ChargeOverCurrent=false;
	boolean MosBroken=false;
	
	boolean DischargeOn=false;
	boolean ReChargeOn=false;
	
	
	
	
	public boolean isExternalCircuitFused() {
		return ExternalCircuitFused;
	}

	public void setExternalCircuitFused(boolean externalCircuitFused) {
		ExternalCircuitFused = externalCircuitFused;
	}

	public boolean isOverCurrent() {
		return OverCurrent;
	}

	public void setOverCurrent(boolean overCurrent) {
		OverCurrent = overCurrent;
	}

	public boolean isLowVoltageProtected() {
		return LowVoltageProtected;
	}

	public void setLowVoltageProtected(boolean lowVoltageProtected) {
		LowVoltageProtected = lowVoltageProtected;
	}

	public boolean isOverVoltageProtected() {
		return OverVoltageProtected;
	}

	public void setOverVoltageProtected(boolean overVoltageProtected) {
		OverVoltageProtected = overVoltageProtected;
	}

	public boolean isOverTemperature() {
		return OverTemperature;
	}

	public void setOverTemperature(boolean overTemperature) {
		OverTemperature = overTemperature;
	}

	public boolean isChargeOverCurrent() {
		return ChargeOverCurrent;
	}

	public void setChargeOverCurrent(boolean chargeOverCurrent) {
		ChargeOverCurrent = chargeOverCurrent;
	}

	public boolean isMosBroken() {
		return MosBroken;
	}

	public void setMosBroken(boolean mosBroken) {
		MosBroken = mosBroken;
	}

	public boolean isDischargeOn() {
		return DischargeOn;
	}

	public void setDischargeOn(boolean dischargeOn) {
		DischargeOn = dischargeOn;
	}

	public boolean isReChargeOn() {
		return ReChargeOn;
	}

	public void setReChargeOn(boolean reChargeOn) {
		ReChargeOn = reChargeOn;
	}

	public int getResidualChargeDuration() {
		return ResidualChargeDuration;
	}

	public void setResidualChargeDuration(int residualChargeDuration) {
		ResidualChargeDuration = residualChargeDuration;
	}

	public float getDischargeCurrent() {
		return DischargeCurrent;
	}

	public void setDischargeCurrent(float dischargeCurrent) {
		DischargeCurrent = dischargeCurrent;
	}

	public int getBatteryItemNO() {
		return BatteryItemNO;
	}

	public void setBatteryItemNO(int batteryItemNO) {
		BatteryItemNO = batteryItemNO;
	}

	public float getVoltage() {
		return Voltage;
	}

	public void setVoltage(float voltage) {
		Voltage = voltage;
	}

	public float getStandardVoltage() {
		return StandardVoltage;
	}

	public void setStandardVoltage(float standardVoltage) {
		StandardVoltage = standardVoltage;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}
	public long getServerSamplingTime() {
		return ServerSamplingTime;
	}

	public void setServerSamplingTime(long serverSamplingTime) {
		ServerSamplingTime = serverSamplingTime;
	}

	public int getChargingPortStatus() {
		return ChargingPortStatus;
	}

	public void setChargingPortStatus(int chargingPortStatus) {
		ChargingPortStatus = chargingPortStatus;
	}

	public long getSessionID() {
		return SessionID;
	}

	public void setSessionID(long sessionID) {
		SessionID = sessionID;
	}

	public int getPilePort() {
		return PilePort;
	}

	public void setPilePort(int pilePort) {
		PilePort = pilePort;
	}

//	public long getStationTerminalID() {
//		return StationTerminalID;
//	}
//
//	public void setStationTerminalID(long stationTerminalID) {
//		StationTerminalID = stationTerminalID;
//	}

	public int getChargingDuration() {
		return ChargingDuration;
	}

	public void setChargingDuration(int chargingDuration) {
		ChargingDuration = chargingDuration;
	}

	public float getChargingPower() {
		return ChargingPower;
	}

	public void setChargingPower(float chargingPower) {
		ChargingPower = chargingPower;
	}

	public long getEBikeRFID() {
		return EBikeRFID;
	}

	public void setEBikeRFID(long rFID) {
		EBikeRFID = rFID;
	}

	public float getAnticipateKilometre() {
		return AnticipateKilometre;
	}

	public void setAnticipateKilometre(float anticipateKilometre) {
		AnticipateKilometre = anticipateKilometre;
	}
	// public int getChargeDuration() {
	// return ChargeDuration;
	// }
	// public void setChargeDuration(int chargeDuration) {
	// ChargeDuration = chargeDuration;
	// }
	// public float getChargeTotalPower() {
	// return ChargeTotalPower;
	// }
	// public void setChargeTotalPower(float chargeTotalPower) {
	// ChargeTotalPower = chargeTotalPower;
	// }

	public float GetVersion() {
		return Version;
	}

	public void SetVersion(float version) {
		this.Version = version;
	}

	public boolean GetIsRealTime() {
		return IsRealTme;
	}

	public void SetIsRealTime(boolean isRealTime) {
		this.IsRealTme = isRealTime;
	}

	public int GetHealthCondition() {
		return HealthCondition;
	}

	public void SetHealthCondition(int healthCondition) {
		this.HealthCondition = healthCondition;
	}

	public int GetRechargeTimes() {
		return RechargeTimes;
	}

	public void SetRechargeTimes(int rechargeTimes) {
		this.RechargeTimes = rechargeTimes;
	}

	public int GetChargeNumeric() {
		return ChargeNumeric;
	}

	public void SetChargeNumeric(int chargeNumeric) {
		this.ChargeNumeric = chargeNumeric;
	}

	public String GetVoltageValueList() {
		return VoltageValueList;
	}

	public void SetVoltageValueList(String voltageValueList) {
		this.VoltageValueList = voltageValueList;
	}

	public float GetRechargeVoltage() {
		return RechargeVoltage;
	}

	public void SetRechargeVoltage(float rechargeVoltage) {
		this.RechargeVoltage = rechargeVoltage;
	}

	public float GetRechargeCurrent() {
		return RechargeCurrent;
	}

	public void SetRechargeCurrent(float rechargeCurrent) {
		this.RechargeCurrent = rechargeCurrent;
	}

	public float GetResidualCapacity() {
		return ResidualCapacity;
	}

	public void SetResidualCapacity(float residualCapacity) {
		this.ResidualCapacity = residualCapacity;
	}

	public float GetTemperature() {
		return Temperature;
	}

	public void SetTemperature(float temperature) {
		this.Temperature = temperature;
	}

	public long GetTerminalID() {
		return TerminalID;
	}

	public void SetTerminalID(long terminalID) {
		this.TerminalID = terminalID;
	}

	public long GetSamplingTime() {
		return SamplingTime;
	}

	public void SetSamplingTime(long samplingTime) {
		this.SamplingTime = samplingTime;
	}

	public long GetWrittenIntoSystemTime() {
		return WrittenIntoSystemTime;
	}

	public void SetWrittenIntoSystemTime(long writtenIntoSystemTime) {
		this.WrittenIntoSystemTime = writtenIntoSystemTime;
	}

	public int GetTypeID() {
		return TypeID;
	}

	public void SetTypeID(int typeID) {
		this.TypeID = typeID;
	}

}
