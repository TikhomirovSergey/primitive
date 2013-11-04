package org.primitive.webdriverencapsulations.production;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.AndroidDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.ChromeDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.FirefoxDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.HtmlUnitDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.InternetExplorerDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.OperaDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.PhantomJSDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.RemoteWebDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.SafariDriverEncapsulation;

public enum EFactoryProducts {
	FIREFOX(ESupportedDrivers.FIREFOX, FirefoxDriverEncapsulation.class, DesiredCapabilities.firefox()),
	CHROME(ESupportedDrivers.CHROME, ChromeDriverEncapsulation.class, DesiredCapabilities.chrome()),
	INTERNETEXPLORER(ESupportedDrivers.INTERNETEXPLORER, InternetExplorerDriverEncapsulation.class, DesiredCapabilities.internetExplorer()),
	SAFARI(ESupportedDrivers.SAFARI, SafariDriverEncapsulation.class, DesiredCapabilities.safari()),
	OPERA(ESupportedDrivers.OPERA, OperaDriverEncapsulation.class, DesiredCapabilities.opera()),
	REMOTE(ESupportedDrivers.REMOTE, RemoteWebDriverEncapsulation.class, DesiredCapabilities.firefox()), //remote webdriver will use capabilities of FireFox browser by default
	HTMLUNIT(ESupportedDrivers.HTMLUNIT, HtmlUnitDriverEncapsulation.class, DesiredCapabilities.htmlUnitWithJs()),
	ANDROID(ESupportedDrivers.ANDROID, AndroidDriverEncapsulation.class, DesiredCapabilities.android()),
	PHANTOMJS(ESupportedDrivers.PHANTOMJS, PhantomJSDriverEncapsulation.class, DesiredCapabilities.phantomjs());
	
	
	private ESupportedDrivers supportedDrivers;
	private Class<? extends WebDriverEncapsulation> clazz;
	private Capabilities defaultCapabilities;
	
	EFactoryProducts(ESupportedDrivers supportedDrivers, Class<? extends WebDriverEncapsulation> clazz, Capabilities defaultCapabilities)
	{
		this.supportedDrivers = supportedDrivers;
		this.clazz = clazz;
		this.defaultCapabilities = defaultCapabilities;
	}
	
	private static EFactoryProducts findProduct(ESupportedDrivers supportedDriver)
	{
		EFactoryProducts result = null;
		for (EFactoryProducts product: values())
		{
			if (product.supportedDrivers==supportedDriver)
			{
				result = product;
				break;
			}
		}
		return result;
	}
	
	public static Class<? extends WebDriverEncapsulation> getProduct(ESupportedDrivers supportedDriver)
	{
		return findProduct(supportedDriver).clazz;
	}
	
	public static Capabilities getCapabilities(ESupportedDrivers supportedDriver)
	{
		return findProduct(supportedDriver).defaultCapabilities;
	}

}
