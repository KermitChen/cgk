package com.dyst.BaseDataMsg.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tpyz entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TPYZ", catalog = "dc")
public class Tpyz implements java.io.Serializable {

	// Fields

	private Integer id;
	private String jcdid1;
	private String jcdid2;
	private Integer tpsjyz;
	private Integer cssjyz;
	private Integer lxsjsx;
	private Integer lxsjxx;
	private Integer jl;
	private Integer xs;
	private Double wcb;
	private Integer dcssjyz;
	private Integer xs1;
	private String fx;
	private String wlddbm;
	private String qybz;
	private Integer hpcxs;

	// Constructors

	/** default constructor */
	public Tpyz() {
	}

	/** minimal constructor */
	public Tpyz(String jcdid1, String jcdid2) {
		this.jcdid1 = jcdid1;
		this.jcdid2 = jcdid2;
	}

	public Tpyz(String jcdid1, String jcdid2, Integer tpsjyz, Integer jl,
			Integer dcssjyz, String qybz) {
		super();
		this.jcdid1 = jcdid1;
		this.jcdid2 = jcdid2;
		this.tpsjyz = tpsjyz;
		this.jl = jl;
		this.dcssjyz = dcssjyz;
		this.qybz = qybz;
	}

	/** full constructor */
	public Tpyz(String jcdid1, String jcdid2, Integer tpsjyz, Integer cssjyz,
			Integer lxsjsx, Integer lxsjxx, Integer jl, Integer xs, Double wcb,
			Integer dcssjyz, Integer xs1, String fx, String wlddbm,
			String qybz, Integer hpcxs) {
		this.jcdid1 = jcdid1;
		this.jcdid2 = jcdid2;
		this.tpsjyz = tpsjyz;
		this.cssjyz = cssjyz;
		this.lxsjsx = lxsjsx;
		this.lxsjxx = lxsjxx;
		this.jl = jl;
		this.xs = xs;
		this.wcb = wcb;
		this.dcssjyz = dcssjyz;
		this.xs1 = xs1;
		this.fx = fx;
		this.wlddbm = wlddbm;
		this.qybz = qybz;
		this.hpcxs = hpcxs;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "JCDID1", nullable = false, length = 8)
	public String getJcdid1() {
		return this.jcdid1;
	}

	public void setJcdid1(String jcdid1) {
		this.jcdid1 = jcdid1;
	}

	@Column(name = "JCDID2", nullable = false, length = 8)
	public String getJcdid2() {
		return this.jcdid2;
	}

	public void setJcdid2(String jcdid2) {
		this.jcdid2 = jcdid2;
	}

	@Column(name = "TPSJYZ")
	public Integer getTpsjyz() {
		return this.tpsjyz;
	}

	public void setTpsjyz(Integer tpsjyz) {
		this.tpsjyz = tpsjyz;
	}

	@Column(name = "CSSJYZ")
	public Integer getCssjyz() {
		return this.cssjyz;
	}

	public void setCssjyz(Integer cssjyz) {
		this.cssjyz = cssjyz;
	}

	@Column(name = "LXSJSX")
	public Integer getLxsjsx() {
		return this.lxsjsx;
	}

	public void setLxsjsx(Integer lxsjsx) {
		this.lxsjsx = lxsjsx;
	}

	@Column(name = "LXSJXX")
	public Integer getLxsjxx() {
		return this.lxsjxx;
	}

	public void setLxsjxx(Integer lxsjxx) {
		this.lxsjxx = lxsjxx;
	}

	@Column(name = "JL")
	public Integer getJl() {
		return this.jl;
	}

	public void setJl(Integer jl) {
		this.jl = jl;
	}

	@Column(name = "XS")
	public Integer getXs() {
		return this.xs;
	}

	public void setXs(Integer xs) {
		this.xs = xs;
	}

	@Column(name = "WCB", precision = 3)
	public Double getWcb() {
		return this.wcb;
	}

	public void setWcb(Double wcb) {
		this.wcb = wcb;
	}

	@Column(name = "DCSSJYZ")
	public Integer getDcssjyz() {
		return this.dcssjyz;
	}

	public void setDcssjyz(Integer dcssjyz) {
		this.dcssjyz = dcssjyz;
	}

	@Column(name = "XS1")
	public Integer getXs1() {
		return this.xs1;
	}

	public void setXs1(Integer xs1) {
		this.xs1 = xs1;
	}

	@Column(name = "FX", length = 2)
	public String getFx() {
		return this.fx;
	}

	public void setFx(String fx) {
		this.fx = fx;
	}

	@Column(name = "WLDDBM", length = 10)
	public String getWlddbm() {
		return this.wlddbm;
	}

	public void setWlddbm(String wlddbm) {
		this.wlddbm = wlddbm;
	}

	@Column(name = "QYBZ", length = 1)
	public String getQybz() {
		return this.qybz;
	}

	public void setQybz(String qybz) {
		this.qybz = qybz;
	}

	@Column(name = "HPCXS")
	public Integer getHpcxs() {
		return this.hpcxs;
	}

	public void setHpcxs(Integer hpcxs) {
		this.hpcxs = hpcxs;
	}

}