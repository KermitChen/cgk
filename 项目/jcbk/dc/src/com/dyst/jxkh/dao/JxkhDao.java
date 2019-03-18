package com.dyst.jxkh.dao;

import java.util.List;
import java.util.Map;

import com.dyst.dispatched.entities.DisReceive;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.systemmanage.entities.Department;

public interface JxkhDao{

	//查询列表
	public List<Dispatched> getList(String hql ,Map<String,Object> params);
	
	//根据sql语句查询列表
	public List getObjects(String sql ,Map<String,Object>params);
	
	//查询统计部门列表
	public List<Department> getDeptList(String hql,Map<String,Object> params);
	
	//查询布控撤控 列表
	public List<DisReceive> getDisReceive(String hql,Map<String,Object> params);
}
