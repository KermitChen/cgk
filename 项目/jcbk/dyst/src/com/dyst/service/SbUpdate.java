package com.dyst.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dyst.elasticsearch.ESsearcherFilter;
import com.dyst.oracle.OracleSearch;
import com.dyst.utils.Config;
import com.dyst.utils.InterUtil;
import com.dyst.utils.XmlCreater;

public class SbUpdate {
	/**
	 * ����ʶ����¼
	 * @param xml
	 * @return
	 */
	public String updateSb(String xml){
		Document document = null;
		XmlCreater xmlcreate = new XmlCreater();
		Config config = Config.getInstance();//������Ϣ��
		
		//-----------1------------------��ȡ����-------------------------
		String cphid = null;
		String cplx = null;
		String gcxh = null;
		String sbsj = null;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);
			
			cphid = data.element("hphm").getText();//���ƺ���
			cplx = data.element("cplx").getText();//��������
			gcxh = data.element("tpid").getText();//�������
			sbsj = data.element("sbsj").getText();//ʶ��ʱ��
		} catch (Exception e) {
			e.printStackTrace();
			return xmlcreate.createErrorXml(config.getErrorCode09());//"09:xml�ļ���ʽ�޷����������飡"
		}
		
		//-----------2-------------------------------------------
		//�жϸ���ʱ�䣬��ѯOracle���ݿ⻹��ES��
		Date midDate = null;//oracle��es�ֽ�ʱ��
		Date sbDate = null;//ʶ���¼ʱ��
		SimpleDateFormat dfsb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//---�м�ʱ��---
			midDate = df.parse(InterUtil.getTime(Integer.parseInt(config.getBeforeDate())));
		
			//---ʶ���¼ʱ��---
			try {
				sbDate = dfsb.parse(sbsj);
			} catch (ParseException e) {
				return xmlcreate.createErrorXml(config.getErrorCode03());//"03:ʱ���ʽ���Ϸ�����Ϊ�գ����飡"
			}
			
			//����ʱ��ǰ�󣬸���ES�⻹��Oracle��
			if(sbDate.before(midDate)){
			    try {
			    	ESsearcherFilter.updateEsSb(gcxh, cphid, cplx);
					return xmlcreate.createUpdateXml("���¼�¼�ɹ�");
				} catch (Exception e) {
					e.printStackTrace();
					return xmlcreate.createErrorXml(config.getErrorCode13());
				}
			}else{
			    try {
			    	OracleSearch.updateOracleSb(gcxh, cphid, cplx);
					return xmlcreate.createUpdateXml("���¼�¼�ɹ�");
				} catch (Exception e) {
					return xmlcreate.createErrorXml(config.getErrorCode13());
				}
			}
		} catch (ParseException e) {
			return xmlcreate.createErrorXml(config.getErrorCode12());
		}
	}
}
