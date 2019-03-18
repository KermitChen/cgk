package com.dyst.systemmanage.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * @Entity User
 * @author： cgk
 * @date：2016-03-02
 * @version：0.0.1
 * @doc：用户信息实体类
 */
@Entity
@Table(name="USER")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 100000000000L;
	
	private int id;//主键id
	private String loginName;//用户登录名（警号）
	private String userName;//姓名
	private String password;//密码
	private String telPhone;//电话(手机号码)
	private String identityCard;//身份证号码
	private String deptId;//隶属部门id
	private String systemNo;//系统自身的部门编号
	private String deptName;//隶属部门名称
	private Integer roleId;//角色ID
	private String roleName;//角色名称
	private String position;//职位
	private String buildPno;//创建人用户名
	private String buildName;//创建人姓名
	private Date buildTime;//创建时间
	private Date updateTime;//更新时间
	private String infoSource;//信息来源
	private String enable;//是否可用   1.可用  0.不可用
	private String lskhbm;//隶属考核部门
	private String lskhbmmc;//隶属考核部门名称
	private String remark;//备注
	private String annids;//已读公告的ids
	
	public User(){
		
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

	@Column(name="LOGIN_NAME", length=30)
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name="USER_NAME", length=80)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="PASSWD", length=100)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="TELPHONE", length=15)
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	
	@Column(name="IDENTITY_CARD", length=20)
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	@Column(name="DEPT_ID", length=20)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@Column(name="SYSTEM_NO", length=50)
	public String getSystemNo() {
		return systemNo;
	}

	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
	}

	@Column(name="DEPT_NAME", length=80)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="ROLE_ID", length=10)
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name="ROLE_NAME", length=80)
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name="POSITION", length=5)
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name="INFO_SOURCE", length=1)
	public String getInfoSource() {
		return infoSource;
	}
	public void setInfoSource(String infoSource) {
		this.infoSource = infoSource;
	}

	@Column(name="ENABLE", length=1)
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Column(name="REMARK", length=1024)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Type(type="text")
	@Column(name="ANN_IDS",length=65535)
	public String getAnnids() {
		return annids;
	}

	public void setAnnids(String annids) {
		this.annids = annids;
	}

	@Column(name="LSKHBM", length=20)
	public String getLskhbm() {
		return lskhbm;
	}
	public void setLskhbm(String lskhbm) {
		this.lskhbm = lskhbm;
	}

	@Column(name="LSKHBMMC", length=80)
	public String getLskhbmmc() {
		return lskhbmmc;
	}
	public void setLskhbmmc(String lskhbmmc) {
		this.lskhbmmc = lskhbmmc;
	}
}