package com.dyst.systemmanage.serviceImpl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.systemmanage.dao.*;
import com.dyst.systemmanage.entities.*;
import com.dyst.systemmanage.service.*;

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
 *       6.新增用户信息
 *       7.通过id获取用户信息
 *       8.修改用户信息
 *       9.获取所有用户信息
 *       10.批量授权
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	//注入持久层dao
	@Resource 
	private BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Resource 
	private UserDao userDao;
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/**
	 * 通过用户名获取用户信息
	 * @param loginName
	 * @return User信息
	 */
	@SuppressWarnings("rawtypes")
	public User getUser(String loginName) throws Exception{
		List listUser = userDao.getUser(loginName);
		//如果没有，则返回null
		if(listUser == null || listUser.size() < 1){
			return null;
		}
		return (User)listUser.get(0);
	}
	
	/**
	 * 根据用户和密码获取用户信息
	 * @param loginName 用户名
	 * @param password  密码
	 * @return User信息
	 */
	@SuppressWarnings("rawtypes")
	public User getUser(String loginName, String password) throws Exception{
		List userList = userDao.getUser(loginName, password);
		//如果没有，则返回null
		if(userList == null || userList.size() < 1){
			return null;
		}
		return (User)userList.get(0);
	}
	
	/**
	 * 通过姓名和身份证号获取用户信息
	 * @param username  姓名
	 * @param idcard  身份证号
	 * @return User信息
	 */
	@SuppressWarnings("rawtypes")
	public User getUserByNameAndIdCard(String username, String idcard) throws Exception{
		List userList = userDao.getUserByNameAndIdCard(username, idcard);
		//如果没有，则返回null
		if(userList == null || userList.size() < 1){
			return null;
		}
		return (User)userList.get(0);
	}
	
	/**
	 * 通过类型代码获取字典数据
	 * @param typeCodes 可以是多个，以逗号隔开
	 * @return List<Dictionary>
	 */
	public List<Dictionary> getDictionarysByTypeCode(String typeCodes) throws Exception{
		return userDao.getDictionarysByTypeCode(typeCodes);
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
//	public PageResult getUserForPage(String position, String loginName, String username, String deptno, String roleType, 
//			String roleId, String infoSource, String startTime, String endTime, int pageNo, int pageSize) throws Exception{
//		return userDao.getUserForPage(position, loginName, username, deptno, roleType, roleId, infoSource, startTime, endTime, pageNo, pageSize);
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
	public PageResult getUserForPage(String position, String deptNo, String loginName, String username, String deptno, String roleType, 
			String roleId, String infoSource, String startTime, String endTime, int pageNo, int pageSize) throws Exception{
		return userDao.getUserForPage(position, deptNo, loginName, username, deptno, roleType, roleId, infoSource, startTime, endTime, pageNo, pageSize);
	}
	
	/**
	 * 删除用户信息
	 * @param ids 用户信息id
	 * @return User信息
	 */
	public void deleteUser(String ids) throws Exception{
		userDao.deleteUser(ids);
	}
	
	/**
	 * 新增用户信息
	 * @param user 对象
	 */
	public void addUser(User user) throws Exception{
		baseDao.save(user);
	}
	
	/**
	 * 通过id获取用户信息
	 * @param id 信息编号
	 * @return User信息
	 */
	public User getUser(int id) throws Exception{
		return (User)baseDao.getObjectById(User.class, id);
	}
	
	/**
	 * 修改用户信息
	 * @param user 对象
	 */
	public void updateUser(User user) throws Exception{
		baseDao.update(user);
	}
	
	/**
	 * 获取所有用户信息
	 * @return List<User>信息
	 */
	public List<User> getAllUser() throws Exception{
		return userDao.getAllUser();
	}
	
	/**
	 * 批量授权
	 * @param userIds 用户信息id
	 * @param roleId 数据格式：角色ID:角色名称:角色类型
	 * 
	 */
	public void grantRole(String userIds, String roleId) throws Exception{
		userDao.grantRole(userIds, roleId);
	}
	//查询相应条件的用户
	public List<User> getUserList(QueryHelper queryHelper){
		return userDao.findObjects(queryHelper);
	}
}
