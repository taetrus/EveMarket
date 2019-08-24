package com.kk.evemarket.model.provider;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.kk.evemarket.common.events.EveMarketEventConstants;
import com.kk.evemarket.common.events.ModelProgress;
import com.kk.evemarket.common.events.ModelState;
import com.kk.evemarket.common.trade.CharacterInfo;
import com.kk.evemarket.common.trade.Trade;
import com.kk.evemarket.common.trade.TradeParameters;
import com.kk.evemarket.model.api.IEveMarketModel;

import javafx.collections.ObservableList;
import net.troja.eve.esi.ApiCallback;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.UserInterfaceApi;

//@Designate(ocd = EveMarketModel.Config.class, factory = true)
@Component(name = "com.kk.evemarket.model.provider", property = {
		EventConstants.EVENT_TOPIC + "=" + EveMarketEventConstants.TOPIC_FIND_TRADES,
		EventConstants.EVENT_TOPIC + "=" + EveMarketEventConstants.TOPIC_CLEAR_TRADES,
		EventConstants.EVENT_TOPIC + "=" + EveMarketEventConstants.TOPIC_ACCESS_TOKEN,
		EventConstants.EVENT_TOPIC + "=" + EveMarketEventConstants.TOPIC_REFRESH_TOKEN,
		EventConstants.EVENT_TOPIC + "=" + EveMarketEventConstants.TOPIC_OPEN_IN_GAME,
		EventConstants.EVENT_TOPIC + "=" + EveMarketEventConstants.TOPIC_CHARACTER_INFO })
public class EveMarketModel implements IEveMarketModel, EventHandler {

	private String name;
	private String accessToken;
	private String refreshToken;

	@ObjectClassDefinition
	@interface Config {
		String name() default "World";
	}

	@Reference
	public void setEventHandler(EventAdmin service) {
		ModelEventHandler.setEventAdmin(service);
	}

	@Activate
	void activate(Config config) {
		System.out.println("EveMarketModel.activate()");
		this.name = config.name();

		Event modelState = ModelEventHandler.getModelState(new ModelProgress(ModelState.Initial, 0));
		ModelEventHandler.getEventAdmin().postEvent(modelState);
	}

	@Deactivate
	void deactivate() {
	}

	@Override
	public void findTrades(TradeParameters parameters) {
		Utils.get();
		TradeFinder.get().findHmHvTrades(parameters);
	}

	public void findUndercutOrders(CharacterInfo characterInfo) {
		OrderManager.get().checkUndercutOrders(characterInfo, accessToken);
	}

	@Override
	public ObservableList<Trade> getModel() {
		return TradeFinder.get().getTrades();
	}

	private void openInGame(String typeName) {

		Integer type_id = Utils.getItemNoFromName(typeName);
		UserInterfaceApi api = new UserInterfaceApi();

		try {
			api.postUiOpenwindowMarketdetailsAsync(type_id, "tranquility", accessToken, new ApiCallback<Void>() {

				@Override
				public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(Void result, int statusCode, Map<String, List<String>> responseHeaders) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
					// TODO Auto-generated method stub

				}
			});
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void handleEvent(Event event) {

		String topic = event.getTopic();
		System.out.println("EveMarketModel.handleEvent():" + topic);
		Object property = null;
		switch (topic) {
		case EveMarketEventConstants.TOPIC_ACCESS_TOKEN:
			property = event.getProperty(EveMarketEventConstants.ACCESS_TOKEN);
			accessToken = property.toString();
			System.out.println(accessToken);
			break;
		case EveMarketEventConstants.TOPIC_REFRESH_TOKEN:
			property = event.getProperty(EveMarketEventConstants.REFRESH_TOKEN);
			refreshToken = property.toString();
			System.out.println(refreshToken);
			break;
		case EveMarketEventConstants.TOPIC_FIND_TRADES:
			property = event.getProperty(EveMarketEventConstants.TRADE_PARAMETERS);
			final TradeParameters thrParam = (TradeParameters) property;
			new Thread(new Runnable() {
				@Override
				public void run() {
					findTrades(thrParam);
				}
			}).start();

			break;
		case EveMarketEventConstants.TOPIC_CLEAR_TRADES:
			// clearTrades();
			break;
		case EveMarketEventConstants.TOPIC_OPEN_IN_GAME:
			property = event.getProperty(EveMarketEventConstants.OPEN_IN_GAME_TYPE_NAME);
			final String type = (String) property;
			new Thread(new Runnable() {
				@Override
				public void run() {
					openInGame(type);
				}
			}).start();
		case EveMarketEventConstants.TOPIC_CHARACTER_INFO:
			property = event.getProperty(EveMarketEventConstants.CHARACTER_INFO);
			final CharacterInfo characterInfo = (CharacterInfo) property;

			new Thread(() -> {
				findUndercutOrders(characterInfo);
			}).start();
		default:
			break;
		}
	}

	// @Override
	// public void handleEvent(Event event) {
	// System.out.println("EveMarketModel.handleEvent()");
	// }

}
