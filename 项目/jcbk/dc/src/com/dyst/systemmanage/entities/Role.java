package com.dyst.systemmanage.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Entity Role
 * @author： cgk
 * @date：2016-03-20
 * @version：0.0.1
 * @doc：角色实体类
 */
@Entity
@Table(name="ROLE")
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 100000000001L;
	
	private int id;//主键id
	private String roleName;//角色名称
	private String roleType;//角色类型编码
	private int parentId;//父级IDS
	private String permissionContent;//权限内容，总共630位，每一位对应一个功能，用0或1表示是否具有该权限
	private String buildPno;//创建人用户名
	private String buildName;//创建人姓名
	private Date buildTime;//创建时间
	private Date updateTime;//更新时间
	private String remark;//备注
	
	public Role(){
		
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

	@Column(name="ROLE_NAME", length=80)
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Column(name="ROLE_TYPE", length=5)
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@Column(name="PARENT_ID", length=10)
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Column(name="PERMISSION_CONTENT", length=630)
	public String getPermissionContent() {
		return permissionContent;
	}
	public void setPermissionContent(String permissionContent) {
		this.permissionContent = permissionContent;
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

	@Column(name="REMARK", length=1024)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
