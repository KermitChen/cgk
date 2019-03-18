package com.dyst.BaseDataMsg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Hmd;
import com.dyst.BaseDataMsg.service.DictionaryService;
import com.dyst.BaseDataMsg.service.HmdService;
import com.dyst.base.utils.GetKeyByValue;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.CommonUtils;

@Controller
@RequestMapping(value="/hmd")
public class HmdController {

	//注入hmdService、dictionaryService
	private HmdService hmdService;
	private DictionaryService dicService;
	
	public DictionaryService getDicService() {
		return dicService;
	}
	@Autowired
	public void setDicService(DictionaryService dicService) {
		this.dicService = dicService;
	}
	public HmdService getHmdService() {
		return hmdService;
	}
	@Autowired
	public void setHmdService(HmdService hmdService) {
		this.hmdService = hmdService;
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

	@RequestMapping(value="/findHmd")
	@Description(moduleName="红名单管理",operateType="1",operationName="分页查询红名单")
	public String findHmd(Model model,String Check_cpNo,String Check_cllx,String Check_clsyr,
			String Check_startTime,String Check_endTime,String Check_jlzt,PageResult pageResult){
		//加载车辆类型
		String hql = "select distinct(d.typeDesc)from Dictionary d ";
		List<Object> list = dicService.findObjects(hql, null);
		model.addAttribute("cllxlist", list);
		//加载记录状态
		model.addAttribute("stateMap", Hmd.HMD_STATE_MAP);
		//查询记录信息
		QueryHelper queryHelper = new QueryHelper(Hmd.class, "h");
		if(Check_cpNo!=null&&Check_cpNo.length()>=1){
			queryHelper.addCondition("h.cph like ", "%"+Check_cpNo+"%");
		}
		if(Check_cllx!=null&&Check_cllx.length()>=1){
			queryHelper.addCondition("h.cplx = ?", Check_cllx);
		}
		if(Check_clsyr!=null&&Check_clsyr.length()>=1){
			queryHelper.addCondition("h.syz like ", "%"+Check_clsyr+"%");
		}
		//把String的时间转化成 时间类型
		try {
			if(Check_startTime!=null&&Check_startTime.length()>=1){
				queryHelper.addCondition("h.kssj >= ?", DateUtils.parseDate(Check_startTime,"yyyy-MM-dd HH:mm"));
			}
			if(Check_endTime!=null&&Check_endTime.length()>=1){
				queryHelper.addCondition("h.cssj <= ?", DateUtils.parseDate(Check_endTime,"yyyy-MM-dd HH:mm"));
			}
		} catch (Exception e) {
			
		}
		if(StringUtils.isNotBlank(Check_jlzt)){
			queryHelper.addCondition("h.zt = ?", GetKeyByValue.getKeyByValue(Hmd.HMD_STATE_MAP,Check_jlzt));
		}
		pageResult = hmdService.getPageResult(queryHelper, pageResult.getPageNo(), 10);
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("Check_cpNo", Check_cpNo);
		model.addAttribute("Check_cllx", Check_cllx);
		model.addAttribute("Check_clsyr", Check_clsyr);
		model.addAttribute("Check_startTime", Check_startTime);
		model.addAttribute("Check_endTime", Check_endTime);
		model.addAttribute("Check_jlzt", Check_jlzt);
		return "/baseDataMsg/hmd/listUI";
	}
	
	//异步加载车辆详细信息
	@RequestMapping(value="/getDetail")
	@Description(moduleName="红名单管理",operateType="1",operationName="查看红名单")
	public String getDetail(HttpServletRequest req,Model model){
		//获取传过来的id,根据id查询车辆的详细信息
		String id = CommonUtils.keyWordFilter(req.getParameter("id"));
		Hmd hmdDetail = hmdService.getHmdById(Integer.parseInt(id));
		model.addAttribute("hmdDetail", hmdDetail);
		return "/baseDataMsg/hmd/hmdDetail";
	}
	
	//加载监测点页面
	@RequestMapping(value="/getJcd")
	public String getJcd(HttpServletRequest request, Model model){
		//获取角色类型，用户信息来源数据字典
		List<Dictionary> dicList = new ArrayList<Dictionary>();
		try {
			dicList = userService.getDictionarysByTypeCode("JCDLX");
			request.setAttribute("dicList", dicList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/baseDataMsg/jcd/jcdChoose";
	}
		
	//跳转到新增页面
	@RequestMapping(value="/toAddUI")
	public String toAddUI(){
		return "/baseDataMsg/jcd/addUI";
	}
}