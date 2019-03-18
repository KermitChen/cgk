package com.dyst.mapTrail.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.mapTrail.entities.MapTrailPoint;
import com.dyst.mapTrail.entities.MapTrailPointKD;

public interface MapDao extends BaseDao{
	//清空map的2张表
	public void clearAll();
	public List<Map> findList(String hql, List<Object> parameters);
	public List<MapTrailPoint> findMapTrailList(String sql, List<Object> parameters);
	//2点路径查询
	public MapTrailPoint findMapTrailById(String start, String end);
	public MapTrailPointKD findMapTrailKDById(String start, String end);
}
