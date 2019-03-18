package com.dyst.dispatched.serviceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.dispatched.dao.DispatchedDao;
import com.dyst.dispatched.dao.WithdrawDao;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.entities.Withdraw;
import com.dyst.dispatched.service.WithdrawService;
import com.dyst.systemmanage.entities.User;
import com.dyst.workflow.service.WorkflowTraceService;

@Service("withdrawService")
public class WithdrawServiceImpl implements WithdrawService {

	// 注入持久层dao
	@Autowired
	private WithdrawDao withdrawDao;
	@Autowired
	private DispatchedDao dispatchedDao;
	@Autowired
	private IdentityService identityService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected WorkflowTraceService traceService;
	@Autowired
	protected ProcessEngineFactoryBean processEngine;
    @Autowired
    protected ProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    protected HistoryService historyService;
    
	//保存撤控信息并启动工作流
	public ProcessInstance addWithdraw(Withdraw w,Dispatched d, Map<String, Object> variables) {
		withdrawDao.save(w);
        String businessKey = w.getCkid().toString();

        ProcessInstance processInstance = null;
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(w.getCxsqrjh());
            if(d.getBkdl().equals("1")){//涉案类
            	processInstance = runtimeService.startProcessInstanceByKey("withdraw_crime", businessKey, variables);
            } else if(d.getBkdl().equals("3")){//管控类
            	processInstance = runtimeService.startProcessInstanceByKey("withdraw_control", businessKey, variables);
            } else{//交通违法类
            	processInstance = runtimeService.startProcessInstanceByKey("withdraw_traffic", businessKey, variables);
            }
        } catch (Exception e) {
			e.printStackTrace();
		}
        return processInstance;
	}
	//直接撤控
	public void addZJWithdraw(Withdraw w, Map<String, Object> variables) {
		try {
			withdrawDao.save(w);
			String businessKey = w.getCkid().toString();

//		    ProcessInstance processInstance = null;
		    identityService.setAuthenticatedUserId(w.getCxsqrjh());
			runtimeService.startProcessInstanceByKey("withdraw_zjbk", businessKey, variables);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
     * 查询待办公开任务
     *
     * @param userId 用户ID
     * @return
     */
    @Transactional(readOnly = true)
    public PageResult findTodoOpenTasks(User user, int pageNo, int pageSize) {
        List<Withdraw> results = new ArrayList<Withdraw>();
        List<Withdraw> lastResults = new ArrayList<Withdraw>();//分页后的结果
        if(pageNo < 1) pageNo = 1;
        
        List<String> disList = new ArrayList<String>();
        disList.add("withdraw_crime");
        disList.add("withdraw_control");
        disList.add("withdraw_traffic");
        disList.add("withdraw_zjbk");
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKeyIn(disList).taskCandidateOrAssigned(user.getLoginName());
        List<Task> tasks = taskQuery.list();
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            if (processInstance == null || processInstance.getBusinessKey() == null) {
                continue;
            }
	        String businessKey = processInstance.getBusinessKey();
	        Withdraw withdraw = withdrawDao.findObjectById(new Long(businessKey));
	        if(withdraw != null){
	        	Dispatched dispatched = dispatchedDao.findObjectById(withdraw.getBkid());
		        if(dispatched != null && user != null && user.getPosition() != null && user.getLskhbm() != null && dispatched.getLskhbm() != null && dispatched.getBkxz() != null && user.getSystemNo() != null){
		        	String currentUserPosition = user.getPosition().trim();
		        	String currentUserLskhbm = user.getLskhbm().trim();
		        	String currentUserSystemNo = user.getSystemNo().trim();
		        	String bkqsLskhbm = dispatched.getLskhbm().trim();
		        	String bkxz = dispatched.getBkxz().trim();
		        	if("1".equals(bkxz)){
		        		if(currentUserPosition.substring(0, 2).compareTo("91") >= 0){//市局不区分部门
		        			withdraw.setDispatched(dispatched);
				        	withdraw.setTask(task);
				        	withdraw.setProcessInstance(processInstance);
				        	withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					        results.add(withdraw);
			        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("81") >= 0 && (currentUserLskhbm.equals(bkqsLskhbm) || (dispatched.getFjsn() != null && dispatched.getFjsn().trim().equals(currentUserSystemNo.substring(0, 6)))))
		        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("81") >= 0)){//分局
			        		withdraw.setDispatched(dispatched);
							withdraw.setTask(task);
							withdraw.setProcessInstance(processInstance);
							withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
							results.add(withdraw);
			        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("71") >= 0 && currentUserLskhbm.equals(bkqsLskhbm))
		        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("71") >= 0)){//分局以下
			        		withdraw.setDispatched(dispatched);
				        	withdraw.setTask(task);
				        	withdraw.setProcessInstance(processInstance);
				        	withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					        results.add(withdraw);
			        	}
		        	}
		        }
	        }
        }
        int endIndex = (pageNo-1)*pageSize+pageSize > results.size() ? results.size() : (pageNo-1)*pageSize+pageSize;
        lastResults = results.subList((pageNo-1)*pageSize, endIndex);
        return new PageResult(results.size(), pageNo, pageSize, lastResults);
    }
    /**
     * 查询待办任务(包括秘密任务)
     *
     * @param userId 用户ID
     * @return
     */
    @Transactional(readOnly = true)
    public PageResult findTodoTasks(User user, int pageNo, int pageSize) {
        List<Withdraw> results = new ArrayList<Withdraw>();
        List<Withdraw> lastResults = new ArrayList<Withdraw>();//分页后的结果
        if(pageNo < 1) pageNo = 1;
        List<String> disList = new ArrayList<String>();
        disList.add("withdraw_crime");
        disList.add("withdraw_control");
        disList.add("withdraw_traffic");
        disList.add("withdraw_zjbk");
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKeyIn(disList).taskCandidateOrAssigned(user.getLoginName());
        List<Task> tasks = taskQuery.list();
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            if (processInstance == null || processInstance.getBusinessKey() == null) {
                continue;
            }
	        String businessKey = processInstance.getBusinessKey();
	        Withdraw withdraw = withdrawDao.findObjectById(new Long(businessKey));
	        if(withdraw != null){
	        	Dispatched dispatched = dispatchedDao.findObjectById(withdraw.getBkid());
		        if(dispatched != null && user != null && user.getPosition() != null && user.getLskhbm() != null && dispatched.getLskhbm() != null && dispatched.getBkxz() != null && user.getSystemNo() != null){
		        	String currentUserPosition = user.getPosition().trim();
		        	String currentUserLskhbm = user.getLskhbm().trim();
		        	String currentUserSystemNo = user.getSystemNo().trim();
		        	String bkqsLskhbm = dispatched.getLskhbm().trim();
		        	if(currentUserPosition.substring(0, 2).compareTo("91") >= 0){//市局不区分部门
		        		withdraw.setDispatched(dispatched);
				        withdraw.setTask(task);
				        withdraw.setProcessInstance(processInstance);
				        withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					    results.add(withdraw);
		        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("81") >= 0 && (currentUserLskhbm.equals(bkqsLskhbm) || (dispatched.getFjsn() != null && dispatched.getFjsn().trim().equals(currentUserSystemNo.substring(0, 6)))))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("81") >= 0)){//分局
		        		withdraw.setDispatched(dispatched);
				        withdraw.setTask(task);
				        withdraw.setProcessInstance(processInstance);
				        withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					    results.add(withdraw);
		        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("71") >= 0 && currentUserLskhbm.equals(bkqsLskhbm))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("71") >= 0)){//分局以下
		        		withdraw.setDispatched(dispatched);
				        withdraw.setTask(task);
				        withdraw.setProcessInstance(processInstance);
				        withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					    results.add(withdraw);
		        	}
		        }
	        }
        }
        int endIndex = (pageNo-1)*pageSize+pageSize > results.size() ? results.size() : (pageNo-1)*pageSize+pageSize;
        lastResults = results.subList((pageNo-1)*pageSize, endIndex);
        return new PageResult(results.size(), pageNo, pageSize, lastResults);
    }
    /**
     * 查询待办公开任务数量
     *
     * @param userId 用户ID
     * @return
     */
    @Transactional(readOnly = true)
    public int findTodoOpenTasksCount(User user) {
    	List<Withdraw> results = new ArrayList<Withdraw>();
    	int sum = 0;
    	List<String> disList = new ArrayList<String>();
        disList.add("withdraw_crime");
        disList.add("withdraw_control");
        disList.add("withdraw_traffic");
        disList.add("withdraw_zjbk");
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKeyIn(disList).taskCandidateOrAssigned(user.getLoginName());
    	List<Task> tasks = taskQuery.list();
    	// 根据流程的业务ID查询实体并关联
    	for (Task task : tasks) {
    		String processInstanceId = task.getProcessInstanceId();
    		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
    		if (processInstance == null || processInstance.getBusinessKey() == null) {
    			continue;
    		}
    		String businessKey = processInstance.getBusinessKey();
    		Withdraw withdraw = withdrawDao.findObjectById(new Long(businessKey));
    		if(withdraw != null){
    			Dispatched dispatched = dispatchedDao.findObjectById(withdraw.getBkid());
        		if(dispatched != null && user != null && user.getPosition() != null && user.getLskhbm() != null && dispatched.getLskhbm() != null && dispatched.getBkxz() != null && user.getSystemNo() != null){
    	        	String currentUserPosition = user.getPosition().trim();
    	        	String currentUserLskhbm = user.getLskhbm().trim();
    	        	String currentUserSystemNo = user.getSystemNo().trim();
    	        	String bkqsLskhbm = dispatched.getLskhbm().trim();
    	        	String bkxz = dispatched.getBkxz().trim();
    	        	if("1".equals(bkxz)){
    	        		if(currentUserPosition.substring(0, 2).compareTo("91") >= 0){//市局不区分部门
    	        			withdraw.setDispatched(dispatched);
    	    				withdraw.setTask(task);
    	    				withdraw.setProcessInstance(processInstance);
    	    				withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
    	    				results.add(withdraw);
    		        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("81") >= 0 && (currentUserLskhbm.equals(bkqsLskhbm) || (dispatched.getFjsn() != null && dispatched.getFjsn().trim().equals(currentUserSystemNo.substring(0, 6)))))
    	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("81") >= 0)){//分局
    		        		withdraw.setDispatched(dispatched);
    	    				withdraw.setTask(task);
    	    				withdraw.setProcessInstance(processInstance);
    	    				withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
    	    				results.add(withdraw);
    		        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("71") >= 0 && currentUserLskhbm.equals(bkqsLskhbm))
    	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("71") >= 0)){//分局以下
    		        		withdraw.setDispatched(dispatched);
    	    				withdraw.setTask(task);
    	    				withdraw.setProcessInstance(processInstance);
    	    				withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
    	    				results.add(withdraw);
    		        	}
    	        	}
        		}
    		}
    	}
    	if(results != null && results.size() > 0){
    		sum = results.size();
    	}
    	return sum;
    }
    /**
     * 查询待办任务(包括秘密任务)数量
     *
     * @param userId 用户ID
     * @return
     */
    @Transactional(readOnly = true)
    public int findTodoTasksCount(User user) {
    	List<Withdraw> results = new ArrayList<Withdraw>();
    	int sum = 0;
    	List<String> disList = new ArrayList<String>();
        disList.add("withdraw_crime");
        disList.add("withdraw_control");
        disList.add("withdraw_traffic");
        disList.add("withdraw_zjbk");
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKeyIn(disList).taskCandidateOrAssigned(user.getLoginName());
    	List<Task> tasks = taskQuery.list();
    	// 根据流程的业务ID查询实体并关联
    	for (Task task : tasks) {
    		String processInstanceId = task.getProcessInstanceId();
    		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
    		if (processInstance == null || processInstance.getBusinessKey() == null) {
    			continue;
    		}
    		String businessKey = processInstance.getBusinessKey();
    		Withdraw withdraw = withdrawDao.findObjectById(new Long(businessKey));
    		if(withdraw != null){
    			Dispatched dispatched = dispatchedDao.findObjectById(withdraw.getBkid());
        		if(dispatched != null && user != null && user.getPosition() != null && user.getLskhbm() != null && dispatched.getLskhbm() != null && dispatched.getBkxz() != null && user.getSystemNo() != null){
    	        	String currentUserPosition = user.getPosition().trim();
    	        	String currentUserLskhbm = user.getLskhbm().trim();
    	        	String currentUserSystemNo = user.getSystemNo().trim();
    	        	String bkqsLskhbm = dispatched.getLskhbm().trim();
    	        	if(currentUserPosition.substring(0, 2).compareTo("91") >= 0){//市局不区分部门
    	        		withdraw.setDispatched(dispatched);
    	    			withdraw.setTask(task);
    	    			withdraw.setProcessInstance(processInstance);
    	    			withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
    	    			results.add(withdraw);
    	        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("81") >= 0 && (currentUserLskhbm.equals(bkqsLskhbm) || (dispatched.getFjsn() != null && dispatched.getFjsn().trim().equals(currentUserSystemNo.substring(0, 6)))))
            			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("81") >= 0)){//分局
    	        		withdraw.setDispatched(dispatched);
        				withdraw.setTask(task);
        				withdraw.setProcessInstance(processInstance);
        				withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
        				results.add(withdraw);
    	        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("71") >= 0 && currentUserLskhbm.equals(bkqsLskhbm))
            			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("71") >= 0)){//分局以下
    	        		withdraw.setDispatched(dispatched);
    	    			withdraw.setTask(task);
    	    			withdraw.setProcessInstance(processInstance);
    	    			withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
    	    			results.add(withdraw);
    	        	}
        		}
    		}
    	}
    	if(results != null && results.size() > 0){
    		sum = results.size();
    	}
    	return sum;
    }
    /**
     * 查询流程定义对象
     *
     * @param processDefinitionId 流程定义ID
     * @return
     */
    public ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }
	
	public void updateWithdraw(Withdraw w) {
		withdrawDao.update(w);
	}

	public void deleteWithdraw(Withdraw w) {
		withdrawDao.delete(w);
	}

	public List<Object> findObjects(String hql, List<Object> parameters) {
		return withdrawDao.findObjects(hql, parameters);
	}

	public List<Withdraw> findObjects(QueryHelper queryHelper) {
		return withdrawDao.findObjects(queryHelper);
	}
	
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,
			int pageSize,Boolean publicBkxz) {
		PageResult result = withdrawDao.getPageResult(queryHelper, pageNo, pageSize);
		List<Withdraw> list = result.getItems();
		List<Withdraw> results = new ArrayList<Withdraw>();
		List<String> disList = new ArrayList<String>();
        disList.add("withdraw_crime");
        disList.add("withdraw_control");
        disList.add("withdraw_traffic");
        disList.add("withdraw_zjbk");
		for(Withdraw withdraw : list){
			Task task = taskService.createTaskQuery().processDefinitionKeyIn(disList).processInstanceBusinessKey(withdraw.getCkid().toString()).singleResult();
			Dispatched dispatched = dispatchedDao.findObjectById(withdraw.getBkid());
			if(publicBkxz && !"1".equals(dispatched.getBkxz())){
				continue;
			}
			withdraw.setDispatched(dispatched);
			if(task == null){
				results.add(withdraw);
				continue;
			}
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
//			if(!processInstance.getProcessDefinitionKey().equals("withdraw")){
//				results.add(withdraw);
//				continue;
//			}
			
			withdraw.setTask(task);
			withdraw.setProcessInstance(processInstance);
			withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
			results.add(withdraw);
		}
        return new PageResult(result.getTotalCount(), pageNo, pageSize, results);
	}
	

	public Withdraw findWithdrawById(Serializable id) {
		Withdraw withdraw = withdrawDao.findObjectById(id);
		Dispatched dispatched = dispatchedDao.findObjectById(withdraw.getBkid());
		withdraw.setDispatched(dispatched);
		return withdraw;
	}

	/**
	 * 根据ID逻辑删除基础数据条目
	 */
	public void deleteWithdraw(Serializable id) {
		withdrawDao.delete(id);
	}

	public List<Map> findList(String hql, List<Object> parameters) {
		return withdrawDao.findList(hql, parameters);
	}	
	
	public Serializable save(Object entity){
		return dispatchedDao.save(entity);
	}
	
	public void update(Object entity){
		dispatchedDao.update(entity);
	}
	
	/**
	 * 查询出撤控列表
	 */
	@Override
	public List<Withdraw> findList(String hql, Map<String, Object> params) {
		List<Withdraw> list = dispatchedDao.findWithdrawList(hql, params);
		for(Withdraw w:list){
			Dispatched dispatched = dispatchedDao.findObjectById(w.getBkid());
			w.setDispatched(dispatched);
		}
		return list;
	}
	
	/**
     * 查询待办公开任务
     *
     * @param userId 用户ID
     * @return
     */
    @Transactional(readOnly = true)
    public List<Withdraw> findTodoOpenTasksForOa(User user) {
        List<Withdraw> results = new ArrayList<Withdraw>();
        List<String> disList = new ArrayList<String>();
        disList.add("withdraw_crime");
        disList.add("withdraw_control");
        disList.add("withdraw_traffic");
        disList.add("withdraw_zjbk");
        
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKeyIn(disList).taskCandidateOrAssigned(user.getLoginName());
        List<Task> tasks = taskQuery.list();
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            if (processInstance == null || processInstance.getBusinessKey() == null) {
                continue;
            }
	        String businessKey = processInstance.getBusinessKey();
	        Withdraw withdraw = withdrawDao.findObjectById(new Long(businessKey));
	        if(withdraw != null){
	        	Dispatched dispatched = dispatchedDao.findObjectById(withdraw.getBkid());
		        if(dispatched != null && user != null && user.getPosition() != null && user.getLskhbm() != null && dispatched.getLskhbm() != null && dispatched.getBkxz() != null && user.getSystemNo() != null){
		        	String currentUserPosition = user.getPosition().trim();
		        	String currentUserLskhbm = user.getLskhbm().trim();
		        	String currentUserSystemNo = user.getSystemNo().trim();
		        	String bkqsLskhbm = dispatched.getLskhbm().trim();
		        	String bkxz = dispatched.getBkxz().trim();
		        	if("1".equals(bkxz)){
		        		if(currentUserPosition.substring(0, 2).compareTo("91") >= 0){//市局不区分部门
		        			withdraw.setDispatched(dispatched);
				        	withdraw.setTask(task);
				        	withdraw.setProcessInstance(processInstance);
				        	withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					        results.add(withdraw);
			        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("81") >= 0 && (currentUserLskhbm.equals(bkqsLskhbm) || (dispatched.getFjsn() != null && dispatched.getFjsn().trim().equals(currentUserSystemNo.substring(0, 6)))))
		        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("81") >= 0)){//分局
			        		withdraw.setDispatched(dispatched);
				        	withdraw.setTask(task);
				        	withdraw.setProcessInstance(processInstance);
				        	withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					        results.add(withdraw);
			        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("71") >= 0 && currentUserLskhbm.equals(bkqsLskhbm))
		        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("71") >= 0)){//分局以下
			        		withdraw.setDispatched(dispatched);
					        withdraw.setTask(task);
					        withdraw.setProcessInstance(processInstance);
					        withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
						    results.add(withdraw);
			        	}
		        	}
		        }
	        }
        }
        return results;
    }
    
    /**
     * 查询待办任务(包括秘密任务)
     *
     * @param userId 用户ID
     * @return
     */
    @Transactional(readOnly = true)
    public List<Withdraw> findTodoTasksForOa(User user) {
        List<Withdraw> results = new ArrayList<Withdraw>();
        List<String> disList = new ArrayList<String>();
        disList.add("withdraw_crime");
        disList.add("withdraw_control");
        disList.add("withdraw_traffic");
        disList.add("withdraw_zjbk");
        
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKeyIn(disList).taskCandidateOrAssigned(user.getLoginName());
        List<Task> tasks = taskQuery.list();
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            if (processInstance == null || processInstance.getBusinessKey() == null) {
                continue;
            }
	        String businessKey = processInstance.getBusinessKey();
	        Withdraw withdraw = withdrawDao.findObjectById(new Long(businessKey));
	        if(withdraw != null){
	        	Dispatched dispatched = dispatchedDao.findObjectById(withdraw.getBkid());
		        if(dispatched != null && user != null && user.getPosition() != null && user.getLskhbm() != null && dispatched.getLskhbm() != null && dispatched.getBkxz() != null && user.getSystemNo() != null){
		        	String currentUserPosition = user.getPosition().trim();
		        	String currentUserLskhbm = user.getLskhbm().trim();
		        	String currentUserSystemNo = user.getSystemNo().trim();
		        	String bkqsLskhbm = dispatched.getLskhbm().trim();
		        	if(currentUserPosition.substring(0, 2).compareTo("91") >= 0){//市局不区分部门
		        		withdraw.setDispatched(dispatched);
				        withdraw.setTask(task);
				        withdraw.setProcessInstance(processInstance);
				        withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					    results.add(withdraw);
		        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("81") >= 0 && (currentUserLskhbm.equals(bkqsLskhbm) || (dispatched.getFjsn() != null && dispatched.getFjsn().trim().equals(currentUserSystemNo.substring(0, 6)))))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("81") >= 0)){//分局
		        		withdraw.setDispatched(dispatched);
				        withdraw.setTask(task);
				        withdraw.setProcessInstance(processInstance);
				        withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					    results.add(withdraw);
		        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("71") >= 0 && currentUserLskhbm.equals(bkqsLskhbm))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("71") >= 0)){//分局以下
		        		withdraw.setDispatched(dispatched);
				        withdraw.setTask(task);
				        withdraw.setProcessInstance(processInstance);
				        withdraw.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					    results.add(withdraw);
		        	}
		        }
	        }
        }
        return results;
    }
}