package com.dyst.systemmanage.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.datetime.JDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dyst.base.utils.PageResult;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.BugReport;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.BugReportService;
import com.dyst.utils.Config;
import com.dyst.utils.StaticUtils;

@Controller
@RequestMapping("/bugReport")
public class BugReportController {

	@Autowired
	private BugReportService bugReportService;
	/**
	 * 初始化查询页面
	 * @return
	 */
	@RequestMapping("/preFind")
	public String preFind(Map<String, Object> map){
		String page = "/systemmanage/bugReport/bugReport";
		try {
			JDateTime jdate = new JDateTime();
			map.put("endTime", jdate.toString("YYYY-MM-DD hh:mm:ss"));
			map.put("beginTime", jdate.subDay(30).toString("YYYY-MM-DD hh:mm:ss"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * 分页查询
	 * @param beginTime
	 * @param endTime
	 * @param isDeal
	 * @param map
	 * @return
	 */
	@RequestMapping("/find")
	@Description(moduleName="问题反馈", operateType="1", operationName="分页查询反馈问题")
	public String findByPage(PageResult pageResult,String beginTime,String endTime,String isDeal,Map<String, Object> map){
		String page = "/systemmanage/bugReport/bugReport";
		try {
			pageResult = bugReportService.searchBugReportByPage(beginTime, endTime, isDeal, pageResult.getPageNo(), 10);
			map.put("pageResult", pageResult);
			map.put("beginTime", beginTime);
			map.put("endTime", endTime);
			map.put("isDeal", isDeal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * 初始化添加页面
	 * @return
	 */
	@RequestMapping("/preAdd")
	public String preAdd(){
		return "/systemmanage/bugReport/addReport";
	}
	/**
	 * 添加问题反馈
	 * @param request
	 * @param file
	 * @param problemDesc
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/add")
	@Description(moduleName="问题反馈", operateType="2", operationName="添加问题")
	public String addBugReport(HttpServletRequest request,@RequestParam(value="file",required=false) MultipartFile file,String problemDesc){
		String page = "/systemmanage/bugReport/addReport";
		String result = "0";
		try {
			//获取用户信息
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			BugReport report = new BugReport(user.getLoginName(), user.getUserName(), new Date(), problemDesc,"0");
			bugReportService.addBugReport(report);
			//保存成功
			if (report.getId() != null && !file.isEmpty()) {
				//保存路径
				String pathRoot = Config.getInstance().getBugPicDir();
				//路径不存在则进行创建
				File fileFolder = new File(pathRoot);
				if (!fileFolder.getParentFile().exists()) {
					fileFolder.getParentFile().mkdirs();
				}
				if (!fileFolder.exists()) {
					fileFolder.mkdirs();
				}
				//保存文件
                String path = pathRoot + File.separator + report.getId() + file.getOriginalFilename();
                file.transferTo(new File(path));  
                report.setPicAddress(report.getId()+file.getOriginalFilename());
	            bugReportService.updateBugReport(report);
			}
			result = "1";
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			request.getSession().setAttribute("result", result);
			return page;
		}
	}
	/**
	 * 删除记录
	 * @param id
	 * @param response
	 */
	@RequestMapping("/delete")
	@Description(moduleName="问题反馈", operateType="4", operationName="删除问题")
	public void deleteReport(String id,HttpServletResponse response){
		response.setContentType("application/json");
		String res = "0";
		PrintWriter writer = null;
		try {
			if(StringUtils.isNotEmpty(id)){
				BugReport report = bugReportService.getById(Integer.parseInt(id));
				String filePath = Config.getInstance().getBugPicDir() + File.separator + report.getPicAddress();
				bugReportService.deleteBugReport(report);
				File fileFolder = new File(filePath);
				if(fileFolder.exists()){
					fileFolder.delete();
				}
				res = "1";
			}
			writer = response.getWriter();
			String jsonData = "{\"res\":\"" + res + "\"}";
			writer.write(jsonData);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	/**
	 * 标记已处理
	 * @param id
	 * @param response
	 */
	@RequestMapping("/deal")
	@Description(moduleName="问题反馈", operateType="3", operationName="标记问题已处理")
	public void dealReport(String id,HttpServletResponse response){
		response.setContentType("application/json");
		String res = "0";
		PrintWriter writer = null;
		try {
			if(StringUtils.isNotEmpty(id)){
				BugReport report = bugReportService.getById(Integer.parseInt(id));
				report.setIsDeal("1");
				report.setDealTime(new Date());
				bugReportService.updateBugReport(report);
				res = "1";
			}
			writer = response.getWriter();
			String jsonData = "{\"res\":\"" + res + "\"}";
			writer.write(jsonData);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.close();
			}
		}
	}
}
