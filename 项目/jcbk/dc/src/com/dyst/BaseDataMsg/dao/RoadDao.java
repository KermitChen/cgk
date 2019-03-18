package com.dyst.BaseDataMsg.dao;

import java.io.Serializable;
import java.util.List;

import com.dyst.BaseDataMsg.entities.Road;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

/**
 * 道路信息查询DAO接口
 * @author liuqiang
 * @date 2016.03.18
 */
public interface RoadDao extends BaseDao {

	//根据ID删除
	public void delete(Serializable id);
	
	//根据ID查询，基础信息管理详细信息
	public Road findObjectById(Serializable id);
	
	//根据道路ID查询道路信息
	public Road findRoadByDlId(String dlid);
	
	//多条件语句查询
	public List<Road> findObjects(String hql, List<Object> parameters);
	
	//使用 QueryHelper 多条件查询
	public List<Road> findObjects(QueryHelper queryHelper);
	
	//带分页的多条件查询
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize);

	//String hql 带分页的多条件查询
	public PageResult getPageResult(String hql, List<Object> params, int pageNo,int pageSize);
}
