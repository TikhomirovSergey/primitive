package org.primitive.webdriverencapsulations.inheritors;

import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

public final class AndroidDriverEncapsulation extends WebDriverEncapsulation {
	
	public AndroidDriverEncapsulation(String remoteAddress)
	{
		createWebDriver(ESupportedDrivers.ANDROID.getUsingWebDriverClass(), new Class[] {String.class}, new Object[] {remoteAddress});
	}
	
	public AndroidDriverEncapsulation(URL remoteAddress)
	{
		createWebDriver(ESupportedDrivers.ANDROID.getUsingWebDriverClass(), new Class[] {URL.class}, new Object[] {remoteAddress});
	}
	
	public AndroidDriverEncapsulation(URL remoteAddress, DesiredCapabilities capabilities)
	{
		createWebDriver(ESupportedDrivers.ANDROID.getUsingWebDriverClass(), new Class[] {URL.class, DesiredCapabilities.class}, new Object[] {remoteAddress,capabilities});
	}
}
