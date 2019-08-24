package com.kk.evemarket.viewpartprovider.toolbar;

import java.io.IOException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.kk.evemarket.view.api.IViewPart;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;

//@Designate(ocd = ToolbarProvider.Config.class, factory = true)
@Component(name = "ToolbarProvider", immediate = true)
public class ToolbarProvider implements IViewPart {
	private String name;
	public static BundleContext bundleContext;

	@ObjectClassDefinition
	@interface Config {
		String name() default "Part2";
	}

	@Reference
	public void setEventAdmin(EventAdmin service) {
		ToolbarEventHandler.setEventHandler(service);
	}

	@Activate
	void activate(Config config) {
		System.out.println("ToolbarProvider.activate()");
		this.name = config.name();

		bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
	}

	@Deactivate
	void deactivate() {
	}

	@Override
	public Node getViewPart() {

		Node viewPart = new Button("2");

		// viewPart = new Toolbar();
		try {
			viewPart = FXMLLoader.load(getClass().getResource("Toolbar.fxml"));
			// ToolbarController controller = new ToolbarController();

			// controller.getBtnStart().setText("Start");
			// controller.getTxtFeeTax().setText("hjgadkhsa");
			//
			// controller.getLblFeeTax().setText(String.valueOf(123));

			System.out.println("ToolbarProvider.getViewPart()");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return viewPart;
	}

	@Override
	public String getName() {
		return "Toolbar";
	}
}
