package com.dyst.utils;

import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlCreater {
	/**
	 * 查询失败，返回指定错误的xml文件
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createErrorXml(String message){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		// 添加head元素
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");

		el_success.setText("0");//失败
		el_count.setText("0");
		el_message.setText(message);//失败信息描述
		String str_xml = doc.asXML();// 转换成字符串
		return str_xml;
	}
	
	/**
	 * 图片查询，返回的xml文件
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createPicPath(List<String> listPic){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		// 添加head元素
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_rowdata1.addElement("body");

		el_success.setText("1");//失败
		el_message.setText("图片地址查询成功");//
		if(listPic == null || listPic.size() == 0){//集合为空
			el_count.setText("0");
			return doc.asXML();// 转换成字符串
		}
		
		//如果获取到结果，则返回查询结果
		el_count.setText("" + listPic.size());//查询总数
		for (int j = 0;j < listPic.size();j++) {
			Element el_path = el_body.addElement("path");
	    	//赋值
			el_path.setText(listPic.get(j));
		}     
		String str_xml = doc.asXML();// 转换成字符串
		// System.out.println(str_xml);
		return str_xml;
	}
}