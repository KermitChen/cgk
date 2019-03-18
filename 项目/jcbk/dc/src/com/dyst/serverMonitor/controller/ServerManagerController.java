package com.dyst.serverMonitor.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.log.annotation.Description;
import com.dyst.serverMonitor.service.ServerManagerService;
import com.dyst.systemmanage.service.UserService;

@Controller
@RequestMapping("/server")
public class ServerManagerController {

	@Resource
	private ServerManagerService serverManager;
	@Resource
	private UserService userService;
	
	@SuppressWarnings("finally")
	@RequestMapping("/pre")
	public String preQuery(Map<String, Object> map){
		String page = "/serverMonitor/serverManager";
		try {
			List<Dictionary> list = userService.getDictionarysByTypeCode("SERVER");
			map.put("serverList", JSON.toJSON(list));
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		}finally{
			return page;
		}
	}
	/**
	 * 查询服务器相关信息
	 * @param hosts
	 * @param response
	 */
	@RequestMapping("/getMess")
	@Description(moduleName="服务器运维管理",operateType="1",operationName="查询服务器状态信息")
	public void getServerMess(String hosts,HttpServletResponse response){
		try {
			response.setContentType("application/json");
			response.getWriter().write(JSON.toJSONString(serverManager.queryServerInfo(hosts)));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
