
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
 *         &lt;element name="PicCall2Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "picCall2Result"
})
@XmlRootElement(name = "PicCall2Response")
public class PicCall2Response {

    @XmlElement(name = "PicCall2Result")
    protected String picCall2Result;

    /**
     * Gets the value of the picCall2Result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPicCall2Result() {
        return picCall2Result;
    }

    /**
     * Sets the value of the picCall2Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicCall2Result(String value) {
        this.picCall2Result = value;
    }

}
