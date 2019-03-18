package com.dyst.jxkh.entities;

public class YjqsEntity22 {

	private String deptID;
	private String deptName;
	
	private YjqsEntity2 yjqsEntity;
	private ZlqsEntity2 zlqsEntity;
	private CzfkEntity2 czfkEntity;
	
	public YjqsEntity22() {
		
	}
	
	public YjqsEntity2 getYjqsEntity() {
		return yjqsEntity;
	}
	
	public void setYjqsEntity(YjqsEntity2 yjqsEntity) {
		this.yjqsEntity = yjqsEntity;
	}
	
	public ZlqsEntity2 getZlqsEntity() {
		return zlqsEntity;
	}
	
	public void setZlqsEntity(ZlqsEntity2 zlqsEntity) {
		this.zlqsEntity = zlqsEntity;
	}
	
	public CzfkEntity2 getCzfkEntity() {
		return czfkEntity;
	}
	
	public void setCzfkEntity(CzfkEntity2 czfkEntity) {
		this.czfkEntity = czfkEntity;
	}

	public String getDeptID() {
		return deptID;
	}

	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}