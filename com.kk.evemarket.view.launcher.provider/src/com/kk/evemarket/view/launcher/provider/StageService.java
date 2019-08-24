package com.kk.evemarket.view.launcher.provider;

import com.kk.evemarket.view.api.IStageService;

import javafx.stage.Stage;

public class StageService implements IStageService {

	private Stage stage;

	public StageService(Stage primaryStage) {
		stage = primaryStage;
	}

	@Override
	public Stage getStage() {
		System.out.println("StageService.getStage()");
		return stage;
	}

}
