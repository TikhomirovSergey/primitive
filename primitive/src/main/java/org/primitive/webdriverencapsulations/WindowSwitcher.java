package org.primitive.webdriverencapsulations;


import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.primitive.configuration.commonhelpers.BrowserWindowsTimeOuts;
import org.primitive.interfaces.IDestroyable;
import org.primitive.logging.Log;
import org.primitive.logging.Photographer;
import org.primitive.webdriverencapsulations.webdrivercomponents.AlertHandler;
import org.primitive.webdriverencapsulations.webdrivercomponents.Awaiting;


public final class WindowSwitcher implements IDestroyable
{
	protected WebDriverEncapsulation driverEncapsulation;
	private final static List<WindowSwitcher> swithcerList = Collections.synchronizedList(new ArrayList<WindowSwitcher>());
	private WindowTimeOuts windowTimeOuts;
	private Awaiting awaiting;
	private FluentWindowConditions fluent;
	final List<SingleWindow> openedWindows = Collections.synchronizedList(new ArrayList<SingleWindow>());
	private boolean isAlive = true;
	
	private void changeActiveWindow(String handle) throws NoSuchWindowException, UnhandledAlertException	{
		Set<String> handles = getWindowHandles();
		if (!handles.contains(handle))
		{
			throw new NoSuchWindowException("There is no browser window with handle " + handle + "!");
		}	
		try
		{
			driverEncapsulation.getWrappedDriver().switchTo().window(handle);
		}
		catch (UnhandledAlertException|NoSuchWindowException e)
		{
			throw e;
		}
	}
	
	//returns WindowSwither instance that exists or creates a new instance 
	public static WindowSwitcher get(WebDriverEncapsulation driver)	{
		for (WindowSwitcher switcher: swithcerList)
		{
			if (switcher.driverEncapsulation == driver)
			{
				return switcher;
			}
		}
		
		return new WindowSwitcher(driver);
	}
	
	private WindowSwitcher(WebDriverEncapsulation InitialDriverPerformance)	{
		driverEncapsulation = InitialDriverPerformance;
		windowTimeOuts   = driverEncapsulation.getWindowTimeOuts();
		awaiting        = driverEncapsulation.getAwaiting();
		fluent          = new FluentWindowConditions(this);
		swithcerList.add(this);
	}
	
	public String getBrowserWindowHandleByInex(int windowIndex) throws NoSuchWindowException	{
		try		{
			Log.message("Attempt to get window that is specified by index " + Integer.toString(windowIndex) + " is present");
			BrowserWindowsTimeOuts timeOuts = windowTimeOuts.getTimeOuts();
			long timeOut = windowTimeOuts.getTimeOut(timeOuts.getWindowCountTimeOutSec(),windowTimeOuts.defaultTime);
			return awaiting.awaitCondition(timeOut, 100, fluent.suchBrowserhWindowWithIndexIsPresent(windowIndex));
		}
		catch (TimeoutException e)		{
			throw new NoSuchWindowException("Can't find browser window! Index out of bounds! Specified index is " + Integer.toString(windowIndex) + 
											" is more then actual window count", e);
		}
	}
	
	//returns handle of a new browser window that we have been waiting for specified time
	public String switchToNewBrowserWindow(long timeOutInSeconds) throws NoSuchWindowException	{	
		try
		{
			Log.message("Waiting a new browser window for " + Long.toString(timeOutInSeconds) + " seconds.");
			String newHandle = awaiting.awaitCondition(timeOutInSeconds, 100, fluent.newWindowIsAppeared());
			synchronized (this) {
				changeActiveWindow(newHandle);
			}
			return newHandle;
		}
		catch (TimeoutException e)		{
			throw new NoSuchWindowException("There is no new browser window! We have been waiting for "	+ Long.toString(timeOutInSeconds) + " seconds", e);
		}
	}
	
	//returns handle of a new browser window that we have been waiting for time that specified in configuration 
	public String switchToNewBrowserWindow() throws NoSuchWindowException	{	
		BrowserWindowsTimeOuts timeOuts = windowTimeOuts.getTimeOuts();
		long timeOut = windowTimeOuts.getTimeOut(timeOuts.getNewBrowserWindowTimeOutSec(),windowTimeOuts.defaultTimeForNewWindow);
		return switchToNewBrowserWindow(timeOut);
	}	
	
	//returns handle of a new browser window that we have been waiting for specified time
	//new browser window should has defined title. We can specify title in this way:
	//title, title*, *title, *title*
	public String switchToNewBrowserWindow(long timeOutInSeconds, String title) throws NoSuchWindowException	{	
		try		{
			Log.message("Waiting a new browser window for " + Long.toString(timeOutInSeconds) + " seconds. New window should have title " + title);
			String newHandle = awaiting.awaitCondition(timeOutInSeconds, 100, fluent.newWindowIsAppeared(title));
			synchronized (this) {
				changeActiveWindow(newHandle);
			}
			return newHandle;
		}
		catch (TimeoutException e)		{
			throw new NoSuchWindowException("There is no new browser window with title " + title + 
					" ! We have been waiting for "	+ Long.toString(timeOutInSeconds) + " seconds", e);
		}
	}	
	
