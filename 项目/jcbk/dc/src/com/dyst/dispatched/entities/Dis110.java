package com.dyst.dispatched.entities;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 110平台布控表(ZJBK)
 * 
 * @version 1.0.0 2016-07-04
 */
@Entity
@Table(name = "ZJBK")
public class Dis110 implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8413770593759341868L;
    
    /** 主键ID */
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 10)
    private Integer id;
    
    /** 警员警号 */
    @Column(name = "jyjy", nullable = true, length = 20)
    private String jyjy;
    
    /** 号牌号码 */
    @Column(name = "hphm", nullable = true, length = 15)
    private String hphm;
    
    /** 号牌颜色 */
    @Column(name = "hpys", nullable = true, length = 2)
    private String hpys;
    
    /** 号牌颜色 */
    @Transient
    private String hpzl;
    
    /** 警情编号 */
    @Column(name = "jqbh", nullable = true, length = 20)
    private String jqbh;
    
    /** 警情描述 */
    @Column(name = "jqms", nullable = true, length = 1024)
    private String jqms;
    
    /** 接警时间 */
    @Column(name = "bjsj", nullable = true)
    private Date bjsj;
    
    /** 布控状态（0：未布控；,1：布控） */
    @Column(name = "bkzt", nullable = true, length = 1)
    private String bkzt;
    
    /**
     * 获取主键ID
     * 
     * @return 主键ID
     */
     public Integer getId() {
        return this.id;
     }
     
    /**
     * 设置主键ID
     * 
     * @param id
     *          主键ID
     */
     public void setId(Integer id) {
        this.id = id;
     }
    
    /**
     * 获取警员警号
     * 
     * @return 警员警号
     */
     public String getJyjy() {
        return this.jyjy;
     }
     
    /**
     * 设置警员警号
     * 
     * @param jyjy
     *          警员警号
     */
     public void setJyjy(String jyjy) {
        this.jyjy = jyjy;
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
     * 获取警情编号
     * 
     * @return 警情编号
     */
     public String getJqbh() {
        return this.jqbh;
     }
     
    /**
     * 设置警情编号
     * 
     * @param jqbh
     *          警情编号
     */
     public void setJqbh(String jqbh) {
        this.jqbh = jqbh;
     }
    
    /**
     * 获取警情描述
     * 
     * @return 警情描述
     */
     public String getJqms() {
        return this.jqms;
     }
     
    /**
     * 设置警情描述
     * 
     * @param jqms
     *          警情描述
     */
     public void setJqms(String jqms) {
        this.jqms = jqms;
     }
    
    /**
     * 获取接警时间
     * 
     * @return 接警时间
     */
     public Date getBjsj() {
        return this.bjsj;
     }
     
    /**
     * 设置接警时间
     * 
     * @param bjsj
     *          接警时间
     */
     public void setBjsj(Date bjsj) {
        this.bjsj = bjsj;
     }
    
    /**
     * 获取布控状态（0：未布控；,1：布控）
     * 
     * @return 布控状态（0
     */
     public String getBkzt() {
        return this.bkzt;
     }
     
    /**
     * 设置布控状态（0：未布控；,1：布控）
     * 
     * @param bkzt
     *          布控状态（0：未布控；,1：布控）
     */
     public void setBkzt(String bkzt) {
        this.bkzt = bkzt;
     }

	public String getHpzl() {
		return hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}
}