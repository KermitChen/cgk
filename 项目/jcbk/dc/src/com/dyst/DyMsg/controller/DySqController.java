package com.dyst.DyMsg.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.service.DictionaryService;
import com.dyst.DyMsg.entities.Bean;
import com.dyst.DyMsg.entities.Dyxx;
import com.dyst.DyMsg.entities.DyxxXq;
import com.dyst.DyMsg.entities.Dyxxsp;
import com.dyst.DyMsg.service.DySqService;
import com.dyst.base.utils.PageResult;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.Role;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.RoleService;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.ReadConfig;
import com.dyst.utils.StaticUtils;
import com.dyst.utils.Tools;
import com.dyst.utils.excel.ExportExcelUtil;
import com.dyst.utils.excel.bean.DyExcelBean;
import com.dyst.workflow.listener.dy.AfterDyspCompleteFalse;
import com.dyst.workflow.listener.dy.AfterDyspCompleteProcessor;
import com.dyst.workflow.listener.dy.AfterDyspOvertime;
import com.dyst.workflow.service.WorkflowTraceService;

@Controller
@RequestMapping(value="/dy")
public class DySqController {
	
	//静态的所有监测点的json字符串
	public static StringBuffer treeJson= new  StringBuffer();
	
	/*
	 * 1.注入DictionaryService
	 * 2.注入DyspService
	 * 3.注入布控service
	 */
	private DictionaryService dicService;
	public DictionaryService getDicService() {
		return dicService;
	}
	@Autowired
	public void setDicService(DictionaryService dicService) {
		this.dicService = dicService;
	}
	private DySqService dySqService;
	public DySqService getDySqService() {
		return dySqService;
	}
	@Autowired
	public void setDySqService(DySqService dySqService) {
		this.dySqService = dySqService;
	}
	@Autowired
	private RoleService roleService;
	@Autowired
	private DispatchedService dispatchedService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected WorkflowTraceService traceService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected HistoryService historyService;
	
	/*
	 * 1.页面记录状态list,第一次查询后保存在内存中
	 * 2.审批结果,第一次查询后保存在内存中
	 * 3.订阅类型,第一次查询后保存在内存中
	 * 4.通知方式,第一次查询后保存在内存中
	 */
	public static List<Object> jlztList = new ArrayList<Object>();
	public static List<Object> spjgList = new ArrayList<Object>();
	public static List<Object> dylxList = new ArrayList<Object>();
	public static List<Object> tzfsList = new ArrayList<Object>();
	public static List<Object> hpzlList = new ArrayList<Object>();
	public static List<Object> ywztList = new ArrayList<Object>();
	
