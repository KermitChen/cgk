package com.dyst.dispatched.entities;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 单号生成表(DHSCB)
 * 
 * @version 1.0.0 2016-08-05
 */
@Entity
@Table(name = "DHSCB")
public class Lsh implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 6049300052318363423L;
    
    /** 生成时间（天） */
    @Id
    @Column(name = "scsj", unique = true, nullable = false, length = 10)
    private String scsj;
    
    /** 基层流水号 */
    @Column(name = "jclsh", nullable = true, length = 10)
    private Integer jclsh;
    
    /** 分局流水号 */
    @Column(name = "fjlsh", nullable = true, length = 10)
    private Integer fjlsh;
    
    /** 市局流水号 */
    @Column(name = "sjlsh", nullable = true, length = 10)
    private Integer sjlsh;
    
    /**
     * 获取生成时间（天）
     * 
     * @return 生成时间（天）
     */
     public String getScsj() {
        return this.scsj;
     }
     
    /**
     * 设置生成时间（天）
     * 
     * @param scsj
     *          生成时间（天）
     */
     public void setScsj(String scsj) {
        this.scsj = scsj;
     }
    
    /**
     * 获取基层流水号
     * 
     * @return 基层流水号
     */
     public Integer getJclsh() {
        return this.jclsh;
     }
     
    /**
     * 设置基层流水号
     * 
     * @param jclsh
     *          基层流水号
     */
     public void setJclsh(Integer jclsh) {
        this.jclsh = jclsh;
     }
    
    /**
     * 获取分局流水号
     * 
     * @return 分局流水号
     */
     public Integer getFjlsh() {
        return this.fjlsh;
     }
     
    /**
     * 设置分局流水号
     * 
     * @param fjlsh
     *          分局流水号
     */
     public void setFjlsh(Integer fjlsh) {
        this.fjlsh = fjlsh;
     }
    
    /**
     * 获取市局流水号
     * 
     * @return 市局流水号
     */
     public Integer getSjlsh() {
        return this.sjlsh;
     }
     
    /**
     * 设置市局流水号
     * 
     * @param sjlsh
     *          市局流水号
     */
     public void setSjlsh(Integer sjlsh) {
        this.sjlsh = sjlsh;
     }
}