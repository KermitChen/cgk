package com.dyst.DyMsg.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hibernate.annotations.GenericGenerator;

/**
 * Dyxx entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DYXX", catalog = "dc")
public class Dyxx implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer lxid;
	private String jyjh;
	private String dwmc;
	private String hphm;
	private String cplx;
	private String jcdid;
	private Timestamp lrsj;
	private Timestamp qssj;
	private Timestamp jzsj;
	private String dylx;
	private String dxjshm;
	private String tzfs;
	private Integer swid;
	private String dylxzl;
	private String jlzt;
	private String ywzt;
	private String jyxm;
	private String bzsm;
	private String cxyy;
	private Set<Dyxxsp> dyxxsps = new HashSet<Dyxxsp>(0);
	private Set<DyxxXq> dyxxXqs = new HashSet<DyxxXq>(0);
	private String sqrjsdj;
	
	// Constructors

	/** default constructor */
	public Dyxx() {
	}

	/** full constructor */
	public Dyxx(Integer lxid, String jyjh,String dwmc, String hphm, String cplx,
			String jcdid, Timestamp lrsj, Timestamp qssj, Timestamp jzsj,
			String dylx, String dxjshm, String tzfs, Integer swid,
			String dylxzl, String jlzt, String ywzt, String jyxm, String bzsm,
			String cxyy, Set<Dyxxsp> dyxxsps, Set<DyxxXq> dyxxXqs,String sqrjsdj) {
		this.lxid = lxid;
		this.jyjh = jyjh;
		this.dwmc = dwmc;
		this.hphm = hphm;
		this.cplx = cplx;
		this.jcdid = jcdid;
		this.lrsj = lrsj;
		this.qssj = qssj;
		this.jzsj = jzsj;
		this.dylx = dylx;
		this.dxjshm = dxjshm;
		this.tzfs = tzfs;
		this.swid = swid;
		this.dylxzl = dylxzl;
		this.jlzt = jlzt;
		this.ywzt = ywzt;
		this.jyxm = jyxm;
		this.bzsm = bzsm;
		this.cxyy = cxyy;
		this.dyxxsps = dyxxsps;
		this.dyxxXqs = dyxxXqs;
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

	@Column(name = "lxid")
	public Integer getLxid() {
		return this.lxid;
	}

	public void setLxid(Integer lxid) {
		this.lxid = lxid;
	}

	@Column(name = "jyjh", length = 30)
	public String getJyjh() {
		return this.jyjh;
	}

	public void setJyjh(String jyjh) {
		this.jyjh = jyjh;
	}
	
	@Column(name = "dwmc", length = 80)
	public String getDwmc() {
		return this.dwmc;
	}
	
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	@Column(name = "hphm", length = 400)
	public String getHphm() {
		return this.hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	@Column(name = "cplx", length = 2)
	public String getCplx() {
		return this.cplx;
	}

	public void setCplx(String cplx) {
		this.cplx = cplx;
	}

	@Column(name = "jcdid", length = 4000)
	public String getJcdid() {
		return this.jcdid;
	}

	public void setJcdid(String jcdid) {
		this.jcdid = jcdid;
	}

	@Column(name = "lrsj", length = 19)
	public Timestamp getLrsj() {
		return this.lrsj;
	}

	public void setLrsj(Timestamp lrsj) {
		this.lrsj = lrsj;
	}

	@Column(name = "qssj", length = 19)
	public Timestamp getQssj() {
		return this.qssj;
	}

	public void setQssj(Timestamp qssj) {
		this.qssj = qssj;
	}

	@Column(name = "jzsj", length = 19)
	public Timestamp getJzsj() {
		return this.jzsj;
	}

	public void setJzsj(Timestamp jzsj) {
		this.jzsj = jzsj;
	}

	@Column(name = "dylx", length = 10)
	public String getDylx() {
		return this.dylx;
	}

	public void setDylx(String dylx) {
		this.dylx = dylx;
	}

	@Column(name = "dxjshm", length = 400)
	public String getDxjshm() {
		return this.dxjshm;
	}

	public void setDxjshm(String dxjshm) {
		this.dxjshm = dxjshm;
	}

	@Column(name = "tzfs", length = 10)
	public String getTzfs() {
		return this.tzfs;
	}

	public void setTzfs(String tzfs) {
		this.tzfs = tzfs;
	}

	@Column(name = "swid")
	public Integer getSwid() {
		return this.swid;
	}

	public void setSwid(Integer swid) {
		this.swid = swid;
	}

	@Column(name = "dylxzl", length = 4000)
	public String getDylxzl() {
		return this.dylxzl;
	}

	public void setDylxzl(String dylxzl) {
		this.dylxzl = dylxzl;
	}

	@Column(name = "jlzt", length = 10)
	public String getJlzt() {
		return this.jlzt;
	}

	public void setJlzt(String jlzt) {
		this.jlzt = jlzt;
	}

	@Column(name = "ywzt", length = 2)
	public String getYwzt() {
		return this.ywzt;
	}

	public void setYwzt(String ywzt) {
		this.ywzt = ywzt;
	}

	@Column(name = "jyxm", length = 80)
	public String getJyxm() {
		return this.jyxm;
	}

	public void setJyxm(String jyxm) {
		this.jyxm = jyxm;
	}

	@Column(name = "bzsm", length = 1024)
	public String getBzsm() {
		return this.bzsm;
	}

	public void setBzsm(String bzsm) {
		this.bzsm = bzsm;
	}

	@Column(name = "cxyy", length = 500)
	public String getCxyy() {
		return this.cxyy;
	}

	public void setCxyy(String cxyy) {
		this.cxyy = cxyy;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="dyxxId2")
	public Set<Dyxxsp> getDyxxsps() {
		return this.dyxxsps;
	}

	public void setDyxxsps(Set<Dyxxsp> dyxxsps) {
		this.dyxxsps = dyxxsps;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="dyxxId")
	public Set<DyxxXq> getDyxxXqs() {
		return this.dyxxXqs;
	}

	public void setDyxxXqs(Set<DyxxXq> dyxxXqs) {
		this.dyxxXqs = dyxxXqs;
	}
	
	@Column(name = "sqrjsdj", length = 5)
	public String getSqrjsdj() {
		return sqrjsdj;
	}

	public void setSqrjsdj(String sqrjsdj) {
		this.sqrjsdj = sqrjsdj;
	}
	

}