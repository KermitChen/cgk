package com.dyst.vehicleQuery.dto;

public class PathDay {

	private String year;
	private String month;
	private String day;
	private String className;
	
	
	public PathDay() {
		super();
	}
	public PathDay(String year, String month, String day, String className) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.className = className;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	
}
