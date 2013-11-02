package org.primitive.webdriverencapsulations.factory;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.primitive.configuration.Configuration;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

public class AndroidDriverEncapsulation extends WebDriverEncapsulation {
	private static final Class<? extends WebDriver> androidDriver = AndroidDriver.class; 
		
	public AndroidDriverEncapsulation(Configuration configuration, String openingURL)
	{
		super(configuration);
		constructBodyInGeneral(openingURL, androidDriver);
	}
	
	public AndroidDriverEncapsulation(String openingURL)
	{
		super(Configuration.byDefault);
		constructBodyInGeneral(openingURL, androidDriver);
	}
	
	public AndroidDriverEncapsulation(Configuration configuration, String openingURL, DesiredCapabilities capabilities)
	{
		super(configuration);
		constructBodyInGeneral(openingURL, androidDriver, capabilities);
	}
	
	public AndroidDriverEncapsulation(String openingURL, DesiredCapabilities capabilities)
	{
		super(Configuration.byDefault);
		constructBodyInGeneral(openingURL, androidDriver, capabilities);
	}
	
	private void constructBody(String openingURL, String remoteAddress)
	{
		createWebDriver(openingURL, androidDriver, new Class[] {String.class}, new Object[] {remoteAddress});
	}
	
	public AndroidDriverEncapsulation(Configuration configuration, String openingURL, String remoteAddress)
	{
		super(configuration);
		constructBody(openingURL, remoteAddress);
	}
	
	public AndroidDriverEncapsulation(String openingURL, String remoteAddress)
	{
		super(Configuration.byDefault);
		constructBody(openingURL, remoteAddress);
	}
	
	private void constructBody(String openingURL, URL remoteAddress)
	{
		createWebDriver(openingURL, androidDriver, new Class[] {URL.class}, new Object[] {remoteAddress});
	}
	
	public AndroidDriverEncapsulation(Configuration configuration, String openingURL, URL remoteAddress)
	{
		super(configuration);
		constructBody(openingURL, remoteAddress);
	}
	
	
	public AndroidDriverEncapsulation(String openingURL, URL remoteAddress)
	{
		super(Configuration.byDefault);
		constructBody(openingURL, remoteAddress);
	}
	
	private void constructBody(String openingURL, URL remoteAddress, DesiredCapabilities capabilities)
	{
		createWebDriver(openingURL, androidDriver, new Class[] {URL.class, DesiredCapabilities.class}, new Object[] {remoteAddress,capabilities});
	}
	
	public AndroidDriverEncapsulation(Configuration configuration, String openingURL, URL remoteAddress, DesiredCapabilities capabilities)
	{
		super(configuration);
		constructBody(openingURL, remoteAddress, capabilities);
	}
	
	
	public AndroidDriverEncapsulation(String openingURL, URL remoteAddress, DesiredCapabilities capabilities)
	{
		super(Configuration.byDefault);
		constructBody(openingURL, remoteAddress, capabilities);
	}	
	
	
	@Override
	@Deprecated
	protected void prepare() {
		Log.debug("There is nothing to prepare for " + androidDriver.getSimpleName());
	}

}
