package sample.application;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	Logger log = LoggerFactory.getLogger(this.getClass());

//	@Reference
//	public void setSample(ISample service) {
//		log.info("SampleApplication.setSample()");
//		sample = service;
//	}

//	@Reference
//	public void setCm(ConfigurationAdmin service) {
//		log.info("SampleApplication.setCm()");
//
//		try {
//
//			Configuration configuration = service.getConfiguration("org.osgi.service.log.admin");
//
//			Configuration createFactoryConfiguration = service.createFactoryConfiguration("sample.application");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

//	@Reference(service=LoggerFactory.class)
//	public void setLogger(LoggerFactory service) {
//		log.info("SampleApplication.setLogger(): " + service.toString());
//
//		service.getLogger(this.getClass()).error("KK Deneme");
//
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

		log.info("KKKKK Denemem");
		log.info("SampleApplication.start()");
//
//		sample.sample();

		log.info("EveMarket.start()");
		Platform.runLater(() -> {
			log.info("EveMarketView.activate2()");
			Stage primaryStage = stageService.getStage();
			primaryStage.setTitle("Hello World");
			Parent view = viewService.getView();
			// Scene scene = new Scene(view, 800, 600);

			Scene scene = new Scene(view, 800, 600);

			primaryStage.setScene(scene);

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent we) {
					log.info("Stage is closing");
					System.exit(0);
				}
			});

			primaryStage.show();
			primaryStage.toFront();
		});
	}

}
