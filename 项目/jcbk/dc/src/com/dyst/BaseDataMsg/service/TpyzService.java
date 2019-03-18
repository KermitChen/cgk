package com.dyst.BaseDataMsg.service;

import com.dyst.base.utils.PageResult;

public interface TpyzService {

	/**
	 * 更新套牌阈值表
	 * @param distance	距离
	 * @param speed	速度
	 * @return
	 */
	public abstract void upTpyz(double distance,double speed)throws Exception;
	/**
	 * 分页查询
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public abstract PageResult getTpyzForPage(int pageNo,int pageSize,String jcdid)throws Exception;
}
