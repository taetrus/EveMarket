package sample.provider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import sample.api.ISample;

@Component
public class Sample implements ISample {

	@Override
	public void sample() {
		System.out.println("Sample.sample()");
	}

	@Activate
	public void start() {
		System.out.println("Sample.start()");
	}

}
