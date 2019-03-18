package com.dyst.earlyWarning.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Target;
import org.springframework.format.annotation.DateTimeFormat;

import com.dyst.dispatched.entities.Dispatched;
@Entity
@Table(name = "YJQS")
public class EWRecieve implements java.io.Serializable {
	/** 版本号 */
    private static final long serialVersionUID = 179018157834096659L;
    
    /** 序号 */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
    @Column(name = "qsid", unique = true, nullable = false, length = 10)
    private Integer qsid;
    
    /** 布控 */
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bkid",insertable= false ,updatable= false)
    @Target(Dispatched.class)
    private Dispatched dispatched;
    
    /** 预警 */
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bjxh",insertable= false ,updatable= false)
    @Target(EWarning.class)
    private EWarning ewarning;
    
    /** 布控表id关联 */
    @Column(name = "bkid", nullable = false, length = 10)
    private Integer bkid;
    
    /** 历史预警ID关联 */
    @Column(name = "bjxh", nullable = false, length = 10)
    private Integer bjxh;
    
    /** 号牌号码 */
    @Column(name = "hphm", nullable = false, length = 15)
    private String hphm;
    
    /** 号牌种类 */
    @Column(name = "hpzl", nullable = true, length = 2)
    private String hpzl;
    
    /** 车牌类型 */
    @Column(name = "cplx", nullable = true, length = 2)
    private String cplx;
    
    /** 监测点ID */
    @Column(name = "jcdid", nullable = false, length = 18)
    private String jcdid;
    
    /** 监测点名称 */
    @Column(name = "jcdmc", nullable = true, length = 128)
    private String jcdmc;
    
    /** 经度 */
    @Column(name = "jd", nullable = true)
	private Double jd;
	
	/** 纬度 */
    @Column(name = "wd", nullable = true)
	private Double wd;
    
    /** 车道ID */
    @Column(name = "cdid", nullable = true, length = 2)
    private String cdid;
    
    /** 图片1 */
    @Column(name = "tpid", nullable = false, length = 256)
    private String tpid;
    
	/** 车辆速度 */
	@Column(name = "sd", nullable = true)
	private double sd;
    
