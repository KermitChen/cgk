package com.dyst.advancedAnalytics.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ResCxgl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RES_CXGL", catalog = "dc")
public class ResCxgl implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7564680930238885079L;
	private Integer id;
	private String resFlag;
	private String wheelPath;
	private Integer passNum;
	private String passTime;

	// Constructors

	/** default constructor */
	public ResCxgl() {
	}

	/** full constructor */
	public ResCxgl(String resFlag, String wheelPath, Integer passNum,
			String passTime) {
		this.resFlag = resFlag;
		this.wheelPath = wheelPath;
		this.passNum = passNum;
		this.passTime = passTime;
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

	@Column(name = "wheelPath", length = 1000)
	public String getWheelPath() {
		return this.wheelPath;
	}

	public void setWheelPath(String wheelPath) {
		this.wheelPath = wheelPath;
	}

	@Column(name = "passNum")
	public Integer getPassNum() {
		return this.passNum;
	}

	public void setPassNum(Integer passNum) {
		this.passNum = passNum;
	}

	@Column(name = "passTime", length = 20)
	public String getPassTime() {
		return this.passTime;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}

}