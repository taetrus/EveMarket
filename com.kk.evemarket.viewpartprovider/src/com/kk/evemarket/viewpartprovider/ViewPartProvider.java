package com.kk.evemarket.viewpartprovider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.kk.evemarket.view.api.IViewPart;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

@Component
public class ViewPartProvider implements IViewPart {

	@Activate
	public void start() {
		System.out.println("Example.start()");
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
