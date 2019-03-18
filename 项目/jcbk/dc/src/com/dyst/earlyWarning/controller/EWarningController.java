package com.dyst.earlyWarning.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.datetime.JDateTime;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.earlyWarning.entities.EWRecieve;
import com.dyst.earlyWarning.entities.EWarning;
import com.dyst.earlyWarning.entities.Instruction;
import com.dyst.earlyWarning.entities.InstructionSign;
import com.dyst.earlyWarning.entities.Ya;
import com.dyst.earlyWarning.service.EWarningService;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.BaiduapiOffline;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.Config;
import com.dyst.utils.IntefaceUtils;
import com.dyst.utils.ReadConfig;
import com.dyst.utils.StaticUtils;
import com.dyst.utils.Tools;
import com.dyst.utils.XmlCreater;
import com.dyst.vehicleQuery.dto.SbDto;
import com.dyst.vehicleQuery.service.PicService;


@Controller
@RequestMapping("/earlyWarning")
@RemoteProxy
public class EWarningController {
	//注入业务层
	@Autowired
	private EWarningService eWarningService;
	@Autowired
	private JcdService jcdService;
	@Autowired
	private UserService userService;
	@Autowired
	private DispatchedService dispatchedService;
	@Resource
	private PicService picSearch;
	
	//查询类型map表
	private Map<String, String> getCxlxMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "按天查询");
		map.put("2", "按月查询");
		map.put("3", "自定义查询");
		return map;
	}
	
	/**
	 * 跳转到实时预警
	 * @return
	 */
	@SuppressWarnings({ "finally", "rawtypes" })
	@RequestMapping("/loadActualEWarning")
	@Description(moduleName="预警管理",operateType="1",operationName="跳转实时预警")
	public String loadActualEWarning(HttpServletRequest request, HttpServletResponse response){
		String page = "/earlyWarning/queryActualEWarning";
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,BKDL,BKDL1,BKDL2,BKDL3");
			request.setAttribute("dicList", JSON.toJSONString(dicList));
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			request.setAttribute("userId", user.getLoginName());
			//指挥部指挥处的sysNo
//			String sysNo = "0";
//			List<Department> deptList1 = deptService.getDeptByDeptNo("440300010100");
//			if(deptList1.size() > 0){
//				sysNo = deptList1.get(0).getSystemNo();
//			}
//			if(sysNo.length() > 0 && user.getSystemNo().substring(0, sysNo.length()).equals(sysNo)){//市局指挥处
//				request.setAttribute("isCity", "true");
//			}else{
//				request.setAttribute("isCity", "false");
//			}
			//市局
			if("91".equals(user.getPosition())){
				request.setAttribute("isCity", "true");
			} else{
				request.setAttribute("isCity", "false");
			}
//			eWarningService.sendDWRMsg("");//测试推送消息用
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			request.setAttribute("openTime", simpleDateFormat.format(new Date()));
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}  
	
	/**
	 * 跳转预警签收确认
	 * @return
	 */
	@SuppressWarnings({ "finally", "rawtypes" })
	@RequestMapping("/loadEWarningConfirm")
	@Description(moduleName="预警管理",operateType="1",operationName="跳转预警签收确认")
	public String loadEWarningConfirm(HttpServletRequest request, HttpServletResponse response, int qsid, String isSign){
		String page = "/earlyWarning/eWarningConfirm";
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,YJQRZT,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,BKYWZT");
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
			
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklb",bklbList);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			
			//签收的预警
			EWRecieve ewRecieve = eWarningService.findEWRecieveById(qsid);
			request.setAttribute("ewRecieve", ewRecieve);
			request.setAttribute("isSign", isSign);
			
			//加载图片
			SbDto sbDto = new SbDto();
			if(ewRecieve != null && ewRecieve.getTpid() != null && !"".equals(ewRecieve.getTpid().trim())){
				//解析图片ID
				String[] picArr = ewRecieve.getTpid().split(",");
				if(picArr.length >= 1){
					sbDto.setTp1(picArr[0]);
				}
				if(picArr.length > 1){
					sbDto.setTp2(picArr[1]);
				}
				
				//调用图片
				List<SbDto> list = new ArrayList<SbDto>();
				list.add(sbDto);
				picSearch.findPic(list);
			}
			request.setAttribute("sbDto", sbDto);
			
			//预警记录
			List<EWarning> ewarningList = dispatchedService.findEWaringList(ewRecieve.getBkid());
			request.setAttribute("ewarningList", ewarningList);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	} 
	
	/**
     * 加载实时预警
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/loadAEW")
	@Description(moduleName="预警管理",operateType="1",operationName="加载实时预警数据")
	public void loadAEW(HttpServletRequest request, HttpServletResponse response, String hphm) {
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			request.setAttribute("userId", user.getLoginName());
			List<Map> result = new ArrayList<Map>();
			List<Map> last = new ArrayList<Map>();

			String sql = "select qsid, bkid, bjxh, hphm, hpzl, jcdid, jcdmc, jd, wd, cdid, tpid, sd, tgsj, scsj, bjsj, bjdl, bklb, bjlx, bjbm, bjbmmc, qrzt from YJQS where qrzt='0' and bjbm=? ";
			List<Object> param = new ArrayList<Object>();
			param.add(user.getLskhbm());
			if(hphm != null && !"".equals(hphm.trim())){
				sql += " and hphm like ? ";
				param.add("%" + hphm.trim() + "%");
			}
			sql += " order by qsid asc ";
			result = eWarningService.findList(sql, param);

			//经纬度转换
			if(result.size() > 0){
				for(Map map : result){
					if(map.get("jd") != null && map.get("wd") != null && !"0.0".equals(map.get("jd").toString()) && !"0.0".equals(map.get("wd").toString())){
	//					double[] bdGis = MapConvertor.wgs2bd(j.getJd(), j.getWd());//这个目前精确度欠缺
						double[] bdGis = BaiduapiOffline.transform(Double.parseDouble(map.get("wd").toString()),Double.parseDouble(map.get("jd").toString()));
						map.put("jd", bdGis[0]);
						map.put("wd", bdGis[1]);
						last.add(map);
					} else {
						last.add(map);
					}
				}
			}
			response.getWriter().write(JSON.toJSONString(last, SerializerFeature.DisableCircularReferenceDetect));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 预警签收
     */
	@RequestMapping("/sighEW")
	@Description(moduleName="预警管理",operateType="3",operationName="签收预警")
	public void sighEW(HttpServletRequest request,HttpServletResponse response, int qsid, 
			String qrzt, String lxdh, String qrjg, String ljtj) {
		Map<String,String> result = new HashMap<String,String>();
		Date nowDate = new Date();
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			request.setAttribute("userId", user.getLoginName());
			EWRecieve eWRecieve = eWarningService.findEWRecieveById(qsid);
			if("0".equals(eWRecieve.getQrzt())){
				eWRecieve.setQrzt(qrzt);
				eWRecieve.setQrrjh(user.getLoginName());
				eWRecieve.setQrr(user.getUserName());
				eWRecieve.setQrdwdm(user.getDeptId());
				eWRecieve.setQrdwdmmc(user.getDeptName());
				eWRecieve.setQrsj(nowDate);
				eWRecieve.setQrdwlxdh(lxdh);
				eWRecieve.setQrjg(qrjg);
				eWRecieve.setJyljtj(ljtj);
				eWRecieve.setGxsj(nowDate);
				
				String flag = "0";//调用省预警签收接口   0未调用    && "2".equals(eWRecieve.getDispatched().getXxly())
				//调用省厅接口，传递至省厅    布控信息必须是省厅布控过来的
				if("00".equals(eWRecieve.getDispatched().getBklb()) && "91".equals(user.getPosition()) 
						&& "1".equals(Config.getInstance().getStInterfaceFlag().split(":")[2])){
					flag = IntefaceUtils.sendEWRecieve(eWRecieve);
				}
				eWRecieve.setBy3(flag);
				
				//插入信息
				eWarningService.update(eWRecieve);
				
				//将预警在PGIS上显示
		    	if("81".equals(user.getPosition()) || "91".equals(user.getPosition())){
		    		//封装信息，并写到指定地方，并返回xml地址
					String filePath = XmlCreater.yjToXml("01", CommonUtils.getIpAddr(request), eWRecieve);
						
					//发送至PGIS
					if(filePath != null && !"".equals(filePath.trim())){
						IntefaceUtils.sendXmlToPGIS(CommonUtils.getIpAddr(request), 8900, new File(filePath));
					}
				}
				
				result.put("success", "success");
				response.getWriter().write(JSON.toJSONString(result));
			} else{
				result.put("success", "repeat");
				response.getWriter().write(JSON.toJSONString(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 获取图片
     */
	@RequestMapping("/getPic")
	@Description(moduleName="预警管理",operateType="1",operationName="查询图片")
	public void getPic(HttpServletRequest request,HttpServletResponse response,String tpid) {
		try {
			response.getWriter().write(PicService.getPicByTpid(tpid));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到预警签收查询
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/queryEWRecieve")
	@Description(moduleName="预警管理",operateType="1",operationName="跳转预警签收查询")
	public String queryEWRecieve(Model model, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, PageResult pageResult,
			String kssj, String jssj, String hphm, String hpzl, String qszt){
		String page = "/earlyWarning/queryEWRecieve";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dayformt = new SimpleDateFormat("yyyy-MM-dd");
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			String cxrq = request.getParameter("cxrq");
			if(cxrq == null || "".equals(cxrq.trim())){
				JDateTime jdt = new JDateTime();
				Date nowDate = new Date();
				model.addAttribute("year", jdt.toString("YYYY"));
				model.addAttribute("month", jdt.toString("MM"));
				model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
				model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
				model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
				model.addAttribute("cxlb", "1");
				model.addAttribute("cxlxMap", getCxlxMap());
				kssj = dayformt.format(nowDate) + " 00:00:00";
				jssj = formatter.format(nowDate);
			} else {
				map.put("year", request.getParameter("year"));
				map.put("month", request.getParameter("month"));
				map.put("cxrq", request.getParameter("cxrq"));
				map.put("cxlb", request.getParameter("cxlb"));
				map.put("kssj", kssj);
				map.put("jssj", jssj);
				map.put("cxlxMap", getCxlxMap());
			}
			
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,YJQRZT,BKDL,BKDL1,BKDL2,BKDL3");
			request.setAttribute("dicList", dicList);
			
			//根据条件查询
			String hql = " from EWRecieve e where bjbm=? ";
			List<Object> param = new ArrayList<Object>();
			param.add(user.getLskhbm());//预警接收部门
			if(Tools.isNotEmpty(hphm)){
				hql += " and e.hphm like ? ";
				param.add("%" +hphm + "%");
			}
			if(Tools.isNotEmpty(hpzl)){
				hql += " and e.hpzl = ? ";
				param.add(hpzl);
			}
			if(Tools.isNotEmpty(qszt)){
				hql += " and e.qrzt = ? ";
				param.add(qszt);
			}
			if(Tools.isNotEmpty(kssj)){
				hql += " and e.tgsj > ? ";
				param.add(formatter.parse(kssj));
			}
			if(Tools.isNotEmpty(jssj)){
				hql += " and e.tgsj < ? ";
				param.add(formatter.parse(jssj));
			}
			hql += " order by e.qsid desc ";
			pageResult = eWarningService.getPageResult(hql, param, pageResult.getPageNo(), 10);
			
			//市局
			if("91".equals(user.getPosition())){
				request.setAttribute("isCity", "1");
			} else{
				request.setAttribute("isCity", "0");
			}
			
			request.setAttribute("kssj", kssj);
			request.setAttribute("jssj", jssj);
			request.setAttribute("hphm", hphm);
			request.setAttribute("hpzl", hpzl);
			request.setAttribute("qszt", qszt);
			request.setAttribute("pageResults", pageResult);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 预警信息查询（不查lsyj表）
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/queryEWarning")
	@Description(moduleName="预警管理",operateType="1",operationName="分页查询预警信息")
	public String queryEWarning(Model model, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, PageResult pageResult, 
			String kssj, String jssj, String hphm, String hpzl, String cplx){
		String page = "/earlyWarning/queryEWarning";
		try {
			//获取所有带坐标的监测点
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dayformt = new SimpleDateFormat("yyyy-MM-dd");
			String cxrq = request.getParameter("cxrq");
			if(cxrq == null || "".equals(cxrq.trim())){
				JDateTime jdt = new JDateTime();
				Date nowDate = new Date();
				model.addAttribute("year", jdt.toString("YYYY"));
				model.addAttribute("month", jdt.toString("MM"));
				model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
				model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
				model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
				model.addAttribute("cxlb", "1");
				model.addAttribute("cxlxMap", getCxlxMap());
				kssj = dayformt.format(nowDate) + " 00:00:00";
				jssj = formatter.format(nowDate);
			} else {
				map.put("year", request.getParameter("year"));
				map.put("month", request.getParameter("month"));
				map.put("cxrq", request.getParameter("cxrq"));
				map.put("cxlb", request.getParameter("cxlb"));
				map.put("kssj", kssj);
				map.put("jssj", jssj);
				map.put("cxlxMap", getCxlxMap());
			}
			
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,YJQRZT,0002");
			request.setAttribute("dicList", dicList);
			//根据条件查询
			String hql = " from EWarning ewa where 1=1 ";
			List<Object> param = new ArrayList<Object>();
			if(Tools.isNotEmpty(hphm)){
				hql += " and ewa.hphm like ? ";
				param.add("%" +hphm + "%");
			}
			if(Tools.isNotEmpty(hpzl)){
				hql += " and ewa.hpzl = ? ";
				param.add(hpzl);
			}
			if(Tools.isNotEmpty(cplx)){
				hql += " and ewa.cplx = ? ";
				param.add(cplx);
			}
			if(Tools.isNotEmpty(kssj)){
				hql += " and ewa.tgsj >= ? ";
				param.add(formatter.parse(kssj));
			}
			if(Tools.isNotEmpty(jssj)){
				hql += " and ewa.tgsj <= ? ";
				param.add(formatter.parse(jssj));
			}
			hql += " order by ewa.bjxh desc ";
			pageResult = eWarningService.getPageResult(hql, param, pageResult.getPageNo(), 10);
			request.setAttribute("kssj", kssj);
			request.setAttribute("jssj", jssj);
			request.setAttribute("hphm", hphm);
			request.setAttribute("hpzl", hpzl);
			request.setAttribute("cplx", cplx);
			request.setAttribute("pageResults", pageResult);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 跳转到预警信息查询
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/queryEWRecieveForEWarning")
	@Description(moduleName="预警管理",operateType="1",operationName="跳转预警信息查询")
	public String queryEWRecieveForEWarning(Model model, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, PageResult pageResult,
			String kssj, String jssj, String hphm, String hpzl, String cplx){
		String page = "/earlyWarning/queryEWarning";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dayformt = new SimpleDateFormat("yyyy-MM-dd");
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			String cxrq = request.getParameter("cxrq");
			if(cxrq == null || "".equals(cxrq.trim())){
				JDateTime jdt = new JDateTime();
				Date nowDate = new Date();
				model.addAttribute("year", jdt.toString("YYYY"));
				model.addAttribute("month", jdt.toString("MM"));
				model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
				model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
				model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
				model.addAttribute("cxlb", "1");
				model.addAttribute("cxlxMap", getCxlxMap());
				kssj = dayformt.format(nowDate) + " 00:00:00";
				jssj = formatter.format(nowDate);
			} else {
				map.put("year", request.getParameter("year"));
				map.put("month", request.getParameter("month"));
				map.put("cxrq", request.getParameter("cxrq"));
				map.put("cxlb", request.getParameter("cxlb"));
				map.put("kssj", kssj);
				map.put("jssj", jssj);
				map.put("cxlxMap", getCxlxMap());
			}
			
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,YJQRZT,0002,BKDL,BKDL1,BKDL2,BKDL3");
			request.setAttribute("dicList", dicList);
			
			//根据条件查询
			String hql = " from EWRecieve e where bjbm=? ";
			List<Object> param = new ArrayList<Object>();
			param.add(user.getLskhbm());//预警接收部门
			if(Tools.isNotEmpty(hphm)){
				hql += " and e.hphm like ? ";
				param.add("%" +hphm + "%");
			}
			if(Tools.isNotEmpty(hpzl)){
				hql += " and e.hpzl = ? ";
				param.add(hpzl);
			}
			if(Tools.isNotEmpty(cplx)){
				hql += " and e.cplx = ? ";
				param.add(cplx);
			}
			if(Tools.isNotEmpty(kssj)){
				hql += " and e.tgsj > ? ";
				param.add(formatter.parse(kssj));
			}
			if(Tools.isNotEmpty(jssj)){
				hql += " and e.tgsj < ? ";
				param.add(formatter.parse(jssj));
			}
			hql += " order by e.qsid desc ";
			pageResult = eWarningService.getPageResult(hql, param, pageResult.getPageNo(), 10);
			
			request.setAttribute("kssj", kssj);
			request.setAttribute("jssj", jssj);
			request.setAttribute("hphm", hphm);
			request.setAttribute("hpzl", hpzl);
			request.setAttribute("cplx", cplx);
			request.setAttribute("pageResults", pageResult);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 跳转预警信息详情
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/loadEWarningDetail")
	@Description(moduleName="预警管理",operateType="1",operationName="预警详情")
	public String loadEWarningDetail(HttpServletRequest request, HttpServletResponse response,int bjxh){
		String page = "/earlyWarning/eWarningDetail";
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,YJQRZT,CSYS");
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
			
			//预警信息
			EWarning ewarning =  eWarningService.findEWarningById(bjxh);
			request.setAttribute("ewarning", ewarning);
			
			//调用图片
			SbDto sbDto = new SbDto();
			if(ewarning != null && ewarning.getTpid() != null && !"".equals(ewarning.getTpid().trim())){
				//解析图片ID
				String[] picArr = ewarning.getTpid().split(",");
				if(picArr.length >= 1){
					sbDto.setTp1(picArr[0]);
				}
				if(picArr.length > 1){
					sbDto.setTp2(picArr[1]);
				}
				
				//调用图片
				List<SbDto> list = new ArrayList<SbDto>();
				list.add(sbDto);
				picSearch.findPic(list);
			}
			request.setAttribute("sbDto", sbDto);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 跳转预警信息详情
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/loadEWRecieveDetail")
	@Description(moduleName="预警管理",operateType="1",operationName="预警签收详情")
	public String loadEWRecieveDetail(HttpServletRequest request, HttpServletResponse response, int qsid){
		String page = "/earlyWarning/eWRecieveDetail";
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,YJQRZT,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,BKYWZT,XDZLFS,SFLJ,WLJDYY,CJCZJG");
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
			
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklb",bklbList);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			
			//签收的预警
			EWRecieve ewRecieve = eWarningService.findEWRecieveById(qsid);
			request.setAttribute("ewRecieve", ewRecieve);
			
			//调用图片
			SbDto sbDto = new SbDto();
			if(ewRecieve != null && ewRecieve.getTpid() != null && !"".equals(ewRecieve.getTpid().trim())){
				//解析图片ID
				String[] picArr = ewRecieve.getTpid().split(",");
				if(picArr.length >= 1){
					sbDto.setTp1(picArr[0]);
				}
				if(picArr.length > 1){
					sbDto.setTp2(picArr[1]);
				}
				
				//调用图片
				List<SbDto> list = new ArrayList<SbDto>();
				list.add(sbDto);
				picSearch.findPic(list);
			}
			request.setAttribute("sbDto", sbDto);
			
			//获取指令
			Instruction instruction = eWarningService.findInstructionByQsId(ewRecieve.getQsid());
			request.setAttribute("instruction", instruction);
			
			//获取指令签收
			if(instruction != null){
				List<Object> param = new ArrayList<Object>();
				param.add(instruction.getId());
				List<InstructionSign> instructionSignList = eWarningService.findObjects("FROM InstructionSign where zlid = ?", param);
				request.setAttribute("instructionSignList", instructionSignList);
			}
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 跳转预警签收情况
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/showEWRecievesByBjxh")
	@Description(moduleName="预警管理",operateType="1",operationName="预警签收情况")
	public String showEWRecievesByBjxh(HttpServletRequest request, HttpServletResponse response, int bjxh){
		String page = "/earlyWarning/showEWRecieves";
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,YJQRZT,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,BKYWZT,XDZLFS,SFLJ,WLJDYY,CJCZJG");
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
			
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklb",bklbList);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			
			//获取预警的所有签收记录
			List<Object> param = new ArrayList<Object>();
			param.add(bjxh);
			List<EWRecieve> eWRecieveList = eWarningService.findObjects("FROM EWRecieve where bjxh = ?", param);
			request.setAttribute("eWRecieveList", eWRecieveList);
			//取出一个签收信息
			if(eWRecieveList != null && eWRecieveList.size() > 0){
				EWRecieve ewRecieve = eWRecieveList.get(0);
				request.setAttribute("ewRecieve", ewRecieve);
				
				//调用图片
				SbDto sbDto = new SbDto();
				if(ewRecieve != null && ewRecieve.getTpid() != null && !"".equals(ewRecieve.getTpid().trim())){
					//解析图片ID
					String[] picArr = ewRecieve.getTpid().split(",");
					if(picArr.length >= 1){
						sbDto.setTp1(picArr[0]);
					}
					if(picArr.length > 1){
						sbDto.setTp2(picArr[1]);
					}
					
					//调用图片
					List<SbDto> list = new ArrayList<SbDto>();
					list.add(sbDto);
					picSearch.findPic(list);
				}
				request.setAttribute("sbDto", sbDto);
			}
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 跳转指令下发
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/loadInstruction")
	@Description(moduleName="预警管理",operateType="1",operationName="跳转指令下发")
	public String loadInstruction(HttpServletRequest request, HttpServletResponse response, int qsid){
		String page = "/earlyWarning/sendInstruction";
		try {
			//有人值守的卡点
			String sql = "SELECT z.*, p.POSTCODE, COUNT(bb.POLICENUM) worknum FROM ZAKD z LEFT JOIN  POSTAREAR p ON p.AREACODE=z.CODE " +
					" LEFT JOIN BBDETAILINFO_3D bb ON bb.POSTCODE=p.POSTCODE AND bb.STARTTIME < NOW() AND bb.ENDTIME > NOW() GROUP BY z.ID ";
			List<Map> list =  eWarningService.findList(sql, null);
			List<Map> zakd = new ArrayList();
			for(Map map : list){//坐标转换
				double[] bdGis = BaiduapiOffline.transform(Double.parseDouble(map.get("Y").toString()), Double.parseDouble(map.get("X").toString()));
				//原坐标
				map.put("aX", map.get("X"));
				map.put("aY", map.get("Y"));
				
				//转换坐标
				map.remove("X");
				map.remove("Y");
				map.put("X", bdGis[0]);
				map.put("Y", bdGis[1]);
				zakd.add(map);
			}

			//签收信息
			EWRecieve eWRecieve = eWarningService.findEWRecieveById(qsid);
			
			//查找当前预警的坐标
			String hql = "from Jcd where ID = ? ";
			List<Jcd> jcdList = jcdService.findObject(hql, eWRecieve.getJcdid());
			Jcd jcd = null;
			if(jcdList.size() > 0){
				jcd = jcdList.get(0);
				
				//保存原坐标
				Double jd = jcd.getJd();
				Double wd = jcd.getWd();
				if(jd != null && wd != null && jcd.getWd() != 0 && jcd.getJd() != 0) {
					jcd.setPy(jd + ":" + wd);
				} else {
					jcd.setPy("0:0");
				}
				
				//坐标转换
				if(jcd.getWd() != 0 && jcd.getJd() != 0){
					double[] bdGis = BaiduapiOffline.transform(jcd.getWd(), jcd.getJd());
					jcd.setJd(bdGis[0]);
					jcd.setWd(bdGis[1]);
				} else {
					jcd.setJd(0.0);
					jcd.setWd(0.0);
				}
			}
			
			//获取24小时内的相关预警信息
			QueryHelper queryHelper = new QueryHelper(EWRecieve.class, "ewa");
			queryHelper.addCondition("ewa.hphm = ?", eWRecieve.getHphm());
			queryHelper.addCondition("ewa.hpzl = ?", eWRecieve.getHpzl());
			queryHelper.addCondition("ewa.qrzt = ?", "1");
			queryHelper.addCondition("ewa.bjxh != ?", eWRecieve.getBjxh());
			Date now = new Date();
			queryHelper.addCondition("ewa.qrsj > ?", new Date(now.getTime()-(24* 3600000)));//时间不超过一天
			queryHelper.addOrderByProperty("ewa.tgsj ", "desc");
			List<EWRecieve> ewList = eWarningService.findObjects(queryHelper);
			if(ewList.size() > 0){
				for(EWRecieve e : ewList){
					if(e.getJd() != 0 && e.getWd() != 0){
						double[] bdGis = BaiduapiOffline.transform(e.getWd(), e.getJd());
						e.setJd(bdGis[0]);//坐标转换
						e.setWd(bdGis[1]);
					} else {
						e.setJd(0.0);//坐标转换
						e.setWd(0.0);
					}
				}
			}
			
			request.setAttribute("ewarning", JSON.toJSONString(eWRecieve));
			request.setAttribute("ewList", JSON.toJSONString(ewList));
			request.setAttribute("jcd", JSON.toJSONString(jcd));
			request.setAttribute("zakd", JSON.toJSONString(zakd));
			
			//通知单位
			String sql1 = "select dept_no,dept_name from DEPARTMENT where jxkh='1'";
			List<Map> depts = dispatchedService.findList(sql1, null);
			request.setAttribute("jxkhDepts", JSON.toJSONString(depts));
			
			//获取默认值
			int defaultSpeed = Integer.parseInt(ReadConfig.getPropertiesValue("activiti", "defaultSpeed"));
			int rangeX = Integer.parseInt(ReadConfig.getPropertiesValue("activiti", "rangeX"));
			request.setAttribute("defaultSpeed", defaultSpeed);
			request.setAttribute("rangeX", rangeX);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	} 
	/**
     * 下发指令
     */
	@RequestMapping("/sendInstruction")
	@Description(moduleName="预警管理",operateType="2",operationName="下发指令")
	public void sendInstruction(HttpServletRequest request, HttpServletResponse response, 
			int bjxh, int qsid, String hphm, String hpzl, String jqjb, String zlfs, String yanr, String[] lastKD, String zldw) {
		String result = "success";
		try {
			//用户信息
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			//签收信息
			EWRecieve eWRecieve = eWarningService.findEWRecieveById(qsid);
			if(eWRecieve == null){
				result = "fail";
			} else if(eWRecieve.getBy1() != null && "1".equals(eWRecieve.getBy1().trim())){
				result = "repeat";
			} else if(eWRecieve.getBy1() != null && "0".equals(eWRecieve.getBy1().trim())){
				//已下发指令
				eWRecieve.setBy1("1");
				eWarningService.update(eWRecieve);
				
				//指令基础信息
				Date zlsj = new Date();
				Instruction instruction = new Instruction();
				instruction.setBjxh(bjxh);
				instruction.setQsid(qsid);
				instruction.setZlxfbm(user.getLskhbm());
				instruction.setZlxfbmmc(user.getLskhbmmc());
				instruction.setZlxfr(user.getLoginName());
				instruction.setZlxfrmc(user.getUserName());
				instruction.setXfsj(zlsj);
				instruction.setJqjb(jqjb);
				instruction.setZlfs(zlfs);
				instruction.setYanr(yanr);
				instruction.setXxly("0");
				int insId = (Integer)eWarningService.save(instruction);//指令ID
				
				//开始指令下发到卡点
				if(lastKD != null && lastKD.length > 0){
					for(String s:lastKD){
						String[] str = s.split(";");
						String name = str[0];
						String time = str[1];
						String id = str[2];
						
						//查询卡点对应的部门
						List<Object> param = new ArrayList<Object>();
						param.add(id);
						List<Map> orgcodeList = eWarningService.findList("select ORGCODE, ORGCODENAME from ZAKD where ID = ? ", param);
						
						//插入指令签收
						if(orgcodeList.size() > 0){
							InstructionSign insSign = new InstructionSign();
							insSign.setZlid(insId);
							insSign.setBkid(eWRecieve.getBkid());
							insSign.setHphm(hphm);
							insSign.setHpzl(hpzl);
							insSign.setKdid(id);
							insSign.setKdmc(name);
							insSign.setYjsj(time);
							insSign.setZlbm(orgcodeList.get(0).get("ORGCODE")+"");
							insSign.setZlbmmc(orgcodeList.get(0).get("ORGCODENAME")+"");
							insSign.setQszt("0");
							insSign.setFkzt("0");
							insSign.setXxly("0");
							insSign.setZlsj(zlsj);
							eWarningService.save(insSign);//保存指令签收信息
						}
					}
				}
				
				//分局或卡点  部门ID1:部门名称1;部门ID2:部门名称2……
				if(zldw != null && !"".equals(zldw)){
					String[] zldwArr = zldw.split(";");
					for(int i=0;i < zldwArr.length;i++){
						InstructionSign insSign = new InstructionSign();
						insSign.setZlid(insId);
						insSign.setBkid(eWRecieve.getBkid());
						insSign.setHphm(hphm);
						insSign.setHpzl(hpzl);
						insSign.setZlbm(zldwArr[i].split(":")[0]);
						insSign.setZlbmmc(zldwArr[i].split(":")[1]);
						insSign.setQszt("0");
						insSign.setFkzt("0");
						insSign.setXxly("0");
						insSign.setZlsj(zlsj);
						eWarningService.save(insSign);//保存指令签收信息
					}
				}
			}
		} catch (Exception e) {
			result = "fail";
			e.printStackTrace();
		} finally{
			try {
				response.getWriter().write(JSON.toJSONString(result));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 指令查询
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/queryInstruction")
	@Description(moduleName="预警管理",operateType="1",operationName="分页查询指令")
	public String queryInstruction(Model model, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response,PageResult pageResult,
			String qsr, String qszt, String hphm, String hpzl, String fkzt, String kssj, String jssj){
		String page = "/earlyWarning/queryInstruction";
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取所有带坐标的监测点
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dayformt = new SimpleDateFormat("yyyy-MM-dd");
			String cxrq = request.getParameter("cxrq");
			if(cxrq == null || "".equals(cxrq.trim())){
				JDateTime jdt = new JDateTime();
				Date nowDate = new Date();
				model.addAttribute("year", jdt.toString("YYYY"));
				model.addAttribute("month", jdt.toString("MM"));
				model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
				model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
				model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
				model.addAttribute("cxlb", "1");
				model.addAttribute("cxlxMap", getCxlxMap());
				kssj = dayformt.format(nowDate) + " 00:00:00";
				jssj = formatter.format(nowDate);
			} else {
				map.put("year", request.getParameter("year"));
				map.put("month", request.getParameter("month"));
				map.put("cxrq", request.getParameter("cxrq"));
				map.put("cxlb", request.getParameter("cxlb"));
				map.put("kssj", kssj);
				map.put("jssj", jssj);
				map.put("cxlxMap", getCxlxMap());
			}
			
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,ZLQSZT,ZLFKZT");
			request.setAttribute("dicList", dicList);
			//根据条件查询
			String hql = "select instructionSign from InstructionSign instructionSign where instructionSign.zlbm=? ";
			List<Object> param = new ArrayList<Object>();
			param.add(user.getLskhbm());
			if(Tools.isNotEmpty(hphm)){
				hql += " and instructionSign.hphm like ? ";
				param.add("%" + hphm + "%");
			}
			if(Tools.isNotEmpty(hpzl)){
				hql += " and instructionSign.hpzl = ? ";
				param.add(hpzl);
			}
			if(Tools.isNotEmpty(qsr)){
				hql += " and instructionSign.qsrmc like ? ";
				param.add("%" +qsr+"%");
			}
			if(Tools.isNotEmpty(qszt)){
				hql += " and instructionSign.qszt = ? ";
				param.add(qszt);
			}
			if(Tools.isNotEmpty(fkzt)){
				hql += " and instructionSign.fkzt = ? ";
				param.add(fkzt);
			}
			if(Tools.isNotEmpty(kssj)){
				hql += " and instructionSign.zlsj >= ? ";
				param.add(formatter.parse(kssj));
			}
			if(Tools.isNotEmpty(jssj)){
				hql += " and instructionSign.zlsj <= ? ";
				param.add(formatter.parse(jssj));
			}
			hql += " order by instructionSign.id desc ";
			
			pageResult = eWarningService.getPageResult(hql, param, pageResult.getPageNo(), 10);
			request.setAttribute("hphm", hphm);
			request.setAttribute("hpzl", hpzl);
			request.setAttribute("qsr", qsr);
			request.setAttribute("qszt", qszt);
			request.setAttribute("fkzt", fkzt);
			request.setAttribute("kssj", kssj);
			request.setAttribute("jssj", jssj);
			request.setAttribute("pageResults", pageResult);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	} 
	
	/**
	 * 指令反馈查询
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/queryInstructionForFk")
	@Description(moduleName="预警管理",operateType="1",operationName="分页查询指令反馈")
	public String queryInstructionForFk(Model model, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response,PageResult pageResult,
			String qsr, String qszt, String hphm, String hpzl, String fkzt, String kssj, String jssj){
		String page = "/earlyWarning/queryInstructionForFk";
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取所有带坐标的监测点
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dayformt = new SimpleDateFormat("yyyy-MM-dd");
			String cxrq = request.getParameter("cxrq");
			if(cxrq == null || "".equals(cxrq.trim())){
				JDateTime jdt = new JDateTime();
				Date nowDate = new Date();
				model.addAttribute("year", jdt.toString("YYYY"));
				model.addAttribute("month", jdt.toString("MM"));
				model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
				model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
				model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
				model.addAttribute("cxlb", "1");
				model.addAttribute("cxlxMap", getCxlxMap());
				kssj = dayformt.format(nowDate) + " 00:00:00";
				jssj = formatter.format(nowDate);
			} else {
				map.put("year", request.getParameter("year"));
				map.put("month", request.getParameter("month"));
				map.put("cxrq", request.getParameter("cxrq"));
				map.put("cxlb", request.getParameter("cxlb"));
				map.put("kssj", kssj);
				map.put("jssj", jssj);
				map.put("cxlxMap", getCxlxMap());
			}
			
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,ZLQSZT,ZLFKZT");
			request.setAttribute("dicList", dicList);
			//根据条件查询
			String hql = "select instructionSign from InstructionSign instructionSign where instructionSign.zlbm=? and instructionSign.qszt='1' ";
			List<Object> param = new ArrayList<Object>();
			param.add(user.getLskhbm());
			if(Tools.isNotEmpty(hphm)){
				hql += " and instructionSign.hphm like ? ";
				param.add("%" + hphm + "%");
			}
			if(Tools.isNotEmpty(hpzl)){
				hql += " and instructionSign.hpzl = ? ";
				param.add(hpzl);
			}
			if(Tools.isNotEmpty(qsr)){
				hql += " and instructionSign.qsrmc like ? ";
				param.add("%" +qsr+"%");
			}
			if(Tools.isNotEmpty(qszt)){
				hql += " and instructionSign.qszt = ? ";
				param.add(qszt);
			}
			if(Tools.isNotEmpty(fkzt)){
				hql += " and instructionSign.fkzt = ? ";
				param.add(fkzt);
			}
			if(Tools.isNotEmpty(kssj)){
				hql += " and instructionSign.zlsj >= ? ";
				param.add(formatter.parse(kssj));
			}
			if(Tools.isNotEmpty(jssj)){
				hql += " and instructionSign.zlsj <= ? ";
				param.add(formatter.parse(jssj));
			}
			hql += " order by instructionSign.id desc ";
			
			pageResult = eWarningService.getPageResult(hql, param, pageResult.getPageNo(), 10);
			request.setAttribute("hphm", hphm);
			request.setAttribute("hpzl", hpzl);
			request.setAttribute("qsr", qsr);
			request.setAttribute("qszt", qszt);
			request.setAttribute("fkzt", fkzt);
			request.setAttribute("kssj", kssj);
			request.setAttribute("jssj", jssj);
			request.setAttribute("pageResults", pageResult);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	} 
	
	/**
	 * 跳转指令详情
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/loadInsDetail")
	@Description(moduleName="预警管理",operationName="指令详情")
	public String loadInsDetail(HttpServletRequest request, HttpServletResponse response,int id){
		String page = "/earlyWarning/insDetail";
		try {
			InstructionSign insSign = eWarningService.findInstructionSignById(id);
			request.setAttribute("insSign", insSign);
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,ZLQSZT,ZLFKZT,BKJB,SFLJ,WLJDYY");
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	} 
	
	/**
	 * 跳转指令签收查询
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/queryInsSign")
	@Description(moduleName="预警管理",operateType="1",operationName="跳转指令签收查询")
	public String queryInsSign(HttpServletRequest request, HttpServletResponse response,PageResult pageResult,String qsr, String qszt){
		String page = "/earlyWarning/queryInsSign";
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			//找到当前人分局下的指令签收信息
//			String dept = " ";
//			if(user.getDeptId().length() >= 6){
//				dept = user.getDeptId().substring(0, 6);
//			}
			String hql = "select instructionSign from InstructionSign instructionSign where instructionSign.qszt='0' and instructionSign.zlbm=? order by instructionSign.id desc";
			List<Object> param = new ArrayList<Object>();
			param.add(user.getLskhbm());
			//根据条件查询
			pageResult = eWarningService.getPageResult(hql, param, pageResult.getPageNo(), 10);
			request.setAttribute("pageResults", pageResult);
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL");
			request.setAttribute("dicList", dicList);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	} 
	
	/**
	 * 跳转指令反馈
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/queryInsSignForFk")
	@Description(moduleName="预警管理",operateType="1",operationName="跳转指令反馈")
	public String queryInsSignForFk(HttpServletRequest request, HttpServletResponse response,PageResult pageResult,String qsr, String qszt){
		String page = "/earlyWarning/queryInsSignForFk";
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			String hql = "select instructionSign from InstructionSign instructionSign where instructionSign.qszt='1' and instructionSign.fkzt='0' and instructionSign.zlbm=? order by instructionSign.id desc";
			List<Object> param = new ArrayList<Object>();
			param.add(user.getLskhbm());
			//根据条件查询
			pageResult = eWarningService.getPageResult(hql, param, pageResult.getPageNo(), 10);
			request.setAttribute("pageResults", pageResult);
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL");
			request.setAttribute("dicList", dicList);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	} 
	
	/**
	 * 跳转指令签收
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/insSign")
	@Description(moduleName="预警管理",operateType="1",operationName="跳转指令签收")
	public String insSign(HttpServletRequest request, HttpServletResponse response,int id){
		String page = "/earlyWarning/insSign";
		try {
			InstructionSign insSign = eWarningService.findInstructionSignById(id);
			request.setAttribute("insSign", insSign);
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,BKJB");
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	} 
	/**
	 * 跳转指令反馈
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/loadInsFeedback")
	@Description(moduleName="预警管理",operateType="1",operationName="跳转指令反馈")
	public String loadInsFeedback(HttpServletRequest request, HttpServletResponse response,int id){
		String page = "/earlyWarning/insFeedback";
		try {
			InstructionSign insSign = eWarningService.findInstructionSignById(id);
			request.setAttribute("insSign", insSign);
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,ZLQSZT,ZLFKZT,BKJB,SFLJ,WLJDYY");
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	} 
	
	/**
     * 签收指令
     */
	@RequestMapping("/insSigned")
	@Description(moduleName="预警管理",operateType="3",operationName="签收指令")
	public void insSigned(HttpServletRequest request,HttpServletResponse response,int zlqsid, String qsms, String qsrlxdh) {
		String result = "success";
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			InstructionSign instructionSign = eWarningService.findInstructionSignById(zlqsid);
			if("1".equals(instructionSign.getQszt())){
				result = "repeat";
			} else {
				instructionSign.setQssj(new Date());
				instructionSign.setQszt("1");
				instructionSign.setQsr(user.getLoginName());
				instructionSign.setQsrmc(user.getUserName());
				instructionSign.setQsbm(user.getDeptId());
				instructionSign.setQsbmmc(user.getDeptName());
				instructionSign.setQsms(qsms);
				instructionSign.setQsrlxdh(qsrlxdh);
				eWarningService.update(instructionSign);
			}
		} catch (Exception e) {
			result = "fail";
			e.printStackTrace();
		} finally{
			try {
				response.getWriter().write(JSON.toJSONString(result));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
     * 指令反馈
     */
	@RequestMapping("/insFeedback")
	@Description(moduleName="预警管理",operationName="指令反馈")
	public void insFeedback(HttpServletRequest request, HttpServletResponse response, int zlqsid, 
			String fkrlxdh, String czjg, String sflj, String wljdyy, String ddr, String xbr, 
			String zhrs, String phajs, String fknr) {
		String result = "success";
		try {
			//用户信息
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取指令反馈信息
			InstructionSign instructionSign = eWarningService.findInstructionSignById(zlqsid);
			
			//判断是否已反馈
			if("1".equals(instructionSign.getFkzt())){
				result = "repeat";
			} else {
				//如果未拦截到，则需要检查是否有拦截成功的
				if("2".equals(czjg) && "1".equals(sflj)){
					String hql = " from InstructionSign where fkzt='1' and czjg='2' and sflj='1' and bkid=" + instructionSign.getBkid() + " order by fksj desc ";
					List<InstructionSign> instructionSignList = eWarningService.findObjects(hql, null);
					if(instructionSignList != null && instructionSignList.size() > 0){
						result = "yjzh";
					}
				}
				
				//未有其他单位抓获
				if(!"yjzh".equals(result)){
					instructionSign.setFkr(user.getLoginName());
					instructionSign.setFkrmc(user.getUserName());
					instructionSign.setFkbm(user.getDeptId());
					instructionSign.setFkbmmc(user.getDeptName());
					instructionSign.setFkzt("1");
					instructionSign.setFksj(new Date());
					instructionSign.setFkrlxdh(fkrlxdh);
					instructionSign.setCzjg(czjg);
					instructionSign.setSflj(sflj);
					instructionSign.setWljdyy(wljdyy);
					instructionSign.setDdr(ddr);
					instructionSign.setXbr(xbr);
					instructionSign.setZhrs(zhrs);
					instructionSign.setPhajs(phajs);
					instructionSign.setFknr(fknr);
					
					String flag = "0";//调用省反馈接口   0未调用   && "2".equals(eWRecieve.getDispatched().getXxly())
					//调用省厅接口，传递至省厅    布控信息必须是省厅布控过来的
					EWRecieve eWRecieve = instructionSign.getInstruction().getEwrecieve();
					if("00".equals(eWRecieve.getDispatched().getBklb()) 
							&& "1".equals(Config.getInstance().getStInterfaceFlag().split(":")[3])){
						flag = IntefaceUtils.sendInstructionSignForFk(instructionSign);
					}
					instructionSign.setBy1(flag);
					
					//更新
					eWarningService.update(instructionSign);
				}
			}
		} catch (Exception e) {
			result = "fail";
			e.printStackTrace();
		} finally{
			try {
				response.getWriter().write(JSON.toJSONString(result));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 跳转预警演练
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/exercise")
	@Description(moduleName="预警管理",operateType="1",operationName="跳转预警演练")
	public String exercise(HttpServletRequest request, HttpServletResponse response){
		String page = "/earlyWarning/exercise";
		try {
			String sql = "SELECT z.*,COUNT(bb.POSTCODE) worknum FROM ZAKD z LEFT JOIN  POSTAREAR p ON p.AREACODE=z.CODE " +
					" LEFT JOIN  BBDETAILINFO_3D bb ON bb.POSTCODE=p.ID AND bb.STARTTIME < NOW() AND bb.ENDTIME > NOW() GROUP BY z.ID ";
			List<Map> list =  eWarningService.findList(sql, null);
			List<Map> zakd = new ArrayList();
			for(Map map : list){//坐标转换
				double[] bdGis = BaiduapiOffline.transform(Double.parseDouble(map.get("Y").toString()),Double.parseDouble(map.get("X").toString()));
				map.remove("X");
				map.remove("Y");
				map.put("X", bdGis[0]);
				map.put("Y", bdGis[1]);
				zakd.add(map);
			}
			List<Jcd> jcdList = jcdService.findAllJcd();
			request.setAttribute("jcdList", JSON.toJSONString(jcdList));
			request.setAttribute("zakd", JSON.toJSONString(zakd));
			//获取默认值
			int defaultSpeed = Integer.parseInt(ReadConfig.getPropertiesValue("activiti", "defaultSpeed"));
			int rangeX = Integer.parseInt(ReadConfig.getPropertiesValue("activiti", "rangeX"));
			request.setAttribute("defaultSpeed", defaultSpeed);
			request.setAttribute("rangeX", rangeX);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 将预案信息推送至PGIS
	 */
	@RequestMapping("/sendToPGIS")
	@Description(moduleName="下发指令",operateType="1",operationName="预案信息推送至PGIS")
	public void sendToPGIS(HttpServletRequest request, HttpServletResponse response, String hphm, 
			String hpzl, String tgsj, String jcdid, String jcdmc, String cdid, String jwd, String yanr, String[] kd){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = "1";
		try {
			//号牌种类
			List<Object> params = new ArrayList<Object>();
			params.add(hpzl);
			List<Map> dicList = eWarningService.findList(" select TYPE_CODE, MEMO, TYPE_SERIAL_NO, TYPE_DESC from dictionary where TYPE_CODE='HPZL' and TYPE_SERIAL_NO=? ", params);
			if(dicList != null && dicList.size() > 0) {
				hpzl = dicList.get(0).get("TYPE_DESC").toString();
			}
			
			//预案内容换行
			StringBuffer yanrSb = new StringBuffer(yanr);
			for(int i=35;i < yanrSb.length();i+=36){
				yanrSb.insert(i, "\\n");
			}
			
			//预案信息
			//卡点信息
			List<Ya> yaList = new ArrayList<Ya>();
			if(kd != null && kd.length > 0){
				for(String s:kd){
					//卡点名称:到达时间:卡点ID:X坐标:Y坐标:卡点编号:对讲机呼号:岗位编号
					String[] str = s.split(";");
//					String id = str[2];
					String code = str[5];
					String kdName = str[0];
					String x = str[3];
					String y = str[4];
					String time = str[1];
					String postcode = str[7];
					String callnum = str[6];
					
					//根据岗位编码获取人员信息
					String kdzsry = "";
					String sql = " select POLICENUM, NAME, MOBILEPHONE from policeinfo where POLICENUM IN (select POLICENUM from bbdetailinfo_3d where POSTCODE=? and STARTTIME < NOW() AND ENDTIME > NOW()) ";
					List<Object> param = new ArrayList<Object>();
					param.add(postcode);
					List<Map> userList =  eWarningService.findList(sql, param);
					if(userList != null){
						for(Map map : userList){//坐标转换
							String name = map.get("NAME").toString();
							Object phone = map.get("MOBILEPHONE");
							kdzsry = kdzsry + name + (phone != null && !"".equals(phone)?"(" + phone.toString() + ") ":" ");
						}
					}
					StringBuffer zsrySb = new StringBuffer(kdzsry);
					for(int i=35;i < zsrySb.length();i+=36){
						zsrySb.insert(i, "\\n");
					}
					
					//封装预案
					Ya ya = new Ya(code, kdName, x, y, time, yanrSb.toString(), zsrySb.toString(), callnum);
					yaList.add(ya);
				}
			}
				
			//封装信息，并写到指定地方，并返回xml地址
			String filePath = XmlCreater.yaToXml("03", CommonUtils.getIpAddr(request), hphm, hpzl, dateFormat.format(new Date(Long.parseLong(tgsj))), 
				jcdid, jcdmc, jwd.split(":")[0], jwd.split(":")[1], cdid, yaList);
				
			//发送至PGIS
			if(filePath != null && !"".equals(filePath.trim())){
				result = IntefaceUtils.sendXmlToPGIS(CommonUtils.getIpAddr(request), 8900, new File(filePath));
			} else{
				result = "0";//发送失败
			}
		} catch (Exception e) {
			result = "0";//发送失败
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}