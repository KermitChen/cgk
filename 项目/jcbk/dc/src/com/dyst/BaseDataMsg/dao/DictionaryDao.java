package com.dyst.BaseDataMsg.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

public interface DictionaryDao extends BaseDao{
	
	
	//根据ID删除
	public void delete(Serializable id);

	//根据ID查询，基础信息管理详细信息
	public Dictionary findObjectById(Serializable id);
	
	//多条件语句查询
	public List<Object> findObjects(String hql, List<Object> parameters);
	
	//使用 QueryHelper 多条件查询
	public List<Dictionary> findObjects(QueryHelper queryHelper);
	
	//带分页的多条件查询
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize);

	//查询出map结果 jcd
	public Map<String, String> findMapObjects(String hql, List<Object> params);

	//查询出map
	public Map<String,String> findMapObject(String hql,Map<String,Object> params);

	/**
	 * 根据typeCode数组，查询出dicList
	 */
	public List<Dictionary> findDicListByTypeCodeIds(String hql, Map<String, Object> params);

}
