package com.ludong.bll;

import com.google.gson.Gson;


public class Battery {
	static Gson gson=new Gson();
public static int GetItemNum(int typeID)
{
	  int num=typeID;
	  int itemNum=0;
      if ((num > 1) && (num <= 10))
      {
    	  itemNum = 7;
      }
      else if ((num > 10) && (num <= 50))
      {
    	  itemNum = 8;
      }
      else if ((num > 50) && (num <= 100))
      {
    	  itemNum= 10;
     
         
      }
      else if ((num > 100) && (num <= 160))
      {
    	  itemNum = 13;
     
          
      }
      else if ((num > 160) && (num <= 180))
      {
    	  itemNum = 16;
      
         
      }
      else if ((num > 180) && (num <= 190))
      {
    	  itemNum = 18;
   
          
      }
      else if ((num > 190) && (num <= 200))
      {
    	  itemNum= 20;
    
          
      }
      else if ((num > 200) && (num <= 0xcd))
      {
    	  itemNum= 22;
    
         
      }
      else if ((num > 0xce) && (num <= 210))
      {
    	  itemNum= 3;
      
         
      }
      else if ((num > 210) && (num <= 220))
      {
    	  itemNum = 4;
      
          
      }
      else if ((num > 220) && (num <= 230))
      {
    	  itemNum = 5;
     
         
      }
      else if ((num > 230) && (num <= 240))
      {
    	  itemNum = 4;
       
         
      }
      else if ((num > 240) && (num <= 250))
      {
    	  itemNum = 6;
     
          
      }
      else if ((num > 250) && (num <= 0xff))
      {
    	  itemNum = 5;
         
        
      }
      return itemNum;
	}

/** 
* CalculateMileage  
* ����ʣ�����
* @param eBikeRFID 
*  the request send by the client to the server 
* @param percentageAH 
*  ʣ�������ʱ�ٷֱ�
* @throws ServletException 
*  if an error occurred 
* @throws IOException 
*  if an error occurred 
*/ 
public static float CalculateKilometreByEBikeRFID(long eBikeRFID,float percentageAH){
	float s=0;
	float standardMileage=0; //��׼���
	
	String jStrEBikeModel=com.cocopass.helper.CRedis.get("EBike:"+eBikeRFID);
	com.ludong.model.EBike ebike = gson.fromJson(jStrEBikeModel, com.ludong.model.EBike.class);
	standardMileage=Float.parseFloat(com.cocopass.helper.CRedis.get("StandardKilometre:"+ebike.getModel()));
	
	float capacityDecline=CalculateCapacityDecline(ebike.getAddTime());
	//��ȡ��ǰ����
	String strTempture=com.cocopass.helper.CRedis.get("OutDoorTemperature:"+ebike.getCityCode());
	float tempture=20;
	if(strTempture!=null&&strTempture.equals(""))
	{
		tempture=Float.parseFloat(strTempture);
	}
	 
	float dischargeCapacity=CalculateDischargeCapacity(tempture);
	
	s =standardMileage * (1-(capacityDecline+dischargeCapacity)) * percentageAH;
	return s;
}

/** 
* CalculateCapacityDecline  
* ����˥��ֵ
* @param eBikeRFID 
*  the request send by the client to the server 
* @param percentageAH 
*  ʣ�������ʱ�ٷֱ�
*/ 
public static float CalculateCapacityDecline(long firstUsedTime){
	float �� =0;
	long nowTime = System.currentTimeMillis(); 
	long timeSpan = nowTime-firstUsedTime ;
	long perMonthMillis=30*24*60*60*1000;
	int month=(int) (timeSpan/perMonthMillis);
	int monthRemainder=(int) (timeSpan%perMonthMillis);
	
	int step=month/3;
	int stepRemainder=month%3;
//	if(stepRemainder>0)
//	{
//		step+=1;
//	}
	
	 ��=stepRemainder*(step+1)+3*step*(step+1)/2;
	 ��=(float) (��*0.01);

	
//	if(result<4)
//	{
//		result=(float) (month*0.01);
//	}
//	else if(result>3&result<7)
//	{
//		result= (float) (3*(month*0.01)+(month-3)*0.02);
//	}
//	else if(result>6&result<10)
//	{
//		result= (float) (3*(month*0.01)+(month-3)*0.02);
//	}
	return ��;
 }


/** 
* CalculateDischargeCapacity  
* ����ŵ�����
* @param tempture 
*  �����¶�
*/ 
public static float CalculateDischargeCapacity(float tempture){
	float �� =0;
	if(tempture<11)
	{
		��=(float) (1+(10-tempture)*0.01);
	}
	else if(tempture>10&tempture<21)
	{
		��=(float) (1+(10-tempture)*0.005);
	}
	else if(tempture>20)
	{
		��=0;
	}
	return ��;
}


}