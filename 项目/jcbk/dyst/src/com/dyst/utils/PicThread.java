package com.dyst.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * ͼƬ��ַ��ѯ�߳���
 * @author Administrator
 */
@SuppressWarnings("unchecked")
public class PicThread extends Thread {
	private CountDownLatch threadsSignal;//�߳���
	private String pic = "";//ͼƬid
	private List ListPic = new ArrayList();//��Ž��
	private String flag = "";
	
	//���췽��
	public PicThread(CountDownLatch threadsSignal, String pic, String flag, List listPic) {
		this.threadsSignal = threadsSignal;//�߳���
		this.pic = pic;//ͼƬid
		this.ListPic = listPic;//��Ž��
		this.flag = flag;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			String picURL = Config.getInstance().getPicURL();//ͼƬ����ǰ׺
			//��������ڽ���ͼƬ����,ͣ������ͼƬid��Ҫ�������һ�����
			String xtFlag = Config.getInstance().getSysFlag();
			
			//����ͼƬid��ʽ����ȡ��ͬ�ķ���
			String path = "";
			if(pic != null && !"".equals(pic) && pic.length() >= 24){
				if(!"00000000000000000000000000000000000".equals(pic)){
					//�ж�ͼƬid�Ƿ���pic��ͷ������ǣ����ȡ�����ƴ洢
					if(pic.startsWith("pic")){
						path = CopyPicFromServer.copyPicReturnPathOfHk(pic, flag);//�����ƴ洢
					} else {
						//��ȡ����id
						String jcdid = pic.substring(16, 24);
						//-----ͼƬid����Ϊ27λ--,,,,,,�����м�����ͼƬid�����쳣�Ĵ����������˺�׺����Ҫȥ����׺
						if(pic.indexOf("_") != -1 || pic.length() == 27){
							String tpid = pic;
							if(pic.indexOf("_") != -1){//����������_����ȡǰ�沿��
								tpid = pic.substring(0, pic.lastIndexOf("_"));//����id	
							}
							
							//�������27�����ȡ����id
							if(tpid.length() == 27){
								jcdid = tpid.substring(17, 25);//����id
							}
						}
						
						//���ݲ�ͬϵͳ���������͵���
						if("1".equals(xtFlag) && !jcdid.contains("A")){//����
							//��ȡͼƬ
							path = CopyPicFromServer.copyPicReturnPath(pic, flag);//����洢
						} else if("1".equals(xtFlag) && jcdid.contains("A")){//����ͣ����
							//��ȡͼƬ
							path = CopyPicFromServer.picCall(pic, flag);
						}
					}
				} else{//������ͼƬ
					path = picURL + File.separator + pic + ".jpg";//ͼƬǰ׺+ͼƬ�ɣ�
				}
			}
			
			//ͼƬ����·�������б�
			ListPic.add(path);
			
			threadsSignal.countDown();// �̼߳�������1,ִ�������
		} catch (Exception e) {
			threadsSignal.countDown();// �̼߳�������1,ִ�������
			Thread.currentThread().yield();
		}
	}
}