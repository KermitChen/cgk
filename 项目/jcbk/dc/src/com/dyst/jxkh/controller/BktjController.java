package com.dyst.jxkh.controller;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.datetime.JDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.dispatched.entities.DisReceive;
import com.dyst.earlyWarning.service.EWarningService;
import com.dyst.jxkh.entities.BkckEntity;
import com.dyst.jxkh.entities.BkckListBean;
import com.dyst.jxkh.entities.BktjEntity;
import com.dyst.jxkh.entities.BktjItemEntity;
import com.dyst.jxkh.service.JxkhService;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.Department;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.StaticUtils;
import com.dyst.utils.excel.bean.bktj.BkckExcelBean;
import com.dyst.utils.excel.bean.bktj.BktjExcelBean;

@Controller
@RequestMapping(value="/bktj")
public class BktjController {
	@Autowired
	private JxkhService jxkhService;
	
	//注入业务层
	@Autowired
	private UserService userService;
	
	@Autowired
	private EWarningService eWarningService;
	
	//查询类型map表
	private Map<String, String> getCxlxMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "按天查询");
		map.put("2", "按月查询");
		map.put("3", "自定义查询");
		return map;
	}
	
	/**
	 * 跳转到布控统计页面的方法
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/findBktjUI")
	public String findBktjUI(Model model, HttpServletRequest request){
		//获取所有带坐标的监测点
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dayformt = new SimpleDateFormat("yyyy-MM-dd");
		JDateTime jdt = new JDateTime();
		Date nowDate = new Date();
		model.addAttribute("year", jdt.toString("YYYY"));
		model.addAttribute("month", jdt.toString("MM"));
		model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
		model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
		model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
		model.addAttribute("cxlb", "1");
		model.addAttribute("cxlxMap", getCxlxMap());
		model.addAttribute("kssj", dayformt.format(nowDate) + " 00:00:00");
		model.addAttribute("jssj", formatter.format(nowDate));
		return "/jxkh/bk/bktjcxListUI";
	}
	
	/**
	 * 根据条件查询出  布控统计
	 * @throws ParseException 
	 */
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping(value="/findBktj")
	@Description(moduleName="布控统计查询",operateType="1",operationName="布控统计")
	public String findBktj(Model model, Map<String, Object> map, HttpServletRequest request, String kssj, String jssj) {
		String result = "1";
		try {
			//根据时间统计布控信息
			Map<String, List> bktjMap = jxkhService.getBktj(kssj, jssj, "");
			
			//获取角色类型，用户信息来源数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("BKDL,BKDL1,BKDL2,BKDL3");
			
			//拆分出布控小类
			List<Dictionary> bkdlList = getBkdl("BKDL", dicList);//布控大类
			List<Dictionary> bkdl1List = getBkdl("BKDL1", dicList);//涉案类
			List<Dictionary> bkdl2List = getBkdl("BKDL2", dicList);//交通违法类
			List<Dictionary> bkdl3List = getBkdl("BKDL3", dicList);//管控类
			
			//根据类型封装数据
			List<BktjEntity> bktjList = new ArrayList<BktjEntity>(); 
			BktjEntity bktjEntity = null;
			for(int i=0;i < bkdlList.size();i++){
				//布控大类
				Dictionary bkdlDic = bkdlList.get(i);
				
				//获取相应小类
				Dictionary bklbDic = null;
				if("BKDL".equals(bkdlDic.getTypeCode()) && "1".equals(bkdlDic.getTypeSerialNo())){//涉案类
					//一大类
					bktjEntity = new BktjEntity();
					bktjEntity.setTypeSerialNo(bkdlDic.getTypeSerialNo());
					bktjEntity.setTypeDesc(bkdlDic.getTypeDesc());
					
					//每小类
					List<BktjItemEntity> itemList = new ArrayList<BktjItemEntity>();
					for(int j=0;j < bkdl1List.size();j++){
						bklbDic = bkdl1List.get(j);
						
						//获取每一种小类的三项值
						int bdbk = getCountValue(bktjMap.get("bdbk"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int jcjbk = getCountValue(bktjMap.get("110bk"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int ldbk = getCountValue(bktjMap.get("ldbk"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						
						BktjItemEntity bktjItemEntity = new BktjItemEntity();
						bktjItemEntity.setTypeSerialNo(bklbDic.getTypeSerialNo());
						bktjItemEntity.setTypeDesc(bklbDic.getTypeDesc());
						bktjItemEntity.setBdbk(bdbk);
						bktjItemEntity.setBjfwt(jcjbk);
						bktjItemEntity.setLdbk(ldbk);
						bktjItemEntity.setSubtotal(bdbk + jcjbk + ldbk);
						itemList.add(bktjItemEntity);
					}
					
					//添加小类
					bktjEntity.setList(itemList);
					//添加大类
					bktjList.add(bktjEntity);
				} else if("BKDL".equals(bkdlDic.getTypeCode()) && "2".equals(bkdlDic.getTypeSerialNo())){//交通违法类
					//一大类
					bktjEntity = new BktjEntity();
					bktjEntity.setTypeSerialNo(bkdlDic.getTypeSerialNo());
					bktjEntity.setTypeDesc(bkdlDic.getTypeDesc());
					
					//每小类
					List<BktjItemEntity> itemList = new ArrayList<BktjItemEntity>();
					for(int j=0;j < bkdl2List.size();j++){
						bklbDic = bkdl2List.get(j);
						
						//获取每一种小类的三项值
						int bdbk = getCountValue(bktjMap.get("bdbk"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int jcjbk = getCountValue(bktjMap.get("110bk"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int ldbk = getCountValue(bktjMap.get("ldbk"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						
						BktjItemEntity bktjItemEntity = new BktjItemEntity();
						bktjItemEntity.setTypeSerialNo(bklbDic.getTypeSerialNo());
						bktjItemEntity.setTypeDesc(bklbDic.getTypeDesc());
						bktjItemEntity.setBdbk(bdbk);
						bktjItemEntity.setBjfwt(jcjbk);
						bktjItemEntity.setLdbk(ldbk);
						bktjItemEntity.setSubtotal(bdbk + jcjbk + ldbk);
						itemList.add(bktjItemEntity);
					}
					//添加小类
					bktjEntity.setList(itemList);
					//添加大类
					bktjList.add(bktjEntity);
				} else if("BKDL".equals(bkdlDic.getTypeCode()) && "3".equals(bkdlDic.getTypeSerialNo())){//管控类
					//一大类
					bktjEntity = new BktjEntity();
					bktjEntity.setTypeSerialNo(bkdlDic.getTypeSerialNo());
					bktjEntity.setTypeDesc(bkdlDic.getTypeDesc());
					
					//每小类
					List<BktjItemEntity> itemList = new ArrayList<BktjItemEntity>();
					for(int j=0;j < bkdl3List.size();j++){
						bklbDic = bkdl3List.get(j);
						
						//获取每一种小类的三项值
						int bdbk = getCountValue(bktjMap.get("bdbk"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int jcjbk = getCountValue(bktjMap.get("110bk"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						int ldbk = getCountValue(bktjMap.get("ldbk"), bkdlDic.getTypeSerialNo(), bklbDic.getTypeSerialNo());
						
						BktjItemEntity bktjItemEntity = new BktjItemEntity();
						bktjItemEntity.setTypeSerialNo(bklbDic.getTypeSerialNo());
						bktjItemEntity.setTypeDesc(bklbDic.getTypeDesc());
						bktjItemEntity.setBdbk(bdbk);
						bktjItemEntity.setBjfwt(jcjbk);
						bktjItemEntity.setLdbk(ldbk);
						bktjItemEntity.setSubtotal(bdbk + jcjbk + ldbk);
						itemList.add(bktjItemEntity);
					}
					//添加小类
					bktjEntity.setList(itemList);
					//添加大类
					bktjList.add(bktjEntity);
				}
			}
			request.setAttribute("bktjList", bktjList);
			request.getSession().setAttribute("bktjList", bktjList);
			
			//回显查询条件
			map.put("year", request.getParameter("year"));
			map.put("month", request.getParameter("month"));
			map.put("cxrq", request.getParameter("cxrq"));
			map.put("cxlb", request.getParameter("cxlb"));
			map.put("kssj", kssj);
			map.put("jssj", jssj);
			map.put("cxlxMap", getCxlxMap());
		} catch (Exception e) {
			result = "2";
			e.printStackTrace();
		} finally {
			request.setAttribute("result", result);
			return "/jxkh/bk/bktjcxListUI";
		}
	}
	
	/**
	 * 获取相应类别的数据字典
	 * @param typeCode
	 * @param dicList
	 * @return
	 */
	private List<Dictionary> getBkdl(String typeCode, List<Dictionary> dicList){
		List<Dictionary> newDicList = new ArrayList<Dictionary>();
		for(int i=0;i < dicList.size();i++){
			Dictionary dic = dicList.get(i);
			if(typeCode != null && typeCode.trim().equals(dic.getTypeCode())){
				newDicList.add(dic);
			}
		}
		return newDicList;
	}
	
	/**
	 * 获取相应的统计数量，没有返回0
	 * @param data
	 * @param bkdl
	 * @param bklb
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getCountValue(List data, String bkdl, String bklb){
		int count = 0;
		for(int i=0;i < data.size();i++){
			Object[] objArr = (Object[])data.get(i);
			if(bkdl != null && bklb != null && bkdl.trim().equals(objArr[0]) && bklb.trim().equals(objArr[1])){
				BigInteger num = (BigInteger)objArr[2];
				if(num != null){
					count = Integer.parseInt(num.toString());
					break;//找到，退出循环
				}
			}
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportExcel")
	@Description(moduleName="布控统计查询",operateType="1",operationName="导出布控统计")
	public void exportExcel(HttpServletRequest request,HttpServletResponse resp, String kssj, String jssj) throws Exception{
		//封装ExcelBean
		BktjExcelBean bean = null;
		User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		List<BktjEntity> bktjList = new ArrayList<BktjEntity>();
		try {
			bean = new BktjExcelBean();
			bean.setStartTime(kssj);
			bean.setEndTime(jssj);
			
			//获取结果
			bktjList = (List<BktjEntity>)request.getSession().getAttribute("bktjList");
			bean.setList(bktjList);
			
			//输出
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			StringBuilder filename = new StringBuilder();//文件名
			filename.append("布控统计查询").append(dateFormat.format(new Date())).append(".xls");
			resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
			//附件形式，并指定文件名
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();//输出流
			jxkhService.excelExportForBktj(user, bean, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//跳转到布控 撤控统计UI
	@RequestMapping(value="findBkckUI")
	public String findBkckUI(HttpServletRequest request, Model model){
		//获取用户信息
		User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		
		//获取操作人考核单位
		List<Department> deptList = getJxkhDept(operUser.getLskhbm());
		Department jxkhBm = null;
		if(deptList != null && deptList.size() > 0){
			jxkhBm = deptList.get(0);
		}
		
		//绩效考核部门
		List<Department> jxkhDeptList = new ArrayList<Department>();
		List<Department> deptsList = getJxkhDepts();
		for(int i=0;i < deptsList.size();i++){
			Department dept = deptsList.get(i);
			
			//市局
			if(operUser.getPosition().length() == 2 && Integer.parseInt(operUser.getPosition()) >= 90){//全部
				jxkhDeptList.add(dept);
			} else if((dept.getDeptNo() != null && operUser.getLskhbm() != null && dept.getDeptNo().trim().equals(operUser.getLskhbm().trim()))
					|| (dept.getSystemNo() != null && jxkhBm != null && jxkhBm.getSystemNo() != null && dept.getSystemNo().trim().indexOf(jxkhBm.getSystemNo().trim()) != -1)){//分局或卡点，单一
				jxkhDeptList.add(dept);
			}
		}
		request.getSession().setAttribute("jxkhDeptListForBkck", jxkhDeptList);//放入session
		model.addAttribute("deptsList", jxkhDeptList);
		
		//获取所有带坐标的监测点
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dayformt = new SimpleDateFormat("yyyy-MM-dd");
		JDateTime jdt = new JDateTime();
		Date nowDate = new Date();
		model.addAttribute("year", jdt.toString("YYYY"));
		model.addAttribute("month", jdt.toString("MM"));
		model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
		model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
		model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
		model.addAttribute("cxlb", "1");
		model.addAttribute("cxlxMap", getCxlxMap());
		model.addAttribute("kssj", dayformt.format(nowDate) + " 00:00:00");
		model.addAttribute("jssj", formatter.format(nowDate));
		return "/jxkh/bk/bkckcxListUI";
	}
	
	//布控撤控统计页面中，点击查询按钮，查询出结果
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping(value="findBkck")
	@Description(moduleName="布控撤控签收统计",operateType="1",operationName="布控撤控签收统计")
	public String findBkck(Model model, Map<String, Object> map, HttpServletRequest request, String kssj, String jssj, String Check_tjbm) throws Exception{
		String result = "1";
		try {
			//绩效考核部门
			List<Department> jxkhDeptList = (List<Department>)request.getSession().getAttribute("jxkhDeptListForBkck");//
			
			//回显查询条件
			map.put("year", request.getParameter("year"));
			map.put("month", request.getParameter("month"));
			map.put("cxrq", request.getParameter("cxrq"));
			map.put("cxlb", request.getParameter("cxlb"));
			map.put("kssj", kssj);
			map.put("jssj", jssj);
			map.put("Check_tjbm", Check_tjbm);
			map.put("cxlxMap", getCxlxMap());
			request.getSession().setAttribute("jxkhDeptListForBkck", jxkhDeptList);//放入session
			model.addAttribute("deptsList", jxkhDeptList);
			
			List<BkckEntity> list = new ArrayList<BkckEntity>();
			list = jxkhService.getBkck(kssj, jssj, Check_tjbm, jxkhDeptList);
			request.getSession().setAttribute("jxkhBkckList", list);//放入session
			
			//封装数据
			BkckListBean bean = new BkckListBean(list);
			model.addAttribute("bean", bean);
		} catch (Exception e) {
			result = "2";
			e.printStackTrace();
		} finally {
			request.setAttribute("result", result);
			return "/jxkh/bk/bkckcxListUI";
		}
	}
	
	//布控撤控统计页面中，点击导出按钮，导出excel文件
	@SuppressWarnings("unchecked")
	@RequestMapping(value="excelExportForBkck")
	@Description(moduleName="布控撤控签收统计",operateType="1",operationName="导出布控撤控签收统计")
	public void excelExportForBkck(HttpServletRequest request, String kssj, String jssj, String Check_tjbm, HttpServletRequest req,HttpServletResponse resp) throws Exception{
		//获取结果
		List<BkckEntity> list = (List<BkckEntity>)request.getSession().getAttribute("jxkhBkckList");//放入session
		
		//封装结果
		BkckListBean bean = new BkckListBean(list);
		BkckExcelBean excelBean = new BkckExcelBean(bean, kssj, jssj, Check_tjbm);
		
		//输出
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		StringBuilder filename = new StringBuilder();//文件名
		filename.append("布控撤控统计查询").append(dateFormat.format(new Date())).append(".xls");
		resp.setContentType("application/ms-excel");//告诉浏览器下载文件的类型
		//附件形式，并指定文件名
		resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.toString().getBytes(), "ISO-8859-1"));
		ServletOutputStream outputStream = resp.getOutputStream();//输出流
		jxkhService.excelExportForBkck(excelBean, outputStream);
	}
	
	public List<Department> getJxkhDepts(){
		String hql = " from Department d where d.jxkh = '1' ";
		return jxkhService.getDeptList(hql, null);
	}
	
	public List<Department> getJxkhDept(String deptNo){
		String hql = " from Department d where d.jxkh = '1' and d.deptNo = '" + deptNo + "' ";
		return jxkhService.getDeptList(hql, null);
	}
	
	//布控通知签收情况详细信息，显示具体信息
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping(value="getBkckInfo")
	@Description(moduleName="布控撤控签收统计",operateType="1",operationName="布控撤控签收详情")
	public String getBkckInfo(HttpServletRequest request, Model model) throws Exception{
		//默认页面
		String page = "/jxkh/bk/bkqsInfoDetail";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<DisReceive> bkDisReceiveList = new ArrayList<DisReceive>();
		List<DisReceive> ckDisReceiveList = new ArrayList<DisReceive>();
		try {
			//获取页面参数
			String kssj = request.getParameter("kssj");
			String jssj = request.getParameter("jssj");
			String infodl = request.getParameter("infodl");
			String infoxl = request.getParameter("infoxl");
			String jxbm = request.getParameter("jxbm");
			
			//获取数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("JXKHTIME,HPZL,YJQRZT,CSYS,BKDL,BKDL1,BKDL2,BKDL3,BKJB,BKFWLX,BKFW,BKXZ,BJYA,BKYWZT,XDZLFS,SFLJ,WLJDYY,CJCZJG");
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicListJSON", JSON.toJSONString(dicList));
			
			//绩效考核时间
			String bkqsTime = "10";
			String ckqsTime = "10";
			for(int i=0;i < dicList.size();i++){
				Dictionary dic = dicList.get(i);
				if("JXKHTIME".equals(dic.getTypeCode()) && "BKQS".equals(dic.getTypeSerialNo())){
					bkqsTime = dic.getTypeDesc();
				} else if("JXKHTIME".equals(dic.getTypeCode()) && "CKQS".equals(dic.getTypeSerialNo())){
					ckqsTime = dic.getTypeDesc();
				}
			}
			
			if(infodl != null && "1".equals(infodl.trim()) 
					&& infoxl != null && "1".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有布控签收数据
				page = "/jxkh/bk/bkqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				bkDisReceiveList = eWarningService.findObjects(" FROM DisReceive where bkckbz = '1' and qrdw = ? and (xfsj between ? and ?) order by xfsj desc ", param);
				//结果
				request.setAttribute("bkDisReceiveList", bkDisReceiveList);
			} else if(infodl != null && "1".equals(infodl.trim()) 
					&& infoxl != null && "2".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有按时签收的布控数据
				page = "/jxkh/bk/bkqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				param.add((Integer.parseInt(bkqsTime) * 60));
				bkDisReceiveList = eWarningService.findObjects(" FROM DisReceive where bkckbz = '1' and qrdw = ? and (xfsj between ? and ?) and qrzt != '0' and TIMESTAMPDIFF(SECOND, xfsj, qrsj) <= ? order by xfsj desc ", param);
				//结果
				request.setAttribute("bkDisReceiveList", bkDisReceiveList);
			} else if(infodl != null && "1".equals(infodl.trim()) 
					&& infoxl != null && "3".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有超时签收的布控数据
				page = "/jxkh/bk/bkqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				param.add((Integer.parseInt(bkqsTime) * 60));
				bkDisReceiveList = eWarningService.findObjects(" FROM DisReceive where bkckbz = '1' and qrdw = ? and (xfsj between ? and ?) and qrzt != '0' and TIMESTAMPDIFF(SECOND, xfsj, qrsj) > ? order by xfsj desc ", param);
				//结果
				request.setAttribute("bkDisReceiveList", bkDisReceiveList);
			} else if(infodl != null && "1".equals(infodl.trim()) 
					&& infoxl != null && "4".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有未签收的布控数据
				page = "/jxkh/bk/bkqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				bkDisReceiveList = eWarningService.findObjects(" FROM DisReceive where bkckbz = '1' and qrdw = ? and (xfsj between ? and ?) and qrzt = '0' order by xfsj desc ", param);
				//结果
				request.setAttribute("bkDisReceiveList", bkDisReceiveList);
			} else if(infodl != null && "2".equals(infodl.trim()) 
					&& infoxl != null && "1".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有撤控签收数据
				page = "/jxkh/bk/ckqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				ckDisReceiveList = eWarningService.findObjects(" FROM DisReceive where bkckbz = '2' and qrdw = ? and (xfsj between ? and ?) order by xfsj desc ", param);
				//结果
				request.setAttribute("ckDisReceiveList", ckDisReceiveList);
			} else if(infodl != null && "2".equals(infodl.trim()) 
					&& infoxl != null && "2".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有按时签收的撤控数据
				page = "/jxkh/bk/ckqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				param.add((Integer.parseInt(ckqsTime) * 60));
				ckDisReceiveList = eWarningService.findObjects(" FROM DisReceive where bkckbz = '2' and qrdw = ? and (xfsj between ? and ?) and qrzt != '0' and TIMESTAMPDIFF(SECOND, xfsj, qrsj) <= ? order by xfsj desc ", param);
				//结果
				request.setAttribute("ckDisReceiveList", ckDisReceiveList);
			} else if(infodl != null && "2".equals(infodl.trim()) 
					&& infoxl != null && "3".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有超时签收的撤控数据
				page = "/jxkh/bk/ckqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				param.add((Integer.parseInt(ckqsTime) * 60));
				ckDisReceiveList = eWarningService.findObjects(" FROM DisReceive where bkckbz = '2' and qrdw = ? and (xfsj between ? and ?) and qrzt != '0' and TIMESTAMPDIFF(SECOND, xfsj, qrsj) > ? order by xfsj desc ", param);
				//结果
				request.setAttribute("ckDisReceiveList", ckDisReceiveList);
			} else if(infodl != null && "2".equals(infodl.trim()) 
					&& infoxl != null && "4".equals(infoxl.trim())){//根据绩效考核部门编码查询该绩效考核部门指定时间范围内的所有未签收的撤控数据
				page = "/jxkh/bk/ckqsInfoDetail";
				List<Object> param = new ArrayList<Object>();
				param.add(jxbm);
				param.add(formatter.parse(kssj));
				param.add(formatter.parse(jssj));
				ckDisReceiveList = eWarningService.findObjects(" FROM DisReceive where bkckbz = '2' and qrdw = ? and (xfsj between ? and ?) and qrzt = '0' order by xfsj desc ", param);
				//结果
				request.setAttribute("ckDisReceiveList", ckDisReceiveList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			return page;
		}
	}
}