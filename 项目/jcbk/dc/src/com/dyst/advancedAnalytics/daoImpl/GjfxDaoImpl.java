package com.dyst.advancedAnalytics.daoImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.advancedAnalytics.dao.GjfxDao;
import com.dyst.advancedAnalytics.entities.ResFootHold;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
@Repository("gjfxDao")
public class GjfxDaoImpl extends BaseDaoImpl implements GjfxDao{
	
	/**
	 * 通用（分页查询某一计算结果表）
	 * @param tableName 表名
	 * @param resFlag 查询标志
	 * @param pageNo 页码
	 * @param pageSize 单页数量
	 * @return
	 */
	public PageResult getResForPage(String tableName,String resFlag,String orderName,String orderType,int pageNo,int pageSize)  throws Exception{
		Session session = getSessionFactory().getCurrentSession();
		StringBuffer sb = new StringBuffer("from "+tableName+" where resFlag = :resFlag ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resFlag", resFlag);
		if(StringUtils.isNotEmpty(orderName) && StringUtils.isNotEmpty(orderType)){
			map.put("orderName", orderName);
			map.put("orderType", orderType);
			sb.append(" order by :orderName :orderType");
		}
		String countSql = "SELECT COUNT(*) from "+tableName+" where resFlag = :resFlag";//查询总量语句
		//查询总量并判断是否查询到数据
		Query queryCount = session.createQuery(countSql);
		queryCount.setParameter("resFlag", resFlag);
		long totalCount = (Long)queryCount.uniqueResult();
		if(totalCount < 1){
			return new PageResult("未查询到数据！");
		}
		Query query = setParameter(session.createQuery(sb.toString()), map);
		if(pageNo < 1) pageNo = 1;//页数小于1，从第一页开始查
		query.setFirstResult((pageNo - 1)*pageSize);//设置数据起始索引号
		query.setMaxResults(pageSize);
		List items = query.list();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}
	
	@Override
	public PageResult getPfgcResForPage(String resFlag, int pageNo,
			int pageSize) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer("from ResPfgc where resFlag = :resFlag order by gccs desc");
		map.put("resFlag", resFlag);
		StringBuffer sbCount = new StringBuffer("SELECT COUNT(*) from ResPfgc where resFlag = :resFlag");//查询总量语句
		//查询总量并判断是否查询到数据
		Query queryCount = session.createQuery(sbCount.toString());
		queryCount.setParameter("resFlag", resFlag);
		long totalCount = (Long)queryCount.uniqueResult();
		if(totalCount < 1){
			return new PageResult("未查询到数据！");
		}
		Query query = setParameter(session.createQuery(sb.toString()), map);
		if(pageNo < 1) pageNo = 1;//页数小于1，从第一页开始查
		query.setFirstResult((pageNo - 1)*pageSize);//设置数据起始索引号
		query.setMaxResults(pageSize);
		List items = query.list();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}
	/**
	 * 查询落脚点分析统计结果
	 * @param resFlag
	 * @return
	 */
	@Override
	public List<ResFootHold> getFootHoldRes(String resFlag) {
		Session session = getSessionFactory().getCurrentSession();
		String sql = "from ResFootHold where resFlag = :resFlag order by holdTimes desc";
		Query query = session.createQuery(sql);
		query.setParameter("resFlag", resFlag);
		return query.list();
	}
	/**
	 * 查询出行规律结果
	 */
	@Override
	public PageResult getCxglResForPage(String resFlag, int pageNo, int pageSize)
			throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		StringBuffer sb = new StringBuffer("from ResCxgl where resFlag = :resFlag order by passNum desc");
		String countSql = "SELECT COUNT(*) from ResCxgl where resFlag = :resFlag";//查询总量语句
		//查询总量并判断是否查询到数据
		Query queryCount = session.createQuery(countSql);
		queryCount.setParameter("resFlag", resFlag);
		long totalCount = (Long)queryCount.uniqueResult();
		if(totalCount < 1){
			return new PageResult("未查询到数据！");
		}
		Query query = session.createQuery(sb.toString());
		query.setParameter("resFlag", resFlag);
		if(pageNo < 1) pageNo = 1;//页数小于1，从第一页开始查
		query.setFirstResult((pageNo - 1)*pageSize);//设置数据起始索引号
		query.setMaxResults(pageSize);
		List items = query.list();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}
	/**
	 * 查询伴随分析的车牌号
	 */
	@Override
	public List getBsCphm(String resFlag) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		StringBuffer sb = new StringBuffer("SELECT DISTINCT CPHM1 from RES_SB where resFlag = :resFlag");
		Query query = session.createSQLQuery(sb.toString());
		query.setParameter("resFlag", resFlag);
		return query.list();
	}
	/**
	 * 查询伴随过车记录
	 */
	@Override
	public List getBsSb(String resFlag, String hphm) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer("from ResSb where resFlag = :resFlag");
		map.put("resFlag", resFlag);
		if(StringUtils.isNotEmpty(hphm)){
			map.put("hphm", hphm);
			sb.append("  and cphm1 = :hphm");
		}
		sb.append("  order by tgsj asc");
		Query query = setParameter(session.createQuery(sb.toString()), map);
		return query.list();
	}

	@Override
	public List getBkCphm(String cphm) throws Exception {
		String sql = "select hphm from BKSQ where (ywzt = '1' or ywzt = '7') and by3 = '0'";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setMaxResults(100);
		return query.list();
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
