package com.dyst.kafka.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取kafka配置文件类
 * @author Administrator
 *
 */
public class KafkaProperties {

	public static Properties producerProps = null;
	public static Properties consumerProps = null;
	/**
	 * 获取kafka生产者配置文件
	 * @return
	 */
	public synchronized static Properties getProducerProperties() {
		if(producerProps == null){
			InputStream in = null;
			try {
				producerProps = new Properties();
				in = KafkaProperties.class.getResourceAsStream("/com/dyst/kafka/config/producer.properties");
				producerProps.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return producerProps;
	}
	/**
	 * 获取kakfa消费者配置文件
	 * @return
	 */
	public synchronized static Properties getConsumerProperties() {
		if(consumerProps == null){
			InputStream in = null;
			try {
				consumerProps = new Properties();
				in = KafkaProperties.class.getResourceAsStream("/com/dyst/kafka/config/consumer.properties");
				consumerProps.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return consumerProps;
	}
}
