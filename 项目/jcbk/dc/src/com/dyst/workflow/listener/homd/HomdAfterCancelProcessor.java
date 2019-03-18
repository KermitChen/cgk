package com.dyst.workflow.listener.homd;

import java.sql.Timestamp;
import java.util.Date;

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
public class HomdAfterCancelProcessor implements ExecutionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void notify(DelegateExecution delegateExecution) throws Exception {
		//从web中获取spring容器
    	WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
    	JjHmdService jjHmdService = (JjHmdService) ac.getBean("JjHmdService");
    	KafkaService kafkaService = (KafkaService) ac.getBean("kafkaService");
    	//更改业务状态
		String businessKey = delegateExecution.getProcessBusinessKey();
        String id = businessKey;
        Jjhomd jjhomd = jjHmdService.getHmdById(Integer.parseInt(id));
        /*
         * 同意撤销申请：1.设置撤销表状态为1同意撤销申请
         * 		2.设置homd申请表状态为空
         * 		3.设置红名单申请表状态为003已撤销申请状态
         */
        jjhomd.setJlzt("003");
        jjhomd.setZt(null);
        jjhomd.setRwzt("01");
        jjhomd.getJjhomdCx().setSpjg("1");
        jjhomd.getJjhomdCx().setSpsj(new Timestamp(new Date().getTime()));
        jjHmdService.update(jjhomd);
        kafkaService.sendMessage("DtgxUpdate", "JJHOMD-update","");
	}
}
