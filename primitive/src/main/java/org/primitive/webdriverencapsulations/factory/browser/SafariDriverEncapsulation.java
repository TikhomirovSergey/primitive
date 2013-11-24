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
	
	public SafariDriverEncapsulation() {
		super(Configuration.byDefault);
		constructBodyInGeneral(safariDriver);
	}
	
	public SafariDriverEncapsulation(Configuration configuration) {
		super(configuration);
		constructBodyInGeneral(safariDriver);
	}

	public SafariDriverEncapsulation(Capabilities capabilities) {
		super(Configuration.byDefault);
		constructBodyInGeneral(safariDriver, capabilities);
	}
	
	public SafariDriverEncapsulation(Configuration configuration, Capabilities capabilities) {
		super(configuration);
		constructBodyInGeneral(safariDriver, capabilities);
	}	

	@Override
	protected void prepare() 
	{
		 Platform current = Platform.getCurrent();
		 Assume.assumeTrue(Platform.MAC.is(current) || Platform.WINDOWS.is(current));		
	}

}

