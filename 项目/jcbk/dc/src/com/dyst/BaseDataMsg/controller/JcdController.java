package com.dyst.BaseDataMsg.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Area;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Road;
import com.dyst.BaseDataMsg.service.AreaService;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.BaseDataMsg.service.RoadService;
import com.dyst.base.utils.PageResult;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.CommonUtils;

@Controller
@RequestMapping(value="/jcd")
public class JcdController extends DictionaryController{

	//静态的所有监测点的json字符串
	public static StringBuffer treeJson= new  StringBuffer();

	//注入业务层
	@Resource
	private UserService userService;
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	//注入监测点service
	@Autowired
	private JcdService jcdService;
	private AreaService areaService;
	private RoadService roadService;
	
	public AreaService getAreaService() {
		return areaService;
	}
	@Autowired
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	public RoadService getRoadService() {
		return roadService;
	}
	@Autowired
	public void setRoadService(RoadService roadService) {
		this.roadService = roadService;
	}
	
	//监测点类型静态map
	public static String JCD_KIND_ROAD = "1";
	public static String JCD_KIND_PARKING = "2";
	public static String JCD_KIND_GASSTATION = "3";
	public static Map<String,String> JCD_STATE_MAP;
	static{
		JCD_STATE_MAP = new HashMap<String, String>();
		JCD_STATE_MAP.put(JCD_KIND_ROAD, "路面");
		JCD_STATE_MAP.put(JCD_KIND_PARKING, "停车场");
		JCD_STATE_MAP.put(JCD_KIND_GASSTATION, "加油站");
	}
	
