
package com.sunshine.monitor.system.ws.server;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sunshine.monitor.system.ws.server package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _QueryVehPassrec_QNAME = new QName("http://server.ws.system.monitor.sunshine.com/", "queryVehPassrec");
    private final static QName _QueryVehPassrecEntity_QNAME = new QName("http://server.ws.system.monitor.sunshine.com/", "queryVehPassrecEntity");
    private final static QName _QueryVehPassrecResponse_QNAME = new QName("http://server.ws.system.monitor.sunshine.com/", "queryVehPassrecResponse");
    private final static QName _VehPassrec_QNAME = new QName("http://server.ws.system.monitor.sunshine.com/", "vehPassrec");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sunshine.monitor.system.ws.server
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryVehPassrec }
     * 
     */
    public QueryVehPassrec createQueryVehPassrec() {
        return new QueryVehPassrec();
    }

    /**
     * Create an instance of {@link QueryVehPassrecEntity }
     * 
     */
    public QueryVehPassrecEntity createQueryVehPassrecEntity() {
        return new QueryVehPassrecEntity();
    }

    /**
     * Create an instance of {@link QueryVehPassrecResponse }
     * 
     */
    public QueryVehPassrecResponse createQueryVehPassrecResponse() {
        return new QueryVehPassrecResponse();
    }

    /**
     * Create an instance of {@link VehPassrec }
     * 
     */
    public VehPassrec createVehPassrec() {
        return new VehPassrec();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryVehPassrec }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ws.system.monitor.sunshine.com/", name = "queryVehPassrec")
    public JAXBElement<QueryVehPassrec> createQueryVehPassrec(QueryVehPassrec value) {
        return new JAXBElement<QueryVehPassrec>(_QueryVehPassrec_QNAME, QueryVehPassrec.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryVehPassrecEntity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ws.system.monitor.sunshine.com/", name = "queryVehPassrecEntity")
    public JAXBElement<QueryVehPassrecEntity> createQueryVehPassrecEntity(QueryVehPassrecEntity value) {
        return new JAXBElement<QueryVehPassrecEntity>(_QueryVehPassrecEntity_QNAME, QueryVehPassrecEntity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryVehPassrecResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ws.system.monitor.sunshine.com/", name = "queryVehPassrecResponse")
    public JAXBElement<QueryVehPassrecResponse> createQueryVehPassrecResponse(QueryVehPassrecResponse value) {
        return new JAXBElement<QueryVehPassrecResponse>(_QueryVehPassrecResponse_QNAME, QueryVehPassrecResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VehPassrec }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ws.system.monitor.sunshine.com/", name = "vehPassrec")
    public JAXBElement<VehPassrec> createVehPassrec(VehPassrec value) {
        return new JAXBElement<VehPassrec>(_VehPassrec_QNAME, VehPassrec.class, null, value);
    }

}
