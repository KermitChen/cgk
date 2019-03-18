package com.dyst.sjjk.serviceImpl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dyst.BaseDataMsg.dao.DictionaryDao;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.sjjk.entities.TjEnitity;
import com.dyst.sjjk.entities.YwtjEnitity;
import com.dyst.sjjk.service.SjjkService;
import com.dyst.systemmanage.entities.Department;
import com.dyst.utils.Config;
import com.dyst.utils.pushmsg.MyScriptSessionListener;
import com.dyst.utils.pushmsg.PushMessageUtil;
import com.dyst.webservice.IInAccessServicePortType;

@Service("sjjkService")
public class SjjkServiceImpl implements SjjkService{

	@Autowired
	private JcdService jcdService;
	
	@Autowired
	private DictionaryDao dictionaryDao;
	
	private static Map<String, String> jcdMap = new HashMap<String, String>();
	
	/**
	 * 往前端页面发送实时过车数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void sendGcToPage(String message, boolean updateFlag) throws Exception {
		//判断监测点信息是否为空，为空则需要获取
		if(jcdMap == null || jcdMap.isEmpty() || updateFlag){
			jcdMap = initJcdMap();
		}
		
		//解析数据
		//T001#0106#0200#2017-02-27 14:17:37#车牌号码1#车牌号码2#车牌类型1#车牌类型2#通过时间#监测点#车道#XXX#图片ID#XXX#XXX##监测点名称
		if(message != null && !"".equals(message.trim())){
			String[] data = message.split("#");
			if(data.length > 12){
				//翻译监测点
				String jcdmc = jcdMap.get(data[9]);
				if(jcdmc == null || "".equals(jcdmc.trim())){
					jcdmc = data[9];
				}
				
				//获取当前在实时过车数据页面的人员
				List<String> vehiclePeopleList = MyScriptSessionListener.getAllScriptSessionIds();
				List<String> userIds = new ArrayList<String>();
				for(String userId : vehiclePeopleList){
					if(userId != null && !"".equals(userId) && userId.startsWith("gcsjjk@")){//给过车数据监控人员推送数据
						userIds.add(userId);//添加该用户到推送名单
					}
				}
				//开始推送
				if(userIds != null && userIds.size() > 0){
					PushMessageUtil.sendMessageToMul(userIds, "showMessage", message + "#" + jcdmc);//推送
				}
			}
		}
	}
	
	/**
	 * 初始化监测点map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> initJcdMap() throws Exception{
		Map<String, String> jcdMap = new HashMap<String, String>();
		String hql = "select new map(j.id as id,j.jcdmc as name) from Jcd j";
		List<Object> list4 = jcdService.findObjects(hql, null);
		for (Object object : list4) {
			Map map = (Map) object;
			jcdMap.put((String)map.get("id"), (String)map.get("name"));
		}
		return jcdMap;
	}
	
	/**
	 * 根据条件获取已识别、未识别数据量
	 * @param jcdid   监测点Id
	 * @param cd      车道
	 * @param startTime    起始时间
	 * @param endTime      截止时间
	 */
	@SuppressWarnings("finally")
	public List<TjEnitity> getYsbWsbData(String jcdid, String cd, String startTime, 
			String endTime, Map<String, String> jcdMap){
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMinimumFractionDigits(2);
		List<TjEnitity> statLst = new ArrayList<TjEnitity>();
		Map<String, Integer> identifyMap = new HashMap<String, Integer>(); //识别数据
		Map<String, Integer> notIdentifyMap = new HashMap<String, Integer>(); //未识别数据
		
		//查询结果
		String result = "1";
		String [] jcdArr = null;
		try {
			//以逗号分隔jcdid字符串，组成数组
			jcdArr = jcdid.split(",");
			if(jcdArr.length > 400){//监测点大于400个，则分批查询
				//计算批次
				int n = (jcdArr.length%400)==0?(jcdArr.length/400):(jcdArr.length/400)+1;
				//循环查询
				for(int ij=0;ij < n;ij++){
					int startIndex = 400 * ij;//计算起始索引
					//一次最多400个监测点
					String jcdStr = "";
					for(int ci=0;ci < 400 && startIndex < jcdArr.length;ci++){
						jcdStr += jcdArr[startIndex] + ",";
						startIndex++;
					}
					//去掉最后一个逗号
					jcdStr = jcdStr.substring(0, jcdStr.length() - 1);
					
					//查询已识别
					identifyMap.putAll(statOverCarNumMap(startTime, endTime, jcdStr, cd, "1"));
					//查询未识别
					notIdentifyMap.putAll(statOverCarNumMap(startTime, endTime, jcdStr, cd, "0"));
				}
			} else {
				//查询已识别
				identifyMap = statOverCarNumMap(startTime, endTime, jcdid, cd, "1");
				//查询未识别
				notIdentifyMap = statOverCarNumMap(startTime, endTime, jcdid, cd, "0");
			}
		} catch (Exception e) {
			//查询失败
			result = "0";
			e.printStackTrace();
		} finally{
			//-------成功，则封装结果
			if("1".equals(result)){
				//判断是否选择车道统计
				//前台传入的车道不为空时，统计此监测点下的识别量、未识别量、总量以及识别率和此监测点下某车道下的
				//识别量、未识别量、总量以及识别率
				//循环创建统计结果对象
				TjEnitity tjEnitity = null;
				for (int i = 0; i < jcdArr.length; i++) {
					int ityNum = 0;
					int notItyNum = 0;
					int totalNum = 0;
					//获取识别数据
					if(identifyMap != null && identifyMap.containsKey(jcdArr[i])){
						ityNum = identifyMap.get(jcdArr[i]);
					}
					//获取未识别数据
					if(notIdentifyMap != null && notIdentifyMap.containsKey(jcdArr[i])){
						notItyNum = notIdentifyMap.get(jcdArr[i]);
					}
					//总数
					totalNum = ityNum + notItyNum;
					//识别率
					String sbl = "";
					if(totalNum == 0){
						sbl = percentFormat.format(0);
					} else{
						sbl = percentFormat.format((double)(ityNum)/(totalNum*1.0));
					}
					
					//根据识别量 + 未识别量 得到总量
					tjEnitity = new TjEnitity((i+1), jcdArr[i], jcdMap.get(jcdArr[i]), notItyNum, ityNum, totalNum, sbl);
					statLst.add(tjEnitity);
				}
			} else {
				statLst = null;//失败标志
			}
			
			//返回结果
			return statLst;
		}
	}
	
