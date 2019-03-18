package com.ht.dao;

import java.util.List;
import com.ht.entities.HkCloudConfig;

public interface HkCloudConfigMapper {
	/**
	 * 获取所有海康云图片配置
	 * @return
	 * @throws Exception
	 */
    public abstract List<HkCloudConfig> getHkCloudConfig() throws Exception;
}