package com.cocopass.dal.result.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dave.common.database.convert.ResultConverter;

public class Terminal  implements ResultConverter<com.cocopass.iot.model.Terminal>{

	@Override
	public com.cocopass.iot.model.Terminal convert(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		com.cocopass.iot.model.Terminal model = new com.cocopass.iot.model.Terminal();
		model.setAddDate(rs.getLong("AddDate"));
		model.setFirmwareVersion(rs.getFloat("FirmwareVersion")); 
		model.setICCID(rs.getString("ICCID"));
		model.setID(rs.getLong("ID"));
		model.setIMEI(rs.getString("IMEI"));
		model.setProtocolVersion(rs.getFloat("ProtocolVersion")); 
		model.setReceiveHost(rs.getString("ReceiveHost")); 
		model.setReceiveTCPPort(rs.getInt("ReceiveTCPPort"));
		model.setReceiveUDPPort(rs.getInt("ReceiveUDPPort")); 
		model.setStatus(rs.getInt("Status")); 
		model.setSupplierCode(rs.getString("SupplierCode"));
		model.setTypeID(rs.getInt("TypeID")); 
		model.setValidDate(rs.getLong("ValidDate")); 
	 
		return model;
	}
	

}
