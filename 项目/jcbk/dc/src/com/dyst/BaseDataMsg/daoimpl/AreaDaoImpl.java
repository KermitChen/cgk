package com.dyst.BaseDataMsg.daoimpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.BaseDataMsg.dao.AreaDao;
import com.dyst.BaseDataMsg.entities.Area;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

@Repository("areaDao")
public class AreaDaoImpl extends BaseDaoImpl implements AreaDao{

	/**
	 * 根据ID逻辑删除城区信息 条目
	 */
	public void delete(Serializable id) {
		Area area = findObjectById(id);
		area.setDeleteFlag("1");
		update(area);		
	}

	/**
	 * 根据ID查询城区信息
	 */
	public Area findObjectById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Area where id = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return (Area) query.uniqueResult();
	}
	
	//根据城区ID查询，基础信息管理详细信息
	public Area findAreaByCqId(String cqid) {
		String hql = "from Area a where a.areano = ?";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, cqid);
		List<Area> list = query.list();
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 利用queryHelper多条件查询 带分页的查询城区信息
	 */
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

	//根据城区的名称模糊查询城区信息
	public Area findAreaByName(String areaName) {
		String hql = "from Area a where a.areaName = ?";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, areaName);
		List<Area> list = query.list();
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	//根据pid查询出所有的子节点
	public List<Area> findAreasById(Serializable id) {
		String hql = "from Area a where a.superAreaNo is ?";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return query.list();
	}
	
	//多条件语句查询
	public List<Area> findObjects(String hql, List<Object> parameters) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if(parameters !=null){
			for(int i = 0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	

}
