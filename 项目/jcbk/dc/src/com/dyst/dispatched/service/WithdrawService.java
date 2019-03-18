package com.dyst.dispatched.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.transaction.annotation.Transactional;

import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.entities.Withdraw;
import com.dyst.systemmanage.entities.User;

public interface WithdrawService {
	//增加
	public ProcessInstance addWithdraw(Withdraw w,Dispatched d, Map<String, Object> variables);
	public void addZJWithdraw(Withdraw w, Map<String, Object> variables);
	//查询待办任务
	public PageResult findTodoTasks(User user, int pageNo, int pageSize);
	//查询待办公开任务
	public PageResult findTodoOpenTasks(User user, int pageNo, int pageSize);
	//查询待办任务数量
	public int findTodoTasksCount(User user);
	//查询待办公开任务数量
	public int findTodoOpenTasksCount(User user);
	//查询流程定义对象
    public ProcessDefinition getProcessDefinition(String processDefinitionId);
	//更新
	public void updateWithdraw(Withdraw d);
	//删除
	@Deprecated
	public void deleteWithdraw(Withdraw d);
	//根据ID逻辑删除
	public void deleteWithdraw(Serializable id);
	//根据ID查询
	public Withdraw findWithdrawById(Serializable id);
	//多条件语句查询
	public List<Object> findObjects(String hql, List<Object> parameters);
	//利用queryHelper，多条件语句查询
	public 	List<Withdraw> findObjects(QueryHelper queryHelper);
	//根据条件查询布控
	public 	PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize,Boolean publicBkxz);
	//多条件语句查询
	public List<Map> findList(String sql, List<Object> parameters);
	
	public Serializable save(Object entity);
	
	public void update(Object entity);
	/**
	 * @author liuqiang  查询出撤控列表，导出excel
	 */
	public List<Withdraw> findList(String hql,Map<String,Object>params);
	
	/**
     * 查询待办任务(包括秘密任务)
     *
     * @param userId 用户ID
     * @return
     */
    public List<Withdraw> findTodoTasksForOa(User user);
    
    /**
     * 查询待办公开任务
     *
     * @param userId 用户ID
     * @return
     */
    public List<Withdraw> findTodoOpenTasksForOa(User user);
}
