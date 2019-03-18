package com.dyst.dispatched.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.dispatched.entities.Withdraw;

public interface WithdrawDao extends BaseDao{
	
	
	//根据ID删除
	public void delete(Serializable id);

	//根据ID查询
	public Withdraw findObjectById(Serializable id);
	
	//多条件语句查询
	public List<Object> findObjects(String hql, List<Object> parameters);
	
	//使用 QueryHelper 多条件查询
	public List<Withdraw> findObjects(QueryHelper queryHelper);
	
	//带分页的多条件查询
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize);

	public List<Map> findList(String hql, List<Object> parameters);
}
