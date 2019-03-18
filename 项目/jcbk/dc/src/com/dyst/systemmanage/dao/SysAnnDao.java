package com.dyst.systemmanage.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.entities.Announcement;
import com.dyst.systemmanage.entities.Message;


public interface SysAnnDao extends BaseDao{

	//保存新增的系统消息
//	public void addSysAnn(Announcement a);
	
	//分页查询
	public PageResult getAnnforPage(String loginName, String fileName, String annType,
			String startTime, String endTime, int pageNo, int pageSize) throws Exception;
	//查询list
	public List<Object> getList(String hql);
	//查询list
	public List<Announcement> getList2(String hql);

	//带部门的分页查询
	public PageResult getAnnforPageAndNormalUser(String loginName,
			String fileName, String annType, String startTime, String endTime,
			int pageNo, int pageSize, String deptID) throws Exception;
	
	/**
	 * 系统 向在线的用户 推送站内信消息
	 * @param params 
	 */
	public List<Message> getMessages(String hql, Map<String, Object> params);
	/**
	 * 带分页的 查询 用户待查看的  通知消息
	 */
	public PageResult getPageResult(String hql, Map<String, Object> params,
			int pageNo, int pageSize);
	/**
	 * 标记所有的  通知消息为  已读
	 */
	public void markUserAllTzzxHasRed(String loginName);
	/**
	 * 根据id查询出一条message消息
	 */
	public Message getOneMessageById(Serializable id);
}
