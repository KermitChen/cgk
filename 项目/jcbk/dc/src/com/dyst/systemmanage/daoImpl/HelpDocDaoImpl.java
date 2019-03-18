package com.dyst.systemmanage.daoImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.dao.HelpDocDao;

/**
 * @author： cgk
 * @date：2016-03-02
 * @version：0.0.1
 * @doc：帮助文档持久层实现类，主要实现方法：
 *       1.根据条件分页查询帮助文档
 *       2.删除帮助文档
 *       4.更新帮助文档
 */
@Repository("helpDocDao")
public class HelpDocDaoImpl extends BaseDaoImpl implements HelpDocDao {
	/**
	 * 根据条件分页查询帮助文档
	 * @param loginName 用户名
	 * @param userName 姓名
	 * @param fileName 
	 * @param startTime  起始时间
	 * @param endTime  截至时间
	 * @param pageNo  第几页
	 * @param pageSize 每页条数
	 * @return PageResult pageResult
	 */
	@SuppressWarnings("rawtypes")
	public PageResult getWjjlbForPage(String loginName, String userName, String fileName, 
			String startTime, String endTime, int pageNo, int pageSize) throws Exception{
		//条件
		StringBuilder sb = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		if(loginName != null && !"".equals(loginName.trim())){
			sb.append(" and a.buildPno like :buildPno ");
			map.put("buildPno", "%" + loginName.trim() + "%");
		}
		if(userName != null && !"".equals(userName.trim())){
			sb.append(" and a.buildName like :buildName ");
			map.put("buildName", "%" + userName.trim() + "%");
		}
		if(fileName != null && !"".equals(fileName.trim())){
			sb.append(" and a.fileName like :fileName ");
			map.put("fileName", "%" + fileName.trim() + "%");
		}
		
		//时间时间范围
		if(startTime != null && !"".equals(startTime))//判断操作时间段
		 {
			 if(endTime != null && !"".equals(endTime)){	
				 sb.append(" and (a.buildTime between date_format(:startTime,'%Y-%c-%d %H:%i:%s') and date_format(:endTime,'%Y-%c-%d %H:%i:%s')) ");
				 map.put("startTime", startTime);
				 map.put("endTime", endTime);
			 } else{
				 sb.append(" and (a.buildTime between date_format(:startTime,'%Y-%c-%d %H:%i:%s') and now()) ");//开始时间到当前系统时间。。。
				 map.put("startTime", startTime);
			 }
		 } else if(startTime == null || "".equals(startTime)){
			 if(endTime != null && !"".equals(endTime)){	
				 sb.append(" and a.buildTime < date_format(:endTime,'%Y-%c-%d %H:%i:%s')");
				 map.put("endTime", endTime);
			 }
		 }
		
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from Wjjlb a where a.jlzt='1' " + sb.toString() + " order by a.id desc ");
		query = setParameter(query, map);
		
		//页数不能小于1
		if(pageNo < 1){
			pageNo = 1;
		}
		
		//设置数据起始索引号
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		List items = query.list();
			
		//获取总记录数
		Query queryCount = session.createQuery("select count(*) from Wjjlb a where a.jlzt='1' " + sb.toString());
		queryCount = setParameter(queryCount, map);
		long totalCount = (Long)queryCount.uniqueResult();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}
	
	/**
	 * 删除帮助文档
	 * @param ids 帮助文档id
	 * 
	 */
	public void deleteWjjlb(String ids) throws Exception{
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" update WJJLB set JLZT='0' where ID in(:ids)");
		//参数
		String[] idsStr = ids.split(",");
		Integer[] idsInt = new Integer[idsStr.length];
		for(int i=0;i < idsStr.length;i++){
			idsInt[i] = Integer.parseInt(idsStr[i]);
		}
		query.setParameterList("ids", (Object[])idsInt);
		//执行
		query.executeUpdate();
		session.flush();
	}
	
	/**
	 * 更新帮助文档
	 * @param id 帮助文档id
	 * @param fileName
	 * @param remark
	 */
	public void updateWjjlb(int id, String fileName, String remark) throws Exception{
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" update WJJLB set FILE_NAME=:FILE_NAME, REMARK=:REMARK, UPDATE_TIME=now() where ID=:ID ");
		query.setString("FILE_NAME", fileName);
		query.setString("REMARK", remark);
		query.setInteger("ID", id);
		query.executeUpdate();
		session.flush();
	}
	
	// 私有的给hql传参的方法
	private Query setParameter(Query query, Map<String, Object> map) {
		if (map != null) {
			Set<String> keySet = map.keySet();
			for (String string : keySet) {
				Object obj = map.get(string);
				// 这里考虑传入的参数是什么类型，不同类型使用的方法不同
				if (obj instanceof Collection<?>) {
					query.setParameterList(string, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					query.setParameterList(string, (Object[]) obj);
				} else {
					query.setParameter(string, obj);
				}
			}
		}
		return query;
	}
}