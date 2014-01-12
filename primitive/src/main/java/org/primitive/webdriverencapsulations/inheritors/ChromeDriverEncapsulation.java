package org.primitive.webdriverencapsulations.inheritors;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

public final class ChromeDriverEncapsulation extends WebDriverEncapsulation 
{			
	
	public ChromeDriverEncapsulation(ChromeDriverService service)	{
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeDriverService.class}, new Object[] {service});
	}
	
	public ChromeDriverEncapsulation(ChromeDriverService service, Capabilities capabilities)	{
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeDriverService.class, Capabilities.class}, new Object[] {service, capabilities});
	}
	
	public ChromeDriverEncapsulation(ChromeOptions options)	{
		ESupportedDrivers.CHROME.setSystemProperty();
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeOptions.class}, new Object[] {options}); 
	}
	
	
	public ChromeDriverEncapsulation(ChromeDriverService service, ChromeOptions options)	{
		createWebDriver(ESupportedDrivers.CHROME.getUsingWebDriverClass(), new Class<?> [] {ChromeDriverService.class, ChromeOptions.class}, new Object[] {service, options});
	}
}