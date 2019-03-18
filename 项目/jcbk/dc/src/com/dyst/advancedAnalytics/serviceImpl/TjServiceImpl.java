package com.dyst.advancedAnalytics.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import jodd.datetime.JDateTime;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.dyst.advancedAnalytics.dto.FootHoldDetail;
import com.dyst.advancedAnalytics.dto.FootHoldResult;
import com.dyst.advancedAnalytics.service.TjService;
import com.dyst.utils.Config;
import com.dyst.vehicleQuery.dto.ReqArgs;
import com.dyst.vehicleQuery.dto.SbDto;
import com.dyst.vehicleQuery.service.SearchService;
import com.dyst.webservice.IInAccessServicePortType;
@Service("tjService")
public class TjServiceImpl implements TjService {

	@Resource
	private SearchService searchService;
	
	@Override
	public List<FootHoldResult> footHold(String cphm, String kssj, String jssj)
			throws Exception {
		List<FootHoldResult> list = new ArrayList<FootHoldResult>();
		FootHoldResult footHold = null;
		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>" +
				"<head>" +
				"</head><body><data>" +
				"<cphm>" + cphm + "</cphm>" +
				"<kssj>" + kssj + "</kssj>" +
				"<jssj>" + jssj + "</jssj>" +
				"</data></body></root>";
	   	String dataType = "101";
	   	
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
				for (int i = 0; i < data.size(); i++) {
					Element e = (Element)data.get(i);
					footHold = new FootHoldResult();
					footHold.setHphm(cphm);
					footHold.setJcdid(((Element)e.selectNodes("key").get(0)).getText());
					footHold.setTimes(Integer.parseInt(((Element)e.selectNodes("doc_count").get(0)).getText()));
					footHold.setTotal(total);
					list.add(footHold);
				}
			}
		}
		return list;
	}

	@Override
	public List<FootHoldDetail> footHoldDetail(String hphm, String kssj,
			String jssj, String jcdid) throws Exception {
		List<FootHoldDetail> resList = new ArrayList<FootHoldDetail>();
		int _0_3 = 0;
		int _3_6 = 0;
		int _6_9 = 0;
		int _9_12 = 0;
		int _12_15 = 0;
		int _15_18 = 0;
		int _18_21 = 0;
		int _21_0 = 0;
		List<SbDto> list = searchService.findSomeVehicleQuery(new ReqArgs(kssj+" 00:00:00", jssj+" 23:59:59", hphm, jcdid, 10000, 0, "", ""));
		if (list != null && list.size() > 0) {
			for (SbDto sbDto : list) {
				int hourTemp = new JDateTime(sbDto.getSbsj(),"YYYY-MM-DD hh:mm:ss").getHour();
				if(hourTemp < 3){
					_0_3++;
				}else if(hourTemp >= 3 && hourTemp < 6){
					_3_6++;
				}else if(hourTemp >= 6 && hourTemp < 9){
					_6_9++;
				}else if(hourTemp >= 9 && hourTemp < 12){
					_9_12++;
				}else if(hourTemp >= 12 && hourTemp < 15){
					_12_15++;
				}else if(hourTemp >= 15 && hourTemp < 18){
					_15_18++;
				}else if(hourTemp >= 18 && hourTemp < 21){
					_18_21++;
				}else if(hourTemp >= 21){
					_21_0++;
				}
			}
			
			if(_0_3 != 0){
				resList.add(new FootHoldDetail("00:00-03:00", jcdid, _0_3, list.size()));
			}
			if(_3_6 != 0){
				resList.add(new FootHoldDetail("03:00-06:00", jcdid, _3_6, list.size()));
			}
			if(_6_9 != 0){
				resList.add(new FootHoldDetail("06:00-09:00", jcdid, _6_9, list.size()));
			}
			if(_9_12 != 0){
				resList.add(new FootHoldDetail("09:00-12:00", jcdid, _9_12, list.size()));
			}
			if(_12_15 != 0){
				resList.add(new FootHoldDetail("12:00-15:00", jcdid, _12_15, list.size()));
			}
			if(_15_18 != 0){
				resList.add(new FootHoldDetail("15:00-18:00", jcdid, _15_18, list.size()));
			}
			if(_18_21 != 0){
				resList.add(new FootHoldDetail("18:00-21:00", jcdid, _18_21, list.size()));
			}
			if(_21_0 != 0){
				resList.add(new FootHoldDetail("21:00-00:00", jcdid, _21_0, list.size()));
			}
		}
		return resList;
	}
}
