package org.primitive.configuration.webdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.primitive.configuration.Configuration;

import com.opera.core.systems.OperaDriver;


public enum ESupportedDrivers {
	FIREFOX((org.openqa.selenium.Capabilities) DesiredCapabilities.firefox(), FirefoxDriver.class, null),
	CHROME(DesiredCapabilities.chrome(), ChromeDriver.class, EServices.CHROMESERVICE), 
	INTERNETEXPLORER(DesiredCapabilities.internetExplorer(), InternetExplorerDriver.class, EServices.IEXPLORERSERVICE), 
	SAFARI(DesiredCapabilities.safari(), SafariDriver.class, null), 
	REMOTE(DesiredCapabilities.firefox(), RemoteWebDriver.class, null), 
	OPERA(DesiredCapabilities.opera(), OperaDriver.class, null),
	HTMLUNIT(DesiredCapabilities.htmlUnitWithJs(), HtmlUnitDriver.class, null), 
	PHANTOMJS(DesiredCapabilities.phantomjs(), PhantomJSDriver.class, EServices.PHANTOMJSSERVICE);
	
	private Capabilities capabilities;
	private Class<? extends WebDriver> driverClazz;
	private EServices service;

	private ESupportedDrivers(Capabilities capabilities,
			Class<? extends WebDriver> driverClazz, EServices sevice) {
		this.capabilities = capabilities;
		this.driverClazz = driverClazz;
		this.service = sevice;
	}

	public static ESupportedDrivers parse(String original) {
		String parcingStr = original.toUpperCase().trim();

		ESupportedDrivers[] values = ESupportedDrivers.values();
		for (ESupportedDrivers enumElem : values) {
			if (parcingStr.equals(enumElem.toString())) {
				return enumElem;
			}
		}
		throw new IllegalArgumentException("Webdriver with specified name "
				+ original + " is not supported");
	}

	public Capabilities getDefaultCapabilities() {
		return capabilities;
	}

	public Class<? extends WebDriver> getUsingWebDriverClass() {
		return driverClazz;
	}

	public void setSystemProperty(Configuration configInstance) {
		if (service != null) {
			this.service.setSystemProperty(configInstance);
		}
	}
}
