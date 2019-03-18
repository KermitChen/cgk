package com.dyst.BaseDataMsg.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Dictionary entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DICTIONARY")
public class Dictionary implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1739114807045185940L;
	// Fields

	private Integer dictionaryid;
	private String deleteFlag;
	private String typeCode;
	private String memo;
	private String typeSerialNo;
	private String typeDesc;
	private String remark;
	private String loginName;
	private String addOperator;
	private Timestamp addTime;
	private String editFlag;

	// Constructors

	/** default constructor */
	public Dictionary() {
	}

	/** full constructor */
	public Dictionary(String deleteFlag, String typeCode, String typeSerialNo,
			String typeDesc, String memo, String loginName, String addOperator,
			Timestamp addTime, String editFlag) {
		this.deleteFlag = deleteFlag;
		this.memo = memo;
		this.typeCode = typeCode;
		
		this.typeSerialNo = typeSerialNo;
		this.typeDesc = typeDesc;
		
		this.loginName = loginName;
		this.addOperator = addOperator;
		this.addTime = addTime;
		this.editFlag = editFlag;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "DICTIONARYID", unique = true, nullable = false)
	public Integer getDictionaryid() {
		return this.dictionaryid;
	}

	public void setDictionaryid(Integer dictionaryid) {
		this.dictionaryid = dictionaryid;
	}

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "TYPE_CODE", length = 110)
	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "TYPE_SERIAL_NO", length = 120)
	public String getTypeSerialNo() {
		return this.typeSerialNo;
	}

	public void setTypeSerialNo(String typeSerialNo) {
		this.typeSerialNo = typeSerialNo;
	}

	@Column(name = "TYPE_DESC", length = 200)
	public String getTypeDesc() {
		return this.typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	@Column(name = "MEMO", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "ADD_OPERATOR", length = 20)
	public String getAddOperator() {
		return this.addOperator;
	}

	public void setAddOperator(String addOperator) {
		this.addOperator = addOperator;
	}

	@Column(name = "ADD_TIME", length = 19)
	public Timestamp getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	@Column(name = "EDIT_FLAG", length = 1)
	public String getEditFlag() {
		return this.editFlag;
	}

	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}

	@Column(name = "LOGIN_NAME", length = 30)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}