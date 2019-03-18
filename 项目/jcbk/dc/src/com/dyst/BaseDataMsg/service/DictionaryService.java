package com.dyst.BaseDataMsg.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

public interface DictionaryService {
	//增加
	public void addDictionary(Dictionary d);
	//更新
	public void updateDictionary(Dictionary d);
	//删除
	@Deprecated
	public void deleteDictionary(Dictionary d);
	//根据ID逻辑删除
	public void deleteDictionary(Serializable id);
	//根据ID查询
	public Dictionary findDictionayById(Serializable id);
	//多条件语句查询
	public List<Object> findObjects(String hql, List<Object> parameters);
	//利用queryHelper，多条件语句查询
	public 	List<Dictionary> findObjects(QueryHelper queryHelper);
	//带分页的多条件查询
	public 	PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize);
	//查询结果为map..监测点名称  id
	public Map<String,String> findMapObjects(String hql,List<Object> params);
	//
	public Map<String,String> findMapObject(String hql,Map<String,Object> params);
	
	/**
	 * 通过类型代码获取字典数据
	 */
	public List<Dictionary> findDicListByTypeCodeIds(String hql,Map<String,Object>params);
}
