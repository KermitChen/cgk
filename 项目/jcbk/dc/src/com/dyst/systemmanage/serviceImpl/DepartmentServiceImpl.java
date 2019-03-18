package com.dyst.systemmanage.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.dao.DepartmentDao;
import com.dyst.systemmanage.entities.Department;
import com.dyst.systemmanage.service.DepartmentService;
/**
 * @author： cgk
 * @date：2016-03-02
 * @version：0.0.1
 * @doc：部门信息业务层实现类，主要实现方法：
 *       1.根据条件分页查询部门信息
 *       2.获取所有的部门信息
 *       3.删除部门信息
 *       4.新增部门信息
 *       5.根据部门编号获取部门信息
 *       6.根据部门名称获取部门信息
 *       7.自动生成部门编号
 *       8.通过id获取部门信息
 *       9.修改部门信息
 *       10.判断部门名称是否存在，排除指定的部门编号
 *       11.更新用户信息表里的部门名称
 */
@Service("deptService")
public class DepartmentServiceImpl implements DepartmentService {

	//注入Dao
	private DepartmentDao deptDao;
	public DepartmentDao getDeptDao() {
		return deptDao;
	}
	@Autowired
	public void setDeptDao(DepartmentDao deptDao) {
		this.deptDao = deptDao;
	}
	
	@Resource 
	private BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	/**
	 * 根据条件分页查询部门信息
	 * @param deptNo
	 * @param deptName
	 * @param infoSource
	 * @param pageNo  第几页
	 * @param pageSize 每页条数
	 * @return PageResult pageResult
	 */
	public PageResult getDeptForPage(String deptNo, String deptName, String infoSource, String jxkh, int pageNo, int pageSize) throws Exception{
		return deptDao.getDeptForPage(deptNo, deptName, infoSource, jxkh, pageNo, pageSize);
	}

	/**
	 * 获取所有的部门信息
	 * @return
	 *     部门信息集合
	 */
	public List<Department> getAllDept() throws Exception{
		return deptDao.getAllDept();
	}
	
	/**
	 * 获取所有的部门信息
	 * @return
	 *     部门信息集合
	 */
	public List<Department> getAllDept2() throws Exception{
		return deptDao.getAllDept2();
	}
	
	/**
	 * 获取顶级部门信息
	 * @return
	 *     部门信息集合
	 */
	public List<Department> getMaxDept() throws Exception{
		return deptDao.getMaxDept();
	}
	
	/**
	 * 根据部门编号加载子部门
	 * @param deptNo 父级部门编号
	 */
	public List<Department> getDeptByParentNo(String deptNo) throws Exception{
		return deptDao.getDeptByParentNo(deptNo);
	}
	
	/**
	 * 删除部门信息
	 * @param ids 部门信息id
	 */
	public void deleteDept(String ids) throws Exception{
		deptDao.deleteDept(ids);
	}
	
	/**
	 * 新增部门信息
	 * @param Department 对象
	 */
	public void addDept(Department dept) throws Exception{
		baseDao.save(dept);
	}
	
	/**
	 * 根据部门编号获取部门信息
	 * @param deptNo 部门编号
	 * return Department 对象
	 */
	public List<Department> getDeptByDeptNo(String deptNo) throws Exception{
		return deptDao.getDeptByDeptNo(deptNo);
	}
	
	/**
	 * 根据部门名称获取部门信息
	 * @param deptName 部门名称
	 * return Department 对象
	 */
	public List<Department> getDeptByDeptName(String deptName) throws Exception{
		return deptDao.getDeptByDeptName(deptName);
	}
	
	/**
	 * 自动生成部门编号
	 * @return
	 */
	public String getDeptNo(String parentNo) throws Exception{
		return deptDao.getDeptNo(parentNo);
	}
	
	/**
	 * 通过id获取部门信息
	 * @param id 信息编号
	 * @return Department信息
	 */
	public Department getDept(int id) throws Exception{
		return (Department)baseDao.getObjectById(Department.class, id);
	}
	
	/**
	 * 修改部门信息
	 * @param Department 对象
	 */
	public void updateDept(Department dept) throws Exception{
		baseDao.update(dept);
	}
	
	/**
	 * 判断部门名称是否存在，排除指定的部门编号
	 * @param deptNo 部门编号
	 * @param deptName 部门名称
	 */
	public boolean isDeptNameExist(String deptNo, String deptName) throws Exception{
		return deptDao.isDeptNameExist(deptNo, deptName);
	}
	
	/**
	 * 更新用户信息表里的部门名称
	 * @param id 角色id
	 * @param roleName 角色名称
	 * @param roleType 角色类型
	 */
	public void updateDeptToUser(String deptNo, String deptName) throws Exception{
		deptDao.updateDeptToUser(deptNo, deptName);
	}
	
	/**
	 * 获取本部门以下的绩效考核部门
	 * @param systemNo
	 */
	public List<Department> getKhbm(String systemNo) throws Exception{
		return deptDao.getKhbm(systemNo);
	}
}