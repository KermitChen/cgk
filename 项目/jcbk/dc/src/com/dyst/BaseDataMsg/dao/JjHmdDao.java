package com.dyst.BaseDataMsg.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.dao.BaseDao2;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

public interface JjHmdDao extends BaseDao2<Jjhomd>{

	//带分页的多条件查询
	public PageResult getPageResult2(String hql, Map<String,Object> params,int pageNo, int pageSize) ;
	public PageResult getPageResult(String hql, List<Object> params,int pageNo, int pageSize);
	//单表queryHelper 多条件带分页的查询
	public PageResult getPageResult(QueryHelper queryHelper,int pageNo,int pageSize);
	//根据id查找
	public Jjhomd findHmdById(Serializable id);
	//根据号牌号码模糊查询
	public List wildFindHmd(String hphm) throws Exception;
	//查询该车是否是生效红名单
	public Boolean isHmdByHphm(String hphm,String hpzl);
	//查询列表
	
}
