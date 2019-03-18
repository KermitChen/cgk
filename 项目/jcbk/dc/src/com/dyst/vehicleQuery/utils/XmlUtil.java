package com.dyst.vehicleQuery.utils;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;


public class XmlUtil {
	
	private static Logger logger = Logger.getLogger(XmlUtil.class);


	/**
	 * 返回解析指定xml字符串后的document，若出现解析错误，则返回null
	 * @param xml
	 * @return
	 */
	public static Document getDocument(String xml){
		Document document = null;
		try {
			document = (Document) DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			logger.error(" 返回解析指定xml字符串后的document：", e);
		}
		return document;
	}
	
	/**
	 * 取出node的文本，若为null，则返回""
	 * @param node
	 * @return
	 */
	public static String getNodeText(Node node){
		if(node == null){
			return "";
		} 
		return node.getText();
	}
}
