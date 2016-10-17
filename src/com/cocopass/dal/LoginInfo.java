package com.cocopass.dal;

import java.sql.Connection;
import java.util.List;

import org.dave.common.database.access.DataAccess;
import org.dave.common.database.convert.LongConverter;

public class LoginInfo  extends DataAccess{
	
	public LoginInfo(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 查询符合条件的GPS历史信息
	 * @return
	 */
	public long Add (com.cocopass.iot.model.LoginInfo model)
	{
		String sql="INSERT INTO LoginInfo( TerminalID,SamplingTime,ReceiveHost,ReceiveLocalHost,ReceiveTCPPort,ReceiveUDPPort,AddTime ,ServerSamplingTime) values(?,?,?,?,?,?,?,?)";
		long id= super.insert(sql, new LongConverter(), 
				model.getTerminalID(),
				model.getSamplingTime(),
				model.getReceiveHost(),
				model.getReceiveLocalHost(),
				model.getReceiveTCPPort(),
				model.getReceiveUDPPort(),
				model.getAddTime(),
				model.getServerSamplingTime()
				);
		return id;
	}
	
	/**
	 * 查询是否已经存在记录
	 */
	public long GetCount(String condition) {
		String sql="SELECT COUNT(0) FROM LoginInfo";
		if(!com.cocopass.helper.CString.IsNullOrEmpty(condition)){
			sql+=" WHERE "+condition;
		}
		return super.queryForObject(sql, new LongConverter());
	}
	
	/**
	 * 查询符合条件的GPS历史信息
	 * @return
	 */
	public List<com.cocopass.iot.model.LoginInfo> GetList(String condition,int page,int pageNum)
	{
		StringBuilder sqlStringBuilder = new StringBuilder();
		 sqlStringBuilder.append("  SELECT * FROM LoginInfo " );
			if(condition!=null&&!condition.trim().equals("")){
				sqlStringBuilder.append(" WHERE  "+ condition );
			}

			int startRecord=(page-1)*pageNum;
			sqlStringBuilder.append(" ORDER BY ID DESC LIMIT "+startRecord+","+pageNum);
		String sql=sqlStringBuilder.toString();
		
		return super.queryForList(sql, new com.cocopass.dal.result.convert.LoginInfo());
	}
}
