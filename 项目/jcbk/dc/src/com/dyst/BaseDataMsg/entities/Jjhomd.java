package com.dyst.BaseDataMsg.entities;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Jjhomd entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JJHOMD", catalog = "dc")
public class Jjhomd implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5266634520466230810L;
	// Fields

	private Integer id;
	private String cphid;
	private String cplx;
	private String clsyz;
	private String sqrjh;
	private String sqrxm;
	private String sqrdw;
	private String sqrdwmc;
	private Timestamp lrsj;
	private String lryy;
	private String zt;
	private String rwzt;
	private String jlzt;
	private String bzsm;
	private String cb;
	private String csys;
	private String cz;
	private Jjhomdsp jjhomdsp;
	private JjhomdCx jjhomdCx;
	private String sqrjsdj;
	
	// Constructors

	/** default constructor */
	public Jjhomd() {
	}

	/** minimal constructor */
	public Jjhomd(Integer id, String cphid, String cplx,
			String sqrjh, String sqrxm, Timestamp lrsj, String jlzt,Jjhomdsp jjhomdsp,JjhomdCx jjhomdCx,String sqrjsdj) {
		this.id = id;
		this.cphid = cphid;
		this.cplx = cplx;
		this.sqrjh = sqrjh;
		this.sqrxm = sqrxm;
		this.lrsj = lrsj;
		this.jlzt = jlzt;
		this.jjhomdsp = jjhomdsp;
		this.sqrjsdj = sqrjsdj;
		this.jjhomdCx = jjhomdCx;
	}

	/** full constructor */
	public Jjhomd(Integer id, String cphid, String cplx, String clsyz, String sqrjh,
			String sqrxm, String sqrdw, String sqrdwmc, Timestamp lrsj,
			String lryy, String zt, String jlzt, String bzsm, String cb,
			String csys, String cz,Jjhomdsp jjhomdsp,JjhomdCx jjhomdCx,String sqrjsdj) {
		this.id = id;
		this.cphid = cphid;
		this.cplx = cplx;
		this.clsyz = clsyz;
		this.sqrjh = sqrjh;
		this.sqrxm = sqrxm;
		this.sqrdw = sqrdw;
		this.sqrdwmc = sqrdwmc;
		this.lrsj = lrsj;
		this.lryy = lryy;
		this.zt = zt;
		this.jlzt = jlzt;
		this.bzsm = bzsm;
		this.cb = cb;
		this.csys = csys;
		this.cz = cz;
		this.jjhomdsp = jjhomdsp;
		this.jjhomdCx = jjhomdCx;
		this.sqrjsdj = sqrjsdj;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "cphid", nullable = false, length = 20)
	public String getCphid() {
		return this.cphid;
	}

	public void setCphid(String cphid) {
		this.cphid = cphid;
	}

	@Column(name = "cplx", nullable = false, length = 2)
	public String getCplx() {
		return this.cplx;
	}

	public void setCplx(String cplx) {
		this.cplx = cplx;
	}

	@Column(name = "clsyz", length = 200)
	public String getClsyz() {
		return this.clsyz;
	}

	public void setClsyz(String clsyz) {
		this.clsyz = clsyz;
	}


	@Column(name = "sqrjh", length = 30)
	public String getSqrjh() {
		return this.sqrjh;
	}

	public void setSqrjh(String sqrjh) {
		this.sqrjh = sqrjh;
	}

	@Column(name = "sqrxm", length = 80)
	public String getSqrxm() {
		return this.sqrxm;
	}

	public void setSqrxm(String sqrxm) {
		this.sqrxm = sqrxm;
	}

	@Column(name = "sqrdw", length = 20)
	public String getSqrdw() {
		return this.sqrdw;
	}

	public void setSqrdw(String sqrdw) {
		this.sqrdw = sqrdw;
	}

	@Column(name = "sqrdwmc", length = 80)
	public String getSqrdwmc() {
		return this.sqrdwmc;
	}

	public void setSqrdwmc(String sqrdwmc) {
		this.sqrdwmc = sqrdwmc;
	}

	@Column(name = "lrsj", length = 19)
	public Timestamp getLrsj() {
		return this.lrsj;
	}

	public void setLrsj(Timestamp lrsj) {
		this.lrsj = lrsj;
	}

	@Column(name = "lryy", length = 1024)
	public String getLryy() {
		return this.lryy;
	}

	public void setLryy(String lryy) {
		this.lryy = lryy;
	}

	@Column(name = "zt", length = 2)
	public String getZt() {
		return this.zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
	@Column(name = "rwzt", length = 5)
	public String getRwzt() {
		return rwzt;
	}

	public void setRwzt(String rwzt) {
		this.rwzt = rwzt;
	}

	@Column(name = "jlzt", length = 5)
	public String getJlzt() {
		return this.jlzt;
	}

	public void setJlzt(String jlzt) {
		this.jlzt = jlzt;
	}

	@Column(name = "bzsm", length = 1024)
	public String getBzsm() {
		return this.bzsm;
	}

	public void setBzsm(String bzsm) {
		this.bzsm = bzsm;
	}

	@Column(name = "cb", length = 100)
	public String getCb() {
		return this.cb;
	}

	public void setCb(String cb) {
		this.cb = cb;
	}

	@Column(name = "csys", length = 2)
	public String getCsys() {
		return this.csys;
	}

	public void setCsys(String csys) {
		this.csys = csys;
	}

	@Column(name = "cz", length = 200)
	public String getCz() {
		return this.cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name ="spid")
	public Jjhomdsp getJjhomdsp() {
		return jjhomdsp;
	}

	public void setJjhomdsp(Jjhomdsp jjhomdsp) {
		this.jjhomdsp = jjhomdsp;
	}
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name ="cxid")
	public JjhomdCx getJjhomdCx() {
		return jjhomdCx;
	}
	
	public void setJjhomdCx(JjhomdCx jjhomdCx) {
		this.jjhomdCx = jjhomdCx;
	}

	@Column(name = "sqrjsdj", length = 5)
	public String getSqrjsdj() {
		return sqrjsdj;
	}

	public void setSqrjsdj(String sqrjsdj) {
		this.sqrjsdj = sqrjsdj;
	}
	

}