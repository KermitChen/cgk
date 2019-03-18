package com.dyst.BaseDataMsg.daoimpl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.BaseDataMsg.dao.JcdDao;
import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
@Repository("jcdDao")
public class JcdImpl extends BaseDaoImpl implements JcdDao {

	/**
	 * 启用停用
	 * @param jcdId 监测点ID
	 * @param qybz  变更后的状态值
	 */
	public void qyty(String jcdId, String qybz) throws Exception{
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" update jcd set qybz=:qybz where id=:id ");
		query.setParameter("qybz", qybz);
		query.setParameter("id", jcdId);
		
		//执行
		query.executeUpdate();
		session.flush();
	}
	
	//hql 带分页的多条件语句查询
	public PageResult getPageResult(String hql, List<Object> params,int pageNo, int pageSize) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if(params!= null){
			for(int i = 0; i < params.size(); i++){
				query.setParameter(i, params.get(i));
			}
		}
		if(pageNo < 1) pageNo = 1;
		
		query.setFirstResult((pageNo-1)*pageSize);//设置数据起始索引号
		query.setMaxResults(pageSize);
		List items = query.list();
		//获取总记录数
		Query queryCount = session.createQuery("select count(*) "+hql);
		if(params != null){
			for(int i = 0; i < params.size(); i++){
				queryCount.setParameter(i, params.get(i));
			}
		}
		long totalCount = (Long) queryCount.uniqueResult();
		return new PageResult(totalCount, pageNo, pageSize, items);	
	}
	
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
		Query queryCount = session.createQuery("select count(*) "+ hql);
		queryCount = setParameter(queryCount,params);
		long totalCount = (Long) queryCount.uniqueResult();
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

	//利用QueryHelper进行带分页的多条件语句查询
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

	//根据条件查询列表
	public List<Object> findObjects(String hql, List<Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql).setCacheable(true);
		if(params !=null){
			for(int i = 0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		return query.list();
	}
	
	//根据id查询出一条记录
	public List<Jcd> findObject(String hql ,String id){
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if(id!=null){
			query.setParameter(0, id);
		}
		return query.list();
	}

	@Override
	public List<Object> findObjects2(String hql, Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, params);
		query.setCacheable(true);
		List<Object> items = query.list();
		return items;
	}

	@Override
	public List<Jcd> findAllJcd() {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "from Jcd WHERE JD IS NOT NULL AND JD != '0'";
		Query query = session.createQuery(hql);
		return query.list();
	}
	//根据ID查询监测点
	@Override
	public Jcd findJcdById(String id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "from Jcd WHERE id = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return (Jcd) query.uniqueResult();
	}
	//hql 带分页的多条件语句查询
	public List findJcds(String hql, Map<String,Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query,params);
		return query.list();
	}
	
	/**
	 * 根据hql及参数查询总数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int getCountByHql(String hql, Map<String, Object> params) throws Exception{
		Session session = getSessionFactory().getCurrentSession();
		//获取总记录数
		Query queryCount = session.createQuery(hql);
		if(params != null){
			for(int i=0;i < params.size();i++){
				queryCount.setParameter(i, params.get(i));
			}
		}
		return Integer.parseInt(""+(Long)queryCount.uniqueResult());
	}
}