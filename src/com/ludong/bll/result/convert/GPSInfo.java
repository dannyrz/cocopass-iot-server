package com.ludong.bll.result.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dave.common.database.convert.ResultConverter;

public class GPSInfo implements ResultConverter<com.ludong.model.GPSInfo>{

	@Override
	public com.ludong.model.GPSInfo convert(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		com.ludong.model.GPSInfo gpsInfo = new com.ludong.model.GPSInfo();
		gpsInfo.setID(rs.getLong("ID"));
		gpsInfo.setAddress(rs.getString("Address"));
		gpsInfo.setBDLocation(rs.getString("BDLocation"));
		gpsInfo.setGDLocation(rs.getString("GDLocation"));
		gpsInfo.SetAlarmNO(rs.getInt("AlarmNO"));
		gpsInfo.SetAlarmParam(rs.getInt("AlarmParam"));
		gpsInfo.SetAntennaState(rs.getInt("AntennaState"));
		gpsInfo.SetBuiltInBatteryVoltageQuantity(rs.getInt("BuiltInBatteryVoltageQuantity"));//°Ù·Ö±È
		gpsInfo.SetDirection(rs.getInt("Direction"));
		gpsInfo.SetExternalVoltage(rs.getFloat("ExternalVoltage"));
		gpsInfo.SetGPSSignalValue(rs.getInt("GPSSignalValue"));
		gpsInfo.SetGSMSignalValue(rs.getInt("GSMSignalValue"));
		gpsInfo.SetGSMStationAreaID(rs.getString("GSMStationAreaID"));
		gpsInfo.SetGSMStationID(rs.getString("GSMStationID"));
		gpsInfo.SetIsDefence(rs.getBoolean("IsDefence"));
		gpsInfo.SetIsMosOpen(rs.getBoolean("IsMosOpen"));
		gpsInfo.SetIsRealTime(rs.getBoolean("IsRealTime"));
		gpsInfo.SetLatitude(rs.getFloat("Latitude"));
		gpsInfo.SetLongitude(rs.getFloat("Longitude"));
		gpsInfo.SetPositionState(rs.getBoolean("PositionState"));
		gpsInfo.SetPowerState(rs.getInt("PowerState"));
		gpsInfo.SetSamplingTime(rs.getLong("SamplingTime"));
		gpsInfo.SetSpeed(rs.getInt("Speed"));
		gpsInfo.SetTerminalID(rs.getLong("TerminalID"));
		gpsInfo.SetTotalMileage(rs.getFloat("TotalMileage"));
		gpsInfo.SetVersion(rs.getFloat("Version"));
		gpsInfo.SetWrittenIntoSystemTime(rs.getLong("WrittenIntoSystemTime"));
		return gpsInfo;
	}
	

}
