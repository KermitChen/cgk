package com.dyst.dispatched.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.dispatched.entities.DisApproveRecord;
import com.dyst.dispatched.entities.DisReceive;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.entities.Withdraw;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.dispatched.service.WithdrawService;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.Department;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.DepartmentService;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.Config;
import com.dyst.utils.IntefaceUtils;
import com.dyst.utils.ReadConfig;
import com.dyst.utils.StaticUtils;
import com.dyst.utils.Tools;
import com.dyst.utils.excel.bean.bukong.CKExcelBean;
import com.dyst.vehicleQuery.utils.ComUtils;
import com.dyst.workflow.listener.AfterWithdrawCompleteProcessor;
import com.dyst.workflow.listener.AfterWithdrawOvertime;
import com.dyst.workflow.listener.BeforeWithdrawModifyApplyProcessor;
import com.dyst.workflow.service.WorkflowTraceService;

@Controller
@RequestMapping("/withdraw")
public class WithdrawController {
	//注入业务层
	@Autowired
	private WithdrawService withdrawService;
	@Autowired
	private DispatchedService dispatchedService;
	@Autowired
	private UserService userService;
	@Autowired
	private DepartmentService deptService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected WorkflowTraceService traceService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected HistoryService historyService;
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/loadWithdrawAdd")
	@Description(moduleName="撤控管理",operateType="1",operationName="跳转新增撤控页面")
	public String loadWithdrawAdd(HttpServletRequest request, HttpServletResponse response,String bkid,Boolean isZjck){
		String page = "/dispatched/addWithdraw";
		try {
			String sql = "select dept_no,dept_name from DEPARTMENT where jxkh='1'";
			List<Map> depts = dispatchedService.findList(sql, null);
			
			Dispatched dispatched= dispatchedService.findDispatchedById(bkid);
			
			request.setAttribute("dispatched", dispatched);
			request.setAttribute("depts", JSON.toJSONString(depts));
			request.setAttribute("bkid", bkid);
			request.setAttribute("isZjck", isZjck);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	} 
	
	/**
	 * 撤控申请
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "addWithdraw", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	@Description(moduleName="撤控管理",operateType="2",operationName="新增撤控")
	public String addWithdraw(HttpServletRequest request, HttpServletResponse response,Withdraw withdraw,String bkid,Boolean isZjck,String tzdw,String tznr) {
		try {
			Dispatched dispatched = dispatchedService.findDispatchedById(bkid);
			if(!dispatched.getYwzt().equals("1")){
				return "report";
			}
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			withdraw.setCxsqr(user.getUserName());//布控人姓名
			withdraw.setCxsqrjh(user.getLoginName());//布控人警号
			withdraw.setCxsqdw(user.getDeptId());//机关
			withdraw.setCxsqdwmc(user.getDeptName());//机关名称
			withdraw.setCxsqsj(new Timestamp(new Date().getTime()));//布控申请时间
			withdraw.setTzdw(tzdw);
			withdraw.setTznr(tznr);
			withdraw.setLskhbm(user.getLskhbm());
			withdraw.setLskhbmmc(user.getLskhbmmc());
			
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("afterWithdrawCompleteProcessor", new AfterWithdrawCompleteProcessor());
			variables.put("beforeWithdrawModifyApplyProcessor", new BeforeWithdrawModifyApplyProcessor());
			variables.put("afterWithdrawOvertime", new AfterWithdrawOvertime());
			String roleType = user.getPosition();
			if(roleType.length() > 2){
				roleType = roleType.substring(0, 2)+"X";
			}
			variables.put("roleType", roleType);//当前角色,进行走向判断
			String valueX = "";
			if(dispatched.getBkdl().equals("1") && user.getPosition().length() > 2){//涉案类
				valueX = user.getPosition().substring(2, 3);
			}else if(dispatched.getBkdl().equals("3")){
				if(dispatched.getBklb().equals("03")){
					valueX = "1";
				}else if(dispatched.getBklb().equals("04")){
					valueX = "2";
				}else if(dispatched.getBklb().equals("05")){
					valueX = "3";
				}else if(dispatched.getBklb().equals("06")){
					valueX = "4";
				}else if(dispatched.getBklb().equals("07")){
					valueX = "5";
				}else if(dispatched.getBklb().equals("08")){
					valueX = "6";
				}else if(dispatched.getBklb().equals("09")){
					valueX = "7";
				}else if(dispatched.getBklb().equals("10")){
					valueX = "8";
				}else if (dispatched.getBkdl().equals("2")){
					valueX = "0";
				}
			}else if(dispatched.getBkdl().equals("2")){//交通违法
				valueX = "0";
			}
			variables.put("X", valueX);//管控类的角色尾数编号
			if(dispatched.getBklb().equals("02")){
				variables.put("isProvince", false);//分局布控
			}else{
				variables.put("isProvince", true);//联动布控
			}
			int time = Integer.parseInt(ReadConfig.getPropertiesValue("activiti", "dispatchedExamineTime"));
			long currentTime = System.currentTimeMillis() + 60 * 60 * 1000 * time;//以小时为单位
			Date date = new Date(currentTime);
			variables.put("endTime", DateFormatUtils.format(date, "yyyy-MM-dd'T'HH:mm:ss"));
			
			if(isZjck){
				withdraw.setYwzt("1");//业务状态   1、已撤控，2、审批中，3、已取消，5、已失效，6、调整申请
				withdraw.setZjck("1");//直接布控
				dispatched.setYwzt("3");
				
				 //联动撤控
		    	String flag = "0";//撤控生效调用省布控接口   0未调用
		    	if("00".equals(dispatched.getBklb())){
		    		if("1".equals(Config.getInstance().getStInterfaceFlag().split(":")[1])){//判断开关
		    			flag = IntefaceUtils.sendWithdraw(dispatched, withdraw);//撤控生效调用省布控接口
		    		}
		    	}
		    	withdraw.setBy1(flag);
				
				withdrawService.addZJWithdraw(withdraw, variables);
				
				//撤控签收
				if(!StringUtils.isEmpty(withdraw.getTzdw())){
					String[] dws = withdraw.getTzdw().split(";");
					for(String dw: dws){
						DisReceive disReceive = new DisReceive();
						disReceive.setBkckbz("2");
						disReceive.setBkid(withdraw.getCkid());
						disReceive.setTzrjh(withdraw.getCxsqrjh());
						disReceive.setTzr(withdraw.getCxsqr());
						disReceive.setTznr(withdraw.getTznr());
						disReceive.setQrzt("0");
						List<Department> depts = deptService.getDeptByDeptNo(dw);
						if(depts != null && depts.size() > 0){
							disReceive.setQrdwmc(depts.get(0).getDeptName());
						}
						disReceive.setQrdw(dw);
						disReceive.setXfdw(withdraw.getCxsqdw());
						disReceive.setXfdwmc(withdraw.getCxsqdwmc());
						disReceive.setXfsj(new Date());
						disReceive.setHphm(dispatched.getHphm());
						disReceive.setHpzl(dispatched.getHpzl());
						disReceive.setBklb(dispatched.getBklb());
						dispatchedService.save(disReceive);
					}
				}
			} else{
				withdraw.setYwzt("2");//业务状态   1、已撤控，2、审批中，3、已取消，5、已失效，6、调整申请
				withdraw.setZjck("0");//不是直接撤控
				
				//保存用户并启动审批流程
				withdrawService.addWithdraw(withdraw,dispatched,variables);
				dispatched.setYwzt("7");
			}
			dispatchedService.updateDispatched(dispatched);
			
            return "success";
        } catch (Exception e) {
			e.printStackTrace();
			return "error";
		} 
	}
	
	/**
	 * 撤控管理查询（当前部门的）
	 * @param pageResult
	 * @return pageResult
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/findWithdraw")
	@Description(moduleName="撤控管理",operateType="1",operationName="当前部门撤控管理")
	public String findDispatched(HttpServletRequest request, HttpServletResponse response,Withdraw withdraw,PageResult pageResult,String qssj,String jzsj){
		String page = "/dispatched/queryWithdraw";
		try {	
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			SimpleDateFormat   formatter   =  new   SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,CKYWZT");
			request.setAttribute("dicList", dicList);
			//获取布控类别
//			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
//			List<Map> bklbList = dispatchedService.findList(sql, null);
//			request.setAttribute("bklb",bklbList);
//			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			QueryHelper queryHelper = new QueryHelper(Withdraw.class, "w");
			//获取前台页面要查询的条件
			if(Tools.isNotEmpty(qssj)){
				queryHelper.addCondition("w.cxsqsj > ?", formatter.parse(qssj));
			}
			if(Tools.isNotEmpty(jzsj)){
				queryHelper.addCondition("w.cxsqsj < ?", formatter.parse(jzsj));
			}
			queryHelper.addCondition("w.lskhbm = ?", user.getLskhbm());
			//按最后修改时间降序
			queryHelper.addOrderByProperty("w.cxsqsj", QueryHelper.ORDER_BY_DESC);
			pageResult = withdrawService.getPageResult(queryHelper, pageResult.getPageNo(), 10,false);
			request.setAttribute("qssj", qssj);
			request.setAttribute("jzsj", jzsj);
			request.setAttribute("withdraw", withdraw);
			request.setAttribute("pageResults", pageResult);
			request.setAttribute("conPath", "findWithdraw");
			request.setAttribute("userid", user.getLoginName());
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 撤控导出excel
	 * @throws Exception 
	 */
	@RequestMapping(value="exportExcelForCheKong")
	@Description(moduleName="撤控管理",operateType="1",operationName="导出当前部门撤控信息")
	public void exportExcelForCheKong(HttpServletRequest req, HttpServletResponse resp,Withdraw withdraw,String qssj,String jzsj) throws Exception{
		User user = (User)req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		String conPath = req.getParameter("conPath");
		//获取数据字典
		List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKJB,BKFWLX,BKFW,BKXZ,BJYA,CKYWZT");
		req.setAttribute("dicList", dicList);
		StringBuffer hql = new StringBuffer();
		Map<String,Object> params = new HashMap<String, Object>();
		hql.append(" from Withdraw w where 1=1");
		if(StringUtils.isNotBlank(qssj)){
			hql.append(" and w.cxsqsj >=:qssj");
			params.put("qssj", DateUtils.parseDate(qssj, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(jzsj)){
			hql.append(" and w.jzsj <=:jzsj");
			params.put("jzsj", DateUtils.parseDate(jzsj, "yyyy-MM-dd HH:mm:ss"));
		}
		if(conPath.equals("findWithdraw")&&StringUtils.isNotBlank(user.getLskhbm())){
			hql.append(" w.lskhbm =:dw");
			params.put("dw", user.getLskhbm());
		}
		//按最后修改时间降序
		hql.append(" order by w.cxsqsj desc");
		List<Withdraw> list = withdrawService.findList(hql.toString(), params);
		//封装excel bean
		CKExcelBean bean = new CKExcelBean(dicList,list);
		//导出
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Date now=new Date();
		StringBuilder filename = new StringBuilder();//文件名
		filename.append("布控综合查询").append(dateFormat.format(now)).append(".xls");
		resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
			//附件形式，并指定文件名
		resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
		ServletOutputStream outputStream = resp.getOutputStream();//输出流
		dispatchedService.excelExportForCK(bean, outputStream);
	}
	/**
	 * 所有布控信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/findAllWithdraw")
	@Description(moduleName="撤控管理",operateType="1",operationName="分页查询所有撤控")
	public String findAllDispatched(HttpServletRequest request, HttpServletResponse response,Withdraw withdraw,PageResult pageResult,String qssj,String jzsj){
		String page = "/dispatched/queryWithdraw";
		try {	
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			SimpleDateFormat   formatter   =  new   SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,CKYWZT");
			request.setAttribute("dicList", dicList);
			//获取布控类别
//			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
//			List<Map> bklbList = dispatchedService.findList(sql, null);
//			request.setAttribute("bklb",bklbList);
//			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			QueryHelper queryHelper = new QueryHelper(Withdraw.class, "w");
			//获取前台页面要查询的条件
			if(Tools.isNotEmpty(qssj)){
				queryHelper.addCondition("w.cxsqsj > ?", formatter.parse(qssj));
			}
			if(Tools.isNotEmpty(jzsj)){
				queryHelper.addCondition("w.cxsqsj < ?", formatter.parse(jzsj));
			}
			//按最后修改时间降序
			queryHelper.addOrderByProperty("w.cxsqsj", QueryHelper.ORDER_BY_DESC);
			pageResult = withdrawService.getPageResult(queryHelper, pageResult.getPageNo(), 10,false);
			request.setAttribute("qssj", qssj);
			request.setAttribute("jzsj", jzsj);
			request.setAttribute("withdraw", withdraw);
			request.setAttribute("pageResults", pageResult);
			request.setAttribute("conPath", "findAllWithdraw");
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 查询公开布控信息
	 * @param pageResult
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/findOpenWithdraw")
	@Description(moduleName="撤控管理",operateType="1",operationName="分页查询公开撤控")
	public String findOpenDispatched(HttpServletRequest request, HttpServletResponse response,Withdraw withdraw,PageResult pageResult,String qssj,String jzsj){
		String page = "/dispatched/queryWithdraw";
		try {	
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			SimpleDateFormat   formatter   =  new   SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,CKYWZT");
			request.setAttribute("dicList", dicList);
			//获取布控类别
//			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
//			List<Map> bklbList = dispatchedService.findList(sql, null);
//			request.setAttribute("bklb",bklbList);
//			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			QueryHelper queryHelper = new QueryHelper(Withdraw.class, "w");
			//获取前台页面要查询的条件
			if(Tools.isNotEmpty(qssj)){
				queryHelper.addCondition("w.cxsqsj > ?", formatter.parse(qssj));
			}
			if(Tools.isNotEmpty(jzsj)){
				queryHelper.addCondition("w.cxsqsj < ?", formatter.parse(jzsj));
			}
			//按最后修改时间降序
			queryHelper.addOrderByProperty("w.cxsqsj", QueryHelper.ORDER_BY_DESC);
			pageResult = withdrawService.getPageResult(queryHelper, pageResult.getPageNo(), 10,true);
			request.setAttribute("qssj", qssj);
			request.setAttribute("jzsj", jzsj);
			request.setAttribute("withdraw", withdraw);
			request.setAttribute("pageResults", pageResult);
			request.setAttribute("conPath", "findOpenWithdraw");
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
     * 待办任务列表（包括秘密任务）
     *
     * @param leave
     */
    @SuppressWarnings("finally")
	@RequestMapping(value = "withdrawTaskList")
    @Description(moduleName="撤控管理",operateType="1",operationName="分页查询撤控审批")
    public ModelAndView withdrawTaskList(HttpServletRequest request,PageResult pageResult,String success) {
    	ModelAndView mav = new ModelAndView("/dispatched/withdrawTaskList");
	    try{
	        pageResult.setPageSize(10);
	        //获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA");
			mav.addObject("dicList", dicList);
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			mav.addObject("bklb",bklbList);
	        User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
	        pageResult = withdrawService.findTodoTasks(user,pageResult.getPageNo(),pageResult.getPageSize());
	        mav.addObject("pageResults", pageResult);
	        mav.addObject("success", success);
	        mav.addObject("conPath", "withdrawTaskList");
	    } catch (Exception e) {
	    	mav = new ModelAndView("redirect:/common/errorPage/error500.jsp");
			e.printStackTrace();
		} finally{
			return mav;
		}
    }
    /**
     * 待办公开任务列表
     *
     * @param leave
     */
    @SuppressWarnings("finally")
	@RequestMapping(value = "openWithdrawTaskList")
    @Description(moduleName="撤控管理",operateType="1",operationName="分页查询公开撤控审批")
    public ModelAndView openWithdrawTaskList(HttpServletRequest request,PageResult pageResult,String success) {
    	ModelAndView mav = new ModelAndView("/dispatched/withdrawTaskList");
	    try{
	        pageResult.setPageSize(10);
	        //获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA");
			mav.addObject("dicList", dicList);
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			mav.addObject("bklb",bklbList);
	        User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
	        pageResult = withdrawService.findTodoOpenTasks(user,pageResult.getPageNo(),pageResult.getPageSize());
	        mav.addObject("pageResults", pageResult);
	        mav.addObject("success", success);
	        mav.addObject("conPath", "openWithdrawTaskList");
	    } catch (Exception e) {
	    	mav = new ModelAndView("redirect:/common/errorPage/error500.jsp");
			e.printStackTrace();
		} finally{
			return mav;
		}
    }


    /**
     * 签收任务
     */
    @SuppressWarnings("finally")
	@RequestMapping(value = "taskClaim")
    @Description(moduleName="撤控管理",operateType="3",operationName="签收撤控审批")
    public String claim(String taskId, HttpServletRequest request,RedirectAttributes redirectAttributes,String conPath) {
    	String success = "";
    	try {
	    	User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
	        taskService.claim(taskId, user.getLoginName());
	        success= "success";
        } catch (Exception e) {
        	e.printStackTrace();
        	success= "error";
        }finally{
        	return "redirect:/withdraw/"+conPath+".do?success="+success;
        }
    }

    
    /**
	 * 跳转到办理审批页面
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/loadWithdrawHandle")
	@Description(moduleName="撤控管理",operateType="1",operationName="跳转撤控审批页面")
	public String loadWithdrawHandle(HttpServletRequest request, HttpServletResponse response,String rowId,String taskId,String conPath){
		String page = "/dispatched/dealWithdraw";
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA");
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			Withdraw withdraw= withdrawService.findWithdrawById(rowId);
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			String passKey = traceService.findKeyById("ACT_PASS_KEY",task.getTaskDefinitionKey());
			request.setAttribute("passKey", passKey);
			request.setAttribute("dispatched", withdraw.getDispatched());
			request.setAttribute("withdraw", withdraw);
			request.setAttribute("task", task);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			request.setAttribute("success", "0");
			request.setAttribute("conPath", conPath);
			String hpys = ComUtils.hpzlToCplx(withdraw.getDispatched().getHpzl());
			request.setAttribute("hpys", hpys);
			request.setAttribute("user", user);
			
			//查看当前人是否有权限直接完成审批
			String roles = traceService.findKeyById("ACT_PASS_KEY","completeRole");
			Boolean isComplete = false;
			for(String r : roles.split(",")){
				if(user.getPosition().equals(r)){
					isComplete = true;
				}
			}
			request.setAttribute("isComplete", isComplete);
			//查询当前人是否有审批权限
			Boolean isDeal = true;
			if(user.getPosition().equals("81") || user.getPosition().equals("91") || user.getPosition().compareTo("80")<0){
				isDeal = false;
			}
			request.setAttribute("isDeal", isDeal);
			
			//审批记录
			List<DisApproveRecord> commentList = dispatchedService.findApproveRecord(withdraw.getCkid(), "2");
			request.setAttribute("commentList", JSON.toJSONString(commentList));
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
     * 撤控详情页面
     *
     * @param leave
     */
	@SuppressWarnings("finally")
	@RequestMapping("/detailWithdraw")
	@Description(moduleName="撤控管理",operateType="1",operationName="撤控详情")
	public String detailDispatched(HttpServletRequest request, HttpServletResponse response,String rowId,String taskId,String conPath){
		String page = "/dispatched/detailWithdraw";
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA");
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			Withdraw withdraw= withdrawService.findWithdrawById(rowId);
			//审批记录
			List<DisApproveRecord> commentList = dispatchedService.findApproveRecord(withdraw.getCkid(), "2");
//			List<Comment> commentList = new ArrayList();
//			if(Tools.isNotEmpty(taskId)){
//				commentList = traceService.findCommentByTaskId(taskId);
//			}else{
//				List<HistoricTaskInstance> task = historyService.createHistoricTaskInstanceQuery()
//						.processInstanceBusinessKey(rowId)
//						.processDefinitionKey("withdraw")
//						.list();
//				if(task.size()>0){
//					commentList = traceService.findCommentByTaskId(task.get(0).getId());
//				}
//			}
			request.setAttribute("commentList", commentList);
//			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//			String passKey = traceService.findKeyById(task.getTaskDefinitionKey());
//			request.setAttribute("passKey", passKey);
			request.setAttribute("dispatched", withdraw.getDispatched());
			request.setAttribute("withdraw", withdraw);
//			request.setAttribute("task", task);
			request.setAttribute("conPath", conPath);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 跳转到修改撤控页面
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/editWithdraw")
	@Description(moduleName="撤控管理",operateType="3",operationName="跳转修改撤控")
	public String editWithdraw(HttpServletRequest request, HttpServletResponse response,String rowId,String taskId,String conPath){
		String page = "/dispatched/editWithdraw";
		try {
			//获取数据字典
//			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA");
//			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));//JS赋值用
//			request.setAttribute("dicList", dicList);
			//获取布控类别
//			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
//			List<Map> bklbList = dispatchedService.findList(sql, null);
			Withdraw withdraw= withdrawService.findWithdrawById(rowId);
//			Dispatched dispatched= dispatchedService.findDispatchedById(rowId);
//			SimpleDateFormat   formatter   =  new   SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
//			dispatched.setBksj(formatter.parse(dispatched.getBksj().toString()));
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			String passKey = traceService.findKeyById("ACT_PASS_KEY",task.getTaskDefinitionKey());
			request.setAttribute("passKey", passKey);
			request.setAttribute("withdraw", withdraw);
//			request.setAttribute("dispatched", dispatched);
			request.setAttribute("task", task);
//			request.setAttribute("bklbList", JSON.toJSONString(bklbList));
			request.setAttribute("success", "0");
			request.setAttribute("conPath", conPath);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
     * 调整完成
     *
     * @param id
     * @return
     */
    @RequestMapping(value="editComplete")
    @Description(moduleName="撤控管理",operateType="3",operationName="修改撤控")
    public String editComplete(HttpServletRequest request, HttpServletResponse response,Withdraw withdraw
      ,String taskId,String taskProInstId, String key,Boolean value,String conPath) {
        try {
        	withdraw.setGxsj(new Timestamp(new Date().getTime()));//布控更新时间
        	//审批记录
			DisApproveRecord disApproveRecord = new DisApproveRecord();
        	if(value){
        		withdraw.setYwzt("2");//业务状态   1、布控中，2、审批中，3、已撤控，4、已取消，5、已失效，6、调整申请
//        		taskService.addComment(taskId, taskProInstId, "重新提交申请");
        		disApproveRecord.setCzjg("2");
        	}else{
        		withdraw.setYwzt("4");
//        		taskService.addComment(taskId, taskProInstId, "取消申请");
        		disApproveRecord.setCzjg("3");
        	}
        	//审批记录
    		User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
    		disApproveRecord.setYwid(withdraw.getCkid());
    		disApproveRecord.setBzw("2");
    		disApproveRecord.setCzr(user.getUserName());
    		disApproveRecord.setCzrjh(user.getLoginName());
    		String deptId = user.getDeptId().length()>12 ? user.getDeptId().substring(0, 12): user.getDeptId();
    		disApproveRecord.setCzrdw(deptId);
    		disApproveRecord.setCzrdwmc(user.getDeptName());
    		disApproveRecord.setCzrjs(user.getRoleName());
    		disApproveRecord.setCzrjslx(user.getPosition());
    		disApproveRecord.setCzsj(new Date());
    		dispatchedService.save(disApproveRecord);
        	
			Map<String, Object> variables = new HashMap<String, Object>();
			//更新布控信息
			withdrawService.updateWithdraw(withdraw);
        	variables.put(key, value);
            taskService.complete(taskId, variables);
            return "redirect:/withdraw/"+conPath+".do";
        } catch (Exception e) {
        	e.printStackTrace();
            return "redirect:/common/errorPage/error500.jsp";
        }
    }
    /**
     * 挂起、激活流程实例
     */
    @RequestMapping(value = "update", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @Description(moduleName="撤控管理",operateType="3",operationName="取消撤控申请")
    public String updateState(HttpServletRequest request,String state,String processInstanceId,String ckid) {
    	try {
//	    	if (state.equals("active")) {
//	            runtimeService.activateProcessInstanceById(processInstanceId);
//	            Withdraw withdraw = withdrawService.findWithdrawById(ckid);
//	            withdraw.setYwzt("2");//审批中
//	            withdrawService.updateWithdraw(withdraw);
//	        } else if (state.equals("suspend")) {
//	            runtimeService.suspendProcessInstanceById(processInstanceId);
//	            Withdraw withdraw = withdrawService.findWithdrawById(ckid);
//	            withdraw.setYwzt("4");//挂起
//	            withdrawService.updateWithdraw(withdraw);
//	        }
    		//终止流程
    		runtimeService.deleteProcessInstance(processInstanceId, "");
	    	Withdraw withdraw = withdrawService.findWithdrawById(ckid);
            withdraw.setYwzt("4");//已取消
            withdrawService.updateWithdraw(withdraw);
            //审批记录
    		User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
    		DisApproveRecord disApproveRecord = new DisApproveRecord();
    		disApproveRecord.setYwid(withdraw.getCkid());
    		disApproveRecord.setCzjg("3");
    		disApproveRecord.setBzw("2");
    		disApproveRecord.setCzr(user.getUserName());
    		disApproveRecord.setCzrjh(user.getLoginName());
    		String deptId = user.getDeptId().length()>12 ? user.getDeptId().substring(0, 12): user.getDeptId();
    		disApproveRecord.setCzrdw(deptId);
    		disApproveRecord.setCzrdwmc(user.getDeptName());
    		disApproveRecord.setCzrjs(user.getRoleName());
    		disApproveRecord.setCzrjslx(user.getPosition());
    		disApproveRecord.setCzsj(new Date());
    		withdrawService.save(disApproveRecord);
	    	return "success";
    	} catch (Exception e) {
        	e.printStackTrace();
            return "error";
        }
    }
    
    /**
     * 完成任务
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "complete", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @Description(moduleName="撤控管理",operateType="2",operationName="完成撤控审批")
    public String complete(HttpServletRequest request, String taskId,String taskProInstId, String key,String value, String advice,int id) {
        try {
        	Map<String, Object> variables = new HashMap<String, Object>();
        	variables.put(key, value);
        	if(Tools.isNotEmpty(advice)){
        		User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
        		//审批记录
        		DisApproveRecord disApproveRecord = new DisApproveRecord();
        		disApproveRecord.setYwid(id);
        		String czjg = value;
        		disApproveRecord.setCzjg(czjg);
        		disApproveRecord.setBzw("2");
        		disApproveRecord.setCzr(user.getUserName());
        		disApproveRecord.setCzrjh(user.getLoginName());
        		String deptId = user.getDeptId().length()>12 ? user.getDeptId().substring(0, 12): user.getDeptId();
        		disApproveRecord.setCzrdw(deptId);
        		disApproveRecord.setCzrdwmc(user.getDeptName());
        		disApproveRecord.setCzrjs(user.getRoleName());
        		disApproveRecord.setCzrjslx(user.getPosition());
        		disApproveRecord.setCzsj(new Date());
        		advice = URLDecoder.decode(advice,"utf-8");
        		disApproveRecord.setMs(advice);
        		dispatchedService.save(disApproveRecord);
        	}
        	
        	taskService.complete(taskId, variables);
            return "success";
        } catch (Exception e) {
        	e.printStackTrace();
            return "error";
        }
    }
    
    /**
     * 签收任务
     */
	@RequestMapping(value = "taskClaimForOa")
    @Description(moduleName="撤控管理",operateType="3",operationName="签收撤控审批(OA)")
    public void taskClaimForOa(String taskId, String loginName, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, String conPath) {
    	String success = "1";
    	try {
	        taskService.claim(taskId, loginName);
        } catch (Exception e) {
        	success = "2";
        	e.printStackTrace();
        } finally{
        	//封装数据
			String jsonData = "{\"result\":\"" + success + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			} finally{
				if(out != null){
					out.close();
				}
			}
        }
    }
	
	/**
	 * 跳转到办理审批页面
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/loadWithdrawHandleForOa")
	@Description(moduleName="撤控管理",operateType="1",operationName="跳转撤控审批页面(OA)")
	public String loadWithdrawHandleForOa(HttpServletRequest request, HttpServletResponse response,String rowId,String taskId,String conPath){
		String page = "/dispatched/dealWithdrawForOa";
		try {
			//获取用户名，并获取用户数据
			String loginName = request.getParameter("loginName");
			User user = null;
			if(loginName != null && !"".equals(loginName.trim())){
				//获取用户信息
				user = userService.getUser(loginName);
			}
			
			//用户信息不为空
			if(user != null && user.getRoleId() != 0 && user.getPosition() != null && !"".equals(user.getPosition())){
				request.setAttribute("user", user);
				
				//获取数据字典
				List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,0002,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA");
				request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
				//获取布控类别
				String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
				List<Map> bklbList = dispatchedService.findList(sql, null);
				Withdraw withdraw= withdrawService.findWithdrawById(rowId);
				Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
				String passKey = traceService.findKeyById("ACT_PASS_KEY",task.getTaskDefinitionKey());
				request.setAttribute("passKey", passKey);
				request.setAttribute("dispatched", withdraw.getDispatched());
				request.setAttribute("withdraw", withdraw);
				request.setAttribute("task", task);
				request.setAttribute("bklbList",JSON.toJSONString(bklbList));
				request.setAttribute("success", "0");
				request.setAttribute("conPath", conPath);
				String hpys = ComUtils.hpzlToCplx(withdraw.getDispatched().getHpzl());
				request.setAttribute("hpys", hpys);
				
				//查看当前人是否有权限直接完成审批
				String roles = traceService.findKeyById("ACT_PASS_KEY","completeRole");
				Boolean isComplete = false;
				for(String r : roles.split(",")){
					if(user.getPosition().equals(r)){
						isComplete = true;
					}
				}
				request.setAttribute("isComplete", isComplete);
				//查询当前人是否有审批权限
				Boolean isDeal = true;
				if("81".equals(user.getPosition()) || "91".equals(user.getPosition()) || user.getPosition().compareTo("80") < 0){
					isDeal = false;
				}
				request.setAttribute("isDeal", isDeal);
				
				//审批记录
				List<DisApproveRecord> commentList = dispatchedService.findApproveRecord(withdraw.getCkid(), "2");
				request.setAttribute("commentList", JSON.toJSONString(commentList));
			}
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
     * 完成任务
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "completeForOa", method = {RequestMethod.POST, RequestMethod.GET})
    @Description(moduleName="撤控管理",operateType="2",operationName="完成撤控审批(OA)")
    public void completeForOa(HttpServletRequest request, HttpServletResponse response, String taskId, String taskProInstId, 
    		String key,String value, String advice,int id) {
    	 String success = "1";
    	try {
        	Map<String, Object> variables = new HashMap<String, Object>();
        	variables.put(key, value);
        	
        	//获取用户名，并获取用户数据
        	String loginName = request.getParameter("loginName");
        	User user = null;
        	if(loginName != null && !"".equals(loginName.trim())){
        		//获取用户信息
        		user = userService.getUser(loginName);
        	}
        	
        	//用户信息不为空
        	if(user != null && user.getRoleId() != 0 && user.getPosition() != null && !"".equals(user.getPosition())){
        		request.setAttribute("user", user);
        		
        		if(Tools.isNotEmpty(advice)){
            		//审批记录
            		DisApproveRecord disApproveRecord = new DisApproveRecord();
            		disApproveRecord.setYwid(id);
            		String czjg = value;
            		disApproveRecord.setCzjg(czjg);
            		disApproveRecord.setBzw("2");
            		disApproveRecord.setCzr(user.getUserName());
            		disApproveRecord.setCzrjh(user.getLoginName());
            		String deptId = user.getDeptId().length()>12 ? user.getDeptId().substring(0, 12): user.getDeptId();
            		disApproveRecord.setCzrdw(deptId);
            		disApproveRecord.setCzrdwmc(user.getDeptName());
            		disApproveRecord.setCzrjs(user.getRoleName());
            		disApproveRecord.setCzrjslx(user.getPosition());
            		disApproveRecord.setCzsj(new Date());
            		advice = URLDecoder.decode(advice,"utf-8");
            		disApproveRecord.setMs(advice);
            		dispatchedService.save(disApproveRecord);
            	}
            	
            	taskService.complete(taskId, variables);
        	} else {
        		success = "2";
        	}
        } catch (Exception e) {
        	success = "2";
        	e.printStackTrace();
        } finally{
        	//封装数据
			String jsonData = "{\"result\":\"" + success + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			} finally{
				if(out != null){
					out.close();
				}
			}
        }
    }
}