	//returns handle of a new browser window that we have been waiting for time that specified in configuration
	//new browser window should has defined title. We can specify title in this way:
	//title, title*, *title, *title*
	public String switchToNewBrowserWindow(String title) throws NoSuchWindowException	{	
		BrowserWindowsTimeOuts timeOuts = windowTimeOuts.getTimeOuts();
		long timeOut = windowTimeOuts.getTimeOut(timeOuts.getNewBrowserWindowTimeOutSec(),windowTimeOuts.defaultTimeForNewWindow);
		return switchToNewBrowserWindow(timeOut, title);
	}
	
	//returns handle of a new browser window that we have been waiting for specified time
	//new browser window should has page that loads by specified URL
	public String switchToNewBrowserWindow(long timeOutInSeconds, URL url) throws NoSuchWindowException	{	
		try	{
			Log.message("Waiting a new browser window for " + Long.toString(timeOutInSeconds) + " seconds. New window should have page " +  
					" that is loaded by specified URL. Url is " + url.toString());
			String newHandle = awaiting.awaitCondition(timeOutInSeconds, 100, fluent.newWindowIsAppeared(url));
			synchronized (this) {
				changeActiveWindow(newHandle);
			}
			return newHandle;
		}
		catch (TimeoutException e)	{
			throw new NoSuchWindowException("There is no new browser window that loads by " + url.toString() + 
					" ! We have been waiting for "	+ Long.toString(timeOutInSeconds) + " seconds", e);
		}	
	}	
	
	//returns handle of a new browser window that we have been waiting for time that specified in configuration 
	//new browser window should has page that loads by specified URL
	public String switchToNewBrowserWindow(URL url) throws NoSuchWindowException	{	
		BrowserWindowsTimeOuts timeOuts = windowTimeOuts.getTimeOuts();
		long timeOut = windowTimeOuts.getTimeOut(timeOuts.getNewBrowserWindowTimeOutSec(),windowTimeOuts.defaultTimeForNewWindow);
		return switchToNewBrowserWindow(timeOut, url);
	}
	
	
	public synchronized void switchTo(String Handle) throws NoSuchWindowException	{
		changeActiveWindow(Handle);					
	}
	
	public synchronized String getWindowURLbyHandle(String handle) throws NoSuchWindowException	{
		changeActiveWindow(handle);
		return(driverEncapsulation.getWrappedDriver().getCurrentUrl());
	}
	
	public synchronized String getTitleByHandle(String handle)  throws NoSuchWindowException	{
		changeActiveWindow(handle);
		return (driverEncapsulation.getWrappedDriver().getTitle());
	}
	
	public synchronized void destroy()	{		
		swithcerList.remove(this);
		isAlive = false;
		
		List<SingleWindow> windowsToBeDestroyed = new ArrayList<SingleWindow>();
		windowsToBeDestroyed.addAll(openedWindows);
		for (SingleWindow window: windowsToBeDestroyed)	{
			window.destroy();
		}		
		//removes all windows that was generated by this switcher
		openedWindows.clear();		
	}
	
	public synchronized void close(String handle) throws UnclosedBrowserWindowException, NoSuchWindowException, UnhandledAlertException, UnreachableBrowserException	{
		BrowserWindowsTimeOuts timeOuts = windowTimeOuts.getTimeOuts();
		long timeOut = windowTimeOuts.getTimeOut(timeOuts.getWindowClosingTimeOutSec(),windowTimeOuts.defaultTime);
		
		try		{
			changeActiveWindow(handle);
			WebDriver driver = driverEncapsulation.getWrappedDriver();	
			driver.switchTo().window(handle).close();
		}
		catch (UnhandledAlertException|NoSuchWindowException e)		{
			throw e;
		}
		
		try	{
			awaiting.awaitCondition(timeOut, fluent.isClosed(handle));
		}
		catch (TimeoutException e) {
			throw new UnclosedBrowserWindowException("Browser window hasn't been closed!",e);
		}
		
		int actualWinCount = 0;
		try		{
			actualWinCount = getWindowHandles().size();
		}
		catch (WebDriverException e) { //if all windows are closed
			actualWinCount = 0;
		}
		finally		{
			if (actualWinCount==0)			{
				destroy();
				driverEncapsulation.destroy();
			}
		}
	}
	
	public Set<String> getWindowHandles() 	{
		return(driverEncapsulation.getWrappedDriver().getWindowHandles());		
	}

	protected synchronized void takeAPictureOfASevere(String handle, String Comment)	{
		changeActiveWindow(handle);
		Photographer.takeAPictureOfASevere(driverEncapsulation.getWrappedDriver(), Comment);	
	}
	
	protected synchronized void takeAPictureOfAWarning(String handle, String Comment)	{
		changeActiveWindow(handle);
		Photographer.takeAPictureOfAWarning(driverEncapsulation.getWrappedDriver(), Comment);	
	}
	
	protected synchronized void takeAPictureOfAnInfo(String handle, String Comment)	{
		changeActiveWindow(handle);
		Photographer.takeAPictureOfAnInfo(driverEncapsulation.getWrappedDriver(), Comment);
	}
	
	protected synchronized void takeAPictureOfAFine(String handle, String Comment)	{
		changeActiveWindow(handle);
		Photographer.takeAPictureOfAFine(driverEncapsulation.getWrappedDriver(), Comment);	
	}	
	
	public synchronized Alert getAlert(long secsToWait) throws NoAlertPresentException	{
		return(new AlertHandler(driverEncapsulation.getWrappedDriver(), secsToWait));
	}
	
	public synchronized Alert getAlert() throws NoAlertPresentException	{
		return(new AlertHandler(driverEncapsulation.getWrappedDriver()));
	}
	
	boolean isAlive()	{
		return isAlive;
	}
}