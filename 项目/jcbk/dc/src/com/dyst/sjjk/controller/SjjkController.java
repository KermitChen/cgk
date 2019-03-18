package com.dyst.sjjk.controller;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jodd.datetime.JDateTime;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.base.utils.PageResult;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.jxkh.service.JxkhService;
import com.dyst.log.annotation.Description;
import com.dyst.sjjk.entities.TjEnitity;
import com.dyst.sjjk.entities.YwtjEnitity;
import com.dyst.sjjk.service.SjjkService;
import com.dyst.systemmanage.entities.Department;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.excel.ExportExcelUtil;
import com.dyst.vehicleQuery.service.PicService;

@Controller
@RequestMapping("/sjjk")
@RemoteProxy
public class SjjkController {
	
	//注入业务层
	@Resource
	private UserService userService;
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	private DispatchedService dispatchedService;
	
	@Autowired
	private SjjkService sjjkService;
	
	@Autowired
	private JcdService jcdService;
	
	@Autowired
	private JxkhService jxkhService;
	
	//查询类型map表
	private Map<String, String> getCxlxMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "按天查询");
		map.put("2", "按月查询");
		map.put("3", "自定义查询");
		return map;
	}
	
	//过车实时监控界面
	@SuppressWarnings("finally")
	@RequestMapping("/initGcsjjk")
	@Description(moduleName = "过车实时监控", operateType="1", operationName = "跳转界面")
	public String initGcsjjk(HttpServletRequest request, HttpServletResponse response) {
		String page = "/sjjk/gcsjjk";
		try {
			//获取车牌颜色数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("0002,CD");
			String dicJson = JSON.toJSONString(dicList);
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicJson", dicJson);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally {
			return page;
		}
	}
	
	//实时过车图片
	@SuppressWarnings("finally")
	@RequestMapping("/loadGcPic")
	@Description(moduleName = "实时过车监控", operateType="1", operationName = "过车详情")
	public String loadGcPic(HttpServletRequest request, HttpServletResponse response) {
		String page = "/sjjk/showGcPic";
		String result = "0";
		String tp1Url = "";
		String tp2Url = "";
		String hphm = "";
		String hpys = "";
		String cdid = "";
		String jcdmc = "";
		String tgsj = "";
		String scsj = "";
		try {
			//获取信息
			String gcInfo = request.getParameter("gcInfo");
			//车牌号码;号牌颜色;号牌颜色翻译;通过时间;上传时间;车道;监测点ID;监测点名称;图片ID
			
			//解析
			if(gcInfo != null && !"".equals(gcInfo.trim())){
				String[] infoArr = gcInfo.split(";");
				if(infoArr.length >= 9){
					//获取图片ID，并调取图片URL
					String tpids = infoArr[8];
					if(tpids != null && !"".equals(tpids.trim())){
						String[] tpidArr = tpids.split(",");
						//将图片id放入列表中
						List<String> idList = new ArrayList<String>();
						if(tpidArr != null && tpidArr.length > 0){
							idList.add(tpidArr[0]);
						}
						if(tpidArr != null && tpidArr.length > 1){
							idList.add(tpidArr[1]);
						}
						
						//调取图片
						List<String> urlList = PicService.getPicUrl(idList);
						if(tpidArr != null && tpidArr.length > 0){
							for (String string : urlList) {
								String tpid = tpidArr[0];
								if(tpid.indexOf("pic") != -1){//hk云，海康云图片的特殊字符需要去掉
									tpid = PicService.changeSpecialSign(tpid);
								}
								if(string.contains(tpid)){
									tp1Url = string;
									break;
								}
							}
						}
						if(tpidArr != null && tpidArr.length > 1){
							for (String string : urlList) {
								String tpid = tpidArr[1];
								if(tpid.indexOf("pic") != -1){//hk云，海康云图片的特殊字符需要去掉
									tpid = PicService.changeSpecialSign(tpid);
								}
								if(string.contains(tpid)){
									tp2Url = string;
									break;
								}
							}
						}
					}
					
					//返回信息      车牌号码;号牌颜色;号牌颜色翻译;通过时间;上传时间;车道;监测点ID;监测点名称;图片ID
					hphm = infoArr[0];
					hpys = infoArr[2];
					tgsj = infoArr[3];
					scsj = infoArr[4];
					cdid = infoArr[5];
					jcdmc = infoArr[7];
				}
			}
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally {
			request.setAttribute("result", result);
			request.setAttribute("tp1Url", tp1Url);
			request.setAttribute("tp2Url", tp2Url);
			request.setAttribute("hphm", hphm);
			request.setAttribute("hpys", hpys);
			request.setAttribute("cdid", cdid);
			request.setAttribute("jcdmc", jcdmc);
			request.setAttribute("tgsj", tgsj);
			request.setAttribute("scsj", scsj);
			return page;
		}
	}
	
	//预警实时监控界面
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping("/initYjsjjk")
	@Description(moduleName = "实时预警监控", operateType="1", operationName = "跳转界面")
	public String initYjsjjk(HttpServletRequest request, HttpServletResponse response) {
		String page = "/sjjk/yjsjjk";
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("0002,HPZL,BKDL,BKDL1,BKDL2,BKDL3");
			request.setAttribute("dicList", JSON.toJSONString(dicList));
			//获取布控类别
			String sql = "select d.ID ID, d.NAME NAME, d.SUPERID SUPERID, d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally {
			return page;
		}
	}
	
	//车牌识别率监测    界面
	@SuppressWarnings("finally")
	@RequestMapping("/initHpsbqkjc")
	@Description(moduleName = "车牌识别率监测", operateType="1", operationName = "跳转界面")
	public String initHpsbqkjc(Model model, HttpServletRequest request, HttpServletResponse response) {
		String page = "/sjjk/hpsbqkjc";
		try {
			JDateTime jdt = new JDateTime();
			model.addAttribute("year", jdt.toString("YYYY"));
			model.addAttribute("month", jdt.toString("MM"));
			model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
			model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("cxlb", "1");
			model.addAttribute("cxlxMap", getCxlxMap());
			
			//获取车道数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("CD");
			String dicJson = JSON.toJSONString(dicList);
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicJson", dicJson);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally {
			return page;
		}
	}
	
	/**
	 * 查询识别率
	 */
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping("/hpsbqkjc")
	@Description(moduleName = "车牌识别率监测", operateType="1", operationName = "车牌识别率监测")
	public String hpsbqkjc(HttpServletRequest request, HttpServletResponse response) {
		String page = "/sjjk/hpsbqkjcList";
		//获取信息
		String jcdid = request.getParameter("jcd");
		String cd = request.getParameter("cd");
		String startTime = request.getParameter("kssj");
		String endTime = request.getParameter("jssj");
		
		List<TjEnitity> hpsbqkjcList = new ArrayList<TjEnitity>();
		String result = "1";
		try {
			//先查询监测点
			String hql = "select new map(id as id, jcdmc as name) from Jcd where id in(" + CommonUtils.changeString(jcdid) + ")";
			List<Object> list = jcdService.findObjects(hql, null);
			Map<String, String> jcdMap = new HashMap<String, String>();
			for (Object object : list) {
				Map mapObj = (Map) object;
				jcdMap.put((String)mapObj.get("id"), (String)mapObj.get("name"));
			}
			
			//查询
			hpsbqkjcList = sjjkService.getYsbWsbData(jcdid, cd, startTime, endTime, jcdMap);
			//如果结果为空，则查询失败
			if(hpsbqkjcList == null){
				result = "0";
			}
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		} finally {
			request.setAttribute("result", result);
			request.getSession().setAttribute("hpsbqkjcList", hpsbqkjcList);
			return page;
		}
	}
	
	/**
	 * 导出号牌识别情况监测报表
	 * @param request
	 * @param resp
	 * @param kssj
	 * @param jssj
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportHpsbqkjcExcel")
	@Description(moduleName = "车牌识别率监测", operateType="1", operationName = "导出车牌识别率监测结果")
	public void exportHpsbqkjcExcel(HttpServletRequest request, HttpServletResponse resp) throws Exception{
		try {
			//获取结果
			List<TjEnitity> hpsbqkjcList = (List<TjEnitity>)request.getSession().getAttribute("hpsbqkjcList");
			
			//输出
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("号牌识别情况监测").append(dateFormat.format(new Date())).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
			//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			ExportExcelUtil.exportHpsbqkjcExcel(hpsbqkjcList, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//卡口在线情况监测    界面
	@SuppressWarnings("finally")
	@RequestMapping("/initKkzxqkjc")
	@Description(moduleName = "卡口在线情况监测", operateType="1",operationName = "跳转界面")
	public String initKkzxqkjc(Model model, HttpServletRequest request, HttpServletResponse response) {
		String page = "/sjjk/kkzxqkjc";
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMinimumFractionDigits(2);
		String zxl = "";
		int zs = 0;
		int zxs = 0;
		try {
			try {
				//总数sql
				zs = sjjkService.getCountByHql("select count(*)from Jcd j left join JCDStatus js on j.id=js.jcdid where j.qybz='1' ", null);
				
				//在线sql
				zxs = sjjkService.getCountByHql("select count(*)from Jcd j left join JCDStatus js on j.id=js.jcdid where j.qybz='1' and js.id IS NULL", null);
			
				//计算在线率
				if(zs == 0){
					zxl = percentFormat.format(0);
				} else{
					zxl = percentFormat.format((double)(zxs)/(zs*1.0));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally {
			request.getSession().setAttribute("zxl", zxl);
			request.getSession().setAttribute("zs", zs);
			request.getSession().setAttribute("zxs", zxs);
			return page;
		}
	}
	
	/**
	 * 跳转到设备状态监控
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/kkzxqkjc")
	@Description(moduleName = "卡口在线情况监测", operateType="1", operationName = "卡口在线情况监测")
	public String kkzxqkjc(HttpServletRequest request, HttpServletResponse response){
		String page = "/sjjk/kkzxqkjcList";
		PageResult pageResult = null;
		try {
			//获取信息
			String jcdid = request.getParameter("jcdid");
			String jcdmc = request.getParameter("jcdmc");
			String cqNo = request.getParameter("cqNo");
			String roadNo = request.getParameter("roadNo");
			String jcdzt = request.getParameter("jcdzt");
			int pageNo = Integer.parseInt(request.getParameter("pageNo"));
			
			//根据Hql 单表、带分页、多条件查询
			StringBuffer hql1 = new StringBuffer();
			hql1.append("from Jcd j left join JCDStatus js on j.id=js.jcdid where j.qybz='1' ");
			Map<String,Object> params = new HashMap<String, Object>();
			if(jcdid != null && jcdid.length() >= 1){
				hql1.append(" and j.id= :Check_JcdID");
				params.put("Check_JcdID", jcdid);
			}
			if(jcdmc != null && !"".equals(jcdmc.trim())){
				hql1.append(" and j.jcdmc like :Check_JcdName");
				params.put("Check_JcdName", "%" + jcdmc + "%");
			}
			if(cqNo != null && !"".equals(cqNo.trim())){
				String[] areanames = cqNo.split(",");
				hql1.append(" and j.cqid in(:ids)");
				params.put("ids", areanames);
			}
			if(roadNo != null && !"".equals(roadNo.trim())){
				hql1.append(" and j.dlid = :Check_roadNo");
				params.put("Check_roadNo", roadNo);
			}
			if(jcdzt != null && "0".equals(jcdzt.trim())){//异常
				hql1.append(" and js.id IS NOT NULL");
			}
			if(jcdzt != null && "1".equals(jcdzt.trim())){//正常
				hql1.append(" and js.id IS NULL");
			}
			
			pageResult = jcdService.getPageResult(hql1.toString(), params, pageNo, 10);
			request.setAttribute("pageResult", pageResult);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 导出卡口在线情况监测报表
	 * @param request
	 * @param resp
	 * @param kssj
	 * @param jssj
	 * @throws Exception
	 */
	@RequestMapping(value="exportKkzxqkjcExcel")
	@Description(moduleName = "卡口在线情况监测", operateType="1", operationName = "导出卡口在线情况监测结果")
	public void exportKkzxqkjcExcel(HttpServletRequest request, HttpServletResponse resp) throws Exception{
		try {
			//获取信息
			String jcdid = request.getParameter("Check_JcdID");
			String jcdmc = request.getParameter("Check_JcdName");
			String jcdzt = request.getParameter("jcdzt");
			
			//根据Hql 获取结果
			StringBuffer hql1 = new StringBuffer();
			hql1.append("from Jcd j left join JCDStatus js on j.id=js.jcdid where j.qybz='1' ");
			Map<String,Object> params = new HashMap<String, Object>();
			String flag = "1";
			if(jcdid != null && jcdid.length() >= 1){
				hql1.append(" and j.id= :Check_JcdID");
				params.put("Check_JcdID", jcdid);
				flag = "0";
			}
			if(jcdmc != null && !"".equals(jcdmc.trim())){
				hql1.append(" and j.jcdmc like :Check_JcdName");
				params.put("Check_JcdName", "%" + jcdmc + "%");
				flag = "0";
			}
			if(jcdzt != null && "0".equals(jcdzt.trim())){//异常
				hql1.append(" and js.id IS NOT NULL");
				flag = "0";
			}
			if(jcdzt != null && "1".equals(jcdzt.trim())){//正常
				hql1.append(" and js.id IS NULL");
				flag = "0";
			}
			List<Object> kkzxqkjcList = jcdService.findObjects2(hql1.toString(), params);
			
			//在线率
			String zxl = (String)request.getSession().getAttribute("zxl");
			//总数
			int zs = Integer.parseInt(request.getSession().getAttribute("zs").toString());
			//在线数
			int zxs = Integer.parseInt(request.getSession().getAttribute("zxs").toString());
			
			//输出
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("卡口在线情况监测").append(dateFormat.format(new Date())).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
			//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			//生成报表，并导出
			ExportExcelUtil.exportKkzxqkjcExcel(kkzxqkjcList, flag, zxl, zs, zxs, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//数据传输情况监测    界面
	@SuppressWarnings("finally")
	@RequestMapping("/initSjcsqkjc")
	@Description(moduleName = "数据传输情况监测", operateType="1", operationName = "跳转界面")
	public String initSjcsqkjc(Model model, HttpServletRequest request, HttpServletResponse response) {
		String page = "/sjjk/sjcsqkjc";
		try {
			JDateTime jdt = new JDateTime();
			model.addAttribute("year", jdt.toString("YYYY"));
			model.addAttribute("month", jdt.toString("MM"));
			model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
			model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("cxlb", "1");
			model.addAttribute("cxlxMap", getCxlxMap());
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally {
			return page;
		}
	}
	
	/**
	 * 查询数据传输情况
	 */
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping("/sjcsqkjc")
	@Description(moduleName = "数据传输情况监测", operateType="1", operationName = "数据传输情况监测")
	public String sjcsqkjc(HttpServletRequest request, HttpServletResponse response) {
		String page = "/sjjk/sjcsqkjcList";
		//获取信息
		String jcdid = request.getParameter("jcd");
		String startTime = request.getParameter("kssj");
		String endTime = request.getParameter("jssj");
		String csbz = request.getParameter("csbz");
		
		List<TjEnitity> sjcsqkjcList = new ArrayList<TjEnitity>();
		String result = "1";
		try {
			//先查询监测点
			String hql = "select new map(id as id, jcdmc as name) from Jcd where id in(" + CommonUtils.changeString(jcdid) + ")";
			List<Object> list = jcdService.findObjects(hql, null);
			Map<String, String> jcdMap = new HashMap<String, String>();
			for (Object object : list) {
				Map mapObj = (Map) object;
				jcdMap.put((String)mapObj.get("id"), (String)mapObj.get("name"));
			}
			
			//查询
			sjcsqkjcList = sjjkService.getSjcsqkData(jcdid, startTime, endTime, csbz, jcdMap);
			
			//如果结果为空，则查询失败
			if(sjcsqkjcList == null){
				result = "0";
			}
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		} finally {
			request.setAttribute("result", result);
			request.getSession().setAttribute("sjcsqkjcList", sjcsqkjcList);
			return page;
		}
	}
	
	/**
	 * 导出数据传输情况监测报表
	 * @param request
	 * @param resp
	 * @param kssj
	 * @param jssj
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportSjcsqkjcExcel")
	@Description(moduleName = "数据传输情况监测", operateType="1", operationName = "导出数据传输情况监测结果")
	public void exportSjcsqkjcExcel(HttpServletRequest request, HttpServletResponse resp) throws Exception{
		try {
			//获取结果
			List<TjEnitity> hpsbqkjcList = (List<TjEnitity>)request.getSession().getAttribute("sjcsqkjcList");
			
			//输出
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("数据传输情况监测").append(dateFormat.format(new Date())).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
			//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			ExportExcelUtil.exportSjcsqkjcExcel(hpsbqkjcList, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//业务处置情况监测    界面
	@SuppressWarnings("finally")
	@RequestMapping("/initYwczqkjc")
	@Description(moduleName = "业务处置情况监测", operateType="1", operationName = "跳转界面")
	public String initYwczqkjc(Model model, HttpServletRequest request, HttpServletResponse response) {
		String page = "/sjjk/ywczqkjc";
		try {
			JDateTime jdt = new JDateTime();
			model.addAttribute("year", jdt.toString("YYYY"));
			model.addAttribute("month", jdt.toString("MM"));
			model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
			model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("cxlb", "1");
			model.addAttribute("cxlxMap", getCxlxMap());
			
			//绩效考核部门
			List<Department> deptsList = getJxkhDepts();
			request.setAttribute("deptsListOfJson", JSON.toJSONString(deptsList));
			request.getSession().setAttribute("deptsList", deptsList);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally {
			return page;
		}
	}
	
	/**
	 * 查询业务处置情况
	 */
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping("/ywczqkjc")
	@Description(moduleName = "业务处置情况监测", operateType="1", operationName = "业务处置情况监测")
	public String ywczqkjc(HttpServletRequest request, HttpServletResponse response) {
		String page = "/sjjk/ywczqkjcList";
		//获取信息
		String startTime = request.getParameter("kssj");
		String endTime = request.getParameter("jssj");
		String khbm = request.getParameter("khbm");
		
		String result = "1";
		List<YwtjEnitity> ywczqkjcList = new ArrayList<YwtjEnitity>();
		try {
			//查询
			ywczqkjcList = sjjkService.getYwczqk(startTime, endTime, khbm, (List<Department>)request.getSession().getAttribute("deptsList"));
			//如果结果为空，则查询失败
			if(ywczqkjcList == null){
				result = "0";
			}
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		} finally {
			request.setAttribute("result", result);
			request.getSession().setAttribute("ywczqkjcList", ywczqkjcList);
			return page;
		}
	}
	
	/**
	 * 导出业务处置情况监测报表
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportYwczqkjcExcel")
	@Description(moduleName = "业务处置情况监测", operateType="1", operationName = "导出业务处置情况监测结果")
	public void exportYwczqkjcExcel(HttpServletRequest request, HttpServletResponse resp) throws Exception{
		try {
			//获取结果
			List<YwtjEnitity> ywczqkjcList = (List<YwtjEnitity>)request.getSession().getAttribute("ywczqkjcList");
			
			//输出
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("业务处置情况监测").append(dateFormat.format(new Date())).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
			//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			ExportExcelUtil.exportYwczqkjcExcel(ywczqkjcList, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Department> getJxkhDepts(){
		String hql = " from Department d where d.jxkh = '1' ";
		return jxkhService.getDeptList(hql, null);
	}
}