package org.primitive.webdriverencapsulations.inheritors;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CommandExecutor;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.primitive.webdriverencapsulations.localserver.LocalRemoteServerInstance;

public class RemoteWebDriverEncapsulation extends WebDriverEncapsulation {
	
	private void constructBody(Capabilities capabilities, URL remoteAddress)
	{
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(), new Class[] {URL.class, Capabilities.class}, new Object[] {remoteAddress, capabilities});
	}
	
	private void constructBody(Capabilities capabilities, CommandExecutor executor)
	{
		LocalRemoteServerInstance.startLocally();
		setSystemProprtyByCapabilities(capabilities);
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(), new Class[] {CommandExecutor.class, Capabilities.class}, new Object[] {executor, capabilities});
	}	
	
	private void constructBody(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  CommandExecutor executor)
	{
		LocalRemoteServerInstance.startLocally();
		setSystemProprtyByCapabilities(desiredCapabilities);
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(), new Class[] {CommandExecutor.class, Capabilities.class, Capabilities.class},
				new Object[] {executor, desiredCapabilities, requiredCapabilities});
	}	
	
	private void constructBody(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  URL remoteAddress)
	{
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(), new Class[] {URL.class, Capabilities.class, Capabilities.class},
				new Object[] {remoteAddress, desiredCapabilities, requiredCapabilities});
	}	
	
	public RemoteWebDriverEncapsulation(Capabilities capabilities, URL remoteAddress) 
	{
		constructBody(capabilities, remoteAddress);
	}
	
	public RemoteWebDriverEncapsulation(Capabilities capabilities, CommandExecutor executor) 
	{
		constructBody(capabilities, executor);
		
	}	
	
	public RemoteWebDriverEncapsulation(Configuration configuration, 
			Capabilities capabilities, CommandExecutor executor) 
	{
		this.configuration = configuration;
		constructBody(capabilities, executor);
		
	}
	
	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  CommandExecutor executor) 
	{
		constructBody(desiredCapabilities, requiredCapabilities, executor);
	}
	
	public RemoteWebDriverEncapsulation(Configuration configuration, Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  CommandExecutor executor) 
	{
		this.configuration = configuration;
		constructBody(desiredCapabilities, requiredCapabilities, executor);
	}
	
	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  URL remoteAddress) 
	{
		constructBody(desiredCapabilities, requiredCapabilities, remoteAddress);		
	}
	
	public RemoteWebDriverEncapsulation(Configuration configuration, Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  URL remoteAddress) 
	{
		this.configuration = configuration;
		constructBody(desiredCapabilities, requiredCapabilities, remoteAddress);		
	}	
}
