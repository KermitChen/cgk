package com.dyst.serverMonitor.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.controller.DictionaryController;
import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.BaseDataMsg.entities.Road;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.BaseDataMsg.service.RoadService;
import com.dyst.base.utils.PageResult;
import com.dyst.log.annotation.Description;
import com.dyst.serverMonitor.entities.ExcelBeanForJcdStatus;
import com.dyst.serverMonitor.entities.JCDStatus;
import com.dyst.serverMonitor.service.ServerManagerService;

@Controller
@RequestMapping("/deviceStatus")
@RemoteProxy
public class DeviceStatusController extends DictionaryController{
	//注入业务层
	@Autowired
	private JcdService jcdService;
	@Autowired
	private RoadService roadService;
	@Autowired
	private ServerManagerService serverManagerService;
	Map<String, String> road = new HashMap<String, String>();
	
	/**
	 * 跳转到设备状态监控
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/queryDeviceStatus")
	@Description(moduleName="设备状态监控",operateType="1",operationName="查询监测点状态")
	public String queryDeviceStatus(HttpServletRequest request, HttpServletResponse response, String Check_JcdID, String Check_JcdName,
			String Check_areaNo, String Check_areaName, String Check_roadNo, String jcdzt, String tabMap, PageResult pageResults){
		String page = "/serverMonitor/queryDeviceStatus";
		try {
			//加载所有的监测点类型
			jcdKindList = getXXXList(jcdKindList, HQL_MAP.get("jcdKind"));
			request.setAttribute("jcdKind", jcdKindList);
			//加载所有的道路信息
			String hql = "from Road r";
			List<Road> roads = roadService.findObjects(hql, null);
			request.setAttribute("roads", roads);
			for(Road r:roads){
				road.put(r.getRoadNo(), r.getRoadName());
			}
			request.setAttribute("roadMap", road);
			//加载监测点方向
			jcdFx = getXXXMap(jcdFx, HQL_MAP.get("jcdFx"));
			request.setAttribute("jcdFx", jcdFx);
			//加载城区
			cq = getXXXMap(cq, HQL_MAP.get("cq"));
			request.setAttribute("cq", cq);
			
			//根据Hql 单表、带分页、多条件查询
			StringBuffer hql1 = new StringBuffer();
			hql1.append("from Jcd j left join JCDStatus js on j.id=js.jcdid where 1=1");
			Map<String,Object> params = new HashMap<String, Object>();
			if(Check_JcdID != null && Check_JcdID.length() >= 1){
				hql1.append(" and j.id= :Check_JcdID");
				params.put("Check_JcdID", Check_JcdID);
			}
			if(Check_JcdName != null && !"".equals(Check_JcdName.trim())){
				hql1.append(" and j.jcdmc like :Check_JcdName");
				params.put("Check_JcdName", "%"+Check_JcdName+"%");
			}
			if(Check_areaNo != null && !"".equals(Check_areaNo.trim())){
				String[] areanames = Check_areaNo.split(",");
				hql1.append(" and j.cqid in(:ids)");
				params.put("ids", areanames);
			}
			if(Check_roadNo != null && !"".equals(Check_roadNo.trim())){
				hql1.append(" and j.dlid = :Check_roadNo");
				params.put("Check_roadNo", Check_roadNo);
			}
			if(jcdzt != null && "0".equals(jcdzt.trim())){//异常
				hql1.append(" and js.id IS NOT NULL");
			}
			if(jcdzt != null && "1".equals(jcdzt.trim())){//正常
				hql1.append(" and js.id IS NULL");
			}
			
			pageResults = jcdService.getPageResult(hql1.toString(), params, pageResults.getPageNo(), 10);
			request.setAttribute("pageResults", pageResults);
			
			hql1.append(" and j.jd IS NOT NULL and j.jd != '0' and j.wd IS NOT NULL and j.wd != '0'");
			List list = jcdService.findJcds(hql1.toString(), params);
			request.setAttribute("jcd", JSON.toJSONString(list));
			
			if(StringUtils.isEmpty(tabMap)){
				tabMap = "0";
			}
			request.setAttribute("tabMap", tabMap);
			request.setAttribute("jcdzt", jcdzt);
			request.setAttribute("Check_JcdID", Check_JcdID);
			request.setAttribute("Check_JcdName", Check_JcdName);
			request.setAttribute("Check_areaNo", Check_areaNo);
			request.setAttribute("Check_areaName", Check_areaName);
			request.setAttribute("Check_roadNo", Check_roadNo);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	} 
	
	@RequestMapping(value="excelExportForSbzt")
	@Description(moduleName="设备状态监控",operateType="1",operationName="导出监测点状态")
	public void excelExportForSbzt(HttpServletRequest request, HttpServletResponse resp, String Check_JcdID, String Check_JcdName,
			String Check_areaNo, String Check_areaName, String Check_roadNo, String jcdzt, String tabMap){
		//查询出所有的设备状态list
		try {
			//根据Hql 单表、带分页、多条件查询
			StringBuffer hql1 = new StringBuffer();
			hql1.append("from Jcd j left join JCDStatus js on j.id=js.jcdid where 1=1");
			Map<String,Object> params = new HashMap<String, Object>();
			if(Check_JcdID != null && Check_JcdID.length() >= 1){
				hql1.append(" and j.id= :Check_JcdID");
				params.put("Check_JcdID", Check_JcdID);
			}
			if(Check_JcdName != null && !"".equals(Check_JcdName.trim())){
				hql1.append(" and j.jcdmc like :Check_JcdName");
				params.put("Check_JcdName", "%"+Check_JcdName+"%");
			}
			if(Check_areaNo != null && !"".equals(Check_areaNo.trim())){
				String[] areanames = Check_areaNo.split(",");
				hql1.append(" and j.cqid in(:ids)");
				params.put("ids", areanames);
			}
			if(Check_roadNo != null && !"".equals(Check_roadNo.trim())){
				hql1.append(" and j.dlid = :Check_roadNo");
				params.put("Check_roadNo", Check_roadNo);
			}
			if(jcdzt != null && "0".equals(jcdzt.trim())){//异常
				hql1.append(" and js.id IS NOT NULL");
			}
			if(jcdzt != null && "1".equals(jcdzt.trim())){//正常
				hql1.append(" and js.id IS NULL");
			}
			List<Object> list = jcdService.findObjects2(hql1.toString(), params);
			
			List<ExcelBeanForJcdStatus> excelBeanList = new ArrayList<ExcelBeanForJcdStatus>();
			for(Object o:list){
				ExcelBeanForJcdStatus bean = new ExcelBeanForJcdStatus();
				Jcd j = (Jcd)((Object[])o)[0];
				JCDStatus jcdStatus = (JCDStatus)((Object[])o)[1];
				bean.setJcdID(j.getId());
				bean.setJcdName(j.getJcdmc());
				for(Object jcdlx:jcdKindList){
					Map<String,String> map = (Map<String,String>)jcdlx;
					if((map.get("typeSerialNo").equals(j.getJcdxz()))){
						bean.setJcdType(map.get("typeDesc"));
					}
				}
				bean.setJcdDirection(jcdFx.get(j.getXsfx()));
				bean.setJcdAtArea(cq.get(j.getCqid()));
				bean.setJcdAtRoad(road.get(j.getDlid()));
				if(j.getJd()!=null){
					bean.setJcdJd(j.getJd().toString());
				}
				if(j.getWd()!=null){
					bean.setJcdWd(j.getWd().toString());
				}
				if(jcdStatus==null){
					bean.setJcdZt("连接正常");
				}else{
					bean.setJcdZt("连接异常");
				}
				excelBeanList.add(bean);
			}
			//交给excelUtils导出为excel文件
			//输出
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Date now=new Date();
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("设备状态查询").append(dateFormat.format(now)).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
				//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			serverManagerService.excelExportForDeviceStatus(excelBeanList,outputStream);
			if(outputStream!=null){
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
