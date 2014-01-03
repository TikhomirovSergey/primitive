package org.primitive.webdriverencapsulations.inheritors;

import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

import com.gargoylesoftware.htmlunit.BrowserVersion;


/**
 * @author s.tihomirov
 *
 */
public class HtmlUnitDriverEncapsulation extends WebDriverEncapsulation {
	private void constructBody(boolean enableJavascript)
	{
		createWebDriver(ESupportedDrivers.HTMLUNIT.getUsingWebDriverClass(), new Class<?>[] {Boolean.class}, new Object[] {enableJavascript});
	}
	
	private void constructBody(BrowserVersion version)
	{
		createWebDriver(ESupportedDrivers.HTMLUNIT.getUsingWebDriverClass(), new Class<?>[] {BrowserVersion.class}, new Object[] {version});
	}	
	
	public HtmlUnitDriverEncapsulation(boolean enableJavascript) {
		constructBody(enableJavascript);
	}
	
	public HtmlUnitDriverEncapsulation(Configuration configuration, boolean enableJavascript) {
		this.configuration = configuration;
		constructBody(enableJavascript);
	}	
	
	public HtmlUnitDriverEncapsulation(BrowserVersion version) {
		constructBody(version);
	}	
	
	public HtmlUnitDriverEncapsulation(Configuration configuration, BrowserVersion version) {
		this.configuration = configuration;
		constructBody(version);
	}	
}
