package com.ht.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ht.dao.HkCloudConfigMapper;
import com.ht.entities.HkCloudConfig;

/**
 * 定时任务 -- 定时加载海康图片云配置
 */
@Component
public class HkCloudSchedule {
	private Logger logger = LoggerFactory.getLogger(HkCloudSchedule.class);
	
	public static Map<String, HkCloudConfig> hkCloudConfigMap = new HashMap<String, HkCloudConfig>();

	@Resource
    private HkCloudConfigMapper hkCloudConfigMapper;
	
	//执行状态  true 正在执行   false
	public boolean executeFlag = false;
	
	/**
	 * 定时加载海康图片云配置
	 */
//	@Scheduled(cron = "0 */55 * * * ?")
	@Scheduled(initialDelay = 10 * 1000, fixedRate = 55 * 60 * 1000)
	public void hkCloudJob() {
		//判断是否在执行，未执行，则进行
		if(!executeFlag) {
			//更改状态
			executeFlag = true;
			
			//加载云配置
			try {
				List<HkCloudConfig> list = hkCloudConfigMapper.getHkCloudConfig();
				//组装成map
				if(list != null && list.size() > 0) {
					//清空map
					//hkCloudConfigMap.clear();
					
					//重新组装
					for(HkCloudConfig config : list) {
						if(StringUtils.isNotBlank(config.getId())) {
							hkCloudConfigMap.put(config.getId().trim(), config);
						}
					}
				}
				logger.info("定时加载海康图片云配置成功！" + JSON.toJSONString(list));
			} catch (Exception e) {
				logger.error("定时加载海康图片云配置失败！", e);
				e.printStackTrace();
			}
			
			//执行完成
			executeFlag = false;
		}
	}
	
	/**
	 * 根据图片服务id返回图片存放路径
	 */
	public static String getTpcfljById(String id) {
		if(id == null || "".equals(id)) {
			return "";
		}

		HkCloudConfig hkCloudConfig = hkCloudConfigMap.get(id);
		if(hkCloudConfig != null) {
			return "http://" + hkCloudConfig.getIp() + ":" + hkCloudConfig.getPort() + "/";
		}
		return "";
	}
}