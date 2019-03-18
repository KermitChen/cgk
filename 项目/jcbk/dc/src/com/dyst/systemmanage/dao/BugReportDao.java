package com.dyst.systemmanage.dao;

import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;

public interface BugReportDao extends BaseDao{

	/**
	 * 分页查询问题
	 * @param submitTime 提交时间
	 * @param isDeal  是否已处理
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public PageResult searchBugReportByPage(String beginTime,String endTime,String isDeal,int pageNo,int pageSize)throws Exception;
}
