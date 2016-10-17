package com.cocopass.dal;

import java.sql.Connection;
import java.util.List;

import org.dave.common.database.access.DataAccess;

public class Terminal extends DataAccess {

	public Terminal(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 查询符合条件的GPS历史信息
	 * 
	 * @return
	 */
	public List<com.cocopass.iot.model.Terminal> GetList(String condition, int page, int pageNum) {
		StringBuilder sqlStringBuilder = new StringBuilder();
		sqlStringBuilder.append("  SELECT * FROM Terminal ");
		if (condition != null && !condition.trim().equals("")) {
			sqlStringBuilder.append(" WHERE  " + condition);
		}

		int startRecord = (page - 1) * pageNum;
		sqlStringBuilder.append(" ORDER BY AddDate DESC LIMIT " + startRecord + "," + pageNum);
		String sql = sqlStringBuilder.toString();

		return super.queryForList(sql, new com.cocopass.dal.result.convert.Terminal());
	}

}
