
package com.sunshine.monitor.system.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>QueryVehPassrecEntity complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="QueryVehPassrecEntity"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kdbh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fxbh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hpzl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hphm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kssj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jssj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryVehPassrecEntity", propOrder = {
    "kdbh",
    "fxbh",
    "hpzl",
    "hphm",
    "kssj",
    "jssj"
})
public class QueryVehPassrecEntity {

    protected String kdbh;
    protected String fxbh;
    protected String hpzl;
    protected String hphm;
    protected String kssj;
    protected String jssj;

    /**
     * 获取kdbh属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKdbh() {
        return kdbh;
    }

    /**
     * 设置kdbh属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKdbh(String value) {
        this.kdbh = value;
    }

    /**
     * 获取fxbh属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFxbh() {
        return fxbh;
    }

    /**
     * 设置fxbh属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFxbh(String value) {
        this.fxbh = value;
    }

    /**
     * 获取hpzl属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHpzl() {
        return hpzl;
    }

    /**
     * 设置hpzl属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHpzl(String value) {
        this.hpzl = value;
    }

    /**
     * 获取hphm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHphm() {
        return hphm;
    }

    /**
     * 设置hphm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHphm(String value) {
        this.hphm = value;
    }

    /**
     * 获取kssj属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKssj() {
        return kssj;
    }

    /**
     * 设置kssj属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKssj(String value) {
        this.kssj = value;
    }

    /**
     * 获取jssj属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJssj() {
        return jssj;
    }

    /**
     * 设置jssj属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJssj(String value) {
        this.jssj = value;
    }

}
