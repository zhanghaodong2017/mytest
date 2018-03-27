package com.test.createtable;

import java.io.File;
import java.util.List;

public class CreateDictid {
	public static void main(String[] args) {
		File file = new File("E:\\file\\crateTable.xls");
		List<Dictid> arrayList = ExcelUtil.readDictid(file, 5, "100157");
//		System.out.println(arrayList.size());
		for (Dictid dictid : arrayList) {
			String sql = SQLUtils.createDictidSql(dictid);
			System.out.println(sql);
		}
	}
}
