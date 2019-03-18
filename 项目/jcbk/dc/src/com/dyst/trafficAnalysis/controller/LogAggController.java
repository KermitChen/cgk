package com.dyst.trafficAnalysis.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jodd.datetime.JDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dyst.log.annotation.Description;
import com.dyst.trafficAnalysis.service.LogAggService;

@Controller
@RequestMapping("/logAgg")
public class LogAggController {
	
	@Autowired
	private LogAggService logAggService;
	
	//查询类型map表
	private Map<String, String> getCxlxMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "按天查询");
		map.put("2", "按月查询");
		map.put("3", "自定义查询");
		return map;
	}

	@RequestMapping("/pre")
	public String prelogAgg(Model model, Map<String, Object> map){
		JDateTime jdt = new JDateTime();
		model.addAttribute("year", jdt.toString("YYYY"));
		model.addAttribute("month", jdt.toString("MM"));
		model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
		model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
		model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
		model.addAttribute("cxlb", "1");
		model.addAttribute("cxlxMap", getCxlxMap());
		
		String page = "/trafficAnalysis/zhtj";
		return page;
	}
	
	@RequestMapping("/tjBusLog")
	@Description(moduleName="综合数据统计", operationName="统计业务日志")
	public void tjBusinessLog(HttpServletResponse response, String kssj, String jssj, String ip, String operator, 
			String operateName, String tjWord){
		List<JSONObject> list = null;
		try {
			list = logAggService.tjBusinessLog(kssj, jssj, ip, operator, operateName, tjWord);
			response.setContentType("application/json");
			response.getWriter().write(JSON.toJSONString(list));
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}