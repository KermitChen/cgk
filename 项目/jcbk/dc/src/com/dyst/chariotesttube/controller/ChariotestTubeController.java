package com.dyst.chariotesttube.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.chariotesttube.entities.Driver;
import com.dyst.chariotesttube.entities.Vehicle;
import com.dyst.chariotesttube.service.ChariotestTubeService;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.IntefaceUtils;
import com.dyst.utils.StaticUtils;

/**
 * @author： cgk
 * @date：2016-04-02
 * @version：0.0.1
 * @doc：车架管信息Controller类，主要实现方法：
 *       1.初始化机动车信息查询页面
 *       2.查询机动车信息
 *       3.初始化驾驶员查询页面
 *       4.查询驾驶员信息
 */
@Controller
@RequestMapping("/cjg")
public class ChariotestTubeController {
	//注入业务层
	@Resource
	private ChariotestTubeService chariotestTubeService;
	public ChariotestTubeService getChariotestTubeService() {
		return chariotestTubeService;
	}
	public void setChariotestTubeService(ChariotestTubeService chariotestTubeService) {
		this.chariotestTubeService = chariotestTubeService;
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
	 * 初始化机动车信息查询页面
	 * @return
	 *      机动车信息查询页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initQueryVehicleInfo")
	public String initQueryVehicleInfo(HttpServletRequest request, HttpServletResponse response) {
		String page = "chariotesttube/vehicle/vehicleQuery";
		try {
			//查询相应字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("CSYS,HPZL,CLZT");
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
	 * 查询机动车信息
	 * @return
	 *      机动车信息显示页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/queryVehicleInfo")
	@Description(moduleName="机动车信息查询",operateType="1",operationName="查询机动车信息")
	public String queryVehicleInfo(HttpServletRequest request, HttpServletResponse response) {
		Vehicle vehicle = null;
		String result = "1";//查询成功
		try {
			//获取用户信息
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			if(user.getIdentityCard() == null || "".equals(user.getIdentityCard().trim())){
				result = "2";//身份证号不能为空
			} else {
				//获取参数
				String hphm = request.getParameter("hphm");
				String hpzl = request.getParameter("hpzl");
				
				//查询数据
				vehicle = IntefaceUtils.queryVehicle(hphm, hpzl, user.getUserName(), user.getIdentityCard(), user.getDeptId());
			}
		} catch (Exception e) {
			result = "0";//查询失败
			e.printStackTrace();
		} finally{
			//返回数据
			request.setAttribute("result", result);
			request.setAttribute("vehicle", vehicle);
			return "chariotesttube/vehicle/showVehicleInfo";
		}
	}
	
	/**
	 * 初始化驾驶员信息查询页面
	 * @return
	 *      驾驶员信息查询页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initQueryDriverInfo")
	public String initQueryDriverInfo(HttpServletRequest request, HttpServletResponse response) {
		String page = "chariotesttube/driver/driverQuery";
		try {
			//查询相应字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("JSZZT,XB");
			String dicJson = JSON.toJSONString(dicList);
			request.setAttribute("dicJson", dicJson);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 查询驾驶员信息
	 * @return
	 *      驾驶员信息显示页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/queryDriverInfo")
	@Description(moduleName="驾驶员信息查询",operateType="1",operationName="查询驾驶员信息")
	public String queryDriverInfo(HttpServletRequest request, HttpServletResponse response) {
		List<Driver> driverList = null;
		String result = "1";//查询成功
		try {
			//获取用户信息
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			if(user.getIdentityCard() == null || "".equals(user.getIdentityCard().trim())){
				result = "2";//身份证号不能为空
			} else {
				//获取参数
				String jszh = request.getParameter("jszh");
				
				//访问接口
				if(jszh != null && !"".equals(jszh.trim())){
					//检验身份证号
					if(CommonUtils.isIdentityCard(jszh)){
						//获取结果
						driverList = IntefaceUtils.queryDriverOfNationwide(jszh, user.getUserName(), user.getIdentityCard(), user.getDeptId());
					} else{
						result = "3";//身份证号不正确
					}
				}
			}
		} catch (Exception e) {
			result = "0";//查询失败
			e.printStackTrace();
		} finally{
			//返回数据
			request.setAttribute("result", result);
			request.setAttribute("driverList", driverList);
			return "chariotesttube/driver/showDriverInfo";
		}
	}
}