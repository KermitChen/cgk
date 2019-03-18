package com.dyst.BaseDataMsg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dyst.BaseDataMsg.entities.Area;
import com.dyst.BaseDataMsg.entities.Road;
import com.dyst.BaseDataMsg.service.AreaService;
import com.dyst.BaseDataMsg.service.RoadService;
import com.dyst.base.utils.PageResult;
import com.dyst.log.annotation.Description;
import com.dyst.utils.CommonUtils;

@Controller
@RequestMapping("/road")
public class RoadController {

	//注入道路..业务层
	@Autowired
	private RoadService roadService;
	//注入城区..业务层
	@Autowired
	private AreaService areaService;
	
	/**
	 * 道路首页查询
	 * @param model
	 * @param roadName
	 * @param cityName
	 * @param pageResult
	 * @return
	 */
	@RequestMapping("/findRoad")
	@Description(moduleName="道路信息管理",operateType="1",operationName="查询道路信息")
	public String findRoad(Model model,String Check_roadName,String Check_cityName,PageResult pageResult){
		//查询Road表 Area表 关联查询
		//sql:select * from BASE_DATAMSG_ROAD as r left join BASE_DATAMSG_AREA as a on r.city_Id=a.pk_id where r.road_name like '%路%';\
		StringBuffer hql = new StringBuffer();
		hql.append("FROM Road r left JOIN Area a with r.cityId=a.id WHERE r.deleteFlag != '1' ");
		List<Object > params = new ArrayList<Object>();
		if(Check_roadName!=null&&Check_roadName.length()>=1){
			hql.append(" and r.roadName LIKE ?");
			params.add("%"+Check_roadName+"%");
		}
		if(Check_cityName!=null&&Check_cityName.length()>=1){
			hql.append(" and a.areaname LIKE ?");
			params.add("%"+Check_cityName+"%");
		}
		hql.append(" ORDER BY r.roadNo ASC");
		pageResult = roadService.getPageResult(hql.toString(), params, pageResult.getPageNo(), 10);
		List road = pageResult.getItems();
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("Check_roadName", Check_roadName);
		model.addAttribute("Check_cityName", Check_cityName);
		model.addAttribute("road", road);
		return "/baseDataMsg/road/listUI";
	}
	
	/**
	 * 批量删除操作
	 * @param ids
	 * @param roadName
	 * @param cityName
	 * @param model
	 * @return
	 */
	@RequestMapping(value="deleteRoads")
	@Description(moduleName="道路信息管理",operateType="4",operationName="批量删除道路信息")
	public String batchesDeleteRoads(int[] ids ,String roadName,String cityName,Model model){
		for(int id:ids){
			roadService.deleteRoad(id);
		}
		model.addAttribute("roadName", roadName);
		model.addAttribute("cityName", cityName);
		return "redirect:/road/findRoad.do";
	}
	
	//删除一条记录
	@RequestMapping(value="deleteRoad")
	@Description(moduleName="道路信息管理",operateType="4",operationName="删除道路信息")
	public String deleteRoad(String roadName,String cityName,Model model,HttpServletRequest req){
		String id = CommonUtils.keyWordFilter(req.getParameter("id"));
		roadService.deleteRoad(Integer.parseInt(id));
		model.addAttribute("roadName", roadName);
		model.addAttribute("cityName", cityName);
		return "redirect:/road/findRoad.do";
	}
	
	//跳转到添加页面
	@RequestMapping("/addRoadUI")
	public String addRoadUI(Model model,Road road){
		return "/baseDataMsg/road/addUI";
	}
	
	//保存道路信息
	@RequestMapping("/addRoad")
	@Description(moduleName="道路信息管理",operateType="2",operationName="添加道路信息")
	public String addRoad(Road road,Model model,String cityName) throws Exception{
		road.setDeleteFlag("0");
		//根据城区名称，查询城区Id
		Area area = areaService.findAreaByName(cityName);
		int areaNo = Integer.parseInt(area.getAreano());
		if(area != null){
			road.setCityId(areaNo);
			roadService.addRoad(road);
			return "redirect:/road/findRoad.do";
		}
		return "/baseDataMsg/road/addUI";
	}
	
	//跳转到编辑页面
	@RequestMapping(value="editRoadUI")
	@Description(moduleName="道路信息管理",operateType="1",operationName="查询道路信息")
	public String editRoadUI(Model model,Road road,HttpServletRequest req,String Check_roadName,String Check_areaName,String pageNo){
		//获取道路ID
		String id = CommonUtils.keyWordFilter(req.getParameter("id"));
		road = roadService.findDictionayById(Integer.parseInt(id));
		//获取城区ID,查询城区名称
		int cityId = road.getCityId();
		Area area = areaService.findAreaById(cityId);
		String areaName = area.getAreaname();
		model.addAttribute("road", road);
		model.addAttribute("Check_roadName", Check_roadName);
		model.addAttribute("areaName", areaName);
		model.addAttribute("Check_areaName", Check_areaName);
		model.addAttribute("pageNo", pageNo);
		return "/baseDataMsg/road/editUI";
	}
	
	//保存编辑信息,返回列表页面
	@RequestMapping(value="editRoad")
	@Description(moduleName="道路信息管理",operateType="3",operationName="修改道路信息")
	public String editRoad(Model model,Road road,String Check_roadName,String Check_areaName,String areaName,String pageNo){
		//根据地区名称查询地区的id
		Area area = areaService.findAreaByName(areaName);
		if(area!=null && area.getId() != null){
			road.setCityId(area.getId());
			road.setDeleteFlag("0");
			roadService.updateRoad(road);
		} else {
			String msg = "您输入的城区名称不存在,请核查!";
			model.addAttribute("", msg);
			model.addAttribute("road", road);
			model.addAttribute("Check_roadName", Check_roadName);
			model.addAttribute("Check_areaName", Check_areaName);
			return "/baseDataMsg/road/editUI";
		}
		model.addAttribute("Check_roadName", Check_roadName);
		model.addAttribute("Check_areaName", Check_areaName);
		model.addAttribute("pageNo", pageNo);

		return "forward:/road/findRoad.do";
	}
}