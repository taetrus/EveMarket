package com.kk.evemarket.view.launcher.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kk.evemarket.view.api.IStageService;

import javafx.stage.Stage;

public class StageService implements IStageService {
	private static Logger LOGGER = LoggerFactory.getLogger("StageService");

	private Stage stage;

	public StageService(Stage primaryStage) {
		stage = primaryStage;
	}

	@Override
	public Stage getStage() {
		LOGGER.info("StageService.getStage()");
		return stage;
	}

}
