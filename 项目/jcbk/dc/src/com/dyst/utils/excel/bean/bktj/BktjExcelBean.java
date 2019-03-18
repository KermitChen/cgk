package com.dyst.utils.excel.bean.bktj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dyst.jxkh.entities.BktjEntity;

/**
 * 布控统计ExcelBean
 * @author liuqiang
 *
 */
public class BktjExcelBean {
	
	List<BktjEntity> list = new ArrayList<BktjEntity>();
	
	//计算总计
	Map<String,Integer> total = new HashMap<String, Integer>();
	
	private String startTime;//统计开始时间
	
	private String endTime;//统计结束时间
	
	private String tjDetp;//统计的部门
	
	public BktjExcelBean() {
	}

	public BktjExcelBean(List<BktjEntity> list, Map<String, Integer> total,
			String startTime, String endTime, String tjDetp) {
		this.list = list;
		this.total = total;
		this.startTime = startTime;
		this.endTime = endTime;
		this.tjDetp = tjDetp;
	}



	public List<BktjEntity> getList() {
		return list;
	}

	public void setList(List<BktjEntity> list) {
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

	public String getTjDetp() {
		return tjDetp;
	}

	public void setTjDetp(String tjDetp) {
		this.tjDetp = tjDetp;
	}
	
}
