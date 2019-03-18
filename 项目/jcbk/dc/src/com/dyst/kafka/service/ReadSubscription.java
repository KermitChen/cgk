package com.dyst.kafka.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import com.dyst.DyMsg.service.DyssQueryService;
import com.dyst.kafka.config.KafkaProperties;

public class ReadSubscription extends Observable implements Runnable{
	
	private String groupId;
	private String topic;
	private static Logger logger = LoggerFactory.getLogger(ReadSubscription.class);

	public ReadSubscription(){}
	
	public ReadSubscription(String groupId,String topic){
		this.groupId = groupId;
		this.topic = topic;
	}
	public void restart(){
		super.setChanged();
		notifyObservers();
	}
	
	@Override
	public void run() {
		WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		DyssQueryService dyssQueryService = (DyssQueryService) ac.getBean("dyssQueryService");
		
		Properties props = KafkaProperties.getConsumerProperties();
		props.put("group.id", groupId);//指定消费组
		props.put("consumer.timeout.ms", "-1");
		ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = messageStreams.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
		String message = null;
		try {
			logger.info("开始读取订阅消息!");
			while(iterator.hasNext()){
				message = new String(iterator.next().message(),"UTF-8");
				dyssQueryService.sendDyssQueryMsg(message);
			}
		} catch (Exception e) {
			logger.error("读取订阅消息进程异常!", e);
			e.printStackTrace();
			restart();
		}finally{
			if(consumer != null){
				consumer.shutdown();
			}
		}
	}
}
