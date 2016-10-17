package com.kl.nts.json;

public class ReceiveInfo {
    private String IP;
    private int TCPPort;
    private int UDPPort;

    public ReceiveInfo() {
    }



    public String GetIP() {
        return IP;
    }
    public void SetIP(String ip) {
       this.IP=ip;
    }

    public int GetTCPPort() {
        return TCPPort;
    }
    public void SetTCPPort(int tcpPort) {
        this.TCPPort=tcpPort;
     }
    
    public int GetUDPPort() {
        return UDPPort;
    }
    public void SetUDPPort(int udpPort) {
        this.UDPPort=udpPort;
     }
}