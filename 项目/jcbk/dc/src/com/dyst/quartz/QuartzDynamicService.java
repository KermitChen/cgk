package com.dyst.quartz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dyst.dynamicservice.oracle.OracleConnection;
import com.dyst.quartz.dao.JobDao;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.Config;

/**
 * 动态勤务Quartz定时器
 * @author 陈高科
 *    定时执行任务：
 *    	1.定时同步动态勤务系统的数据
 */
@Component("quartzDynamicService")
public class QuartzDynamicService{
	//注入持久层
	@Resource
    private JobDao jobDao;
	public JobDao getJobDao() {
		return jobDao;
	}
	public void setJobDao(JobDao jobDao) {
		this.jobDao = jobDao;
	}
	
	private SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
	private String dayString = dayFormat.format(new Date());//记录当前日期，如果日期不等，则删除两天前的排班信息
	private String deleteJcdStatusFlag = "0";
	
	/**
	 * 定时同步动态勤务系统的数据
	 */
	@Transactional
	public void updateDynamicService(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat hour = new SimpleDateFormat("HH");
		//判断是否更新数据
		if("1".equals(Config.getInstance().getUpdateDynamicService())){
			//更新时间范围
			Date nowTime = new Date();
			String endTime = dateFormat.format(nowTime);
			String startTime = dateFormat.format(CommonUtils.getTimeOf_N_Minute_Date("yyyy-MM-dd HH:mm:ss", nowTime, -Integer.parseInt(Config.getInstance().getUpdateDynamicServiceTime())));
			
			//删除排班信息
			try {
				if(!dayString.equals(dayFormat.format(new Date()))){
					//执行删除
					deleteBbdetailinfo();
					//更新时间
					dayString = dayFormat.format(new Date());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//更新组织结构organization----ORGANIZATION
			try {
				updateOrganization(startTime, endTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//更新卡点坐标信息zakdian_view-----ZAKD
			try {
				updateZakd(startTime, endTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//更新岗位与巡逻区域关联信息postarear----POSTAREAR
			try {
				updatePostArea(startTime, endTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//更新岗位信息post----POST
			try {
				updatePost(startTime, endTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//更新人员排班信息bbdetailinfo_3d----BBDETAILINFO_3D
			try {
				updateBbdetailinfo(startTime, endTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//更新人员信息policeinfo----POLICEINFO
			try {
				updatePoliceinfo(startTime, endTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//每天7点清理监测点状态表前一天的数据，仅清理一次
		if("07".equals(hour.format(new Date()))){
			if("0".equals(deleteJcdStatusFlag)){
				deleteJcdStatusFlag = "1";
				jobDao.deleteJcdStatus();
			}
		} else {
			deleteJcdStatusFlag = "0";
		}
	}
	
	/**
	 * 更新治安卡点信息
	 * @throws Exception
	 */
	private void updateZakd(String startTime, String endTime) throws Exception{
		OracleConnection oracleConnection = null;
	   	Connection con = null; 
	   	PreparedStatement st = null;
	   	ResultSet rs = null;
	   	try {
	   		//获取连接
	       	oracleConnection = new OracleConnection();
	       	con = oracleConnection.getConnection();
	        
	    	//获取需要治安卡点
	       	st = con.prepareStatement(" select ID, CODE, LEVELS, POSTION, X, Y, ORGCODE, DUTYNUM, DUTYNAME, DUTYPHONE, to_char(UPDATETIME, 'yyyy-mm-dd hh24:mi:ss') as UPDATETIME, CALLNUM from zakdian_view where UPDATETIME between to_date(?, 'yyyy-mm-dd hh24:mi:ss') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ");//操作数据库
	       	st.setString(1, startTime);
	       	st.setString(2, endTime);
	       	rs = st.executeQuery();
	       	while(rs.next()){
	       		//判断是否存在，如果存在，则更新，不存在，则插入
	       		int count = jobDao.isZakdExist(rs.getInt("ID"), rs.getString("CODE"));
	       		String type = "1";//增加
	       		if(count > 0){
	       			type = "2";//更新
	       		}
	       		
	       		//增加或更新
	       		jobDao.insertOrUpdateZakd(rs.getInt("ID"), rs.getString("CODE"), rs.getString("LEVELS"), rs.getString("POSTION"), 
	       				rs.getDouble("X"), rs.getDouble("Y"), rs.getString("ORGCODE"), getOrganizationNameByOrgcode(rs.getString("ORGCODE")), 
	       				rs.getString("DUTYNUM"), rs.getString("DUTYNAME"), rs.getString("DUTYPHONE"), 
	       				rs.getString("UPDATETIME"), rs.getString("CALLNUM"), type);
	       	}
	   	} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally{
			//关闭连接
			if(oracleConnection != null){
				oracleConnection.close(con, st, rs);
			}
		}
	}
	
	/**
	 * 更新岗位与巡逻区域关联信息
	 * @throws Exception
	 */
	private void updatePostArea(String startTime, String endTime) throws Exception{
		OracleConnection oracleConnection = null;
	   	Connection con = null; 
	   	PreparedStatement st = null;
	   	ResultSet rs = null;
	   	try {
	   		//获取连接
	       	oracleConnection = new OracleConnection();
	       	con = oracleConnection.getConnection();
	        
	    	//获取岗位与巡逻区域关联信息
	       	st = con.prepareStatement(" select ID, POSTCODE, AREACODE, to_char(UPDATETIME, 'yyyy-mm-dd hh24:mi:ss') as UPDATETIME, UPDATEFLAG from postarear where UPDATETIME between to_date(?, 'yyyy-mm-dd hh24:mi:ss') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ");
	       	st.setString(1, startTime);
	       	st.setString(2, endTime);
	       	rs = st.executeQuery();
	       	while(rs.next()){
	       		//增加或更新或删除你
	       		jobDao.insertOrUpdateOrDeletePostArea(rs.getInt("ID"), rs.getString("POSTCODE"), rs.getString("AREACODE"), 
	       				rs.getString("UPDATETIME"), rs.getString("UPDATEFLAG"));
	       	}
	   	} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally{
			//关闭连接
			if(oracleConnection != null){
				oracleConnection.close(con, st, rs);
			}
		}
	}
	
	/**
	 * 更新岗位信息
	 * @throws Exception
	 */
	private void updatePost(String startTime, String endTime) throws Exception{
		OracleConnection oracleConnection = null;
	   	Connection con = null; 
	   	PreparedStatement st = null;
	   	ResultSet rs = null;
	   	try {
	   		//获取连接
	       	oracleConnection = new OracleConnection();
	       	con = oracleConnection.getConnection();
	        
	    	//获取岗位信息
	       	st = con.prepareStatement(" select POSTCODE, POSTNAME, ORGCODE, POSTTYPECODE, POSTTYPENAME, ISLEADER, to_char(UPDATETIME, 'yyyy-mm-dd hh24:mi:ss') as UPDATETIME, UPDATEFLAG from post where UPDATETIME between to_date(?, 'yyyy-mm-dd hh24:mi:ss') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ");
	       	st.setString(1, startTime);
	       	st.setString(2, endTime);
	       	rs = st.executeQuery();
	       	while(rs.next()){
	       		//增加或更新或删除你
	       		jobDao.insertOrUpdateOrDeletePost(rs.getInt("POSTCODE"), rs.getString("POSTNAME"), rs.getString("ORGCODE"), 
	       				rs.getString("POSTTYPECODE"), rs.getString("POSTTYPENAME"), rs.getString("ISLEADER"), rs.getString("UPDATETIME"), rs.getString("UPDATEFLAG"));
	       	}
	   	} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally{
			//关闭连接
			if(oracleConnection != null){
				oracleConnection.close(con, st, rs);
			}
		}
	}
	
	/**
	 * 更新组织机构信息
	 * @throws Exception
	 */
	private void updateOrganization(String startTime, String endTime) throws Exception{
		OracleConnection oracleConnection = null;
	   	Connection con = null; 
	   	PreparedStatement st = null;
	   	ResultSet rs = null;
	   	try {
	   		//获取连接
	       	oracleConnection = new OracleConnection();
	       	con = oracleConnection.getConnection();
	        
	    	//获取岗位信息
	       	st = con.prepareStatement(" select ORGCODE, ORGNAME, PARENTCODE, INOROUT, JIBIE, to_char(UPDATETIME, 'yyyy-mm-dd hh24:mi:ss') as UPDATETIME, UPDATEFLAG from organization where UPDATETIME between to_date(?, 'yyyy-mm-dd hh24:mi:ss') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ");
	       	st.setString(1, startTime);
	       	st.setString(2, endTime);
	       	rs = st.executeQuery();
	       	while(rs.next()){
	       		//增加或更新或删除你
	       		jobDao.insertOrUpdateOrDeleteOrganization(rs.getString("ORGCODE"), rs.getString("ORGNAME"), rs.getString("PARENTCODE"), 
	       				rs.getString("INOROUT"), rs.getString("JIBIE"), rs.getString("UPDATETIME"), rs.getString("UPDATEFLAG"));
	       	}
	   	} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally{
			//关闭连接
			if(oracleConnection != null){
				oracleConnection.close(con, st, rs);
			}
		}
	}
	
	/**
	 * 更新人员排班信息
	 * @throws Exception
	 */
	private void updateBbdetailinfo(String startTime, String endTime) throws Exception{
		OracleConnection oracleConnection = null;
	   	Connection con = null; 
	   	PreparedStatement st = null;
	   	ResultSet rs = null;
	   	try {
	   		//获取连接
	       	oracleConnection = new OracleConnection();
	       	con = oracleConnection.getConnection();
	        
	    	//获取岗位信息
	       	st = con.prepareStatement(" select BBDETAILICODE, POLICENUM, POSTCODE, BCNAME, to_char(STARTTIME, 'yyyy-mm-dd hh24:mi:ss') as STARTTIME, to_char(ENDTIME, 'yyyy-mm-dd hh24:mi:ss') as ENDTIME, ISSI, GSSI, to_char(UPDATETIME, 'yyyy-mm-dd hh24:mi:ss') as UPDATETIME, UPDATEFLAG from bbdetailinfo_3d where UPDATETIME between to_date(?, 'yyyy-mm-dd hh24:mi:ss') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ");
	       	st.setString(1, startTime);
	       	st.setString(2, endTime);
	       	rs = st.executeQuery();
	       	while(rs.next()){
	       		//增加或更新或删除你
	       		jobDao.insertOrUpdateOrDeleteBbdetailinfo(rs.getInt("BBDETAILICODE"), rs.getString("POLICENUM"), rs.getInt("POSTCODE"), 
	       				rs.getString("BCNAME"), rs.getString("STARTTIME"), rs.getString("ENDTIME"), rs.getString("ISSI"), 
	       				rs.getString("GSSI"), rs.getString("UPDATETIME"), rs.getString("UPDATEFLAG"));
	       	}
	   	} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally{
			//关闭连接
			if(oracleConnection != null){
				oracleConnection.close(con, st, rs);
			}
		}
	}
	
	/**
	 * 更新人员信息
	 * @throws Exception
	 */
	private void updatePoliceinfo(String startTime, String endTime) throws Exception{
		OracleConnection oracleConnection = null;
	   	Connection con = null; 
	   	PreparedStatement st = null;
	   	ResultSet rs = null;
	   	try {
	   		//获取连接
	       	oracleConnection = new OracleConnection();
	       	con = oracleConnection.getConnection();
	        
	    	//获取岗位信息
	       	st = con.prepareStatement(" select POLICENUM, NAME, POLICETYPECODE, POLICETYPENAME, POLICESTATECODE, POLICESTATENAME, MOBILEPHONE, DUTY, ORGCODE, to_char(UPDATETIME, 'yyyy-mm-dd hh24:mi:ss') as UPDATETIME, UPDATEFLAG from policeinfo where UPDATETIME between to_date(?, 'yyyy-mm-dd hh24:mi:ss') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ");
	       	st.setString(1, startTime);
	       	st.setString(2, endTime);
	       	rs = st.executeQuery();
	       	while(rs.next()){
	       		//增加或更新或删除你
	       		jobDao.insertOrUpdateOrDeletePoliceinfo(rs.getString("POLICENUM"), rs.getString("NAME"), rs.getString("POLICETYPECODE"), 
	       				rs.getString("POLICETYPENAME"), rs.getString("POLICESTATECODE"), rs.getString("POLICESTATENAME"), 
	       				rs.getString("MOBILEPHONE"), rs.getString("DUTY"), rs.getString("ORGCODE"), rs.getString("UPDATETIME"), rs.getString("UPDATEFLAG"));
	       	}
	   	} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally{
			//关闭连接
			if(oracleConnection != null){
				oracleConnection.close(con, st, rs);
			}
		}
	}
	
	/**
	 * 删除两天前的人员排班信息
	 * @throws Exception
	 */
	private void deleteBbdetailinfo() throws Exception{
	   	try {
	   		jobDao.deleteBbdetailinfo();
	   	} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally{
			
		}
	}
	
	/**
	 * 根据部门ID获取部门名称
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	private String getOrganizationNameByOrgcode(String orgCode) throws Exception{
		OracleConnection oracleConnection = null;
	   	Connection con = null; 
	   	PreparedStatement st = null;
	   	ResultSet rs = null;
	   	String orgName = "";
	   	try {
	   		//获取连接
	       	oracleConnection = new OracleConnection();
	       	con = oracleConnection.getConnection();
	        
	    	//根据ID获取名称
	       	st = con.prepareStatement(" select ORGNAME from ORGANIZATION where ORGCODE=? ");
	       	st.setString(1, orgCode);
	       	rs = st.executeQuery();
	       	if(rs.next()){
	       		orgName = rs.getString(1);
	       	}
	   	} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally{
			//关闭连接
			if(oracleConnection != null){
				oracleConnection.close(con, st, rs);
			}
			
			return orgName;
		}
	}
}