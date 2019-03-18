package com.dyst.vehicleQuery.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

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
import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.BaseDataMsg.service.JjHmdService;
import com.dyst.advancedAnalytics.entities.FalseCphm;
import com.dyst.advancedAnalytics.entities.FalseDelete;
import com.dyst.advancedAnalytics.service.FakeService;
import com.dyst.advancedAnalytics.service.FalseService;
import com.dyst.advancedAnalytics.service.GjfxService;
import com.dyst.base.utils.PageResult;
import com.dyst.chariotesttube.entities.Vehicle;
import com.dyst.kafka.service.KafkaService;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.Role;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.RoleService;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.RomdOrbitThread;
import com.dyst.utils.StaticUtils;
import com.dyst.vehicleQuery.dto.MapObj;
import com.dyst.vehicleQuery.dto.ReqArgs;
import com.dyst.vehicleQuery.dto.SbDto;
import com.dyst.vehicleQuery.service.BDService;
import com.dyst.vehicleQuery.service.PicService;
import com.dyst.vehicleQuery.service.SearchService;
import com.dyst.vehicleQuery.utils.ComUtils;
import com.sunshine.monitor.system.ws.server.VehPassrec;

/**
 * 车辆查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/clcx")
public class VehicleQuery {
	@Autowired
	private KafkaService kafkaService;
	@Autowired
	private BDService bdService;
	@Autowired
	private SearchService searchService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private JjHmdService jjHmdService;
	@Autowired
	private UserService userService;
	@Autowired
	private FakeService fakeService;
	@Autowired
	private FalseService falseService;
	@Autowired
	private GjfxService gjfxService;
	@Autowired
	private PicService picService;
	@Autowired
	private JcdService jcdService;
	
	//分类map表
	private Map<String, String> getSortMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("tgsj", "按通过时间");
		map.put("hphm", "按车牌号码");
		map.put("cplx", "按车牌类型");
		map.put("jcdid", "按监测点");
		return map;
	}
	//查询类型map表
	private Map<String, String> getCxlxMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "按天查询");
		map.put("2", "按月查询");
		map.put("3", "自定义查询");
		return map;
	}
	/**
	 * 车辆综合查询页面初始化方法
	 * @param model
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/preSearch")
	public String preSearch(Model  model,HttpServletRequest request){
		String page = "/clcx/clcx";
		try {
			JDateTime jdt = new JDateTime();
			model.addAttribute("year", jdt.toString("YYYY"));
			model.addAttribute("month", jdt.toString("MM"));
			model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
			model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("showStyle", "show_style0");
			model.addAttribute("isHiddenDiv", "0");
			model.addAttribute("cxlb", "1");
			List<Dictionary> cplxList = userService.getDictionarysByTypeCode("0002");//查出车牌类型
			model.addAttribute("cplxList", JSON.toJSON(cplxList));
			request.getSession().setAttribute("cplxList", JSON.toJSON(cplxList));
			model.addAttribute("cdMap", bdService.getCdMap());
			model.addAttribute("sortMap", getSortMap());
			model.addAttribute("cxlxMap", getCxlxMap());
			model.addAttribute("jcdid1", "==全部==");
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 车辆综合查询
	 * @param pageResult
	 * @param map
	 * @param args
	 * @param request
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/clcx")
	@Description(moduleName="过车查询",operateType="1",operationName="查询过车记录")
	public String clcx(PageResult pageResult, Map<String, Object> map, ReqArgs args, HttpServletRequest request){
		String page = "/clcx/clcx";
		args.setIsFilterHmd("1");//是否过滤红名单，0为不过滤
		try {
			boolean flag = false;
			if(StringUtils.isNotEmpty(args.getHphm()) && args.getHphm().length() >= 7 && !args.getHphm().contains("*") 
					&& !args.getHphm().contains("?") && !args.getHphm().contains(",")){//是否查询的一个车的过车记录
				flag = true;
			}
			if(StringUtils.isNotEmpty(args.getCplx())){//替换车牌类型分隔符
				args.setCplx(args.getCplx().trim().replace(";", ","));
			}
			//排序设置
			args.setSort("tgsj");
			args.setSortType("DESC");
			//查询
			pageResult = searchService.vehicleQuery(args, pageResult.getPageNo(), 10);
			if(pageResult.getItems() == null || pageResult.getItems().size() < 1){
				pageResult.setErrorMessage("未查询到过车数据!");
			} else if(pageResult.getItems().size() > 0 && flag){
				List<MapObj> list = searchService.showWheelPath(args.getHphm(), args.getKssj(), args.getJssj());
				if(list != null && list.size() > 0){
					map.put("dayList", list);
				}
			}
			
			//TODO 
			if(args != null && args.getShowStyle() != null && "show_style2".equals(args.getShowStyle())){
				User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
				//查询是否有查询机动车信息的权限
				Role role = roleService.getRoleById(user.getRoleId());
				char isHaveVehiclePermission = role.getPermissionContent().charAt(110);
				if(pageResult.getItems() != null && pageResult.getItems().size() > 0 && isHaveVehiclePermission == '1'){
					SbDto dto = (SbDto) pageResult.getItems().get(0);
					Vehicle vehicle = ComUtils.getVehicleInfo(user, dto.getHphm(), ComUtils.cplxToHpzl(dto.getCplx()), 
							CommonUtils.getIpAddr(request), kafkaService);
					map.put("vehicle", vehicle);
				}
			}
			
			ComUtils.putInMap(args, map);
			map.put("pageResult", pageResult);
			map.put("cplxMap", bdService.getCplxMap());
			map.put("jcdMap", bdService.getJcdMap());
			map.put("cdMap", bdService.getCdMap());
			map.put("sortMap", getSortMap());
			map.put("cxlxMap", getCxlxMap());
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 红名单查询页面初始化方法
	 * @param model
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/preHmdSearch")
	public String preHmdSearch(Model  model,HttpServletRequest request){
		String page = "/clcx/hmdcx";
		try {
			JDateTime jdt = new JDateTime();
			model.addAttribute("year", jdt.toString("YYYY"));
			model.addAttribute("month", jdt.toString("MM"));
			model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
			model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("showStyle", "show_style0");
			model.addAttribute("isHiddenDiv", "0");
			model.addAttribute("cxlb", "1");
			List<Dictionary> cplxList = userService.getDictionarysByTypeCode("0002");//查出车牌类型
			model.addAttribute("cplxList", JSON.toJSON(cplxList));
			model.addAttribute("sortMap", getSortMap());
			model.addAttribute("cxlxMap", getCxlxMap());
			model.addAttribute("jcdid1", "==全部==");
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 红名单查询
	 * @param pageResult
	 * @param map
	 * @param args
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "finally", "rawtypes" })
	@RequestMapping("/hmdClcx")
	@Description(moduleName="红名单过车查询",operateType="1",operationName="查询过车记录")
	public String hmdClcx(PageResult pageResult,Map<String, Object> map,ReqArgs args,HttpServletRequest request){
		String page = "/clcx/hmdcx";
		try {
			args.setIsFilterHmd("0");//是否过滤红名单，0为不过滤
			args.setCplx(args.getCplx().trim().replace(";", ","));
			if(StringUtils.isEmpty(args.getHphm())){
				StringBuffer sb = new StringBuffer();
				List list = jjHmdService.wildFindHmd(null);
				if(list.size() > 0){
					for (Object object : list) {
						Jjhomd jjhomd = (Jjhomd)object;
						sb.append(jjhomd.getCphid()).append(",");
					}
					args.setHphm(sb.toString().substring(0, sb.toString().lastIndexOf(",")));
					pageResult = searchService.vehicleQuery(args,pageResult.getPageNo(),10);
					args.setHphm("");
				}
			}else{
				pageResult = searchService.vehicleQuery(args,pageResult.getPageNo(),10);
			}
			boolean flag = false;
			if(StringUtils.isNotEmpty(args.getHphm()) && args.getHphm().length() == 7 && !args.getHphm().contains("*") 
					&& !args.getHphm().contains("?") && !args.getHphm().contains(",")){//是否查询的一个车的过车记录
					flag = true;
			}
			if(pageResult.getItems() == null || pageResult.getItems().size() < 1){
				pageResult.setErrorMessage("未查询到过车数据!");
			}else if(pageResult.getItems().size() > 0 && flag){
				List<MapObj> list = searchService.showWheelPath(args.getHphm(), args.getKssj(), args.getJssj());
				if(list != null && list.size() > 0){
					map.put("dayList", list);
				}
			}
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			//查询是否有查询机动车信息的权限
			Role role = roleService.getRoleById(user.getRoleId());
			char isHaveVehiclePermission = role.getPermissionContent().charAt(110);
			if(pageResult.getItems() != null && pageResult.getItems().size() > 0 && isHaveVehiclePermission == '1'){
				SbDto dto = (SbDto) pageResult.getItems().get(0);
				Vehicle vehicle = ComUtils.getVehicleInfo(user, dto.getHphm(), ComUtils.cplxToHpzl(dto.getCplx()), 
						CommonUtils.getIpAddr(request), kafkaService);
				map.put("vehicle", vehicle);
			}
			ComUtils.putInMap(args, map);
			map.put("pageResult", pageResult);
			List<Dictionary> cplxList = userService.getDictionarysByTypeCode("0002");//查出车牌类型
			map.put("cplxList", JSON.toJSON(cplxList));
			map.put("cplxMap", bdService.getCplxMap());
			map.put("jcdMap", bdService.getJcdMap());
			map.put("sortMap", getSortMap());
			map.put("cxlxMap", getCxlxMap());
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 重点车辆查询页面初始化方法
	 * @param model
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/preSearchImportant")
	public String preSearchImportant(Model  model,HttpServletRequest request){
		String page = "/clcx/importantVehicleSbQuery";
		try {
			JDateTime jdt = new JDateTime();
			model.addAttribute("year", jdt.toString("YYYY"));
			model.addAttribute("month", jdt.toString("MM"));
			model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
			model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("showStyle", "show_style0");
			model.addAttribute("isHiddenDiv", "0");
			model.addAttribute("cxlb", "1");
			List<Dictionary> cplxList = userService.getDictionarysByTypeCode("0002");//查出车牌类型
			model.addAttribute("cplxList", JSON.toJSON(cplxList));
			model.addAttribute("sortMap", getSortMap());
			model.addAttribute("cxlxMap", getCxlxMap());
			model.addAttribute("cdMap", bdService.getCdMap());
			model.addAttribute("jcdid1", "==全部==");
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 重点车辆查询
	 * @param pageResult
	 * @param map
	 * @param args
	 * @param request
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/importantQuery")
	@Description(moduleName="重点车辆过车查询",operateType="1",operationName="查询过车记录")
	public String importantVehicleQuery(PageResult pageResult,Map<String, Object> map,ReqArgs args,HttpServletRequest request){
		String page = "/clcx/importantVehicleSbQuery";
		try {
			boolean flag = false;
			if(StringUtils.isNotEmpty(args.getHphm()) && args.getHphm().length() == 7 && !args.getHphm().contains("*") 
					&& !args.getHphm().contains("?") && !args.getHphm().contains(",")){//是否查询的一个车的过车记录
					flag = true;
			}
			args.setIsFilterHmd("0");//是否过滤红名单，0为不过滤
			args.setCplx(args.getCplx().trim().replace(";", ","));
			pageResult = searchService.vehicleQuery(args,pageResult.getPageNo(),10);
			if(pageResult.getItems() == null || pageResult.getItems().size() < 1){
				pageResult.setErrorMessage("未查询到过车数据!");
			}else if(pageResult.getItems().size() > 0 && flag){
				List<MapObj> list = searchService.showWheelPath(args.getHphm(), args.getKssj(), args.getJssj());
				if(list != null && list.size() > 0){
					map.put("dayList", list);
				}
			}
			
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			//查询是否有查询机动车信息的权限
			Role role = roleService.getRoleById(user.getRoleId());
			char isHaveVehiclePermission = role.getPermissionContent().charAt(110);
			if(pageResult.getItems() != null && pageResult.getItems().size() > 0 && isHaveVehiclePermission == '1'){
				SbDto dto = (SbDto) pageResult.getItems().get(0);
				Vehicle vehicle = ComUtils.getVehicleInfo(user, dto.getHphm(), ComUtils.cplxToHpzl(dto.getCplx()), 
						CommonUtils.getIpAddr(request), kafkaService);
				map.put("vehicle", vehicle);
			}
			ComUtils.putInMap(args, map);
			map.put("pageResult", pageResult);
			List<Dictionary> cplxList = userService.getDictionarysByTypeCode("0002");//查出车牌类型
			map.put("cplxList", JSON.toJSON(cplxList));
			map.put("cplxMap", bdService.getCplxMap());
			map.put("jcdMap", bdService.getJcdMap());
			map.put("sortMap", getSortMap());
			map.put("cxlxMap", getCxlxMap());
			map.put("cdMap", bdService.getCdMap());
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 查询重点车辆
	 * @param hphm
	 * @param searchFlag
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getImportCar")
	@Description(moduleName="重点车辆过车查询",operateType="1",operationName="查询重点车辆")
	public void getImportCar(String hphm,String searchFlag,HttpServletResponse response){
		List<String> cphmList = new ArrayList<String>();
		try {
			List list = null;
			if ("0".equals(searchFlag)) {//假牌
				list = falseService.findByHphm(hphm);
			} else if("1".equals(searchFlag)){//套牌
				list = fakeService.findByHphm(hphm);
			} else if("2".equals(searchFlag)){//布控
				list = gjfxService.getBkCphm(hphm);
			}
			
			if(list != null){
				cphmList.addAll(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				response.setContentType("application/json");
				response.getWriter().write(JSON.toJSONString(cphmList));
				response.getWriter().flush();
				response.getWriter().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 按月显示过车记录
	 * @param hphm	车牌号
	 * @param kssj	开始时间
	 * @param jssj	结束时间
	 * @param map
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/showSbMonth")
	@Description(moduleName="过车查询",operateType="1",operationName="按月显示过车记录")
	public String showSbMonth(String hphm,String kssj,String jssj,Map<String, Object> map,HttpServletRequest request){
		String page = "/clcx/showSbMonth";
		try {
			if(StringUtils.isNotEmpty(hphm) && StringUtils.isNotEmpty(kssj) && StringUtils.isNotEmpty(jssj)){
				List<MapObj> list = searchService.showWheelPath(hphm, kssj, jssj);
				if(list != null && list.size() > 0){
					map.put("dayList", list);
				}
			}
			map.put("hphm",hphm);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 按月显示过车记录
	 * @param hphm	车牌号
	 * @param kssj	开始时间
	 * @param jssj	结束时间
	 * @param map
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/showMonth")
	@Description(moduleName="过车查询",operateType="1",operationName="按月显示过车记录")
	public String showMonth(String id,String kssj,String jssj,Map<String, Object> map,HttpServletRequest request){
		String page = "/clcx/module/showSbMonth";
		try {
			if(StringUtils.isNotEmpty(id)){
				FalseCphm fal = falseService.getFalseById(Integer.parseInt(id));
				if(fal != null && StringUtils.isNotEmpty(kssj) && StringUtils.isNotEmpty(jssj)){
					List<MapObj> list = searchService.showWheelPath(fal.getJcphm(), kssj, jssj);
					if(list != null && list.size() > 0){
						map.put("dayList", list);
					}
					map.put("fal",fal);
				}
			}
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 根据tpid查询详细信息
	 * @param tpid
	 * @param map
	 * @return
	 */
	@RequestMapping("/getSbByTpid")
	@Description(moduleName="过车查询",operateType="1",operationName="查询过车序号查询过车记录")
	public String getSbByTpid(String tpid,Map<String, Object> map,HttpServletRequest request){
		String page = "/clcx/sbDetail";
		try {
			SbDto dto = searchService.queryByTpid(tpid);
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			Role role = roleService.getRoleById(user.getRoleId());
			char isHaveVehiclePermission = role.getPermissionContent().charAt(110);
			if(isHaveVehiclePermission == '1'){
				Vehicle vehicle = ComUtils.getVehicleInfo(user, dto.getHphm(), ComUtils.cplxToHpzl(dto.getCplx()), 
						CommonUtils.getIpAddr(request), kafkaService);
				map.put("vehicle", vehicle);
			}
			map.put("jcdMap", bdService.getJcdMap());
			map.put("cplxMap", bdService.getCplxMap());
			map.put("sb", dto);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * 根据tpid查询详细信息
	 * @param tpid
	 * @param map
	 * @return
	 */
	@RequestMapping("/getSb")
	@Description(moduleName="过车查询",operateType="1",operationName="查询过车序号查询过车记录")
	public String getSb(String tpid,Map<String, Object> map,HttpServletRequest request){
		String page = "/clcx/sbDetail";
		try {
			SbDto dto = searchService.queryByTpid(tpid);
			map.put("jcdMap", bdService.getJcdMap());
			map.put("cplxMap", bdService.getCplxMap());
			map.put("sb", dto);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * 查询车辆信息
	 * @param user
	 * @return
	 */
	@RequestMapping("/getVehInfo")
	@Description(moduleName="车辆信息查询",operateType="1",operationName="查询车辆信息")
	public void getVehicleInfo(HttpServletRequest request,HttpServletResponse response) {
		try {
			String hphm = request.getParameter("hphm");
			String cpzl = request.getParameter("cpzl");
			if(StringUtils.isNotEmpty(hphm) && StringUtils.isNotEmpty(cpzl)){
				response.setContentType("application/json");
				User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
				//查询是否有查询机动车信息的权限
				Role role = roleService.getRoleById(user.getRoleId());
				char isHaveVehiclePermission = role.getPermissionContent().charAt(110);
				if(isHaveVehiclePermission == '1'){//有查询权限
					Vehicle vehicle = ComUtils.getVehicleInfo(user, hphm, ComUtils.cplxToHpzl(cpzl), 
							CommonUtils.getIpAddr(request), kafkaService);
					String jsonData = JSON.toJSONString(vehicle);
					response.getWriter().write(jsonData);
				}else{//无查询权限
					response.getWriter().write(JSON.toJSONString(null));
				}
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * GIS和图片展示过车数据
	 * @param pageResult
	 * @param map
	 * @param args
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/showSbInGis")
	public String showSbInGis(PageResult pageResult,Map<String, Object> map,ReqArgs args){
		String page = "/clcx/showSbInGis";
		try {
			pageResult = searchService.vehicleQuery(args,pageResult.getPageNo(),9);
			map.put("pageResult", pageResult);
			map.put("args", args);
			map.put("jcdMap", bdService.getJcdMap());
			map.put("cplxMap", bdService.getCplxMap());
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 展示车辆轨迹
	 * @param pageResult
	 * @param map
	 * @param args
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/showWheelPath")
	@Description(moduleName="过车查询",operateType="1",operationName="展示车辆轨迹")
	public String showWheelPath(Map<String, Object> map,ReqArgs args,HttpServletRequest request){
		String page = "/clcx/showWheelPath";
		try {
			args.setSort("tgsj");
			args.setSortType("ASC");
			List<SbDto> list= searchService.findSomeVehicleQuery(args);
			map.put("sbList", JSON.toJSON(list));
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 展示假牌车辆轨迹
	 * @param pageResult
	 * @param map
	 * @param args
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/showWheel")
	@Description(moduleName="过车查询",operateType="1",operationName="展示假牌车辆轨迹")
	public String showWheel(Map<String, Object> map,ReqArgs args,HttpServletRequest request,String id){
		String page = "/clcx/module/showWheelPath";
		String flag = "0";
		try {
			if(StringUtils.isNotEmpty(id)){
				FalseCphm fla = falseService.getFalseById(Integer.parseInt(id));
				if(fla != null){
					JDateTime jdate = new JDateTime(fla.getLrsj());
					args.setJssj(jdate.toString("YYYY-MM-DD")+" 00:00:00");
					args.setKssj(jdate.subDay(1).toString("YYYY-MM-DD")+" 00:00:00");
					args.setHphm(fla.getJcphm());
					args.setSort("tgsj");
					args.setSortType("ASC");
					List<SbDto> list= searchService.findSomeVehicleQuery(args);
					if(list != null && list.size() > 0){
						picService.findPic(list);
					}
					List delList = falseService.findDeleteData(fla.getId());//标记删除的用户列表
					User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
					for (int i = 0; i < delList.size(); i++) {
						FalseDelete fd = (FalseDelete) delList.get(i);
						if(user.getLoginName().equals(fd.getPno())){
							flag = "1";
						}
					}
					map.put("reslist", list);
					map.put("sbList", JSON.toJSON(list));
					map.put("delList", delList);
					map.put("fla", fla);
					map.put("flag", flag);
				}
			}
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 列表展示过车数据
	 * @param map
	 * @param args
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/showSbInTable")
	@Description(moduleName="过车查询",operateType="1",operationName="列表展示过车数据")
	public String showSbInTable(Map<String, Object> map,ReqArgs args,HttpServletRequest request){
		String page = "/clcx/showSbInTable";
		try {
			args.setSort("tgsj");
			args.setSortType("desc");
			args.setPagesize(1000);
			List<SbDto> list = searchService.findSomeVehicleQuery(args);
			map.put("list", list);
			map.put("jcdMap", bdService.getJcdMap());
			map.put("cplxMap", bdService.getCplxMap());
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 查询昼伏夜出过车记录
	 * @param map
	 * @param args
	 * @return
	 */
	@RequestMapping("/showZfycSb")
	@Description(moduleName="过车查询",operateType="1",operationName="查询昼伏夜出过车记录")
	public String showZfycSb(Map<String, Object> map,ReqArgs args){
		String page = "/clcx/showSbInTable";
		try {
			args.setSort("tgsj");
			args.setSortType("desc");
			args.setPagesize(1000);
			List<SbDto> list = searchService.findZfycVehicleQuery(args);
			if(list != null && list.size() > 0){
				//过滤停车场过车记录
				List<SbDto> tempList = new ArrayList<SbDto>();
				String hql = "select j.id from Jcd j where j.jcdxz = '2'";
				List<Object> jcdList = jcdService.findObjects(hql, null);
				SbDto sb = null;
				if(jcdList != null && jcdList.size() > 0){
					for (int i = 0; i < list.size(); i++) {
						sb = list.get(i);
						if (sb != null && jcdList.contains(sb.getJcdid())) {
							continue;
						}
						tempList.add(sb);
					}
					list = tempList;
				}
				map.put("list", list);
				map.put("jcdMap", bdService.getJcdMap());
				map.put("cplxMap", bdService.getCplxMap());
			}
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * 查询频繁过车过车记录
	 * @param map
	 * @param args
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/showPfgcSb")
	@Description(moduleName="过车查询",operateType="1",operationName="查询频繁过车过车记录")
	public String showPfgcSb(Map<String, Object> map,ReqArgs args,int time1,int time2,HttpServletRequest request){
		String page = "/clcx/showSbInTable";
		try {
			args.setKssj(args.getKssj() + " 00:00:00");
			args.setJssj(args.getJssj()+ " 23:59:59");
			args.setSort("tgsj");
			args.setSortType("desc");
			args.setPagesize(2000);
			List<SbDto> list = searchService.findSomeVehicleQuery(args);
			List<SbDto> resList = new ArrayList<SbDto>();
			//遍历剔除不在时间段范围内的记录
			if(list != null){
				for (int i = 0; i < list.size(); i++) {
					int temp = Integer.parseInt(list.get(i).getSbsj().substring(11, 13));
					if(temp >= time1 && temp < time2){
						resList.add(list.get(i));
					}
				}
			}
			map.put("list", resList);
			map.put("jcdMap", bdService.getJcdMap());
			map.put("cplxMap", bdService.getCplxMap());
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 列表展示过车数据(根据多个图片id)
	 * @param map
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getSbByTpids")
	@Description(moduleName="过车查询",operateType="1",operationName="列表展示过车数据(根据多个图片id)")
	public String getSbByTpids(Map<String, Object> map,String tpids,HttpServletRequest request){
		String page = "/clcx/showSbInTable";
		try {
			List<SbDto> list= new ArrayList<SbDto>();
			String[] tpidStr = tpids.split(",");
			for (int i = 0; i < tpidStr.length; i++) {
				SbDto dto = searchService.queryByTpid(tpidStr[i]);
				list.add(dto);
			}
			map.put("list", list);
			map.put("jcdMap", bdService.getJcdMap());
			map.put("cplxMap", bdService.getCplxMap());
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 跳转红名单选择页面
	 * @return
	 */
	@RequestMapping("hmdChoose")
	public String hmdChoose(){
		return "/clcx/module/hmdChoose";
	}
	/**
	 * 跳转重点车辆选择页面
	 * @return
	 */
	@RequestMapping("importantChoose")
	public String importantChoose(){
		return "/clcx/module/importantVehicleChoose";
	}
	/**
	 * 查询某辆车某段时间过车记录
	 * @param pageResult
	 * @param map
	 * @param args
	 * @param req
	 * @return
	 */
	@RequestMapping("/findSomeSb")
	@Description(moduleName="过车查询",operateType="1",operationName="查询某辆车某段时间过车记录")
	public String findSomeSb(String hphm,String jcdid,String kssj,String jssj,PageResult pageResult,Map<String, Object> map,ReqArgs args,HttpServletRequest request){
		List<SbDto> list = null;
		SbDto sb = null;
		StringBuffer tpids = new StringBuffer();
		try {
			hphm = new String(hphm.getBytes("iso-8859-1"), "utf-8");
			args.setTpid("");
			args.setIsFilterHmd("0");
			//得到请求参数
			if(StringUtils.isNotEmpty(hphm)){
				args.setHphm(hphm);
			}
			if(StringUtils.isNotEmpty(jcdid)){
				args.setJcdid(jcdid);
			}
			if(StringUtils.isNotEmpty(kssj) && StringUtils.isNotEmpty(jssj)){
				args.setKssj(kssj);
				args.setJssj(jssj);
				//查询过车记录
				list = searchService.findSomeVehicleQuery(args);
			}
			if(list != null && list.size() > 0){//查询到记录
				//循环将图片id取出存入tpids
				for (int i = 0;i < list.size();i++) {
					sb = list.get(i);
					if(i < (list.size()-1)){
						tpids.append(sb.getTp1()).append(",");
					}else{
						tpids.append(sb.getTp1());
					}
				}
				map.put("tpids", tpids);
				//查询第一条记录并返回
				sb = searchService.queryByTpid(list.get(0).getTp1());
				User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
				//查询是否有查询机动车信息的权限
				Role role = roleService.getRoleById(user.getRoleId());
				char isHaveVehiclePermission = role.getPermissionContent().charAt(110);
				if(isHaveVehiclePermission == '1'){
					Vehicle vehicle = ComUtils.getVehicleInfo(user, sb.getHphm(), ComUtils.cplxToHpzl(sb.getCplx()),
							CommonUtils.getIpAddr(request), kafkaService);
					map.put("vehicle", vehicle);
				}
				map.put("jcdMap", bdService.getJcdMap());
				map.put("cplxMap", bdService.getCplxMap());
				map.put("sb", sb);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/clcx/details";
	}
	/**
	 * ajax查询过车记录
	 * @param tpid
	 * @return
	 */
	@RequestMapping("/getByTpid4Details")
	@Description(moduleName="过车查询",operateType="1",operationName="查询过车记录")
	public void getByTpid4Details(HttpServletRequest request,HttpServletResponse response){
		String tpids = request.getParameter("tpids");
		String index = request.getParameter("index");
		User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		response.setContentType("application/json");
		PrintWriter out = null;
		try {
			if(StringUtils.isNotEmpty(tpids) && StringUtils.isNotEmpty(index)){
				//将传过来的多个图片id拆分
				String[] strs = tpids.split(",");
				//根据传过来的索引查询过车记录
				SbDto dto = searchService.queryByTpid(strs[Integer.valueOf(index)]);
				//查询是否有查询机动车信息的权限
				Role role = roleService.getRoleById(user.getRoleId());
				char isHaveVehiclePermission = role.getPermissionContent().charAt(110);
				Vehicle vehicle = null;
				if(isHaveVehiclePermission == '1'){
					vehicle = ComUtils.getVehicleInfo(user, dto.getHphm(), ComUtils.cplxToHpzl(dto.getCplx()), 
							CommonUtils.getIpAddr(request), kafkaService);
				}
				searchService.tranJcd(dto);
				searchService.tranCplx(dto);
				Object[] obj = new Object[]{dto,vehicle};
				String jsonData = JSON.toJSONString(obj);
				out = response.getWriter();
				out.write(jsonData);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据tpid查询详细信息
	 * @param tpid
	 * @param map
	 * @return
	 */
	@RequestMapping("/getByTpidAjax")
	@Description(moduleName="过车查询",operateType="1",operationName="根据tpid查询详细信息")
	public void getByTpidAjax(String tpid,HttpServletResponse response,HttpServletRequest request){
		try {
			SbDto dto = searchService.queryByTpid(tpid);
			searchService.tranJcd(dto);
			searchService.tranCplx(dto);
			String jsonData = JSON.toJSONString(dto);
			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ajax查询一些过车记录
	 * @param hphm
	 * @param kssj
	 * @param jssj
	 * @param response
	 */
	@RequestMapping("/findSomeSbAjax")
	@Description(moduleName="过车查询",operateType="1",operationName="查询一些过车记录")
	public void findSomeSbAjax(ReqArgs args,HttpServletResponse response,HttpServletRequest request){
		try {
			response.setContentType("application/json");
			if(StringUtils.isNotEmpty(args.getHphm()) && StringUtils.isNotEmpty(args.getKssj()) && StringUtils.isNotEmpty(args.getJssj())){
				//查询过车记录
				args.setFrom(0);
				args.setPagesize(1000);
				args.setSort("tgsj");
				args.setSortType("desc");
				List<SbDto> list = searchService.findSomeVehicleQuery(args);
				response.getWriter().write(JSON.toJSONString(list));
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化全省车辆轨迹查询页面
	 * @return
	 *      用户信息管理页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initQueryRoamOrbit")
	public String initQueryRoamOrbit(Model model, HttpServletRequest request, HttpServletResponse response) {
		String page = "clcx/roamorbit/queryRoamOrbit";
		try {
			//获取角色类型，用户信息来源数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("0002,MYGJDZ");
			String dicJson = JSON.toJSONString(dicList);
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicJson", dicJson);
			
			JDateTime jdt = new JDateTime();
			model.addAttribute("year", jdt.toString("YYYY"));
			model.addAttribute("month", jdt.toString("MM"));
			model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
			model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("cxlb", "1");
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 漫游轨迹查询
	 * @return
	 *      漫游轨迹页面
	 */
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping("/getRoamOrbits")
	@Description(moduleName="漫游轨迹查询",operateType="1",operationName="查询漫游轨迹")
	public String getRoamOrbits(HttpServletRequest request, HttpServletResponse response) {
		List<VehPassrec> roamOrbitList = new ArrayList<VehPassrec>();//所有轨迹集合
		List<VehPassrec> items = new ArrayList<VehPassrec>();//当前页轨迹集合
		List<String> failfureList = new ArrayList<String>();//查询出错的城市集合
		PageResult pageResult = null;
		String result = "1";//1成功，2失败
		int pageNo = 1;
		try {
			//获取参数
			String cphid = request.getParameter("cphid");
			String cpys = request.getParameter("cpys");
			String city = request.getParameter("city");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String pageQuery = request.getParameter("pageQuery");
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			
			//一次开启3个线程
			CountDownLatch threadsSignal;
			RomdOrbitThread thread = null;
			try {
				if("1".equals(pageQuery)){//翻页查询
					//分页
					roamOrbitList.addAll((List<VehPassrec>)request.getSession().getAttribute("roamOrbitList"));
					for(int i=(pageNo-1)*10;i < roamOrbitList.size() && i < ((pageNo-1)*10 + 10);i++){
						VehPassrec vehPassrec = roamOrbitList.get(i);
						vehPassrec.setId(i+1);
						items.add(vehPassrec);
					}
				} else {
					//销毁以前存在session中的数据
					request.getSession().removeAttribute("roamOrbitList");
					
					//拆分城市地址
					String[] cityAddress = city.split(";");
					int threadNum = 3;//每次启动线程数
					for (int j = 0;j < cityAddress.length;j+=threadNum) {
						if(cityAddress.length - j < threadNum){
							threadsSignal = new CountDownLatch(cityAddress.length - j);//创建en-beg个线程,当不足threadNum个时
						} else{
							threadsSignal = new CountDownLatch(threadNum);//创建threadNum个线程
						}
						
						for(int i = j;i < cityAddress.length && i < j + threadNum;i++){
							//判断地址是否为空
							String address = cityAddress[i];
							if(address == null || "".equals(address) || "undefined".equals(address) || address.split("\\|").length <= 1){
								continue;
							}
							
							//启动线程
							thread = new RomdOrbitThread(threadsSignal, cphid, ComUtils.cplxToHpzl(cpys), startTime, endTime, address, failfureList, roamOrbitList);
							thread.start();
						}
						
						//等待线程结束
						threadsSignal.await();
					}
					
					//保存结果在session中
					request.getSession().setAttribute("roamOrbitList", roamOrbitList);
					
					//获取第一页
					for(int i=0;i < roamOrbitList.size() && i < 10;i++){
						VehPassrec vehPassrec = roamOrbitList.get(i);
						vehPassrec.setId(i+1);
						items.add(vehPassrec);
					}
				}
			} catch (InterruptedException e) {
				//查询失败
				result = "0";
				
				e.printStackTrace();
			}//等待线程组执行完成
		} catch (Exception e) {
			//查询失败
			result = "0";
			
			e.printStackTrace();
		} finally{
			//分页轨迹结果
			pageResult = new PageResult(roamOrbitList.size(), pageNo, 10, items);
			request.setAttribute("pageResult", pageResult);
			//返回结果
			request.setAttribute("result", result);
			//错误城市
			String failfure = "";
			for(int i=0;i < failfureList.size();i++){
				failfure += failfureList.get(i) + ";";
			}
			request.setAttribute("failfure", failfure);
			
			//根据显示类型，跳转页面
			String showStyle = request.getParameter("showStyle");
			String page = "clcx/roamorbit/roamOrbitTableList";
			if("1".equals(showStyle)){
				page = "clcx/roamorbit/roamOrbitDetailList";
			} else if("2".equals(showStyle)){
				page = "clcx/roamorbit/roamOrbitTableList";
			} else if("3".equals(showStyle)){
				page = "clcx/roamorbit/roamOrbitPicList";
			}
			
			return page;
		}
	}
	
	/**
	 * 根据id获取漫游详细信息
	 * @param tpid
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	@RequestMapping("/getRoamOrbitById")
	@Description(moduleName="漫游轨迹查询",operateType="1",operationName="查看漫游轨迹详情")
	public String getRoamOrbitById(HttpServletRequest request, HttpServletResponse response){
		String page = "/clcx/roamorbit/roamOrbitDetail";
		VehPassrec vehPassrec = null;
		String result = "1";//1成功，2失败
		try {
			//获取下标
			int id = Integer.parseInt(request.getParameter("id"));
			
			//从session中获取数据
			List<VehPassrec> roamOrbitList = (List<VehPassrec>)request.getSession().getAttribute("roamOrbitList");
			vehPassrec = roamOrbitList.get(id-1);
		} catch (Exception e) {
			result = "2";//1成功，2失败
			e.printStackTrace();
		} finally{
			//返回结果
			request.setAttribute("result", result);
			request.setAttribute("vehPassrec", vehPassrec);
			return page;
		}
	}
}