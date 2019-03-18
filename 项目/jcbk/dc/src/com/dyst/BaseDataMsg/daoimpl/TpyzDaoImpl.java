package com.dyst.BaseDataMsg.daoimpl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.dyst.BaseDataMsg.dao.TpyzDao;
import com.dyst.BaseDataMsg.entities.Tpyz;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
@Repository("tpyzDao")
public class TpyzDaoImpl extends BaseDaoImpl implements TpyzDao{
	
	private static Logger logger = LoggerFactory.getLogger(TpyzDaoImpl.class);

	@Override
	public int deleteAll() throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery("delete from TPYZ");
		return query.executeUpdate();
	}

	@Override
	public void upData(final List<Tpyz> list) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		session.doWork(new Work() {
			@Override
			public void execute(java.sql.Connection connection) throws SQLException {
				java.sql.PreparedStatement stmt = null;
				Tpyz tpyz = null;
				try {
					connection.setAutoCommit(false);
					stmt = connection.prepareStatement("INSERT INTO TPYZ(JCDID1,JCDID2,TPSJYZ,JL,DCSSJYZ,QYBZ) VALUES(?,?,?,?,?,?)");
					for (int i = 0; i < list.size(); i++) {
						tpyz = list.get(i);
						stmt.setString(1, tpyz.getJcdid1());
						stmt.setString(2, tpyz.getJcdid2());
						stmt.setInt(3, tpyz.getTpsjyz());
						stmt.setInt(4, tpyz.getJl());
						stmt.setInt(5, tpyz.getDcssjyz());
						stmt.setString(6, tpyz.getQybz());
						stmt.addBatch();
						if((i+1) % 200 == 0){
							stmt.executeBatch();
							stmt.clearBatch();
						}
					}
					stmt.executeBatch();
					stmt.clearBatch();
					connection.commit();
				} catch (Exception e) {
					logger.error("插入套牌阈值出错!", e);
					e.printStackTrace();
				}finally{
					if (stmt != null) {
						stmt.close();
					}
				}
			}
		});
		session.flush();
	}

	@Override
	public PageResult getTpyzForPage(int pageNo, int pageSize,String jcdid) throws Exception {
		String[] jcds = null;
		if(StringUtils.isNotEmpty(jcdid)){
			jcds = jcdid.split(",");
		}
		Session session = getSessionFactory().getCurrentSession();
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("from Tpyz where QYBZ = '1' ");
		StringBuffer countSql = new StringBuffer("select count(*) from Tpyz where QYBZ = '1' ");
		if(jcds != null && jcds.length > 0){
			map.put("ids", jcds);
			sql.append(" and jcdid1 in(:ids)");
			countSql.append(" and jcdid1 in(:ids)");
		}
		Query query = setParameter(session.createQuery(sql.toString()), map);
		if(pageNo < 1){
			pageNo = 1;
		}
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		Query countQuery = setParameter(session.createQuery(countSql.toString()), map);
		long totalCount = (Long) countQuery.uniqueResult();
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
