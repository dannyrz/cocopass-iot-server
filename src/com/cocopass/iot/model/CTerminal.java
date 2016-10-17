package com.cocopass.iot.model;

import io.netty.channel.ChannelHandlerContext;

public class CTerminal {
	long ID;
	ChannelHandlerContext CChannelHandlerContext;
	long LastOnlineTime;
	
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public ChannelHandlerContext getCChannelHandlerContext() {
		return CChannelHandlerContext;
	}
	public void setCChannelHandlerContext(ChannelHandlerContext cChannelHandlerContext) {
		CChannelHandlerContext = cChannelHandlerContext;
	}
	public long getLastOnlineTime() {
		return LastOnlineTime;
	}
	public void setLastOnlineTime(long lastOnlineTime) {
		LastOnlineTime = lastOnlineTime;
	}
}
