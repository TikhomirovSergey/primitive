package org.primitive.webdriverencapsulations.webdrivercomponents;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriverException;
import org.primitive.configuration.Configuration;
import org.primitive.interfaces.IConfigurable;
import org.primitive.logging.Log;

public class TimeOut extends WebdriverComponent implements Timeouts, IConfigurable {

	public TimeOut(WebDriver driver, Configuration configuration) {
		super(driver);
		resetAccordingTo(configuration);
	}

	private final long defaultTimeOut = 20000; //20 seconds
	private final TimeUnit defaultTimeUnit = TimeUnit.MILLISECONDS;

	private Long getTimeOutValue(Long longObjParam)
	{
		if (longObjParam == null)
		{
			longObjParam = defaultTimeOut;
		}
		return longObjParam;
	}

	public Timeouts implicitlyWait(long timeOut, TimeUnit timeUnit)
	{
		try
		{
			return driver.manage().timeouts().implicitlyWait(timeOut, timeUnit);
		}
		catch (WebDriverException e)
		{
			Log.debug("Setting of an implicitly wait timeout is not supported.",e);
			return null;
		}
	}

	public Timeouts  pageLoadTimeout(long timeOut, TimeUnit timeUnit)
	{
		try
		{
			return driver.manage().timeouts().pageLoadTimeout(timeOut, timeUnit);
		}
		catch (WebDriverException e)
		{
			Log.debug("Setting of a page load timeout is not supported.",e);
			return null;
		}
	}

	//set values of time outs according to configuration
	public synchronized void resetAccordingTo(Configuration config)
	{
		TimeUnit settingTimeUnit = config.getWebDriverTimeOuts().getTimeUnit();
		if (settingTimeUnit==null)
		{
			settingTimeUnit = defaultTimeUnit;
		}
		
		Long timeOut = getTimeOutValue(config.getWebDriverTimeOuts().getImplicitlyWaitTimeOut());
		implicitlyWait(timeOut, settingTimeUnit);
					
		timeOut = getTimeOutValue(config.getWebDriverTimeOuts().getScriptTimeOut());
		setScriptTimeout(timeOut, settingTimeUnit);
		
		timeOut = getTimeOutValue(config.getWebDriverTimeOuts().getLoadTimeout());
		pageLoadTimeout(timeOut, settingTimeUnit);
	}

	public Timeouts setScriptTimeout(long timeOut, TimeUnit timeUnit)
	{
		try
		{
			return driver.manage().timeouts().setScriptTimeout(timeOut, timeUnit);
		}
		catch (WebDriverException e)
		{
			Log.debug("Setting of a script execution timeout is not supported.",e);
			return null;
		}
	}

	
}
