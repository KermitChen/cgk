package com.dyst.dispatched.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 布控申请_直接布控报备信息表(BKSQ_ZJBK)
 * 
 * @version 1.0.0 2016-07-06
 */
@Entity
@Table(name = "BKSQ_ZJBK")
public class DisReport implements java.io.Serializable {
	/** 版本号 */
    private static final long serialVersionUID = 8762179511824385555L;
    
    /**  */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", unique = true, nullable = false, length = 10)
    private Integer id;
    
    /** 布控ID */
    @Column(name = "bkid", nullable = false, length = 10)
    private Integer bkid;
    
    /** 报备人警号 */
    @Column(name = "bbr", nullable = true, length = 20)
    private String bbr;
    
    /** 报备人名称 */
    @Column(name = "bbrmc", nullable = true, length = 50)
    private String bbrmc;
    
    /** 报备人电话 */
    @Column(name = "bbrdh", nullable = true, length = 20)
    private String bbrdh;
    
    /** 报备人部门编号 */
    @Column(name = "bbrbm", nullable = true, length = 20)
    private String bbrbm;
    
    /** 报备人部门名称 */
    @Column(name = "bbrbmmc", nullable = true, length = 50)
    private String bbrbmmc;
    
    /** 报备人角色等级（POSITION） */
    @Column(name = "bbrjs", nullable = true, length = 10)
    private String bbrjs;
    
    /** 报备人角色名称 */
    @Column(name = "bbrjsmc", nullable = true, length = 50)
    private String bbrjsmc;
    
    /** 反馈内容 */
    @Column(name = "fknr", nullable = true, length = 1024)
    private String fknr;
    
    /** 反馈时间 */
    @Column(name = "fksj", nullable = true)
    private Date fksj;
    
    /** 报备状态（1、已完成 0、未完成） */
    @Column(name = "bbzt", nullable = true, length = 2)
    private String bbzt;
    
    /**
     * 获取
     * 
     * @return 
     */
     public Integer getId() {
        return this.id;
     }
     
    /**
     * 设置
     * 
     * @param id
     *          
     */
     public void setId(Integer id) {
        this.id = id;
     }
    
    /**
     * 获取布控ID
     * 
     * @return 布控ID
     */
     public Integer getBkid() {
        return this.bkid;
     }
     
    /**
     * 设置布控ID
     * 
     * @param bkid
     *          布控ID
     */
     public void setBkid(Integer bkid) {
        this.bkid = bkid;
     }
    
    /**
     * 获取报备人警号
     * 
     * @return 报备人警号
     */
     public String getBbr() {
        return this.bbr;
     }
     
    /**
     * 设置报备人警号
     * 
     * @param bbr
     *          报备人警号
     */
     public void setBbr(String bbr) {
        this.bbr = bbr;
     }
    
    /**
     * 获取报备人名称
     * 
     * @return 报备人名称
     */
     public String getBbrmc() {
        return this.bbrmc;
     }
     
    /**
     * 设置报备人名称
     * 
     * @param bbrmc
     *          报备人名称
     */
     public void setBbrmc(String bbrmc) {
        this.bbrmc = bbrmc;
     }
    
    /**
     * 获取报备人电话
     * 
     * @return 报备人电话
     */
     public String getBbrdh() {
        return this.bbrdh;
     }
     
    /**
     * 设置报备人电话
     * 
     * @param bbrdh
     *          报备人电话
     */
     public void setBbrdh(String bbrdh) {
        this.bbrdh = bbrdh;
     }
    
    /**
     * 获取报备人部门编号
     * 
     * @return 报备人部门编号
     */
     public String getBbrbm() {
        return this.bbrbm;
     }
     
    /**
     * 设置报备人部门编号
     * 
     * @param bbrbm
     *          报备人部门编号
     */
     public void setBbrbm(String bbrbm) {
        this.bbrbm = bbrbm;
     }
    
    /**
     * 获取报备人部门名称
     * 
     * @return 报备人部门名称
     */
     public String getBbrbmmc() {
        return this.bbrbmmc;
     }
     
    /**
     * 设置报备人部门名称
     * 
     * @param bbrbmmc
     *          报备人部门名称
     */
     public void setBbrbmmc(String bbrbmmc) {
        this.bbrbmmc = bbrbmmc;
     }
    
    /**
     * 获取报备人角色等级（POSITION）
     * 
     * @return 报备人角色等级（POSITION）
     */
     public String getBbrjs() {
        return this.bbrjs;
     }
     
    /**
     * 设置报备人角色等级（POSITION）
     * 
     * @param bbrjs
     *          报备人角色等级（POSITION）
     */
     public void setBbrjs(String bbrjs) {
        this.bbrjs = bbrjs;
     }
    
    /**
     * 获取报备人角色名称
     * 
     * @return 报备人角色名称
     */
     public String getBbrjsmc() {
        return this.bbrjsmc;
     }
     
    /**
     * 设置报备人角色名称
     * 
     * @param bbrjsmc
     *          报备人角色名称
     */
     public void setBbrjsmc(String bbrjsmc) {
        this.bbrjsmc = bbrjsmc;
     }
    
    /**
     * 获取反馈内容
     * 
     * @return 反馈内容
     */
     public String getFknr() {
        return this.fknr;
     }
     
    /**
     * 设置反馈内容
     * 
     * @param fknr
     *          反馈内容
     */
     public void setFknr(String fknr) {
        this.fknr = fknr;
     }
    
    /**
     * 获取反馈时间
     * 
     * @return 反馈时间
     */
     public Date getFksj() {
        return this.fksj;
     }
     
    /**
     * 设置反馈时间
     * 
     * @param fksj
     *          反馈时间
     */
     public void setFksj(Date fksj) {
        this.fksj = fksj;
     }
    
    /**
     * 获取报备状态（1、已完成 0、未完成）
     * 
     * @return 报备状态（1、已完成 0、未完成）
     */
     public String getBbzt() {
        return this.bbzt;
     }
     
    /**
     * 设置报备状态（1、已完成 0、未完成）
     * 
     * @param bbzt
     *          报备状态（1、已完成 0、未完成）
     */
     public void setBbzt(String bbzt) {
        this.bbzt = bbzt;
     }
}