package com.kk.evemarket.model.api;

import org.osgi.annotation.versioning.ProviderType;

import com.kk.evemarket.common.trade.Trade;
import com.kk.evemarket.common.trade.TradeParameters;

import javafx.collections.ObservableList;

/**
 * This is an example OSGi enRoute bundle that has a component that implements
 * an API.
 */

@ProviderType
public interface IEveMarketModel {

	// void findTrades();

	ObservableList<Trade> getModel();

	void findTrades(TradeParameters parameters);

}
