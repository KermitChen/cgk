package com.dyst.utils;

import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.dyst.oracle.DBConnectionManager;
import com.dyst.oracle.JcdOracle;

/**
 * 初始化加载
 */
public class MyServletContextListener implements ServletContextListener {
	
	// 初始化加载方法
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			//初始化配置文件信息
			Config.getInstance();
			
			//mysql数据库连接池
			DBConnectionManager.getInstance();
			
			//加载监测点信息（监测点对应的图片存储ip）
			createGetJcdTimer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// tomcat销毁时执行方法
	public void contextDestroyed(ServletContextEvent sce) {
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
//				JcdOracle.getJcds();
				JcdOracle.getHkCloudConfig();
			}
		}, 0, time);
	}
}