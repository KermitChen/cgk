package com.dyst.BaseDataMsg.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Jcd entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "JCD", catalog = "dc")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Jcd implements java.io.Serializable {

	// Fields

	private String id;
	private String jcdmc;
	private String dlid;
	private String cqid;
	private String xsfx;
	private String jcdxz;
	private String py;
	private Double jd;
	private Double wd;
	private String jcddz;
	private Short jccds;
	private Short sbs;
	private Short xyjcds;
	private Timestamp jlsj;
	private String qybz;
	private String tpcflj;

	// Constructors

	/** default constructor */
	public Jcd() {
	}

	/** minimal constructor */
	public Jcd(String id) {
		this.id = id;
	}

	/** full constructor */
	public Jcd(String id, String jcdmc, String dlid, String cqid, String xsfx, 
			String jcdxz, Short sbs, Short jccds, Short xyjcds, String py, Double jd, 
			Double wd, String qybz, String jcddz, Timestamp jlsj, String tpcflj) {
		this.id = id;
		this.jcdmc = jcdmc;
		this.dlid = dlid;
		this.xsfx = xsfx;
		this.jcdxz = jcdxz;
		this.sbs = sbs;
		this.jccds = jccds;
		this.xyjcds = xyjcds;
		this.cqid = cqid;
		this.py = py;
		this.jd = jd;
		this.wd = wd;
		this.qybz = qybz;
		this.jcddz = jcddz;
		this.jlsj = jlsj;
		this.tpcflj = tpcflj;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 8)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "JCDMC", length = 100)
	public String getJcdmc() {
		return this.jcdmc;
	}

	public void setJcdmc(String jcdmc) {
		this.jcdmc = jcdmc;
	}

	@Column(name = "DLID", length = 6)
	public String getDlid() {
		return this.dlid;
	}

	public void setDlid(String dlid) {
		this.dlid = dlid;
	}

	@Column(name = "XSFX", length = 10)
	public String getXsfx() {
		return this.xsfx;
	}

	public void setXsfx(String xsfx) {
		this.xsfx = xsfx;
	}

	@Column(name = "JCDXZ", length = 10)
	public String getJcdxz() {
		return this.jcdxz;
	}

	public void setJcdxz(String jcdxz) {
		this.jcdxz = jcdxz;
	}

	@Column(name = "SBS")
	public Short getSbs() {
		return this.sbs;
	}

	public void setSbs(Short sbs) {
		this.sbs = sbs;
	}

	@Column(name = "JCCDS")
	public Short getJccds() {
		return this.jccds;
	}

	public void setJccds(Short jccds) {
		this.jccds = jccds;
	}

	@Column(name = "XYJCDS")
	public Short getXyjcds() {
		return this.xyjcds;
	}

	public void setXyjcds(Short xyjcds) {
		this.xyjcds = xyjcds;
	}

	@Column(name = "CQID", length = 2)
	public String getCqid() {
		return this.cqid;
	}

	public void setCqid(String cqid) {
		this.cqid = cqid;
	}

	@Column(name = "PY", length = 50)
	public String getPy() {
		return this.py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	@Column(name = "JD", precision = 22, scale = 0)
	public Double getJd() {
		return this.jd;
	}

	public void setJd(Double jd) {
		this.jd = jd;
	}

	@Column(name = "WD", precision = 22, scale = 0)
	public Double getWd() {
		return this.wd;
	}

	public void setWd(Double wd) {
		this.wd = wd;
	}

	@Column(name = "QYBZ", length = 1)
	public String getQybz() {
		return this.qybz;
	}

	public void setQybz(String qybz) {
		this.qybz = qybz;
	}

	@Column(name = "JCDDZ", length = 100)
	public String getJcddz() {
		return this.jcddz;
	}

	public void setJcddz(String jcddz) {
		this.jcddz = jcddz;
	}

	
	@Column(name = "JLSJ", length = 19)
	public Timestamp getJlsj() {
		return this.jlsj;
	}

	public void setJlsj(Timestamp jlsj) {
		this.jlsj = jlsj;
	}

	public String getTpcflj() {
		return tpcflj;
	}

	public void setTpcflj(String tpcflj) {
		this.tpcflj = tpcflj;
	}
}