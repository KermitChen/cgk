package com.dyst.jxkh.entities;

import java.text.NumberFormat;

public class ZlqsEntity2 {

	private String deptID;
	private String deptName;
	private int zlqs_zlzs;
	private int zlqs_asqs;
	private int zlqs_csqs;
	private int zlqs_wqs;
	private String zlqs_zqsl;
	private String zlqs_asqsl;
	
	NumberFormat percentFormat = NumberFormat.getPercentInstance();
	
	public ZlqsEntity2() {
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
	public int getZlqs_zlzs() {
		return zlqs_zlzs;
	}
	public void setZlqs_zlzs(int zlqs_zlzs) {
		this.zlqs_zlzs = zlqs_zlzs;
	}
	public int getZlqs_asqs() {
		return zlqs_asqs;
	}
	public void setZlqs_asqs(int zlqs_asqs) {
		this.zlqs_asqs = zlqs_asqs;
	}
	public int getZlqs_csqs() {
		return zlqs_csqs;
	}
	public void setZlqs_csqs(int zlqs_csqs) {
		this.zlqs_csqs = zlqs_csqs;
	}
	public int getZlqs_wqs() {
		return zlqs_wqs;
	}
	public void setZlqs_wqs(int zlqs_wqs) {
		this.zlqs_wqs = zlqs_wqs;
	}
	
	public String getZlqs_zqsl() {
		percentFormat.setMinimumFractionDigits(2);
	    int zlzs = zlqs_zlzs;
		int asqs = zlqs_asqs;
		int csqs = zlqs_csqs;
		if(zlzs == 0){
			return percentFormat.format(0);
		}
		return percentFormat.format((double)(asqs + csqs) / (zlzs * 1.0));
	}
	
	public void setZlqs_zqsl(String zlqs_zqsl) {
		this.zlqs_zqsl = zlqs_zqsl;
	}
	
	public String getZlqs_asqsl() {
		percentFormat.setMinimumFractionDigits(2);
	    int zlzs = zlqs_zlzs;
		int asqs = zlqs_asqs;
		if(asqs == 0||zlzs ==0){
			return percentFormat.format(0);
		}
		return percentFormat.format((double)asqs / (zlzs * 1.0));
	}
	
	public void setZlqs_asqsl(String zlqs_asqsl) {
		this.zlqs_asqsl = zlqs_asqsl;
	}
}
