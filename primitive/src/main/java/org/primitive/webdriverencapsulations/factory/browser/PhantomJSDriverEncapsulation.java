package org.primitive.webdriverencapsulations.factory.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.service.DriverService;
import org.primitive.configuration.Configuration;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.exe.ExeProperties;

public class PhantomJSDriverEncapsulation extends WebDriverEncapsulation {

	private static final Class<? extends WebDriver> phantomJSDriver = PhantomJSDriver.class; 
	
	public PhantomJSDriverEncapsulation(Configuration configuration)
	{
		super(configuration);
		prepare();
		constructBodyInGeneral(phantomJSDriver);
	}

	public PhantomJSDriverEncapsulation()
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral( phantomJSDriver);
	}

	public PhantomJSDriverEncapsulation(Configuration configuration, Capabilities capabilities)
	{
		super(configuration);
		prepare();
		constructBodyInGeneral(phantomJSDriver, capabilities);
	}

	public PhantomJSDriverEncapsulation(Capabilities capabilities)
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral(phantomJSDriver, capabilities);
	}

	public PhantomJSDriverEncapsulation(Configuration configuration, DriverService service, Capabilities desiredCapabilities)
	{
		super(configuration);
		constructBody(service, desiredCapabilities);
	}

	public PhantomJSDriverEncapsulation(ChromeDriverService service, Capabilities desiredCapabilities)
	{
		super(Configuration.byDefault);
		constructBody(service, desiredCapabilities);
	}

	@Override
	protected void prepare() {
		setSystemPropertyLocally(ExeProperties.FORPHANTOMJS.getProperty(), configuration.getPhantomJSDriverSettings(), ExeProperties.FORPHANTOMJS.getDefaultPropertyValue());		
	}
	
	private void constructBody(DriverService service, Capabilities desiredCapabilities)
	{
		prepare();
		createWebDriver(phantomJSDriver, new Class<?> [] {DriverService.class, Capabilities.class}, new Object[] {service, desiredCapabilities});
	}

}
