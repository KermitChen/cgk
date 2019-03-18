package com.dyst.utils.excel.bean;

import com.dyst.DyMsg.entities.Dyxx;

public class DyExcelBean {

	private Dyxx dyxx;
	
	private String dylx;
	
	private String jlzt;
	
	private String spjg;
	
	private String ywzt;
	
	private String dqjd;

	public DyExcelBean() {
		
	}

	public DyExcelBean(Dyxx dyxx, String dylx, String jlzt, String spjg,
			String ywzt, String dqjd) {
		this.dyxx = dyxx;
		this.dylx = dylx;
		this.jlzt = jlzt;
		this.spjg = spjg;
		this.ywzt = ywzt;
		this.dqjd = dqjd;
	}


	public Dyxx getDyxx() {
		return dyxx;
	}

	public void setDyxx(Dyxx dyxx) {
		this.dyxx = dyxx;
	}

	public String getDylx() {
		return dylx;
	}

	public void setDylx(String dylx) {
		this.dylx = dylx;
	}

	public String getJlzt() {
		return jlzt;
	}

	public void setJlzt(String jlzt) {
		this.jlzt = jlzt;
	}

	public String getSpjg() {
		return spjg;
	}

	public void setSpjg(String spjg) {
		this.spjg = spjg;
	}

	public String getYwzt() {
		return ywzt;
	}

	public void setYwzt(String ywzt) {
		this.ywzt = ywzt;
	}

	public String getDqjd() {
		return dqjd;
	}

	public void setDqjd(String dqjd) {
		this.dqjd = dqjd;
	}
	
}
