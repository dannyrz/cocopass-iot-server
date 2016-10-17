package com.cocopass.iot.model;

public class Terminal {
	long ID;
	int TypeID;
	String IMEI;
	String ICCID;
	String SupplierCode;
	long ValidDate;
	String ReceiveHost;
	int ReceiveTCPPort;
	int ReceiveUDPPort;
	int Status;
	long AddDate;
	float ProtocolVersion;
	float FirmwareVersion;
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public int getTypeID() {
		return TypeID;
	}
	public void setTypeID(int typeID) {
		TypeID = typeID;
	}
	public String getIMEI() {
		return IMEI;
	}
	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
	public String getSupplierCode() {
		return SupplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		SupplierCode = supplierCode;
	}
	public long getValidDate() {
		return ValidDate;
	}
	public void setValidDate(long validDate) {
		ValidDate = validDate;
	}
	public String getReceiveHost() {
		return ReceiveHost;
	}
	public void setReceiveHost(String receiveHost) {
		ReceiveHost = receiveHost;
	}
	public int getReceiveTCPPort() {
		return ReceiveTCPPort;
	}
	public void setReceiveTCPPort(int receiveTCPPort) {
		ReceiveTCPPort = receiveTCPPort;
	}
	public int getReceiveUDPPort() {
		return ReceiveUDPPort;
	}
	public void setReceiveUDPPort(int receiveUDPPort) {
		ReceiveUDPPort = receiveUDPPort;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public long getAddDate() {
		return AddDate;
	}
	public void setAddDate(long addDate) {
		AddDate = addDate;
	}
	public float getProtocolVersion() {
		return ProtocolVersion;
	}
	public void setProtocolVersion(float protocolVersion) {
		ProtocolVersion = protocolVersion;
	}
	public float getFirmwareVersion() {
		return FirmwareVersion;
	}
	public void setFirmwareVersion(float firmwareVersion) {
		FirmwareVersion = firmwareVersion;
	}

}
