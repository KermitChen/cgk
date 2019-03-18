package com.dyst.BaseDataMsg.dao;

import java.io.Serializable;
import java.util.List;

import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.BaseDataMsg.entities.JjhomdCx;
import com.dyst.BaseDataMsg.entities.Jjhomdsp;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

public interface JjHmdSpDao extends BaseDao{

	//带分页的多条件查询
	public PageResult getPageResult(String hql, List<Object> params,int pageNo, int pageSize);
	//单表queryHelper 多条件带分页的查询
	public PageResult getPageResult(QueryHelper queryHelper,int pageNo,int pageSize);
	//根据id查找
	public Jjhomd findHmdById(Serializable id);
	//根据id查询审批红名单
	public Jjhomd findHmdSpById(Serializable id);
	//根据id查询撤销红名单
	public Jjhomd finHmdCxById(Serializable id);
}
