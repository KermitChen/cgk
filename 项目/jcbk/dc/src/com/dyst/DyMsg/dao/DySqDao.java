package com.dyst.DyMsg.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dyst.DyMsg.entities.Dyjg;
import com.dyst.DyMsg.entities.Dyxx;
import com.dyst.DyMsg.entities.Dyxxsp;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;

public interface DySqDao extends BaseDao{
	//hql  带分页的多条件查询，可以传入 集合作为参数
	public PageResult getPageResult(String hql, Map<String,Object> params,int pageNo, int pageSize) ;
	//查询列表
	public List<Object> getList(String hql ,Map<String,Object> params);
	public Dyxx findDyxxById(Serializable id);
	public Dyxxsp findDyxxspById(Serializable id);
	//查询列表
	public List<Dyxx> getList2(String hql );
	//查询列表
	public List<Dyjg> getList3(String hql,Map<String,Object> params);
	public List<Dyxx> findExcelList(String hql, Map<String, Object> params);
}
