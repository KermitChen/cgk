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
public class HomdAfterCompleteFalse implements ExecutionListener{

	@Autowired
	private KafkaService kafkaService;
	/**
	 * 红名单申请 不同意
	 */
	public void notify(DelegateExecution delegateExecution) throws Exception {
		//从web中获取spring容器
    	WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
    	JjHmdService jjHmdService = (JjHmdService) ac.getBean("JjHmdService");
    	String businessKey = delegateExecution.getProcessBusinessKey();
    	Jjhomd jjhomd = jjHmdService.getHmdById(Integer.parseInt(businessKey));
        //设置为已审批
        jjhomd.setJlzt("002");
        jjhomd.setZt("0");
        jjhomd.getJjhomdsp().setSpjg("0");
        jjHmdService.update(jjhomd);
        kafkaService.sendMessage("dtgx-update", "JJHOMD-update", "");
	}
}
