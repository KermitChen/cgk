package com.dyst.elasticsearch.util;

import java.util.concurrent.CountDownLatch;

import org.elasticsearch.index.query.FilterBuilder;

import com.dyst.elasticsearch.ESsearcherFilter;
/**
 * ��ѯES���¼����
 * @author Administrator
 */
public class ESCountThread extends Thread {
	private CountDownLatch threadsSignal;
	private FilterBuilder filter=null;
	public int count;

	public ESCountThread(CountDownLatch threadsSignal, FilterBuilder filter) {
		this.threadsSignal = threadsSignal;
		this.filter = filter;
	}
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			count = ESsearcherFilter.getTdcpgjcxCount(filter);
			threadsSignal.countDown();// �̼߳�������1,ִ�����������
		} catch (Exception e) {
			threadsSignal.countDown();// �̼߳�������1,ִ�����������
			Thread.currentThread().yield();
		}
	}
}
