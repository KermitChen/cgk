package com.dyst.BaseDataMsg.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.BaseDataMsg.entities.JjhomdCx;
import com.dyst.BaseDataMsg.entities.Jjhomdsp;
import com.dyst.BaseDataMsg.service.DictionaryService;
import com.dyst.BaseDataMsg.service.JjHmdService;
import com.dyst.base.utils.PageResult;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.RoleService;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.ReadConfig;
import com.dyst.utils.StaticUtils;
import com.dyst.utils.Tools;
import com.dyst.utils.excel.ExportExcelUtil;
import com.dyst.utils.excel.bean.JjhomdExcelBean;
import com.dyst.workflow.listener.homd.HomdAfterCancelFalse;
import com.dyst.workflow.listener.homd.HomdAfterCancelOverTime;
import com.dyst.workflow.listener.homd.HomdAfterCancelProcessor;
import com.dyst.workflow.listener.homd.HomdAfterCompleteFalse;
import com.dyst.workflow.listener.homd.HomdAfterCompleteProcessor;
import com.dyst.workflow.listener.homd.HomdAfterOverTime;
import com.dyst.workflow.service.WorkflowTraceService;

@Controller
@RequestMapping(value="/JjHmd")
public class JjhomdController extends DictionaryController{

	//注入JjHmdService和DictionaryService
	private JjHmdService jjHmdService;
	private DictionaryService dictionaryService;
	public JjHmdService getJjHmdService() {
		return jjHmdService;
	}
	@Autowired
	public void setJjHmdService(JjHmdService jjHmdService) {
		this.jjHmdService = jjHmdService;
	}
	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}
	@Autowired
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	@Autowired
	private RoleService roleService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected WorkflowTraceService traceService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected HistoryService historyService;
	
	/**
	 * 进入红名单首页的方法
	 * @throws Exception
	 */
	@RequestMapping(value="/findJjHmd")
	@Description(moduleName="红名单管理",operateType="1",operationName="查询红名单")
	public String findJjHmd(Model model,String Check_cpNo,String Check_cllx,String Check_clsyr,
			String Check_startTime,String Check_endTime,String Check_jlzt,PageResult pageResult,HttpServletRequest req) throws Exception{
		try {
			hpzlList = getXXXList(hpzlList, HQL_MAP.get("hpzl"));
			spjgList = getXXXList(spjgList, HQL_MAP.get("spjg"));
			jlztList = getXXXList(jlztList, HQL_MAP.get("jlzt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("cpysList", hpzlList);
		model.addAttribute("spjgList", spjgList);
		model.addAttribute("jlztList", jlztList);
		//查询记录信息
		StringBuffer hql = new StringBuffer();
		Map<String,Object> params = new HashMap<String, Object>();
		//获取当前用户角色类型等级
		User currUser = (User)req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		hql.append("select h from Jjhomd h left join Jjhomdsp hmdsp on h.jjhomdsp.id = hmdsp.id left join JjhomdCx hmdcx on h.jjhomdCx.id = hmdcx.id where convert(h.sqrjsdj,SIGNED) <= :roleType");
		params.put("roleType",Integer.parseInt(currUser.getPosition()));
		//把车牌字符串变成一个车牌数组
		if(StringUtils.isNotBlank(Check_cpNo)){
			String [] cpNos = Check_cpNo.split(",");
			int flag = 0;
			for(String cpNo :cpNos){
				if(cpNo!=null&&cpNo.length()>=1){
					hql.append(" and h.cphid like :cpNo");
					params.put("cpNo", "%"+cpNo+"%");
					flag=1;
				}else if(flag==1){
					hql.append(" 1=1 or h.cphid like :cpNo");
					params.put("cpNo", "%"+cpNo+"%");
				}
			}
		}
		if(Check_cllx!=null&&Check_cllx.length()>=1){
			hql.append(" and h.cplx = :Check_cllx");
			params.put("Check_cllx", Check_cllx);
		}
		if(Check_clsyr!=null&&Check_clsyr.length()>=1){
			hql.append(" and h.clsyz like :Check_clsyr");
			params.put("Check_clsyr", "%"+Check_clsyr+"%");
		}
		if(StringUtils.isNotBlank(Check_jlzt)){
			hql.append(" and h.jlzt = :Check_jlzt");
			params.put("Check_jlzt", Check_jlzt);
		}
		hql.append(" order by h.lrsj desc");
		pageResult = jjHmdService.getPageResult2(hql.toString(),params, pageResult.getPageNo(), 10);
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("Check_cpNo", Check_cpNo);
		model.addAttribute("Check_cllx", Check_cllx);
		model.addAttribute("Check_clsyr", Check_clsyr);
		model.addAttribute("Check_startTime", Check_startTime);
		model.addAttribute("Check_endTime", Check_endTime);
		model.addAttribute("Check_jlzt", Check_jlzt);
		return "/HmdMsg/JjHmd/listUI";
	}
	
	//异步加载车辆详细信息
	@RequestMapping(value="/getDetail")
	@Description(moduleName="红名单管理",operateType="1",operationName="查看红名单")
	public String getDetail(HttpServletRequest req,Model model,String taskId){
		//加载车辆车牌颜色
		model.addAttribute("cpysList", hpzlList);
		//获取传过来的id,根据id查询车辆的详细信息
		String id = CommonUtils.keyWordFilter(req.getParameter("id"));
		Jjhomd hmdDetail = jjHmdService.getHmdById(Integer.parseInt(id));
		model.addAttribute("hmdDetail", hmdDetail);
		//加载审批流程记录
		List<Comment> commentList = new ArrayList<Comment>();
		if(Tools.isNotEmpty(taskId)){
			commentList = traceService.findCommentByTaskId(taskId);
		}else{
			List<HistoricTaskInstance> task = historyService.createHistoricTaskInstanceQuery()
					.processInstanceBusinessKey(id)
					.processDefinitionKey("homd")
					.list();
			if(task.size()>0){
				commentList = traceService.findCommentByTaskId(task.get(0).getId());
			}
		}
		List<Comment> commentList2 = new ArrayList<Comment>();
		List<HistoricTaskInstance> task = historyService.createHistoricTaskInstanceQuery()
				.processInstanceBusinessKey(id)
				.processDefinitionKey("homdcx")
				.list();
		if(task!=null &&task.size()>0){
			commentList2 = traceService.findCommentByTaskId(task.get(0).getId());
		}
		model.addAttribute("commentList", commentList);
		model.addAttribute("commentList2", commentList2);
		return "/HmdMsg/JjHmd/hmdDetail";
	}
	
	//跳转到新增页面
	@RequestMapping(value="/toAddUI")
	public String toAddUI(Model model){
		//加载车牌类型下拉框
		try {
			hpzlList = getXXXList(hpzlList, HQL_MAP.get("hpzl"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("cpysList", hpzlList);
		return "/HmdMsg/JjHmd/addUI";
	}
	
	/**
	 * 保存  新增红名单信息
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="saveJJhomd")
	@Description(moduleName="红名单管理",operateType="2",operationName="新增红名单")
	public String saveJJhomd(Model model,Jjhomd hmd,HttpServletRequest req) throws Exception{
		String page ="/HmdMsg/JjHmd/listUI";
		//获取当前用户
		User user = (User) req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		String sqr = user.getLoginName();
		hmd.setSqrjsdj(roleService.getRoleById(user.getRoleId()).getRoleType());
		hmd.setSqrjh(sqr);
		hmd.setSqrdw(user.getDeptId());
		hmd.setSqrdwmc(user.getDeptName());
		hmd.setSqrxm(user.getUserName());
		//设置初始记录状态为   未审批
		hmd.setJlzt("001");
		//设置录入时间为当前时间
		hmd.setLrsj(new Timestamp(new Date().getTime()));
		//设置新增hmd  任务状态为01
		hmd.setRwzt("01");
		//新增该条红名单信息 对应的红名单审批信息
		/*
		 * 1.初始化红名单审批信息
		 * 		申请时间
		 */
		Jjhomdsp jjhomdsp = new Jjhomdsp();
		jjhomdsp.setSqsj(new Timestamp(new Date().getTime()));
		jjhomdsp.setJjhomd(hmd);
		hmd.setJjhomdsp(jjhomdsp);
		/*
		 * 2.初始化红名单撤销信息
		 */
		JjhomdCx jjhomdcx = new JjhomdCx();
		jjhomdcx.setJjhomd(hmd);
		hmd.setJjhomdCx(jjhomdcx);
		try {
			/***************************工作流部分*********************************/
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("homdAfterCompleteProcessor", new HomdAfterCompleteProcessor());
			variables.put("homdAfterCompleteFalse", new HomdAfterCompleteFalse());
			variables.put("roleType", user.getPosition());//当前角色,进行走向判断
			variables.put("homdAfterOverTime", new HomdAfterOverTime());
			int time = Integer.parseInt(ReadConfig.getPropertiesValue("activiti", "HomdSpExamineTime"));
			long currentTime = System.currentTimeMillis() + 60 * 60 * 1000 * time;//以小时为单位
			Date date = new Date(currentTime);
			variables.put("endTime", DateFormatUtils.format(date, "yyyy-MM-dd'T'HH:mm:ss"));			
			jjHmdService.saveHomdAndStartFlow(hmd,variables);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
			return page;
		}finally {
			return "redirect:/JjHmd/findJjHmd.do";
		}
	}
	
	//加载监测点页面
	@RequestMapping(value="/getJcd")
	public String getJcd(Model model){
		return "/baseDataMsg/jcd/jcdChoose";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/findHmdAjax")
	@Description(moduleName="红名单管理",operateType="1",operationName="查询红名单")
	public void findHmdAjax(String hphm,HttpServletResponse response){
		try {
			List list = jjHmdService.wildFindHmd(hphm);
			response.setContentType("application/json");
			response.getWriter().write(JSON.toJSONString(list));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 撤销红名单
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="revokeHmd")
	@Description(moduleName="红名单管理",operateType="4",operationName="红名单撤销申请")
	public String revokeHmd(HttpServletRequest req,HttpServletResponse resp,Model model,String hmdId){
		String message = "申请撤销红名单成功！";
		int id = Integer.parseInt(hmdId);
		Jjhomd hmd = jjHmdService.getHmdById(id);
		//设置此红名单 任务状态为 撤销
		hmd.setRwzt("02");
		/***********************撤销红名单,并加工作流*****************************/
		try {
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("HomdAfterCancelProcessor", new HomdAfterCancelProcessor());
			variables.put("HomdAfterCancelFalse", new HomdAfterCancelFalse());
			//获取当前用户
			User user = (User) req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			variables.put("roleType", user.getPosition());//当前角色,进行走向判断
			variables.put("HomdAfterCancelOverTime", new HomdAfterCancelOverTime());
			int time = Integer.parseInt(ReadConfig.getPropertiesValue("activiti", "HomdSpExamineTime"));
			long currentTime = System.currentTimeMillis() + 60 * 60 * 1000 * time;//以小时为单位
			Date date = new Date(currentTime);
			variables.put("endTime", DateFormatUtils.format(date, "yyyy-MM-dd'T'HH:mm:ss"));
			//设置hmd 撤销实体
			JjhomdCx jjhomdcx = new JjhomdCx();
			jjhomdcx.setJlzt("001");
			hmd.setJjhomdCx(jjhomdcx);
			jjhomdcx.setJjhomd(hmd);
			jjHmdService.revokeHomdAndStartFlow(hmd,variables);
		} catch (Exception e) {
			e.printStackTrace();
			message = "申请撤销失败！";
		}finally {
			writeResponse(resp,message);
			return "redirect:/JjHmd/findJjHmd.do";
		}
	}
	//取消申请红名单
	@RequestMapping(value="cancelHmd")
	@Description(moduleName="红名单管理",operateType="4",operationName="取消红名单申请")
	public void CancelHmd(HttpServletResponse resp,Model model,String state,String processInstanceId,String hmdId){
		String message ="取消申请成功！";
		int id =Integer.parseInt(hmdId);
		try {
			//终止流程
    		runtimeService.deleteProcessInstance(processInstanceId, "");
			Jjhomd hmd = jjHmdService.getHmdById(id);
			//设置为 已取消申请状态
			hmd.setJlzt("004");	
			jjHmdService.update(hmd);
		} catch (Exception e) {
			e.printStackTrace();
			message="取消申请失败";
		}
		writeResponse(resp,message); 
	}
	//取消撤销红名单的申请
	@RequestMapping(value="cancelRevokeHmd")
	@Description(moduleName="红名单管理",operateType="4",operationName="取消红名单撤销申请")
	public void CancelRevokeHmd(HttpServletResponse resp,Model model,String state,String processInstanceId,String hmdId){
		String message ="取消撤销红名单成功！";
		int id =Integer.parseInt(hmdId);
		try {
			//终止流程
    		runtimeService.deleteProcessInstance(processInstanceId, "");
			Jjhomd hmd = jjHmdService.getHmdById(id);
			//设置为 已取消申请状态
			hmd.getJjhomdCx().setJlzt("004");
			//设置任务状态为01 申请
			hmd.setRwzt("01");
			jjHmdService.update(hmd);
		} catch (Exception e) {
			e.printStackTrace();
			message="取消撤销红名单失败";
		}
		writeResponse(resp,message); 
	}
	
	/**
	 * 红名单  导出 Excel
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportHmdExcel")
	@Description(moduleName="红名单管理",operateType="1",operationName="导出红名单")
	public void exportExcel(HttpServletRequest req,HttpServletResponse resp,String Check_cpNo,String Check_cllx,String Check_clsyr,
			String Check_startTime,String Check_endTime,String Check_jlzt){
		User user = (User) req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		try {
			//根据用户条件查询出列表
			List<Jjhomd> hmdList = new ArrayList<Jjhomd>();
			Map<String,Object> params = new HashMap<String, Object>();
			StringBuilder hql = new StringBuilder();
			hql.append("select h from Jjhomd h left join Jjhomdsp hmdsp on h.jjhomdsp.id = hmdsp.id left join JjhomdCx hmdcx on h.jjhomdCx.id = hmdcx.id where convert(h.sqrjsdj,SIGNED) <= :dj ");
			params.put("dj", user.getPosition());
			String[] cphids = StringUtils.split(Check_cpNo, ",");
			if(StringUtils.isNoneBlank(Check_cpNo)){
				hql.append(" and h.cphid in :cphids");
				params.put("cphids", cphids);
			}
			if(Check_cllx!=null&&Check_cllx.length()>=1){
				hql.append(" and h.cplx = :Check_cllx");
				params.put("Check_cllx", Check_cllx);
			}
			if(Check_clsyr!=null&&Check_clsyr.length()>=1){
				hql.append(" and h.clsyz like :Check_clsyr");
				params.put("Check_clsyr", "%"+Check_clsyr+"%");
			}
			if(StringUtils.isNotBlank(Check_jlzt)){
				hql.append(" and h.jlzt = :Check_jlzt");
				params.put("Check_jlzt", Check_jlzt);
			}
			hql.append(" order by h.id desc");
			hmdList = jjHmdService.findObjects(hql.toString(), params);
			//列表转变
			List<JjhomdExcelBean> excelBeanList = new ArrayList<JjhomdExcelBean>();
			for(Jjhomd j :hmdList){
				JjhomdExcelBean excelBean = new JjhomdExcelBean();
				excelBean.setJjhomd(j);
				if(j.getRwzt().equals("01")){
					excelBean.setDqjd(j.getJjhomdsp().getTask()==null?"":j.getJjhomdsp().getTask().getName());
				}else if(j.getRwzt().equals("02")){
					excelBean.setDqjd(j.getJjhomdCx().getTask()==null?"":j.getJjhomdCx().getTask().getName());
				}
				String jlzt = j.getJlzt()==null?"":j.getJlzt();
				String cllx = j.getCplx()==null?"":j.getCplx();
				String spjg = j.getJjhomdsp().getSpjg()==null?"":j.getJjhomdsp().getSpjg();
				jlztList = getXXXList(jlztList, HQL_MAP.get("jlzt"));
				for(Object o:jlztList){
					Map<String,String> map = ((HashMap<String, String>)o);
					String serialNo = map.get("typeSerialNo");
					String desc = map.get("typeDesc");
					if(serialNo.equals(jlzt)){
						excelBean.setJlzt(desc);
						break;
					}
				}
				hpzlList = getXXXList(hpzlList, HQL_MAP.get("hpzl"));
				for(Object o:hpzlList){
					Map<String,String> map = ((HashMap<String, String>)o);
					String serialNo = map.get("typeSerialNo");
					String desc = map.get("typeDesc");
					if(serialNo.equals(cllx)){
						excelBean.setCllx(desc);
						break;
					}
				}
				spjgList = getXXXList(spjgList, HQL_MAP.get("spjg"));
				for(Object o:spjgList){
					Map<String,String> map = ((HashMap<String, String>)o);
					String serialNo = map.get("typeSerialNo");
					String desc = map.get("typeDesc");
					if(serialNo.equals(spjg)){
						excelBean.setSpjg(desc);
					}
				}
				excelBeanList.add(excelBean);
			}
			//输出
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Date now=new Date();
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("红名单申请").append(dateFormat.format(now)).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
				//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			jjHmdService.exportExcel(excelBeanList,outputStream);
			if(outputStream!=null){
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	//私有的向前端页面返回 resp 的方法
	private void writeResponse(HttpServletResponse resp,String data){
		try {
			resp.getWriter().write(data);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 红名单查询
	 * @throws Exception
	 */
	@RequestMapping(value="/queryHmd")
	@Description(moduleName="红名单查询",operateType="1",operationName="查询红名单")
	public String queryHmd(Model model,String Check_cpNo,String Check_cllx,String Check_clsyr,
			String Check_startTime,String Check_endTime,String Check_jlzt,PageResult pageResult,HttpServletRequest req) throws Exception{
		try {
			hpzlList = getXXXList(hpzlList, HQL_MAP.get("hpzl"));
			model.addAttribute("cpysList", hpzlList);
			
			//红名单记录，01有效   02无效
			List<Dictionary> jlztList = new ArrayList<Dictionary>();
			Dictionary dictionary = new Dictionary();
			dictionary.setTypeSerialNo("01");
			dictionary.setTypeDesc("有效");
			jlztList.add(dictionary);
			dictionary = new Dictionary();
			dictionary.setTypeSerialNo("02");
			dictionary.setTypeDesc("已撤销");
			jlztList.add(dictionary);
			model.addAttribute("jlztList", jlztList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//查询记录信息
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append("from Jjhomd h where h.jlzt='002' and h.zt='1' ");
		
		//把车牌字符串变成一个车牌数组
		if(StringUtils.isNotBlank(Check_cpNo)){
			String[] cphmStr = Check_cpNo.split(",");
			String a = "";
			for(int i=0;i < cphmStr.length;i++){
	    		a = a + "?";
	    		if(i < cphmStr.length-1){
	    			a = a + ",";
	    		}
	    		params.add(cphmStr[i]);
	    	}
			hql.append(" and h.cphid in(" + a + ") ");
		}
		if(Check_cllx != null && Check_cllx.length() >= 1){
			hql.append(" and h.cplx=? ");
			params.add(Check_cllx);
		}
		if(Check_clsyr != null && Check_clsyr.length() >= 1){
			hql.append(" and h.clsyz like ? ");
			params.add("%" + Check_clsyr + "%");
		}
		if(StringUtils.isNotBlank(Check_jlzt)){
			hql.append(" and h.rwzt=? ");
			params.add(Check_jlzt);
		}
		hql.append(" order by h.lrsj desc");
		pageResult = jjHmdService.getPageResult(hql.toString(), params, pageResult.getPageNo(), 10);
		
		//返回
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("Check_cpNo", Check_cpNo);
		model.addAttribute("Check_cllx", Check_cllx);
		model.addAttribute("Check_clsyr", Check_clsyr);
		model.addAttribute("Check_startTime", Check_startTime);
		model.addAttribute("Check_endTime", Check_endTime);
		model.addAttribute("Check_jlzt", Check_jlzt);
		return "/HmdMsg/JjHmd/queryHmd";
	}
	
	/**
	 * 红名单  导出 Excel
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportHmdToExcel")
	@Description(moduleName="红名单查询",operateType="1",operationName="导出红名单")
	public void exportHmdToExcel(HttpServletRequest req, HttpServletResponse resp,String Check_cpNo, String Check_cllx, String Check_clsyr,
			String Check_startTime, String Check_endTime, String Check_jlzt){
		List<Dictionary> jlztList = null;
		try {
			try {
				hpzlList = getXXXList(hpzlList, HQL_MAP.get("hpzl"));
				
				//红名单记录，01有效   02无效
				jlztList = new ArrayList<Dictionary>();
				Dictionary dictionary = new Dictionary();
				dictionary.setTypeSerialNo("01");
				dictionary.setTypeDesc("有效");
				jlztList.add(dictionary);
				dictionary = new Dictionary();
				dictionary.setTypeSerialNo("02");
				dictionary.setTypeDesc("已撤销");
				jlztList.add(dictionary);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//根据用户条件查询出列表
			List<Jjhomd> hmdList = new ArrayList<Jjhomd>();
			StringBuffer hql = new StringBuffer();
			Map<String,Object> params = new HashMap<String, Object>();
			hql.append("from Jjhomd h where h.jlzt='002' and h.zt='1' ");
			
			//把车牌字符串变成一个车牌数组
			if(StringUtils.isNotBlank(Check_cpNo)){
				hql.append(" and h.cphid in(:cphid) ");
				params.put("cphid", (Object[])Check_cpNo.trim().split(","));
			}
			if(Check_cllx != null && Check_cllx.length() >= 1){
				hql.append(" and h.cplx=:cplx ");
				params.put("cplx", Check_cllx);
			}
			if(Check_clsyr != null && Check_clsyr.length() >= 1){
				hql.append(" and h.clsyz like :clsyz ");
				params.put("clsyz", "%" + Check_clsyr + "%");
			}
			if(StringUtils.isNotBlank(Check_jlzt)){
				hql.append(" and h.rwzt=:rwzt ");
				params.put("rwzt", Check_jlzt);
			}
			hql.append(" order by h.lrsj desc");
			hmdList = jjHmdService.findObjects(hql.toString(), params);
			
			
			//列表转变
			List<JjhomdExcelBean> excelBeanList = new ArrayList<JjhomdExcelBean>();
			for(Jjhomd j:hmdList){
				JjhomdExcelBean excelBean = new JjhomdExcelBean();
				excelBean.setJjhomd(j);
				String jlzt = j.getRwzt()==null?"":j.getRwzt();
				String cplx = j.getCplx()==null?"":j.getCplx();
				for(Dictionary o:jlztList){
					String serialNo = o.getTypeSerialNo();
					String desc = o.getTypeDesc();
					if(serialNo.equals(jlzt)){
						excelBean.setJlzt(desc);
						break;
					}
				}
				for(Object o:hpzlList){
					Map<String,String> map = ((HashMap<String, String>)o);
					String serialNo = map.get("typeSerialNo");
					String desc = map.get("typeDesc");
					if(serialNo.equals(cplx)){
						excelBean.setCllx(desc);
						break;
					}
				}
				excelBeanList.add(excelBean);
			}
			
			//输出
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Date now = new Date();
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("红名单查询").append(dateFormat.format(now)).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
			//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(), "ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			ExportExcelUtil.exportExcelOfHmd(excelBeanList, outputStream);
			if(outputStream != null){
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}