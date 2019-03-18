package com.dyst.DyMsg.controller;

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
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.service.DictionaryService;
import com.dyst.DyMsg.entities.Dyjg;
import com.dyst.DyMsg.service.DySqService;
import com.dyst.base.utils.PageResult;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.User;
import com.dyst.utils.StaticUtils;
import com.dyst.utils.excel.bean.DyjgExcelBean;

@Controller
@RequestMapping(value="dyjg")
public class DyJgController {
	
	@Autowired
	private DictionaryService dicService;
	@Autowired
	private DySqService dySqService;
	
	/*
	 * 1.页面记录状态list,第一次查询后保存在内存中*****(改成每次都重新查询数据库)
	 * 2.审批结果,第一次查询后保存在内存中
	 * 3.订阅类型,第一次查询后保存在内存中
	 * 4.通知方式,第一次查询后保存在内存中
	 */
	public static List<Object> jlztList = new ArrayList<Object>();
	public static List<Object> spjgList = new ArrayList<Object>();
	public static List<Object> dylxList = new ArrayList<Object>();
	public static List<Object> tzfsList = new ArrayList<Object>();
	public static List<Object> cpysList = new ArrayList<Object>();
	public static List<Object> jcdsList = new ArrayList<Object>();
	public static Map<String,String> jcdsMap = new HashMap<String, String>();
	/*
	 * 1.获取jlztList
	 * 2.获取spjgList
	 * 3.获取dylxList
	 */
	public List<Object> getXXXList(List<Object> xxxList,String hql) throws Exception{
		xxxList.clear();
		xxxList = dicService.findObjects(hql, null);
		return xxxList;
	}
	public Map<String,String> getXXXMap(Map<String,String> xxxMap,String hql) throws Exception{
		xxxMap.clear();
		return dicService.findMapObjects(hql, null);
	}
	/*
	 * 1.提供基本查询hql语句
	 */
	public static Map<String,String> HQL_MAP;
	static{
		HQL_MAP = new HashMap<String,String>();
		HQL_MAP.put("jlzt", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HmdSpZt' and d.deleteFlag != '1'");
		HQL_MAP.put("spjg", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HmdSpJg' and d.deleteFlag != '1'");
		HQL_MAP.put("dylx", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'DyLx' and d.deleteFlag != '1'");
		HQL_MAP.put("cpys", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = '0002' and d.deleteFlag != '1'");
		HQL_MAP.put("jcds", "from Jcd j where 1=1");
	}


	//跳转到订阅结果查询页面
	@RequestMapping(value="findDyJg")
	@Description(moduleName="订阅结果查询",operateType="1",operationName="查询订阅结果")
	public String findDyJg(Model model,PageResult pageResult,String cphms,String cpys,String jcdid,
			String startTime,String endTime,String dylxs,HttpServletRequest req) throws Exception{
		/*
		 * 1.加载车牌颜色
		 * 2.加载订阅类型
		 * 3.监测点
		 */
		cpysList = getXXXList(cpysList, HQL_MAP.get("cpys"));
		dylxList = getXXXList(dylxList, HQL_MAP.get("dylx"));
		//jcdsList = getXXXList(jcdsList, HQL_MAP.get("jcds"));
		jcdsMap = getXXXMap(jcdsMap,HQL_MAP.get("jcds"));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cpysList", cpysList);
		map.put("dylxList", dylxList);
		map.put("jcdsList", jcdsList);
		map.put("jcdsMap", jcdsMap);
		//获取当前用户
		User user = (User) req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		String dyrjh = user.getLoginName();
		//加载当前页的信息
		StringBuffer hql = new StringBuffer();
		Map<String,Object> params = new HashMap<String,Object>();
		hql.append("from Dyjg d where d.dyrjh=:dyrjh ");
		params.put("dyrjh", dyrjh);
		if(StringUtils.isNotBlank(cphms)){
			String [] cpNos = cphms.split(",");
			hql.append(" and d.hphm in(:cpNos)");
			params.put("cpNos", cpNos);
		}
		if(StringUtils.isNotBlank(cpys)){
			hql.append(" and d.hpzl =:cpys");
			params.put("cpys", cpys);
		}
		if(StringUtils.isNotBlank(startTime)){
			hql.append(" and d.tgsj >= :startTime");
			params.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			hql.append(" and d.tgsj <= :endTime");
			params.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNoneBlank(dylxs)){
			String[] dylx = dylxs.split(";");
			hql.append(" and d.dylx in(:dylxs)");
			params.put("dylxs", dylx);
		}
		if(StringUtils.isNotBlank(jcdid)){
			String[] jcds = jcdid.split(",");
			hql.append(" and d.jcdid in:(jcdid)");
			params.put("jcdid", jcds);
		}
		pageResult=dySqService.getPageResult(hql.toString(), params, pageResult.getPageNo(), 10);
		//返回查询结果
		map.put("pageResult", pageResult);
		//返回查询条件
		map.put("cphms", cphms);
		map.put("cpys", cpys);
		map.put("jcdid", jcdid);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("dylxs", dylxs);
		String dylxListJson = JSON.toJSONString(dylxList);
		map.put("dylxListJson", dylxListJson);
		model.addAllAttributes(map);
		return "DyMsg/dyjg/listUI";
	}
	
	//导出excel
	@RequestMapping(value="exportExcel")
	@Description(moduleName="订阅结果查询",operateType="1",operationName="导出订阅结果")
	public void exportExcel(HttpServletRequest req,HttpServletResponse resp,String cphms,String cpys,String jcdid,
			String startTime,String endTime,String dylxs) throws Exception{
		User user = (User) req.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		if(user!=null){
			//根据用户查询出列表
    		StringBuilder hql = new StringBuilder();
    		Map<String,Object> params = new HashMap<String, Object>();
    		hql.append("from Dyjg d where d.dyrjh=:dyrjh ");
    		params.put("dyrjh", user.getLoginName());
    		if(StringUtils.isNotBlank(cphms)){
    			String [] cpNos = cphms.split(",");
    			hql.append(" and d.hphm in(:cpNos)");
    			params.put("cpNos", cpNos);
    		}
    		if(StringUtils.isNotBlank(cpys)){
    			hql.append(" and d.hpzl =:cpys");
    			params.put("cpys", cpys);
    		}
    		if(StringUtils.isNotBlank(startTime)){
    			hql.append(" and d.tgsj >= :startTime");
    			params.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
    		}
    		if(StringUtils.isNotBlank(endTime)){
    			hql.append(" and d.tgsj <= :endTime");
    			params.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
    		}
    		if(StringUtils.isNoneBlank(dylxs)){
    			String[] dylx = dylxs.split(";");
    			hql.append(" and d.dylx in(:dylxs)");
    			params.put("dylxs", dylx);
    		}
    		if(StringUtils.isNotBlank(jcdid)){
    			String[] jcds = jcdid.split(",");
    			hql.append(" and d.jcdid in:(jcdid)");
    			params.put("jcdid", jcds);
    		}
    		List<Dyjg> list = new ArrayList<Dyjg>();
    		List<DyjgExcelBean> dyjgExcelBeanList = new ArrayList<DyjgExcelBean>();
    		list = dySqService.findExcelList2(hql.toString(), params);
    		//车牌颜色
    		cpysList = getXXXList(cpysList, HQL_MAP.get("cpys"));
    		//订阅类型
    		dylxList = getXXXList(dylxList, HQL_MAP.get("dylx"));
    		//监测点s
			jcdsMap = getXXXMap(jcdsMap,HQL_MAP.get("jcds"));
    		//封装excelBean
    		if(list!=null&&list.size()>0){
    			for(Dyjg d:list){
        			DyjgExcelBean dyjgExcelBean = new DyjgExcelBean();
        			dyjgExcelBean.setDyjg(d);
        			String cpys2 = d.getHpzl();
        			String dylx2 = d.getDylx();
        			//车牌颜色转换
        			for(Object o:cpysList){
    					Map<String,String> map = ((HashMap<String, String>)o);
    					String serialNo = map.get("typeSerialNo");
    					String desc = map.get("typeDesc");
    					if(serialNo.equals(cpys2)){
    						dyjgExcelBean.setHpzl(desc);
    						break;
    					}
    				}
        			//订阅类型转换
    				for(Object o:dylxList){
    					Map<String,String> map = ((HashMap<String, String>)o);
    					String serialNo = map.get("typeSerialNo");
    					String desc = map.get("typeDesc");
    					if(serialNo.equals(dylx2)){
    						dyjgExcelBean.setDylx(desc);
    						break;
    					}
    				}
    				//监测点转换
    				String jcdmc = jcdsMap.get(d.getJcdid());
    				dyjgExcelBean.setJcdmc(jcdmc);
    				dyjgExcelBeanList.add(dyjgExcelBean);
        		}
    		}
    		//输出
    		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    		Date now=new Date();
    		StringBuilder filename = new StringBuilder();//文件名
    		filename.append("订阅结果列表").append(dateFormat.format(now)).append(".xls");
    		resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
    		//附件形式，并指定文件名
    		resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
    		ServletOutputStream outputStream = resp.getOutputStream();//输出流
    		dySqService.exportDyjgExcel(dyjgExcelBeanList, outputStream);
    		if(outputStream!=null){
    			outputStream.close();
    		}
		}
	}
}
