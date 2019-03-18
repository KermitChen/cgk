package com.dyst.DyMsg.daoImpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.DyMsg.dao.DySqDao;
import com.dyst.DyMsg.entities.Dyjg;
import com.dyst.DyMsg.entities.Dyxx;
import com.dyst.DyMsg.entities.Dyxxsp;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.dispatched.entities.Dispatched;

@Repository(value="dysqDao")
public class DySqDaoImpl extends BaseDaoImpl implements DySqDao{

	//hql 带分页的多条件语句查询
	public PageResult getPageResult(String hql, Map<String,Object> params,int pageNo, int pageSize) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query,params);
		if(pageNo < 1) pageNo = 1;
		query.setFirstResult((pageNo-1)*pageSize);//设置数据起始索引号
		query.setMaxResults(pageSize);
		List items = query.list();
		//获取总记录数
		Query queryCount = session.createQuery(hql);
		queryCount = setParameter(queryCount,params);
		int totalCount = queryCount.list().size();
		return new PageResult(totalCount, pageNo, pageSize, items);	
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
	
	//查询列表
	public List<Object> getList(String hql ,Map<String,Object> params){
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, params);
		List<Object> items = query.list();
		return items;
	}
	
	@Override
	public List<Dyxx> findExcelList(String hql, Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, params);
		List<Dyxx> list = query.list();
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public Dyxx findDyxxById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Dyxx where id = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return (Dyxx) query.uniqueResult();
	}

	@Override
	public Dyxxsp findDyxxspById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Dyxxsp where dyxxId2 = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return (Dyxxsp) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dyxx> getList2(String hql) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		List<Dyxx> list = query.list();
		return list;
	}

	@Override
	public List<Dyjg> getList3(String hql, Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, params);
		List<Dyjg> list = query.list();
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	
}
