package com.dyst.jxkh.entities;

import java.text.NumberFormat;

public class CzfkEntity2 {
	
	NumberFormat percentFormat = NumberFormat.getPercentInstance();

	private String deptID;
	private String deptName;
	private int czfk_fkzs;
	private int czfk_asfk;
	private int czfk_csfk;
	private int czfk_wfk;
	private String czfk_zfkl;
	private String czfk_asfkl;
	
	public CzfkEntity2() {
	}
	
	public String getDeptID() {
		return deptID;
	}
	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getCzfk_fkzs() {
		return czfk_fkzs;
	}
	public void setCzfk_fkzs(int czfk_fkzs) {
		this.czfk_fkzs = czfk_fkzs;
	}
	public int getCzfk_asfk() {
		return czfk_asfk;
	}
	public void setCzfk_asfk(int czfk_asfk) {
		this.czfk_asfk = czfk_asfk;
	}
	public int getCzfk_csfk() {
		return czfk_csfk;
	}
	public void setCzfk_csfk(int czfk_csfk) {
		this.czfk_csfk = czfk_csfk;
	}
	public int getCzfk_wfk() {
		return czfk_wfk;
	}
	public void setCzfk_wfk(int czfk_wfk) {
		this.czfk_wfk = czfk_wfk;
	}

	public String getCzfk_zfkl() {
		percentFormat.setMinimumFractionDigits(2);
		int fkzs = czfk_fkzs;
		int asfk = czfk_asfk;
		int csfk = czfk_csfk;
		if(fkzs==0){
			return percentFormat.format(0);
		}
		return percentFormat.format((double)(asfk + csfk) / (fkzs * 1.0));
	}
	
	public void setCzfk_zfkl(String czfk_zfkl) {
		this.czfk_zfkl = czfk_zfkl;
	}

	public String getCzfk_asfkl() {
		percentFormat.setMinimumFractionDigits(2);
		int fkzs = czfk_fkzs;
		int asfk = czfk_asfk;
		if(asfk==0||fkzs==0){
			return percentFormat.format(0);
		}
		return percentFormat.format((double)asfk / (fkzs * 1.0));
	}
	
	public void setCzfk_asfkl(String czfk_asfkl) {
		this.czfk_asfkl = czfk_asfkl;
	}
}