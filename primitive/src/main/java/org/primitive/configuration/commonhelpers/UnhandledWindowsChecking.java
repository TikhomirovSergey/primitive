package org.primitive.configuration.commonhelpers;

import java.util.concurrent.TimeUnit;

import org.primitive.configuration.AbstractConfigurationAccessHelper;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.interfaces.ITimeUnitSetting;

/**
 * @author s.tihomirov
 * for situations when unhandled alerts and windows are need to be checked
 */
public class UnhandledWindowsChecking extends AbstractConfigurationAccessHelper
		implements ITimeUnitSetting {

	private final String secsForAwaitinAlertPresentSetting = "secsForAwaitinAlertPresent";
	private final String sessionTimeSetting = "sessionTime";
	private final String timeForWaitingSetting = "timeForWaiting";
	private final String unhandledWindowsCheckingGroup= "unhandledWindowsChecking";

	public UnhandledWindowsChecking(Configuration configuration) {
		super(configuration);
	}

	public Long getSecsForAwaitinAlertPresent()
	{
		return (Long) getSetting(secsForAwaitinAlertPresentSetting);
	}

	public Long getSessionTime()
	{
		return (Long) getSetting(sessionTimeSetting);
	}

	@Override
	public Object getSetting(String name)
	{
		return  getSettingValue(unhandledWindowsCheckingGroup,  name);
	}

	public Long getTimeForWaiting()
	{
		return (Long) getSetting(timeForWaitingSetting);
	}

	@Override
	public TimeUnit getTimeUnit() {
		String timeUnitStr = (String) getSettingValue(unhandledWindowsCheckingGroup, timeUnitSetting);
		if (timeUnitStr != null)
		{
			return TimeUnit.valueOf(timeUnitStr.toUpperCase());
		}
		else
		{
			return null;
		}
	}

}
