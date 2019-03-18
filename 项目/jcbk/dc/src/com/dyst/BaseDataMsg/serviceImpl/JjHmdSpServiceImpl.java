package com.dyst.BaseDataMsg.serviceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.dao.JjHmdSpDao;
import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.BaseDataMsg.entities.JjhomdCx;
import com.dyst.BaseDataMsg.entities.Jjhomdsp;
import com.dyst.BaseDataMsg.service.JjHmdSpService;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.systemmanage.entities.User;
import com.dyst.utils.StaticUtils;

@Service(value="JjHmdSpService")
public class JjHmdSpServiceImpl implements JjHmdSpService{

	private JjHmdSpDao hmdSpDao;

	public JjHmdSpDao getHmdSpDao() {
		return hmdSpDao;
	}
	@Autowired
	public void setHmdSpDao(JjHmdSpDao hmdSpDao) {
		this.hmdSpDao = hmdSpDao;
	}
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected RepositoryService repositoryService;
	
	//hql 带分页的多条件语句查询
	public PageResult getPageResult(String hql, List<Object> params,
			int pageNo, int pageSize) {
		return hmdSpDao.getPageResult(hql, params, pageNo, pageSize);
	}
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,
			int pageSize) {
		return hmdSpDao.getPageResult(queryHelper, pageNo, pageSize);
	}
	public Jjhomd getHmdById(Serializable id) {
		return hmdSpDao.findHmdById(id);
	}
	
	/**
	 * 查看待办   审批任务
	 */
	@Override
	public PageResult findTodoSpTasks(User user, int pageNo, int pageSize) {
		List<Jjhomd> results = new ArrayList<Jjhomd>();
		List<Jjhomd> lastResults = new ArrayList<Jjhomd>();
        List<String> list = new ArrayList<String>();
        list.add("homd");
        list.add("homdcx");
//        list.add("withdraw");
        if(pageNo < 1) pageNo = 1;
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKeyIn(list).taskCandidateOrAssigned(user.getLoginName());
        List<Task> tasks = taskQuery.list();
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            if (processInstance == null || processInstance.getBusinessKey() == null) {
                continue;
            }else if(processInstance.getProcessDefinitionKey().equals("homd")){
	            String businessKey = processInstance.getBusinessKey();
	            Jjhomd hmd = hmdSpDao.findHmdSpById(Integer.parseInt(businessKey));
	            if(hmd==null||hmd.getJjhomdsp()==null){
	            	continue;
	            }else if(hmd.getJjhomdsp()!=null){
	            	if(user.getPosition().compareTo("91") >= 0){//市局不区分部门
	            		hmd.getJjhomdsp().setTask(task);
	            		hmd.getJjhomdsp().setProcessInstance(processInstance);
	            		hmd.getJjhomdsp().setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
	            		results.add(hmd);
	            	}else if(user.getPosition().compareTo("80")<0){
	            		hmd.getJjhomdsp().setTask(task);
	            		hmd.getJjhomdsp().setProcessInstance(processInstance);
	            		hmd.getJjhomdsp().setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
	            		results.add(hmd);
	            	}else if(hmd.getSqrdw().length()>=6&&user.getDeptId().length() >= 6){//截取前6位区别部门
	            		if(hmd.getSqrdw().substring(0, 6).equals(user.getDeptId().substring(0, 6))){
	            			hmd.getJjhomdsp().setTask(task);
		            		hmd.getJjhomdsp().setProcessInstance(processInstance);
		            		hmd.getJjhomdsp().setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
		            		results.add(hmd);
	            		}
	            	}
	            }
            }else if(processInstance.getProcessDefinitionKey().equals("homdcx")){
            	String businessKey = processInstance.getBusinessKey();
	            Jjhomd hmd = hmdSpDao.finHmdCxById(Integer.parseInt(businessKey));
	            if(hmd==null||hmd.getJjhomdCx()==null){
	            	continue;
	            }else if(hmd.getJjhomdCx()!=null){
	            	if(user.getPosition().compareTo("91") >= 0){//市局不区分部门
	            		hmd.getJjhomdCx().setTask(task);
	            		hmd.getJjhomdCx().setProcessInstance(processInstance);
	            		hmd.getJjhomdCx().setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
	            		results.add(hmd);
	            	}else if(user.getPosition().compareTo("80")<0){
	            		hmd.getJjhomdsp().setTask(task);
	            		hmd.getJjhomdsp().setProcessInstance(processInstance);
	            		hmd.getJjhomdsp().setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
	            		results.add(hmd);
	            	}
	            	else if(hmd.getSqrdw().length()>=6&&user.getDeptId().length() >= 6){//截取前6位区别部门
	            		if(hmd.getSqrdw().substring(0, 6).equals(user.getDeptId().substring(0, 6))){
	            			hmd.getJjhomdCx().setTask(task);
	            			hmd.getJjhomdCx().setProcessInstance(processInstance);
	            			hmd.getJjhomdCx().setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
	            			results.add(hmd);
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
	
	/**
	 * 查询某个用户待办审批的数量
	 */
	@Override
	public int findCountTodoSpTasks(User user) {
		String userId = user.getLoginName();
        List<String> list = new ArrayList<String>();
        list.add("homd");
        list.add("homdcx");
        int sum=0;
		TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKeyIn(list).taskCandidateOrAssigned(userId);
		List<Task> tasks = taskQuery.list();
		if(tasks!=null&&tasks.size()>0){
			for(Task task : tasks){
				String processInstanceId = task.getProcessInstanceId();
	            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
	            if (processInstance == null || processInstance.getBusinessKey() == null) {
	                continue;
	            }else if(processInstance.getProcessDefinitionKey().equals("homd")){
		            String businessKey = processInstance.getBusinessKey();
		            Jjhomd hmd = hmdSpDao.findHmdSpById(Integer.parseInt(businessKey));
		            if(hmd==null||hmd.getJjhomdsp()==null){
		            	continue;
		            }else if(hmd.getJjhomdsp()!=null){
		            	if(user.getPosition().compareTo("91") >= 0){//市局不区分部门
		            		sum +=1;
		            	}else if(user.getPosition().compareTo("80")<0){//派出所
		            		sum +=1;
		            	}else if(hmd.getSqrdw().length()>=6&&user.getDeptId().length() >= 6){//截取前6位区别部门
		            		if(hmd.getSqrdw().substring(0, 6).equals(user.getDeptId().substring(0, 6))){
		            			sum +=1;
		            		}
		            	}
		            }
	            }else if(processInstance.getProcessDefinitionKey().equals("homdcx")){
	            	String businessKey = processInstance.getBusinessKey();
		            Jjhomd hmd = hmdSpDao.finHmdCxById(Integer.parseInt(businessKey));
		            if(hmd==null||hmd.getJjhomdCx()==null){
		            	continue;
		            }else if(hmd.getJjhomdCx()!=null){
		            	if(user.getPosition().compareTo("91") >= 0){//市局不区分部门
		            		sum +=1;
		            	}else if(user.getPosition().compareTo("80")<0){
		            		sum +=1;
		            	}
		            	else if(hmd.getSqrdw().length()>=6&&user.getDeptId().length() >= 6){//截取前6位区别部门
		            		if(hmd.getSqrdw().substring(0, 6).equals(user.getDeptId().substring(0, 6))){
		            			sum +=1;
		            		}
		            	}
		            }
	            }
			}
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
	@Override
	public int findCountTodoSpTasks2(String userId) {
		return 0;
	}
	
}
