package com.dyst.quartz.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.quartz.dao.JobDao;

/**
 * 持久层
 * 
 * @author：陈高科
 * @date：2013-06-27
 * @version：v1
 * @doc：定时器持久层接口实现类，主要实现的接口方法有：
 *      1.检查治安卡点是否存在数据库
 *      2.更新或插入治安卡点
 *      3.更新岗位与巡逻区域关联信息
 *      4.更新岗位信息
 *      5.更新组织结构信息
 *      6.更新人员排班信息
 *      7.更新人员信息
 *      8.删除两天前的人员排班信息
 */
@Repository("jobDao")
@SuppressWarnings("rawtypes")
public class JobDaoImpl extends BaseDaoImpl implements JobDao{
	/**
	 * 检查治安卡点是否存在数据库
	 * @param ID 信息编号
	 * @param CODE 卡点编号
	 * @return
	 */
	public int isZakdExist(int ID, String CODE) {
		//查询
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" select count(*) from ZAKD where ID=:ID and CODE=:CODE ");
		query.setInteger("ID", ID);
		query.setString("CODE", CODE);
		List list = query.list();
		session.flush();
		
		//返回结果
		if(list != null && list.size() > 0){
			return Integer.parseInt(""+list.get(0));
		}
		return 0;
	}

	/**
	 * 更新或插入治安卡点
	 * @param ID
	 * @param CODE
	 * @param LEVELS
	 * @param POSTION
	 * @param X
	 * @param Y
	 * @param ORGCODE
	 * @param DUTYNUM
	 * @param DUTYNAME
	 * @param DUTYPHONE
	 * @param UPDATETIME
	 * @param CALLNUM
	 * @return
	 */
	public void insertOrUpdateZakd(int ID, String CODE, String LEVELS,
			String POSTION, double X, double Y, String ORGCODE, String ORGCODENAME, 
			String DUTYNUM,	String DUTYNAME, String DUTYPHONE, String UPDATETIME,
			String CALLNUM, String type) {
		Query query = null;
		StringBuilder sb = new StringBuilder();
		Session session = getSessionFactory().getCurrentSession();
		
		//新增
		if("1".equals(type)){
			sb.append(" insert into ZAKD(ID, CODE, LEVELS, POSTION, X, Y, ORGCODE, ORGCODENAME, DUTYNUM, DUTYNAME, DUTYPHONE, UPDATETIME, CALLNUM) ");
			sb.append(" values(:ID, :CODE, :LEVELS, :POSTION, :X, :Y, :ORGCODE, :ORGCODENAME, :DUTYNUM, :DUTYNAME, :DUTYPHONE, :UPDATETIME, :CALLNUM) ");
			
			//执行
			query = session.createSQLQuery(sb.toString());
			query.setInteger("ID", ID);
			query.setString("CODE", CODE);
			query.setString("LEVELS", LEVELS);
			query.setString("POSTION", POSTION);
			query.setDouble("X", X);
			query.setDouble("Y", Y);
			query.setString("ORGCODE", ORGCODE);
			query.setString("ORGCODENAME", ORGCODENAME);
			query.setString("DUTYNUM", DUTYNUM);
			query.setString("DUTYNAME", DUTYNAME);
			query.setString("DUTYPHONE", DUTYPHONE);
			query.setString("UPDATETIME", UPDATETIME);
			query.setString("CALLNUM", CALLNUM);
			query.executeUpdate();
			session.flush();
		} else if("2".equals(type)){
			sb.append(" update ZAKD set LEVELS=:LEVELS, POSTION=:POSTION, X=:X, Y=:Y, ORGCODE=:ORGCODE, ");
			sb.append(" ORGCODENAME=:ORGCODENAME, DUTYNUM=:DUTYNUM, DUTYNAME=:DUTYNAME, DUTYPHONE=:DUTYPHONE, ");
			sb.append(" UPDATETIME=:UPDATETIME, CALLNUM=:CALLNUM where ID=:ID and CODE=:CODE ");
			
			//执行
			query = session.createSQLQuery(sb.toString());
			query.setString("LEVELS", LEVELS);
			query.setString("POSTION", POSTION);
			query.setDouble("X", X);
			query.setDouble("Y", Y);
			query.setString("ORGCODE", ORGCODE);
			query.setString("ORGCODENAME", ORGCODENAME);
			query.setString("DUTYNUM", DUTYNUM);
			query.setString("DUTYNAME", DUTYNAME);
			query.setString("DUTYPHONE", DUTYPHONE);
			query.setString("UPDATETIME", UPDATETIME);
			query.setString("CALLNUM", CALLNUM);
			query.setInteger("ID", ID);
			query.setString("CODE", CODE);
			query.executeUpdate();
			session.flush();
		}
	}
	
	/**
	 * 检查岗位与巡逻区域关联信息是否存在数据库
	 * @param ID 信息编号
	 * @return
	 */
	public int isPostAreaExist(int ID) {
		//查询
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" select count(*) from POSTAREAR where ID=:ID ");
		query.setInteger("ID", ID);
		List list = query.list();
		session.flush();
		
		//返回结果
		if(list != null && list.size() > 0){
			return Integer.parseInt(""+list.get(0));
		}
		return 0;
	}
	
	/**
	 * 更新岗位与巡逻区域关联信息
	 * @param ID
	 * @param POSTCODE
	 * @param AREACODE
	 * @param UPDATETIME
	 * @param UPDATEFLAG
	 */
	public void insertOrUpdateOrDeletePostArea(int ID, String POSTCODE, String AREACODE, String UPDATETIME, String UPDATEFLAG){
		Query query = null;
		StringBuilder sb = new StringBuilder();
		Session session = getSessionFactory().getCurrentSession();
		
		if("-1".equals(UPDATEFLAG)){//删除
			sb.append(" delete from POSTAREAR where ID=:ID ");
			query = session.createSQLQuery(sb.toString());
			query.setInteger("ID", ID);
			query.executeUpdate();
			session.flush();
		} else if("0".equals(UPDATEFLAG)){//更新
			sb.append(" update POSTAREAR set POSTCODE=:POSTCODE, AREACODE=:AREACODE, UPDATETIME=:UPDATETIME where ID=:ID ");
			query = session.createSQLQuery(sb.toString());
			query.setString("POSTCODE", POSTCODE);
			query.setString("AREACODE", AREACODE);
			query.setString("UPDATETIME", UPDATETIME);
			query.setInteger("ID", ID);
			query.executeUpdate();
			session.flush();
		} else if("1".equals(UPDATEFLAG)){//新增
			if(isPostAreaExist(ID) > 0){//更新
				sb.append(" update POSTAREAR set POSTCODE=:POSTCODE, AREACODE=:AREACODE, UPDATETIME=:UPDATETIME where ID=:ID ");
				query = session.createSQLQuery(sb.toString());
				query.setString("POSTCODE", POSTCODE);
				query.setString("AREACODE", AREACODE);
				query.setString("UPDATETIME", UPDATETIME);
				query.setInteger("ID", ID);
				query.executeUpdate();
				session.flush();
			} else {
				sb.append(" insert into POSTAREAR(ID, POSTCODE, AREACODE, UPDATETIME) values(:ID, :POSTCODE, :AREACODE, :UPDATETIME) ");
				query = session.createSQLQuery(sb.toString());
				query.setInteger("ID", ID);
				query.setString("POSTCODE", POSTCODE);
				query.setString("AREACODE", AREACODE);
				query.setString("UPDATETIME", UPDATETIME);
				query.executeUpdate();
				session.flush();
			}
		} 
	}
	
	/**
	 * 检查岗位信息是否存在数据库
	 * @param POSTCODE 信息编号
	 * @return
	 */
	public int isPostExist(int POSTCODE) {
		//查询
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" select count(*) from POST where POSTCODE=:POSTCODE ");
		query.setInteger("POSTCODE", POSTCODE);
		List list = query.list();
		session.flush();
		
		//返回结果
		if(list != null && list.size() > 0){
			return Integer.parseInt(""+list.get(0));
		}
		return 0;
	}
	
	/**
	 * 更新岗位信息
	 * @param POSTCODE
	 * @param POSTNAME
	 * @param ORGCODE
	 * @param POSTTYPECODE
	 * @param POSTTYPENAME
	 * @param ISLEADER
	 * @param UPDATETIME
	 * @param UPDATEFLAG
	 */
	public void insertOrUpdateOrDeletePost(int POSTCODE, String POSTNAME, String ORGCODE, String POSTTYPECODE, 
			String POSTTYPENAME, String ISLEADER, String UPDATETIME, String UPDATEFLAG){
		Query query = null;
		StringBuilder sb = new StringBuilder();
		Session session = getSessionFactory().getCurrentSession();
		
		if("-1".equals(UPDATEFLAG)){//删除
			sb.append(" delete from POST where POSTCODE=:POSTCODE ");
			query = session.createSQLQuery(sb.toString());
			query.setInteger("POSTCODE", POSTCODE);
			query.executeUpdate();
			session.flush();
		} else if("0".equals(UPDATEFLAG)){//更新
			sb.append(" update POST set POSTNAME=:POSTNAME, ORGCODE=:ORGCODE, POSTTYPECODE=:POSTTYPECODE, ");
			sb.append(" POSTTYPENAME=:POSTTYPENAME, ISLEADER=:ISLEADER, UPDATETIME=:UPDATETIME where POSTCODE=:POSTCODE ");
			query = session.createSQLQuery(sb.toString());
			query.setString("POSTNAME", POSTNAME);
			query.setString("ORGCODE", ORGCODE);
			query.setString("POSTTYPECODE", POSTTYPECODE);
			query.setString("POSTTYPENAME", POSTTYPENAME);
			query.setString("ISLEADER", ISLEADER);
			query.setString("UPDATETIME", UPDATETIME);
			query.setInteger("POSTCODE", POSTCODE);
			query.executeUpdate();
			session.flush();
		} else if("1".equals(UPDATEFLAG)){//新增
			if(isPostExist(POSTCODE) > 0){//更新
				sb.append(" update POST set POSTNAME=:POSTNAME, ORGCODE=:ORGCODE, POSTTYPECODE=:POSTTYPECODE, ");
				sb.append(" POSTTYPENAME=:POSTTYPENAME, ISLEADER=:ISLEADER, UPDATETIME=:UPDATETIME where POSTCODE=:POSTCODE ");
				query = session.createSQLQuery(sb.toString());
				query.setString("POSTNAME", POSTNAME);
				query.setString("ORGCODE", ORGCODE);
				query.setString("POSTTYPECODE", POSTTYPECODE);
				query.setString("POSTTYPENAME", POSTTYPENAME);
				query.setString("ISLEADER", ISLEADER);
				query.setString("UPDATETIME", UPDATETIME);
				query.setInteger("POSTCODE", POSTCODE);
				query.executeUpdate();
				session.flush();
			} else {
				sb.append(" insert into POST(POSTCODE, POSTNAME, ORGCODE, POSTTYPECODE, POSTTYPENAME, ISLEADER, UPDATETIME) ");
				sb.append(" values(:POSTCODE, :POSTNAME, :ORGCODE, :POSTTYPECODE, :POSTTYPENAME, :ISLEADER, :UPDATETIME) ");
				
				query = session.createSQLQuery(sb.toString());
				query.setInteger("POSTCODE", POSTCODE);
				query.setString("POSTNAME", POSTNAME);
				query.setString("ORGCODE", ORGCODE);
				query.setString("POSTTYPECODE", POSTTYPECODE);
				query.setString("POSTTYPENAME", POSTTYPENAME);
				query.setString("ISLEADER", ISLEADER);
				query.setString("UPDATETIME", UPDATETIME);
				query.executeUpdate();
				session.flush();
			}
		} 
	}
	
	/**
	 * 检查组织结构是否存在数据库
	 * @param POSTCODE 信息编号
	 * @return
	 */
	public int isOrganizationExist(String ORGCODE) {
		//查询
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" select count(*) from ORGANIZATION where ORGCODE=:ORGCODE ");
		query.setString("ORGCODE", ORGCODE);
		List list = query.list();
		session.flush();
		
		//返回结果
		if(list != null && list.size() > 0){
			return Integer.parseInt(""+list.get(0));
		}
		return 0;
	}
	
	/**
	 * 更新组织结构信息
	 * @param ORGCODE
	 * @param ORGNAME
	 * @param PARENTCODE
	 * @param INOROUT
	 * @param JIBIE
	 * @param UPDATETIME
	 * @param UPDATEFLAG
	 */
	public void insertOrUpdateOrDeleteOrganization(String ORGCODE, String ORGNAME, String PARENTCODE, 
				String INOROUT, String JIBIE, String UPDATETIME, String UPDATEFLAG){
		Query query = null;
		StringBuilder sb = new StringBuilder();
		Session session = getSessionFactory().getCurrentSession();
		
		if("-1".equals(UPDATEFLAG)){//删除
			sb.append(" delete from ORGANIZATION where ORGCODE=:ORGCODE ");
			query = session.createSQLQuery(sb.toString());
			query.setString("ORGCODE", ORGCODE);
			query.executeUpdate();
			session.flush();
		} else if("0".equals(UPDATEFLAG)){//更新
			sb.append(" update ORGANIZATION set ORGNAME=:ORGNAME, PARENTCODE=:PARENTCODE, INOROUT=:INOROUT, ");
			sb.append(" JIBIE=:JIBIE, UPDATETIME=:UPDATETIME where ORGCODE=:ORGCODE ");
			query = session.createSQLQuery(sb.toString());
			query.setString("ORGNAME", ORGNAME);
			query.setString("PARENTCODE", PARENTCODE);
			query.setString("INOROUT", INOROUT);
			query.setString("JIBIE", JIBIE);
			query.setString("UPDATETIME", UPDATETIME);
			query.setString("ORGCODE", ORGCODE);
			query.executeUpdate();
			session.flush();
		} else if("1".equals(UPDATEFLAG)){//新增
			if(isOrganizationExist(ORGCODE) > 0){//更新
				sb.append(" update ORGANIZATION set ORGNAME=:ORGNAME, PARENTCODE=:PARENTCODE, INOROUT=:INOROUT, ");
				sb.append(" JIBIE=:JIBIE, UPDATETIME=:UPDATETIME where ORGCODE=:ORGCODE ");
				query = session.createSQLQuery(sb.toString());
				query.setString("ORGNAME", ORGNAME);
				query.setString("PARENTCODE", PARENTCODE);
				query.setString("INOROUT", INOROUT);
				query.setString("JIBIE", JIBIE);
				query.setString("UPDATETIME", UPDATETIME);
				query.setString("ORGCODE", ORGCODE);
				query.executeUpdate();
				session.flush();
			} else {
				sb.append(" insert into ORGANIZATION(ORGCODE, ORGNAME, PARENTCODE, INOROUT, JIBIE, UPDATETIME) ");
				sb.append(" values(:ORGCODE, :ORGNAME, :PARENTCODE, :INOROUT, :JIBIE, :UPDATETIME) ");
				query = session.createSQLQuery(sb.toString());
				query.setString("ORGCODE", ORGCODE);
				query.setString("ORGNAME", ORGNAME);
				query.setString("PARENTCODE", PARENTCODE);
				query.setString("INOROUT", INOROUT);
				query.setString("JIBIE", JIBIE);
				query.setString("UPDATETIME", UPDATETIME);
				query.executeUpdate();
				session.flush();
			}
		} 
	}
	
	/**
	 * 检查人员排班信息是否存在数据库
	 * @param BBDETAILICODE 信息编号
	 * @return
	 */
	public int isBbdetailinfoExist(int BBDETAILICODE) {
		//查询
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" select count(*) from BBDETAILINFO_3D where BBDETAILICODE=:BBDETAILICODE ");
		query.setInteger("BBDETAILICODE", BBDETAILICODE);
		List list = query.list();
		session.flush();
		
		//返回结果
		if(list != null && list.size() > 0){
			return Integer.parseInt(""+list.get(0));
		}
		return 0;
	}
	
	/**
	 * 更新人员排班信息
	 * @param BBDETAILICODE
	 * @param POLICENUM
	 * @param POSTCODE
	 * @param BCNAME
	 * @param STARTTIME
	 * @param ENDTIME
	 * @param ISSI
	 * @param GSSI
	 * @param UPDATETIME
	 * @param UPDATEFLAG
	 */
	public void insertOrUpdateOrDeleteBbdetailinfo(int BBDETAILICODE, String POLICENUM, int POSTCODE, 
				String BCNAME, String STARTTIME, String ENDTIME, String ISSI, String GSSI, String UPDATETIME, String UPDATEFLAG){
		Query query = null;
		StringBuilder sb = new StringBuilder();
		Session session = getSessionFactory().getCurrentSession();
		
		if("-1".equals(UPDATEFLAG)){//删除
			sb.append(" delete from BBDETAILINFO_3D where BBDETAILICODE=:BBDETAILICODE ");
			query = session.createSQLQuery(sb.toString());
			query.setInteger("BBDETAILICODE", BBDETAILICODE);
			query.executeUpdate();
			session.flush();
		} else if("0".equals(UPDATEFLAG)){//更新
			sb.append(" update BBDETAILINFO_3D set POLICENUM=:POLICENUM, POSTCODE=:POSTCODE, BCNAME=:BCNAME, ");
			sb.append(" STARTTIME=:STARTTIME, ENDTIME=:ENDTIME, ISSI=:ISSI, GSSI=:GSSI, UPDATETIME=:UPDATETIME where BBDETAILICODE=:BBDETAILICODE ");
			query = session.createSQLQuery(sb.toString());
			query.setString("POLICENUM", POLICENUM);
			query.setInteger("POSTCODE", POSTCODE);
			query.setString("BCNAME", BCNAME);
			query.setString("STARTTIME", STARTTIME);
			query.setString("ENDTIME", ENDTIME);
			query.setString("ISSI", ISSI);
			query.setString("GSSI", GSSI);
			query.setString("UPDATETIME", UPDATETIME);
			query.setInteger("BBDETAILICODE", BBDETAILICODE);
			query.executeUpdate();
			session.flush();
		} else if("1".equals(UPDATEFLAG)){//新增
			if(isBbdetailinfoExist(BBDETAILICODE) > 0){//更新
				sb.append(" update BBDETAILINFO_3D set POLICENUM=:POLICENUM, POSTCODE=:POSTCODE, BCNAME=:BCNAME, ");
				sb.append(" STARTTIME=:STARTTIME, ENDTIME=:ENDTIME, ISSI=:ISSI, GSSI=:GSSI, UPDATETIME=:UPDATETIME where BBDETAILICODE=:BBDETAILICODE ");
				query = session.createSQLQuery(sb.toString());
				query.setString("POLICENUM", POLICENUM);
				query.setInteger("POSTCODE", POSTCODE);
				query.setString("BCNAME", BCNAME);
				query.setString("STARTTIME", STARTTIME);
				query.setString("ENDTIME", ENDTIME);
				query.setString("ISSI", ISSI);
				query.setString("GSSI", GSSI);
				query.setString("UPDATETIME", UPDATETIME);
				query.setInteger("BBDETAILICODE", BBDETAILICODE);
				query.executeUpdate();
				session.flush();
			} else {
				sb.append(" insert into BBDETAILINFO_3D(BBDETAILICODE, POLICENUM, POSTCODE, BCNAME, STARTTIME, ENDTIME, ISSI, GSSI, UPDATETIME) ");
				sb.append(" values(:BBDETAILICODE, :POLICENUM, :POSTCODE, :BCNAME, :STARTTIME, :ENDTIME, :ISSI, :GSSI, :UPDATETIME) ");
				query = session.createSQLQuery(sb.toString());
				query.setInteger("BBDETAILICODE", BBDETAILICODE);
				query.setString("POLICENUM", POLICENUM);
				query.setInteger("POSTCODE", POSTCODE);
				query.setString("BCNAME", BCNAME);
				query.setString("STARTTIME", STARTTIME);
				query.setString("ENDTIME", ENDTIME);
				query.setString("ISSI", ISSI);
				query.setString("GSSI", GSSI);
				query.setString("UPDATETIME", UPDATETIME);
				query.executeUpdate();
				session.flush();
			}
		} 
	}
	
	/**
	 * 检查人员信息是否存在数据库
	 * @param BBDETAILICODE 信息编号
	 * @return
	 */
	public int isPoliceinfoExist(String POLICENUM) {
		//查询
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" select count(*) from POLICEINFO where POLICENUM=:POLICENUM ");
		query.setString("POLICENUM", POLICENUM);
		List list = query.list();
		session.flush();
		
		//返回结果
		if(list != null && list.size() > 0){
			return Integer.parseInt(""+list.get(0));
		}
		return 0;
	}
	
	/**
	 * 更新人员信息
	 * @param POLICENUM
	 * @param NAME
	 * @param POLICETYPECODE
	 * @param POLICETYPENAME
	 * @param POLICESTATECODE
	 * @param POLICESTATENAME
	 * @param MOBILEPHONE
	 * @param DUTY
	 * @param ORGCODE
	 * @param UPDATETIME
	 * @param UPDATEFLAG
	 */
	public void insertOrUpdateOrDeletePoliceinfo(String POLICENUM, String NAME, String POLICETYPECODE, 
				String POLICETYPENAME, String POLICESTATECODE, String POLICESTATENAME, String MOBILEPHONE, 
				String DUTY, String ORGCODE, String UPDATETIME, String UPDATEFLAG){
		Query query = null;
		StringBuilder sb = new StringBuilder();
		Session session = getSessionFactory().getCurrentSession();
		
		if("-1".equals(UPDATEFLAG)){//删除
			sb.append(" delete from POLICEINFO where POLICENUM=:POLICENUM ");
			query = session.createSQLQuery(sb.toString());
			query.setString("POLICENUM", POLICENUM);
			query.executeUpdate();
			session.flush();
		} else if("0".equals(UPDATEFLAG)){//更新
			sb.append(" update POLICEINFO set NAME=:NAME, POLICETYPECODE=:POLICETYPECODE, POLICETYPENAME=:POLICETYPENAME, ");
			sb.append(" POLICESTATECODE=:POLICESTATECODE, POLICESTATENAME=:POLICESTATENAME, MOBILEPHONE=:MOBILEPHONE, ");
			sb.append(" DUTY=:DUTY, ORGCODE=:ORGCODE, UPDATETIME=:UPDATETIME where POLICENUM=:POLICENUM ");
			query = session.createSQLQuery(sb.toString());
			query.setString("NAME", NAME);
			query.setString("POLICETYPECODE", POLICETYPECODE);
			query.setString("POLICETYPENAME", POLICETYPENAME);
			query.setString("POLICESTATECODE", POLICESTATECODE);
			query.setString("POLICESTATENAME", POLICESTATENAME);
			query.setString("MOBILEPHONE", MOBILEPHONE);
			query.setString("DUTY", DUTY);
			query.setString("ORGCODE", ORGCODE);
			query.setString("UPDATETIME", UPDATETIME);
			query.setString("POLICENUM", POLICENUM);
			query.executeUpdate();
			session.flush();
		} else if("1".equals(UPDATEFLAG)){//新增
			if(isPoliceinfoExist(POLICENUM) > 0){//更新
				sb.append(" update POLICEINFO set NAME=:NAME, POLICETYPECODE=:POLICETYPECODE, POLICETYPENAME=:POLICETYPENAME, ");
				sb.append(" POLICESTATECODE=:POLICESTATECODE, POLICESTATENAME=:POLICESTATENAME, MOBILEPHONE=:MOBILEPHONE, ");
				sb.append(" DUTY=:DUTY, ORGCODE=:ORGCODE, UPDATETIME=:UPDATETIME where POLICENUM=:POLICENUM ");
				query = session.createSQLQuery(sb.toString());
				query.setString("NAME", NAME);
				query.setString("POLICETYPECODE", POLICETYPECODE);
				query.setString("POLICETYPENAME", POLICETYPENAME);
				query.setString("POLICESTATECODE", POLICESTATECODE);
				query.setString("POLICESTATENAME", POLICESTATENAME);
				query.setString("MOBILEPHONE", MOBILEPHONE);
				query.setString("DUTY", DUTY);
				query.setString("ORGCODE", ORGCODE);
				query.setString("UPDATETIME", UPDATETIME);
				query.setString("POLICENUM", POLICENUM);
				query.executeUpdate();
				session.flush();
			} else {
				sb.append(" insert into POLICEINFO(POLICENUM, NAME, POLICETYPECODE, POLICETYPENAME, POLICESTATECODE, POLICESTATENAME, MOBILEPHONE, DUTY, ORGCODE, UPDATETIME) ");
				sb.append(" values(:POLICENUM, :NAME, :POLICETYPECODE, :POLICETYPENAME, :POLICESTATECODE, :POLICESTATENAME, :MOBILEPHONE, :DUTY, :ORGCODE, :UPDATETIME) ");
				query = session.createSQLQuery(sb.toString());
				query.setString("POLICENUM", POLICENUM);
				query.setString("NAME", NAME);
				query.setString("POLICETYPECODE", POLICETYPECODE);
				query.setString("POLICETYPENAME", POLICETYPENAME);
				query.setString("POLICESTATECODE", POLICESTATECODE);
				query.setString("POLICESTATENAME", POLICESTATENAME);
				query.setString("MOBILEPHONE", MOBILEPHONE);
				query.setString("DUTY", DUTY);
				query.setString("ORGCODE", ORGCODE);
				query.setString("UPDATETIME", UPDATETIME);
				query.executeUpdate();
				session.flush();
			}
		} 
	}
	
	/**
	 * 删除两天前的人员排班信息
	 * @param startTime
	 * @param endTime
	 */
	public void deleteBbdetailinfo(){
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" delete from BBDETAILINFO_3D where ENDTIME < (select date_sub(now(), interval 1 day) from dual) ");
		query.executeUpdate();
		session.flush();
	}
	
	/**
	 * 删除一天前的状态
	 */
	public void deleteJcdStatus(){
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(" delete from JCDSTATUS where time < (select date_sub(now(), interval 1 day) from dual) ");
		query.executeUpdate();
		session.flush();
	}
}