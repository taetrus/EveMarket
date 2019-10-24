package sample.provider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sample.api.ISample;

@Component
public class Sample implements ISample {
	private static Logger LOGGER = LoggerFactory.getLogger("Sample");

	@Override
	public void sample() {
		LOGGER.info("Sample.sample()");
	}

	@Activate
	public void start() {
		LOGGER.info("Sample.start()");
	}

}
