
package com.sunshine.monitor.system.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>queryVehPassrec complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="queryVehPassrec"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="queryConditions" type="{http://server.ws.system.monitor.sunshine.com/}QueryVehPassrecEntity" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryVehPassrec", propOrder = {
    "queryConditions"
})
public class QueryVehPassrec {

    protected QueryVehPassrecEntity queryConditions;

    /**
     * ��ȡqueryConditions���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link QueryVehPassrecEntity }
     *     
     */
    public QueryVehPassrecEntity getQueryConditions() {
        return queryConditions;
    }

    /**
     * ����queryConditions���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link QueryVehPassrecEntity }
     *     
     */
    public void setQueryConditions(QueryVehPassrecEntity value) {
        this.queryConditions = value;
    }

}
