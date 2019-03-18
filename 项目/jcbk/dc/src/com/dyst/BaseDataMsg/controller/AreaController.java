package com.dyst.BaseDataMsg.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.dyst.BaseDataMsg.entities.Area;
import com.dyst.BaseDataMsg.service.AreaService;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.log.annotation.Description;
import com.dyst.utils.CommonUtils;

@Controller
@RequestMapping("/area")
public class AreaController {

	//注入业务层
	@Autowired
	private AreaService areaService;
	
	//城区首页查询
	@RequestMapping(value="findArea")
	@Description(moduleName="城区信息管理",operateType="1",operationName="分页查询城区信息")
	public String findArea(Model model,Area area,PageResult pageResult,String Check_areaName,String Check_areaNo){
		QueryHelper queryHelper = new QueryHelper(Area.class,"a");
		/*
		 * 1.查询删除标志不为1的数据
		 * 2.利用区域名称模糊查询
		 * 3.利用区域代码精确查询
		 */
		queryHelper.addCondition("a.deleteFlag != ?", "1");
		String areaName = Check_areaName;
		String areaNo = Check_areaNo;
		if(areaName!=null&& areaName.length()>=1){
			queryHelper.addCondition("a.areaname like ?", "%"+areaName+"%");
		}
		if(areaNo!= null && areaNo.length()>=1){
			queryHelper.addCondition("a.areano = ?", areaNo);
		}
		queryHelper.addOrderByProperty("a.id", QueryHelper.ORDER_BY_DESC);
		pageResult = areaService.getPageResult(queryHelper, pageResult.getPageNo(), 10);
		/*
		 * 1.利用model把页面中需要的东西返回到页面上
		 */
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("area",area);
		model.addAttribute("Check_areaName",Check_areaName);
		model.addAttribute("Check_areaNo",Check_areaNo);
		return "/baseDataMsg/area/listUI";
	}
	
	//根据ID删除城区信息
	@RequestMapping(value="deleteArea")
	@Description(moduleName="城区信息管理",operateType="4",operationName="删除城区信息")
	public String deleteArea(Area area,Model model,HttpServletRequest req){
		String id = CommonUtils.keyWordFilter(req.getParameter("id"));
		areaService.deleteArea(Integer.parseInt(id));
		model.addAttribute("area", area);
		return "redirect:/area/findArea.do";
	}
	
	//根据IDS批量删除城区信息
	@RequestMapping(value="deleteAreas")
	@Description(moduleName="城区信息管理",operateType="4",operationName="批量删除城区信息")
	public String batchesDeleteAreas(int[] ids ,Area area,Model model){
		for(int id:ids){
			areaService.deleteArea(id);
		}
		model.addAttribute("area", area);
		return "redirect:/area/findArea.do";
	}
	
	//跳转到添加城区信息页面
	@RequestMapping(value="addAreaUI")
	public String addAreaUI(){
		return "/baseDataMsg/area/addUI";
	}
	
	//添加城区信息
	@RequestMapping(value="addArea")
	@Description(moduleName="城区信息管理",operateType="2",operationName="新增城区信息")
	public String addArea(Model model,Area area){
		//设置删除标志为未删除
		area.setDeleteFlag("0");
		areaService.addArea(area);
		return "redirect:/area/findArea.do";
	}
	
	//跳转到编辑页面
	@RequestMapping(value="editAreaUI")
	@Description(moduleName="城区信息管理",operateType="1",operationName="查看城区信息")
	public String editAreaUI(Model model,String Check_areaName,String Check_areaNo,String pageNo,HttpServletRequest req){
		/*
		 * 1.根据ID查询出 area信息,回显到editUI页面中
		 */
		String id =CommonUtils.keyWordFilter(req.getParameter("id"));
		Area area = areaService.findAreaById(Integer.parseInt(id));
		area.setDeleteFlag("0");
		model.addAttribute("area", area);
		/*
		 * 2.编辑页面要保存ListUI页面中的一些数据
		 * 		*页号 pageNo
		 * 		*查询的城区名称 Check_areaName
		 * 		*查询的城区代码Check_areaNo
		 * 		
		 */
		model.addAttribute("Check_areaName", Check_areaName);
		model.addAttribute("Check_areaNo", Check_areaNo);
		model.addAttribute("pageNo", pageNo);
		return "/baseDataMsg/area/editUI";
	}
	
	//保存编辑信息
	@RequestMapping(value="editArea")
	@Description(moduleName="城区信息管理",operateType="3",operationName="编辑城区信息")
	public String editArea(Model model,Area area,String Check_areaName,String Check_areaNo,String pageNo){
		area.setDeleteFlag("0");
		areaService.updateArea(area);
		/*
		 * 1.把最初的条件传递回去
		 */
		model.addAttribute("Check_areaName", Check_areaName);
		model.addAttribute("Check_areaNo", Check_areaNo);
		model.addAttribute("pageNo", pageNo);
		return "forward:/area/findArea.do" ;
	}
}