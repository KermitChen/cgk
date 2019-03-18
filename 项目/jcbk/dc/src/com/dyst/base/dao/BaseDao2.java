package com.dyst.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author LIU QIANG
 */
public interface BaseDao2<T> {
	
	//hql，map parameter查询列表
	public List<T> findObjects(String hql, Map<String,Object> parameters);
	public abstract Serializable save(Object entity);

	public abstract void update(Object entity);

	public abstract void delete(Object entity);
	
	public abstract Object getObjectById(Class<?> entityClass, Serializable id);
}