    /** 通过时间 */
    @Column(name = "tgsj", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date tgsj;
    
    /** 上传时间 */
    @Column(name = "scsj", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date scsj;
    
	/** 报警时间 */
	@Column(name = "bjsj", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date bjsj;
    
    /** 报警大类 */
    @Column(name = "bjdl", nullable = true, length = 2)
    private String bjdl;
    
    /** 布控类别 */
    @Column(name = "bklb", nullable = true, length = 2)
    private String bklb;
    
    /** 报警类型 */
    @Column(name = "bjlx", nullable = true, length = 2)
    private String bjlx;
    
    /** 报警部门 */
    @Column(name = "bjbm", nullable = true, length = 20)
    private String bjbm;
    
    /** 报警部门名称 */
    @Column(name = "bjbmmc", nullable = true, length = 128)
    private String bjbmmc;
    
    /** 确认人警号 */
    @Column(name = "qrrjh", nullable = true, length = 50)
    private String qrrjh;
    
    /** 确认人 */
    @Column(name = "qrr", nullable = true, length = 128)
    private String qrr;
    
    /** 确认单位代码 */
    @Column(name = "qrdwdm", nullable = true, length = 20)
    private String qrdwdm;
    
    /** 确认单位代码名称 */
    @Column(name = "qrdwdmmc", nullable = true, length = 128)
    private String qrdwdmmc;
    
    /** 确认单位联系电话 */
    @Column(name = "qrdwlxdh", nullable = true, length = 20)
    private String qrdwlxdh;
    
    /** 确认时间 */
    @Column(name = "qrsj", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date qrsj;
    
    /** 确认状态 */
    @Column(name = "qrzt", nullable = true, length = 2)
    private String qrzt;
    
    /** 签收状态名称 */
    @Formula("(select dict.TYPE_DESC from DICTIONARY dict where dict.DELETE_FLAG='0' and dict.TYPE_CODE='YJQRZT' and dict.TYPE_SERIAL_NO=qrzt)")
    private String qrztmc;
    
    /** 确认结果简述 */
    @Column(name = "qrjg", nullable = true, length = 1024)
    private String qrjg;
    
    /** 是否具有拦截条件 */
    @Column(name = "jyljtj", nullable = true, length = 1)
    private String jyljtj;
    
    /** 更新时间 */
    @Column(name = "gxsj", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date gxsj;
    
    /** 过车序号 */
    @Column(name = "gcxh", nullable = true, length = 200)
    private String gcxh;
    
    /** 识别编号 */
    @Column(name = "sbbh", nullable = true, length = 20)
    private String sbbh;
    
    /** 设备名称 */
    @Column(name = "sbmc", nullable = true, length = 128)
    private String sbmc;
    
    /** 方向编号 */
    @Column(name = "fxbh", nullable = true, length = 10)
    private String fxbh;
    
    /** 方向名称 */
    @Column(name = "fxmc", nullable = true, length = 256)
    private String fxmc;
    
    /** 车辆类型 */
    @Column(name = "cllx", nullable = true, length = 4)
    private String cllx;
    
    /** 车位号牌号码 */
    @Column(name = "cwhphm", nullable = true, length = 15)
    private String cwhphm;
    
    /** 车尾号牌颜色 */
    @Column(name = "cwhpys", nullable = true, length = 3)
    private String cwhpys;
    
    /** 号牌一致 */
    @Column(name = "hpyz", nullable = true, length = 1)
    private String hpyz;
    
    /** 车辆外形 */
    @Column(name = "clwx", nullable = true, length = 32)
    private String clwx;
    
    /** 车身颜色 */
    @Column(name = "csys", nullable = true, length = 3)
    private String csys;
    
    /** 信息来源 */
    @Column(name = "xxly", nullable = true, length = 1)
    private String xxly;
    
    /** 备用字段1 */
    @Column(name = "by1", nullable = true, length = 256)
    private String by1;
    
    /** 备用字段2 */
    @Column(name = "by2", nullable = true, length = 256)
    private String by2;
    
    /** 备用字段3 */
    @Column(name = "by3", nullable = true, length = 256)
    private String by3;
    
    /** 备用字段4 */
    @Column(name = "by4", nullable = true, length = 256)
    private String by4;
   
    
    /**
     * 获取报警序号
     * 
     * @return 报警序号
     */
     public Integer getBjxh() {
        return this.bjxh;
     }
     
    /**
     * 设置报警序号
     * 
     * @param bjxh
     *          报警序号
     */
     public void setBjxh(Integer bjxh) {
        this.bjxh = bjxh;
     }
    
    /**
     * 获取布控表id关联
     * 
     * @return 布控表id关联
     */
     public Integer getBkid() {
        return this.bkid;
     }
     
    /**
     * 设置布控表id关联
     * 
     * @param bkid
     *          布控表id关联
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
     * 获取过车序号
     * 
     * @return 过车序号
     */
     public String getGcxh() {
        return this.gcxh;
     }
     
    /**
     * 设置过车序号
     * 
     * @param gcxh
     *          过车序号
     */
     public void setGcxh(String gcxh) {
        this.gcxh = gcxh;
     }
    
    /**
     * 获取识别编号
     * 
     * @return 识别编号
     */
     public String getSbbh() {
        return this.sbbh;
     }
     
    /**
     * 设置识别编号
     * 
     * @param sbbh
     *          识别编号
     */
     public void setSbbh(String sbbh) {
        this.sbbh = sbbh;
     }
    
    /**
     * 获取设备名称
     * 
     * @return 设备名称
     */
     public String getSbmc() {
        return this.sbmc;
     }
     
    /**
     * 设置设备名称
     * 
     * @param sbmc
     *          设备名称
     */
     public void setSbmc(String sbmc) {
        this.sbmc = sbmc;
     }
    
    /**
     * 获取监测点ID
     * 
     * @return 监测点ID
     */
     public String getJcdid() {
        return this.jcdid;
     }
     
    /**
     * 设置监测点ID
     * 
     * @param jcdid
     *          监测点ID
     */
     public void setJcdid(String jcdid) {
        this.jcdid = jcdid;
     }
    
    /**
     * 获取监测点名称
     * 
     * @return 监测点名称
     */
     public String getJcdmc() {
        return this.jcdmc;
     }
     
    /**
     * 设置监测点名称
     * 
     * @param jcdmc
     *          监测点名称
     */
     public void setJcdmc(String jcdmc) {
        this.jcdmc = jcdmc;
     }
    
    /**
     * 获取通过时间
     * 
     * @return 通过时间
     */
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     public Date getTgsj() {
        return this.tgsj;
     }
     
    /**
     * 设置通过时间
     * 
     * @param tgsj
     *          通过时间
     */
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     public void setTgsj(Date tgsj) {
        this.tgsj = tgsj;
     }
    
    /**
     * 获取方向编号
     * 
     * @return 方向编号
     */
     public String getFxbh() {
        return this.fxbh;
     }
     
    /**
     * 设置方向编号
     * 
     * @param fxbh
     *          方向编号
     */
     public void setFxbh(String fxbh) {
        this.fxbh = fxbh;
     }
    
    /**
     * 获取方向名称
     * 
     * @return 方向名称
     */
     public String getFxmc() {
        return this.fxmc;
     }
     
    /**
     * 设置方向名称
     * 
     * @param fxmc
     *          方向名称
     */
     public void setFxmc(String fxmc) {
        this.fxmc = fxmc;
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
     * 获取车辆速度
     * 
     * @return 车辆速度
     */
     public double getSd() {
        return this.sd;
     }
     
    /**
     * 设置车辆速度
     * 
     * @param sd
     *          车辆速度
     */
     public void setSd(double sd) {
        this.sd = sd;
     }
    
    /**
     * 获取车位号牌号码
     * 
     * @return 车位号牌号码
     */
     public String getCwhphm() {
        return this.cwhphm;
     }
     
    /**
     * 设置车位号牌号码
     * 
     * @param cwhphm
     *          车位号牌号码
     */
     public void setCwhphm(String cwhphm) {
        this.cwhphm = cwhphm;
     }
    
    /**
     * 获取车尾号牌颜色
     * 
     * @return 车尾号牌颜色
     */
     public String getCwhpys() {
        return this.cwhpys;
     }
     
    /**
     * 设置车尾号牌颜色
     * 
     * @param cwhpys
     *          车尾号牌颜色
     */
     public void setCwhpys(String cwhpys) {
        this.cwhpys = cwhpys;
     }
    
    /**
     * 获取号牌一致
     * 
     * @return 号牌一致
     */
     public String getHpyz() {
        return this.hpyz;
     }
     
    /**
     * 设置号牌一致
     * 
     * @param hpyz
     *          号牌一致
     */
     public void setHpyz(String hpyz) {
        this.hpyz = hpyz;
     }
    
    /**
     * 获取车道ID
     * 
     * @return 车道ID
     */
     public String getCdid() {
        return this.cdid;
     }
     
    /**
     * 设置车道ID
     * 
     * @param cdid
     *          车道ID
     */
     public void setCdid(String cdid) {
        this.cdid = cdid;
     }
    
    /**
     * 获取车辆外形
     * 
     * @return 车辆外形
     */
     public String getClwx() {
        return this.clwx;
     }
     
    /**
     * 设置车辆外形
     * 
     * @param clwx
     *          车辆外形
     */
     public void setClwx(String clwx) {
        this.clwx = clwx;
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
     * 获取图片1
     * 
     * @return 图片1
     */
     public String getTpid() {
        return this.tpid;
     }
     
    /**
     * 设置图片1
     * 
     * @param tpid1
     *          图片1
     */
     public void setTpid(String tpid) {
        this.tpid = tpid;
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
     * 获取确认单位代码
     * 
     * @return 确认单位代码
     */
     public String getQrdwdm() {
        return this.qrdwdm;
     }
     
    /**
     * 设置确认单位代码
     * 
     * @param qrdwdm
     *          确认单位代码
     */
     public void setQrdwdm(String qrdwdm) {
        this.qrdwdm = qrdwdm;
     }
    
    /**
     * 获取确认单位代码名称
     * 
     * @return 确认单位代码名称
     */
     public String getQrdwdmmc() {
        return this.qrdwdmmc;
     }
     
    /**
     * 设置确认单位代码名称
     * 
     * @param qrdwdmmc
     *          确认单位代码名称
     */
     public void setQrdwdmmc(String qrdwdmmc) {
        this.qrdwdmmc = qrdwdmmc;
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
     * 获取确认状态
     * 
     * @return 确认状态
     */
     public String getQrzt() {
        return this.qrzt;
     }
     
    /**
     * 设置确认状态
     * 
     * @param qrzt
     *          确认状态
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
     * 获取更新时间
     * 
     * @return 更新时间
     */
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
     * 获取是否具有拦截条件
     * 
     * @return 是否具有拦截条件
     */
     public String getJyljtj() {
        return this.jyljtj;
     }
     
    /**
     * 设置是否具有拦截条件
     * 
     * @param jyljtj
     *          是否具有拦截条件
     */
     public void setJyljtj(String jyljtj) {
        this.jyljtj = jyljtj;
     }
    
    /**
     * 获取信息来源
     * 
     * @return 信息来源
     */
     public String getXxly() {
        return this.xxly;
     }
     
    /**
     * 设置信息来源
     * 
     * @param xxly
     *          信息来源
     */
     public void setXxly(String xxly) {
        this.xxly = xxly;
     }
    
    /**
     * 获取报警大类
     * 
     * @return 报警大类
     */
     public String getBjdl() {
        return this.bjdl;
     }
     
    /**
     * 设置报警大类
     * 
     * @param bjdl
     *          报警大类
     */
     public void setBjdl(String bjdl) {
        this.bjdl = bjdl;
     }
    
    /**
     * 获取备用字段1
     * 
     * @return 备用字段1
     */
     public String getBy1() {
        return this.by1;
     }
     
    /**
     * 设置备用字段1
     * 
     * @param by1
     *          备用字段1
     */
     public void setBy1(String by1) {
        this.by1 = by1;
     }
    
    /**
     * 获取备用字段2
     * 
     * @return 备用字段2
     */
     public String getBy2() {
        return this.by2;
     }
     
    /**
     * 设置备用字段2
     * 
     * @param by2
     *          备用字段2
     */
     public void setBy2(String by2) {
        this.by2 = by2;
     }
    
    /**
     * 获取备用字段3
     * 
     * @return 备用字段3
     */
     public String getBy3() {
        return this.by3;
     }
     
    /**
     * 设置备用字段3
     * 
     * @param by3
     *          备用字段3
     */
     public void setBy3(String by3) {
        this.by3 = by3;
     }
    
    /**
     * 获取备用字段4
     * 
     * @return 备用字段4
     */
     public String getBy4() {
        return this.by4;
     }
     
    /**
     * 设置备用字段4
     * 
     * @param by4
     *          备用字段4
     */
     public void setBy4(String by4) {
        this.by4 = by4;
     }
    
     public Dispatched getDispatched() {
 		return dispatched;
 	}

 	public void setDispatched(Dispatched dispatched) {
 		this.dispatched = dispatched;
 	}

	public Date getScsj() {
		return scsj;
	}

	public void setScsj(Date scsj) {
		this.scsj = scsj;
	}

	public Integer getQsid() {
		return qsid;
	}

	public void setQsid(Integer qsid) {
		this.qsid = qsid;
	}

	public EWarning getEwarning() {
		return ewarning;
	}

	public void setEwarning(EWarning ewarning) {
		this.ewarning = ewarning;
	}

	public String getBjlx() {
		return bjlx;
	}

	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}

	public Double getJd() {
		return jd;
	}

	public void setJd(Double jd) {
		this.jd = jd;
	}

	public Double getWd() {
		return wd;
	}

	public void setWd(Double wd) {
		this.wd = wd;
	}

	public String getBjbm() {
		return bjbm;
	}

	public void setBjbm(String bjbm) {
		this.bjbm = bjbm;
	}

	public String getBjbmmc() {
		return bjbmmc;
	}

	public void setBjbmmc(String bjbmmc) {
		this.bjbmmc = bjbmmc;
	}

	public String getBklb() {
		return bklb;
	}

	public void setBklb(String bklb) {
		this.bklb = bklb;
	}

	public Date getBjsj() {
		return bjsj;
	}

	public void setBjsj(Date bjsj) {
		this.bjsj = bjsj;
	}

	public String getQrztmc() {
		return qrztmc;
	}

	public void setQrztmc(String qrztmc) {
		this.qrztmc = qrztmc;
	}

	public String getCplx() {
		return cplx;
	}

	public void setCplx(String cplx) {
		this.cplx = cplx;
	}
}