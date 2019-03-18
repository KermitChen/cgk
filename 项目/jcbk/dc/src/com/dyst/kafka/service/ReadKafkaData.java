package com.dyst.kafka.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import com.dyst.kafka.config.KafkaProperties;
import com.dyst.sjjk.service.SjjkService;
import com.dyst.utils.Config;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class ReadKafkaData implements Runnable{
	private String groupId;
	private String topic;
	private static Logger logger = LoggerFactory.getLogger(ReadKafkaData.class);
	private Thread thread;
	
	public ReadKafkaData(){
		
	}
	
	public ReadKafkaData(String groupId, String topic){
		this.groupId = groupId;
		this.topic = topic;
	}

	@Override
	public void run() {
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
			//首次获取监测点信息
			WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
			SjjkService sjjkService = (SjjkService) ac.getBean("sjjkService");
			
			int count = 0;//计数器，达到100万，则清理，需要加载监测点信息
			logger.info("开始接收消息，消息主题：" + topic);
			while(iterator.hasNext()){
				message = new String(iterator.next().message(),"UTF-8");
				
				//解析数据
				//T001#0106#0200#2017-02-27 14:17:37#车牌号码1#车牌号码2#车牌类型1#车牌类型2#通过时间#监测点#车道#XXX#图片ID#XXX#XXX##监测点名称
				if(message != null && !"".equals(message.trim())){
					count++;
					if(count >= 1000000){
						count = 0;
						sjjkService.sendGcToPage(message, true);
					} else {
						sjjkService.sendGcToPage(message, false);
					}
				}
			}
		} catch (Exception e) {
			logger.error("读取消息进程异常!元消息:{}异常信息:{}", message,e);
			e.printStackTrace();
		} finally{
			//关闭消费者
			if(consumer != null){
				consumer.shutdown();
			}
			 
			//重新启动
			ReadKafkaData readKafkaData = new ReadKafkaData(UUID.randomUUID().toString().replaceAll("\\-", ""), Config.getInstance().getRealVehicleDataTopicName());
			//启动
			thread = new Thread(readKafkaData);
			thread.start();
		}
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
}