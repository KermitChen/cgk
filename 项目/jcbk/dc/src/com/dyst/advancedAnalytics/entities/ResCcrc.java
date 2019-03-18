package com.dyst.advancedAnalytics.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ResCcrc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RES_CCRC", catalog = "dc")
public class ResCcrc implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 432858949318020624L;
	private Integer id;
	private String resFlag;
	private String hphm;
	private String cplx;
	private String ccrcsj;
	private String ccrcjcd;
	private String ccrcjlid;
	private Timestamp time;

	// Constructors

	/** default constructor */
	public ResCcrc() {
	}

	/** full constructor */
	public ResCcrc(String resFlag, String hphm, String cplx, String ccrcsj,
			String ccrcjcd, String ccrcjlid, Timestamp time) {
		this.resFlag = resFlag;
		this.hphm = hphm;
		this.cplx = cplx;
		this.ccrcsj = ccrcsj;
		this.ccrcjcd = ccrcjcd;
		this.ccrcjlid = ccrcjlid;
		this.time = time;
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

	@Column(name = "hphm", length = 20)
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

	@Column(name = "ccrcsj", length = 30)
	public String getCcrcsj() {
		return this.ccrcsj;
	}

	public void setCcrcsj(String ccrcsj) {
		this.ccrcsj = ccrcsj;
	}

	@Column(name = "ccrcjcd", length = 20)
	public String getCcrcjcd() {
		return this.ccrcjcd;
	}

	public void setCcrcjcd(String ccrcjcd) {
		this.ccrcjcd = ccrcjcd;
	}

	@Column(name = "ccrcjlid", length = 30)
	public String getCcrcjlid() {
		return this.ccrcjlid;
	}

	public void setCcrcjlid(String ccrcjlid) {
		this.ccrcjlid = ccrcjlid;
	}

	@Column(name = "time", length = 0)
	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}