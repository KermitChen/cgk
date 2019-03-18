package com.dyst.systemmanage.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.datetime.JDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.controller.DictionaryController;
import com.dyst.base.utils.PageResult;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.Announcement;
import com.dyst.systemmanage.entities.Message;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.SysAnnService;
import com.dyst.utils.Config;
import com.dyst.utils.StaticUtils;

@Controller
@RequestMapping(value="sysAnn")
public class SysAnnController extends DictionaryController{
	
	@Autowired
	private SysAnnService sysAnnService;

	/**
	 * 跳转到系统公告管理页面
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="toSysAnnListUI")
	public String toListUI(Model model) throws Exception{
		//加载公告类型
		List<Object> annType = new ArrayList<Object>();
		annType = this.getXXXList(annType, HQL_MAP.get("AnnType"));
		String page = "systemmanage/systemAnnouncement/sysAnn";
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
			model.addAttribute("annType", annType);
			return page;
		}
	}
	
	/**
	 * 跳转到系统公告管理页面
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="toSysAnnListUI2")
	public String toListUI2(Model model) throws Exception{
		int temp = sysAnnService.saveOneMessage(new Message());
		//加载公告类型
		List<Object> annType = new ArrayList<Object>();
		annType = this.getXXXList(annType, HQL_MAP.get("AnnType"));
		String page = "systemmanage/systemAnnouncement/sysAnnUI";
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
			model.addAttribute("annType", annType);
			return page;
		}
	}
	
	/**
	 * 管理员Ajax带分页异步请求系统公告文档列表信息
	 * @throws Exception 
	 */
	@RequestMapping(value="getPageRsultByAjax")
	@Description(moduleName="系统公告管理",operateType="1",operationName="查询系统公告")
	public String getPageRsultByAjax(Model model,HttpServletRequest request, HttpServletResponse response,String loginName, 
			String fileName, String annType, String kssj, String jssj, String pageNo) throws Exception{
		PageResult pageResult = sysAnnService.getAnnforPage(loginName, fileName, annType, kssj, jssj, Integer.parseInt(pageNo), 10);
		model.addAttribute("pageResult", pageResult);
		//加载公告类型
		List<Object> annTypeList = new ArrayList<Object>();
		annTypeList = this.getXXXList(annTypeList, HQL_MAP.get("AnnType"));
		model.addAttribute("annTypeList", annTypeList);
		return "systemmanage/systemAnnouncement/sysAnnList";
	}
	
	/**
	 * 用户Ajax带分页异步请求系统公告文档列表信息
	 * @throws Exception 
	 */
	@RequestMapping(value="getPageRsultByAjax2")
	@Description(moduleName="系统公告管理",operateType="1",operationName="查询系统公告")
	public String getPageRsultByAjax2(Model model,HttpServletRequest request, HttpServletResponse response,String loginName
			,String fileName, String annType, String kssj, String jssj, String pageNo) throws Exception{
		User user = (User) request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		String deptId = user.getDeptId();
		PageResult pageResult = sysAnnService.getAnnforPageAndNormalUser(loginName, fileName, annType, kssj, jssj, Integer.parseInt(pageNo), 10,deptId);
/*		@SuppressWarnings("unchecked")
		List<Announcement> list = pageResult.getItems();
		List<Announcement> resultList = new ArrayList<Announcement>();
		for(Announcement a :list){
			if(a.getDeptids()==null||a.getDeptids().equals("")){
				resultList.add(a);
			}else{
				String[] deptIds = a.getDeptids().split(",");//该条公告发送给的部门ids数组
				for(String d:deptIds){
					if(d.equals(deptId)){
						resultList.add(a);
						break;
					}
				}
			}
		}
		pageResult.setItems(resultList);
		pageResult.setTotalCount(resultList.size());
		//计算总页数
		int temp = (int)resultList.size()/10;
		pageResult.setTotalPageCount(resultList.size()%10==0?temp:temp+1);*/
		model.addAttribute("pageResult", pageResult);
		//加载公告类型
		List<Object> annTypeList = new ArrayList<Object>();
		annTypeList = this.getXXXList(annTypeList, HQL_MAP.get("AnnType"));
		model.addAttribute("annTypeList", annTypeList);
		return "systemmanage/systemAnnouncement/sysAnnListUI";
	}
	