	/*
	 * 统计车流量
	 * @param startTime    识别开始时间
	 * @param endTime      识别截止时间
	 * @param jcdid   监测点ID
	 * @param cd   车道
	 * @param sbtype 识别状态   0表示未识别         1表示已识别       为空时查询所有
	 */
	@SuppressWarnings({ "unchecked" })
	public Map<String, Integer> statOverCarNumMap(String startTime ,String endTime ,String jcdid, String cd, String sbtype) throws Exception{
		Map<String, Integer> maps = new HashMap<String, Integer>();
		Document document = null ;
		
		String sql_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
				+ "<head><groupName>jcdid</groupName><type>01</type><sbzt>" + sbtype + "</sbzt>" 
				+ "</head><body><data><hphm></hphm><kssj>" + startTime + "</kssj>"
				+ "<jssj>" + endTime + "</jssj><tpid></tpid><jcdid>" + jcdid + "</jcdid><hpzl></hpzl>"
				+ "<cplx></cplx><cd>" + cd + "</cd><cb></cb><sd></sd><hmdCphm></hmdCphm>" 
				+ "</data></body></root>";
		
		//连接
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IInAccessServicePortType.class);
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.setAddress(Config.getInstance().getSbPicWebservice());
		IInAccessServicePortType service = (IInAccessServicePortType) factory.create();
		//调用服务
		String resultXml = service.executes("01", "08", "hello,world", "0", sql_xml);
		
		//获取结果
		document = (Document)DocumentHelper.parseText(resultXml);
		Element root = document.getRootElement();
		Element head = (Element) root.selectNodes("head").get(0);
		String success = head.element("success").getText();
		String count = head.element("count").getText();
		if("1".equals(success) && Integer.parseInt(count) > 0){
			Element body = (Element) root.selectNodes("body").get(0);
			List dataLst = body.selectNodes("data");
			for (int i=0; i < dataLst.size();i++) {
				Element elt = (Element) dataLst.get(i);
				maps.put(elt.element("groupName").getText(), Integer.parseInt(elt.element("value").getText() != null? elt.element("value").getText():"0"));
			}
		} else {
			throw new Exception();
		}
		
