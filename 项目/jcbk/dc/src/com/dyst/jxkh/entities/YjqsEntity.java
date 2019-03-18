package com.dyst.jxkh.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YjqsEntity {

	private String dwid;
	private String dwmc;
	private Map<String,QslbEntity> map = new HashMap<String,QslbEntity>();

	
	public String getDwid() {
		return dwid;
	}

	public void setDwid(String dwid) {
		this.dwid = dwid;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public Map<String, QslbEntity> getMap() {
		return map;
	}

	public void setMap(Map<String, QslbEntity> map) {
		this.map = map;
	}

}
