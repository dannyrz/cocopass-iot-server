package com.ludong.model;

public class ActionLog {
	long ID;
	String AppKey="";
	String SerialNumber="";
	long TerminalID=0;
	long Timestamp = 0;  //�·�ʱ�����Я�����ն�ָ�� 6�ֽ�
	String ActionCode=""; //�ڶ�ȡָ��ص�״̬ʱ��������ActionName,ֻ����Code����¼
	String ActionName="";//����ȷ������ʱ��ActionName
	Object Parameters;
	int Status=0;
	long SamplingTime=0; //����ʱ�䣬��ʾ�ն�Ӧ����߷�����ʱ��
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
