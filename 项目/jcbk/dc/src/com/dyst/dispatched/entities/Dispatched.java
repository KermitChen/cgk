package com.dyst.dispatched.entities;

import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;


import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 * 布控申请表(BKSQ)
 * 
 * @version 1.0.0 2016-03-21
 */
@Entity
@Table(name = "BKSQ")
public class Dispatched implements java.io.Serializable {
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = -2901291124082716985L;

	/** 布控ID */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
    @Column(name = "bkid", unique = true, nullable = false, length = 10)
    private Integer bkid;
    
    /** 任务号 */
    @Transient
    private Task Task;
    /** 运行中的流程实例 */
    @Transient
    private ProcessInstance processInstance;
    /** 流程定义 */
    @Transient
    private ProcessDefinition processDefinition;
    /** 历史的流程实例 */
    @Transient
    private HistoricProcessInstance historicProcessInstance;
    @Transient
    private Map<String, Object> variables;
    /**  报备记录   */
    @Transient
    private DisReport disReport;
    
    /** 布控序号 */
    @Column(name = "bkxh", nullable = true, length = 22)
    private String bkxh;

    /** 号牌号码 */
    @Column(name = "hphm", nullable = false, length = 15)
    private String hphm;
    
    /** 号牌种类 */
    @Column(name = "hpzl", nullable = true, length = 2)
    private String hpzl;
    
    /** 布控大类 */
    @Column(name = "bkdl", nullable = true, length = 1)
    private String bkdl;
    
    /** 布控类别 */
    @Column(name = "bklb", nullable = true, length = 2)
    private String bklb;
    
    /** 是否假套牌 */
    @Column(name = "sfjtp", nullable = true, length = 1)
    private String sfjtp;
    
