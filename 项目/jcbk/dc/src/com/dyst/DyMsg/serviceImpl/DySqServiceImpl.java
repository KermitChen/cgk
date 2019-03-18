package com.dyst.DyMsg.serviceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

import com.dyst.DyMsg.dao.DySqDao;
import com.dyst.DyMsg.entities.Dyjg;
import com.dyst.DyMsg.entities.Dyxx;
import com.dyst.DyMsg.entities.DyxxXq;
import com.dyst.DyMsg.entities.Dyxxsp;
import com.dyst.DyMsg.service.DySqService;
import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.excel.ExportExcelUtil;
import com.dyst.utils.excel.bean.DyExcelBean;
import com.dyst.utils.excel.bean.DyjgExcelBean;
import com.dyst.workflow.service.WorkflowTraceService;
import com.sun.jmx.snmp.Timestamp;
@Service(value="dysqService")
public class DySqServiceImpl implements DySqService{
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
	
	//注入DAO层
	private DySqDao dysqDao;
	public DySqDao getDysqDao() {
		return dysqDao;
	}
	@Autowired
	public void setDysqDao(DySqDao dysqDao) {
		this.dysqDao = dysqDao;
	}
	@Autowired
	private UserService userService;
	
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

	public PageResult getPageResult(String hql, Map<String, Object> params,
			int pageNo, int pageSize) {
			return dysqDao.getPageResult(hql, params, pageNo, pageSize);
	}
	
	public PageResult getPageResult2(String hql, Map<String, Object> params,
			int pageNo, int pageSize) {
		if(pageNo < 1) pageNo = 1;
		PageResult result = dysqDao.getPageResult(hql, params, pageNo, pageSize);
		List<Dyxxsp> list = result.getItems();
		List<Dyxxsp> results = new ArrayList<Dyxxsp>();
		for(Dyxxsp dyxxsp : list){
			Task task = taskService.createTaskQuery().processDefinitionKey("dysp").processInstanceBusinessKey(dyxxsp.getDyxx().getId().toString()).singleResult();
			if(task == null){
				results.add(dyxxsp);
				continue;
			}
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			dyxxsp.setTask(task);
			dyxxsp.setProcessInstance(processInstance);
			dyxxsp.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
			results.add(dyxxsp);
		}
        return new PageResult(result.getTotalCount(), pageNo, pageSize, results);
	}
	
