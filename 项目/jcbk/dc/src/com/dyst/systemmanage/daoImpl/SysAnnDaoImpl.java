package com.dyst.systemmanage.daoImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.dao.SysAnnDao;
import com.dyst.systemmanage.entities.Announcement;
import com.dyst.systemmanage.entities.Message;

@Repository(value="sysAnnDao")
public class SysAnnDaoImpl extends BaseDaoImpl implements SysAnnDao{

//	@Override
//	public void addSysAnn(Announcement a) {
//		
//	}

	@SuppressWarnings("unchecked")
	@Override
	public PageResult getAnnforPage(String loginName, String fileName,
			String annType, String startTime, String endTime, int pageNo,
			int pageSize) throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<String,Object> params = new HashMap<String, Object>();
		sb.append("from Announcement a where a.jlzt = '1'");
		if(StringUtils.isNotBlank(loginName)){
			sb.append(" and a.buildPno like :loginName");
			params.put("loginName", loginName);
		}
		if(StringUtils.isNotBlank(fileName)){
			sb.append(" and a.fileName like :fileName");
			params.put("fileName", fileName);
		}
		if(StringUtils.isNotBlank(annType)){
			sb.append(" and a.annType = :annType");
			params.put("annType", annType);
		}
		if(StringUtils.isNotBlank(startTime)){
			sb.append(" and a.buildTime >= :startTime");
			params.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sb.append(" and a.buildTime <= :endTime");
			params.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sb.append(" order by a.buildTime desc");
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		query = this.setParameter(query, params);
		if(pageNo<1){
			pageNo = 1;
		}
		//设置数据起始索引号
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		List<Announcement> items = query.list();
		//获取总记录数
		Query queryCount = session.createQuery(sb.toString());
		queryCount = this.setParameter(queryCount,params);
		int totalCount = queryCount.list().size();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}
	
	/**
	 * 带部门 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageResult getAnnforPageAndNormalUser(String loginName,String fileName, String annType, String startTime, String endTime,
			int pageNo, int pageSize, String deptID) throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<String,Object> params = new HashMap<String, Object>();
		sb.append("from Announcement a where a.jlzt = '1'");
		if(StringUtils.isNotBlank(loginName)){
			sb.append(" and a.buildPno like :loginName");
			params.put("loginName", loginName);
		}
		if(StringUtils.isNotBlank(fileName)){
			sb.append(" and a.fileName like :fileName");
			params.put("fileName", fileName);
		}
		if(StringUtils.isNotBlank(annType)){
			sb.append(" and a.annType = :annType");
			params.put("annType", annType);
		}
		if(StringUtils.isNotBlank(startTime)){
			sb.append(" and a.buildTime >= :startTime");
			params.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sb.append(" and a.buildTime <= :endTime");
			params.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sb.append(" order by a.buildTime desc");
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		query = this.setParameter(query, params);
		if(pageNo<1){
			pageNo = 1;
		}
		List<Announcement> list = query.list();
		List<Announcement> resultList = new ArrayList<Announcement>();
		for(Announcement a :list){
			if(a.getDeptids()==null||a.getDeptids().equals("")){
				resultList.add(a);
			}else{
				String[] deptIds = a.getDeptids().split(",");//该条公告发送给的部门ids数组
				for(String d:deptIds){
					if(d.equals(deptID)){
						resultList.add(a);
						break;
					}
				}
			}
		}
		List<Announcement> items = new ArrayList<Announcement>();
		if(pageSize>=resultList.size()){
			items = resultList;
		}else{
			items = resultList.subList((pageNo-1)*pageSize, pageNo*pageSize>=resultList.size()?resultList.size():pageNo*pageSize);
		}
		//总记录数
		int totalCount = resultList.size();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(String hql) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object> list = new ArrayList<Object>();
		list = query.list();
		if(list!=null&&list.size()>0){
			return query.list();
		}else{
			return null;
		}
	}
	
	/**
	 * 用户待查看 的消息中心 的消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getMessages(String hql,Map<String,Object>params) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, params);
		List<Message> list = new ArrayList<Message>();
		list = query.list();
		if(list!=null&&list.size()>0){
			return query.list();
		}else{
			return null;
		}
	}
	
	/**
	 * 带分页 查询  用户待查看的 通知消息
	 */
	@Override
	public PageResult getPageResult(String hql, Map<String, Object> params,
			int pageNo, int pageSize) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParameter(query, params);
		int totalCount = query.list().size();
		if(pageNo < 1) pageNo = 1;
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		List<Message> items = new ArrayList<Message>();
		items = query.list();
		PageResult pageResult = new PageResult(totalCount,pageNo,pageSize,items);
		return pageResult;
	} 	

	/**
	 * 标记 某用户  通知消息为已读
	 */
	@Override
	public void markUserAllTzzxHasRed(String loginName) {
		String hql = "from Message m where m.recid =?";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, loginName);
		List<Message> items = new ArrayList<Message>();
		items = query.list();
		for(Message m:items){
			m.setHasread("1");//是否已读字段为1，说明已读
			this.update(m);
		}
	}
	/**
	 * 根据id查询出一条message消息
	 */
	@Override
	public Message getOneMessageById(Serializable id) {
		 Message message = (Message) this.getObjectById(Message.class, id);
		 if(!message.getHasread().equals("1")){
			 message.setHasread("1");
		 }
		 this.update(message);
		 return message;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Announcement> getList2(String hql) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		List<Announcement> list = new ArrayList<Announcement>();
		list = query.list();
		if(list!=null&&list.size()>0){
			return query.list();
		}else{
			return null;
		}
	}

}