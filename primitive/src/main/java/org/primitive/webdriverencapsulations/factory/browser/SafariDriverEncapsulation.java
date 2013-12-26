package org.primitive.webdriverencapsulations.factory.browser;

import org.openqa.selenium.safari.SafariDriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.primitive.configuration.Configuration;
import org.primitive.logging.Log;
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
	@Deprecated
	protected void prepare() 
	{
		Log.warning("I do nothing");
	}

}

