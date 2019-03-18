package com.dyst.dispatched.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
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

import jodd.datetime.JDateTime;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Comment;
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

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Yacs;
import com.dyst.BaseDataMsg.service.JjHmdService;
import com.dyst.BaseDataMsg.service.YaglService;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.chariotesttube.entities.Vehicle;
import com.dyst.dispatched.entities.Dis110;
import com.dyst.dispatched.entities.DisApproveRecord;
import com.dyst.dispatched.entities.DisReceive;
import com.dyst.dispatched.entities.DisReport;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.entities.Withdraw;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.dispatched.service.WithdrawService;
import com.dyst.earlyWarning.entities.EWarning;
import com.dyst.earlyWarning.entities.InstructionSign;
import com.dyst.earlyWarning.service.EWarningService;
import com.dyst.kafka.service.KafkaService;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.Department;
import com.dyst.systemmanage.entities.Role;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.DepartmentService;
import com.dyst.systemmanage.service.RoleService;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.IntefaceUtils;
import com.dyst.utils.ReadConfig;
import com.dyst.utils.StaticUtils;
import com.dyst.utils.Tools;
import com.dyst.utils.excel.bean.bukong.BKQueryExcelBean;
import com.dyst.utils.excel.bean.bukong.CKExcelBean;
import com.dyst.vehicleQuery.utils.ComUtils;
import com.dyst.workflow.listener.AfterDispatchedCompleteProcessor;
import com.dyst.workflow.listener.AfterDispatchedOvertime;
import com.dyst.workflow.listener.BeforeDispatchedModifyApplyProcessor;
import com.dyst.workflow.service.WorkflowTraceService;

@Controller
@RequestMapping("/dispatched")
public class DispatchedController {
	//注入业务层
	@Autowired
	private DepartmentService deptService;
	@Autowired
	private DispatchedService dispatchedService;
	@Autowired
	private WithdrawService withdrawService;
	@Autowired
	private UserService userService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	protected WorkflowTraceService traceService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	private YaglService yaglService;
	@Autowired
	private JjHmdService jjHmdService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private EWarningService eWarningService;
	@Autowired
	private KafkaService kafkaService;
	
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/loadDispatchedAdd")
	public String loadDispatchedAdd(HttpServletRequest request, HttpServletResponse response){
		String page = "/dispatched/addDispatched";
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA");
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicListJSON",JSON.toJSONString(dicList));
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			request.setAttribute("user", user);
			//查询领导
			List<User> leaders = dispatchedService.getLeader(user);
			request.setAttribute("leaders", leaders);
			//通知单位
			String sql1 = "select dept_no,dept_name from DEPARTMENT where jxkh='1'";
			List<Map> depts = dispatchedService.findList(sql1, null);
			request.setAttribute("depts", JSON.toJSONString(depts));
			request.setAttribute("success", "0");
			//布控时间，默认一天
			JDateTime jDate = new JDateTime();
			request.setAttribute("bkqssj", jDate.toString("YYYY-MM-DD hh:mm:ss"));//起始时间
			request.setAttribute("bkjzsj", jDate.addDay(1).toString("YYYY-MM-DD hh:mm:ss"));//结束时间
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}                         
	
