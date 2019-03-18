package com.dyst.jxkh.daoImpl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.dispatched.entities.DisReceive;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.jxkh.dao.JxkhDao;
import com.dyst.systemmanage.entities.Department;

@Repository(value="jxkhDao")
public class JxkhDaoImpl extends BaseDaoImpl implements JxkhDao{

	@Override
	//查询列表
	public List<Dispatched> getList(String hql ,Map<String,Object> params){
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, params);
		List<Dispatched> items = query.list();
		return items;
	}
	
	//查询部门列表
	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getDeptList(String hql, Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, params);
		List<Department> items = query.list();
		if(items!=null&&items.size()>0){
			return items;
		}
		return null;
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
	
	@Override
	public List getObjects(String sql, Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
	    Query query = session.createSQLQuery(sql);
	    query = setParameter(query,params);
		return query.list();
	}

	@Override
	public List<DisReceive> getDisReceive(String hql, Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
	    Query query = session.createQuery(hql);
	    query = setParameter(query,params);
	    List<DisReceive> list = query.list();
	    if(list==null||list.size()==0){
	    	return null;
	    }
		return list;
	} 
}