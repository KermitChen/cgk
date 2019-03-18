package com.dyst.workflow.listener;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.dispatched.dao.DispatchedDao;

/**
 * 调整布控内容处理器
 *
 */
@Component
public class BeforeDispatchedModifyApplyProcessor implements TaskListener {

	private static final long serialVersionUID = 1L;

    public void notify(DelegateTask delegateTask) {
    	//从web中获取spring容器
    	WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
    	RuntimeService runtimeService = (RuntimeService) ac.getBean("runtimeService");
    	DispatchedService dispatchedService = (DispatchedService) ac.getBean("dispatchedService");
    	//更改业务状态
    	String processInstanceId = delegateTask.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        Dispatched dispatched = dispatchedService.findDispatchedById(processInstance.getBusinessKey());
        dispatched.setYwzt("6");//调整申请
        dispatchedService.updateDispatched(dispatched);
    }

}
