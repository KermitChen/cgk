package com.dyst.systemmanage.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Entity FunctionTree
 * @author： cgk
 * @date：2016-03-20
 * @version：0.0.1
 * @doc：功能目录实体类
 */
@Entity
@Table(name="FUNCTION_TREE")
public class FunctionTree implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 100000000002L;
	
	private int id;//主键id
	private String funName;//功能名称
	private String linkPath;//访问地址 例如：user/login.do、views/systemmanage/main/main.jsp
	private String funIcon;//功能图标 例如：icon4.png
	private int permissionPosition;//权限位 1,2,3,4……(每个大模块占21位,模块本身占1位,最多20个子功能)1-21、22-42、43-63、64-84
	private int parentPermission;
	private String rank;//功能分级(仅支持两级，即1和2)
	private int sort;//功能排序 1,2,3,4…… (针对同级进行排序)
	private String enable;//是否启用 0不启用 1启用
	private String remark;//备注
	
	public FunctionTree(){
		
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

	@Column(name="FUN_NAME", length=30)
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}

	@Column(name="LINK_PATH", length=100)
	public String getLinkPath() {
		return linkPath;
	}
	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}

	@Column(name="FUN_ICON", length=20)
	public String getFunIcon() {
		return funIcon;
	}
	public void setFunIcon(String funIcon) {
		this.funIcon = funIcon;
	}

	@Column(name="PERMISSION_POSITION", length=4)
	public int getPermissionPosition() {
		return permissionPosition;
	}
	public void setPermissionPosition(int permissionPosition) {
		this.permissionPosition = permissionPosition;
	}
	
	@Column(name="PARENT_PERMISSION", length=4)
	public int getParentPermission() {
		return parentPermission;
	}
	public void setParentPermission(int parentPermission) {
		this.parentPermission = parentPermission;
	}

	@Column(name="RANK", length=1)
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}

	@Column(name="SORT", length=2)
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
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
}
