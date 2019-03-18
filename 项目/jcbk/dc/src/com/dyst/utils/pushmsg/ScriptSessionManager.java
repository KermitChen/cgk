package com.dyst.utils.pushmsg;

import java.util.Collection;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.impl.DefaultScriptSessionManager;

public class ScriptSessionManager extends DefaultScriptSessionManager{

	public ScriptSessionManager() {
		this.addScriptSessionListener(new MyScriptSessionListener());
	}

	@Override
	public Collection<ScriptSession> getAllScriptSessions() {
		return MyScriptSessionListener.getAllScriptSessions();
	}
	
	
	
}
