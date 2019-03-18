package com.dyst.BaseDataMsg.daoimpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.BaseDataMsg.dao.HmdDao;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Hmd;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

@Repository("hmdDao")
public class HmdDaoImpl extends BaseDaoImpl implements HmdDao {

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
		Query queryCount = session.createQuery(hql);
		if(params != null){
			for(int i = 0; i < params.size(); i++){
				queryCount.setParameter(i, params.get(i));
			}
		}
		int totalCount = queryCount.list().size();
		return new PageResult(totalCount, pageNo, pageSize, items);	
	}

	//queryHelper带分页多条件查询
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

	//根据id查找
	public Hmd findHmdById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Hmd where id = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		List list = query.list();
		if(list!=null){
			return (Hmd) query.list().get(0);
		}
		return null;
	}

}
