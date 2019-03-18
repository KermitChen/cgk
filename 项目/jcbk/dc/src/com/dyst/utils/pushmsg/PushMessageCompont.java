package com.dyst.utils.pushmsg;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;

@RemoteProxy(name="pushMessageCompont",creator=SpringCreator.class)
public class PushMessageCompont {
	
	@RemoteMethod
	public void onPageLoad(final String userId) {
		ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
		scriptSession.setAttribute(PushMessageUtil.DEFAULT_MARK, userId);
		MyScriptSessionListener.scriptSessionMap.put(userId, scriptSession);
	}
}