	/**
     * 查询待办任务
     *
     * @param userId 用户ID
     * @return
	 * @throws Exception 
     */
    @Transactional(readOnly = true)
    public PageResult findTodoTasks(User user, int pageNo, int pageSize) throws Exception {
    	String userId = user.getLoginName();
    	List<Dyxxsp> results = new ArrayList<Dyxxsp>();
    	List<Dyxxsp> lastResults = new ArrayList<Dyxxsp>();
        List<String> list = new ArrayList<String>();
        list.add("dysp");
//        list.add("withdraw");
        if(pageNo < 1) pageNo = 1;
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKeyIn(list).taskCandidateOrAssigned(userId);
        List<Task> tasks = taskQuery.list();
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            if (processInstance == null || processInstance.getBusinessKey() == null) {
                continue;
            }else{
            	String businessKey = processInstance.getBusinessKey();
            	Dyxxsp dyxxsp = new Dyxxsp();
            	dyxxsp = dysqDao.findDyxxspById(new Long(businessKey));
            	if(dyxxsp==null){
            		continue;
            	}else if(dyxxsp!=null){
            		String jyjh = dyxxsp.getDyxx().getJyjh();
            		User sqr_user = new User();//订阅申请人
            		sqr_user = userService.getUser(jyjh);
            		if(sqr_user!=null){
            			if(user.getPosition().compareTo("91") >= 0){//市局不区分部门
            				dyxxsp.setTask(task);
            				dyxxsp.setProcessInstance(processInstance);
            				dyxxsp.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
            				results.add(dyxxsp);
            			}else if(user.getPosition().compareTo("80")<0){//派出所
            				dyxxsp.setTask(task);
            				dyxxsp.setProcessInstance(processInstance);
            				dyxxsp.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
            				results.add(dyxxsp);
            			}else{
            				if(sqr_user.getDeptId().length()>=6&&user.getDeptId().length() >= 6){//截取前6位区别部门
	            				if(sqr_user.getDeptId().substring(0, 6).equals(user.getDeptId().substring(0, 6))){
	            					dyxxsp.setTask(task);
	                				dyxxsp.setProcessInstance(processInstance);
	                				dyxxsp.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
	                				results.add(dyxxsp);
	            				}
	            			}
            			}
            		}
            	}
            }
        }
        int endIndex = (pageNo-1)*pageSize+pageSize > results.size() ? results.size() : (pageNo-1)*pageSize+pageSize;
        lastResults = results.subList((pageNo-1)*pageSize, endIndex);
        PageResult pageResult = new PageResult(results.size(), pageNo, pageSize, lastResults);
        return pageResult;
    }
	
    //查询待审批的任务的数量
	@Override
	public int findToDoTasksCount(User user)  {
		int sum = 0;
    	String userId = user.getLoginName();
    	List<Dyxxsp> results = new ArrayList<Dyxxsp>();
        List<String> list = new ArrayList<String>();
        list.add("dysp");
//        list.add("withdraw");
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKeyIn(list).taskCandidateOrAssigned(userId);
        List<Task> tasks = taskQuery.list();
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            if (processInstance == null || processInstance.getBusinessKey() == null) {
                continue;
            }else{
            	String businessKey = processInstance.getBusinessKey();
            	Dyxxsp dyxxsp = new Dyxxsp();
            	dyxxsp = dysqDao.findDyxxspById(new Long(businessKey));
            	if(dyxxsp==null){
            		continue;
            	}else if(dyxxsp!=null){
            		String jyjh = dyxxsp.getDyxx().getJyjh();
            		User sqr_user = null;//订阅申请人
            		try {
						sqr_user = userService.getUser(jyjh);
					} catch (Exception e) {
						e.printStackTrace();
					}
            		if(sqr_user!=null){
            			if(user.getPosition().compareTo("91") >= 0){//市局不区分部门
            				dyxxsp.setTask(task);
            				dyxxsp.setProcessInstance(processInstance);
            				dyxxsp.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
            				results.add(dyxxsp);
            			}else if(user.getPosition().compareTo("80")<0){//派出所
            				dyxxsp.setTask(task);
            				dyxxsp.setProcessInstance(processInstance);
            				dyxxsp.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
            				results.add(dyxxsp);
            			}else if(sqr_user.getDeptId().length()>=6&&user.getDeptId().length() >= 6){//截取前6位区别部门
            				if(sqr_user.getDeptId().substring(0, 6).equals(user.getDeptId().substring(0, 6))){
            					dyxxsp.setTask(task);
                				dyxxsp.setProcessInstance(processInstance);
                				dyxxsp.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
                				results.add(dyxxsp);
            				}
            			}
            		}
            	}
            }
        }
        if(results!=null&&results.size()>0){
        	sum = results.size();
        }
        return sum;
	}
	public List<Object> getList(String hql, Map<String, Object> params) {
		return dysqDao.getList(hql, params);
	}
	
	//新增订阅申请
	@Override
	public void addDysq(Dyxx d, Map<String, Object> variables) {
		try {
			dysqDao.save(d);
	        String businessKey = d.getId().toString();
	
	        ProcessInstance processInstance = null;
        
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(d.getJyjh());
            processInstance = runtimeService.startProcessInstanceByKey("dysp", businessKey, variables);
        } catch (Exception e) {
        	e.printStackTrace();
		}
	}
	
	
	@Override
	@Transactional
	public void updateDyStateByQuartz() {
		//遍历整个表把状态为（已审批，订阅中）的条目找出来，根据有效期设置是否继续有效
		String hql = "from Dyxx d where 1=1 ";
		List<Dyxx> list = dysqDao.getList2(hql);
		for(Dyxx d:list){
			//获取当前时间
			Timestamp now = new Timestamp(new Date().getTime());
			java.sql.Timestamp jzsj = d.getJzsj();
			//比较两个时间
			if(now.getDateTime()>=jzsj.getTime()){
				d.setYwzt("4");
				if(d.getJlzt().equals("001")){//待审批
					d.setJlzt("005");//过期未审批
				}
				dysqDao.update(d);
			}
		}
	}
	
	//导出该用户dy列表
	@Override
	public List<Dyxx> findExcelList(String hql, Map<String, Object> params) {
		return dysqDao.findExcelList(hql,params);
	}
	
	//导出订阅结果列表
	@Override
	public List<Dyjg> findExcelList2(String hql, Map<String, Object> params) {
		return dysqDao.getList3(hql, params);
	}
	
	@Override
	public void exportExcel(List<DyExcelBean> dyExcelBeanList,
			ServletOutputStream outputStream) throws Exception {
		//加载当前节点
		for(DyExcelBean bean:dyExcelBeanList){
			Task task = taskService.createTaskQuery().processDefinitionKey("dysp").processInstanceBusinessKey(bean.getDyxx().getId().toString()).singleResult();
			if(task == null){
				bean.setDqjd("");
				continue;
			}
			bean.setDqjd(task.getName());
		}
		ExportExcelUtil.ExcelExportForDy(dyExcelBeanList, outputStream);
	}
	
	//导出订阅结果列表
	@Override
	public void exportDyjgExcel(List<DyjgExcelBean> dyjgExcelBeanList,
			ServletOutputStream outputStream) throws Exception {
		ExportExcelUtil.ExcelExportForDyjg(dyjgExcelBeanList, outputStream);
	}
	@Override
	public void addDysq(DyxxXq d) {
		dysqDao.save(d);
	}

	@Override
	public void addDysp(Dyxxsp d) {
		
		dysqDao.save(d);
	}
	@Override
	public void updateDyxx(Object d) {
		dysqDao.update(d);
	}
	@Override
	public Dyxxsp findDyxxspById(Serializable id) {
		return dysqDao.findDyxxspById(id);
	}
	@Override
	public Dyxx findDyxxById(Serializable id) {
		return dysqDao.findDyxxById(id);
	}
	@Override
	public DyxxXq findDyxxXqById(Serializable id) {
		return (DyxxXq) dysqDao.getObjectById(DyxxXq.class, id);
	}
}