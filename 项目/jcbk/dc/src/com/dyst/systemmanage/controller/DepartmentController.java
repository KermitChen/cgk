package com.dyst.systemmanage.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.base.utils.PageResult;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.Department;
import com.dyst.systemmanage.entities.DeptAndUserNode;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.DepartmentService;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.StaticUtils;

/**
 * @author： cgk
 * @date：2016-04-02
 * @version：0.0.1
 * @doc：部门信息管理Controller类，主要实现方法：
 *       1.初始化部门信息管理页面
 *       2.分页查询部门信息
 *       3.删除部门信息
 *       4.批量删除部门信息
 */
@Controller
@RequestMapping(value="/dept")
public class DepartmentController {

	//注入service
	private DepartmentService deptService;
	public DepartmentService getDeptService() {
		return deptService;
	}
	@Autowired
	public void setDeptService(DepartmentService deptService) {
		this.deptService = deptService;
	}
	
	//注入业务层
	@Resource
	private UserService userService;
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 初始化部门信息管理页面
	 * @return
	 *      部门信息管理页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initDeptManage")
	public String initDeptManage(HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/department/deptManage";
		try {
			//获取角色类型，用户信息来源数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("1002");
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
	 * 分页查询部门信息
	 * @return
	 *      部门信息管理页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getDeptForPage")
	@Description(moduleName="部门信息管理", operateType="1", operationName="查询部门信息")
	public String getDeptForPage(HttpServletRequest request, HttpServletResponse response) {
		PageResult pageResult = null;
		try {
			//获取参数
			String deptNo = request.getParameter("deptNo");
			String deptName = request.getParameter("deptName");
			String infoSource = request.getParameter("infoSource");
			String jxkh = request.getParameter("jxkh");
			int pageNo = Integer.parseInt(request.getParameter("pageNo"));
			
			//获取分页结果
			pageResult = deptService.getDeptForPage(deptNo, deptName, infoSource, jxkh, pageNo, 10);
			request.setAttribute("pageResult", pageResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			return "systemmanage/department/deptInfoList";
		}
	}
	
	/**
	 * 获取所有的部门信息，并转换成json格式，用于创建部门树
	 * @return
	 * 		json格式的部门信息，如果失败，则返回空字符串
	 */
	@RequestMapping("/getAllDeptToJson")
	public void getAllDeptToJson(HttpServletRequest request, HttpServletResponse response){
		String result = "[]";
		try {
			//获取所有的部门信息
			List<Department> deptList = deptService.getAllDept();
			//转换成json格式
			result = JSON.toJSONString(deptList);
		} catch (Exception e) {
			result = "[]";
			e.printStackTrace();
		} finally{
			//封装数据
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(result);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除部门信息
	 * @return
	 *      0:删除失败！
	 * 		1:删除成功！
	 */
	@RequestMapping("/deleteDept")
	@Description(moduleName="部门信息管理", operateType="4", operationName="删除部门信息")
	public void deleteDept(HttpServletRequest request, HttpServletResponse response){
		String result = "1";
		try {
			String deptNo = request.getParameter("deptNo");//信息编号
			if(deptNo != null && !"".equals(deptNo.trim())){
				//执行删除
				deptService.deleteDept(deptNo);
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
	 * 批量删除部门信息
	 * @return
	 *      0:删除失败！
	 * 		1:删除成功！
	 */
	@RequestMapping("/batcheDeleteDept")
	@Description(moduleName="部门信息管理", operateType="4",  operationName="批量删除部门信息")
	public void batcheDeleteDept(HttpServletRequest request, HttpServletResponse response){
		String result = "1";
		try {
			String[] deptNos = request.getParameterValues("deptNos");//信息编号
			if(deptNos != null && deptNos.length > 0){
				//连接每个部门的子孙部门，并连接每个部门
				for(int i=0;i < deptNos.length;i++){
					deptService.deleteDept(deptNos[i]);
				}
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
	
//	/**
//	 * 获取子部门编号
//	 * @param deptNo  部门编号
//	 * @param depts  部门集合
//	 * @return id1,id2,id3,……
//	 */
//	private String getSubDepts(String lsbmid, List<Department> depts){
//		StringBuilder sb = new StringBuilder();
//		for(int i = 0;i < depts.size();i++){//部门循环
//			Department d = depts.get(i);
//			String deptNo = d.getDeptNo();
//			String parentNo = d.getParentNo();
//			if(lsbmid != null && deptNo != null && parentNo != null && lsbmid.trim().equals(parentNo.trim()) && !(deptNo.trim()).equals(parentNo.trim())){
//				//连接该节点
//				sb.append("'").append(deptNo.trim()).append("',");
//				
//				//获取该节点的子孙节点
//				if(hasChilds(deptNo.trim(), depts)){//如果存在子节点
//					sb.append(getSubDepts(deptNo.trim(), depts));
//				}
//			}
//		}
//		return sb.toString();
//	} 
	
//	/**
//	 * 判断某一个部门id是否具有子部门
//	 * @param deptNo  部门编号
//	 * @param depts  部门集合
//	 * @return
//	 */
//	private boolean hasChilds(String deptNo, List<Department> depts){
//		for(int i = 0;i < depts.size();i++){//部门循环
//			Department dept = depts.get(i);
//			if(deptNo != null && dept.getParentNo() != null && deptNo.trim().equals(dept.getParentNo().trim())){
//				return true;
//			}
//		}
//		return false;
//	}
	
	/**
	 * 获取所有的部门信息，并转换成json格式，用于创建部门树
	 * @return
	 * 		json格式的部门信息，如果失败，则返回空字符串
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getDeptToJson")
	public String getDeptToJson(HttpServletRequest request, HttpServletResponse response){
		String result = "[]";
		try {
			//获取所有的部门信息
			List<Department> deptList = deptService.getAllDept();
			//转换成json格式
			result = JSON.toJSONString(deptList);
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally{
			//设置值
			request.setAttribute("deptList", result);
			//返回
			return "systemmanage/department/deptTree";
		}
	}
	
	/**
	 * 加载顶级部门
	 * @return
	 * 		json格式的部门信息，如果失败，则返回空字符串
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getMaxDeptToJson")
	public String getMaxDeptToJson(HttpServletRequest request, HttpServletResponse response){
		String result = "[]";
		try {
			//获取所有的部门信息
			List<Department> deptList = deptService.getMaxDept();
			
			DeptAndUserNode deptAndUserNode = null;
			List<DeptAndUserNode> deptAndUserNodeList = new ArrayList<DeptAndUserNode>();
			for(int i=0;i < deptList.size();i++){
				Department dept = deptList.get(i);
				deptAndUserNode = new DeptAndUserNode(dept.getId(), dept.getDeptNo(), dept.getDeptName(), dept.getParentNo(), (dept.getSystemNo() != null ?dept.getSystemNo():""), "./common/images/department.png", true, dept.getInfoSource());
				deptAndUserNodeList.add(deptAndUserNode);
			}
			
			//转换成json格式
			result = JSON.toJSONString(deptAndUserNodeList);
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally {
			//设置值
			request.setAttribute("MaxDeptToJson", result);
			//返回
			return "systemmanage/department/deptTree";
		}
	}
	
	/**
	 * 加载所有部门
	 * @return
	 * 		json格式的部门信息，如果失败，则返回空字符串
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getMaxDeptToJson2")
	public String getMaxDeptToJson2(HttpServletRequest request, HttpServletResponse response){
		String result = "[]";
		try {
			//一次获取所有的部门，放弃异步
			List<Department> deptList = deptService.getAllDept2();
			
			DeptAndUserNode deptAndUserNode = null;
			List<DeptAndUserNode> deptAndUserNodeList = new ArrayList<DeptAndUserNode>();
			for(int i=0;i < deptList.size();i++){
				Department dept = deptList.get(i);
				deptAndUserNode = new DeptAndUserNode(dept.getId(), dept.getDeptNo(), dept.getDeptName(), dept.getParentNo(), (dept.getSystemNo() != null ?dept.getSystemNo():""), "./common/images/department.png", true, dept.getInfoSource());
				deptAndUserNodeList.add(deptAndUserNode);
			}
			
			//转换成json格式
			result = JSON.toJSONString(deptAndUserNodeList);
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally{
			//设置值
			request.setAttribute("MaxDeptToJson", result);
			//返回
			return "systemmanage/department/deptTree2";
		}
	}
	
	/**
	 * 加载顶级部门
	 * @return
	 * 		json格式的部门信息，如果失败，则返回空字符串
	 */
	@RequestMapping("/getMaxDeptToJsonWriter")
	public void getMaxDeptToJsonWriter(HttpServletRequest request, HttpServletResponse response){
		String result = "[]";
		try {
			//获取所有的部门信息
			List<Department> deptList = deptService.getMaxDept();
			
			DeptAndUserNode deptAndUserNode = null;
			List<DeptAndUserNode> deptAndUserNodeList = new ArrayList<DeptAndUserNode>();
			for(int i=0;i < deptList.size();i++){
				Department dept = deptList.get(i);
				deptAndUserNode = new DeptAndUserNode(dept.getId(), dept.getDeptNo(), dept.getDeptName(), dept.getParentNo(), (dept.getSystemNo() != null ?dept.getSystemNo():""), "./common/images/department.png", true, dept.getInfoSource());
				deptAndUserNodeList.add(deptAndUserNode);
			}
			
			//转换成json格式
			result = JSON.toJSONString(deptAndUserNodeList);
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally{
			//封装数据
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(result);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据部门编号加载子部门
	 * @return
	 * 		json格式的部门信息，如果失败，则返回空字符串
	 */
	@RequestMapping("/getDeptByParentNoToJson")
	public void getDeptByParentNoToJson(HttpServletRequest request, HttpServletResponse response){
		String result = "[]";
		try {
			//获取父节点编号
			String deptNo = request.getParameter("nodeNo");//信息编号
			//根据部门编号加载子部门
			List<Department> deptList = deptService.getDeptByParentNo(deptNo);
			
			DeptAndUserNode deptAndUserNode = null;
			List<DeptAndUserNode> deptAndUserNodeList = new ArrayList<DeptAndUserNode>();
			for(int i=0;i < deptList.size();i++){
				Department dept = deptList.get(i);
				deptAndUserNode = new DeptAndUserNode(dept.getId(), dept.getDeptNo(), dept.getDeptName(), dept.getParentNo(), (dept.getSystemNo() != null ?dept.getSystemNo():""), "./common/images/department.png", true, dept.getInfoSource());
				deptAndUserNodeList.add(deptAndUserNode);
			}
			
			//转换成json格式
			result = JSON.toJSONString(deptAndUserNodeList);
		} catch (Exception e) {
			result = "[]";
			e.printStackTrace();
		} finally{
			//封装数据
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(result);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 初始化新增部门信息页面
	 * @return
	 *      新增部门信息页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initAddDept")
	public String initAddDept(HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/department/addDept";
		try {
			//传递父级，不一定有，没有则不选
			request.setAttribute("parentDeptNo", request.getParameter("pNo"));
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 新增部门信息
	 * @return
	 *      部门信息管理页面
	 */
	@RequestMapping("/addDept")
	@Description(moduleName="部门信息管理", operateType="2",  operationName="新增部门信息")
	public void addDept(HttpServletRequest request, HttpServletResponse response) {
		String result = "1";
		List<Department> deptList = null;
		try {
			//获取用户信息
			User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取数据
			String deptNo = request.getParameter("deptNo");//部门编号
			String deptName = request.getParameter("deptName");//部门名称
			String telphone = request.getParameter("telphone");//联系电话
			String parentId = request.getParameter("parentId");//上级部门
			String parentName = request.getParameter("parentName");//上级部门名称
			String jxkh = request.getParameter("jxkh");//绩效考核
			String remark = request.getParameter("remark");//备注
			
			//产生系统自身的部门编码
			String systemNo = deptService.getDeptNo(parentId);
			
			//检查部门编号
			if(deptNo != null && !"".equals(deptNo)){
				//检查部门编号是否存在
				deptList = deptService.getDeptByDeptNo(deptNo);
				if(deptList != null && deptList.size() > 0) {
					result = "2";
				}
			} else {//自动产生部门编号
				deptNo = systemNo;
			}
			
			//判断是否继续
			if("1".equals(result)){
				//检查部门名称是否存在
				deptList = deptService.getDeptByDeptName(deptName);
				if(deptList != null && deptList.size() > 0) {
					result = "3";
				} else {
					//封装
					Department dept = new Department();
					dept.setDeptNo(deptNo);
					dept.setDeptName(deptName);
					dept.setDeptTelephone(telphone);
					dept.setParentNo(parentId);
					dept.setParentName(parentName);
					dept.setBuildPno(operUser.getLoginName());
					dept.setBuildName(operUser.getUserName());
					dept.setBuildTime(new Date());
					dept.setUpdateTime(new Date());
					dept.setInfoSource("1");
					dept.setJxkh(jxkh);
					dept.setSystemNo(systemNo);
					dept.setRemark(remark);
					
					//保存
					deptService.addDept(dept);
				}
			}
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
	 * 初始化更新部门信息页面
	 * @return
	 *      更新部门信息页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initUpdateDept")
	public String initUpdateDept(HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/department/updateDept";
		try {
			//根据ID，获取部门信息
			int id = Integer.parseInt(request.getParameter("id"));//部门ID
			Department dept = deptService.getDept(id);
			request.setAttribute("deptObj", dept);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 修改部门信息
	 * @return
	 *      部门信息管理页面
	 */
	@RequestMapping("/updateDept")
	@Description(moduleName="部门信息管理", operateType="3",  operationName="修改部门信息")
	public void updateDept(HttpServletRequest request, HttpServletResponse response) {
		String result = "1";
		try {
			//获取数据
			String deptNo = request.getParameter("deptNo");//部门编号
			String deptName = request.getParameter("deptName");//部门名称
			String telphone = request.getParameter("telphone");//联系电话
			String parentId = request.getParameter("parentId");//上级部门
			String parentName = request.getParameter("parentName");//上级部门名称
			String jxkh = request.getParameter("jxkh");//绩效考核
			String remark = request.getParameter("remark");//备注
			
			//检查部门名称是否存在
			if(deptService.isDeptNameExist(deptNo, deptName)) {
				result = "2";
			} else {
				//根据ID，获取部门信息
				int id = Integer.parseInt(request.getParameter("id"));//部门ID
				Department dept = deptService.getDept(id);
				
				//更改信息
				dept.setDeptName(deptName);
				dept.setDeptTelephone(telphone);
				dept.setParentNo(parentId);
				dept.setParentName(parentName);
				dept.setUpdateTime(new Date());
				dept.setJxkh(jxkh);
				dept.setRemark(remark);
				
				//保存
				deptService.updateDept(dept);
				
				//更新用户信息表里的部门名称
				deptService.updateDeptToUser(deptNo, deptName);
			}
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
	 * 显示部门信息详情页面
	 * @return
	 *      显示部门信息详情页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/deptDetail")
	@Description(moduleName="部门信息管理", operateType="1", operationName="查看部门信息详情")
	public String deptDetail(HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/department/deptDetail";
		try {
			//获取角色类型，用户信息来源数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("1002");
			String dicJson = JSON.toJSONString(dicList);
			request.setAttribute("dicJson", dicJson);
			
			//根据ID，获取部门信息
			int id = Integer.parseInt(request.getParameter("id"));//部门ID
			Department dept = deptService.getDept(id);
			request.setAttribute("deptObj", dept);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
}