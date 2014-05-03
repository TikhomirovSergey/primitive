package org.primitive.webdriverencapsulations.inheritors;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CommandExecutor;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.webdriver.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.primitive.webdriverencapsulations.localserver.LocalRemoteServerInstance;

public final class RemoteWebDriverEncapsulation extends WebDriverEncapsulation {
	
	public RemoteWebDriverEncapsulation(Capabilities capabilities, CommandExecutor executor) 	{
		LocalRemoteServerInstance.startLocally();
		setSystemProprtyByCapabilities(capabilities, Configuration.byDefault);
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(), new Class[] {CommandExecutor.class, Capabilities.class}, new Object[] {executor, capabilities});	
	}
	
	public RemoteWebDriverEncapsulation(Capabilities capabilities, CommandExecutor executor, Configuration config) 	{
		LocalRemoteServerInstance.startLocally();
		setSystemProprtyByCapabilities(capabilities, config);
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(), new Class[] {CommandExecutor.class, Capabilities.class}, new Object[] {executor, capabilities});	
		resetAccordingTo(config);
	}
	
	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  CommandExecutor executor) 	{
		LocalRemoteServerInstance.startLocally();
		setSystemProprtyByCapabilities(desiredCapabilities, Configuration.byDefault);
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(), new Class[] {CommandExecutor.class, Capabilities.class, Capabilities.class},
				new Object[] {executor, desiredCapabilities, requiredCapabilities});
	}
	
	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  CommandExecutor executor, Configuration config) 	{
		LocalRemoteServerInstance.startLocally();
		setSystemProprtyByCapabilities(desiredCapabilities, config);
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(), new Class[] {CommandExecutor.class, Capabilities.class, Capabilities.class},
				new Object[] {executor, desiredCapabilities, requiredCapabilities});
		resetAccordingTo(config);
	}
	
	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  URL remoteAddress) 	{
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(), new Class[] {URL.class, Capabilities.class, Capabilities.class},
				new Object[] {remoteAddress, desiredCapabilities, requiredCapabilities});
	}	
	
	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities, 
			Capabilities requiredCapabilities,  URL remoteAddress, Configuration config) 	{
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(), new Class[] {URL.class, Capabilities.class, Capabilities.class},
				new Object[] {remoteAddress, desiredCapabilities, requiredCapabilities});
		resetAccordingTo(config);
	}	
}
