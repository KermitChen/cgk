package com.dyst.BaseDataMsg.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * BaseDatamsgRoad entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ROAD", catalog = "dc")
public class Road implements java.io.Serializable {

	// Fields

	private Integer pkId;  
	private String deleteFlag;
	private String roadNo;
	private String roadName;
	private Integer cityId;
	private Integer roadwayNum;
	private String controlBehavior;
	private String roadProperty;
	private String roadGrade;
	private String nearRoad1;
	private String nearRoad2;

	// Constructors

	/** default constructor */
	public Road() {
	}

	/** full constructor */
	public Road(String deleteFlag, String roadNo, String roadName,
			Integer cityId, Integer roadwayNum, String controlBehavior,
			String roadProperty, String roadGrade, String nearRoad1,
			String nearRoad2) {
		this.deleteFlag = deleteFlag;
		this.roadNo = roadNo;
		this.roadName = roadName;
		this.cityId = cityId;
		this.roadwayNum = roadwayNum;
		this.controlBehavior = controlBehavior;
		this.roadProperty = roadProperty;
		this.roadGrade = roadGrade;
		this.nearRoad1 = nearRoad1;
		this.nearRoad2 = nearRoad2;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "PK_ID", unique = true, nullable = false)
	public Integer getPkId() {
		return this.pkId;
	}

	public void setPkId(Integer pkId) {
		this.pkId = pkId;
	}

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "ROAD_NO", length = 10)
	public String getRoadNo() {
		return this.roadNo;
	}

	public void setRoadNo(String roadNo) {
		this.roadNo = roadNo;
	}

	@Column(name = "ROAD_NAME", length = 50)
	public String getRoadName() {
		return this.roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	@Column(name = "CITY_ID")
	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	@Column(name = "ROADWAY_NUM")
	public Integer getRoadwayNum() {
		return this.roadwayNum;
	}

	public void setRoadwayNum(Integer roadwayNum) {
		this.roadwayNum = roadwayNum;
	}

	@Column(name = "CONTROL_BEHAVIOR", length = 10)
	public String getControlBehavior() {
		return this.controlBehavior;
	}

	public void setControlBehavior(String controlBehavior) {
		this.controlBehavior = controlBehavior;
	}

	@Column(name = "ROAD_PROPERTY", length = 10)
	public String getRoadProperty() {
		return this.roadProperty;
	}

	public void setRoadProperty(String roadProperty) {
		this.roadProperty = roadProperty;
	}

	@Column(name = "ROAD_GRADE", length = 10)
	public String getRoadGrade() {
		return this.roadGrade;
	}

	public void setRoadGrade(String roadGrade) {
		this.roadGrade = roadGrade;
	}

	@Column(name = "NEAR_ROAD1", length = 10)
	public String getNearRoad1() {
		return this.nearRoad1;
	}

	public void setNearRoad1(String nearRoad1) {
		this.nearRoad1 = nearRoad1;
	}

	@Column(name = "NEAR_ROAD2", length = 10)
	public String getNearRoad2() {
		return this.nearRoad2;
	}

	public void setNearRoad2(String nearRoad2) {
		this.nearRoad2 = nearRoad2;
	}

}