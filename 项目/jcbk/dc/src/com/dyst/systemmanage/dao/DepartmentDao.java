package com.dyst.systemmanage.dao;

import java.util.List;

import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.entities.Department;

/**
 * @author： cgk
 * @date：2016-03-02
 * @version：0.0.1
 * @doc：部门信息持久层接口，主要接口方法：
 *       1.根据条件分页查询部门信息
 *       2.获取所有的部门信息
 *       3.删除部门信息
 *       4.根据部门编号获取部门信息
 *       5.根据部门名称获取部门信息
 *       6.自动生成部门编号
 *       7.判断部门名称是否存在，排除指定的部门编号
 *       8.更新用户信息表里的部门名称
 */
public interface DepartmentDao extends BaseDao{
	/**
	 * 根据条件分页查询部门信息
	 * @param deptNo
	 * @param deptName
	 * @param infoSource
	 * @param pageNo  第几页
	 * @param pageSize 每页条数
	 * @return PageResult pageResult
	 */
	public PageResult getDeptForPage(String deptNo, String deptName, String infoSource, String jxkh, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 获取所有的部门信息
	 * @return List<Department>信息
	 */
	public List<Department> getAllDept() throws Exception;
	
	/**
	 * 获取所有的部门信息
	 * @return List<Department>信息
	 */
	public List<Department> getAllDept2() throws Exception;
	
	/**
	 * 获取顶级部门信息
	 * @return
	 *     部门信息集合
	 */
	public List<Department> getMaxDept() throws Exception;
	
	/**
	 * 根据部门编号加载子部门
	 * @param deptNo 父级部门编号
	 */
	public List<Department> getDeptByParentNo(String deptNo) throws Exception;
	
	/**
	 * 删除部门信息
	 * @param ids 部门信息id
	 */
	public void deleteDept(String ids) throws Exception;
	
	/**
	 * 根据部门编号获取部门信息
	 * @param deptNo 部门编号
	 */
	public List<Department> getDeptByDeptNo(String deptNo) throws Exception;
	
	/**
	 * 根据部门名称获取部门信息
	 * @param deptName 部门名称
	 */
	public List<Department> getDeptByDeptName(String deptName) throws Exception;
	
//	/**
//	 * 自动生成部门编号
//	 * @return
//	 */
//	public abstract String getDeptNo() throws Exception;
	
	/**
	 * 自动生成部门编号
	 * @return
	 */
	public abstract String getDeptNo(String parentNo) throws Exception;
	
	/**
	 * 判断部门名称是否存在，排除指定的部门编号
	 * @param deptNo 部门编号
	 * @param deptName 部门名称
	 */
	public boolean isDeptNameExist(String deptNo, String deptName) throws Exception;
	
	/**
	 * 更新用户信息表里的部门名称
	 * @param id 角色id
	 * @param roleName 角色名称
	 * @param roleType 角色类型
	 */
	public void updateDeptToUser(String deptNo, String deptName) throws Exception;

	/**
	 * 获取本部门以下的绩效考核部门
	 * @param systemNo
	 */
	public List<Department> getKhbm(String systemNo) throws Exception;

}