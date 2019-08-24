package com.kk.evemarket.view.api;

import org.osgi.annotation.versioning.ProviderType;

import javafx.stage.Stage;

@ProviderType
public interface IStageService {
	Stage getStage();
}