	/**
	 * 跳转到新增系统公告文件UI页面
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="toAddSysAnnUI")
	public String toAddUI(Model model) throws Exception{
		//加载公告类型
		List<Object> annType = new ArrayList<Object>();
		annType = this.getXXXList(annType, HQL_MAP.get("AnnType"));
		String page = "systemmanage/systemAnnouncement/addSysAnn";
		try {
			
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			model.addAttribute("annType", annType);
			return page;
		}
	}
	
	/**
	 * 新增方法。。。。系统公告信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="addSysAnn")
	@Description(moduleName="系统公告管理",operateType="2",operationName="发布系统公告")
	public String addSysAnn(@RequestParam("file") CommonsMultipartFile commonsMultipartFile, HttpServletRequest request, HttpServletResponse response
			,String fileName,String remark,String days,String annType,String annGeneral,String dept){
		String page = "redirect:/sysAnn/toAddSysAnnUI.do";
		String result = "1";
		FileOutputStream os = null;
		InputStream is = null;
		try {
			//获取用户信息
			User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//存放路径
			String filePath = Config.getInstance().getSysAnnDir();
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
				//os = new FileOutputStream("D://upload" + File.separator + commonsMultipartFile.getOriginalFilename());
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
			int flag = 1;
			Announcement ann = new Announcement();
			if(StringUtils.isNotBlank(fileName)){
				ann.setFileName(fileName);
			} else{
				flag = 0;
			}
			if(StringUtils.isNotBlank(remark)){
				ann.setRemark(remark);
			}
			if(StringUtils.isNotBlank(days)){
				//设置公告结束时间
				int day = Integer.parseInt(days);
				Calendar now = Calendar.getInstance();
				now.setTime(new Date());
				now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
				ann.setEndTime(new Timestamp(now.getTime().getTime()));
			} else{
				flag = 0;
			}
			if(StringUtils.isNotBlank(annType)){
				ann.setAnnType(annType);
			} else{
				flag = 0;
			}
			if(StringUtils.isNotBlank(annGeneral)){
				ann.setOutline(annGeneral);
			} else{
				flag = 0;
			}
			
			//部门可以为空
			if(StringUtils.isNotBlank(dept)){
				ann.setDeptids(dept);
			}
			ann.setFileUrl(commonsMultipartFile.getOriginalFilename());
			ann.setJlzt("1");
			ann.setBuildPno(operUser.getLoginName());
			ann.setBuildName(operUser.getUserName());
			ann.setBuildTime(new Timestamp(new Date().getTime()));
			ann.setUpdateTime(new Timestamp(new Date().getTime()));
			ann.setStartTime(new Timestamp(new Date().getTime()));
			//service层保存
			if(flag == 1){
				sysAnnService.addSysAnn(ann);
			} else if(flag ==0){
				result = "0";
			}
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		} finally{
			request.getSession().setAttribute("result", result);
			return page;
		}
	}
	
	/**
	 * 异步加载..系统公告详情
	 * @throws Exception 
	 */
	@RequestMapping(value="getSysAnnDetail")
	@Description(moduleName="系统公告管理",operateType="1",operationName="查看系统公告")
	public String getSysAnnDetail(Model model,HttpServletRequest req) throws Exception{
		String message = "加载详情成功！";
		String id = (String) req.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			try {
				Announcement a = sysAnnService.getObjectById(Integer.parseInt(id));
				model.addAttribute("Ann", a);
			} catch (Exception e) {
				e.printStackTrace();
				message = "加载详情失败！";
			}
		}
		model.addAttribute("message", message);
		//加载公告类型
		List<Object> annType = new ArrayList<Object>();
		annType = this.getXXXList(annType, HQL_MAP.get("AnnType"));
		model.addAttribute("annTypeList", annType);
		return "systemmanage/systemAnnouncement/sysAnnDetail";
	}
	
	
	/**
	 * 跳转到编辑页面
	 * @throws Exception 
	 */
	@RequestMapping(value="toEditUI")
	public String toEditUI(Model model,HttpServletRequest req) throws Exception{
		String id = (String) req.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			try {
				Announcement a = sysAnnService.getObjectById(Integer.parseInt(id));
				model.addAttribute("Ann", a);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//加载公告类型
		List<Object> annType = new ArrayList<Object>();
		annType = this.getXXXList(annType, HQL_MAP.get("AnnType"));
		model.addAttribute("annType", annType);
		return "systemmanage/systemAnnouncement/sysAnnEdit";
	}
	
	/**
	 * 保存编辑
	 */
	@RequestMapping(value="saveEdit")
	@Description(moduleName="系统公告管理",operateType="3",operationName="修改系统公告")
	public void saveEdit(Announcement announcement, Model model,HttpServletRequest req,HttpServletResponse resp){
		int flag = 1;
		try {
			//获取信息
			Announcement an = sysAnnService.getObjectById(announcement.getId());
			//更新
			an.setAnnType(announcement.getAnnType());
			an.setUpdateTime(new Timestamp(new Date().getTime()));
			an.setFileName(announcement.getFileName());
			an.setOutline(announcement.getOutline());
			an.setRemark(announcement.getRemark());
			flag = sysAnnService.update(an);
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
		} finally{
			writeResponse(resp, Integer.toString(flag));
		}
	}
	
	/**
	 * 删除一条系统公告
	 */
	@RequestMapping(value="deleteOneById")
	@Description(moduleName="系统公告管理",operateType="4",operationName="删除系统公告")
	public void deleteOneById(Model model,HttpServletRequest req,HttpServletResponse resp){
		String id = req.getParameter("id");
		int temp =0;
		if(StringUtils.isNotBlank(id)){
			temp = sysAnnService.deleteOneById(id);
		}
		String result = JSON.toJSONString(temp);
		writeResponse(resp, result);
	}
	
	/**
	 * 批量删除选中的文件
	 */
	@RequestMapping(value="batcheDeleteObj")
	@Description(moduleName="系统公告管理",operateType="4",operationName="批量删除系统公告")
	public void batcheDeleteObj(Model model,String[] ids,HttpServletRequest req,HttpServletResponse resp){
		int temp = 0;
		if(ids != null&& ids.length>=1){
			temp = sysAnnService.batcheDeleteObj(ids);
		}
		Map<String,String> message = new HashMap<String, String>();
		message.put("result", Integer.toString(temp));
		String data = JSON.toJSONString(message);
		writeResponse(resp, data);
	}
	
	/**
	 * 用户点击推送。。跳转到系统公告查看页面
	 * @throws Exception 
	 */
	@RequestMapping(value="toSysAnnForUserUI")
	public String toSysAnnForUser(Model model) throws Exception{
		//加载所有的公告类型
		List<Object> annType = new ArrayList<Object>();
		annType = this.getXXXList(annType, HQL_MAP.get("AnnType"));
		model.addAttribute("annType", annType);
		//加载所有的公告时长
		List<Object> annTime = new ArrayList<Object>();
		annTime = this.getXXXList(annTime, HQL_MAP.get("AnnTime"));
		model.addAttribute("annTime", annTime);
		return "systemmanage/systemAnnouncement/sysAnnForUser";
	}
	
	//Ajax保存已读的文件id到user中字段
	@RequestMapping(value="markFileHasRead")
	@Description(moduleName="系统公告管理",operateType="3",operationName="已读系统公告")
	public void markFileHasRead(HttpServletRequest req,HttpServletResponse resp){
		String id = req.getParameter("id");
		User user = (User)req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		StringBuilder oldAnnids = new StringBuilder();
		oldAnnids.append(user.getAnnids());
		oldAnnids.append(","+id);
		user.setAnnids(oldAnnids.toString());
		int tmp = sysAnnService.updateUser(user);
		writeResponse(resp, Integer.toString(tmp));
	}
	
	//异步请求所有的公告ids
	@RequestMapping(value="getAllAnnIds")
	@Description(moduleName="系统公告管理",operateType="1",operationName="查询系统公告")
	public void getAllAnnIds(HttpServletRequest req,HttpServletResponse resp){
		String hql = "select id from Announcement a ";
		List<Object> list = sysAnnService.getAllAnnIds(hql);
		String data = JSON.toJSONString(list);
		writeResponse(resp,data);
	}
	
	//查询该用户加上限制条件---用户部门
		//新加功能，查询用户待查看的  消息
	@RequestMapping(value="getAllAnnIds2")
	public void getAllAnnIds2(HttpServletRequest req,HttpServletResponse resp){
		//查询该用户待查看的系统公告
		User user = (User) req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		String deptId = user.getDeptId();
		String hql = "from Announcement a where 1=1 order by a.id desc";
		List<Announcement> list = sysAnnService.getAllAnnIds2(hql);//得到系统公告 文档列表
		List<Announcement> resultList = new ArrayList<Announcement>();//用户有权限查到的公告列表
		List<String> allAnnIds = new ArrayList<String>();//用户未读的公告ids列表
		if(list!=null&&list.size()>=1){
			for(Announcement a:list){
				if(a.getDeptids()==null||a.getDeptids().equals("")){//发送所有部门
					resultList.add(a);
				}else{//发送指定部门
					String[] deptIds = a.getDeptids().split(",");
					for(String d:deptIds){
						if(d.equals(deptId)){
							resultList.add(a);
							break;
						}
					}
				}
			}
			for(Announcement a :resultList){
				allAnnIds.add(a.getId().toString());
			}
		}
		//[22,21,20,18]
		//用户已读的ids
		int temp = 0;
		if(StringUtils.isNotBlank(user.getAnnids())){
			String[] hasReadIds = user.getAnnids().split(",");
			for(String h:hasReadIds){
				while(allAnnIds.indexOf(h)>=0){
					allAnnIds.remove(allAnnIds.indexOf(h));
				}
			}
		}
		temp = allAnnIds.size();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sysAnn", Integer.toString(temp));
		//我的消息 部分
		String hql_myMessage = "from Message m where m.statue ='1' and m.hasread ='0' and m.recid=:recid";
		Map<String,Object> params = new HashMap<String, Object>();
		String loginName = user.getLoginName();
		params.put("recid", loginName);
		List<Message> list_allMessage = new ArrayList<Message>();
		list_allMessage = sysAnnService.getEnabledMessage(hql_myMessage,params);
		int messageCount =0;
		if(list_allMessage!=null&&list_allMessage.size()>0){
			messageCount = list_allMessage.size();
		}
		map.put("myMessage", messageCount);
		String message = JSON.toJSONString(map);
		writeResponse(resp,message);
	}
	
	/**
	 * 跳转到通知中心页面
	 */
	@RequestMapping(value="toTzzxUI")
	@Description(moduleName="通知中心",operateType="1",operationName="查询通知")
	public String toTzzxUI(Model model,HttpServletRequest req,HttpServletResponse resp,PageResult pageResult){
		User user = (User) req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		String hql_myMessage = "from Message m where m.statue ='1' and m.recid =:loginName order by m.sendtime desc";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("loginName", user.getLoginName());
		pageResult = sysAnnService.getPageResult(hql_myMessage, params, pageResult.getPageNo(), 10);
		model.addAttribute("pageResult", pageResult);
		return "systemMessage/myTz/myTzListUI";
	}
	/**
	 * 通知中心中，所有的  通知消息都标为已读
	 */
	@RequestMapping(value="markAllTzzxHasRed")
	@Description(moduleName="通知中心",operateType="3",operationName="已读通知")
	public String markAllTzzxHasRed(HttpServletRequest req,HttpServletResponse resp){
		User user = (User) req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		if(user!=null){
			sysAnnService.markUserAllTzzxHasRed(user.getLoginName());
		}
		return "redirect:/sysAnn/toTzzxUI.do";
	}
	/**
	 * 通知中心，根据消息的id加载出消息的详情
	 * 并设置为已读
	 */
	@RequestMapping(value="getMessageDetail")
	@Description(moduleName="通知中心",operateType="1",operationName="查看通知")
	public String getMessageDetail(HttpServletRequest req,HttpServletResponse resp,Model model){
		String id = (String) req.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			Message message = sysAnnService.getOneMessgeById(Integer.parseInt(id));
			model.addAttribute("message", message);
		}
		return "systemMessage/myTz/myTzDetail";
	}
	
	//私有的向前端页面发送信息的方法
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
