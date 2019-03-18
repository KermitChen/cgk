package com.dyst.jxkh.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.datetime.JDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.earlyWarning.entities.EWRecieve;
import com.dyst.earlyWarning.entities.InstructionSign;
import com.dyst.earlyWarning.service.EWarningService;
import com.dyst.jxkh.entities.BklbEntity;
import com.dyst.jxkh.entities.YjqsEntity22;
import com.dyst.jxkh.entities.YjqsListBean;
import com.dyst.jxkh.entities.YjtjEntity;
import com.dyst.jxkh.service.JxkhService;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.Department;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.StaticUtils;
import com.dyst.utils.excel.bean.yj.YjqsExcelBean;
import com.dyst.utils.excel.bean.yj.YjtjExcelBean;

@Controller
@RequestMapping(value="/yjqs")
public class YjqsController {
	
	@Autowired
	private JxkhService jxkhService;
	
	//注入业务层
	@Autowired
	private UserService userService;
	
	@Autowired
	private EWarningService eWarningService;
	
	//查询类型map表
	private Map<String, String> getCxlxMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "按天查询");
		map.put("2", "按月查询");
		map.put("3", "自定义查询");
		return map;
	}
	
	//跳转到预警签收页面
	@RequestMapping(value="findYjqsUI")
	public String findYjqsUI(HttpServletRequest request, Model model) throws Exception{
		//获取用户信息
		User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		
		//获取操作人考核单位
		List<Department> deptList = getJxkhDept(operUser.getLskhbm());
		Department jxkhBm = null;
		if(deptList != null && deptList.size() > 0){
			jxkhBm = deptList.get(0);
		}
		
		//绩效考核部门
		List<Department> jxkhDeptList = new ArrayList<Department>();
		List<Department> deptsList = getJxkhDepts();
		for(int i=0;i < deptsList.size();i++){
			Department dept = deptsList.get(i);
			
			//市局
			if(operUser.getPosition().length() == 2 && Integer.parseInt(operUser.getPosition()) >= 90){//全部
				jxkhDeptList.add(dept);
			} else if((dept.getDeptNo() != null && operUser.getLskhbm() != null && dept.getDeptNo().trim().equals(operUser.getLskhbm().trim()))
					|| (dept.getSystemNo() != null && jxkhBm != null && jxkhBm.getSystemNo() != null && dept.getSystemNo().trim().indexOf(jxkhBm.getSystemNo().trim()) != -1)){//分局或卡点，单一
				jxkhDeptList.add(dept);
			}
		}
		request.getSession().setAttribute("jxkhDeptListForYjqs", jxkhDeptList);//放入session
		model.addAttribute("deptsList", jxkhDeptList);
		
		//获取所有带坐标的监测点
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dayformt = new SimpleDateFormat("yyyy-MM-dd");
		JDateTime jdt = new JDateTime();
		Date nowDate = new Date();
		model.addAttribute("year", jdt.toString("YYYY"));
		model.addAttribute("month", jdt.toString("MM"));
		model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
		model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
		model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
		model.addAttribute("cxlb", "1");
		model.addAttribute("cxlxMap", getCxlxMap());
		model.addAttribute("kssj", dayformt.format(nowDate) + " 00:00:00");
		model.addAttribute("jssj", formatter.format(nowDate));
		
		return "/jxkh/yj/yjqscxListUI";
	}
	
	/**
	 * 预警签收统计查询
	 * @throws Exception 
	 */
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping(value="findYjqs")
	@Description(moduleName="预警签收统计查询",operateType="1",operationName="预警签收统计")
	public String findYjqs(Model model, Map<String, Object> map, HttpServletRequest request, 
			String kssj, String jssj, String Check_tjbm) throws Exception{
		//绩效考核部门
		List<Department> jxkhDeptList = (List<Department>)request.getSession().getAttribute("jxkhDeptListForYjqs");//
		
		//回显查询条件
		map.put("year", request.getParameter("year"));
		map.put("month", request.getParameter("month"));
		map.put("cxrq", request.getParameter("cxrq"));
		map.put("cxlb", request.getParameter("cxlb"));
		map.put("kssj", kssj);
		map.put("jssj", jssj);
		map.put("Check_tjbm", Check_tjbm);
		map.put("cxlxMap", getCxlxMap());
		request.getSession().setAttribute("jxkhDeptListForYjqs", jxkhDeptList);//放入session
		model.addAttribute("deptsList", jxkhDeptList);
		
		//查询出列表
		List<YjqsEntity22> list = new ArrayList<YjqsEntity22>();
		String result = "1";
		try {
			//查询
			list = jxkhService.getYjqs(kssj, jssj, Check_tjbm, jxkhDeptList);
			
			//保存结果，用于导出
			request.getSession().setAttribute("jxkhYjqsList", list);//放入session
			
			//计算总计
			YjqsListBean bean = new YjqsListBean(list);
			model.addAttribute("bean", bean);
		} catch (Exception e) {
			result = "2";
			e.printStackTrace();
		} finally {
			request.setAttribute("result", result);
			return "/jxkh/yj/yjqscxListUI";
		}
	}
	
	/**
	 * 预警签收导出Excel
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportExcelForYjqs")
	@Description(moduleName="预警签收统计查询",operateType="1",operationName="导出预警签收统计")
	public void exportExcelForYjqs(HttpServletRequest request, HttpServletResponse resp, String kssj, String jssj, String Check_tjbm) throws Exception{
		List<YjqsEntity22> list = new ArrayList<YjqsEntity22>();
		try {
			//获取结果
			list = (List<YjqsEntity22>)request.getSession().getAttribute("jxkhYjqsList");//放入session
			
			//封装
			YjqsExcelBean bean = new YjqsExcelBean(list);
			bean.setStartTime(kssj);
			bean.setEndTime(jssj);
			bean.setDeptName(Check_tjbm);
			
			//输出
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("预警签收查询").append(dateFormat.format(new Date())).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
				//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			jxkhService.excelExportForYjqs(bean, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//跳转到预警统计页面
	@RequestMapping(value="toYjtjUI")
	public String toYjtjUI(HttpServletRequest request, Model model) throws Exception{
		//获取用户信息
		User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		
		//获取操作人考核单位
		List<Department> deptList = getJxkhDept(operUser.getLskhbm());
		Department jxkhBm = null;
		if(deptList != null && deptList.size() > 0){
			jxkhBm = deptList.get(0);
		}
		
		//绩效考核部门
		List<Department> jxkhDeptList = new ArrayList<Department>();
		List<Department> deptsList = getJxkhDepts();
		for(int i=0;i < deptsList.size();i++){
			Department dept = deptsList.get(i);
			
			//市局
			if(operUser.getPosition().length() == 2 && Integer.parseInt(operUser.getPosition()) >= 90){//全部
				jxkhDeptList.add(dept);
			} else if((dept.getDeptNo() != null && operUser.getLskhbm() != null && dept.getDeptNo().trim().equals(operUser.getLskhbm().trim()))
					|| (dept.getSystemNo() != null && jxkhBm != null && jxkhBm.getSystemNo() != null && dept.getSystemNo().trim().indexOf(jxkhBm.getSystemNo().trim()) != -1)){//分局或卡点，单一
				jxkhDeptList.add(dept);
			}
		}
		request.getSession().setAttribute("jxkhDeptListForYjtj", jxkhDeptList);//放入session
		model.addAttribute("deptsList", jxkhDeptList);
		
		//获取所有带坐标的监测点
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dayformt = new SimpleDateFormat("yyyy-MM-dd");
		JDateTime jdt = new JDateTime();
		Date nowDate = new Date();
		model.addAttribute("year", jdt.toString("YYYY"));
		model.addAttribute("month", jdt.toString("MM"));
		model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
		model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
		model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
		model.addAttribute("cxlb", "1");
		model.addAttribute("cxlxMap", getCxlxMap());
		model.addAttribute("kssj", dayformt.format(nowDate) + " 00:00:00");
		model.addAttribute("jssj", formatter.format(nowDate));
		
		return "/jxkh/yj/yjtjcxListUI";
	}
	
	//查询预警统计列表
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping(value="findYjtj")
	@Description(moduleName="预警统计查询",operateType="1",operationName="预警统计")
	public String findYjtj(Model model, Map<String, Object> map, HttpServletRequest request, 
			String kssj, String jssj, String Check_tjbm) throws Exception{
		//绩效考核部门
		List<Department> jxkhDeptList = (List<Department>)request.getSession().getAttribute("jxkhDeptListForYjtj");//
		
		//回显查询条件
		map.put("year", request.getParameter("year"));
		map.put("month", request.getParameter("month"));
		map.put("cxrq", request.getParameter("cxrq"));
		map.put("cxlb", request.getParameter("cxlb"));
		map.put("kssj", kssj);
		map.put("jssj", jssj);
		map.put("Check_tjbm", Check_tjbm);
		map.put("cxlxMap", getCxlxMap());
		request.getSession().setAttribute("jxkhDeptListForYjtj", jxkhDeptList);//放入session
		model.addAttribute("deptsList", jxkhDeptList);
		
		String result = "1";
		try {
			//根据时间统计预警信息
			Map<String, List> yjtjMap = jxkhService.getYjtj(kssj, jssj, Check_tjbm);
			
			//获取角色类型，用户信息来源数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("BKDL,BKDL1,BKDL2,BKDL3");
			
			//拆分出布控小类
			List<Dictionary> bkdlList = getBkdl("BKDL", dicList);//布控大类
			List<Dictionary> bkdl1List = getBkdl("BKDL1", dicList);//涉案类
			List<Dictionary> bkdl2List = getBkdl("BKDL2", dicList);//交通违法类
			List<Dictionary> bkdl3List = getBkdl("BKDL3", dicList);//管控类
			
			List<YjtjEntity> yjtjList = new ArrayList<YjtjEntity>();
			YjtjEntity yjtjEntity = null;
			for(int i=0;i < bkdlList.size();i++){
				//布控大类
				Dictionary bkdlDic = bkdlList.get(i);
				
				//获取相应小类
				Dictionary bklbDic = null;
				if("BKDL".equals(bkdlDic.getTypeCode()) && "1".equals(bkdlDic.getTypeSerialNo())){//涉案类
					//一大类
					yjtjEntity = new YjtjEntity();
					yjtjEntity.setTypeSerialNo(bkdlDic.getTypeSerialNo());
					yjtjEntity.setTypeDesc(bkdlDic.getTypeDesc());
					
					//每小类
					List<BklbEntity> bklbList = new ArrayList<BklbEntity>();
					for(int j=0;j < bkdl1List.size();j++){
						bklbDic = bkdl1List.get(j);
						
						//获取每一种小类的三项值
						int bjzs = getCountValue(yjtjMap.get("bjzs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int bjjsqs = getCountValue(yjtjMap.get("bjjsqs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int bjcsqs = getCountValue(yjtjMap.get("bjcsqs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int bjyxs = getCountValue(yjtjMap.get("bjyxs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int bjwxs = getCountValue(yjtjMap.get("bjwxs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int zlzs = getCountValue(yjtjMap.get("zlzs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int zljsfks = getCountValue(yjtjMap.get("zljsfks"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int zlwjsfks = getCountValue(yjtjMap.get("zlwjsfks"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						
						BklbEntity bklbEntity = new BklbEntity();
						bklbEntity.setParentTypeSerialNo(bklbDic.getTypeCode());
						bklbEntity.setTypeSerialNo(bklbDic.getTypeSerialNo());
						bklbEntity.setTypeDesc(bklbDic.getTypeDesc());
						bklbEntity.setBjzs(bjzs);//报警总数
						bklbEntity.setBjjsqs(bjjsqs);//报警及时签收数
						bklbEntity.setBjcsqs(bjcsqs);//报警超时签收数
						bklbEntity.setBjwqs(bjzs - bjjsqs - bjcsqs);//报警未签收数
						bklbEntity.setBjyxs(bjyxs);//报警有效数
						bklbEntity.setBjwxs(bjwxs);//报警无效数
						
						bklbEntity.setZlzs(zlzs);//指令总数
						bklbEntity.setZljsfks(zljsfks);//指令及时反馈数
						bklbEntity.setZlwjsfks(zlwjsfks);//指令超时反馈数
						bklbEntity.setZlwfks(zlzs - zljsfks - zlwjsfks);//指令未反馈数
						
						bklbList.add(bklbEntity);
					}
					
					//添加小类
					yjtjEntity.setBklbList(bklbList);
					//添加大类
					yjtjList.add(yjtjEntity);
				} else if("BKDL".equals(bkdlDic.getTypeCode()) && "2".equals(bkdlDic.getTypeSerialNo())){//交通违法类
					//一大类
					yjtjEntity = new YjtjEntity();
					yjtjEntity.setTypeSerialNo(bkdlDic.getTypeSerialNo());
					yjtjEntity.setTypeDesc(bkdlDic.getTypeDesc());
					
					//每小类
					List<BklbEntity> bklbList = new ArrayList<BklbEntity>();
					for(int j=0;j < bkdl2List.size();j++){
						bklbDic = bkdl2List.get(j);
						
						//获取每一种小类的三项值
						int bjzs = getCountValue(yjtjMap.get("bjzs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int bjjsqs = getCountValue(yjtjMap.get("bjjsqs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int bjcsqs = getCountValue(yjtjMap.get("bjcsqs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int bjyxs = getCountValue(yjtjMap.get("bjyxs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int bjwxs = getCountValue(yjtjMap.get("bjwxs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int zlzs = getCountValue(yjtjMap.get("zlzs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int zljsfks = getCountValue(yjtjMap.get("zljsfks"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int zlwjsfks = getCountValue(yjtjMap.get("zlwjsfks"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						
						BklbEntity bklbEntity = new BklbEntity();
						bklbEntity.setParentTypeSerialNo(bklbDic.getTypeCode());
						bklbEntity.setTypeSerialNo(bklbDic.getTypeSerialNo());
						bklbEntity.setTypeDesc(bklbDic.getTypeDesc());
						bklbEntity.setBjzs(bjzs);//报警总数
						bklbEntity.setBjjsqs(bjjsqs);//报警及时签收数
						bklbEntity.setBjcsqs(bjcsqs);//报警超时签收数
						bklbEntity.setBjwqs(bjzs - bjjsqs - bjcsqs);//报警未签收数
						bklbEntity.setBjyxs(bjyxs);//报警有效数
						bklbEntity.setBjwxs(bjwxs);//报警无效数
						
						bklbEntity.setZlzs(zlzs);//指令总数
						bklbEntity.setZljsfks(zljsfks);//指令及时反馈数
						bklbEntity.setZlwjsfks(zlwjsfks);//指令超时反馈数
						bklbEntity.setZlwfks(zlzs - zljsfks - zlwjsfks);//指令未反馈数
						
						bklbList.add(bklbEntity);
					}
					//添加小类
					yjtjEntity.setBklbList(bklbList);
					//添加大类
					yjtjList.add(yjtjEntity);
				} else if("BKDL".equals(bkdlDic.getTypeCode()) && "3".equals(bkdlDic.getTypeSerialNo())){//管控类
					//一大类
					yjtjEntity = new YjtjEntity();
					yjtjEntity.setTypeSerialNo(bkdlDic.getTypeSerialNo());
					yjtjEntity.setTypeDesc(bkdlDic.getTypeDesc());
					
					//每小类
					List<BklbEntity> bklbList = new ArrayList<BklbEntity>();
					for(int j=0;j < bkdl3List.size();j++){
						bklbDic = bkdl3List.get(j);
						
						//获取每一种小类的三项值
						int bjzs = getCountValue(yjtjMap.get("bjzs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int bjjsqs = getCountValue(yjtjMap.get("bjjsqs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int bjcsqs = getCountValue(yjtjMap.get("bjcsqs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int bjyxs = getCountValue(yjtjMap.get("bjyxs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int bjwxs = getCountValue(yjtjMap.get("bjwxs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int zlzs = getCountValue(yjtjMap.get("zlzs"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int zljsfks = getCountValue(yjtjMap.get("zljsfks"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int zlwjsfks = getCountValue(yjtjMap.get("zlwjsfks"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						
						BklbEntity bklbEntity = new BklbEntity();
						bklbEntity.setParentTypeSerialNo(bklbDic.getTypeCode());
						bklbEntity.setTypeSerialNo(bklbDic.getTypeSerialNo());
						bklbEntity.setTypeDesc(bklbDic.getTypeDesc());
						bklbEntity.setBjzs(bjzs);//报警总数
						bklbEntity.setBjjsqs(bjjsqs);//报警及时签收数
						bklbEntity.setBjcsqs(bjcsqs);//报警超时签收数
						bklbEntity.setBjwqs(bjzs - bjjsqs - bjcsqs);//报警未签收数
						bklbEntity.setBjyxs(bjyxs);//报警有效数
						bklbEntity.setBjwxs(bjwxs);//报警无效数
						
						bklbEntity.setZlzs(zlzs);//指令总数
						bklbEntity.setZljsfks(zljsfks);//指令及时反馈数
						bklbEntity.setZlwjsfks(zlwjsfks);//指令超时反馈数
						bklbEntity.setZlwfks(zlzs - zljsfks - zlwjsfks);//指令未反馈数
						
						bklbList.add(bklbEntity);
					}
					//添加小类
					yjtjEntity.setBklbList(bklbList);
					//添加大类
					yjtjList.add(yjtjEntity);
				}
			}
			request.setAttribute("yjtjList", yjtjList);
			request.getSession().setAttribute("yjtjList", yjtjList);
			
			//总计
			Map<String, Integer> total = new HashMap<String, Integer>();
			int bjzs = 0;
			int bjjsqs = 0;
			int bjcsqs = 0;
			int bjwqs = 0;
			int bjyxs = 0;
			int bjwxs = 0;
			
			int zlzs = 0;
			int zljsfks = 0;
			int zlwjsfks = 0;
			int zlwfks = 0;
			
			for(YjtjEntity y:yjtjList){
				bjzs += y.getSubTotalList().get("xj_bjzs");
				bjjsqs += y.getSubTotalList().get("xj_bjjsqs");
				bjcsqs += y.getSubTotalList().get("xj_bjcsqs");
				bjwqs += y.getSubTotalList().get("xj_bjwqs");
				bjyxs += y.getSubTotalList().get("xj_bjyxs");
				bjwxs += y.getSubTotalList().get("xj_bjwxs");
				
				zlzs += y.getSubTotalList().get("xj_zlzs");
				zljsfks += y.getSubTotalList().get("xj_zljsfks");
				zlwjsfks += y.getSubTotalList().get("xj_zlwjsfks");
				zlwfks += y.getSubTotalList().get("xj_zlwfks");
			}
			
			total.put("zj_bjzs", bjzs);
			total.put("zj_bjjsqs", bjjsqs);
			total.put("zj_bjcsqs",bjcsqs);
			total.put("zj_bjwqs",bjwqs);
			total.put("zj_bjyxs",bjyxs);
			total.put("zj_bjwxs",bjwxs);
			total.put("zj_zlzs",zlzs);
			total.put("zj_zljsfks",zljsfks);
			total.put("zj_zlwjsfks",zlwjsfks);
			total.put("zj_zlwfks",zlwfks);
			request.setAttribute("total", total);
			request.getSession().setAttribute("yjtjTotal", total);
		} catch (Exception e) {
			result = "2";
			e.printStackTrace();
		} finally{
			request.setAttribute("result", result);
			return "/jxkh/yj/yjtjcxListUI";
		}
	}
	
	/**
	 * 预警统计  导出excel
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportExcelForYjtj")
	@Description(moduleName="预警统计查询",operateType="1",operationName="导出预警统计")
	public void exportExcelForYjtj(Model model, Map<String, Object> map, HttpServletRequest request, HttpServletResponse resp, 
			String kssj, String jssj, String Check_tjbm) throws Exception{
		YjtjExcelBean bean = new YjtjExcelBean();//excel Bean 
		List<YjtjEntity> list = new ArrayList<YjtjEntity>();//预警统计  list
		Map<String, Integer> yjtjTotal = new HashMap<String, Integer>();
		try {
			//获取用户信息
			User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取sesion中的结果
			list = (List<YjtjEntity>)request.getSession().getAttribute("yjtjList");//
			yjtjTotal = (Map<String, Integer>)request.getSession().getAttribute("yjtjTotal");
			 
			bean.setList(list);
			bean.setTotal(yjtjTotal);
			bean.setStartTime(kssj);//startTime
			bean.setEndTime(jssj);//endTime
			bean.setDeptName(Check_tjbm);// deptName
			
			//输出
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("预警统计查询").append(dateFormat.format(new Date())).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
				//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			jxkhService.excelExportForYjtj(operUser, bean, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Department> getJxkhDepts(){
		String hql = " from Department d where d.jxkh = '1' ";
		return jxkhService.getDeptList(hql, null);
	}
	
	public List<Department> getJxkhDept(String deptNo){
		String hql = " from Department d where d.jxkh = '1' and d.deptNo = '" + deptNo + "' ";
		return jxkhService.getDeptList(hql, null);
	}
	
	/**
	 * 获取相应类别的数据字典
	 * @param typeCode
	 * @param dicList
	 * @return
	 */
	private List<Dictionary> getBkdl(String typeCode, List<Dictionary> dicList){
		List<Dictionary> newDicList = new ArrayList<Dictionary>();
		for(int i=0;i < dicList.size();i++){
			Dictionary dic = dicList.get(i);
			if(typeCode != null && typeCode.trim().equals(dic.getTypeCode())){
				newDicList.add(dic);
			}
		}
		return newDicList;
	}
	
	/**
	 * 获取相应的统计数量，没有返回0
	 * @param data
	 * @param bkdl
	 * @param bklb
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getCountValue(List data, String bkdl, String bklb){
		int count = 0;
		for(int i=0;i < data.size();i++){
			Object[] objArr = (Object[])data.get(i);
			if(bkdl != null && bklb != null && bkdl.trim().equals(objArr[0]) && bklb.trim().equals(objArr[1])){
				BigInteger num = (BigInteger)objArr[2];
				if(num != null){
					count = Integer.parseInt(num.toString());
					break;//找到，退出循环
				}
			}
		}
		return count;
	}
	
	//预警签收情况详细信息，显示具体信息
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping(value="getYjqsInfo")
	@Description(moduleName="预警签收统计查询",operateType="1",operationName="预警签收情况详情")
	public String getYjqsInfo(HttpServletRequest request, Model model) throws Exception{
		//默认页面
		String page = "/jxkh/yj/yjqsInfoDetail";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<EWRecieve> eWRecieveList = new ArrayList<EWRecieve>();
		List<InstructionSign> instructionSignList = new ArrayList<InstructionSign>();
		List<InstructionSign> instructionSignFkList = new ArrayList<InstructionSign>();
		try {
			//获取页面参数
			String kssj = request.getParameter("kssj");
			String jssj = request.getParameter("jssj");
			String infodl = request.getParameter("infodl");
			String infoxl = request.getParameter("infoxl");
			String jxbm = request.getParameter("jxbm");
			
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("JXKHTIME,HPZL,YJQRZT,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,BKYWZT,XDZLFS,SFLJ,WLJDYY,CJCZJG");
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
			
			//绩效考核时间
			String yjqsTime = "2";
			String zlqsTime = "10";
			String zlfkTime = "24";
			for(int i=0;i < dicList.size();i++){
				Dictionary dic = dicList.get(i);
				if("JXKHTIME".equals(dic.getTypeCode()) && "YJQS".equals(dic.getTypeSerialNo())){
					yjqsTime = dic.getTypeDesc();
				} else if("JXKHTIME".equals(dic.getTypeCode()) && "ZLQS".equals(dic.getTypeSerialNo())){
					zlqsTime = dic.getTypeDesc();
				} else if("JXKHTIME".equals(dic.getTypeCode()) && "ZLFK".equals(dic.getTypeSerialNo())){
					zlfkTime = dic.getTypeDesc();
				}
			}
			
			if(infodl != null && "1".equals(infodl.trim()) 
					&& infoxl != null && "1".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有预警签收数据
				page = "/jxkh/yj/yjqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				eWRecieveList = eWarningService.findObjects(" FROM EWRecieve where bjbm = ? and (bjsj between ? and ?) order by bjsj desc ", param);
				//结果
				request.setAttribute("eWRecieveList", eWRecieveList);
			} else if(infodl != null && "1".equals(infodl.trim()) 
					&& infoxl != null && "2".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有按时签收的预警数据
				page = "/jxkh/yj/yjqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				param.add((Integer.parseInt(yjqsTime) * 60));
				eWRecieveList = eWarningService.findObjects(" FROM EWRecieve where bjbm = ? and (bjsj between ? and ?) and qrzt != '0' and TIMESTAMPDIFF(SECOND, bjsj, qrsj) <= ? order by bjsj desc ", param);
				//结果
				request.setAttribute("eWRecieveList", eWRecieveList);
			} else if(infodl != null && "1".equals(infodl.trim()) 
					&& infoxl != null && "3".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有超时签收的预警数据
				page = "/jxkh/yj/yjqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				param.add((Integer.parseInt(yjqsTime) * 60));
				eWRecieveList = eWarningService.findObjects(" FROM EWRecieve where bjbm = ? and (bjsj between ? and ?) and qrzt != '0' and TIMESTAMPDIFF(SECOND, bjsj, qrsj) > ? order by bjsj desc ", param);
				//结果
				request.setAttribute("eWRecieveList", eWRecieveList);
			} else if(infodl != null && "1".equals(infodl.trim()) 
					&& infoxl != null && "4".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有未签收的预警数据
				page = "/jxkh/yj/yjqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				eWRecieveList = eWarningService.findObjects(" FROM EWRecieve where bjbm = ? and (bjsj between ? and ?) and qrzt = '0' order by bjsj desc ", param);
				//结果
				request.setAttribute("eWRecieveList", eWRecieveList);
			} else if(infodl != null && "2".equals(infodl.trim()) 
					&& infoxl != null && "1".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有指令签收数据
				page = "/jxkh/yj/zlqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				instructionSignList = eWarningService.findObjects(" FROM InstructionSign where zlbm = ? and (zlsj between ? and ?) order by zlsj desc ", param);
				//结果
				request.setAttribute("instructionSignList", instructionSignList);
			} else if(infodl != null && "2".equals(infodl.trim()) 
					&& infoxl != null && "2".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有按时签收的指令数据
				page = "/jxkh/yj/zlqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				param.add((Integer.parseInt(zlqsTime) * 60));
				instructionSignList = eWarningService.findObjects(" FROM InstructionSign where zlbm = ? and (zlsj between ? and ?) and qszt = '1' and TIMESTAMPDIFF(SECOND, zlsj, qssj) <= ? order by zlsj desc ", param);
				//结果
				request.setAttribute("instructionSignList", instructionSignList);
			} else if(infodl != null && "2".equals(infodl.trim()) 
					&& infoxl != null && "3".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有未按时签收的指令数据
				page = "/jxkh/yj/zlqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				param.add((Integer.parseInt(zlqsTime) * 60));
				instructionSignList = eWarningService.findObjects(" FROM InstructionSign where zlbm = ? and (zlsj between ? and ?) and qszt = '1' and TIMESTAMPDIFF(SECOND, zlsj, qssj) > ? order by zlsj desc ", param);
				//结果
				request.setAttribute("instructionSignList", instructionSignList);
			} else if(infodl != null && "2".equals(infodl.trim()) 
					&& infoxl != null && "4".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有未签收的指令数据
				page = "/jxkh/yj/zlqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				instructionSignList = eWarningService.findObjects(" FROM InstructionSign where zlbm = ? and (zlsj between ? and ?) and qszt = '0' order by zlsj desc ", param);
				//结果
				request.setAttribute("instructionSignList", instructionSignList);
			} else if(infodl != null && "3".equals(infodl.trim()) 
					&& infoxl != null && "1".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有指令反馈数据
				page = "/jxkh/yj/zlfkInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				instructionSignFkList = eWarningService.findObjects(" FROM InstructionSign where zlbm = ? and (zlsj between ? and ?) order by zlsj desc ", param);
				//结果
				request.setAttribute("instructionSignFkList", instructionSignFkList);
			} else if(infodl != null && "3".equals(infodl.trim()) 
					&& infoxl != null && "2".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有按时反馈的指令数据
				page = "/jxkh/yj/zlfkInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				param.add((Integer.parseInt(zlfkTime) * 60 * 60));
				instructionSignFkList = eWarningService.findObjects(" FROM InstructionSign where zlbm = ? and (zlsj between ? and ?) and fkzt = '1' and TIMESTAMPDIFF(SECOND, zlsj, fksj) <= ? order by zlsj desc ", param);
				//结果
				request.setAttribute("instructionSignFkList", instructionSignFkList);
			} else if(infodl != null && "3".equals(infodl.trim()) 
					&& infoxl != null && "3".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有未按时反馈的指令数据
				page = "/jxkh/yj/zlfkInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				param.add((Integer.parseInt(zlfkTime) * 60 * 60));
				instructionSignFkList = eWarningService.findObjects(" FROM InstructionSign where zlbm = ? and (zlsj between ? and ?) and fkzt = '1' and TIMESTAMPDIFF(SECOND, zlsj, fksj) > ? order by zlsj desc ", param);
				//结果
				request.setAttribute("instructionSignFkList", instructionSignFkList);
			} else if(infodl != null && "3".equals(infodl.trim()) 
					&& infoxl != null && "4".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有未反馈的指令数据
				page = "/jxkh/yj/zlfkInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				instructionSignFkList = eWarningService.findObjects(" FROM InstructionSign where zlbm = ? and (zlsj between ? and ?) and fkzt = '0' order by zlsj desc ", param);
				//结果
				request.setAttribute("instructionSignFkList", instructionSignFkList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	//跳转到预警签收页面
	@RequestMapping(value="findYycxtjUI")
	public String findYycxtjUI(HttpServletRequest request, Model model) throws Exception{
		//获取所有带坐标的监测点
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dayformt = new SimpleDateFormat("yyyy-MM-dd");
		JDateTime jdt = new JDateTime();
		Date nowDate = new Date();
		model.addAttribute("year", jdt.toString("YYYY"));
		model.addAttribute("month", jdt.toString("MM"));
		model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
		model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
		model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
		model.addAttribute("cxlb", "1");
		model.addAttribute("cxlxMap", getCxlxMap());
		model.addAttribute("kssj", dayformt.format(nowDate) + " 00:00:00");
		model.addAttribute("jssj", formatter.format(nowDate));
		
		return "/jxkh/yj/yycxtjListUI";
	}
	
	//跳转到预警签收页面
	@SuppressWarnings("finally")
	@RequestMapping(value="findYycxtj")
	@Description(moduleName="应用成效统计",operateType="1",operationName="应用成效统计")
	public String findYycxtj(Model model, Map<String, Object> map, HttpServletRequest request, String kssj, String jssj) throws Exception{
		//回显查询条件
		map.put("year", request.getParameter("year"));
		map.put("month", request.getParameter("month"));
		map.put("cxrq", request.getParameter("cxrq"));
		map.put("cxlb", request.getParameter("cxlb"));
		map.put("kssj", kssj);
		map.put("jssj", jssj);
		map.put("cxlxMap", getCxlxMap());
				
		//查询出列表
		String result = "1";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List listss = new ArrayList();
		List listsh = new ArrayList();
		List listwf = new ArrayList();
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("BKDL,BKDL1,BKDL2,BKDL3");
			request.setAttribute("dicList", dicList);
			request.getSession().setAttribute("jxkhYycxDicList", dicList);//放入session
			
			//查询实时拦截
			String hqlss = " from Dispatched d, InstructionSign inSign where d.bkid=inSign.bkid and inSign.sflj='1' and inSign.czjg='2' and d.bkdl='1' and (inSign.fksj between ? and ?) and inSign.zlid is not null ";
			List<Object> ssParam = new ArrayList<Object>();
			ssParam.add(formatter.parse(kssj));
			ssParam.add(formatter.parse(jssj));
			listss = eWarningService.findObjects(hqlss, ssParam);
			request.setAttribute("listss", listss);
			request.getSession().setAttribute("jxkhSsljList", listss);//放入session
			
			//查询时候拦截
			String hqlsh = " from Dispatched d, InstructionSign inSign where d.bkid=inSign.bkid and inSign.sflj='1' and inSign.czjg='2' and d.bkdl='1' and (inSign.fksj between ? and ?) and inSign.zlid is null ";
			List<Object> shParam = new ArrayList<Object>();
			shParam.add(formatter.parse(kssj));
			shParam.add(formatter.parse(jssj));
			listsh = eWarningService.findObjects(hqlsh, shParam);
			request.setAttribute("listsh", listsh);
			request.getSession().setAttribute("jxkhShljList", listsh);//放入session
			
			//违法类车辆
			String hqlwf = " from Dispatched d, InstructionSign inSign where d.bkid=inSign.bkid and inSign.sflj='1' and inSign.czjg='2' and d.bkdl='2' and (inSign.fksj between ? and ?) and inSign.zlid is null ";
			List<Object> wfParam = new ArrayList<Object>();
			wfParam.add(formatter.parse(kssj));
			wfParam.add(formatter.parse(jssj));
			listwf = eWarningService.findObjects(hqlwf, wfParam);
			request.setAttribute("listwf", listwf);
			request.getSession().setAttribute("jxkhWfljList", listwf);//放入session
		} catch (Exception e) {
			result = "2";
			e.printStackTrace();
		} finally {
			request.setAttribute("result", result);
			return "/jxkh/yj/yycxtjListUI";
		}
	}
	
	/**
	 * 应用成效统计  导出excel
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportExcelForYycx")
	@Description(moduleName="应用成效统计",operateType="1",operationName="导出应用成效")
	public void exportExcelForYycx(Model model, Map<String, Object> map, HttpServletRequest request, HttpServletResponse resp, 
			String kssj, String jssj) throws Exception{
		try {
			//获取用户信息
			User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取数据
			List<Dictionary> dicList = (List<Dictionary>)request.getSession().getAttribute("jxkhYycxDicList");
			//实时拦截
			List listss = (List)request.getSession().getAttribute("jxkhSsljList");//放入session
			//事后拦截
			List listsh = (List)request.getSession().getAttribute("jxkhShljList");//放入session
			//违法拦截
			List listwf = (List)request.getSession().getAttribute("jxkhWfljList");//放入session
			
			//输出
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("应用成效统计").append(dateFormat.format(new Date())).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
			//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			jxkhService.excelExportForYycx(operUser, kssj, jssj, dicList, listss, listsh, listwf, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}