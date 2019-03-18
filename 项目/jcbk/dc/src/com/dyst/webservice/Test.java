package com.dyst.webservice;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.dyst.webservice.IInAccessServicePortType;

/**
 * 轨迹接口调用方式
 *
 */
public class Test {
	public static void main(String[] args) {
		try {
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(IInAccessServicePortType.class);
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.setAddress("http://10.42.31.224:8080/dyst/services/InAccess");
			
			IInAccessServicePortType service = (IInAccessServicePortType) factory.create();
			 
			String str_xml = "<?xml version='1.0' encoding='gb2312'?><root><head/><body><tpid>20160722020042002050180311_40</tpid></body></root>";
			String xml = service.executes("01", "02", "hello,world", "0", str_xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
