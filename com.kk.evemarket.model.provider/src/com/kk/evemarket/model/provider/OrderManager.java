package com.kk.evemarket.model.provider;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kk.evemarket.common.trade.CharacterInfo;

import net.troja.eve.esi.ApiCallback;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.MarketApi;
import net.troja.eve.esi.model.CharacterOrdersResponse;

public class OrderManager {
	private static Logger LOGGER = LoggerFactory.getLogger("OrderManager");
	private static OrderManager orderManager;

	private OrderManager() {
	}

	public static OrderManager get() {
		if (orderManager == null) {
			orderManager = new OrderManager();
		}

		return orderManager;
	}

	public void checkUndercutOrders(CharacterInfo characterInfo, String token) {

		String characterID = characterInfo.getCharacterID();

		MarketApi api = new MarketApi();
		try {
			api.getCharactersCharacterIdOrdersAsync(Integer.valueOf(characterID), "tranquility", token, "",
					new ApiCallback<List<CharacterOrdersResponse>>() {
						@Override
						public void onSuccess(List<CharacterOrdersResponse> orders, int statusCode,
								Map<String, List<String>> responseHeaders) {

							Stream<CharacterOrdersResponse> filter = orders.stream();
//									.filter(o -> o.getState() == StateEnum.OPEN);

							filter.forEach(f -> {

//								try {
//									List<CharacterOrdersResponse> marketOrdersForType = api.getMarketsRegionIdOrders(
//											f.getIsBuyOrder() ? "buy" : "sell", f.getRegionId(), "tranquility", "",
//											f.getTypeId(), 0);
//
//									// TODO:
//									// benim order'lardan daha yenisi var mı
//									// buylardan yuksek, sell'lerden dusuk var mı
//
//									LOGGER.info(
//											"OrderManager.checkUndercutOrders(...).new ApiCallback() {...}.onSuccess()");
//								} catch (ApiException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
							});

							LOGGER.info("OrderManager.checkUndercutOrders(...).new ApiCallback() {...}.onSuccess()");
						}

						@Override
						public void onFailure(ApiException e, int statusCode,
								Map<String, List<String>> responseHeaders) {
							LOGGER.info("OrderManager.checkUndercutOrders(...).new ApiCallback() {...}.onFailure()");
						}

						@Override
						public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
							// TODO Auto-generated method stub

						}
					});
		} catch (NumberFormatException | ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
