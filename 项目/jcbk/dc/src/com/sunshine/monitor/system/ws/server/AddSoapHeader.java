package com.sunshine.monitor.system.ws.server;

import java.util.List;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.XMLUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.namespace.QName;

public class AddSoapHeader extends AbstractSoapInterceptor {

	public AddSoapHeader() {
		// 指定该拦截器在哪个阶段被激发
		super(Phase.WRITE);
	}
	
	public void handleMessage(SoapMessage message) throws Fault {
		String sysid = "001";
		String user = "jcbk";
		String pass = "jcbk";

		QName qname = new QName("RequestSOAPHeader");//这个值暂时不清楚具体做什么用，可以随便写

		Document doc = (Document) DOMUtils.createDocument();
		Element eSysId = doc.createElement("sysid");
		eSysId.setTextContent(sysid);
		Element eUserId = doc.createElement("user");
		eUserId.setTextContent(user);
		Element ePwd = doc.createElement("pass");
		ePwd.setTextContent(pass);
		//
		Element child = doc.createElementNS("http://server.ws.system.monitor.sunshine.com/", "authority");
		child.appendChild(eSysId);
		child.appendChild(eUserId);
		child.appendChild(ePwd);
		//
		Element root = doc.createElement("soap:Header");
		root.appendChild(child);
		
		XMLUtils.printDOM(root);// 只是打印xml内容到控制台,可删除
		SoapHeader head = new SoapHeader(qname, root);
		List<Header> headers = message.getHeaders();
		headers.add(head);
	}
}
