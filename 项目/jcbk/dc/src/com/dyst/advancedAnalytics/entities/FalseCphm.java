package com.dyst.advancedAnalytics.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FalseCphm entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FALSE_CPHM", catalog = "dc")
public class FalseCphm implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8699523819477475193L;
	private Integer id;
	private String jcphm;
	private Date lrsj;
	private String jlzt;
	private String clr;
	private Date clsj;
	private String clyj;
	private Date jskssj;
	private Date jsjssj;
	private String jcplx;

	// Constructors

	/** default constructor */
	public FalseCphm() {
	}

	/** full constructor */
	public FalseCphm(String jcphm, Date lrsj, String jlzt, String clr,
			Date clsj, String clyj, Date jskssj, Date jsjssj, String jcplx) {
		this.jcphm = jcphm;
		this.lrsj = lrsj;
		this.jlzt = jlzt;
		this.clr = clr;
		this.clsj = clsj;
		this.clyj = clyj;
		this.jskssj = jskssj;
		this.jsjssj = jsjssj;
		this.jcplx = jcplx;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "jcphm", length = 50)
	public String getJcphm() {
		return this.jcphm;
	}

	public void setJcphm(String jcphm) {
		this.jcphm = jcphm;
	}

	@Column(name = "lrsj", length = 19)
	public Date getLrsj() {
		return this.lrsj;
	}

	public void setLrsj(Date lrsj) {
		this.lrsj = lrsj;
	}

	@Column(name = "jlzt", length = 10)
	public String getJlzt() {
		return this.jlzt;
	}

	public void setJlzt(String jlzt) {
		this.jlzt = jlzt;
	}

	@Column(name = "clr", length = 20)
	public String getClr() {
		return this.clr;
	}

	public void setClr(String clr) {
		this.clr = clr;
	}

	@Column(name = "clsj", length = 19)
	public Date getClsj() {
		return this.clsj;
	}

	public void setClsj(Date clsj) {
		this.clsj = clsj;
	}

	@Column(name = "clyj", length = 100)
	public String getClyj() {
		return this.clyj;
	}

	public void setClyj(String clyj) {
		this.clyj = clyj;
	}

	@Column(name = "jskssj", length = 19)
	public Date getJskssj() {
		return this.jskssj;
	}

	public void setJskssj(Date jskssj) {
		this.jskssj = jskssj;
	}

	@Column(name = "jsjssj", length = 19)
	public Date getJsjssj() {
		return this.jsjssj;
	}

	public void setJsjssj(Date jsjssj) {
		this.jsjssj = jsjssj;
	}

	@Column(name = "jcplx", length = 10)
	public String getJcplx() {
		return this.jcplx;
	}

	public void setJcplx(String jcplx) {
		this.jcplx = jcplx;
	}

}