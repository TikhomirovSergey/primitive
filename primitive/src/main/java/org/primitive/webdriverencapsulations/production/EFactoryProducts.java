package org.primitive.webdriverencapsulations.production;

import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.RemoteWebDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.browser.AndroidDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.browser.ChromeDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.browser.FirefoxDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.browser.HtmlUnitDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.browser.InternetExplorerDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.browser.OperaDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.browser.PhantomJSDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.browser.SafariDriverEncapsulation;

public enum EFactoryProducts {
	FIREFOX(ESupportedDrivers.FIREFOX, FirefoxDriverEncapsulation.class),
	CHROME(ESupportedDrivers.CHROME, ChromeDriverEncapsulation.class),
	INTERNETEXPLORER(ESupportedDrivers.INTERNETEXPLORER, InternetExplorerDriverEncapsulation.class),
	SAFARI(ESupportedDrivers.SAFARI, SafariDriverEncapsulation.class),
	OPERA(ESupportedDrivers.OPERA, OperaDriverEncapsulation.class),
	REMOTE(ESupportedDrivers.REMOTE, RemoteWebDriverEncapsulation.class), //remote webdriver will use capabilities of FireFox browser by default
	HTMLUNIT(ESupportedDrivers.HTMLUNIT, HtmlUnitDriverEncapsulation.class),
	ANDROID(ESupportedDrivers.ANDROID, AndroidDriverEncapsulation.class),
	PHANTOMJS(ESupportedDrivers.PHANTOMJS, PhantomJSDriverEncapsulation.class);
	
	
	private ESupportedDrivers supportedDrivers;
	private Class<? extends WebDriverEncapsulation> clazz;
	
	EFactoryProducts(ESupportedDrivers supportedDrivers, Class<? extends WebDriverEncapsulation> clazz)
	{
		this.supportedDrivers = supportedDrivers;
		this.clazz = clazz;
	}
	
	public static EFactoryProducts findProduct(ESupportedDrivers supportedDriver)
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
	
	public Class<? extends WebDriverEncapsulation> getProduct()
	{
		return clazz;
	}

}
