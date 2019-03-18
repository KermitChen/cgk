package com.dyst.BaseDataMsg.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dyst.BaseDataMsg.entities.Hmd;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

@Repository(value="hmdDao")
public interface HmdDao extends BaseDao{

	//带分页的多条件查询
	public PageResult getPageResult(String hql, List<Object> params,int pageNo, int pageSize);
	//单表queryHelper 多条件带分页的查询
	public PageResult getPageResult(QueryHelper queryHelper,int pageNo,int pageSize);
	//根据id查找
	public Hmd findHmdById(Serializable id);
}
