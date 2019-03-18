package com.dyst.elasticsearch.util;

import java.util.concurrent.CountDownLatch;

import org.elasticsearch.index.query.FilterBuilder;

import com.dyst.elasticsearch.ESsearcherFilter;
/**
 * ��ѯES���¼����
 * @author Administrator
 */
public class ESCountThreadByQuery extends Thread {
	private CountDownLatch threadsSignal;
	private FilterBuilder query = null;
	public int count;

	public ESCountThreadByQuery(CountDownLatch threadsSignal, FilterBuilder query) {
		this.threadsSignal = threadsSignal;
		this.query = query;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			count = ESsearcherFilter.getTdcpgjcxCount(query);
			threadsSignal.countDown();// �̼߳�������1,ִ�����������
		} catch (Exception e) {
			threadsSignal.countDown();// �̼߳�������1,ִ�����������
			Thread.currentThread().yield();
		}
	}
}