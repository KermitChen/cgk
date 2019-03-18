package com.dyst.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dyst.activemq.entities.SyncDept;
import com.dyst.activemq.entities.SyncUser;

/**
 * jdbc操作
 * @author cgk
 *
 */
public class MySqlOperate {
	
	/**
	 * 新增或更新用户信息
	 * @param SyncUser sU
	 * @return  0失败，1成功
	 */
	@SuppressWarnings("finally")
	public static String insertOrUpdateUser(SyncUser sU){
		MySqlConnection mySqlConnection = null;
	   	Connection con = null; 
	   	PreparedStatement st = null;
	   	PreparedStatement stEdit = null;
	   	ResultSet rs = null;
	   	String result = "1";
	   	StringBuilder sb = new StringBuilder();
	   	try {
	   		//获取连接
	   		mySqlConnection = new MySqlConnection();
	       	con = mySqlConnection.getConnection();
	       	con.setAutoCommit(false);//自动提交
	        
	       	if(sU != null){
	       		//查询用户信息是否存在
	       		st = con.prepareStatement(" select count(1) from USER where LOGIN_NAME=? ");
	       		st.setString(1, sU.getUserLoginId());
	       		rs = st.executeQuery();
		       	if(rs.next()){
		       		//根据部门ID，查询部门名称
	       			String dept_name = "";
	       			String system_no = "";
	       			Map<String, String> map = null;
	       			if(sU.getDeptCode() != null && !"".equals(sU.getDeptCode())){
	       				map = getDeptName(sU.getDeptCode());
	       				dept_name = map.get("deptName");
	       				system_no = map.get("systemNo");
	       			}
	       			
		       		int count = rs.getInt(1);
		       		if(count == 0){//没有记录，可以插入
		       			//执行---sU.getDeptName()!=null?sU.getDeptName():""
		       			sb.append(" INSERT INTO USER(LOGIN_NAME, USER_NAME, PASSWD, TELPHONE, IDENTITY_CARD, DEPT_ID, SYSTEM_NO, DEPT_NAME, ")
		       				.append("BUILD_PNO, BUILD_NAME, BUILD_TIME, UPDATE_TIME, INFO_SOURCE, ENABLE, LSKHBM, LSKHBMMC) ").append(" VALUES(?, ?")
		       				.append(", ?, ?, ?, ?, ?, ?, 'system', '系统', now(), now(), '2', '1', ?, ?)");
				       	
		       			stEdit = con.prepareStatement(sb.toString());
		       			stEdit.setString(1, (sU.getUserLoginId()!=null?sU.getUserLoginId():""));
		       			stEdit.setString(2, (sU.getUserName()!=null?sU.getUserName():""));
		       			stEdit.setString(3, (sU.getUserPassword()!=null?sU.getUserPassword():""));
		       			stEdit.setString(4, (sU.getMobilePhone()!=null?sU.getMobilePhone():""));
		       			stEdit.setString(5, (sU.getIdcard()!=null?sU.getIdcard():""));
		       			stEdit.setString(6, (sU.getDeptCode()!=null?sU.getDeptCode():""));
		       			stEdit.setString(7, (system_no!=null?system_no:""));
		       			stEdit.setString(8, (dept_name!=null?dept_name:""));
		       			stEdit.setString(9, (sU.getDeptCode()!=null?sU.getDeptCode():""));
		       			stEdit.setString(10, (dept_name!=null?dept_name:""));
		       			stEdit.execute(); // 插入表记录
		       		} else {//有记录，则更新
		       			//sU.getDeptName()!=null?sU.getDeptName():""
		       			sb.append(" UPDATE USER SET USER_NAME=?, PASSWD=?, TELPHONE=?, IDENTITY_CARD=?, DEPT_ID=?, SYSTEM_NO=?, DEPT_NAME=?, UPDATE_TIME=now(), ENABLE='1' ")
		       			.append(" where LOGIN_NAME=? ");
				       	
		       			stEdit = con.prepareStatement(sb.toString());
		       			stEdit.setString(1, (sU.getUserName()!=null?sU.getUserName():""));
		       			stEdit.setString(2, (sU.getUserPassword()!=null?sU.getUserPassword():""));
		       			stEdit.setString(3, (sU.getMobilePhone()!=null?sU.getMobilePhone():""));
		       			stEdit.setString(4, (sU.getIdcard()!=null?sU.getIdcard():""));
		       			stEdit.setString(5, (sU.getDeptCode()!=null?sU.getDeptCode():""));
		       			stEdit.setString(6, (system_no!=null?system_no:""));
		       			stEdit.setString(7, (dept_name!=null?dept_name:""));
		       			stEdit.setString(8, (sU.getUserLoginId()!=null?sU.getUserLoginId():""));
		       			st.execute(); // 插入表记录
		       		}
		       	}
	       	}
	       	
	       	//提交
	       	con.commit();
	   	} catch (Exception e) {
	   		//回滚
			if(con != null){
				con.rollback();
			}
			result = "0";
			e.printStackTrace();
		} finally{
			//关闭连接
   			if(stEdit != null){
   				try {
					stEdit.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
   			}
			if(mySqlConnection != null){
				mySqlConnection.close(con, st, rs);
			}
			
			//记录日志
			CommonUtils.writeFile(Config.getInstance().getLogFolder(), "dc.log", "OA用户信息同步：" + JSON.toJSONString(sU) + "，同步结果：" + result, true, true);
			
			//返回结果
			return result;
		}
	}
	
	/**
	 * 删除用户信息
	 * @param SyncUser sU
	 * @return  0失败，1成功
	 */
	@SuppressWarnings("finally")
	public static String deleteUser(SyncUser sU){
		MySqlConnection mySqlConnection = null;
	   	Connection con = null; 
	   	PreparedStatement st = null;
	   	ResultSet rs = null;
	   	String result = "1";
	   	try {
	   		//获取连接
	   		mySqlConnection = new MySqlConnection();
	       	con = mySqlConnection.getConnection();
	       	con.setAutoCommit(false);//自动提交
	        
	       	//删除记录
	       	if(sU != null){
	       		st = con.prepareStatement(" DELETE FROM USER WHERE LOGIN_NAME=? ");// 操作数据库
	       		st.setString(1, sU.getUserLoginId());
	       		st.execute(); 
	       	}
	       	
	       	//提交
	       	con.commit();
	   	} catch (Exception e) {
	   		//回滚
			if(con != null){
				con.rollback();
			}
			result = "0";
			e.printStackTrace();
		} finally{
			//关闭连接
			if(mySqlConnection != null){
				mySqlConnection.close(con, st, rs);
			}
			
			//记录日志
			CommonUtils.writeFile(Config.getInstance().getLogFolder(), "dc.log", "OA用户信息同步：" + JSON.toJSONString(sU) + "，同步结果：" + result, true, true);
			
			//返回结果
			return result;
		}
	}
	
	/**
	 * 新增或更新部门信息
	 * @param SyncDept sD
	 * @return  0失败，1成功
	 */
	@SuppressWarnings("finally")
	public static String insertOrUpdateDept(SyncDept sD){
		MySqlConnection mySqlConnection = null;
	   	Connection con = null; 
	   	PreparedStatement st = null;
	   	PreparedStatement stEdit = null;
	   	ResultSet rs = null;
	   	String result = "1";
	   	StringBuilder sb = new StringBuilder();
	   	try {
	   		//获取连接
	   		mySqlConnection = new MySqlConnection();
	       	con = mySqlConnection.getConnection();
	       	con.setAutoCommit(false);//自动提交
	        
	       	if(sD != null){
	       		//查询用户信息是否存在
	       		st = con.prepareStatement(" select count(1) from DEPARTMENT where DEPT_NO=? ");// 操作数据库
	       		st.setString(1, sD.getDeptCode());
	       		rs = st.executeQuery();
		       	if(rs.next()){
		       		//查询父级部门名称
		       		String dept_name = "";
		       		Map<String, String> map = null;
		       		if(sD.getParentDeptCode() != null && !"".equals(sD.getParentDeptCode())){
		       			map = getDeptName(sD.getParentDeptCode());
		       			dept_name = map.get("deptName");
		       		}
		       		
		       		int count = rs.getInt(1);
		       		if(count == 10){//没有记录，可以插入 //TODO   count == 0
		       			//获取系统自身编码
		       			String systemNo = "";
		       			try {
		       				systemNo = getDeptNo(sD.getParentDeptCode()!=null?sD.getParentDeptCode():"");
		       				//获取不到，则继续获取
		       				while(systemNo == null || "".equals(systemNo)){
		       					systemNo = getDeptNo(sD.getParentDeptCode()!=null?sD.getParentDeptCode():"");
		       				}
						} catch (Exception e) {
							systemNo = getDeptNo(sD.getParentDeptCode()!=null?sD.getParentDeptCode():"");//出错继续获取
						}
						
		       			//执行--sD.getParentDeptCode()!=null?sD.getParentDeptCode():""
		       			sb.append(" INSERT INTO DEPARTMENT(DEPT_NO, DEPT_NAME, DEPT_TELEPHONE, PARENT_NO, PARENT_NAME, BUILD_PNO, BUILD_NAME, BUILD_TIME, UPDATE_TIME, INFO_SOURCE, SYSTEM_NO) ")
		       				.append(" VALUES(?, ?, ?, ?, ?, 'system', '系统', now(), now(), '2', ?) ");
		       			
		       			stEdit = con.prepareStatement(sb.toString());
		       			stEdit.setString(1, (sD.getDeptCode()!=null?sD.getDeptCode():""));
		       			stEdit.setString(2, (sD.getName()!=null?sD.getName():""));
		       			stEdit.setString(3, (sD.getPhone()!=null?sD.getPhone():""));
		       			stEdit.setString(4, (sD.getParentDeptCode()!=null?sD.getParentDeptCode():""));
		       			stEdit.setString(5, dept_name);
		       			stEdit.setString(6, systemNo);
		       			stEdit.execute();// 插入表记录
		       		} else if(count == 100) {//有记录，则更新  TODO   count > 0
		       			sb.append(" UPDATE DEPARTMENT SET DEPT_NAME=?, DEPT_TELEPHONE=?, PARENT_NO=?, PARENT_NAME=?, UPDATE_TIME=now() ").append(" where DEPT_NO=? ");
		       			
		       			stEdit = con.prepareStatement(sb.toString());
		       			stEdit.setString(1, (sD.getName()!=null?sD.getName():""));
		       			stEdit.setString(2, (sD.getPhone()!=null?sD.getPhone():""));
		       			stEdit.setString(3, (sD.getParentDeptCode()!=null?sD.getParentDeptCode():""));
		       			stEdit.setString(4, dept_name);
		       			stEdit.setString(5, sD.getDeptCode());
		       			stEdit.execute();// 插入表记录
		       		}
		       	}
	       	}
	       	
	       	//提交
	       	con.commit();
	   	} catch (Exception e) {
	   		//回滚
			if(con != null){
				con.rollback();
			}
			result = "0";
			e.printStackTrace();
		} finally{
			//关闭连接
			if(stEdit != null){
				try {
					stEdit.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(mySqlConnection != null){
				mySqlConnection.close(con, st, rs);
			}
			
			//记录日志
			CommonUtils.writeFile(Config.getInstance().getLogFolder(), "dc.log", "OA部门信息同步：" + JSON.toJSONString(sD) + "，同步结果：" + result, true, true);
			
			//返回结果
			return result;
		}
	}
	
	/**
	 * 自动生成部门编号
	 * @return
	 */
	@SuppressWarnings({ "finally"})
	public static String getDeptNo(String parentNo) throws Exception{
		MySqlConnection mySqlConnection = null;
	   	Connection con = null; 
	   	PreparedStatement st1 = null;
	   	PreparedStatement st2 = null;
	   	ResultSet rs1 = null;
	   	ResultSet rs2 = null;
	   	
		String deptNo = "";
		try{
			//获取连接
	   		mySqlConnection = new MySqlConnection();
	       	con = mySqlConnection.getConnection();
	       	con.setAutoCommit(false);//自动提交
	       	
			//根据parentNo部门编号，查询部门信息，获取其系统自生成的编码
	       	st1 = con.prepareStatement(" select SYSTEM_NO from DEPARTMENT WHERE DEPT_NO = ? order by id ");
	       	st1.setString(1, parentNo.trim());
	       	rs1 = st1.executeQuery();
	       	//没有系统自生成的编码，则默认为68
	       	String systemNo = "";
	       	if(rs1.next() && rs1.getString("SYSTEM_NO") != null && !"".equals(rs1.getString("SYSTEM_NO"))){
	       		systemNo = rs1.getString("SYSTEM_NO");
	       	} else {
	       		systemNo = "68";
	       	}
			
			//执行查询
	       	st2 = con.prepareStatement("select max(SYSTEM_NO) as SYSTEM_NO from DEPARTMENT WHERE SYSTEM_NO like ? ");
	       	st2.setString(1, systemNo.trim() + "__");
	       	rs2 = st2.executeQuery();
			//生成编码
			if(rs2.next() && rs2.getString("SYSTEM_NO") != null && !"".equals(rs2.getString("SYSTEM_NO"))){
				deptNo = (Long.parseLong(rs2.getString("SYSTEM_NO")) + 1) + "";
			} else {
				deptNo = systemNo + "01";
			}
			
			//提交
	       	con.commit();
		} catch(Exception e){
			//回滚
			if(con != null){
				con.rollback();
			}
			e.printStackTrace();
		} finally{
			if(rs2 != null){
				rs2.close();
			}
			if(st2 != null){
				st2.close();
			}
			if(rs1 != null){
				rs1.close();
			}
			if(st1 != null){
				st1.close();
			}
			if(con != null){
				con.close();
			}
			return deptNo;
		}
	}
	
	/**
	 * 删除部门信息
	 * @param SyncDept sD
	 * @return  0失败，1成功
	 */
	@SuppressWarnings("finally")
	public static String deleteDept(SyncDept sD){
		MySqlConnection mySqlConnection = null;
	   	Connection con = null; 
	   	PreparedStatement st = null;
	   	ResultSet rs = null;
	   	String result = "1";
	   	try {
	   		//获取连接
	   		mySqlConnection = new MySqlConnection();
	       	con = mySqlConnection.getConnection();
	       	con.setAutoCommit(false);//自动提交
	        
	       	//删除记录
	       	if(sD != null){//TODO
	       		st = con.prepareStatement(" DELETE FROM DEPARTMENT WHERE DEPT_NO=? and ID=0");// 操作数据库
	       		st.setString(1, sD.getDeptCode());
	       		st.execute(); 
	       	}
	       	
	       	//提交
	       	con.commit();
	   	} catch (Exception e) {
	   		//回滚
			if(con != null){
				con.rollback();
			}
			result = "0";
			e.printStackTrace();
		} finally{
			//关闭连接
			if(mySqlConnection != null){
				mySqlConnection.close(con, st, rs);
			}
			
			//记录日志
			CommonUtils.writeFile(Config.getInstance().getLogFolder(), "dc.log", "OA部门信息同步：" + JSON.toJSONString(sD) + "，同步结果：" + result, true, true);
			
			//返回结果
			return result;
		}
	}
	
	/**
	 * 根据部门ID查询部门名称
	 * @param dept_no
	 * @return  部门名称
	 */
	@SuppressWarnings("finally")
	public static Map<String, String> getDeptName(String deptNo){
		MySqlConnection mySqlConnection = null;
	   	Connection con = null; 
	   	PreparedStatement st = null;
	   	ResultSet rs = null;
	   	Map<String, String> map = new HashMap<String, String>();
	   	try {
	   		//获取连接
	   		mySqlConnection = new MySqlConnection();
	       	con = mySqlConnection.getConnection();
	       	con.setAutoCommit(false);//自动提交
	        
	       	//查询
	       	st = con.prepareStatement(" SELECT DEPT_NAME, SYSTEM_NO FROM DEPARTMENT WHERE DEPT_NO=? ");// 操作数据库
	       	st.setString(1, deptNo);
	       	rs = st.executeQuery();
	       	if(rs.next()){
	       		map.put("deptName", rs.getString(1));
	       		map.put("systemNo", rs.getString(2));
	       	}
	       	
	       	//提交
	       	con.commit();
	   	} catch (Exception e) {
	   		//回滚
			if(con != null){
				con.rollback();
			}
			e.printStackTrace();
		} finally{
			//关闭连接
			if(mySqlConnection != null){
				mySqlConnection.close(con, st, rs);
			}
			
			//返回结果
			return map;
		}
	}
	
//	public static void parentDeptName(){
//		MySqlConnection mySqlConnection = null;
//	   	Connection con = null; 
//	   	Statement st = null;
//	   	ResultSet rs = null;
//	   	Statement st2 = null;
//	   	try {
//	   		//获取连接
//	   		mySqlConnection = new MySqlConnection();
//	       	con = mySqlConnection.getConnection();
//	       	con.setAutoCommit(false);//自动提交
//	       	st = con.createStatement();// 操作数据库
//	       	st2 = con.createStatement();// 操作数据库
//	        
//	       	//查询用户信息是否存在
//	       	rs = st.executeQuery(" select DEPT_NO, DEPT_NAME, PARENT_NO from DEPARTMENT ");
//	       	int i = 0;
//		    while(rs.next()){
//		    	i++;
//		    	//查询父级部门名称
//	       		String dept_name = "";
//	       		Map<String, String> map = null;
//	       		if(rs.getString(3) != null && !"".equals(rs.getString(3))){
//	       			map = getDeptName(rs.getString(3));
//       				dept_name = map.get("deptName");
//	       		}
//	       		System.out.println(i + "====" + rs.getString(3) + "====" + dept_name);
//	       		st2.execute(" update DEPARTMENT set PARENT_NAME='" + dept_name + "' where DEPT_NO='" + rs.getString(1) + "' and PARENT_NO='" + rs.getString(3) + "' "); 
//	       	}
//	       	
//	       	//提交
//	       	con.commit();
//	   	} catch (Exception e) {
//	   		//回滚
//			if(con != null){
//				try {
//					con.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//			e.printStackTrace();
//		} finally{
//			//关闭连接
//			if(mySqlConnection != null){
//				mySqlConnection.close(con, st, rs);
//			}
//		}
//	}
	
//	public static void setDeptName(){
//		MySqlConnection mySqlConnection = null;
//	   	Connection con = null; 
//	   	Statement st = null;
//	   	ResultSet rs = null;
//	   	Statement st2 = null;
//	   	ResultSet rs2 = null;
//	   	Statement st3 = null;
//	   	try {
//	   		//获取连接
//	   		mySqlConnection = new MySqlConnection();
//	       	con = mySqlConnection.getConnection();
//	       	con.setAutoCommit(false);//自动提交
//	       	st = con.createStatement();// 操作数据库
//	       	st2 = con.createStatement();// 操作数据库
//	       	st3 = con.createStatement();// 操作数据库
//	        
//	       	//查询用户信息是否存在
//	       	rs = st.executeQuery(" select LOGIN_NAME, DEPT_ID from USER WHERE (DEPT_NAME is null or DEPT_NAME='' or DEPT_NAME='null') and DEPT_ID is not null and DEPT_ID!='' ");
//	       	int i = 0;
//		    while(rs.next()){
//		    	i++;
//		    	rs2 = st2.executeQuery(" select DEPT_NAME from DEPARTMENT where DEPT_NO='" + rs.getString(2) + "' ");
//		    	if(rs2.next()){
//		    		System.out.println(i + "====" + rs2.getString(1));
//		    		st3.execute(" update USER set DEPT_NAME='" + rs2.getString(1) + "' where LOGIN_NAME='" + rs.getString(1) + "' "); // 插入表记录
//			    	if(rs2 != null){
//			    		rs2.close();
//			    	}
//		    	}
//	       	}
//	       	
//	       	//提交
//	       	con.commit();
//	   	} catch (Exception e) {
//	   		//回滚
//			if(con != null){
//				try {
//					con.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//			e.printStackTrace();
//		} finally{
//			//关闭连接
//			if(mySqlConnection != null){
//				mySqlConnection.close(con, st, rs);
//			}
//		}
//	}
	
//	public static void setSystemNo(){
//		MySqlConnection mySqlConnection = null;
//	   	Connection con = null; 
//	   	Statement st = null;
//	   	ResultSet rs = null;
//	   	List<Department> list = new ArrayList<Department>();
//	   	try {
//	   		//获取连接
//	   		mySqlConnection = new MySqlConnection();
//	       	con = mySqlConnection.getConnection();
//	       	st = con.createStatement();// 操作数据库
//	        
//	       	//
//	       	rs = st.executeQuery(" select DEPT_NO, PARENT_NO, SYSTEM_NO from DEPARTMENT ");
//		    while(rs.next()){
//		    	Department dept = new Department();
//		    	dept.setDeptNo(rs.getString("DEPT_NO"));
//		    	dept.setParentNo(rs.getString("PARENT_NO"));
//		    	dept.setSystemNo(rs.getString("SYSTEM_NO"));
//		    	list.add(dept);
//	       	}
//	   	} catch (Exception e) {
//			e.printStackTrace();
//		} finally{
//			//关闭连接
//			if(mySqlConnection != null){
//				mySqlConnection.close(con, st, rs);
//			}
//			
//			//递归部门并更新系统自身编码
//		    recurseGetDept("440300", list);
//		}
//	}
//	
//	/**
//	 * 递归部门并更新系统自身编码
//	 */
//	private static void recurseGetDept(String deptNo, List<Department> depts){
//		//然后获取该部门的子孙部门，并递归获取部门下的用户信息
//		for(int i=0;i < depts.size();i++){
//			//获取一个部门
//			Department dept = depts.get(i);
//			//判断是否为子部门
//			if(deptNo != null && dept.getParentNo() != null && !dept.getDeptNo().trim().equals(dept.getParentNo().trim()) && deptNo.trim().equals(dept.getParentNo().trim())){//
//				//生成系统自身编码，并更新
//				try {
//					//生成系统自身编码
//					String systemNo = "";
//					try {
//						systemNo = getDeptNo(deptNo.trim());
//						//获取不到，则继续获取
//	       				while(systemNo == null || "".equals(systemNo)){
//	       					systemNo = getDeptNo(deptNo.trim());
//	       				}
//					} catch (Exception e) {
//						systemNo = getDeptNo(deptNo.trim());//出错继续获取
//					}
//					System.out.println(i + "deptNo：" + deptNo.trim() + "，systemNo：" + systemNo.trim());
//					
//					//更新
//					updateSystemNo(systemNo, dept.getDeptNo());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
//				recurseGetDept(dept.getDeptNo(), depts);
//			}
//		}
//	}
	
//	public static void updateSystemNo(String systemNo, String deptNo){
//		MySqlConnection mySqlConnection = null;
//	   	Connection con = null; 
//	   	Statement st = null;
//	   	try {
//	   		//获取连接
//	   		mySqlConnection = new MySqlConnection();
//	       	con = mySqlConnection.getConnection();
//	       	st = con.createStatement();// 操作数据库
//	       	
//	       	//执行
//	       	st.execute(" update DEPARTMENT set SYSTEM_NO='" + systemNo.trim() + "' where DEPT_NO='" + deptNo.trim() + "' "); // 插入表记录
//	   	} catch (Exception e) {
//	   		//出错继续更新
//	   		updateSystemNo(systemNo, deptNo);
//		} finally{
//			//关闭连接
//			if(mySqlConnection != null){
//				mySqlConnection.close(con, st, null);
//			}
//		}
//	}
//	
//	public static void setSystemNoToUser(){
//		MySqlConnection mySqlConnection = null;
//	   	Connection con = null; 
//	   	Statement st = null;
//	   	ResultSet rs = null;
//	   	try {
//	   		//获取连接
//	   		mySqlConnection = new MySqlConnection();
//	       	con = mySqlConnection.getConnection();
//	       	st = con.createStatement();// 操作数据库
//	        
//	       	//查询用户信息是否存在
//	       	rs = st.executeQuery(" select DEPT_NO, SYSTEM_NO from DEPARTMENT ");
//		    while(rs.next()){
//		    	System.out.println(rs.getString("DEPT_NO") + "=========" + rs.getString("SYSTEM_NO"));
//		    	updateUserSystemNo(rs.getString("SYSTEM_NO"), rs.getString("DEPT_NO"));
//	       	}
//	   	} catch (Exception e) {
//			e.printStackTrace();
//		} finally{
//			//关闭连接
//			if(mySqlConnection != null){
//				mySqlConnection.close(con, st, rs);
//			}
//		}
//	}
//	
//	public static void updateUserSystemNo(String systemNo, String deptNo){
//		MySqlConnection mySqlConnection = null;
//	   	Connection con = null; 
//	   	Statement st = null;
//	   	try {
//	   		//获取连接
//	   		mySqlConnection = new MySqlConnection();
//	       	con = mySqlConnection.getConnection();
//	       	st = con.createStatement();// 操作数据库
//	       	
//	       	//执行
//	       	st.execute(" update USER set SYSTEM_NO='" + systemNo.trim() + "' where DEPT_ID='" + deptNo.trim() + "' "); // 插入表记录
//	   	} catch (Exception e) {
//	   		//出错继续更新
//	   		updateUserSystemNo(systemNo, deptNo);
//		} finally{
//			//关闭连接
//			if(mySqlConnection != null){
//				mySqlConnection.close(con, st, null);
//			}
//		}
//	}
//	
//	public static void setZakdName(){
//		MySqlConnection mySqlConnection = null;
//	   	Connection con = null; 
//	   	Statement st = null;
//	   	ResultSet rs = null;
//	   	Statement st2 = null;
//	   	Statement st3 = null;
//	   	ResultSet rs2 = null;
//	   	try {
//	   		//获取连接
//	   		mySqlConnection = new MySqlConnection();
//	       	con = mySqlConnection.getConnection();
//	       	con.setAutoCommit(false);//自动提交
//	       	st = con.createStatement();// 操作数据库
//	       	st2 = con.createStatement();// 操作数据库
//	       	st3 = con.createStatement();// 操作数据库
//	        
//	       	//查询用户信息是否存在
//	       	rs = st.executeQuery(" select OBJECTID, ORGCODE from ZAKD ");
//		    while(rs.next()){
//		    	rs2 = st2.executeQuery(" select ORGNAME from ORGANIZATION where ORGCODE='" + rs.getString(2) + "' ");
//		    	if(rs2.next()){
//		    		st3.execute(" update ZAKD set ORGCODENAME='" + rs2.getString(1) + "' where OBJECTID='" + rs.getString(1) + "' "); // 插入表记录
//			    	if(rs2 != null){
//			    		rs2.close();
//			    	}
//		    	}
//	       	}
//	       	
//	       	//提交
//	       	con.commit();
//	   	} catch (Exception e) {
//	   		//回滚
//			if(con != null){
//				try {
//					con.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//			e.printStackTrace();
//		} finally{
//			//关闭连接
//			if(mySqlConnection != null){
//				mySqlConnection.close(con, st, rs);
//			}
//		}
//	}
	
	public static void main(String[] args) {
//		SyncUser sU = new SyncUser();
//		sU.setUserLoginId("aaaaaaa");
//		sU.setUserName("12121");
//		sU.setDeptCode("440399000000");
//		deleteUser(sU);
//		insertOrUpdateUser(sU);
		
//		SyncDept sD = new SyncDept();
//		sD.setDeptCode("1312312312");
//		sD.setName("了多少家开发商");
//		sD.setParentDeptCode("440300");
//		deleteDept(sD);
//		insertOrUpdateDept(sD);
		
//		setDeptName();
		//System.out.println(getDeptName("440398000000"));
//		parentDeptName();
		
//		setZakdName();
	}
}
