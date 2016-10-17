package com.cocopass.dal.result.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dave.common.database.convert.ResultConverter;

public class OnLineShaft  implements ResultConverter<com.cocopass.iot.model.OnLineShaft>{

	@Override
	public com.cocopass.iot.model.OnLineShaft convert(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		com.cocopass.iot.model.OnLineShaft model = new com.cocopass.iot.model.OnLineShaft();
		model.setAddTime(rs.getLong("AddTime")); 
		model.setID(rs.getLong("ID")); 
		model.setIsOnline(rs.getBoolean("IsOnline")); 
		model.setTerminalID(rs.getLong("TerminalID"));
		
		return model;
	}
	

}
