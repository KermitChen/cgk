package com.dyst.workflow.dao;


import com.dyst.base.dao.BaseDao;

public interface WorkflowDao extends BaseDao{

	//根据typeCode和serialNo 查询值
	public String findKeyById(String typeCode,String id);
	
}
