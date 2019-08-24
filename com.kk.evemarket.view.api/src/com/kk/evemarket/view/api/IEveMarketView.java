package com.kk.evemarket.view.api;

import org.osgi.annotation.versioning.ProviderType;

import javafx.scene.Parent;

/**
 * This is an example OSGi enRoute bundle that has a component that implements
 * an API.
 */

@ProviderType
public interface IEveMarketView {

	Parent getView();

	void showWindow();

}
