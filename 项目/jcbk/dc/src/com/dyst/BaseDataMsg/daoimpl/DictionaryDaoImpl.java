package com.dyst.BaseDataMsg.daoimpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dyst.BaseDataMsg.dao.DictionaryDao;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

@Repository("dictionaryDao")
public class DictionaryDaoImpl extends BaseDaoImpl implements DictionaryDao {
	

	//根据ID 逻辑删除基础数据
	public void delete(Serializable id) {
		Dictionary dic = findObjectById(id);
		dic.setDeleteFlag("1");
		update(dic);
	}
	//根据ID 查询
	public Dictionary findObjectById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Dictionary where dictionaryid = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return (Dictionary) query.list().get(0);
	}
	//多条件语句查询
	public List<Object> findObjects(String hql, List<Object> parameters) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if(parameters !=null){
			for(int i = 0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	//使用 QueryHelper 多条件查询
	public List<Dictionary> findObjects(QueryHelper queryHelper) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(queryHelper.getQueryListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters !=null){
			for(int i = 0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	//带分页的多条件查询
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(queryHelper.getQueryListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		if(pageNo < 1) pageNo = 1;
		
		query.setFirstResult((pageNo-1)*pageSize);//设置数据起始索引号
		query.setMaxResults(pageSize);
		List items = query.list();
		//获取总记录数
		Query queryCount = session.createQuery(queryHelper.getQueryCountHql());
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				queryCount.setParameter(i, parameters.get(i));
			}
		}
		long totalCount = (Long)queryCount.uniqueResult();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}
	@Override
	public Map<String, String> findMapObjects(String hql, List<Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if(params !=null){
			for(int i = 0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		@SuppressWarnings("unchecked")
		List<Jcd> list = query.list();
		Map<String,String> jcds = new HashMap<String, String>();
		for(Jcd j: list){
			jcds.put(j.getId(), j.getJcdmc());
		}
		return jcds;
	}
	
	@Override
	public Map<String, String> findMapObject(String hql,
			Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, params);
		Map<String,String> map = new HashMap<String, String>();
		List<Object> list = query.list();
		for(Object o:list){
			 String key = (String) ((Object[])o)[0];
			 String value = (String) ((Object[])o)[1];
			 map.put(key, value);
		}
		return map;
	}
	
	//私有的给hql传参的方法
	private Query setParameter(Query query, Map<String, Object> map) {  
        if (map != null) {  
            Set<String> keySet = map.keySet();  
            for (String string : keySet) {  
                Object obj = map.get(string);  
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
                if(obj instanceof Collection<?>){  
                    query.setParameterList(string, (Collection<?>)obj);  
                }else if(obj instanceof Object[]){  
                    query.setParameterList(string, (Object[])obj);  
                }else{   
                    query.setParameter(string, obj);  
                }  
            }  
        }  
        return query;  
    }
	
	/**
	 * 根据typeCode数组，查询出dicList
	 */
	@Override
	public List<Dictionary> findDicListByTypeCodeIds(String hql, Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, params);
		List<Dictionary> list = query.list();
		if(list!=null&&list.size()>=1){
			return list;
		}
		return null;
	} 
	

}
