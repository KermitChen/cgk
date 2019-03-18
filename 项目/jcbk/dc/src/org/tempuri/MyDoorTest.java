
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="strYzid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strHphm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strHpzl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "strYzid",
    "strHphm",
    "strHpzl"
})
@XmlRootElement(name = "MyDoorTest")
public class MyDoorTest {

    protected String strYzid;
    protected String strHphm;
    protected String strHpzl;

    /**
     * Gets the value of the strYzid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrYzid() {
        return strYzid;
    }

    /**
     * Sets the value of the strYzid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrYzid(String value) {
        this.strYzid = value;
    }

    /**
     * Gets the value of the strHphm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrHphm() {
        return strHphm;
    }

    /**
     * Sets the value of the strHphm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrHphm(String value) {
        this.strHphm = value;
    }

    /**
     * Gets the value of the strHpzl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrHpzl() {
        return strHpzl;
    }

    /**
     * Sets the value of the strHpzl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrHpzl(String value) {
        this.strHpzl = value;
    }

}
