package com.dyst.entites;

import java.util.Date;

/**
 * @Entity Jjhomd  交警红名单表
 * @author chengaoke
 * @Date 2013-09-17
 */
@SuppressWarnings("serial")
public class Jjhomd implements java.io.Serializable {
	private Integer id;
	private String cphid;
	private String cplx;


    // Constructors
    /** default constructor */
    public Jjhomd() {
    }

	/** minimal constructor */
    public Jjhomd(String cphid, String cplx, String honmddj, Date kssj, Date jssj, 
    		String zt, String jlzt) {
       this.cphid = cphid;
       this.cplx = cplx;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCphid() {
		return cphid;
	}

	public void setCphid(String cphid) {
		this.cphid = cphid;
	}

	public String getCplx() {
		return cplx;
	}

	public void setCplx(String cplx) {
		this.cplx = cplx;
	}
}