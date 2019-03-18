package com.dyst.jxkh.entities;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QslbEntity {

	private String totalQsl;
	private String asQsl;
	private Map<String,Integer> map = new HashMap<String, Integer>();
	NumberFormat percentFormat = NumberFormat.getPercentInstance();
	
	private List<ZlqsEntity> zllist = new ArrayList<ZlqsEntity>();
	
	public Map<String, Integer> getMap() {
		return map;
	}
	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}
	public String getTotalQsl() {
		percentFormat.setMinimumFractionDigits(2);
		Integer yjzs = map.get("yjzs");
		Integer asqs = map.get("asqs");
		Integer wasqs = map.get("wasqs");
		return percentFormat.format((double)(asqs+wasqs)/yjzs);
	}
	public void setTotalQsl(String totalQsl) {
		this.totalQsl = totalQsl;
	}
	public String getAsQsl() {
		Integer yjzs = map.get("yjzs");
		Integer asqs = map.get("asqs");
		percentFormat.setMinimumFractionDigits(2);
		return percentFormat.format((double)asqs/yjzs);
	}
	public void setAsQsl(String asQsl) {
		this.asQsl = asQsl;
	}
	public List<ZlqsEntity> getZllist() {
		return zllist;
	}
	public void setZllist(List<ZlqsEntity> zllist) {
		this.zllist = zllist;
	}
	
}
