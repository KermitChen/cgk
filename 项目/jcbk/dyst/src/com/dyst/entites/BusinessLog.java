package com.dyst.entites;

public class BusinessLog {

	private String operator;
	private String operatorName;
	private String ip;
	private String moduleName;
	private String operateName;
	private String args;
	private String operateDate;
	private String operatorDeptNo;
	private String operatorDeptName;
	
	public BusinessLog() {
		super();
	}
	public BusinessLog(String operator, String operatorName, String ip, String moduleName,
			String operateName, String args, String operateDate, String operatorDeptNo, String operatorDeptName) {
		super();
		this.operator = operator;
		this.operatorName = operatorName;
		this.ip = ip;
		this.moduleName = moduleName;
		this.operateName = operateName;
		this.args = args;
		this.operateDate = operateDate;
		this.operatorDeptNo = operatorDeptNo;
		this.operatorDeptName = operatorDeptName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	public String getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}
	public String getOperatorDeptNo() {
		return operatorDeptNo;
	}
	public void setOperatorDeptNo(String operatorDeptNo) {
		this.operatorDeptNo = operatorDeptNo;
	}
	public String getOperatorDeptName() {
		return operatorDeptName;
	}
	public void setOperatorDeptName(String operatorDeptName) {
		this.operatorDeptName = operatorDeptName;
	}
	
	@Override
	public String toString() {
		return "BusinessLog [operator=" + operator + ", operatorName="
				+ operatorName + ", ip=" + ip + ", moduleName=" + moduleName
				+ ", operateName=" + operateName + ", args=" + args
				+ ", operateDate=" + operateDate + ", operatorDeptNo=" + operatorDeptNo 
				+ ", operatorDeptName=" + operatorDeptName + "]";
	}
}