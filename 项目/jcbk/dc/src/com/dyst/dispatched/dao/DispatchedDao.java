package com.dyst.dispatched.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.dispatched.entities.Dis110;
import com.dyst.dispatched.entities.DisApproveRecord;
import com.dyst.dispatched.entities.DisReceive;
import com.dyst.dispatched.entities.DisReport;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.entities.Lsh;
import com.dyst.dispatched.entities.Withdraw;
import com.dyst.earlyWarning.entities.EWarning;
import com.dyst.systemmanage.entities.User;

public interface DispatchedDao extends BaseDao{
	
	
	//根据ID删除
	public void delete(Serializable id);

	//根据ID查询
	public Dispatched findObjectById(Serializable id);
	
	//根据号牌号码查询
	public List<Dispatched> findDispatchedByHphm(String hphm, String hpzl, int bkid);
	
	//多条件语句查询
	public List<Object> findObjects(String hql, List<Object> parameters);
	
	//使用 QueryHelper 多条件查询
	public List<Dispatched> findObjects(QueryHelper queryHelper);
	
	//带分页的多条件查询
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize);

	public List<Map> findList(String hql, List<Object> parameters);
	//查审批记录
	public List<DisApproveRecord> findApproveRecord(Serializable id, String bzw);
	
	//根据布控ID查直接布控报备记录
	public List<DisReport> findDisReport(Serializable bkid);
	/**
	 * 根据ID查询报备
	 * @param id
	 * @return
	 */
	public DisReport findDisReportById(Serializable id);
	/**
	 * 根据ID查询布控签收
	 * @param id
	 * @return
	 */
	public DisReceive findDisReceive(Serializable id);
	
	//查询可布控的110数据
	public List<Dis110> findDis110(String loginName);
	
	/**
	 * 更改cad布控的110数据
	 * @return
	 */
	public void updateCadBk(String cadBkid);
	
	public PageResult getAllPageResult(String hql,List<Object> parameters, int pageNo,int pageSize);
	/**
	 * 获取用户
	 * @param hql
	 * @return
	 */
	public List<User> getLeader(String hql,List<Object> parameters);
	/**
	 * 查询布控信息
	 * @param hql
	 * @param parameters
	 * @return
	 */
	public List<Dispatched> findDispatched(String hql,List<Object> parameters);
	/**
	 * 根据布控ID查找未完成的报备
	 * @param bkid
	 * @return
	 */
	public DisReport findNoDealDisReport(Serializable bkid);
	
	/**
	 * 根据布控ID查签收情况
	 * @param bkid
	 * @return
	 */
	public List<DisReceive> findDisReceiveList(Serializable bkid, String bkckbz);
	
	/**
	 * 根据布控ID查撤控
	 * @param bkid
	 * @return
	 */
	public List<Withdraw> findWithdrawList(Serializable bkid);		
	
	/**
	 * 根据布控ID查预警
	 * @param bkid
	 * @return
	 */
	public List<EWarning> findEWaringList(Serializable bkid);
	
	/**
	 * 获得流水号
	 * @param date 当前日期
	 * @return
	 */
	public Lsh getLsh(String date);

	/**
	 * 根据查询条件查出dispatched 布控 列表
	 */
	public List<Dispatched> getListByQuery(String hql,
			Map<String, Object> params);

	/**
	 * 根据查询条件 查出  撤控  列表
	 */
	public List<Withdraw> findWithdrawList(String hql,
			Map<String, Object> params);
}

