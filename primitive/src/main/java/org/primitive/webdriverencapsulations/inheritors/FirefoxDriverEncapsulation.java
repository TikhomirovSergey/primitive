package org.primitive.webdriverencapsulations.inheritors;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;



public class FirefoxDriverEncapsulation extends WebDriverEncapsulation{
	private void constructBody(FirefoxProfile profile)
	{
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(), new Class<?>[] {FirefoxProfile.class}, new Object[] {profile});
	}
	
	private void constructBody(Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(), new Class<?>[] {Capabilities.class, Capabilities.class}, new Object[] {desiredCapabilities, requiredCapabilities});
	}
	
	private void constructBody(FirefoxBinary binary, FirefoxProfile profile)
	{	
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(), new Class<?>[] {FirefoxBinary.class, FirefoxProfile.class}, new Object[] {binary, profile});
	}	
	
	private void constructBody(FirefoxBinary binary, FirefoxProfile profile, Capabilities capabilities)
	{
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(), new Class<?>[] {FirefoxBinary.class, FirefoxProfile.class, Capabilities.class}, new Object[] {binary, profile, capabilities});		
	}
	
	private void constructBody(FirefoxBinary binary, FirefoxProfile profile, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(), new Class<?>[] {FirefoxBinary.class, FirefoxProfile.class, Capabilities.class, Capabilities.class}, new Object[] {binary, profile, desiredCapabilities, requiredCapabilities});	
	}
	
	public FirefoxDriverEncapsulation(FirefoxProfile profile)
	{
		constructBody(profile);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, FirefoxProfile profile)
	{
		this.configuration = configuration;
		constructBody(profile);
	}	
	
	public FirefoxDriverEncapsulation(Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		constructBody(desiredCapabilities, requiredCapabilities);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		this.configuration = configuration;
		constructBody(desiredCapabilities, requiredCapabilities);
	}	
		
	public FirefoxDriverEncapsulation(FirefoxBinary binary, FirefoxProfile profile)
	{
		constructBody(binary, profile);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, FirefoxBinary binary, FirefoxProfile profile)
	{
		this.configuration = configuration;
		constructBody(binary, profile);
	}
	
	public FirefoxDriverEncapsulation(FirefoxBinary binary, FirefoxProfile profile, Capabilities capabilities)
	{
		constructBody(binary, profile, capabilities);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, FirefoxBinary binary, FirefoxProfile profile, Capabilities capabilities)
	{
		this.configuration = configuration;
		constructBody(binary, profile, capabilities);
	}
	
	public FirefoxDriverEncapsulation(FirefoxBinary binary, FirefoxProfile profile, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		constructBody(binary, profile, desiredCapabilities, requiredCapabilities);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, FirefoxBinary binary, FirefoxProfile profile, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		this.configuration = configuration;
		constructBody(binary, profile, desiredCapabilities, requiredCapabilities);
	}	
}
