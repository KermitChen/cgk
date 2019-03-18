package com.dyst.BaseDataMsg.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.service.TpyzService;
import com.dyst.base.utils.PageResult;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.service.UserService;
import com.dyst.vehicleQuery.service.BDService;

@Controller
@RequestMapping("/tpyz")
public class TpyzController {

	@Autowired
	private TpyzService tpyzService;
	@Autowired
	private UserService userService;
	@Autowired
	private BDService bdService;
	
	private static Logger logger = LoggerFactory.getLogger(TpyzController.class);
	/**
	 * 初始化页面方法
	 * @return
	 */
	@RequestMapping("/pre")
	public String preTpyz(){
		return "/baseDataMsg/tpyz/tpyz";
	}
	@RequestMapping("/param")
	public String turnParam(){
		return "/baseDataMsg/tpyz/param";
	}
	/**
	 * 计算套牌阈值
	 * @return
	 */
	@RequestMapping("/update")
	@Description(moduleName="套牌阈值管理",operateType="3",operationName="计算套牌阈值")
	public void upTpyz(String distance,String speed,HttpServletResponse response){
		String result = "更新成功!";
		double distanceDouble = 0;
		double speedDouble = 0;
		try {
			if(StringUtils.isNotEmpty(distance)){//用户是否输入距离
				distanceDouble = Double.parseDouble(distance);
			} else{//没输入则到数据库查默认值
				List<Dictionary> list = userService.getDictionarysByTypeCode("TPYZCS");
				for (Dictionary dic : list) {
					if(StringUtils.isNotEmpty(dic.getTypeDesc()) && dic.getTypeDesc().indexOf("距离") > 0){
						if(StringUtils.isNotEmpty(dic.getTypeSerialNo())){
							distanceDouble = Double.parseDouble(dic.getTypeSerialNo());
						}
					}
				}
			}
			if(StringUtils.isNotEmpty(speed)){
				speedDouble = Double.parseDouble(speed);
			} else{
				List<Dictionary> list = userService.getDictionarysByTypeCode("TPYZCS");
				for (Dictionary dic : list) {
					if(StringUtils.isNotEmpty(dic.getTypeDesc()) && dic.getTypeDesc().indexOf("速度") > 0){
						if(StringUtils.isNotEmpty(dic.getTypeSerialNo())){
							speedDouble = Double.parseDouble(dic.getTypeSerialNo());
						}
					}
				}
			}
			logger.debug("套牌阈值计算距离：{}，速度：{}", distanceDouble, speedDouble);
			if(distanceDouble > 0 && speedDouble > 0){
				tpyzService.upTpyz(distanceDouble, speedDouble);
			}
		} catch (Exception e) {
			result = "更新失败!";
			e.printStackTrace();
		}finally{
			try {
				response.setContentType("application/text");
				response.getWriter().write(result);
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 分页查询套牌阈值
	 * @param pageNo
	 * @param map
	 * @return
	 */
	@RequestMapping("/find")
	@Description(moduleName="套牌阈值管理",operateType="1",operationName="分页查询套牌阈值")
	public String getTpyzForPage(String pageNo,String jcdid,Map<String, Object> map){
		String page = "/baseDataMsg/tpyz/tpyz";
		try {
			logger.debug("套牌阈值查询.页数：{}，监测点id：{}",pageNo,jcdid);
			int pageNum = 1;
			if(StringUtils.isNotEmpty(pageNo)){
				pageNum = Integer.parseInt(pageNo);
			}
			PageResult pageResult = tpyzService.getTpyzForPage(pageNum, 10, jcdid);
			map.put("pageResult", pageResult);
			map.put("jcdMap", bdService.getJcdMap());
			map.put("jcdid", jcdid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
}