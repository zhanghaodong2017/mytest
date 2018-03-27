package com.test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.driver.OracleResultSetMetaData;

public class JDBCTest {
	public static void main(String[] args) {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@//10.0.12.20:1521/oracle", "capital", "capital");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from tb_PayCheckDeatail");
			OracleResultSetMetaData orsmd = (OracleResultSetMetaData) rs.getMetaData();
			for (int i = 1; i <= orsmd.getColumnCount(); i++) {
				System.out.println("字段名：" + orsmd.getColumnName(i));
			}
			System.out.println("success");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
