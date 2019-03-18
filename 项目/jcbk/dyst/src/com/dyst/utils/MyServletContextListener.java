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
 * ��ʼ������
 */
public class MyServletContextListener implements ServletContextListener {
	
	// ��ʼ�����ط���
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			//��ʼ�������ļ���Ϣ
			Config config = Config.getInstance();
			
			//mysql���ݿ����ӳ�
			DBConnectionManager.getInstance();
			
			//���ؼ�����Ϣ�������Ӧ��ͼƬ�洢ip��
			createGetJcdTimer();
			
			//����һ��������
			createGetJjhomdTimer();
			
			//ES���ݿ����ӳ�
			ESClientManager.getInstance(); 
			//��kafka��ȡ��־����es
			if("1".equals(config.getIsInsertLog())){
				IndexBusinessLog ibl = new IndexBusinessLog(config.getBusinessTopic(), config.getZkCon());
				BusinessListener bl = new BusinessListener();
				ibl.addObserver(bl);
				new Thread(ibl).start();
				System.out.println("ҵ����־�����������!");
//				IndexErrorLog iel = new IndexErrorLog(config.getErrorTopic(), config.getZkCon());
//				ErrorListener el = new ErrorListener();
//				iel.addObserver(el);
//				new Thread(iel).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// tomcat����ʱִ�з���
	public void contextDestroyed(ServletContextEvent sce) {
		// tomcat �ر�Web����ʱ�ر�ES���ӳ�����
		ESClientManager.getInstance().release();// �ͷ���������
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
				JcdOracle.getJcds();
				JcdOracle.getHkCloudConfig();
			}
		}, 0, time);
	}
	
	/**
	 * ��ʱ����һ��������
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
		}, 5000, time);//�ӳ�5�����
	}
}