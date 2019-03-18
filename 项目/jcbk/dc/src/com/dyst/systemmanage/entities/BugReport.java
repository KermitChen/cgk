package com.dyst.systemmanage.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * BugReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BugReport", catalog = "dc")
public class BugReport implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7570850852854794648L;
	private Integer id;
	private String loginName;
	private String userName;
	private Date submitTime;
	private String problemDesc;
	private String isDeal;
	private Date dealTime;
	private String picAddress;
	private Date time;

	// Constructors

	/** default constructor */
	public BugReport() {
	}

	/** full constructor */
	public BugReport(String loginName, String userName, Date submitTime,
			String problemDesc, String isDeal, Date dealTime,
			String picAddress, Date time) {
		this.loginName = loginName;
		this.userName = userName;
		this.submitTime = submitTime;
		this.problemDesc = problemDesc;
		this.isDeal = isDeal;
		this.dealTime = dealTime;
		this.picAddress = picAddress;
		this.time = time;
	}

	public BugReport(String loginName, String userName, Date submitTime,
			String problemDesc,String isDeal) {
		super();
		this.loginName = loginName;
		this.userName = userName;
		this.submitTime = submitTime;
		this.problemDesc = problemDesc;
		this.isDeal = isDeal;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "loginName", length = 50)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "userName", length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "submitTime", length = 19)
	public Date getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	@Column(name = "problemDesc")
	public String getProblemDesc() {
		return this.problemDesc;
	}

	public void setProblemDesc(String problemDesc) {
		this.problemDesc = problemDesc;
	}

	@Column(name = "isDeal", length = 1)
	public String getIsDeal() {
		return this.isDeal;
	}

	public void setIsDeal(String isDeal) {
		this.isDeal = isDeal;
	}

	@Column(name = "dealTime", length = 19)
	public Date getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	@Column(name = "picAddress")
	public String getPicAddress() {
		return this.picAddress;
	}

	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}

	@Column(name = "time", length = 19)
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}