package org.primitive.webdriverencapsulations.factory;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import org.primitive.configuration.Configuration;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;


public class RemoteWebDriverEncapsulation extends WebDriverEncapsulation {
	
	//remote server that is started on localhost
	private static SeleniumServer server;
	
	private static String ieProperty = "webdriver.ie.driver"; 
	private static String chromeProperty = "webdriver.chrome.driver";
	private static Class<? extends WebDriver> remoteDriver = RemoteWebDriver.class;
	
	private String defaultIEDriver 	  = "IEDriverServer.exe"; 
	private String defaultChromeDriver = "chromedriver.exe"; 

	
	public RemoteWebDriverEncapsulation(String openingURL,
			Capabilities capabilities) {
		super(Configuration.byDefault);
		prepare(); //starts server locally
		initForLocalWorking(capabilities);
		constructBodyInGeneral(openingURL, remoteDriver, capabilities);
	}
	
	public RemoteWebDriverEncapsulation(Configuration configuration, String openingURL,
			Capabilities capabilities) {
		super(configuration);
		prepare(); //starts server locally
		initForLocalWorking(capabilities);
		constructBodyInGeneral(openingURL, remoteDriver, capabilities);
	}
	
	private void constructBody(String openingURL,
			Capabilities capabilities, URL remoteAddress)
	{
		createWebDriver(openingURL, remoteDriver, new Class[] {URL.class, Capabilities.class}, new Object[] {remoteAddress, capabilities});
	}
	
	private void constructBody(String openingURL,
			Capabilities capabilities, CommandExecutor executor)
	{
		prepare(); //starts server locally
		initForLocalWorking(capabilities);
		createWebDriver(openingURL, remoteDriver, new Class[] {CommandExecutor.class, Capabilities.class}, new Object[] {executor, capabilities});
	}	
	
	private void constructBody(String openingURL,
			Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  CommandExecutor executor)
	{
		prepare(); //starts server locally
		createWebDriver(openingURL, remoteDriver, new Class[] {CommandExecutor.class, Capabilities.class, Capabilities.class},
				new Object[] {executor, desiredCapabilities, requiredCapabilities});
	}	
	
	private void constructBody(String openingURL,
			Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  URL remoteAddress)
	{
		createWebDriver(openingURL, remoteDriver, new Class[] {URL.class, Capabilities.class, Capabilities.class},
				new Object[] {remoteAddress, desiredCapabilities, requiredCapabilities});
	}	
	
	public RemoteWebDriverEncapsulation(String URL,
			Capabilities capabilities, URL remoteAddress) 
	{
		super(Configuration.byDefault);
		constructBody(URL, capabilities, remoteAddress);
	}
	
	public RemoteWebDriverEncapsulation(Configuration configuration, String URL,
			Capabilities capabilities, URL remoteAddress) 
	{
		super(configuration);
		constructBody(URL, capabilities, remoteAddress);
	}
	
	public RemoteWebDriverEncapsulation(String URL,
			Capabilities capabilities, CommandExecutor executor) 
	{
		super(Configuration.byDefault);
		constructBody(URL, capabilities, executor);
		
	}	
	
	public RemoteWebDriverEncapsulation(Configuration configuration, String URL,
			Capabilities capabilities, CommandExecutor executor) 
	{
		super(configuration);
		constructBody(URL, capabilities, executor);
		
	}
	
	public RemoteWebDriverEncapsulation(String URL,
			Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  CommandExecutor executor) 
	{
		super(Configuration.byDefault);
		constructBody(URL, desiredCapabilities, requiredCapabilities, executor);
	}
	
	public RemoteWebDriverEncapsulation(Configuration configuration, String URL,
			Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  CommandExecutor executor) 
	{
		super(configuration);
		constructBody(URL, desiredCapabilities, requiredCapabilities, executor);
	}
	
	public RemoteWebDriverEncapsulation(String URL,
			Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  URL remoteAddress) 
	{
		super(Configuration.byDefault);
		constructBody(URL, desiredCapabilities, requiredCapabilities, remoteAddress);		
	}
	
	public RemoteWebDriverEncapsulation(Configuration configuration, String URL,
			Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  URL remoteAddress) 
	{
		super(configuration);
		constructBody(URL, desiredCapabilities, requiredCapabilities, remoteAddress);		
	}	

	
	private void initForLocalWorking(Capabilities capabilities)
	{
		//if proxy server need to be run
		String brofserName = capabilities.getBrowserName();

		if (brofserName.equals(DesiredCapabilities.chrome().getBrowserName()))
		{
			setSystemPropertyLocally(chromeProperty, configuration.getChromeDriverSettings(), defaultChromeDriver);
		}
		if (brofserName.equals(DesiredCapabilities.internetExplorer().getBrowserName()))
		{
			setSystemPropertyLocally(ieProperty, configuration.getIEDriverSettings(), defaultIEDriver);
		}			
	}

	private static void startServerLocally()
	{
		  if (server==null)
		  {	
			  
			  RemoteControlConfiguration rcc = null;
			  int port = RemoteControlConfiguration.DEFAULT_PORT;
			  boolean slowResources = false;
			  rcc = new RemoteControlConfiguration();			  
			  rcc.setPort(port);
			  
		      try 
		      {
		    	  server = new SeleniumServer(slowResources, rcc);
		      } 
		      catch (Exception e) 
		      {
		    	  throw new WebDriverException("Cann't start server on localhost!",e);
		      }
		      try 
		      {
		    	  server.start();
		      } 
		      catch (Exception e) 
		      {
		    	  throw new WebDriverException("Cann't start server on localhost!",e);
		      }
		      Log.message("Remote server has been started.");
		  }
		}
	
		public synchronized static SeleniumServer getActiveServer()
		{
			return(server);
		}
		
		public synchronized static void shutDownServer()
		{
			if (server!=null)
			{
				server.stop();
			}
		}
		
		//for starting server locally only
		protected void prepare()
		{
			startServerLocally();
		}
}
