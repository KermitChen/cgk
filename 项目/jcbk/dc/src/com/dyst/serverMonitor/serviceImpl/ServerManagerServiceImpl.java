package com.dyst.serverMonitor.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dyst.serverMonitor.entities.ExcelBeanForJcdStatus;
import com.dyst.serverMonitor.service.ServerManagerService;
import com.dyst.utils.Config;
import com.dyst.utils.excel.ExportExcelUtil;
import com.dyst.webservice.IInAccessServicePortType;
@Service("serverManager")
public class ServerManagerServiceImpl implements ServerManagerService {

	/**
	 * 查询服务器信息
	 */
	@Override
	public List queryServerInfo(String hosts) throws Exception {
		List list = new ArrayList();
		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>" +
				"<head></head><body><data><hosts>" + hosts + "</hosts></data></body></root>";
	   	String dataType = "103";
	   	
	    //连接
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IInAccessServicePortType.class);
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.setAddress(Config.getInstance().getSbPicWebservice());
		IInAccessServicePortType service = (IInAccessServicePortType) factory.create();
		//调用服务
		String resultXml = service.executes("01", dataType, "hello,world", "1" ,str_xml);
		if(resultXml != null){
			Document document = null;
			document = (Document) DocumentHelper.parseText(resultXml);// String转化为XML
			Element root = document.getRootElement();
			Element head = (Element) root.selectNodes("head").get(0);
			String success = head.element("success").getText();// 是否查询成功
			if ("1".equals(success.trim())) {
				Element body = (Element) root.selectNodes("body").get(0);//实体数据
				List data = (List) body.selectNodes("data");
				for (Object object : data) {
					Element e = (Element)object;
					list.add(JSON.parse(e.getTextTrim()));
				}
			}
		}
		return list;
	}

	/**
	 * 设备状态导出excel
	 */
	@Override
	public void excelExportForDeviceStatus(
			List<ExcelBeanForJcdStatus> excelBeanList,
			ServletOutputStream outputStream) {
		ExportExcelUtil.excelExportForDeviceStatus(excelBeanList, outputStream);
	}
	
	
}