package org.primitive.webdriverencapsulations.inheritors;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;



public final class InternetExplorerDriverEncapsulation extends WebDriverEncapsulation  {
	
	public InternetExplorerDriverEncapsulation(InternetExplorerDriverService service)	{
		createWebDriver(ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(), new Class[] {InternetExplorerDriverService.class}, new Object[] {service});
	}
	
	public InternetExplorerDriverEncapsulation(InternetExplorerDriverService service, Capabilities capabilities)	{
		createWebDriver(ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(), new Class[] {InternetExplorerDriverService.class, Capabilities.class}, new Object[] {service, capabilities});
	}
	
	public InternetExplorerDriverEncapsulation(int port)	{
		ESupportedDrivers.INTERNETEXPLORER.setSystemProperty();
		createWebDriver(ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(), new Class[] {int.class}, new Object[] {port});
	}	
}
