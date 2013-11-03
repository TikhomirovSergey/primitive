package org.primitive.webdriverencapsulations.factory.products;

import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.AndroidDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.ChromeDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.FirefoxDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.HtmlUnitDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.InternetExplorerDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.OperaDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.RemoteWebDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.SafariDriverEncapsulation;

public enum EFactoryProducts {
	FIREFOX(ESupportedDrivers.FIREFOX, FirefoxDriverEncapsulation.class),
	CHROME(ESupportedDrivers.CHROME, ChromeDriverEncapsulation.class),
	INTERNETEXPLORER(ESupportedDrivers.INTERNETEXPLORER, InternetExplorerDriverEncapsulation.class),
	SAFARI(ESupportedDrivers.SAFARI, SafariDriverEncapsulation.class),
	OPERA(ESupportedDrivers.OPERA, OperaDriverEncapsulation.class),
	REMOTE(ESupportedDrivers.REMOTE, RemoteWebDriverEncapsulation.class),
	HTMLUNIT(ESupportedDrivers.HTMLUNIT, HtmlUnitDriverEncapsulation.class),
	ANDROID(ESupportedDrivers.ANDROID, AndroidDriverEncapsulation.class);
	
	
	private ESupportedDrivers supportedDrivers;
	private Class<? extends WebDriverEncapsulation> clazz;
	
	EFactoryProducts(ESupportedDrivers supportedDrivers, Class<? extends WebDriverEncapsulation> clazz)
	{
		this.supportedDrivers = supportedDrivers;
		this.clazz = clazz;
	}
	
	public static Class<? extends WebDriverEncapsulation> getProduct(ESupportedDrivers supportedDriver)
	{
		Class<? extends WebDriverEncapsulation> result = null;
		for (EFactoryProducts product: values())
		{
			if (product.supportedDrivers==supportedDriver)
			{
				result = product.clazz;
				break;
			}
		}
		return result;
	}

}
