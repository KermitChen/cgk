package com.dyst.advancedAnalytics.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * FakeDelete entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FAKE_DELETE", catalog = "dc")
public class FakeDelete implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5334643011651191753L;
	private Integer id;
	private Integer tpid;
	private String pno;
	private String pname;
	private Date time;
	private String reason;
	private String realPlate;

	// Constructors

	/** default constructor */
	public FakeDelete() {
	}

	/** full constructor */
	public FakeDelete(Integer tpid, String pno, String pname, Date time,
			String reason, String realPlate) {
		this.tpid = tpid;
		this.pno = pno;
		this.pname = pname;
		this.time = time;
		this.reason = reason;
		this.realPlate = realPlate;
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

	@Column(name = "tpid")
	public Integer getTpid() {
		return this.tpid;
	}

	public void setTpid(Integer tpid) {
		this.tpid = tpid;
	}

	@Column(name = "pno", length = 30)
	public String getPno() {
		return this.pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	@Column(name = "pname", length = 30)
	public String getPname() {
		return this.pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	@Column(name = "time", length = 19)
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "reason")
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "realPlate", length = 30)
	public String getRealPlate() {
		return this.realPlate;
	}

	public void setRealPlate(String realPlate) {
		this.realPlate = realPlate;
	}

}