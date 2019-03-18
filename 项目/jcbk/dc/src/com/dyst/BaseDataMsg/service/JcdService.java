package com.dyst.BaseDataMsg.service;

import java.util.List;
import java.util.Map;

import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

public interface JcdService {

	/**
	 * 启用停用
	 * @param jcdId 监测点ID
	 * @param qybz  变更后的状态值
	 */
	public void qyty(String jcdId, String qybz) throws Exception;
	
	// hql带分页 多条件语句查询
	public PageResult getPageResult(String hql, List<Object> params,int pageNo, int pageSize);
	//queryHelper带分页的多条件语句查询
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize);
	//查询所有监测点
	public List<Object> findObjects(String hql ,List<Object> params);
	//查询所有监测点
	public List<Object> findObjects2(String hql ,Map<String,Object> params);
	//根据id查询出记录
	public List<Jcd> findObject(String hql,String id);
	//hql  带分页的多条件查询，可以传入 集合作为参数
	public PageResult getPageResult(String hql, Map<String,Object> params,int pageNo, int pageSize) ;
	//查询所有监测点并转换坐标为百度坐标
	public List<Jcd> findAllJcd();
	//根据条件查询监测点
	public List findJcds(String hql, Map<String, Object> params);
	//根据ID查询监测点，并转换坐标
	public Jcd findJcdById(String id);
	
	/**
	 * 根据hql及参数查询总数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int getCountByHql(String hql, Map<String, Object> params) throws Exception;
}