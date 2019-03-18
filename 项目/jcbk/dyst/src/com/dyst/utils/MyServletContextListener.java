package com.dyst.utils;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dyst.elasticsearch.util.ESClientManager;
import com.dyst.kafka.BusinessListener;
import com.dyst.kafka.IndexBusinessLog;
import com.dyst.oracle.DBConnectionManager;
import com.dyst.oracle.JcdOracle;
import com.dyst.oracle.JjhomdOracle;

/**
 * 初始化加载
 */
public class MyServletContextListener implements ServletContextListener {
	
	// 初始化加载方法
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			//初始化配置文件信息
			Config config = Config.getInstance();
			
			//mysql数据库连接池
			DBConnectionManager.getInstance();
			
			//加载监测点信息（监测点对应的图片存储ip）
			createGetJcdTimer();
			
			//加载一级红名单
			createGetJjhomdTimer();
			
			//ES数据库连接池
			ESClientManager.getInstance(); 
			//从kafka读取日志插入es
			if("1".equals(config.getIsInsertLog())){
				IndexBusinessLog ibl = new IndexBusinessLog(config.getBusinessTopic(), config.getZkCon());
				BusinessListener bl = new BusinessListener();
				ibl.addObserver(bl);
				new Thread(ibl).start();
				System.out.println("业务日志插入进程启动!");
//				IndexErrorLog iel = new IndexErrorLog(config.getErrorTopic(), config.getZkCon());
//				ErrorListener el = new ErrorListener();
//				iel.addObserver(el);
//				new Thread(iel).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// tomcat销毁时执行方法
	public void contextDestroyed(ServletContextEvent sce) {
		// tomcat 关闭Web服务时关闭ES连接池连接
		ESClientManager.getInstance().release();// 释放所有连接
		DBConnectionManager.getInstance().release();// 释放所有连接
	}
	
	/**
	 * 定时加载监测点对应的图片存储ip
	 */
	private void createGetJcdTimer(){
		Config config = Config.getInstance();
		String getJcdTime = config.getGetJcdTime();
		if(getJcdTime == null || "".equals(getJcdTime)){
			return;
		}
		int time = Integer.parseInt(getJcdTime);
		
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				JcdOracle.getJcds();
				JcdOracle.getHkCloudConfig();
			}
		}, 0, time);
	}
	
	/**
	 * 定时加载一级红名单
	 */
	private void createGetJjhomdTimer(){
		Config config = Config.getInstance();
		String getJjhomdTime = config.getGetJjhomdTime();
		if(getJjhomdTime == null || "".equals(getJjhomdTime)){
			return;
		}
		int time = Integer.parseInt(getJjhomdTime);
		
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				JjhomdOracle.getJjhomds();
			}
		}, 5000, time);//延迟5秒加载
	}
}