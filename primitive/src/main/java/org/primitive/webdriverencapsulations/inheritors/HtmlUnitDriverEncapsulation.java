package org.primitive.webdriverencapsulations.inheritors;

import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

import com.gargoylesoftware.htmlunit.BrowserVersion;


/**
 * @author s.tihomirov
 *
 */
public final class HtmlUnitDriverEncapsulation extends WebDriverEncapsulation {
	public HtmlUnitDriverEncapsulation(boolean enableJavascript) {
		createWebDriver(ESupportedDrivers.HTMLUNIT.getUsingWebDriverClass(), new Class<?>[] {boolean.class}, new Object[] {enableJavascript});
	}
	
	public HtmlUnitDriverEncapsulation(BrowserVersion version) {
		createWebDriver(ESupportedDrivers.HTMLUNIT.getUsingWebDriverClass(), new Class<?>[] {BrowserVersion.class}, new Object[] {version});
	}	
}