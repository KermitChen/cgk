package com.dyst.dispatched.entities;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 布控签收表(BKQS)
 * @version 1.0.0 2016-07-25
 */
@Entity
@Table(name = "BKQS")
public class DisReceive implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -5248974921606130569L;
    
    /** 自增主键 */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
    @Column(name = "id", unique = true, nullable = false, length = 10)
    private Integer id;
    
    /** 布控或撤控ID */
    @Column(name = "bkid", nullable = false, length = 10)
    private Integer bkid;
    
    /** 布控撤控标志（1布控，2撤控） */
    @Column(name = "bkckbz", nullable = false, length = 1)
    private String bkckbz;
    
    /** 确认人 */
    @Column(name = "qrr", nullable = true, length = 128)
    private String qrr;
    
    /** 确认人警号 */
    @Column(name = "qrrjh", nullable = true, length = 20)
    private String qrrjh;
    
    /** 确认人单位名称 */
    @Column(name = "qrdwmc", nullable = true, length = 128)
    private String qrdwmc;
    
    /** 确认单位代码 */
    @Column(name = "qrdw", nullable = true, length = 20)
    private String qrdw;
    
    /** 确认单位联系电话 */
    @Column(name = "qrdwlxdh", nullable = true, length = 20)
    private String qrdwlxdh;
    
    /** 确认时间 */
    @Column(name = "qrsj", nullable = true)
    private Date qrsj;
    
    /** 确认状态（0否,1是） */
    @Column(name = "qrzt", nullable = true, length = 1)
    private String qrzt;
    
    /** 确认结果简述 */
    @Column(name = "qrjg", nullable = true, length = 1024)
    private String qrjg;
    
    /** 下发时间 */
    @Column(name = "xfsj", nullable = true)
    private Date xfsj;
    
    /** 下发单位代码 */
    @Column(name = "xfdw", nullable = true, length = 20)
    private String xfdw;
    
    /** 下发单位名称 */
    @Column(name = "xfdwmc", nullable = true, length = 128)
    private String xfdwmc;
    
    /** 通知人警号 */
    @Column(name = "tzrjh", nullable = true, length = 20)
    private String tzrjh;
    
    /** 通知人 */
    @Column(name = "tzr", nullable = true, length = 128)
    private String tzr;
    
    /** 通知内容 */
    @Column(name = "tznr", nullable = true, length = 1024)
    private String tznr;
    
    /** 号牌号码 */
    @Column(name = "hphm", nullable = true, length = 20)
    private String hphm;
    
    /** 号牌种类 */
    @Column(name = "hpzl", nullable = true, length = 2)
    private String hpzl;
    
    /** 布控类别 */
    @Column(name = "bklb", nullable = true, length = 2)
    private String bklb;
    
    /**
     * 获取自增主键
     * 
     * @return 自增主键
     */
     public Integer getId() {
        return this.id;
     }
     
    /**
     * 设置自增主键
     * 
     * @param id
     *          自增主键
     */
     public void setId(Integer id) {
        this.id = id;
     }
    
    /**
     * 获取布控或撤控ID
     * 
     * @return 布控或撤控ID
     */
     public Integer getBkid() {
        return this.bkid;
     }
     
    /**
     * 设置布控或撤控ID
     * 
     * @param bkid
     *          布控或撤控ID
     */
     public void setBkid(Integer bkid) {
        this.bkid = bkid;
     }
    
    /**
     * 获取布控撤控标志（1布控，2撤控）
     * 
     * @return 布控撤控标志（1布控
     */
     public String getBkckbz() {
        return this.bkckbz;
     }
     
    /**
     * 设置布控撤控标志（1布控，2撤控）
     * 
     * @param bkckbz
     *          布控撤控标志（1布控，2撤控）
     */
     public void setBkckbz(String bkckbz) {
        this.bkckbz = bkckbz;
     }
    
    /**
     * 获取确认人
     * 
     * @return 确认人
     */
     public String getQrr() {
        return this.qrr;
     }
     
    /**
     * 设置确认人
     * 
     * @param qrr
     *          确认人
     */
     public void setQrr(String qrr) {
        this.qrr = qrr;
     }
    
    /**
     * 获取确认人警号
     * 
     * @return 确认人警号
     */
     public String getQrrjh() {
        return this.qrrjh;
     }
     
    /**
     * 设置确认人警号
     * 
     * @param qrrjh
     *          确认人警号
     */
     public void setQrrjh(String qrrjh) {
        this.qrrjh = qrrjh;
     }
    
    /**
     * 获取确认人单位名称
     * 
     * @return 确认人单位名称
     */
     public String getQrdwmc() {
        return this.qrdwmc;
     }
     
    /**
     * 设置确认人单位名称
     * 
     * @param qrdwmc
     *          确认人单位名称
     */
     public void setQrdwmc(String qrdwmc) {
        this.qrdwmc = qrdwmc;
     }
    
    /**
     * 获取确认单位代码
     * 
     * @return 确认单位代码
     */
     public String getQrdw() {
        return this.qrdw;
     }
     
    /**
     * 设置确认单位代码
     * 
     * @param qrdw
     *          确认单位代码
     */
     public void setQrdw(String qrdw) {
        this.qrdw = qrdw;
     }
    
    /**
     * 获取确认单位联系电话
     * 
     * @return 确认单位联系电话
     */
     public String getQrdwlxdh() {
        return this.qrdwlxdh;
     }
     
    /**
     * 设置确认单位联系电话
     * 
     * @param qrdwlxdh
     *          确认单位联系电话
     */
     public void setQrdwlxdh(String qrdwlxdh) {
        this.qrdwlxdh = qrdwlxdh;
     }
    
    /**
     * 获取确认时间
     * 
     * @return 确认时间
     */
     public Date getQrsj() {
        return this.qrsj;
     }
     
    /**
     * 设置确认时间
     * 
     * @param qrsj
     *          确认时间
     */
     public void setQrsj(Date qrsj) {
        this.qrsj = qrsj;
     }
    
    /**
     * 获取确认状态（0否,1是）
     * 
     * @return 确认状态（0否
     */
     public String getQrzt() {
        return this.qrzt;
     }
     
    /**
     * 设置确认状态（0否,1是）
     * 
     * @param qrzt
     *          确认状态（0否,1是）
     */
     public void setQrzt(String qrzt) {
        this.qrzt = qrzt;
     }
    
    /**
     * 获取确认结果简述
     * 
     * @return 确认结果简述
     */
     public String getQrjg() {
        return this.qrjg;
     }
     
    /**
     * 设置确认结果简述
     * 
     * @param qrjg
     *          确认结果简述
     */
     public void setQrjg(String qrjg) {
        this.qrjg = qrjg;
     }
    
    /**
     * 获取下发时间
     * 
     * @return 下发时间
     */
     public Date getXfsj() {
        return this.xfsj;
     }
     
    /**
     * 设置下发时间
     * 
     * @param xfsj
     *          下发时间
     */
     public void setXfsj(Date xfsj) {
        this.xfsj = xfsj;
     }
    
    /**
     * 获取下发单位代码
     * 
     * @return 下发单位代码
     */
     public String getXfdw() {
        return this.xfdw;
     }
     
    /**
     * 设置下发单位代码
     * 
     * @param xfdw
     *          下发单位代码
     */
     public void setXfdw(String xfdw) {
        this.xfdw = xfdw;
     }
    
    /**
     * 获取下发单位名称
     * 
     * @return 下发单位名称
     */
     public String getXfdwmc() {
        return this.xfdwmc;
     }
     
    /**
     * 设置下发单位名称
     * 
     * @param xfdwmc
     *          下发单位名称
     */
     public void setXfdwmc(String xfdwmc) {
        this.xfdwmc = xfdwmc;
     }
    
    /**
     * 获取通知人警号
     * 
     * @return 通知人警号
     */
     public String getTzrjh() {
        return this.tzrjh;
     }
     
    /**
     * 设置通知人警号
     * 
     * @param tzrjh
     *          通知人警号
     */
     public void setTzrjh(String tzrjh) {
        this.tzrjh = tzrjh;
     }
    
    /**
     * 获取通知人
     * 
     * @return 通知人
     */
     public String getTzr() {
        return this.tzr;
     }
     
    /**
     * 设置通知人
     * 
     * @param tzr
     *          通知人
     */
     public void setTzr(String tzr) {
        this.tzr = tzr;
     }
    
    /**
     * 获取通知内容
     * 
     * @return 通知内容
     */
     public String getTznr() {
        return this.tznr;
     }
     
    /**
     * 设置通知内容
     * 
     * @param tznr
     *          通知内容
     */
     public void setTznr(String tznr) {
        this.tznr = tznr;
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
     *          号牌号码
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
     *          号牌种类
     */
     public void setHpzl(String hpzl) {
        this.hpzl = hpzl;
     }
    
    /**
     * 获取布控类别
     * 
     * @return 布控类别
     */
     public String getBklb() {
        return this.bklb;
     }
     
    /**
     * 设置布控类别
     * 
     * @param bklb
     *          布控类别
     */
     public void setBklb(String bklb) {
        this.bklb = bklb;
     }
}