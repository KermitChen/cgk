package com.dyst.BaseDataMsg.dao;

import java.io.Serializable;
import java.util.List;

import com.dyst.BaseDataMsg.entities.Area;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

public interface AreaDao extends BaseDao {

	//根据ID删除
	public void delete(Serializable id);
	
	//根据ID查询，基础信息管理详细信息
	public Area findObjectById(Serializable id);
	
	//根据城区ID查询，基础信息管理详细信息
	public Area findAreaByCqId(String cqid);
	
	//带分页的多条件查询
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize);

	//根据地区名称,模糊查询
	public Area findAreaByName(String areaName);
	
	//根据superAreaNo查询出所有的子节点
	public List<Area> findAreasById(Serializable id);
	
	//多条件语句查询
	public List<Area> findObjects(String hql, List<Object> parameters);
}
