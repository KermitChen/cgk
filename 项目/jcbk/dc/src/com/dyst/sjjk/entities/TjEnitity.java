package com.dyst.sjjk.entities;

public class TjEnitity {
	private int sn;
	private String jcdid;
	private String jcdmc;
	private Integer totalRst;
	
	private Integer identify;//识别量
	private Integer notIdentify;//未识别量
	private String sbl;//识别率
	
	private Integer jss;//及时传输数量
	private Integer css;//超时传输数量
	private String jsl;//上传及时率
	 
	public TjEnitity() {
		super();
	}
	
	public TjEnitity(String jcdid) {
		this.jcdid = jcdid;
	}
	
	public TjEnitity(String jcdid, Integer totalRst) {
		this.jcdid = jcdid;
		this.totalRst = totalRst;
	}
	
	//传输率
	public TjEnitity(int sn, String jcdid, String jcdmc, String jsl, Integer jss, Integer css, 
			Integer totalRst) {
		this.sn = sn;
		this.jcdid = jcdid;
		this.jcdmc = jcdmc;
		this.jss = jss;
		this.css = css;
		this.totalRst = totalRst;
		this.jsl = jsl;
	}
	
	//识别率
	public TjEnitity(int sn, String jcdid, String jcdmc, Integer notIdentify, Integer identify, 
			Integer totalRst, String sbl) {
		this.sn = sn;
		this.jcdid = jcdid;
		this.jcdmc = jcdmc;
		this.notIdentify = notIdentify;
		this.identify = identify;
		this.totalRst = totalRst;
		this.sbl = sbl;
	}
	
	public String getJcdid() {
		return jcdid;
	}

	public void setJcdid(String jcdid) {
		this.jcdid = jcdid;
	}

	public Integer getNotIdentify() {
		return notIdentify;
	}

	public void setNotIdentify(Integer notIdentify) {
		this.notIdentify = notIdentify;
	}

	public Integer getIdentify() {
		return identify;
	}

	public void setIdentify(Integer identify) {
		this.identify = identify;
	}

	public Integer getTotalRst() {
		return totalRst;
	}

	public void setTotalRst(Integer totalRst) {
		this.totalRst = totalRst;
	}

	public String getSbl() {
		return sbl;
	}

	public void setSbl(String sbl) {
		this.sbl = sbl;
	}

	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public String getJcdmc() {
		return jcdmc;
	}

	public void setJcdmc(String jcdmc) {
		this.jcdmc = jcdmc;
	}

	public Integer getJss() {
		return jss;
	}

	public void setJss(Integer jss) {
		this.jss = jss;
	}

	public Integer getCss() {
		return css;
	}

	public void setCss(Integer css) {
		this.css = css;
	}

	public String getJsl() {
		return jsl;
	}

	public void setJsl(String jsl) {
		this.jsl = jsl;
	}
}