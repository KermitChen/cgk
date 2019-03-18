package com.dyst.utils.excel.bean.bktj;

import com.dyst.jxkh.entities.BkckListBean;

/**
 * 布控撤控 ExcelBean
 * @author LQQ 
 *
 */
public class BkckExcelBean {

	BkckListBean bean = new BkckListBean();
	private String startTime;
	private String endTime;
	private String deptName;
	
	public BkckExcelBean() {
	}
	
	public BkckExcelBean(BkckListBean bean, String startTime, String endTime,
			String deptName) {
		this.bean = bean;
		this.startTime = startTime;
		this.endTime = endTime;
		this.deptName = deptName;
	}

	public BkckListBean getBean() {
		return bean;
	}
	public void setBean(BkckListBean bean) {
		this.bean = bean;
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
