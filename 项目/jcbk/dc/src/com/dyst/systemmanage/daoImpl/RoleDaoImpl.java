package com.dyst.systemmanage.daoImpl;

import java.util.ArrayList;
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
import com.dyst.systemmanage.dao.RoleDao;
import com.dyst.systemmanage.entities.FunctionTree;
import com.dyst.systemmanage.entities.Role;

/**
 * @author： cgk
 * @date：2016-03-02
 * @version：0.0.1
 * @doc：角色信息业务层实现类，主要实现方法：
 *       1.根据功能级别和权限位获取启用的功能（必须按权限位升序排序）
 *       2.获取所有的角色
 *       3.根据条件获取该用户所创建的角色
 *       4.获取所有的可用的功能列表
 *       5.删除角色信息
 *       6.更新角色信息
 *       7.更新用户信息表中的角色信息
 */
@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {
	/**
	 * 根据功能级别和权限位获取启用的功能（必须按权限位升序排序）
	 * @param rank 权限级别   '1' 和 '2' 
	 * @param permissionPositions 权限位集合，逗号隔开
	 * @param enable 是否启用  1启用   0不启用
	 * @return List<FunctionTree>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<FunctionTree> getFunction(String ranks, String permissionPositions, String enable) throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from FunctionTree where enable=:enable and rank in(:ranks) and permissionPosition in (:permissionPositions) order by sort ");
		query.setString("enable", enable);
		query.setParameterList("ranks", (Object[])ranks.split(","));
		
		String[] permissionPositionStr = permissionPositions.split(",");
		Integer[] permissionPositionInt = new Integer[permissionPositionStr.length];
		for(int i=0;i < permissionPositionStr.length;i++){
			permissionPositionInt[i] = Integer.parseInt(permissionPositionStr[i]);
		}
		query.setParameterList("permissionPositions", (Object[])permissionPositionInt);
		List listFunctionTree = query.list();
		session.flush();
		return listFunctionTree;
	}
	
	/**
	 * 获取所有的角色
	 * @return List<Role>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Role> getRoles() throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		List roleList = session.createQuery(" from Role order by id ").list();
		session.flush();
		return roleList;
	}
	
	/**
	 * 获取所有自己创建的角色
	 * @return List<Role>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Role> getRoles(String buildPno) throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from Role where buildPno=? order by id ");
		query.setString(0, buildPno);
		List roleList = query.list();
		session.flush();
		return roleList;
	}
	
	/**
	 * 获取某角色的子孙角色
	 * @param roleId 角色Id 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Role> getAllMyRole(int roleId, String systemNo, String position) throws Exception{
		//条件
		List<String> listString = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		if(systemNo != null && !"".equals(systemNo.trim())){
			sb.append(" and a.buildPno in ( select loginName from User where systemNo like ? ) ");
			listString.add(systemNo.trim() + "%");
		} else{
			return new ArrayList<Role>();//返回空数据
		}
		
		//角色类型一致
		if(position.length() != 5){
			//两位
			if(position.length() == 2) {
				sb.append(" and a.roleType like ? ");
				listString.add("__");
			} else if(position.length() == 3){//三位
				sb.append(" and a.roleType like ? ");
				listString.add("__" + position.substring(2, 3));
			}
		}
		
		//查询
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from Role a where a.buildPno != 'system' and a.id != ? " + sb.append(" order by a.roleType desc ").toString());
		query.setInteger(0, roleId);
		//添加其他参数
		for(int i=0;i < listString.size();i++){
			query.setString(i + 1, listString.get(i));
		}
		List items = query.list();
		
		//返回结果
		return items;
	}
	
	/**
	 * 根据条件获取该用户所创建的角色
	 * @param loginName 用户名
	 * @param roleName 角色名称
	 * @param roleType 角色类型
	 * @return PageResult pageResult
	 */
	@SuppressWarnings("rawtypes")
	public PageResult getAllMyRole(String loginName, int roleId, String systemNo, String position, String roleName, String roleType, int pageNo, int pageSize) throws Exception{
		//条件
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		if(systemNo != null && !"".equals(systemNo.trim())){
			sb.append(" and a.buildPno in ( select loginName from User where systemNo like :systemNo ) ");
			map.put("systemNo", systemNo.trim() + "%");
		} else{
			return new PageResult(0, pageNo, pageSize, new ArrayList<Role>());//返回空数据
		}
			
		if(roleName != null && !"".equals(roleName.trim())){
			sb.append(" and a.roleName like :roleName ");
			map.put("roleName", "%" + roleName.trim() + "%");
		}
		if(roleType != null && !"".equals(roleType.trim())){
			sb.append(" and a.roleType in (:roleType1) ");
			map.put("roleType1", (Object[])roleType.trim().split(";"));
		}
		
		//角色类型一致
		if(position.length() != 5){
			//两位
			if(position.length() == 2) {
				sb.append(" and a.roleType like :roleType2 ");
				map.put("roleType2", "__");
			} else if(position.length() == 3){//三位
				sb.append(" and a.roleType like :roleType3 ");
				map.put("roleType3", "__" + position.substring(2, 3));
			}
		}
		//角色ID
		map.put("roleId", roleId);
		
		//查询
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from Role a where a.buildPno != 'system' and a.id != :roleId " + sb.append(" order by a.roleType desc ").toString());
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
		Query queryCount = session.createQuery("select count(*) from Role a where a.buildPno != 'system' and a.id != :roleId " + sb.toString());
		queryCount = setParameter(queryCount, map);
		long totalCount = (Long)queryCount.uniqueResult();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}
	
//	/**
//	 * 获取子孙角色
//	 * @param RoleId
//	 */
//	private void recurseGetRole(int roleId, List<Role> listRole, List<Role> newListRole){
//		//然后再找到这些人创建了上面角色
//		for(int i=0;i < listRole.size();i++){
//			Role role = listRole.get(i);
//			if(roleId == role.getParentId()){//如果父级ID为roleId，则获取
//				newListRole.add(role);
//				//递归获取该角色的子孙角色
//				recurseGetRole(role.getId(), listRole, newListRole);
//			}
//		}
//	}
	
	/**
	 * 获取所有的可用的功能列表
	 * @return List<FunctionTree>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<FunctionTree> getFunctions() throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		List listFunctionTree = session.createQuery(" from FunctionTree where enable='1' order by permissionPosition,sort ").list();
		session.flush();
		return listFunctionTree;
	}
	
	/**
	 * 删除角色信息
	 * @param ids 角色信息id
	 * @return Role信息
	 */
	public void deleteRole(String ids) throws Exception{
		//以逗号分隔
		String[] idArr = ids.split(",");
		for(int i=0;i < idArr.length;i++){//逐个递归删除
			recurseDeleteRole(Integer.parseInt(idArr[i]));
		}
	}
	
	/**
	 * 根据角色ID递归删除角色（包括子角色）
	 * @param roleId 角色表主键ID
	 */
	@SuppressWarnings("unchecked")
	private void recurseDeleteRole(int RoleId){
		//删除当前角色
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" delete from ROLE where id = ? ");
		query.setInteger(0, RoleId);
		query.executeUpdate();
		session.flush();
		
		//查询子孙角色，递归删除
		Query queryParent = session.createQuery(" from Role where parentId = ? ");
		queryParent.setInteger(0, RoleId);
		List<Role> roleList = queryParent.list();
		for(int i=0;i < roleList.size();i++){
			Role role = roleList.get(i);
			//递归删除
			recurseDeleteRole(role.getId());
		}
	}
	
	/**
	 * 更新角色信息
	 * @param id 角色id
	 * @param roleName 角色名称
	 * @param roleType 角色类型
	 * @param permission 权限
	 */
	public void updateRole(String id, String roleName, String roleType, String permission) throws Exception{
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" update ROLE set ROLE_NAME=?,ROLE_TYPE=?,UPDATE_TIME=NOW(),PERMISSION_CONTENT=? where id=? ");
		query.setString(0, roleName);
		query.setString(1, roleType);
		query.setString(2, permission);
		query.setInteger(3, Integer.parseInt(id));
		query.executeUpdate();
		session.flush();
	}
	
	/**
	 * 更新用户信息表中的角色信息
	 * @param id 角色id
	 * @param roleName 角色名称
	 * @param roleType 角色类型
	 */
	public void updateRoleToUser(String id, String roleName, String roleType) throws Exception{
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" update USER set ROLE_NAME=?, POSITION=? where ROLE_ID=? ");
		query.setString(0, roleName);
		query.setString(1, roleType);
		query.setInteger(2, Integer.parseInt(id));
		query.executeUpdate();
		session.flush();
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