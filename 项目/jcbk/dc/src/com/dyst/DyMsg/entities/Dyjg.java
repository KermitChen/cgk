package com.dyst.DyMsg.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 订阅结果表   实体
 * Dyjg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DYJG", catalog = "dc")
public class Dyjg implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer dyjgid;
	private Dyxx dyxx;
	private String hphm;
	private String hpzl;
	private String jcdid;
	private Timestamp lrsj;
	private Timestamp tgsj;
	private String dylx;
	private String dyrjh;
	private String cdid;
	private String tpid;

	// Constructors

	/** default constructor */
	public Dyjg() {
	}

	/** full constructor */
	public Dyjg(Dyxx dyxx, String hphm, String hpzl, String jcdid,
			Timestamp lrsj, Timestamp tgsj, String dylx, String dyrjh,
			String cdid,String tpid) {
		this.dyxx = dyxx;
		this.hphm = hphm;
		this.hpzl = hpzl;
		this.jcdid = jcdid;
		this.lrsj = lrsj;
		this.tgsj = tgsj;
		this.dylx = dylx;
		this.dyrjh = dyrjh;
		this.cdid = cdid;
		this.tpid = tpid;
	}

	// Property accessors
	@Id
	@GenericGenerator(name="idGenerator",strategy="native")
	@GeneratedValue(generator="idGenerator")
	@Column(name = "DYJGID", unique = true, nullable = false)
	public Integer getDyjgid() {
		return this.dyjgid;
	}

	public void setDyjgid(Integer dyjgid) {
		this.dyjgid = dyjgid;
	}

	@OneToOne
	@JoinColumn(name="DYID",insertable=true,unique=true)
	public Dyxx getDyxx() {
		return dyxx;
	}

	public void setDyxx(Dyxx dyxx) {
		this.dyxx = dyxx;
	}

	@Column(name = "HPHM", length = 10)
	public String getHphm() {
		return this.hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	@Column(name = "HPZL", length = 5)
	public String getHpzl() {
		return this.hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	@Column(name = "JCDID", length = 10)
	public String getJcdid() {
		return this.jcdid;
	}

	public void setJcdid(String jcdid) {
		this.jcdid = jcdid;
	}

	@Column(name = "LRSJ", length = 19)
	public Timestamp getLrsj() {
		return this.lrsj;
	}

	public void setLrsj(Timestamp lrsj) {
		this.lrsj = lrsj;
	}

	@Column(name = "TGSJ", length = 19)
	public Timestamp getTgsj() {
		return this.tgsj;
	}

	public void setTgsj(Timestamp tgsj) {
		this.tgsj = tgsj;
	}

	@Column(name = "DYLX", length = 10)
	public String getDylx() {
		return this.dylx;
	}

	public void setDylx(String dylx) {
		this.dylx = dylx;
	}

	@Column(name = "DYRJH", length = 20)
	public String getDyrjh() {
		return this.dyrjh;
	}

	public void setDyrjh(String dyrjh) {
		this.dyrjh = dyrjh;
	}

	@Column(name = "CDID", length = 5)
	public String getCdid() {
		return this.cdid;
	}

	public void setCdid(String cdid) {
		this.cdid = cdid;
	}
	@Column(name= "TPID",length = 255)
	public String getTpid() {
		return tpid;
	}

	public void setTpid(String tpid) {
		this.tpid = tpid;
	}
	

}