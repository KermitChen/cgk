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
	 * 更新识别表记录
	 * @param xml
	 * @return
	 */
	public String updateSb(String xml){
		Document document = null;
		XmlCreater xmlcreate = new XmlCreater();
		Config config = Config.getInstance();//配置信息类
		
		//-----------1------------------获取参数-------------------------
		String cphid = null;
		String cplx = null;
		String gcxh = null;
		String sbsj = null;
		try {
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Element body = (Element) root.selectNodes("body").get(0);
			Element data = (Element) body.selectNodes("data").get(0);
			
			cphid = data.element("hphm").getText();//号牌号码
			cplx = data.element("cplx").getText();//车牌类型
			gcxh = data.element("tpid").getText();//过车序号
			sbsj = data.element("sbsj").getText();//识别时间
		} catch (Exception e) {
			e.printStackTrace();
			return xmlcreate.createErrorXml(config.getErrorCode09());//"09:xml文件格式无法解析，请检查！"
		}
		
		//-----------2-------------------------------------------
		//判断给定时间，查询Oracle数据库还是ES库
		Date midDate = null;//oracle与es分解时间
		Date sbDate = null;//识别记录时间
		SimpleDateFormat dfsb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//---中间时间---
			midDate = df.parse(InterUtil.getTime(Integer.parseInt(config.getBeforeDate())));
		
			//---识别记录时间---
			try {
				sbDate = dfsb.parse(sbsj);
			} catch (ParseException e) {
				return xmlcreate.createErrorXml(config.getErrorCode03());//"03:时间格式不合法或者为空，请检查！"
			}
			
			//根据时间前后，更新ES库还是Oracle库
			if(sbDate.before(midDate)){
			    try {
			    	ESsearcherFilter.updateEsSb(gcxh, cphid, cplx);
					return xmlcreate.createUpdateXml("更新记录成功");
				} catch (Exception e) {
					e.printStackTrace();
					return xmlcreate.createErrorXml(config.getErrorCode13());
				}
			}else{
			    try {
			    	OracleSearch.updateOracleSb(gcxh, cphid, cplx);
					return xmlcreate.createUpdateXml("更新记录成功");
				} catch (Exception e) {
					return xmlcreate.createErrorXml(config.getErrorCode13());
				}
			}
		} catch (ParseException e) {
			return xmlcreate.createErrorXml(config.getErrorCode12());
		}
	}
}
