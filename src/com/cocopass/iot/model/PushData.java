package com.cocopass.iot.model;

import com.google.gson.JsonObject;

public class PushData {
	long NO;
	String ID = "";
	String AppKey = ""; // 保留key是因为在做签名是需要通过key获取secrect
	long TimeStamp = 0;
	float Version = 0;
	int DataTypeID = 0;
	JsonObject Body;
	String Sign = "";
	String Response = "";

	public long getNO() {
		return NO;
	}

	public void setNO(long nO) {
		NO = nO;
	}

	public String getResponse() {
		return Response;
	}

	public void setResponse(String response) {
		Response = response;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void SetDataTypeID(int dataTypeID) {
		this.DataTypeID = dataTypeID;
	}

	public int GetDataTypeID() {
		return this.DataTypeID;
	}

	public void SetBody(JsonObject body) {
		this.Body = body;
	}

	public JsonObject GetBody() {
		return this.Body;
	}

	public void SetTimeStamp(long timeStamp) {
		this.TimeStamp = timeStamp;
	}

	public long GetTimeStamp() {
		return this.TimeStamp;
	}

	public void SetAppKey(String appkey) {
		this.AppKey = appkey;
	}

	public String GetAppKey() {
		return this.AppKey;
	}

	public void SetVersion(float version) {
		this.Version = version;
	}

	public float GetVersion() {
		return this.Version;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

}
