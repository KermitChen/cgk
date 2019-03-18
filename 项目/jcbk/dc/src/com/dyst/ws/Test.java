package com.dyst.ws;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.dyst.ws.InterfaceServicePortType;

/**
 * 短信接口调用方式
 *
 */
public class Test {
	public static void main(String[] args) {
		try {
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(InterfaceServicePortType.class);
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.setAddress("http://10.42.127.81:8989/interface/services/interfaceService");
			
			InterfaceServicePortType service = (InterfaceServicePortType) factory.create();
			 
			String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root><head></head><body><data><phones>15218722860</phones><content>测试！</content></data></body></root>";
			String xml = service.executes("hello world", "01", str_xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
