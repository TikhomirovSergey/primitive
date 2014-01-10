package org.primitive.webdriverencapsulations;


import java.net.URL;
import java.util.Set;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.primitive.interfaces.IDestroyable;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.interfaces.ISingleBrowserWindow;
import org.primitive.webdriverencapsulations.interfaces.ITakesPictureOfItSelf;
import org.primitive.webdriverencapsulations.webdrivercomponents.WindowTool;



//It is performs actions on a single browser window
public final class SingleWindow implements Window, Navigation, ISingleBrowserWindow, ITakesPictureOfItSelf, IDestroyable{
	private WindowSwitcher nativeSwitcher;
	private String objectWindow;
	private WebDriverEncapsulation driverEncapsulation;
	private WindowTool windowTool;
	
    public static SingleWindow checkForInit(String handle, WindowSwitcher switcher)
    {   	
    	SingleWindow result = null;
    	try
    	{
    		for (SingleWindow objWindow: switcher.openedWindows)
    		{
    			if ((handle.equals(objWindow.objectWindow))&(objWindow.nativeSwitcher.equals(switcher)))
    			{
    				result=objWindow;
    				break;
    			}
    		}
    	}
    	catch(Exception e)
    	{
    		result = null;
    	}
    	return(result);
    	
    }
    
    private SingleWindow(WindowSwitcher switcher, String handle)
    {
		this.nativeSwitcher 		=  switcher;
		this.driverEncapsulation 	=  switcher.driverEncapsulation;
		this.objectWindow			=  handle;
		this.windowTool              = new WindowTool(driverEncapsulation.getWrappedDriver());
		switcher.openedWindows.add(this);
		takeAPictureOfAnInfo("New object has been made of browser window");
    }
       
    //Static constructor ¹1 - initialization of new window that will appear.
    public static SingleWindow initNewWindow(WindowSwitcher switcher) throws NoSuchWindowException
    {
    	try
    	{
			return(new SingleWindow(switcher, switcher.switchToNewBrowserWindow()));
    	}
    	catch (NoSuchWindowException e)
    	{
    		throw e;
    	}
    }
    
    //Static constructor ¹1.1
    public static SingleWindow initNewWindow(WindowSwitcher switcher, long secondsTimeOut) throws NoSuchWindowException
    {
    	try
    	{
			return(new SingleWindow(switcher, switcher.switchToNewBrowserWindow(secondsTimeOut)));
    	}
    	catch (NoSuchWindowException e)
    	{
    		throw e;
    	}
    }
    
    //Static constructor ¹2 - initialization of new window that will appear. 
    //We use either title of a window or piece of its title. Fragment is formatted as:
    //title*, *title, *title*
    public static SingleWindow initNewWindow(WindowSwitcher switcher, String title) throws NoSuchWindowException
    {
    	try
    	{
			return(new SingleWindow(switcher, switcher.switchToNewBrowserWindow(title)));
    	}
    	catch (NoSuchWindowException e)
    	{
    		throw e;
    	}
    } 
    
    //Static constructor ¹2.1
    public static SingleWindow initNewWindow(WindowSwitcher switcher, String title,  long secondsTimeOut) throws NoSuchWindowException
    {
    	try
    	{
			return(new SingleWindow(switcher, switcher.switchToNewBrowserWindow(secondsTimeOut, title)));
    	}
    	catch (NoSuchWindowException e)
    	{
    		throw e;
    	}
    }  
    
    //Static constructor ¹3 - initialization of new window that will appear. 
    //We use url of a loaded page.    
    public static SingleWindow initNewWindow(WindowSwitcher switcher, URL url) throws NoSuchWindowException
    {
    	try
    	{
			return(new SingleWindow(switcher, switcher.switchToNewBrowserWindow(url)));
    	}
    	catch (NoSuchWindowException e)
    	{
    		throw e;
    	}
    }   
    
    //Static constructor ¹3.1  
    public static SingleWindow initNewWindow(WindowSwitcher switcher, URL url, long secondsTimeOut) throws NoSuchWindowException
    {
    	try
    	{
			return(new SingleWindow(switcher, switcher.switchToNewBrowserWindow(secondsTimeOut, url)));
    	}
    	catch (NoSuchWindowException e)
    	{
    		throw e;
    	}
    }    
    
