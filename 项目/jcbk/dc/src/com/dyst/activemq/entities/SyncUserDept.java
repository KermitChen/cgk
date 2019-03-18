package com.dyst.activemq.entities;

import java.io.Serializable;
/**
 * SyncMessage keyId 为userLoginId
 * 
 */
public class SyncUserDept implements Serializable {
	
	private static final long serialVersionUID = 766948916936298034L;
	
	private Integer isPrimary; //是否主部门（1-是,0-否）
	private Integer isCharge; // 未用到
	private String deptCode; // 组织编码

	public SyncUserDept() {
		super();
	}


	public Integer getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Integer isPrimary) {
		this.isPrimary = isPrimary;
	}

	public Integer getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(Integer isCharge) {
		this.isCharge = isCharge;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
}