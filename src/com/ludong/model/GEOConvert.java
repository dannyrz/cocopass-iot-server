package com.ludong.model;

import java.util.List;

public class GEOConvert {

	int status=0;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<Location> getResult() {
		return result;
	}
	public void setResult(List<Location> result) {
		this.result = result;
	}
	List<Location> result=null;
	
	String info="";
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getInfocode() {
		return infocode;
	}
	public void setInfocode(String infocode) {
		this.infocode = infocode;
	}
	public String getLocations() {
		return locations;
	}
	public void setLocations(String locations) {
		this.locations = locations;
	}
	String infocode="";
	String locations="";
}
