package com.dyst.workflow.listener.dy;


import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dyst.DyMsg.entities.Dyxx;
import com.dyst.DyMsg.entities.Dyxxsp;
import com.dyst.DyMsg.service.DySqService;
import com.dyst.kafka.service.KafkaService;


/**
 * 审批完成处理器
 *
 */
@Component
public class AfterDyspCompleteFalse implements ExecutionListener {

	private static final long serialVersionUID = 1L;
	@Autowired
	private KafkaService kafkaService;
	public void notify(DelegateExecution delegateExecution) throws Exception {
		//从web中获取spring容器
    	WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
    	DySqService dySqService = (DySqService) ac.getBean("dysqService");
		//更改业务状态
    	String businessKey = delegateExecution.getProcessBusinessKey();
        Dyxx dyxx = dySqService.findDyxxById(Integer.parseInt(businessKey));
		dyxx.setJlzt("002");//已审批
		//dyxx.setYwzt("3");//设置业务状态为 审批不通过
        dySqService.updateDyxx(dyxx);
        Dyxxsp dyxxsp = dySqService.findDyxxspById(Integer.parseInt(businessKey));
		dyxxsp.setSpjg("0");//不同意
        dySqService.updateDyxx(dyxxsp);
        kafkaService.sendMessage("dtgx-update", "DYXX-update", "");
	}

}
