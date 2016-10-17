package com.ludong.dal;

import java.sql.Connection;
import java.util.List;

import org.dave.common.database.access.DataAccess;
import org.dave.common.database.convert.IntegerConverter;
import org.dave.common.database.convert.LongConverter;

public class BatteryInfo extends DataAccess{
	
	public BatteryInfo(Connection conn) {
		super(conn);
	}
 

	
	public  void AddMutilList(String  TableName,List<com.ludong.model.BatteryInfo> list){		 
		 String prefix="INSERT INTO "+TableName+"(TerminalID,SamplingTime,TypeID,VoltageValueList,RechargeVoltage"
					+ " ,RechargeCurrent,ResidualCapacity,RechargeTimes,Temperature,ChargeNumeric,HealthCondition"				 
					+ " ,WrittenIntoSystemTime,IsRealTime,Version,ServerSamplingTime,PilePort,SessionID,ChargingPortStatus,EBikeRFID, "
					+" ChargingDuration,ChargingPower,AnticipateKilometre ) VALUES";
		StringBuffer suffix = new StringBuffer();  
		for (com.ludong.model.BatteryInfo model:list) {  
			String cloumnValue=String.format("(%s,%s,%s,'%s',%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s),",  model.GetTerminalID(), model.GetSamplingTime(), model.GetTypeID(), model.GetVoltageValueList(), model.GetRechargeVoltage(), 
					model.GetRechargeCurrent(), model.GetResidualCapacity(), model.GetRechargeTimes(), model.GetTemperature(), model.GetChargeNumeric(), model.GetHealthCondition(), 
					model.GetWrittenIntoSystemTime(), model.GetIsRealTime(), model.GetVersion(), model.getServerSamplingTime(),model.getPilePort(),model.getSessionID(),model.getChargingPortStatus(),model.getEBikeRFID(),
					model.getChargingDuration(),model.getChargingPower(),model.getAnticipateKilometre());
            suffix.append(cloumnValue);  
        }  
        String sql = prefix + suffix.substring(0, suffix.length() - 1);  

        super.insertMulti(sql); 
	}
	
	
	/**
	 * 增加一行GPS数据
	 * @param 所有字段参数
	 * @return
	 */
 
	/*
	public  long Add(String TableName,com.ludong.model.GPSInfo gpsInfo)
	{
		long TerminalID=gpsInfo.GetTerminalID();
		float Longitude=gpsInfo.GetLongitude();
		float Latitude=gpsInfo.GetLatitude();
		float Speed=gpsInfo.GetSpeed();
		int Direction=gpsInfo.GetDirection();
		boolean PositionState=gpsInfo.GetPositionState();
		int  AntennaState=gpsInfo.GetAntennaState();
		int PowerState=gpsInfo.GetPowerState();
		float TotalMileage=gpsInfo.GetTotalMileage();
		long SamplingTime=gpsInfo.GetSamplingTime();
		long WrittenIntoSystemTime=gpsInfo.GetWrittenIntoSystemTime();
		String GSMStationID=gpsInfo.GetGSMStationID();
		String GSMStationAreaID=gpsInfo.GetGSMStationAreaID();
		int GSMSignalValue=gpsInfo.GetGSMSignalValue();
		int GPSSignalValue=gpsInfo.GetGPSSignalValue();
		float BuiltInBatteryVoltageQuantity=gpsInfo.GetBuiltInBatteryVoltageQuantity();
		float ExternalVoltage=gpsInfo.GetExternalVoltage();
		boolean IsDefence=gpsInfo.GetIsDefence();
		boolean IsMosOpen=gpsInfo.GetIsMosOpen();
		float Version=gpsInfo.GetVersion();
		boolean IsRealTime=gpsInfo.GetIsRealTime();
		int AlarmNO=gpsInfo.GetAlarmNO();
		int AlarmParam=gpsInfo.GetAlarmParam();
		String GDLocation=gpsInfo.getGDLocation();
		String BDLocation=gpsInfo.getBDLocation();
		String Address=gpsInfo.getAddress();
		boolean result=false;
		String sql="INSERT INTO "+TableName+"(TerminalID,Longitude,Latitude,Speed,Direction,PositionState,"
				+ "AntennaState,PowerState,TotalMileage,"
				+ "SamplingTime,WrittenIntoSystemTime,GSMStationID,GSMStationAreaID,GSMSignalValue,"
				+ "GPSSignalValue,BuiltInBatteryVoltageQuantity,"
				+ "ExternalVoltage,IsDefence,IsMosOpen,Version,IsRealTime,AlarmNO,AlarmParam,"
				+ "GDLocation,BDLocation,Address) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		long id= super.insert(sql, new LongConverter(), TerminalID,Longitude,Latitude,Speed,Direction,PositionState,AntennaState,
				PowerState,TotalMileage,SamplingTime,WrittenIntoSystemTime,GSMStationID,GSMStationAreaID,GSMSignalValue,
				GPSSignalValue,BuiltInBatteryVoltageQuantity,
						 ExternalVoltage,IsDefence,IsMosOpen,Version,IsRealTime,AlarmNO,AlarmParam,GDLocation,BDLocation,Address);
		return id;
			
	}
	*/
	
	/**
	 * 查询符合条件的GPS历史信息
	 * @return
	 */
	public List<com.ludong.model.BatteryInfo> GetList(String condition,int page,int pageNum,List<String> tables)
	{
		StringBuilder sqlStringBuilder = new StringBuilder();
		for(String table:tables)
		{
			sqlStringBuilder.append(" UNION SELECT * FROM "+table );
			if(condition!=null&&!condition.trim().equals(""))
			{
				sqlStringBuilder.append(" WHERE  "+ condition );
			}
		}
		
		String sql=sqlStringBuilder.toString().replaceFirst("UNION", "");
		
		int startRecord=(page-1)*pageNum;
		sql=String.format("SELECT * FROM (%s) AS TG ORDER BY SamplingTime DESC LIMIT "+startRecord+","+pageNum, sql);
		
		return super.queryForList(sql, new com.ludong.bll.result.convert.BatteryInfo());
	}
	
	public long GetCount(String condition,List<String> tables){
		
		StringBuilder sqlStringBuilder = new StringBuilder();
		for(String table:tables)
		{
			sqlStringBuilder.append(" UNION SELECT * FROM "+table );
			if(condition!=null&&!condition.trim().equals(""))
			{
				sqlStringBuilder.append(" WHERE  "+ condition );
			}
		}
		
		String sql=sqlStringBuilder.toString().replaceFirst("UNION", "");
		
		sql=String.format("SELECT Count(0) FROM (%s) AS TG ", sql);
		return super.queryForObject(sql, new LongConverter());
	}

}



