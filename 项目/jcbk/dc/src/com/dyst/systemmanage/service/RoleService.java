package com.dyst.systemmanage.service;

import java.util.List;

import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.entities.FunctionTree;
import com.dyst.systemmanage.entities.Role;

/**
 * @author： cgk
 * @date：2016-03-20
 * @version：0.0.1
 * @doc：角色信息业务层接口，主要接口方法：
 *       1.根据角色Id获取角色数据
 *       2.根据权限位获取对应的功能（必须按权限位升序排序）
 *       3.获取所有的角 色
 *       4.根据条件获取该用户所创建的角色
 *       5.获取所有的可用的功能列表
 *       6.删除角色信息
 *       7.新增角色信息
 *       8.更新角色信息
 *       9.更新用户信息表中的角色信息
 */
public interface RoleService {
	/**
	 * 根据角色Id获取角色数据
	 * @param roleId 角色Id
	 * @return Role信息
	 */
	public Role getRoleById(int roleId) throws Exception;
	
	/**
	 * 根据权限位获取对应的功能（必须按权限位升序排序）
	 * @param rank 权限级别   '1' 和 '2' 
	 * @param permissionPositions 权限位集合，逗号隔开
	 * @param enable 是否启用  1启用   0不启用
	 * @return List<FunctionTree>
	 */
	public List<FunctionTree> getFunction(String ranks, String permissionPositions, String enable) throws Exception;
	
	/**
	 * 获取所有的角色
	 * @return List<Role>
	 */
	public List<Role> getRoles() throws Exception;
	
	/**
	 * 获取所有自己创建的角色
	 * @return List<Role>
	 */
	public List<Role> getRoles(String buildPno) throws Exception;
	
	/**
	 * 获取某角色的子孙角色
	 * @param roleId 角色Id 
	 */
	public List<Role> getAllMyRole(int roleId, String systemNo, String position) throws Exception;
	
	/**
	 * 根据条件获取该用户所创建的角色
	 * @param loginName 用户名
	 * @param roleName 角色名称
	 * @param roleType 角色类型
	 * @return PageResult pageResult
	 */
	public PageResult getAllMyRole(String loginName, int roleId, String systemNo, String position, String roleName, String roleType, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 获取所有的可用的功能列表
	 * @return List<FunctionTree>
	 */
	public List<FunctionTree> getFunctions() throws Exception;
	
	/**
	 * 删除角色信息
	 * @param ids 角色信息id
	 * @return Role信息
	 */
	public void deleteRole(String ids) throws Exception;
	
	/**
	 * 新增角色信息
	 * @param Role信息
	 * @return 
	 */
	public void addRole(Role role) throws Exception;
	
	/**
	 * 更新角色信息
	 * @param id 角色id
	 * @param roleName 角色名称
	 * @param roleType 角色类型
	 * @param permission 权限
	 */
	public void updateRole(String id, String roleName, String roleType, String permission) throws Exception;
	
	/**
	 * 更新用户信息表中的角色信息
	 * @param id 角色id
	 * @param roleName 角色名称
	 * @param roleType 角色类型
	 */
	public void updateRoleToUser(String id, String roleName, String roleType) throws Exception;
}
