package com.dyst.systemmanage.daoImpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.systemmanage.dao.UserDao;
import com.dyst.systemmanage.entities.User;

/**
 * @author： cgk
 * @date：2016-03-02
 * @version：0.0.1
 * @doc：用户信息业务层实现类，主要实现方法：
 *       1.通过用户名获取用户信息
 *       2.通过用户和密码获取用户信息
 *       3.通过类型代码获取字典数据
 *       4.根据条件分页查询用户信息
 *       5.删除用户信息
 *       6.获取所有用户信息
 *       7.批量授权
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {
	
	/**
	 * 通过用户名获取用户信息
	 * @param loginName
	 * @return List<User>s信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<User> getUser(String loginName) throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from User where loginName=? and enable!='0' ");
		query.setString(0, loginName);
		List listUser = query.list();
		session.flush();
		return listUser;
	}
	
	/**
	 * 通过用户和密码获取用户信息
	 * @param loginName 用户名
	 * @param password  密码
	 * @return List<User>信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<User> getUser(String loginName, String password) throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from User where loginName=? and password=? and enable != '0' ");
		query.setString(0, loginName);
		query.setString(1, password);
		List userList = query.list();
		session.flush();
		return userList;
	}
	
	/**
	 * 通过姓名和身份证号获取用户信息
	 * @param username  姓名
	 * @param idcard  身份证号
	 * @return List<User>信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<User> getUserByNameAndIdCard(String username, String idcard) throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from User where userName=? and identityCard=? and enable!='0' ");
		query.setString(0, username);
		query.setString(1, idcard);
		List userList = query.list();
		
		session.flush();
		return userList;
	}
	
	/**
	 * 通过类型代码获取字典数据
	 * @param typeCodes 可以是多个，以逗号隔开
	 * @return List<Dictionary>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Dictionary> getDictionarysByTypeCode(String typeCodes) throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from Dictionary where typeCode in(:typeCode) and deleteFlag != '1' order by typeCode, typeSerialNo ");
		query.setParameterList("typeCode", (Object[])typeCodes.split(","));
		List listDictionary = query.list();
		session.flush();
		return listDictionary;
	}
	
//	/**
//	 * 根据条件分页查询用户信息
//	 * @param position  角色类型，其他类型，不可以看到超级管理员角色
//	 * @param loginName 用户名
//	 * @param username 姓名
//	 * @param deptno 所属部门
//	 * @param roleType 角色类型
//	 * @param roleId  角色ID
//	 * @param startTime  起始时间
//	 * @param endTime  截至时间
//	 * @param pageNo  第几页
//	 * @param pageSize 每页条数
//	 * @return PageResult pageResult
//	 */
//	@SuppressWarnings("unchecked")
//	public PageResult getUserForPage(String position, String loginName, String username, String deptno, String roleType, 
//			String roleId, String infoSource, String startTime, String endTime, int pageNo, int pageSize) throws Exception{
//		//条件
//		StringBuilder sb = new StringBuilder();
//		if(position != null && !"1".equals(position.trim())){//非超级管理员，不能查看超级管理员信息
//			sb.append(" and (a.position != '1' or a.position is null) ");
//		}
//		if(loginName != null && !"".equals(loginName.trim())){
//			sb.append(" and a.loginName like '%").append(loginName.trim()).append("%' ");
//		}
//		if(username != null && !"".equals(username.trim())){
//			sb.append(" and a.userName like '%").append(username.trim()).append("%' ");
//		}
//		if(deptno != null && !"".equals(deptno.trim())){
//			sb.append(" and a.deptId in (").append(CommonUtils.changeString(deptno.trim().replace(";", ","))).append(") ");
//		}
//		if(roleType != null && !"".equals(roleType.trim())){
//			sb.append(" and a.position in (").append(CommonUtils.changeString(roleType.trim().replace(";", ","))).append(") ");
//		}
//		if(roleId != null && !"".equals(roleId.trim())){
//			sb.append(" and a.roleId in (").append(roleId.trim().replace(";", ",")).append(") ");
//		}
//		if(infoSource != null && !"".equals(infoSource.trim())){
//			sb.append(" and a.infoSource in (").append(CommonUtils.changeString(infoSource.trim().replace(";", ","))).append(") ");
//		}
//		
//		//时间时间范围
//		if(startTime != null && !"".equals(startTime))//判断操作时间段
//		 {
//			 if(endTime != null && !"".equals(endTime)){	
//				 sb.append(" and (a.buildTime between date_format('" + startTime + "','%Y-%c-%d %H:%i:%s') and date_format('" + endTime + "','%Y-%c-%d %H:%i:%s')) ");
//			 } else{
//				 sb.append(" and (a.buildTime between date_format('" + startTime + "','%Y-%c-%d %H:%i:%s') and now()) ");//开始时间到当前系统时间。。。
//			 }
//		 } else if(startTime == null || "".equals(startTime)){
//			 if(endTime != null && !"".equals(endTime)){	
//				 sb.append(" and a.buildTime < date_format('" + endTime + "','%Y-%c-%d %H:%i:%s')");
//			 }	 
//		 }
//		
//		Session session = getSessionFactory().getCurrentSession();
//		Query query = session.createQuery(" from User a where a.enable='1' " + sb.toString() + " order by a.id desc ");
//		
//		//页数不能小于1
//		if(pageNo < 1){
//			pageNo = 1;
//		}
//		
//		//设置数据起始索引号
//		query.setFirstResult((pageNo-1)*pageSize);
//		query.setMaxResults(pageSize);
//		List items = query.list();
//			
//		//获取总记录数
//		Query queryCount = session.createQuery("select count(*) from User a where a.enable='1' " + sb.toString());
//		long totalCount = (Long)queryCount.uniqueResult();
//		return new PageResult(totalCount, pageNo, pageSize, items);
//	}
	
	/**
	 * 根据条件查询用户信息，并返回只返回本部门及子孙部门的所有用户
	 * @param position  角色类型，其他类型，不可以看到超级管理员角色
	 * @param loginName 用户名
	 * @param username 姓名
	 * @param deptno 所属部门
	 * @param roleType 角色类型
	 * @param roleId  角色ID
	 * @param startTime  起始时间
	 * @param endTime  截至时间
	 */
	@SuppressWarnings("rawtypes")
	public PageResult getUserForPage(String position, String systemNo, String loginName, String username, String deptno, String roleType, 
			String roleId, String infoSource, String startTime, String endTime, int pageNo, int pageSize) throws Exception{
		//position、systemNo这两个参数不能为空
		if(position == null || "".equals(position.trim()) || systemNo == null || "".equals(systemNo.trim())){
			return new PageResult(0, pageNo, pageSize, new ArrayList<User>());//返回空数据
		}
		
		//条件
		StringBuilder sb = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		//查询本部门及子孙部门下的用户
		sb.append(" and SYSTEM_NO like :SYSTEM_NO ");
		map.put("SYSTEM_NO", systemNo.trim() + "%");
		
		if(position != null && !"1".equals(position.trim())){//非超级管理员，不能查看超级管理员信息
			sb.append(" and (a.POSITION != '1' or a.POSITION is null) ");
		}
		if(loginName != null && !"".equals(loginName.trim())){
			sb.append(" and a.LOGIN_NAME like :LOGIN_NAME ");
			map.put("LOGIN_NAME", "%" + loginName.trim() + "%");
		}
		if(username != null && !"".equals(username.trim())){
			sb.append(" and a.USER_NAME like :USER_NAME ");
			map.put("USER_NAME", "%" + username.trim() + "%");
		}
		if(deptno != null && !"".equals(deptno.trim())){
			sb.append(" and a.DEPT_ID in (:DEPT_ID) ");
			map.put("DEPT_ID", (Object[])deptno.trim().split(";"));
		}
		if(roleType != null && !"".equals(roleType.trim())){
			sb.append(" and a.POSITION in (:POSITION) ");
			map.put("POSITION", (Object[])roleType.trim().split(";"));
		}
		if(roleId != null && !"".equals(roleId.trim())){
			sb.append(" and a.ROLE_ID in (:ROLE_ID) ");
			
			String[] roleIdStr = roleId.trim().split(";");
			Integer[] roleIdInt = new Integer[roleIdStr.length];
			for(int i=0;i < roleIdStr.length;i++){
				roleIdInt[i] = Integer.parseInt(roleIdStr[i]);
			}
			map.put("ROLE_ID", (Object[])roleIdInt);
		}
		if(infoSource != null && !"".equals(infoSource.trim())){
			sb.append(" and a.INFO_SOURCE in (:INFO_SOURCE) ");
			map.put("INFO_SOURCE", (Object[])infoSource.trim().split(";"));
		}
		
		//时间时间范围
		if(startTime != null && !"".equals(startTime))//判断操作时间段
		{
			 if(endTime != null && !"".equals(endTime)){	
				 sb.append(" and (a.UPDATE_TIME between date_format(:startTime,'%Y-%c-%d %H:%i:%s') and date_format(:endTime,'%Y-%c-%d %H:%i:%s')) ");
				 map.put("startTime", startTime);
				 map.put("endTime", endTime);
			 } else{
				 sb.append(" and (a.UPDATE_TIME between date_format(:startTime,'%Y-%c-%d %H:%i:%s') and now()) ");//开始时间到当前系统时间。。。
				 map.put("startTime", startTime);
			 }
		} else if(startTime == null || "".equals(startTime)){
			 if(endTime != null && !"".equals(endTime)){	
				 sb.append(" and a.UPDATE_TIME < date_format(:endTime,'%Y-%c-%d %H:%i:%s')");
				 map.put("endTime", endTime);
			 }	 
		}
		
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" select * from USER a where a.ENABLE='1' " + sb.toString() + " order by a.ID desc ");
		query = setParameter(query, map);
		
		//页数不能小于1
		if(pageNo < 1){
			pageNo = 1;
		}
		
		//设置数据起始索引号
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List items = query.list();
		
		//封装对象
		List<User> userList = new ArrayList<User>();
		for(int i=0;i < items.size();i++){
			Map userMap = (Map)items.get(i);
			//创建对象
			User user = new User();
			user.setId((Integer)userMap.get("ID"));
			user.setLoginName(userMap.get("LOGIN_NAME") != null?(String)userMap.get("LOGIN_NAME"):"");
			user.setUserName(userMap.get("USER_NAME") != null?(String)userMap.get("USER_NAME"):"");
			user.setPassword(userMap.get("PASSWD") != null?(String)userMap.get("PASSWD"):"");
			user.setTelPhone(userMap.get("TELPHONE") != null?(String)userMap.get("TELPHONE"):"");
			user.setIdentityCard(userMap.get("IDENTITY_CARD") != null?(String)userMap.get("IDENTITY_CARD"):"");
			user.setDeptId(userMap.get("DEPT_ID") != null?(String)userMap.get("DEPT_ID"):"");
			user.setDeptId(userMap.get("SYSTEM_NO") != null?(String)userMap.get("SYSTEM_NO"):"");
			user.setDeptName(userMap.get("DEPT_NAME") != null?(String)userMap.get("DEPT_NAME"):"");
			user.setRoleId(userMap.get("ROLE_ID") != null?(Integer)userMap.get("ROLE_ID"):null);
			user.setRoleName(userMap.get("ROLE_NAME") != null?(String)userMap.get("ROLE_NAME"):"");
			user.setPosition(userMap.get("POSITION") != null?(String)userMap.get("POSITION"):"");
			user.setBuildPno(userMap.get("BUILD_PNO") != null?(String)userMap.get("BUILD_PNO"):"");
			user.setBuildName(userMap.get("BUILD_NAME") != null?(String)userMap.get("BUILD_NAME"):"");
			user.setBuildTime(userMap.get("BUILD_TIME") != null?(Date)userMap.get("BUILD_TIME"):null);
			user.setUpdateTime(userMap.get("UPDATE_TIME") != null?(Date)userMap.get("UPDATE_TIME"):null);
			user.setInfoSource(userMap.get("INFO_SOURCE") != null?(Character)userMap.get("INFO_SOURCE")+"":"");
			user.setEnable(userMap.get("ENABLE") != null?(Character)userMap.get("ENABLE")+"":"");
			user.setRemark(userMap.get("REMARK") != null?(String)userMap.get("REMARK"):"");
			user.setAnnids(userMap.get("ANN_IDS") != null?(String)userMap.get("ANN_IDS"):"");
			userList.add(user);
		}
			
		//获取总记录数
		Query queryCount = session.createSQLQuery("select count(*) from USER a where a.ENABLE='1' " + sb.toString());
		queryCount = setParameter(queryCount, map);
		long totalCount = Long.parseLong(""+(BigInteger)queryCount.uniqueResult());
		return new PageResult(totalCount, pageNo, pageSize, userList);
	}
	
	/**
	 * 删除用户信息
	 * @param ids 用户信息id
	 * @return User信息
	 */
	public void deleteUser(String ids) throws Exception{
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" delete from USER where id in(:ids) ");
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
	 * 获取所有用户信息
	 * @return List<User>信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<User> getAllUser() throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		List listUser = session.createQuery(" from User where enable!='0' ").list();
		session.flush();
		return listUser;
	}
	
	/**
	 * 批量授权
	 * @param ids 用户信息id
	 * @param roleId 数据格式：角色ID:角色名称:角色类型
	 * 
	 */
	public void grantRole(String userIds, String roleId) throws Exception{
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" update USER set ROLE_ID=:ROLE_ID, ROLE_NAME=:ROLE_NAME, POSITION=:POSITION where ID in(:ids) ");
		query.setInteger("ROLE_ID", Integer.parseInt(roleId.split(":")[0]));
		query.setString("ROLE_NAME", roleId.split(":")[1]);
		query.setString("POSITION", roleId.split(":")[2]);
		//ids参数
		String[] idsStr = userIds.split(",");
		Integer[] idsInt = new Integer[idsStr.length];
		for(int i=0;i < idsStr.length;i++){
			idsInt[i] = Integer.parseInt(idsStr[i]);
		}
		query.setParameterList("ids", (Object[])idsInt);
		//执行
		query.executeUpdate();
		session.flush();
	}
	
	//使用 QueryHelper 多条件查询
	@SuppressWarnings("unchecked")
	public List<User> findObjects(QueryHelper queryHelper) {
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