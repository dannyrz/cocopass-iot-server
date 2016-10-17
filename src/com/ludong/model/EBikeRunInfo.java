package com.ludong.model;

public class EBikeRunInfo {
	 private com.ludong.model.GPSInfo GPSInfo  ;
     private com.ludong.model.BatteryInfo BatteryInfo  ;
     private com.ludong.model.ControllerInfo ControllerInfo  ;
     private com.ludong.model.Alarm Alarm  ;

	    public com.ludong.model.GPSInfo GetGPSInfo() {
	        return GPSInfo;
	    }
	    public void SetGPSInfo(com.ludong.model.GPSInfo gpsInfo) {
	       this.GPSInfo=gpsInfo;
	    }
	    public com.ludong.model.BatteryInfo GetBatteryInfo() {
	        return BatteryInfo;
	    }
	    public void SetBatteryInfo(com.ludong.model.BatteryInfo batteryInfo) {
	       this.BatteryInfo=batteryInfo;
	    }
	    public com.ludong.model.ControllerInfo GetControllerInfo() {
	        return ControllerInfo;
	    }
	    public void SetControllerInfo(com.ludong.model.ControllerInfo controllerInfo) {
	       this.ControllerInfo=controllerInfo;
	    }
	    public com.ludong.model.Alarm GetAlarm() {
	        return Alarm;
	    }
	    public void SetAlarm(com.ludong.model.Alarm alarm) {
	       this.Alarm=alarm;
	    }
}
