package com.dyst.BaseDataMsg.service;

import java.io.Serializable;
import java.util.List;

import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.systemmanage.entities.User;

public interface JjHmdSpService {

	public PageResult getPageResult(String hql, List<Object> params,int pageNo, int pageSize);
	
	public PageResult getPageResult(QueryHelper queryHelper,int pageNo,int pageSize);

	public Jjhomd getHmdById(Serializable id);
	/**
	 * 查看代办审批任务
	 */
	public PageResult findTodoSpTasks(User user, int pageNo, int pageSize);
	/**
	 * 查看代办审批的任务的数量
	 */
	public int findCountTodoSpTasks(User user);
	/**
	 * 查看待办任务的数量
	 */
	public int findCountTodoSpTasks2(String userId);
}
