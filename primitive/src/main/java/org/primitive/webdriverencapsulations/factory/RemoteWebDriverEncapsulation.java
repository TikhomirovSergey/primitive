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
import org.primitive.webdriverencapsulations.systemproperties.ELocalServices;


public class RemoteWebDriverEncapsulation extends WebDriverEncapsulation {
	
	//remote server that is started on localhost
	private static SeleniumServer server;	
	private static Class<? extends WebDriver> remoteDriver = RemoteWebDriver.class;
	public RemoteWebDriverEncapsulation(Capabilities capabilities) {
		super(Configuration.byDefault);
		prepare(); //starts server locally
		initForLocalWorking(capabilities);
		constructBodyInGeneral(remoteDriver, capabilities);
	}
	
	public RemoteWebDriverEncapsulation(Configuration configuration,
			Capabilities capabilities) {
		super(configuration);
		prepare(); //starts server locally
		initForLocalWorking(capabilities);
		constructBodyInGeneral(remoteDriver, capabilities);
	}
	
	private void constructBody(Capabilities capabilities, URL remoteAddress)
	{
		createWebDriver(remoteDriver, new Class[] {URL.class, Capabilities.class}, new Object[] {remoteAddress, capabilities});
	}
	
	private void constructBody(Capabilities capabilities, CommandExecutor executor)
	{
		prepare(); //starts server locally
		initForLocalWorking(capabilities);
		createWebDriver(remoteDriver, new Class[] {CommandExecutor.class, Capabilities.class}, new Object[] {executor, capabilities});
	}	
	
	private void constructBody(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  CommandExecutor executor)
	{
		prepare(); //starts server locally
		createWebDriver(remoteDriver, new Class[] {CommandExecutor.class, Capabilities.class, Capabilities.class},
				new Object[] {executor, desiredCapabilities, requiredCapabilities});
	}	
	
	private void constructBody(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  URL remoteAddress)
	{
		createWebDriver(remoteDriver, new Class[] {URL.class, Capabilities.class, Capabilities.class},
				new Object[] {remoteAddress, desiredCapabilities, requiredCapabilities});
	}	
	
	public RemoteWebDriverEncapsulation(Capabilities capabilities, URL remoteAddress) 
	{
		super(Configuration.byDefault);
		constructBody(capabilities, remoteAddress);
	}
	
	public RemoteWebDriverEncapsulation(Configuration configuration, Capabilities capabilities, URL remoteAddress) 
	{
		super(configuration);
		constructBody(capabilities, remoteAddress);
	}
	
	public RemoteWebDriverEncapsulation(Capabilities capabilities, CommandExecutor executor) 
	{
		super(Configuration.byDefault);
		constructBody(capabilities, executor);
		
	}	
	
	public RemoteWebDriverEncapsulation(Configuration configuration, 
			Capabilities capabilities, CommandExecutor executor) 
	{
		super(configuration);
		constructBody(capabilities, executor);
		
	}
	
	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  CommandExecutor executor) 
	{
		super(Configuration.byDefault);
		constructBody(desiredCapabilities, requiredCapabilities, executor);
	}
	
	public RemoteWebDriverEncapsulation(Configuration configuration, Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  CommandExecutor executor) 
	{
		super(configuration);
		constructBody(desiredCapabilities, requiredCapabilities, executor);
	}
	
	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  URL remoteAddress) 
	{
		super(Configuration.byDefault);
		constructBody(desiredCapabilities, requiredCapabilities, remoteAddress);		
	}
	
	public RemoteWebDriverEncapsulation(Configuration configuration, Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  URL remoteAddress) 
	{
		super(configuration);
		constructBody(desiredCapabilities, requiredCapabilities, remoteAddress);		
	}	

	
	private void initForLocalWorking(Capabilities capabilities)
	{
		//if proxy server need to be run
		String brofserName = capabilities.getBrowserName();

		if (brofserName.equals(DesiredCapabilities.chrome().getBrowserName()))
		{
			ELocalServices.CHROMESERVICE.setSystemProperty();
		}
		if (brofserName.equals(DesiredCapabilities.internetExplorer().getBrowserName()))
		{
			ELocalServices.IEXPLORERSERVICE.setSystemProperty();
		}
		if (brofserName.equals(DesiredCapabilities.phantomjs().getBrowserName()))
		{
			ELocalServices.PHANTOMJSSERVICE.setSystemProperty();
		}
	}

	private synchronized static void startServerLocally()
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
		private void prepare()
		{
			startServerLocally();
		}
}
