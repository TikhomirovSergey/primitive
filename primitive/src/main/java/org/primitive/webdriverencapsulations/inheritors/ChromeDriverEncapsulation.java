package org.primitive.webdriverencapsulations.inheritors;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.webdriver.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

public final class ChromeDriverEncapsulation extends WebDriverEncapsulation 
{			
	
	public ChromeDriverEncapsulation(ChromeDriverService service)	{
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeDriverService.class}, new Object[] {service});
	}
	
	public ChromeDriverEncapsulation(ChromeDriverService service, Configuration config)	{
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeDriverService.class}, new Object[] {service});
		resetAccordingTo(config);
	}
	
	public ChromeDriverEncapsulation(ChromeDriverService service, Capabilities capabilities)	{
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeDriverService.class, Capabilities.class}, new Object[] {service, capabilities});
	}
	
	public ChromeDriverEncapsulation(ChromeDriverService service, Capabilities capabilities, Configuration config)	{
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeDriverService.class, Capabilities.class}, new Object[] {service, capabilities});
		resetAccordingTo(config);
	}
	
	public ChromeDriverEncapsulation(ChromeOptions options)	{
		ESupportedDrivers.CHROME.setSystemProperty(Configuration.byDefault);
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeOptions.class}, new Object[] {options}); 
	}
	
	public ChromeDriverEncapsulation(ChromeOptions options, Configuration config)	{
		ESupportedDrivers.CHROME.setSystemProperty(config);
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeOptions.class}, new Object[] {options}); 
	}
	
	
	public ChromeDriverEncapsulation(ChromeDriverService service, ChromeOptions options)	{
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeDriverService.class, ChromeOptions.class}, new Object[] {service, options});
	}
	
	public ChromeDriverEncapsulation(ChromeDriverService service, ChromeOptions options, Configuration config)	{
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeDriverService.class, ChromeOptions.class}, new Object[] {service, options});
		resetAccordingTo(config);
	}
}