	/**
	 * 初始化监测点管理页面
	 * @return
	 *      监测点管理页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/findJcd")
	public String initFindJcd(HttpServletRequest request, HttpServletResponse response) {
		String page = "baseDataMsg/jcd/listUI";
		try {
			//获取监测点类型，方向数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("JCDLX,0039,JCDQYBZ");
			String dicJson = JSON.toJSONString(dicList);
			request.getSession().setAttribute("dicList", dicList);
			request.setAttribute("dicJson", dicJson);
			
			//加载所有的道路信息
			List<Road> roads = roadService.findObjects("from Road r", null);
			request.getSession().setAttribute("roads", roads);
			
			//加载所有的道路信息
			List<Area> areas = areaService.findObjects("from Area r", null);
			request.getSession().setAttribute("areas", areas);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	//查询所有，加载首页
	@SuppressWarnings("finally")
	@RequestMapping(value="/getJcdForPage")
	@Description(moduleName="监测点管理",operateType="1",operationName="查询监测点信息")
	public String getJcdForPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PageResult pageResult = null;
		try {
			//条件
			String jcdId = request.getParameter("jcdId");
			String jcdName = request.getParameter("jcdName");
			String cqName = request.getParameter("cqName");
			String dlName = request.getParameter("dlName");
			String jcdxzName = request.getParameter("jcdxzName");
			String qybzName = request.getParameter("qybzName");
			int pageNo = Integer.parseInt(request.getParameter("pageNo"));
			
			//根据Hql 单表、带分页、多条件查询
			StringBuffer hql1 = new StringBuffer();
			hql1.append("from Jcd j where 1=1 ");
			Map<String,Object> params = new HashMap<String, Object>();
			if(jcdId != null && !"".equals(jcdId.trim())){
				hql1.append(" and j.id = :Check_JcdID");
				params.put("Check_JcdID", jcdId);
			}
			if(jcdName != null && !"".equals(jcdName.trim())){
				hql1.append(" and j.jcdmc like :Check_JcdName");
				params.put("Check_JcdName", "%" + jcdName + "%");
			}
			if(cqName != null && !"".equals(cqName.trim())){
				String [] areanames = cqName.split(",");
				hql1.append(" and j.cqid in(:ids)");
				params.put("ids", areanames);
			}
			if(dlName != null && !"".equals(dlName.trim())){
				hql1.append(" and j.dlid = :Check_roadName");
				params.put("Check_roadName", dlName);
			}
			if(jcdxzName != null && !"".equals(jcdxzName.trim())){
				hql1.append(" and j.jcdxz = :Check_Jcdxz");
				params.put("Check_Jcdxz", jcdxzName);
			}
			if(qybzName != null && !"".equals(qybzName.trim())){
				hql1.append(" and j.qybz = :qybzName");
				params.put("qybzName", qybzName);
			}
			pageResult = jcdService.getPageResult(hql1.toString(), params, pageNo, 10);
			request.setAttribute("pageResult", pageResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return "baseDataMsg/jcd/jcdInfoList";
		}
	}
	
	/**
	 * 启用停用
	 * @return
	 *      0:操作失败！
	 * 		1:操作成功！
	 */
	@RequestMapping("/qyty")
	@Description(moduleName="监测点管理",operateType="3",operationName="启用停用操作")
	public void qyty(HttpServletRequest request, HttpServletResponse response){
		String result = "1";
		try {
			String jcdId = request.getParameter("jcdId");//信息编号
			String qybz = request.getParameter("qybz");//当前状态
			if(jcdId != null && !"".equals(jcdId.trim())){
				jcdService.qyty(jcdId, (qybz != null && "1".equals(qybz.trim()) ? "0" : "1"));
			} else {
				result = "0";//操作失败
			}
		} catch (Exception e) {
			result = "0";//操作失败
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
	
	//执行findJcd时异步请求城区信息树
	@RequestMapping(value="getTreeJson2")
	public void getTreeJson2(HttpServletResponse resp){
/*		String hql = "from Area a ";
		List<Area> list = areaService.findObjects(hql, null);
		String data = JSON.toJSONString(list);*/
		
		//查询出pid为00的二级城区
		String hql2 ="select new map(a.areano as id ,a.suparea as pId ,a.areaname as name) from Area a where a.suparea ='00'";
		List<Object> list2 = jcdService.findObjects(hql2, null);
/*		//查询出二级城区下的所有道路
		String hql4 ="select new map(r.pkId as id ,a.areano as pId ,r.roadName as name) from Road r ,Area a where r.cityId =a.areano";
		List<Object> list4 = jcdService.findObjects(hql4, null);
		list2.addAll(list4);*/
		String data = JSON.toJSONString(list2);
		try {
			resp.getWriter().write(data);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Hmd中单击监测点查询后，加载监测点树
	@RequestMapping(value="getTreeJson3")
	public void getTreeJson3(HttpServletResponse resp,Model model,String Check_jcd1,String Check_jcd2,String Check_jcd3){
		//最后还是选用了简单json格式的数据
		//查询出pid为00的二级城区
		if(treeJson.toString().isEmpty()){
			getJsonTree();
		}
		try {
			resp.getWriter().write(treeJson.toString());
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//获得jsonTree方法
	private String getJsonTree(){
		String hql2 ="select new map(a.areano as id ,a.suparea as pId ,a.areaname as name) from Area a where a.suparea ='00'";
		List<Object> list2 = jcdService.findObjects(hql2, null);
		//字典表中查询所有二级城区下的监测点类型
		String hql3 ="select new map(a.areano||d.typeSerialNo as id,a.areano as pId,d.typeDesc as name) from Dictionary d ,Area a where d.typeCode='JCDLX'" +
				" and a.suparea ='00' and a.areano!='00'";
		List<Object> list3 = jcdService.findObjects(hql3, null);
		//查询出二级城区下的所有监测点
		String hql4 ="select new map(j.id as id ,a.areano ||j.jcdxz as pId ,j.jcdmc as name) from Jcd j ,Area a where j.cqid =a.areano";
		List<Object> list4 = jcdService.findObjects(hql4, null);
		list2.addAll(list3);
		list2.addAll(list4);
		treeJson.setLength(0);
		treeJson.append(JSON.toJSONString(list2));		
		return treeJson.toString();
	}
	
	//根据名称查询所有的监测点
	@RequestMapping(value="getJcdByName")
	@Description(moduleName="监测点管理",operateType="1",operationName="根据名称查询监测点")
	public void getJcdByName(HttpServletRequest req,HttpServletResponse resp, String[] Check_jcd, String jcdmc) throws UnsupportedEncodingException{
		//获取表单中的监测点类型
		//获取url中带的监测点名称
		String Check_jcdmc = jcdmc;
		StringBuffer hql = new StringBuffer();
	    hql.append("select new map(j.id as id, j.jcdmc as jcdmc) from Jcd j where j.jcdmc like ?");
		List<Object> params = new ArrayList<Object>();
		if(StringUtils.isNotBlank(Check_jcdmc)){
			params.add("%" + Check_jcdmc.trim() + "%");
		}
		
	    //拼接jcdxz占位符
	    String a = "";
	    if(Check_jcd !=null && Check_jcd.length >= 1){
	    	for(int i=0;i < Check_jcd.length;i++){
	    		a = a + "?";
	    		if(i < Check_jcd.length-1){
	    			a = a + ",";
	    		}
	    		params.add(Check_jcd[i]);
	    	}
	    	hql.append("and j.jcdxz in (" + a + ")");
	    }
		
	    String jcdDatas = "[]";
		try {
			//查询
			List<Object> jcdList = jcdService.findObjects(hql.toString(), params);
			jcdDatas = JSON.toJSONString(jcdList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				resp.getWriter().write(jcdDatas);
				resp.getWriter().flush();
				resp.getWriter().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//根据拼音查询监测点
	@RequestMapping(value="getJcdByPinYin")
	@Description(moduleName="监测点管理",operateType="1",operationName="根据拼音查询监测点")
	public void getJcdByPinYin(HttpServletRequest req, HttpServletResponse resp, Model model, String[] Check_jcd, String jcdmc) throws UnsupportedEncodingException{
		//获取表单中的监测点类型
		String Check_pinYin = jcdmc;
		String hql = "select new map(j.id as id, j.jcdmc as jcdmc) from Jcd j where j.py like ?";
		List<Object> params = new ArrayList<Object>();
		if(StringUtils.isNotBlank(Check_pinYin)){
			params.add("%" + Check_pinYin + "%");
		}
		
		//拼接jcdxz占位符
	    String a = "";
	    if(Check_jcd !=null && Check_jcd.length >= 1){
	    	for(int i=0;i < Check_jcd.length;i++){
	    		a = a + "?";
	    		if(i < Check_jcd.length-1){
	    			a = a + ",";
	    		}
	    		params.add(Check_jcd[i]);
	    	}
	    	hql = hql + " and j.jcdxz in (" + a + ")";
	    }
		
		String jcdDatas = "[]";
		try {
			List<Object> jcdList = jcdService.findObjects(hql, params);
			jcdDatas = JSON.toJSONString(jcdList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				resp.getWriter().write(jcdDatas);
				resp.getWriter().flush();
				resp.getWriter().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//根据监测点编号检索
	@RequestMapping(value="getJcdByBianHao")
	@Description(moduleName="监测点管理",operateType="1",operationName="根据编号查询监测点")
	public void getJcdByBianHao(HttpServletRequest req, HttpServletResponse resp, Model model, String[] Check_jcd, String jcdmc) throws UnsupportedEncodingException{
		//获取表单中的监测点类型
		String Check_pinYin = jcdmc;
		String hql = "select new map(j.id as id, j.jcdmc as jcdmc) from Jcd j where j.id like ?";
		List<Object> params = new ArrayList<Object>();
		if(StringUtils.isNotBlank(Check_pinYin)){
			params.add("%" + Check_pinYin + "%");
		}
		
		//拼接jcdxz占位符
	    String a = "";
	    if(Check_jcd !=null && Check_jcd.length >= 1){
	    	for(int i=0;i < Check_jcd.length;i++){
	    		a = a + "?";
	    		if(i < Check_jcd.length - 1){
	    			a = a + ",";
	    		}
	    		params.add(Check_jcd[i]);
	    	}
	    	hql = hql + " and j.jcdxz in (" + a + ")";
	    }
		
		String jcdDatas = "[]";
		try {
			List<Object> jcdList = jcdService.findObjects(hql, params);
			jcdDatas = JSON.toJSONString(jcdList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				resp.getWriter().write(jcdDatas);
				resp.getWriter().flush();
				resp.getWriter().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//根据选中的监测点的Ids查询出所有的监测点，在已选下拉框中显示
	@RequestMapping(value="getByIds")
	@Description(moduleName="监测点管理",operateType="1",operationName="根据编号查询监测点")
	public void getByIds(HttpServletRequest req,HttpServletResponse resp,Model model){
		String[] ids = req.getParameterValues("ids[]");
		List<Object> jcdList = new ArrayList<Object>();
/*		if(ids!=null &&ids.length >=1){
	        String hql = "from Jcd j where j.id = ?";
	        for(String a :ids){
	        	jcdList.add(jcdService.findObject(hql, a).get(0));
	        }
		}*/
		String hql = "select new map(j.id as id,j.jcdmc as jcdmc) from Jcd j where j.id in(:ids)";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		jcdList = jcdService.findObjects2(hql, params);
        String jcdDatas = JSON.toJSONString(jcdList);
		try {
			resp.getWriter().write(jcdDatas);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	  * 页面中监测点自动补全文本调用的方法
	  * 1.根据页面传递过来的部分监测点名称，模糊查询数据库并返回查询结果
	  * 2.返回XML页面处理
	  * @return
	  * @throws Exception
	 */
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping(value="getJcdListByJcdmc")
	@Description(moduleName="监测点管理",operateType="1",operationName="根据编号或名称查询监测点")
	public String getJcdListByJcdmc(HttpServletRequest request,HttpServletResponse response) throws Exception {
		List autoList = new ArrayList();
		String jcdmc = CommonUtils.keyWordFilter(request.getParameter("word"));
		try {
			if(jcdmc != null && !"".equals(jcdmc.trim())){
				String hql = " select concat(jcdmc, ',', id) from Jcd where id like ? or jcdmc like ? ";
				List<Object> params = new ArrayList<Object>();
				params.add("%" + jcdmc.trim() + "%");
				params.add("%" + jcdmc.trim() + "%");
				autoList = jcdService.findObjects(hql, params);
			}
		} catch (Exception e) {			
			e.printStackTrace();			
		} finally{
			request.setAttribute("autolist", autoList); 
			return "/baseDataMsg/jcd/jcdList";
		}
	}
}