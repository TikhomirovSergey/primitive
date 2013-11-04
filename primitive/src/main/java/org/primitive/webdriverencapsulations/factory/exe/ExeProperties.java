package org.primitive.webdriverencapsulations.factory.exe;

import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

public enum ExeProperties {
	FORCHROME(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "chromedriver.exe"), 
	FORIEXPLORER(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY,"IEDriverServer.exe"), 
	FORPHANTOMJS(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"phantomjs.exe");
	
	private String property;
	private String defaultValue;
	
	ExeProperties(String property, String defaultValue)
	{
		this.property     = property;
		this.defaultValue = defaultValue;
	}
	
	public String getProperty()
	{
		return property;
	}
	
	public String getDefaultPropertyValue()
	{
		return defaultValue;
	}

}
