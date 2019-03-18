package com.dyst.dispatched.daoimpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.dispatched.dao.DispatchedDao;
import com.dyst.dispatched.entities.Dis110;
import com.dyst.dispatched.entities.DisApproveRecord;
import com.dyst.dispatched.entities.DisReceive;
import com.dyst.dispatched.entities.DisReport;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.entities.Lsh;
import com.dyst.dispatched.entities.Withdraw;
import com.dyst.earlyWarning.entities.EWarning;
import com.dyst.systemmanage.entities.User;

@Repository("dispatchedDao")
public class DispatchedDaoImpl extends BaseDaoImpl implements DispatchedDao {
	

	//根据ID 逻辑删除基础数据
	public void delete(Serializable id) {
		Dispatched dic = findObjectById(id);
//		dic.setDeleteFlag("1");
		update(dic);
	}
	//根据ID 查询
	public Dispatched findObjectById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Dispatched where BKID = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return (Dispatched) query.uniqueResult();
	}
	
	//根据车牌 查询
	public List<Dispatched> findDispatchedByHphm(String hphm, String hpzl, int bkid) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Dispatched where HPHM = ? and HPZL = ? and ywzt in (1,7)";
		//排除某布控
		if(bkid != 0){
			hql = "FROM Dispatched where HPHM = ? and HPZL = ? and bkid != ? and ywzt in (1, 7)";
		}
		Query query = session.createQuery(hql);
		query.setParameter(0, hphm);
		query.setParameter(1, hpzl);
		//排除某布控
		if(bkid != 0){
			query.setParameter(2, bkid);
		}
		
		return query.list();
	}
	
	//多条件语句查询
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
	//使用 QueryHelper 多条件查询
	@Transactional(readOnly=true)
	public List<Dispatched> findObjects(QueryHelper queryHelper) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(queryHelper.getQueryListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters !=null){
			for(int i = 0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	//带分页的多条件查询
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
	
	public PageResult getAllPageResult(String hql,List<Object> parameters, int pageNo,
			int pageSize){
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		if (pageNo < 1)
			pageNo = 1;

		long totalCount = new Long(query.list().size());
		query.setFirstResult((pageNo - 1) * pageSize);// 设置数据起始索引号
		query.setMaxResults(pageSize);
		List items = query.list();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}
	
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
	@Override
	public List<DisApproveRecord> findApproveRecord(Serializable id, String bzw) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM DisApproveRecord where ywid = ? and bzw = ? order by id asc";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		query.setParameter(1, bzw);
		return query.list();
	}
	
	@Override
	public List<DisReport> findDisReport(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM DisReport where bkid = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return query.list();
	}
	
	/**
	 * 根据布控ID查签收情况
	 * @param bkid
	 * @return
	 */
	public List<DisReceive> findDisReceiveList(Serializable bkid, String bkckbz){
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM DisReceive where bkckbz = ? and bkid = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, bkckbz);
		query.setParameter(1, bkid);
		return query.list();
	}
	
	/**
	 * 根据ID查询报备
	 * @param id
	 * @return
	 */
	public DisReport findDisReportById(Serializable id){
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM DisReport where id = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return (DisReport) query.uniqueResult();
	}
	
	/**
	 * 根据ID查询布控签收
	 * @param id
	 * @return
	 */
	public DisReceive findDisReceive(Serializable id){
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM DisReceive where id = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return (DisReceive) query.uniqueResult();
	}
	
	//查询可布控的110数据
	public List<Dis110> findDis110(String loginName){
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Dis110 where bkzt = '0' and jyjy='" + loginName.trim() + "' order by id desc";
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	/**
	 * 更改cad布控的110数据
	 * @return
	 */
	public void updateCadBk(String cadBkid){
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" update ZJBK set BKZT='1' where ID=:ID ");
		query.setInteger("ID", Integer.parseInt(cadBkid));
		//执行
		query.executeUpdate();
		session.flush();
	}
	
	//获取领导
	public List<User> getLeader(String hql,List<Object> parameters){
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if(parameters !=null){
			for(int i = 0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	
	/**
	 * 查询布控信息
	 * @param hql
	 * @param parameters
	 * @return
	 */
	public List<Dispatched> findDispatched(String hql,List<Object> parameters){
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if(parameters !=null){
			for(int i = 0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	/**
	 * 根据布控ID查找未完成的报备
	 * @param bkid
	 * @return
	 */
	public DisReport findNoDealDisReport(Serializable bkid){
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM DisReport where bkid = ? and bbzt = '0'";
		Query query = session.createQuery(hql);
		query.setParameter(0, bkid);
		return (DisReport) query.uniqueResult();
	}
	
	/**
	 * 根据布控ID查撤控
	 * @param bkid
	 * @return
	 */
	public List<Withdraw> findWithdrawList(Serializable bkid){
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Withdraw where bkid = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, bkid);
		return query.list();
	}
	
	/**
	 * 根据布控ID查预警
	 * @param bkid
	 * @return
	 */
	public List<EWarning> findEWaringList(Serializable bkid){
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM EWarning where bkid = ? order by tgsj desc";
		Query query = session.createQuery(hql);
		query.setParameter(0, bkid);
		return query.list();
	}
	
	/**
	 * 获得流水号
	 * @param date
	 * @return
	 */
	public Lsh getLsh(String date){
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Lsh where scsj = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, date);
		return (Lsh) query.uniqueResult();
	}
	
	/**
	 * 根据查询条件查询dispatched列表
	 */
	@Override
	public List<Dispatched> getListByQuery(String hql,
			Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, params);
		List<Dispatched> list = query.list();
		return list;
	}
	/**
	 * 根据查询条件查询出撤控列表
	 */
	@Override
	public List<Withdraw> findWithdrawList(String hql,
			Map<String, Object> params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, params);
		List<Withdraw> list = query.list();
		if(list!=null&&list.size()>=1){
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
