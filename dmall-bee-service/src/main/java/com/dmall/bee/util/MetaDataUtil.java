package com.dmall.bee.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmall.bee.domain.AppInfo;
import com.dmall.bee.domain.Field;
import com.dmall.bee.domain.Table;

/**
 * 数据库解析工具
 * 
 */
public class MetaDataUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(MetaDataUtil.class);


	/**
	 * 数据库类型映射
	 * 
	 * @param type
	 * @return
	 */
	private static Class<?> getType(int type) {
		Class<?> clz = null;
		switch (type) {
		// 字符类型
		case Types.CLOB:
		case Types.NCLOB:
		case Types.CHAR:
		case Types.VARCHAR:
		case Types.NVARCHAR:
		case Types.LONGNVARCHAR:
		case Types.LONGVARCHAR:
			clz = String.class;
			break;
		// 数字类型
		case Types.NUMERIC:
		case Types.DECIMAL:
			clz = Number.class;
			break;
		case Types.BIT:
		case Types.BOOLEAN:
			clz = Boolean.class;
			break;
		case Types.TINYINT:
		case Types.SMALLINT:
		case Types.INTEGER:
			clz = Integer.class;
			break;
		case Types.VARBINARY:
		case Types.BIGINT:
			clz = Long.class;
			break;
		case Types.REAL:
			clz = Float.class;
			break;
		case Types.FLOAT:
		case Types.DOUBLE:
			clz = Double.class;
			break;
		case Types.DATE:
		case Types.TIME:
		case Types.TIMESTAMP:
			clz = Date.class;
			break;
		// 其他
		default:
			clz = Object.class;
		}
		return clz;
	}

	public static void getTables(AppInfo appInfo) throws Exception {
		Connection conn = null;
		try {
			
			Class.forName(appInfo.getDriver());
			conn = DriverManager.getConnection(appInfo.getUrl(), appInfo.getUser(), appInfo.getPwd());
			// 收集数据库信息
			DatabaseMetaData dm = conn.getMetaData();
			if(StringUtils.isEmpty(appInfo.getDbName())){
				String dbName = getDbName(appInfo.getUrl());
				if(StringUtils.isEmpty(dbName)){
					return;
				}
				appInfo.setDbName(dbName);
			}
			ResultSet rs = dm.getTables(appInfo.getDbName(), null, null, appInfo.getType());
			
			HashSet<String> selectTables = new HashSet<String>(Arrays.asList(appInfo.getSelectedTables()));
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				if(appInfo.getAllTables() != null && appInfo.getAllTables() == 2 && selectTables.contains(tableName)){
					Table table = new Table();
					table.setName(tableName);
					table.setRemark(rs.getString("REMARKS"));
					appInfo.addTable(table);
				}else if(appInfo.getAllTables() == null || appInfo.getAllTables() == 1){
					Table table = new Table();
					table.setName(tableName);
					table.setRemark(rs.getString("REMARKS"));
					appInfo.addTable(table);
				}
			}
			rs.close();// 关闭游标

			// 遍历数据库信息
			for (Table table : appInfo.getTables()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("load table [" + table.getName() + "]");
				}
				rs = dm.getPrimaryKeys(appInfo.getDbName(), null, table.getName());
				Set<String> primarySet = new HashSet<String>();
				while (rs.next()) {// 查询表主键
					primarySet.add(rs.getString("COLUMN_NAME"));
				}
				rs.close();// 关闭游标

				// 查询列信息
				rs = dm.getColumns(appInfo.getDbName(), null, table.getName(), null);
				while (rs.next()) {
					Field field = new Field();
					field.setName(rs.getString("COLUMN_NAME"));
					field.setLength(rs.getInt("COLUMN_SIZE"));
					field.setNullAble(rs.getBoolean("NULLABLE"));
					field.setRemark(rs.getString("REMARKS"));
					field.setPrimaryKey(primarySet.contains(field.getFieldName()));
					field.setType(getType(rs.getInt("DATA_TYPE")));
					field.setJdbcType(rs.getString("TYPE_NAME"));
					table.addField(field);
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("load [" + table.getName() + "] ==> [" + field.getFieldName() + "]");
					}
				}
				rs.close();// 关闭游标
			}
		} catch (Exception e) {
			LOGGER.error("获取数据库信息异常", e);
			throw e;
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e2) {
				LOGGER.error("关闭数据库异常", e2);
			}
		}
	}
	
	private static String getDbName(String jdbcUrl){
		if(!StringUtils.isEmpty(jdbcUrl)){
			int index = jdbcUrl.indexOf("//");
			if(index>=0){
				String tmp = jdbcUrl.substring(index + 2);
				index = index + 2 + tmp.indexOf("/");
			}
			int endIndex = jdbcUrl.indexOf("?");
			if(index > 0 && endIndex>0){
				return jdbcUrl.substring(index+1, endIndex);
			}else if(index > 0 && endIndex < 0){
				return jdbcUrl.substring(index);
			}else{
				return "";
			}
		}
		return "";
	}
	
	

}
