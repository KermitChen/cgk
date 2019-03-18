package com.dyst.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dyst.systemmanage.entities.User;

public class UserOnlineCountUtil {
	//存放在线用户信息，sessionId作key
	private static Map<String, User> onlineUserMap = new HashMap<String, User>();
	
	/**
	 * 增加用户在线
	 * @param request 请求
	 */
	public synchronized static void addUserOnline(HttpServletRequest request){
		HttpSession session = request.getSession();
		//获取用户信息
		User user = (User)session.getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		//用户信息存在，才能执行下一步操作
		if(user != null){
			//增加
			user.setUpdateTime(new Date());//上线时间
			user.setRemark("1");//设置在线
			getOnlineUserMap().put(session.getId(), user);
		}
	}
	
	/**
	 * 标记用户在线
	 */
	public synchronized static void setUserOnline(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//根据sessionId获取相关的用户信息
		User user = onlineUserMap.get(session.getId());
		if(user != null){
			user.setRemark("1");//设置在线
			//返回map中
			onlineUserMap.put(session.getId(), user);
		}
	}
	
	/**
	 * 不在线，则从map中移除
	 * 在线，则更改状态为不在线（前端会定时更改为在线）
	 */
	public synchronized static void checkOnline() {
		//获取所有的元素，并放到一个新的map中（避免对map操作时，造成对迭代有影响）
		Map<String, User> currentOnlineUserMap = new HashMap<String, User>();
		currentOnlineUserMap.putAll(getOnlineUserMap());
		
		//遍历新集合，对旧的map进行操作
		for (Map.Entry<String, User> entry : currentOnlineUserMap.entrySet()) {
			User user = entry.getValue();
			if(user != null && "1".equals(user.getRemark())){
				//更改为不在线，并放回map中
				user.setRemark("0");
				onlineUserMap.put(entry.getKey(), user);
			} else if(user != null && "0".equals(user.getRemark())){
				//从map中移除
				getOnlineUserMap().remove(entry.getKey());
			}
		}
	}
	
	/**
	 * 获取在线用户map，凡是对map造成元素增加或减少的，都采用这种方式获取
	 * @return
	 */
	public synchronized static Map<String, User> getOnlineUserMap(){
		return onlineUserMap;
	}
}
