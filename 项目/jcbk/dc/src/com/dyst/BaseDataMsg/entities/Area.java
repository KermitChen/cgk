package com.dyst.BaseDataMsg.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Area entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AREA", catalog = "dc")
public class Area implements java.io.Serializable {

	// Fields

	private Integer id;
	private String deleteFlag;
	private String areano;
	private String areaname;
	private String suparea;
	private String specialareaflagName;
	private String specialareaflagDm;

	// Constructors

	/** default constructor */
	public Area() {
	}

	/** minimal constructor */
	public Area(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Area(Integer id, String deleteFlag, String areano, String areaname,
			String suparea, String specialareaflagName, String specialareaflagDm) {
		this.id = id;
		this.deleteFlag = deleteFlag;
		this.areano = areano;
		this.areaname = areaname;
		this.suparea = suparea;
		this.specialareaflagName = specialareaflagName;
		this.specialareaflagDm = specialareaflagDm;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "AREANO", length = 10)
	public String getAreano() {
		return this.areano;
	}

	public void setAreano(String areano) {
		this.areano = areano;
	}

	@Column(name = "AREANAME", length = 30)
	public String getAreaname() {
		return this.areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	@Column(name = "SUPAREA", length = 10)
	public String getSuparea() {
		return this.suparea;
	}

	public void setSuparea(String suparea) {
		this.suparea = suparea;
	}

	@Column(name = "SPECIALAREAFLAG_NAME", length = 10)
	public String getSpecialareaflagName() {
		return this.specialareaflagName;
	}

	public void setSpecialareaflagName(String specialareaflagName) {
		this.specialareaflagName = specialareaflagName;
	}

	@Column(name = "SPECIALAREAFLAG_DM", length = 10)
	public String getSpecialareaflagDm() {
		return this.specialareaflagDm;
	}

	public void setSpecialareaflagDm(String specialareaflagDm) {
		this.specialareaflagDm = specialareaflagDm;
	}

}