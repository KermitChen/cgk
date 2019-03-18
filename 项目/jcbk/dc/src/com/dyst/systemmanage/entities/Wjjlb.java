package com.dyst.systemmanage.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Entity Wjjlb
 * @author： cgk
 * @date：2016-03-20
 * @version：0.0.1
 * @doc：帮助文档实体类
 */
@Entity
@Table(name="WJJLB")
public class Wjjlb implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 100000000005L;
	
	private int id;//主键id
	private String fileName;//文档名称
	private String fileUrl;//文档路径
	private String buildPno;//创建人用户名
	private String buildName;//创建人姓名
	private Date buildTime;//创建时间
	private Date updateTime;//更新时间
	private String jlzt;//记录状态
	private String remark;//备注
	
	public Wjjlb(){
		
	}

	@Id
	@Column(name="ID", length=10)
	@GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column(name="FILE_NAME", length=200)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Column(name="FILE_URL", length=200)
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Column(name="BUILD_PNO", length=30)
	public String getBuildPno() {
		return buildPno;
	}
	public void setBuildPno(String buildPno) {
		this.buildPno = buildPno;
	}

	@Column(name="BUILD_NAME", length=80)
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	
	@Column(name="BUILD_TIME", columnDefinition="DATETIME")
	public Date getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}
	
	@Column(name="UPDATE_TIME", columnDefinition="DATETIME")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name="JLZT", length=1)
	public String getJlzt() {
		return jlzt;
	}
	public void setJlzt(String jlzt) {
		this.jlzt = jlzt;
	}

	@Column(name="REMARK", length=1024)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}