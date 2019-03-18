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
import com.dyst.systemmanage.dao.DepartmentDao;
import com.dyst.systemmanage.entities.Department;

/**
 * @author： cgk
 * @date：2016-03-02
 * @version：0.0.1
 * @doc：部门信息业务层实现类，主要实现方法：
 *       1.通过用户名获取用户信息
 *       2.获取所有的部门信息
 *       3.删除部门信息
 *       4.根据部门编号获取部门信息
 *       5.根据部门名称获取部门信息
 *       6.自动生成部门编号
 *       7.判断部门名称是否存在，排除指定的部门编号
 *       8.更新用户信息表里的部门名称
 */
@Repository("deptDao")
public class DepartmentDaoImpl extends BaseDaoImpl implements DepartmentDao {
	/**
	 * 根据条件分页查询部门信息
	 * @param deptNo
	 * @param deptName
	 * @param infoSource
	 * @param pageNo  第几页
	 * @param pageSize 每页条数
	 * @return PageResult pageResult
	 */
	@SuppressWarnings("rawtypes")
	public PageResult getDeptForPage(String deptNo, String deptName, String infoSource, String jxkh, int pageNo, int pageSize) throws Exception{
		//条件
		StringBuilder sb = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		if(deptNo != null && !"".equals(deptNo.trim())){
			sb.append(" and a.deptNo like :deptNo ");
			map.put("deptNo", "%" + deptNo.trim() + "%");
		}
		if(deptName != null && !"".equals(deptName.trim())){
			sb.append(" and a.deptName like :deptName ");
			map.put("deptName", "%" + deptName.trim() + "%");
		}
		if(infoSource != null && !"".equals(infoSource.trim())){
			sb.append(" and a.infoSource in (:infoSource) ");
			map.put("infoSource", (Object[])infoSource.trim().split(";"));
		}
		if(jxkh != null && !"".equals(jxkh.trim())){
			sb.append(" and a.jxkh in (:jxkh) ");
			map.put("jxkh", (Object[])jxkh.trim().split(";"));
		}
		
		//查询数据
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from Department a where a.id != 0 " + sb.append(" order by a.id ").toString());
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
		Query queryCount = session.createQuery("select count(*) from Department a where a.id != 0 " + sb.toString());
		queryCount = setParameter(queryCount, map);
		long totalCount = (Long)queryCount.uniqueResult();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}

	/**
	 * 获取所有的部门信息
	 * @return List<Department>信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Department> getAllDept() throws Exception {
		Session session = this.getSessionFactory().getCurrentSession();
		List listDept = session.createQuery(" from Department order by id ").list();
		session.flush();
		return listDept;
	}
	
	/**
	 * 获取所有的部门信息
	 * @return List<Department>信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Department> getAllDept2() throws Exception {
		Session session = this.getSessionFactory().getCurrentSession();
		List listDept = session.createQuery(" from Department ").list();
		session.flush();
		return listDept;
	}
	
	/**
	 * 获取顶级部门信息
	 * @return
	 *     部门信息集合
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Department> getMaxDept() throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		List listDept = session.createQuery(" from Department where parentNo=deptNo order by id ").list();
		session.flush();
		return listDept;
	}
	
	/**
	 * 根据部门编号加载子部门
	 * @param deptNo 父级部门编号
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Department> getDeptByParentNo(String deptNo) throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from Department where parentNo=:parentNo and deptNo!=parentNo order by id ");
		query.setString("parentNo", deptNo);
		List listDept = query.list();
		session.flush();
		return listDept;
	}
	
	/**
	 * 删除部门信息 TODO1
	 * @param ids 部门信息id
	 */
	@SuppressWarnings("rawtypes")
	public void deleteDept(String ids) throws Exception{
		Session session = getSessionFactory().getCurrentSession();
		Query query = null;
		
		//查询部门id，查询系统自身的编号
		query = session.createSQLQuery(" select SYSTEM_NO from DEPARTMENT WHERE DEPT_NO = :DEPT_NO ");
		query.setString("DEPT_NO", ids);
		List listQuery = query.list();
		//获取编号
		String systemNo = "";
		if(listQuery != null && listQuery.size() > 0 && listQuery.get(0) != null){
			systemNo = listQuery.get(0) + "";
		}
		
		//如果存在，系统自身编码，则删除子孙部门
		if(systemNo != null && !"".equals(systemNo.trim())){
			query = session.createSQLQuery(" delete from DEPARTMENT where SYSTEM_NO like :SYSTEM_NO ");
			query.setString("SYSTEM_NO", systemNo + "%");
			query.executeUpdate();
			session.flush();
		}
	}
	
