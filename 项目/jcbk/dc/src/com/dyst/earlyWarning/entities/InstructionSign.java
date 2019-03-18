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

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Target;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * (INSTRUCTION_SIGN)
 * 
 * @version 1.0.0 2016-05-28
 */
@Entity
@Table(name = "INSTRUCTION_SIGN")
public class InstructionSign implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -3839858076888075240L;
    
    /** 流水号 */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
    @Column(name = "id", unique = true, nullable = false, length = 10)
    private Integer id;
    
    /** 指令基础信息 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zlid",insertable= false ,updatable= false)
    @Target(Instruction.class)
    private Instruction instruction;
    
    /** 指令ID */
    @Column(name = "zlid", nullable = true, length = 10)
    private Integer zlid;
    
    /** 布控表id关联 */
    @Column(name = "bkid", nullable = true, length = 10)
    private Integer bkid;
    
    /** 号牌号码 */
    @Column(name = "hphm", nullable = true, length = 30)
    private String hphm;
    
    /**号牌种类 */
    @Column(name = "hpzl", nullable = true, length = 30)
    private String hpzl;
    
    /** 预计到达时间 */
    @Column(name = "yjsj", nullable = true, length = 20)
    private String yjsj;
    
    /** 卡点ID */
    @Column(name = "kdid", nullable = true, length = 40)
    private String kdid;
    
    /** 卡点名称 */
    @Column(name = "kdmc", nullable = true, length = 100)
    private String kdmc;
    
    
    /** 指令部门 */
    @Column(name = "zlbm", nullable = true, length = 20)
    private String zlbm;
    
    /** 指令部门名称 */
    @Column(name = "zlbmmc", nullable = true, length = 120)
    private String zlbmmc;
    
    /** 指令时间 */
    @Column(name = "zlsj", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date zlsj;
    
    
    /** 签收人 */
    @Column(name = "qsr", nullable = true, length = 30)
    private String qsr;
    
    /** 签收人名称 */
    @Column(name = "qsrmc", nullable = true, length = 50)
    private String qsrmc;
    
    /** 签收部门 */
    @Column(name = "qsbm", nullable = true, length = 20)
    private String qsbm;
    
    /** 签收部门名称 */
    @Column(name = "qsbmmc", nullable = true, length = 50)
    private String qsbmmc;
    
    /** 签收描述 */
    @Column(name = "qsms", nullable = true)
    private String qsms;
    
    /** 签收人联系电话 */
    @Column(name = "qsrlxdh", nullable = true, length = 20)
    private String qsrlxdh;
    
    /** 签收状态(0、未签收，1、已签收) */
    @Column(name = "qszt", nullable = true, length = 2)
    private String qszt;
    
    /** 签收状态名称 */
    @Formula("(select dict.TYPE_DESC from DICTIONARY dict where dict.DELETE_FLAG = '0' and dict.TYPE_CODE = 'YJQSZT' and dict.TYPE_SERIAL_NO = qszt)")
    private String qsztmc;
    
    /** 签收时间 */
    @Column(name = "qssj", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date qssj;
    
    
    /** 反馈状态(0、未反馈，1、已反馈) */
    @Column(name = "fkzt", nullable = true, length = 2)
    private String fkzt;
    
    /** 反馈状态名称 */
    @Formula("(select dict.TYPE_DESC from DICTIONARY dict where dict.DELETE_FLAG = '0' and dict.TYPE_CODE = 'ZLFKZT' and dict.TYPE_SERIAL_NO = fkzt)")
    private String fkztmc;
    
    /** 反馈时间 */
    @Column(name = "fksj", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date fksj;
    
    /** 反馈人 */
    @Column(name = "fkr", nullable = true, length = 30)
    private String fkr;
    
    /** 反馈人名称 */
    @Column(name = "fkrmc", nullable = true, length = 50)
    private String fkrmc;
    
    /** 反馈人联系电话 */
    @Column(name = "fkrlxdh", nullable = true, length = 20)
    private String fkrlxdh;
    
    /** 反馈部门 */
    @Column(name = "fkbm", nullable = true, length = 20)
    private String fkbm;
    
    /** 反馈部门名称 */
    @Column(name = "fkbmmc", nullable = true, length = 50)
    private String fkbmmc;
    
    
    
    /** 处置结果  1.无效报警  2.移交处置 */
    @Column(name = "czjg", nullable = true, length = 50)
    private String czjg;
    
    /** 是否拦截到（0:未拦截到，1:拦截到） */
    @Column(name = "sflj", nullable = true, length = 2)
    private String sflj;
    
    /** 未拦截到原因（0:冲卡1:有岔路存在9:其他） */
    @Column(name = "wljdyy", nullable = true, length = 2)
    private String wljdyy;
    
    /** 带队人 */
    @Column(name = "ddr", nullable = true, length = 50)
    private String ddr;
    
    /** 协办人 */
    @Column(name = "xbr", nullable = true, length = 1024)
    private String xbr;
    
    /** 抓获人数 */
    @Column(name = "zhrs", nullable = true, length = 10)
    private String zhrs;
    
    /** 破获案件数 */
    @Column(name = "phajs", nullable = true, length = 10)
    private String phajs;
    
    /** 反馈内容 */
    @Column(name = "fknr", nullable = true, length = 1024)
    private String fknr;
    
    /** 查获时间 */
    @Column(name = "chsj", nullable = true, length = 20)
    private String chsj;
    
    /** 查获地点 */
    @Column(name = "chdd", nullable = true, length = 1024)
    private String chdd;
    
    /** 信息来源 */
    @Column(name = "xxly", nullable = true, length = 1)
    private String xxly;
    
    /** 备用字段1 */
    @Column(name = "by1", nullable = true, length = 256)
    private String by1;
    
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
     * 获取指令ID
     * 
     * @return 指令ID
     */
     public Integer getZlid() {
        return this.zlid;
     }
     
    /**
     * 设置指令ID
     * 
     * @param zlid
     *          指令ID
     */
     public void setZlid(Integer zlid) {
        this.zlid = zlid;
     }
    
    /**
     * 获取签收人
     * 
     * @return 签收人
     */
     public String getQsr() {
        return this.qsr;
     }
     
    /**
     * 设置签收人
     * 
     * @param qsr
     *          签收人
     */
     public void setQsr(String qsr) {
        this.qsr = qsr;
     }
    
    /**
     * 获取签收人名称
     * 
     * @return 签收人名称
     */
     public String getQsrmc() {
        return this.qsrmc;
     }
     
    /**
     * 设置签收人名称
     * 
     * @param qsrmc
     *          签收人名称
     */
     public void setQsrmc(String qsrmc) {
        this.qsrmc = qsrmc;
     }
    
    /**
     * 获取签收状态(0、未签收，1、已签收)
     * 
     * @return 签收状态(0、未签收
     */
     public String getQszt() {
        return this.qszt;
     }
     
    /**
     * 设置签收状态(0、未签收，1、已签收)
     * 
     * @param qszt
     *          签收状态(0、未签收，1、已签收)
     */
     public void setQszt(String qszt) {
        this.qszt = qszt;
     }
    
    /**
     * 获取签收时间
     * 
     * @return 签收时间
     */
     public Date getQssj() {
        return this.qssj;
     }
     
    /**
     * 设置签收时间
     * 
     * @param qssj
     *          签收时间
     */
     public void setQssj(Date qssj) {
        this.qssj = qssj;
     }
    
    /**
     * 获取预计到达时间
     * 
     * @return 预计到达时间
     */
     public String getYjsj() {
        return this.yjsj;
     }
     
    /**
     * 设置预计到达时间
     * 
     * @param yjsj
     *          预计到达时间
     */
     public void setYjsj(String yjsj) {
        this.yjsj = yjsj;
     }
    
    /**
     * 获取反馈状态(0、未反馈，1、已反馈)
     * 
     * @return 反馈状态(0、未反馈
     */
     public String getFkzt() {
        return this.fkzt;
     }
     
    /**
     * 设置反馈状态(0、未反馈，1、已反馈)
     * 
     * @param fkzt
     *          反馈状态(0、未反馈，1、已反馈)
     */
     public void setFkzt(String fkzt) {
        this.fkzt = fkzt;
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
     * 获取卡点名称
     * 
     * @return 卡点名称
     */
     public String getKdmc() {
        return this.kdmc;
     }
     
    /**
     * 设置卡点名称
     * 
     * @param kdmc
     *          卡点名称
     */
     public void setKdmc(String kdmc) {
        this.kdmc = kdmc;
     }
    
    /**
     * 获取卡点ID
     * 
     * @return 卡点ID
     */
     public String getKdid() {
        return this.kdid;
     }
     
    /**
     * 设置卡点ID
     * 
     * @param kdid
     *          卡点ID
     */
     public void setKdid(String kdid) {
        this.kdid = kdid;
     }
    
    /**
     * 获取带队人
     * 
     * @return 带队人
     */
     public String getDdr() {
        return this.ddr;
     }
     
    /**
     * 设置带队人
     * 
     * @param ddr
     *          带队人
     */
     public void setDdr(String ddr) {
        this.ddr = ddr;
     }
    
    /**
     * 获取协办人
     * 
     * @return 协办人
     */
     public String getXbr() {
        return this.xbr;
     }
     
    /**
     * 设置协办人
     * 
     * @param xbr
     *          协办人
     */
     public void setXbr(String xbr) {
        this.xbr = xbr;
     }
    
    /**
     * 获取是否拦截到（0:未拦截到，1:拦截到）
     * 
     * @return 是否拦截到（0
     */
     public String getSflj() {
        return this.sflj;
     }
     
    /**
     * 设置是否拦截到（0:未拦截到，1:拦截到）
     * 
     * @param sflj
     *          是否拦截到（0:未拦截到，1:拦截到）
     */
     public void setSflj(String sflj) {
        this.sflj = sflj;
     }
    
    /**
     * 获取未拦截到原因（0:冲卡1:有岔路存在9:其他）
     * 
     * @return 未拦截到原因（0
     */
     public String getWljdyy() {
        return this.wljdyy;
     }
     
    /**
     * 设置未拦截到原因（0:冲卡1:有岔路存在9:其他）
     * 
     * @param wljdyy
     *          未拦截到原因（0:冲卡1:有岔路存在9:其他）
     */
     public void setWljdyy(String wljdyy) {
        this.wljdyy = wljdyy;
     }
    
    /**
     * 获取反馈人
     * 
     * @return 反馈人
     */
     public String getFkr() {
        return this.fkr;
     }
     
    /**
     * 设置反馈人
     * 
     * @param fkr
     *          反馈人
     */
     public void setFkr(String fkr) {
        this.fkr = fkr;
     }
    
    /**
     * 获取反馈人名称
     * 
     * @return 反馈人名称
     */
     public String getFkrmc() {
        return this.fkrmc;
     }
     
    /**
     * 设置反馈人名称
     * 
     * @param fkrmc
     *          反馈人名称
     */
     public void setFkrmc(String fkrmc) {
        this.fkrmc = fkrmc;
     }

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}

	public String getQsztmc() {
		return qsztmc;
	}

	public void setQsztmc(String qsztmc) {
		this.qsztmc = qsztmc;
	}

	public String getFkztmc() {
		return fkztmc;
	}

	public void setFkztmc(String fkztmc) {
		this.fkztmc = fkztmc;
	}

	public String getZlbm() {
		return zlbm;
	}

	public void setZlbm(String zlbm) {
		this.zlbm = zlbm;
	}

	public String getZlbmmc() {
		return zlbmmc;
	}

	public void setZlbmmc(String zlbmmc) {
		this.zlbmmc = zlbmmc;
	}

	public Date getZlsj() {
		return zlsj;
	}

	public void setZlsj(Date zlsj) {
		this.zlsj = zlsj;
	}

	/**
	 * @return the qsbm
	 */
	public String getQsbm() {
		return qsbm;
	}

	/**
	 * @param qsbm the qsbm to set
	 */
	public void setQsbm(String qsbm) {
		this.qsbm = qsbm;
	}

	/**
	 * @return the qsbmmc
	 */
	public String getQsbmmc() {
		return qsbmmc;
	}

	/**
	 * @param qsbmmc the qsbmmc to set
	 */
	public void setQsbmmc(String qsbmmc) {
		this.qsbmmc = qsbmmc;
	}

	/**
	 * @return the fkbm
	 */
	public String getFkbm() {
		return fkbm;
	}

	/**
	 * @param fkbm the fkbm to set
	 */
	public void setFkbm(String fkbm) {
		this.fkbm = fkbm;
	}

	/**
	 * @return the fkbmmc
	 */
	public String getFkbmmc() {
		return fkbmmc;
	}

	/**
	 * @param fkbmmc the fkbmmc to set
	 */
	public void setFkbmmc(String fkbmmc) {
		this.fkbmmc = fkbmmc;
	}

	public String getQsms() {
		return qsms;
	}

	public void setQsms(String qsms) {
		this.qsms = qsms;
	}

	public String getQsrlxdh() {
		return qsrlxdh;
	}

	public void setQsrlxdh(String qsrlxdh) {
		this.qsrlxdh = qsrlxdh;
	}

	public String getHphm() {
		return hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public String getHpzl() {
		return hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public String getCzjg() {
		return czjg;
	}

	public void setCzjg(String czjg) {
		this.czjg = czjg;
	}

	public String getZhrs() {
		return zhrs;
	}

	public void setZhrs(String zhrs) {
		this.zhrs = zhrs;
	}

	public String getPhajs() {
		return phajs;
	}

	public void setPhajs(String phajs) {
		this.phajs = phajs;
	}

	public String getFkrlxdh() {
		return fkrlxdh;
	}

	public void setFkrlxdh(String fkrlxdh) {
		this.fkrlxdh = fkrlxdh;
	}

	public Integer getBkid() {
		return bkid;
	}

	public void setBkid(Integer bkid) {
		this.bkid = bkid;
	}

	public String getChsj() {
		return chsj;
	}

	public void setChsj(String chsj) {
		this.chsj = chsj;
	}

	public String getChdd() {
		return chdd;
	}

	public void setChdd(String chdd) {
		this.chdd = chdd;
	}

	public String getBy1() {
		return by1;
	}

	public void setBy1(String by1) {
		this.by1 = by1;
	}

	public String getXxly() {
		return xxly;
	}

	public void setXxly(String xxly) {
		this.xxly = xxly;
	}
}