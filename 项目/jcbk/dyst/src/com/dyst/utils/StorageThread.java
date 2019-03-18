package com.dyst.utils;

import java.util.concurrent.CountDownLatch;

/**
 * ͼƬ��ַ��ѯ�߳���
 * 
 * @author Administrator
 * 
 */
public class StorageThread extends Thread {
	private CountDownLatch threadsSignal;// �߳�ͬ��
	private String localPath;// ����·�����ҵ�ͼƬ�󣬸��Ƶ���·����
	private String smbPath;// ����·��

	public StorageThread(CountDownLatch threadsSignal, String localPath,
			String smbPath) {
		this.threadsSignal = threadsSignal;
		this.localPath = localPath;
		this.smbPath = smbPath;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			// ���ͼƬ���ڣ����Ƶ�������
			if (CopyPicFromServer.exists(smbPath)) {
				// ���Ƶ�������
				CopyPicFromServer.copyImage(smbPath, localPath);

				// �̼߳�������1,ִ�����������
				while (threadsSignal.getCount() > 0) {
					threadsSignal.countDown();
				}
			} else {
				threadsSignal.countDown();// �̼߳�������1,ִ�����������
			}
		} catch (Exception e) {
			threadsSignal.countDown();// �̼߳�������1,ִ�����������
			Thread.currentThread().yield();
			// System.exit();
		}
	}
}
