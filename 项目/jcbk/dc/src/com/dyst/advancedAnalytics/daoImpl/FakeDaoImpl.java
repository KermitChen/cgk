package com.dyst.advancedAnalytics.daoImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.advancedAnalytics.dao.FakeDao;
import com.dyst.advancedAnalytics.entities.FakePlate;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.utils.CommonUtils;
@Repository("fakeDao")
public class FakeDaoImpl extends BaseDaoImpl implements FakeDao{
	/**
	 * 分页查询套牌车辆信息
	 * @param jcdid	监测点id
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageResult getFakeForPage(String hphm,String jcdid, String kssj, String jssj,
			int pageNo, int pageSize) throws Exception {
		//条件
		StringBuilder sb = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		sb.append(" and f.cplx1 = f.cplx2 ");
		if(StringUtils.isNotEmpty(hphm)){
			map.put("hphm", "%"+hphm+"%");
			sb.append(" and f.cphid like :hphm ");
		}
		if(jcdid != null && !"".equals(jcdid.trim())){
			map.put("jcdid", CommonUtils.changeString(jcdid.trim().replace(";", ",")));
			sb.append(" and f.jcdid2 in (:jcdid) ");
		}
		
		//时间时间范围
		if(kssj != null && !"".equals(kssj))//判断操作时间段
		 {
			 if(jssj != null && !"".equals(jssj)){	
				 map.put("kssj", kssj);
				 map.put("jssj", jssj);
				 sb.append(" and (f.sbsj2 between date_format(:kssj,'%Y-%c-%d %H:%i:%s') and date_format(:jssj,'%Y-%c-%d %H:%i:%s')) ");
			 } else{
				 map.put("kssj", kssj);
				 sb.append(" and (f.sbsj2 between date_format(:kssj,'%Y-%c-%d %H:%i:%s') and now()) ");//开始时间到当前系统时间。。。
			 }
		 } else if(kssj == null || "".equals(kssj)){
			 if(jssj != null && !"".equals(jssj)){	
				 map.put("jssj", jssj);
				 sb.append(" and f.sbsj2 < date_format(:jssj,'%Y-%c-%d %H:%i:%s')");
			 }	 
		 }
		
		Session session = getSessionFactory().getCurrentSession();
		Query query = setParameter(session.createQuery("from FakePlate f where f.flag = '1' " + sb.toString() + " order by f.id desc "), map);
		
		//页数不能小于1
		if(pageNo < 1){
			pageNo = 1;
		}
		
		//设置数据起始索引号
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		List items = query.list();
			
		//获取总记录数
		Query queryCount = setParameter(session.createQuery("select count(*) from FakePlate f where f.flag = '1' " + sb.toString()), map);
		long totalCount = (Long)queryCount.uniqueResult();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}
	/**
	 * 根据id查询套牌车辆
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FakePlate getFakeById(int id) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		FakePlate fake = new FakePlate();
		fake = session.get(FakePlate.class, id);
		return fake;
	}
	@Override
	public List findByHphm(String hphm) throws Exception {
		String sql = "select cphid from FAKE_PLATE where cphid like :hphm order by time desc";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter("hphm", "%"+hphm+"%");
		query.setMaxResults(100);
		return query.list();
	}
	@Override
	public long getDeleteNum(int tpid) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		String sql = "select count(*) from FakeDelete where tpid = :tpid";
		Query query = session.createQuery(sql);
		query.setParameter("tpid", tpid);
		return (Long) query.uniqueResult();
	}
	@Override
	public List findDeleteData(int tpid) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from FakeDelete where tpid =:tpid");
		query.setParameter("tpid", tpid);
		return query.list();
	}
	@Override
	public void deleteCphmAndFlag(int tpid) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		String sql = "delete from FAKE_DELETE where tpid = :tpid";
		String sql1 = "delete from FAKE_PLATE where id =:tpid";
		session.createSQLQuery(sql1).setParameter("tpid", tpid).executeUpdate();
		session.createSQLQuery(sql).setParameter("tpid", tpid).executeUpdate();
	}
	/**
	 * 根据hql语句，查询出列表
	 */
	@Override
	public List<FakePlate> getList(String hql, Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = setParameter(query, params);
		List<FakePlate> list = new ArrayList<FakePlate>();
		list = query.list();
		if(list.size()>0&&list!=null){
			return list;
		}
		return null;
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
