package com.dyst.advancedAnalytics.dto;

public class BsResult implements Comparable<BsResult>{

	private String cphm;
	private int jcdgs;
	private String resFlag;
	
	public BsResult() {
		super();
	}
	public BsResult(String cphm, int jcdgs, String resFlag) {
		super();
		this.cphm = cphm;
		this.jcdgs = jcdgs;
		this.resFlag = resFlag;
	}
	public String getCphm() {
		return cphm;
	}
	public void setCphm(String cphm) {
		this.cphm = cphm;
	}
	public int getJcdgs() {
		return jcdgs;
	}
	public void setJcdgs(int jcdgs) {
		this.jcdgs = jcdgs;
	}
	public String getResFlag() {
		return resFlag;
	}
	public void setResFlag(String resFlag) {
		this.resFlag = resFlag;
	}
	@Override
	public String toString() {
		return "BsResult [cphm=" + cphm + ", jcdgs=" + jcdgs +"]";
	}
	@Override
	public int compareTo(BsResult o) {
		if(this.jcdgs > o.getJcdgs()){
			return -1;
		}else if(this.jcdgs == o.getJcdgs()){
			return 0;
		}else{
			return 1;
		}
	}
}
