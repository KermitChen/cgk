package com.dyst.BaseDataMsg.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dyst.BaseDataMsg.entities.DicDisType;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.service.BkdjService;
import com.dyst.base.utils.PageResult;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.service.UserService;

@Controller
@RequestMapping("/bkdj")
public class BkdjController {

	@Autowired
	private UserService userService;
	@Autowired
	private BkdjService bkdjService;
	/**
	 * 查询页面初始化
	 * @return
	 */
	@RequestMapping("/preFind")
	public String preFind(Map<String, Object> map){
		String page = "/baseDataMsg/bkdj/listUI";
		try {
			List<Dictionary> list = userService.getDictionarysByTypeCode("BKDL");
			map.put("bkdlList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * 分页查询数据
	 * @param bkdl
	 * @param map
	 * @return
	 */
	@RequestMapping("/findBkdj")
	@Description(moduleName="布控等级管理",operateType="1",operationName="分页查询数据")
	public String findByPage(String bkdl,Map<String, Object> map,PageResult pageResult){
		String page = "/baseDataMsg/bkdj/listUI";
		try {
			pageResult = bkdjService.findByPage(bkdl, pageResult.getPageNo(), 10);
			List<Dictionary> list = userService.getDictionarysByTypeCode("BKDL");
			map.put("bkdlList", list);
			map.put("bkdl", bkdl);
			map.put("pageResult", pageResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * 初始化新增页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/preAdd")
	public String preAdd(Map<String, Object> map){
		String page = "/baseDataMsg/bkdj/addUI";
		try {
			List<Dictionary> list = userService.getDictionarysByTypeCode("BKDL");
			map.put("bkdlList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * 判断某字段是否已存在
	 * @param showOrder
	 * @param level
	 * @param id
	 * @param notId 用于更新时判断
	 * @param response
	 */
	@RequestMapping("/isHave")
	@Description(moduleName="布控等级管理",operateType="1",operationName="判断某布控等级是否存在")
	public void isHave(String showOrder,String level,String id,String notId,HttpServletResponse response){
		response.setContentType("application/json");
		PrintWriter writer = null;
		String res = "1";
		try {
			long num = bkdjService.decideIsHave(showOrder, level,id,notId);
			if(num == 0){
				res = "0";
			}
			writer = response.getWriter();
			String jsonData = "{\"res\":\"" + res + "\"}";
			writer.write(jsonData);
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
	 * 添加布控等级
	 * @param response
	 * @param name
	 * @param showOrder
	 * @param level
	 * @param superid
	 * @param id
	 */
	@RequestMapping("/add")
	@Description(moduleName="布控等级管理",operateType="2",operationName="添加布控等级")
	public void addBkdj(HttpServletResponse response,String name,String showOrder,String level,String superid,String id){
		response.setContentType("application/json");
		String res = "0";
		PrintWriter writer = null;
		try {
			if(StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(showOrder) && StringUtils.isNotEmpty(level) && StringUtils.isNotEmpty(superid)
					&& StringUtils.isNotEmpty(id)){
				DicDisType type = new DicDisType(id, name, superid, showOrder, level);
				bkdjService.addBkdj(type);
				res = "1";
			}
			writer = response.getWriter();
			String jsonData = "{\"res\":\"" + res + "\"}";
			writer.write(jsonData);
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
	 * 删除布控等级
	 * @param id
	 * @param response
	 */
	@RequestMapping("/delete")
	@Description(moduleName="布控等级管理",operateType="4",operationName="删除布控等级")
	public void deleteBkdj(String id,HttpServletResponse response){
		response.setContentType("application/json");
		String res = "0";
		PrintWriter writer = null;
		try {
			if(StringUtils.isNotEmpty(id)){
				bkdjService.deleteBkdj(id);
				res = "1";
			}
			writer = response.getWriter();
			String jsonData = "{\"res\":\"" + res + "\"}";
			writer.write(jsonData);
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
	 * 初始化更新页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping("preUpdate")
	public String preUpdate(String id,Map<String, Object> map){
		String page = "/baseDataMsg/bkdj/editUI";
		try {
			DicDisType type = bkdjService.getBkdjById(id);
			List<Dictionary> list = userService.getDictionarysByTypeCode("BKDL");
			map.put("bkdlList", list);
			map.put("type", type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * 更新记录
	 * @param response
	 * @param name
	 * @param showOrder
	 * @param level
	 * @param superid
	 * @param id
	 */
	@RequestMapping("/update")
	@Description(moduleName="布控等级管理",operateType="3",operationName="更新布控等级")
	public void updateBkdj(HttpServletResponse response,String name,String showOrder,String level,String superid,String id){
		response.setContentType("application/json");
		String res = "0";
		PrintWriter writer = null;
		try {
			if(StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(showOrder) && StringUtils.isNotEmpty(level) && StringUtils.isNotEmpty(superid)
					&& StringUtils.isNotEmpty(id)){
				DicDisType type = new DicDisType(id, name, superid, showOrder, level);
				bkdjService.updateBkdj(type);
				res = "1";
			}
			writer = response.getWriter();
			String jsonData = "{\"res\":\"" + res + "\"}";
			writer.write(jsonData);
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