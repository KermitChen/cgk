package com.dyst.BaseDataMsg.service;

import java.io.Serializable;
import java.util.List;

import com.dyst.BaseDataMsg.entities.Area;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

public interface AreaService {

	//添加城区信息
	public void addArea(Area area);
	
	//更新城区信息
	public void updateArea(Area area);
	
	//根据ID逻辑删除城区信息
	public void deleteArea(Serializable id);
	
	//根据ID查询城区信息
	public Area findAreaById(Serializable id);
	
	//根据城区ID查询城区信息
	public Area findAreaByCqId(String cqid);
	
	//根据城区名称模糊查询城区信息
	public Area findAreaByName(String areaName);
	
	//带分页的多条件查询,返回pageBean
	public 	PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize);
	
	public List<Area> findAreasById(Serializable id);
	
	//多条件语句查询
	public List<Area> findObjects(String hql, List<Object> parameters);
}
