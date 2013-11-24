package org.primitive.webdriverencapsulations.factory.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.primitive.configuration.Configuration;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.exe.ExeProperties;



public class ChromeDriverEncapsulation extends WebDriverEncapsulation 
{	
	private static final Class<? extends WebDriver> chromeDriver = ChromeDriver.class; 
	
	public ChromeDriverEncapsulation()
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral(chromeDriver);
	}
	
	public ChromeDriverEncapsulation(Configuration configuration)
	{
		super(configuration);
		prepare();
		constructBodyInGeneral(chromeDriver);
	}	
	
	public ChromeDriverEncapsulation(Capabilities capabilities)
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral(chromeDriver, capabilities);
	}
	
	public ChromeDriverEncapsulation(Configuration configuration, Capabilities capabilities)
	{
		super(configuration);
		prepare();
		constructBodyInGeneral(chromeDriver, capabilities);
	}	
	
	private void constructBody(ChromeDriverService service)
	{
		prepare();
		createWebDriver(chromeDriver, new Class<?> [] {ChromeDriverService.class}, new Object[] {service});
	}
	
	private void constructBody(ChromeDriverService service, Capabilities capabilities)
	{
		prepare();
		createWebDriver(chromeDriver, new Class<?> [] {ChromeDriverService.class, Capabilities.class}, new Object[] {service, capabilities});
	}	
	
	private void constructBody(ChromeOptions options)
	{
		prepare();
		createWebDriver(chromeDriver, new Class<?> [] {ChromeOptions.class}, new Object[] {options}); 
	}
	
	private void constructBody(ChromeDriverService service, ChromeOptions options)
	{
		prepare();
		createWebDriver(chromeDriver, new Class<?> [] {ChromeDriverService.class, ChromeOptions.class}, new Object[] {service, options});
	}	
	
	public ChromeDriverEncapsulation(ChromeDriverService service)
	{
		super(Configuration.byDefault);
		constructBody(service);
	}
	
	public ChromeDriverEncapsulation(Configuration configuration, ChromeDriverService service)
	{
		super(configuration);
		constructBody(service);
	}	
	
	public ChromeDriverEncapsulation(ChromeDriverService service, Capabilities capabilities)
	{
		super(Configuration.byDefault);
		constructBody(service, capabilities);
	}
	
	public ChromeDriverEncapsulation(Configuration configuration, ChromeDriverService service, Capabilities capabilities)
	{
		super(configuration);
		constructBody(service, capabilities);
	}
	
	public ChromeDriverEncapsulation(ChromeOptions options)
	{
		super(Configuration.byDefault);
		constructBody(options);
	}
	
	
	public ChromeDriverEncapsulation(ChromeDriverService service, ChromeOptions options)
	{
		super(Configuration.byDefault);
		constructBody(service, options);
	}

	@Override
	protected void prepare()
	{
		setSystemPropertyLocally(ExeProperties.FORCHROME.getProperty(), configuration.getChromeDriverSettings(), ExeProperties.FORCHROME.getDefaultPropertyValue());		
	}
}