	/**
	 * 根据部门编号获取部门信息
	 * @param deptNo 部门编号
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Department> getDeptByDeptNo(String deptNo) throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from Department where deptNo=:deptNo ");
		query.setString("deptNo", deptNo);
		List listDept = query.list();
		session.flush();
		return listDept;
	}
	
	/**
	 * 根据部门名称获取部门信息
	 * @param deptName 部门名称
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Department> getDeptByDeptName(String deptName) throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from Department where deptName=:deptName ");
		query.setString("deptName", deptName);
		List listDept = query.list();
		session.flush();
		return listDept;
	}
	
//	/**
//	 * 自动生成部门编号
//	 * @return
//	 */
//	@SuppressWarnings({ "finally", "unchecked" })
//	public String getDeptNo() throws Exception{
//		String deptNo = null;
//		List listQuery = new ArrayList();
//		Session session = null;
//		StringBuilder hql= new StringBuilder(" SELECT (CASE WHEN ycnt = 0 THEN CONCAT(DATE_FORMAT(NOW(),'%Y%m%d'), '0001') ELSE CONCAT(id + 1, '') END) AS DEPT_NO ");
//		hql.append(" FROM (SELECT MAX(DEPT_NO) AS id, COUNT(1) AS ycnt FROM DEPARTMENT aa WHERE SUBSTRING(DEPT_NO, 1, 8) = DATE_FORMAT(NOW(),'%Y%m%d')) a ");
//		try{
//			//执行查询
//			session = this.getSessionFactory().getCurrentSession();
//			listQuery = session.createSQLQuery(hql.toString()).list();
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new Exception();
//		}finally{
//			//获取结果
//			if(listQuery != null && listQuery.size() > 0){
//				deptNo = listQuery.get(0)+"";
//			}
//			return deptNo;
//		}
//	}
	
	/**
	 * 自动生成部门编号
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "finally" })
	public String getDeptNo(String parentNo) throws Exception{
		String deptNo = "";
		List listQuery = new ArrayList();
		Session session = null;
		
		try{
			//根据parentNo部门编号，查询部门信息，获取其系统自生成的编码
			session = this.getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery(" select SYSTEM_NO from DEPARTMENT WHERE DEPT_NO = :DEPT_NO ");
			query.setString("DEPT_NO", parentNo.trim());
			listQuery = query.list();
			
			//没有系统自生成的编码，则默认为68
			String systemNo = "";
			if(listQuery != null && listQuery.size() > 0 && listQuery.get(0) != null){
				systemNo = listQuery.get(0) + "";
			} else {
				systemNo = "68";
			}
			
			//执行查询
			query = session.createSQLQuery("select max(SYSTEM_NO) as SYSTEM_NO from DEPARTMENT WHERE SYSTEM_NO like :SYSTEM_NO ");
			query.setString("SYSTEM_NO", systemNo.trim() + "__");
			listQuery = query.list();
			//生成编码
			if(listQuery != null && listQuery.size() > 0 && listQuery.get(0) != null){
				deptNo = (Integer.parseInt(listQuery.get(0) + "") + 1) + "";
			} else {
				deptNo = systemNo + "01";
			}
		} catch(Exception e){
			e.printStackTrace();
			throw new Exception();
		} finally{
			return deptNo;
		}
	}
	
	/**
	 * 判断部门名称是否存在，排除指定的部门编号
	 * @param deptNo 部门编号
	 * @param deptName 部门名称
	 */
	@SuppressWarnings("rawtypes")
	public boolean isDeptNameExist(String deptNo, String deptName) throws Exception{
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from Department where deptNo != :deptNo and deptName = :deptName ");
		query.setString("deptNo", deptNo);
		query.setString("deptName", deptName);
		List listDept = query.list();
		session.flush();
		
		if(listDept != null && listDept.size() > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 更新用户信息表里的部门名称
	 * @param id 角色id
	 * @param roleName 角色名称
	 * @param roleType 角色类型
	 */
	public void updateDeptToUser(String deptNo, String deptName) throws Exception{
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" update USER set DEPT_NAME= :DEPT_NAME where DEPT_ID= :DEPT_ID ");
		query.setString("DEPT_NAME", deptName);
		query.setString("DEPT_ID", deptNo);
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
	
	/**
	 * 获取本部门以下的绩效考核部门
	 * @param systemNo
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Department> getKhbm(String systemNo) throws Exception {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(" from Department where jxkh = '1' " + (systemNo != null && !"".equals(systemNo.trim())? " and systemNo like :systemNo ":"") + " order by id ");
		if(systemNo != null && !"".equals(systemNo.trim())){
			query.setString("systemNo", systemNo + "%");
		}
		List listDept = query.list();
		session.flush();
		
		return listDept;
	}
}