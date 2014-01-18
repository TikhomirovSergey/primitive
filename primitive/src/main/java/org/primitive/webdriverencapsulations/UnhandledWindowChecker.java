package org.primitive.webdriverencapsulations;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.UnhandledAlertException;
import org.primitive.configuration.Configuration;
import org.primitive.interfaces.IDestroyable;
import org.primitive.logging.Log;


public final class UnhandledWindowChecker extends Thread implements IDestroyable {
	/**
	 * @author s.tihomirov
	 *
	 */
	private enum EActionsOnUnhandledAlert {
		DISMISS, ACCEPT
	}

	private WindowSwitcher switcher = null;
	private final static List<UnhandledWindowChecker> checkers = new ArrayList<UnhandledWindowChecker>();
	
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
		checkers.add(checker);
		return(checker);	
	}
	
	public synchronized static UnhandledWindowChecker getChecker(WindowSwitcher switcher)
	{
		return(returnChecker(switcher,Configuration.byDefault));				
	}
	
	public synchronized static UnhandledWindowChecker getChecker(WindowSwitcher switcher, Configuration configuration)
	{
		UnhandledWindowChecker checker = returnChecker(switcher, configuration);
		return(checker);				
	}
	
	public void destroy()
	{
		switcher = null;
		checkers.remove(this);			
	}
	
	private boolean attemptToCloseWindow(List<String> windowList, int index) throws UnclosedWindowException, UnhandledAlertException
	{
		try
		{
			switcher.switchTo(windowList.get(index));
			switcher.takeAPictureOfAWarning(windowList.get(index).toString(), "Unhandled browser window!");
			Log.warning("There is an unhandled browser window!");
			switcher.close(windowList.get(index).toString());
		}
		catch (UnclosedWindowException e)
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
			Alert alert = switcher.getAlert();
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
	
	public synchronized void killUnexpectedWindows() throws UnhandledAlertException, UnclosedWindowException
	{	 
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
			catch (UnclosedWindowException|UnhandledAlertException e)
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
				catch (UnclosedWindowException|UnhandledAlertException e)
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
				catch (UnclosedWindowException|UnhandledAlertException e)
				{ 
					throw e;
				}
			}					
			i=i-1;
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
				if (SingleWindow.isInitiated(handle,switcher)==null)
				{   // it trying to filter windows that unhandled 
					unexpectedList.add(handle);
				}
			}
			return(unexpectedList);	
		}		
	}
}
