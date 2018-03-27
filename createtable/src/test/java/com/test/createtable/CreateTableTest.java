package com.test.createtable;

import java.io.File;
import java.util.List;

public class CreateTableTest {
	public static void main(String[] args) {
		File file = new File("E:\\file\\crateTable.xls");
		List<Table> arrayList = ExcelUtil.readExcel(file, 0);//第二个参数是sheet下标
//		System.out.println(arrayList.size());
//		System.out.println(new Gson().toJson(arrayList));
//		System.out.println();
		for (Table table : arrayList) {
			String sql = SQLUtils.createSql2(table);
			System.out.println(sql);
		}
	}
}
