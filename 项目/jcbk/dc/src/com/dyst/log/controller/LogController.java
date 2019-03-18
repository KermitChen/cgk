package com.dyst.log.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.datetime.JDateTime;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dyst.base.utils.PageResult;
import com.dyst.log.annotation.Description;
import com.dyst.log.service.LogService;

@Controller
@RequestMapping("/log")
public class LogController {

	@Autowired
	private LogService logService;
	
	//查询类型map表
	private Map<String, String> getCxlxMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "按天查询");
		map.put("2", "按月查询");
		map.put("3", "自定义查询");
		return map;
	}
	
	/**
	 * 跳转到查询页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/preSearch")
	public String preSearch(Model model, Map<String, Object> map){
		try {
			JDateTime jdt = new JDateTime();
			model.addAttribute("year", jdt.toString("YYYY"));
			model.addAttribute("month", jdt.toString("MM"));
			model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
			model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("cxlb", "1");
			model.addAttribute("cxlxMap", getCxlxMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "systemmanage/log/businessLog";
	}
	/**
	 * 查询业务日志
	 * @param pageNo
	 * @param kssj
	 * @param jssj
	 * @param map
	 * @return
	 */
	@RequestMapping("/searchBusinessLog")
	@Description(moduleName="操作日志查询",operateType="1",operationName="查询业务日志")
	public String searchBusinessLog(HttpServletRequest request, Map<String, Object> map){
		String page = "systemmanage/log/businessLog";
		try {
			String operator = request.getParameter("operator");
			String operaterName = request.getParameter("operaterName");
			String operatorIp = request.getParameter("operatorIp");
			String moduleName = request.getParameter("moduleName");
			String operateName = request.getParameter("operateName");
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String cxrq = request.getParameter("cxrq");
			String cxlb = request.getParameter("cxlb");
			String kssj = request.getParameter("kssj");
			String jssj = request.getParameter("jssj");
			String pageNo = request.getParameter("pageNo");
			int pageNum = StringUtils.isEmpty(pageNo)?1:Integer.parseInt(pageNo);
			
			//调用
			PageResult pageResult = logService.searchBusinessLog(pageNum, 10, operator, operaterName, operatorIp, moduleName, operateName, kssj, jssj);
			
			map.put("pageResult", pageResult);
			map.put("cxlxMap", getCxlxMap());
			map.put("operator", operator);
			map.put("operaterName", operaterName);
			map.put("operatorIp", operatorIp);
			map.put("moduleName", moduleName);
			map.put("operateName", operateName);
			map.put("year", year);
			map.put("month", month);
			map.put("cxrq", cxrq);
			map.put("cxlb", cxlb);
			map.put("kssj", kssj);
			map.put("jssj", jssj);
		} catch (Exception e) {
			e.printStackTrace();
			page = "redirect:/common/errorPage/error500.jsp";
		}
		return page;
	}
	/**
	 * 根据id查询业务日志
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping("/getLogById")
	@Description(moduleName="操作日志查询",operateType="1",operationName="根据id查询业务日志")
	public String getBusinessLogById(String id,Map<String, Object> map){
		String page = "systemmanage/log/logDetail";
		try {
			String result = logService.getBusinessLogById(id);
			if(StringUtils.isNotEmpty(result)){
				map.put("log", JSON.parse(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
			page = "redirect:/common/errorPage/error500.jsp";
		}
		return page;
	}
}
