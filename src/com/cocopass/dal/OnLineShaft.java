package com.cocopass.dal;

import java.sql.Connection;
import java.util.List;

import org.dave.common.database.access.DataAccess;
import org.dave.common.database.convert.LongConverter;

public class OnLineShaft  extends DataAccess{
	
	public OnLineShaft(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 查询符合条件的GPS历史信息
	 * @return
	 */
	public long Add (com.cocopass.iot.model.OnLineShaft model)
	{
		String sql="INSERT INTO OnLineShaft(TerminalID,IsOnline,AddTime) values(?,?,?)";
		long id= super.insert(sql, new LongConverter(), model.getTerminalID(),model.isIsOnline(),model.getAddTime());
		return id;
	}
	
	/**
	 * 查询是否已经存在记录
	 */
	public long GetCount(String condition) {
		String sql="SELECT COUNT(0) FROM OnLineShaft";
		if(!com.cocopass.helper.CString.IsNullOrEmpty(condition)){
			sql+=" WHERE "+condition;
		}
		return super.queryForObject(sql, new LongConverter());
	}
	
	/**
	 * 查询符合条件的GPS历史信息
	 * @return
	 */
	public List<com.cocopass.iot.model.OnLineShaft> GetList(String condition,int page,int pageNum)
	{
		StringBuilder sqlStringBuilder = new StringBuilder();
		 sqlStringBuilder.append("SELECT * FROM OnLineShaft " );
			if(condition!=null&&!condition.trim().equals("")){
				sqlStringBuilder.append(" WHERE  "+ condition );
			}

			int startRecord=(page-1)*pageNum;
			sqlStringBuilder.append(" ORDER BY ID DESC LIMIT "+startRecord+","+pageNum);
		String sql=sqlStringBuilder.toString();
		
		return super.queryForList(sql, new com.cocopass.dal.result.convert.OnLineShaft());
	}
	 
	 

}
