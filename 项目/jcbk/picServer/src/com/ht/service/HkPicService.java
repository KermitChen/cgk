package com.ht.service;

/**
 * 海康云图片服务层接口
 * @author 
 *
 */
public interface HkPicService {
	/**
	 * 根据图片id获取base编码的图片
	 * @param tpid
	 * @return
	 * @throws Exception
	 */
	public abstract String getPicOfBase64ById(String tpid) throws Exception;
}