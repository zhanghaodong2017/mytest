package com.test.oracle;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oracle.sql.TIMESTAMP;

public class OracleSyc {
	private static final String source_jdbc_url = "jdbc:oracle:thin:@//192.168.1.245:1521/testjijin";
	private static final String source_jdbc_username = "fund";
	private static final String source_jdbc_password = "fund";

	private static final String tagert_jdbc_url = "jdbc:oracle:thin:@//10.0.12.20:1521/oracle";
	private static final String tagert_jdbc_username = "capital";
	private static final String tagert_jdbc_password = "capital";

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Date d = new Date();
		long start = d.getTime();
		Connection sourceConn = null;
		Connection tagertConn = null;
		try {
			sourceConn = getSourceConnection();
			tagertConn = getTagertConnection();
			// 开启事务
			// sourceConn.setAutoCommit(false);
			// 查询出生产库所有表的名称
			List<String> list_table_names = new ArrayList<String>();
			Statement stmt = sourceConn.createStatement();
			ResultSet rs = stmt.executeQuery("select table_name from user_tables");
			while (rs.next()) {
				list_table_names.add(rs.getString(1));
			}
			stmt.close();
			stmt = sourceConn.createStatement();
			for (String tableName : list_table_names) {
				ResultSet rstable = stmt.executeQuery("select * from " + tableName);
				String insertSql = buildInsertSql(rstable, tableName);
				System.out.println(insertSql);
			}
			// System.err.println(list_table_names.size());
			stmt.close();
			// 提交事务
			// sourceConn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sourceConn.close();
				tagertConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		d = new Date();
		long end = d.getTime();

		// System.out.println("执行时间：" + (end - start) + "毫秒");
	}

	private static String buildInsertSql(ResultSet rstable, String tableName) throws Exception {
		ResultSetMetaData rsmd = rstable.getMetaData();
		StringBuilder builder = new StringBuilder("insert into ");
		builder.append(tableName);
		builder.append("(");

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			builder.append(rsmd.getColumnName(i));
			if (i < rsmd.getColumnCount()) {
				builder.append(",");
			}
		}
		builder.append(") values (");
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			if (rstable.next()) {
				int columntype = rsmd.getColumnType(i);
				builder.append(getValue(rstable, i, columntype));
				if (i < rsmd.getColumnCount()) {
					builder.append(",");
				}
			}
		}
		builder.append(");");
		return builder.toString();
	}

	private static String getValue(ResultSet rstable, int index, int columntype) throws SQLException {
		if (rstable.getObject(index) == null) {
			return "null";
		}
		if (columntype == Types.INTEGER) {
			return rstable.getInt(index) + "";
		} else if (columntype == Types.DATE || columntype == Types.TIMESTAMP) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return "to_date('" + sdf.format(rstable.getDate(index)) + "','yyyy-mm-dd hh24:mi:ss')";
		} else if (columntype == Types.NUMERIC) {
			return rstable.getDouble(index) + "";
		} else if (columntype == Types.VARCHAR || columntype == Types.CHAR || columntype == Types.CLOB) {
			return "'" + rstable.getString(index) + "'";
		} else {
			throw new RuntimeException("columntype:" + columntype);
		}
	}

	// 获取源数据库链接
	public static Connection getSourceConnection() throws Exception {
		Connection connection = null;
		connection = DriverManager.getConnection(source_jdbc_url, source_jdbc_username, source_jdbc_password);
		return connection;
	}

	// 获取目标数据库链接
	public static Connection getTagertConnection() throws Exception {
		Connection connection = null;
		connection = DriverManager.getConnection(tagert_jdbc_url, tagert_jdbc_username, tagert_jdbc_password);
		return connection;
	}

}
