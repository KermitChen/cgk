package com.dyst.BaseDataMsg.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.service.DictionaryService;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.User;
import com.dyst.utils.CommonUtils;

@Controller
@RequestMapping("/dic")
public class DictionaryController {

	//注入业务层
	@Autowired
	private DictionaryService dictionaryService;
	
	public static List<Object> cpysList = new ArrayList<Object>();
	public static List<Object> hpzlList = new ArrayList<Object>();
	public static List<Object> spjgList = new ArrayList<Object>();
	public static List<Object> jlztList = new ArrayList<Object>();
	public static List<Object> bkdaList = new ArrayList<Object>();
	public static List<Object> jcdKindList = new ArrayList<Object>();
	public static List<Object> jcdFxList = new ArrayList<Object>();
	public static List<Object> AnnType = new ArrayList<Object>();
	public static List<Object> AnnTime = new ArrayList<Object>();
	public static Map<String, String> jcdFx = new HashMap<String, String>();
	public static Map<String, String> cq = new HashMap<String, String>();
	
	public List<Object> getXXXList(List<Object> xxxList,String hql) throws Exception{
		xxxList.clear();
		xxxList = dictionaryService.findObjects(hql, null);
		return xxxList;
	}
	
	public Map<String,String> getXXXMap(Map<String,String> xxxMap,String hql) throws Exception{
		xxxMap.clear();
		xxxMap = dictionaryService.findMapObject(hql, null);
		return xxxMap;
	}
	