		return maps;
	}
	
	/**
	 * 根据hql及参数查询总数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int getCountByHql(String hql, Map<String, Object> params) throws Exception{
		return jcdService.getCountByHql(hql, params);
	}
	
	/**
	 * 根据条件获取实时传输数据量
	 * @param jcdid   监测点Id
	 * @param startTime    起始时间
	 * @param endTime      截止时间
	 * @param csbz 实时传输标准      大于该值则视为超时传输
	 */
	@SuppressWarnings("finally")
	public List<TjEnitity> getSjcsqkData(String jcdid, String startTime, String endTime, String csbz, Map<String, String> jcdMap){
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMinimumFractionDigits(2);
		List<TjEnitity> statLst = new ArrayList<TjEnitity>();
		Map<String, Integer> zsMap = new HashMap<String, Integer>(); //非补传数据总量
		Map<String, Integer> csMap = new HashMap<String, Integer>(); //非补传超时数据量
		
		//查询结果
		String result = "1";
		String [] jcdArr = null;
		try {
			//以逗号分隔jcdid字符串，组成数组
			jcdArr = jcdid.split(",");
			if(jcdArr.length > 400){//监测点大于400个，则分批查询
				//计算批次
				int n = (jcdArr.length%400)==0?(jcdArr.length/400):(jcdArr.length/400)+1;
				//循环查询
				for(int ij=0;ij < n;ij++){
					int startIndex = 400 * ij;//计算起始索引
					//一次最多400个监测点
					String jcdStr = "";
					for(int ci=0;ci < 400 && startIndex < jcdArr.length;ci++){
						jcdStr += jcdArr[startIndex] + ",";
						startIndex++;
					}
					//去掉最后一个逗号
					jcdStr = jcdStr.substring(0, jcdStr.length() - 1);
					
					//查询上传总数（不包括补传数据）
					zsMap.putAll(getOverTimeCarNumMap(startTime, endTime, jcdStr, "", "0"));
					//查询超时上传数
					csMap.putAll(getOverTimeCarNumMap(startTime, endTime, jcdStr, csbz, "0"));
				}
			} else {
				//查询上传总数（不包括补传数据）
				zsMap = getOverTimeCarNumMap(startTime, endTime, jcdid, "", "0");
				//查询超时上传数，上传时间 - 通过时间 > csbz（单位：秒）
				csMap = getOverTimeCarNumMap(startTime, endTime, jcdid, csbz, "0");
			}
		} catch (Exception e) {
			//查询失败
			result = "0";
			e.printStackTrace();
		} finally{
			//-------成功，则封装结果
			if("1".equals(result)){
				//循环创建统计结果对象
				TjEnitity tjEnitity = null;
				for (int i = 0; i < jcdArr.length; i++) {
					int jss = 0;
					int css = 0;
					int totalNum = 0;
					//上传总数（不包括补传数据）
					if(zsMap != null && zsMap.containsKey(jcdArr[i])){
						totalNum = zsMap.get(jcdArr[i]);
					}
					//获取超时上传数
					if(csMap != null && csMap.containsKey(jcdArr[i])){
						css = csMap.get(jcdArr[i]);
					}
					//及时数
					jss = totalNum - css;
					//及时上传率
					String jsl = "";
					if(totalNum == 0){
						jsl = percentFormat.format(0);
					} else{
						jsl = percentFormat.format((double)(jss)/(totalNum*1.0));
					}
					
					//根据及时上传数 + 超时上传数 得到总量
					tjEnitity = new TjEnitity((i+1), jcdArr[i], jcdMap.get(jcdArr[i]), jsl, jss, css, totalNum);
					statLst.add(tjEnitity);
				}
			} else {
				statLst = null;//失败标志
			}
			
			//返回结果
			return statLst;
		}
	}
	
	/*
	 * 统计车流量，可以统计超时上传的量，上传总量是否包括补传数据
	 * @param startTime    识别开始时间
	 * @param endTime      识别截止时间
	 * @param jcdid   监测点ID
	 * @param csbz   超时标志                 上传时间 - 通过时间 > csbz（单位：秒）
	 * @param bcbz   是否包含补传数据           0表示不包含         1表示包含
	 */
	@SuppressWarnings({ "unchecked" })
	public static Map<String, Integer> getOverTimeCarNumMap(String startTime, String endTime, String jcdid, String csbz, 
			String bcbz) throws Exception{
		Map<String, Integer> maps = new HashMap<String, Integer>();
		Document document = null ;
		
		String sql_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
				+ "<head></head>"
				+ "<body><data><kssj>" + startTime + "</kssj><jssj>" + endTime + "</jssj><jcdid>" + jcdid + "</jcdid>" 
				+ "<csbz>" + csbz + "</csbz><bcbz>" + bcbz + "</bcbz></data></body></root>";
		
		//连接
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IInAccessServicePortType.class);
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.setAddress(Config.getInstance().getSbPicWebservice());
		IInAccessServicePortType service = (IInAccessServicePortType) factory.create();
		//调用服务
		String resultXml = service.executes("01", "12", "hello,world", "0", sql_xml);
		
		//获取结果
		document = (Document)DocumentHelper.parseText(resultXml);
		Element root = document.getRootElement();
		Element head = (Element) root.selectNodes("head").get(0);
		String success = head.element("success").getText();
		String count = head.element("count").getText();
		if("1".equals(success) && Integer.parseInt(count) > 0){
			Element body = (Element) root.selectNodes("body").get(0);
			List dataLst = body.selectNodes("data");
			for (int i=0;i < dataLst.size();i++) {
				Element elt = (Element) dataLst.get(i);
				maps.put(elt.element("groupName").getText(), Integer.parseInt(elt.element("value").getText() != null? elt.element("value").getText():"0"));
			}
		} else if ("0".equals(success)){//查询失败
			throw new Exception();
		}
		return maps;
	}
	
	/**
	 * 根据条件获取业务处置情况
	 * @param startTime    起始时间
	 * @param endTime      截止时间
	 * @param khbm 
	 */
	@SuppressWarnings("finally")
	public List<YwtjEnitity> getYwczqk(String startTime, String endTime, String khbm, List<Department> deptList){
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMinimumFractionDigits(2);
		List<YwtjEnitity> statLst = new ArrayList<YwtjEnitity>();
		
		//查询结果
		String result = "1";
//		String bksp = "120";//分钟
//		String cksp = "120";//分钟
		String bkqs = "10";//分钟
		String ckqs = "10";//分钟
		String yjqs = "2";//分钟
		String zlxd = "10";//分钟
		String zlqs = "10";//分钟
		String zlfk = "24";//小时
		try {
			//需要检测的考核部门
			String[] khbmArr = null;
			if(khbm != null && !"".equals(khbm.trim())){
				khbmArr = khbm.split(";");
			} else if(deptList != null && deptList.size() > 0){
				khbmArr = new String[deptList.size()];
				for(int i=0;i < deptList.size();i++){
					khbmArr[i] = deptList.get(i).getDeptNo().trim();
				}
			}
			
			//考核部门不为空
			if(khbmArr != null){
				//获取考核点
				Map<String, String> dicMap = dictionaryDao.findMapObject("select d.typeSerialNo as typeSerialNo, d.typeDesc as typeDesc from Dictionary d where d.typeCode='JXKHTIME' and d.deleteFlag != '1'", null);
//				bksp = dicMap.get("BKSP") != null && !"".equals(dicMap.get("BKSP").trim())?dicMap.get("BKSP").trim():"120";
//				cksp = dicMap.get("CKSP") != null && !"".equals(dicMap.get("CKSP").trim())?dicMap.get("CKSP").trim():"120";
				bkqs = dicMap.get("BKQS") != null && !"".equals(dicMap.get("BKQS").trim())?dicMap.get("BKQS").trim():"10";
				ckqs = dicMap.get("CKQS") != null && !"".equals(dicMap.get("CKQS").trim())?dicMap.get("CKQS").trim():"10";
				yjqs = dicMap.get("YJQS") != null && !"".equals(dicMap.get("YJQS").trim())?dicMap.get("YJQS").trim():"2";
				zlxd = dicMap.get("ZLXD") != null && !"".equals(dicMap.get("ZLXD").trim())?dicMap.get("ZLXD").trim():"10";
				zlqs = dicMap.get("ZLQS") != null && !"".equals(dicMap.get("ZLQS").trim())?dicMap.get("ZLQS").trim():"10";
				zlfk = dicMap.get("ZLFK") != null && !"".equals(dicMap.get("ZLFK").trim())?dicMap.get("ZLFK").trim():"24";
				
				//======================布控签收=================
				//条件
				StringBuffer bkqsSql = new StringBuffer();
				Map<String,Object> bkqsParams = new HashMap<String,Object>();
				if(StringUtils.isNotBlank(startTime)){
					bkqsSql.append(" and xfsj >= :startTime");
					bkqsParams.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
				}
				if(StringUtils.isNotBlank(endTime)){
					bkqsSql.append(" and xfsj <= :endTime");
					bkqsParams.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
				}
				bkqsSql.append(" group by qrdw order by qrdw asc ");
				
				//布控签收及时数
				Map<String, String> bkqsjsMap = dictionaryDao.findMapObject("select qrdw, concat(count(id), '') as sum from DisReceive where bkckbz='1' and qrzt = '1' and TIMESTAMPDIFF(SECOND, xfsj, qrsj) <= " + (Integer.parseInt(bkqs) * 60) + bkqsSql.toString(), bkqsParams);
				//布控签收超时数
				Map<String, String> bkqscsMap = dictionaryDao.findMapObject("select qrdw, concat(count(id), '') as sum from DisReceive where bkckbz='1' and qrzt = '1' and TIMESTAMPDIFF(SECOND, xfsj, qrsj) > " + (Integer.parseInt(bkqs) * 60) + bkqsSql.toString(), bkqsParams);
			    //未签收数
				Map<String, String> bkwqssMap = dictionaryDao.findMapObject("select qrdw, concat(count(id), '') as sum from DisReceive where bkckbz='1' and qrzt = '0' " + bkqsSql.toString(), bkqsParams);
				
				//======================撤控签收=================
				//条件
				StringBuffer ckqsSql = new StringBuffer();
				Map<String,Object> ckqsParams = new HashMap<String,Object>();
				if(StringUtils.isNotBlank(startTime)){
					ckqsSql.append(" and xfsj >= :startTime");
					ckqsParams.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
				}
				if(StringUtils.isNotBlank(endTime)){
					ckqsSql.append(" and xfsj <= :endTime");
					ckqsParams.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
				}
				ckqsSql.append(" group by qrdw order by qrdw asc ");
				
				//撤控签收及时数
				Map<String, String> ckqsjsMap = dictionaryDao.findMapObject("select qrdw, concat(count(id), '') as sum from DisReceive where bkckbz='2' and qrzt = '1' and TIMESTAMPDIFF(SECOND, xfsj, qrsj) <= " + (Integer.parseInt(ckqs) * 60) + ckqsSql.toString(), ckqsParams);
				//撤控签收超时数
				Map<String, String> ckqscsMap = dictionaryDao.findMapObject("select qrdw, concat(count(id), '') as sum from DisReceive where bkckbz='2' and qrzt = '1' and TIMESTAMPDIFF(SECOND, xfsj, qrsj) > " + (Integer.parseInt(ckqs) * 60) + ckqsSql.toString(), ckqsParams);
			    //未签收数
				Map<String, String> ckwqssMap = dictionaryDao.findMapObject("select qrdw, concat(count(id), '') as sum from DisReceive where bkckbz='2' and qrzt = '0' " + ckqsSql.toString(), ckqsParams);
				
				//======================预警签收=================
				//条件
				StringBuffer yjqsSql = new StringBuffer();
				Map<String,Object> yjqsParams = new HashMap<String,Object>();
				if(StringUtils.isNotBlank(startTime)){
					yjqsSql.append(" and bjsj >= :startTime");
					yjqsParams.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
				}
				if(StringUtils.isNotBlank(endTime)){
					yjqsSql.append(" and bjsj <= :endTime");
					yjqsParams.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
				}
				yjqsSql.append(" group by bjbm order by bjbm asc ");
				
				//预警签收及时数
				Map<String, String> yjqsjsMap = dictionaryDao.findMapObject("select bjbm, concat(count(qsid), '') as sum from EWRecieve where xxly='0' and qrzt != '0' and TIMESTAMPDIFF(SECOND, bjsj, qrsj) <= " + (Integer.parseInt(yjqs) * 60) + yjqsSql.toString(), yjqsParams);
				//预警签收超时数
				Map<String, String> yjqscsMap = dictionaryDao.findMapObject("select bjbm, concat(count(qsid), '') as sum from EWRecieve where xxly='0' and qrzt != '0' and TIMESTAMPDIFF(SECOND, bjsj, qrsj) > " + (Integer.parseInt(yjqs) * 60) + yjqsSql.toString(), yjqsParams);
			    //未签收数
				Map<String, String> yjwqssMap = dictionaryDao.findMapObject("select bjbm, concat(count(qsid), '') as sum from EWRecieve where xxly='0' and qrzt = '0' " + yjqsSql.toString(), yjqsParams);
				
				//======================指令下达=================
				//条件
				StringBuffer zlxdSql = new StringBuffer();
				Map<String,Object> zlxdParams = new HashMap<String,Object>();
				if(StringUtils.isNotBlank(startTime)){
					zlxdSql.append(" and a.xfsj >= :startTime");
					zlxdParams.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
				}
				if(StringUtils.isNotBlank(endTime)){
					zlxdSql.append(" and a.xfsj <= :endTime");
					zlxdParams.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
				}
				zlxdSql.append(" group by a.zlxfbm order by a.zlxfbm asc ");
				
				//指令下达及时数
				Map<String, String> zlxdjsMap = dictionaryDao.findMapObject("select a.zlxfbm, concat(count(a.id), '') as sum from Instruction a where xxly='0' and TIMESTAMPDIFF(SECOND, a.ewrecieve.bjsj, a.xfsj) <= " + (Integer.parseInt(zlxd) * 60) + zlxdSql.toString(), zlxdParams);
				//指令下达超时数
				Map<String, String> zlxdcsMap = dictionaryDao.findMapObject("select a.zlxfbm, concat(count(a.id), '') as sum from Instruction a where xxly='0' and TIMESTAMPDIFF(SECOND, a.ewrecieve.bjsj, a.xfsj) > " + (Integer.parseInt(zlxd) * 60) + zlxdSql.toString(), zlxdParams);
				
				//======================指令签收=================
				//条件
				StringBuffer zlqsSql = new StringBuffer();
				Map<String,Object> zlqsParams = new HashMap<String,Object>();
				if(StringUtils.isNotBlank(startTime)){
					zlqsSql.append(" and zlsj >= :startTime");
					zlqsParams.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
				}
				if(StringUtils.isNotBlank(endTime)){
					zlqsSql.append(" and zlsj <= :endTime");
					zlqsParams.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
				}
				zlqsSql.append(" group by zlbm order by zlbm asc ");
				
				//指令签收及时数
				Map<String, String> zlqsjsMap = dictionaryDao.findMapObject("select zlbm, concat(count(id), '') as sum from InstructionSign where xxly='0' and qszt = '1' and TIMESTAMPDIFF(SECOND, zlsj, qssj) <= "+ (Integer.parseInt(zlqs) * 60) + zlqsSql.toString(), zlqsParams);
				//指令签收超时数
				Map<String, String> zlqscsMap = dictionaryDao.findMapObject("select zlbm, concat(count(id), '') as sum from InstructionSign where xxly='0' and qszt = '1' and TIMESTAMPDIFF(SECOND, zlsj, qssj) > "+ (Integer.parseInt(zlqs) * 60) + zlqsSql.toString(), zlqsParams);
				//指令未签收数
				Map<String, String> zlwqssMap = dictionaryDao.findMapObject("select zlbm, concat(count(id), '') as sum from InstructionSign where xxly='0' and qszt = '0' " + zlqsSql.toString(), zlqsParams);
				
				//======================指令反馈=================
				//条件
				StringBuffer zlfkSql = new StringBuffer();
				Map<String,Object> zlfkParams = new HashMap<String,Object>();
				if(StringUtils.isNotBlank(startTime)){
					zlfkSql.append(" and zlsj >= :startTime");
					zlfkParams.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
				}
				if(StringUtils.isNotBlank(endTime)){
					zlfkSql.append(" and zlsj <= :endTime");
					zlfkParams.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
				}
				zlfkSql.append(" group by zlbm order by zlbm asc ");
				
				//指令反馈及时数
				Map<String, String> zlfkjsMap = dictionaryDao.findMapObject("select zlbm, concat(count(id), '') as sum from InstructionSign where xxly='0' and fkzt = '1' and TIMESTAMPDIFF(SECOND, zlsj, fksj) <= " + (Integer.parseInt(zlfk) * 60 * 60) + zlfkSql.toString(), zlfkParams);
				//指令反馈超时数
				Map<String, String> zlfkcsMap = dictionaryDao.findMapObject("select zlbm, concat(count(id), '') as sum from InstructionSign where xxly='0' and fkzt = '1' and TIMESTAMPDIFF(SECOND, zlsj, fksj) > " + (Integer.parseInt(zlfk) * 60 * 60) + zlfkSql.toString(), zlfkParams);
				//指令未反馈数
				Map<String, String> zlwfksMap = dictionaryDao.findMapObject("select zlbm, concat(count(id), '') as sum from InstructionSign where xxly='0' and fkzt = '0' " + zlfkSql.toString(), zlfkParams);
				
				//======================封装数据======================
				for(int i=0;i < khbmArr.length;i++){
					YwtjEnitity ywtjEnitity = new YwtjEnitity();
					ywtjEnitity.setSn((i+1));
					ywtjEnitity.setBmbh(khbmArr[i]);
					ywtjEnitity.setBmmc(getBmmcByBmbh(deptList, khbmArr[i]));
					
					//==================布控签收==================
					//布控签收及时数
					int bkqsjss = bkqsjsMap.get(khbmArr[i]) != null && !"".equals(bkqsjsMap.get(khbmArr[i]).trim()) ? Integer.parseInt(bkqsjsMap.get(khbmArr[i])):0;
					ywtjEnitity.setBkqsjss(bkqsjss);
					//布控签收超时数
					int bkqscss = bkqscsMap.get(khbmArr[i]) != null && !"".equals(bkqscsMap.get(khbmArr[i]).trim()) ? Integer.parseInt(bkqscsMap.get(khbmArr[i])):0;
					ywtjEnitity.setBkqscss(bkqscss);
					//布控未签收数
					int bkwqss = bkwqssMap.get(khbmArr[i]) != null && !"".equals(bkwqssMap.get(khbmArr[i]).trim()) ? Integer.parseInt(bkwqssMap.get(khbmArr[i])):0;
					ywtjEnitity.setBkwqss(bkwqss);
					//布控签收总数
					int bkqszs = bkqsjss + bkqscss + bkwqss;
					ywtjEnitity.setBkqszs(bkqszs);
					//布控签收及时率
					String bkqsjsl = "";
					if(bkqszs == 0){
						bkqsjsl = percentFormat.format(0);
					} else{
						bkqsjsl = percentFormat.format((double)(bkqsjss)/(bkqszs*1.0));
					}
					ywtjEnitity.setBkqsjsl(bkqsjsl);
					
					//==================撤控签收==================
					//撤控签收及时数
					int ckqsjss = ckqsjsMap.get(khbmArr[i]) != null && !"".equals(ckqsjsMap.get(khbmArr[i]).trim()) ? Integer.parseInt(ckqsjsMap.get(khbmArr[i])):0;
					ywtjEnitity.setCkqsjss(ckqsjss);
					//撤控签收超时数
					int ckqscss = ckqscsMap.get(khbmArr[i]) != null && !"".equals(ckqscsMap.get(khbmArr[i]).trim()) ? Integer.parseInt(ckqscsMap.get(khbmArr[i])):0;
					ywtjEnitity.setCkqscss(ckqscss);
					//撤控未签收数
					int ckwqss = ckwqssMap.get(khbmArr[i]) != null && !"".equals(ckwqssMap.get(khbmArr[i]).trim()) ? Integer.parseInt(ckwqssMap.get(khbmArr[i])):0;
					ywtjEnitity.setCkwqss(ckwqss);
					//撤控签收总数
					int ckqszs = ckqsjss + ckqscss + ckwqss;
					ywtjEnitity.setCkqszs(ckqszs);
					//撤控签收及时率
					String ckqsjsl = "";
					if(ckqszs == 0){
						ckqsjsl = percentFormat.format(0);
					} else{
						ckqsjsl = percentFormat.format((double)(ckqsjss)/(ckqszs*1.0));
					}
					ywtjEnitity.setCkqsjsl(ckqsjsl);
					
					//==================预警签收==================
					//预警签收及时数
					int yjqsjss = yjqsjsMap.get(khbmArr[i]) != null && !"".equals(yjqsjsMap.get(khbmArr[i]).trim()) ? Integer.parseInt(yjqsjsMap.get(khbmArr[i])):0;
					ywtjEnitity.setYjqsjss(yjqsjss);
					//预警签收超时数
					int yjqscss = yjqscsMap.get(khbmArr[i]) != null && !"".equals(yjqscsMap.get(khbmArr[i]).trim()) ? Integer.parseInt(yjqscsMap.get(khbmArr[i])):0;
					ywtjEnitity.setYjqscss(yjqscss);
					//预警未签收数
					int yjwqss = yjwqssMap.get(khbmArr[i]) != null && !"".equals(yjwqssMap.get(khbmArr[i]).trim()) ? Integer.parseInt(yjwqssMap.get(khbmArr[i])):0;
					ywtjEnitity.setYjwqss(yjwqss);
					//预警签收总数
					int yjqszs = yjqsjss + yjqscss + yjwqss;
					ywtjEnitity.setYjqszs(yjqszs);
					//预警签收及时率
					String yjqsjsl = "";
					if(yjqszs == 0){
						yjqsjsl = percentFormat.format(0);
					} else{
						yjqsjsl = percentFormat.format((double)(yjqsjss)/(yjqszs*1.0));
					}
					ywtjEnitity.setYjqsjsl(yjqsjsl);
					
					//==================指令下达==================
					//指令下达及时数
					int zlxdjss = zlxdjsMap.get(khbmArr[i]) != null && !"".equals(zlxdjsMap.get(khbmArr[i]).trim()) ? Integer.parseInt(zlxdjsMap.get(khbmArr[i])):0;
					ywtjEnitity.setZlxdjss(zlxdjss);
					//指令下达超时数
					int zlxdcss = zlxdcsMap.get(khbmArr[i]) != null && !"".equals(zlxdcsMap.get(khbmArr[i]).trim()) ? Integer.parseInt(zlxdcsMap.get(khbmArr[i])):0;
					ywtjEnitity.setZlxdcss(zlxdcss);
					//指令下达总数
					int zlxdzs = zlxdjss + zlxdcss + 0;
					ywtjEnitity.setZlxdzs(zlxdzs);
					//指令下达及时率
					String zlxdjsl = "";
					if(zlxdzs == 0){
						zlxdjsl = percentFormat.format(0);
					} else{
						zlxdjsl = percentFormat.format((double)(zlxdjss)/(zlxdzs*1.0));
					}
					ywtjEnitity.setZlxdjsl(zlxdjsl);
					
					//==================指令签收==================
					//指令签收及时数
					int zlqsjss = zlqsjsMap.get(khbmArr[i]) != null && !"".equals(zlqsjsMap.get(khbmArr[i]).trim()) ? Integer.parseInt(zlqsjsMap.get(khbmArr[i])):0;
					ywtjEnitity.setZlqsjss(zlqsjss);
					//指令签收超时数
					int zlqscss = zlqscsMap.get(khbmArr[i]) != null && !"".equals(zlqscsMap.get(khbmArr[i]).trim()) ? Integer.parseInt(zlqscsMap.get(khbmArr[i])):0;
					ywtjEnitity.setZlqscss(zlqscss);
					//指令未签收数
					int zlwqss = zlwqssMap.get(khbmArr[i]) != null && !"".equals(zlwqssMap.get(khbmArr[i]).trim()) ? Integer.parseInt(zlwqssMap.get(khbmArr[i])):0;
					ywtjEnitity.setZlwqss(zlwqss);
					//指令签收总数
					int zlqszs = zlqsjss + zlqscss + zlwqss;
					ywtjEnitity.setZlqszs(zlqszs);
					//指令签收及时率
					String zlqsjsl = "";
					if(zlqszs == 0){
						zlqsjsl = percentFormat.format(0);
					} else{
						zlqsjsl = percentFormat.format((double)(zlqsjss)/(zlqszs*1.0));
					}
					ywtjEnitity.setZlqsjsl(zlqsjsl);
					
					//==================指令反馈==================
					//指令反馈及时数
					int zlfkjss = zlfkjsMap.get(khbmArr[i]) != null && !"".equals(zlfkjsMap.get(khbmArr[i]).trim()) ? Integer.parseInt(zlfkjsMap.get(khbmArr[i])):0;
					ywtjEnitity.setZlfkjss(zlfkjss);
					//指令反馈超时数
					int zlfkcss = zlfkcsMap.get(khbmArr[i]) != null && !"".equals(zlfkcsMap.get(khbmArr[i]).trim()) ? Integer.parseInt(zlfkcsMap.get(khbmArr[i])):0;
					ywtjEnitity.setZlfkcss(zlfkcss);
					//指令未反馈数
					int zlwfks = zlwfksMap.get(khbmArr[i]) != null && !"".equals(zlwfksMap.get(khbmArr[i]).trim()) ? Integer.parseInt(zlwfksMap.get(khbmArr[i])):0;
					ywtjEnitity.setZlwfks(zlwfks);
					//指令反馈总数
					int zlfkzs = zlfkjss + zlfkcss + zlwfks;
					ywtjEnitity.setZlfkzs(zlfkzs);
					//指令反馈及时率
					String zlfkjsl = "";
					if(zlfkzs == 0){
						zlfkjsl = percentFormat.format(0);
					} else{
						zlfkjsl = percentFormat.format((double)(zlfkjss)/(zlfkzs*1.0));
					}
					ywtjEnitity.setZlfkjsl(zlfkjsl);
					
					//加入列表
					statLst.add(ywtjEnitity);
				}
			} else {
				//查询失败
				result = "0";
			}
		} catch (Exception e) {
			//查询失败
			result = "0";
			e.printStackTrace();
		} finally{
			if(!"1".equals(result)){
				statLst = null;//失败标志
			}
			
			//返回结果
			return statLst;
		}
	}
	
	//根据部门编号从列表中翻译出部门名称
	private String getBmmcByBmbh(List<Department> deptList, String bmbh){
		for(int i=0;i < deptList.size();i++){
			Department dept = deptList.get(i);
			if(bmbh != null && !"".equals(bmbh.trim())
					&& dept != null && dept.getDeptNo() != null && !"".equals(dept.getDeptNo().trim())
					&& bmbh.trim().equals(dept.getDeptNo().trim())){
				return dept.getDeptName();
			}
		}
		return "";
	}
}