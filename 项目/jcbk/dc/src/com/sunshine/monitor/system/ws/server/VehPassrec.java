
package com.sunshine.monitor.system.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>VehPassrec complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="VehPassrec"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="gcxh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sbbh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kdbh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fxbh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hpzl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hphm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="gcsj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="clsd" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="hpys" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cllx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tp1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tp2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tp3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="wfbj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="by1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="rksj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="clxs" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="cwhphm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cwhpys" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hpyz" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="xszt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="byzd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="clpp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="clwx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="csys" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cdbh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kssj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jssj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kssd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jssd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="glbm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hpzlmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sbmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kdmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fxmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hpysmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cllxmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="wfbjmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cwhpysmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hpyzmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="csysmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="xsztmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="wpc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="datasource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kkjd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kkwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehPassrec", propOrder = {
    "id",	
    "gcxh",
    "sbbh",
    "kdbh",
    "fxbh",
    "hpzl",
    "hphm",
    "gcsj",
    "clsd",
    "hpys",
    "cllx",
    "tp1",
    "tp2",
    "tp3",
    "wfbj",
    "by1",
    "rksj",
    "clxs",
    "cwhphm",
    "cwhpys",
    "hpyz",
    "xszt",
    "byzd",
    "clpp",
    "clwx",
    "csys",
    "cdbh",
    "kssj",
    "jssj",
    "kssd",
    "jssd",
    "glbm",
    "hpzlmc",
    "sbmc",
    "kdmc",
    "fxmc",
    "hpysmc",
    "cllxmc",
    "wfbjmc",
    "cwhpysmc",
    "hpyzmc",
    "csysmc",
    "xsztmc",
    "wpc",
    "city",
    "datasource",
    "ll",
    "kkjd",
    "kkwd"
})
public class VehPassrec {

	protected int id;
    protected String gcxh;
    protected String sbbh;
    protected String kdbh;
    protected String fxbh;
    protected String hpzl;
    protected String hphm;
    protected String gcsj;
    protected Long clsd;
    protected String hpys;
    protected String cllx;
    protected String tp1;
    protected String tp2;
    protected String tp3;
    protected String wfbj;
    protected String by1;
    protected String rksj;
    protected Long clxs;
    protected String cwhphm;
    protected String cwhpys;
    protected String hpyz;
    protected String xszt;
    protected String byzd;
    protected String clpp;
    protected String clwx;
    protected String csys;
    protected String cdbh;
    protected String kssj;
    protected String jssj;
    protected String kssd;
    protected String jssd;
    protected String glbm;
    protected String hpzlmc;
    protected String sbmc;
    protected String kdmc;
    protected String fxmc;
    protected String hpysmc;
    protected String cllxmc;
    protected String wfbjmc;
    protected String cwhpysmc;
    protected String hpyzmc;
    protected String csysmc;
    protected String xsztmc;
    protected String wpc;
    protected String city;
    protected String datasource;
    protected String ll;
    protected String kkjd;
    protected String kkwd;

    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
     * ��ȡgcxh���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGcxh() {
        return gcxh;
    }

