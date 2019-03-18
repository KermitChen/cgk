package com.dyst.workflow.listener.dy;


import java.util.Set;

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
public class AfterDyspCompleteProcessor implements ExecutionListener {

	private static final long serialVersionUID = 1L;
	public void notify(DelegateExecution delegateExecution) throws Exception {
		//从web中获取spring容器
    	WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
    	DySqService dySqService = (DySqService) ac.getBean("dysqService");
    	KafkaService kafkaService = (KafkaService) ac.getBean("kafkaService");
    	//更改业务状态
		String businessKey = delegateExecution.getProcessBusinessKey();
        Dyxx dyxx = dySqService.findDyxxById(Integer.parseInt(businessKey));
		dyxx.setJlzt("002");//已审批
		dyxx.setYwzt("1");//设置业务状态 为订阅中
		Set<Dyxxsp> dyxxsps = dyxx.getDyxxsps();
		for(Dyxxsp d: dyxxsps){
			d.setSpjg("1");
			d.setDyxx(dyxx);
		}
        dySqService.updateDyxx(dyxx);
       /* Dyxxsp dyxxsp = dySqService.findDyxxspById(Integer.parseInt(businessKey));
		dyxxsp.setSpjg("1");//审批结果为同意
        dySqService.updateDyxx(dyxxsp);*/
        kafkaService.sendMessage("DtgxUpdate", "DYXX-update", "");
	}

}
