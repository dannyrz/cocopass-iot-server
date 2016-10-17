package com.cocopass.iot.model;

public class OnLineShaft {
	long ID;
	long TerminalID;
	boolean IsOnline;
	long AddTime;
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public long getTerminalID() {
		return TerminalID;
	}
	public void setTerminalID(long terminalID) {
		TerminalID = terminalID;
	}
	public boolean isIsOnline() {
		return IsOnline;
	}
	public void setIsOnline(boolean isOnline) {
		IsOnline = isOnline;
	}
	public long getAddTime() {
		return AddTime;
	}
	public void setAddTime(long addTime) {
		AddTime = addTime;
	}

}
