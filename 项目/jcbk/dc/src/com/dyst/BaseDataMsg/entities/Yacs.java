package com.dyst.BaseDataMsg.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Yacs entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "YACS", catalog = "dc")
public class Yacs implements java.io.Serializable {

	// Fields

	private Integer id;
	private String yamc;
	private String yams;
	private String yazl;
	private String yadj;
	private String bjlx;
	private String by1;
	private String by2;
	private String by3;

	// Constructors

	/** default constructor */
	public Yacs() {
	}

	/** full constructor */
	public Yacs(String yamc, String yams, String yazl, String yadj,String bjlx, String by1,
			String by2, String by3) {
		this.yamc = yamc;
		this.yams = yams;
		this.yazl = yazl;
		this.yadj = yadj;
		this.bjlx = bjlx;
		this.by1 = by1;
		this.by2 = by2;
		this.by3 = by3;
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

	@Column(name = "Yamc", length = 100)
	public String getYamc() {
		return this.yamc;
	}

	public void setYamc(String yamc) {
		this.yamc = yamc;
	}

	@Column(name = "Yams", length = 1024)
	public String getYams() {
		return this.yams;
	}

	public void setYams(String yams) {
		this.yams = yams;
	}

	@Column(name = "Yazl", length = 3)
	public String getYazl() {
		return this.yazl;
	}

	public void setYazl(String yazl) {
		this.yazl = yazl;
	}

	@Column(name = "Yadj", length = 10)
	public String getYadj() {
		return this.yadj;
	}

	public void setYadj(String yadj) {
		this.yadj = yadj;
	}
	@Column(name = "Bjlx", length = 10)
	public String getBjlx() {
		return bjlx;
	}

	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}

	@Column(name = "By_1", length = 20)
	public String getBy1() {
		return this.by1;
	}

	public void setBy1(String by1) {
		this.by1 = by1;
	}

	@Column(name = "By_2", length = 20)
	public String getBy2() {
		return this.by2;
	}

	public void setBy2(String by2) {
		this.by2 = by2;
	}

	@Column(name = "By_3", length = 20)
	public String getBy3() {
		return this.by3;
	}

	public void setBy3(String by3) {
		this.by3 = by3;
	}

}