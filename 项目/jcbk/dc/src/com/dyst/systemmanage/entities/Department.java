package com.dyst.systemmanage.entities;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Department entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DEPARTMENT")
public class Department implements java.io.Serializable {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 100000000003L;
	
	private Integer id;
	private String deptNo;
	private String deptName;
	private String deptTelephone;
	private String parentNo;
	private String parentName;
	private String buildPno;
	private String buildName;
	private Date buildTime;
	private Date updateTime;
	private String infoSource;//信息来源
	private String jxkh;
	private String systemNo;
	private String remark;

	// Constructors

	/** default constructor */
	public Department() {
	}

	/** full constructor */
	public Department(String deptNo, String deptName, String parentNo, String parentName,
			Timestamp buildTime, Timestamp updateTime, String buildName,
			String buildPno, String deptTelephone, String infoSource, String jxkh, String systemNo, String remark) {
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.deptTelephone = deptTelephone;
		this.parentNo = parentNo;
		this.parentName = parentName;
		this.buildPno = buildPno;
		this.buildName = buildName;
		this.buildTime = buildTime;
		this.updateTime = updateTime;
		this.infoSource = infoSource;
		this.jxkh = jxkh;
		this.systemNo = systemNo;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "DEPT_NO", length = 20)
	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	@Column(name = "DEPT_NAME", length = 80)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "PARENT_NO", length = 20)
	public String getParentNo() {
		return this.parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	
	@Column(name = "PARENT_NAME", length = 80)
	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Column(name = "BUILD_TIME")
	public Date getBuildTime() {
		return this.buildTime;
	}

	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}

	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "BUILD_NAME", length = 80)
	public String getBuildName() {
		return this.buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	@Column(name = "BUILD_PNO", length = 30)
	public String getBuildPno() {
		return this.buildPno;
	}

	public void setBuildPno(String buildPno) {
		this.buildPno = buildPno;
	}

	@Column(name = "DEPT_TELEPHONE", length = 20)
	public String getDeptTelephone() {
		return this.deptTelephone;
	}

	public void setDeptTelephone(String deptTelephone) {
		this.deptTelephone = deptTelephone;
	}
	
	@Column(name="INFO_SOURCE", length=1)
	public String getInfoSource() {
		return infoSource;
	}
	public void setInfoSource(String infoSource) {
		this.infoSource = infoSource;
	}
	
	@Column(name="JXKH", length=1)
	public String getJxkh() {
		return jxkh;
	}

	public void setJxkh(String jxkh) {
		this.jxkh = jxkh;
	}
	
	@Column(name="SYSTEM_NO", length=50)
	public String getSystemNo() {
		return systemNo;
	}

	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}