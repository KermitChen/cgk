package com.dyst.advancedAnalytics.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FakePlate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FAKE_PLATE", catalog = "dc")
public class FakePlate implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8184911498433445817L;
	private Integer id;
	private String cphid;
	private String cplx1;
	private String jcdid1;
	private String cdid1;
	private Date sbsj1;
	private String sbjlid1;
	private String cplx2;
	private String jcdid2;
	private String cdid2;
	private Date sbsj2;
	private String sbjlid2;
	private String lxsj;
	private String sfhs;
	private String qrwg;
	private String yhdlm;
	private Date qrsj;
	private String czfs;
	private String jjtp1;
	private String jjtp2;
	private String yjtp1;
	private String yjtp2;
	private String xzqrr;
	private String xzsfhs;
	private Date xzqrsj;
	private String xzqrwg;
	private String tpdcbz;
	private String fprmc;
	private String bzsm;
	private String yhxm;
	private String flag;
	private Integer deptId;
	private String deptName;

	// Constructors

	/** default constructor */
	public FakePlate() {
	}

	/** minimal constructor */
	public FakePlate(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public FakePlate(Integer id, String cphid, String cplx1,String cplx2, String jcdid1,
			String cdid1, Date sbsj1, String sbjlid1, String jcdid2,
			String cdid2, Date sbsj2, String sbjlid2, String lxsj, String sfhs,
			String qrwg, String yhdlm, Date qrsj, String czfs, String jjtp1,
			String jjtp2, String yjtp1, String yjtp2, String xzqrr,
			String xzsfhs, Date xzqrsj, String xzqrwg, String tpdcbz,
			String fprmc, String bzsm, String yhxm, String flag,
			Integer deptId, String deptName) {
		this.id = id;
		this.cphid = cphid;
		this.cplx1 = cplx1;
		this.cplx2 = cplx2;
		this.jcdid1 = jcdid1;
		this.cdid1 = cdid1;
		this.sbsj1 = sbsj1;
		this.sbjlid1 = sbjlid1;
		this.jcdid2 = jcdid2;
		this.cdid2 = cdid2;
		this.sbsj2 = sbsj2;
		this.sbjlid2 = sbjlid2;
		this.lxsj = lxsj;
		this.sfhs = sfhs;
		this.qrwg = qrwg;
		this.yhdlm = yhdlm;
		this.qrsj = qrsj;
		this.czfs = czfs;
		this.jjtp1 = jjtp1;
		this.jjtp2 = jjtp2;
		this.yjtp1 = yjtp1;
		this.yjtp2 = yjtp2;
		this.xzqrr = xzqrr;
		this.xzsfhs = xzsfhs;
		this.xzqrsj = xzqrsj;
		this.xzqrwg = xzqrwg;
		this.tpdcbz = tpdcbz;
		this.fprmc = fprmc;
		this.bzsm = bzsm;
		this.yhxm = yhxm;
		this.flag = flag;
		this.deptId = deptId;
		this.deptName = deptName;
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

	@Column(name = "cphid", length = 12)
	public String getCphid() {
		return this.cphid;
	}

	public void setCphid(String cphid) {
		this.cphid = cphid;
	}

	@Column(name = "cplx1", length = 2)
	public String getCplx1() {
		return this.cplx1;
	}

	public void setCplx1(String cplx1) {
		this.cplx1 = cplx1;
	}
	@Column(name = "cplx2", length = 2)
	public String getCplx2() {
		return this.cplx2;
	}

	public void setCplx2(String cplx2) {
		this.cplx2 = cplx2;
	}

	@Column(name = "jcdid1", length = 8)
	public String getJcdid1() {
		return this.jcdid1;
	}

	public void setJcdid1(String jcdid1) {
		this.jcdid1 = jcdid1;
	}

	@Column(name = "cdid1", length = 5)
	public String getCdid1() {
		return this.cdid1;
	}

	public void setCdid1(String cdid1) {
		this.cdid1 = cdid1;
	}

	@Column(name = "sbsj1", length = 0)
	public Date getSbsj1() {
		return this.sbsj1;
	}

	public void setSbsj1(Date sbsj1) {
		this.sbsj1 = sbsj1;
	}

	@Column(name = "sbjlid1", length = 200)
	public String getSbjlid1() {
		return this.sbjlid1;
	}

	public void setSbjlid1(String sbjlid1) {
		this.sbjlid1 = sbjlid1;
	}

	@Column(name = "jcdid2", length = 8)
	public String getJcdid2() {
		return this.jcdid2;
	}

	public void setJcdid2(String jcdid2) {
		this.jcdid2 = jcdid2;
	}

	@Column(name = "cdid2", length = 5)
	public String getCdid2() {
		return this.cdid2;
	}

	public void setCdid2(String cdid2) {
		this.cdid2 = cdid2;
	}

	@Column(name = "sbsj2", length = 0)
	public Date getSbsj2() {
		return this.sbsj2;
	}

	public void setSbsj2(Date sbsj2) {
		this.sbsj2 = sbsj2;
	}

	@Column(name = "sbjlid2", length = 200)
	public String getSbjlid2() {
		return this.sbjlid2;
	}

	public void setSbjlid2(String sbjlid2) {
		this.sbjlid2 = sbjlid2;
	}

	@Column(name = "lxsj", length = 10)
	public String getLxsj() {
		return this.lxsj;
	}

	public void setLxsj(String lxsj) {
		this.lxsj = lxsj;
	}

	@Column(name = "sfhs", length = 1)
	public String getSfhs() {
		return this.sfhs;
	}

	public void setSfhs(String sfhs) {
		this.sfhs = sfhs;
	}

	@Column(name = "qrwg", length = 2)
	public String getQrwg() {
		return this.qrwg;
	}

	public void setQrwg(String qrwg) {
		this.qrwg = qrwg;
	}

	@Column(name = "yhdlm", length = 30)
	public String getYhdlm() {
		return this.yhdlm;
	}

	public void setYhdlm(String yhdlm) {
		this.yhdlm = yhdlm;
	}

	@Column(name = "qrsj", length = 0)
	public Date getQrsj() {
		return this.qrsj;
	}

	public void setQrsj(Date qrsj) {
		this.qrsj = qrsj;
	}

	@Column(name = "czfs", length = 10)
	public String getCzfs() {
		return this.czfs;
	}

	public void setCzfs(String czfs) {
		this.czfs = czfs;
	}

	@Column(name = "jjtp1", length = 50)
	public String getJjtp1() {
		return this.jjtp1;
	}

	public void setJjtp1(String jjtp1) {
		this.jjtp1 = jjtp1;
	}

	@Column(name = "jjtp2", length = 50)
	public String getJjtp2() {
		return this.jjtp2;
	}

	public void setJjtp2(String jjtp2) {
		this.jjtp2 = jjtp2;
	}

	@Column(name = "yjtp1", length = 50)
	public String getYjtp1() {
		return this.yjtp1;
	}

	public void setYjtp1(String yjtp1) {
		this.yjtp1 = yjtp1;
	}

	@Column(name = "yjtp2", length = 50)
	public String getYjtp2() {
		return this.yjtp2;
	}

	public void setYjtp2(String yjtp2) {
		this.yjtp2 = yjtp2;
	}

	@Column(name = "xzqrr", length = 10)
	public String getXzqrr() {
		return this.xzqrr;
	}

	public void setXzqrr(String xzqrr) {
		this.xzqrr = xzqrr;
	}

	@Column(name = "xzsfhs", length = 1)
	public String getXzsfhs() {
		return this.xzsfhs;
	}

	public void setXzsfhs(String xzsfhs) {
		this.xzsfhs = xzsfhs;
	}

	@Column(name = "xzqrsj", length = 0)
	public Date getXzqrsj() {
		return this.xzqrsj;
	}

	public void setXzqrsj(Date xzqrsj) {
		this.xzqrsj = xzqrsj;
	}

	@Column(name = "xzqrwg", length = 2)
	public String getXzqrwg() {
		return this.xzqrwg;
	}

	public void setXzqrwg(String xzqrwg) {
		this.xzqrwg = xzqrwg;
	}

	@Column(name = "tpdcbz", length = 1)
	public String getTpdcbz() {
		return this.tpdcbz;
	}

	public void setTpdcbz(String tpdcbz) {
		this.tpdcbz = tpdcbz;
	}

	@Column(name = "fprmc", length = 10)
	public String getFprmc() {
		return this.fprmc;
	}

	public void setFprmc(String fprmc) {
		this.fprmc = fprmc;
	}

	@Column(name = "bzsm", length = 1000)
	public String getBzsm() {
		return this.bzsm;
	}

	public void setBzsm(String bzsm) {
		this.bzsm = bzsm;
	}

	@Column(name = "yhxm", length = 80)
	public String getYhxm() {
		return this.yhxm;
	}

	public void setYhxm(String yhxm) {
		this.yhxm = yhxm;
	}

	@Column(name = "flag", length = 1)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "deptId")
	public Integer getDeptId() {
		return this.deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	@Column(name = "deptName", length = 200)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}