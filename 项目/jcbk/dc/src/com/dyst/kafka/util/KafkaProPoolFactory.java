package com.dyst.kafka.util;


import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.dyst.kafka.config.KafkaProperties;
/**
 * kafka生产者连接池
 * @author Administrator
 *
 */
public class KafkaProPoolFactory {
	
	
	private GenericObjectPool<Producer<String, String>> pool;
	private static KafkaProPoolFactory instance;
	
	private int max_idle= 10;//最大空闲连接数
	private int min_idle= 5;//最少空闲连接数
	private int max_total= 100;//最大连接值
	
    private KafkaProPoolFactory(){
    	GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(max_idle);//最大空闲连接数
		config.setMinIdle(min_idle);//最少空闲连接数
		config.setMaxTotal(max_total);//最大连接值
        ConnectionFactory factory = new ConnectionFactory(); 
        pool = new GenericObjectPool<Producer<String, String>>(factory, config);  
    }  
    
    public static synchronized KafkaProPoolFactory getInstance() throws Exception{
    	try {
			if(instance == null){
				instance = new KafkaProPoolFactory();
			}
		} catch (Exception e) {
			throw new CustomException("实例化kafka连接池出错!", e);
		}
    	return instance;
    }
    /**
     * 获取producer连接  
     * @return
     * @throws Exception
     */
    public Producer<String, String> getConnection()throws Exception{
    	Producer<String, String> producer = null;
        try {
			producer = pool.borrowObject();
		} catch (Exception e) {
			throw new CustomException("获取kafka生产者失败!", e);
		} 
        return producer;
    }  
    /**
     * 返还连接
     * @param client
     */
    public void releaseConnection(Producer<String, String> producer)throws Exception{  
        try{  
            pool.returnObject(producer);  
        }catch(Exception e){  
        	if(producer != null){  
        		try{  
        			producer.close();  
        		}catch(Exception ex){  
        			ex.printStackTrace();
        		}  
        	}  
        	throw new CustomException("返还kafka生产者失败!", e);
        }
    }  
	
	
	class ConnectionFactory extends BasePooledObjectFactory<Producer<String, String>>{

		private ProducerConfig config;
		/**
		 * 初始化创建连接所需数据
		 * @param ip
		 * @param port
		 * @param clusterName
		 */
		public ConnectionFactory(){
			Properties props = KafkaProperties.getProducerProperties();
			config = new ProducerConfig(props);
		}
		@Override
		public Producer<String, String> create() throws Exception {
			Producer<String, String> producer = null;
			try {
				producer = new Producer<String, String>(config);
			} catch (Exception e) {
				throw new CustomException("创建kafka生产者失败!", e);
			}
			return producer;
		}
		@Override
		public PooledObject<Producer<String, String>> wrap(
				Producer<String, String> pro) {
			return new DefaultPooledObject<Producer<String,String>>(pro);
		}
	}
}
