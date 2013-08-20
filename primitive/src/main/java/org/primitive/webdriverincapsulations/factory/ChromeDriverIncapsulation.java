package org.primitive.webdriverincapsulations.factory;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.primitive.configuration.Configuration;
import org.primitive.webdriverincapsulations.WebDriverIncapsulation;



public class ChromeDriverIncapsulation extends WebDriverIncapsulation 
{	
	private final String defaultDriver = "chromedriver.exe";  
	private final static String property = "webdriver.chrome.driver"; 
	private static final Class<? extends WebDriver> chromeDriver = ChromeDriver.class; 
	
	public ChromeDriverIncapsulation(String openingURL)
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral(openingURL, chromeDriver);
	}
	
	public ChromeDriverIncapsulation(Configuration configuration, String openingURL)
	{
		super(configuration);
		prepare();
		constructBodyInGeneral(openingURL, chromeDriver);
	}	
	
	public ChromeDriverIncapsulation(String openingURL, Capabilities capabilities)
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral(openingURL, chromeDriver, capabilities);
	}
	
	public ChromeDriverIncapsulation(Configuration configuration, String openingURL, Capabilities capabilities)
	{
		super(configuration);
		prepare();
		constructBodyInGeneral(openingURL, chromeDriver, capabilities);
	}	
	
	private void constructBody(String openingURL, ChromeDriverService service)
	{
		prepare();
		createWebDriver(openingURL, chromeDriver, new Class<?> [] {ChromeDriverService.class}, new Object[] {service});
	}
	
	private void constructBody(String openingURL, ChromeDriverService service, Capabilities capabilities)
	{
		prepare();
		createWebDriver(openingURL, chromeDriver, new Class<?> [] {ChromeDriverService.class, Capabilities.class}, new Object[] {service, capabilities});
	}	
	
	private void constructBody(String openingURL, ChromeOptions options)
	{
		prepare();
		createWebDriver(openingURL, chromeDriver, new Class<?> [] {ChromeOptions.class}, new Object[] {options}); 
	}
	
	private void constructBody(String openingURL, ChromeDriverService service, ChromeOptions options)
	{
		prepare();
		createWebDriver(openingURL, chromeDriver, new Class<?> [] {ChromeDriverService.class, ChromeOptions.class}, new Object[] {service, options});
	}	
	
	public ChromeDriverIncapsulation(String URL, ChromeDriverService service)
	{
		super(Configuration.byDefault);
		constructBody(URL, service);
	}
	
	public ChromeDriverIncapsulation(Configuration configuration, String URL, ChromeDriverService service)
	{
		super(configuration);
		constructBody(URL, service);
	}	
	
	public ChromeDriverIncapsulation(String URL, ChromeDriverService service, Capabilities capabilities)
	{
		super(Configuration.byDefault);
		constructBody(URL, service, capabilities);
	}
	
	public ChromeDriverIncapsulation(Configuration configuration, String URL, ChromeDriverService service, Capabilities capabilities)
	{
		super(configuration);
		constructBody(URL, service, capabilities);
	}
	
	public ChromeDriverIncapsulation(String URL, ChromeOptions options)
	{
		super(Configuration.byDefault);
		constructBody(URL, options);
	}
	
	
	public ChromeDriverIncapsulation(String URL, ChromeDriverService service, ChromeOptions options)
	{
		super(Configuration.byDefault);
		constructBody(URL, service, options);
	}

	@Override
	protected void prepare()
	{
		setSystemPropertyLocally(property, configuration.getChromeDriverSettings(), defaultDriver);		
	}
}