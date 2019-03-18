package com.dyst.advancedAnalytics.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ResFootHold entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RES_FOOTHOLD", catalog = "dc")
public class ResFootHold implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1810467504226117999L;
	private Integer id;
	private String resFlag;
	private String footHold;
	private Integer holdTimes;

	// Constructors

	/** default constructor */
	public ResFootHold() {
	}

	/** full constructor */
	public ResFootHold(String resFlag, String footHold, Integer holdTimes) {
		this.resFlag = resFlag;
		this.footHold = footHold;
		this.holdTimes = holdTimes;
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

	@Column(name = "footHold", length = 20)
	public String getFootHold() {
		return this.footHold;
	}

	public void setFootHold(String footHold) {
		this.footHold = footHold;
	}

	@Column(name = "holdTimes")
	public Integer getHoldTimes() {
		return this.holdTimes;
	}

	public void setHoldTimes(Integer holdTimes) {
		this.holdTimes = holdTimes;
	}

}