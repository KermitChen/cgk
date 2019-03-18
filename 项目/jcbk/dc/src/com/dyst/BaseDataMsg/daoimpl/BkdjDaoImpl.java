package com.dyst.BaseDataMsg.daoimpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.BaseDataMsg.dao.BkdjDao;
import com.dyst.BaseDataMsg.entities.DicDisType;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;

@Repository("bkdjDao")
public class BkdjDaoImpl extends BaseDaoImpl implements BkdjDao{

	@Override
	public PageResult findByPage(String bkdl, int pageNo, int pageSize)
			throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("from DicDisType where 1 = 1 ");
		StringBuffer countSql = new StringBuffer("select count(*) from DicDisType where 1=1 ");
		if (StringUtils.isNotEmpty(bkdl)) {
			map.put("superid", bkdl);
			sql.append("and superid = :superid");
			countSql.append("and superid = :superid");
		}
		Query countQuery = setParameter(session.createQuery(countSql.toString()), map);
		long totalCount = (Long) countQuery.uniqueResult();
		if (totalCount < 1) {
			return new PageResult("未查询到数据!");
		}
		Query query = setParameter(session.createQuery(sql.toString()), map);
		if(pageNo < 1) pageNo = 1;//页数小于1，从第一页开始查
		query.setFirstResult((pageNo - 1)*pageSize);//设置数据起始索引号
		query.setMaxResults(pageSize);
		List items = query.list();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}

	@Override
	public long decideIsHave(String showOrder, String level,String id,String notId) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		StringBuffer countSql = new StringBuffer("select count(*) from DicDisType where 1=1 ");
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(showOrder)){
			countSql.append(" and showOrder = :showOrder ");
			map.put("showOrder", showOrder);
		}else if(StringUtils.isNotEmpty(level)){
			countSql.append(" and level = :level ");
			map.put("level", level);
		}else if(StringUtils.isNotEmpty(id)){
			countSql.append(" and id = :id ");
			map.put("id", id);
		}
		if(StringUtils.isNotEmpty(notId)){
			countSql.append(" and id != :notid ");
			map.put("notid", notId);
		}
		Query query = setParameter(session.createQuery(countSql.toString()), map);
		return (Long) query.uniqueResult();
	}

	@Override
	public void deleteBkdj(String id) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery("delete from DicDisType where id = :id");
		query.setParameter("id", id);
		query.executeUpdate();
	}

	@Override
	public DicDisType getBkdjById(String id) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from DicDisType where id = :id");
		query.setParameter("id", id);
		return (DicDisType) query.list().get(0);
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
