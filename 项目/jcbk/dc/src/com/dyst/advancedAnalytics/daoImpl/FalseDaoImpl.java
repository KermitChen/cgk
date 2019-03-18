package com.dyst.advancedAnalytics.daoImpl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.advancedAnalytics.dao.FalseDao;
import com.dyst.advancedAnalytics.entities.FalseCphm;
import com.dyst.advancedAnalytics.entities.FalseDelete;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
@Repository("falseDao")
public class FalseDaoImpl extends BaseDaoImpl implements FalseDao{
	/**
	 * 分页查询假牌车辆
	 * @param kssj
	 * @param jssj
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageResult getFalseForPage(String hphm, String kssj, String jssj,
			int pageNo, int pageSize) throws Exception {
		//条件
		StringBuilder sb = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(hphm)){
			map.put("hphm", "%"+hphm+"%");
			sb.append(" and f.jcphm like :hphm ");
		}
		
		//时间时间范围
		if(kssj != null && !"".equals(kssj))//判断操作时间段
		 {
			 if(jssj != null && !"".equals(jssj)){	
				 map.put("kssj", kssj);
				 map.put("jssj", jssj);
				 sb.append(" and (f.lrsj between date_format(:kssj,'%Y-%c-%d %H:%i:%s') and date_format(:jssj,'%Y-%c-%d %H:%i:%s')) ");
			 } else{
				 map.put("kssj", kssj);
				 sb.append(" and (f.lrsj between date_format(:kssj,'%Y-%c-%d %H:%i:%s') and now()) ");//开始时间到当前系统时间。。。
			 }
		 } else if(kssj == null || "".equals(kssj)){
			 if(jssj != null && !"".equals(jssj)){	
				 map.put("jssj", jssj);
				 sb.append(" and f.lrsj < date_format(:jssj,'%Y-%c-%d %H:%i:%s')");
			 }	 
		 }
		
		Session session = getSessionFactory().getCurrentSession();
		Query query = setParameter(session.createQuery("from FalseCphm f where 1 = 1 " + sb.toString() + " order by f.id desc "), map);
		
		//页数不能小于1
		if(pageNo < 1){
			pageNo = 1;
		}
		
		//设置数据起始索引号
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		List items = query.list();
			
		//获取总记录数
		Query queryCount = setParameter(session.createQuery("select count(*) from FalseCphm f where 1 = 1 " + sb.toString()), map);
		long totalCount = (Long)queryCount.uniqueResult();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}
	/**
	 * 根据id获取假牌车辆
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FalseCphm getFalseById(int id) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		FalseCphm falseCphm = session.get(FalseCphm.class, id);
		return falseCphm;
	}
	@Override
	public List findByHphm(String hphm) throws Exception {
		String sql = "select jcphm from FALSE_CPHM where jcphm like :hphm order by lrsj desc";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter("hphm", "%"+hphm+"%");
		query.setMaxResults(100);
		return query.list();
	}
	@Override
	public long getDeleteNum(int jpid) throws Exception{
		String sql = "select count(*) from FalseDelete where jpid = :jpid";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sql);
		query.setParameter("jpid", jpid);
		return (Long) query.uniqueResult();
	}
	@Override
	public void markDelete(Integer jpid, String pno, String pname,String reason, String realPlate)
			throws Exception {
		FalseDelete fd = new FalseDelete(jpid, pno, pname, new Date(), reason, realPlate);
		super.save(fd);
	}
	@Override
	public void deleteCphmAndFlag(int jpid) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		String sql = "delete from FALSE_DELETE where jpid = :jpid";
		FalseCphm fc = (FalseCphm) super.getObjectById(FalseCphm.class, jpid);
		super.delete(fc);
		session.createSQLQuery(sql).setParameter("jpid", jpid).executeUpdate();
	}
	@Override
	public List findDeleteData(int jpid) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from FalseDelete where jpid = :jpid");
		query.setParameter("jpid", jpid);
		query.setMaxResults(100);
		return query.list();
	}
	@Override
	public List searchIsMark(int jpid, String pno) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from FalseDelete where jpid = :jpid and pno = :pno");
		query.setParameter("jpid", jpid);
		query.setParameter("pno", pno);
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
	/**
	 * 查询出列表
	 */
	@Override
	public List<FalseCphm> getList(String hql, Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, params);
		List<FalseCphm> list = query.list();
		if(list!=null&&list.size()>=1){
			return list;
		}
		return null;
	}
}
