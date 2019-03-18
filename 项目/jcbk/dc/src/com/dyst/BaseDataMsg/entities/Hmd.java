package com.dyst.BaseDataMsg.entities;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Hmd entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HMD", catalog = "dc")
public class Hmd implements java.io.Serializable {

	// Fields

	private Integer id;      //id
	private String cph;      //车牌号
	private String cplx;	//车牌类型
	private Timestamp kssj;  //开始时间
	private Timestamp jssj;	 //结束时间
	private String sqdw;     //申请单位
	private String spzbh;   //审批者编号
	private String zt;      //状态
	private Timestamp rksj; //入库时间
	private Timestamp xgsj; //修改时间
	private String syz;     //使用者
	private String sqrbh;   //申请人编号
	private String lryy;    //列入原因
	
	public static String RECORD_STATE_OK = "1";
	public static String RECORD_STATE_CANCLE = "0";
	public static Map<String,String> HMD_STATE_MAP;
	static{
		HMD_STATE_MAP = new HashMap<String, String>();
		HMD_STATE_MAP.put(RECORD_STATE_OK, "有效");
		HMD_STATE_MAP.put(RECORD_STATE_CANCLE, "已取消");
	}

	// Constructors

	/** default constructor */
	public Hmd() {
	}

	/** full constructor */
	public Hmd(String cph, String cplx, Timestamp kssj, Timestamp jssj,
			String sqdw, String spzbh, String zt, Timestamp rksj,
			Timestamp xgsj, String syz, String sqrbh, String lryy) {
		this.cph = cph;
		this.cplx = cplx;
		this.kssj = kssj;
		this.jssj = jssj;
		this.sqdw = sqdw;
		this.spzbh = spzbh;
		this.zt = zt;
		this.rksj = rksj;
		this.xgsj = xgsj;
		this.syz = syz;
		this.sqrbh = sqrbh;
		this.lryy = lryy;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CPH", length = 14)
	public String getCph() {
		return this.cph;
	}

	public void setCph(String cph) {
		this.cph = cph;
	}

	@Column(name = "CPLX", length = 200)
	public String getCplx() {
		return this.cplx;
	}

	public void setCplx(String cplx) {
		this.cplx = cplx;
	}

	@Column(name = "KSSJ", length = 19)
	public Timestamp getKssj() {
		return this.kssj;
	}

	public void setKssj(Timestamp kssj) {
		this.kssj = kssj;
	}

	@Column(name = "JSSJ", length = 19)
	public Timestamp getJssj() {
		return this.jssj;
	}

	public void setJssj(Timestamp jssj) {
		this.jssj = jssj;
	}

	@Column(name = "SQDW", length = 50)
	public String getSqdw() {
		return this.sqdw;
	}

	public void setSqdw(String sqdw) {
		this.sqdw = sqdw;
	}

	@Column(name = "SPZBH", length = 20)
	public String getSpzbh() {
		return this.spzbh;
	}

	public void setSpzbh(String spzbh) {
		this.spzbh = spzbh;
	}

	@Column(name = "ZT", length = 2)
	public String getZt() {
		return this.zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	@Column(name = "RKSJ", length = 19)
	public Timestamp getRksj() {
		return this.rksj;
	}

	public void setRksj(Timestamp rksj) {
		this.rksj = rksj;
	}

	@Column(name = "XGSJ", length = 19)
	public Timestamp getXgsj() {
		return this.xgsj;
	}

	public void setXgsj(Timestamp xgsj) {
		this.xgsj = xgsj;
	}

	@Column(name = "SYZ", length = 20)
	public String getSyz() {
		return this.syz;
	}

	public void setSyz(String syz) {
		this.syz = syz;
	}

	@Column(name = "SQRBH", length = 20)
	public String getSqrbh() {
		return this.sqrbh;
	}

	public void setSqrbh(String sqrbh) {
		this.sqrbh = sqrbh;
	}

	@Column(name = "LRYY", length = 200)
	public String getLryy() {
		return this.lryy;
	}

	public void setLryy(String lryy) {
		this.lryy = lryy;
	}

}