package com.dyst.vehicleQuery.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dyst.utils.CommonUtils;
import com.dyst.utils.Config;
import com.dyst.vehicleQuery.dto.SbDto;
import com.dyst.vehicleQuery.utils.XmlUtil;
import com.dyst.webservice.IInAccessServicePortType;

@Service("picService")
public class PicService {
	
	private static Logger logger = LoggerFactory.getLogger(PicService.class);
	/**
	 * 批量查询图片
	 * @param list
	 */
	public void findPic(List<SbDto> dtoList){
		List<String> idList = new ArrayList<String>();
		//循环取出图片id
		for (SbDto dto:dtoList) {
			if(dto.getTp1() != null && !"".equals(dto.getTp1())){
				idList.add(dto.getTp1());
			}
			if(dto.getTp2() != null && !"".equals(dto.getTp2())){
				idList.add(dto.getTp2());
			}
		}
		//查询图片
		List<String> urlList = getPicUrl(idList);
		if(urlList != null && urlList.size() > 0){
			for (SbDto dto:dtoList) {
				addUrlToSb(urlList, dto);
			}
		}
	}
	/**
	 * 将查询到的图片添加到实体类中
	 * @param list
	 * @param dto
	 */
	public void addUrlToSb(List<String> list,SbDto dto){
		if(dto.getTp1() != null && !"".equals(dto.getTp1())){
			for (String string : list) {
				String tpid = dto.getTp1();
				if(tpid.indexOf("pic") != -1){//hk云，海康云图片的特殊字符需要去掉
					tpid = changeSpecialSign(tpid);
				}
				if(string.contains(tpid)){
					dto.setTp1Url(string);
					break;
				}
			}
		}
		if(dto.getTp2() != null && !"".equals(dto.getTp2())){
			for (String string : list) {
				String tpid = dto.getTp2();
				if(tpid.indexOf("pic") != -1){//hk云，海康云图片的特殊字符需要去掉
					tpid = changeSpecialSign(tpid);
				}
				if(string.contains(tpid)){
					dto.setTp2Url(string);
					break;
				}
			}
		}
	}
	
	/**
	 * 获取图片方法
	 * @param tpidList 图片id集合
	 * @param ip	
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getPicUrl(List<String> tpidList){
		if (tpidList == null || tpidList.size() < 1) {
			return null;
		}
		List<String> tpurlList = new ArrayList<String>();
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='gb2312'?><root><head/><body>");
		for(String tpid:tpidList){
			sb.append("<tpid>").append(tpid).append("</tpid>");
		}
		sb.append("</body></root>");
		String reqxml = getPic(sb.toString());
		if(StringUtils.isEmpty(reqxml)){
			return null;
		}
		Document document = XmlUtil.getDocument(reqxml);
		if(document != null){
			Element body = document.getRootElement().element("body");
			if(body != null){
				List<Element> eleList = body.elements();
				for (Element element : eleList) {
					if(StringUtils.isNotEmpty(element.getTextTrim())){
						tpurlList.add(element.getTextTrim());
					}
				}
			}
		}
		return tpurlList;
	}
	/**
	 * 根据图片id获取图片路径20140506155135723060670941
	 * @return 相应图片路径
	 */
	private static String getPic(String xml) {
		String result = null;
		String resultXml = null;
		try {
			//连接
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(IInAccessServicePortType.class);
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.setAddress(Config.getInstance().getSbPicWebservice());
			IInAccessServicePortType service = (IInAccessServicePortType) factory.create();
			CommonUtils.setCxfTimeOut(service, 10, 45);//超时设置
			
			//调用服务
			resultXml = service.executes("01", "02", "hello,world", "0", xml);
			
			//深圳交警，如果为公安网访问，则转换ip
			if(resultXml != null){
				String dzys1 = Config.getInstance().getDzys1();
				if(StringUtils.isNotEmpty(dzys1)){
					String[] dzys = dzys1.split(":");
					result = ((String)resultXml).replaceAll(dzys[0], dzys[1]);
				} else{
					result = (String)resultXml;
				}
			}
		} catch (Exception e1) {
			logger.error("调用接口查询图片失败", e1);
			e1.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据图片id获取图片路径
	 * 
	 * @return 相应图片路径 单张图片
	 */
	public static String getPicByTpid(String tpid) {
		Document document = null;
		String pic = "";
		try {
			if(tpid != null && !"".equals(tpid)){
				//连接
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(IInAccessServicePortType.class);
				factory.getOutInterceptors().add(new LoggingOutInterceptor());
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.setAddress(Config.getInstance().getSbPicWebservice());
				IInAccessServicePortType service = (IInAccessServicePortType) factory.create();
				//封装
				String str_xml = "<?xml version='1.0' encoding='gb2312'?><root><head/><body><tpid>" + tpid + "</tpid></body></root>";
				//调用服务
				String resultXml = service.executes("01", "02", "hello,world", "0", str_xml);
				//解析
				document = (Document) DocumentHelper.parseText(resultXml);// 字符串转换为document文件。
				Element root = document.getRootElement();
				Element body = (Element) root.selectNodes("body").get(0);
				if(body.element("path") != null && !"".equals(body.element("path"))){
					pic = body.element("path").getText();
					
					//地址转换
					String dzys1 = Config.getInstance().getDzys1();
					if(StringUtils.isNotEmpty(dzys1)){
						String[] dzys = dzys1.split(":");
						pic = pic.replace(dzys[0], dzys[1]);
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return pic;
	}
	
	/**
	 * 转换特殊字符
	 * @return
	 */
	public static String changeSpecialSign(String tpid) {
		String[] specialSignArr = new String[]{"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};
		for(int i=0;i < specialSignArr.length;i++){
			while(tpid.indexOf(specialSignArr[i]) != -1){
				tpid = tpid.replace(specialSignArr[i], "-");
			}
		}
		return tpid;
	}
}