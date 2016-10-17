package com.cocopass.dal;

import java.sql.Connection;
import java.util.List;

import org.dave.common.database.access.DataAccess;
import org.dave.common.database.convert.LongConverter;

public class AuthInfo  extends DataAccess{
	
	public AuthInfo(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * ��ѯ����������GPS��ʷ��Ϣ
	 * @return
	 */

	public long Add (com.cocopass.iot.model.AuthInfo model)
	{
		String sql="INSERT INTO AuthInfo( TerminalID,SamplingTime,ServerSamplingTime,IMEI,IMSI,ICCID,FirmwareVersion ) values(?,?,?,?,?,?,?)";
		long id= super.insert(sql, new LongConverter(), 
				model.getTerminalID(),
				model.getSamplingTime(),
				model.getServerSamplingTime(),
				model.getIMEI(),
				model.getIMSI(),
				model.getICCID(),
				model.getFirmwareVersion()
			
				);
		return id;
	}
	
	
	/**
	 * ��ѯ�Ƿ��Ѿ����ڼ�¼
	 */
	public long GetCount(String condition) {
		String sql="SELECT COUNT(0) FROM AuthInfo";
		if(!com.cocopass.helper.CString.IsNullOrEmpty(condition)){
			sql+=" WHERE "+condition;
		}
		return super.queryForObject(sql, new LongConverter());
	}

	
	/**
	 * ��ѯ����������GPS��ʷ��Ϣ
	 * @return
	 */
	public List<com.cocopass.iot.model.AuthInfo> GetList(String condition,int page,int pageNum)
	{
		StringBuilder sqlStringBuilder = new StringBuilder();
		 sqlStringBuilder.append("  SELECT * FROM AuthInfo " );
			if(condition!=null&&!condition.trim().equals("")){
				sqlStringBuilder.append(" WHERE  "+ condition );
			}

			int startRecord=(page-1)*pageNum;
			sqlStringBuilder.append(" ORDER BY ID DESC LIMIT "+startRecord+","+pageNum);
		String sql=sqlStringBuilder.toString();
		
		return super.queryForList(sql, new com.cocopass.dal.result.convert.AuthInfo());
	}
}
