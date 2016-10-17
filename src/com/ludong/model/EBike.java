package com.ludong.model;

public class EBike {
   public String getBSM() {
		return BSM;
	}
	public void setBSM(String bSM) {
		BSM = bSM;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPersonalName() {
		return PersonalName;
	}
	public void setPersonalName(String personalName) {
		PersonalName = personalName;
	}
	public int getColorID() {
		return ColorID;
	}
	public void setColorID(int colorID) {
		ColorID = colorID;
	}
	public String getCityCode() {
		return CityCode;
	}
	public void setCityCode(String cityCode) {
		CityCode = cityCode;
	}
	public long getAddTime() {
		return AddTime;
	}
	public void setAddTime(long addTime) {
		AddTime = addTime;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
String BSM="";
   String Model="";
   String Name="";
   String PersonalName="";
   int ColorID=0;
   String CityCode="";
   long AddTime=0;
   int Status=0;
   float StandardKilometre=0;
public float getStandardKilometre() {
	return StandardKilometre;
}
public void setStandardKilometre(float standardKilometre) {
	StandardKilometre = standardKilometre;
}
}
