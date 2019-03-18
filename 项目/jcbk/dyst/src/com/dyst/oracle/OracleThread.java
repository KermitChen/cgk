package com.dyst.oracle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@SuppressWarnings("unchecked")
public class OracleThread extends Thread {
	private CountDownLatch threadsSignal;
	private String sql = "";
	private int from =0;
	private int pagsize=0;
	private String sort="";
	private String sortType = "";
	public List listtx = new ArrayList();

	public OracleThread(CountDownLatch threadsSignal, String sql,int from ,int pagsize,String sort,String sortType) {
		this.threadsSignal = threadsSignal;//
		this.sql = sql;
		this.from = from ;
		this.pagsize = pagsize;
		this.sort = sort;
		this.sortType = sortType;
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			listtx = OracleSearch.TDCPGJCX(sql,from,pagsize,sort,sortType);
			threadsSignal.countDown();// �̼߳�������1,ִ�����������
		} catch (Exception e) {
			threadsSignal.countDown();// �̼߳�������1,ִ�����������
			Thread.currentThread().yield();
			// System.exit();
		}
	}
}
