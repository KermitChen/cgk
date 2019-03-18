package com.dyst.systemmanage.daoImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.dao.BugReportDao;

@Repository("bugReportDao")
public class BugReportDaoImpl extends BaseDaoImpl implements BugReportDao{

	@Override
	public PageResult searchBugReportByPage(String beginTime,String endTime, String isDeal,
			int pageNo,int pageSize) throws Exception {
		//条件
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isNotEmpty(isDeal)){
			sb.append(" and isDeal = :isDeal ");
			map.put("isDeal", isDeal);
		}
		
		//时间时间范围
		if(StringUtils.isNotEmpty(beginTime))//判断操作时间段
		 {
			 if(StringUtils.isNotEmpty(endTime)){	
				 sb.append(" and (submitTime between date_format( :beginTime,'%Y-%c-%d %H:%i:%s') and date_format( :endTime,'%Y-%c-%d %H:%i:%s')) ");
				 map.put("beginTime", beginTime);
				 map.put("endTime", endTime);
			 } else{
				 sb.append(" and (submitTime between date_format(:beginTime,'%Y-%c-%d %H:%i:%s') and now()) ");//开始时间到当前系统时间。。。
				 map.put("beginTime", beginTime);
			 }
		 } else {
			 if(StringUtils.isNotEmpty(endTime)){	
				 sb.append(" and submitTime < date_format(:endTime,'%Y-%c-%d %H:%i:%s')");
				 map.put("endTime", endTime);
			 }	 
		 }
		
		Session session = getSessionFactory().getCurrentSession();
		//获取总记录数
		Query queryCount = setParameter(session.createQuery("select count(*) from BugReport where 1=1 " + sb.toString()), map);
		long totalCount = (Long)queryCount.uniqueResult();
		if(totalCount < 1){
			return new PageResult("未查询到记录!");
		}
		//页数不能小于1
		if(pageNo < 1){
			pageNo = 1;
		}
		
		Query query = setParameter(session.createQuery("from BugReport where 1=1 " + sb.toString() + " order by id desc "), map);
		//设置数据起始索引号
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		List items = query.list();
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