	public static Map<String,String> HQL_MAP;
	static{
		HQL_MAP = new HashMap<String,String>();
		HQL_MAP.put("jlzt", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HmdSpZt' and d.deleteFlag != '1'");
		HQL_MAP.put("spjg", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HmdSpJg' and d.deleteFlag != '1'");
		HQL_MAP.put("dylx", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'DyLx' and d.deleteFlag != '1'");
		HQL_MAP.put("cpys", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = '0002' and d.deleteFlag != '1'");
		HQL_MAP.put("hpzl", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'HPZL' and d.deleteFlag != '1'");
		HQL_MAP.put("bkdl", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'BKDL' and d.deleteFlag != '1'");
		HQL_MAP.put("jcdKind", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'JCDLX' and d.deleteFlag != '1'");
		HQL_MAP.put("AnnType", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'ANNTYPE' and d.deleteFlag != '1'");
		HQL_MAP.put("AnnTime", "select new map(d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc) from Dictionary d where d.typeCode = 'ANNTIME' and d.deleteFlag != '1'");
		HQL_MAP.put("jcdFx", "select d.typeSerialNo as typeSerialNo,d.typeDesc as typeDesc from Dictionary d where d.typeCode = '0039' and d.deleteFlag != '1'");
		HQL_MAP.put("cq", "select a.areano as typeSerialNo, a.areaname as typeDesc from Area a where a.deleteFlag != '1' order by areano");
	}
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping("/addDicUI")
	public String addDicUI(Model model){
		//1.把所有的种类查询出来放到前台页面中去(hql去重查询)
		String hql = "select distinct new map (d.typeCode as typeCode ,d.memo as memo) from Dictionary d where d.deleteFlag != '1' order by d.typeCode asc";
		List<Object> kindSet = dictionaryService.findObjects(hql, null);
		model.addAttribute("kindSet", kindSet);
		return "/baseDataMsg/addUI";
	}                         
	
	/**
	 * 添加基础信息的Action方法
	 * @param dic
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addDic")
	@Description(moduleName="数据字典管理",operateType="2",operationName="新增数据字典")
	public String addDic(Dictionary dic,Model model,HttpSession session) throws Exception{
		/*
		 * 业务逻辑处理
		 * 1.添加最后修改人
		 * 2.添加最后修改时间
		 */
		dic.setAddTime(new Timestamp(new Date().getTime()));
		User user = (User) session.getAttribute("USER_OBJ");
		dic.setLoginName(user.getLoginName());
		dic.setAddOperator(user.getUserName());
		//设置删除标志为 未删除
		dic.setDeleteFlag("0");
		//设置编辑标志为 可编辑
		dic.setEditFlag("1");
		//保存用户
		dictionaryService.addDictionary(dic);
		return "redirect:/dic/findDic.do";
	}
	
	/**
	 * 带分页的单表多条件查询
	 * @param pageResult
	 * @param model
	 * @param kind
	 * @param code
	 * @return pageResult
	 */
	@RequestMapping("/findDic")
	@Description(moduleName="数据字典管理",operateType="1",operationName="查询数据字典")
	public String findDictionary(PageResult pageResult, Model model, String kind, String memo, 
			String typeSerialNo, String typeDesc){
		//1.把所有的种类查询出来放到前台页面中去(hql去重查询)
		String hql = "select distinct new map (d.typeCode as typeCode ,d.memo as memo) from Dictionary d where d.deleteFlag != '1' order by d.typeCode asc";
		List<Object> kindSet = dictionaryService.findObjects(hql, null);
		model.addAttribute("kindSet", kindSet);
		
		//过滤特殊字符
		memo = CommonUtils.keyWordFilter(memo);//
		typeSerialNo = CommonUtils.keyWordFilter(typeSerialNo);//
		typeDesc = CommonUtils.keyWordFilter(typeDesc);//
		
		//查询删除标志不为1的基础数据
		QueryHelper queryHelper = new QueryHelper(Dictionary.class, "d");
		queryHelper.addCondition(" d.deleteFlag != ? ", "1");
		//获取前台页面要查询的类别种类
		if(kind != null && !"".equals(kind.trim())){
			queryHelper.addCondition(" d.typeCode = ? ", kind);
		}
		if(memo != null && !"".equals(memo.trim())){
			queryHelper.addCondition(" d.memo like ? ", "%" + memo + "%");
		}
		if(typeSerialNo != null && !"".equals(typeSerialNo.trim())){
			queryHelper.addCondition(" d.typeSerialNo like ? ", "%" + typeSerialNo + "%");
		}
		if(typeDesc != null && !"".equals(typeDesc.trim())){
			queryHelper.addCondition(" d.typeDesc like ? ", "%" + typeDesc + "%");
		}
		//按最后修改时间降序
		queryHelper.addOrderByProperty(" d.typeCode ", QueryHelper.ORDER_BY_ASC);
		pageResult = dictionaryService.getPageResult(queryHelper, pageResult.getPageNo(), 10);
		
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("kind", kind);
		model.addAttribute("memo", memo);
		model.addAttribute("typeSerialNo", typeSerialNo);
		model.addAttribute("typeDesc", typeDesc);
		return "/baseDataMsg/listUI";
	}
	
	/**
	 * 跳转到更新基础数据页面
	 * @return 基础数据页面逻辑地址
	 */
	@RequestMapping(value="updateDicUI")
	public String updateDictionaryUI(Dictionary dic,Model model,HttpServletRequest req){
		/*
		 * 1.根据ID查询出该id对应的dic信息,并回显
		 */
		String id = CommonUtils.keyWordFilter(req.getParameter("id"));
		Dictionary dictionary = dictionaryService.findDictionayById(Integer.parseInt(id));
		model.addAttribute("dic", dictionary);
		return "/baseDataMsg/editUI";
	}
	
	
	/**
	 * 更新字典表Action
	 * @param dic
	 * @param model
	 * @return
	 */
	@RequestMapping(value="updateDic")
	@Description(moduleName="数据字典管理",operateType="3",operationName="修改数据字典")
	public String updateDictionary(Dictionary dic, Model model,HttpServletRequest req,HttpSession session){
		/*
		 * 业务逻辑处理
		 * 	1.修改最后修改时间字段为当前
		 * 	2.修改最后修改人为当前操作者
		 *  3.设置删除标志为0   未删除
		 */
		User user = (User) session.getAttribute("USER_OBJ");
		dic.setAddOperator(user.getUserName());
		dic.setAddTime(new Timestamp(new Date().getTime()));
		dic.setDeleteFlag("0");
		dictionaryService.updateDictionary(dic);
		return "redirect:/dic/findDic.do";
	}
	
	/**
	 * 根据前台页面传过来的DictionaryID，来删除某一条基础数据
	 * @param id
	 * @return 重定向到列表页面
	 */
	@RequestMapping(value="deleteDic")
	@Description(moduleName="数据字典管理",operateType="4",operationName="删除数据字典")
	public String deleteDic(String kind,String code,Model model,HttpServletRequest req){
		String id = CommonUtils.keyWordFilter(req.getParameter("id"));
		dictionaryService.deleteDictionary(Integer.parseInt(id));
		model.addAttribute("kind", kind);
		model.addAttribute("code", code);
		return "redirect:/dic/findDic.do";
	}
	/**
	 * 批量删除基础信息条目
	 * @param ids 条目id数组
	 * @param kind 查询条件：分类类型
	 * @param code 查询条件：分类序号
	 * @param model 向前台回显数据模型
	 * @return 转发到列表页面
	 */
	@RequestMapping(value="deleteDics")
	@Description(moduleName="数据字典管理",operateType="4",operationName="批量删除数据字典")
	public String BatchesDeleteDic(int[] ids ,String kind,String code,Model model){
		for(int id:ids){
			dictionaryService.deleteDictionary(id);
		}
		model.addAttribute("kind", kind);
		model.addAttribute("code", code);
		return "redirect:/dic/findDic.do";
	}
}