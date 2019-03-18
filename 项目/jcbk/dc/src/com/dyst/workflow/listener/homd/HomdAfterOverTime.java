package com.dyst.workflow.listener.homd;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.BaseDataMsg.service.JjHmdService;

@Component
public class HomdAfterOverTime implements ExecutionListener{

	public void notify(DelegateExecution delegateExecution) throws Exception {
		//从web中获取spring容器
    	WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
    	RuntimeService runtimeService = (RuntimeService) ac.getBean("runtimeService");
    	JjHmdService jjHmdService = (JjHmdService) ac.getBean("JjHmdService");
		String processInstanceId = delegateExecution.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        Jjhomd jjhomd = jjHmdService.getHmdById(Integer.parseInt(processInstance.getBusinessKey()));
        //修改记录状态为 审批超时 
        jjhomd.setJlzt("005");
        jjHmdService.update(jjhomd);
        //终止流程
        runtimeService.deleteProcessInstance(processInstanceId, "");
	}
}
