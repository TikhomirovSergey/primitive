package org.primitive.webdriverencapsulations.factory.browser;

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
	
	public FirefoxDriverEncapsulation()
	{
		super(Configuration.byDefault);
		constructBodyInGeneral(fireFoxDriver);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration)
	{
		super(configuration);
		constructBodyInGeneral(fireFoxDriver);
	}	
	
	public FirefoxDriverEncapsulation(Capabilities capabilities)
	{
		super(Configuration.byDefault);
		constructBodyInGeneral(fireFoxDriver, capabilities);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, Capabilities capabilities)
	{
		super(configuration);
		constructBodyInGeneral(fireFoxDriver, capabilities);
	}

	private void constructBody(FirefoxProfile profile)
	{
		createWebDriver(fireFoxDriver, new Class<?>[] {FirefoxProfile.class}, new Object[] {profile});
	}
	
	private void constructBody(Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		createWebDriver(fireFoxDriver, new Class<?>[] {Capabilities.class, Capabilities.class}, new Object[] {desiredCapabilities, requiredCapabilities});
	}
	
	private void constructBody(FirefoxBinary binary, FirefoxProfile profile)
	{	
		createWebDriver(fireFoxDriver, new Class<?>[] {FirefoxBinary.class, FirefoxProfile.class}, new Object[] {binary, profile});
	}	
	
	private void constructBody(FirefoxBinary binary, FirefoxProfile profile, Capabilities capabilities)
	{
		createWebDriver(fireFoxDriver, new Class<?>[] {FirefoxBinary.class, FirefoxProfile.class, Capabilities.class}, new Object[] {binary, profile, capabilities});		
	}
	
	private void constructBody(FirefoxBinary binary, FirefoxProfile profile, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		createWebDriver(fireFoxDriver, new Class<?>[] {FirefoxBinary.class, FirefoxProfile.class, Capabilities.class, Capabilities.class}, new Object[] {binary, profile, desiredCapabilities, requiredCapabilities});	
	}
	
	public FirefoxDriverEncapsulation(FirefoxProfile profile)
	{
		super(Configuration.byDefault);
		constructBody(profile);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, FirefoxProfile profile)
	{
		super(configuration);
		constructBody(profile);
	}	
	
	public FirefoxDriverEncapsulation(Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		super(Configuration.byDefault);
		constructBody(desiredCapabilities, requiredCapabilities);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		super(configuration);
		constructBody(desiredCapabilities, requiredCapabilities);
	}	
		
	public FirefoxDriverEncapsulation(FirefoxBinary binary, FirefoxProfile profile)
	{
		super(Configuration.byDefault);
		constructBody(binary, profile);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, FirefoxBinary binary, FirefoxProfile profile)
	{
		super(configuration);
		constructBody(binary, profile);
	}
	
	public FirefoxDriverEncapsulation(FirefoxBinary binary, FirefoxProfile profile, Capabilities capabilities)
	{
		super(Configuration.byDefault);
		constructBody(binary, profile, capabilities);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, FirefoxBinary binary, FirefoxProfile profile, Capabilities capabilities)
	{
		super(configuration);
		constructBody(binary, profile, capabilities);
	}
	
	public FirefoxDriverEncapsulation(FirefoxBinary binary, FirefoxProfile profile, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		super(Configuration.byDefault);
		constructBody(binary, profile, desiredCapabilities, requiredCapabilities);
	}
	
	public FirefoxDriverEncapsulation(Configuration configuration, FirefoxBinary binary, FirefoxProfile profile, Capabilities desiredCapabilities, Capabilities requiredCapabilities)
	{
		super(configuration);
		constructBody(binary, profile, desiredCapabilities, requiredCapabilities);
	}

	@Override
	@Deprecated
	protected void prepare(){
		Log.debug("There is nothing to prepare for " + fireFoxDriver.getSimpleName());
	}	
}
