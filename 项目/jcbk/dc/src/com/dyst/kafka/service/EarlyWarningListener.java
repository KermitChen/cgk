package com.dyst.kafka.service;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import com.dyst.utils.Config;

public class EarlyWarningListener implements Observer{
	
	public EarlyWarningListener(){
		
	}

	@Override
	public void update(Observable o, Object arg) {
		ReadEarlyWarning earlyWaring = new ReadEarlyWarning(UUID.randomUUID().toString().replaceAll("\\-", ""), Config.getInstance().getEarlyWaringTopicName());
		earlyWaring.addObserver(this);
		new Thread(earlyWaring).start();
	}
}