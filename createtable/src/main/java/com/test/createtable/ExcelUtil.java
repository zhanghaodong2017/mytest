package com.test.createtable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelUtil {

	public static List<Table> readExcel(File file, int sheetAt) {
		List<Table> tables = new ArrayList<>();
		boolean columnStart = false;
		try {
			Table table1 = null;

			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(sheetAt);
			HSSFRow row;
			HSSFCell cell;
			Object value;

			for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
				if (sheet.getRow(i) == null) {
					continue;
				}
				if (sheet.getRow(i).getCell(1) == null) {
					continue;
				}
				
				if ("表名".equals(sheet.getRow(i).getCell(1).getStringCellValue())) {
					table1 = new Table();
					table1.setTablename(sheet.getRow(i).getCell(2).getStringCellValue().toUpperCase());
					table1.setTablespace("CAPITAL");
				}
				if ("说明".equals(sheet.getRow(i).getCell(1).getStringCellValue())) {
					table1.setTableremark(sheet.getRow(i).getCell(2).getStringCellValue());
				}
				if (sheet.getRow(i).getCell(1).getStringCellValue().contains("索引字段")) {
					columnStart = false;
					tables.add(table1);
				}
				if (columnStart) {
					Columu columu1 = new Columu();
					columu1.setAllowEmpty(true);
					columu1.setColumnname(sheet.getRow(i).getCell(3).getStringCellValue().toUpperCase());
					columu1.setRemark(sheet.getRow(i).getCell(2).getStringCellValue());
					String type = sheet.getRow(i).getCell(4).getStringCellValue();
					if (type.contains("varchar(")) {
						type = type.replace("varchar(", "varchar2(");
					}
					if (type.contains("numeric")) {
						type = type.replace("numeric", "number");
					}
					if (type.equals("int")) {
						type = "number(10)";
					}
					columu1.setColumutype(type.toUpperCase());
					table1.addColumus(columu1);
				}
				if ("字段".equals(sheet.getRow(i).getCell(1).getStringCellValue())) {
					columnStart = true;
				}
				if ("C".equals(sheet.getRow(i).getCell(1).getStringCellValue())) {
					table1.setPrimarykeyname(sheet.getRow(i).getCell(2).getStringCellValue().toUpperCase());
					table1.setPrimarykey(sheet.getRow(i).getCell(6).getStringCellValue().toUpperCase());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tables;
	}

	public static List<Dictid> readDictid(File file, int sheetAt, String dictId) {
		List<Dictid> dictids = new ArrayList<Dictid>();
		try {
			Dictid dictid2 = null;
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(sheetAt);

			for (int i = 1; i < sheet.getLastRowNum()+20; i++) {
				if (sheet.getRow(i) == null) {
					continue;
				}
				if (dictId.equals(pd(sheet.getRow(i).getCell(0).getNumericCellValue()))) {
					dictid2 = new Dictid();
					dictid2.setDictid(pd(sheet.getRow(i).getCell(0).getNumericCellValue()));
					dictid2.setDictname(sheet.getRow(i).getCell(1).getStringCellValue());
					dictid2.setDictcomment(sheet.getRow(i).getCell(2).getStringCellValue());
					dictid2.setDictkind(sheet.getRow(i).getCell(3).getStringCellValue());
					dictid2.setDictvalue(sheet.getRow(i).getCell(4).getStringCellValue());
					dictid2.setCnstname(sheet.getRow(i).getCell(5).getStringCellValue());
					dictid2.setAccesslevel(sheet.getRow(i).getCell(6).getStringCellValue());
					dictid2.setRemark(sheet.getRow(i).getCell(7).getStringCellValue());
					dictid2.setIsenable(pd(sheet.getRow(i).getCell(8).getNumericCellValue()));
					dictids.add(dictid2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dictids;
	}
	private static String pd(double d){
		return Integer.valueOf(Double.valueOf(d).intValue()).toString();
	}

}
