package com.cocopass.iot.model;

import java.net.InetAddress;

public class EndPoint {
	String IP=null;
	int Port =0;
	long Time;
	
	public long GetTime() {
        return Time;
    }
    public void SetTime(long time) {
        this.Time=time;
     }
    public int GetPort() {
        return Port;
    }
    public void SetPort(int port) {
        this.Port=port;
     }
    public String GetIP() {
        return IP;
    }
    public void SetIP(String ip) {
        this.IP=ip;
     }  
    
}
