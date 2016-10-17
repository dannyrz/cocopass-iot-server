package com.kl.nts.json;

public class CRouteInfo
{
//	ReceiveInfo ReceiveInfo;
//	public ReceiveInfo GetReceiveInfo() {
//        return ReceiveInfo;
//    }
//    public void SetReceiveInfo(ReceiveInfo receiveInfo) {
//       this.ReceiveInfo=receiveInfo;
//    }
	private String RecIP;
    private int RecTCPPort;
    private int RecUDPPort;
    
    public String GetRecIP() {
        return RecIP;
    }
    public void SetRecIP(String ip) {
       this.RecIP=ip;
    }

    public int GetRecTCPPort() {
        return RecTCPPort;
    }
    public void SetRecTCPPort(int tcpPort) {
        this.RecTCPPort=tcpPort;
     }
    
    public int GetRecUDPPort() {
        return RecUDPPort;
    }
    public void SetRecUDPPort(int udpPort) {
        this.RecUDPPort=udpPort;
     }
}
