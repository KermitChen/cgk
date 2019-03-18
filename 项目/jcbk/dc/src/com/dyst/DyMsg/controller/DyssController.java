package com.dyst.DyMsg.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.DyMsg.service.DyssQueryService;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.User;
import com.dyst.utils.StaticUtils;

@Controller
@RequestMapping(value="dyss")
public class DyssController extends DySpController{
	
	StringBuffer dyData = new StringBuffer();
	@Autowired
	private DyssQueryService dyssQueryService;

	//跳转到订阅实时查询页面
	@RequestMapping(value="findDyss")
	public String findDyssUI(Model model,HttpServletRequest req,String Check_dylx) throws Exception{
		//审批结果..json格式
		dylxList = getXXXList(dylxList,HQL_MAP.get("dylx"));
		String dylxListJson = JSON.toJSONString(dylxList);
		model.addAttribute("dylxListJson", dylxListJson);
		//加载车牌颜色json格式
		hpysList = getXXXList(hpysList,HQL_MAP.get("hpys"));
		String cpysListJson =JSON.toJSONString(hpysList);
		model.addAttribute("cpysListJson", cpysListJson);
		//返回打开此页面的用户
		User user = (User) req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		model.addAttribute("userId", "Dy@"+user.getLoginName());
		//返回 监测点 json 格式
		jcdsList = getXXXList(jcdsList,HQL_MAP.get("jcds"));
		String jcdsListJson =JSON.toJSONString(jcdsList);
		model.addAttribute("jcdsListJson", jcdsListJson);
		model.addAttribute("Check_dylx", Check_dylx);
		return "DyMsg/dysscx/dyRealTimeQuery";
	}
	
	//加载实时订阅车辆 的过车数据
	@RequestMapping(value="loadDy")
	@Description(moduleName="订阅实时跟踪",operateType="1",operationName="查询订阅结果")
	public void loadRealTimeDyGcData(HttpServletRequest req,HttpServletResponse resp,String Check_dylx){
		try {
			StringBuffer hql = new StringBuffer();
			Map<String,Object> params = new HashMap<String,Object>();
			hql.append("from Dyjg d where d.dyrjh =:dyrjh");
			if(StringUtils.isNotBlank(Check_dylx)){
				String [] dys = Check_dylx.split(";");
				hql.append(" and d.dylx in(:Check_dylx)");
				params.put("Check_dylx", dys);
			}
			hql.append(" order by d.lrsj desc");
			List<Object> list = dyssQueryService.getObjects(hql.toString(), params);
			String result = JSON.toJSONString(list.get(0));
			writeResponse(resp,result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//加载历史订阅车辆 的过车数据
	@RequestMapping(value="loadHistoryDy")
	@Description(moduleName="订阅实时跟踪",operateType="1",operationName="查询订阅结果")
	public void loadHistoryDyGcData(HttpServletRequest req,HttpServletResponse resp,String Check_dylx){
		try {
			User user = (User) req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			req.setAttribute("DyUserId", "Dy@"+user.getLoginName());
			StringBuffer hql = new StringBuffer();
			Map<String,Object> params = new HashMap<String,Object>();
			hql.append("from Dyjg d where d.dyrjh =:dyrjh");
			params.put("dyrjh", user.getLoginName());
			if(StringUtils.isNotBlank(Check_dylx)){
				String [] dys = Check_dylx.split(";");
				hql.append(" and d.dylx in(:Check_dylx)");
				params.put("Check_dylx", dys);
			}
			List<Object> list = dyssQueryService.getObjects(hql.toString(), params);
			String result = JSON.toJSONString(list);
			writeResponse(resp,result);
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
	
	//根据id查询订阅信息
	@RequestMapping(value="getDyxxById")
	@Description(moduleName="订阅实时跟踪",operateType="1",operationName="查看订阅详情")
	public void getDyxxById(Model model,String id,HttpServletResponse resp){
		String hql = "from Dyjg d where d.dyxx.id =:id order by d.lrsj desc";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", Integer.parseInt(id));
		List<Object> list = dyssQueryService.getObjects(hql, params);
		Object obj = list.get(0);
		if(obj!=null){
			writeResponse(resp,JSON.toJSONString(obj));
		}
	}
	
	@RequestMapping(value="addDyssItem")
	public void addDyssItem(HttpServletRequest req,HttpServletResponse resp){
		dyssQueryService.sendDyssQueryMsg("");
		writeResponse(resp, "1");
	}
}