    //Static constructor ¹4 - initialization of new window object by its index. It is known that window is present 
    public static SingleWindow initWindowByIndex(WindowSwitcher switcher, int index) throws NoSuchWindowException
    {
    	try
    	{
			String handle = switcher.getBrowserWindowHandleByInex(index);
			SingleWindow  InitedWindow = checkForInit(handle, switcher);
			if (InitedWindow!=null)
			{
				return(InitedWindow);
			}
			else
			{	
	    		return(new SingleWindow(switcher, handle));
			}	
    	}
    	catch (NoSuchWindowException e)
    	{
    		throw e; 
    	}    
    }  
    
    private void requestToMe()
    {
    	nativeSwitcher.switchTo(objectWindow);
    }
    
    public void destroy()
    {
	    nativeSwitcher.openedWindows.remove(this);
		try 
		{
			finalize();
		} 
		catch (Throwable e) 
		{
			Log.warning("Some problem has occured while instance of single browser window was finalized! " + e.getMessage(),e);
		}
    }
    
    public synchronized void close() throws UnclosedBrowserWindowException, NoSuchWindowException, UnhandledAlertException, UnreachableBrowserException
    {
    	try
    	{
    		nativeSwitcher.close(objectWindow);
    		destroy();
    	}
    	catch (UnhandledAlertException|UnclosedBrowserWindowException e)
    	{
    		throw e;
    	}
    	catch (NoSuchWindowException e)
    	{
    		destroy();
    		throw e;
    	}
    }
    
    public synchronized void switchToMe() throws NoSuchWindowException
    {
    	requestToMe();
    }
    
    
    
    public synchronized String getWindowHandle()
    {
    	return(objectWindow);
    }
    
    public synchronized String getCurrentUrl() throws NoSuchWindowException
    {
		return(nativeSwitcher.getWindowURLbyHandle(objectWindow));
    }
    
    public synchronized String getTitle()
    {
    	return nativeSwitcher.getTitleByHandle(objectWindow);
    }
    
    public WebDriverEncapsulation getDriverEncapsulation()
    {
    	return(driverEncapsulation);
    }
    
    public synchronized void to(String link)
    {
    	requestToMe();
		windowTool.to(link);
    }
    
    public synchronized void forward()
    {
    	requestToMe();
    	windowTool.forward();
    }
    
    public synchronized void back()
    {
    	requestToMe();
    	windowTool.back();
    }

	@Override
	public synchronized void refresh() 
	{
		requestToMe();
		windowTool.refresh();		
	}

	@Override
	public synchronized void to(URL url) 
	{
		requestToMe();
		windowTool.to(url);
		
	}

	@Override
	public synchronized Point getPosition() 
	{
		requestToMe();
		return windowTool.getPosition();
	}

	@Override
	public synchronized Dimension getSize() 
	{
		requestToMe();
		return windowTool.getSize();
	}

	@Override
	public synchronized void maximize() 
	{
		requestToMe();
		windowTool.maximize();		
	}

	@Override
	public synchronized void setPosition(Point position) 
	{
		requestToMe();
		windowTool.setPosition(position);		
	}

	@Override
	public synchronized void setSize(Dimension size) 
	{
		requestToMe();
		windowTool.setSize(size);		
	} 
	
	@Override
	public synchronized void takeAPictureOfAnInfo(String Comment)
	{
		nativeSwitcher.takeAPictureOfAnInfo(objectWindow, Comment);
	}

	@Override
	public synchronized void takeAPictureOfAFine(String Comment)
	{
		nativeSwitcher.takeAPictureOfAFine(objectWindow, Comment);
	}
	
	@Override
	public synchronized void takeAPictureOfAWarning(String Comment)
	{
		nativeSwitcher.takeAPictureOfAWarning(objectWindow, Comment);
	}
	
	@Override
	public synchronized void takeAPictureOfASevere(String Comment)
	{
		nativeSwitcher.takeAPictureOfASevere(objectWindow, Comment);
	}
	
	public synchronized boolean exists()
	{
		if (!nativeSwitcher.isAlive()) 	{
			return false;
		}
		try {
			Set<String> handles = nativeSwitcher.getWindowHandles();
			return handles.contains(objectWindow);
		}
		catch (WebDriverException e) { //if there is no browser window
			return false;
		}
	}
	
	public WindowSwitcher getSwitcher()
	{
		return nativeSwitcher;
	}
}
