package com.dyst.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dyst.activemq.ReceiveMsgFromMq;
import com.dyst.kafka.service.EarlyWarningListener;
import com.dyst.kafka.service.ReadEarlyWarning;
import com.dyst.kafka.service.ReadKafkaData;
import com.dyst.kafka.service.ReadSubscription;
import com.dyst.kafka.service.SubscriptionListener;
import com.dyst.kafka.util.KafkaProPoolFactory;

/**
 * 启动监听类，初始化加载
 */
public class MyServletContextListener implements ServletContextListener {
	
	// 初始化加载方法
	public void contextInitialized(ServletContextEvent arg0) {
		//初始化配置文件信息
		Config.getInstance();
		
		//用户在线检查定时任务（两分钟检查一次）
		try {
			userOnlineTimer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//实时更新用户及部门数据
		if("1".equals(Config.getInstance().getUpdateUserDeptFlag())){
			try {
				ReceiveMsgFromMq.updateDeptOrUser();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//初始化kafka生产者连接池
		try {
			KafkaProPoolFactory.getInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//实时读取kafka数据
		try {
			readMessFromKafka();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// tomcat销毁时执行方法
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
	/**
	 * 用户在线检查定时任务（8秒检查一次，前端5秒请求一次）
	 * 不在线，则从map中移除
	 * 在线，则更改状态为不在线（前端会定时更改为在线）
	 */
	private void userOnlineTimer(){
		try {
			final Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					//检查用户在线
					UserOnlineCountUtil.checkOnline();
				}
			}, 0, 1000*60*8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从kafka读取消息，并定时检测线程是否活动
	 * @throws Exception
	 */
	private void readMessFromKafka() throws Exception{
		//预警消息
		if("1".equals(Config.getInstance().getIsReadWarn())){
			//被观察者
			ReadEarlyWarning earlyWaring = new ReadEarlyWarning(UUID.randomUUID().toString().replaceAll("\\-", ""),Config.getInstance().getEarlyWaringTopicName());
			//观察者
			EarlyWarningListener listener = new EarlyWarningListener();
			//被观察者调用addObserver(this)方法让观察者达到观察被观察者的目的
			earlyWaring.addObserver(listener);
			//首次启动
			new Thread(earlyWaring).start();
		}
		
		//订阅消息
		if("1".equals(Config.getInstance().getIsReadSubscription())){
			//被观察者
			ReadSubscription subscription = new ReadSubscription(UUID.randomUUID().toString().replaceAll("\\-", ""),Config.getInstance().getSubscriptionTopicName());
			//观察者
			SubscriptionListener listener = new SubscriptionListener();
			//被观察者调用addObserver(this)方法让观察者达到观察被观察者的目的
			subscription.addObserver(listener);
			//首次启动
			new Thread(subscription).start();
		}
		
		//实时过车数据
		if("1".equals(Config.getInstance().getIsReadVehicleData())){
			ReadKafkaData readKafkaData = new ReadKafkaData(UUID.randomUUID().toString().replaceAll("\\-", ""), Config.getInstance().getRealVehicleDataTopicName());
			//启动
			Thread thread = new Thread(readKafkaData);
			readKafkaData.setThread(thread);
			thread.start();
		}
	}
}