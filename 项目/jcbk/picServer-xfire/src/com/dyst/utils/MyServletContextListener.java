package com.dyst.utils;

import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.dyst.oracle.DBConnectionManager;
import com.dyst.oracle.JcdOracle;

/**
 * ��ʼ������
 */
public class MyServletContextListener implements ServletContextListener {
	
	// ��ʼ�����ط���
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			//��ʼ�������ļ���Ϣ
			Config.getInstance();
			
			//mysql���ݿ����ӳ�
			DBConnectionManager.getInstance();
			
			//���ؼ�����Ϣ�������Ӧ��ͼƬ�洢ip��
			createGetJcdTimer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// tomcat����ʱִ�з���
	public void contextDestroyed(ServletContextEvent sce) {
		DBConnectionManager.getInstance().release();// �ͷ���������
	}
	
	/**
	 * ��ʱ���ؼ����Ӧ��ͼƬ�洢ip
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