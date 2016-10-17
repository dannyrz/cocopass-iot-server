package com.ludong.bll.result.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dave.common.database.convert.ResultConverter;

public class ActionLog  implements ResultConverter<com.ludong.model.ActionLog>{
	@Override
	public com.ludong.model.ActionLog convert(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		com.ludong.model.ActionLog model = new com.ludong.model.ActionLog();
		model.setID(rs.getLong("ID"));
		model.setActionName(rs.getString("ActionName"));
		model.SetActionCode(rs.getString("ActionCode"));
		model.SetAddTime(rs.getLong("AddTime")); 
		model.SetAppKey(rs.getString("AppKey")); 
		model.SetParameters(rs.getString("Parameters"));
		model.SetSamplingTime(rs.getLong("SamplingTime")); 
		model.SetSerialNumber(rs.getString("SerialNumber"));
		model.SetStatus(rs.getInt("Status"));
		model.SetTerminalID(rs.getLong("TerminalID"));
		model.SetTimestamp(rs.getLong("Timestamp"));
		return model;
	}

}
