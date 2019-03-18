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
public class HomdAfterCancelOverTime implements ExecutionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 979506956336315905L;

	public void notify(DelegateExecution delegateExecution) throws Exception {
		//从web中获取spring容器
    	WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
    	RuntimeService runtimeService = (RuntimeService) ac.getBean("runtimeService");
    	JjHmdService jjHmdService = (JjHmdService) ac.getBean("JjHmdService");
		String processInstanceId = delegateExecution.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        Jjhomd jjhomd = jjHmdService.getHmdById(Integer.parseInt(processInstance.getBusinessKey()));
        /*
         * 业务部分：撤销红名单->不同意->
         * 		homd状态：不变
         * 		homdCx状态： 已审批状态
         * 		homdCx结果：不同意撤销申请
         */
        jjhomd.getJjhomdCx().setJlzt("002");
        jjhomd.getJjhomdCx().setSpjg("0");
        jjHmdService.update(jjhomd);
        //终止流程
        runtimeService.deleteProcessInstance(processInstanceId, "");
	}
}
