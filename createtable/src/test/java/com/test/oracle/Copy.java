package com.test.oracle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
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

import org.apache.commons.io.IOUtils;

public class Copy {
	private static final String jdbc_url = "jdbc:oracle:thin:@//10.0.12.19:51521/fund";
	private static final String fund_jdbc_username = "fund";
	private static final String fund_jdbc_password = "fund";
	private static final String capitalsettle_jdbc_username = "capitalsettle";
	private static final String capitalsettle_jdbc_password = "capitalsettle";

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
		Connection fundConn = null;
		Connection capitalConn = null;
		try {
			fundConn = getFundConnection();
			capitalConn = getCapitalConnection();
			// 开启事务
			// sourceConn.setAutoCommit(false);
			// 查询出生产库所有表的名称
			List<CapitalArrivalRule> prodList = new ArrayList<CapitalArrivalRule>();
			Statement stmt = fundConn.createStatement();
			ResultSet rs = stmt.executeQuery("select tano,prodcode from tb_prodcodeinfo h where h.producttype <> '04' ");
			while (rs.next()) {
				String tano = rs.getString(1);
				String prodcode = rs.getString(2);
				CapitalArrivalRule arrivalRule = new CapitalArrivalRule();
				arrivalRule.setTaNo(tano);
				arrivalRule.setProdCode(prodcode);
				prodList.add(arrivalRule);
			}
			stmt.close();
			List<CapitalArrivalRule> capitalList = new ArrayList<CapitalArrivalRule>();

			stmt = capitalConn.createStatement();
			ResultSet rs2 = stmt.executeQuery("select t.busincode, t.daten, t.itime, t.busindatetype, t.isenable  from tb_capitalarrivalrule t where t.prodcode = '001870'");
			while (rs2.next()) {
				String busincode = rs2.getString(1);
				BigDecimal daten = rs2.getBigDecimal(2);
				BigDecimal itime = rs2.getBigDecimal(3);
				String busindatetype = rs2.getString(4);
				String isenable = rs2.getString(5);
				CapitalArrivalRule arrivalRule = new CapitalArrivalRule();
				arrivalRule.setBusinCode(busincode);
				arrivalRule.setDateN(daten);
				arrivalRule.setiTime(itime);
				arrivalRule.setBusinDateType(busindatetype);
				arrivalRule.setIsEnable(isenable);
				capitalList.add(arrivalRule);
			}
			stmt.close();
			// 提交事务
			System.err.println(prodList.size());
			System.err.println(capitalList.size());
			for (CapitalArrivalRule prodinfo : prodList) {
				for (CapitalArrivalRule capitalArrivalRule : capitalList) {
					capitalArrivalRule.setTaNo(prodinfo.getTaNo());
					capitalArrivalRule.setProdCode(prodinfo.getProdCode());
//					save(buildSql(capitalArrivalRule));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fundConn.close();
				capitalConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		d = new Date();
		long end = d.getTime();

		// System.out.println("执行时间：" + (end - start) + "毫秒");
	}

	private static void save(String sql) {
		// TODO Auto-generated method stub
		OutputStream output;
		try {
			output = new FileOutputStream(new File("E:\\mytxt.sql"),true);
			IOUtils.write(sql, output);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private static String buildSql(CapitalArrivalRule capitalArrivalRule) {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into tb_capitalarrivalrule(tano,prodcode,busincode,daten,itime,busindatetype,isenable,createtime,updatetime  )");
		builder.append("values('");
		builder.append(capitalArrivalRule.getTaNo());
		builder.append("','");
		builder.append(capitalArrivalRule.getProdCode());
		builder.append("','");
		builder.append(capitalArrivalRule.getBusinCode());
		builder.append("','");
		builder.append(capitalArrivalRule.getDateN());
		builder.append("','");
		builder.append(capitalArrivalRule.getiTime());
		builder.append("','");
		builder.append(capitalArrivalRule.getBusinDateType());
		builder.append("','");
		builder.append(capitalArrivalRule.getIsEnable());
		builder.append("',sysdate,sysdate);\r\n");
		return builder.toString();
	}

	// 获取源数据库链接
	public static Connection getFundConnection() throws Exception {
		Connection connection = null;
		connection = DriverManager.getConnection(jdbc_url, fund_jdbc_username, fund_jdbc_password);
		return connection;
	}

	// 获取目标数据库链接
	public static Connection getCapitalConnection() throws Exception {
		Connection connection = null;
		connection = DriverManager.getConnection(jdbc_url, capitalsettle_jdbc_username, capitalsettle_jdbc_password);
		return connection;
	}

}
