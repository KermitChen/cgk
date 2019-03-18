package com.dyst.BaseDataMsg.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Yacs;
import com.dyst.BaseDataMsg.service.DictionaryService;
import com.dyst.BaseDataMsg.service.YaglService;
import com.dyst.base.utils.PageResult;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.log.annotation.Description;
import com.dyst.utils.CommonUtils;

@Controller
@RequestMapping(value="/yagl")
public class YaglController {
	//注入Service
	private YaglService yaglService;
	public YaglService getYaglService() {
		return yaglService;
	}
	@Autowired
	public void setYaglService(YaglService yaglService) {
		this.yaglService = yaglService;
	}
	//注入字典表Service
	private DictionaryService dicService;
	public DictionaryService getDicService() {
		return dicService;
	}
	@Autowired
	public void setDicService(DictionaryService dicService) {
		this.dicService = dicService;
	}
	
	@Autowired
	private DispatchedService dispatchedService;
	
	//显示预案列表
	@RequestMapping(value="/findYa")
	@Description(moduleName="预案参数管理",operateType="1",operationName="查询预案参数")
	public String findYa(Model model,String Check_Yamc,String Check_Yazl,String Check_Yadj,PageResult pageResult){
		//查询出所有的预案种类，返回页面显示
		StringBuffer hql = new StringBuffer();
		hql.append("from Yacs y where 1=1");
		List<Object> params = new ArrayList<Object>();
		if(StringUtils.isNotBlank(Check_Yamc)){
			hql.append(" and y.yamc like ?");
			params.add("%"+Check_Yamc+"%");
		}
		if(StringUtils.isNotBlank(Check_Yazl)){
			hql.append(" and y.yazl = ?");
			params.add(Check_Yazl);
		}
		if(StringUtils.isNotBlank(Check_Yadj)){
			hql.append(" and y.yadj = ?");
			params.add(Check_Yadj);
		}
		hql.append(" order by y.id asc");
		//设置每页默认10条数据
		pageResult = yaglService.getPageResult(hql.toString(), params, pageResult.getPageNo(), 10);
		model.addAttribute("pageResult", pageResult);
		//加载预案种类下拉列表
		String hql1 = "select new map(d.typeCode as typeCode ,d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'BKDL'";
		List<Object> Ya_kinds = dicService.findObjects(hql1, null);
		model.addAttribute("Ya_kinds", Ya_kinds);
		//获取布控类别
		String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID from DIC_DISPATCHED_TYPE d";
		List<Map> bklbList = dispatchedService.findList(sql, null);
		String data = JSON.toJSONString(bklbList);
		model.addAttribute("Ya_lbs", bklbList);
		model.addAttribute("Ya_lbs_json", data);//js接收布控类别json字符串
		//加载dicList信息
		String hql_dicList = " from Dictionary d where d.typeCode in(:ids)";
		Map<String,Object> params_dicList = new HashMap<String, Object>();
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("BKDL1");
		ids.add("BKDL2");
		ids.add("BKDL3");
		params_dicList.put("ids", ids);
		List<Dictionary> list_dicList = dicService.findDicListByTypeCodeIds(hql_dicList, params_dicList);
		model.addAttribute("list_dicList", list_dicList);
		//返回预案名称查询条件
		model.addAttribute("Check_Yamc", Check_Yamc);
		model.addAttribute("Check_Yazl", Check_Yazl);
		model.addAttribute("Check_Yadj", Check_Yadj);
		return "baseDataMsg/yagl/listUI";
	}
	
	//跳转到新增页面
	@RequestMapping(value="/addYaUI")
	public String toAddUI(Model model,String Check_Yazl,String Check_Yadj){
		//加载预案种类下拉列表
		String hql1 = "select new map(d.typeCode as typeCode ,d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'BKDL'";
		List<Object> Ya_kinds = dicService.findObjects(hql1, null);
		model.addAttribute("Ya_kinds", Ya_kinds);
		//加载预案等级下拉列表
		/*String hql2 = "select new map(d.typeCode as typeCode ,d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'BKJB'";
		List<Object> Ya_djs = dicService.findObjects(hql2, null);
		model.addAttribute("Ya_djs", Ya_djs);*/
		//获取布控类别
		String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID from DIC_DISPATCHED_TYPE d";
		List<Map> bklbList = dispatchedService.findList(sql, null);
		String Ya_lbs = JSON.toJSONString(bklbList);
		model.addAttribute("Ya_lbs", Ya_lbs);
		//加载dicList信息
		String hql_dicList = " from Dictionary d where d.typeCode in(:ids)";
		Map<String,Object> params_dicList = new HashMap<String, Object>();
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("BKDL1");
		ids.add("BKDL2");
		ids.add("BKDL3");
		params_dicList.put("ids", ids);
		List<Dictionary> list_dicList = dicService.findDicListByTypeCodeIds(hql_dicList, params_dicList);
		model.addAttribute("list_dicList", list_dicList);
		String list_dicList_json = JSON.toJSONString(list_dicList);
		model.addAttribute("list_dicList_json", list_dicList_json);
		return "/baseDataMsg/yagl/addUI";
	}
	
