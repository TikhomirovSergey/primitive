package org.primitive.webdriverencapsulations.inheritors;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CommandExecutor;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.primitive.webdriverencapsulations.localserver.LocalRemoteServerInstance;

public final class RemoteWebDriverEncapsulation extends WebDriverEncapsulation {
	
	public RemoteWebDriverEncapsulation(Capabilities capabilities, CommandExecutor executor) 	{
		LocalRemoteServerInstance.startLocally();
		setSystemProprtyByCapabilities(capabilities);
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(), new Class[] {CommandExecutor.class, Capabilities.class}, new Object[] {executor, capabilities});	
	}	
	
	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  CommandExecutor executor) 	{
		LocalRemoteServerInstance.startLocally();
		setSystemProprtyByCapabilities(desiredCapabilities);
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(), new Class[] {CommandExecutor.class, Capabilities.class, Capabilities.class},
				new Object[] {executor, desiredCapabilities, requiredCapabilities});
	}
	
	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  URL remoteAddress) 	{
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(), new Class[] {URL.class, Capabilities.class, Capabilities.class},
				new Object[] {remoteAddress, desiredCapabilities, requiredCapabilities});
	}	
}
