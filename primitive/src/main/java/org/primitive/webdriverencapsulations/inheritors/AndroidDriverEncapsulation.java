package org.primitive.webdriverencapsulations.inheritors;

import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

public class AndroidDriverEncapsulation extends WebDriverEncapsulation {
		
	private void constructBody(String remoteAddress)
	{
		createWebDriver(ESupportedDrivers.ANDROID.getUsingWebDriverClass(), new Class[] {String.class}, new Object[] {remoteAddress});
	}
	
	public AndroidDriverEncapsulation(Configuration configuration, String remoteAddress)
	{
		this.configuration = configuration;
		constructBody(remoteAddress);
	}
	
	public AndroidDriverEncapsulation(String remoteAddress)
	{
		constructBody(remoteAddress);
	}
	
	private void constructBody(URL remoteAddress)
	{
		createWebDriver(ESupportedDrivers.ANDROID.getUsingWebDriverClass(), new Class[] {URL.class}, new Object[] {remoteAddress});
	}
	
	public AndroidDriverEncapsulation(Configuration configuration, URL remoteAddress)
	{
		this.configuration = configuration;
		constructBody(remoteAddress);
	}
	
	
	public AndroidDriverEncapsulation(URL remoteAddress)
	{
		constructBody(remoteAddress);
	}
	
	private void constructBody(URL remoteAddress, DesiredCapabilities capabilities)
	{
		createWebDriver(ESupportedDrivers.ANDROID.getUsingWebDriverClass(), new Class[] {URL.class, DesiredCapabilities.class}, new Object[] {remoteAddress,capabilities});
	}
	
	public AndroidDriverEncapsulation(Configuration configuration, URL remoteAddress, DesiredCapabilities capabilities)
	{
		this.configuration = configuration;
		constructBody(remoteAddress, capabilities);
	}
	
	
	public AndroidDriverEncapsulation(URL remoteAddress, DesiredCapabilities capabilities)
	{
		constructBody(remoteAddress, capabilities);
	}
}
