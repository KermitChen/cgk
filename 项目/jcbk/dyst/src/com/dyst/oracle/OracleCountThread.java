package com.dyst.oracle;

import java.util.concurrent.CountDownLatch;

public class OracleCountThread extends Thread {
	private CountDownLatch threadsSignal;
	private String sql = "";
	public int count =0;

	public OracleCountThread(CountDownLatch threadsSignal, String sql) {
		this.threadsSignal = threadsSignal;//
		this.sql = sql;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		try {
			count = OracleSearch.getTDCPGJCXCount(sql);
			threadsSignal.countDown();// �̼߳�������1,ִ�����������
		} catch (Exception e) {
			threadsSignal.countDown();// �̼߳�������1,ִ�����������
			Thread.currentThread().stop();
			// System.exit();
		}
	}
}
