package com.dyst.advancedAnalytics.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ResDwpz entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RES_DWPZ", catalog = "dc")
public class ResDwpz implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1424405302234055247L;
	private Integer id;
	private String resFlag;
	private String hphm;
	private String tpids;
	private Date time;

	// Constructors

	/** default constructor */
	public ResDwpz() {
	}

	/** full constructor */
	public ResDwpz(String resFlag, String hphm, String tpids, Date time) {
		this.resFlag = resFlag;
		this.hphm = hphm;
		this.tpids = tpids;
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

	@Column(name = "hphm", length = 16)
	public String getHphm() {
		return this.hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	@Column(name = "tpids")
	public String getTpids() {
		return this.tpids;
	}

	public void setTpids(String tpids) {
		this.tpids = tpids;
	}

	@Column(name = "time", length = 0)
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}