package com.dyst.kafka;

import java.util.Observable;
import java.util.Observer;

import com.dyst.utils.Config;

public class ErrorListener implements Observer{

	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			Thread.sleep(600000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Config config = Config.getInstance();
		IndexErrorLog iel = new IndexErrorLog(config.getErrorTopic(), config.getZkCon());
		iel.addObserver(this);
		new Thread(iel).start();
	}

}
