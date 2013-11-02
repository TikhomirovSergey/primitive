package org.primitive.configuration;

public enum ESupportedDrivers {
	FIREFOX, CHROME, INTERNETEXPLORER, SAFARI, REMOTE, OPERA, HTMLUNIT, ANDROID;
	
	public synchronized static ESupportedDrivers parse(String original)
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
}
