package com.dyst.BaseDataMsg.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DicDisType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DIC_DISPATCHED_TYPE", catalog = "dc")
public class DicDisType implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1873217213718548800L;
	private String id;
	private String name;
	private String superid;
	private String showOrder;
	private String level;

	// Constructors

	/** default constructor */
	public DicDisType() {
	}

	/** full constructor */
	public DicDisType(String id, String name, String superid, String showOrder,
			String level) {
		this.id = id;
		this.name = name;
		this.superid = superid;
		this.showOrder = showOrder;
		this.level = level;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SUPERID", nullable = false, length = 20)
	public String getSuperid() {
		return this.superid;
	}

	public void setSuperid(String superid) {
		this.superid = superid;
	}

	@Column(name = "SHOW_ORDER", nullable = false, length = 2)
	public String getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
	}

	@Column(name = "LEVEL", nullable = false, length = 2)
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}