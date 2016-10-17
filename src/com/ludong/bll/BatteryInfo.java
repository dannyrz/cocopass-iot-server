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
	 * �·���
	 */
	public void AddMutilList(List<com.ludong.model.BatteryInfo> list) {

		DatabaseTransaction trans = new DatabaseTransaction(false);
		com.ludong.service.BatteryInfo service = new com.ludong.service.BatteryInfo(trans);
		// Ϊ�˷�ֹ�ն��쳣�¼����֣������ݱ����ڵ��յı�
		String TableName = GetTable(new Date().getTime());
		service.AddMutilList(TableName, list);
		trans.close();

	}

	/**
	 * ����һ��GPS����,2016��д���������°汾�ӿ��в��ô˷��� ä�����������Ҳ���ڱ����¼�����Ǽ�¼��һ��ı�
	 * 
	 * @param �����ֶβ���
	 * @return
	 */
	public long Add(com.ludong.model.GPSInfo gpsInfo) {
		long result = 0;
		DatabaseTransaction trans = new DatabaseTransaction(false);

		com.ludong.service.GPSInfo gpsInfoService = new com.ludong.service.GPSInfo(trans);
		// Ϊ�˷�ֹ�ն��쳣�¼����֣������ݱ����ڵ��յı�
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
		// �ж��������ʼʱ�䣬�����ʼʱ���С�ߵ��������
		if (startTime > endTime) {
			long tempEndTime = endTime;
			endTime = startTime;
			startTime = tempEndTime;
		}

		long now = new Date().getTime();
		String strToday = com.cocopass.helper.CDate.ToString("yyyy-MM-dd", new Date());
		long today = com.cocopass.helper.CDate.ToMillions(strToday, "yyyy-MM-dd");
		// �����ѯʱ����ڵ�ǰ���ڣ��򷵻�null
		if (startTime > today && (startTime - today) > 24 * 3600 * 1000)
			return null;
		// �ȱȽ��ǲ���ͬһ��

		String strStartDate = com.cocopass.helper.CDate.ToString("yyyy-MM-dd",
				com.cocopass.helper.CDate.MillionsToDate(startTime));
		String strEndDate = com.cocopass.helper.CDate.ToString("yyyy-MM-dd",
				com.cocopass.helper.CDate.MillionsToDate(endTime));

		// ֻ��ѯһ�ű�����
		if (strStartDate.equals(strEndDate)) {
			listBatteryInfoTable.add(GetTable(startTime));
		}
		// ���ű�����
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
	 * ��ѯһ��ʱ�����ڵĵ������
	 * 
	 * @param �����ֶβ���
	 * @return
	 * @throws ParseException
	 */
	public List<com.ludong.model.BatteryInfo> GetList(long terminalID, long startTime, long endTime,int page,int pageNum) throws ParseException {
		DatabaseTransaction trans = new DatabaseTransaction(false);
		com.ludong.service.BatteryInfo service = new com.ludong.service.BatteryInfo(trans);
		List<String> listBatteryInfoTable = new ArrayList<String>();
		// �ж��������ʼʱ�䣬�����ʼʱ���С�ߵ��������
		if (startTime > endTime) {
			long tempEndTime = endTime;
			endTime = startTime;
			startTime = tempEndTime;
		}

		long now = new Date().getTime();
		String strToday = com.cocopass.helper.CDate.ToString("yyyy-MM-dd", new Date());
		long today = com.cocopass.helper.CDate.ToMillions(strToday, "yyyy-MM-dd");
		// �����ѯʱ����ڵ�ǰ���ڣ��򷵻�null
		if (startTime > today && (startTime - today) > 24 * 3600 * 1000)
			return null;
		// �ȱȽ��ǲ���ͬһ��

		String strStartDate = com.cocopass.helper.CDate.ToString("yyyy-MM-dd",
				com.cocopass.helper.CDate.MillionsToDate(startTime));
		String strEndDate = com.cocopass.helper.CDate.ToString("yyyy-MM-dd",
				com.cocopass.helper.CDate.MillionsToDate(endTime));

		// ֻ��ѯһ�ű�����
		if (strStartDate.equals(strEndDate)) {
			listBatteryInfoTable.add(GetTable(startTime));
		}
		// ���ű�����
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
	 * ����ʱ���ȡ����
	 * 
	 * @param time
	 * @return ����
	 * @throws ParseException
	 */
	public String GetTable(long time) {
		Date date = com.cocopass.helper.CDate.MillionsToDate(time);
		String strDate = com.cocopass.helper.CDate.ToString("yyyy_MM_dd", date);
		String tableName = "Mile_" + strDate;
		return "BatteryInfo";

	}

}
