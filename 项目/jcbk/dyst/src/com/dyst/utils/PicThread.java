package com.dyst.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 图片地址查询线程类
 * @author Administrator
 */
@SuppressWarnings("unchecked")
public class PicThread extends Thread {
	private CountDownLatch threadsSignal;//线程数
	private String pic = "";//图片id
	private List ListPic = new ArrayList();//存放结果
	private String flag = "";
	
	//构造方法
	public PicThread(CountDownLatch threadsSignal, String pic, String flag, List listPic) {
		this.threadsSignal = threadsSignal;//线程数
		this.pic = pic;//图片id
		this.ListPic = listPic;//存放结果
		this.flag = flag;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			String picURL = Config.getInstance().getPicURL();//图片调用前缀
			//如果是深圳交警图片调用,停车场的图片id需要另外调用一个借口
			String xtFlag = Config.getInstance().getSysFlag();
			
			//根据图片id形式，调取不同的方法
			String path = "";
			if(pic != null && !"".equals(pic) && pic.length() >= 24){
				if(!"00000000000000000000000000000000000".equals(pic)){
					//判断图片id是否以pic开头，如果是，则调取海康云存储
					if(pic.startsWith("pic")){
						path = CopyPicFromServer.copyPicReturnPathOfHk(pic, flag);//海康云存储
					} else {
						//获取监测点id
						String jcdid = pic.substring(16, 24);
						//-----图片id长度为27位--,,,,,,由于有几个点图片id出现异常的处理方法，加了后缀，需要去掉后缀
						if(pic.indexOf("_") != -1 || pic.length() == 27){
							String tpid = pic;
							if(pic.indexOf("_") != -1){//如果后面包含_，则取前面部分
								tpid = pic.substring(0, pic.lastIndexOf("_"));//监测点id	
							}
							
							//如果长度27，则截取监测点id
							if(tpid.length() == 27){
								jcdid = tpid.substring(17, 25);//监测点id
							}
						}
						
						//根据不同系统及监测点类型调用
						if("1".equals(xtFlag) && !jcdid.contains("A")){//深圳
							//调取图片
							path = CopyPicFromServer.copyPicReturnPath(pic, flag);//中央存储
						} else if("1".equals(xtFlag) && jcdid.contains("A")){//深圳停车场
							//调取图片
							path = CopyPicFromServer.picCall(pic, flag);
						}
					}
				} else{//红名单图片
					path = picURL + File.separator + pic + ".jpg";//图片前缀+图片ＩＤ
				}
			}
			
			//图片访问路径放入列表
			ListPic.add(path);
			
			threadsSignal.countDown();// 线程计数器减1,执行完操作
		} catch (Exception e) {
			threadsSignal.countDown();// 线程计数器减1,执行完操作
			Thread.currentThread().yield();
		}
	}
}