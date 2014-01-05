package org.primitive.configuration.interfaces;

import java.util.concurrent.TimeUnit;

public interface ITimeUnitSetting {
	public static final String timeUnitSetting = "timeUnit";
	
	public TimeUnit getTimeUnit();
}
