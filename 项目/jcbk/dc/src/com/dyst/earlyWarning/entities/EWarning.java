package com.dyst.earlyWarning.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Target;
import org.springframework.format.annotation.DateTimeFormat;

import com.dyst.dispatched.entities.Dispatched;

@Entity
@Table(name = "LSYJ")
public class EWarning implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = 179018157834096659L;

	/** 布控 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bkid", insertable = false, updatable = false)
	@Target(Dispatched.class)
	private Dispatched dispatched;

	/** 报警序号 */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")
	// 需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "bjxh", unique = true, nullable = false, length = 10)
	private Integer bjxh;

	/** 布控表id关联 */
	@Column(name = "bkid", nullable = false, length = 10)
	private Integer bkid;
	
	/** 号牌号码 */
	@Column(name = "hphm", nullable = false, length = 15)
	private String hphm;

	/** 号牌种类 */
	@Column(name = "hpzl", nullable = true, length = 2)
	private String hpzl;
	
	/** 车牌类型 */
	@Column(name = "cplx", nullable = true, length = 2)
	private String cplx;

	/** 监测点ID */
	@Column(name = "jcdid", nullable = false, length = 18)
	private String jcdid;

	/** 监测点名称 */
	@Column(name = "jcdmc", nullable = true, length = 128)
	private String jcdmc;

	/** 车道ID */
	@Column(name = "cdid", nullable = true, length = 2)
	private String cdid;

	/** 图片1 */
	@Column(name = "tpid", nullable = false, length = 256)
	private String tpid;
	
	/** 车辆速度 */
	@Column(name = "sd", nullable = true)
	private double sd;
	
	/** 通过时间 */
	@Column(name = "tgsj", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date tgsj;

	/** 上传时间 */
	@Column(name = "scsj", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date scsj;
	
	/** 报警时间 */
	@Column(name = "bjsj", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date bjsj;
	
	/** 信息来源 */
	@Column(name = "xxly", nullable = true, length = 2)
	private String xxly;

	/**
	 * 获取布控表id关联
	 * 
	 * @return 布控表id关联
	 */
	public Integer getBkid() {
		return this.bkid;
	}

	/**
	 * 设置布控表id关联
	 * 
	 * @param bkid
	 *            布控表id关联
	 */
	public void setBkid(Integer bkid) {
		this.bkid = bkid;
	}

	/**
	 * 获取号牌号码
	 * 
	 * @return 号牌号码
	 */
	public String getHphm() {
		return this.hphm;
	}

	/**
	 * 设置号牌号码
	 * 
	 * @param hphm
	 *            号牌号码
	 */
	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	/**
	 * 获取号牌种类
	 * 
	 * @return 号牌种类
	 */
	public String getHpzl() {
		return this.hpzl;
	}

	/**
	 * 设置号牌种类
	 * 
	 * @param hpzl
	 *            号牌种类
	 */
	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	/**
	 * 获取监测点ID
	 * 
	 * @return 监测点ID
	 */
	public String getJcdid() {
		return this.jcdid;
	}

	/**
	 * 设置监测点ID
	 * 
	 * @param jcdid
	 *            监测点ID
	 */
	public void setJcdid(String jcdid) {
		this.jcdid = jcdid;
	}

	/**
	 * 获取监测点名称
	 * 
	 * @return 监测点名称
	 */
	public String getJcdmc() {
		return this.jcdmc;
	}

	/**
	 * 设置监测点名称
	 * 
	 * @param jcdmc
	 *            监测点名称
	 */
	public void setJcdmc(String jcdmc) {
		this.jcdmc = jcdmc;
	}

	/**
	 * 获取通过时间
	 * 
	 * @return 通过时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTgsj() {
		return this.tgsj;
	}

	/**
	 * 设置通过时间
	 * 
	 * @param tgsj
	 *            通过时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setTgsj(Date tgsj) {
		this.tgsj = tgsj;
	}

	/**
	 * 获取车道ID
	 * 
	 * @return 车道ID
	 */
	public String getCdid() {
		return this.cdid;
	}

	/**
	 * 设置车道ID
	 * 
	 * @param cdid
	 *            车道ID
	 */
	public void setCdid(String cdid) {
		this.cdid = cdid;
	}

	/**
	 * 获取图片1
	 * 
	 * @return 图片1
	 */
	public String getTpid() {
		return this.tpid;
	}

	/**
	 * 设置图片1
	 * 
	 * @param tpid1
	 *            图片1
	 */
	public void setTpid(String tpid) {
		this.tpid = tpid;
	}

	public Dispatched getDispatched() {
		return dispatched;
	}

	public void setDispatched(Dispatched dispatched) {
		this.dispatched = dispatched;
	}

	public Date getScsj() {
		return scsj;
	}

	public void setScsj(Date scsj) {
		this.scsj = scsj;
	}

	public Integer getBjxh() {
		return bjxh;
	}

	public void setBjxh(Integer bjxh) {
		this.bjxh = bjxh;
	}

	public double getSd() {
		return sd;
	}

	public void setSd(double sd) {
		this.sd = sd;
	}

	public Date getBjsj() {
		return bjsj;
	}

	public void setBjsj(Date bjsj) {
		this.bjsj = bjsj;
	}

	public String getCplx() {
		return cplx;
	}

	public void setCplx(String cplx) {
		this.cplx = cplx;
	}

	public String getXxly() {
		return xxly;
	}

	public void setXxly(String xxly) {
		this.xxly = xxly;
	}
}