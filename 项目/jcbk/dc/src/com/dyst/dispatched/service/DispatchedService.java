package com.dyst.dispatched.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.dispatched.entities.Dis110;
import com.dyst.dispatched.entities.DisApproveRecord;
import com.dyst.dispatched.entities.DisReceive;
import com.dyst.dispatched.entities.DisReport;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.entities.Withdraw;
import com.dyst.earlyWarning.entities.EWarning;
import com.dyst.systemmanage.entities.User;
import com.dyst.utils.excel.bean.bukong.BKQueryExcelBean;
import com.dyst.utils.excel.bean.bukong.CKExcelBean;

public interface DispatchedService {
	//增加
	public ProcessInstance addDispatched(Dispatched d, Map<String, Object> variables);
	public void addDis(Dispatched d,User user,User leader, Map<String, Object> variables);
	/**
	 * 查询待办任务
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult findTodoTasks(User user, int pageNo, int pageSize);
	/**
	 * 查询待办公开任务
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult findTodoOpenTasks(User user, int pageNo, int pageSize);
	
	/**
	 * 查询待办任务数量
	 * @param user
	 * @return
	 */
	public int findTodoTasksCount(User user);
	/**
	 * 查询待办公开任务
	 * @param user
	 * @return
	 */
	public int findTodoOpenTasksCount(User user);
	/**
	 * 查询流程定义对象
	 * @param processDefinitionId
	 * @return
	 */
    public ProcessDefinition getProcessDefinition(String processDefinitionId);
	//更新
	public void updateDispatched(Dispatched d);
	//删除
	@Deprecated
	public void deleteDispatched(Dispatched d);
	//根据ID逻辑删除
	public void deleteDispatched(Serializable id);
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public Dispatched findDispatchedById(Serializable id);
	/**
	 * 根据车牌查询
	 * @param hphm
	 * @param hpzl
	 * @return
	 */
	public Dispatched findDispatchedByHphmBest(String hphm,String hpzl);
	/**
	 * 根据车牌查询(状态为1和7的)
	 * @param hphm
	 * @param hpzl
	 * @return
	 */
	public List<Dispatched> findDispatchedByHphm(String hphm, String hpzl, int bkid);
	/**
	 * 多条件语句查询
	 * @param hql
	 * @param parameters
	 * @return
	 */
	public List<Object> findObjects(String hql, List<Object> parameters);
	/**
	 * 利用queryHelper，多条件语句查询
	 * @param queryHelper
	 * @return
	 */
	public 	List<Dispatched> findObjects(QueryHelper queryHelper);
	/**
	 * 根据条件查询布控
	 */
	public 	PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize);
	/**
	 * 多条件语句查询
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public List<Map> findList(String sql, List<Object> parameters);
	
	public Serializable save(Object entity);
	
	public void update(Object entity);
	/**
	 * 查审批记录
	 * @param id
	 * @param bzw
	 * @return
	 */
	public List<DisApproveRecord> findApproveRecord(Serializable id, String bzw);
	
	/**
	 * 根据布控ID查直接布控报备记录
	 * @param bkid
	 * @return
	 */
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
	
	/**
	 * 查询可布控的110数据
	 * @return
	 */
	public List<Dis110> findDis110(String loginName);
	
	/**
	 * 更改cad布控的110数据
	 * @return
	 */
	public void updateCadBk(String cadBkid);
	
	/**
	 * hql语句分页查询
	 * @param hql
	 * @param parameters
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getAllPageResult(String hql,List<Object> parameters, int pageNo,int pageSize);
	/**
	 * 查询布控信息
	 * @param hql
	 * @param parameters
	 * @return
	 */
	public List<Dispatched> findDispatched(String hql,List<Object> parameters);
	
	/**
	 * 获取高一级的领导
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public List<User> getLeader(User user) throws Exception;
	
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
	 * @param sLshTemp (fjlsh,sjlsh,jclsh)
	 * @return
	 */
	public int getLsh(String sLshTemp,String date);
	
	/**
	 * 查询出excel导出列表
	 */
	public List<Dispatched> getListByQuery(String hql,Map<String,Object>params);
	/**
	 * 布控导出excel
	 */
	public void excelExportForDispatched(BKQueryExcelBean bean,
			ServletOutputStream outputStream);
	/**
	 * 撤控导出excel
	 */
	public void excelExportForCK(CKExcelBean bean,
			ServletOutputStream outputStream);
	
	/**
     * 查询待办任务(包括秘密任务)，oa审批
     *
     * @param userId 用户ID
     * @return
     */
    public List<Dispatched> findTodoTasksForOa(User user);
    
    /**
     * 查询待办公开任务，oa审批
     *
     * @param userId 用户ID
     * @return
     */
    public List<Dispatched> findTodoOpenTasksForOa(User user);
}
