package com.dyst.BaseDataMsg.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.BaseDataMsg.service.DictionaryService;
import com.dyst.BaseDataMsg.service.JjHmdService;
import com.dyst.BaseDataMsg.service.JjHmdSpService;
import com.dyst.DyMsg.service.DySqService;
import com.dyst.base.utils.PageResult;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.dispatched.service.WithdrawService;
import com.dyst.earlyWarning.service.EWarningService;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.Department;
import com.dyst.systemmanage.entities.Role;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.DepartmentService;
import com.dyst.systemmanage.service.RoleService;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.StaticUtils;
import com.dyst.workflow.service.WorkflowTraceService;

@Controller
@RequestMapping(value="/JjHmdSp")
public class JjHmdSpController extends DictionaryController{
	
	//注入字典表Service
	private DictionaryService dicService;
	public DictionaryService getDicService() {
		return dicService;
	}
	@Autowired
	public void setDicService(DictionaryService dicService) {
		this.dicService = dicService;
	}
	@Autowired
	private JjHmdService jjHmdService;
	//注入 红名单审批Service
	private JjHmdSpService jjHmdSpService;
	public JjHmdSpService getJjHmdSpService() {
		return jjHmdSpService;
	}
	@Autowired
	public void setJjHmdSpService(JjHmdSpService jjHmdSpService) {
		this.jjHmdSpService = jjHmdSpService;
	}
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected WorkflowTraceService traceService;
	@Autowired
	protected DepartmentService deptService;
	@Autowired
	protected DySqService dysqService;
	@Autowired
	protected RoleService roleService;
	@Autowired
	protected DispatchedService bkService;//布控service
	@Autowired
	protected WithdrawService ckService;//车控service
	@Autowired
	private EWarningService eWarningService;
	
