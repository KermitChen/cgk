package com.dyst.systemmanage.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * Announcement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ANNOUNCEMENT")
public class Announcement implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1357291936548250747L;
	
	private Integer id;
	private String fileName;
	private String fileUrl;
	private String buildPno;
	private String buildName;
	private Timestamp buildTime;
	private Timestamp updateTime;
	private String jlzt;
	private String remark;
	private Timestamp startTime;
	private Timestamp endTime;
	private String outline;
	private String annType;
	private String deptids;

	// Constructors
	

	/** default constructor */
	public Announcement() {
	}

	/** full constructor */
	public Announcement(String fileName, String fileUrl, String buildPno,
			String buildName, Timestamp buildTime, Timestamp updateTime,
			String jlzt, String remark, Timestamp startTime, Timestamp endTime,
			String outline, String annType) {
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.buildPno = buildPno;
		this.buildName = buildName;
		this.buildTime = buildTime;
		this.updateTime = updateTime;
		this.jlzt = jlzt;
		this.remark = remark;
		this.startTime = startTime;
		this.endTime = endTime;
		this.outline = outline;
		this.annType = annType;
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

	@Column(name = "FILE_NAME", length = 200)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "FILE_URL", length = 200)
	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Column(name = "BUILD_PNO", length = 30)
	public String getBuildPno() {
		return this.buildPno;
	}

	public void setBuildPno(String buildPno) {
		this.buildPno = buildPno;
	}

	@Column(name = "BUILD_NAME", length = 50)
	public String getBuildName() {
		return this.buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	@Column(name = "BUILD_TIME", length = 19)
	public Timestamp getBuildTime() {
		return this.buildTime;
	}

	public void setBuildTime(Timestamp buildTime) {
		this.buildTime = buildTime;
	}

	@Column(name = "UPDATE_TIME", length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "JLZT", length = 1)
	public String getJlzt() {
		return this.jlzt;
	}

	public void setJlzt(String jlzt) {
		this.jlzt = jlzt;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "START_TIME", length = 19)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", length = 19)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "OUTLINE", length = 200)
	public String getOutline() {
		return this.outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	@Column(name = "ANN_TYPE", length = 5)
	public String getAnnType() {
		return this.annType;
	}

	public void setAnnType(String annType) {
		this.annType = annType;
	}
	
	@Type(type="text")
	@Column(name="DEPT_IDS",length=65535)
	public String getDeptids() {
		return deptids;
	}

	public void setDeptids(String deptids) {
		this.deptids = deptids;
	}
	

}