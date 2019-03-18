package com.dyst.advancedAnalytics.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Sb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SB", catalog = "dc")
public class Sb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1775186816043257067L;
	private Integer id;
	private String resFlag;
	private String cphm1;
	private String cphm2;
	private String cplx1;
	private String cplx2;
	private String hpzl;
	private String tgsj;
	private String scsj;
	private String jcdid;
	private String jcklx;
	private String cdid;
	private String csys;
	private String cllx;
	private String cb;
	private String xxkbm;
	private String wflx;
	private String sd;
	private String qdid;
	private String xsfx;
	private String tpzs;
	private String tpid1;
	private String tpid2;
	private String tpid3;
	private String tpid4;
	private String tpid5;
	private String qpsfwc;
	private String hpsfwc;
	private String qhsfyz;
	private String zxd;
	private String fbcd;
	private String bcbz;
	private String fqh;
	private String spurl;
	private String bl;
	private String byzd1;
	private String byzd2;
	private String byzd3;

	// Constructors

	/** default constructor */
	public Sb() {
	}

	/** minimal constructor */
	public Sb(Integer id, String resFlag) {
		this.id = id;
		this.resFlag = resFlag;
	}

	/** full constructor */
	public Sb(Integer id, String resFlag, String cphm1, String cphm2,
			String cplx1, String cplx2, String hpzl, String tgsj, String scsj,
			String jcdid, String jcklx, String cdid, String csys, String cllx,
			String cb, String xxkbm, String wflx, String sd, String qdid,
			String xsfx, String tpzs, String tpid1, String tpid2, String tpid3,
			String tpid4, String tpid5, String qpsfwc, String hpsfwc,
			String qhsfyz, String zxd, String fbcd, String bcbz, String fqh,
			String spurl, String bl, String byzd1, String byzd2, String byzd3) {
		this.id = id;
		this.resFlag = resFlag;
		this.cphm1 = cphm1;
		this.cphm2 = cphm2;
		this.cplx1 = cplx1;
		this.cplx2 = cplx2;
		this.hpzl = hpzl;
		this.tgsj = tgsj;
		this.scsj = scsj;
		this.jcdid = jcdid;
		this.jcklx = jcklx;
		this.cdid = cdid;
		this.csys = csys;
		this.cllx = cllx;
		this.cb = cb;
		this.xxkbm = xxkbm;
		this.wflx = wflx;
		this.sd = sd;
		this.qdid = qdid;
		this.xsfx = xsfx;
		this.tpzs = tpzs;
		this.tpid1 = tpid1;
		this.tpid2 = tpid2;
		this.tpid3 = tpid3;
		this.tpid4 = tpid4;
		this.tpid5 = tpid5;
		this.qpsfwc = qpsfwc;
		this.hpsfwc = hpsfwc;
		this.qhsfyz = qhsfyz;
		this.zxd = zxd;
		this.fbcd = fbcd;
		this.bcbz = bcbz;
		this.fqh = fqh;
		this.spurl = spurl;
		this.bl = bl;
		this.byzd1 = byzd1;
		this.byzd2 = byzd2;
		this.byzd3 = byzd3;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "resFlag", nullable = false, length = 50)
	public String getresFlag() {
		return this.resFlag;
	}

	public void setresFlag(String resFlag) {
		this.resFlag = resFlag;
	}

	@Column(name = "CPHM1", length = 12)
	public String getCphm1() {
		return this.cphm1;
	}

	public void setCphm1(String cphm1) {
		this.cphm1 = cphm1;
	}

	@Column(name = "CPHM2", length = 12)
	public String getCphm2() {
		return this.cphm2;
	}

	public void setCphm2(String cphm2) {
		this.cphm2 = cphm2;
	}

	@Column(name = "CPLX1", length = 2)
	public String getCplx1() {
		return this.cplx1;
	}

	public void setCplx1(String cplx1) {
		this.cplx1 = cplx1;
	}

	@Column(name = "CPLX2", length = 2)
	public String getCplx2() {
		return this.cplx2;
	}

	public void setCplx2(String cplx2) {
		this.cplx2 = cplx2;
	}

	@Column(name = "HPZL", length = 2)
	public String getHpzl() {
		return this.hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	@Column(name = "TGSJ", length = 15)
	public String getTgsj() {
		return this.tgsj;
	}

	public void setTgsj(String tgsj) {
		this.tgsj = tgsj;
	}

	@Column(name = "SCSJ", length = 15)
	public String getScsj() {
		return this.scsj;
	}

	public void setScsj(String scsj) {
		this.scsj = scsj;
	}

	@Column(name = "JCDID", length = 9)
	public String getJcdid() {
		return this.jcdid;
	}

	public void setJcdid(String jcdid) {
		this.jcdid = jcdid;
	}

	@Column(name = "JCKLX", length = 1)
	public String getJcklx() {
		return this.jcklx;
	}

	public void setJcklx(String jcklx) {
		this.jcklx = jcklx;
	}

	@Column(name = "CDID", length = 6)
	public String getCdid() {
		return this.cdid;
	}

	public void setCdid(String cdid) {
		this.cdid = cdid;
	}

	@Column(name = "CSYS", length = 3)
	public String getCsys() {
		return this.csys;
	}

	public void setCsys(String csys) {
		this.csys = csys;
	}

	@Column(name = "CLLX", length = 2)
	public String getCllx() {
		return this.cllx;
	}

	public void setCllx(String cllx) {
		this.cllx = cllx;
	}

	@Column(name = "CB", length = 16)
	public String getCb() {
		return this.cb;
	}

	public void setCb(String cb) {
		this.cb = cb;
	}

	@Column(name = "XXKBM", length = 12)
	public String getXxkbm() {
		return this.xxkbm;
	}

	public void setXxkbm(String xxkbm) {
		this.xxkbm = xxkbm;
	}

	@Column(name = "WFLX", length = 3)
	public String getWflx() {
		return this.wflx;
	}

	public void setWflx(String wflx) {
		this.wflx = wflx;
	}

	@Column(name = "SD", length = 10)
	public String getSd() {
		return this.sd;
	}

	public void setSd(String sd) {
		this.sd = sd;
	}

	@Column(name = "QDID", length = 5)
	public String getQdid() {
		return this.qdid;
	}

	public void setQdid(String qdid) {
		this.qdid = qdid;
	}

	@Column(name = "XSFX", length = 1)
	public String getXsfx() {
		return this.xsfx;
	}

	public void setXsfx(String xsfx) {
		this.xsfx = xsfx;
	}

	@Column(name = "TPZS", length = 1)
	public String getTpzs() {
		return this.tpzs;
	}

	public void setTpzs(String tpzs) {
		this.tpzs = tpzs;
	}

	@Column(name = "TPID1", length = 70)
	public String getTpid1() {
		return this.tpid1;
	}

	public void setTpid1(String tpid1) {
		this.tpid1 = tpid1;
	}

	@Column(name = "TPID2", length = 70)
	public String getTpid2() {
		return this.tpid2;
	}

	public void setTpid2(String tpid2) {
		this.tpid2 = tpid2;
	}

	@Column(name = "TPID3", length = 70)
	public String getTpid3() {
		return this.tpid3;
	}

	public void setTpid3(String tpid3) {
		this.tpid3 = tpid3;
	}

	@Column(name = "TPID4", length = 70)
	public String getTpid4() {
		return this.tpid4;
	}

	public void setTpid4(String tpid4) {
		this.tpid4 = tpid4;
	}

	@Column(name = "TPID5", length = 70)
	public String getTpid5() {
		return this.tpid5;
	}

	public void setTpid5(String tpid5) {
		this.tpid5 = tpid5;
	}

	@Column(name = "QPSFWC", length = 1)
	public String getQpsfwc() {
		return this.qpsfwc;
	}

	public void setQpsfwc(String qpsfwc) {
		this.qpsfwc = qpsfwc;
	}

	@Column(name = "HPSFWC", length = 1)
	public String getHpsfwc() {
		return this.hpsfwc;
	}

	public void setHpsfwc(String hpsfwc) {
		this.hpsfwc = hpsfwc;
	}

	@Column(name = "QHSFYZ", length = 1)
	public String getQhsfyz() {
		return this.qhsfyz;
	}

	public void setQhsfyz(String qhsfyz) {
		this.qhsfyz = qhsfyz;
	}

	@Column(name = "ZXD", length = 16)
	public String getZxd() {
		return this.zxd;
	}

	public void setZxd(String zxd) {
		this.zxd = zxd;
	}

	@Column(name = "FBCD", length = 16)
	public String getFbcd() {
		return this.fbcd;
	}

	public void setFbcd(String fbcd) {
		this.fbcd = fbcd;
	}

	@Column(name = "BCBZ", length = 2)
	public String getBcbz() {
		return this.bcbz;
	}

	public void setBcbz(String bcbz) {
		this.bcbz = bcbz;
	}

	@Column(name = "FQH", length = 12)
	public String getFqh() {
		return this.fqh;
	}

	public void setFqh(String fqh) {
		this.fqh = fqh;
	}

	@Column(name = "SPURL", length = 34)
	public String getSpurl() {
		return this.spurl;
	}

	public void setSpurl(String spurl) {
		this.spurl = spurl;
	}

	@Column(name = "BL", length = 16)
	public String getBl() {
		return this.bl;
	}

	public void setBl(String bl) {
		this.bl = bl;
	}

	@Column(name = "BYZD1", length = 34)
	public String getByzd1() {
		return this.byzd1;
	}

	public void setByzd1(String byzd1) {
		this.byzd1 = byzd1;
	}

	@Column(name = "BYZD2", length = 34)
	public String getByzd2() {
		return this.byzd2;
	}

	public void setByzd2(String byzd2) {
		this.byzd2 = byzd2;
	}

	@Column(name = "BYZD3", length = 34)
	public String getByzd3() {
		return this.byzd3;
	}

	public void setByzd3(String byzd3) {
		this.byzd3 = byzd3;
	}

}