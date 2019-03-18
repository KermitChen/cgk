package com.dyst.advancedAnalytics.serviceImpl;

import static com.dyst.advancedAnalytics.utils.StaticUtil.BS_BUS_FLAG;
import static com.dyst.advancedAnalytics.utils.StaticUtil.BS_TOPIC_NAME;
import static com.dyst.advancedAnalytics.utils.StaticUtil.DWPZ_BUS_FLAG;
import static com.dyst.advancedAnalytics.utils.StaticUtil.DWPZ_TOPIC_NAME;
import static com.dyst.advancedAnalytics.utils.StaticUtil.KAFKA_REQUEST_TOPIC_NAME;
import static com.dyst.advancedAnalytics.utils.StaticUtil.PFGC_BUS_FLAG;
import static com.dyst.advancedAnalytics.utils.StaticUtil.PFGC_TOPIC_NAME;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dyst.advancedAnalytics.dao.GjfxDao;
import com.dyst.advancedAnalytics.dto.BsResult;
import com.dyst.advancedAnalytics.dto.RequestParameters;
import com.dyst.advancedAnalytics.entities.ExcelBeanForDwpz;
import com.dyst.advancedAnalytics.service.GjfxService;
import com.dyst.advancedAnalytics.utils.CalculateUtil;
import com.dyst.base.utils.PageResult;
import com.dyst.kafka.service.KafkaService;
import com.dyst.utils.Config;
import com.dyst.utils.excel.ExportExcelUtil;
import com.dyst.webservice.IInAccessServicePortType;

@Service("gjfxService")
public class GjfxServiceImpl implements GjfxService{

	@Resource
	private KafkaService kafkaService;
	@Autowired
	private GjfxDao gjfxDao;
	

	/**
	 * 想kafka发送请求并得到查询标志
	 * @param parms
	 * @return
	 */
	public void requestKafka(RequestParameters params) throws Exception{
		params.setReqFlag(UUID.randomUUID().toString().replaceAll("\\-", ""));//用一个随机数作为计算标记
		kafkaService.sendMessage(KAFKA_REQUEST_TOPIC_NAME, JSON.toJSONString(params),params.getBusinessFlag());//发送消息
		String resultFlag = kafkaService.getMessage(params.getLoginName(), params.getResponseTopic(),params.getReqFlag());//注意设置topic
		if(StringUtils.isNotEmpty(resultFlag)){
			params.setResFlag(resultFlag);
		}
	}
	
	/**
	 * 伴随分析
	 * @param params	请求参数
	 * @param pageResult
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageResult bsAnalysis(RequestParameters params) throws Exception{
		//params.setResFlag("3ff5cde43d7e4aa6b777f82fb7f51f0f");
		
		Config config = Config.getInstance();
		long interval = config.getInterval();//判断是否为否为轨迹的时间标准
		int defaultTxyz = config.getDefaultTxyz();//默认同行阈值
		String resFlag = params.getResFlag();
		if(StringUtils.isEmpty(resFlag)){
			params.setResponseTopic(BS_TOPIC_NAME);//设置接收结果的topic名称
			params.setBusinessFlag(BS_BUS_FLAG);//设置业务标志
			requestKafka(params);//发送计算请求并接收查询标记
		}
		List<BsResult> resList = new ArrayList<BsResult>();
		Map<String, List> map = CalculateUtil.groupByCphm(gjfxDao.getBsSb(params.getResFlag(), ""));
		if(!map.isEmpty()){
			int txyz = StringUtils.isEmpty(params.getTxyz().trim())?defaultTxyz:Integer.parseInt(params.getTxyz().trim());//同行阈值
			int temp = 0;
			for(Map.Entry<String, List> entry:map.entrySet()){
				Collections.sort(entry.getValue());
				//统计单个车的伴随轨迹的监测点总个数
				temp = CalculateUtil.countJcd(entry.getValue(), interval,txyz);
				if(temp > 0 ){//监测点个数大于0
					resList.add(new BsResult(entry.getKey(), temp, params.getResFlag()));
					temp = 0;
				}
			}
		}
		if(resList.size() < 1){
			return new PageResult("无计算结果!");
		}
		Collections.sort(resList);
		return new PageResult(resList.size(), 1, 10, resList);
	}
	/**
	 * 获取伴随路径
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getBsPath(String hphm, String resFlag,String txyz) throws Exception {
		Config config = Config.getInstance();
		long interval = config.getInterval();
		int txyzInt = StringUtils.isEmpty(txyz)?config.getDefaultTxyz():Integer.parseInt(txyz.trim());//同行阈值
		Map<String, List> map = CalculateUtil.groupByCphm(gjfxDao.getBsSb(resFlag, hphm));
		List resList = null;
		if(!map.isEmpty()){
			for(Map.Entry<String, List> entry:map.entrySet()){
				Collections.sort(entry.getValue());
				resList = CalculateUtil.matchPath(entry.getValue(), interval, txyzInt);
			}
		}
		return resList;
	}
	
	/**
	 * 多维碰撞分析
	 * @param parms
	 * @param pageResult
	 * @return
	 */
	public PageResult dwpzAnalysis(RequestParameters params,int pageNo,int pageSize) throws Exception{
		String resFlag = params.getResFlag();
		if(StringUtils.isEmpty(resFlag)){
			params.setResponseTopic(DWPZ_TOPIC_NAME);//设置接收结果的topic名称
			params.setBusinessFlag(DWPZ_BUS_FLAG);
			requestKafka(params);//发送计算请求并接收查询标记
		}
		if(StringUtils.isEmpty(params.getResFlag())){
			return new PageResult("等待计算结果超时,计算失败!");
		}
		PageResult pageResult = gjfxDao.getResForPage("ResDwpz",params.getResFlag(),null,null, pageNo,pageSize);
		pageResult.setDwpzQueryFlag(params.getResFlag());
		return pageResult;
	}	
	
