package com.kk.evemarket.model.provider;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

import com.kk.evemarket.common.events.ModelProgress;
import com.kk.evemarket.common.events.ModelState;
import com.kk.evemarket.common.trade.Trade;
import com.kk.evemarket.common.trade.TradeParameters;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.MarketApi;
//import net.troja.eve.esi.ApiException;
//import net.troja.eve.esi.api.MarketApi;
//import net.troja.eve.esi.api.UniverseApi;
//import net.troja.eve.esi.model.MarketHistoryResponse;
//import net.troja.eve.esi.model.MarketOrdersResponse;
//import net.troja.eve.esi.model.TypeResponse;
import net.troja.eve.esi.api.UniverseApi;
import net.troja.eve.esi.model.MarketHistoryResponse;
import net.troja.eve.esi.model.MarketOrdersResponse;
import net.troja.eve.esi.model.TypeResponse;

public class TradeFinder {

	private static TradeFinder tradeFinder;
	// private ConcurrentHashMap<Integer, List<MarketHistory>>
	// typeIDMarketHistoryMap;
	private ConcurrentLinkedQueue<Trade> hmhvTrades;
	private ObservableList<Trade> trades;

	private TradeFinder() {
		hmhvTrades = new ConcurrentLinkedQueue<Trade>();
		trades = FXCollections.observableArrayList(hmhvTrades);

	}

	public static TradeFinder get() {
		if (tradeFinder == null) {
			tradeFinder = new TradeFinder();

		}

		return tradeFinder;
	}

	public ObservableList<Trade> getTrades() {
		System.out.println(trades);
		return trades;
	}

	public void findHmHvTrades(TradeParameters parameters) {

		MarketApi marketApi = new MarketApi();

		// Utils.marketableTypeIds.forEach(typeID -> //Single Threaded

		ForkJoinPool myPool = new ForkJoinPool(25);

		ModelEventHandler.getEventAdmin()
				.postEvent(ModelEventHandler.getModelState(new ModelProgress(ModelState.Running, 0)));

		try {
			myPool.submit(() -> Utils.marketableTypeIds.parallelStream()
					.forEach(getHmHvTradesConsumer(parameters, marketApi))).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		ModelEventHandler.getEventAdmin()
				.postEvent(ModelEventHandler.getModelState(new ModelProgress(ModelState.Initial, 0)));
	}

	int progress = 0;

	private Consumer<Integer> getHmHvTradesConsumer(TradeParameters parameters, MarketApi marketApi) {
		return typeID -> {
			List<MarketHistoryResponse> marketHistory;
			try {
				marketHistory = marketApi.getMarketsRegionIdHistory(parameters.getTradeRegion(), typeID, "tranquility",
						"");

				marketHistory.sort((m1, m2) -> m2.getDate().compareTo(m1.getDate()));
				OptionalDouble average = marketHistory.stream().limit(5).mapToDouble(MarketHistoryResponse::getVolume)
						.average();

				if (!average.isPresent())
					return;

				double volume = average.getAsDouble();

				if (volume > parameters.getMinVolume()) {

					List<MarketOrdersResponse> marketOrdersForType = marketApi.getMarketsRegionIdOrders("all",
							parameters.getTradeRegion(), "tranquility", "", 0, typeID);
					// marketApi.getMarketsRegionIdOrders("all",
					// parameters.getTradeRegion(), typeID, "tranquility", null, "", "");

					Double highestBuy = marketOrdersForType.stream().filter(o -> o.getIsBuyOrder())
							.mapToDouble(o -> o.getPrice()).max().getAsDouble();
					Double lowestSell = marketOrdersForType.stream().filter(o -> !o.getIsBuyOrder())
							.mapToDouble(o -> o.getPrice()).min().getAsDouble();

					Double margin = ((lowestSell / highestBuy) - 1) * 100;

					if (margin >= parameters.getMinMargin()) {
						String itemNameFromNo = Utils.getItemNameFromNo(typeID);

						if (itemNameFromNo == null) {
							TypeResponse universeTypesTypeId = (new UniverseApi()).getUniverseTypesTypeId(typeID,
									"tranquility", "en-us", "", "");
							itemNameFromNo = universeTypesTypeId.getName();
						}

						System.out.println(itemNameFromNo + " vol: " + volume + " margin: " + margin);

						double profit = (lowestSell - highestBuy) - (lowestSell * (parameters.getFeeTax())) / 100;

						Trade tradeItem = new Trade(itemNameFromNo, volume, margin, profit, highestBuy, lowestSell);
						synchronized (hmhvTrades) {
							// hmhvTrades.add(tradeItem);
							trades.add(tradeItem);
						}

					}
				}

			} catch (ApiException e) {

				String itemNameFromNo = Utils.getItemNameFromNo(typeID);
				System.err.println(typeID + ": " + itemNameFromNo + "NOT FOUND");

			} catch (NoSuchElementException e) {
				String itemNameFromNo = Utils.getItemNameFromNo(typeID);
				System.err.println(typeID + ": " + itemNameFromNo + " NO SUCH ELEMENT");
			}

		};
	}
}
