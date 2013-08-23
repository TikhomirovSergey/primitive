package org.primitive.webdriverencapsulations.factory;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.primitive.configuration.Configuration;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

import com.gargoylesoftware.htmlunit.BrowserVersion;


/**
 * @author s.tihomirov
 *
 */
public class HtmlUnitDriverIncapsulation extends WebDriverEncapsulation {
	private static final Class<? extends WebDriver> htnlUnitDriver = HtmlUnitDriver.class;
	
	public HtmlUnitDriverIncapsulation(String openingURL) {
		super(Configuration.byDefault);
		constructBodyInGeneral(openingURL, htnlUnitDriver);
	}
	
	public HtmlUnitDriverIncapsulation(Configuration configuration, String openingURL) {
		super(configuration);
		constructBodyInGeneral(openingURL,htnlUnitDriver);
	}

	public HtmlUnitDriverIncapsulation(String openingURL,
			Capabilities capabilities) {
		super(Configuration.byDefault);
		constructBodyInGeneral(openingURL, htnlUnitDriver, capabilities);
	}
	
	public HtmlUnitDriverIncapsulation(Configuration configuration, String openingURL,
			Capabilities capabilities) {
		super(configuration);
		constructBodyInGeneral(openingURL, htnlUnitDriver, capabilities);
	}	
	
	private void constructBody(String openingURL,
			boolean enableJavascript)
	{
		createWebDriver(openingURL, htnlUnitDriver, new Class<?>[] {Boolean.class}, new Object[] {enableJavascript});
	}
	
	private void constructBody(String openingURL,
			BrowserVersion version)
	{
		createWebDriver(openingURL, htnlUnitDriver, new Class<?>[] {BrowserVersion.class}, new Object[] {version});
	}	
	
	public HtmlUnitDriverIncapsulation(String URL,
			boolean enableJavascript) {
		super(Configuration.byDefault);
		constructBody(URL, enableJavascript);
	}
	
	public HtmlUnitDriverIncapsulation(Configuration configuration, String URL,
			boolean enableJavascript) {
		super(configuration);
		constructBody(URL, enableJavascript);
	}	
	
	public HtmlUnitDriverIncapsulation(String URL,
			BrowserVersion version) {
		super(Configuration.byDefault);
		constructBody(URL, version);
	}	
	
	public HtmlUnitDriverIncapsulation(Configuration configuration, String URL,
			BrowserVersion version) {
		super(configuration);
		constructBody(URL, version);
	}

	@Override
	@Deprecated
	protected void prepare(){
		Log.debug("There is nothing to prepare for " + htnlUnitDriver.getSimpleName());
	}	
}