	//保存新增预案信息
	@RequestMapping(value="/addYa")
	@Description(moduleName="预案参数管理",operateType="2",operationName="新增预案参数")
	public String addYa(Model model,Yacs y){
		y.setYamc(CommonUtils.keyWordFilter(y.getYamc()));
		y.setYams(CommonUtils.keyWordFilter(y.getYams()));
		int flag = yaglService.addYa(y);
		if(flag == 1){//保存成功
			return "redirect:/yagl/findYa.do";
		}else if(flag ==0){
			String message = "数据库中已有该\"预案类别\"、\"报警类型\"组合，新增冲突！";
			model.addAttribute("yacs", y);
			model.addAttribute("Check_Yazl", y.getYazl());
			model.addAttribute("message", message);
			return "forward:/yagl/addYaUI.do";
		}else{
			String message = "添加失败！";
			model.addAttribute("yacs", y);
			model.addAttribute("message", message);
			return "forward:/yagl/addYaUI.do";
		}
	}
	
	//根据传过来的id删除一条记录,重定向到列表页面
	@RequestMapping(value="deleteYa")
	@Description(moduleName="预案参数管理",operateType="4",operationName="删除预案参数")
	public String deleteYa(Model model,String Check_Yamc,String Check_Yazl,
			String Check_Yadj,HttpServletRequest req,String pageNo){
		String id = CommonUtils.keyWordFilter(req.getParameter("id"));
		yaglService.deleteYa(Integer.parseInt(id));
		model.addAttribute("Check_Yamc", Check_Yamc);
		model.addAttribute("Check_Yazl", Check_Yazl);
		model.addAttribute("Check_Yadj", Check_Yadj);
		model.addAttribute("pageNo", pageNo);
		return "redirect:/yagl/findYa.do";
	}
	
	//批量删除预案参数信息
	@RequestMapping(value="deleteYas")
	@Description(moduleName="预案参数管理",operateType="4",operationName="批量删除预案参数")
	public String BatchesDeleteDic(int[] ids ,String Check_Yamc,String Check_Yazl,
			String Check_Yadj,Model model,String pageNo){
		for(int id:ids){
			yaglService.deleteYa(id);
		}
		model.addAttribute("Check_Yamc", Check_Yamc);
		model.addAttribute("Check_Yazl", Check_Yazl);
		model.addAttribute("Check_Yadj", Check_Yadj);
		model.addAttribute("pageNo", pageNo);
		return "redirect:/yagl/findYa.do";
	}
	
	//跳转到编辑页面
	@RequestMapping(value="editYaUI")
	@Description(moduleName="预案参数管理",operateType="1",operationName="查看预案参数")
	public String toEditYaUI(Model model,String pageNo,HttpServletRequest req){
		//根据id查询对应的信息 并回显在编辑页面上
		String id = CommonUtils.keyWordFilter(req.getParameter("id"));
		Yacs yacs = yaglService.findObjectById(Integer.parseInt(id));
		model.addAttribute("yacs", yacs);
		String yacs_json = JSON.toJSONString(yacs);
		model.addAttribute("yacs_json", yacs_json);
		//加载预案种类下拉列表
		String hql1 = "select new map(d.typeCode as typeCode ,d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'BKDL'";
		List<Object> Ya_kinds = dicService.findObjects(hql1, null);
		model.addAttribute("Ya_kinds", Ya_kinds);
		//获取布控类别
		String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID from DIC_DISPATCHED_TYPE d";
		List<Map> bklbList = dispatchedService.findList(sql, null);
		String Ya_lbs = JSON.toJSONString(bklbList);
		model.addAttribute("Ya_lbs", Ya_lbs);
		//加载dicList信息
		String hql_dicList = " from Dictionary d where d.typeCode in(:ids)";
		Map<String,Object> params_dicList = new HashMap<String, Object>();
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("BKDL1");
		ids.add("BKDL2");
		ids.add("BKDL3");
		params_dicList.put("ids", ids);
		List<Dictionary> list_dicList = dicService.findDicListByTypeCodeIds(hql_dicList, params_dicList);
		model.addAttribute("list_dicList", list_dicList);
		String list_dicList_json = JSON.toJSONString(list_dicList);
		model.addAttribute("list_dicList_json", list_dicList_json);
		model.addAttribute("pageNo", pageNo);
		return "baseDataMsg/yagl/editUI";
	}
	
	//保存编辑信息
	@RequestMapping(value="updateYa")
	@Description(moduleName="预案参数管理",operateType="3",operationName="修改预案参数")
	public String doUpdateYa(Yacs y,Model model,String pageNo){
		String yadj = y.getYadj();
		String bjlx = y.getBjlx();
		int flag = 1;
		int updateSuccessFlag = 0;
		String hql = "from Yacs y where 1=1 and y.id !=:id";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", y.getId());
		List<Yacs> yacsList = yaglService.getList(hql, params);
		for(Yacs yacs:yacsList){
			if(yacs.getYadj().equals(yadj)&&yacs.getBjlx().equals(bjlx)){
				flag = 0;
				break;
			}
		}
		if(flag == 1){
			y.setYamc(CommonUtils.keyWordFilter(y.getYamc()));
			y.setYams(CommonUtils.keyWordFilter(y.getYams()));
			updateSuccessFlag = yaglService.udpateYa(y);
		}
		if(updateSuccessFlag == 1){//保存成功
			return "redirect:/yagl/findYa.do";
		}else if(flag ==0){
			String message = "数据库中已有该\"预案类别\"、\"报警类型\"组合，新增冲突！";
			model.addAttribute("yacs", y);
			model.addAttribute("Check_Yazl", y.getYazl());
			model.addAttribute("message", message);
			return "forward:/yagl/editYaUI.do";
		}else{
			String message = "添加失败！";
			model.addAttribute("yacs", y);
			model.addAttribute("message", message);
			return "forward:/yagl/editYaUI.do";
		}
	}
}