    /**
     * ����gcxh���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGcxh(String value) {
        this.gcxh = value;
    }

    /**
     * ��ȡsbbh���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSbbh() {
        return sbbh;
    }

    /**
     * ����sbbh���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSbbh(String value) {
        this.sbbh = value;
    }

    /**
     * ��ȡkdbh���Ե�ֵ��
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
     * ����kdbh���Ե�ֵ��
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
     * ��ȡfxbh���Ե�ֵ��
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
     * ����fxbh���Ե�ֵ��
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
     * ��ȡhpzl���Ե�ֵ��
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
     * ����hpzl���Ե�ֵ��
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
     * ��ȡhphm���Ե�ֵ��
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
     * ����hphm���Ե�ֵ��
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
     * ��ȡgcsj���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGcsj() {
        return gcsj;
    }

    /**
     * ����gcsj���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGcsj(String value) {
        this.gcsj = value;
    }

    /**
     * ��ȡclsd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getClsd() {
        return clsd;
    }

    /**
     * ����clsd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setClsd(Long value) {
        this.clsd = value;
    }

    /**
     * ��ȡhpys���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHpys() {
        return hpys;
    }

    /**
     * ����hpys���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHpys(String value) {
        this.hpys = value;
    }

    /**
     * ��ȡcllx���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCllx() {
        return cllx;
    }

    /**
     * ����cllx���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCllx(String value) {
        this.cllx = value;
    }

    /**
     * ��ȡtp1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTp1() {
        return tp1;
    }

    /**
     * ����tp1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTp1(String value) {
        this.tp1 = value;
    }

    /**
     * ��ȡtp2���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTp2() {
        return tp2;
    }

    /**
     * ����tp2���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTp2(String value) {
        this.tp2 = value;
    }

    /**
     * ��ȡtp3���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTp3() {
        return tp3;
    }

    /**
     * ����tp3���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTp3(String value) {
        this.tp3 = value;
    }

    /**
     * ��ȡwfbj���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWfbj() {
        return wfbj;
    }

    /**
     * ����wfbj���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWfbj(String value) {
        this.wfbj = value;
    }

    /**
     * ��ȡby1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBy1() {
        return by1;
    }

    /**
     * ����by1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBy1(String value) {
        this.by1 = value;
    }

    /**
     * ��ȡrksj���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRksj() {
        return rksj;
    }

    /**
     * ����rksj���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRksj(String value) {
        this.rksj = value;
    }

    /**
     * ��ȡclxs���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getClxs() {
        return clxs;
    }

    /**
     * ����clxs���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setClxs(Long value) {
        this.clxs = value;
    }

    /**
     * ��ȡcwhphm���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCwhphm() {
        return cwhphm;
    }

    /**
     * ����cwhphm���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCwhphm(String value) {
        this.cwhphm = value;
    }

    /**
     * ��ȡcwhpys���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCwhpys() {
        return cwhpys;
    }

    /**
     * ����cwhpys���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCwhpys(String value) {
        this.cwhpys = value;
    }

    /**
     * ��ȡhpyz���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHpyz() {
        return hpyz;
    }

    /**
     * ����hpyz���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHpyz(String value) {
        this.hpyz = value;
    }

    /**
     * ��ȡxszt���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXszt() {
        return xszt;
    }

    /**
     * ����xszt���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXszt(String value) {
        this.xszt = value;
    }

    /**
     * ��ȡbyzd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getByzd() {
        return byzd;
    }

    /**
     * ����byzd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setByzd(String value) {
        this.byzd = value;
    }

    /**
     * ��ȡclpp���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClpp() {
        return clpp;
    }

    /**
     * ����clpp���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClpp(String value) {
        this.clpp = value;
    }

    /**
     * ��ȡclwx���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClwx() {
        return clwx;
    }

    /**
     * ����clwx���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClwx(String value) {
        this.clwx = value;
    }

    /**
     * ��ȡcsys���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCsys() {
        return csys;
    }

    /**
     * ����csys���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCsys(String value) {
        this.csys = value;
    }

    /**
     * ��ȡcdbh���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCdbh() {
        return cdbh;
    }

    /**
     * ����cdbh���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCdbh(String value) {
        this.cdbh = value;
    }

    /**
     * ��ȡkssj���Ե�ֵ��
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
     * ����kssj���Ե�ֵ��
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
     * ��ȡjssj���Ե�ֵ��
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
     * ����jssj���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJssj(String value) {
        this.jssj = value;
    }

    /**
     * ��ȡkssd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKssd() {
        return kssd;
    }

    /**
     * ����kssd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKssd(String value) {
        this.kssd = value;
    }

    /**
     * ��ȡjssd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJssd() {
        return jssd;
    }

    /**
     * ����jssd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJssd(String value) {
        this.jssd = value;
    }

    /**
     * ��ȡglbm���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlbm() {
        return glbm;
    }

    /**
     * ����glbm���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlbm(String value) {
        this.glbm = value;
    }

    /**
     * ��ȡhpzlmc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHpzlmc() {
        return hpzlmc;
    }

    /**
     * ����hpzlmc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHpzlmc(String value) {
        this.hpzlmc = value;
    }

    /**
     * ��ȡsbmc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSbmc() {
        return sbmc;
    }

    /**
     * ����sbmc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSbmc(String value) {
        this.sbmc = value;
    }

    /**
     * ��ȡkdmc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKdmc() {
        return kdmc;
    }

    /**
     * ����kdmc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKdmc(String value) {
        this.kdmc = value;
    }

    /**
     * ��ȡfxmc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFxmc() {
        return fxmc;
    }

    /**
     * ����fxmc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFxmc(String value) {
        this.fxmc = value;
    }

    /**
     * ��ȡhpysmc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHpysmc() {
        return hpysmc;
    }

    /**
     * ����hpysmc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHpysmc(String value) {
        this.hpysmc = value;
    }

    /**
     * ��ȡcllxmc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCllxmc() {
        return cllxmc;
    }

    /**
     * ����cllxmc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCllxmc(String value) {
        this.cllxmc = value;
    }

    /**
     * ��ȡwfbjmc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWfbjmc() {
        return wfbjmc;
    }

    /**
     * ����wfbjmc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWfbjmc(String value) {
        this.wfbjmc = value;
    }

    /**
     * ��ȡcwhpysmc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCwhpysmc() {
        return cwhpysmc;
    }

    /**
     * ����cwhpysmc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCwhpysmc(String value) {
        this.cwhpysmc = value;
    }

    /**
     * ��ȡhpyzmc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHpyzmc() {
        return hpyzmc;
    }

    /**
     * ����hpyzmc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHpyzmc(String value) {
        this.hpyzmc = value;
    }

    /**
     * ��ȡcsysmc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCsysmc() {
        return csysmc;
    }

    /**
     * ����csysmc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCsysmc(String value) {
        this.csysmc = value;
    }

    /**
     * ��ȡxsztmc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXsztmc() {
        return xsztmc;
    }

    /**
     * ����xsztmc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXsztmc(String value) {
        this.xsztmc = value;
    }

    /**
     * ��ȡwpc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWpc() {
        return wpc;
    }

    /**
     * ����wpc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWpc(String value) {
        this.wpc = value;
    }

    /**
     * ��ȡcity���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * ����city���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * ��ȡdatasource���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * ����datasource���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasource(String value) {
        this.datasource = value;
    }

    /**
     * ��ȡll���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLl() {
        return ll;
    }

    /**
     * ����ll���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLl(String value) {
        this.ll = value;
    }

    /**
     * ��ȡkkjd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKkjd() {
        return kkjd;
    }

    /**
     * ����kkjd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKkjd(String value) {
        this.kkjd = value;
    }

    /**
     * ��ȡkkwd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKkwd() {
        return kkwd;
    }

    /**
     * ����kkwd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKkwd(String value) {
        this.kkwd = value;
    }

}
