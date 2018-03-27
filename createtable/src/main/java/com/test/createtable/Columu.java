package com.test.createtable;

public class Columu {
	private String columnname;
	private String remark;
	private String columutype;
	private boolean isAllowEmpty;
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	public String getColumutype() {
		return columutype;
	}
	public void setColumutype(String columutype) {
		this.columutype = columutype;
	}
	
	public boolean isAllowEmpty() {
		return isAllowEmpty;
	}
	public void setAllowEmpty(boolean isAllowEmpty) {
		this.isAllowEmpty = isAllowEmpty;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
