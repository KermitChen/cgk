package com.dyst.utils.excel.bean.yj;

import java.util.List;
import java.util.Map;

import com.dyst.jxkh.entities.YjtjEntity;

public class YjtjExcelBean {

	private List<YjtjEntity> list ;
	
	//计算总计
	Map<String, Integer> total;
	
	private String startTime;
	
	private String endTime;
	
	private String deptName;

	public List<YjtjEntity> getList() {
		return list;
	}

	public void setList(List<YjtjEntity> list) {
		this.list = list;
	}
	
	public Map<String, Integer> getTotal() {
		return total;
	}

	public void setTotal(Map<String, Integer> total) {
		this.total = total;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}