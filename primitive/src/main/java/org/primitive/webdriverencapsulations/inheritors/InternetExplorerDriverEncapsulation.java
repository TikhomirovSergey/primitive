package org.primitive.webdriverencapsulations.inheritors;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;



public class InternetExplorerDriverEncapsulation extends WebDriverEncapsulation 
{
	private void constructBody(InternetExplorerDriverService service)
	{
		createWebDriver(ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(), new Class[] {InternetExplorerDriverService.class}, new Object[] {service});
	}
	
	private void constructBody(InternetExplorerDriverService service, Capabilities capabilities)
	{
		createWebDriver(ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(), new Class[] {InternetExplorerDriverService.class, Capabilities.class}, new Object[] {service, capabilities});
	}
	
	private void constructBody(int port)
	{
		ESupportedDrivers.INTERNETEXPLORER.setSystemProperty();
		createWebDriver(ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(), new Class[] {Integer.class}, new Object[] {port});
	}	
	
	public InternetExplorerDriverEncapsulation(InternetExplorerDriverService service)
	{
		constructBody(service);
	}
	
	public InternetExplorerDriverEncapsulation(Configuration configuration, InternetExplorerDriverService service)
	{
		this.configuration = configuration;
		constructBody(service);
	}
	
	public InternetExplorerDriverEncapsulation(InternetExplorerDriverService service, Capabilities capabilities)
	{
		constructBody(service, capabilities);
	}
	
	public InternetExplorerDriverEncapsulation(Configuration configuration, InternetExplorerDriverService service, Capabilities capabilities)
	{
		this.configuration = configuration;
		constructBody(service, capabilities);
	}	
	
	public InternetExplorerDriverEncapsulation(int port)
	{
		constructBody(port);
	}
	
	public InternetExplorerDriverEncapsulation(Configuration configuration, int port)
	{
		this.configuration = configuration;
		constructBody(port);
	}
}
