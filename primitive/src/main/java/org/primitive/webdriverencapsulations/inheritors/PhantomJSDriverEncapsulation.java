package org.primitive.webdriverencapsulations.inheritors;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.service.DriverService;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.webdriver.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

public final class PhantomJSDriverEncapsulation extends WebDriverEncapsulation {
	public PhantomJSDriverEncapsulation(DriverService service,
			Capabilities desiredCapabilities) {
		createWebDriver(ESupportedDrivers.PHANTOMJS.getUsingWebDriverClass(),
				new Class<?>[] { DriverService.class, Capabilities.class },
				new Object[] { service, desiredCapabilities });
	}

	public PhantomJSDriverEncapsulation(DriverService service,
			Capabilities desiredCapabilities, Configuration config) {
		createWebDriver(ESupportedDrivers.PHANTOMJS.getUsingWebDriverClass(),
				new Class<?>[] { DriverService.class, Capabilities.class },
				new Object[] { service, desiredCapabilities });
		resetAccordingTo(config);
	}
}
