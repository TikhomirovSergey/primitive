package org.primitive.webdriverencapsulations.inheritors;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

public class ChromeDriverEncapsulation extends WebDriverEncapsulation 
{	
	private void constructBody(ChromeDriverService service)
	{
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeDriverService.class}, new Object[] {service});
	}
	
	private void constructBody(ChromeDriverService service, Capabilities capabilities)
	{
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeDriverService.class, Capabilities.class}, new Object[] {service, capabilities});
	}	
	
	private void constructBody(ChromeOptions options)
	{
		ESupportedDrivers.CHROME.setSystemProperty();
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeOptions.class}, new Object[] {options}); 
	}
	
	private void constructBody(ChromeDriverService service, ChromeOptions options)
	{
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeDriverService.class, ChromeOptions.class}, new Object[] {service, options});
	}	
	
	public ChromeDriverEncapsulation(ChromeDriverService service)
	{
		constructBody(service);
	}
	
	public ChromeDriverEncapsulation(Configuration configuration, ChromeDriverService service)
	{
		this.configuration = configuration;
		constructBody(service);
	}	
	
	public ChromeDriverEncapsulation(ChromeDriverService service, Capabilities capabilities)
	{
		constructBody(service, capabilities);
	}
	
	public ChromeDriverEncapsulation(Configuration configuration, ChromeDriverService service, Capabilities capabilities)
	{
		this.configuration = configuration;
		constructBody(service, capabilities);
	}
	
	public ChromeDriverEncapsulation(Configuration configuration, ChromeOptions options)
	{
		this.configuration = configuration;
		constructBody(options);
	}
	
	public ChromeDriverEncapsulation(ChromeOptions options)
	{
		constructBody(options);
	}
	
	
	public ChromeDriverEncapsulation(ChromeDriverService service, ChromeOptions options)
	{
		constructBody(service, options);
	}
	
	public ChromeDriverEncapsulation(Configuration configuration, ChromeDriverService service, ChromeOptions options)
	{
		this.configuration = configuration;
		constructBody(service, options);
	}
}