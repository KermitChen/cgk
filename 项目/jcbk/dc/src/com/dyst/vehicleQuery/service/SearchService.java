package com.dyst.vehicleQuery.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import jodd.datetime.JDateTime;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.dyst.base.utils.PageResult;
import com.dyst.utils.Config;
import com.dyst.vehicleQuery.dto.MapObj;
import com.dyst.vehicleQuery.dto.PathDay;
import com.dyst.vehicleQuery.dto.ReqArgs;
import com.dyst.vehicleQuery.dto.SbDto;
import com.dyst.vehicleQuery.utils.ComUtils;
import com.dyst.webservice.IInAccessServicePortType;

/**
 * 调用dyst接口，查询数据
 * @author Administrator
 *
 */
@Service("searchService")
public class SearchService {

	@Resource
	private PicService picSearch;
	@Resource
	private BDService bdService;
	
	/**
	 * 过车查询
	 * @param args
	 * @return
	 * @throws Exception 
	 */
	public PageResult vehicleQuery(ReqArgs args,int pageNo,int pageSize) throws Exception{
		List<SbDto> list = new ArrayList<SbDto>();
		//页数不能小于1
		if(pageNo < 1){
			pageNo = 1;
		}
		args.setFrom((pageNo-1)*pageSize);
		args.setPagesize(pageSize);
		int count = getCountOfSb(args);//得到查询到的总数
		String result = getSbForPage(args);//查询es
		if(result != null && !"".equals(result)){//解析查询结果
			Document document = null;
			document = (Document) DocumentHelper.parseText(result);// String转化为XML
			Element root = document.getRootElement();
			Element head = (Element) root.selectNodes("head").get(0);
			String success = head.element("success").getText();// 是否查询成功
			String num = head.element("count").getText();//是否查询成功
			if("1".equals(success.trim()) && Integer.parseInt(num) > 0) {
				Element body = (Element) root.selectNodes("body").get(0);//实体数据
				List data = (List) body.selectNodes("data");
				SbDto dto = null;
				for(int i=0;i < data.size();i++){
					Element e = (Element)data.get(i);
					dto = new SbDto();
					dto.setHphm(((Element)e.selectNodes("hphm").get(0)).getText());
					dto.setCplx(((Element)e.selectNodes("cplx").get(0)).getText());
					dto.setJcdid(((Element)e.selectNodes("jcdid").get(0)).getText());
					dto.setCdid(((Element)e.selectNodes("cdid").get(0)).getText());
					dto.setSbsj(((Element)e.selectNodes("sbsj").get(0)).getText());
					dto.setScsj(((Element)e.selectNodes("scsj").get(0)).getText());
					dto.setTpzs(((Element)e.selectNodes("tpzs").get(0)).getText());
					dto.setTp1(((Element)e.selectNodes("tp1").get(0)).getText());
					dto.setTp2(((Element)e.selectNodes("tp2").get(0)).getText());
					dto.setTp3(((Element)e.selectNodes("tp3").get(0)).getText());
					dto.setTp4(((Element)e.selectNodes("tp4").get(0)).getText());
					dto.setTp5(((Element)e.selectNodes("tp5").get(0)).getText());
					dto.setCb(((Element)e.selectNodes("cb").get(0)).getText());
					dto.setSd(((Element)e.selectNodes("sd").get(0)).getText());
					list.add(dto);
				}
			}
		}
		if(list != null && list.size() > 0){
			//TODO
			picSearch.findPic(list);
		}
		return new PageResult(count, pageNo,pageSize , list);
	}
	
