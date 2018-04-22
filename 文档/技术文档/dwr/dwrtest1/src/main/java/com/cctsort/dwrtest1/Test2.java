package com.cctsort.dwrtest1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;

public class Test2 {
	public static final String DEFAULT_MARK = "userId";//会话ScriptSession中身份标识，用于区分不同用户ScriptSession
	public static final String DEFAULT_METHOD_NAME = "showMessage";//javascript中接收信息的方法
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static List<String> list = new ArrayList<String>();
	
	static {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("=======开始发送消息==========");
				for(String userId: list) {
					//发送给订阅该消息用户
					if(userId != null && userId.startsWith("msgType@")) {
						sendMessageSingle(userId, format.format(new Date()));
						System.out.println("==发送==" + userId);
					}
				}
			}
		}, 10000, 1000);
	}
	
	/**
	 * 接收上线用户，需要维护
	 * 
	 * @param userId
	 */
	public void onPageLoad(String userId) {
		//获取此次会话的ScriptSession对象，
		//注意一个HttpSession会有多个ScriptSession，可以使用Map<HttpSessionId, ScriptSession>
		//或Map<userId, ScriptSession>，最好使用后者，可允许同一用户打开不同页面接收不同的消息
		//（即可以给一个用户推送不同的消息到不同的页面）
		//可以实现ScriptSessionListener类，实现对ScriptSession的创建与销毁
		//此处不维护ScriptSession，仅保存订阅用户userId到list中（图方便，不可取，
		//如果用户退出，不会从list中移除）
		ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
		scriptSession.setAttribute(DEFAULT_MARK, userId);//打标记，
		//记录在list中
		list.add(userId);
		System.out.println("==========订阅用户============" + userId);
	}

	/**
	 * 推送消息给单个用户
	 * 
	 * @param userid
	 * @param message
	 */
	public static void sendMessageSingle(String userid, String message) {
		final String userId = userid;
		final String autoMessage = message;
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			//过滤，精准推送给指定用户
			public boolean match(ScriptSession session) {
				if (session.getAttribute(DEFAULT_MARK) == null)//找不到身份标识，放弃
					return false;
				else
					//如果找到相应的用户，则返回true
					return (session.getAttribute(DEFAULT_MARK)).equals(userId);
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();
			public void run() {
				//调用javascript的showMessage方法，把数据传输给页面
				script.appendCall(DEFAULT_METHOD_NAME, autoMessage);
				//下面并不是发给所有用户，上面做了过滤
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
}