package com.dyst.dispatched.daoimpl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.dispatched.dao.WithdrawDao;
import com.dyst.dispatched.entities.Withdraw;

@Repository("withdrawDao")
public class WithdrawDaoImpl extends BaseDaoImpl implements WithdrawDao {
	

	//根据ID 逻辑删除基础数据
	public void delete(Serializable id) {
		Withdraw dic = findObjectById(id);
		update(dic);
	}
	//根据ID 查询
	public Withdraw findObjectById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Withdraw where CKID = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return (Withdraw) query.uniqueResult();
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
	public List<Withdraw> findObjects(QueryHelper queryHelper) {
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
	
	//多条件语句查询
	public List<Map> findList(String sql, List<Object> parameters) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(parameters !=null){
			for(int i = 0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
}
