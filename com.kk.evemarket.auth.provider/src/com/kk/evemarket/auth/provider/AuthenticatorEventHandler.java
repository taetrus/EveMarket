package com.kk.evemarket.auth.provider;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import com.kk.evemarket.common.events.EveMarketEventConstants;
import com.kk.evemarket.common.trade.CharacterInfo;

@Component(property = { EventConstants.EVENT_TOPIC + "=" + EveMarketEventConstants.TOPIC_START_AUTH })
public class AuthenticatorEventHandler implements EventHandler {

	public static EventAdmin eventAdmin;

	public static Authenticator callback;

	public static void setEventHandler(EventAdmin service) {
		eventAdmin = service;
	}

	public static EventAdmin getEventHandler() {
		return eventAdmin;
	}

	public static Event getAccessTokenEvent(String accessToken) {

		Map<String, Object> properties = new HashMap<>();
		properties.put(EveMarketEventConstants.ACCESS_TOKEN, accessToken);

		return new Event(EveMarketEventConstants.TOPIC_ACCESS_TOKEN, properties);
	}

	public static Event getRefreshTokenEvent(String refreshToken) {

		Map<String, Object> properties = new HashMap<>();
		properties.put(EveMarketEventConstants.REFRESH_TOKEN, refreshToken);

		return new Event(EveMarketEventConstants.TOPIC_REFRESH_TOKEN, properties);
	}

	public static Event getCharacterInfoEvent(CharacterInfo characterInfo) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(EveMarketEventConstants.CHARACTER_INFO, characterInfo);

		return new Event(EveMarketEventConstants.TOPIC_CHARACTER_INFO, properties);
	}

	@Override
	public void handleEvent(Event event) {
		String topic = event.getTopic();
		Object property = event.getProperty(EveMarketEventConstants.PROPERTY_KEY_TARGET);

		switch (topic) {
		case EveMarketEventConstants.TOPIC_START_AUTH:
			System.out.println(topic);
			callback.authenticate();
			break;

		default:
			break;
		}

	}

}
