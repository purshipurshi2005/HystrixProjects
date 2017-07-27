package com.citi;

import lombok.extern.slf4j.Slf4j;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;

@Slf4j
public class CircuitHystrixEventNotifier extends HystrixEventNotifier {
	public enum Reason { SHORTCIRCUIT,THREADREJECT,FBREJECT}

	public static final String HYSTRIX_CICUIT_EVENT_SHORT_CIRCUITED = "SHORT_CIRCUITED";
	public static final String HYSTRIX_CICUIT_EVENT_THREAD_POOL_REJECTED = "THREAD_POOL_REJECTED";


	private static DynamicStringProperty mqpath = DynamicPropertyFactory
			.getInstance().getStringProperty("path", "/temp");

	@Override
	public void markEvent(HystrixEventType eventType, HystrixCommandKey key) {

		//log.info("MQ.PATH" + mqpath);
		if (eventType.name().equals(HYSTRIX_CICUIT_EVENT_SHORT_CIRCUITED)) {

			log.info("IN HYSTRIX_CICUIT_EVENT_SHORT_CIRCUITED");
	
		} else if (eventType.name().equals(
				HYSTRIX_CICUIT_EVENT_THREAD_POOL_REJECTED)) {
			log.info("IN HYSTRIX_CICUIT_EVENT_THREAD_POOL_REJECTED");
			
		} else if (eventType.name().equals("FALLBACK_REJECTION")) {
	
			log.info("IN FALLBACK_REJECTION");

		}

	}



}