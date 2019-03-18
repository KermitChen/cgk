package com.dyst.activemq.entities;

import java.io.Serializable;

/**
 * 所有的组织架构都在认证中心中添加。
 * @author frying 2014-08-13
 *
 */
public class SyncDept implements Serializable {
	private static final long serialVersionUID = 549864784849858L;
	
	private String deptCode;                  //组织编码
	private String name;	//名称
	private String description;	//描述
	private String levelId;	//级别
	private Integer parentId;	//父部门ID
	private Integer type;	//类型
	private Integer listOrder;	//排序号
	private String remark;	//备注
	private String phone;                     //电话
	private String fax;                       //传真
	private String mailCode;                  //邮编
	private String eMail;                     //电子邮箱
	private String address;                   //地址
	
	private Integer isLeaf = 1;	//是否子部门
	private String path;	//所有父部门ID
	private Integer pathLevel;	//pathLevel
	private String py;	//部门拼音
	private String pytree;	//所有父部门的拼音
	private String pathtree;	//所有父部门名称
	
	private String parentDeptCode;	//父部门的组织编码（用于同步时，定位父部门）
	private String oldDeptCode;	//原组织编码，用于防止组织部门有变更
	private String uuid;	//品高mySql中的ID
	
	public SyncDept() {
		super();
	}

	
	public SyncDept(String name, String deptCode) {
		super();
		this.name = name;
		this.deptCode = deptCode;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getPathLevel() {
		return pathLevel;
	}

	public void setPathLevel(Integer pathLevel) {
		this.pathLevel = pathLevel;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public String getPytree() {
		return pytree;
	}

	public void setPytree(String pytree) {
		this.pytree = pytree;
	}

	public String getPathtree() {
		return pathtree;
	}

	public void setPathtree(String pathtree) {
		this.pathtree = pathtree;
	}

	public Integer getListOrder() {
		return listOrder;
	}

	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMailCode() {
		return mailCode;
	}

	public void setMailCode(String mailCode) {
		this.mailCode = mailCode;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getParentDeptCode() {
		return parentDeptCode;
	}

	public void setParentDeptCode(String parentDeptCode) {
		this.parentDeptCode = parentDeptCode;
	}

	public String getOldDeptCode() {
		return oldDeptCode;
	}

	public void setOldDeptCode(String oldDeptCode) {
		this.oldDeptCode = oldDeptCode;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}