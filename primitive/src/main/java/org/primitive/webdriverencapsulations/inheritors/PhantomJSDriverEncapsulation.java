package org.primitive.webdriverencapsulations.inheritors;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.service.DriverService;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

public class PhantomJSDriverEncapsulation extends WebDriverEncapsulation {
	public PhantomJSDriverEncapsulation(Configuration configuration, DriverService service, Capabilities desiredCapabilities)
	{
		this.configuration = configuration;
		constructBody(service, desiredCapabilities);
	}

	public PhantomJSDriverEncapsulation(DriverService service, Capabilities desiredCapabilities)
	{
		constructBody(service, desiredCapabilities);
	}
	
	private void constructBody(DriverService service, Capabilities desiredCapabilities)
	{
		createWebDriver(ESupportedDrivers.PHANTOMJS.getUsingWebDriverClass(), new Class<?> [] {DriverService.class, Capabilities.class}, new Object[] {service, desiredCapabilities});
	}
}
