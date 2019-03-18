package com.dyst.kafka.service;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import com.dyst.utils.Config;

public class SubscriptionListener implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		ReadSubscription subscription = new ReadSubscription(UUID.randomUUID().toString().replaceAll("\\-", ""),Config.getInstance().getSubscriptionTopicName());
		subscription.addObserver(this);
		new Thread(subscription).start();
	}

}
