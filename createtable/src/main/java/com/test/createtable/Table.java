package com.test.createtable;

import java.util.ArrayList;
import java.util.List;

public class Table {
	private String tablespace;
	private String tablename;
	private String tableremark;
	private String primarykey;
	private String primarykeyname;
	private List<Columu> columus = new ArrayList<>();

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getTableremark() {
		return tableremark;
	}

	public void setTableremark(String tableremark) {
		this.tableremark = tableremark;
	}

	public String getPrimarykey() {
		return primarykey;
	}

	public void setPrimarykey(String primarykey) {
		this.primarykey = primarykey;
	}

	public void addColumus(Columu columu) {
		this.columus.add(columu);
	}

	public String getTablespace() {
		return tablespace;
	}

	public void setTablespace(String tablespace) {
		this.tablespace = tablespace;
	}

	public List<Columu> getColumus() {
		return columus;
	}

	public String getPrimarykeyname() {
		return primarykeyname;
	}

	public void setPrimarykeyname(String primarykeyname) {
		this.primarykeyname = primarykeyname;
	}
	

}
