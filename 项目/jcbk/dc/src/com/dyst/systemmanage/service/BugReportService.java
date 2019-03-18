package com.dyst.systemmanage.service;

import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.entities.BugReport;

public interface BugReportService {

	/**
	 * 分页查询问题
	 * @param submitTime 提交时间
	 * @param isDeal  是否已处理
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public PageResult searchBugReportByPage(String beginTime,String endTime,String isDeal,int pageNo,int pageSize)throws Exception;
	/**
	 * 添加问题反馈
	 * @param report
	 * @throws Exception
	 */
	public void addBugReport(BugReport report)throws Exception;
	/**
	 * 更新问题反馈
	 * @param report
	 * @throws Exception
	 */
	public void updateBugReport(BugReport report)throws Exception;
	/**
	 * 删除问题反馈
	 * @param id
	 * @throws Exception
	 */
	public void deleteBugReport(BugReport report)throws Exception;
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BugReport getById(int id)throws Exception;
}
