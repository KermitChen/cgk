package com.dyst.mapTrail.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.log.annotation.Description;
import com.dyst.mapTrail.entities.MapTrailPoint;
import com.dyst.mapTrail.entities.MapTrailPointKD;
import com.dyst.mapTrail.service.MapService;

@Controller
@RequestMapping("/mapTrail")
public class MapController {
	@Autowired
	private MapService mapService;
	@Autowired
	private JcdService jcdService;
	
	
	/**
	 * 查询2个监测点的路径
	 */
	@RequestMapping("/selectMapTrail")
	@Description(moduleName="地图服务",operateType="1",operationName="查询两点路径")
	public void selectMapTrail(HttpServletRequest request, HttpServletResponse response,String start,String end){
		MapTrailPoint mapTrailPoint = null;
		try {
			if(!start.equals(end)){
				MapTrailPoint mapTrailPoint1 = mapService.findMapTrailById(start, end);
				MapTrailPoint mapTrailPoint2 = mapService.findMapTrailById(end, start);
				if(mapTrailPoint1 == null){
					mapTrailPoint = mapTrailPoint2;
				} else if(mapTrailPoint2 == null){
					mapTrailPoint = mapTrailPoint1;
				} else{
					if(Integer.valueOf(mapTrailPoint1.getLen()) < Integer.valueOf(mapTrailPoint2.getLen())){
						mapTrailPoint = mapTrailPoint1;
					} else {
						mapTrailPoint = mapTrailPoint2;
					}
				}
				response.getWriter().write(JSON.toJSONString(mapTrailPoint!=null?mapTrailPoint:"null"));
//				List<Jcd> list = new ArrayList<Jcd>();
//				list.add(jcdService.findJcdById(start));
//				list.add(jcdService.findJcdById(end));
//				response.getWriter().write(JSON.toJSONString(list));
			} else{
				String n = "null";
				response.getWriter().write(JSON.toJSONString(n));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 查询单个监测点
	 */
	@RequestMapping("/selectJcd")
	public void selectJcd(HttpServletRequest request, HttpServletResponse response,String id){
		try {	
			if(!StringUtils.isEmpty(id)){
				Jcd jcd = jcdService.findJcdById(id);
				response.getWriter().write(JSON.toJSONString(jcd));
			}else{
				String n = "null";
				response.getWriter().write(JSON.toJSONString(n));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 查询多个监测点
	 */
	@RequestMapping("/selectJcds")
	public void selectJcds(HttpServletRequest request, HttpServletResponse response,String id){
		try {	
			if(!StringUtils.isEmpty(id)){
				List<Jcd> jcds = new ArrayList<Jcd>();
				if(id.endsWith(",")){
					id = id.substring(0, id.length());
				}
				String[] ids = id.split(",");
				for(String jcdid : ids){
					jcds.add(jcdService.findJcdById(jcdid));
				}
				response.getWriter().write(JSON.toJSONString(jcds));
			}else{
				String n = "null";
				response.getWriter().write(JSON.toJSONString(n));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 查询卡点-监测点的路径
	 */
	@RequestMapping("/selectMapTrailKD")
	@Description(moduleName="地图服务",operateType="1",operationName="查询卡点-监测点的路径")
	public void selectMapTrailKD(HttpServletRequest request, HttpServletResponse response,String start,String end){
		MapTrailPointKD mapTrailPoint1 = new MapTrailPointKD();
		try {	
			if(start != null && end != null && !start.trim().equals(end.trim())){
				mapTrailPoint1 = mapService.findMapTrailKDById(start, end);
				//为空，则创建一个对象，页面需判断是否为对象是否有数据
				if(mapTrailPoint1 == null){
					mapTrailPoint1 = new MapTrailPointKD();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.getWriter().write(JSON.toJSONString(mapTrailPoint1));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
