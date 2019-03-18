package com.dyst.activemq;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.dyst.activemq.cas.*;
import com.dyst.activemq.config.OaProperties;
import com.dyst.activemq.entities.*;
import com.dyst.utils.MySqlOperate;

public class ReceiveMsgFromMq {
	 private static String broker_url = "";
	 private static String user_name = "";
	 private static String password = "";
	 private static String clientId = ""; 
	 private static String userTopic = "";
	 
	 static{
		//获取相关属性
		Properties pro = OaProperties.getProperties();
		broker_url = pro.getProperty("oa.broker_url");
		user_name = pro.getProperty("oa.user_name");
		password = pro.getProperty("oa.password");
		clientId =  pro.getProperty("oa.clientId");
		userTopic =  pro.getProperty("oa.userTopic");
	 }
	    
	/**
	 * 动态监听oa系统的消息中间件mq，动态增加或更新部门信息、用户信息	
	 */
	public static void updateDeptOrUser() throws Exception{
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(user_name,password,broker_url);   
        Connection connection = factory.createConnection();
        connection.setClientID(clientId); // 设置客户端ID，持久订阅必须的。  
        connection.start();

        // 创建一个session
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        // 取得Topic，没有的话，会自动创建
        Topic topic = session.createTopic(userTopic);
        
        //注册消费者1
        // 创建持久订阅，第二个参数是订户名称，和clientID可以不同
        MessageConsumer consumer = session.createDurableSubscriber(topic,clientId);
        consumer.setMessageListener(new MessageListener(){
			public void onMessage(Message m) {
                try {
                	if(m instanceof TextMessage){
                		SyncMessage syncMessage = SyncMessage.parseJson(((TextMessage)m).getText());
                		//String senderClientId = syncMessage.getClientId();
	        			String type = syncMessage.getType().toString();
	                	if(SyncType.USER_ADD.toString().equals(type)){//1、保存用户
	                		SyncUser sU = (SyncUser)syncMessage.getObject();
	                		MySqlOperate.insertOrUpdateUser(sU);
	                	} else if(SyncType.USER_DELETE.toString().equals(type)){//2、删除用户
	                      	SyncUser sU = (SyncUser)syncMessage.getObject();
	                      	MySqlOperate.deleteUser(sU);
	                    } else if(SyncType.DEPT_ADD.toString().equals(type)){//3、保存部门
	                      	SyncDept sD = (SyncDept)syncMessage.getObject();
	                      	MySqlOperate.insertOrUpdateDept(sD);
	                    } else if(SyncType.DEPT_DELETE.toString().equals(type)){//4、删除部门
	                    	SyncDept sD = (SyncDept)syncMessage.getObject();
	                    	MySqlOperate.deleteDept(sD);
	                    } else if(SyncType.USER_DEPT_LINK.toString().equals(type)){//5、保存用户与部门关系
	                    
	                    }
                	}
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
	}
	
	public static void main(String[] args){
		try {
			ReceiveMsgFromMq.updateDeptOrUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}