    /** 布控起始时间 */
    @Column(name = "bkqssj", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date bkqssj;
    
    /** 布控截止时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "bkjzsj", nullable = true)
    private Date bkjzsj;
    
    /** 简要案情 */
    @Column(name = "jyaq", nullable = true)
    private String jyaq;
    
    /** 布控范围类型（1为本地布控，2为联动布控） */
    @Column(name = "bkfwlx", nullable = true, length = 1)
    private String bkfwlx;
    
    /** 布控范围 */
    @Column(name = "bkfw", nullable = true)
    private String bkfw;
    
    /** 布控级别（1,2,3,4） */
    @Column(name = "bkjb", nullable = true, length = 2)
    private String bkjb;
    
    /** 布控性质（0：无，1：是） */
    @Column(name = "bkxz", nullable = true, length = 1)
    private String bkxz;
    
    /** 报警预案 */
    @Column(name = "bjya", nullable = true, length = 4)
    private String bjya;
    
    /** 涉枪涉爆（0：否；1：是） */
    @Column(name = "sqsb", nullable = true, length = 1)
    private String sqsb;
    
    /** 报警方式（1、2、3、4） */
    @Column(name = "bjfs", nullable = true, length = 8)
    private String bjfs;
    
    /** 短信接收号码 */
    @Column(name = "dxjshm", nullable = true, length = 256)
    private String dxjshm;
    
    /** 立案人（姓名） */
    @Column(name = "lar", nullable = true, length = 32)
    private String lar;
    
    /** 立案单位 */
    @Column(name = "ladw", nullable = true, length = 128)
    private String ladw;
    
    /** 立案单位联系电话 */
    @Column(name = "ladwlxdh", nullable = true, length = 20)
    private String ladwlxdh;
    
    /** 车辆品牌 */
    @Column(name = "clpp", nullable = true, length = 32)
    private String clpp;
    
    /** 号牌颜色 */
    @Column(name = "hpys", nullable = true, length = 4)
    private String hpys;
    
    /** 车辆类型 */
    @Column(name = "cllx", nullable = true, length = 20)
    private String cllx;
    
    /** 车身颜色 */
    @Column(name = "csys", nullable = true, length = 32)
    private String csys;
    
    /** 车辆识别代号 */
    @Column(name = "clsbdh", nullable = true, length = 25)
    private String clsbdh;
    
    /** 发动机号 */
    @Column(name = "fdjh", nullable = true, length = 30)
    private String fdjh;
    
    /** 车辆特征 */
    @Column(name = "cltz", nullable = true, length = 512)
    private String cltz;
    
    /** 车辆所有人 */
    @Column(name = "clsyr", nullable = true, length = 256)
    private String clsyr;
    
    /** 车辆型号 */
    @Column(name = "clxh", nullable = true, length = 256)
    private String clxh;
    
    /** 车辆所有人电话 */
    @Column(name = "syrlxdh", nullable = true, length = 20)
    private String syrlxdh;
    
    /** 所有人详细地址 */
    @Column(name = "syrxxdz", nullable = true, length = 256)
    private String syrxxdz;
    
    /** 布控申请人 */
    @Column(name = "bkr", nullable = true, length = 128)
    private String bkr;
    
    /** 布控申请人警号 */
    @Column(name = "bkrjh", nullable = true, length = 50)
    private String bkrjh;
    
    /** 布控申请机关 */
    @Column(name = "bkjg", nullable = true, length = 12)
    private String bkjg;
    
    /** 布控机关名称 */
    @Column(name = "bkjgmc", nullable = true, length = 128)
    private String bkjgmc;
    
    /** 布控申请机关联系电话 */
    @Column(name = "bkjglxdh", nullable = true, length = 20)
    private String bkjglxdh;
    
    /** 布控申请时间 */
    @Column(name = "bksj", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date bksj;
    
    /** 业务状态 1、布控中，2、布控审批，3、已撤控，4、已挂起，5、已失效，6、调整申请，7、撤控审批*/
    @Column(name = "ywzt", nullable = true, length = 2)
    private String ywzt;
    
    /** 更新时间 */
    @Column(name = "gxsj", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date gxsj;
    
    /** 信息来源（0、1、2、5） */
    @Column(name = "xxly", nullable = true, length = 2)
    private String xxly;
    
    
    /** 直接布控(0否，1是) */
    @Column(name = "zjbk", nullable = true, length = 1)
    private String zjbk;
    
    /** 备用字段 */
    @Column(name = "by2", nullable = true, length = 1024)
    private String by2;
    
    /** 备用字段 */
    @Column(name = "by3", nullable = true, length = 2)
    private String by3;
    
    /** 备用字段 */
    @Column(name = "by4", nullable = true, length = 2)
    private String by4;
    
    /** 备用字段 */
    @Column(name = "by5", nullable = true, length = 2)
    private String by5;
    
    /** 通知单位 */
    @Column(name = "tzdw", nullable = true, length = 2048)
    private String tzdw;
   
    /** 通知内容 */
    @Column(name = "tznr", nullable = true, length = 1024)
    private String tznr;
    
    /** 派出所单号 */
    @Column(name = "pcsdh", nullable = true, length = 25)
    private String pcsdh;
   
    /** 分局单号 */
    @Column(name = "fjdh", nullable = true, length = 25)
    private String fjdh;
    
    /** 市局单号 */
    @Column(name = "sjdh", nullable = true, length = 25)
    private String sjdh;
    
    /** 布控类别级别 */
    @Column(name = "bklbjb", nullable = true, length = 2)
    private String bklbjb;
    
    /** 分局编号 */
    @Column(name = "fjbh", nullable = true, length = 12)
    private String fjbh;
    
    /** 分局SYS_NO */
    @Column(name = "fjsn", nullable = true, length = 50)
    private String fjsn;
    
    /** 报警类型 */
    @Column(name = "bjlx", nullable = true, length = 2)
    private String bjlx;
    
	private String lskhbm;//隶属考核部门
	private String lskhbmmc;//隶属考核部门名称
    
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
     * 获取布控大类
     * 
     * @return 布控大类
     */
     public String getBkdl() {
        return this.bkdl;
     }
     
    /**
     * 设置布控大类
     * 
     * @param bkdl
     *          布控大类
     */
     public void setBkdl(String bkdl) {
        this.bkdl = bkdl;
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
    
    /**
     * 获取是否假套牌
     * 
     * @return 是否假套牌
     */
     public String getSfjtp() {
        return this.sfjtp;
     }
     
    /**
     * 设置是否假套牌
     * 
     * @param sfjtp
     *          是否假套牌
     */
     public void setSfjtp(String sfjtp) {
        this.sfjtp = sfjtp;
     }
    
    /**
     * 获取布控起始时间
     * 
     * @return 布控起始时间
     */
     @DateTimeFormat(pattern="yyyy-MM-dd")
     public Date getBkqssj() {
        return this.bkqssj;
     }
     
    /**
     * 设置布控起始时间
     * 
     * @param bkqssj
     *          布控起始时间
     */
     @DateTimeFormat(pattern="yyyy-MM-dd")
     public void setBkqssj(Date bkqssj) {
        this.bkqssj = bkqssj;
     }
    
    /**
     * 获取布控截止时间
     * 
     * @return 布控截止时间
     */
     @DateTimeFormat(pattern="yyyy-MM-dd")
     public Date getBkjzsj() {
        return this.bkjzsj;
     }
     
    /**
     * 设置布控截止时间
     * 
     * @param bkjzsj
     *          布控截止时间
     */
     @DateTimeFormat(pattern="yyyy-MM-dd")
     public void setBkjzsj(Date bkjzsj) {
        this.bkjzsj = bkjzsj;
     }
    
    /**
     * 获取简要案情
     * 
     * @return 简要案情
     */
     public String getJyaq() {
        return this.jyaq;
     }
     
    /**
     * 设置简要案情
     * 
     * @param jyaq
     *          简要案情
     */
     public void setJyaq(String jyaq) {
        this.jyaq = jyaq;
     }
    
    /**
     * 获取布控范围类型（1为本地布控，2为联动布控）
     * 
     * @return 布控范围类型（1为本地布控
     */
     public String getBkfwlx() {
        return this.bkfwlx;
     }
     
    /**
     * 设置布控范围类型（1为本地布控，2为联动布控）
     * 
     * @param bkfwlx
     *          布控范围类型（1为本地布控，2为联动布控）
     */
     public void setBkfwlx(String bkfwlx) {
        this.bkfwlx = bkfwlx;
     }
    
    /**
     * 获取布控范围
     * 
     * @return 布控范围
     */
     public String getBkfw() {
        return this.bkfw;
     }
     
    /**
     * 设置布控范围
     * 
     * @param bkfw
     *          布控范围
     */
     public void setBkfw(String bkfw) {
        this.bkfw = bkfw;
     }
    
    /**
     * 获取布控级别（1,2,3,4）
     * 
     * @return 布控级别（1
     */
     public String getBkjb() {
        return this.bkjb;
     }
     
    /**
     * 设置布控级别（1,2,3,4）
     * 
     * @param bkjb
     *          布控级别（1,2,3,4）
     */
     public void setBkjb(String bkjb) {
        this.bkjb = bkjb;
     }
    
    /**
     * 获取布控性质（0：无，1：是）
     * 
     * @return 布控性质（0
     */
     public String getBkxz() {
        return this.bkxz;
     }
     
    /**
     * 设置布控性质（0：无，1：是）
     * 
     * @param bkxz
     *          布控性质（0：无，1：是）
     */
     public void setBkxz(String bkxz) {
        this.bkxz = bkxz;
     }
    
    /**
     * 获取报警预案
     * 
     * @return 报警预案
     */
     public String getBjya() {
        return this.bjya;
     }
     
    /**
     * 设置报警预案
     * 
     * @param bjya
     *          报警预案
     */
     public void setBjya(String bjya) {
        this.bjya = bjya;
     }
    
    /**
     * 获取涉枪涉爆（0：否；1：是）
     * 
     * @return 涉枪涉爆（0
     */
     public String getSqsb() {
        return this.sqsb;
     }
     
    /**
     * 设置涉枪涉爆（0：否；1：是）
     * 
     * @param sqsb
     *          涉枪涉爆（0：否；1：是）
     */
     public void setSqsb(String sqsb) {
        this.sqsb = sqsb;
     }
    
    /**
     * 获取报警方式（1、2、3、4）
     * 
     * @return 报警方式（1、2、3、4）
     */
     public String getBjfs() {
        return this.bjfs;
     }
     
    /**
     * 设置报警方式（1、2、3、4）
     * 
     * @param bjfs
     *          报警方式（1、2、3、4）
     */
     public void setBjfs(String bjfs) {
        this.bjfs = bjfs;
     }
    
    /**
     * 获取短信接收号码
     * 
     * @return 短信接收号码
     */
     public String getDxjshm() {
        return this.dxjshm;
     }
     
    /**
     * 设置短信接收号码
     * 
     * @param dxjshm
     *          短信接收号码
     */
     public void setDxjshm(String dxjshm) {
        this.dxjshm = dxjshm;
     }
    
    /**
     * 获取立案人（姓名）
     * 
     * @return 立案人（姓名）
     */
     public String getLar() {
        return this.lar;
     }
     
    /**
     * 设置立案人（姓名）
     * 
     * @param lar
     *          立案人（姓名）
     */
     public void setLar(String lar) {
        this.lar = lar;
     }
    
    /**
     * 获取立案单位
     * 
     * @return 立案单位
     */
     public String getLadw() {
        return this.ladw;
     }
     
    /**
     * 设置立案单位
     * 
     * @param ladw
     *          立案单位
     */
     public void setLadw(String ladw) {
        this.ladw = ladw;
     }
    
    /**
     * 获取立案单位联系电话
     * 
     * @return 立案单位联系电话
     */
     public String getLadwlxdh() {
        return this.ladwlxdh;
     }
     
    /**
     * 设置立案单位联系电话
     * 
     * @param ladwlxdh
     *          立案单位联系电话
     */
     public void setLadwlxdh(String ladwlxdh) {
        this.ladwlxdh = ladwlxdh;
     }
    
    /**
     * 获取车辆品牌
     * 
     * @return 车辆品牌
     */
     public String getClpp() {
        return this.clpp;
     }
     
    /**
     * 设置车辆品牌
     * 
     * @param clpp
     *          车辆品牌
     */
     public void setClpp(String clpp) {
        this.clpp = clpp;
     }
    
    /**
     * 获取号牌颜色
     * 
     * @return 号牌颜色
     */
     public String getHpys() {
        return this.hpys;
     }
     
    /**
     * 设置号牌颜色
     * 
     * @param hpys
     *          号牌颜色
     */
     public void setHpys(String hpys) {
        this.hpys = hpys;
     }
    
    /**
     * 获取车辆类型
     * 
     * @return 车辆类型
     */
     public String getCllx() {
        return this.cllx;
     }
     
    /**
     * 设置车辆类型
     * 
     * @param cllx
     *          车辆类型
     */
     public void setCllx(String cllx) {
        this.cllx = cllx;
     }
    
    /**
     * 获取车身颜色
     * 
     * @return 车身颜色
     */
     public String getCsys() {
        return this.csys;
     }
     
    /**
     * 设置车身颜色
     * 
     * @param csys
     *          车身颜色
     */
     public void setCsys(String csys) {
        this.csys = csys;
     }
    
    /**
     * 获取车辆识别代号
     * 
     * @return 车辆识别代号
     */
     public String getClsbdh() {
        return this.clsbdh;
     }
     
    /**
     * 设置车辆识别代号
     * 
     * @param clsbdh
     *          车辆识别代号
     */
     public void setClsbdh(String clsbdh) {
        this.clsbdh = clsbdh;
     }
    
    /**
     * 获取发动机号
     * 
     * @return 发动机号
     */
     public String getFdjh() {
        return this.fdjh;
     }
     
    /**
     * 设置发动机号
     * 
     * @param fdjh
     *          发动机号
     */
     public void setFdjh(String fdjh) {
        this.fdjh = fdjh;
     }
    
    /**
     * 获取车辆特征
     * 
     * @return 车辆特征
     */
     public String getCltz() {
        return this.cltz;
     }
     
    /**
     * 设置车辆特征
     * 
     * @param cltz
     *          车辆特征
     */
     public void setCltz(String cltz) {
        this.cltz = cltz;
     }
    
    /**
     * 获取车辆所有人
     * 
     * @return 车辆所有人
     */
     public String getClsyr() {
        return this.clsyr;
     }
     
    /**
     * 设置车辆所有人
     * 
     * @param clsyr
     *          车辆所有人
     */
     public void setClsyr(String clsyr) {
        this.clsyr = clsyr;
     }
    
    /**
     * 获取车辆型号
     * 
     * @return 车辆型号
     */
     public String getClxh() {
        return this.clxh;
     }
     
    /**
     * 设置车辆型号
     * 
     * @param clxh
     *          车辆型号
     */
     public void setClxh(String clxh) {
        this.clxh = clxh;
     }
    
    /**
     * 获取车辆所有人电话
     * 
     * @return 车辆所有人电话
     */
     public String getSyrlxdh() {
        return this.syrlxdh;
     }
     
    /**
     * 设置车辆所有人电话
     * 
     * @param syrlxdh
     *          车辆所有人电话
     */
     public void setSyrlxdh(String syrlxdh) {
        this.syrlxdh = syrlxdh;
     }
    
    /**
     * 获取所有人详细地址
     * 
     * @return 所有人详细地址
     */
     public String getSyrxxdz() {
        return this.syrxxdz;
     }
     
    /**
     * 设置所有人详细地址
     * 
     * @param syrxxdz
     *          所有人详细地址
     */
     public void setSyrxxdz(String syrxxdz) {
        this.syrxxdz = syrxxdz;
     }
    
    /**
     * 获取布控申请人
     * 
     * @return 布控申请人
     */
     public String getBkr() {
        return this.bkr;
     }
     
    /**
     * 设置布控申请人
     * 
     * @param bkr
     *          布控申请人
     */
     public void setBkr(String bkr) {
        this.bkr = bkr;
     }
    
    /**
     * 获取布控申请人警号
     * 
     * @return 布控申请人警号
     */
     public String getBkrjh() {
        return this.bkrjh;
     }
     
    /**
     * 设置布控申请人警号
     * 
     * @param bkrjh
     *          布控申请人警号
     */
     public void setBkrjh(String bkrjh) {
        this.bkrjh = bkrjh;
     }
    
    /**
     * 获取布控申请机关
     * 
     * @return 布控申请机关
     */
     public String getBkjg() {
        return this.bkjg;
     }
     
    /**
     * 设置布控申请机关
     * 
     * @param bkjg
     *          布控申请机关
     */
     public void setBkjg(String bkjg) {
        this.bkjg = bkjg;
     }
    
    /**
     * 获取布控机关名称
     * 
     * @return 布控机关名称
     */
     public String getBkjgmc() {
        return this.bkjgmc;
     }
     
    /**
     * 设置布控机关名称
     * 
     * @param bkjgmc
     *          布控机关名称
     */
     public void setBkjgmc(String bkjgmc) {
        this.bkjgmc = bkjgmc;
     }
    
    /**
     * 获取布控申请机关联系电话
     * 
     * @return 布控申请机关联系电话
     */
     public String getBkjglxdh() {
        return this.bkjglxdh;
     }
     
    /**
     * 设置布控申请机关联系电话
     * 
     * @param bkjglxdh
     *          布控申请机关联系电话
     */
     public void setBkjglxdh(String bkjglxdh) {
        this.bkjglxdh = bkjglxdh;
     }
    
    /**
     * 获取布控申请时间
     * 
     * @return 布控申请时间
     */
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     public Date getBksj() {
        return this.bksj;
     }
     
    /**
     * 设置布控申请时间
     * 
     * @param bksj
     *          布控申请时间
     */
     public void setBksj(Date bksj) {
        this.bksj = bksj;
     }
    
    /**
     * 获取业务状态
     * 
     * @return 业务状态
     */
     public String getYwzt() {
        return this.ywzt;
     }
     
    /**
     * 设置业务状态
     * 
     * @param ywzt
     *          业务状态
     */
     public void setYwzt(String ywzt) {
        this.ywzt = ywzt;
     }
    
    /**
     * 获取更新时间
     * 
     * @return 更新时间
     */
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     public Date getGxsj() {
        return this.gxsj;
     }
     
    /**
     * 设置更新时间
     * 
     * @param gxsj
     *          更新时间
     */
     public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
     }
    
    /**
     * 获取信息来源（0、1、2、5）
     * 
     * @return 信息来源（0、1、2、5）
     */
     public String getXxly() {
        return this.xxly;
     }
     
    /**
     * 设置信息来源（0、1、2、5）
     * 
     * @param xxly
     *          信息来源（0、1、2、5）
     */
     public void setXxly(String xxly) {
        this.xxly = xxly;
     }
    
         
    /**
     * 获取直接布控(0否，1是)
     * 
     * @return 直接布控(0否
     */
     public String getZjbk() {
        return this.zjbk;
     }
     
    /**
     * 设置直接布控(0否，1是)
     * 
     * @param zjbk
     *          直接布控(0否，1是)
     */
     public void setZjbk(String zjbk) {
        this.zjbk = zjbk;
     }
    
    /**
     * 获取备用字段
     * 
     * @return 备用字段
     */
     public String getBy2() {
        return this.by2;
     }
     
    /**
     * 设置备用字段
     * 
     * @param by2
     *          备用字段
     */
     public void setBy2(String by2) {
        this.by2 = by2;
     }
    
    /**
     * 获取备用字段
     * 
     * @return 备用字段
     */
     public String getBy3() {
        return this.by3;
     }
     
    /**
     * 设置备用字段
     * 
     * @param by3
     *          备用字段
     */
     public void setBy3(String by3) {
        this.by3 = by3;
     }
    
    /**
     * 获取备用字段
     * 
     * @return 备用字段
     */
	public String getBy4() {
        return this.by4;
	}
     
    /**
     * 设置备用字段
     * 
     * @param by4
     *          备用字段
     */
	public void setBy4(String by4) {
        this.by4 = by4;
	}
     

	public String getBy5() {
		return by5;
	}

	public void setBy5(String by5) {
		this.by5 = by5;
	}

	public Task getTask() {
		return Task;
	}

	public void setTask(Task task) {
		Task = task;
	}

	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	public HistoricProcessInstance getHistoricProcessInstance() {
		return historicProcessInstance;
	}

	public void setHistoricProcessInstance(
			HistoricProcessInstance historicProcessInstance) {
		this.historicProcessInstance = historicProcessInstance;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	/**
	 * @return 布控序号
	 */
	public String getBkxh() {
		return bkxh;
	}

	/**
	 * @param 布控序号
	 */
	public void setBkxh(String bkxh) {
		this.bkxh = bkxh;
	}

	/**
	 * @return the disReport
	 */
	public DisReport getDisReport() {
		return disReport;
	}

	/**
	 * @param disReport the disReport to set
	 */
	public void setDisReport(DisReport disReport) {
		this.disReport = disReport;
	}

	/**
	 * @return the pcsdh
	 */
	public String getPcsdh() {
		return pcsdh;
	}

	/**
	 * @param pcsdh the pcsdh to set
	 */
	public void setPcsdh(String pcsdh) {
		this.pcsdh = pcsdh;
	}

	/**
	 * @return the fjdh
	 */
	public String getFjdh() {
		return fjdh;
	}

	/**
	 * @param fjdh the fjdh to set
	 */
	public void setFjdh(String fjdh) {
		this.fjdh = fjdh;
	}

	/**
	 * @return the sjdh
	 */
	public String getSjdh() {
		return sjdh;
	}

	/**
	 * @param sjdh the sjdh to set
	 */
	public void setSjdh(String sjdh) {
		this.sjdh = sjdh;
	}

	/**
	 * @return the tzdw
	 */
	public String getTzdw() {
		return tzdw;
	}

	/**
	 * @param tzdw the tzdw to set
	 */
	public void setTzdw(String tzdw) {
		this.tzdw = tzdw;
	}

	/**
	 * @return the tznr
	 */
	public String getTznr() {
		return tznr;
	}

	/**
	 * @param tznr the tznr to set
	 */
	public void setTznr(String tznr) {
		this.tznr = tznr;
	}

	public String getBklbjb() {
		return bklbjb;
	}

	public void setBklbjb(String bklbjb) {
		this.bklbjb = bklbjb;
	}

	public String getFjbh() {
		return fjbh;
	}

	public void setFjbh(String fjbh) {
		this.fjbh = fjbh;
	}

	public String getFjsn() {
		return fjsn;
	}

	public void setFjsn(String fjsn) {
		this.fjsn = fjsn;
	}

	public String getBjlx() {
		return bjlx;
	}

	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}

	@Column(name="LSKHBM", length=20)
	public String getLskhbm() {
		return lskhbm;
	}
	public void setLskhbm(String lskhbm) {
		this.lskhbm = lskhbm;
	}

	@Column(name="LSKHBMMC", length=80)
	public String getLskhbmmc() {
		return lskhbmmc;
	}
	public void setLskhbmmc(String lskhbmmc) {
		this.lskhbmmc = lskhbmmc;
	}
}