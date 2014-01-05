package org.primitive.webdriverencapsulations;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.Configuration.UnhandledWindowsChecking;
import org.primitive.configuration.interfaces.IConfigurable;
import org.primitive.interfaces.IDestroyable;
import org.primitive.logging.Log;

import com.google.common.base.Function;


public final class UnhandledWindowChecker extends Thread implements IDestroyable, IConfigurable {
	/**
	 * @author s.tihomirov
	 *
	 */
	private enum EActionsOnUnhandledAlert {
		DISMISS, ACCEPT
	}

	private WindowSwitcher switcher = null;
	private boolean isRunned=false;
	private boolean isStopped=false;
	private final static List<UnhandledWindowChecker> checkers = new ArrayList<UnhandledWindowChecker>();
	
	private final TimeUnit defaultTimeUnit = TimeUnit.SECONDS;
	private final long defaultTimeForWaiting = 60; //time for waiting while all browser windows will be handled
	private final long defaultCheckingSessionTime = 10; //time for one not handled windows detecting session
	private long defaultTimeForAwaitinAlertPresent = 1; //in seconds only
	private	long pollingEverySec = 1;
	
	private TimeUnit timeUnitForChecking; //should be set
	private long timeForWaiting;  //should be set
	private long sessionTime; //should be set
	private long timeForAwaitinAlertPresent; //should be set in seconds only
	
	private final int permits = 1;
	private final Semaphore semaphore = new Semaphore(permits, true);
	
	@Deprecated
	public synchronized void start()
	{
		if (!isRunned)
		{	
			setPriority(NORM_PRIORITY);
			isRunned  = true;
			isStopped = false;
			super.start();
		}
	}
	
	//I wanted to call it "stop", but there is some problems as it is name of the deprecated method
	//So I called it ...
	@Deprecated
	public void pause()
	{
		if (isRunned)
		{	
			isRunned  = false;
			while (!isStopped);
		}
	}
	
	private static UnhandledWindowChecker returnChecker(WindowSwitcher switcher, Configuration configuration)
	{
		for (UnhandledWindowChecker checker: checkers)
		{
			if (checker.switcher == switcher)
			{
				return(checker);
			}
		}		
		UnhandledWindowChecker checker = new UnhandledWindowChecker();
		checker.switcher   = switcher;
		checker.resetAccordingTo(Configuration.byDefault);
		checkers.add(checker);
		checker.config(configuration.getUnhandledWindowsChecking());
		return(checker);	
	}
	
	public synchronized static UnhandledWindowChecker getChecker(WindowSwitcher switcher)
	{
		return(returnChecker(switcher,Configuration.byDefault));				
	}
	
	@Deprecated
	public synchronized static UnhandledWindowChecker getChecker(WindowSwitcher switcher, Configuration configuration)
	{
		UnhandledWindowChecker checker = returnChecker(switcher, configuration);
		return(checker);				
	}
	
	private long getLongValuesFromConfig(Long possibleValue, long defaultValue)
	{
		if (possibleValue==null)
		{
			return defaultValue;
		}
		else
		{
			return possibleValue;
		}
	}
	
	private void config(UnhandledWindowsChecking config)
	{
		TimeUnit settingTimeUnit = config.getTimeUnit();
		if (settingTimeUnit == null)
		{
			settingTimeUnit = defaultTimeUnit;
		}
		timeUnitForChecking = settingTimeUnit;
		sessionTime    = getLongValuesFromConfig(config.getSessionTime(),defaultCheckingSessionTime);
		timeForWaiting = getLongValuesFromConfig(config.getTimeForWaiting(), defaultTimeForWaiting);
		defaultTimeForAwaitinAlertPresent = getLongValuesFromConfig(config.getSecsForAwaitinAlertPresent(), defaultTimeForAwaitinAlertPresent);
	}
	
	public synchronized void resetAccordingTo(Configuration newConfig)
	{	
		config(newConfig.getUnhandledWindowsChecking());
	}
	
	private void block()
	{
		try 
		{
			semaphore.acquire(permits);
		} 
		catch (InterruptedException e) 
		{
			Log.debug("Thread that waited for permit has been interrupted...", e);
		}
	}
	
