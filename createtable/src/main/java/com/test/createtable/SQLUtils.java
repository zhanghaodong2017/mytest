package com.test.createtable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLUtils {
	public static String createSql(Table table) {
		StringBuilder builder = new StringBuilder();
		// 删除表语句
		builder.append("drop table   ");
		builder.append(table.getTablename());
		builder.append(";\r\n");

		builder.append("create table ");
		builder.append(table.getTablename());
		builder.append("(\r\n");
		for (Columu columu : table.getColumus()) {
			builder.append(columu.getColumnname());
			builder.append(" ");
			builder.append(columu.getColumutype());
			if(!columu.isAllowEmpty()){
				builder.append(" not null ");
			}
			builder.append(",\r\n");
		}
		builder.delete(builder.length()-3, builder.length()-1);
		builder.append(")\r\n");
		builder.append(" tablespace ");
		builder.append(table.getTablespace());
		builder.append(" pctfree 10 initrans 1 maxtrans 255 storage ( initial 64K next 8K minextents 1 maxextents unlimited  );\r\n");
		
		//备注信息
		builder.append("comment on table ");
		builder.append(table.getTablename());
		builder.append(" is '");
		builder.append(table.getTableremark());
		builder.append("';\r\n");
		
		//字段备注
		for (Columu columu : table.getColumus()) {
			builder.append("comment on column ");
			builder.append(table.getTablename());
			builder.append(".");
			builder.append(columu.getColumnname());
			builder.append(" is '");
			builder.append(columu.getRemark());
			builder.append("';\r\n");
		}
		
		//主键
		builder.append("alter table ");
		builder.append(table.getTablename());
		builder.append(" add constraint ");
		builder.append(table.getPrimarykeyname());
		builder.append(" primary key (");
		builder.append(table.getPrimarykey());
		builder.append(") using index tablespace ");
		builder.append(table.getTablespace());
		builder.append(" pctfree 10  initrans 2  maxtrans 255;");
		
		return builder.toString();
	}
	public static String createSql2(Table table) {
		StringBuilder builder = new StringBuilder();
		// 删除表语句
		builder.append("prompt\r\n");
		builder.append("prompt 检查表 ");
		builder.append(table.getTablename());
		builder.append(" 是否存在，不存在则创建......\r\n");
		builder.append("declare\r\n");
		builder.append("  v_rowcount integer;\r\n");
		builder.append("begin\r\n");
		builder.append("  select count(1) into v_rowcount from user_tables where table_name = upper('");
		builder.append(table.getTablename());
		builder.append("');\r\n");
		builder.append("  if v_rowcount = 0 then\r\n");
		builder.append("    execute immediate \r\n");
		builder.append("    'create table ");
		builder.append(table.getTablename());
		builder.append("(\r\n");
		for (Columu columu : table.getColumus()) {
			builder.append("        ");
			builder.append(columu.getColumnname());
			builder.append(" ");
			builder.append(columu.getColumutype());
			if(!columu.isAllowEmpty()){
				builder.append(" not null ");
			}
			builder.append(",\r\n");
		}
		builder.append("        constraint ");
		builder.append(table.getPrimarykeyname());
		builder.append(" primary key (");
		builder.append(table.getPrimarykey());
		builder.append(")\r\n");
		builder.append("    )';\r\n");
		
		//备注信息
		builder.append("    execute immediate 'comment on table ");
		builder.append(table.getTablename());
		builder.append(" is ''");
		builder.append(table.getTableremark());
		builder.append("''';\r\n");
		
		//字段备注
		for (Columu columu : table.getColumus()) {
			builder.append("    execute immediate 'comment on column ");
			builder.append(table.getTablename());
			builder.append(".");
			builder.append(columu.getColumnname());
			builder.append(" is ''");
			builder.append(columu.getRemark());
			builder.append("''';\r\n");
		}
		
		builder.append("  end if;\r\n");
		builder.append("  commit;\r\n");
		builder.append("end;\r\n");
		builder.append("/\r\n");
		
		return builder.toString();
	}
	public static String createDictidSql(Dictid dictid) {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into tb_dictitems (DICTID, DICTNAME, DICTCOMMENT, DICTKIND, DICTVALUE, CNSTNAME, ACCESSLEVEL, REMARK, ISENABLE, UPDATETIME, CREATETIME)");
		builder.append("\r\n");
		builder.append("values (");
		builder.append(getColumn(dictid.getDictid()));
		builder.append(",");
		builder.append(getColumn(dictid.getDictname()));
		builder.append(",");
		builder.append(getColumn(dictid.getDictcomment()));
		builder.append(",");
		builder.append(getColumn(dictid.getDictkind()));
		builder.append(",");
		builder.append(getColumn(dictid.getDictvalue()));
		builder.append(",");
		builder.append(getColumn(dictid.getCnstname()));
		builder.append(",");
		builder.append(getColumn(dictid.getAccesslevel()));
		builder.append(",");
		builder.append(getColumn(dictid.getRemark()));
		builder.append(",");
		builder.append(getColumn(dictid.getIsenable()));
		builder.append(",");
		builder.append(getDate());
		builder.append(",");
		builder.append(getDate());
		builder.append(");");
		builder.append("\r\n");
		return builder.toString();
	}
	private static String getColumn(String value){
		if(value == null){
			return "null";
		}
		if(value.trim().equals("")){
			return "null";
		}
		return "'"+value+"'";
	}
	private static String getDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return "to_date('"+sdf.format(new Date())+"', 'yyyy-mm-dd')";
	}

}
