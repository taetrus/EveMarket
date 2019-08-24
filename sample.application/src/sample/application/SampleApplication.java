package sample.application;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.kk.evemarket.view.api.IEveMarketView;
import com.kk.evemarket.view.api.IStageService;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@Component
public class SampleApplication {

//	private ISample sample;
	private IEveMarketView viewService;
	private IStageService stageService;

//	@Reference
//	public void setSample(ISample service) {
//		System.out.println("SampleApplication.setSample()");
//		sample = service;
//	}

	@Reference
	public void setView(IEveMarketView service) {
		viewService = service;
	}

	@Reference
	public void setStage(IStageService stage) {
		stageService = stage;
	}

	@Activate
	public void start() {
		System.out.println("SampleApplication.start()");
//
//		sample.sample();

		System.out.println("EveMarket.start()");
		Platform.runLater(() -> {
			System.out.println("EveMarketView.activate2()");
			Stage primaryStage = stageService.getStage();
			primaryStage.setTitle("Hello World");
			Parent view = viewService.getView();
			// Scene scene = new Scene(view, 800, 600);

			Scene scene = new Scene(view, 800, 600);

			primaryStage.setScene(scene);

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent we) {
					System.out.println("Stage is closing");
					System.exit(0);
				}
			});

			primaryStage.show();
			primaryStage.toFront();
		});
	}

}
