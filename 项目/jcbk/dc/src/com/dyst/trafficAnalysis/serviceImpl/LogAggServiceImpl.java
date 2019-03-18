package com.dyst.trafficAnalysis.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dyst.trafficAnalysis.service.LogAggService;
import com.dyst.utils.Config;
import com.dyst.webservice.IInAccessServicePortType;
@Service("logAggService")
public class LogAggServiceImpl implements LogAggService{

	@Override
	public List<JSONObject> tjBusinessLog(String kssj, String jssj, String ip,
			String operator, String operateName, String tjWord)
			throws Exception {
		List<JSONObject> list = null;
		int size = 100;
		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
				+ "<head><pagesize>"+size+"</pagesize></head><body>"
				+ "<data><kssj>"+kssj+"</kssj>"
				+ "<jssj>"+jssj+"</jssj>"
				+ "<tjWord>"+tjWord+"</tjWord>"
				+ "<ip>"+ip+"</ip>" +
				"<operator>"+operator+"</operator>" +
				"<operateName>"+operateName+"</operateName>"
				+ "</data></body></root>";
	String dataType = "108";
	   	
	    //连接
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IInAccessServicePortType.class);
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.setAddress(Config.getInstance().getSbPicWebservice());
		IInAccessServicePortType service = (IInAccessServicePortType) factory.create();
		//调用服务
		String resultXml = service.executes("01", dataType, "hello,world", "1" ,str_xml);
	   	//解析
		if(resultXml != null){
			Document document = null;
			document = (Document) DocumentHelper.parseText(resultXml);// String转化为XML
			Element root = document.getRootElement();
			Element head = (Element) root.selectNodes("head").get(0);
			String success = head.element("success").getText();// 是否查询成功
			int total = Integer.parseInt(head.element("count").getText());
			if ("1".equals(success.trim())) {
				Element body = (Element) root.selectNodes("body").get(0);//实体数据
				List data = (List) body.selectNodes("data");
				list = new ArrayList<JSONObject>();
				JSONObject obj = null;
				for (int i = 0; i < data.size(); i++) {
					Element e = (Element)data.get(i);
					obj = new JSONObject(); 
					obj.put("moduleName", ((Element)e.selectNodes("moduleName").get(0)).getText());
					obj.put("tjWord", ((Element)e.selectNodes("tjWord").get(0)).getText());
					obj.put("count", ((Element)e.selectNodes("count").get(0)).getText());
					list.add(obj);
				}
			}
		}
		return list;
	}

}
