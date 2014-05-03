package org.primitive.webdriverencapsulations.inheritors;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.webdriver.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;



public final class InternetExplorerDriverEncapsulation extends WebDriverEncapsulation  {
	
	public InternetExplorerDriverEncapsulation(InternetExplorerDriverService service)	{
		createWebDriver(ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(), new Class[] {InternetExplorerDriverService.class}, new Object[] {service});
	}
	
	public InternetExplorerDriverEncapsulation(InternetExplorerDriverService service, Configuration config)	{
		createWebDriver(ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(), new Class[] {InternetExplorerDriverService.class}, new Object[] {service});
		resetAccordingTo(config);
	}
	
	public InternetExplorerDriverEncapsulation(InternetExplorerDriverService service, Capabilities capabilities)	{
		createWebDriver(ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(), new Class[] {InternetExplorerDriverService.class, Capabilities.class}, new Object[] {service, capabilities});
	}
	
	public InternetExplorerDriverEncapsulation(InternetExplorerDriverService service, Capabilities capabilities, Configuration config)	{
		createWebDriver(ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(), new Class[] {InternetExplorerDriverService.class, Capabilities.class}, new Object[] {service, capabilities});
		resetAccordingTo(config);
	}
	
	public InternetExplorerDriverEncapsulation(int port)	{
		ESupportedDrivers.INTERNETEXPLORER.setSystemProperty(Configuration.byDefault);
		createWebDriver(ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(), new Class[] {int.class}, new Object[] {port}); 
	}	
	
	public InternetExplorerDriverEncapsulation(int port, Configuration config)	{
		ESupportedDrivers.INTERNETEXPLORER.setSystemProperty(config);
		createWebDriver(ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(), new Class[] {int.class}, new Object[] {port});
		resetAccordingTo(config);
	}	
}
