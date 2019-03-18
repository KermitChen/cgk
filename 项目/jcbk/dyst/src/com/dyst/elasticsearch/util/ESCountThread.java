package com.dyst.elasticsearch.util;

import java.util.concurrent.CountDownLatch;

import org.elasticsearch.index.query.FilterBuilder;

import com.dyst.elasticsearch.ESsearcherFilter;
/**
 * 查询ES库记录总数
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
			threadsSignal.countDown();// 线程计数器减1,执行完操作后在
		} catch (Exception e) {
			threadsSignal.countDown();// 线程计数器减1,执行完操作后在
			Thread.currentThread().yield();
		}
	}
}