	private void release()
	{
		semaphore.release(permits);
	}
	
	public void destroy()
	{
		pause();
		switcher = null;
		checkers.remove(this);				
		try 
		{
			this.finalize();
		} 
		catch (Throwable e) 
		{
			Log.warning(e.getMessage(),e);
		}				
	}
	
	private boolean attemptToCloseWindow(List<String> windowList, int index) throws UnclosedBrowserWindowException, UnhandledAlertException
	{
		try
		{
			switcher.switchTo(windowList.get(index));
			switcher.takeAPictureOfAWarning(windowList.get(index).toString(), "Unhandled browser window!");
			Log.warning("There is an unhandled browser window!");
			switcher.close(windowList.get(index).toString());
		}
		catch (UnclosedBrowserWindowException e)
		{
			throw e;
		}
		catch (UnhandledAlertException e)
		{
			throw e;
		}		
		catch (NoSuchWindowException e)
		{
			Log.debug("Browser window is already closed...");
		}
		return true;
	}
	
	private void attemptToHandleAlert(EActionsOnUnhandledAlert whatToDo)
	{
		try
		{
			Alert alert = switcher.getAlert(timeForAwaitinAlertPresent);
			String alertText = alert.getText();
			if (whatToDo.equals(EActionsOnUnhandledAlert.DISMISS))
			{
				alert.dismiss();
			}
			if (whatToDo.equals(EActionsOnUnhandledAlert.ACCEPT))
			{
				alert.accept();
			}
			String msg = "Unhandled alert has been caught out!";
			if (alertText!=null)
			{
				msg = msg + " Text: " + alertText;
			}
			Log.warning(msg);
		}
		catch (NoAlertPresentException e1)
		{
			Log.debug("There is not any alert", e1);
		}			
	}
	
	private Wait<UnhandledWindowChecker> getWait(long time)
	{
		return 	 new FluentWait<UnhandledWindowChecker>(this)
				.withTimeout(time, timeUnitForChecking)
				.pollingEvery(pollingEverySec, TimeUnit.SECONDS)
				.ignoring(UnhandledAlertException.class);
	}
	
	private List<String> waitForUnexpectedBrowserWindows()
	{
		Wait<UnhandledWindowChecker> wait = getWait(sessionTime);	
		List <String> unexpectedHandles = new ArrayList<String>();
		try
		{
			unexpectedHandles = wait.until(new Function<UnhandledWindowChecker, List<String>>() {
				public List<String> apply(UnhandledWindowChecker checker) 
			     {
			    	 //list of window handles that probably unexpected
			    	 List <String> unexpectedList = new ArrayList<String>();
			    	 if ((!isRunned)) //if checking has been stopped 
			    	 {							
			    		 return new ArrayList<String>(); //returns empty list
			    	 }
			    	 else 
			    	 {
			    		 try
			    		 {
			    			 block();
			    			 unexpectedList = getUnexpectedWindows();
			    		 }
			    		 catch (UnhandledAlertException e)
			    		 {
			    			 attemptToHandleAlert(EActionsOnUnhandledAlert.DISMISS);
			    			 return null;
			    		 }
			    		 finally
			    		 {
			    			 release();
			    		 }
			    		 if (unexpectedList.size()==0)
			    		 {
			    			 return null; //if there is no windows that probably unexpected
			    		 }
			    		 else
			    		 {
			    			 return unexpectedList; //if there is unhandled browser windows
			    		 }
			    	 }				    	 
			     }
			});
		}
		catch (TimeoutException e)
		{
			Log.debug("There is no unhandled browser windows...");
		}
			
		return unexpectedHandles;
	}
	
