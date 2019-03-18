package com.dyst.earlyWarning.entities;

public class Ya {
	private String kdbh;
	private String kdmc;
	private String jd;
	private String wd;
	private String yjsj;
	private String czya;
	private String zbry;
	private String lxfs;
	
	public Ya(){
		
	}
	
	public Ya(String kdbh, String kdmc, String jd, String wd, String yjsj, 
			String czya, String zbry, String lxfs){
		this.kdbh = kdbh;
		this.kdmc = kdmc;
		this.jd = jd;
		this.wd = wd;
		this.yjsj = yjsj;
		this.czya = czya;
		this.zbry = zbry;
		this.lxfs = lxfs;
	}

	public String getKdbh() {
		return kdbh;
	}

	public void setKdbh(String kdbh) {
		this.kdbh = kdbh;
	}

	public String getKdmc() {
		return kdmc;
	}

	public void setKdmc(String kdmc) {
		this.kdmc = kdmc;
	}

	public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getYjsj() {
		return yjsj;
	}

	public void setYjsj(String yjsj) {
		this.yjsj = yjsj;
	}

	public String getCzya() {
		return czya;
	}

	public void setCzya(String czya) {
		this.czya = czya;
	}

	public String getZbry() {
		return zbry;
	}

	public void setZbry(String zbry) {
		this.zbry = zbry;
	}

	public String getLxfs() {
		return lxfs;
	}

	public void setLxfs(String lxfs) {
		this.lxfs = lxfs;
	}
}