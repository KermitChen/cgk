package com.dyst.jxkh.entities;

import java.text.NumberFormat;

public class YjqsEntity2 {
	NumberFormat percentFormat = NumberFormat.getPercentInstance();
	
	private String deptID;
	
	private String deptName;
	
	private int yjqs_qszs;
	
	private int yjqs_asqs;
	
	private int yjqs_csqs;
	
	private int yjqs_wqs;
	
	private String yjqs_zqsl;
	
	private String yjqs_asqsl;

	
	public YjqsEntity2() {
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

	public int getYjqs_qszs() {
		return yjqs_qszs;
	}

	public void setYjqs_qszs(int yjqs_qszs) {
		this.yjqs_qszs = yjqs_qszs;
	}

	public int getYjqs_asqs() {
		return yjqs_asqs;
	}

	public void setYjqs_asqs(int yjqs_asqs) {
		this.yjqs_asqs = yjqs_asqs;
	}

	public int getYjqs_csqs() {
		return yjqs_csqs;
	}

	public void setYjqs_csqs(int yjqs_csqs) {
		this.yjqs_csqs = yjqs_csqs;
	}

	public int getYjqs_wqs() {
		return yjqs_wqs;
	}

	public void setYjqs_wqs(int yjqs_wqs) {
		this.yjqs_wqs = yjqs_wqs;
	}

	public String getYjqs_zqsl() {
		percentFormat.setMinimumFractionDigits(2);
		int yjzs = yjqs_qszs;
		int asqs = yjqs_asqs;
		int csqs = yjqs_csqs;
		if(yjzs == 0){
			return percentFormat.format(0);
		}
		return percentFormat.format((double)(asqs + csqs) / (yjzs * 1.0));
	}

	public void setYjqs_zqsl(String yjqs_zqsl) {
		this.yjqs_zqsl = yjqs_zqsl;
	}

	public String getYjqs_asqsl() {
		percentFormat.setMinimumFractionDigits(2);
		int yjzs = yjqs_qszs;
		int asqs = yjqs_asqs;
		if(asqs==0||yjzs==0){
			return percentFormat.format(0);
		}
		return percentFormat.format((double)asqs / (yjzs * 1.0));
	}

	public void setYjqs_asqsl(String yjqs_asqsl) {
		this.yjqs_asqsl = yjqs_asqsl;
	}
}