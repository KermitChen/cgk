package com.dyst.log.service;

import com.dyst.base.utils.PageResult;

public interface LogService {

	/**
	 * 查询业务日志
	 * @param pageNo
	 * @param pageSize
	 * @param kssj
	 * @param jssj
	 * @return
	 * @throws Exception
	 */
	public abstract PageResult searchBusinessLog(int pageNo,int pageSize, String operator, 
			String operaterName, String operatorIp, String moduleName, String operateName, 
			String kssj, String jssj)throws Exception;
	/**
	 * 根据id查询业务日志
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String getBusinessLogById(String id)throws Exception;
}
