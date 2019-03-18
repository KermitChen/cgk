package com.dyst.base.dao;

import java.io.Serializable;
import java.util.Date;

/*******
 * 持久层基类
 * 主要接口：
 * 		1.获取数据库时间
 *      2.添加数据
 *      3.更新数据
 *      4.删除数据
 *      5.根据Id获取数据
 */
public interface BaseDao {
	/**
	 * 获取数据库时间
	 * @param pattern 格式字符串
	 * @throws ParseException 
	 */
	public Date getSysDate(String pattern) throws Exception;
	
	/***************************************************************************
	 * 添加数据
	 **************************************************************************/
	public abstract Serializable save(Object entity);

	/***************************************************************************
	 * 更新数据
	 **************************************************************************/
	public abstract void update(Object entity);

	/***************************************************************************
	 * 删除数据
	 **************************************************************************/
	public abstract void delete(Object entity);
	
	/***************************************************************************
	 * 根据Id获取数据
	 **************************************************************************/
	public abstract Object getObjectById(Class<?> entityClass, Serializable id);

}