	//остались ли неожиданные окна?
	private boolean areThereUnexpectedWindows()
	{
		Wait<UnhandledWindowChecker> wait = getWait(timeForWaiting);				
		boolean thereIsNoUnExpectedWindows = false;
		try
		{
			thereIsNoUnExpectedWindows = wait.until(new Function<UnhandledWindowChecker, Boolean>() 
			{
			     public Boolean apply(UnhandledWindowChecker checker) 
			     {
			    	 //list of window handles that probably unexpected
			    	 List <String> unexpectedList = new ArrayList<String>();
			    	 if ((!isRunned)) //if checking by this thread has been stopped
			    	 {
			    		 return true;
			    	 }
			    	 else
			    	 {  
			    		 try
			    		 {
			    			 block();
			    			 unexpectedList = getUnexpectedWindows();
			    		 }
			    		 catch (UnhandledAlertException e)
			    		 {
			    			 attemptToHandleAlert(EActionsOnUnhandledAlert.DISMISS);
			    			 return null;
			    		 }
			    		 finally
			    		 {
			    			 release();
			    		 }
			    		 if (unexpectedList.size()!=0)
			    		 {
			    			 return null; //if there are unhandled browser windows
			    		 }
			    		 else
			    		 {
			    			 return true; //if all browser windows have been handled
			    		 }
			    	 }				    	 
			     }
			});
		}
		catch (TimeoutException e)
		{
			Log.debug("It seems that there are unhandled browser windows...");
		}
			
		return(!thereIsNoUnExpectedWindows);
	}
	
	public synchronized void killUnexpectedWindows() throws UnhandledAlertException, UnclosedBrowserWindowException
	{	 
		try
		{
			block();
			List<String> WindowList = null;
			WindowList = getUnexpectedWindows();	
				
			Log.debug("Let's start killing of unhandled browser windows and alerts!");
			int i = WindowList.size()-1;
			while (i>=0)
			{
				boolean closed = false;
				try
				{
					closed = attemptToCloseWindow(WindowList, i);
				}	
				catch (UnclosedBrowserWindowException|UnhandledAlertException e)
				{
					closed = false;
					Log.warning("Unhandled browser window hasn't been closed!");
				}	
					
				if (!closed)
				{
					attemptToHandleAlert(EActionsOnUnhandledAlert.DISMISS);
				}
				
				if (!closed)
				{
					try
					{
						closed = attemptToCloseWindow(WindowList, i);
					}	
					catch (UnclosedBrowserWindowException|UnhandledAlertException e)
					{
						closed = false;
					}
				}
					
				if (!closed)
				{
					attemptToHandleAlert(EActionsOnUnhandledAlert.ACCEPT);
				}
					
				if (!closed)
				{
					try
					{
						attemptToCloseWindow(WindowList, i);
					}	
					catch (UnclosedBrowserWindowException|UnhandledAlertException e)
					{ 
						throw e;
					}
				}					
				i=i-1;
			}
		}
		finally
		{
			release();
		}
	}	
	
	//getting of browser window handles that probably unexpected
	private List <String> getUnexpectedWindows()
	{
		attemptToHandleAlert(EActionsOnUnhandledAlert.DISMISS);
		List<String>  handles		 = new ArrayList<String>(switcher.getWindowHandles());
		List <String> unexpectedList = new ArrayList<String>();				
		//If there is only one browser window we ignore it as it will be handled soon
		if (handles.size()<=1) // or it is already handled
		{
			return(unexpectedList); //returns empty list of window handles
		}
		else
		{	// If there is more than one browser window
			for (String handle: handles)
			{
				if (SingleWindow.checkForInit(handle,switcher)==null)
				{   // it trying to filter windows that unhandled 
					unexpectedList.add(handle);
				}
			}
			return(unexpectedList);	
		}		
	}
	
	//checking in this thread
	@Deprecated
	public void run()
	{	
		while (isRunned)
		{	
			try
			{
				while ((waitForUnexpectedBrowserWindows().size()==0)&
						isRunned);	
				
				if (areThereUnexpectedWindows()&isRunned)
				{
					killUnexpectedWindows();
				}
			}
			catch (UnclosedBrowserWindowException|UnhandledAlertException e)
			{ 
				Log.warning("Attempting to close unhandled browser windows has been failed",e);
			}
			catch (Exception e) {
				isRunned = false;
				throw e;
			}
		}
		isStopped = true;
	}
}
