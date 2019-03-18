
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="str_zjlx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="str_zjhm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "strZjlx",
    "strZjhm"
})
@XmlRootElement(name = "jsyxxcx")
public class Jsyxxcx {

    @XmlElement(name = "str_zjlx")
    protected String strZjlx;
    @XmlElement(name = "str_zjhm")
    protected String strZjhm;

    /**
     * Gets the value of the strZjlx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrZjlx() {
        return strZjlx;
    }

    /**
     * Sets the value of the strZjlx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrZjlx(String value) {
        this.strZjlx = value;
    }

    /**
     * Gets the value of the strZjhm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrZjhm() {
        return strZjhm;
    }

    /**
     * Sets the value of the strZjhm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrZjhm(String value) {
        this.strZjhm = value;
    }

}
