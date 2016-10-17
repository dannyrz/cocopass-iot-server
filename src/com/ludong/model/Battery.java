package com.ludong.model;

public class Battery {
	private int TypeID=0;
	  public int GetTypeID() {
	        return TypeID;
	    }
	    public void SetTypeID(int typeID) {
	        this.TypeID=typeID;
	     }
	    
	    private int ItemNum=0;
		  public int GetItemNum() {
		        return ItemNum;
		    }
		    public void SetItemNum(int itemNum) {
		        this.ItemNum=itemNum;
		     }
		    
		    private String Name="";
			  public String GetName() {
			        return Name;
			    }
			    public void SetName(String name) {
			        this.Name=name;
			     }
}
