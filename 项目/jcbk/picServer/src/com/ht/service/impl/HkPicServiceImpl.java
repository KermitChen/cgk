package com.ht.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ht.schedule.HkCloudSchedule;
import com.ht.service.HkPicService;
import com.ht.utils.ImageUtils;

/**
 * 海康云图片服务层实现
 * @author 
 *
 */
@Service
public class HkPicServiceImpl implements HkPicService {
	/**
	 * 根据图片id获取base编码的图片
	 * @param tpid
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getPicOfBase64ById(String tpid) throws Exception {
		String base64 = "";
		if (StringUtils.isNotBlank(tpid)) {
			//获取图片服务id
			String tpfwid = tpid.substring(tpid.lastIndexOf("_") + 1, tpid.length());
			
			//根据图片服务id，获取图片云服务地址
			String httpStr = HkCloudSchedule.getTpcfljById(tpfwid);
			if(StringUtils.isNotBlank(httpStr)) {
				//拼接成访问路径
				httpStr = httpStr + tpid.substring(0, tpid.lastIndexOf("_"));//远程图片目录
				
				//根据url读取图片的base64编码值
				base64 = ImageUtils.getBase64FromUrl(httpStr);
			}
		}
		return base64;//图片的base64编码值
	}
}