package com.ludong.model;

public class ActionLog {
	long ID;
	String AppKey="";
	String SerialNumber="";
	long TerminalID=0;
	long Timestamp = 0;  //下发时间戳会携带到终端指令 6字节
	String ActionCode=""; //在读取指令返回的状态时，不存在ActionName,只能以Code来记录
	String ActionName="";//用以确认请求当时的ActionName
	Object Parameters;
	int Status=0;
	long SamplingTime=0; //采样时间，表示终端应答或者服务器时间
	long AddTime ;
	
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public String getActionName() {
		return ActionName;
	}
	public void setActionName(String actionName) {
		ActionName = actionName;
	}

	 public void SetAddTime(long addTime)
	    {
	        this.AddTime= addTime;
	    }
	    public long GetAddTime()
	    {
	        return this.AddTime;
	    }
	
	 public void SetSamplingTime(long samplingTime)
	    {
	        this.SamplingTime= samplingTime;
	    }
	    public long GetSamplingTime()
	    {
	        return this.SamplingTime;
	    }
	    
	 public void SetParameters(Object parameters)
	    {
	        this.Parameters= parameters;
	    }
	    public Object GetParameters()
	    {
	        return this.Parameters;
	    }
	    
	    public void SetActionCode(String actionCode)
	    {
	        this.ActionCode= actionCode;
	    }
	    public String GetActionCode()
	    {
	        return this.ActionCode;
	    }
	
	 public void SetTimestamp(long timestamp)
	    {
	        this.Timestamp= timestamp;
	    }
	    public long GetTimestamp()
	    {
	        return this.Timestamp;
	    }
	    
	    
	
	 public void SetTerminalID(long terminalID)
	    {
	        this.TerminalID= terminalID;
	    }
	    public long GetTerminalID()
	    {
	        return this.TerminalID;
	    }
	
	 public void SetSerialNumber(String serialNumber)
	    {
	        this.SerialNumber= serialNumber;
	    }
	    public String GetSerialNumber()
	    {
	        return this.SerialNumber;
	    }
	
	 public void SetAppKey(String appKey)
	    {
	        this.AppKey= appKey;
	    }
	    public String GetAppKey()
	    {
	        return this.AppKey;
	    }
	
	  public void SetStatus(int status)
	    {
	        this.Status= status;
	    }
	    public int GetStatus()
	    {
	        return this.Status;
	    }
}
