package com.dyst.advancedAnalytics.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ResZfyc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RES_ZFYC", catalog = "dc")
public class ResZfyc implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6694176134918429928L;
	private Integer id;
	private String resFlag;
	private String hphm;
	private Integer zfTimes;
	private Integer ycTimes;

	// Constructors

	/** default constructor */
	public ResZfyc() {
	}

	/** full constructor */
	public ResZfyc(String resFlag, String hphm, Integer zfTimes, Integer ycTimes) {
		this.resFlag = resFlag;
		this.hphm = hphm;
		this.zfTimes = zfTimes;
		this.ycTimes = ycTimes;
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

	@Column(name = "resFlag", length = 50)
	public String getResFlag() {
		return this.resFlag;
	}

	public void setResFlag(String resFlag) {
		this.resFlag = resFlag;
	}

	@Column(name = "hphm", length = 10)
	public String getHphm() {
		return this.hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	@Column(name = "zfTimes")
	public Integer getZfTimes() {
		return this.zfTimes;
	}

	public void setZfTimes(Integer zfTimes) {
		this.zfTimes = zfTimes;
	}

	@Column(name = "ycTimes")
	public Integer getYcTimes() {
		return this.ycTimes;
	}

	public void setYcTimes(Integer ycTimes) {
		this.ycTimes = ycTimes;
	}

}