package com.dyst.earlyWarning.entities;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Target;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 指令信息表(INSTRUCTION)
 * 
 * @version 1.0.0 2016-05-28
 */
@Entity
@Table(name = "INSTRUCTION")
public class Instruction implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7184765493011987511L;
    
    /** 流水号 */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
    @Column(name = "id", unique = true, nullable = false, length = 10)
    private Integer id;
    
    /** 预警 */
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qsid",insertable= false ,updatable= false)
    @Target(EWRecieve.class)
    private EWRecieve ewrecieve;
    
    /** 预警ID */
    @Column(name = "bjxh", nullable = false, length = 10)
    private Integer bjxh;
    
    /** 签收ID */
    @Column(name = "qsid", nullable = false, length = 10)
    private Integer qsid;
    
    /** 指令下发人 */
    @Column(name = "zlxfr", nullable = true, length = 40)
    private String zlxfr;
    
    /** 指令下发人名称 */
    @Column(name = "zlxfrmc", nullable = true, length = 60)
    private String zlxfrmc;
    
    /** 指令下发部门 */
    @Column(name = "zlxfbm", nullable = true, length = 40)
    private String zlxfbm;
    
    /** 指令下发部门名称 */
    @Column(name = "zlxfbmmc", nullable = true, length = 60)
    private String zlxfbmmc;
    
    /** 指令下发时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "xfsj", nullable = true)
    private Date xfsj;
    
    /** 警情级别 */
    @Column(name = "jqjb", nullable = true, length = 2)
    private String jqjb;
    
    /** 指令方式 */
    @Column(name = "zlfs", nullable = true, length = 2)
    private String zlfs;
    
    /** 预案内容 */
    @Column(name = "yanr", nullable = true, length = 1024)
    private String yanr;
    
    /** 信息来源 */
    @Column(name = "xxly", nullable = true, length = 1)
    private String xxly;
    
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
     * 获取预案内容
     * 
     * @return 预案内容
     */
     public String getYanr() {
        return this.yanr;
     }
     
    /**
     * 设置预案内容
     * 
     * @param yanr
     *          预案内容
     */
     public void setYanr(String yanr) {
        this.yanr = yanr;
     }
    
    /**
     * 获取指令下发时间
     * 
     * @return 指令下发时间
     */
     public Date getXfsj() {
        return this.xfsj;
     }
     
    /**
     * 设置指令下发时间
     * 
     * @param xfsj
     *          指令下发时间
     */
     public void setXfsj(Date xfsj) {
        this.xfsj = xfsj;
     }
    
    /**
     * 获取指令下发人
     * 
     * @return 指令下发人
     */
     public String getZlxfr() {
        return this.zlxfr;
     }
     
    /**
     * 设置指令下发人
     * 
     * @param zlxfr
     *          指令下发人
     */
     public void setZlxfr(String zlxfr) {
        this.zlxfr = zlxfr;
     }
    
    /**
     * 获取指令下发人名称
     * 
     * @return 指令下发人名称
     */
     public String getZlxfrmc() {
        return this.zlxfrmc;
     }
     
    /**
     * 设置指令下发人名称
     * 
     * @param zlxfrmc
     *          指令下发人名称
     */
     public void setZlxfrmc(String zlxfrmc) {
        this.zlxfrmc = zlxfrmc;
     }
    
    /**
     * 获取指令下发部门
     * 
     * @return 指令下发部门
     */
     public String getZlxfbm() {
        return this.zlxfbm;
     }
     
    /**
     * 设置指令下发部门
     * 
     * @param zlxfbm
     *          指令下发部门
     */
     public void setZlxfbm(String zlxfbm) {
        this.zlxfbm = zlxfbm;
     }
    
    /**
     * 获取指令下发部门名称
     * 
     * @return 指令下发部门名称
     */
     public String getZlxfbmmc() {
        return this.zlxfbmmc;
     }
     
    /**
     * 设置指令下发部门名称
     * 
     * @param zlxfbmmc
     *          指令下发部门名称
     */
     public void setZlxfbmmc(String zlxfbmmc) {
        this.zlxfbmmc = zlxfbmmc;
     }

	public Integer getBjxh() {
		return bjxh;
	}

	public void setBjxh(Integer bjxh) {
		this.bjxh = bjxh;
	}

	public Integer getQsid() {
		return qsid;
	}

	public void setQsid(Integer qsid) {
		this.qsid = qsid;
	}

	public String getJqjb() {
		return jqjb;
	}

	public void setJqjb(String jqjb) {
		this.jqjb = jqjb;
	}

	public String getZlfs() {
		return zlfs;
	}

	public void setZlfs(String zlfs) {
		this.zlfs = zlfs;
	}

	public EWRecieve getEwrecieve() {
		return ewrecieve;
	}

	public void setEwrecieve(EWRecieve ewrecieve) {
		this.ewrecieve = ewrecieve;
	}

	public String getXxly() {
		return xxly;
	}

	public void setXxly(String xxly) {
		this.xxly = xxly;
	}
}