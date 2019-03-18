package com.ht.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ht.service.HkPicService;
import com.ht.utils.ResultData;
import com.ht.utils.SystemStatusCode;

/**
 * 海康云图片
 * @author 
 *
 */
@RestController
@RequestMapping("/hkpic")
public class HkPicController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private HkPicService hkPicService;
	
	/**
	 * 根据图片id获取base编码的图片
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getPicOfBase64ById", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public ResultData getPicOfBase64ById(@RequestBody Map<String, Object> params) {
		ResultData resultData = new ResultData("根据图片id获取base编码的图片失败！", SystemStatusCode.SYSTEM_ERROR);//默认失败
		String base64 = "";
		try {
			//获取图片id
			String tpid = (String) params.get("tpid");
			if(StringUtils.isBlank(tpid)) {//参数错误
				resultData.setMsg("图片ID不能为空！");
				resultData.setRet(SystemStatusCode.INVALID_PARAMETER);
				return resultData;
			}
			
			//tpid不为空
			base64 = hkPicService.getPicOfBase64ById(tpid);//海康云存储
			
			//返回
			resultData.setData(base64);
			resultData.setMsg("根据图片id获取base编码的图片成功！");
			resultData.setRet(SystemStatusCode.CALL_SUCCESS);
		} catch (Exception e) {
			resultData.setMsg("根据图片id获取base编码的图片失败！");
			resultData.setRet(SystemStatusCode.SYSTEM_ERROR);
			logger.error("根据图片id获取base编码的图片失败！", e);
		}
		return resultData;
	}
}