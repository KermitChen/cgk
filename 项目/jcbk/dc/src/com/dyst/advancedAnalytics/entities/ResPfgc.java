package com.dyst.advancedAnalytics.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ResPfgc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RES_PFGC", catalog = "dc")
public class ResPfgc implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3658710670024214687L;
	private Integer id;
	private String resFlag;
	private String hphm;
	private String cplx;
	private String jcdid;
	private Integer gccs;

	// Constructors

	/** default constructor */
	public ResPfgc() {
	}

	/** minimal constructor */
	public ResPfgc(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public ResPfgc(Integer id, String resFlag, String hphm, String cplx,
			String jcdid, Integer gccs) {
		this.id = id;
		this.resFlag = resFlag;
		this.hphm = hphm;
		this.cplx = cplx;
		this.jcdid = jcdid;
		this.gccs = gccs;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "resFlag", length = 100)
	public String getResFlag() {
		return this.resFlag;
	}

	public void setResFlag(String resFlag) {
		this.resFlag = resFlag;
	}

	@Column(name = "hphm", length = 16)
	public String getHphm() {
		return this.hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	@Column(name = "cplx", length = 10)
	public String getCplx() {
		return this.cplx;
	}

	public void setCplx(String cplx) {
		this.cplx = cplx;
	}

	@Column(name = "jcdid", length = 20)
	public String getJcdid() {
		return this.jcdid;
	}

	public void setJcdid(String jcdid) {
		this.jcdid = jcdid;
	}

	@Column(name = "gccs")
	public Integer getGccs() {
		return this.gccs;
	}

	public void setGccs(Integer gccs) {
		this.gccs = gccs;
	}

}