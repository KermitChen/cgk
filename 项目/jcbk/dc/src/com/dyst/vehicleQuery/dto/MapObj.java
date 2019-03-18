package com.dyst.vehicleQuery.dto;

import java.util.List;

public class MapObj {

	private String key;
	private List list;
	public MapObj() {
		super();
	}
	public MapObj(String key, List list) {
		super();
		this.key = key;
		this.list = list;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
}
