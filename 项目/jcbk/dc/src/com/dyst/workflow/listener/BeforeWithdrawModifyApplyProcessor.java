package com.dyst.workflow.listener;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dyst.dispatched.dao.WithdrawDao;
import com.dyst.dispatched.entities.Withdraw;


/**
 * 调整布控内容处理器
 *
 */
@Component
public class BeforeWithdrawModifyApplyProcessor implements TaskListener {

	private static final long serialVersionUID = 1L;

    public void notify(DelegateTask delegateTask) {
    	//从web中获取spring容器
    	WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
    	RuntimeService runtimeService = (RuntimeService) ac.getBean("runtimeService");
    	WithdrawDao withdrawDao = (WithdrawDao) ac.getBean("withdrawDao");
    	//更改业务状态
    	String processInstanceId = delegateTask.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        Withdraw withdraw = withdrawDao.findObjectById(processInstance.getBusinessKey());
        withdraw.setYwzt("6");//调整申请
        withdrawDao.update(withdraw);
    }

}
