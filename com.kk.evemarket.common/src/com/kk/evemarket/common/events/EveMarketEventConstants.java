package com.kk.evemarket.common.events;

public class EveMarketEventConstants {

	private EveMarketEventConstants() {
	}

	public static final String TOPIC_BASE = "com/kk/evemarket/";
	public static final String TOPIC_FIND_TRADES = TOPIC_BASE + "FIND_TRADES";
	public static final String TOPIC_CLEAR_TRADES = TOPIC_BASE + "CLEAR_TRADES";
	public static final String TOPIC_AUTHORIZED = TOPIC_BASE + "AUTHORIZED";
	public static final String TOPIC_ACCESS_TOKEN = TOPIC_BASE + "ACCESS_TOKEN";
	public static final String TOPIC_REFRESH_TOKEN = TOPIC_BASE + "REFRESH_TOKEN";
	public static final String TOPIC_START_AUTH = TOPIC_BASE + "START_AUTH";
	public static final String TOPIC_OPEN_IN_GAME = TOPIC_BASE + "OPEN_IN_GAME";
	public static final String TOPIC_MODEL_PROGRESS = TOPIC_BASE + "MODEL_PROGRESS";
	public static final String TOPIC_CHARACTER_INFO = TOPIC_BASE + "CHARACTER_INFO";

	public static final String PROPERTY_KEY_TARGET = "target";

	public static final String ACCESS_TOKEN = "accessToken";
	public static final String REFRESH_TOKEN = "refreshToken";
	public static final String CHARACTER_INFO = "characterInfo";
	public static final String OPEN_IN_GAME_TYPE_NAME = "typeName";
	public static final String TRADE_PARAMETERS = "tradeParameters";
	public static final String MODEL_PROGRESS = "modelProgress";

}
