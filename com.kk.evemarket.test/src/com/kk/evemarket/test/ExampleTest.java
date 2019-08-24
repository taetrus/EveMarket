package com.kk.evemarket.test;

import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import com.kk.evemarket.view.api.IEveMarketView;
import com.kk.evemarket.view.api.IStageService;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 */

public class ExampleTest {

	private final BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();

	/*
	 * 
	 */
	// @Test
	// public void testAuth() throws Exception {
	//
	// System.out.println("ExampleTest.testAuth()");
	// IAuthenticator service = getService(IAuthenticator.class);
	//
	// service.authenticate(new IAuthenticatedCallback() {
	//
	// @Override
	// public void authenticated(String token) {
	// System.out.println("Token:" + token);
	// Assert.assertNotNull(token);
	// }
	// });
	// Thread.sleep(5000);
	//
	// System.out.println("EveMarketTest.testAuthentication(): END");
	// }

	@Test
	public void testView() throws Exception {

		System.out.println("ExampleTest.testView()");
		IEveMarketView viewService = getService(IEveMarketView.class);
		IStageService stageService = getService(IStageService.class);

//		PaxLoggingService service = getService(PaxLoggingService.class);
//		Bundle bundle = FrameworkUtil.getBundle(IEveMarketView.class);
//		PaxLogger logger = service.getLogger(bundle, "", "");
//		logger.inform("sdskjdksjhdjksh", null);

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

		// viewService.showWindow();

		Thread.sleep(500000);
		Assert.assertNotNull("");
	}

	<T> T getService(Class<T> clazz) throws InterruptedException {
		ServiceTracker<T, T> st = new ServiceTracker<>(context, clazz, null);
		st.open();
		return st.waitForService(1000);
	}
}
