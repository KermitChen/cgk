package com.dyst.utils;

import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlCreater {
	/**
	 * ��ѯʧ�ܣ�����ָ�������xml�ļ�
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createErrorXml(String message){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");

		el_success.setText("0");//ʧ��
		el_count.setText("0");
		el_message.setText(message);//ʧ����Ϣ����
		String str_xml = doc.asXML();// ת�����ַ���
		return str_xml;
	}
	
	/**
	 * ͼƬ��ѯ�����ص�xml�ļ�
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String createPicPath(List<String> listPic){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB2312");
		Element el_rowdata1 = doc.addElement("root");
		// ���headԪ��
		Element el_head = el_rowdata1.addElement("head");
		Element el_success = el_head.addElement("success");
		Element el_count = el_head.addElement("count");
		Element el_message = el_head.addElement("message");
		Element el_body = el_rowdata1.addElement("body");

		el_success.setText("1");//ʧ��
		el_message.setText("ͼƬ��ַ��ѯ�ɹ�");//
		if(listPic == null || listPic.size() == 0){//����Ϊ��
			el_count.setText("0");
			return doc.asXML();// ת�����ַ���
		}
		
		//�����ȡ��������򷵻ز�ѯ���
		el_count.setText("" + listPic.size());//��ѯ����
		for (int j = 0;j < listPic.size();j++) {
			Element el_path = el_body.addElement("path");
	    	//��ֵ
			el_path.setText(listPic.get(j));
		}     
		String str_xml = doc.asXML();// ת�����ַ���
		// System.out.println(str_xml);
		return str_xml;
	}
}