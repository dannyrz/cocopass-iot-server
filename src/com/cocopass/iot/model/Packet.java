package com.cocopass.iot.model;

public class Packet {
	 String Hex;
	 byte[] Head;
	 private float Version=0;
	 byte Tail ;
	 byte CheckValue;
	 int Length;
	 int Signaling;
	 String GPSID;
	 byte[] Body;
	 Object Model;
	 
	 public Object GetModel() {
	        return Model;
	    }
	 public void SetModel(Object Model) {
	        this.Model=Model;
	     }
	 public String GetGPSID() {
	        return GPSID;
	    }
	 public void SetGPSID(String GPSID) {
	        this.GPSID=GPSID;
	     }
	 public String GetHex() {
	        return Hex;
	    }
	 public void SetHex(String Hex) {
	        this.Hex=Hex;
	     }
	 public byte[] GetBody() {
	        return Body;
	    }
	 public void SetBody(byte[] Body) {
	        this.Body=Body;
	     }
	 public byte[] GetHead() {
	        return Head;
	    }
	 public void SetHead(byte[] Head) {
	        this.Head=Head;
	     }
	 public byte  GetTail() {
	        return Tail;
	    }
	 public void SetTail(byte  Tail) {
	        this.Tail=Tail;
	     }
	 public byte  GetCheckValue() {
	        return CheckValue;
	    }
	 public void SetCheckValue(byte  CheckValue) {
	        this.CheckValue=CheckValue;
	     }
	 public int  GetLength() {
	        return Length;
	    }
	 public void SetLength(int  Length) {
	        this.Length=Length;
	     }
	 public int  GetSignaling() {
	        return Signaling;
	    }
	 public void SetSignaling(int  Signaling) {
	        this.Signaling=Signaling;
	     }
     public float GetVersion() {
	        return Version;
	    }
	 public void SetVersion(float version) {
	        this.Version=version;
	     }
}
