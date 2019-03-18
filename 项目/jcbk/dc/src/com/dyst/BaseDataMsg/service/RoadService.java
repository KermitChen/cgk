package com.dyst.BaseDataMsg.service;

import java.io.Serializable;
import java.util.List;

import com.dyst.BaseDataMsg.entities.Area;
import com.dyst.BaseDataMsg.entities.Road;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

/**
 * 道路信息查询 Service Interface
 * @author LiuQiang
 *
 */
public interface RoadService {
	//增加
	public void addRoad(Road d);
	//更新
	public void updateRoad(Road d);
	//根据ID逻辑删除
	public void deleteRoad(Serializable id);
	//根据ID查询
	public Road findDictionayById(Serializable id);
	
	//根据道路ID查询道路信息
	public Road findRoadByDlId(String dlid);
	
	//多条件语句查询
	public List<Road> findObjects(String hql, List<Object> parameters);
	//利用queryHelper，多条件语句查询
	public 	List<Road> findObjects(QueryHelper queryHelper);
	//带分页的多条件查询
	public 	PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize);
	//多表带分页的多条件查询，查询结果用pageResult封装
	public PageResult getPageResult(String hql, List<Object> params,int pageNo, int pageSize);
	//定时器自动触发
	public void autoDeal();
}
