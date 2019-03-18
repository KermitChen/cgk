package com.dyst.BaseDataMsg.serviceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.dao.JjHmdDao;
import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.BaseDataMsg.entities.Jjhomdsp;
import com.dyst.BaseDataMsg.service.DictionaryService;
import com.dyst.BaseDataMsg.service.JjHmdService;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.kafka.service.KafkaService;
import com.dyst.utils.excel.ExportExcelUtil;
import com.dyst.utils.excel.bean.JjhomdExcelBean;

@Service(value="JjHmdService")
public class JjHmdServiceImpl implements JjHmdService{

	private JjHmdDao jjHmdDao;

	public JjHmdDao getJjHmdDao() {
		return jjHmdDao;
	}
	@Autowired
	public void setJjHmdDao(JjHmdDao jjHmdDao) {
		this.jjHmdDao = jjHmdDao;
	}
    @Autowired
    protected KafkaService kafkaService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected DictionaryService dicService;
	
	
	//hql 带分页的多条件语句查询
	public PageResult getPageResult(String hql, List<Object> params,
			int pageNo, int pageSize) {
		return jjHmdDao.getPageResult(hql, params, pageNo, pageSize);
	}
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,
			int pageSize) {
		return jjHmdDao.getPageResult(queryHelper, pageNo, pageSize);
	}
	public Jjhomd getHmdById(Serializable id) {
		return jjHmdDao.findHmdById(id);
	}
	@Override
	public List wildFindHmd(String hphm) throws Exception {
		return jjHmdDao.wildFindHmd(hphm);
	}
	//查询该车是否是生效红名单
	public Boolean isHmdByHphm(String hphm,String hpzl){
		return jjHmdDao.isHmdByHphm(hphm, hpzl);
	}
	@Override
	public void save(Jjhomd hmd) {
		jjHmdDao.save(hmd);
	}
	@Override
	public void update(Jjhomd hmd) {
		jjHmdDao.update(hmd);
	}
	//保存红名单和启动工作流
	@Override
	public ProcessInstance saveHomdAndStartFlow(Jjhomd jjhomd,Map<String, Object> variables) {
		jjHmdDao.save(jjhomd);
		kafkaService.sendMessage("dtgx-update", "JJHOMD","");
        String businessKey = jjhomd.getId().toString();
        ProcessInstance processInstance = null;	
        
        try {
        	 // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(jjhomd.getSqrjh()); 
            processInstance = runtimeService.startProcessInstanceByKey("homd", businessKey, variables);			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return processInstance;
	}
    /**
     * 撤销红名单，并启动工作流
     */
	@Override
	public ProcessInstance revokeHomdAndStartFlow(Jjhomd jjhomd,Map<String, Object> variables) {
		jjHmdDao.update(jjhomd);
        String businessKey = jjhomd.getId().toString();
        ProcessInstance processInstance = null;	
        
        try {
        	 // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(jjhomd.getSqrjh()); 
            processInstance = runtimeService.startProcessInstanceByKey("homdcx", businessKey, variables);			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return processInstance;
	}
	
	//加工作流之后的分页查询
	public PageResult getPageResult2(String hql, Map<String, Object> params,
			int pageNo, int pageSize) {
		if(pageNo < 1) pageNo = 1;
		PageResult result = jjHmdDao.getPageResult2(hql, params, pageNo, pageSize);
		List<Jjhomd> list = result.getItems();
		List<Jjhomd> results = new ArrayList<Jjhomd>();
		for(Jjhomd hmd : list){
			if(hmd.getRwzt().equals("01")){
				List<Task> listTask = taskService.createTaskQuery().processDefinitionKey("homd").processInstanceBusinessKey(hmd.getId().toString()).list();
				Task task = null;
				if(listTask.size()>=1){
					task = (Task) listTask.get(listTask.size()-1);
				}
				if(task == null){
					results.add(hmd);
					continue;
				}
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				Jjhomdsp jjhomdsp = hmd.getJjhomdsp();
				jjhomdsp.setTask(task);
				jjhomdsp.setProcessInstance(processInstance);
				jjhomdsp.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
				hmd.setJjhomdsp(jjhomdsp);
				results.add(hmd);
			}else if(hmd.getRwzt().equals("02")){
				List<Task> listTask = taskService.createTaskQuery().processDefinitionKey("homdcx").processInstanceBusinessKey(hmd.getId().toString()).list();
				Task task = null;
				if(listTask.size()>=1){
					task = (Task) listTask.get(listTask.size()-1);
				}
				if(task == null){
					results.add(hmd);
					continue;
				}
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				hmd.getJjhomdCx().setTask(task);
				hmd.getJjhomdCx().setProcessInstance(processInstance);
				hmd.getJjhomdCx().setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
				results.add(hmd);
			}
			
		}
        return new PageResult(result.getTotalCount(), pageNo, pageSize, results);
	}
	
	//导出Excel
	@Override
	public void exportExcel(List<JjhomdExcelBean> excelBeanList,ServletOutputStream outputStream) throws Exception {
		ExportExcelUtil.exportExcel(excelBeanList, outputStream);
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
	public List<Jjhomd> findObjects(String hql, Map<String,Object> params) {
		List<Jjhomd> hmdList  = jjHmdDao.findObjects(hql, params);
		List<Jjhomd> results = new ArrayList<Jjhomd>();
		for(Jjhomd hmd :hmdList){
			if(hmd.getRwzt().equals("01")){
				List<Task> listTask = taskService.createTaskQuery().processDefinitionKey("homd").processInstanceBusinessKey(hmd.getId().toString()).list();
				Task task = null;
				if(listTask.size()>=1){
					task = (Task) listTask.get(listTask.size()-1);
				}
				if(task == null){
					results.add(hmd);
					continue;
				}
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				Jjhomdsp jjhomdsp = hmd.getJjhomdsp();
				jjhomdsp.setTask(task);
				jjhomdsp.setProcessInstance(processInstance);
				jjhomdsp.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
				hmd.setJjhomdsp(jjhomdsp);
				results.add(hmd);
			}else if(hmd.getRwzt().equals("02")){
				List<Task> listTask = taskService.createTaskQuery().processDefinitionKey("homdcx").processInstanceBusinessKey(hmd.getId().toString()).list();
				Task task = null;
				if(listTask.size()>=1){
					task = (Task) listTask.get(listTask.size()-1);
				}
				if(task == null){
					results.add(hmd);
					continue;
				}
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				hmd.getJjhomdCx().setTask(task);
				hmd.getJjhomdCx().setProcessInstance(processInstance);
				hmd.getJjhomdCx().setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
				results.add(hmd);
			}
		}
		return results;
	}
    
	@Override
	public List<Jjhomd> findObjects(String hql) {
		return jjHmdDao.findObjects(hql, null);
	}
}