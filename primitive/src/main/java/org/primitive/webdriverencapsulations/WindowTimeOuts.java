package org.primitive.webdriverencapsulations;

import org.primitive.configuration.Configuration;
import org.primitive.configuration.Configuration.BrowserWindowsTimeOuts;
import org.primitive.interfaces.IConfigurable;
import org.primitive.interfaces.IDestroyable;
import org.primitive.logging.Log;

class WindowTimeOuts implements IConfigurable, IDestroyable {

	protected final long defaultTime = 1; //default time we waiting for anything
	protected final long defaultTimeForNewWindow = 30; //we will wait appearance of a new browser window for 30 seconds by default 
	private BrowserWindowsTimeOuts timeOuts;

	WindowTimeOuts(Configuration config)
	{
		resetAccordingTo(config);
	}
	
	protected Long getTimeOut(Long possibleTimeOut, long defaultValue)
	{
		if (possibleTimeOut==null)
		{
			return defaultValue;
		}
		else
		{
			return possibleTimeOut;
		}
	}

	protected BrowserWindowsTimeOuts getTimeOuts()
	{
		return timeOuts;
	}

	public synchronized void resetAccordingTo(Configuration config)
	{
		timeOuts = config.getBrowserWindowsTimeOuts();
	}

	@Override
	public void destroy() {
		timeOuts = null;
		try 
		{
			this.finalize();
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
			Log.warning(e.getMessage(), e);
		}
	}

}