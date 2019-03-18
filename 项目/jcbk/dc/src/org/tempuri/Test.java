package org.tempuri;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.tempuri.ServiceSoap;

/**
 * 交警机动车信息
 */
public class Test {
	public static void main(String[] args) {
		try {
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(ServiceSoap.class);
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.setAddress("http://100.100.36.201:8080/GdCjg/Service.asmx");
			
			ServiceSoap service = (ServiceSoap) factory.create();
			
			javax.xml.ws.Holder<java.lang.Integer> a = new javax.xml.ws.Holder<java.lang.Integer>();
			javax.xml.ws.Holder<java.lang.String> b = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> bb = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> c = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> d = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> e = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> f = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> g = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> h = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> i = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> j = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> k = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> l = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> m = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> n = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> o = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> p = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> q = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> r = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> s = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> t = new javax.xml.ws.Holder<java.lang.String>();
			service.myDoor("sunlight.acsl", "B12345", "02", a, bb, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
