package com.ludong.bll;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dave.common.BaseService;
import org.dave.common.database.DatabaseTransaction;

import com.cocopass.helper.CDate;
import com.cocopass.iot.model.*;
import com.kl.nts.server.tcp.NettyTCPServerHandler;

public class BatteryInfo {

	/*
	 * 新方法
	 */
	public void AddMutilList(List<com.ludong.model.BatteryInfo> list) {

		DatabaseTransaction trans = new DatabaseTransaction(false);
		com.ludong.service.BatteryInfo service = new com.ludong.service.BatteryInfo(trans);
		// 为了防止终端异常事件出现，将数据保存在当日的表
		String TableName = GetTable(new Date().getTime());
		service.AddMutilList(TableName, list);
		trans.close();

	}

	/**
	 * 增加一行GPS数据,2016新写方法，在新版本接口中采用此方法 盲区补足的数据也将在本表记录，而非记录那一天的表
	 * 
	 * @param 所有字段参数
	 * @return
	 */
	public long Add(com.ludong.model.GPSInfo gpsInfo) {
		long result = 0;
		DatabaseTransaction trans = new DatabaseTransaction(false);

		com.ludong.service.GPSInfo gpsInfoService = new com.ludong.service.GPSInfo(trans);
		// 为了防止终端异常事件出现，将数据保存在当日的表
		String TableName = GetTable(gpsInfo.GetSamplingTime());
		// String TableName=GetTable(new Date().getTime());

		result = gpsInfoService.Add(TableName, gpsInfo);

		return result;
	}

	
	@SuppressWarnings("unchecked")
	public <T> CPageRecord<T> GetPageRecord(long terminalID, long startTime, long endTime, int page, int pageNum,boolean isCount) throws ParseException{
		
		DatabaseTransaction trans = new DatabaseTransaction(false);
		com.ludong.service.BatteryInfo service=new com.ludong.service.BatteryInfo(trans);

		List<String> listBatteryInfoTable = new ArrayList<String>();
		// 判断输入的起始时间，如果起始时间大小颠倒，则调换
		if (startTime > endTime) {
			long tempEndTime = endTime;
			endTime = startTime;
			startTime = tempEndTime;
		}

		long now = new Date().getTime();
		String strToday = com.cocopass.helper.CDate.ToString("yyyy-MM-dd", new Date());
		long today = com.cocopass.helper.CDate.ToMillions(strToday, "yyyy-MM-dd");
		// 如果查询时间大于当前日期，则返回null
		if (startTime > today && (startTime - today) > 24 * 3600 * 1000)
			return null;
		// 先比较是不是同一天

		String strStartDate = com.cocopass.helper.CDate.ToString("yyyy-MM-dd",
				com.cocopass.helper.CDate.MillionsToDate(startTime));
		String strEndDate = com.cocopass.helper.CDate.ToString("yyyy-MM-dd",
				com.cocopass.helper.CDate.MillionsToDate(endTime));

		// 只查询一张表的情况
		if (strStartDate.equals(strEndDate)) {
			listBatteryInfoTable.add(GetTable(startTime));
		}
		// 多张表的情况
		else {
			long snap = 24 * 3600 * 1000;
			String todayTable = GetTable(now);
			for (int i = 0; i < 100; i++) {
				long time = startTime + i * snap;
				String table = GetTable(time);
				listBatteryInfoTable.add(table);
				if (table.equals(todayTable))
					break;
			}
		}
		
		String condition= GetCondition( terminalID,  startTime,  endTime);

		List<com.ludong.model.BatteryInfo> list = null;

		list = service.GetList(condition, page, pageNum, listBatteryInfoTable);

		
		 long count = 0;
		 if(isCount){
		  count=service.GetCount(condition,listBatteryInfoTable);
		 }
		 trans.close();
		 CPageRecord<T> result=new  CPageRecord<T>();
		 result.setRecordsCount(count);
		 result.setRecords((List<T>) list);
		 
		 trans.close(); 
		 return result;
		
	}
	
	/**
	 * 查询一个时间间隔内的电池数据
	 * 
	 * @param 所有字段参数
	 * @return
	 * @throws ParseException
	 */
	public List<com.ludong.model.BatteryInfo> GetList(long terminalID, long startTime, long endTime,int page,int pageNum) throws ParseException {
		DatabaseTransaction trans = new DatabaseTransaction(false);
		com.ludong.service.BatteryInfo service = new com.ludong.service.BatteryInfo(trans);
		List<String> listBatteryInfoTable = new ArrayList<String>();
		// 判断输入的起始时间，如果起始时间大小颠倒，则调换
		if (startTime > endTime) {
			long tempEndTime = endTime;
			endTime = startTime;
			startTime = tempEndTime;
		}

		long now = new Date().getTime();
		String strToday = com.cocopass.helper.CDate.ToString("yyyy-MM-dd", new Date());
		long today = com.cocopass.helper.CDate.ToMillions(strToday, "yyyy-MM-dd");
		// 如果查询时间大于当前日期，则返回null
		if (startTime > today && (startTime - today) > 24 * 3600 * 1000)
			return null;
		// 先比较是不是同一天

		String strStartDate = com.cocopass.helper.CDate.ToString("yyyy-MM-dd",
				com.cocopass.helper.CDate.MillionsToDate(startTime));
		String strEndDate = com.cocopass.helper.CDate.ToString("yyyy-MM-dd",
				com.cocopass.helper.CDate.MillionsToDate(endTime));

		// 只查询一张表的情况
		if (strStartDate.equals(strEndDate)) {
			listBatteryInfoTable.add(GetTable(startTime));
		}
		// 多张表的情况
		else {
			long snap = 24 * 3600 * 1000;
			String todayTable = GetTable(now);
			for (int i = 0; i < 100; i++) {
				long time = startTime + i * snap;
				String table = GetTable(time);
				listBatteryInfoTable.add(table);
				if (table.equals(todayTable))
					break;
			}
		}
		
		String condition= GetCondition( terminalID,  startTime,  endTime);

		List<com.ludong.model.BatteryInfo> list = null;

		list = service.GetList(condition, page, pageNum, listBatteryInfoTable);

		trans.close();
		// trans.commit();

		return list;
	}
	
	public String GetCondition(long terminalID, long startTime, long endTime){
		String condition = " TerminalID>0 AND (SamplingTime BETWEEN " + startTime + " AND " + endTime + ")";
		if (terminalID > 0) {
			condition += " AND TerminalID=" + String.valueOf(terminalID);
		}
		return condition;
	}

	/**
	 * 根据时间获取表名
	 * 
	 * @param time
	 * @return 表名
	 * @throws ParseException
	 */
	public String GetTable(long time) {
		Date date = com.cocopass.helper.CDate.MillionsToDate(time);
		String strDate = com.cocopass.helper.CDate.ToString("yyyy_MM_dd", date);
		String tableName = "Mile_" + strDate;
		return "BatteryInfo";

	}

}
