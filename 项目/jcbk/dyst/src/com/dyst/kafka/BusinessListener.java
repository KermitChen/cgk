package com.dyst.kafka;

import java.util.Observable;
import java.util.Observer;

import com.dyst.utils.Config;

public class BusinessListener implements Observer{

	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			Thread.sleep(600000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Config config = Config.getInstance();
		IndexBusinessLog ibl = new IndexBusinessLog(config.getBusinessTopic(), config.getZkCon());
		ibl.addObserver(this);
		new Thread(ibl).start();
	}

}
