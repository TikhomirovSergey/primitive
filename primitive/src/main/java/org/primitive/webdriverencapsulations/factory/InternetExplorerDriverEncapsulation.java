package org.primitive.webdriverencapsulations.factory;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.primitive.configuration.Configuration;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;



public class InternetExplorerDriverEncapsulation extends WebDriverEncapsulation 
{
	private String defaultDriver = "IEDriverServer.exe";  	
	private static String property = "webdriver.ie.driver";
	private static final Class<? extends WebDriver> internetExplorerDriver = InternetExplorerDriver.class;
	
	public InternetExplorerDriverEncapsulation(String openingURL) 
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral(openingURL,internetExplorerDriver);
	}
	
	public InternetExplorerDriverEncapsulation(Configuration configuration, String openingURL) 
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral(openingURL, internetExplorerDriver);
	}	
	
	public InternetExplorerDriverEncapsulation(String openingURL, Capabilities capabilities) 
	{
		super(Configuration.byDefault);
		prepare();
		constructBodyInGeneral(openingURL, internetExplorerDriver, capabilities);
	}
	
	public InternetExplorerDriverEncapsulation(Configuration configuration, String openingURL, Capabilities capabilities) 
	{
		super(configuration);
		prepare();
		constructBodyInGeneral(openingURL, internetExplorerDriver, capabilities);
	}
	
	private void constructBody(String openingURL, InternetExplorerDriverService service)
	{
		prepare();
		createWebDriver(openingURL, internetExplorerDriver, new Class[] {InternetExplorerDriverService.class}, new Object[] {service});
	}
	
	private void constructBody(String openingURL, InternetExplorerDriverService service, Capabilities capabilities)
	{
		prepare();
		createWebDriver(openingURL, internetExplorerDriver, new Class[] {InternetExplorerDriverService.class, Capabilities.class}, new Object[] {service, capabilities});
	}
	
	private void constructBody(String openingURL, int port)
	{
		prepare();
		createWebDriver(openingURL, internetExplorerDriver, new Class[] {Integer.class}, new Object[] {port});
	}	
	
	public InternetExplorerDriverEncapsulation(String URL, InternetExplorerDriverService service)
	{
		super(Configuration.byDefault);
		constructBody(URL, service);
	}
	
	public InternetExplorerDriverEncapsulation(Configuration configuration, String URL, InternetExplorerDriverService service)
	{
		super(configuration);
		constructBody(URL, service);
	}
	
	public InternetExplorerDriverEncapsulation(String URL, InternetExplorerDriverService service, Capabilities capabilities)
	{
		super(Configuration.byDefault);
		constructBody(URL, service, capabilities);
	}
	
	public InternetExplorerDriverEncapsulation(Configuration configuration, String URL, InternetExplorerDriverService service, Capabilities capabilities)
	{
		super(configuration);
		constructBody(URL, service, capabilities);
	}	
	
	public InternetExplorerDriverEncapsulation(String URL, int port)
	{
		super(Configuration.byDefault);
		constructBody(URL, port);
	}
	
	public InternetExplorerDriverEncapsulation(Configuration configuration, String URL, int port)
	{
		super(configuration);
		constructBody(URL, port);
	}

	@Override
	protected void prepare() {
		setSystemPropertyLocally(property, configuration.getIEDriverSettings(), defaultDriver);		
	}
}
