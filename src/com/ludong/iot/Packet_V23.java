package com.ludong.iot;

import com.cocopass.helper.CByte;

public class Packet_V23  extends Packet_V21{
	public Packet_V23(byte[] buffer) {
		super(buffer);
		if(buffer!=null)
		{
		
		}
	}
	
	/**
	 * 该方法在PV20也存在，但是为了继承透传，所以在这里重写覆盖V21的方法
	 */
	public byte[] CreateSwitchEBikePowerPacket(long timestamp,long terminalID, byte onORoff) {
		// TODO Auto-generated method stub
	
		byte[] byteArrayTerminalID=CByte.longToBytes(terminalID);
		byte[] packet=new byte[19];
        packet[0] = 0x29;
        packet[1] = 0x29;
        packet[2] = (byte) 0x96;
        packet[3] = 0x58;
        packet[4] = 0;
        packet[5] = 13;
        packet[6] = byteArrayTerminalID[4];
        packet[7] = byteArrayTerminalID[5];
        packet[8] = byteArrayTerminalID[6];
        packet[9] = byteArrayTerminalID[7];
        
        byte[] timestampByteArray=com.cocopass.helper.CByte.longToBytes(timestamp);
        
        packet[10] =  timestampByteArray[2];
        packet[11] =  timestampByteArray[3];
        packet[12] =  timestampByteArray[4];
	    packet[13] =  timestampByteArray[5];
	    packet[14] =  timestampByteArray[6];
	    packet[15] =  timestampByteArray[7];
	    
	    packet[16] =  onORoff;
        packet[17] = CreateCheckValue(packet);
        packet[18] = 0x0D;
        
		return packet;

	}
}
