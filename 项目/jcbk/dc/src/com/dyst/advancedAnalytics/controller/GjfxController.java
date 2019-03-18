package com.dyst.advancedAnalytics.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.datetime.JDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.BaseDataMsg.service.JjHmdService;
import com.dyst.advancedAnalytics.dto.CxglResult;
import com.dyst.advancedAnalytics.dto.FootHoldDetail;
import com.dyst.advancedAnalytics.dto.FootHoldResult;
import com.dyst.advancedAnalytics.dto.RequestParameters;
import com.dyst.advancedAnalytics.entities.ExcelBeanForDwpz;
import com.dyst.advancedAnalytics.entities.ResDwpz;
import com.dyst.advancedAnalytics.service.GjfxService;
import com.dyst.advancedAnalytics.service.TjService;
import com.dyst.advancedAnalytics.utils.CalculateUtil;
import com.dyst.base.utils.PageResult;
import com.dyst.kafka.service.KafkaService;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.StaticUtils;
import com.dyst.vehicleQuery.dto.ReqArgs;
import com.dyst.vehicleQuery.service.BDService;
import com.dyst.vehicleQuery.service.SearchService;
import com.dyst.vehicleQuery.utils.ComUtils;
@Controller
@RequestMapping("/gjfx")
public class GjfxController {

