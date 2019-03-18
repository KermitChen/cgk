package com.dyst.mapTrail.service;

import java.util.List;
import java.util.Map;

import com.dyst.mapTrail.entities.MapTrailPoint;
import com.dyst.mapTrail.entities.MapTrailPointKD;

public interface MapService {
	
	//清空map的2张表
//	public void clearAll();//正式项目中禁用
	
	public List<Map> findList(String hql, List<Object> parameters);
	
//	public void save(Object entity);
	
	public List<MapTrailPoint> findMapTrailList(String hql, List<Object> parameters);
	
	//2点路径查询
	public MapTrailPoint findMapTrailById(String start, String end);
	
	public MapTrailPointKD findMapTrailKDById(String start, String end);
}
