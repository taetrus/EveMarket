package com.kk.evemarket.view.launcher.provider;

import java.util.concurrent.Executors;

import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kk.evemarket.view.api.IStageService;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

@Designate(ocd = FxLauncher.Config.class, factory = false)
@Component
public class FxLauncher extends Application {
	private static Logger LOGGER = LoggerFactory.getLogger("FxLauncher");
	private String name;
	private IStageService stageService;

	@ObjectClassDefinition
	@interface Config {
		String name() default "World";
	}

	@Activate
	void activate(Config config) {
		this.name = config.name();

		LOGGER.info("App.activate()");

		Executors.defaultThreadFactory().newThread(() -> {
			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
			launch();
		}).start();
	}

	@Deactivate
	void deactivate() {
		Platform.exit();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		LOGGER.info("App.start()");

		BundleContext bc = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		DependencyManager dependencyManager = new DependencyManager(bc);
		dependencyManager.add(dependencyManager.createComponent().setInterface(IStageService.class.getName(), null)
				.setImplementation(new StageService(primaryStage)));
	}

}
