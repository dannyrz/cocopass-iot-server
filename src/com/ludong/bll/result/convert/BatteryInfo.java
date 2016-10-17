package com.ludong.bll.result.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dave.common.database.convert.ResultConverter;

public class BatteryInfo implements ResultConverter<com.ludong.model.BatteryInfo>{

	@Override
	public com.ludong.model.BatteryInfo convert(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		com.ludong.model.BatteryInfo model = new com.ludong.model.BatteryInfo();
		model.setID(rs.getLong("ID"));
		model.SetIsRealTime(rs.getBoolean("IsRealTime"));
		model.setAnticipateKilometre(rs.getFloat("AnticipateKilometre"));
		model.setChargingDuration(rs.getInt("ChargingDuration"));
		model.setChargingPortStatus(rs.getInt("ChargingPortStatus"));
		model.setChargingPower(rs.getFloat("ChargingPower"));
		model.setEBikeRFID(rs.getLong("EBikeRFID"));
		model.setPilePort(rs.getInt("PilePort"));
		model.setServerSamplingTime(rs.getLong("ServerSamplingTime"));
		model.setSessionID(rs.getLong("SessionID"));
		model.SetChargeNumeric(rs.getInt("ChargeNumeric"));
		model.SetHealthCondition(rs.getInt("HealthCondition"));
		model.SetIsRealTime(rs.getBoolean("IsRealTime"));
		model.SetRechargeCurrent(rs.getFloat("RechargeCurrent"));
		model.SetRechargeTimes(rs.getInt("RechargeTimes"));
		model.SetRechargeVoltage(rs.getFloat("RechargeVoltage"));
		model.SetResidualCapacity(rs.getFloat("ResidualCapacity"));
		model.SetSamplingTime(rs.getLong("SamplingTime"));
		model.SetTemperature(rs.getFloat("Temperature"));
		model.SetTerminalID(rs.getLong("TerminalID"));
		model.SetTypeID(rs.getInt("TypeID"));
		model.SetVersion(rs.getFloat("Version"));
		model.SetVoltageValueList(rs.getString("VoltageValueList"));
		model.SetWrittenIntoSystemTime(rs.getLong("WrittenIntoSystemTime"));
		return model;
	}
	

}
