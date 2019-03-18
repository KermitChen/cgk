package com.dyst.advancedAnalytics.entities;

public class FakeHphmExcelBean {

	private String hphm;
	private String cpys;
	private String jcd;
	private String sbsj;
	private String xscd;
	
	public FakeHphmExcelBean() {
	}

	public FakeHphmExcelBean(String hphm, String cpys, String jcd, String sbsj,
			String xscd) {
		super();
		this.hphm = hphm;
		this.cpys = cpys;
		this.jcd = jcd;
		this.sbsj = sbsj;
		this.xscd = xscd;
	}

	public String getHphm() {
		return hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public String getCpys() {
		return cpys;
	}

	public void setCpys(String cpys) {
		this.cpys = cpys;
	}

	public String getJcd() {
		return jcd;
	}

	public void setJcd(String jcd) {
		this.jcd = jcd;
	}

	public String getSbsj() {
		return sbsj;
	}

	public void setSbsj(String sbsj) {
		this.sbsj = sbsj;
	}

	public String getXscd() {
		return xscd;
	}

	public void setXscd(String xscd) {
		this.xscd = xscd;
	}
	
}
