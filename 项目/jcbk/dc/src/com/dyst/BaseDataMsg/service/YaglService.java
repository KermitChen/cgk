package com.dyst.BaseDataMsg.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dyst.BaseDataMsg.entities.Yacs;
import com.dyst.base.utils.PageResult;

public interface YaglService {
	//根据hql语句 带分页的多条件语句查询
	public PageResult getPageResult(String hql, List<Object> params,int pageNo, int pageSize) ;
	//保存预案信息
	public int addYa(Yacs y);
	//根据id删除一条预案信息
	public void deleteYa(Serializable id);
	//根据ID查询，基础信息管理详细信息
	public Yacs findObjectById(Serializable id);
	//多条件语句查询
	public List<Object> findObjects(String hql, List<Object> parameters);
	//更新预案信息
	public int udpateYa(Yacs y);
	//根据预案种类和预案等级查询预案
	public Yacs findYacsByType(String bklb,String bjlx);
	/**
	 * 查询yacsList列表
	 */
	public List<Yacs> getList(String hql,Map<String,Object> params);
}
