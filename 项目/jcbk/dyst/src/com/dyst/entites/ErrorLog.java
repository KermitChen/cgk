package com.dyst.entites;

public class ErrorLog {

	private String operator;
	private String operatorName;
	private String ip;
	private String moduleName;
	private String operateName;
	private String args;
	private String operateDate;
	private String message;
	
	public ErrorLog() {
		super();
	}
	
	public ErrorLog(String operator, String operatorName, String ip, String moduleName,
			String operateName, String args, String operateDate, String message) {
		super();
		this.operator = operator;
		this.operatorName = operatorName;
		this.ip = ip;
		this.moduleName = moduleName;
		this.operateName = operateName;
		this.args = args;
		this.operateDate = operateDate;
		this.message = message;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
