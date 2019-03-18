package com.dyst.systemmanage.service;

import java.util.List;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.systemmanage.entities.User;

/**
 * @author： cgk
 * @date：2016-03-02
 * @version：0.0.1
 * @doc：用户信息业务层接口，主要接口方法：
 *       1.通过用户名获取用户信息
 *       2.通过用户和密码获取用户信息
 *       3.通过类型代码获取字典数据
 *       4.根据条件分页查询用户信息
 *       5.删除用户信息
 *       6.新增用户信息
 *       7.通过id获取用户信息
 *       8.修改用户信息
 *       9.获取所有用户信息
 *       10.批量授权
 */
public interface UserService {
	/**
	 * 通过用户名获取用户信息
	 * @param loginName 用户名
	 * @return User信息
	 */
	public User getUser(String loginName) throws Exception;
	
	/**
	 * 通过用户和密码获取用户信息
	 * @param loginName 用户名
	 * @param password  密码
	 * @return User信息
	 */
	public User getUser(String loginName, String password) throws Exception;
	
	/**
	 * 通过姓名和身份证号获取用户信息
	 * @param username  姓名
	 * @param idcard  身份证号
	 * @return User信息
	 */
	public User getUserByNameAndIdCard(String username, String idcard) throws Exception;
	
	/**
	 * 通过类型代码获取字典数据
	 * @param typeCodes 可以是多个，以逗号隔开
	 * @return List<Dictionary>
	 */
	public List<Dictionary> getDictionarysByTypeCode(String typeCodes) throws Exception;
	
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
//	public PageResult getUserForPage(String position, String loginName, String username, String deptno, String roleType, 
//			String roleId, String infoSource, String startTime, String endTime, int pageNo, int pageSize) throws Exception;
	
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
	public PageResult getUserForPage(String position, String deptNo, String loginName, String username, String deptno, String roleType, 
			String roleId, String infoSource, String startTime, String endTime, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 删除用户信息
	 * @param ids 用户信息id
	 * @return User信息
	 */
	public void deleteUser(String ids) throws Exception;
	
	/**
	 * 新增用户信息
	 * @param user 对象
	 */
	public void addUser(User user) throws Exception;
	
	/**
	 * 通过id获取用户信息
	 * @param id 信息编号
	 * @return User信息
	 */
	public User getUser(int id) throws Exception;
	
	/**
	 * 修改用户信息
	 * @param user 对象
	 */
	public void updateUser(User user) throws Exception;
	
	/**
	 * 获取所有用户信息
	 * @return List<User>信息
	 */
	public List<User> getAllUser() throws Exception;
	
	/**
	 * 批量授权
	 * @param userIds 用户信息id
	 * @param roleId 数据格式：角色ID:角色名称:角色类型
	 * 
	 */
	public void grantRole(String userIds, String roleId) throws Exception;
	
	/**
	 * 查询相应条件的用户
	 * @param queryHelper
	 * @return
	 */
	public List<User> getUserList(QueryHelper queryHelper);
}
