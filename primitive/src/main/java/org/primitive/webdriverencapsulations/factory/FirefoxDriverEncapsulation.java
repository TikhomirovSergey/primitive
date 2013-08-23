package org.primitive.webdriverencapsulations.factory;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.primitive.configuration.Configuration;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;



public class FirefoxDriverEncapsulation extends WebDriverEncapsulation{
	private static final Class<? extends WebDriver> fireFoxDriver = FirefoxDriver.class;
	
	public FirefoxDriverEncapsulation(String openingURL)
	{
		super(Configuration.byDefault);
		constructBodyInGeneral(openingURL, fireFoxDriver);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, String openingURL)
	{
		super(configuration);
		constructBodyInGeneral(openingURL, fireFoxDriver);
	}	
	
	public FirefoxDriverEncapsulation(String openingURL, Capabilities capabilities)
	{
		super(Configuration.byDefault);
		constructBodyInGeneral(openingURL, fireFoxDriver, capabilities);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, String openingURL, Capabilities capabilities)
	{
		super(configuration);
		constructBodyInGeneral(openingURL, fireFoxDriver, capabilities);
	}

	private void constructBody(String openingURL, FirefoxProfile profile)
	{
		createWebDriver(openingURL, fireFoxDriver, new Class<?>[] {FirefoxProfile.class}, new Object[] {profile});
	}
	
	private void constructBody(String openingURL, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		createWebDriver(openingURL, fireFoxDriver, new Class<?>[] {Capabilities.class, Capabilities.class}, new Object[] {desiredCapabilities, requiredCapabilities});
	}
	
	private void constructBody(String openingURL, FirefoxBinary binary, FirefoxProfile profile)
	{	
		createWebDriver(openingURL, fireFoxDriver, new Class<?>[] {FirefoxBinary.class, FirefoxProfile.class}, new Object[] {binary, profile});
	}	
	
	private void constructBody(String openingURL, FirefoxBinary binary, FirefoxProfile profile, Capabilities capabilities)
	{
		createWebDriver(openingURL, fireFoxDriver, new Class<?>[] {FirefoxBinary.class, FirefoxProfile.class, Capabilities.class}, new Object[] {binary, profile, capabilities});		
	}
	
	private void constructBody(String openingURL, FirefoxBinary binary, FirefoxProfile profile, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		createWebDriver(openingURL, fireFoxDriver, new Class<?>[] {FirefoxBinary.class, FirefoxProfile.class, Capabilities.class, Capabilities.class}, new Object[] {binary, profile, desiredCapabilities, requiredCapabilities});	
	}
	
	public FirefoxDriverEncapsulation(String URL, FirefoxProfile profile)
	{
		super(Configuration.byDefault);
		constructBody(URL, profile);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, String URL, FirefoxProfile profile)
	{
		super(configuration);
		constructBody(URL, profile);
	}	
	
	public FirefoxDriverEncapsulation(String URL, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		super(Configuration.byDefault);
		constructBody(URL, desiredCapabilities, requiredCapabilities);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, String URL, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		super(configuration);
		constructBody(URL, desiredCapabilities, requiredCapabilities);
	}	
		
	public FirefoxDriverEncapsulation(String URL, FirefoxBinary binary, FirefoxProfile profile)
	{
		super(Configuration.byDefault);
		constructBody(URL, binary, profile);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, String URL, FirefoxBinary binary, FirefoxProfile profile)
	{
		super(configuration);
		constructBody(URL, binary, profile);
	}
	
	public FirefoxDriverEncapsulation(String URL, FirefoxBinary binary, FirefoxProfile profile, Capabilities capabilities)
	{
		super(Configuration.byDefault);
		constructBody(URL, binary, profile, capabilities);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, String URL, FirefoxBinary binary, FirefoxProfile profile, Capabilities capabilities)
	{
		super(configuration);
		constructBody(URL, binary, profile, capabilities);
	}
	
	public FirefoxDriverEncapsulation(String URL, FirefoxBinary binary, FirefoxProfile profile, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		super(Configuration.byDefault);
		constructBody(URL, binary, profile, desiredCapabilities, requiredCapabilities);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, String URL, FirefoxBinary binary, FirefoxProfile profile, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		super(configuration);
		constructBody(URL, binary, profile, desiredCapabilities, requiredCapabilities);
	}

	@Override
	@Deprecated
	protected void prepare(){
		Log.debug("There is nothing to prepare for " + fireFoxDriver.getSimpleName());
	}	
}
