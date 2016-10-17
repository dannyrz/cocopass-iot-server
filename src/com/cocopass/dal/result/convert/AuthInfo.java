package com.cocopass.dal.result.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dave.common.database.convert.ResultConverter;

public class AuthInfo  implements ResultConverter<com.cocopass.iot.model.AuthInfo>{
	@Override
	public com.cocopass.iot.model.AuthInfo convert(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		com.cocopass.iot.model.AuthInfo model = new com.cocopass.iot.model.AuthInfo();
		model.setID(rs.getLong("ID"));
		model.setAddTime(rs.getLong("AddTime"));
		model.setFirmwareVersion(rs.getString("FirmwareVersion"));
		model.setICCID(rs.getString("ICCID")); 
		model.setIMEI(rs.getString("IMEI")); 
		model.setIMSI(rs.getString("iMSI"));
		model.setSamplingTime(rs.getLong("SamplingTime"));
		model.setServerSamplingTime(rs.getLong("ServerSamplingTime"));
		model.setTerminalID(rs.getLong("TerminalID"));
		return model;
	}

}
