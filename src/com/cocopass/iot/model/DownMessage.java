package com.cocopass.iot.model;

public class DownMessage {
     long TerminalID=0;
     String HexPacket;
     byte[] Packet;
     
     public String GetHexPacket()
     {
    	 return this.HexPacket;
     }
     public void SetHexPacket(String hexPacket)
     {
    	 this.HexPacket=hexPacket;
     }
     public long GetTerminalID()
     {
    	 return this.TerminalID;
     }
     public void SetTerminalID(long terminalID)
     {
    	 this.TerminalID=terminalID;
     }
     public byte[] GetPacket()
     {
    	 return this.Packet;
     }
     public void SetPacket(byte[] packet)
     {
    	 this.Packet=packet;
     }
}
