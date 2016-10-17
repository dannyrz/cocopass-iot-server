package com.ludong.model;

import java.util.List;

public class Station {
	long TerminalID=0;
	public long getTerminalID() {
		return TerminalID;
	}
	public void setTerminalID(long terminalID) {
		TerminalID = terminalID;
	}
	public int getPortNum() {
		return PortNum;
	}
	public void setPortNum(int portNum) {
		PortNum = portNum;
	}
	public List<PileInfo> getPileData() {
		return ListPileData;
	}
	public void setPileData(List<PileInfo> listPileData) {
		ListPileData = listPileData;
	}
	int PortNum=0;
	List<PileInfo> ListPileData;
}