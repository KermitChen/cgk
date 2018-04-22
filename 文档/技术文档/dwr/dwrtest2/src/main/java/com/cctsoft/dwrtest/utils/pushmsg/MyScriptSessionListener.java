package com.cctsoft.dwrtest.utils.pushmsg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;

/**
 * 重新实现ScriptSessionListener
 *
 */
public class MyScriptSessionListener implements ScriptSessionListener{
	//维护一个Map key为type@userid（type可以标识数据类型，userid为用户标识）， value为ScriptSession对象
	public static final Map<String, ScriptSession> scriptSessionMap = new HashMap<String, ScriptSession>();
	
	/**
	 * 创建Session
	 * (non-Javadoc)
	 * @param ev
	 * @see org.directwebremoting.event.ScriptSessionListener#sessionCreated(org.directwebremoting.event.ScriptSessionEvent)
	 */
	@Override
	public void sessionCreated(ScriptSessionEvent ev) {
		//PushMessageCompont类负责创建
	}

	/**
	 * 销毁Session
	 * (non-Javadoc)
	 * @param ev
	 * @see org.directwebremoting.event.ScriptSessionListener#sessionDestroyed(org.directwebremoting.event.ScriptSessionEvent)
	 */
	@Override
	public void sessionDestroyed(ScriptSessionEvent ev) {
		Object userId = ev.getSession().getAttribute(PushMessageUtil.DEFAULT_MARK);
		if(userId == null) {
		}else {
			@SuppressWarnings("unused")
			ScriptSession scriptSession = scriptSessionMap.remove(ObjectUtils.toString(userId));
		}
	}
	
	/***
	 * 获取所有的ScriptSession
	 * @Title: getAllScriptSessions 
	 * @return
	 */
	public static Collection<ScriptSession> getAllScriptSessions() {
		return scriptSessionMap.values();
	}
	
	/**
	 * 获取所有的当前登录用户ID
	 * @Title: getAllScriptSessionIds 
	 * @return
	 */
	public static List<String> getAllScriptSessionIds() {
		List<String> allUserIds = new ArrayList<String>(scriptSessionMap.keySet());
		return allUserIds;
	}
}