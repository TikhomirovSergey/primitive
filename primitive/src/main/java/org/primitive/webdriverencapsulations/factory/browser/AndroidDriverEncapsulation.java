package org.primitive.webdriverencapsulations.factory.browser;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.primitive.configuration.Configuration;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

public class AndroidDriverEncapsulation extends WebDriverEncapsulation {
	private static final Class<? extends WebDriver> androidDriver = AndroidDriver.class; 
		
	public AndroidDriverEncapsulation(Configuration configuration)
	{
		super(configuration);
		constructBodyInGeneral(androidDriver);
	}
	
	public AndroidDriverEncapsulation()
	{
		super(Configuration.byDefault);
		constructBodyInGeneral(androidDriver);
	}
	
	public AndroidDriverEncapsulation(Configuration configuration, Capabilities capabilities)
	{
		super(configuration);
		DesiredCapabilities desiredCapabilities = (DesiredCapabilities) capabilities;
		constructBodyInGeneral(androidDriver, desiredCapabilities);
	}
	
	public AndroidDriverEncapsulation(Capabilities capabilities)
	{
		super(Configuration.byDefault);
		DesiredCapabilities desiredCapabilities = (DesiredCapabilities) capabilities;
		constructBodyInGeneral(androidDriver, desiredCapabilities);
	}
	
	private void constructBody(String remoteAddress)
	{
		createWebDriver(androidDriver, new Class[] {String.class}, new Object[] {remoteAddress});
	}
	
	public AndroidDriverEncapsulation(Configuration configuration, String remoteAddress)
	{
		super(configuration);
		constructBody(remoteAddress);
	}
	
	public AndroidDriverEncapsulation(String remoteAddress)
	{
		super(Configuration.byDefault);
		constructBody(remoteAddress);
	}
	
	private void constructBody(URL remoteAddress)
	{
		createWebDriver(androidDriver, new Class[] {URL.class}, new Object[] {remoteAddress});
	}
	
	public AndroidDriverEncapsulation(Configuration configuration, URL remoteAddress)
	{
		super(configuration);
		constructBody(remoteAddress);
	}
	
	
	public AndroidDriverEncapsulation(URL remoteAddress)
	{
		super(Configuration.byDefault);
		constructBody(remoteAddress);
	}
	
	private void constructBody(URL remoteAddress, DesiredCapabilities capabilities)
	{
		createWebDriver(androidDriver, new Class[] {URL.class, DesiredCapabilities.class}, new Object[] {remoteAddress,capabilities});
	}
	
	public AndroidDriverEncapsulation(Configuration configuration, URL remoteAddress, DesiredCapabilities capabilities)
	{
		super(configuration);
		constructBody(remoteAddress, capabilities);
	}
	
	
	public AndroidDriverEncapsulation(URL remoteAddress, DesiredCapabilities capabilities)
	{
		super(Configuration.byDefault);
		constructBody(remoteAddress, capabilities);
	}

}
