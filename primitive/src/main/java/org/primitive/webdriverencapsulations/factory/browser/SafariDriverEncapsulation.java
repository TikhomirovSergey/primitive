package org.primitive.webdriverencapsulations.factory.browser;

import org.junit.Assume;
import org.openqa.selenium.safari.SafariDriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.primitive.configuration.Configuration;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;



public class SafariDriverEncapsulation extends WebDriverEncapsulation 
{
	private static final Class<? extends WebDriver> safariDriver = SafariDriver.class;
	
	public SafariDriverEncapsulation(String openingURL) {
		super(Configuration.byDefault);
		constructBodyInGeneral(openingURL, safariDriver);
	}
	
	public SafariDriverEncapsulation(Configuration configuration, String openingURL) {
		super(configuration);
		constructBodyInGeneral(openingURL, safariDriver);
	}

	public SafariDriverEncapsulation(String openingURL, Capabilities capabilities) {
		super(Configuration.byDefault);
		constructBodyInGeneral(openingURL, safariDriver, capabilities);
	}
	
	public SafariDriverEncapsulation(Configuration configuration, String openingURL, Capabilities capabilities) {
		super(configuration);
		constructBodyInGeneral(openingURL, safariDriver, capabilities);
	}	

	@Override
	protected void prepare() 
	{
		 Platform current = Platform.getCurrent();
		 Assume.assumeTrue(Platform.MAC.is(current) || Platform.WINDOWS.is(current));		
	}

}

