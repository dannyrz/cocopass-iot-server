package com.cocopass.dal.result.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dave.common.database.convert.ResultConverter;

public class LoginInfo  implements ResultConverter<com.cocopass.iot.model.LoginInfo>{
	@Override
	public com.cocopass.iot.model.LoginInfo convert(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		com.cocopass.iot.model.LoginInfo model = new com.cocopass.iot.model.LoginInfo();
		model.setID(rs.getLong("ID"));
		model.setAddTime(rs.getLong("AddTime"));
		model.setReceiveHost(rs.getString("ReceiveHost"));
		model.setReceiveLocalHost(rs.getString("ReceiveLocalHost")); 
		model.setReceiveHost(rs.getString("ReceiveHost")); 
		model.setReceiveTCPPort(rs.getInt("ReceiveTCPPort"));
		model.setReceiveUDPPort(rs.getInt("ReceiveUDPPort")); 
		model.setSamplingTime(rs.getLong("SamplingTime"));
		model.setServerSamplingTime(rs.getLong("ServerSamplingTime"));
		model.setTerminalID(rs.getLong("TerminalID"));
		return model;
	}

}
