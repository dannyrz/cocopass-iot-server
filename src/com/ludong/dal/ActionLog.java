package com.ludong.dal;

import java.sql.Connection;
import java.util.List;

import org.dave.common.database.access.DataAccess;
import org.dave.common.database.convert.IntegerConverter;
import org.dave.common.database.convert.LongConverter;

public class ActionLog  extends DataAccess{
	
	public ActionLog(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 增加一条指令
	 * @return
	 */
	public long Add (com.ludong.model.ActionLog model)
	{
		String sql="INSERT INTO ActionLog(AppKey,SerialNumber,TerminalID,Timestamp,ActionCode,ActionName,Parameters,Status,SamplingTime,AddTime) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		long id= super.insert(sql, new LongConverter(), 
				model.GetAppKey(),
				model.GetSerialNumber(),
				model.GetTerminalID(),
				model.GetTimestamp(),
				model.GetActionCode(),
				model.getActionName(),
				model.GetParameters(),
				model.GetStatus(),
				model.GetSamplingTime(),
				model.GetAddTime()
				);
		return id;
	}
	
	/**
	 * 查询是否已经存在记录
	 */
	public long GetCount(String condition) {
		String sql="SELECT COUNT(0) FROM ActionLog";
		if(!com.cocopass.helper.CString.IsNullOrEmpty(condition)){
			sql+=" WHERE "+condition;
		}
		return super.queryForObject(sql, new LongConverter());
	}
	
	/**
	 * 查询符合条件的GPS历史信息
	 * @return
	 */
	public List<com.ludong.model.ActionLog> GetList(String condition,int page,int pageNum) {
		StringBuilder sqlStringBuilder = new StringBuilder();
		sqlStringBuilder.append("  SELECT * FROM ActionLog ");
		if (condition != null && !condition.trim().equals("")) {
			sqlStringBuilder.append(" WHERE  " + condition);
		}		
		int startRecord=(page-1)*pageNum;
		sqlStringBuilder.append(" ORDER BY ID DESC LIMIT "+startRecord+","+pageNum);
		String sql = sqlStringBuilder.toString();
		return super.queryForList(sql, new com.ludong.bll.result.convert.ActionLog());
	}
}
