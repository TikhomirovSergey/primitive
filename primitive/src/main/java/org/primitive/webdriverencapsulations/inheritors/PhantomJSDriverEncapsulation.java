package org.primitive.webdriverencapsulations.inheritors;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.service.DriverService;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

public final class PhantomJSDriverEncapsulation extends WebDriverEncapsulation {
	public PhantomJSDriverEncapsulation(DriverService service, Capabilities desiredCapabilities)	{
		createWebDriver(ESupportedDrivers.PHANTOMJS.getUsingWebDriverClass(), new Class<?> [] {DriverService.class, Capabilities.class}, new Object[] {service, desiredCapabilities});
	}
}
