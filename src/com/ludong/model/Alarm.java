package com.ludong.model;

public class Alarm {
	   private long TerminalID = 0;
	   private int AlarmNO=-1;
	   private int AlarmParam=0;
       private long SamplingTime ;
       private long WrittenIntoSystemTime ;
       private boolean IsRealTme=true;
       private float Version=0;
       public float GetVersion() {
	        return Version;
	    }
	    public void SetVersion(float version) {
	        this.Version=version;
	     }
       
	    public boolean GetIsRealTime() {
	        return IsRealTme;
	    }
	    public void SetIsRealTime(boolean isRealTime) {
	        this.IsRealTme=isRealTime;
	     }
	    
       public long GetTerminalID() {
	        return TerminalID;
	    }
	    public void SetTerminalID(long terminalID) {
	       this.TerminalID=terminalID;
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
}
