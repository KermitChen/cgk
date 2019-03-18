package com.dyst.advancedAnalytics.controller;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.datetime.JDateTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.BaseDataMsg.service.DictionaryService;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.BaseDataMsg.service.RoadService;
import com.dyst.advancedAnalytics.entities.FakeDelete;
import com.dyst.advancedAnalytics.entities.FakeHphmExcelBean;
import com.dyst.advancedAnalytics.entities.FakePlate;
import com.dyst.advancedAnalytics.service.FakeService;
import com.dyst.base.utils.PageResult;
import com.dyst.chariotesttube.entities.Vehicle;
import com.dyst.kafka.service.KafkaService;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.Role;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.RoleService;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.StaticUtils;
import com.dyst.vehicleQuery.service.BDService;
import com.dyst.vehicleQuery.service.PicService;
import com.dyst.vehicleQuery.utils.ComUtils;

@Controller
@RequestMapping("/fake")
public class FakeController {
	@Resource
	private FakeService fakeService;
	@Resource
	private BDService bdService;
	@Resource
	private RoleService roleService;
	@Autowired
	private RoadService roadService;
	@Autowired
	private JcdService jcdService;
	@Resource
	private DictionaryService dicService;
	@Autowired
	private KafkaService kafkaService;
	
	//查询类型map表
	private Map<String, String> getCxlxMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "按天查询");
		map.put("2", "按月查询");
		map.put("3", "自定义查询");
		return map;
	}
	
	/**
	 * 加载套牌分析所需参数
	 * @param map
	 * @return
	 */
	@RequestMapping("/preFind")
	public String preFind(Map<String, Object>  map, Model model){
		String page = "/gjfx/fake/findFake";
		try {
			//获取所有带坐标的监测点
			JDateTime jdt = new JDateTime();
			String nowDate = CommonUtils.getTimeOfBefore_N_Day_String("yyyy-MM-dd", new Date(), 1);
			model.addAttribute("year", jdt.toString("YYYY"));
			model.addAttribute("month", jdt.toString("MM"));
			model.addAttribute("cxrq", nowDate);
			model.addAttribute("cxlb", "1");
			model.addAttribute("cxlxMap", getCxlxMap());
			model.addAttribute("kssj", nowDate + " 00:00:00");
			model.addAttribute("jssj", nowDate + " 23:59:59");

			List roadList = roadService.findObjects("from Road", null);
			map.put("roadList", JSON.toJSON(roadList));
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} 
		return page;
	}
	/**
	 * 分页查询套牌车辆信息
	 * @return
	 *      套牌查询页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getFakeForPage")
	@Description(moduleName="套牌车辆检索",operateType="1",operationName="分页查询套牌车辆信息")
	public String getFakeForPage(HttpServletRequest request, Map<String, Object> map,String hphm,String jcdid,String kssj,String jssj,String pageNo,String roadNo) {
		PageResult pageResult = null;
		try {
			int pageNum = 1;
			if(StringUtils.isNotEmpty(pageNo)){
				pageNum = Integer.parseInt(pageNo);
			}
			if(StringUtils.isEmpty(jcdid) && StringUtils.isNotEmpty(roadNo)){
				StringBuffer sb = new StringBuffer();
				String[] roadIds = roadNo.split(";"); 
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("roadIds", roadIds);
				List li = jcdService.findObjects2("from Jcd where dlid in :roadIds)", m);
				for (Object object : li) {
					Jcd jcd = (Jcd) object;
					sb.append(jcd.getId()).append(",");
				}
				jcdid = sb.toString().substring(0, sb.length()-1);
			}
			pageResult = fakeService.getFakeForPage(hphm,jcdid, kssj, jssj, pageNum, 10);
			map.put("pageResult", pageResult);
			map.put("jcdMap",bdService.getJcdMap());
			map.put("cplxMap", bdService.getCplxMap());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return "/gjfx/fake/fakeInfoList";
		}
	}
	
	@RequestMapping(value="doExportFakeHphm")
	@Description(moduleName="套牌车辆检索",operateType="1",operationName="导出套牌车辆信息")
	public void excelExportForFakeHphm(HttpServletRequest req,HttpServletResponse resp,String hphm,String jcdid,String kssj,String jssj,String roadNo){
		
		StringBuffer hql = new StringBuffer();
		Map<String,Object> params = new HashMap<String, Object>();
		hql.append(" from FakePlate f where 1=1");
		if(StringUtils.isNotBlank(hphm)){
			hql.append(" and f.cphid like:hphm");
			params.put("hphm", "%"+hphm+"%");
		}
		if(StringUtils.isNotBlank(jcdid)){
			hql.append(" and f.jcdid2 = :jcdid");
			params.put("jcdid", jcdid);
		}
		if(StringUtils.isNotBlank(kssj)){
			hql.append(" and f.sbsj2 >=:kssj");
			try {
				params.put("kssj", DateUtils.parseDate(kssj, "yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(jssj)){
			hql.append(" and f.sbsj2 <=:jssj");
			try {
				params.put("jssj", DateUtils.parseDate(jssj, "yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(roadNo!=null&&StringUtils.isNotBlank(roadNo)){
			String[] roadIds = roadNo.split(";");
			String hql_jcd = "from Jcd j where j.dlid in :roadIds";
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("roadIds", roadIds);
			List<Object> jcds = jcdService.findObjects2(hql_jcd,map);
			List<String> jcdids = new ArrayList<String>();
			for(Object o:jcds){
				Jcd j = (Jcd)o;
				jcdids.add(j.getId());
			}
			if(jcdids!=null&&jcdids.size()>=1){
				hql.append(" and f.jcdid2 in :jcdids");
				params.put("jcdids", jcdids);
			}
		}
		List<FakePlate> list = fakeService.getList(hql.toString(), params);
		List<FakeHphmExcelBean> excelBeanList = new ArrayList<FakeHphmExcelBean>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String hql_chedao = "from Dictionary d where d.typeCode in :ids";
		Map<String,Object> params_chedao = new HashMap<String, Object>();
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("CD");
		params_chedao.put("ids", ids);
		List<Dictionary> list_chedao = dicService.findDicListByTypeCodeIds(hql_chedao, params_chedao);
		try {
			Map<String, String> jcdMap = bdService.getJcdMap();
			Map<String, String> cplxMap = bdService.getCplxMap();
			//转化成excelBean
			if(list!=null&&list.size()>=1){
				for(FakePlate f:list){
					FakeHphmExcelBean bean = new FakeHphmExcelBean();
					bean.setHphm(f.getCphid());
					bean.setCpys(cplxMap.get(f.getCplx2()));
					bean.setJcd(jcdMap.get(f.getJcdid2()));
					bean.setSbsj(simpleDateFormat.format(f.getSbsj2()));
					for(Dictionary d:list_chedao){
						if(d.getTypeSerialNo().equals(f.getCdid2())){
							bean.setXscd(d.getTypeDesc());
							break;
						}
					}
					excelBeanList.add(bean);
				}
			}
			//交给excelUtils导出为excel文件
			//输出
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Date now=new Date();
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("套牌车辆查询").append(dateFormat.format(now)).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
				//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			fakeService.excelExportForFakeHphm(excelBeanList,outputStream);
			if(outputStream!=null){
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 根据id查询套牌记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getFakeById")
	@Description(moduleName="套牌车辆检索",operateType="1",operationName="根据id查询套牌记录")
	public String getFakeById(HttpServletRequest request,Map<String, Object> map,String id,String index,String pageNo){
		String page = "/gjfx/fake/fake_detail";
		String flag = "0";
		try {
			if(StringUtils.isNotEmpty(id)){
				User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
				int tpid = Integer.valueOf(id);
				//套牌记录
				FakePlate fake = fakeService.getFakeById(tpid);
				if(StringUtils.isNotEmpty(fake.getJjtp1())){
					fake.setJjtp1(PicService.getPicByTpid(fake.getJjtp1()));
				}
				if(StringUtils.isNotEmpty(fake.getJjtp2())){
					fake.setJjtp2(PicService.getPicByTpid(fake.getJjtp2()));
				}
				//标记删除记录
				List delList = fakeService.findDeleteData(tpid);
				for (int i = 0; i < delList.size(); i++) {
					FakeDelete fd = (FakeDelete) delList.get(i);
					if(user.getLoginName().equals(fd.getPno())){
						flag = "1";
					}
				}
				map.put("pageNo", pageNo);
				map.put("index", index);
				map.put("delList", delList);
				map.put("flag", flag);
				map.put("fake", fake);
				map.put("jcdMap",bdService.getJcdMap());
				map.put("cplxMap", bdService.getCplxMap());
				//查询是否有查询机动车信息的权限
				Role role = roleService.getRoleById(user.getRoleId());
				char isHaveVehiclePermission = role.getPermissionContent().charAt(110);
				if(fake != null && fake.getCphid() != null && !"".equals(fake.getCphid()) && fake.getCplx1() != null && !"".equals(fake.getCplx1()) && isHaveVehiclePermission == '1'){
					Vehicle vehicle = ComUtils.getVehicleInfo(user, fake.getCphid(), ComUtils.cplxToHpzl(fake.getCplx1()), 
							CommonUtils.getIpAddr(request), kafkaService);
					map.put("vehicle", vehicle);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	@RequestMapping("/isDelete")
	@Description(moduleName="套牌车辆检索",operateType="1",operationName="判断是否到达删除个数")
	public void decideIsDelete(String id,HttpServletRequest request,HttpServletResponse response){
		PrintWriter writer = null;
		try {
			if(StringUtils.isNotEmpty(id)){
				int tpid = Integer.parseInt(id);
				String flag = fakeService.decideIsDelete(tpid);
				response.setContentType("text/plain");
				writer = response.getWriter();
				writer.write(flag);
				writer.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	
	@RequestMapping("/delete")
	@Description(moduleName="套牌车辆检索",operateType="4",operationName="输出套牌记录")
	public void deleteFakeData(String id,HttpServletResponse response){
		PrintWriter writer = null;
		String flag = "0";
		try {
			if(StringUtils.isNotEmpty(id)){
				fakeService.deleteCphmAndFlag(Integer.parseInt(id));
				flag = "1";
			}
			response.setContentType("text/plain");
			writer = response.getWriter();
			writer.write(flag);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	
	@RequestMapping(value = "/getFakeByIndex", method = RequestMethod.POST)
	@Description(moduleName="套牌车辆检索",operateType="1",operationName="查询单条套牌记录")
	public void getFakeByIndex( HttpServletResponse response,HttpServletRequest request,String hphm,String jcdid,String kssj,String jssj,String pageNo,String roadNo) {
		PageResult pageResult = null;
		PrintWriter writer = null;
		Vehicle vehicle = null;
		String flag = "0";
		try {
			int pageSizeInt = 1;
			if(StringUtils.isNotEmpty(pageNo)){
				int pageNum = Integer.parseInt(pageNo);
				if(StringUtils.isEmpty(jcdid) && StringUtils.isNotEmpty(roadNo)){
					StringBuffer sb = new StringBuffer();
					String[] roadIds = roadNo.split(";"); 
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("roadIds", roadIds);
					List li = jcdService.findObjects2("from Jcd where dlid in :roadIds", m);
					for (Object object : li) {
						Jcd jcd = (Jcd) object;
						sb.append(jcd.getId()).append(",");
					}
					jcdid = sb.toString().substring(0, sb.length()-1);
				}
				pageResult = fakeService.getFakeForPage(hphm,jcdid, kssj, jssj, pageNum, pageSizeInt);
				if(pageResult != null && pageResult.getItems() != null && pageResult.getItems().size() > 0){
					User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
					//套牌记录
					FakePlate fake = (FakePlate) pageResult.getItems().get(0);
					if(StringUtils.isNotEmpty(fake.getJjtp1())){
						fake.setJjtp1(PicService.getPicByTpid(fake.getJjtp1()));
					}
					if(StringUtils.isNotEmpty(fake.getJjtp2())){
						fake.setJjtp2(PicService.getPicByTpid(fake.getJjtp2()));
					}
					//标记删除记录
					List delList = fakeService.findDeleteData(fake.getId());
					for (int i = 0; i < delList.size(); i++) {
						FakeDelete fd = (FakeDelete) delList.get(i);
						if(user.getLoginName().equals(fd.getPno())){
							flag = "1";
						}
					}
					//查询是否有查询机动车信息的权限
					Role role = roleService.getRoleById(user.getRoleId());
					char isHaveVehiclePermission = role.getPermissionContent().charAt(110);
					if(fake != null && fake.getCphid() != null && !"".equals(fake.getCphid()) && fake.getCplx1() != null && !"".equals(fake.getCplx1()) && isHaveVehiclePermission == '1'){
						vehicle = ComUtils.getVehicleInfo(user, fake.getCphid(), ComUtils.cplxToHpzl(fake.getCplx1()), 
								CommonUtils.getIpAddr(request), kafkaService);
					}
					response.setContentType("application/json");
					writer = response.getWriter();
					writer.write(JSON.toJSONString(new Object[]{fake,vehicle,delList,flag}));
					writer.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	
	@RequestMapping("/turnToDel")
	public String turnToDel(String id,Map<String, Object> map){
		map.put("id", id);
		return "/gjfx/fake/del";
	}
	
	/**
	 * 标记数据无效
	 * @param id
	 * @param reason
	 * @param realPlate
	 * @param request
	 * @param response
	 */
	@RequestMapping("/markDel")
	@Description(moduleName="套牌车辆检索",operateType="4",operationName="标记记录无效")
	public void markDel(String id,String reason,String realPlate,HttpServletRequest request,HttpServletResponse response){
		PrintWriter writer = null;
		response.setContentType("application/json");
		try {
			if(StringUtils.isNotEmpty(id)){
				User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
				int tpid = Integer.parseInt(id);
				String pno = user.getLoginName();
				String pName = user.getUserName();
				fakeService.markData(tpid, pno, pName, reason, realPlate);
				writer = response.getWriter();
				writer.write("1");
				writer.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.close();
			}
		}
	}
}