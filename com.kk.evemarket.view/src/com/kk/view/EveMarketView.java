package com.kk.view;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.kk.evemarket.view.api.IEveMarketView;
import com.kk.evemarket.view.api.IStageService;
import com.kk.evemarket.view.api.IViewPart;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class EveMarketView implements IEveMarketView {
	private String name;
	private IStageService stageService;
	private BorderPane root = new BorderPane();
	// private Parent root = new FlowPane();
	private HBox topBox;
	private HBox bottomBox;
	private VBox centerBox;

	private boolean guiInitialized;

	@Reference
	public void setStageService(IStageService service) {

		stageService = service;
	}

	@Reference(unbind = "removePart", cardinality = ReferenceCardinality.AT_LEAST_ONE)
	// public void addPart(ServiceReference sr, IViewPart provider) {
	public void addPart(IViewPart provider) {

		System.out.println("EveMarketView.addPart(): " + provider.getName() + " added");

		if (!guiInitialized)
			guiInitialized = initGui();

		Node viewPart = provider.getViewPart();

		if (provider.getName().equals("Table")) {

			centerBox.getChildren().add(viewPart);
			VBox.setVgrow(viewPart, Priority.ALWAYS);
		} else if (provider.getName().equals("Toolbar")) {

			topBox.getChildren().add(viewPart);
			HBox.setHgrow(viewPart, Priority.ALWAYS);

		} else if (provider.getName().equals("Progress")) {
			bottomBox.getChildren().add(viewPart);
			HBox.setHgrow(viewPart, Priority.ALWAYS);
		}

	}

	public EveMarketView() {
		System.out.println("EveMarketView.EveMarketView()");
	}

	@ObjectClassDefinition
	@interface Config {
		String name() default "World";
	}

	@Activate
	void activate(Config config) {
		this.name = config.name();

	}

	@Override
	public Parent getView() {
		System.out.println("EveMarketView.getView()");

		if (!guiInitialized)
			guiInitialized = initGui();

		// Button button = new Button("sdfghjklş");
		// button.setMaxWidth(Double.MAX_VALUE);
		// bottomBox.getChildren().add(button);
		// bottomBox.setStyle("-fx-background-color: green;");
		// HBox.setHgrow(button, Priority.ALWAYS);

		return root;
	}

	@Override
	public void showWindow() {
		Platform.runLater(() -> {
			Stage primaryStage = stageService.getStage();
			primaryStage.setTitle("Hello World");
			root.setStyle("-fx-background-color: green;");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			primaryStage.toFront();
		});
	}

	private boolean initGui() {
		centerBox = new VBox();
		centerBox.setAlignment(Pos.CENTER);
		root.setCenter(centerBox);
		bottomBox = new HBox();
		bottomBox.setAlignment(Pos.CENTER);
		root.setBottom(bottomBox);
		topBox = new HBox();
		topBox.setAlignment(Pos.CENTER);
		root.setTop(topBox);

		return true;
	}
}
