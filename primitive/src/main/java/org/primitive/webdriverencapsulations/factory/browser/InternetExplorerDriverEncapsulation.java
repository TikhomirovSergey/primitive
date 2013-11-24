package org.primitive.webdriverencapsulations.factory.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.primitive.configuration.Configuration;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.exe.ExeProperties;



public class InternetExplorerDriverEncapsulation extends WebDriverEncapsulation 
{
	private static final Class<? extends WebDriver> internetExplorerDriver = InternetExplorerDriver.class;
	
	public InternetExplorerDriverEncapsulation() 
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral(internetExplorerDriver);
	}
	
	public InternetExplorerDriverEncapsulation(Configuration configuration) 
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral(internetExplorerDriver);
	}	
	
	public InternetExplorerDriverEncapsulation(Capabilities capabilities) 
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral(internetExplorerDriver, capabilities);
	}
	
	public InternetExplorerDriverEncapsulation(Configuration configuration, Capabilities capabilities) 
	{
		super(configuration);
		prepare();
		constructBodyInGeneral(internetExplorerDriver, capabilities);
	}
	
	private void constructBody(InternetExplorerDriverService service)
	{
		prepare();
		createWebDriver(internetExplorerDriver, new Class[] {InternetExplorerDriverService.class}, new Object[] {service});
	}
	
	private void constructBody(InternetExplorerDriverService service, Capabilities capabilities)
	{
		prepare();
		createWebDriver(internetExplorerDriver, new Class[] {InternetExplorerDriverService.class, Capabilities.class}, new Object[] {service, capabilities});
	}
	
	private void constructBody(int port)
	{
		prepare();
		createWebDriver(internetExplorerDriver, new Class[] {Integer.class}, new Object[] {port});
	}	
	
	public InternetExplorerDriverEncapsulation(InternetExplorerDriverService service)
	{
		super(Configuration.byDefault);
		constructBody(service);
	}
	
	public InternetExplorerDriverEncapsulation(Configuration configuration, InternetExplorerDriverService service)
	{
		super(configuration);
		constructBody(service);
	}
	
	public InternetExplorerDriverEncapsulation(InternetExplorerDriverService service, Capabilities capabilities)
	{
		super(Configuration.byDefault);
		constructBody(service, capabilities);
	}
	
	public InternetExplorerDriverEncapsulation(Configuration configuration, InternetExplorerDriverService service, Capabilities capabilities)
	{
		super(configuration);
		constructBody(service, capabilities);
	}	
	
	public InternetExplorerDriverEncapsulation(int port)
	{
		super(Configuration.byDefault);
		constructBody(port);
	}
	
	public InternetExplorerDriverEncapsulation(Configuration configuration, int port)
	{
		super(configuration);
		constructBody(port);
	}

	@Override
	protected void prepare() {
		setSystemPropertyLocally(ExeProperties.FORIEXPLORER.getProperty(), configuration.getIEDriverSettings(), ExeProperties.FORIEXPLORER.getDefaultPropertyValue());		
	}
}
