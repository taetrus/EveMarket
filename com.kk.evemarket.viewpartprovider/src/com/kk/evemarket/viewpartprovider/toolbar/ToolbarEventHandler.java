package com.kk.evemarket.viewpartprovider.toolbar;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.kk.evemarket.common.events.EveMarketEventConstants;
import com.kk.evemarket.common.trade.TradeParameters;

public class ToolbarEventHandler {

	public static EventAdmin eventAdmin;

	public static void setEventHandler(EventAdmin service) {
		eventAdmin = service;
	}

	public static EventAdmin getEventHandler() {
		return eventAdmin;
	}

	public static Event getFindTradesEvent(TradeParameters parameters) {

		Map<String, Object> properties = new HashMap<>();
		properties.put(EveMarketEventConstants.TRADE_PARAMETERS, parameters);

		return new Event(EveMarketEventConstants.TOPIC_FIND_TRADES, properties);
	}

	public static Event getClearTradesEvent() {

		Map<String, Object> properties = null;

		return new Event(EveMarketEventConstants.TOPIC_CLEAR_TRADES, properties);
	}

	public static Event getStartAuthEvent() {
		Map<String, Object> properties = null;

		return new Event(EveMarketEventConstants.TOPIC_START_AUTH, properties);
	}

}
