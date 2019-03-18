package com.dyst.kafka.service;

import static com.dyst.advancedAnalytics.utils.StaticUtil.BS_BUS_FLAG;
import static com.dyst.advancedAnalytics.utils.StaticUtil.BS_TOPIC_NAME;
import static com.dyst.advancedAnalytics.utils.StaticUtil.DWPZ_BUS_FLAG;
import static com.dyst.advancedAnalytics.utils.StaticUtil.DWPZ_TOPIC_NAME;
import static com.dyst.advancedAnalytics.utils.StaticUtil.PFGC_BUS_FLAG;
import static com.dyst.advancedAnalytics.utils.StaticUtil.PFGC_TOPIC_NAME;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dyst.advancedAnalytics.dto.RequestParameters;
import com.dyst.kafka.config.KafkaProperties;
import com.dyst.kafka.util.KafkaProPoolFactory;

@Service("kafkaService")
public class KafkaService {
	public static int bsCount = 0;
	public static int dwCount = 0;
	public static int pfCount = 0;
	
	private static Logger logger = LoggerFactory.getLogger(KafkaService.class);
	/**
	 * 向kafka发送消息
	 * @param topic 主题
	 * @param message 消息
	 */
	public void sendMessage(String topic,String message,String businessFlag){
		KafkaProPoolFactory pool = null;
		Producer<String, String> producer = null;
		try {
			pool = KafkaProPoolFactory.getInstance();
			producer = pool.getConnection();
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, message);
			producer.send(data);
			if (businessFlag.equals(BS_BUS_FLAG)) {
				bsCount++;
			}else if(businessFlag.equals(DWPZ_BUS_FLAG)){
				dwCount++;
			}else if(businessFlag.equals(PFGC_BUS_FLAG)){
				pfCount++;
			}
		} catch (Exception e) {
			logger.error("向kafka发送请求出错,业务代码:["+businessFlag+"]", e);
			e.printStackTrace();
		}finally{
			try {
				pool.releaseConnection(producer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 消费者获取kafka消息
	 * @param loginName 登录名
	 * @param topic 主题名
	 * @param flag 计算标记
	 * @return
	 */
	public String getMessage(String loginName,String topic,String flag){
		//获取消费者配置文件
		Properties props = KafkaProperties.getConsumerProperties();
		props.put("group.id", UUID.randomUUID().toString().replaceAll("\\-", ""));//指定消费组
		props.put("consumer.timeout.ms", "600000");//计算等待超时时间
		ConsumerConnector consumer = null;
		RequestParameters params= null;
		String resultFlag = null;
		String message = null;
		long start = System.currentTimeMillis();//计算开始时间
		try {
			consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));	
			Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
			topicCountMap.put(topic, 1);
			Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCountMap);
			KafkaStream<byte[], byte[]> stream = messageStreams.get(topic).get(0);
			ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
			while(iterator.hasNext()){
				try {
					message = new String(iterator.next().message(),"UTF-8");
					if(StringUtils.isNotEmpty(message)){
						params = JSON.parseObject(message, RequestParameters.class);
						if(StringUtils.isNotEmpty(params.getResFlag()) && flag.equals(params.getResFlag())){
							resultFlag = params.getResFlag();
							break;
						}
					}
				} catch (UnsupportedEncodingException e) {
					logger.error("编码kafka消息失败!", e);
					e.printStackTrace();
					continue;
				} catch (Exception e){
					logger.error("处理kafka消息失败!", e);
					e.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			logger.error("等待计算出错!", e);
			e.printStackTrace();
		}finally{
			if(topic.equals(BS_TOPIC_NAME)){
				bsCount--;
			}else if(topic.equals(DWPZ_TOPIC_NAME)){
				dwCount--;
			}else if(topic.equals(PFGC_TOPIC_NAME)){
				pfCount--;
			}
			logger.info("本次计算用时(毫秒)：{}", (System.currentTimeMillis()- start));
			if(consumer != null){
				consumer.shutdown();
			}
		}
		return resultFlag;
	}
}
