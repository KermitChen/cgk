package com.dyst.trafficAnalysis.serviceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.codehaus.xfire.client.Client;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.dyst.trafficAnalysis.service.RealFlowService;
import com.dyst.utils.Config;
import com.dyst.webservice.IInAccessServicePortType;

@Service("realFlowService")
public class RealFlowServiceImpl implements RealFlowService{

	@Override
	public Map<String, Object> getChartData(String beginTime,String endTime,String jcd,String interval)  throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>" +
				"<head>" +
				"</head><body><data>" +
				"<beginTime>" + beginTime + "</beginTime>" +
				"<endTime>" + endTime + "</endTime>" +
				"<jcd>" + jcd + "</jcd>" +
				"<interval>" + interval + "</interval>" +
				"</data></body></root>";
	   	String dataType = "102";
	   	
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
			if ("1".equals(success.trim())) {
				Element body = (Element) root.selectNodes("body").get(0);//实体数据
				List data = (List) body.selectNodes("data");
				List<String> legend = new ArrayList<String>();
				
				List<String> timeList = new ArrayList<String>();
				List<Integer> l1 = new ArrayList<Integer>();
				List<Integer> l2 = new ArrayList<Integer>();
				List<Integer> l3 = new ArrayList<Integer>();
				List<Integer> l4 = new ArrayList<Integer>();
				List<Integer> l5 = new ArrayList<Integer>();
				List<Integer> l6 = new ArrayList<Integer>();
				List<Integer> l7 = new ArrayList<Integer>();
				List<Integer> l8 = new ArrayList<Integer>();
				for (int i = 0; i < data.size(); i++) {
					Element e = (Element)data.get(i);
					//车道
					String cd = ((Element)e.selectNodes("cd").get(0)).getText();
					if(cd != null && !"".equals(cd.trim())){
						List childData = e.selectNodes("childData");
						for (int j = 0; j < childData.size(); j++) {
							Element ee = (Element)childData.get(j);
							
							switch (Integer.parseInt(cd)) {
							case 1:
								timeList.add(((Element)ee.selectNodes("time").get(0)).getText().substring(11));
								l1.add((int)Float.parseFloat(((Element)ee.selectNodes("num").get(0)).getText()));
								break;
							case 2:
								l2.add((int)Float.parseFloat(((Element)ee.selectNodes("num").get(0)).getText()));
								break;
							case 3:
								l3.add((int)Float.parseFloat(((Element)ee.selectNodes("num").get(0)).getText()));
								break;
							case 4:
								l4.add((int)Float.parseFloat(((Element)ee.selectNodes("num").get(0)).getText()));
								break;
							case 5:
								l5.add((int)Float.parseFloat(((Element)ee.selectNodes("num").get(0)).getText()));
								break;
							case 6:
								l6.add((int)Float.parseFloat(((Element)ee.selectNodes("num").get(0)).getText()));
								break;
							case 7:
								l7.add((int)Float.parseFloat(((Element)ee.selectNodes("num").get(0)).getText()));
								break;
							case 8:
								l8.add((int)Float.parseFloat(((Element)ee.selectNodes("num").get(0)).getText()));
								break;
							default:
								break;
							}
						}
					}
				}
				map.put("date", timeList);
				if(l1.size() > 0){
					map.put("data1", l1);
					legend.add("车道一");
				}
				if(l2.size() > 0){
					map.put("data2", l2);
					legend.add("车道二");
				}
				if(l3.size() > 0){
					map.put("data3", l3);
					legend.add("车道三");
				}
				if(l4.size() > 0){
					map.put("data4", l4);
					legend.add("车道四");
				}
				if(l5.size() > 0){
					map.put("data5", l5);
					legend.add("车道五");
				}
				if(l6.size() > 0){
					map.put("data6", l6);
					legend.add("车道六");
				}
				if(l7.size() > 0){
					map.put("data7", l7);
					legend.add("车道七");
				}
				if(l8.size() > 0){
					map.put("data8", l8);
					legend.add("车道八");
				}
				map.put("legend", legend);
			}
		}
		return map;
	}

}
