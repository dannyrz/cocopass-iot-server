package com.ludong.model;

public class GPSInfo {
	
	 private long ID = 0;
	 private long TerminalID = 0;
     private float Longitude = 0;
     private float Latitude = 0;
     private int Speed=0;
     private int Direction=0;
     private boolean PositionState=false;
     private int AntennaState = 0;//3 正常  2 天线短路  1 天线开路，没连接上卫星或者连接卫星数《3
     private int PowerState = 0;// 7 正常   5 主电源掉电   6 主电源电压过高   3 电压过低
     private float TotalMileage=0;
     private long  SamplingTime;
     private long WrittenIntoSystemTime;
     private String GSMStationID="";
     private String GSMStationAreaID="";
     private int GSMSignalValue =0;
     private int GPSSignalValue=0;
     private float BuiltInBatteryVoltageQuantity=0;
     private float ExternalVoltage=0;
     private boolean IsDefence=false;
     private boolean IsMosOpen=false;
     private float Version=0;
     private boolean IsRealTime=true;
     private int AlarmNO= 255;
     private int AlarmParam=0;
     String GDLocation="";
     String BDLocation="";
     String Address="";
     
     
     
     
     public long getID() {
		return ID;
	}



	public void setID(long iD) {
		ID = iD;
	}



	public String getGDLocation() {
		return GDLocation;
	}



	public void setGDLocation(String gDLocation) {
		GDLocation = gDLocation;
	}



	public String getBDLocation() {
		return BDLocation;
	}



	public void setBDLocation(String bDLocation) {
		BDLocation = bDLocation;
	}



	public String getAddress() {
		return Address;
	}



	public void setAddress(String address) {
		Address = address;
	}
	
     

	 public GPSInfo() {
		 
	 }



	    public long GetTerminalID() {
	        return TerminalID;
	    }
	    public void SetTerminalID(long terminalID) {
	       this.TerminalID=terminalID;
	    }

	    
	    public float GetLongitude() {
	        return Longitude;
	    }
	    public void SetLongitude(float longitude) {
	        this.Longitude=longitude;
	     }
	    
	    
	    public float GetLatitude() {
	        return Latitude;
	    }
	    public void SetLatitude(float latitude) {
	        this.Latitude=latitude;
	     }
	    
	    
	    
	    public int GetSpeed() {
	        return Speed;
	    }
	    public void SetSpeed(int speed) {
	        this.Speed=speed;
	     }
	    
	    
	    
	    public int GetDirection() {
	        return Direction;
	    }
	    public void SetDirection(int direction) {
	        this.Direction=direction;
	     }
	    
	    
	    public boolean GetPositionState() {
	        return PositionState;
	    }
	    public void SetPositionState(boolean positionState) {
	        this.PositionState=positionState;
	     }
	    
	    
	    public int GetAntennaState() {
	        return AntennaState;
	    }
	    public void SetAntennaState(int antennaState) {
	        this.AntennaState=antennaState;
	     }
 
	    
	    
	    public int GetPowerState() {
	        return PowerState;
	    }
	    public void SetPowerState(int powerState) {
	        this.PowerState=powerState;
	     }
 
	    
	    
	    public float GetTotalMileage() {
	        return TotalMileage;
	    }
	    public void SetTotalMileage(float totalMileage) {
	        this.TotalMileage=totalMileage;
	     }
	    
	    
	    public  long GetSamplingTime() {
	        return SamplingTime;
	    }
	    public void SetSamplingTime( long samplingTime) {
	        this.SamplingTime=samplingTime;
	     }
	    
	    
	    public long GetWrittenIntoSystemTime() {
	        return WrittenIntoSystemTime;
	    }
	    public void SetWrittenIntoSystemTime( long writtenIntoSystemTime) {
	        this.WrittenIntoSystemTime=writtenIntoSystemTime;
	     }
	    
	    
	    public String GetGSMStationID() {
	        return GSMStationID;
	    }
	    public void SetGSMStationID(String gsmStationID) {
	        this.GSMStationID=gsmStationID;
	     }
	    
	    
	    public String GetGSMStationAreaID() {
	        return GSMStationAreaID;
	    }
	    public void SetGSMStationAreaID(String gsmStationAreaID) {
	        this.GSMStationAreaID=gsmStationAreaID;
	     }
	    
	    
	    public int GetGSMSignalValue() {
	        return GSMSignalValue;
	    }
	    public void SetGSMSignalValue(int gsmSignalValue) {
	        this.GSMSignalValue=gsmSignalValue;
	     }
	    
	    
	    public int GetGPSSignalValue() {
	        return GPSSignalValue;
	    }
	    public void SetGPSSignalValue(int gpsSignalValue) {
	        this.GPSSignalValue=gpsSignalValue;
	     }
	    
	    
	    public float GetBuiltInBatteryVoltageQuantity() {
	        return BuiltInBatteryVoltageQuantity;
	    }
	    public void SetBuiltInBatteryVoltageQuantity(int builtInBatteryVoltageQuantity) {
	        this.BuiltInBatteryVoltageQuantity=builtInBatteryVoltageQuantity;
	     }
	    
	    
	    public float GetExternalVoltage() {
	        return ExternalVoltage;
	    }
	    public void SetExternalVoltage(float externalVoltage) {
	        this.ExternalVoltage=externalVoltage;
	     }
	    
	    
	    public boolean GetIsDefence() {
	        return IsDefence;
	    }
	    public void SetIsDefence(boolean isDefence) {
	        this.IsDefence=isDefence;
	     }
	    
	    public boolean GetIsMosOpen() {
	        return IsMosOpen;
	    }
	    public void SetIsMosOpen(boolean isMosOpen) {
	        this.IsMosOpen=isMosOpen;
	     }
	    
	    
	    public float GetVersion() {
	        return Version;
	    }
	    public void SetVersion(float version) {
	        this.Version=version;
	     }
 
	    
	    
	    public boolean GetIsRealTime() {
	        return IsRealTime;
	    }
	    public void SetIsRealTime(boolean isRealTime) {
	        this.IsRealTime=isRealTime;
	     }
	    
	    
	 
	    public int GetAlarmNO() {
	        return AlarmNO;
	    }
	    public void SetAlarmNO(int alarmNO) {
	        this.AlarmNO=alarmNO;
	     }
	    
	    public int GetAlarmParam() {
	        return AlarmParam;
	    }
	    public void SetAlarmParam(int alarmParam) {
	        this.AlarmParam=alarmParam;
	     }
 
}