	/**
	 * 布控申请
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "addDispatched", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	@Description(moduleName="布控管理",operateType="2",operationName="布控申请")
	public String addDispatched(HttpServletRequest request, HttpServletResponse response, Dispatched dispatched, 
			Boolean addRight, Boolean isZjbk, String leader, String tzdw, String tznr, String isSend, 
			String cad, String cadBkid) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String levelNow = "";//当前布控类别级别
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			//过滤红名单
			if(jjHmdService.isHmdByHphm(dispatched.getHphm(), dispatched.getHpzl())){
				map.put("result", "hmd");
				return JSON.toJSONString(map);
			}
			Dispatched dis = dispatchedService.findDispatchedByHphmBest(dispatched.getHphm(),dispatched.getHpzl());
			if(dis != null && !addRight){
				request.setAttribute("success", "2");
				//获取数据字典
				List<Dictionary> dicList = userService.getDictionarysByTypeCode("BKDL,BKJB,BKXZ");
				String bkdl = "";
				String bkjb = "";
				String bkxz = "";
				for(Dictionary dictionary : dicList){
					if(dictionary.getTypeCode().equals("BKDL") && dictionary.getTypeSerialNo().equals(dis.getBkdl())){
						bkdl = dictionary.getTypeDesc();
					}else if(dictionary.getTypeCode().equals("BKJB") && dictionary.getTypeSerialNo().equals(dis.getBkjb())){
						bkjb = dictionary.getTypeDesc();
					}else if(dictionary.getTypeCode().equals("BKXZ") && dictionary.getTypeSerialNo().equals(dis.getBkxz())){
						bkxz = dictionary.getTypeDesc();
					}
				}
				String bklb = "";
				String levelBefore = "";
				for(Map bklbMap :bklbList){
					if(bklbMap.get("ID").toString().equals(dis.getBklb())){
						bklb = bklbMap.get("NAME").toString();
						levelBefore = bklbMap.get("LEVEL").toString();
					}
					if(bklbMap.get("ID").toString().equals(dispatched.getBklb())){
						levelNow = bklbMap.get("LEVEL").toString();
					}
				}
				String s = "布控申请人："+dis.getBkr()+"\n电话:"+dis.getBkjglxdh()+"\n手机:"+dis.getDxjshm()+"\n布控大类:"+bkdl+"\n布控类别:"+bklb+"\n布控性质:"+bkxz;
				map.put("info", s);
				if((levelBefore.equals("02") && levelNow.compareTo("02") >= 0 && levelNow.compareTo("10") <= 0)
						|| (levelNow.equals("02") && levelBefore.compareTo("02") >= 0 && levelBefore.compareTo("10") <= 0)
						|| (levelBefore.compareTo(levelNow) == 0)){
					map.put("result", "equal");
				}else if(levelNow.compareTo(levelBefore) < 0){
					map.put("result", "recover");
				}else{
					map.put("result", "repeat");
				}
				return JSON.toJSONString(map);
			}
			for(Map bklbMap :bklbList){
				if(bklbMap.get("ID").toString().equals(dispatched.getBklb())){
					levelNow = bklbMap.get("LEVEL").toString();
				}
			}
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			User msgUser = userService.getUser(leader);//领导
			dispatched.setBkr(user.getUserName());//布控人姓名
			dispatched.setBkrjh(user.getLoginName());//布控人警号
			dispatched.setBkjg(user.getDeptId());//机关
			dispatched.setBkjgmc(user.getDeptName());//机关名称
			dispatched.setBksj(new Timestamp(new Date().getTime()));//布控申请时间
			dispatched.setYwzt("2");//业务状态   1、布控中，2、审批中，3、已撤控，4、已取消，5、已失效，6、调整申请
			dispatched.setBy3("0");//初始为没屏蔽
			dispatched.setBy4(cad);
			dispatched.setXxly("0");
			dispatched.setTzdw(tzdw);
			dispatched.setTznr(tznr);
			dispatched.setBklbjb(levelNow);
			dispatched.setLskhbm(user.getLskhbm());
			dispatched.setLskhbmmc(user.getLskhbmmc());
			//设置分局编号
			if(user.getDeptId().length() >= 6 && !user.getDeptId().substring(0, 6).equals("440300")){
				dispatched.setFjbh(user.getDeptId().substring(0, 6));
			}else{
				List<Department> deptList = deptService.getDeptByDeptNo(user.getDeptId());
				if(deptList.size() > 0 && deptList.get(0).getParentNo().length() >= 6){
					dispatched.setFjbh(deptList.get(0).getParentNo().substring(0, 6));
				}else{
					dispatched.setFjbh("440300");
				}
			}
			//设置分局SYS_NO
			if(!dispatched.getFjbh().equals("440300") && user.getSystemNo().length() >= 6){
				dispatched.setFjsn(user.getSystemNo().substring(0, 6));
			}
			//流程变量赋值
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("afterDispatchedCompleteProcessor", new AfterDispatchedCompleteProcessor());
			variables.put("beforeDispatchedModifyApplyProcessor", new BeforeDispatchedModifyApplyProcessor());
			variables.put("afterDispatchedOvertime", new AfterDispatchedOvertime());
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
			if("02".equals(dispatched.getBklb())){
				variables.put("isProvince", false);//分局布控
			}else{
				variables.put("isProvince", true);//联动布控
			}
			int time = Integer.parseInt(ReadConfig.getPropertiesValue("activiti", "dispatchedExamineTime"));
			long currentTime = System.currentTimeMillis() + 60 * 60 * 1000 * time;//以小时为单位
			Date date = new Date(currentTime);
			variables.put("endTime", DateFormatUtils.format(date, "yyyy-MM-dd'T'HH:mm:ss"));
			
			if(isZjbk){
				dispatched.setZjbk("1");//直接布控0否1是
				//保存布控并报备领导
				dispatchedService.addDis(dispatched,user,msgUser,variables);
			}else{
				dispatched.setZjbk("0");//直接布控0否1是
				//保存布控并启动审批流程
				dispatchedService.addDispatched(dispatched,variables);
			}
			
			//110布控更新信息
			if(cad != null && "1".equals(cad.trim())){
				dispatchedService.updateCadBk(cadBkid);
			}
			
			//发送短信
			String hpzl = "";
			String bkdl1 = "";
			String bklb1 = "";
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL");
			for(Dictionary dictionary : dicList){
				if(dictionary.getTypeCode().equals("HPZL") && dictionary.getTypeSerialNo().equals(dispatched.getHpzl())){
					hpzl = dictionary.getTypeDesc();
				}else if(dictionary.getTypeCode().equals("BKDL") && dictionary.getTypeSerialNo().equals(dispatched.getBkdl())){
					bkdl1 = dictionary.getTypeDesc();
				}
			}
			for(Map bklbMap :bklbList){
				if(bklbMap.get("ID").toString().equals(dispatched.getBklb())){
					bklb1 = bklbMap.get("NAME").toString();
				}
			}
			String message = "";
			if(isZjbk){
				message = "您好,稽查布控系统有一条新的直接布控向您报备,号牌号码："+dispatched.getHphm()+",号牌种类："+hpzl+",布控大类："+bkdl1+",布控类别："+bklb1
						+",布控人："+dispatched.getBkr()+",布控单位名称："+dispatched.getBkjgmc()+",布控申请时间："+dispatched.getBksj();
			}
			if("1".equals(isSend) && msgUser != null){
				IntefaceUtils.sendMessage(msgUser.getTelPhone(), message);//发送短信
			}
			map.put("result", "success");
			return JSON.toJSONString(map);
		} catch (ActivitiException e) {
            e.printStackTrace();
            map.put("result", "error");
			return JSON.toJSONString(map);
        } catch (Exception e) {
			e.printStackTrace();
			map.put("result", "error");
			return JSON.toJSONString(map);
		} 
	}
	
	/**
	 * 布控管理查询（当前部门）
	 * @param pageResult
	 * @return pageResult
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/findDispatched")
	@Description(moduleName="布控管理",operateType="1",operationName="布控管理查询（当前部门）")
	public String findDispatched(HttpServletRequest request, HttpServletResponse response,Dispatched dispatched,PageResult pageResult,String qssj,String jzsj,String cxlx){
		String page = "/dispatched/queryDispatched";
		try {	
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,BKYWZT");
			request.setAttribute("dicList", dicList);
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklb",bklbList);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			QueryHelper queryHelper = new QueryHelper(Dispatched.class, "d");
			//获取前台页面要查询的类别种类
			if(Tools.isNotEmpty(dispatched.getBkdl())){
				queryHelper.addCondition("d.bkdl = ?", dispatched.getBkdl());
			}
			if(Tools.isNotEmpty(dispatched.getBklb())){
				queryHelper.addCondition("d.bklb = ?", dispatched.getBklb());
			}
			if(Tools.isNotEmpty(dispatched.getHpzl())){
				queryHelper.addCondition("d.hpzl = ?", dispatched.getHpzl());
			}
			if(Tools.isNotEmpty(dispatched.getHphm())){
				queryHelper.addCondition("d.hphm like ?", "%"+dispatched.getHphm()+"%");
			}
			if(Tools.isNotEmpty(qssj)){
				queryHelper.addCondition("d.bksj > ?", formatter.parse(qssj));
			}
			if(Tools.isNotEmpty(jzsj)){
				queryHelper.addCondition("d.bksj < ?", formatter.parse(jzsj));
			}
			if(Tools.isNotEmpty(dispatched.getYwzt())){
				queryHelper.addCondition("d.ywzt = ?", dispatched.getYwzt());
			}
			if(Tools.isNotEmpty(dispatched.getZjbk())){
				queryHelper.addCondition("d.zjbk = ?", dispatched.getZjbk());
			}
			if(Tools.isNotEmpty(dispatched.getBkr())){
				queryHelper.addCondition("d.bkr = ?", dispatched.getBkr());
			}
			queryHelper.addCondition("d.lskhbm = ?", user.getLskhbm());
			
			//按最后修改时间降序
			queryHelper.addOrderByProperty("d.bksj", QueryHelper.ORDER_BY_DESC);
			pageResult = dispatchedService.getPageResult(queryHelper, pageResult.getPageNo(), 10);
			request.setAttribute("qssj", qssj);
			request.setAttribute("jzsj", jzsj);
			request.setAttribute("dispatched", dispatched);
			request.setAttribute("pageResults", pageResult);
			request.setAttribute("conPath", "findDispatched");
			request.setAttribute("user", user);
			request.setAttribute("cxlx", "1");
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * liuqiang1--布控查询导出列表  导出本部门
	 * @throws Exception 
	 */
	@RequestMapping(value="excelExportForDispatched")
	@Description(moduleName="布控管理",operateType="1",operationName="导出布控信息（当前部门）")
	public void excelExportForDispatched(HttpServletRequest req,HttpServletResponse resp,Dispatched dispatched,String qssj,String jzsj,String cxlx) throws Exception{
		User user = (User)req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		//查询出列表
		StringBuffer hql = new StringBuffer();
		Map<String,Object> params = new HashMap<String, Object>();
		hql.append(" from Dispatched d where 1=1");
		if(StringUtils.isNotBlank(dispatched.getBkdl())){
			hql.append(" and d.bkdl=:bkdl");
			params.put("bkdl", dispatched.getBkdl());
		}
		if(StringUtils.isNotBlank(dispatched.getBklb())){
			hql.append(" and d.bklb=:bklb");
			params.put("bklb", dispatched.getBklb());
		}
		if(StringUtils.isNotBlank(dispatched.getHpzl())){
			hql.append(" and d.hpzl=:hpzl");
			params.put("hpzl", dispatched.getHpzl());
		}
		if(StringUtils.isNotBlank(dispatched.getHphm())){
			hql.append(" and d.hphm=:hphm");
			params.put("hphm", dispatched.getHphm());
		}
		if(StringUtils.isNotBlank(qssj)){
			hql.append(" and d.bksj >=:qssj");
			params.put("qssj", DateUtils.parseDate(qssj, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(jzsj)){
			hql.append(" and d.bksj <=:jzsj");
			params.put("jzsj", DateUtils.parseDate(jzsj, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(dispatched.getYwzt())){
			hql.append(" and d.ywzt =:ywzt");
			params.put("ywzt", dispatched.getYwzt());
		}
		if(StringUtils.isNotBlank(dispatched.getZjbk())){
			hql.append(" and d.zjbk=:zjbk");
			params.put("zjbk", dispatched.getZjbk());
		}
		if(StringUtils.isNotBlank(dispatched.getBkr())){
			hql.append(" and d.bkr=:bkr");
			params.put("bkr", dispatched.getBkr());
		}
		if(StringUtils.isNotBlank(user.getLskhbm())){
			hql.append(" and d.lskhbm=:lskhbm");
			params.put("lskhbm", user.getLskhbm());
		}
		List<Dispatched> list = dispatchedService.getListByQuery(hql.toString(), params);
		//获取数据字典
		List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,BKYWZT");
		//获取布控类别
		String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
		List<Map> bklbList = dispatchedService.findList(sql, null);
		BKQueryExcelBean bean = new BKQueryExcelBean(dicList,bklbList,list);
		//导出
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Date now=new Date();
		StringBuilder filename = new StringBuilder();//文件名
		filename.append("布控撤控统计查询").append(dateFormat.format(now)).append(".xls");
		resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
			//附件形式，并指定文件名
		resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
		ServletOutputStream outputStream = resp.getOutputStream();//输出流
		dispatchedService.excelExportForDispatched(bean,outputStream);
	}
	/**
	 * 导出excel  公开布控
	 */
	@RequestMapping(value="excelExportForDispatchedGK")
	@Description(moduleName="布控管理",operateType="1",operationName="导出公开布控信息")
	public void excelExportForDispatchedGK(HttpServletRequest req,HttpServletResponse resp,Dispatched dispatched,String qssj,String jzsj,String cxlx) throws Exception{
		//获取数据字典
		List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKJB,BKFWLX,BKFW,BKXZ,BJYA,BKYWZT");
		//获取布控类别
		String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
		List<Map> bklbList = dispatchedService.findList(sql, null);
		if(StringUtils.isEmpty(cxlx)){
			cxlx = "1";
		}
		if(cxlx.equals("1")){
			//查询出列表                                                               
			StringBuffer hql = new StringBuffer();
			Map<String,Object> params = new HashMap<String, Object>();
			hql.append(" from Dispatched d where 1=1");
			if(StringUtils.isNotBlank(dispatched.getBkdl())){
				hql.append(" and d.bkdl=:bkdl");
				params.put("bkdl", dispatched.getBkdl());
			}
			if(StringUtils.isNotBlank(dispatched.getBklb())){
				hql.append(" and d.bklb=:bklb");
				params.put("bklb", dispatched.getBklb());
			}
			if(StringUtils.isNotBlank(dispatched.getHpzl())){
				hql.append(" and d.hpzl=:hpzl");
				params.put("hpzl", dispatched.getHpzl());
			}
			if(StringUtils.isNotBlank(dispatched.getHphm())){
				hql.append(" and d.hphm=:hphm");
				params.put("hphm", dispatched.getHphm());
			}
			if(StringUtils.isNotBlank(qssj)){
				hql.append(" and d.bksj >=:qssj");
				params.put("qssj", DateUtils.parseDate(qssj, "yyyy-MM-dd HH:mm:ss"));
			}
			if(StringUtils.isNotBlank(jzsj)){
				hql.append(" and d.bksj <=:jzsj");
				params.put("jzsj", DateUtils.parseDate(jzsj, "yyyy-MM-dd HH:mm:ss"));
			}
			if(StringUtils.isNotBlank(dispatched.getYwzt())){
				hql.append(" and d.ywzt =:ywzt");
				params.put("ywzt", dispatched.getYwzt());
			}
			if(StringUtils.isNotBlank(dispatched.getZjbk())){
				hql.append(" and d.zjbk=:zjbk");
				params.put("zjbk", dispatched.getZjbk());
			}
			if(StringUtils.isNotBlank(dispatched.getBkr())){
				hql.append(" and d.bkr=:bkr");
				params.put("bkr", dispatched.getBkr());
			}
			hql.append(" and d.bkxz=:bkxz");
			params.put("bkxz", "1");
			hql.append(" and d.ywzt in ('1','3','5','7','8')");
			hql.append(" order by d.bksj desc");
			List<Dispatched> list = dispatchedService.getListByQuery(hql.toString(), params);
			BKQueryExcelBean bean = new BKQueryExcelBean(dicList,bklbList,list);
			//导出
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Date now=new Date();
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("公开布控撤控统计查询").append(dateFormat.format(now)).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
				//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			dispatchedService.excelExportForDispatched(bean,outputStream);
		}else{
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
			if(StringUtils.isNotBlank(dispatched.getBkr())){
				hql.append(" and w.cxsqr =:bkr");
				params.put("bkr", dispatched.getBkr());
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
	}
	/**
	 * 导出excel  秘密布控所有的布控信息
	 */
	@RequestMapping(value="excelExportForDispatchedMM")
	@Description(moduleName="布控管理",operateType="1",operationName="导出所有布控信息")
	public void excelExportForDispatchedMM(HttpServletRequest req,HttpServletResponse resp,Dispatched dispatched,String qssj,String jzsj,String cxlx) throws Exception{
		//获取数据字典
		List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKJB,BKFWLX,BKFW,BKXZ,BJYA,BKYWZT");
		//获取布控类别
		String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
		List<Map> bklbList = dispatchedService.findList(sql, null);
		if(StringUtils.isEmpty(cxlx)){
			cxlx = "1";
		}
		if(cxlx.equals("1")){
			//查询出列表      
			List<Dispatched> list = new ArrayList<Dispatched>();
			StringBuffer hql = new StringBuffer();
			Map<String,Object> params = new HashMap<String, Object>();
			hql.append(" from Dispatched d where 1=1");
			if(StringUtils.isNotBlank(dispatched.getBkdl())){
				hql.append(" and d.bkdl=:bkdl");
				params.put("bkdl", dispatched.getBkdl());
			}
			if(StringUtils.isNotBlank(dispatched.getBklb())){
				hql.append(" and d.bklb=:bklb");
				params.put("bklb", dispatched.getBklb());
			}
			if(StringUtils.isNotBlank(dispatched.getHpzl())){
				hql.append(" and d.hpzl=:hpzl");
				params.put("hpzl", dispatched.getHpzl());
			}
			if(StringUtils.isNotBlank(dispatched.getHphm())){
				hql.append(" and d.hphm=:hphm");
				params.put("hphm", dispatched.getHphm());
			}
			if(StringUtils.isNotBlank(qssj)){
				hql.append(" and d.bksj >=:qssj");
				params.put("qssj", DateUtils.parseDate(qssj, "yyyy-MM-dd HH:mm:ss"));
			}
			if(StringUtils.isNotBlank(jzsj)){
				hql.append(" and d.bksj <=:jzsj");
				params.put("jzsj", DateUtils.parseDate(jzsj, "yyyy-MM-dd HH:mm:ss"));
			}
			if(StringUtils.isNotBlank(dispatched.getYwzt())){
				hql.append(" and d.ywzt =:ywzt");
				params.put("ywzt", dispatched.getYwzt());
			}
			if(StringUtils.isNotBlank(dispatched.getZjbk())){
				hql.append(" and d.zjbk=:zjbk");
				params.put("zjbk", dispatched.getZjbk());
			}
			if(StringUtils.isNotBlank(dispatched.getBkr())){
				hql.append(" and d.bkr=:bkr");
				params.put("bkr", dispatched.getBkr());
			}
			hql.append(" and d.ywzt in ('1','3','5','7','8')");
			hql.append(" order by d.bksj desc");
			list = dispatchedService.getListByQuery(hql.toString(), params);
			BKQueryExcelBean bean = new BKQueryExcelBean(dicList,bklbList,list);
			//导出
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Date now=new Date();
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("布控综合查询").append(dateFormat.format(now)).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
				//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			dispatchedService.excelExportForDispatched(bean,outputStream);
		}else{
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
			if(StringUtils.isNotBlank(dispatched.getBkr())){
				hql.append(" and w.cxsqr =:bkr");
				params.put("bkr", dispatched.getBkr());
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
	}
	/**
	 * 所有布控信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/findAllDispatched")
	@Description(moduleName="布控管理",operateType="1",operationName="分页查询所有布控信息")
	public String findAllDispatched(HttpServletRequest request, HttpServletResponse response,Dispatched dispatched,PageResult pageResult,String qssj,String jzsj,String cxlx){
		String page = "/dispatched/queryDispatched";
		try {	
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,BKYWZT,CKYWZT");
			request.setAttribute("dicList", dicList);
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklb",bklbList);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			if(StringUtils.isEmpty(cxlx)){
				cxlx = "1";
			}
			if(cxlx.equals("1")){
				QueryHelper queryHelper = new QueryHelper(Dispatched.class, "d");
				//获取前台页面要查询的类别种类
				if(Tools.isNotEmpty(dispatched.getBkdl())){
					queryHelper.addCondition("d.bkdl = ?", dispatched.getBkdl());
				}
				if(Tools.isNotEmpty(dispatched.getBklb())){
					queryHelper.addCondition("d.bklb = ?", dispatched.getBklb());
				}
				if(Tools.isNotEmpty(dispatched.getHpzl())){
					queryHelper.addCondition("d.hpzl = ?", dispatched.getHpzl());
				}
				if(Tools.isNotEmpty(dispatched.getHphm())){
					queryHelper.addCondition("d.hphm like ?", "%"+dispatched.getHphm()+"%");
				}
				if(Tools.isNotEmpty(qssj)){
					queryHelper.addCondition("d.bksj > ?", formatter.parse(qssj));
				}
				if(Tools.isNotEmpty(jzsj)){
					queryHelper.addCondition("d.bksj < ?", formatter.parse(jzsj));
				}
				if(Tools.isNotEmpty(dispatched.getYwzt())){
					queryHelper.addCondition("d.ywzt = ?", dispatched.getYwzt());
				}
				if(Tools.isNotEmpty(dispatched.getZjbk())){
					queryHelper.addCondition("d.zjbk = ?", dispatched.getZjbk());
				}
				if(Tools.isNotEmpty(dispatched.getBkr())){
					queryHelper.addCondition("d.bkr = ?", dispatched.getBkr());
				}
				//按最后修改时间降序
				queryHelper.addOrderByProperty("d.bksj", QueryHelper.ORDER_BY_DESC);
				queryHelper.addCondition("d.ywzt in ('1','3','5','7','8')", null);
				pageResult = dispatchedService.getPageResult(queryHelper, pageResult.getPageNo(), 10);
			}else{
				QueryHelper queryHelper = new QueryHelper(Withdraw.class, "w");
				//获取前台页面要查询的条件
				if(Tools.isNotEmpty(qssj)){
					queryHelper.addCondition("w.cxsqsj > ?", formatter.parse(qssj));
				}
				if(Tools.isNotEmpty(jzsj)){
					queryHelper.addCondition("w.cxsqsj < ?", formatter.parse(jzsj));
				}
				if(Tools.isNotEmpty(dispatched.getBkr())){
					queryHelper.addCondition("w.cxsqr = ?", dispatched.getBkr());
				}
				//按最后修改时间降序
				queryHelper.addOrderByProperty("w.cxsqsj", QueryHelper.ORDER_BY_DESC);
				pageResult = withdrawService.getPageResult(queryHelper, pageResult.getPageNo(), 10,false);
			}
			request.setAttribute("qssj", qssj);
			request.setAttribute("jzsj", jzsj);
			request.setAttribute("dispatched", dispatched);
			request.setAttribute("pageResults", pageResult);
			request.setAttribute("conPath", "findAllDispatched");
			request.setAttribute("user", user);
			request.setAttribute("cxlx", cxlx);
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
	@RequestMapping("/findOpenDispatched")
	@Description(moduleName="布控管理",operateType="1",operationName="分页查询公开布控信息")
	public String findOpenDispatched(HttpServletRequest request, HttpServletResponse response,Dispatched dispatched,PageResult pageResult,String qssj,String jzsj,String cxlx){
		String page = "/dispatched/queryDispatched";
		try {	
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,BKYWZT,CKYWZT");
			request.setAttribute("dicList", dicList);
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklb",bklbList);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			if(StringUtils.isEmpty(cxlx)){
				cxlx = "1";
			}
			if(cxlx.equals("1")){	
				QueryHelper queryHelper = new QueryHelper(Dispatched.class, "d");
				//获取前台页面要查询的类别种类
				if(Tools.isNotEmpty(dispatched.getBkdl())){
					queryHelper.addCondition("d.bkdl = ?", dispatched.getBkdl());
				}
				if(Tools.isNotEmpty(dispatched.getBklb())){
					queryHelper.addCondition("d.bklb = ?", dispatched.getBklb());
				}
				if(Tools.isNotEmpty(dispatched.getHpzl())){
					queryHelper.addCondition("d.hpzl = ?", dispatched.getHpzl());
				}
				if(Tools.isNotEmpty(dispatched.getHphm())){
					queryHelper.addCondition("d.hphm like ?", "%"+dispatched.getHphm()+"%");
				}
				if(Tools.isNotEmpty(qssj)){
					queryHelper.addCondition("d.bksj > ?", formatter.parse(qssj));
				}
				if(Tools.isNotEmpty(jzsj)){
					queryHelper.addCondition("d.bksj < ?", formatter.parse(jzsj));
				}
				if(Tools.isNotEmpty(dispatched.getYwzt())){
					queryHelper.addCondition("d.ywzt = ?", dispatched.getYwzt());
				}
				if(Tools.isNotEmpty(dispatched.getZjbk())){
					queryHelper.addCondition("d.zjbk = ?", dispatched.getZjbk());
				}
				if(Tools.isNotEmpty(dispatched.getBkr())){
					queryHelper.addCondition("d.bkr = ?", dispatched.getBkr());
				}
				queryHelper.addCondition("d.bkxz = ?", "1");
				queryHelper.addCondition("d.ywzt in ('1','3','5','7','8')", null);
				//按最后修改时间降序
				queryHelper.addOrderByProperty("d.bksj", QueryHelper.ORDER_BY_DESC);
				pageResult = dispatchedService.getPageResult(queryHelper, pageResult.getPageNo(), 10);
			}else{
				QueryHelper queryHelper = new QueryHelper(Withdraw.class, "w");
				//获取前台页面要查询的条件
				if(Tools.isNotEmpty(qssj)){
					queryHelper.addCondition("w.cxsqsj > ?", formatter.parse(qssj));
				}
				if(Tools.isNotEmpty(jzsj)){
					queryHelper.addCondition("w.cxsqsj < ?", formatter.parse(jzsj));
				}
				if(Tools.isNotEmpty(dispatched.getBkr())){
					queryHelper.addCondition("w.cxsqr = ?", dispatched.getBkr());
				}
				//按最后修改时间降序
				queryHelper.addOrderByProperty("w.cxsqsj", QueryHelper.ORDER_BY_DESC);
				pageResult = withdrawService.getPageResult(queryHelper, pageResult.getPageNo(), 10,true);
			}
			request.setAttribute("qssj", qssj);
			request.setAttribute("jzsj", jzsj);
			request.setAttribute("dispatched", dispatched);
			request.setAttribute("pageResults", pageResult);
			request.setAttribute("conPath", "findOpenDispatched");
			request.setAttribute("user", user);
			request.setAttribute("cxlx", cxlx);
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
	@RequestMapping(value = "listTask")
    @Description(moduleName="布控管理",operateType="1",operationName="布控审批查询")
    public ModelAndView taskList(HttpServletRequest request,PageResult pageResult,String success) {
    	ModelAndView mav = new ModelAndView("/dispatched/taskList");
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
	        pageResult = dispatchedService.findTodoTasks(user,pageResult.getPageNo(),pageResult.getPageSize());
	        mav.addObject("pageResults", pageResult);
	        mav.addObject("success", success);
	        mav.addObject("conPath", "listTask");
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
	@RequestMapping(value = "openTaskList")
    @Description(moduleName="布控管理",operateType="1",operationName="公开布控审批查询")
    public ModelAndView openTaskList(HttpServletRequest request,PageResult pageResult,String success) {
    	ModelAndView mav = new ModelAndView("/dispatched/taskList");
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
	        pageResult = dispatchedService.findTodoOpenTasks(user,pageResult.getPageNo(),pageResult.getPageSize());
	        mav.addObject("pageResults", pageResult);
	        mav.addObject("success", success);
	        mav.addObject("conPath", "openTaskList");
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
    @Description(moduleName="布控管理",operateType="3",operationName="布控审批签收")
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
        	return "redirect:/dispatched/"+conPath+".do?success="+success;
        }
    }

    /**
     * 读取详细数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "detail")
    @ResponseBody
    @Description(moduleName="布控管理",operateType="1",operationName="查看布控信息")
    public Dispatched getDispatched(Long id) {
    	Dispatched dispatched = dispatchedService.findDispatchedById(id);
        return dispatched;
    }

    /**
     * 读取详细数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "detail-with-vars")
    @ResponseBody
    @Description(moduleName="布控管理",operateType="1",operationName="查看布控信息")
    public Dispatched getDispatchedWithVars(Long id, String taskId) {
    	Dispatched dispatched = dispatchedService.findDispatchedById(id);
        Map<String, Object> variables = taskService.getVariables(taskId);
        dispatched.setVariables(variables);
        return dispatched;
    }

    
    
    /**
	 * 跳转到办理审批页面
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/loadDispatchedHandle")
	@Description(moduleName="布控管理",operateType="1",operationName="跳转布控审批页面")
	public String loadDispatchedHandle(HttpServletRequest request, HttpServletResponse response,String rowId,String taskId,String conPath){
		String page = "/dispatched/dealDispatched";
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			request.setAttribute("user", user);
			
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,0002,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA");
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
			request.setAttribute("dicList", dicList);
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			Dispatched dispatched= dispatchedService.findDispatchedById(rowId);
			if(!StringUtils.isEmpty(taskId)){
				Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
				String passKey = traceService.findKeyById("ACT_PASS_KEY",task.getTaskDefinitionKey());
				request.setAttribute("passKey", passKey);
				request.setAttribute("task", task);
			}
			String hpys = ComUtils.hpzlToCplx(dispatched.getHpzl());
			//查询领导
			List<User> leaders = dispatchedService.getLeader(user);
			request.setAttribute("leaders", leaders);
			if(dispatched.getZjbk().equals("1")){
				//直接布控报备记录
				List<DisReport> disReportList = dispatchedService.findDisReport(dispatched.getBkid());
				request.setAttribute("disReportList", JSON.toJSONString(disReportList));
			}else{
				//审批记录
				List<DisApproveRecord> commentList = dispatchedService.findApproveRecord(dispatched.getBkid(), "1");
				request.setAttribute("commentList", JSON.toJSONString(commentList));
			}
			//生成单号
			String position2 = user.getPosition().substring(0, 2);
			if(position2.compareTo("90") > 0 && StringUtils.isEmpty(dispatched.getSjdh())){
				dispatched.setSjdh(Tools.dhsc(user.getDeptId(), user.getPosition(), new Date()));
				dispatchedService.update(dispatched);
			}else if(position2.compareTo("90") <0 && position2.compareTo("80") >0 && StringUtils.isEmpty(dispatched.getFjdh())){
				dispatched.setFjdh(Tools.dhsc(user.getDeptId(), user.getPosition(), new Date()));
				dispatchedService.update(dispatched);
			}else if(position2.compareTo("80") <0 && StringUtils.isEmpty(dispatched.getPcsdh())){
				dispatched.setPcsdh(Tools.dhsc(user.getDeptId(), user.getPosition(), new Date()));
				dispatchedService.update(dispatched);
			}
			request.setAttribute("hpys", hpys);
			request.setAttribute("dispatched", dispatched);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			request.setAttribute("bklb",bklbList);
			request.setAttribute("success", "0");
			request.setAttribute("conPath", conPath);
			request.setAttribute("user", user);
			//查看当前人是否有权限直接完成审批
			String roles = traceService.findKeyById("ACT_PASS_KEY","completeRole");
			Boolean isComplete = false;
			for(String r : roles.split(",")){
				if(position2.equals(r)){
					isComplete = true;
				}
			}
//			if((dispatched.getBklb().equals("00") || dispatched.getBklb().equals("01"))
//				&& user.getPosition().compareTo("80") > 0 && user.getPosition().compareTo("90") < 0){
//				//涉案类联动和本市的布控，分局没有权限直接完成
//				isComplete = false;
//			}
			request.setAttribute("isComplete", isComplete);
			//查询当前人是否有审批权限
			Boolean isDeal = true;
			if(position2.equals("81") || position2.equals("91")){
				isDeal = false;
			}
			request.setAttribute("isDeal", isDeal);
			//协同作战，相同的布控全列出来
			List<Dispatched> dispatchedList = dispatchedService.findDispatchedByHphm(dispatched.getHphm(), dispatched.getHpzl(), dispatched.getBkid());
			request.setAttribute("dispatchedList", dispatchedList);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
     * 布控详情页面
     *
     * @param leave
     */
	@SuppressWarnings("finally")
	@RequestMapping("/detailDispatched")
	@Description(moduleName="布控管理",operateType="1",operationName="布控详情")
	public String detailDispatched(HttpServletRequest request, HttpServletResponse response,String rowId,String taskId,String conPath){
		String page = "/dispatched/detailDispatched";
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,CKYWZT");
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			Dispatched dispatched= dispatchedService.findDispatchedById(rowId);
			if("1".equals(dispatched.getZjbk())){
				//直接布控报备记录
				List<DisReport> disReportList = dispatchedService.findDisReport(dispatched.getBkid());
				request.setAttribute("disReportList", disReportList);
			} else{
				//审批记录
				List<DisApproveRecord> commentList = dispatchedService.findApproveRecord(dispatched.getBkid(), "1");
				request.setAttribute("commentList", commentList);
			}
			//签收记录
			List<DisReceive> receiveList = dispatchedService.findDisReceiveList(dispatched.getBkid(), "1");
			request.setAttribute("receiveList", receiveList);
			if(receiveList != null && receiveList.size() > 0){
				request.setAttribute("disReceive", receiveList.get(0));
			}
			//撤控记录
			List<Withdraw> withdrawList = dispatchedService.findWithdrawList(dispatched.getBkid());
			request.setAttribute("withdrawList", withdrawList);
			//撤控审批记录、通知记录
			if(withdrawList != null && withdrawList.size() > 0){
				Withdraw withdraw = withdrawList.get(0);
				//撤控通知签收记录
				List<DisReceive> ckReceiveList = dispatchedService.findDisReceiveList(withdraw.getCkid(), "2");
				request.setAttribute("ckReceiveList", ckReceiveList);
				//审批记录
				if("1".equals(withdraw.getZjck())){
					
				} else{
					//审批记录
					List<DisApproveRecord> ckCommentList = dispatchedService.findApproveRecord(withdraw.getCkid(), "2");
					request.setAttribute("ckCommentList", ckCommentList);
				}
			}
			//预警记录
			List<EWarning> ewarningList = dispatchedService.findEWaringList(dispatched.getBkid());
			request.setAttribute("ewarningList", ewarningList);
			
			request.setAttribute("dispatched", dispatched);
			request.setAttribute("conPath", conPath);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			request.setAttribute("bklb",bklbList);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 跳转到修改布控页面
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/editDispatched")
	@Description(moduleName="布控管理",operateType="3",operationName="跳转修改布控")
	public String editDispatched(HttpServletRequest request, HttpServletResponse response,String rowId,String taskId,String conPath){
		String page = "/dispatched/editDispatched";
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA");
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));//JS赋值用
			request.setAttribute("dicList", dicList);
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			Dispatched dispatched= dispatchedService.findDispatchedById(rowId);
//			SimpleDateFormat   formatter   =  new   SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
//			dispatched.setBksj(formatter.parse(dispatched.getBksj().toString()));
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			//获取历史批注
			if(taskId != null){
				List<Comment> commentList = traceService.findCommentByTaskId(taskId);
				request.setAttribute("commentList", commentList);
			}
			
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			request.setAttribute("user", user);
			String passKey = traceService.findKeyById("ACT_PASS_KEY",task.getTaskDefinitionKey());
			request.setAttribute("passKey", passKey);
			request.setAttribute("dispatched", dispatched);
			request.setAttribute("task", task);
			request.setAttribute("bklbList", JSON.toJSONString(bklbList));
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
    @Description(moduleName="布控管理",operateType="3",operationName="修改布控")
    public String editComplete(HttpServletRequest request, HttpServletResponse response,Dispatched dispatched
      ,String taskId,String taskProInstId, String key,Boolean value,String conPath) {
        try {
			dispatched.setGxsj(new Timestamp(new Date().getTime()));//布控更新时间
			//审批记录
			DisApproveRecord disApproveRecord = new DisApproveRecord();
        	if(value){
        		dispatched.setYwzt("2");//业务状态   1、布控中，2、审批中，3、已撤控，4、已取消，5、已失效，6、调整申请
//        		taskService.addComment(taskId, taskProInstId, "重新提交申请");
        		disApproveRecord.setCzjg("2");
        	}else{
        		dispatched.setYwzt("4");
//        		taskService.addComment(taskId, taskProInstId, "取消申请");
        		disApproveRecord.setCzjg("3");
        	}
        	//审批记录
    		User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
    		disApproveRecord.setYwid(dispatched.getBkid());
    		disApproveRecord.setBzw("1");
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
			dispatchedService.updateDispatched(dispatched);
        	variables.put(key, value);
            taskService.complete(taskId, variables);
            return "redirect:/"+conPath;
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
    @Description(moduleName="布控管理",operationName="取消布控申请")
    public String updateState(HttpServletRequest request, String state,String processInstanceId,String bkid) {
    	try {
//	    	if (state.equals("active")) {
//	            runtimeService.activateProcessInstanceById(processInstanceId);
//	            Dispatched dispatched = dispatchedService.findDispatchedById(bkid);
//	            dispatched.setYwzt("2");//审批中
//	            dispatchedService.updateDispatched(dispatched);
//	        } else if (state.equals("suspend")) {
//	            runtimeService.suspendProcessInstanceById(processInstanceId);
//	            Dispatched dispatched = dispatchedService.findDispatchedById(bkid);
//	            dispatched.setYwzt("4");//挂起
//	            dispatchedService.updateDispatched(dispatched);
//	        }
    		//终止流程
    		runtimeService.deleteProcessInstance(processInstanceId, "");
    		Dispatched dispatched = dispatchedService.findDispatchedById(bkid);
    		dispatched.setYwzt("4");//已取消
    		dispatchedService.updateDispatched(dispatched);
    		//审批记录
    		User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
    		DisApproveRecord disApproveRecord = new DisApproveRecord();
    		disApproveRecord.setYwid(dispatched.getBkid());
    		disApproveRecord.setCzjg("3");
    		disApproveRecord.setBzw("1");
    		disApproveRecord.setCzr(user.getUserName());
    		disApproveRecord.setCzrjh(user.getLoginName());
    		String deptId = user.getDeptId().length()>12 ? user.getDeptId().substring(0, 12): user.getDeptId();
    		disApproveRecord.setCzrdw(deptId);
    		disApproveRecord.setCzrdwmc(user.getDeptName());
    		disApproveRecord.setCzrjs(user.getRoleName());
    		disApproveRecord.setCzrjslx(user.getPosition());
    		disApproveRecord.setCzsj(new Date());
    		dispatchedService.save(disApproveRecord);
	    	return "success";
    	} catch (Exception e) {
        	e.printStackTrace();
            return "error";
        }
    }
    /**
     * 加载预案
     */
    @RequestMapping(value = "selectYACS", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @Description(moduleName="布控管理",operationName="加载预案")
    public String selectYACS(HttpServletRequest request, HttpServletResponse response,String bklb,String bjlx) {
    	try {
    		Yacs yacs = (Yacs) yaglService.findYacsByType(bklb,bjlx);
    		if(yacs == null){
    			response.getWriter().write(JSON.toJSONString(""));
    		}else{
    			response.getWriter().write(JSON.toJSONString(yacs.getYams()));
    		}
	    	return null;
    	} catch (Exception e) {
        	e.printStackTrace();
            return null;
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
    @Description(moduleName="布控管理",operationName="完成布控审批")
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
        		disApproveRecord.setBzw("1");
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
	 * 查询车辆信息
	 * @param user
	 * @return
	 */
	@RequestMapping("/getVehInfo")
	@Description(moduleName="布控管理",operationName="查询车辆信息")
	public void getVehicleInfo(HttpServletRequest request,HttpServletResponse response) {
		try {
//			String hphm = URLDecoder.decode(request.getParameter("hphm"), "UTF-8");
			String hphm = request.getParameter("hphm");
			String cpzl = request.getParameter("cpzl");
			if(StringUtils.isNotEmpty(hphm) && StringUtils.isNotEmpty(cpzl)){
				response.setContentType("application/json");
				User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
				//查询是否有查询机动车信息的权限
				Role role = roleService.getRoleById(user.getRoleId());
				char isHaveVehiclePermission = role.getPermissionContent().charAt(110);
				if(isHaveVehiclePermission == '1'){//有查询权限
					Vehicle vehicle = ComUtils.getVehicleInfo(user, hphm, cpzl, 
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
	 * 跳转到添加页面
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/dis110")
	@Description(moduleName="布控管理",operationName="跳转110布控")
	public String dis110(HttpServletRequest request, HttpServletResponse response){
		String page = "/dispatched/dis110";
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			List<Dis110> list = dispatchedService.findDis110(user.getLoginName());
			request.setAttribute("dis110", list);
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
	 * 查询本部门布控签收
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/disReceive")
	@Description(moduleName="布控管理",operateType="1",operationName="分页查询本部门布控签收")
	public String disReceive(HttpServletRequest request, HttpServletResponse response,DisReceive disReceive,PageResult pageResult,String qssj,String jzsj){
		String page = "/dispatched/signDisReceive";
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL");
			request.setAttribute("dicList", dicList);
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklb",bklbList);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			String hql = "from DisReceive d where d.qrdw = ? and d.qrzt='0' ";
			List<Object> param = new ArrayList<Object>();
			param.add(user.getLskhbm());
			if(!StringUtils.isEmpty(disReceive.getBkckbz())){
				hql += " and d.bkckbz = ? ";
				param.add(disReceive.getBkckbz());
			}
			if(!StringUtils.isEmpty(disReceive.getBklb())){
				hql += " and d.bklb = ? ";
				param.add(disReceive.getBklb());
			}
			if(!StringUtils.isEmpty(disReceive.getHphm())){
				hql += " and d.hphm like ? ";
				param.add("%"+disReceive.getHphm()+"%");
			}
			if(!StringUtils.isEmpty(disReceive.getHpzl())){
				hql += " and d.hpzl = ? ";
				param.add(disReceive.getHpzl());
			}
			if(!StringUtils.isEmpty(qssj)){
				hql += " and d.xfsj >= ? ";
				param.add(formatter.parse(qssj));
			}
			if(!StringUtils.isEmpty(jzsj)){
				hql += " and d.xfsj <= ? ";
				param.add(formatter.parse(jzsj));
			}
			
			pageResult = dispatchedService.getAllPageResult(hql,param, pageResult.getPageNo(), 10);
			request.setAttribute("pageResults", pageResult);
			request.setAttribute("disReceive", disReceive);
			request.setAttribute("qssj", qssj);
			request.setAttribute("jzsj", jzsj);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
	
		}
	}     
	/**
	 * 跳转到布控签收页面
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/loadDisReceive")
	@Description(moduleName="布控管理",operateType="1",operationName="跳转布控签收")
	public String loadDisReceive(HttpServletRequest request, HttpServletResponse response,int id){
		String page = "/dispatched/dealDisReceive";
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL");
			request.setAttribute("dicList", dicList);
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklb",bklbList);
			DisReceive disReceive = dispatchedService.findDisReceive(id);
			request.setAttribute("disReceive", disReceive);
			if(disReceive.getBkckbz().equals("1")){
				Dispatched dispatched = dispatchedService.findDispatchedById(disReceive.getBkid());
				request.setAttribute("dispatched", dispatched);
			}else{
				Withdraw withdraw = withdrawService.findWithdrawById(disReceive.getBkid());
				request.setAttribute("withdraw", withdraw);
			}
			
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 签收布控
	 * @param request
	 * @param response
	 */
	@RequestMapping("/dealDisReceive")
	@Description(moduleName="布控管理",operateType="3",operationName="签收布控")
	public void dealDisReceive(HttpServletRequest request,HttpServletResponse response,int id,String qrjg) {
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			DisReceive disReceive = dispatchedService.findDisReceive(id);
			if(disReceive.getQrzt().equals("1")){
				response.getWriter().write(JSON.toJSONString("repeat"));
			}else{
//				disReceive.setQrdw(user.getDeptId());
//				disReceive.setQrdwmc(user.getDeptName());
				disReceive.setQrdwlxdh(user.getTelPhone());
				disReceive.setQrjg(qrjg);
				disReceive.setQrr(user.getUserName());
				disReceive.setQrrjh(user.getLoginName());
				disReceive.setQrsj(new Date());
				disReceive.setQrzt("1");
				dispatchedService.update(disReceive);
				response.getWriter().write(JSON.toJSONString("success"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询布控签收
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/queryDisReceive")
	@Description(moduleName="布控管理",operateType="1",operationName="分页查询布控签收")
	public String queryDisReceive(HttpServletRequest request, HttpServletResponse response,DisReceive disReceive,PageResult pageResult,String qssj,String jzsj){
		String page = "/dispatched/queryDisReceive";
		try {
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL");
			request.setAttribute("dicList", dicList);
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklb",bklbList);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			String hql = "from DisReceive d where d.qrdw = ? ";
			List<Object> param = new ArrayList<Object>();
			param.add(user.getLskhbm());
			if(!StringUtils.isEmpty(disReceive.getBkckbz())){
				hql += " and d.bkckbz = ? ";
				param.add(disReceive.getBkckbz());
			}
			if(!StringUtils.isEmpty(disReceive.getBklb())){
				hql += " and d.bklb = ? ";
				param.add(disReceive.getBklb());
			}
			if(!StringUtils.isEmpty(disReceive.getHphm())){
				hql += " and d.hphm like ? ";
				param.add("%" + disReceive.getHphm() + "%");
			}
			if(!StringUtils.isEmpty(disReceive.getHpzl())){
				hql += " and d.hpzl = ? ";
				param.add(disReceive.getHpzl());
			}
			if(!StringUtils.isEmpty(disReceive.getQrzt())){
				hql += " and d.qrzt = ? ";
				param.add(disReceive.getQrzt());
			}
			if(!StringUtils.isEmpty(qssj)){
				hql += " and d.xfsj >= ? ";
				param.add(formatter.parse(qssj));
			}
			if(!StringUtils.isEmpty(jzsj)){
				hql += " and d.xfsj <= ? ";
				param.add(formatter.parse(jzsj));
			}
			//排序
			hql += " order by id desc ";
			
			pageResult = dispatchedService.getAllPageResult(hql, param, pageResult.getPageNo(), 10);
			request.setAttribute("pageResults", pageResult);
			request.setAttribute("disReceive", disReceive);
			request.setAttribute("qssj", qssj);
			request.setAttribute("jzsj", jzsj);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 跳转到布控签收详情页面
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/detailDisReceive")
	@Description(moduleName="布控管理",operateType="1",operationName="跳转布控签收详情页面")
	public String detailDisReceive(HttpServletRequest request, HttpServletResponse response,int id){
		String page = "/dispatched/detailDisReceive";
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL");
			request.setAttribute("dicList", dicList);
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklb",bklbList);
			DisReceive disReceive = dispatchedService.findDisReceive(id);
			request.setAttribute("disReceive", disReceive);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 完成报备
	 * @param request
	 * @param response
	 */
	@RequestMapping("/dealReport")
	@Description(moduleName="布控管理",operateType="2",operationName="完成布控报备")
	public void dealReport(HttpServletRequest request,HttpServletResponse response,String id,String advice,Boolean czjg,String leader) {
		try {
			DisReport disReport = dispatchedService.findNoDealDisReport(Integer.parseInt(id));
			if(disReport != null){
				disReport.setFksj(new Date());
				disReport.setFknr(advice);
				disReport.setBbzt("1");
				dispatchedService.update(disReport);
			}
			if(czjg && !StringUtils.isEmpty(leader)){
				User uleader = userService.getUser(leader);
				DisReport newReport = new DisReport();
				newReport.setBbr(uleader.getLoginName());
				newReport.setBbrmc(uleader.getUserName());
				newReport.setBbrbm(uleader.getDeptId());
				newReport.setBbrbmmc(uleader.getDeptName());
				newReport.setBbrdh(uleader.getTelPhone());
				newReport.setBbrjs(uleader.getPosition());
				newReport.setBbrjsmc(uleader.getRoleName());
				newReport.setBkid(Integer.parseInt(id));
				newReport.setBbzt("0");
				dispatchedService.save(newReport);
			}
			response.getWriter().write(JSON.toJSONString("success"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /**
     * 签收任务
     */
	@RequestMapping(value = "taskClaimForOa")
    @Description(moduleName="布控管理",operateType="3",operationName="布控审批签收(OA)")
    public void taskClaimForOa(String taskId, String loginName, HttpServletRequest request, HttpServletResponse response, String conPath) {
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
	@RequestMapping("/loadDispatchedHandleForOa")
	@Description(moduleName="布控管理", operateType="1", operationName="跳转布控审批页面(OA)")
	public String loadDispatchedHandleForOa(HttpServletRequest request, HttpServletResponse response,String rowId,String taskId,String conPath){
		String page = "/dispatched/dealDispatchedForOa";
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
				request.setAttribute("dicList", dicList);
				
				//获取布控类别
				String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
				List<Map> bklbList = dispatchedService.findList(sql, null);
				Dispatched dispatched = dispatchedService.findDispatchedById(rowId);
				if(!StringUtils.isEmpty(taskId)){
					Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
					String passKey = traceService.findKeyById("ACT_PASS_KEY",task.getTaskDefinitionKey());
					request.setAttribute("passKey", passKey);
					request.setAttribute("task", task);
				}
				String hpys = ComUtils.hpzlToCplx(dispatched.getHpzl());
				
				//查询领导
				List<User> leaders = dispatchedService.getLeader(user);
				request.setAttribute("leaders", leaders);
				if("1".equals(dispatched.getZjbk())){
					//直接布控报备记录
					List<DisReport> disReportList = dispatchedService.findDisReport(dispatched.getBkid());
					request.setAttribute("disReportList", JSON.toJSONString(disReportList));
				} else{
					//审批记录
					List<DisApproveRecord> commentList = dispatchedService.findApproveRecord(dispatched.getBkid(), "1");
					request.setAttribute("commentList", JSON.toJSONString(commentList));
				}
				
				//生成单号
				String position2 = user.getPosition().substring(0, 2);
				if(position2.compareTo("90") > 0 && StringUtils.isEmpty(dispatched.getSjdh())){
					dispatched.setSjdh(Tools.dhsc(user.getDeptId(), user.getPosition(), new Date()));
					dispatchedService.update(dispatched);
				}else if(position2.compareTo("90") <0 && position2.compareTo("80") >0 && StringUtils.isEmpty(dispatched.getFjdh())){
					dispatched.setFjdh(Tools.dhsc(user.getDeptId(), user.getPosition(), new Date()));
					dispatchedService.update(dispatched);
				}else if(position2.compareTo("80") <0 && StringUtils.isEmpty(dispatched.getPcsdh())){
					dispatched.setPcsdh(Tools.dhsc(user.getDeptId(), user.getPosition(), new Date()));
					dispatchedService.update(dispatched);
				}
				
				request.setAttribute("hpys", hpys);
				request.setAttribute("dispatched", dispatched);
				request.setAttribute("bklbList",JSON.toJSONString(bklbList));
				request.setAttribute("bklb",bklbList);
				request.setAttribute("success", "0");
				request.setAttribute("conPath", conPath);
				request.setAttribute("user", user);
				
				//查看当前人是否有权限直接完成审批
				String roles = traceService.findKeyById("ACT_PASS_KEY","completeRole");
				Boolean isComplete = false;
				for(String r : roles.split(",")){
					if(position2.equals(r)){
						isComplete = true;
					}
				}
//				if((dispatched.getBklb().equals("00") || dispatched.getBklb().equals("01"))
//					&& user.getPosition().compareTo("80") > 0 && user.getPosition().compareTo("90") < 0){
//					//涉案类联动和本市的布控，分局没有权限直接完成
//					isComplete = false;
//				}
				request.setAttribute("isComplete", isComplete);
				//查询当前人是否有审批权限
				Boolean isDeal = true;
				if(position2.equals("81") || position2.equals("91")){
					isDeal = false;
				}
				request.setAttribute("isDeal", isDeal);
				//协同作战，相同的布控全列出来
				List<Dispatched> dispatchedList = dispatchedService.findDispatchedByHphm(dispatched.getHphm(), dispatched.getHpzl(), dispatched.getBkid());
				request.setAttribute("dispatchedList", dispatchedList);
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
    @Description(moduleName="布控管理",operateType="3",operationName="完成布控审批(OA)")
    public void completeForOa(HttpServletRequest request, HttpServletResponse response, String taskId,
    		String taskProInstId, String key,String value, String advice,int id) {
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
	        		disApproveRecord.setBzw("1");
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
        		//完成审批
        		taskService.complete(taskId, variables);
        	} else {
        		success = "2";
        	}
        } catch (Exception e) {
        	success = "3";
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
	 * 完成报备
	 * @param request
	 * @param response
	 */
	@RequestMapping("/dealReportForOa")
	@Description(moduleName="布控管理",operateType="3",operationName="完成布控报备(OA)")
	public void dealReportForOa(HttpServletRequest request, HttpServletResponse response, String id, 
			String advice, Boolean czjg, String leader) {
		String success = "1";
		try {
			DisReport disReport = dispatchedService.findNoDealDisReport(Integer.parseInt(id));
			if(disReport != null){
				disReport.setFksj(new Date());
				disReport.setFknr(advice);
				disReport.setBbzt("1");
				dispatchedService.update(disReport);
			}
			if(czjg && !StringUtils.isEmpty(leader)){
				User uleader = userService.getUser(leader);
				DisReport newReport = new DisReport();
				newReport.setBbr(uleader.getLoginName());
				newReport.setBbrmc(uleader.getUserName());
				newReport.setBbrbm(uleader.getDeptId());
				newReport.setBbrbmmc(uleader.getDeptName());
				newReport.setBbrdh(uleader.getTelPhone());
				newReport.setBbrjs(uleader.getPosition());
				newReport.setBbrjsmc(uleader.getRoleName());
				newReport.setBkid(Integer.parseInt(id));
				newReport.setBbzt("0");
				dispatchedService.save(newReport);
			}
		} catch (Exception e) {
			success = "2";
			e.printStackTrace();
		} finally {
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
	 * 查询生效的布控信息，用于反馈录入应用成效
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/queryBksqForFk")
	@Description(moduleName="已布控车拦截反馈", operateType="1", operationName="查询已布控车辆")
	public String queryBksqForFk(HttpServletRequest request, HttpServletResponse response, String hphm, String hpzl){
		String result = "1";
		String page = "/dispatched/queryBksqForFk";
		PageResult pageResult = null;
		try {
			//用户信息
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,BKYWZT,CKYWZT");
			request.setAttribute("dicList", dicList);
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			request.setAttribute("bklb", bklbList);
			request.setAttribute("bklbList", JSON.toJSONString(bklbList));
			 
			//查询布控
			if(Tools.isNotEmpty(hphm)){
				List<Object> param = new ArrayList<Object>();
				String hql = " from Dispatched where ywzt in ('1', '3', '5', '7', '8') and hphm like ? ";
				param.add(hphm.trim());
				
				if(Tools.isNotEmpty(hpzl)){
					hql += " and hpzl = ? ";
					param.add(hpzl.trim());
				}
				
				//查询公开布控或本部门的秘密布控
				hql += " and (bkxz = '1' or (bkxz = '2' and lskhbm = '" + user.getLskhbm() + "')) ";
				
				//排序
				hql += " order by bksj desc ";
				
				//执行
				pageResult = eWarningService.getPageResult(hql, param, 1, 10000);
				request.setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			result = "0";//失败
			e.printStackTrace();
		} finally{
			request.setAttribute("hphm", hphm);
			request.setAttribute("hpzl", hpzl);
			request.setAttribute("conPath", "queryBksqForFk");
			request.setAttribute("result", result);
			return page;
		}
	}
	
	/**
     * 布控详情页面
     *
     * @param leave
     */
	@SuppressWarnings("finally")
	@RequestMapping("/detailDispatchedForWin")
	@Description(moduleName="已布控车拦截反馈", operateType="1", operationName="查看布控详情")
	public String detailDispatchedForWin(HttpServletRequest request, HttpServletResponse response, String bkid){
		String page = "/dispatched/detailDispatchedForWin";
		try {
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,CKYWZT,SFLJ,WLJDYY,CJCZJG");
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
			//获取布控类别
			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
			List<Map> bklbList = dispatchedService.findList(sql, null);
			Dispatched dispatched= dispatchedService.findDispatchedById(bkid);
			if("1".equals(dispatched.getZjbk())){
				//直接布控报备记录
				List<DisReport> disReportList = dispatchedService.findDisReport(dispatched.getBkid());
				request.setAttribute("disReportList", disReportList);
			} else{
				//审批记录
				List<DisApproveRecord> commentList = dispatchedService.findApproveRecord(dispatched.getBkid(), "1");
				request.setAttribute("commentList", commentList);
			}
			//签收记录
			List<DisReceive> receiveList = dispatchedService.findDisReceiveList(dispatched.getBkid(), "1");
			request.setAttribute("receiveList", receiveList);
			if(receiveList != null && receiveList.size() > 0){
				request.setAttribute("disReceive", receiveList.get(0));
			}
			//撤控记录
			List<Withdraw> withdrawList = dispatchedService.findWithdrawList(dispatched.getBkid());
			request.setAttribute("withdrawList", withdrawList);
			//撤控审批记录、通知记录
			if(withdrawList != null && withdrawList.size() > 0){
				Withdraw withdraw = withdrawList.get(0);
				//撤控通知签收记录
				List<DisReceive> ckReceiveList = dispatchedService.findDisReceiveList(withdraw.getCkid(), "2");
				request.setAttribute("ckReceiveList", ckReceiveList);
				//审批记录
				if("1".equals(withdraw.getZjck())){
					
				} else{
					//审批记录
					List<DisApproveRecord> ckCommentList = dispatchedService.findApproveRecord(withdraw.getCkid(), "2");
					request.setAttribute("ckCommentList", ckCommentList);
				}
			}
			//预警记录
			List<EWarning> ewarningList = dispatchedService.findEWaringList(dispatched.getBkid());
			request.setAttribute("ewarningList", ewarningList);
			
			//反馈记录
			String hql = " from InstructionSign where fkzt='1' and bkid=" + bkid + " order by fksj desc ";
			List<InstructionSign> instructionSignList = eWarningService.findObjects(hql, null);
			request.setAttribute("instructionSignList", instructionSignList);
			
			request.setAttribute("dispatched", dispatched);
			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
			request.setAttribute("bklb",bklbList);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 加载相应的布控信息，显示于已布控车拦截反馈录入页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/loadBksqForFk")
	public String loadBksqForFk(HttpServletRequest request, HttpServletResponse response, String bkid){
		String page = "/dispatched/loadBksqForFk";
		try {
//			//布控信息
//			Dispatched dispatched = dispatchedService.findDispatchedById(bkid);
//			request.setAttribute("dispatched", dispatched);
//			
//			//获取布控类别
//			String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
//			List<Map> bklbList = dispatchedService.findList(sql, null);
//			request.setAttribute("bklbList",JSON.toJSONString(bklbList));
//			request.setAttribute("bklb",bklbList);
			
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("ZLQSZT,ZLFKZT,BKJB,SFLJ,WLJDYY");
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
			request.setAttribute("bkid", bkid);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
     * 已布控车拦截反馈
     */
	@RequestMapping("/feedBack")
	@Description(moduleName="已布控车拦截反馈", operateType="2", operationName="已布控车拦截反馈")
	public void feedBack(HttpServletRequest request,HttpServletResponse response, int bkid,
			String fkrlxdh, String czjg, String sflj, String wljdyy, String ddr, String xbr, 
			String zhrs, String phajs, String fknr, String chsj, String chdd) {
		String result = "success";
		User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		try {
			//如果为拦截到，则需要检查是否有拦截成功的
			if("2".equals(czjg) && "1".equals(sflj)){
				String hql = " from InstructionSign where fkzt='1' and czjg='2' and sflj='1' and bkid=" + bkid + " order by fksj desc ";
				List<InstructionSign> instructionSignList = eWarningService.findObjects(hql, null);
				if(instructionSignList != null && instructionSignList.size() > 0){
					result = "repeat";
				}
			}
			
			if(!"repeat".equals(result)){
				InstructionSign instructionSign = new InstructionSign();
				instructionSign.setBkid(bkid);
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
				instructionSign.setChsj(chsj);
				instructionSign.setChdd(chdd);
				eWarningService.save(instructionSign);
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
}