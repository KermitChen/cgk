package com.dyst.BaseDataMsg.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dyst.BaseDataMsg.entities.Yacs;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;

public interface YaglDao extends BaseDao{
	//hql带分页的 多条件语句查询
	public PageResult getPageResult(String hql, List<Object> params,int pageNo, int pageSize) ;
	
	//根据ID删除
	public void deleteYa(Serializable id);

	//根据ID查询，基础信息管理详细信息
	public Yacs findObjectById(Serializable id);
	
	//多条件语句查询
	public List<Object> findObjects(String hql, List<Object> parameters);

	public Yacs findYacsByType(String yadj,String bjlx);
	/**
	 * 根据hql查询出列表
	 */
	public List<Yacs> getList(String hql, Map<String, Object> params);
	
	public void updateYacs(Yacs y);
	
}
