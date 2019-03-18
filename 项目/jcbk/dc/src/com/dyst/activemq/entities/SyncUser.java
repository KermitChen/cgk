package com.dyst.activemq.entities;

import java.io.Serializable;
import java.util.Date;

public class SyncUser implements Serializable {
	private static final long serialVersionUID = 549863253849857L;

	// 基本属性：用户名，真实姓名，所属部门，是否超管，是否可用
	private String userLoginId;	// 员工登录ID
	private String userPassword;	// 员工密码
	private String userName;	// 员工姓名
	private Integer gender;	// 员工性别（性别 0-男,1-女）
	private Integer isAdmin; //是否管理员
	private Integer listOrder;	//排序号
	
	private Date birthday;	// 出生日期
	private String position;	// 职位
	private String userCode;	// 警号
	private String idcard;	// 身份证号码
	private String empType; // 职工类别
	private String nativePlace;// 籍贯
	private String nation;	//民族
	private String govFeature;	// 政治面貌
	private String marriage;	// 婚姻状况
	private Integer departmentId;	//部门ID（如departmentId与认证中心的departmentId不同，则用deptCode）
	private String deptName;	//部门名称
	private String deptCode; // 组织编码
	private String mobilePhone;	// 移动电话
	private String officePhone;	// 办公室电话
	private String shortMobile;	// 手机短号
	private String homeAddress;	// 家庭住址
	private String homePhone;	// 家庭电话
	private String emailAddress;	// 公安网邮箱
	private String emailAddress2;	// 互联网邮箱
	private String fax;	//传真
	private Integer status;	//状态（1-正常 0-禁用）
	private String mobilePhone2;	//移动电话2
	private String imei;	//
	private String equipmentNum;	//设备编号
	private Integer bingoOpenStatus=0;	//品高的开启状态(用于记录品高已开启的用户)
	public SyncUser() {
	}

	public SyncUser(String userLoginId, String empName) {
		super();
		this.userLoginId = userLoginId;
		this.userName = empName;
	}


	public SyncUser(Integer empId, String userLoginId, String empName,
			String mobilePhone) {
		this.userLoginId = userLoginId;
		this.userName = empName;
		this.mobilePhone = mobilePhone;
	}

	public SyncUser( String userLoginId, String empName,
			String mobilePhone, String officePhone, Integer departmentId) {
		this.userLoginId = userLoginId;
		this.userName = empName;
		this.mobilePhone = mobilePhone;
		this.officePhone = officePhone;
		this.departmentId = departmentId;
	}


	public String getUserName() {
		return this.userName;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String currentPassword) {
		this.userPassword = currentPassword;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getGender() {
		return this.gender;
	}

	public String getOfficePhone() {
		return this.officePhone;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}


	public void setUserName(String empName) {
		this.userName = empName;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getShortMobile() {
		return shortMobile;
	}

	public void setShortMobile(String shortMobile) {
		this.shortMobile = shortMobile;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getEmailAddress2() {
		return emailAddress2;
	}

	public void setEmailAddress2(String emailAddress2) {
		this.emailAddress2 = emailAddress2;
	}

	public Integer getListOrder() {
		return listOrder;
	}

	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getGovFeature() {
		return govFeature;
	}

	public void setGovFeature(String govFeature) {
		this.govFeature = govFeature;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer deptId) {
		this.departmentId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String departmentName) {
		this.deptName = departmentName;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getMobilePhone2() {
		return mobilePhone2;
	}

	public void setMobilePhone2(String mobilePhone2) {
		this.mobilePhone2 = mobilePhone2;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getEquipmentNum() {
		return equipmentNum;
	}

	public void setEquipmentNum(String equipmentNum) {
		this.equipmentNum = equipmentNum;
	}

	public Integer getBingoOpenStatus() {
		return bingoOpenStatus;
	}

	public void setBingoOpenStatus(Integer bingoOpenStatus) {
		this.bingoOpenStatus = bingoOpenStatus;
	}

	/**
	 * 表示管理员
	 * 
	 * @return
	 */
	public boolean isAdmin() {
		return (isAdmin != null && isAdmin == 1);
	}
}