	//访问交警红名单审批list界面
	@Deprecated
	@RequestMapping(value="/findJjHmdSp")
	@Description(moduleName="红名单审批",operateType="1",operationName="查询需要审批的红名单信息")
	public String findJjHmdSp(Model model,String Check_cpNo,String Check_cllx,String Check_jlzt,
			String Check_startTime,String Check_endTime,String Check_spjg,PageResult pageResult) throws ParseException{
		//加载车牌类型
		//从字典表中加载所有的HmdSpZt 红名单  ---> 审批状态
		//从字典表中加载所有的审批结果状态
		try {
			hpzlList = getXXXList(hpzlList, HQL_MAP.get("hpzl"));
			spjgList = getXXXList(spjgList, HQL_MAP.get("spjg"));
			jlztList = getXXXList(jlztList, HQL_MAP.get("jlzt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("cpysList", hpzlList);
		model.addAttribute("HmdSpZtList", jlztList);
		model.addAttribute("HmdSpJgList", spjgList);
		//查询list列表
		StringBuffer hql3 = new StringBuffer();
		hql3.append("FROM Jjhomdsp jp left join Jjhomd j with jp.homdid = j.id where 1=1");
		List<Object> params = new ArrayList<Object>();
		//把车牌字符串变成一个车牌数组
		if(StringUtils.isNotBlank(Check_cpNo)){
			String [] cpNos = Check_cpNo.split(",");
			int flag = 0;
			for(String cpNo :cpNos){
				if(cpNo!=null&&cpNo.length()>=1){
					hql3.append(" and j.cphid like ? ");
					params.add("%"+cpNo+"%");
					flag=1;
				}
				if(flag==1){
					hql3.append(" 1=1 or j.cphid like ?");
					params.add("%"+cpNo+"%");
				}
			}
		}
		if(StringUtils.isNotBlank(Check_cllx)){
			hql3.append(" and j.cplx = ?");
			params.add(Check_cllx);
		}
		if(StringUtils.isNotBlank(Check_jlzt)){
			hql3.append(" and jp.splx = ?");
			params.add(Check_jlzt);
		}
		if(StringUtils.isNotBlank(Check_spjg)){
			hql3.append(" and jp.spjg = ?");
			params.add(Check_spjg);
		}
		hql3.append(" order by jp.sqsj DESC");
		pageResult = jjHmdSpService.getPageResult(hql3.toString(), params, pageResult.getPageNo(), 10);
		model.addAttribute("pageResult", pageResult);
		//回显查询条件
		model.addAttribute("Check_cpNo", Check_cpNo);
		model.addAttribute("Check_cllx", Check_cllx);
		model.addAttribute("Check_jlzt", Check_jlzt);
		model.addAttribute("Check_startTime", Check_startTime);
		model.addAttribute("Check_endTime", Check_endTime);
		model.addAttribute("Check_spjg", Check_spjg);
		return "/HmdMsg/JjHmdSp/listUI";
	}
	
	/**
	 * 加了工作流之后。。红名单审批list界面
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="findNewJjHmdSp")
	@Description(moduleName="红名单审批",operateType="1",operationName="查询需要审批的红名单信息")
	public String findTodoSpTasks(Model model,PageResult pageResult,HttpServletRequest req){
		String page ="/HmdMsg/JjHmdSp/listUI";
		//加载车牌类型
		//从字典表中加载所有的HmdSpZt 红名单  ---> 审批状态
		//从字典表中加载所有的审批结果状态
		try {
			hpzlList = getXXXList(hpzlList, HQL_MAP.get("hpzl"));
			spjgList = getXXXList(spjgList, HQL_MAP.get("spjg"));
			jlztList = getXXXList(jlztList, HQL_MAP.get("jlzt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			model.addAttribute("cpysList", hpzlList);
			model.addAttribute("HmdSpZtList", jlztList);
			model.addAttribute("HmdSpJgList", spjgList);
			User user = (User)req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
	        pageResult = jjHmdSpService.findTodoSpTasks(user,pageResult.getPageNo(),pageResult.getPageSize());
	        model.addAttribute("pageResult", pageResult);	
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
			return page;
		}finally {
			return "/HmdMsg/JjHmdSp/listUI";
		}
	}
	
	/**
	 * 位置：系统首页
	 * 请求源：首页js
	 * 用途：用户登录之后，加载该用户需要审批的红名单个数
	 * 	         待审批的各种信息，封装为json返回
	 * @throws Exception 
	 */
	@RequestMapping(value="findNewJjHmdSpAjax")
	public void findNewJjHmdSpAjax(HttpServletRequest req,HttpServletResponse resp
			,HttpSession session){
		//获取用户的信息
		User user = (User) session.getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		String firstFlag = req.getParameter("firstFlag");//是否首次
		int temp_hmd = 0;//红名单待审批数量
		int temp_dy = 0;//订阅待审批数量
		int temp_gkbk = 0;//公开布控待审批数量
		int temp_bksp = 0;//布控审批  待审批的数量
		int temp_gkck = 0;//公开撤控
		int temp_cksp = 0;//撤控审批
		Map<String,String> data = new HashMap<String, String>();
		if(user != null){
			Role role = null;
			try {
				if(user.getRoleId() != null){
					role = roleService.getRoleById(user.getRoleId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			List<Object> param = null;
			List<Map> result = null;
			if(role != null){
				if(role.getPermissionContent().charAt(128)=='1'){//红名单审批129位为1
					temp_hmd = jjHmdSpService.findCountTodoSpTasks(user);//hmd待审批的数量
					data.put("hmd", Integer.toString(temp_hmd));
				}
				
				if(role.getPermissionContent().charAt(65)=='1'){//66订阅审批权限第66位为1
					temp_dy = dysqService.findToDoTasksCount(user);
					data.put("dy", Integer.toString(temp_dy));
				}
				
				if(role.getPermissionContent().charAt(46)=='1'){//47说明有布控审批权限
					temp_bksp = bkService.findTodoTasksCount(user);
					data.put("bksp", Integer.toString(temp_bksp));
				} else if(role.getPermissionContent().charAt(46) != '1' && role.getPermissionContent().charAt(44)=='1'){//说明有公开布控审批权限45位为1
					temp_gkbk = bkService.findTodoOpenTasksCount(user);
					data.put("gkbk", Integer.toString(temp_gkbk));
				}
				
				if(role.getPermissionContent().charAt(52)=='1'){//53说明有秘密撤控审批的权限
					temp_cksp = ckService.findTodoTasksCount(user);
					data.put("cksp", Integer.toString(temp_cksp));
				} else if(role.getPermissionContent().charAt(52)!='1' && role.getPermissionContent().charAt(53)=='1'){//说明有公开撤控的权限
					temp_gkck = ckService.findTodoOpenTasksCount(user);
					data.put("gkck", Integer.toString(temp_gkck));
				}
				
				if(role.getPermissionContent().charAt(148)=='1'){//149说明有预警签收的权限
					//查看需要签收的预警数据
					param = new ArrayList<Object>();
					param.add(user.getLskhbm());
					result = eWarningService.findList("select count(*) as num from YJQS where qrzt='0' and bjbm=? ", param);
					String countYjqs = "0";
					for(Map map : result){
						countYjqs = map.get("num").toString();
					}
					data.put("yjqs", countYjqs);
				}
				
				if(role.getPermissionContent().charAt(54)=='1'){//55说明有签收的布控撤控通知的权限
					//查看需要签收的布控撤控通知
					param = new ArrayList<Object>();
					param.add(user.getLskhbm());
					result = eWarningService.findList("select BKCKBZ, count(*) as num from BKQS where qrdw=? and qrzt='0' group by BKCKBZ", param);
					String countBktz = "0";
					String countCktz = "0";
					for(Map map : result){
						String bkckzb = map.get("BKCKBZ").toString();
						if("1".equals(bkckzb)){
							countBktz = map.get("num").toString();
						} else if("2".equals(bkckzb)){
							countCktz = map.get("num").toString();
						}
					}
					data.put("bktz", countBktz);
					data.put("cktz", countCktz);
				}
				
				if(role.getPermissionContent().charAt(151)=='1'){//152说明有签收的处警指令的权限
					//查看需要签收的处警指令
					param = new ArrayList<Object>();
					param.add(user.getLskhbm());
					result = eWarningService.findList("select count(*) as num from INSTRUCTION_SIGN where qszt='0' and zlbm=? ", param);
					String countZlqs = "0";
					for(Map map : result){
						countZlqs = map.get("num").toString();
					}
					data.put("zlqs", countZlqs);
				}
				
				if(role.getPermissionContent().charAt(153)=='1'){//154说明有签收的处警反馈的权限
					//查看需要签收的处警反馈
					param = new ArrayList<Object>();
					param.add(user.getLskhbm());
					if("1".equals(firstFlag)){//首次获取所有
						result = eWarningService.findList("select count(*) as num from INSTRUCTION_SIGN where qszt='1' and fkzt='0' and zlbm=? ", param);
					}  else {//获取超过23小时的反馈
						result = eWarningService.findList("select count(*) as num from INSTRUCTION_SIGN where hour(timediff(now(), zlsj)) >= 23 and qszt='1' and fkzt='0' and zlbm=? ", param);
					}
					String countZlfk = "0";
					for(Map map : result){
						countZlfk = map.get("num").toString();
					}
					data.put("zlfk", countZlfk);
				}
			}
		}
		writeResponse(resp, JSON.toJSONString(data));
	}
	
	
	/**
     * 签收任务
     */
    @SuppressWarnings("finally")
	@RequestMapping(value = "taskClaim")
    @Description(moduleName="红名单审批",operateType="3",operationName="签收红名单审批信息")
	public String claim(String taskId, HttpServletRequest request,String conPath) {
    	String success = "";
    	try {
	    	User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
	        taskService.claim(taskId, user.getLoginName());
	        success= "success";
        } catch (Exception e) {
        	e.printStackTrace();
        	success= "error";
        }finally{
        	return "redirect:/JjHmdSp/findNewJjHmdSp.do?success="+success;
        }
    }
    
    /**
     * 点击审批的时候弹出签收页面
     */
    @RequestMapping(value="getSpUI")
    @Description(moduleName="红名单审批",operateType="1",operationName="红名单审批详情")
	public String getSpUI(Model model,HttpServletRequest req,String taskId) throws Exception{
		//加载车辆车牌颜色
		model.addAttribute("cpysList", hpzlList);
		//获取传过来的id,根据id查询车辆的详细信息
		String id = CommonUtils.keyWordFilter(req.getParameter("id"));
		Jjhomd hmdDetail = jjHmdService.getHmdById(Integer.parseInt(id));
		//根据红名单单位id查询出单位的名称
		String dwid = hmdDetail.getSqrdw();
		List<Department> depts = new ArrayList<Department>();
		depts = deptService.getDeptByDeptNo(dwid);
		String dwmc = "";
		if (depts!=null){
			dwmc = depts.get(0).getDeptName();
		}
		model.addAttribute("dwmc", dwmc);
		model.addAttribute("hmdDetail", hmdDetail);   
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String passKey = traceService.findKeyById("ACT_PASS_KEY",task.getTaskDefinitionKey());
		model.addAttribute("passKey", passKey);
		model.addAttribute("task", task);
    	return "HmdMsg/JjHmdSp/hmdSpDetail";
    }
    
    /**
     * 弹出撤销红名单详情页面
     */
    @RequestMapping(value="getCxSpUI")
    @Description(moduleName="红名单审批",operateType="1",operationName="红名单撤销详情")
	public String getCxSpUI(Model model,HttpServletRequest req,String taskId) throws Exception{
    	//加载车辆车牌颜色
    	model.addAttribute("cpysList", hpzlList);
    	//获取传过来的id,根据id查询车辆的详细信息
    	String id = CommonUtils.keyWordFilter(req.getParameter("id"));
    	Jjhomd hmdDetail = jjHmdService.getHmdById(Integer.parseInt(id));
    	model.addAttribute("hmdDetail", hmdDetail);   
    	Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
    	String passKey = traceService.findKeyById("ACT_PASS_KEY",task.getTaskDefinitionKey());
    	model.addAttribute("passKey", passKey);
    	model.addAttribute("task", task);
    	return "HmdMsg/JjHmdSp/hmdCxSpDetail";
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
}