	/**
	 * 查询过车集合，不分页
	 * @param args
	 * @return
	 * @throws Exception 
	 */
	public List<SbDto> findSomeVehicleQuery(ReqArgs args) throws Exception{
		List<SbDto> list = new ArrayList<SbDto>();
		SbDto dto = null;
		args.setIsFilterHmd("0");//不过滤红名单
		String result = getSbForPage(args);//查询es
		if(result != null && !"".equals(result)){//解析查询结果
			Document document = null;
			document = (Document) DocumentHelper.parseText(result);// String转化为XML
			Element root = document.getRootElement();
			Element head = (Element) root.selectNodes("head").get(0);
			String success = head.element("success").getText();// 是否查询成功
			String num = head.element("count").getText();//是否查询成功
			if ("1".equals(success.trim()) && Integer.parseInt(num)>0) {
				Element body = (Element) root.selectNodes("body").get(0);//实体数据
				List data = (List) body.selectNodes("data");
				for(int i=0 ;i < data.size();i++){
					Element e = (Element)data.get(i);
					dto = new SbDto();
					dto.setHphm(((Element)e.selectNodes("hphm").get(0)).getText());
					dto.setCplx(((Element)e.selectNodes("cplx").get(0)).getText());
					dto.setJcdid(((Element)e.selectNodes("jcdid").get(0)).getText());
					dto.setCdid(((Element)e.selectNodes("cdid").get(0)).getText());
					dto.setSbsj(((Element)e.selectNodes("sbsj").get(0)).getText());
					dto.setScsj(((Element)e.selectNodes("scsj").get(0)).getText());
					dto.setTpzs(((Element)e.selectNodes("tpzs").get(0)).getText());
					dto.setTp1(((Element)e.selectNodes("tp1").get(0)).getText());
					dto.setTp2(((Element)e.selectNodes("tp2").get(0)).getText());
					dto.setTp3(((Element)e.selectNodes("tp3").get(0)).getText());
					dto.setTp4(((Element)e.selectNodes("tp4").get(0)).getText());
					dto.setTp5(((Element)e.selectNodes("tp5").get(0)).getText());
					dto.setCb(((Element)e.selectNodes("cb").get(0)).getText());
					dto.setSd(((Element)e.selectNodes("sd").get(0)).getText());
					list.add(dto);
				}
			}
		}
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	/**
	 * 查询昼伏夜出过车记录
	 * @param args
	 * @return
	 * @throws Exception 
	 */
	public List<SbDto> findZfycVehicleQuery(ReqArgs args) throws Exception{
		List<SbDto> list = new ArrayList<SbDto>();
		SbDto dto = null;
		String result = null;
		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
				+ "<head>" +
				"<pagesize>"+ args.getPagesize() +"</pagesize>" +
				"<from>"+ args.getFrom() + "</from>" +
				"<sort>"+ args.getSort() +"</sort>" +
				"<sortType>"+ args.getSortType() +"</sortType>" +
				"</head><body><data>"
				+ "<hphm>" + args.getHphm() + "</hphm>" 
				+ "<kssj>" + args.getKssj() + "</kssj>" 
				+ "<jssj>" + args.getJssj() + "</jssj></data></body></root>";
	   	
	    //包含通配符，模糊查询
	   	String dataType = "109";
	   	
		//连接
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IInAccessServicePortType.class);
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.setAddress(Config.getInstance().getSbPicWebservice());
		IInAccessServicePortType service = (IInAccessServicePortType) factory.create();
		//调用服务
		result = service.executes("01", dataType, "hello,world", "1" ,str_xml);
		
		
		
		if(result != null && !"".equals(result)){//解析查询结果
			Document document = null;
			document = (Document) DocumentHelper.parseText(result);// String转化为XML
			Element root = document.getRootElement();
			Element head = (Element) root.selectNodes("head").get(0);
			String success = head.element("success").getText();// 是否查询成功
			String num = head.element("count").getText();//是否查询成功
			if ("1".equals(success.trim()) && Integer.parseInt(num)>0) {
				Element body = (Element) root.selectNodes("body").get(0);//实体数据
				List data = (List) body.selectNodes("data");
				for(int i=0 ;i < data.size();i++){
					Element e = (Element)data.get(i);
					dto = new SbDto();
					dto.setHphm(((Element)e.selectNodes("hphm").get(0)).getText());
					dto.setCplx(((Element)e.selectNodes("cplx").get(0)).getText());
					dto.setJcdid(((Element)e.selectNodes("jcdid").get(0)).getText());
					dto.setCdid(((Element)e.selectNodes("cdid").get(0)).getText());
					dto.setSbsj(((Element)e.selectNodes("sbsj").get(0)).getText());
					dto.setScsj(((Element)e.selectNodes("scsj").get(0)).getText());
					dto.setTpzs(((Element)e.selectNodes("tpzs").get(0)).getText());
					dto.setTp1(((Element)e.selectNodes("tp1").get(0)).getText());
					dto.setTp2(((Element)e.selectNodes("tp2").get(0)).getText());
					dto.setTp3(((Element)e.selectNodes("tp3").get(0)).getText());
					dto.setTp4(((Element)e.selectNodes("tp4").get(0)).getText());
					dto.setTp5(((Element)e.selectNodes("tp5").get(0)).getText());
					dto.setCb(((Element)e.selectNodes("cb").get(0)).getText());
					dto.setSd(((Element)e.selectNodes("sd").get(0)).getText());
					list.add(dto);
				}
			}
		}
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	/**
	 * 根据图片id查询
	 * @param tpid
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	public SbDto queryByTpid(String tpid) throws Exception{
		List<SbDto> list = new ArrayList<SbDto>();
		SbDto dto = null;
		String str_xml = "";
		try {
			//封装
			str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>" +
					"<head>" +
					"</head><body><data>" +
					"<tpid>" + tpid + "</tpid>" +
					"</data></body></root>";
		   	String dataType = "100";
		   	
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
				if ("1".equals(success.trim())) {
					Element body = (Element) root.selectNodes("body").get(0);//实体数据
					List data = (List) body.selectNodes("data");
					Element e = (Element)data.get(0);
					dto = new SbDto();
					dto.setHphm(((Element)e.selectNodes("hphm").get(0)).getText());
					dto.setCplx(((Element)e.selectNodes("cplx").get(0)).getText());
					dto.setJcdid(((Element)e.selectNodes("jcdid").get(0)).getText());
					dto.setCdid(((Element)e.selectNodes("cdid").get(0)).getText());
					dto.setSbsj(((Element)e.selectNodes("sbsj").get(0)).getText());
					dto.setScsj(((Element)e.selectNodes("scsj").get(0)).getText());
					dto.setTpzs(((Element)e.selectNodes("tpzs").get(0)).getText());
					dto.setTp1(((Element)e.selectNodes("tp1").get(0)).getText());
					dto.setTp2(((Element)e.selectNodes("tp2").get(0)).getText());
					dto.setTp3(((Element)e.selectNodes("tp3").get(0)).getText());
					dto.setTp4(((Element)e.selectNodes("tp4").get(0)).getText());
					dto.setTp5(((Element)e.selectNodes("tp5").get(0)).getText());
					dto.setCb(((Element)e.selectNodes("cb").get(0)).getText());
					dto.setSd(((Element)e.selectNodes("sd").get(0)).getText());
				}
			}
			if(dto != null){
				list.add(dto);
				//TODO
				picSearch.findPic(list);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new Exception();
		} finally{
			return dto;
		}
	}
	/**
	 * 查询符合条件的识别数据总数
	 * @param plateTxt  车牌号码
	 * @param Hpys   车牌类型
	 * @param jcdid   监测点
	 * @param startT   开始时间
	 * @param endT   截止时间
	 * @param hmdCphm   交警红名单
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	public int getCountOfSb(ReqArgs args) throws Exception{
		Document document = null;
		int count = 0;
		try {
			//跨库测试查询
			String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
				+ "<head><pagesize>0</pagesize><from>0</from><sort></sort><sortType></sortType></head><body><data>"
				+ "<hphm>" + args.getHphm() + "</hphm>" 
				+ "<kssj>" + args.getKssj() + "</kssj>" 
				+ "<jssj>" + args.getJssj() + "</jssj>"
				+ "<tpid>" + args.getTpid() + "</tpid>"
				+ "<jcdid>" + args.getJcdid()  + "</jcdid>"
				+ "<hpzl>" + args.getHpzl() + "</hpzl>"
				+ "<cplx>" + args.getCplx() + "</cplx>"
				+ "<cd>" + args.getCd() + "</cd>"
				+ "<cb>" + args.getCb() + "</cb>"
				+ "<sd>" + args.getSd() + "</sd>"
				+ "<hmdCphm>"+ args.getIsFilterHmd() + "</hmdCphm></data></body></root>";
		    
			//包含通配符，模糊查询
			String dataType = ComUtils.ensureBusFlag(args);
			
			//连接
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(IInAccessServicePortType.class);
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.setAddress(Config.getInstance().getSbPicWebservice());
			IInAccessServicePortType service = (IInAccessServicePortType) factory.create();
			//调用服务
			String resultXml = service.executes("01", dataType, "hello,world", "0", str_xml);
			
			//获取结果
			document = (Document) DocumentHelper.parseText(resultXml);//String转化为XML
			Element root = document.getRootElement();
			Element head = (Element) root.selectNodes("head").get(0);
			String success = head.element("success").getText();//是否查询成功
			if("1".equals(success.trim())){
				count = Integer.parseInt(head.element("count").getText());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new Exception();
		} finally{
			return count;
		}
	}
	/**
	 * 分页查询符合条件的识别数据
	 * @param plateTxt
	 * @param Hpys
	 * @param jcdid
	 * @param startT
	 * @param endT
	 * @param start
	 * @param max
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public String getSbForPage(ReqArgs args) throws Exception{
		String result = null;
		String str_xml = "";
		try {
			str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root>"
					+ "<head>" +
					"<pagesize>"+ args.getPagesize() +"</pagesize>" +
					"<from>"+ args.getFrom() + "</from>" +
					"<sort>"+ args.getSort() +"</sort>" +
					"<sortType>"+ args.getSortType() +"</sortType>" +
					"</head><body><data>"
					+ "<hphm>" + args.getHphm() + "</hphm>" 
					+ "<kssj>" + args.getKssj() + "</kssj>" 
					+ "<jssj>" + args.getJssj() + "</jssj>"
					+ "<tpid>" + args.getTpid() + "</tpid>"
					+ "<jcdid>" + args.getJcdid()  + "</jcdid>"
					+ "<hpzl>" + args.getHpzl() + "</hpzl>"
					+ "<cplx>" + args.getCplx() + "</cplx>"
					+ "<cd>" + args.getCd() + "</cd>"
					+ "<cb>" + args.getCb() + "</cb>"
					+ "<sd>" + args.getSd() + "</sd>"
					+ "<hmdCphm>"+ args.getIsFilterHmd() + "</hmdCphm></data></body></root>";
		   	
		    //包含通配符，模糊查询
		   	String dataType = ComUtils.ensureBusFlag(args);
		   	
			//连接
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(IInAccessServicePortType.class);
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.setAddress(Config.getInstance().getSbPicWebservice());
			IInAccessServicePortType service = (IInAccessServicePortType) factory.create();
			//调用服务
			result = service.executes("01", dataType, "hello,world", "1" ,str_xml);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new Exception();
		}finally{
			return result;
		}
	}
	/**
	 * 用于过车查询页面判断输入车辆某天是否有过车记录
	 * @param hphm
	 * @param kssj
	 * @param jssj
	 * @return
	 * @throws Exception
	 */
	public List<MapObj> showWheelPath(String hphm,String kssj,String jssj)throws Exception{
		//查询过车记录
		List<SbDto> list = findSomeVehicleQuery(new ReqArgs(kssj, jssj, hphm, 5000, 0, "", ""));
		if (list == null || list.size() < 1 || list.get(0).getHphm().contains("*")) {
			return null;
		}
		List<MapObj> resList = new ArrayList<MapObj>();//结果list
		Set<String> dateSet = new HashSet<String>();
		String dateTemp = new JDateTime(kssj, "YYYY-MM-DD hh:mm:ss").toString("YYYY-MM-DD");
		for (int i = 0; i < list.size(); i++) {//将所有时间放到set中
			dateSet.add(new JDateTime(list.get(i).getSbsj()).toString("YYYY-MM-DD"));
		}
		while(new JDateTime(jssj, "YYYY-MM-DD hh:mm:ss").addDay(1).isAfterDate(new JDateTime(dateTemp, "YYYY-MM-DD")) ){
			if(dateSet.contains(dateTemp)){
				if(!isContainsObj(resList, dateTemp.substring(5, 7))){
					List li = new ArrayList();
					li.add(new PathDay(dateTemp.substring(0, 4),dateTemp.substring(5, 7) , dateTemp.substring(8, 10), "day_simple"));
					resList.add(new MapObj(dateTemp.substring(5, 7), li));
				}else{
					getList(resList, dateTemp.substring(5, 7)).add(new PathDay(dateTemp.substring(0, 4),dateTemp.substring(5, 7) , dateTemp.substring(8, 10), "day_simple"));
				}
			}else{
				if(!isContainsObj(resList, dateTemp.substring(5, 7))){
					List li = new ArrayList();
					li.add(new PathDay(dateTemp.substring(0, 4),dateTemp.substring(5, 7) , dateTemp.substring(8, 10), "day_void"));
					resList.add(new MapObj(dateTemp.substring(5, 7), li));
				}else{
					getList(resList, dateTemp.substring(5, 7)).add(new PathDay(dateTemp.substring(0, 4),dateTemp.substring(5, 7) , dateTemp.substring(8, 10), "day_void"));
				}
			}
			dateTemp = new JDateTime(dateTemp, "YYYY-MM-DD").addDay(1).toString("YYYY-MM-DD");
		}
		return resList;
	}
	//用于上面判断list中是否包含某对象
	private boolean isContainsObj(List<MapObj> list,String key){
		for (MapObj mapObj : list) {
			if(key.equals(mapObj.getKey())){
				return true;
			}
		}
		return false;
	}
	private List getList(List<MapObj> list,String key){
		for (MapObj mapObj : list) {
			if(key.equals(mapObj.getKey())){
				return mapObj.getList();
			}
		}
		return null;
	}
	/**
	 * 翻译单个
	 * @param sbDto
	 */
	public void tranJcd(SbDto sbDto)  throws Exception{
		Map<String, String> jcdMap = bdService.getJcdMap();
		if(sbDto != null){
				if(sbDto.getJcdid() != null && !"".equals(sbDto.getJcdid()) && jcdMap.get(sbDto.getJcdid()) != null && !"".equals(jcdMap.get(sbDto.getJcdid()))){
					sbDto.setJcdid(jcdMap.get(sbDto.getJcdid()));
			}
		}
	}
	
	/**
	 * 翻译单个车牌类型
	 * @param list
	 * @throws Exception 
	 */
	public void tranCplx(SbDto sbDto) throws Exception{
		Map<String, String> cplxMap = bdService.getCplxMap();
		if(sbDto != null){
			if(sbDto.getCplx() != null && !"".equals(sbDto.getCplx()) && cplxMap.get(sbDto.getCplx()) != null && !"".equals(cplxMap.get(sbDto.getCplx()))){
				sbDto.setCplx(cplxMap.get(sbDto.getCplx()));
			}
		}
	}
}
