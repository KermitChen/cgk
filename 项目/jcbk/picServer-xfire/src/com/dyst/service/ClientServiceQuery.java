package com.dyst.service;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.dyst.utils.Config;
import com.dyst.utils.CopyPicFromServer;
import com.dyst.utils.XmlCreater;

public class ClientServiceQuery {
	private XmlCreater xmlcreate = null;
	
	/**
	 * 轨迹图片方法
	 * @param xml           XML请求报文
	 * @param 返回记录 
	 */
	@SuppressWarnings("unchecked")
	public String tpcx(String xml,String businessType) {
		//配置信息类
		Config config = Config.getInstance();
		
		//xml封装
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		
		List<String> listPic = new ArrayList<String>();//图片地址集合
		try {
			Document document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Element body = (Element) root.selectNodes("body").get(0);
			List<Element> listTpid = body.selectNodes("tpid");//图片id集合
			
			//图片id为空
			if(listTpid == null || listTpid.size() == 0){
				//"10:给定图片id无法解析！"
				return xmlcreate.createErrorXml(config.getErrorCode10());
			}
			
			//图片id
			String tpid = ((Element) listTpid.get(0)).getText();
			//不能为空
			if(tpid == null || "".equals(tpid)){
				//"10:给定图片id无法解析！"
				return xmlcreate.createErrorXml(config.getErrorCode10());
			}
			
			//判断图片id是否以pic开头，如果是，则调取海康云存储
			String base64 = "";
			if(tpid.startsWith("pic")){
				base64 = CopyPicFromServer.copyPicReturnPathOfHk(tpid);//海康云存储
			}
			
			//放入列表
			listPic.add(base64);
		} catch (Exception e) {
			//"09解析XML文件出现异常
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		
		//等待线程组执行完成
		return xmlcreate.createPicPath(listPic);
	}
}