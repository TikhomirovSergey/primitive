package org.primitive.webdriverencapsulations.inheritors;

import org.openqa.selenium.Capabilities;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

public class AndroidEncapsulation extends WebDriverEncapsulation {
	
	public AndroidEncapsulation(String url, Capabilities caps){
		createWebDriver(ESupportedDrivers.ANDROID.getUsingWebDriverClass(), new Class[] {String.class, Capabilities.class}, new Object[] {url, caps});	
	}	
}
