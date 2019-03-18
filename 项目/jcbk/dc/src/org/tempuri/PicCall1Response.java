
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
 *         &lt;element name="PicCall1Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "picCall1Result"
})
@XmlRootElement(name = "PicCall1Response")
public class PicCall1Response {

    @XmlElement(name = "PicCall1Result")
    protected String picCall1Result;

    /**
     * Gets the value of the picCall1Result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPicCall1Result() {
        return picCall1Result;
    }

    /**
     * Sets the value of the picCall1Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicCall1Result(String value) {
        this.picCall1Result = value;
    }

}
