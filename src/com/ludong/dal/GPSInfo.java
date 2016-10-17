package com.ludong.dal;

import java.sql.Connection;
import java.util.List;

import org.dave.common.database.access.DataAccess;
import org.dave.common.database.convert.LongConverter;

public class GPSInfo extends DataAccess{
	
	public GPSInfo(Connection conn) {
		
		super(conn);
		// TODO Auto-generated constructor stub
	}
 

/**
 * 不用
 * 批量GPS数据 
 * @param 所有字段参数
 * @return
 */
	@Deprecated
	public static int AddList(String sql){
		int result=0;
		com.cocopass.helper.MySql.executeUpdate(sql, null);  
		return result;
	}
	
	public  void AddMutilList(String  TableName,List<com.ludong.model.GPSInfo> list){
		 
		 String prefix="INSERT INTO "+TableName+"(TerminalID,Longitude,Latitude,Speed,Direction,PositionState,"
					+ "AntennaState,PowerState,TotalMileage,"
					+ "SamplingTime,WrittenIntoSystemTime,GSMStationID,GSMStationAreaID,GSMSignalValue,"
					+ "GPSSignalValue,BuiltInBatteryVoltageQuantity,"
					+ "ExternalVoltage,IsDefence,IsMosOpen,Version,IsRealTime,AlarmNO,AlarmParam,"
					+ "GDLocation,BDLocation,Address) VALUES ";
		StringBuffer suffix = new StringBuffer();  
		for (com.ludong.model.GPSInfo gpsInfo:list) { 
            // 构建sql后缀 
			String cloumnValue=String.format("(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,'%s','%s',%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,'%s','%s','%s'),",  gpsInfo.GetTerminalID(), gpsInfo.GetLongitude(), gpsInfo.GetLatitude(), gpsInfo.GetSpeed(), gpsInfo.GetDirection(), gpsInfo.GetPositionState(), gpsInfo.GetAntennaState(), 
				gpsInfo.GetPowerState(), gpsInfo.GetTotalMileage(), gpsInfo.GetSamplingTime(), gpsInfo.GetWrittenIntoSystemTime(), gpsInfo.GetGSMStationID(), gpsInfo.GetGSMStationAreaID(), gpsInfo.GetGSMSignalValue(), 
				gpsInfo.GetGPSSignalValue(), gpsInfo.GetBuiltInBatteryVoltageQuantity(), gpsInfo.GetExternalVoltage(), gpsInfo.GetIsDefence(), gpsInfo.GetIsMosOpen(), gpsInfo.GetVersion(), gpsInfo.GetIsRealTime(), gpsInfo.GetAlarmNO(), gpsInfo.GetAlarmParam(), gpsInfo.getGDLocation(), gpsInfo.getBDLocation(), gpsInfo.getAddress());
            suffix.append(cloumnValue);  
        }  
        // 构建完整sql  
        String sql = prefix + suffix.substring(0, suffix.length() - 1);  
        
        super.insertMulti(sql);
		 
	}
	
	
	/**
	 * 增加一行GPS数据
	 * @param 所有字段参数
	 * @return
	 */
 
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
	
	
	/**
	 * 查询符合条件的GPS历史信息
	 * @return
	 */
	public List<com.ludong.model.GPSInfo> GetList(String condition,int page,int pageNum,List<String> tables)
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
 
		
		return super.queryForList(sql, new com.ludong.bll.result.convert.GPSInfo());
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



