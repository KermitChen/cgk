package com.dyst.jxkh.entities;

public class BktjItemEntity {

	//布控大类的id
	private String parentSerialNo;
	//布控大类下的某布控类别的名称
	private String typeDesc;
	//布控大类下的某布控类别的id
	private String typeSerialNo;
	
	private Integer bdbk =0;
	private Integer bjfwt = 0;
	private Integer ldbk = 0;
	private Integer subtotal = 0;
	public String getParentSerialNo() {
		return parentSerialNo;
	}
	public void setParentSerialNo(String parentSerialNo) {
		this.parentSerialNo = parentSerialNo;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getTypeSerialNo() {
		return typeSerialNo;
	}
	public void setTypeSerialNo(String typeSerialNo) {
		this.typeSerialNo = typeSerialNo;
	}
	public Integer getBdbk() {
		return bdbk;
	}
	public void setBdbk(Integer bdbk) {
		this.bdbk = bdbk;
	}
	public Integer getBjfwt() {
		return bjfwt;
	}
	public void setBjfwt(Integer bjfwt) {
		this.bjfwt = bjfwt;
	}
	public Integer getLdbk() {
		return ldbk;
	}
	public void setLdbk(Integer ldbk) {
		this.ldbk = ldbk;
	}
	public Integer getSubtotal() {
		subtotal = bdbk+bjfwt+ldbk;
		return subtotal;
	}
	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}
	
}
