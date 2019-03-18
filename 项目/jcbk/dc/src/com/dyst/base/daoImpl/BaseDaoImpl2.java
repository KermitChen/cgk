package com.dyst.base.daoImpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.dyst.base.dao.BaseDao2;

@Repository("baseDao2")
public abstract class BaseDaoImpl2<T> implements BaseDao2<T>{
	
	/**
	 * 注入 sessionFactory对象便于操作
	 */
	@Resource
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl2(){
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}
	
	public Serializable save(Object entity){
		Session session = getSessionFactory().getCurrentSession();
		Serializable id = session.save(entity);
		session.flush();
		return id;
	}

	public void update(Object entity){
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
		session.flush();
	}

	public void delete(Object entity){
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
		session.flush();
	}
	
	public Object getObjectById(Class<?> entityClass, Serializable id){
		Session session = getSessionFactory().getCurrentSession();
		return session.get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findObjects(String hql, Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		//query传参
		query = this.setParameter(query,params);
		return query.list();
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
}
