package com.dyst.advancedAnalytics.entities;

import java.util.List;

public class ExcelBeanForDwpz {

	private List<ResDwpz> list;
	private String queryCondition;
	
	
	public ExcelBeanForDwpz() {
		super();
	}

	public ExcelBeanForDwpz(List<ResDwpz> list, String queryCondition) {
		super();
		this.list = list;
		this.queryCondition = queryCondition;
	}

	public List<ResDwpz> getList() {
		return list;
	}

	public void setList(List<ResDwpz> list) {
		this.list = list;
	}

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}
	
}
