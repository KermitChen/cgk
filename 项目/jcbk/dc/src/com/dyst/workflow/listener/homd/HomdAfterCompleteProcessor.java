package com.dyst.workflow.listener.homd;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.BaseDataMsg.service.JjHmdService;
import com.dyst.kafka.service.KafkaService;

@Component
public class HomdAfterCompleteProcessor implements ExecutionListener{
	public void notify(DelegateExecution delegateExecution) throws Exception {
		//从web中获取spring容器
    	WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
    	JjHmdService jjHmdService = (JjHmdService) ac.getBean("JjHmdService");
    	KafkaService kafkaService = (KafkaService) ac.getBean("kafkaService");
    	//更改业务状态
    	String businessKey = delegateExecution.getProcessBusinessKey();
    	String id = businessKey;
        Jjhomd jjhomd = jjHmdService.getHmdById(Integer.parseInt(id));
        //修改记录状态为已审批
        jjhomd.setJlzt("002");
        jjhomd.setZt("1");
        jjhomd.getJjhomdsp().setSpjg("1");
        jjHmdService.update(jjhomd);
        kafkaService.sendMessage("DtgxUpdate", "JJHOMD", "");
	}
}
