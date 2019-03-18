package com.dyst.systemmanage.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.entities.Announcement;
import com.dyst.systemmanage.entities.Message;
import com.dyst.systemmanage.entities.User;

public interface SysAnnService {

	public void addSysAnn(Announcement a);
	
	public PageResult getAnnforPage(String loginName, String fileName, String annType,
			String startTime, String endTime, int pageNo, int pageSize) throws Exception;
	public Announcement getObjectById(Serializable id);
	//更新
	public int update(Announcement a);
	//删除一条
	public int deleteOneById(String id);
	//批量删除
	public int batcheDeleteObj(String[] ids);
	
	//查询每一个用户已读公告的ids
	public void countUsersHasReadAnn();
	
	//查询所有的公告ids
	public List<Object> getAllAnnIds(String hql);
	//查询所有的公告ids
	public List<Announcement> getAllAnnIds2(String hql);
	//更新用于
	public int updateUser(User u);
	/**
	 * 普通用户，查询待阅读的系统公告PageResult
	 */
	public PageResult getAnnforPageAndNormalUser(String loginName, String fileName, String annType,
			String startTime, String endTime, int pageNo, int pageSize,String deptID) throws Exception;

	public List<Message> getEnabledMessage(String hql_myMessage,Map<String,Object> params);
	
	public PageResult getPageResult(String hql,Map<String,Object> params,int pageNo,int pageSize);

	public void markUserAllTzzxHasRed(String loginName);
	/**
	 * 新增一条  通知消息
	 */
	public int saveOneMessage(Message message);
	/**
	 * 根据id查询出一条message消息
	 */
	public Message getOneMessgeById(Serializable id);
	
}
