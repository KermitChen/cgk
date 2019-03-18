package com.dyst.BaseDataMsg.daoimpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.BaseDataMsg.dao.JjHmdSpDao;
import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.BaseDataMsg.entities.JjhomdCx;
import com.dyst.BaseDataMsg.entities.Jjhomdsp;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

@Repository(value="JjHmdSpDao")
public class JjHmdSpDaoImpl extends BaseDaoImpl implements JjHmdSpDao{

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
		String hql = queryHelper.getQueryListHql();
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
	public Jjhomd findHmdById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Jjhomd where id = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		List list = query.list();
		if(list!=null){
			return (Jjhomd) query.list().get(0);
		}
		return null;
	}
	
	//根据id查找
	public Jjhomd findHmdSpById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "select h FROM Jjhomd h,Jjhomdsp hp where h.jjhomdsp.id = hp.id and h.id=?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		List list = query.list();
		if(list!=null&&list.size()>0){
			return (Jjhomd) query.list().get(0);
		}
		return null;
	}

	@Override
	public Jjhomd finHmdCxById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "select h FROM Jjhomd h,JjhomdCx hc where h.jjhomdCx.id = hc.id and h.id=?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		List list = query.list();
		if(list!=null&&list.size()>0){
			return (Jjhomd) query.list().get(0);
		}
		return null;
	}

}
