package com.dyst.trafficAnalysis.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.User;
import com.dyst.trafficAnalysis.service.RealFlowService;
import com.dyst.utils.StaticUtils;
import com.dyst.vehicleQuery.service.BDService;

@Controller
@RequestMapping("/traffic")
public class TrafficController {

	@Resource
	private RealFlowService realFlowService;
	@Resource
	private BDService bdService;
	
	/**
	 * 预跳转方法
	 * @param request
	 * @param map
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/preReal")
	public String preRealFlow(HttpServletRequest request,Map<String, Object> map){
		String page = "/trafficAnalysis/realTimeTraffic";
		try {
			//获取数据字典
			//获取布控类别
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			request.setAttribute("userId", "flow"+user.getLoginName());
			map.put("cdMap", bdService.getCdMap());
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 预跳转方法
	 * @param request
	 * @param map
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/preHistory")
	public String preHistoryFlow(HttpServletRequest request,Map<String, Object> map){
		String page = "/trafficAnalysis/unrealTimeTraffic";
		try {
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 动态流量展示获取数据方法
	 * @param response
	 * @param beginTime
	 * @param endTime
	 * @param jcd 监测点
	 * @param interval 时间间隔
	 */
	@RequestMapping("/getChartData")
	@Description(moduleName="流量统计",operationName="获取流量统计数据")
	public void getChartData(HttpServletResponse response,String beginTime,String endTime,String jcd,String interval){
		try {
			Map<String, Object> map = realFlowService.getChartData(beginTime, endTime, jcd, interval);
			response.setContentType("application/json");
			response.getWriter().write(JSON.toJSONString(map));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
