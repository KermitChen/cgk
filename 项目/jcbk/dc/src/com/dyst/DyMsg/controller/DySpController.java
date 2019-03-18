package com.dyst.DyMsg.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.service.DictionaryService;
import com.dyst.DyMsg.entities.Dyxxsp;
import com.dyst.DyMsg.service.DySqService;
import com.dyst.base.utils.PageResult;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.User;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.StaticUtils;
import com.dyst.workflow.service.WorkflowTraceService;

@Controller
@RequestMapping(value="dysp")
public class DySpController {
	
	@Autowired
	private DictionaryService dicService;
	@Autowired
	private DySqService dySqService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected WorkflowTraceService traceService;
	@Autowired
	protected RuntimeService runtimeService;
	
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
	public static List<Object> hpysList = new ArrayList<Object>();
	public static List<Object> hpzlList = new ArrayList<Object>();
	public static List<Object> jcdsList = new ArrayList<Object>();
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
	/*
	 * 提供基本查询hql语句
	 * 	1.从字典表中 查询记录状态
	 * 	2. 		审批结果
	 * 	3.		订阅类型
	 */
	public static Map<String,String> HQL_MAP;
	static{
		HQL_MAP = new HashMap<String,String>();
		HQL_MAP.put("jlzt", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HmdSpZt' and d.deleteFlag != '1'");
		HQL_MAP.put("spjg", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HmdSpJg' and d.deleteFlag != '1'");
		HQL_MAP.put("dylx", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'DyLx' and d.deleteFlag != '1'");
		HQL_MAP.put("hpys", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HPZL' and d.deleteFlag !='1'");
		HQL_MAP.put("jcds", "select new map(j.id as typeSerialNo,j.jcdmc as typeDesc) from Jcd j ");
	}


	//加载订阅审批首页
	@RequestMapping(value="findDySp")
	@Description(moduleName="订阅审批管理",operateType="1",operationName="查询订阅审批")
	public String findDySp(PageResult pageResult,Model model,String Check_dylx,String Check_jlzt,
			String Check_spjg,String Check_startTime,String Check_endTime,HttpServletRequest request,String success) throws Exception{
		
		//dyssQueryService.sendDyssQueryMsg("");
		/*
		 * 1.获取记录状态list
		 *  .获取审批结果list
		 *  .获取订阅类型list
		 *  .加载所有的通知类型list
		 * 2.返回到前端页面显示
		 */
		User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		jlztList = getXXXList(jlztList,HQL_MAP.get("jlzt"));
		spjgList = getXXXList(spjgList,HQL_MAP.get("spjg"));
		dylxList = getXXXList(dylxList,HQL_MAP.get("dylx"));
		String data = JSON.toJSONString(dylxList);
		model.addAttribute("jlztList", jlztList);
		model.addAttribute("spjgList", spjgList);
		model.addAttribute("spjgList1", data);
		model.addAttribute("dylxList", dylxList);
		model.addAttribute("success", success);
		//查询订阅审批表
		pageResult = dySqService.findTodoTasks(user, pageResult.getPageNo(), 10);
		model.addAttribute("pageResult", pageResult);
		//返回查询条件
		model.addAttribute("Check_dylx", Check_dylx);
		model.addAttribute("Check_jlzt", Check_jlzt);
		model.addAttribute("Check_spjg", Check_spjg);
		model.addAttribute("Check_startTime", Check_startTime);
		model.addAttribute("Check_endTime", Check_endTime);
		return "DyMsg/dysp/listUI";
	}
	
	/**
	 * 查询待审批的数量
	 * @throws Exception 
	 */
	@RequestMapping(value="findDySpCountAjax")
	@Description(moduleName="订阅审批管理",operateType="1",operationName="查询订阅待审批数量")
	public void findDySpCountAjax(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		//获取用户的信息
		User user = (User) req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		if(user!=null){
			int temp = dySqService.findToDoTasksCount(user);
			String sum = Integer.toString(temp);
			writeResponse(resp, sum);
		}
	}
	
	//弹出审批页面
	@RequestMapping(value="getSpUI")
	@Description(moduleName="订阅审批管理",operateType="1",operationName="查看订阅审批")
	public String getSpUI(Model model,HttpServletRequest req,String taskId) throws Exception{
		String id = CommonUtils.keyWordFilter(req.getParameter("id"));
		//加载所有订阅的车牌信息 详情
		String hql = "from DyxxXq d where d.dyxx.id = :id ";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", Integer.parseInt(id));
		List<Object> list = dySqService.getList(hql, params);
		model.addAttribute("xqList", JSON.toJSONString(list));
		//加载车牌颜色
		hpzlList =  getXXXList(hpzlList,HQL_MAP.get("hpys"));
		//加载订阅类型
		dylxList = getXXXList(dylxList,HQL_MAP.get("dylx"));
		//加载记录状态
		jlztList = getXXXList(jlztList,HQL_MAP.get("jlzt"));
		//加载该订阅详情
		String hql4 = "from Dyxx d where d.id = :id ";
		List<Object> dyxxList = dySqService.getList(hql4, params);
		//
		String xqList = JSON.toJSONString(dyxxList);
		model.addAttribute("xqListJson", xqList);
		String cpysList = JSON.toJSONString(hpzlList);
		model.addAttribute("cpysList", cpysList);
		String dylxListJson = JSON.toJSONString(dylxList);
		model.addAttribute("dylxListJson", dylxListJson);
		String jlztListJson = JSON.toJSONString(jlztList);
		model.addAttribute("jlztListJson", jlztListJson);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String passKey = traceService.findKeyById("ACT_PASS_KEY",task.getTaskDefinitionKey());
		model.addAttribute("passKey", passKey);
		model.addAttribute("task", task);
		return "DyMsg/dysp/dysp";
	}
	
	//审批操作
	@RequestMapping(value="updateDysp")
	@Description(moduleName="订阅审批管理",operateType="3",operationName="审批订阅申请")
	public void updateDysp(String dyxxId,String spyj,String spjgDesc,Model model,HttpServletRequest req,HttpServletResponse resp){
		try {
			User user = (User) req.getSession().getAttribute("USER_OBJ");
			
			Map<String,Object> params = new HashMap<String, Object>();
			String hql = "from Dyxxsp d where d.dyxx.id = :id ";
			params.put("id", dyxxId);
			Dyxxsp dyxxsp = (Dyxxsp) dySqService.getList(hql, params).get(0);
			/*
			 * 添加审批相关信息
			 */
			dyxxsp.setSpjg(spyj);  //添加审批意见
			dyxxsp.setSpms(spjgDesc);//添加审批描述
			dyxxsp.getDyxx().setJlzt("002");//修改状态为已审批状态
			/*
			 * 添加用户相关的信息
			 */
			dyxxsp.setSprjh(user.getLoginName());
			dyxxsp.setSprxm(user.getUserName());
			dyxxsp.setSprdw(user.getDeptId());
			dyxxsp.setSprdwmc(user.getDeptName());
			dyxxsp.setSpsj(new Timestamp(new Date().getTime()));
			//保存订阅审批信息
			dySqService.updateDyxx(dyxxsp);
			String message = "审批成功！";
			writeResponse(resp,message);
		} catch (Exception e) {
			String message ="审批失败！";
			writeResponse(resp,message);
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
     * 签收任务
     */
    @SuppressWarnings("finally")
	@RequestMapping(value = "taskClaim")
    @Description(moduleName="订阅审批管理",operateType="3",operationName="签收订阅审批")
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
        	return "redirect:/dysp/findDySp.do?success="+success;
        }
    }
}
