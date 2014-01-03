package org.primitive.webdriverencapsulations.inheritors;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;



public final class FirefoxDriverEncapsulation extends WebDriverEncapsulation{	
			
	public FirefoxDriverEncapsulation(FirefoxProfile profile)
	{
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(), new Class<?>[] {FirefoxProfile.class}, new Object[] {profile});
	}
	
	public FirefoxDriverEncapsulation(Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(), new Class<?>[] {Capabilities.class, Capabilities.class}, new Object[] {desiredCapabilities, requiredCapabilities});
	}
	
	public FirefoxDriverEncapsulation(FirefoxBinary binary, FirefoxProfile profile)
	{
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(), new Class<?>[] {FirefoxBinary.class, FirefoxProfile.class}, new Object[] {binary, profile});
	}
	
	public FirefoxDriverEncapsulation(FirefoxBinary binary, FirefoxProfile profile, Capabilities capabilities)
	{
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(), new Class<?>[] {FirefoxBinary.class, FirefoxProfile.class, Capabilities.class}, new Object[] {binary, profile, capabilities});
	}
	
	public FirefoxDriverEncapsulation(FirefoxBinary binary, FirefoxProfile profile, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(), new Class<?>[] {FirefoxBinary.class, FirefoxProfile.class, Capabilities.class, Capabilities.class}, new Object[] {binary, profile, desiredCapabilities, requiredCapabilities});	
	}	
}