	@Autowired
	private GjfxService gjfxService;
	@Autowired
	private BDService bdService;
	@Autowired
	private SearchService searchService;
	@Autowired
	private JcdService jcdService;
	@Autowired
	private TjService tjService;
	@Autowired
	private JjHmdService jjHmdService;
	@Autowired
	private UserService userService;
	/**
	 * 获取当前计算总数
	 * @param response
	 */
	@RequestMapping("/getCalCount")
	public void getCalCount(HttpServletResponse response,String key){
		try {
			response.setContentType("text/plain");
			if ("1".equals(key)) {
				response.getWriter().write(JSON.toJSONString(KafkaService.bsCount));
			}else if("2".equals(key)){
				response.getWriter().write(JSON.toJSONString(KafkaService.dwCount));
			}else if("3".equals(key)){
				response.getWriter().write(JSON.toJSONString(KafkaService.pfCount));
			}
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/******************************************************伴随分析begin********************************************************/
	/**
	 * 用于加载伴随分析页面所需参数
	 * @param map
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/preBsfx")
	public String preBsfx(Map<String, Object>  map){
		String page = "/gjfx/bsfx/bsfx";
		try {
			JDateTime jdt = new JDateTime();
			map.put("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
			map.put("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
			map.put("sjld", "1");
			map.put("txyz", "3");
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 伴随分析
	 * @param request
	 * @param pageResult
	 * @param map
	 * @param params 
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/bsfx")
	@Description(moduleName="车辆伴随分析",operateType="1",operationName="分析伴随车辆")
	public String bsfx(HttpServletRequest request,HttpServletResponse response,PageResult pageResult,Map<String, Object> map,RequestParameters params){
		String page = "/gjfx/bsfx/bsfx";
		try {
			//当前登录用户名，用于读取kafka数据
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			params.setLoginName(user.getLoginName());
			pageResult = gjfxService.bsAnalysis(params);                                                             
			//查询已知车辆过车记录
			map.put("pageResult", pageResult);
			ComUtils.putInMap(params, map);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 查询伴随轨迹
	 * @param hphm	伴随车牌号
	 * @param resFlag	
	 * @param txyz	同行阈值
	 * @param response
	 */
	@RequestMapping("/bsPath")
	@Description(moduleName="车辆伴随分析",operateType="1",operationName="查询伴随车辆轨迹")
	public void getBsPath(String hphm,String resFlag,String txyz,HttpServletResponse response){
		try {
			List list = gjfxService.getBsPath(hphm, resFlag, txyz);
			response.setContentType("application/json");
			response.getWriter().write(JSON.toJSONString(list));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/******************************************************伴随分析end********************************************************/
	

	/******************************************************多维碰撞begin********************************************************/
	/**
	 * 用于加载页面所需参数
	 * @param map
	 * @param req
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/preDwpz")
	public String preDwpz(Map<String, Object>  map){
		String page = "/gjfx/dwpz";
		try {
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 多维碰撞分析
	 * @param request
	 * @param pageResult
	 * @param map
	 * @param parms
	 * @return
	 */
	@RequestMapping("/dwpz")
	@Description(moduleName="多维碰撞分析",operateType="1",operationName="多维碰撞分析")
	public void dwpz(HttpServletRequest request,HttpServletResponse response,RequestParameters params){
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			params.setLoginName(user.getLoginName());
			if(StringUtils.isNotEmpty(params.getDwConGroup())){
				PageResult pageResult = gjfxService.dwpzAnalysis(params, 1,1000);
				response.setContentType("application/json");
				response.getWriter().write(JSON.toJSONString(pageResult));
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 多维碰撞导出excel
	 */
	@RequestMapping(value="excelExportForDwpz")
	@Description(moduleName="多维碰撞分析",operateType="1",operationName="导出多维碰撞分析结果")
	public void excelExportForDwpz(String queryCondition,String queryFlag,Model model,HttpServletRequest req,HttpServletResponse resp){
		try {
			RequestParameters params = new RequestParameters();
			params.setResFlag(queryFlag);
			PageResult pageResult = gjfxService.dwpzAnalysis(params, 1,1000);
			List<ResDwpz> items = pageResult.getItems();
			//把它转化成excelBeanList
			ExcelBeanForDwpz excelBean = new ExcelBeanForDwpz(items,queryCondition);
			//交给excelUtils导出为excel文件
			//输出
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Date now=new Date();
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("多维碰撞结果查询").append(dateFormat.format(now)).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
				//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			gjfxService.excelExportForDwpz(excelBean,outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/******************************************************多维碰撞end********************************************************/
	
	
	/******************************************************出行规律begin********************************************************/
	/**
	 * 跳转出行规律页面
	 * @return
	 */
	@RequestMapping("/preCxgl")
	public String preCxgl(){
		return "/gjfx/cxgl";
	}
	
	/**
	 * 出行规律分析
	 * @param request
	 * @param pageResult
	 * @param map
	 * @param parms
	 * @return
	 */
	@RequestMapping("/cxgl")
	@Description(moduleName="出行规律分析",operateType="1",operationName="分析车辆出行规律")
	public void cxgl(HttpServletResponse response,String hphm,String kssj,String jssj,CxglResult pageResult){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int showDays = 1;//一次查询展示几天的轨迹
		try {
			//第一次查询，计算总页数
			if(pageResult.getTotalPageCount() < 1){
				int betweenDays = CalculateUtil.betweenDays(sdf.parse(kssj), sdf.parse(jssj));//天数差
				if (betweenDays > showDays) {
					if(betweenDays%showDays == 0){
						pageResult.setTotalPageCount(betweenDays/showDays);
					}else{
						pageResult.setTotalPageCount(betweenDays/showDays+1);
					}
				}else{
					pageResult.setTotalPageCount(1);
				}
			}
			//计算查询时间
			JDateTime beginTime = new JDateTime(kssj, "YYYY-MM-DD").addDay((pageResult.getPageNo()-1)*showDays);
			JDateTime endTime = new JDateTime(kssj, "YYYY-MM-DD").addDay(pageResult.getPageNo()*showDays);
			/*if(endTime.isAfter(new JDateTime(jssj, "YYYY-MM-DD"))){
				endTime = new JDateTime(jssj, "YYYY-MM-DD").addDay(1);
			}*/
			boolean flag = isHmdCar(hphm);//是否为红名单车辆
			List list = null;
			//查询过车记录
			if(!flag){
				list = searchService.findSomeVehicleQuery(new ReqArgs(beginTime.toString("YYYY-MM-DD hh:mm:ss"), endTime.toString("YYYY-MM-DD hh:mm:ss"), hphm, 1000, 0, "tgsj", "asc"));
			}
			if(list != null &&list.size() > 0){
				pageResult.setBegin(beginTime.toString("YYYY-MM-DD"));
				pageResult.setEnd(endTime.subDay(1).toString("YYYY-MM-DD"));
				pageResult.setItems(list);
			}else{
				pageResult.setBegin(beginTime.toString("YYYY-MM-DD"));
				pageResult.setEnd(endTime.subDay(1).toString("YYYY-MM-DD"));
				pageResult.setErrorMessage("未查询到过车记录！");
			}
			response.setContentType("application/json");
			response.getWriter().write(JSON.toJSONString(pageResult));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/******************************************************出行规律end********************************************************/
	/******************************************************初次入城begin********************************************************/
	/**
	 * 用于加载初次入城页面所需参数
	 * @param map
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/preCcrc")
	public String preCcrc(Map<String, Object>  map){
		String page = "/gjfx/ccrc";
		try {
			JDateTime jdate = new JDateTime();
			map.put("jssj", jdate.subDay(1).toString("YYYY-MM-DD")+" 23:59:59");
			map.put("kssj", jdate.subMonth(1).toString("YYYY-MM-DD")+" 23:59:59");
			List<Dictionary> cplxList = userService.getDictionarysByTypeCode("0002");//查出车牌类型
			map.put("cplxList", JSON.toJSON(cplxList));
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 初次入城车辆
	 * @param request
	 * @param pageResult
	 * @param map
	 * @param parms
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/ccrc")
	@Description(moduleName="初次入城车辆",operateType="1",operationName="查询初次入城车辆")
	public String ccrc(HttpServletRequest request,PageResult pageResult,Map<String, Object> map,RequestParameters params){
		String page = "/gjfx/ccrc";
		try {
			pageResult = gjfxService.ccrcAnalysis(params, pageResult.getPageNo(),10);
			List<Dictionary> cplxList = userService.getDictionarysByTypeCode("0002");//查出车牌类型
			map.put("cplxList", JSON.toJSON(cplxList));
			ComUtils.putInMap(params, map);
			map.put("pageResult", pageResult);
			map.put("jcdMap", bdService.getJcdMap());
			map.put("cplxMap", bdService.getCplxMap());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/******************************************************初次入城end********************************************************/
	/******************************************************频繁过车begin********************************************************/
	/**
	 * 加载频繁过车所需参数
	 * @param map
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/prePfgc")
	public String prePfgc(Map<String, Object>  map){
		String page = "/gjfx/pfgc/pfgc";
		try {
			JDateTime jdate = new JDateTime();
			map.put("jssj", jdate.toString("YYYY-MM-DD"));//结束时间
			map.put("kssj", jdate.subDay(7).toString("YYYY-MM-DD"));//起始时间
			map.put("time1", "06");
			map.put("time2", "09");
			map.put("gccs", "5");//过车次数
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 频繁过车分析
	 * @param request
	 * @param pageResult
	 * @param map
	 * @param parms
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/pfgc")
	@Description(moduleName="频繁过车分析",operateType="1",operationName="频繁过车分析")
	public String pfgc(HttpServletRequest request,PageResult pageResult,Map<String, Object> map,RequestParameters params){
		String page = "/gjfx/pfgc/pfgc";
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			String loginName = user.getLoginName();
			params.setLoginName(loginName);
			pageResult = gjfxService.pfgcAnalysis(params, pageResult.getPageNo(), 10);
			ComUtils.putInMap(params, map);
			map.put("pageResult", pageResult);
			map.put("cplxMap", bdService.getCplxMap());
			map.put("jcdMap", bdService.getJcdMap());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/******************************************************频繁过车end********************************************************/
	/******************************************************出行时间start********************************************************/
	@RequestMapping("preFootHold")
	public String preFootHold(HttpServletRequest request){
		return "/gjfx/footHold/footHold";
	}
	/**
	 * 落脚点分析
	 * @param request
	 * @param pageResult
	 * @param map
	 * @param parms
	 * @return
	 */
	@RequestMapping("/footHold")
	@Description(moduleName="出行时间分析",operateType="1",operationName="分析车辆出行时间")
	public void footHold(HttpServletResponse response,String hphm,String kssj,String jssj){
		try {
			List<FootHoldResult> list = null;
			boolean flag = isHmdCar(hphm);
			if(!flag){
				list = tjService.footHold(hphm.trim(), kssj+" 00:00:00", jssj+" 23:59:59");
			}
			//获取所有带坐标的监测点
			List<Jcd> jcdList = jcdService.findAllJcd();
			response.setContentType("application/json");
			response.getWriter().write(JSON.toJSONString(new Object[]{list,jcdList}));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 单个监测点分时间段统计次数
	 * @param response
	 * @param hphm
	 * @param kssj
	 * @param jssj
	 * @param jcdid
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/footHoldDetail")
	@Description(moduleName="出行时间分析",operateType="1",operationName="分析车辆出行时间")
	public String footHoldDetail(HttpServletRequest request,Map<String, Object> map,String hphm,String kssj,String jssj,String jcdid){
		String page = "/gjfx/footHold/footHoldDetail";
		try {
			List<FootHoldDetail> list = tjService.footHoldDetail(hphm.trim(), kssj, jssj, jcdid);
			map.put("jcdMap", bdService.getJcdMap());
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			page = "redirect:/common/errorPage/error500.jsp";
		}finally{
			return page;
		}
	}
	
	/******************************************************出行时间end********************************************************/
	/******************************************************昼伏夜出start********************************************************/
	@SuppressWarnings("finally")
	@RequestMapping("preZfyc")
	public String preZfyc(Map<String, Object> map){
		String page = "/gjfx/zfyc/zfyc";
		try {
			JDateTime jdate = new JDateTime();
			map.put("jssj", jdate.subDay(1).toString("YYYY-MM-DD"));
			map.put("kssj", jdate.subDay(7).toString("YYYY-MM-DD"));
			//城区列表
			String hql2 ="select new map(a.areano as id ,a.areaname as name) from Area a where a.suparea ='00'";
			List<Object> list2 = jcdService.findObjects(hql2, null);
			map.put("cqList", list2);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 昼伏夜出分析
	 * @param request
	 * @param pageResult
	 * @param map
	 * @param params
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/zfyc")
	@Description(moduleName="昼伏夜出分析",operateType="1",operationName="昼伏夜出分析")
	public String zfyc(HttpServletRequest request,PageResult pageResult,Map<String, Object> map,RequestParameters params){
		String page = "/gjfx/zfyc/zfyc";
		try {
			pageResult = gjfxService.zfycAnalysis(params, pageResult.getPageNo(), 10);
			ComUtils.putInMap(params, map);
			map.put("pageResult", pageResult);
			//城区列表
			String hql2 ="select new map(a.areano as id ,a.areaname as name) from Area a where a.suparea ='00'";
			List<Object> list2 = jcdService.findObjects(hql2, null);
			map.put("cqList", list2);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/******************************************************昼伏夜出end********************************************************/
	/**
	 * 查询车牌号是否在红名单中
	 * @param hphm	车牌号
	 * @return false 不是红名单车辆  true 是红名单车辆
	 * @throws Exception
	 */
	private boolean isHmdCar(String hphm) throws Exception{
		boolean flag = false;
		//查询所有红名单记录
		List list = jjHmdService.wildFindHmd(hphm);
		if (list != null && list.size() > 0) {
			flag = true;
		}
		return flag;
	}
	
	
}
