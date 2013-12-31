package org.primitive.configuration;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;


public enum ESupportedDrivers {
	FIREFOX((org.openqa.selenium.Capabilities) DesiredCapabilities.firefox()), CHROME(DesiredCapabilities.chrome()), 
	INTERNETEXPLORER(DesiredCapabilities.internetExplorer()), SAFARI(DesiredCapabilities.safari()), REMOTE(DesiredCapabilities.firefox()), 
	OPERA(DesiredCapabilities.opera()), HTMLUNIT(DesiredCapabilities.htmlUnitWithJs()), ANDROID(DesiredCapabilities.android()),PHANTOMJS(DesiredCapabilities.phantomjs());
	
	private Capabilities capabilities;
	
	private ESupportedDrivers(Capabilities capabilities)
	{
		this.capabilities = capabilities;
	}
	
	public static ESupportedDrivers parse(String original)
	{
		String parcingStr = original.toUpperCase().trim();
		
		ESupportedDrivers[] values = ESupportedDrivers.values();
		for (ESupportedDrivers enumElem: values)
		{
			if (parcingStr.equals(enumElem.toString()))
			{
				return enumElem;
			}
		}
		throw new IllegalArgumentException("Webdriver with specified name " + original + " is not supported");
	}	
	
	public Capabilities getDefaultCapabilities()
	{
		return capabilities;
	}
}
