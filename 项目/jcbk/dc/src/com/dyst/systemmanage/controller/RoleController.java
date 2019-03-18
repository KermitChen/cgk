package com.dyst.systemmanage.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.base.utils.*;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.*;
import com.dyst.systemmanage.service.*;
import com.dyst.utils.StaticUtils;

/**
 * @author： cgk
 * @date：2016-04-02
 * @version：0.0.1
 * @doc：角色信息管理Controller类，主要实现方法：
 *       1.初始化角色信息管理页面
 *       2.获取所有本用户所创建的角色
 *       3.获取所有启用的功能
 *       4.删除角色信息
 *       5.批量删除角色信息
 *       6.获取该用户所有的所有启用的功能（新增角色页面）
 *       7.新增角色信息
 *       8.获取该用户所有的所有启用的功能（修改角色页面）
 *       9.修改角色信息
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	//注入业务层
	@Resource
	private UserService userService;
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Resource
	private RoleService roleService;
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	/**
	 * 初始化角色信息管理页面
	 * @return
	 *      角色信息管理页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initRoleManage")
	public String initRoleManage(HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/role/roleManage";
		try {
			//获取角色类型，用户信息来源数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("1001");
			String dicJson = JSON.toJSONString(dicList);
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicJson", dicJson);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 获取所有本用户所创建的角色
	 * @return
	 *      角色权限管理页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getAllMyRole")
	@Description(moduleName="角色权限管理",operateType="1", operationName="查询角色信息")
	public String getAllMyRole(HttpServletRequest request, HttpServletResponse response) {
		PageResult pageResult = null;
		try {
			//获取用户信息
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			//获取参数
			String roleName = request.getParameter("roleName");//角色名称
			String roleType = request.getParameter("roleType");//角色类型
			int pageNo = Integer.parseInt(request.getParameter("pageNo"));
			
			//获取分页结果
			pageResult = roleService.getAllMyRole(user.getLoginName(), user.getRoleId(), user.getSystemNo(), user.getPosition(), roleName, roleType, pageNo, 10);
			request.setAttribute("pageResult", pageResult);
			
			//获取角色类型，用户信息来源数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("1001,1002");
			request.setAttribute("dicList", dicList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			return "systemmanage/role/roleInfoList";
		}
	}
	
	/**
	 * 获取所有启用的功能
	 * @return
	 *      展现系统所有的功能页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getFunctionToJson")
	public String getFunctionToJson(HttpServletRequest request, HttpServletResponse response) {
		String result = "[]";
		try {
			//获取所有启用的功能
			List<FunctionTree> funList = roleService.getFunctions();
			//转换成json格式
			result = JSON.toJSONString(funList);
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally{
			//设置值
			request.setAttribute("funList", result);
			//返回
			return "systemmanage/role/showFunctionList";
		}
	}
	
	/**
	 * 删除角色信息
	 * @return
	 *      0:删除失败！
	 * 		1:删除成功！
	 */
	@RequestMapping("/deleteRole")
	@Description(moduleName="角色权限管理",operateType="4", operationName="删除角色信息")
	public void deleteRole(HttpServletRequest request, HttpServletResponse response){
		String result = "1";
		try {
			String id = request.getParameter("id");//信息编号
			if(id != null && !"".equals(id.trim())){
				roleService.deleteRole(id.trim());
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
	 * 批量删除角色信息
	 * @return
	 *      0:删除失败！
	 * 		1:删除成功！
	 */
	@RequestMapping("/batcheDeleteRole")
	@Description(moduleName="角色权限管理",operateType="4", operationName="批量删除角色信息")
	public void batcheDeleteRole(HttpServletRequest request, HttpServletResponse response){
		String result = "1";
		try {
			String[] ids = request.getParameterValues("ids");//信息编号
			if(ids != null && ids.length > 0){
				StringBuilder sb = new StringBuilder();
				for(int i=0;i < ids.length;i++){
					sb.append(ids[i]).append(",");
				}
				roleService.deleteRole(sb.substring(0, sb.length()-1));
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
	 * 获取该用户所有的所有启用的功能
	 * @return
	 *      新增角色页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getFunctionsOfUserToJsonForAdd")
	public String getFunctionsOfUserToJson(HttpServletRequest request, HttpServletResponse response) {
		String result = "[]";
		StringBuffer sb = new StringBuffer();
		try {
			//获取用户信息
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			//根据角色Id获取角色数据
			Role role = roleService.getRoleById(user.getRoleId());
			if(role != null){
				//获取为1的权限位置
				char permission[] = role.getPermissionContent().toCharArray();
				for (int i = 0;i < permission.length;i++) {
					if (permission[i] == '1') {
						sb.append(i+1).append(",");//权限为从1开始
					}
				}
				
				//根据获得的功能权限位查询出该用户所具体的功能
				List<FunctionTree> allFun = roleService.getFunction("1,2", sb.substring(0, sb.length() - 1), "1");
				//转换成json格式
				result = JSON.toJSONString(allFun);
			}
			
			//获取角色类型，用户信息来源数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("1001");
			request.setAttribute("dicList", dicList);
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally{
			//设置值
			request.setAttribute("funList", result);
			//返回
			return "systemmanage/role/addRole";
		}
	}
	
	/**
	 * 新增角色信息
	 * @return
	 *      0:新增失败！
	 * 		1:新增成功！
	 */
	@RequestMapping("/addRole")
	@Description(moduleName="角色权限管理",operateType="2", operationName="新增角色信息")
	public void addRole(HttpServletRequest request, HttpServletResponse response){
		String result = "1";
		StringBuffer sb = new StringBuffer();
		try {
			//获取用户信息
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			//获取数据
			String roleName = request.getParameter("roleName");//角色名称
			String roleType = request.getParameter("roleType");//角色类型
			String selectFun = request.getParameter("selectFun");//功能权限位
			//创建权限位字符串1到630
			for(int i=1;i <= 630;i++){
				if(selectFun.contains("|" + i + "|")){//如果包含有，则为1，否则该权限位为0
					sb.append("1");
				} else{
					sb.append("0");
				}
			}
			
			//封装数据
			Role role = new Role();
			role.setRoleName(roleName);
			role.setRoleType(roleType);
			role.setParentId(user.getRoleId());
			role.setPermissionContent(sb.toString());
			role.setBuildPno(user.getLoginName());
			role.setBuildName(user.getUserName());
			role.setBuildTime(new Date());
			role.setUpdateTime(new Date());
			
			//执行添加
			roleService.addRole(role);
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
	 * 获取该用户所有的所有启用的功能（修改角色页面）
	 * @return
	 *      修改角色页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getFunctionsOfUserToJsonForEdit")
	public String getFunctionsOfUserToJsonForEdit(HttpServletRequest request, HttpServletResponse response) {
		String result = "[]";
		StringBuffer sb = new StringBuffer();
		try {
			//获取用户信息
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			//根据角色Id获取角色数据
			Role role = roleService.getRoleById(user.getRoleId());
			if(role != null){
				//获取为1的权限位置
				char permission[] = role.getPermissionContent().toCharArray();
				for (int i = 0;i < permission.length;i++) {
					if (permission[i] == '1') {
						sb.append(i+1).append(",");//权限为从1开始
					}
				}
				
				//根据获得的功能权限位查询出该用户所具体的功能
				List<FunctionTree> allFun = roleService.getFunction("1,2", sb.substring(0, sb.length() - 1), "1");
				//转换成json格式
				result = JSON.toJSONString(allFun);
			}
			
			//获取角色类型，用户信息来源数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("1001");
			request.setAttribute("dicList", dicList);
			
			//根据需要编辑的角色Id，获取角色信息
			String roleId = request.getParameter("id");//角色Id
			role = roleService.getRoleById(Integer.parseInt(roleId));
			request.setAttribute("roleObj", role);
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally{
			//设置值
			request.setAttribute("funList", result);
			//返回
			return "systemmanage/role/updateRole";
		}
	}
	
	/**
	 * 修改角色信息
	 * @return
	 *      0:修改失败！
	 * 		1:修改成功！
	 */
	@RequestMapping("/updateRole")
	@Description(moduleName="角色权限管理",operateType="3", operationName="修改角色信息")
	public void updateRole(HttpServletRequest request, HttpServletResponse response){
		String result = "1";
		StringBuffer sb = new StringBuffer();
		try {
			//获取数据
			String id = request.getParameter("id");//角色ID
			String roleName = request.getParameter("roleName");//角色名称
			String roleType = request.getParameter("roleType");//角色类型
			String selectFun = request.getParameter("selectFun");//功能权限位
			//创建权限位字符串1到630
			for(int i=1;i <= 630;i++){
				if(selectFun.contains("|" + i + "|")){//如果包含有，则为1，否则该权限位为0
					sb.append("1");
				} else{
					sb.append("0");
				}
			}
			
			//执行添加
			roleService.updateRole(id, roleName, roleType, sb.toString());
			
			//更新用户信息表中的角色名称
			roleService.updateRoleToUser(id, roleName, roleType);
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
	 * 获取该用户所有的所有启用的功能（角色详情页面）
	 * @return
	 *      角色详情页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getFunctionsOfUserToJsonForDetail")
	public String getFunctionsOfUserToJsonForDetail(HttpServletRequest request, HttpServletResponse response) {
		String result = "[]";
		try {
			//获取所有启用的功能
			List<FunctionTree> funList = roleService.getFunctions();
			//转换成json格式
			result = JSON.toJSONString(funList);
			
			//根据需要编辑的角色Id，获取角色信息
			String roleId = request.getParameter("id");//角色Id
			Role role = roleService.getRoleById(Integer.parseInt(roleId));
			request.setAttribute("roleObj", role);
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally{
			//设置值
			request.setAttribute("funList", result);
			//返回
			return "systemmanage/role/roleDetail";
		}
	}
}
