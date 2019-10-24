package com.kk.evemarket.viewpartprovider.progress;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kk.evemarket.common.events.EveMarketEventConstants;
import com.kk.evemarket.common.events.ModelProgress;
import com.kk.evemarket.common.events.ModelState;
import com.kk.evemarket.view.api.IViewPart;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

//@Designate(ocd = ProgressProvider.Config.class, factory = true)
@Component(name = "ProgressProvider", property = {
		EventConstants.EVENT_TOPIC + "=" + EveMarketEventConstants.TOPIC_MODEL_PROGRESS }, immediate = true)
public class ProgressProvider implements IViewPart, EventHandler {
	private String name;
	private ModelState modelState = ModelState.Initial;
	private ProgressIndicator progressIndicator;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@ObjectClassDefinition
	@interface Config {
		String name() default "Part3";
	}

	@Reference
	public void setEventHandler(EventHandler eventHandler) {
		log.info("ProgressProvider.setEventHandler()");
	}

	@Activate
	void activate(Config config) {
		log.info("ProgressProvider.activate()");
		this.name = config.name();
	}

	@Deactivate
	void deactivate() {
	}

	@Override
	public Node getViewPart() {

		HBox hbox = new HBox();

		ProgressBar progressBar = new ProgressBar();
		progressIndicator = new ProgressIndicator();
		progressIndicator.setVisible(false);

		progressBar.setMaxWidth(Double.MAX_VALUE);
		progressBar.setMaxHeight(Double.MAX_VALUE);
		progressBar.setPrefWidth(120);
		progressBar.setProgress(0);

		hbox.getChildren().add(progressBar);
		hbox.getChildren().add(progressIndicator);
		hbox.setPrefHeight(40D);
		hbox.setPrefWidth(150);

		HBox.setHgrow(progressIndicator, Priority.NEVER);
		HBox.setHgrow(progressBar, Priority.ALWAYS);

		return hbox;
	}

	@Override
	public String getName() {
		return "Progress";
	}

	@Override
	public void handleEvent(Event event) {

		String topic = event.getTopic();
		System.out.println("ProgressProvider.handleEvent():" + topic);
		Object property = null;
		switch (topic) {
		case EveMarketEventConstants.TOPIC_MODEL_PROGRESS:
			property = event.getProperty(EveMarketEventConstants.MODEL_PROGRESS);
			ModelProgress progress = (ModelProgress) property;
			ModelState modelState = progress.getModelState();

			Platform.runLater(() -> {

				switch (modelState) {
				case Initial:
				case NotInitialized:
				case Stopped:
					progressIndicator.setVisible(false);
					break;
				case Running:
					progressIndicator.setVisible(true);
					progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
					break;

				default:
					break;
				}
			});
			break;

		default:
			break;
		}
	}

}
