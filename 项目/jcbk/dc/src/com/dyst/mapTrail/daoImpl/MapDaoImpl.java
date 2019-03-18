package com.dyst.mapTrail.daoImpl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.mapTrail.dao.MapDao;
import com.dyst.mapTrail.entities.MapTrailPoint;
import com.dyst.mapTrail.entities.MapTrailPointKD;

@Repository("mapDao")
public class MapDaoImpl extends BaseDaoImpl implements MapDao {

	//多条件语句查询
	public List<Map> findList(String sql, List<Object> parameters) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(parameters !=null){
			for(int i = 0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	
	//多条件语句查询
	public List<MapTrailPoint> findMapTrailList(String hql, List<Object> parameters) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if(parameters !=null){
			for(int i = 0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	//2点路径查询
	public MapTrailPoint findMapTrailById(String start, String end) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM MapTrailPoint where jcdids = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, start + "," + end);
		return (MapTrailPoint) query.uniqueResult();
	}
	
	public MapTrailPointKD findMapTrailKDById(String start, String end) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" FROM MapTrailPointKD where jcdids=? ");
		query.setString(0,  start + "," + end);
		return (MapTrailPointKD) query.uniqueResult();
	}
	
	@Override
	public void clearAll() {
		Session session = getSessionFactory().getCurrentSession();
		String sql = "delete from MAP_TRAIL_POINT";
		int num = session.createSQLQuery(sql).executeUpdate();
		
	}
}
