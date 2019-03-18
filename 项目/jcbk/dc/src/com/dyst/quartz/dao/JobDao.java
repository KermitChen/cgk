package com.dyst.quartz.dao;

/**
 * 持久层
 * 
 * @author：陈高科
 * @date：2013-06-27
 * @version：v1
 * @doc：定时器持久层接口，主要的接口方法有：
 *      1.检查治安卡点是否存在数据库
 *      2.更新或插入治安卡点
 *      3.更新岗位与巡逻区域关联信息
 *      4.更新岗位信息
 *      5.更新组织结构信息
 *      6.更新人员排班信息
 *      7.更新人员信息
 *      8.删除两天前的人员排班信息
 */
public interface JobDao {
	/**
	 * 检查治安卡点是否存在数据库
	 * @param ID 信息编号
	 * @param CODE 卡点编号
	 * @return
	 */
	public int isZakdExist(int ID, String CODE);
	
	/**
	 * 更新或插入治安卡点
	 * @param ID
	 * @param CODE
	 * @param LEVELS
	 * @param POSTION
	 * @param ORGCODE
	 * @param X
	 * @param Y
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
			String CALLNUM, String type);
	
	/**
	 * 更新岗位与巡逻区域关联信息
	 * @param ID
	 * @param POSTCODE
	 * @param AREACODE
	 * @param UPDATETIME
	 * @param UPDATEFLAG
	 */
	public void insertOrUpdateOrDeletePostArea(int ID, String POSTCODE, String AREACODE, String UPDATETIME, String UPDATEFLAG);
	
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
			String POSTTYPENAME, String ISLEADER, String UPDATETIME, String UPDATEFLAG);
	
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
				String INOROUT, String JIBIE, String UPDATETIME, String UPDATEFLAG);
	
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
				String BCNAME, String STARTTIME, String ENDTIME, String ISSI, String GSSI, String UPDATETIME, String UPDATEFLAG);
	
	
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
				String DUTY, String ORGCODE, String UPDATETIME, String UPDATEFLAG);
	
	/**
	 * 删除两天前的人员排班信息
	 */
	public void deleteBbdetailinfo();
	
	/**
	 * 删除一天前的状态
	 */
	public void deleteJcdStatus();
}