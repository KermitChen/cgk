package com.dyst.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.lang3.StringUtils;

import com.dyst.elasticsearch.util.ESClientManager;
import com.dyst.entites.BusinessLog;
import com.dyst.utils.Config;
import com.dyst.utils.StringUtil;
import com.google.gson.Gson;


public class IndexBusinessLog extends Observable implements Runnable{

	private String topic;
	private String zkCon;
	public IndexBusinessLog() {
		super();
	}

	public IndexBusinessLog(String topic,String zkCon) {
		super();
		this.topic = topic;
		this.zkCon = zkCon;
	}

	private void restart(){
		super.setChanged();
		this.notifyObservers();
	}
	
	@Override
	public void run() {
		Properties props = new Properties();
		props.put("group.id", "business_log_consumer");//ָ��������
		props.put("zookeeper.connect", zkCon);
		props.put("auto.commit.interval.ms", "1000");
		ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));	
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = messageStreams.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
		ESClientManager ecclient = ESClientManager.getInstance();
		Client client = ecclient.getConnection("es");//ES���ݿ����ӳ�,��ȡ��������
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		Config config = Config.getInstance();
		int bulkCount = config.getBulkCount();
		Gson gson = new Gson();
		BulkResponse brs = null;
		String message = null;
		BusinessLog log = null;
		int count = 1;
		try {
			while(iterator.hasNext()){
				message = new String(iterator.next().message(),"UTF-8").trim();
				if(StringUtils.isEmpty(message)){
					continue;
				}
				
            	log = ParseUtil.parseBusinessLog(message, config.getSplitStr());
            	if(log != null){
            		bulkRequest.add(client.prepareIndex("businesslog", "businesslog").setSource(gson.toJson(log)));
            		//д�ļ�
            		StringUtil.writeFile(Config.getInstance().getLogFolder(), "dc-log.txt", message, true, false);
            	} else{
            		continue;
            	}
            	
            	count++;//����������Ϊ�˱����ύ����һ���������ý���ʧ�ܣ��ǽ���û�����request�Ĵ������ᵼ��count������ʵ�ʵ����������
            	if(count % bulkCount == 0){
            		brs = bulkRequest.execute().actionGet();
            		if (brs.hasFailures()) {
            			System.out.println("�������error message:"+brs.buildFailureMessage());
            		}
            		Thread.sleep(1000);
            		bulkRequest = client.prepareBulk();
            		count = 1;
            	}
			}
		} catch (Exception e) {
			e.printStackTrace();
			restart();
		}finally{
			if(consumer != null){
				consumer.shutdown();
			}
			if (client != null) {
				ecclient.freeConnection("es", client);
			}
		}
	}

}
