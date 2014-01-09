package org.primitive.webdriverencapsulations;

import java.net.URL;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.primitive.interfaces.IDestroyable;
import org.primitive.logging.Log;

/**
 * @author s.tihomirov
 * Fluent waiting for browser window conditions
 */
final class FluentWindowConditions implements IDestroyable{

	private WindowSwitcher switcher;

	FluentWindowConditions(WindowSwitcher switcher) {
		this.switcher = switcher;
	}

	@Override
	public void destroy() {
		try 
		{
			this.finalize();
		} 
		catch (Throwable e) {
			Log.warning("There are some problems with finalizing of browser window fluent waiting.", e);
		}
		switcher = null;
		
	}

	//is here new browser window?
	//returns handle of a new browser window that we have been waiting for specified time
	private String getNewHandle(final WebDriver from, final Set<String> oldHandles)
	{
		synchronized (switcher) 
		{
			String newHandle = null;
			Set<String> newHandles = from.getWindowHandles();
			if (newHandles.size()>oldHandles.size())
			{
				newHandles.removeAll(oldHandles);
				Log.message("New browser window is caught!");
				newHandle = (String) newHandles.toArray()[0];
				return newHandle;
			}
			return newHandle;
		}
	
	}
	
	//is here new browser window?
	//returns handle of a new browser window that we have been waiting for specified time
	//new browser window should have defined title. We can specify title in this way:
	//title, title*, *title, *title*,tit*le and etc
	private String getNewHandle(final WebDriver from, final Set<String> oldHandles, String title)
	{
		synchronized(switcher)
		{
			Pattern titlePattern = Pattern.compile(title);				
			String newHandle = null;
			Set<String> newHandles = from.getWindowHandles();
			if (newHandles.size()>oldHandles.size())
			{
				newHandles.removeAll(oldHandles);
				for (String handle:newHandles)
				{
					from.switchTo().window(handle);
					Matcher titleMatcher = titlePattern.matcher(from.getTitle());
					if (titleMatcher.matches())
					{
						newHandle = handle;
						Log.message("New browser window with title " + title + "is caught!");
						return newHandle;
					}
				}
			}
			return newHandle;
		}
	}	

	//is here new browser window?
	//returns handle of a new browser window that we have been waiting for specified time
	//new browser window should have page that loads by specified URL
	private String getNewHandle(final WebDriver from, final Set<String> oldHandles, URL url)
	{
		synchronized (switcher) 
		{
			String newHandle = null;
			Set<String> newHandles = from.getWindowHandles();
			if (newHandles.size()>oldHandles.size())
			{
				newHandles.removeAll(oldHandles);
				for (String handle:newHandles)
				{
					from.switchTo().window(handle);
					if (from.getCurrentUrl().equals(url.toString()))
					{
						newHandle = handle;
						Log.message("New browser window that loaded by URL " + url.toString() + " is caught!");
						return newHandle;
					}
				}
			}
			return newHandle;	
		}			
	}

	//is there such window count? It it is true this method returns handle of window 
	private String getWindowHandleByIndex(final WebDriver from, int windowIndex)
	{
	
		synchronized(switcher)
		{
			Set<String> handles =  from.getWindowHandles();
			if ((handles.size()-1)>=windowIndex)
			{
				Log.message("Browser window that specified by index " + Integer.toString(windowIndex) + " is present");
				return new ArrayList<String>(handles).get(windowIndex);
			}
			else
			{
				return null;
			}	
		}
	}

	//fluent waiting for the result. See above
	ExpectedCondition<Boolean> isClosed(final String closingHandle)
	{
		return new ExpectedCondition<Boolean>()
		{
			public Boolean apply(final WebDriver from)
			{
				return isClosed(from, closingHandle);
			}
		};	
	}

	//is browser window closed?
	private Boolean isClosed(final WebDriver from, String handle)
	{
		synchronized (switcher) 
		{
			Set<String> handles = from.getWindowHandles();
			if (!handles.contains(handle))
			{
				Log.message("Browser window has been closed successfully!");
				return true;
			}
			else
			{
				return null;
			}		
		}					
	}

	//fluent waiting for the result. See above
	ExpectedCondition<String> newWindowIsAppeared()
	{
		return new ExpectedCondition<String>()
		{ 
			Set<String> oldHandles = switcher.getWindowHandles();
			public String apply(final WebDriver from)
			{
				return getNewHandle(from, oldHandles);
			}
		};
	}

	//fluent waiting for the result. See above
	ExpectedCondition<String> newWindowIsAppeared(final String title)
	{
		return new ExpectedCondition<String>()
		{ 
			Set<String> oldHandles = switcher.getWindowHandles();
			public String apply(final WebDriver from)
			{
				return getNewHandle(from, oldHandles, title);
			}
		};
	}

	//fluent waiting of the result. See above
	ExpectedCondition<String> newWindowIsAppeared(final URL url)
	{
		return new ExpectedCondition<String>()
		{ 
			Set<String> oldHandles = switcher.getWindowHandles();
			public String apply(final WebDriver from)
			{
				return getNewHandle(from, oldHandles, url);
			}
		};
	}

	//fluent waiting for the result. See above
	ExpectedCondition<String> suchBrowserhWindowWithIndexIsPresent(final int windowIndex)
	{
		return new ExpectedCondition<String>()
		{ 				
			public String apply(final WebDriver from)
			{
				return getWindowHandleByIndex(from, windowIndex);		
			}
		};
	}

}