package org.primitive.webdriverencapsulations.factory.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.primitive.configuration.Configuration;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

import com.gargoylesoftware.htmlunit.BrowserVersion;


/**
 * @author s.tihomirov
 *
 */
public class HtmlUnitDriverEncapsulation extends WebDriverEncapsulation {
	private static final Class<? extends WebDriver> htnlUnitDriver = HtmlUnitDriver.class;
	
	public HtmlUnitDriverEncapsulation() {
		super(Configuration.byDefault);
		constructBodyInGeneral( htnlUnitDriver);
	}
	
	public HtmlUnitDriverEncapsulation(Configuration configuration) {
		super(configuration);
		constructBodyInGeneral(htnlUnitDriver);
	}

	public HtmlUnitDriverEncapsulation(Capabilities capabilities) {
		super(Configuration.byDefault);
		constructBodyInGeneral(htnlUnitDriver, capabilities);
	}
	
	public HtmlUnitDriverEncapsulation(Configuration configuration, Capabilities capabilities) {
		super(configuration);
		constructBodyInGeneral(htnlUnitDriver, capabilities);
	}	
	
	private void constructBody(boolean enableJavascript)
	{
		createWebDriver(htnlUnitDriver, new Class<?>[] {Boolean.class}, new Object[] {enableJavascript});
	}
	
	private void constructBody(BrowserVersion version)
	{
		createWebDriver(htnlUnitDriver, new Class<?>[] {BrowserVersion.class}, new Object[] {version});
	}	
	
	public HtmlUnitDriverEncapsulation(boolean enableJavascript) {
		super(Configuration.byDefault);
		constructBody(enableJavascript);
	}
	
	public HtmlUnitDriverEncapsulation(Configuration configuration, boolean enableJavascript) {
		super(configuration);
		constructBody(enableJavascript);
	}	
	
	public HtmlUnitDriverEncapsulation(BrowserVersion version) {
		super(Configuration.byDefault);
		constructBody(version);
	}	
	
	public HtmlUnitDriverEncapsulation(Configuration configuration, BrowserVersion version) {
		super(configuration);
		constructBody(version);
	}	
}
