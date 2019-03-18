package com.dyst.log.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dyst.base.utils.PageResult;
import com.dyst.log.service.LogService;
import com.dyst.utils.Config;
import com.dyst.webservice.IInAccessServicePortType;

@Service("logService")
public class LogServiceImpl implements LogService{

	@Override
	public PageResult searchBusinessLog(int pageNo, int pageSize, String operator, 
			String operaterName, String operatorIp, String moduleName, String operateName, 
			String kssj, String jssj) throws Exception {
		List list = new ArrayList();
		if(pageNo < 1){
			pageNo = 1;
		}
		long totalCount = 0;
		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
				+ "<head>" +
				"<pagesize>"+pageSize+"</pagesize>" +
				"<from>"+((pageNo-1)*pageSize)+"</from>" +
				"<sort>operateDate</sort><sortType>DESC</sortType></head><body><data>"
				+ "<operator>" + (operator!=null?operator:"") + "</operator>" 
				+ "<operatorName>" + (operaterName!=null?operaterName:"") + "</operatorName>" 
				+ "<ip>" + (operatorIp!=null?operatorIp:"") + "</ip>" 
				+ "<moduleName>" + (moduleName!=null?moduleName:"") + "</moduleName>" 
				+ "<operateName>" + (operateName!=null?operateName:"") + "</operateName>" 
				+ "<kssj>" + kssj + "</kssj>" 
				+ "<jssj>" + jssj + "</jssj>"
				+ "</data></body></root>";
	   	String dataType = "106";
	   	
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
			String count = head.elementTextTrim("count");
			if(StringUtils.isNotEmpty(count)){
				totalCount = Long.parseLong(count);
			}
			if ("1".equals(success.trim())) {
				Element body = (Element) root.selectNodes("body").get(0);//实体数据
				List data = (List) body.selectNodes("data");
				JSONObject jsonObj = null;
				for (Object object : data) {
					Element e = (Element)object;
					jsonObj = JSON.parseObject(e.getTextTrim());
					list.add(jsonObj);
				}
			}
		}
		if(list.size() < 1){
			return new PageResult("未查询到结果!");
		}
		return new PageResult(totalCount, pageNo, pageSize, list);
	}

	@Override
	public String getBusinessLogById(String id) throws Exception {
		String result = null;
		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
				+ "<head>" +
				"<pagesize></pagesize>" +
				"<from></from>" +
				"<sort></sort><sortType>DESC</sortType></head><body><data>"
				+ "<id>" + id + "</id>"
				+ "</data></body></root>";
	   	String dataType = "107";
	   	
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
			String count = head.elementTextTrim("count");
			if ("1".equals(success.trim())) {
				Element body = (Element) root.selectNodes("body").get(0);//实体数据
				Element data =  (Element) body.selectNodes("data").get(0);
				result = data.getTextTrim();
			}
		}
		return result;
	}

}
