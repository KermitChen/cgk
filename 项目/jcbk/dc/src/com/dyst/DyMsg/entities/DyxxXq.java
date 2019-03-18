package com.dyst.DyMsg.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * DyxxXq entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DYXX_XQ", catalog = "dc")
public class DyxxXq implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6036059215516079575L;
	private Integer dyxqid;
	private Dyxx dyxx;
	private String hphm2;
	private String hpys2;
	private String jcd2;
	private String bz2;
	private String dxhm2;

	// Constructors

	/** default constructor */
	public DyxxXq() {
	}

	/** full constructor */
	public DyxxXq(Dyxx dyxx, String hphm2, String hpys2, String jcd2, String bz2,String dxhm2) {
		this.dyxx = dyxx;
		this.hphm2 = hphm2;
		this.hpys2 = hpys2;
		this.jcd2 = jcd2;
		this.bz2 = bz2;
		this.dxhm2 = dxhm2;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "dyxqid", unique = true, nullable = false)
	public Integer getDyxqid() {
		return this.dyxqid;
	}

	public void setDyxqid(Integer dyxqid) {
		this.dyxqid = dyxqid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dyxxId")
	public Dyxx getDyxx() {
		return this.dyxx;
	}

	public void setDyxx(Dyxx dyxx) {
		this.dyxx = dyxx;
	}

	@Column(name = "hphm2", length = 400)
	public String getHphm2() {
		return this.hphm2;
	}

	public void setHphm2(String hphm2) {
		this.hphm2 = hphm2;
	}

	@Column(name = "hpys2", length = 20)
	public String getHpys2() {
		return this.hpys2;
	}

	public void setHpys2(String hpys2) {
		this.hpys2 = hpys2;
	}

	@Column(name = "jcd2", length = 4000)
	public String getJcd2() {
		return this.jcd2;
	}

	public void setJcd2(String jcd2) {
		this.jcd2 = jcd2;
	}

	@Column(name = "bz2")
	public String getBz2() {
		return this.bz2;
	}

	public void setBz2(String bz2) {
		this.bz2 = bz2;
	}
	
	@Column(name = "dxhm2")
	public String getDxhm2() {
		return this.dxhm2;
	}
	
	public void setDxhm2(String dxhm2) {
		this.dxhm2 = dxhm2;
	}

}