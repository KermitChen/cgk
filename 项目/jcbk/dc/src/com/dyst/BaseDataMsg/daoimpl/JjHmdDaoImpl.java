package com.dyst.BaseDataMsg.daoimpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.BaseDataMsg.dao.JjHmdDao;
import com.dyst.BaseDataMsg.entities.Hmd;
import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.daoImpl.BaseDaoImpl2;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

@Repository("JjHmdDao")
public class JjHmdDaoImpl extends BaseDaoImpl2<Jjhomd> implements JjHmdDao{

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
	/**
	 * 查询有效的红名单
	 */
	@Override
	public List wildFindHmd(String hphm) throws Exception{
		StringBuffer sb = new StringBuffer("from Jjhomd where jlzt = '002' and zt = '1' ");
		if(StringUtils.isNotEmpty(hphm)){
			sb.append(" and cphid like ? ");
		}
		sb.append(" order by id desc");
		
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		if (StringUtils.isNotEmpty(hphm)) {
			query.setString(0, "%" + hphm + "%");
		}
		return query.list();
	}
	//查询该车是否是生效红名单
	public Boolean isHmdByHphm(String hphm,String hpzl) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Jjhomd where cphid = ? and cplx = ? and jlzt = '1'";
		Query query = session.createQuery(hql);
		query.setParameter(0, hphm);
		query.setParameter(1, hpzl);
		Jjhomd jjhomd= (Jjhomd) query.uniqueResult();
		return jjhomd == null ? false:true;
	}

	@Override
	public PageResult getPageResult2(String hql, Map<String, Object> params,int pageNo, int pageSize) {
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
}
