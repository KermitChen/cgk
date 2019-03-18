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
	 * �켣ͼƬ����
	 * @param xml           XML������
	 * @param ���ؼ�¼ 
	 */
	@SuppressWarnings("unchecked")
	public String tpcx(String xml,String businessType) {
		//������Ϣ��
		Config config = Config.getInstance();
		
		//xml��װ
		if(xmlcreate == null){
			xmlcreate = new XmlCreater();
		}
		
		List<String> listPic = new ArrayList<String>();//ͼƬ��ַ����
		try {
			Document document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Element body = (Element) root.selectNodes("body").get(0);
			List<Element> listTpid = body.selectNodes("tpid");//ͼƬid����
			
			//ͼƬidΪ��
			if(listTpid == null || listTpid.size() == 0){
				//"10:����ͼƬid�޷�������"
				return xmlcreate.createErrorXml(config.getErrorCode10());
			}
			
			//ͼƬid
			String tpid = ((Element) listTpid.get(0)).getText();
			//����Ϊ��
			if(tpid == null || "".equals(tpid)){
				//"10:����ͼƬid�޷�������"
				return xmlcreate.createErrorXml(config.getErrorCode10());
			}
			
			//�ж�ͼƬid�Ƿ���pic��ͷ������ǣ����ȡ�����ƴ洢
			String base64 = "";
			if(tpid.startsWith("pic")){
				base64 = CopyPicFromServer.copyPicReturnPathOfHk(tpid);//�����ƴ洢
			}
			
			//�����б�
			listPic.add(base64);
		} catch (Exception e) {
			//"09����XML�ļ������쳣
			return xmlcreate.createErrorXml(config.getErrorCode09());
		}
		
		//�ȴ��߳���ִ�����
		return xmlcreate.createPicPath(listPic);
	}
}