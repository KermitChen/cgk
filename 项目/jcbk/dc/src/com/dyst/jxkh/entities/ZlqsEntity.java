package com.dyst.jxkh.entities;

import java.text.NumberFormat;

public class ZlqsEntity {

	private String deptName;
	private String deptNo;
	private String parentDeptNo;
	
	private int zls;//指令总数
	private int asqs;//按时签收数
	private int wasqs;//未按时签收数
	private int wqs;//未签收数
	private String totalQsl;//总签收率
	private String asQsl;//按时签收率
	
	private int fks;//反馈总数
	private int asfks;//按时反馈数
	private int wasfks;//未按时反馈数
	private int wfk;//未反馈数
	private String totalFkl;//总反馈率
	private String asFkl;//按时反馈率
	
	NumberFormat percentFormat = NumberFormat.getPercentInstance();
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	public String getParentDeptNo() {
		return parentDeptNo;
	}
	public void setParentDeptNo(String parentDeptNo) {
		this.parentDeptNo = parentDeptNo;
	}
	
	public int getZls() {
		return zls;
	}
	public void setZls(int zls) {
		this.zls = zls;
	}
	public int getAsqs() {
		return asqs;
	}
	public void setAsqs(int asqs) {
		this.asqs = asqs;
	}
	public int getWasqs() {
		return wasqs;
	}
	public void setWasqs(int wasqs) {
		this.wasqs = wasqs;
	}
	public int getWqs() {
		return wqs;
	}
	public void setWqs(int wqs) {
		this.wqs = wqs;
	}
	public String getTotalQsl() {
		percentFormat.setMinimumFractionDigits(2);
		if(zls==0){
			return percentFormat.format((double)0);
		}
		return percentFormat.format((double)(asqs+wasqs)/zls);
	}
	public void setTotalQsl(String totalQsl) {
		this.totalQsl = totalQsl;
	}
	public String getAsQsl() {
		percentFormat.setMinimumFractionDigits(2);
		if(zls==0){
			return percentFormat.format((double)0);
		}
		return percentFormat.format((double)(asqs)/zls);
	}
	public void setAsQsl(String asQsl) {
		this.asQsl = asQsl;
	}
	
	
	public int getFks() {
		return fks;
	}
	public void setFks(int fks) {
		this.fks = fks;
	}
	public int getAsfks() {
		return asfks;
	}
	public void setAsfks(int asfks) {
		this.asfks = asfks;
	}
	public int getWasfks() {
		return wasfks;
	}
	public void setWasfks(int wasfks) {
		this.wasfks = wasfks;
	}
	public int getWfk() {
		return wfk;
	}
	public void setWfk(int wfk) {
		this.wfk = wfk;
	}
	public String getTotalFkl() {
		percentFormat.setMinimumFractionDigits(2);
		if(fks==0){
			return percentFormat.format((double)0);
		}
		return percentFormat.format((double)(asfks+wasfks)/fks);
	}
	public void setTotalFkl(String totalFkl) {
		this.totalFkl = totalFkl;
	}
	public String getAsFkl() {
		percentFormat.setMinimumFractionDigits(2);
		if(fks==0){
			return percentFormat.format((double)0);
		}
		return percentFormat.format((double)(asfks)/fks);
	}
	public void setAsFkl(String asFkl) {
		this.asFkl = asFkl;
	}
	
}
