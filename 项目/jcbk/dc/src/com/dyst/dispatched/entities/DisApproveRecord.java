package com.dyst.dispatched.entities;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

/**
 * 布控撤控审批记录表(BKCKSP)
 * 
 * @version 1.0.0 2016-06-14
 */
@Entity
@Table(name = "BKCKSP")
public class DisApproveRecord implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4842426951574819373L;
    
    /** 流水号 */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
    @Column(name = "id", unique = true, nullable = false, length = 10)
    private Integer id;
    
    /** 业务ID（布控或撤控ID） */
    @Column(name = "ywid", nullable = true, length = 10)
    private Integer ywid;
    
    /** 操作人 */
    @Column(name = "czr", nullable = true, length = 30)
    private String czr;
    
    /** 操作人警号 */
    @Column(name = "czrjh", nullable = true, length = 50)
    private String czrjh;
    
    /** 操作人角色 */
    @Column(name = "czrjs", nullable = true, length = 50)
    private String czrjs;

    /** 操作人角色类型 */
    @Column(name = "czrjslx", nullable = true, length = 10)
    private String czrjslx;
    
    /** 操作人单位 */
    @Column(name = "czrdw", nullable = true, length = 12)
    private String czrdw;
    
    /** 操作人单位名称 */
    @Column(name = "czrdwmc", nullable = true, length = 50)
    private String czrdwmc;
    
    /** 操作时间 */
    @Column(name = "czsj", nullable = true)
    private Date czsj;
    
    /** 操作结果（0:不同意1:同意2：修改后重新审批3:取消申请） */
    @Column(name = "czjg", nullable = true, length = 1)
    private String czjg;
    
    /** 操作结果名称 */
    @Formula("(select dict.TYPE_DESC from DICTIONARY dict where dict.DELETE_FLAG = '0' and dict.TYPE_CODE = 'BKCZJG' and dict.TYPE_SERIAL_NO = czjg)")
    private String czjgmc;
    
    /** 描述 */
    @Column(name = "ms", nullable = true, length = 1024)
    private String ms;
    
    /** 标志位（1、布控2、撤控） */
    @Column(name = "bzw", nullable = true, length = 1)
    private String bzw;
    
    /**
     * 获取流水号
     * 
     * @return 流水号
     */
     public Integer getId() {
        return this.id;
     }
     
    /**
     * 设置流水号
     * 
     * @param id
     *          流水号
     */
     public void setId(Integer id) {
        this.id = id;
     }
    
    /**
     * 获取业务ID（布控或撤控ID）
     * 
     * @return 业务ID（布控或撤控ID）
     */
     public Integer getYwid() {
        return this.ywid;
     }
     
    /**
     * 设置业务ID（布控或撤控ID）
     * 
     * @param ywid
     *          业务ID（布控或撤控ID）
     */
     public void setYwid(Integer ywid) {
        this.ywid = ywid;
     }
    
    /**
     * 获取操作人
     * 
     * @return 操作人
     */
     public String getCzr() {
        return this.czr;
     }
     
    /**
     * 设置操作人
     * 
     * @param czr
     *          操作人
     */
     public void setCzr(String czr) {
        this.czr = czr;
     }
    
    /**
     * 获取操作人警号
     * 
     * @return 操作人警号
     */
     public String getCzrjh() {
        return this.czrjh;
     }
     
    /**
     * 设置操作人警号
     * 
     * @param czrjh
     *          操作人警号
     */
     public void setCzrjh(String czrjh) {
        this.czrjh = czrjh;
     }
    
    /**
     * 获取操作人角色
     * 
     * @return 操作人角色
     */
     public String getCzrjs() {
        return this.czrjs;
     }
     
    /**
     * 设置操作人角色
     * 
     * @param czrjs
     *          操作人角色
     */
     public void setCzrjs(String czrjs) {
        this.czrjs = czrjs;
     }
    
    /**
     * 获取操作人单位
     * 
     * @return 操作人单位
     */
     public String getCzrdw() {
        return this.czrdw;
     }
     
    /**
     * 设置操作人单位
     * 
     * @param czrdw
     *          操作人单位
     */
     public void setCzrdw(String czrdw) {
        this.czrdw = czrdw;
     }
    
    /**
     * 获取操作人单位名称
     * 
     * @return 操作人单位名称
     */
     public String getCzrdwmc() {
        return this.czrdwmc;
     }
     
    /**
     * 设置操作人单位名称
     * 
     * @param czrdwmc
     *          操作人单位名称
     */
     public void setCzrdwmc(String czrdwmc) {
        this.czrdwmc = czrdwmc;
     }
    
    /**
     * 获取操作时间
     * 
     * @return 操作时间
     */
     public Date getCzsj() {
        return this.czsj;
     }
     
    /**
     * 设置操作时间
     * 
     * @param czsj
     *          操作时间
     */
     public void setCzsj(Date czsj) {
        this.czsj = czsj;
     }
    
    /**
     * 获取操作结果（0:不同意1:同意2：修改后重新审批3:取消申请）
     * 
     * @return 操作结果（0
     */
     public String getCzjg() {
        return this.czjg;
     }
     
    /**
     * 设置操作结果（0:不同意1:同意2：修改后重新审批3:取消申请）
     * 
     * @param czjg
     *          操作结果（0:不同意1:同意2：修改后重新审批3:取消申请）
     */
     public void setCzjg(String czjg) {
        this.czjg = czjg;
     }
    
    /**
     * 获取描述
     * 
     * @return 描述
     */
     public String getMs() {
        return this.ms;
     }
     
    /**
     * 设置描述
     * 
     * @param ms
     *          描述
     */
     public void setMs(String ms) {
        this.ms = ms;
     }
    
    /**
     * 获取标志位（1、布控2、撤控）
     * 
     * @return 标志位（1、布控2、撤控）
     */
     public String getBzw() {
        return this.bzw;
     }
     
    /**
     * 设置标志位（1、布控2、撤控）
     * 
     * @param bzw
     *          标志位（1、布控2、撤控）
     */
     public void setBzw(String bzw) {
        this.bzw = bzw;
     }

	public String getCzjgmc() {
		return czjgmc;
	}

	public void setCzjgmc(String czjgmc) {
		this.czjgmc = czjgmc;
	}

	/**
	 * @return 角色类型
	 */
	public String getCzrjslx() {
		return czrjslx;
	}

	/**
	 * @param 角色类型
	 */
	public void setCzrjslx(String czrjslx) {
		this.czrjslx = czrjslx;
	}
}