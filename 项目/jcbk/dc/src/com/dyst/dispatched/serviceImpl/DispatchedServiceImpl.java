package com.dyst.dispatched.serviceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;


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
import com.dyst.dispatched.entities.Dis110;
import com.dyst.dispatched.entities.DisApproveRecord;
import com.dyst.dispatched.entities.DisReceive;
import com.dyst.dispatched.entities.DisReport;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.entities.Lsh;
import com.dyst.dispatched.entities.Withdraw;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.earlyWarning.entities.EWarning;
import com.dyst.kafka.service.KafkaService;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.excel.ExportExcelUtil;
import com.dyst.utils.excel.bean.bukong.BKQueryExcelBean;
import com.dyst.utils.excel.bean.bukong.CKExcelBean;
import com.dyst.vehicleQuery.utils.ComUtils;
import com.dyst.workflow.service.WorkflowTraceService;

@Service("dispatchedService")
public class DispatchedServiceImpl implements DispatchedService {

	// 注入持久层dao
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
    @Autowired
    protected KafkaService kafkaService;
    @Autowired
    protected UserService userService;
    
    
	//保存布控信息并启动工作流
	public ProcessInstance addDispatched(Dispatched d, Map<String, Object> variables) {
		dispatchedDao.save(d);
        String businessKey = d.getBkid().toString();

        ProcessInstance processInstance = null;
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(d.getBkrjh());
            if(d.getBkdl().equals("1")){//涉案类
            	processInstance = runtimeService.startProcessInstanceByKey("dispatched_crime", businessKey, variables);
            }else if(d.getBkdl().equals("3")){//管控类
            	processInstance = runtimeService.startProcessInstanceByKey("dispatched_control", businessKey, variables);
            }else{//交通违法类
            	processInstance = runtimeService.startProcessInstanceByKey("dispatched_traffic", businessKey, variables);
            }
        } catch (Exception e) {
        	e.printStackTrace();
		}
        return processInstance;
	}
	
	public void addDis(Dispatched d,User user,User leader, Map<String, Object> variables){
		try {
			int bkid = (Integer) dispatchedDao.save(d);
			String businessKey = Integer.toString(bkid);
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(d.getBkrjh());
            runtimeService.startProcessInstanceByKey("dispatched_zjbk", businessKey, variables);
//			kafkaService.sendMessage("DtgxUpdate", "BKSQ-update","");
			//保存报备信息
			DisReport disReport = new DisReport();
			disReport.setBbr(leader.getLoginName());
			disReport.setBbrmc(leader.getUserName());
			disReport.setBbrbm(leader.getDeptId());
			disReport.setBbrbmmc(leader.getDeptName());
			disReport.setBbrdh(leader.getTelPhone());
			disReport.setBbrjs(leader.getPosition());
			disReport.setBbrjsmc(leader.getRoleName());
			disReport.setBkid(bkid);
			disReport.setBbzt("0");
			dispatchedDao.save(disReport);
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
        List<Dispatched> results = new ArrayList<Dispatched>();
        List<Dispatched> lastResults = new ArrayList<Dispatched>();//分页后的结果
        if(pageNo < 1) pageNo = 1;
        //查询直接布控报备
        String hql = "select d from Dispatched d,DisReport dr where d.bkid=dr.bkid and dr.bbr= ? and dr.bbzt='0'";
        List<Object> parameters = new ArrayList<Object>();
        parameters.add(user.getLoginName());
        List<Dispatched> list = dispatchedDao.findDispatched(hql, parameters);
        for(Dispatched d : list){
        	DisReport disReport = dispatchedDao.findNoDealDisReport(d.getBkid());
        	d.setDisReport(disReport);
        	results.add(d);
        }
        
        // 查询需审批记录根据当前人的ID查询
        List<String> disList = new ArrayList<String>();
        disList.add("dispatched_crime");
        disList.add("dispatched_control");
        disList.add("dispatched_traffic");
        disList.add("dispatched_zjbk");
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
	        Dispatched dispatched = dispatchedDao.findObjectById(new Long(businessKey));
	        if(dispatched != null && user != null && user.getPosition() != null && user.getLskhbm() != null && dispatched.getLskhbm() != null && dispatched.getBkxz() != null && user.getSystemNo() != null){
	        	String currentUserPosition = user.getPosition().trim();
	        	String currentUserLskhbm = user.getLskhbm().trim();
	        	String currentUserSystemNo = user.getSystemNo().trim();
	        	String bkqsLskhbm = dispatched.getLskhbm().trim();
	        	String bkxz = dispatched.getBkxz().trim();
	        	if("1".equals(bkxz)){
	        		if(currentUserPosition.substring(0, 2).compareTo("91") >= 0){//市局不区分部门
				        dispatched.setTask(task);
				        dispatched.setProcessInstance(processInstance);
				        dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
				        results.add(dispatched);
		        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("81") >= 0 && (currentUserLskhbm.equals(bkqsLskhbm) || (dispatched.getFjsn() != null && dispatched.getFjsn().trim().equals(currentUserSystemNo.substring(0, 6)))))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("81") >= 0)){//分局
					    dispatched.setTask(task);
					    dispatched.setProcessInstance(processInstance);
					    dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					    results.add(dispatched);
		        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("71") >= 0 && currentUserLskhbm.equals(bkqsLskhbm))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("71") >= 0)){//分局以下
				        dispatched.setTask(task);
				        dispatched.setProcessInstance(processInstance);
				        dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
				        results.add(dispatched);
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
    	List<Dispatched> results = new ArrayList<Dispatched>();
    	List<Dispatched> lastResults = new ArrayList<Dispatched>();//分页后的结果
        if(pageNo < 1) pageNo = 1;
        
        //查询直接布控报备
        String hql = "select d from Dispatched d, DisReport dr where d.bkid=dr.bkid and dr.bbr= ? and dr.bbzt='0'";
        List<Object> parameters = new ArrayList<Object>();
        parameters.add(user.getLoginName());
        List<Dispatched> listDis = dispatchedDao.findDispatched(hql, parameters);
        for(Dispatched d : listDis){
        	DisReport disReport = dispatchedDao.findNoDealDisReport(d.getBkid());
        	d.setDisReport(disReport);
        	results.add(d);
        }
        
        // 根据当前人的ID查询
        List<String> disList = new ArrayList<String>();
        disList.add("dispatched_crime");
        disList.add("dispatched_control");
        disList.add("dispatched_traffic");
        disList.add("dispatched_zjbk");
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
	        Dispatched dispatched = dispatchedDao.findObjectById(new Long(businessKey));
	        if(dispatched != null && user != null && user.getPosition() != null && user.getLskhbm() != null && dispatched.getLskhbm() != null && user.getSystemNo() != null){
	        	String currentUserPosition = user.getPosition().trim();
	        	String currentUserLskhbm = user.getLskhbm().trim();
	        	String currentUserSystemNo = user.getSystemNo().trim();
	        	String bkqsLskhbm = dispatched.getLskhbm().trim();
	        	if(currentUserPosition.substring(0, 2).compareTo("91") >= 0){//市局
			        dispatched.setTask(task);
			        dispatched.setProcessInstance(processInstance);
			        dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
			        results.add(dispatched);
		        } else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("81") >= 0 && (currentUserLskhbm.equals(bkqsLskhbm) || (dispatched.getFjsn() != null && dispatched.getFjsn().trim().equals(currentUserSystemNo.substring(0, 6)))))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("81") >= 0)){//分局
					dispatched.setTask(task);
					dispatched.setProcessInstance(processInstance);
					dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					results.add(dispatched);
		        } else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("71") >= 0 && currentUserLskhbm.equals(bkqsLskhbm))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("71") >= 0)){//分局以下
					dispatched.setTask(task);
					dispatched.setProcessInstance(processInstance);
					dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					results.add(dispatched);
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
    public int findTodoOpenTasksCount(User user){
        List<Dispatched> results = new ArrayList<Dispatched>();
        int sum = 0;
        //查询直接布控报备
        String hql = "select d from Dispatched d, DisReport dr where d.bkid = dr.bkid and dr.bbr= ? and dr.bbzt='0'";
        List<Object> parameters = new ArrayList<Object>();
        parameters.add(user.getLoginName());
        List<Dispatched> list = dispatchedDao.findDispatched(hql, parameters);
        for(Dispatched d : list){
        	DisReport disReport = dispatchedDao.findNoDealDisReport(d.getBkid());
        	d.setDisReport(disReport);
        	results.add(d);
        }
        
        // 根据当前人的ID查询
        List<String> disList = new ArrayList<String>();
        disList.add("dispatched_crime");
        disList.add("dispatched_control");
        disList.add("dispatched_traffic");
        disList.add("dispatched_zjbk");
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
	        Dispatched dispatched = dispatchedDao.findObjectById(new Long(businessKey));
	        if(dispatched != null && user != null && user.getPosition() != null && user.getLskhbm() != null && dispatched.getLskhbm() != null && dispatched.getBkxz() != null && user.getSystemNo() != null){
	        	String currentUserPosition = user.getPosition().trim();
	        	String currentUserLskhbm = user.getLskhbm().trim();
	        	String currentUserSystemNo = user.getSystemNo().trim();
	        	String bkqsLskhbm = dispatched.getLskhbm().trim();
	        	String bkxz = dispatched.getBkxz().trim();
	        	if("1".equals(bkxz)) {
	        		if(currentUserPosition.substring(0, 2).compareTo("91") >= 0){//市局
				        dispatched.setTask(task);
				        dispatched.setProcessInstance(processInstance);
				        dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
				        results.add(dispatched);
		        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("81") >= 0 && (currentUserLskhbm.equals(bkqsLskhbm) || (dispatched.getFjsn() != null && dispatched.getFjsn().trim().equals(currentUserSystemNo.substring(0, 6)))))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("81") >= 0)){//分局
					    dispatched.setTask(task);
					    dispatched.setProcessInstance(processInstance);
					    dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					    results.add(dispatched);
		        	} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("71") >= 0 && currentUserLskhbm.equals(bkqsLskhbm))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("71") >= 0)){//分局以下
				        dispatched.setTask(task);
				        dispatched.setProcessInstance(processInstance);
				        dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
				        results.add(dispatched);
		        	}
	        	}
	        }
        }
        if(results!=null&&results.size()>0){
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
    public int findTodoTasksCount(User user){
    	List<Dispatched> results = new ArrayList<Dispatched>();
        List<String> list = new ArrayList<String>();
        list.add("dispatched");
        int sum = 0;
        //查询直接布控报备
        String hql = "select d from Dispatched d, DisReport dr where d.bkid=dr.bkid and dr.bbr= ? and dr.bbzt='0'";
        List<Object> parameters = new ArrayList<Object>();
        parameters.add(user.getLoginName());
        List<Dispatched> listDis = dispatchedDao.findDispatched(hql, parameters);
        for(Dispatched d : listDis){
        	DisReport disReport = dispatchedDao.findNoDealDisReport(d.getBkid());
        	d.setDisReport(disReport);
        	results.add(d);
        }
        
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKeyIn(list).taskCandidateOrAssigned(user.getLoginName());
        List<Task> tasks = taskQuery.list();
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            if (processInstance == null || processInstance.getBusinessKey() == null) {
                continue;
            }
	        String businessKey = processInstance.getBusinessKey();
	        Dispatched dispatched = dispatchedDao.findObjectById(new Long(businessKey));
	        if(dispatched != null && user != null && user.getPosition() != null && user.getLskhbm() != null && dispatched.getLskhbm() != null && user.getSystemNo() != null){
	        	String currentUserPosition = user.getPosition().trim();
	        	String currentUserLskhbm = user.getLskhbm().trim();
	        	String currentUserSystemNo = user.getSystemNo().trim();
	        	String bkqsLskhbm = dispatched.getLskhbm().trim();
	        	if(currentUserPosition.substring(0, 2).compareTo("91") >= 0){//市局
			        dispatched.setTask(task);
			        dispatched.setProcessInstance(processInstance);
			        dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
			        results.add(dispatched);
		        } else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("81") >= 0 && (currentUserLskhbm.equals(bkqsLskhbm) || (dispatched.getFjsn() != null && dispatched.getFjsn().trim().equals(currentUserSystemNo.substring(0, 6)))))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("81") >= 0)){//分局
				    dispatched.setTask(task);
					dispatched.setProcessInstance(processInstance);
					dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					results.add(dispatched);
		        } else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("71") >= 0 && currentUserLskhbm.equals(bkqsLskhbm))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("71") >= 0)){//分局以下
				    dispatched.setTask(task);
				    dispatched.setProcessInstance(processInstance);
				    dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
				    results.add(dispatched);
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
	
	
	public void updateDispatched(Dispatched d) {
		dispatchedDao.update(d);
		kafkaService.sendMessage("DtgxUpdate", "BKSQ-update","");
	}

	public void deleteDispatched(Dispatched d) {
		dispatchedDao.delete(d);
	}

	public List<Object> findObjects(String hql, List<Object> parameters) {
		return dispatchedDao.findObjects(hql, parameters);
	}
	@Transactional(readOnly=true)
	public List<Dispatched> findObjects(QueryHelper queryHelper) {
		return dispatchedDao.findObjects(queryHelper);
	}
	
	@SuppressWarnings("unchecked")
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,
			int pageSize) {
		if(pageNo < 1) pageNo = 1;
		PageResult result = dispatchedDao.getPageResult(queryHelper, pageNo, pageSize);
		List<Dispatched> list = result.getItems();
		List<Dispatched> results = new ArrayList<Dispatched>();
		List<String> disList = new ArrayList<String>();
        disList.add("dispatched_crime");
        disList.add("dispatched_control");
        disList.add("dispatched_traffic");
        disList.add("dispatched_zjbk");
		for(Dispatched dispatched : list){
			Task task = taskService.createTaskQuery().processDefinitionKeyIn(disList).processInstanceBusinessKey(dispatched.getBkid().toString()).singleResult();
			if(task == null){
				results.add(dispatched);
				continue;
			}
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
//			if(!processInstance.getProcessDefinitionKey().equals("dispatched")){
//				results.add(dispatched);
//				continue;
//			}
			dispatched.setTask(task);
			dispatched.setProcessInstance(processInstance);
			dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
			results.add(dispatched);
		}
        return new PageResult(result.getTotalCount(), pageNo, pageSize, results);
	}
	
	@Transactional(readOnly=true)
	public Dispatched findDispatchedById(Serializable id) {
		return dispatchedDao.findObjectById(id);
	}
	
	//根据车牌查询
	public Dispatched findDispatchedByHphmBest(String hphm,String hpzl){
		List<Dispatched> list = dispatchedDao.findDispatchedByHphm(hphm, hpzl, 0);
		if(list.size() > 0){
			Dispatched dispatched = list.get(0);//优先级最高的布控
			for(Dispatched d : list){
				if(d.getBklbjb().compareTo(dispatched.getBklbjb()) < 0){
					 dispatched = d;
				}
			}
			return dispatched;
		} else{
			return null;
		}
	}
	
	//根据车牌查询(状态为1和7的)
	public List<Dispatched> findDispatchedByHphm(String hphm, String hpzl, int bkid){
		return dispatchedDao.findDispatchedByHphm(hphm, hpzl, bkid);
	}
	/**
	 * 根据ID逻辑删除基础数据条目
	 */
	public void deleteDispatched(Serializable id) {
		dispatchedDao.delete(id);
	}

	@SuppressWarnings("rawtypes")
	public List<Map> findList(String hql, List<Object> parameters) {
		return dispatchedDao.findList(hql, parameters);
	}	
	
	public Serializable save(Object entity){
		return dispatchedDao.save(entity);
	}
	
	public void update(Object entity){
		dispatchedDao.update(entity);
	}
	
	//根据布控/撤控ID查询审批记录
	public List<DisApproveRecord> findApproveRecord(Serializable id,String bzw){
		return dispatchedDao.findApproveRecord(id,bzw);
	}
	
	//根据布控ID查直接布控报备记录
	public List<DisReport> findDisReport(Serializable id){
		return dispatchedDao.findDisReport(id);
	}
	
	//根据布控ID查签收情况
	public List<DisReceive> findDisReceiveList(Serializable bkid, String bkckbz){
		return dispatchedDao.findDisReceiveList(bkid, bkckbz);
	}
	
	//根据布控ID查撤控
	public List<Withdraw> findWithdrawList(Serializable bkid){
		return dispatchedDao.findWithdrawList(bkid);
	}
	
	//根据布控ID查预警
	public List<EWarning> findEWaringList(Serializable bkid){
		return dispatchedDao.findEWaringList(bkid);
	}
	
	//根据ID查布控签收
	public DisReceive findDisReceive(Serializable id){
		return dispatchedDao.findDisReceive(id);
	}
	
	//查询可布控的110数据
	public List<Dis110> findDis110(String loginName){
		List<Dis110> list = dispatchedDao.findDis110(loginName);
		try {
			for(Dis110 d : list){
				d.setHpzl(ComUtils.cplxToHpzl(d.getHpys()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 更改cad布控的110数据
	 * @return
	 */
	public void updateCadBk(String cadBkid){
		dispatchedDao.updateCadBk(cadBkid);
	}
	
	public PageResult getAllPageResult(String hql,List<Object> parameters, int pageNo,
			int pageSize) {
		return dispatchedDao.getAllPageResult(hql, parameters, pageNo, pageSize);
	}
	
	/**
	 * 获取高一级的领导
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public List<User> getLeader(User user) throws Exception{
		String position = "";
		
		//取上级角色
		String leaderInfo = traceService.findKeyById("SUPER_ROLE_ID", "r" + user.getPosition());
		if(leaderInfo != null){
			String[] info = leaderInfo.split(",");
			position = info[0];
		}
		
		String hql = "from User where position = ? ";
		List<Object> parameters = new ArrayList<Object>();
        parameters.add(position);
        if(user.getPosition().trim().length() != 3){
        	hql += " and lskhbm = ? ";
    		parameters.add(user.getLskhbm());
        }
		
		return dispatchedDao.getLeader(hql,parameters);
	}
	
	public List<Dispatched> findDispatched(String hql,List<Object> parameters){
		return dispatchedDao.findDispatched(hql, parameters);
	}
	
	/**
	 * 根据ID查询报备
	 * @param id
	 * @return
	 */
	public DisReport findDisReportById(Serializable id){
		return dispatchedDao.findDisReportById(id);
	}
	
	/**
	 * 根据布控ID查找未完成的报备
	 * @param bkid
	 * @return
	 */
	public DisReport findNoDealDisReport(Serializable bkid){
		return dispatchedDao.findNoDealDisReport(bkid);
	}
	
	/**
	 * 获得流水号
	 * @param sLshTemp (fjlsh,sjlsh,jclsh)
	 * @return
	 */
	public int getLsh(String sLshTemp,String date){
		int result = 1;
		Lsh lsh = dispatchedDao.getLsh(date);
		if(lsh == null){
			Lsh l = new Lsh();
			l.setScsj(date);
			l.setJclsh(0);
			l.setFjlsh(0);
			l.setSjlsh(0);
			if(sLshTemp.equals("jclsh")){
				l.setJclsh(1);
			}else if(sLshTemp.equals("fjlsh")){
				l.setFjlsh(1);
			}else if(sLshTemp.equals("sjlsh")){
				l.setSjlsh(1);
			}
			dispatchedDao.save(l);
		}else{
			if(sLshTemp.equals("jclsh")){
				result = lsh.getJclsh()+1;
				lsh.setJclsh(result);
			}else if(sLshTemp.equals("fjlsh")){
				result = lsh.getFjlsh()+1;
				lsh.setFjlsh(result);
			}else if(sLshTemp.equals("sjlsh")){
				result = lsh.getSjlsh()+1;
				lsh.setSjlsh(result);
			}
			dispatchedDao.update(lsh);
		}
		return result;
	}

	/**
	 * 根据查询条件查询出列表
	 */
	@Override
	public List<Dispatched> getListByQuery(String hql,Map<String,Object> params) {
		return dispatchedDao.getListByQuery(hql,params);
	}
	/**
	 * 布控导出excel
	 */
	@Override
	public void excelExportForDispatched(BKQueryExcelBean bean,
			ServletOutputStream outputStream) {
		try {
			ExportExcelUtil.excelExportForDispatched(bean, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 撤控导出excel
	 */
	@Override
	public void excelExportForCK(CKExcelBean bean,
			ServletOutputStream outputStream) {
		try {
			ExportExcelUtil.exportExcelForCheKong(bean, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /**
     * 查询待办公开任务，oa审批
     *
     * @param userId 用户ID
     * @return
     */
    @Transactional(readOnly = true)
    public List<Dispatched> findTodoOpenTasksForOa(User user) {
        List<Dispatched> results = new ArrayList<Dispatched>();
        //查询直接布控报备
        String hql = "select d from Dispatched d,DisReport dr where d.bkid=dr.bkid and dr.bbr= ? and dr.bbzt='0'";
        List<Object> parameters = new ArrayList<Object>();
        parameters.add(user.getLoginName());
        List<Dispatched> list = dispatchedDao.findDispatched(hql, parameters);
        for(Dispatched d : list){
        	DisReport disReport = dispatchedDao.findNoDealDisReport(d.getBkid());
        	d.setDisReport(disReport);
        	results.add(d);
        }
        
        // 查询需审批记录根据当前人的ID查询
        List<String> disList = new ArrayList<String>();
        disList.add("dispatched_crime");
        disList.add("dispatched_control");
        disList.add("dispatched_traffic");
        disList.add("dispatched_zjbk");
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
	        Dispatched dispatched = dispatchedDao.findObjectById(new Long(businessKey));
	        if(dispatched != null && user != null && user.getPosition() != null && user.getLskhbm() != null && dispatched.getLskhbm() != null && dispatched.getBkxz() != null && user.getSystemNo() != null){
	        	String currentUserPosition = user.getPosition().trim();
	        	String currentUserLskhbm = user.getLskhbm().trim();
	        	String currentUserSystemNo = user.getSystemNo().trim();
	        	String bkqsLskhbm = dispatched.getLskhbm().trim();
	        	String bkxz = dispatched.getBkxz().trim();
	        	if("1".equals(bkxz)){
	        		if(currentUserPosition.substring(0, 2).compareTo("91") >= 0){//市局
				        dispatched.setTask(task);
				        dispatched.setProcessInstance(processInstance);
				        dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
				        results.add(dispatched);
	        		} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("81") >= 0 && (currentUserLskhbm.equals(bkqsLskhbm) || (dispatched.getFjsn() != null && dispatched.getFjsn().trim().equals(currentUserSystemNo.substring(0, 6)))))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("81") >= 0)){//分局
					    dispatched.setTask(task);
					    dispatched.setProcessInstance(processInstance);
					    dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					    results.add(dispatched);
	        		} else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("71") >= 0 && currentUserLskhbm.equals(bkqsLskhbm))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("71") >= 0)){//分局以下
				        dispatched.setTask(task);
				        dispatched.setProcessInstance(processInstance);
				        dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
				        results.add(dispatched);
	        		}
	        	}
	        }
        }
        return results;
    }
    
    /**
     * 查询待办任务(包括秘密任务)，oa审批
     *
     * @param userId 用户ID
     * @return
     */
    @Transactional(readOnly = true)
    public List<Dispatched> findTodoTasksForOa(User user) {
    	List<Dispatched> results = new ArrayList<Dispatched>();
        
        //查询直接布控报备
        String hql = "select d from Dispatched d, DisReport dr where d.bkid=dr.bkid and dr.bbr= ? and dr.bbzt='0'";
        List<Object> parameters = new ArrayList<Object>();
        parameters.add(user.getLoginName());
        List<Dispatched> listDis = dispatchedDao.findDispatched(hql, parameters);
        for(Dispatched d : listDis){
        	DisReport disReport = dispatchedDao.findNoDealDisReport(d.getBkid());
        	d.setDisReport(disReport);
        	results.add(d);
        }
        
        // 根据当前人的ID查询
        List<String> disList = new ArrayList<String>();
        disList.add("dispatched_crime");
        disList.add("dispatched_control");
        disList.add("dispatched_traffic");
        disList.add("dispatched_zjbk");
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
	        Dispatched dispatched = dispatchedDao.findObjectById(new Long(businessKey));
	        if(dispatched != null && user != null && user.getPosition() != null && user.getLskhbm() != null && dispatched.getLskhbm() != null && user.getSystemNo() != null){
	        	String currentUserPosition = user.getPosition().trim();
	        	String currentUserLskhbm = user.getLskhbm().trim();
	        	String currentUserSystemNo = user.getSystemNo().trim();
	        	String bkqsLskhbm = dispatched.getLskhbm().trim();
	        	if(currentUserPosition.substring(0, 2).compareTo("91") >= 0){//市局
			        dispatched.setTask(task);
			        dispatched.setProcessInstance(processInstance);
			        dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
			        results.add(dispatched);
		        } else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("81") >= 0 && (currentUserLskhbm.equals(bkqsLskhbm) || (dispatched.getFjsn() != null && dispatched.getFjsn().trim().equals(currentUserSystemNo.substring(0, 6)))))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("81") >= 0)){//分局
					dispatched.setTask(task);
					dispatched.setProcessInstance(processInstance);
					dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					results.add(dispatched);
		        } else if((currentUserPosition.length() == 2 && currentUserPosition.substring(0, 2).compareTo("71") >= 0 && currentUserLskhbm.equals(bkqsLskhbm))
	        			|| (currentUserPosition.length() == 3 && currentUserPosition.substring(0, 2).compareTo("71") >= 0)){//分局以下
					dispatched.setTask(task);
					dispatched.setProcessInstance(processInstance);
					dispatched.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					results.add(dispatched);
		        }
	        }
        }
        return results;
    }
}