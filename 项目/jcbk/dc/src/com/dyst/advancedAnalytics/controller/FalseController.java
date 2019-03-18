package com.dyst.advancedAnalytics.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dyst.advancedAnalytics.entities.FalseCphm;
import com.dyst.advancedAnalytics.entities.FalseDelete;
import com.dyst.advancedAnalytics.entities.FalseHphmExcelBean;
import com.dyst.advancedAnalytics.service.FalseService;
import com.dyst.base.utils.PageResult;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.User;
import com.dyst.utils.StaticUtils;
import com.dyst.vehicleQuery.dto.ReqArgs;
import com.dyst.vehicleQuery.dto.SbDto;
import com.dyst.vehicleQuery.service.BDService;
import com.dyst.vehicleQuery.service.PicService;
import com.dyst.vehicleQuery.service.SearchService;

@Controller
@RequestMapping("fals")
public class FalseController {

	@Resource
	private FalseService falseService;
	@Autowired
	private BDService bdService;
	@Autowired
	private SearchService searchService;
	@Autowired
	private PicService picSearch;
	/**
	 * 加载假牌分析所需参数
	 * @param map
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/preFind")
	public String preFind(Map<String, Object>  map){
		String page = "/gjfx/fals/findFalse";
		try {
			JDateTime jDate = new JDateTime();
			map.put("jssj", jDate.toString("YYYY-MM-DD hh:mm:ss"));
			map.put("kssj", jDate.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	/**
	 * 分页查询假牌车辆信息
	 * @return
	 *      套牌查询页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getFalseForPage")
	@Description(moduleName="假牌车辆检索",operateType="1",operationName="分页查询假牌车辆信息")
	public String getFalseForPage(HttpServletRequest request, Map<String, Object> map) {
		PageResult pageResult = null;
		try {
			String hphm = request.getParameter("hphm");
			String kssj = request.getParameter("kssj");
			String jssj = request.getParameter("jssj");
			String pageNum = request.getParameter("pageNo");
			int pageNo = 1;
			if(pageNum != null && StringUtils.isNotEmpty(pageNum)){
				pageNo = Integer.parseInt(request.getParameter("pageNo"));
			}
			pageResult = falseService.getFalseForPage(hphm, kssj, jssj, pageNo, 10);
			map.put("pageResult", pageResult);
			map.put("cplxMap", bdService.getCplxMap());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return "/gjfx/fals/falseInfoList";
		}
	}
	/**
	 * 假牌  导出excel
	 * @throws Exception 
	 */
	@RequestMapping(value="excelExportForFals")
	@Description(moduleName="假牌车辆检索",operateType="1",operationName="导出假牌车辆信息")
	public void excelExportForFalseHphm(HttpServletRequest req,HttpServletResponse resp,String hphm,String kssj,String jssj) throws Exception{
		StringBuffer hql = new StringBuffer();
		Map<String,Object> params = new HashMap<String, Object>();
		hql.append(" from FalseCphm f where 1=1");
		if(StringUtils.isNotBlank(hphm)){
			hql.append(" and f.jcphm like :cphm");
			params.put("cphm", "%"+hphm+"%");
		}
		if(StringUtils.isNotBlank(kssj)){
			hql.append(" and f.lrsj >=:kssj");
			params.put("kssj", DateUtils.parseDate(kssj, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(jssj)){
			hql.append(" and f.lrsj <=:jssj");
			params.put("jssj", DateUtils.parseDate(jssj, "yyyy-MM-dd HH:mm:ss"));
		}
		List<FalseCphm> list = falseService.getList(hql.toString(), params);
		List<FalseHphmExcelBean> excelBeanList = new ArrayList<FalseHphmExcelBean>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//生成excelBeanList
		for(FalseCphm f:list){
			FalseHphmExcelBean bean = new FalseHphmExcelBean();
			bean.setHphm(f.getJcphm());
			bean.setLrsh(simpleDateFormat.format(f.getLrsj()));
			excelBeanList.add(bean);
		}
		try {
			//交给excelUtils导出为excel文件
			//输出
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Date now=new Date();
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("假牌车辆查询").append(dateFormat.format(now)).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
				//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			falseService.excelExportForFakeHphm(excelBeanList,outputStream);
			if(outputStream!=null){
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 请求查看是否到达删除个数
	 */
	@RequestMapping("/isDelete")
	@Description(moduleName="假牌车辆检索",operateType="1",operationName="查看是否到达指定删除个数")
	public void decideIsDelete(String id,HttpServletRequest request,HttpServletResponse response){
		PrintWriter writer = null;
		try {
			if(StringUtils.isNotEmpty(id)){
				int jpid = Integer.parseInt(id);
				String flag = falseService.decideIsDelete(jpid);
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
	@Description(moduleName="假牌车辆检索",operateType="4",operationName="删除假牌记录")
	public void deleteFalseData(String id,HttpServletResponse response){
		PrintWriter writer = null;
		String flag = "0";
		try {
			if(StringUtils.isNotEmpty(id)){
				falseService.deleteCphmAndFlag(Integer.parseInt(id));
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
	/**
	 * 修改假牌记录
	 */
	@RequestMapping("/update")
	@Description(moduleName="假牌车辆检索",operateType="3",operationName="根据id修改假牌记录")
	public void updateFalsePlate(HttpServletRequest request,HttpServletResponse response){
		FalseCphm falseCphm = null;
		User user = null;
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String clyj = request.getParameter("clyj");
			falseCphm = falseService.getFalseById(id);
			user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			falseCphm.setClr(user.getLoginName());
			falseCphm.setClsj(new Date());
			falseCphm.setClyj(clyj);
			falseCphm.setJlzt("1");
			falseService.updateObject(falseCphm);
			response.setContentType("application/json");
			response.getWriter().write("1");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/turnToDel")
	public String turnToDel(String id,Map<String, Object> map){
		map.put("id", id);
		return "/gjfx/fals/del";
	}
	/**
	 * 标记假牌无效
	 * @param id
	 * @param reason
	 * @param realPlate
	 * @param request
	 * @param response
	 */
	@RequestMapping("/markDel")
	@Description(moduleName="假牌车辆检索",operateType="4",operationName="标记记录无效")
	public void markDelData(String id,String reason, String realPlate,HttpServletRequest request,HttpServletResponse response){
		PrintWriter writer = null;
		response.setContentType("application/json");
		try {
			if(StringUtils.isNotEmpty(id)){
				User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
				int jpid = Integer.parseInt(id);
				String pno = user.getLoginName();
				String pName = user.getUserName();
				falseService.markDelete(jpid, pno, pName, reason, realPlate);
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
	/**
	 * 根据id查询详细信息
	 * @param id
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("/showDetail")
	@Description(moduleName="假牌车辆检索",operateType="1",operationName="根据id查询假牌详情")
	public String showDetail(String id,Map<String, Object> map,HttpServletRequest request){
		String page = "/gjfx/fals/false_detail";
		String flag = "0";
		try {
			if(StringUtils.isNotEmpty(id)){
				FalseCphm fla = falseService.getFalseById(Integer.parseInt(id));
				if(fla != null){
					List delList = falseService.findDeleteData(fla.getId());//标记删除的用户列表
					User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
					for (int i = 0; i < delList.size(); i++) {
						FalseDelete fd = (FalseDelete) delList.get(i);
						if(user.getLoginName().equals(fd.getPno())){
							flag = "1";
						}
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					List list = searchService.findSomeVehicleQuery(new ReqArgs(sdf.format(fla.getJskssj()), sdf.format(fla.getJsjssj()), fla.getJcphm(), 1, 0, "tgsj", "desc"));
					if (list != null && list.size() > 0) {
						picSearch.findPic(list);
						map.put("sb", list.get(0));
					}
					map.put("cplxMap", bdService.getCplxMap());
					map.put("jcdMap", bdService.getJcdMap());
					map.put("index", 0);
					map.put("delList", delList);
					map.put("fla", fla);
					map.put("flag", flag);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	@RequestMapping(value = "/getFalseSb", method = RequestMethod.POST)
	@Description(moduleName="假牌车辆检索",operateType="1",operationName="假牌车过车记录查询")
	public void getFalseByIndex( HttpServletResponse response,HttpServletRequest request,String jcpid,String index) {
		PrintWriter writer = null;
		String jsonResult = JSON.toJSONString(new Object[]{"0",null});
		try {
			if(StringUtils.isNotEmpty(jcpid)){
				FalseCphm fla = falseService.getFalseById(Integer.parseInt(jcpid));
				if(fla != null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					List list = searchService.findSomeVehicleQuery(new ReqArgs(sdf.format(fla.getJskssj()), sdf.format(fla.getJsjssj()), fla.getJcphm(), 1, Integer.parseInt(index), "tgsj", "desc"));
					if (list != null && list.size() > 0) {
						picSearch.findPic(list);
						SbDto sb = (SbDto) list.get(0);
						jsonResult = JSON.toJSONString(new Object[]{"1",sb});
					}
				}
			}
			response.setContentType("application/json");
			writer = response.getWriter();
			writer.write(jsonResult);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.close();
			}
		}
	}
}
