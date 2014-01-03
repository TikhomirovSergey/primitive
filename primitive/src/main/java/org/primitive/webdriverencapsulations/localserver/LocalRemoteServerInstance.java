package org.primitive.webdriverencapsulations.localserver;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import org.primitive.logging.Log;

/**
 * @author s.tihomirov
 * it contains instance of locally working remote server
 */
public class LocalRemoteServerInstance {

	//remote server that is started on localhost
	private static final SeleniumServer server = getServerLocally();
	private static synchronized SeleniumServer getServerLocally()
	{
		  SeleniumServer localServer = server;
		  if (localServer==null)
		  {	
			  
			  RemoteControlConfiguration rcc = null;
			  int port = RemoteControlConfiguration.DEFAULT_PORT;
			  boolean slowResources = false;
			  rcc = new RemoteControlConfiguration();			  
			  rcc.setPort(port);
			  
		      try 
		      {
		    	 localServer = new SeleniumServer(slowResources, rcc);
		      } 
		      catch (Exception e) 
		      {
		    	  throw new WebDriverException("Cann't start server on localhost!",e);
		      }
		  }
		  return localServer;
	}	
	
	public static synchronized void startLocally()
	{
		if (!server.getServer().isStarted())
		{
			try 
			{
		   	  server.start();
			} 
			catch (Exception e) 
			{
				throw new WebDriverException("Cann't start server on localhost!",e);
			}
		}
		Log.message("Remote server has been started.");
	}
	
	
	public synchronized static SeleniumServer getLocalServer()
	{
		return server;
	}
}
