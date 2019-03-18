
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
 *         &lt;element name="MyDoorResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="strFactBrand" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strModel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strVehicleTypeDetail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strColorDetail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strBodyNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strEngineNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strOwnerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strCertNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strTel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strZip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strFirstRegTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strLatestChkTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strChkValidTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strIssueCertTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strVehicleStatDetail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strDiscardTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strSteerPosDetail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strRet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "myDoorResult",
    "strFactBrand",
    "strModel",
    "strVehicleTypeDetail",
    "strColorDetail",
    "strBodyNo",
    "strEngineNo",
    "strOwnerName",
    "strCertNo",
    "strAddr",
    "strTel",
    "strZip",
    "strCity",
    "strFirstRegTime",
    "strLatestChkTime",
    "strChkValidTime",
    "strIssueCertTime",
    "strVehicleStatDetail",
    "strDiscardTime",
    "strSteerPosDetail",
    "strRet"
})
@XmlRootElement(name = "MyDoorResponse")
public class MyDoorResponse {

    @XmlElement(name = "MyDoorResult")
    protected int myDoorResult;
    protected String strFactBrand;
    protected String strModel;
    protected String strVehicleTypeDetail;
    protected String strColorDetail;
    protected String strBodyNo;
    protected String strEngineNo;
    protected String strOwnerName;
    protected String strCertNo;
    protected String strAddr;
    protected String strTel;
    protected String strZip;
    protected String strCity;
    protected String strFirstRegTime;
    protected String strLatestChkTime;
    protected String strChkValidTime;
    protected String strIssueCertTime;
    protected String strVehicleStatDetail;
    protected String strDiscardTime;
    protected String strSteerPosDetail;
    protected String strRet;

    /**
     * Gets the value of the myDoorResult property.
     * 
     */
    public int getMyDoorResult() {
        return myDoorResult;
    }

    /**
     * Sets the value of the myDoorResult property.
     * 
     */
    public void setMyDoorResult(int value) {
        this.myDoorResult = value;
    }

    /**
     * Gets the value of the strFactBrand property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrFactBrand() {
        return strFactBrand;
    }

    /**
     * Sets the value of the strFactBrand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrFactBrand(String value) {
        this.strFactBrand = value;
    }

    /**
     * Gets the value of the strModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrModel() {
        return strModel;
    }

    /**
     * Sets the value of the strModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrModel(String value) {
        this.strModel = value;
    }

    /**
     * Gets the value of the strVehicleTypeDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrVehicleTypeDetail() {
        return strVehicleTypeDetail;
    }

    /**
     * Sets the value of the strVehicleTypeDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrVehicleTypeDetail(String value) {
        this.strVehicleTypeDetail = value;
    }

    /**
     * Gets the value of the strColorDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrColorDetail() {
        return strColorDetail;
    }

    /**
     * Sets the value of the strColorDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrColorDetail(String value) {
        this.strColorDetail = value;
    }

    /**
     * Gets the value of the strBodyNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrBodyNo() {
        return strBodyNo;
    }

    /**
     * Sets the value of the strBodyNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrBodyNo(String value) {
        this.strBodyNo = value;
    }

    /**
     * Gets the value of the strEngineNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrEngineNo() {
        return strEngineNo;
    }

    /**
     * Sets the value of the strEngineNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrEngineNo(String value) {
        this.strEngineNo = value;
    }

    /**
     * Gets the value of the strOwnerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrOwnerName() {
        return strOwnerName;
    }

    /**
     * Sets the value of the strOwnerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrOwnerName(String value) {
        this.strOwnerName = value;
    }

    /**
     * Gets the value of the strCertNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrCertNo() {
        return strCertNo;
    }

    /**
     * Sets the value of the strCertNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrCertNo(String value) {
        this.strCertNo = value;
    }

    /**
     * Gets the value of the strAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrAddr() {
        return strAddr;
    }

    /**
     * Sets the value of the strAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrAddr(String value) {
        this.strAddr = value;
    }

    /**
     * Gets the value of the strTel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrTel() {
        return strTel;
    }

    /**
     * Sets the value of the strTel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrTel(String value) {
        this.strTel = value;
    }

    /**
     * Gets the value of the strZip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrZip() {
        return strZip;
    }

    /**
     * Sets the value of the strZip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrZip(String value) {
        this.strZip = value;
    }

    /**
     * Gets the value of the strCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrCity() {
        return strCity;
    }

    /**
     * Sets the value of the strCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrCity(String value) {
        this.strCity = value;
    }

    /**
     * Gets the value of the strFirstRegTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrFirstRegTime() {
        return strFirstRegTime;
    }

    /**
     * Sets the value of the strFirstRegTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrFirstRegTime(String value) {
        this.strFirstRegTime = value;
    }

    /**
     * Gets the value of the strLatestChkTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrLatestChkTime() {
        return strLatestChkTime;
    }

    /**
     * Sets the value of the strLatestChkTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrLatestChkTime(String value) {
        this.strLatestChkTime = value;
    }

    /**
     * Gets the value of the strChkValidTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrChkValidTime() {
        return strChkValidTime;
    }

    /**
     * Sets the value of the strChkValidTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrChkValidTime(String value) {
        this.strChkValidTime = value;
    }

    /**
     * Gets the value of the strIssueCertTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrIssueCertTime() {
        return strIssueCertTime;
    }

    /**
     * Sets the value of the strIssueCertTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrIssueCertTime(String value) {
        this.strIssueCertTime = value;
    }

    /**
     * Gets the value of the strVehicleStatDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrVehicleStatDetail() {
        return strVehicleStatDetail;
    }

    /**
     * Sets the value of the strVehicleStatDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrVehicleStatDetail(String value) {
        this.strVehicleStatDetail = value;
    }

    /**
     * Gets the value of the strDiscardTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrDiscardTime() {
        return strDiscardTime;
    }

    /**
     * Sets the value of the strDiscardTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrDiscardTime(String value) {
        this.strDiscardTime = value;
    }

    /**
     * Gets the value of the strSteerPosDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrSteerPosDetail() {
        return strSteerPosDetail;
    }

    /**
     * Sets the value of the strSteerPosDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrSteerPosDetail(String value) {
        this.strSteerPosDetail = value;
    }

    /**
     * Gets the value of the strRet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrRet() {
        return strRet;
    }

    /**
     * Sets the value of the strRet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrRet(String value) {
        this.strRet = value;
    }

}
