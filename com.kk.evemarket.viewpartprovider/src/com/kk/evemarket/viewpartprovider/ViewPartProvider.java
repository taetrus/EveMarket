package com.kk.evemarket.viewpartprovider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kk.evemarket.view.api.IViewPart;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

@Component
public class ViewPartProvider implements IViewPart {
	private static Logger LOGGER = LoggerFactory.getLogger("ViewPartProvider");

	@Activate
	public void start() {
		LOGGER.info("start()");
	}

	@Override
	public Node getViewPart() {
		return new HBox();
	}

	@Override
	public String getName() {
		return "dddd";
	}

}
