package com.dyst.workflow.listener;


import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.service.DispatchedService;

/**
 * 审批完成处理器
 *
 */
@Component
public class AfterDispatchedOvertime implements ExecutionListener {

	private static final long serialVersionUID = 1L;
	public void notify(DelegateExecution delegateExecution) throws Exception {
		//从web中获取spring容器
    	WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
    	RuntimeService runtimeService = (RuntimeService) ac.getBean("runtimeService");
    	DispatchedService dispatchedService = (DispatchedService) ac.getBean("dispatchedService");
		//更改业务状态
		String processInstanceId = delegateExecution.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //终止流程
		runtimeService.deleteProcessInstance(processInstanceId, "");
        Dispatched dispatched = dispatchedService.findDispatchedById(processInstance.getBusinessKey());
        dispatched.setYwzt("9");//审批超时
        dispatchedService.updateDispatched(dispatched);
	}

}
