package com.cocopass.dal.result.convert;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.dave.common.database.convert.ResultConverter;
import com.google.gson.JsonObject;

public class PushData  implements ResultConverter<com.cocopass.iot.model.PushData>{
	@Override
	public com.cocopass.iot.model.PushData convert(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		com.cocopass.iot.model.PushData model = new com.cocopass.iot.model.PushData();
		model.setNO(rs.getLong("NO"));
		model.setID(rs.getString("ID"));
		model.setResponse(rs.getString("Response"));
		model.setSign(rs.getString("Sign"));
		model.SetAppKey(rs.getString("Appkey")); 
//		model.SetBody((JsonObject)(rs.getObject("Body"))); //ÓÐ´ýÑéÖ¤
		model.SetDataTypeID(rs.getInt("DataTypeID"));
		model.SetTimeStamp(rs.getLong("TimeStamp")); 
		model.SetVersion(rs.getFloat("Version"));
		return model;
	}

}
