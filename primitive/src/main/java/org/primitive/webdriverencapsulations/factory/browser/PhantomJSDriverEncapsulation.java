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
	
	public PhantomJSDriverEncapsulation(Configuration configuration, String openingURL)
	{
		super(configuration);
		prepare();
		constructBodyInGeneral(openingURL, phantomJSDriver);
	}

	public PhantomJSDriverEncapsulation(String openingURL)
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral(openingURL, phantomJSDriver);
	}

	public PhantomJSDriverEncapsulation(Configuration configuration, String openingURL, Capabilities capabilities)
	{
		super(configuration);
		prepare();
		constructBodyInGeneral(openingURL, phantomJSDriver, capabilities);
	}

	public PhantomJSDriverEncapsulation(String openingURL, Capabilities capabilities)
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral(openingURL, phantomJSDriver, capabilities);
	}

	public PhantomJSDriverEncapsulation(Configuration configuration, String URL, DriverService service, Capabilities desiredCapabilities)
	{
		super(configuration);
		constructBody(URL, service, desiredCapabilities);
	}

	public PhantomJSDriverEncapsulation(String URL, ChromeDriverService service, Capabilities desiredCapabilities)
	{
		super(Configuration.byDefault);
		constructBody(URL, service, desiredCapabilities);
	}

	@Override
	protected void prepare() {
		setSystemPropertyLocally(ExeProperties.FORPHANTOMJS.getProperty(), configuration.getPhantomJSDriverSettings(), ExeProperties.FORPHANTOMJS.getDefaultPropertyValue());		
	}
	
	private void constructBody(String openingURL, DriverService service, Capabilities desiredCapabilities)
	{
		prepare();
		createWebDriver(openingURL, phantomJSDriver, new Class<?> [] {DriverService.class, Capabilities.class}, new Object[] {service, desiredCapabilities});
	}

}
