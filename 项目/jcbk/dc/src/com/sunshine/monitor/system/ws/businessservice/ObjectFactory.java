
package com.sunshine.monitor.system.ws.businessservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sunshine.monitor.system.ws.businessservice package. 
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

    private final static QName _Executes_QNAME = new QName("http://businessService.ws.system.monitor.sunshine.com/", "executes");
    private final static QName _FeedBack_QNAME = new QName("http://businessService.ws.system.monitor.sunshine.com/", "feedBack");
    private final static QName _Execute_QNAME = new QName("http://businessService.ws.system.monitor.sunshine.com/", "execute");
    private final static QName _FeedBackResponse_QNAME = new QName("http://businessService.ws.system.monitor.sunshine.com/", "feedBackResponse");
    private final static QName _ExecutesResponse_QNAME = new QName("http://businessService.ws.system.monitor.sunshine.com/", "executesResponse");
    private final static QName _ExecuteResponse_QNAME = new QName("http://businessService.ws.system.monitor.sunshine.com/", "executeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sunshine.monitor.system.ws.businessservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FeedBack }
     * 
     */
    public FeedBack createFeedBack() {
        return new FeedBack();
    }

    /**
     * Create an instance of {@link Executes }
     * 
     */
    public Executes createExecutes() {
        return new Executes();
    }

    /**
     * Create an instance of {@link Execute }
     * 
     */
    public Execute createExecute() {
        return new Execute();
    }

    /**
     * Create an instance of {@link FeedBackResponse }
     * 
     */
    public FeedBackResponse createFeedBackResponse() {
        return new FeedBackResponse();
    }

    /**
     * Create an instance of {@link ExecutesResponse }
     * 
     */
    public ExecutesResponse createExecutesResponse() {
        return new ExecutesResponse();
    }

    /**
     * Create an instance of {@link ExecuteResponse }
     * 
     */
    public ExecuteResponse createExecuteResponse() {
        return new ExecuteResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Executes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://businessService.ws.system.monitor.sunshine.com/", name = "executes")
    public JAXBElement<Executes> createExecutes(Executes value) {
        return new JAXBElement<Executes>(_Executes_QNAME, Executes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FeedBack }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://businessService.ws.system.monitor.sunshine.com/", name = "feedBack")
    public JAXBElement<FeedBack> createFeedBack(FeedBack value) {
        return new JAXBElement<FeedBack>(_FeedBack_QNAME, FeedBack.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Execute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://businessService.ws.system.monitor.sunshine.com/", name = "execute")
    public JAXBElement<Execute> createExecute(Execute value) {
        return new JAXBElement<Execute>(_Execute_QNAME, Execute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FeedBackResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://businessService.ws.system.monitor.sunshine.com/", name = "feedBackResponse")
    public JAXBElement<FeedBackResponse> createFeedBackResponse(FeedBackResponse value) {
        return new JAXBElement<FeedBackResponse>(_FeedBackResponse_QNAME, FeedBackResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecutesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://businessService.ws.system.monitor.sunshine.com/", name = "executesResponse")
    public JAXBElement<ExecutesResponse> createExecutesResponse(ExecutesResponse value) {
        return new JAXBElement<ExecutesResponse>(_ExecutesResponse_QNAME, ExecutesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://businessService.ws.system.monitor.sunshine.com/", name = "executeResponse")
    public JAXBElement<ExecuteResponse> createExecuteResponse(ExecuteResponse value) {
        return new JAXBElement<ExecuteResponse>(_ExecuteResponse_QNAME, ExecuteResponse.class, null, value);
    }

}
