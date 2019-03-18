package com.dyst.systemmanage.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.datetime.JDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dyst.base.utils.*;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.entities.Wjjlb;
import com.dyst.systemmanage.service.*;
import com.dyst.utils.Config;
import com.dyst.utils.StaticUtils;

/**
 * @author： cgk
 * @date：2016-04-02
 * @version：0.0.1
 * @doc：帮助文档管理Controller类，主要实现方法：
 *       1.初始化帮助文档管理页面
 *       2.分页获取所上传的帮助文档
 *       3.删除帮助文档
 *       4.批量删除帮助文档
 *       5.帮助文档详情
 *       6.初始化更新帮助文档页面
 *       7.修改帮助文档
 */
@Controller
@RequestMapping("/helpDoc")
public class HelpDocController {
	//注入业务层
	@Resource
	private HelpDocService helpDocService;
	public HelpDocService getHelpDocService() {
		return helpDocService;
	}
	public void setHelpDocService(HelpDocService helpDocService) {
		this.helpDocService = helpDocService;
	}

	/**
	 * 初始化帮助文档管理页面
	 * @return
	 *      帮助文档管理页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/iniDocManage")
	public String iniDocManage(Model model, HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/document/docManage";
		try {
			JDateTime jdt = new JDateTime();
			model.addAttribute("year", jdt.toString("YYYY"));
			model.addAttribute("month", jdt.toString("MM"));
			model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
			model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("cxlb", "1");
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 分页获取所上传的帮助文档
	 * @return
	 *      帮助文档管理页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getWjjlbForPage")
	@Description(moduleName="帮助文档管理",operateType="1", operationName="查询帮助文档")
	public String getWjjlbForPage(HttpServletRequest request, HttpServletResponse response) {
		PageResult pageResult = null;
		try {
			//获取参数
			String loginName = request.getParameter("loginName");//用户名
			String userName = request.getParameter("userName");//用户姓名
			String fileName = request.getParameter("fileName");//文档名称
			String startTime = request.getParameter("startTime");//上传起始时间
			String endTime = request.getParameter("endTime");//上传截至时间
			int pageNo = Integer.parseInt(request.getParameter("pageNo"));
			
			//获取分页结果
			pageResult = helpDocService.getWjjlbForPage(loginName, userName, fileName, startTime, endTime, pageNo, 10);
			request.setAttribute("pageResult", pageResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			return "systemmanage/document/docList";
		}
	}
	
	/**
	 * 删除帮助文档
	 * @return
	 *      0:删除失败！
	 * 		1:删除成功！
	 */
	@RequestMapping("/deleteWjjlb")
	@Description(moduleName="帮助文档管理",operateType="4", operationName="删除帮助文档")
	public void deleteWjjlb(HttpServletRequest request, HttpServletResponse response){
		String result = "1";
		try {
			String id = request.getParameter("id");//信息编号
			if(id != null && !"".equals(id.trim())){
				helpDocService.deleteWjjlb(id.trim());
			} else {
				result = "0";//删除失败
			}
		} catch (Exception e) {
			result = "0";//删除失败
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 批量删除帮助文档
	 * @return
	 *      0:删除失败！
	 * 		1:删除成功！
	 */
	@RequestMapping("/batcheDeleteWjjlb")
	@Description(moduleName="帮助文档管理",operateType="4", operationName="批量删除帮助文档")
	public void batcheDeleteWjjlb(HttpServletRequest request, HttpServletResponse response){
		String result = "1";
		try {
			String[] ids = request.getParameterValues("ids");//信息编号
			if(ids != null && ids.length > 0){
				StringBuilder sb = new StringBuilder();
				for(int i=0;i < ids.length;i++){
					sb.append(ids[i]).append(",");
				}
				helpDocService.deleteWjjlb(sb.substring(0, sb.length()-1));
			} else {
				result = "0";//删除失败
			}
		} catch (Exception e) {
			result = "0";//删除失败
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 帮助文档详情
	 * @return
	 *      帮助文档详情页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/wjjlbDetail")
	@Description(moduleName="帮助文档管理",operateType="1", operationName="查看帮助文档详情")
	public String wjjlbDetail(HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/document/docDetail";
		try {
			//根据ID，获取用户信息
			int id = Integer.parseInt(request.getParameter("id"));//用户ID
			Wjjlb wjjlb = helpDocService.getWjjlb(id);
			request.setAttribute("wjjlbObj", wjjlb);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 初始化更新帮助文档页面
	 * @return
	 *      更新帮助文档页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initUpdateWjjlb")
	public String initUpdateWjjlb(HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/document/updateDoc";
		try {
			//根据ID，获取用户信息
			int id = Integer.parseInt(request.getParameter("id"));//帮助文档ID
			Wjjlb wjjlb = helpDocService.getWjjlb(id);
			request.setAttribute("wjjlbObj", wjjlb);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 修改帮助文档
	 * @return
	 *      帮助文档页面
	 */
	@RequestMapping("/updateWjjlb")
	@Description(moduleName="帮助文档管理",operateType="3", operationName="修改帮助文档")
	public void updateWjjlb(HttpServletRequest request, HttpServletResponse response) {
		String result = "1";
		try {
			//获取数据
			int id = Integer.parseInt(request.getParameter("id"));//帮助文档ID
			String fileName = request.getParameter("fileName");//文档名称
			String remark = request.getParameter("remark");//备注
			
			//保存
			helpDocService.updateWjjlb(id, fileName, remark);
		} catch (Exception e) {
			result = "0";//失败
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 初始化上传帮助文档页面
	 * @return
	 *      上传帮助文档页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initAddWjjlb")
	public String initAddWjjlb(HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/document/addDoc";
		try {
			
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 上传帮助文档
	 * @return
	 *      上传帮助文档页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/addWjjlb")
	@Description(moduleName="帮助文档管理", operateType="2",  operationName="上传帮助文档")
	public String addWjjlb(@RequestParam("file") CommonsMultipartFile commonsMultipartFile, HttpServletRequest request, HttpServletResponse response) {
		String page = "redirect:/helpDoc/initAddWjjlb.do";
		String result = "1";
		FileOutputStream os = null;
		InputStream is = null;
		try {
			//获取数据
			String fileName = request.getParameter("fileName");//文档名称
			String remark = request.getParameter("remark");//备注
			
			//获取用户信息
			User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//存放路径
			String filePath = Config.getInstance().getHelpDocDir();
			//创建文件夹
			File fileFolder = new File(filePath);
			if (!fileFolder.getParentFile().exists()) {
				fileFolder.getParentFile().mkdirs();
			}
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}

			//保存文件
			if(!commonsMultipartFile.isEmpty()){
				os = new FileOutputStream(filePath + File.separator + commonsMultipartFile.getOriginalFilename());
				is = commonsMultipartFile.getInputStream();
					
				byte[] b = new byte[1024*1024*5];
				int count = 0;
				while((count=is.read(b))!=-1){
					os.write(b, 0, count);
				}
				
				//立即写入文件并关闭
				if(os != null){
					try {
						os.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(os != null){
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(is != null){
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	
			}
			
			//保存
			Wjjlb wjjlb = new Wjjlb();
			wjjlb.setFileName(fileName);
			wjjlb.setFileUrl(commonsMultipartFile.getOriginalFilename());
			wjjlb.setJlzt("1");
			wjjlb.setRemark(remark);
			wjjlb.setBuildPno(operUser.getLoginName());
			wjjlb.setBuildName(operUser.getUserName());
			wjjlb.setBuildTime(new Date());
			wjjlb.setUpdateTime(new Date());
			
			helpDocService.addWjjlb(wjjlb);
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		} finally{
			request.getSession().setAttribute("result", result);
			return page;
		}
	}
	
	/**
	 * 分页获取所上传的帮助文档，下载页面
	 * @return
	 *      帮助文档下载页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getWjjlbForPageToDownLoad")
	@Description(moduleName="帮助文档管理", operateType="1",  operationName="查找及下载帮助文档")
	public String getWjjlbForPageToDownLoad(HttpServletRequest request, HttpServletResponse response) {
		PageResult pageResult = null;
		try {
			//获取参数
			String loginName = request.getParameter("loginName");//用户名
			String userName = request.getParameter("userName");//用户姓名
			String fileName = request.getParameter("fileName");//文档名称
			String startTime = request.getParameter("startTime");//上传起始时间
			String endTime = request.getParameter("endTime");//上传截至时间
			int pageNo = Integer.parseInt(request.getParameter("pageNo"));
			
			//获取分页结果
			pageResult = helpDocService.getWjjlbForPage(loginName, userName, fileName, startTime, endTime, pageNo, 10);
			request.setAttribute("pageResult", pageResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			return "systemmanage/document/docQueryList";
		}
	}
}