	public static Map<String,String> HQL_MAP;
	static{
		HQL_MAP = new HashMap<String, String>();
		HQL_MAP.put("jlzt", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HmdSpZt' and d.deleteFlag = '0' ");
		HQL_MAP.put("spjg", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HmdSpJg' and d.deleteFlag = '0' ");
		HQL_MAP.put("dylx", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'DyLx' and d.deleteFlag = '0' ");
		HQL_MAP.put("ywzt", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'DyYwZt' and d.deleteFlag = '0' ");
	}
	/*
	 * 1.获取jlztList
	 * 2.获取spjgList
	 * 3.获取dylxList
	 */
	public List<Object> getXXXList(List<Object> xxxList,String hql) throws Exception{
		xxxList.clear();
		xxxList = dicService.findObjects(hql, null);
		return xxxList;
	}
	
	//查询订阅申请首页的方法，根据录入时间降序排列
	@RequestMapping(value="findDy")
	@Description(moduleName="订阅申请管理",operateType="1",operationName="查询订阅申请")
	public String findDy(PageResult pageResult, Model model, String Check_dylx, String Check_jlzt, 
			String Check_spjg, String Check_startTime, String Check_endTime, HttpServletRequest req) throws Exception{
		/*
		 * 1.获取记录状态list
		 * 2.获取审批结果list
		 * 3.获取订阅类型list
		 * 4.获取业务状态list
		 */
		String hql1 = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HmdSpZt' and d.deleteFlag = '0' ";
		jlztList = getXXXList(jlztList,hql1);
		String hql2 = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HmdSpJg' and d.deleteFlag = '0' ";
		spjgList = getXXXList(spjgList,hql2);
		String hql3 = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'DyLx' and d.deleteFlag = '0' ";
		dylxList = getXXXList(dylxList,hql3);
		String hql4 = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'DyYwZt' and d.deleteFlag = '0' ";
		ywztList = getXXXList(ywztList,hql4);
		
		StringBuffer hql = new StringBuffer();
		Map<String,Object> params = new HashMap<String, Object>();
		//获取当前用户角色类型等级
		User currUser = (User)req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		Role role = roleService.getRoleById(currUser.getRoleId());
		hql.append("select dy from Dyxx d, Dyxxsp dy where d.id = dy.dyxx.id and convert(d.sqrjsdj,SIGNED) <= :roleType");
		params.put("roleType", Integer.parseInt(role.getRoleType()));
		//根据传过来的查询条件，在DYXX表中查询符合的数据
		/*
		 * 1.记录状态
		 * 2.审批结果
		 * 3.起止日期
		 * 4.订阅类型
		 */
		if(StringUtils.isNotBlank(Check_jlzt)){
			hql.append(" and d.jlzt = :Check_jlzt");
			params.put("Check_jlzt", Check_jlzt);
		}
		if(StringUtils.isNotBlank(Check_spjg)){
			hql.append(" and dy.spjg = :Check_spjg");
			params.put("Check_spjg", Check_spjg);
		}
		if(StringUtils.isNotBlank(Check_startTime)){
			hql.append(" and d.qssj >= :Check_startTime");
			params.put("Check_startTime", DateUtils.parseDate(Check_startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(Check_endTime)){
			hql.append(" and d.jzsj <= :Check_endTime");
			params.put("Check_endTime", DateUtils.parseDate(Check_endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(Check_dylx)){
			String [] dys = Check_dylx.split(";");
			hql.append(" and d.dylx in(:Check_dylx)");
			params.put("Check_dylx", dys);
		}
		hql.append(" order by d.id desc");
		pageResult = dySqService.getPageResult2(hql.toString(), params, pageResult.getPageNo(), 10);
		String data = JSON.toJSONString(dylxList);
		
		model.addAttribute("ywztList", ywztList);
		model.addAttribute("jlztList", jlztList);
		model.addAttribute("spjgList", spjgList);
		model.addAttribute("spjgList1", data);
		model.addAttribute("dylxList", dylxList);
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("Check_dylx", Check_dylx);
		model.addAttribute("Check_jlzt", Check_jlzt);
		model.addAttribute("Check_spjg", Check_spjg);
		model.addAttribute("Check_startTime", Check_startTime);
		model.addAttribute("Check_endTime", Check_endTime);
		return "DyMsg/dysq/listUI";
	}
	
	//跳转到.....新增页面
	@RequestMapping(value="toAddUI")
	public String toAddUI(Model model) throws Exception{
		//通知方式
		String hql4 = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'TzFs' and d.deleteFlag != '1'";
		tzfsList = getXXXList(tzfsList,hql4);
		//订阅类型
		String hql3 = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'DyLx' and d.deleteFlag != '1'";		
		dylxList = getXXXList(dylxList,hql3);
		String data = JSON.toJSONString(dylxList);
		model.addAttribute("dylxList1", data);
		model.addAttribute("dylxList", dylxList);
		model.addAttribute("tzfsList", tzfsList);
		//加载车辆类型
		String hql = "select distinct new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc)from Dictionary d where d.typeCode ='HPZL' and d.deleteFlag !='1'";
		List<Object> list = dicService.findObjects(hql, null);
		String data1 = JSON.toJSONString(list);
		model.addAttribute("cllxlist", list);
		model.addAttribute("cllxListJson", data1);
		return "DyMsg/dysq/addUI";
	}
	
	
	//订阅申请新增页面中点击检索布控车牌，弹出筛选窗口
	@SuppressWarnings("finally")
	@RequestMapping(value="/getJsBkCpUI")
	public String getJsBkCpUI(Model model,String[] ids,HttpServletRequest request, HttpServletResponse response){
		/*
		 * 1.加载布控类型
		 * 2.加载车牌颜色
		 */
		String page = "/DyMsg/dysq/JsBkCp";
		try {
			//获取布控类别
			String sql_bklb = "select d.ID ID,d.NAME NAME from DIC_DISPATCHED_TYPE d";
			List<Map> bklbList = dispatchedService.findList(sql_bklb, null);
			model.addAttribute("bklbList", bklbList);
			//加载车牌颜色
			String sql_cpys = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HPZL' and d.deleteFlag !='1'";
			hpzlList =  getXXXList(hpzlList,sql_cpys);
			model.addAttribute("hpzlList", hpzlList);
			//回显已经布控的车辆
			//String temp = request.getParameter("ids");
			//ids = temp.split(",");
			String hasBkcps = getListByIds(ids);
			model.addAttribute("data", hasBkcps);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	//根据ids数组查询已经选中的车牌
	@Description(moduleName="订阅申请管理",operateType="1",operationName="根据ids数组查询已经选中的布控名单")
	private String getListByIds(String[] ids){
		StringBuffer hql = new StringBuffer();
		hql.append("select new map(b.bkid as bkid,b.hphm as hphm) from Dispatched as b where 1=1");
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isNoneBlank(ids)){
			hql.append(" and b.hphm in(:ids)");
			params.put("ids", ids);
			List<Object> list = dySqService.getList(hql.toString(), params);
			String data = JSON.toJSONString(list);
			return data;
		}
		return JSON.toJSONString("");
	}
	
	//异步...检索布控的车牌
	@RequestMapping(value="/getBkCp")
	@Description(moduleName="订阅申请管理",operateType="1",operationName="检索布控名单")
	public void getBkCp(Model model,String Check_bklx,String Check_hpzl,String[] Check_cphms,
			HttpServletResponse resp){
		StringBuffer sql = new StringBuffer();
		sql.append("select new map(b.bkid as bkid,b.hphm as hphm) from Dispatched as b where b.ywzt in (1,7) and b.by3 = '0'");
		Map<String,Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(Check_hpzl)){
			sql.append(" and b.hpzl = :Check_hpzl");
			params.put("Check_hpzl", Check_hpzl);
		}
		if(StringUtils.isNotBlank(Check_bklx)){
			sql.append(" and b.bklb = :Check_bklx");
			params.put("Check_bklx", Check_bklx);
		}
		if(StringUtils.isNoneBlank(Check_cphms)){
			sql.append(" and b.hphm in(:Check_cphms)");
			params.put("Check_cphms", Check_cphms);
		}
		List<Object> list = dySqService.getList(sql.toString(), params);
		String data = JSON.toJSONString(list);
		try {
			resp.getWriter().write(data);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//异步...检索已选车牌
	@RequestMapping(value="/getYxBkcp")
	@Description(moduleName="订阅申请管理",operateType="1",operationName="检索布控名单")
	public void getYxBkcp(Model model,String[] Check_cphms,HttpServletResponse resp){
		StringBuffer sql = new StringBuffer();
		sql.append("select new map(b.bkid as bkid,b.hphm as hphm,b.hpzl as hpzl,d.typeDesc as typeDesc) from Dispatched as b,Dictionary as d where b.hpzl=d.typeSerialNo and d.typeCode='HPZL' ");
		Map<String,Object> params = new HashMap<String, Object>();
		Integer array[] = new Integer[Check_cphms.length];
		for(int i=0;i<Check_cphms.length;i++){  
		    array[i]=Integer.parseInt(Check_cphms[i]);
		}
		if(StringUtils.isNoneBlank(Check_cphms)){
			sql.append(" and b.bkid in (:Check_cphms)");
			params.put("Check_cphms", array);
			List<Object> list = dySqService.getList(sql.toString(), params);
			String data = JSON.toJSONString(list);
			try {
				resp.getWriter().write(data);
				resp.getWriter().flush();
				resp.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String data = JSON.toJSONString("");
			try {
				resp.getWriter().write(data);
				resp.getWriter().flush();
				resp.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//跳转...layer选择   短信接收的用户页面
	@RequestMapping(value="/getDxJsYhs")
	public String getDxJsYhs(){
		return "/DyMsg/dysq/usersChoose";
	}
	
	//异步...加载用户Ztree，json字符串
	@RequestMapping(value="/getTreeJson3")
	@Description(moduleName="订阅申请管理",operateType="1",operationName="检索用户手机号")
	public void getTreeJson3(HttpServletResponse resp,Model model){
		//最后还是选用了简单json格式的数据
		//查询出pid为00的二级城区
/*		if(treeJson.toString().isEmpty()){
			getJsonTree();
		}*/
		getJsonTree();
		try {
			resp.getWriter().write(treeJson.toString());
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//获得用户jsonTree方法
	private String getJsonTree(){
		//部门list
		String hql2 ="select new map(a.deptNo as id ,a.parentNo as pId ,a.deptName as name) from Department a";
		List<Object> list2 = dySqService.getList(hql2, null);
		//人员list
		String hql1 = "select new map(u.loginName as id,u.deptId as pId,u.userName as name,u.telPhone as phone) from User u";
		List<Object> list1 = dySqService.getList(hql1, null);
		list2.addAll(list1);
		treeJson.setLength(0);
		treeJson.append(JSON.toJSONString(list2));		
		return treeJson.toString();
	}
	
	//保存新增的订阅信息
	@RequestMapping(value="/addDyxx")
	@Description(moduleName="订阅申请管理",operateType="2",operationName="新增订阅")
	public String addDyxx(Model model,Dyxx dyxx,Bean bean,String[] tzfsList,HttpSession session) throws Exception{
		/*
		 * 1.添加录入时间
		 * 2.人员
		 * 3.记录状态 待审批
		 */
		dyxx.setLrsj(new Timestamp(new Date().getTime()));
		User user = (User) session.getAttribute("USER_OBJ");
		dyxx.setSqrjsdj(roleService.getRoleById(user.getRoleId()).getRoleType());
		dyxx.setJyxm(user.getUserName());
		dyxx.setJyjh(user.getLoginName());
		dyxx.setDwmc(user.getDeptName());
			//设置状态为  未审批
		dyxx.setJlzt("001");
		StringBuffer tzfs = new StringBuffer();
		for(int i=0;i<tzfsList.length;i++){
			tzfs.append(tzfsList[i]);
			if(i<tzfsList.length-1){
				tzfs.append(",");
			}
		}
		dyxx.setTzfs(tzfs.toString());
		//遍历取过来的订阅详情列表，向dyxx（订阅信息表中添加详情信息）
		Set<DyxxXq> dyxxXqs = new HashSet<DyxxXq>();
		for(DyxxXq d:bean.getXqList()){
			if(!(d.getHphm2()==null)){
				dyxxXqs.add(d);
			}else if(d.getHphm2()==null){
				continue;
			}
		}
		dyxx.setDyxxXqs(dyxxXqs);
		//初始化 ...订阅审批表
		Dyxxsp dyxxsp = new Dyxxsp();
		dyxxsp.setSqsj(new Timestamp(new Date().getTime()));
		Set<Dyxxsp> set = new HashSet<Dyxxsp>();
		set.add(dyxxsp);
		dyxx.setDyxxsps(set);		
		//工作流信息
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("afterDyspCompleteProcessor", new AfterDyspCompleteProcessor());
		variables.put("afterDyspCompleteFalse", new AfterDyspCompleteFalse());
		variables.put("afterDyspOvertime", new AfterDyspOvertime());
		String valueX = "";
		String roleType = user.getPosition();
		if(roleType.length() > 2){
			roleType = roleType.substring(0, 2)+"X";
			valueX = roleType.substring(2, 3);
		}
		variables.put("roleType", roleType);//当前角色,进行走向判断
		variables.put("X", valueX);//管控类的角色尾数编号
		int time = Integer.parseInt(ReadConfig.getPropertiesValue("activiti", "DySpExamineTime"));
		long currentTime = System.currentTimeMillis() + 60 * 60 * 1000 * time;//以小时为单位
		Date date = new Date(currentTime);
		variables.put("endTime", DateFormatUtils.format(date, "yyyy-MM-dd'T'HH:mm:ss"));
		//保存 订阅信息
		dySqService.addDysq(dyxx,variables);
		return "redirect:/dy/findDy.do";
	}

	//订阅申请页面...点击详情..弹出详情页
	@RequestMapping(value="/getDetail")
	@Description(moduleName="订阅申请管理",operateType="1",operationName="查看订阅详情")
	public String getDetail(Model model,HttpServletRequest req,HttpServletResponse resp,String taskId) throws Exception{
		String id = CommonUtils.keyWordFilter(req.getParameter("id"));
		//加载所有订阅的车牌信息 详情
		String hql = "from DyxxXq d where d.dyxx.id = :id ";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", Integer.parseInt(id));
		List<Object> list = dySqService.getList(hql, params);
		model.addAttribute("xqList", list);
		//加载车牌颜色
		String sql_cpys = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HPZL' and d.deleteFlag !='1'";
		hpzlList =  getXXXList(hpzlList,sql_cpys);
		model.addAttribute("cpysList", hpzlList);
		//加载订阅类型
		String hql3 = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'DyLx' and d.deleteFlag != '1'";
		dylxList = getXXXList(dylxList,hql3);
		model.addAttribute("dylxList", dylxList);
		//加载该订阅详情
		String hql4 = "from Dyxx d where d.id = :id ";
		List<Object> dyxxList = dySqService.getList(hql4, params);
		model.addAttribute("dyxxList", dyxxList);
		//加载记录状态
		String hql_jlzt = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HmdSpZt' and d.deleteFlag !='1'";
		List<Object> jlztList = dySqService.getList(hql_jlzt, null);
		//加载审批结果
		String hql2 = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HmdSpJg' and d.deleteFlag = '0'";
		spjgList = getXXXList(spjgList,hql2);
		//
		String xqList = JSON.toJSONString(dyxxList);
		model.addAttribute("xqListJson", xqList);
		String cpysList = JSON.toJSONString(hpzlList);
		model.addAttribute("cpysList", cpysList);
		String dylxListJson = JSON.toJSONString(dylxList);
		model.addAttribute("dylxListJson", dylxListJson);
		String jlztListJson = JSON.toJSONString(jlztList);
		model.addAttribute("jlztListJson", jlztListJson);
		String spjgListJson = JSON.toJSONString(spjgList);
		model.addAttribute("spjgListJson", spjgListJson);
		List<Comment> commentList = new ArrayList<Comment>();
		if(Tools.isNotEmpty(taskId)){
			commentList = traceService.findCommentByTaskId(taskId);
		}else{
			List<HistoricTaskInstance> task = historyService.createHistoricTaskInstanceQuery()
					.processInstanceBusinessKey(id)
					.processDefinitionKey("dysp")
					.list();
			if(task.size()>0){
				commentList = traceService.findCommentByTaskId(task.get(0).getId());
			}
		}
		model.addAttribute("commentList", commentList);
		return "/DyMsg/dysq/dyDetail";
	}
	
	//返回监测点详情页面
	@RequestMapping(value="showJcds")
	public String showJcds(Model model,HttpServletRequest req,HttpServletResponse resp){
/*		String xqid = req.getParameter("xqid");
		DyxxXq dyxq = null;
		try {
			dyxq = dySqService.findDyxxXqById(Integer.parseInt(xqid));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dyxq!=null){
			String[] jcds = StringUtils.split(dyxq.getJcd2(), ",");
			writeResponse(resp, JSON.toJSONString(jcds));
		}else{
			writeResponse(resp, "error");
		}*/
		return "/DyMsg/dysq/showJcds";
	}
	
	//跳转到编辑页面
	@RequestMapping(value="/getDetailEditUI")
	@Description(moduleName="订阅申请管理",operateType="1",operationName="查看订阅详情")
	public String getDetailEditUI(Model model,HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String id = CommonUtils.keyWordFilter(req.getParameter("id"));
		//加载所有订阅的车牌信息 详情
		String hql = "from DyxxXq d where d.dyxx.id = :id";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", Integer.parseInt(id));
		List<Object> list = dySqService.getList(hql, params);
		model.addAttribute("xqList", list);
		//加载车牌颜色
		String sql_cpys = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HPZL' and d.deleteFlag !='1'";
		hpzlList =  getXXXList(hpzlList,sql_cpys);
		model.addAttribute("hpzlList", hpzlList);
		//加载订阅类型
		String hql3 = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'DyLx' and d.deleteFlag != '1'";
		dylxList = getXXXList(dylxList,hql3);
		model.addAttribute("dylxList", dylxList);
		//加载该订阅详情
		String hql4 = "from Dyxx d where d.id = :id";
		List<Object> dyxxList = dySqService.getList(hql4, params);
		model.addAttribute("dyxxList", dyxxList);
		//加载记录状态
		String hql_jlzt = "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HmdSpZt' and d.deleteFlag !='1'";
		List<Object> jlztList = dySqService.getList(hql_jlzt, null);
		//
		String xqList = JSON.toJSONString(dyxxList);
		model.addAttribute("xqListJson", xqList);
		String cpysList = JSON.toJSONString(hpzlList);
		model.addAttribute("cpysList", cpysList);
		String dylxListJson = JSON.toJSONString(dylxList);
		model.addAttribute("dylxListJson", dylxListJson);
		String jlztListJson = JSON.toJSONString(jlztList);
		model.addAttribute("jlztListJson", jlztListJson);
		return "/DyMsg/dysq/dyDetailEditUI";
	}
	
	//保存编辑后的订阅详情信息
	@RequestMapping(value="updateDyDetail")
	@Description(moduleName="订阅申请管理",operateType="3",operationName="修改订阅")
	public void updateDyDetail(Model model,String _qssj,String _jzsj,String[] tzfs,String[] jcds,String id,String bz
			,HttpServletRequest req,HttpServletResponse resp) throws ParseException{
		//根据id获取对应的订阅详细信息 的实体
		try {
			//参数
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("id", id);
			
			String hql1 = "from Dyxx d where d.id = :id ";
			Dyxx dyxx = (Dyxx) dySqService.getList(hql1, params).get(0);
			for(DyxxXq d:dyxx.getDyxxXqs()){
				d.setBz2(bz);
			}
			if(StringUtils.isNoneBlank(_qssj)){
				dyxx.setQssj(changeTime(_qssj));
			}
			if(StringUtils.isNoneBlank(_jzsj)){
				dyxx.setJzsj(changeTime(_jzsj));
			}
			dySqService.updateDyxx(dyxx);
			
			String hql2 = "from Dyxx d where d.id = :id ";
			List<Object> dyxx_List = dySqService.getList(hql2, params);
			String dyxxList = JSON.toJSONString(dyxx_List);
			writeResponse(resp, dyxxList);
		} catch (Exception e) {
			writeResponse(resp,"0");
		}

	}
	//私有的把字符串时间转化成数据库TimeStamp
	private Timestamp changeTime(String time) throws ParseException{
		return new Timestamp(DateUtils.parseDate(time,"yyyy-MM-dd HH:mm:ss").getTime());
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
     * 取消申请
     */
    @RequestMapping(value = "update", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @Description(moduleName="订阅申请管理",operateType="4",operationName="取消订阅申请")
	public String updateState(String state,String processInstanceId,int id) {
    	try {
    		//终止流程
    		runtimeService.deleteProcessInstance(processInstanceId, "");
    		Dyxx dyxx = dySqService.findDyxxById(id);
    		dyxx.setJlzt("004");
    		dySqService.updateDyxx(dyxx);
	    	return "success";
    	} catch (Exception e) {
        	e.printStackTrace();
            return "error";
        }
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value="exportDyExcel")
    @Description(moduleName="订阅申请管理",operateType="1",operationName="导出订阅信息")
	public void exportDyExcel(HttpServletRequest req,HttpServletResponse resp,String Check_dylx,String Check_jlzt,
			String Check_spjg,String Check_startTime,String Check_endTime) throws Exception{
    	User user = (User) req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
    	if(user!=null){
    		//根据用户查询出列表
    		StringBuilder hql = new StringBuilder();
    		Map<String,Object> params = new HashMap<String, Object>();
    		List<Dyxx> dyxxList = new ArrayList<Dyxx>();
    		List<DyExcelBean> dyExcelBeanList = new ArrayList<DyExcelBean>();
    		hql.append("select d from Dyxx d,Dyxxsp dy where d.id = dy.dyxx.id and convert(d.sqrjsdj,SIGNED) <= :roleType");
    		params.put("roleType", Integer.parseInt(user.getPosition()));
    		if(StringUtils.isNotBlank(Check_jlzt)){
    			hql.append(" and d.jlzt = :Check_jlzt");
    			params.put("Check_jlzt", Check_jlzt);
    		}
    		if(StringUtils.isNotBlank(Check_spjg)){
    			hql.append(" and dy.spjg = :Check_spjg");
    			params.put("Check_spjg", Check_spjg);
    		}
    		if(StringUtils.isNotBlank(Check_startTime)){
    			hql.append(" and d.qssj >= :Check_startTime");
    			params.put("Check_startTime", DateUtils.parseDate(Check_startTime, "yyyy-MM-dd HH:mm:ss"));
    		}
    		if(StringUtils.isNotBlank(Check_endTime)){
    			hql.append(" and d.jzsj <= :Check_endTime");
    			params.put("Check_endTime", DateUtils.parseDate(Check_endTime, "yyyy-MM-dd HH:mm:ss"));
    		}
    		if(StringUtils.isNotBlank(Check_dylx)){
    			String [] dys = Check_dylx.split(";");
    			hql.append(" and d.dylx in(:Check_dylx)");
    			params.put("Check_dylx", dys);
    		}
    		hql.append(" order by d.id desc");
    		dyxxList = dySqService.findExcelList(hql.toString(), params);
    		if(dyxxList.size()>=1){
    			for(Dyxx d: dyxxList){
    				DyExcelBean dyExcelBean = new DyExcelBean();
    				dyExcelBean.setDyxx(d);
    				String jlzt = d.getJlzt()==null?"":d.getJlzt();
    				String dylx = d.getDylx()==null?"":d.getDylx();
    				String spjg = "";
    				for(Dyxxsp dysp:d.getDyxxsps()){
    					if(dysp.getSpjg()!=null){
    						spjg = dysp.getSpjg();
    						break;
    					}
    				}
    				String ywzt = d.getYwzt()==null?"":d.getYwzt();
    				//记录状态转换
    				jlztList = getXXXList(jlztList, HQL_MAP.get("jlzt"));
    				for(Object o:jlztList){
    					Map<String,String> map = ((HashMap<String, String>)o);
    					String serialNo = map.get("typeSerialNo");
    					String desc = map.get("typeDesc");
    					if(serialNo.equals(jlzt)){
    						dyExcelBean.setJlzt(desc);
    						break;
    					}
    				}
    				//订阅类型转换
    				dylxList = getXXXList(dylxList, HQL_MAP.get("dylx"));
    				for(Object o:dylxList){
    					Map<String,String> map = ((HashMap<String, String>)o);
    					String serialNo = map.get("typeSerialNo");
    					String desc = map.get("typeDesc");
    					if(serialNo.equals(dylx)){
    						dyExcelBean.setDylx(desc);
    						break;
    					}
    				}
    				//审批结果转换
    				spjgList = getXXXList(spjgList, HQL_MAP.get("spjg"));
    				for(Object o:spjgList){
    					Map<String,String> map = ((HashMap<String, String>)o);
    					String serialNo = map.get("typeSerialNo");
    					String desc = map.get("typeDesc");
    					if(serialNo.equals(spjg)){
    						dyExcelBean.setSpjg(desc);
    						break;
    					}
    				}
    				//业务状态转换
    				ywztList = getXXXList(ywztList, HQL_MAP.get("ywzt"));
    				for(Object o:ywztList){
    					Map<String,String> map = ((HashMap<String, String>)o);
    					String serialNo = map.get("typeSerialNo");
    					String desc = map.get("typeDesc");
    					if(serialNo.equals(ywzt)){
    						dyExcelBean.setYwzt(desc);
    						break;
    					}else{
    						dyExcelBean.setYwzt("");
    					}
    				}
    				dyExcelBeanList.add(dyExcelBean);
    			}
    		}
    		//输出
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Date now=new Date();
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("订阅申请列表").append(dateFormat.format(now)).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
				//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			dySqService.exportExcel(dyExcelBeanList, outputStream);
			if(outputStream!=null){
				outputStream.close();
			}
    		ExportExcelUtil.ExcelExportForDy(dyExcelBeanList, outputStream);
    	}
    }
}
