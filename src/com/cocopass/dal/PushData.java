package com.cocopass.dal;

import java.sql.Connection;
import java.util.List;

import org.dave.common.database.access.DataAccess;
import org.dave.common.database.convert.LongConverter;

public class PushData extends DataAccess{
	
	public PushData(Connection conn) {
		super(conn);
	}
 

	
	public  void AddMutilList(String  TableName,List<com.cocopass.iot.model.PushData> list){		 
		String prefix="INSERT INTO "+TableName+"(ID,AppKey,`TimeStamp`,DataTypeID,Sign,Response,Body,Version ) VALUES";
		StringBuffer suffix = new StringBuffer();  
		for (com.cocopass.iot.model.PushData model:list) {  
			String cloumnValue=String.format("('%s','%s',%s,%s,'%s','%s','%s',%s),",  
					model.getID(), model.GetAppKey(), model.GetTimeStamp(), model.GetDataTypeID(), 
					model.getSign(), model.getResponse(),model.GetBody(),model.GetVersion());
            suffix.append(cloumnValue);  
        }  
        String sql = prefix + suffix.substring(0, suffix.length() - 1);  

        super.insertMulti(sql); 
	}

	/**
	 * 查询是否已经存在记录
	 */
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
		
		// System.out.println(sql);
		return super.queryForObject(sql, new LongConverter());
	}

	/**
	 * 查询符合条件的GPS历史信息
	 * @return
	 */
	public List<com.cocopass.iot.model.PushData> GetList(String condition,int page,int pageNum,List<String> tables)
	{
		StringBuilder sqlStringBuilder = new StringBuilder();
		for(String table:tables){
			sqlStringBuilder.append(" UNION SELECT * FROM "+table );
			if(condition!=null&&!condition.trim().equals("")){
				sqlStringBuilder.append(" WHERE  "+ condition );
			}
		}
		
		String sql=sqlStringBuilder.toString().replaceFirst("UNION", "");
		
		int startRecord=(page-1)*pageNum;
		
		sql=String.format("SELECT * FROM (%s) AS TG ORDER BY `TimeStamp` DESC  LIMIT "+startRecord+","+pageNum, sql);
		
	    //System.out.println(sql);
		
		return super.queryForList(sql, new com.cocopass.dal.result.convert.PushData());
	}

}



