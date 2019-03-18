package com.dyst.BaseDataMsg.daoimpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.BaseDataMsg.dao.YaglDao;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Yacs;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
@Repository(value="yaglDao")
public class YaglDaoImpl extends BaseDaoImpl implements YaglDao{

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
	
	//根据id删除一条语句
	public void deleteYa(Serializable id) {
		Yacs y = findObjectById(id);
		delete(y);
	}

	public Yacs findObjectById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Yacs where id = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		Yacs y = (Yacs)query.list().get(0);
		session.flush();
		if(y!=null){
			return y;
		}
		return null;
	}

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
	
	public Yacs findYacsByType(String yadj,String bjlx){
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Yacs where yadj = ? and bjlx = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, yadj);
		query.setParameter(1, bjlx);
		return (Yacs) query.uniqueResult();
	}

	/**
	 * 根据hql查询出列表
	 */
	@Override
	public List<Yacs> getList(String hql, Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if(params!=null){
			Set<String> keys = params.keySet();
			for(String key:keys){
				Object obj = params.get(key);
				if(obj instanceof Collection<?>){
					query.setParameterList(key, (Collection<?>)obj);
				}else if(obj instanceof Object[]){
					query.setParameterList(key, (Object[])obj);
				}else{
					query.setParameter(key, obj);
				}
			}
		}
		List<Yacs> list = query.list();
		session.flush();
		if(list!=null&&list.size()>=1){
			return list;
		}
		return null;
	}

	@Override
	public void updateYacs(Yacs y) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(y);
		session.flush();
	}
	
}
