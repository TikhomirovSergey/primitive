package org.primitive.configuration.commonhelpers;

import org.primitive.configuration.AbstractConfigurationAccessHelper;
import org.primitive.configuration.Configuration;

public class BrowserWindowsTimeOuts extends AbstractConfigurationAccessHelper {
	private final String newWindowTimeOutSetting = "newBrowserWindowTimeOutSec";
	private final String windowCountTimeOutSetting = "browserWindowCountTimeOutSec";
	private final String windowClosingTimeOutSetting = "browserWindowClosingTimeOutSec";
	
	private final String browserWindowsTimeOutsGroup = "browserWindowsTimeOuts";
	
	public BrowserWindowsTimeOuts(Configuration configuration) {
		super(configuration);
	}

	@Override
	public Object getSetting(String name) {
		return getSettingValue(browserWindowsTimeOutsGroup, name);
	}
	
	public Long getNewBrowserWindowTimeOutSec()
	{
		return (Long) getSetting(newWindowTimeOutSetting);			
	}
	
	public Long getWindowCountTimeOutSec()
	{
		return (Long) getSetting(windowCountTimeOutSetting);			
	}
	
	public Long getWindowClosingTimeOutSec()
	{
		return (Long) getSetting(windowClosingTimeOutSetting);			
	}		

}
