package com.dyst.workflow.listener;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dyst.dispatched.entities.Withdraw;
import com.dyst.dispatched.dao.WithdrawDao;

/**
 * 调整布控内容处理器
 *
 */
@Component
public class AfterWithdrawOvertime implements ExecutionListener {

	private static final long serialVersionUID = 1L;

	public void notify(DelegateExecution delegateExecution) throws Exception {
    	//从web中获取spring容器
    	WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
    	RuntimeService runtimeService = (RuntimeService) ac.getBean("runtimeService");
    	WithdrawDao withdrawDao = (WithdrawDao) ac.getBean("withdrawDao");
		//更改业务状态
		String processInstanceId = delegateExecution.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //终止流程
      	runtimeService.deleteProcessInstance(processInstanceId, "");
        Withdraw withdraw = withdrawDao.findObjectById(processInstance.getBusinessKey());
        withdraw.setYwzt("9");//审批超时
        withdrawDao.update(withdraw);
    }

}
