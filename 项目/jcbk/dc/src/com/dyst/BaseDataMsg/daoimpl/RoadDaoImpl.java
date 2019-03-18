package com.dyst.BaseDataMsg.daoimpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.BaseDataMsg.dao.RoadDao;
import com.dyst.BaseDataMsg.entities.Road;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

@Repository("roadDao")
public class RoadDaoImpl extends BaseDaoImpl implements RoadDao {

	/**
	 * 根据ID删除对象
	 */
	public void delete(Serializable id) {
		Road road = findObjectById(id);
		road.setDeleteFlag("1");
		update(road);
	}

	/**
	 * 
	 */
	public Road findObjectById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Road where pkId = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return (Road) query.uniqueResult();
	}
	
	//根据道路ID查询道路信息
	public Road findRoadByDlId(String dlid){
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Road where roadNo = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, dlid);
		return (Road) query.uniqueResult();
	}

	/**
	 * 多条件语句查询
	 */
	public List<Road> findObjects(String hql, List<Object> parameters) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if(parameters !=null){
			for(int i = 0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}

	public List<Road> findObjects(QueryHelper queryHelper) {
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

	public PageResult getPageResult(String hql, List<Object> params, int pageNo,int pageSize) {
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




}