	/**
	 * 多维碰撞导出到excel
	 */
	@Override
	public void excelExportForDwpz(ExcelBeanForDwpz excelBean,
			ServletOutputStream outputStream) {
		try {
			ExportExcelUtil.excelExportForDwpz(excelBean,outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 频繁过车分析
	 * @param parms
	 * @param pageResult
	 * @return
	 */
	public PageResult pfgcAnalysis(RequestParameters params, int pageNo, int pageSize) throws Exception{
		String resFlag = params.getResFlag();
		if(StringUtils.isEmpty(resFlag)){
			params.setResponseTopic(PFGC_TOPIC_NAME);//设置接收结果的topic名称
			params.setBusinessFlag(PFGC_BUS_FLAG);
			requestKafka(params);//发送计算请求并接收查询标记
		}
		if(StringUtils.isEmpty(params.getResFlag())){
			return new PageResult("分析失败!");
		}
		return gjfxDao.getPfgcResForPage(params.getResFlag(), pageNo,pageSize);
	}
	/**
	 * 初次入城车辆查询
	 * @param parms
	 * @param pageResult
	 * @return
	 */
	public PageResult ccrcAnalysis(RequestParameters params,int pageNo,int pageSize) throws Exception{
		List list = new ArrayList();
		if(pageNo < 1){
			pageNo = 1;
		}
		long totalCount = 0;
		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
				+ "<head>" +
				"<pagesize>"+pageSize+"</pagesize>" +
				"<from>"+((pageNo-1)*pageSize)+"</from>" +
				"<sort></sort><sortType>DESC</sortType></head><body><data>"
				+ "<cplx>" + params.getCplx() + "</cplx>"
				+ "<kssj>" + params.getKssj() + "</kssj>" 
				+ "<jssj>" + params.getJssj() + "</jssj>"
				+ "<cphm>" + params.getHphm() + "</cphm>"
				+ "</data></body></root>";
	   	String dataType = "105";
	   	
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
			String count = head.elementTextTrim("count");
			if(StringUtils.isNotEmpty(count)){
				totalCount = Long.parseLong(count);
			}
			if ("1".equals(success.trim())) {
				Element body = (Element) root.selectNodes("body").get(0);//实体数据
				List data = (List) body.selectNodes("data");
				JSONObject jsonObj = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (Object object : data) {
					Element e = (Element)object;
					jsonObj = JSON.parseObject(e.getTextTrim());
					jsonObj.put("tgsj_str", sdf.format(new Date((Long) jsonObj.get("tgsj"))));
					list.add(jsonObj);
				}
			}
		}
		if(list.size() < 1){
			return new PageResult("未查询到结果!");
		}
		return new PageResult(totalCount, pageNo, pageSize, list);
	}
	/**
	 * 昼伏夜出分析
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageResult zfycAnalysis(RequestParameters params, int pageNo,
			int pageSize) throws Exception {
		List list = new ArrayList();
		if(pageNo < 1){
			pageNo = 1;
		}
		long totalCount = 0;
		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
				+ "<head>" +
				"<pagesize>1000</pagesize>" +
				"<from></from>" +
				"<sort>yccs</sort><sortType>DESC</sortType></head><body><data>"
				+ "<cqid></cqid>"
				+ "<kssj>" + params.getKssj() + "</kssj>" 
				+ "<jssj>" + params.getJssj() + "</jssj>"
				+ "<cphm>" + params.getHphm() + "</cphm>"
				+ "<zybl>" + params.getZybl() + "</zybl>"
				+ "</data></body></root>";
	   	String dataType = "104";
	   	
	    //连接
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IInAccessServicePortType.class);
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.setAddress(Config.getInstance().getSbPicWebservice());
		IInAccessServicePortType service = (IInAccessServicePortType) factory.create();
		//调用服务
		String resultXml = service.executes("01", dataType, "hello,world", "1" ,str_xml);
		if(resultXml != null){
			Document document = null;
			document = (Document) DocumentHelper.parseText(resultXml);// String转化为XML
			Element root = document.getRootElement();
			Element head = (Element) root.selectNodes("head").get(0);
			String success = head.element("success").getText();// 是否查询成功
			String count = head.elementTextTrim("count");
			if(StringUtils.isNotEmpty(count)){
				totalCount = Long.parseLong(count);
			}
			if ("1".equals(success.trim())) {
				Element body = (Element) root.selectNodes("body").get(0);//实体数据
				List data = (List) body.selectNodes("data");
				for (Object object : data) {
					Element e = (Element)object;
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("cphm", ((Element)e.selectNodes("cphm").get(0)).getTextTrim());
					jsonObj.put("zfcs", ((Element)e.selectNodes("zfcs").get(0)).getTextTrim());
					jsonObj.put("yccs", ((Element)e.selectNodes("yccs").get(0)).getTextTrim());
					list.add(jsonObj);
				}
			}
		}
		if(list.size() < 1){
			return new PageResult("未查询到结果!");
		}
		return new PageResult(totalCount, pageNo, pageSize, list);
	}
	

	@Override
	public List getBkCphm(String cphm) throws Exception {
		return gjfxDao.getBkCphm(cphm);
	}


	
}
