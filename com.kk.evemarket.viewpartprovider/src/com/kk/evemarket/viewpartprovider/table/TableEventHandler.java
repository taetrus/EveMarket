package com.kk.evemarket.viewpartprovider.table;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.kk.evemarket.common.events.EveMarketEventConstants;

public class TableEventHandler {

	public static EventAdmin eventAdmin;

	public static void setEventAdmin(EventAdmin service) {
		eventAdmin = service;
	}

	public static EventAdmin getEventAdmin() {
		return eventAdmin;
	}

	public static Event getOpenInGameEvent(String typeName) {

		Map<String, Object> properties = new HashMap<>();
		properties.put(EveMarketEventConstants.OPEN_IN_GAME_TYPE_NAME, typeName);

		return new Event(EveMarketEventConstants.TOPIC_OPEN_IN_GAME, properties);
	}
}
