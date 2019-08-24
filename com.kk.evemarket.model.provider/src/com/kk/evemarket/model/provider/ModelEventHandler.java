package com.kk.evemarket.model.provider;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.kk.evemarket.common.events.EveMarketEventConstants;
import com.kk.evemarket.common.events.ModelProgress;

public class ModelEventHandler {

	public static EventAdmin eventAdmin;

	public static void setEventAdmin(EventAdmin service) {
		eventAdmin = service;
	}

	public static EventAdmin getEventAdmin() {
		return eventAdmin;
	}

	public static Event getModelState(ModelProgress modelProgress) {

		Map<String, Object> properties = new HashMap<>();
		properties.put(EveMarketEventConstants.MODEL_PROGRESS, modelProgress);

		return new Event(EveMarketEventConstants.TOPIC_MODEL_PROGRESS, properties);
	}
}
