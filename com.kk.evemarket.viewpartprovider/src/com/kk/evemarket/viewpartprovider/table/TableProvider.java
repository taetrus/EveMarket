package com.kk.evemarket.viewpartprovider.table;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.kk.evemarket.model.api.IEveMarketModel;
import com.kk.evemarket.view.api.IViewPart;

import javafx.scene.Node;

@Component(name = "TableProvider")
public class TableProvider implements IViewPart {
	private String name;
	private IEveMarketModel model;

	@ObjectClassDefinition
	@interface Config {
		String name() default "Part1";
	}

	@Reference
	public void setModel(IEveMarketModel service) {
		model = service;
	}

	@Reference
	public void setEventHandler(EventAdmin service) {
		TableEventHandler.setEventAdmin(service);
	}

	@Activate
	void activate(Config config) {
		System.out.println("ViewPart.activate()");
		this.name = config.name();
	}

	@Deactivate
	void deactivate() {
	}

	@Override
	public Node getViewPart() {
		TableViewExpandable helloTableRowExpander = new TableViewExpandable(model.getModel());
		return helloTableRowExpander.getTable();
		// return vbox;
	}

	@Override
	public String getName() {
		return "Table";
	}

}
