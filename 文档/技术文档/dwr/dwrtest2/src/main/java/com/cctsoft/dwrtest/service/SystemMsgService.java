package com.cctsoft.dwrtest.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Service;
import com.cctsoft.dwrtest.utils.pushmsg.MyScriptSessionListener;
import com.cctsoft.dwrtest.utils.pushmsg.PushMessageUtil;

@Service(value="systemMsgService")
public class SystemMsgService {
	static {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					//获取在线用户
					System.out.println("=======开始发送消息==========");
					List<String> allScriptSessionIds = MyScriptSessionListener.getAllScriptSessionIds();
					for(String userId:allScriptSessionIds){
						System.out.println(userId + "======在线用户");
						//过滤，只推送给system@类型的用户
						if(userId != null && userId.trim().length() >= 7 && "system@".equals(userId.substring(0, 7))){
							System.out.println("======发送=====" + userId);
							PushMessageUtil.sendMessageToOne(userId, format.format(new Date()));
						}
					}
				}
			}, 10000, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}