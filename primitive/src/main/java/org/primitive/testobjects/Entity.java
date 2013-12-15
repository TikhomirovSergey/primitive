package org.primitive.testobjects;

import java.net.URL;
import java.util.ArrayList;


import org.primitive.exceptions.ConcstructTestObjectException;
import org.primitive.logging.Log;
import org.primitive.testobjects.testobject.TestObject;
import org.primitive.webdriverencapsulations.SingleWindow;
import org.primitive.webdriverencapsulations.WindowSwitcher;
import org.primitive.webdriverencapsulations.webdrivercomponents.Cookies;
import org.primitive.webdriverencapsulations.webdrivercomponents.Ime;
import org.primitive.webdriverencapsulations.webdrivercomponents.TimeOut;

//Using it you can model your web application as a complex aggregated object 
//it should only generate new objects in general
public abstract class Entity extends TestObject implements IDecomposable, IHasManyWindows{
	
	protected Cookies cookies;
	protected Ime ime;
	protected TimeOut timeOuts;
	protected WindowSwitcher nativeSwitcher;
	
	protected Entity(SingleWindow browserWindow) throws ConcstructTestObjectException
	{
		super(browserWindow);
		cookies  = driverEncapsulation.getCookies();
		ime      = driverEncapsulation.getIme();
		timeOuts = driverEncapsulation.getTimeOut();
		nativeSwitcher = browserWindow.getSwitcher();
	}
	
	private static Class<?>[] restructureParamArray(Class<?>[] original)
	{
		Class<?>[] constructParams = new Class<?> [original.length + 1];
		constructParams[0] = SingleWindow.class;
		for (int i=0; i < original.length; i++)
		{
			constructParams[i+1] = original[i];
		}
		return constructParams;
	}
	
	private static Object[] restructureValueArray(SingleWindow window, Object[] original)
	{
		Object[] constructValues = new Object[original.length + 1];
		constructValues[0]       = window;
		for (int i=0; i < original.length; i++)
		{
			constructValues[i+1] = original[i];
		}
		return constructValues;
	}
	
	//if application exists on only one browser window and not opens other pages in another windows:
	
	//using any accessible (!!!) Page constructor Entity creates page objects
	//Class "SingleWindow" should be first in the list of constructor parameters
	//"params" we specify without "SingleWindow" because it will be added by this method
	//We use the first opened window of the test application 
	protected  <T extends FunctionalPart> T get(Class<? extends FunctionalPart> partClass, Class<?>[] params, Object[] values) throws ConcstructTestObjectException
	{
		T page =  ObjectFactory.get(partClass, restructureParamArray(params), restructureValueArray(nativeWindow, values));
		page.originalEntity = this;
		return page;
	}	
	
	//- simple constructor
	@Override
	public <T extends FunctionalPart> T get(Class<? extends FunctionalPart> partClass) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {}; 
		Object[] values = new Object[] {}; 
		return get(partClass, params, values);
	}
	
	//- with specified frame index
	@Override
	public <T extends FunctionalPart> T get(Class<? extends FunctionalPart> partClass, Integer frameIndex) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {Integer.class}; 
		Object[] values = new Object[] {frameIndex};
		return get(partClass, params, values);
	}
	
	// - with specified path to any frame
	@Override
	public <T extends FunctionalPart> T get(Class<? extends FunctionalPart> partClass, String pathToFrame) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class}; 
		Object[] values = new Object[] {pathToFrame};
		return get(partClass, params, values);
	}
	
	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends FunctionalPart> T get(Class<? extends FunctionalPart> partClass, String pathToFrame, Long timeOutInSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class, Long.class}; 
		Object[] values = new Object[] {pathToFrame, timeOutInSec};
		return get(partClass, params, values);
	}
    
	//-----------------------------------------------------------------------------------------------------------------------------------------------------
	//if application opens other pages in another windows 
	//and some browser window is already opened
	
	//using any accessible (!!!) Page constructor Entity creates page objects
	//Class "SingleWindow" should be first in the list of constructor parameters
	//"params" we specify without "SingleWindow" because it will be added by this method
	//We use any opened window that specified by index
	protected  <T extends FunctionalPart> T get(Class<? extends FunctionalPart> partClass, Class<?>[] params, Object[] values, int windowIndex) throws ConcstructTestObjectException
	{
		T page = ObjectFactory.get(partClass, restructureParamArray(params), restructureValueArray(SingleWindow.initWindowByIndex(nativeSwitcher, windowIndex), values));
		page.originalEntity = this;
		return page;
	}
	
	//- simple constructor
	@Override
	public <T extends FunctionalPart> T getFromWinow(Class<? extends FunctionalPart> partClass, int windowIndex) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {}; 
		Object[] values = new Object[] {}; 
		return get(partClass, params, values, windowIndex);
	}
	
	//- with specified frame index
	@Override
	public <T extends FunctionalPart> T getFromWinow(Class<? extends FunctionalPart> partClass, Integer frameIndex, int windowIndex) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {Integer.class}; 
		Object[] values = new Object[] {frameIndex}; 
		return get(partClass, params, values, windowIndex);
	}
	
	// - with specified path to any frame
	@Override
	public <T extends FunctionalPart> T getFromWinow(String pathToFrame, Class<? extends FunctionalPart> partClass, int windowIndex) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class}; 
		Object[] values = new Object[] {pathToFrame}; 
		return get(partClass, params, values, windowIndex);
	}
	
	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends FunctionalPart> T getFromWinow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass, int windowIndex) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class, Long.class}; 
		Object[] values = new Object[] {pathToFrame, timeOutInSec}; 
		return get(partClass, params, values, windowIndex);
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------------------------------
	//if application opens other pages in another windows 
	//we will wait a new window for default time
	
	//using any accessible (!!!) Page constructor Entity creates page objects
	//Class "SingleWindow" should be first in the list of constructor parameters
	//"params" we specify without "SingleWindow" because it will be added by this method
	//We use any window that has appeared while default time was passing
	protected  <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, Class<?>[] params, Object[] values) throws ConcstructTestObjectException
	{
		T page = ObjectFactory.get(partClass, restructureParamArray(params), restructureValueArray(SingleWindow.initNewWindow(nativeSwitcher), values));
		page.originalEntity = this;
		return page;
	}
	
	//- simple constructor
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {}; 
		Object[] values = new Object[] {}; 
		return getFromNewWindow(partClass, params, values);
	}
	
	//- with specified frame index
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(Integer frameIndex, Class<? extends FunctionalPart> partClass) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {Integer.class}; 
		Object[] 	values = new Object[] {frameIndex}; 
		return getFromNewWindow(partClass, params, values);
	}
	
	// - with specified path to any frame
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Class<? extends FunctionalPart> partClass) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class}; 
		Object[] 	values = new Object[] {pathToFrame}; 
		return getFromNewWindow(partClass, params, values);
	}
	
	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class, Long.class}; 
		Object[] 	values = new Object[] {pathToFrame, timeOutInSec}; 
		return getFromNewWindow(partClass, params, values);
	}	
	//-----------------------------------------------------------------------------------------------------------------------------------------------------
	//if application opens other pages in another windows 
	//if time is specified in seconds
	
	//using any accessible (!!!) Page constructor Entity creates page objects
	//Class "SingleWindow" should be first in the list of constructor parameters
	//"params" we specify without "SingleWindow" because it will be added by this method
	//We use any window that has appeared while specified time was passing
	protected  <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, Class<?>[] params, Object[] values, long timeOutSec) throws ConcstructTestObjectException
	{
		T page = ObjectFactory.get(partClass, restructureParamArray(params), restructureValueArray(SingleWindow.initNewWindow(nativeSwitcher, timeOutSec), values));
		page.originalEntity = this;
		return page;
	}
	
	// - simple constructor
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, long timeOutSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {}; 
		Object[] values = new Object[] {}; 
		return getFromNewWindow(partClass,  params, values, timeOutSec);
	}	
	
	//- with specified frame index
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(Integer frameIndex, Class<? extends FunctionalPart> partClass, long timeOutSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {Integer.class}; 
		Object[] values = new Object[] {frameIndex}; 
		return getFromNewWindow(partClass,  params, values, timeOutSec);
	}
	
	// - with specified path to any frame
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Class<? extends FunctionalPart> partClass, long timeOutSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class}; 
		Object[] values = new Object[] {pathToFrame}; 
		return getFromNewWindow(partClass,  params, values, timeOutSec);
	}
	
	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass, long timeOutSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class, Long.class}; 
		Object[] values = new Object[] {pathToFrame, timeOutInSec}; 
		return getFromNewWindow(partClass,  params, values, timeOutSec);
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------------------------------	
	//if we need a new page on new browser window with specified title
	//We use either title of a window or piece of its title. Fragment is formatted as:
	//title*, *title, *title*		
	//here we use default time
	
	//using any accessible (!!!) Page constructor Entity creates page objects
	//Class "SingleWindow" should be first in the list of constructor parameters
	//"params" we specify without "SingleWindow" because it will be added by this method
	//We use some window that has appeared while default time was passing
	//this window should has the matching title 
	protected  <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, Class<?>[] params, Object[] values, String title) throws ConcstructTestObjectException
	{
		T page = ObjectFactory.get(partClass, restructureParamArray(params), restructureValueArray(SingleWindow.initNewWindow(nativeSwitcher, title), values));
		page.originalEntity = this;
		return page;
	}
	
	// - simple constructor
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, String title) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {}; 
		Object[] values = new Object[] {}; 
		return getFromNewWindow(partClass, params, values, title);
	}
	
	//- with specified frame index
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(Integer frameIndex, Class<? extends FunctionalPart> partClass, String title) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {Integer.class}; 
		Object[] values = new Object[] {frameIndex}; 
		return getFromNewWindow(partClass, params, values, title);
	}
	
	// - with specified path to any frame
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Class<? extends FunctionalPart> partClass, String title) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class}; 
		Object[] values = new Object[] {pathToFrame}; 
		return getFromNewWindow(partClass, params, values, title);
	}
	
	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass, String title) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class, Long.class}; 
		Object[] values = new Object[] {pathToFrame, timeOutInSec}; 
		return getFromNewWindow(partClass, params, values, title);
	}	
	
	//-----------------------------------------------------------------------------------------------------------------------------------------------------	
	//if we need a new page on new browser window with specified title
	//We use either title of a window or piece of its title. Fragment is formatted as:
	//title*, *title, *title*	
	
	//but here we use specified time
	
	//using any accessible (!!!) Page constructor Entity creates page objects
	//Class "SingleWindow" should be first in the list of constructor parameters
	//"params" we specify without "SingleWindow" because it will be added by this method
	//We use some window that has appeared while specified time was passing
	//this window should has the matching title 	
	protected  <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, Class<?>[] params, Object[] values, String title, long timeOutSec) throws ConcstructTestObjectException
	{
		T page = ObjectFactory.get(partClass, restructureParamArray(params), restructureValueArray(SingleWindow.initNewWindow(nativeSwitcher, title, timeOutSec), values));
		page.originalEntity = this;
		return page;
	}
	
	//- simple constructor
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, WindowSwitcher switcher, String title, long timeOutSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {}; 
		Object[] values = new Object[] {};
		return getFromNewWindow(partClass, params, values, title, timeOutSec);
	}	
	
	//- with specified frame index
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(Integer frameIndex, Class<? extends FunctionalPart> partClass, String title, long timeOutSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {Integer.class}; 
		Object[] values = new Object[] {frameIndex};
		return getFromNewWindow(partClass, params, values, title, timeOutSec);
	}
	
	// - with specified path to any frame
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Class<? extends FunctionalPart> partClass, String title, long timeOutSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class}; 
		Object[] values = new Object[] {pathToFrame};
		return getFromNewWindow(partClass, params, values, title, timeOutSec);
	}
	
	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass, String title, long timeOutSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class, Long.class}; 
		Object[] values = new Object[] {pathToFrame, timeOutInSec};
		return getFromNewWindow(partClass, params, values, title, timeOutSec);
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------------------------------		
	//if we need a new page that opens in another window and loads specified url
	//here we use default time
	
	//using any accessible (!!!) Page constructor Entity creates page objects
	//Class "SingleWindow" should be first in the list of constructor parameters
	//"params" we specify without "SingleWindow" because it will be added by this method
	//We use some window that has appeared while default time was passing
	//this window should has page that loaded by specified url
	protected  <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, Class<?>[] params, Object[] values, URL url) throws ConcstructTestObjectException
	{
		T page = ObjectFactory.get(partClass, restructureParamArray(params), restructureValueArray(SingleWindow.initNewWindow(nativeSwitcher, url), values));
		page.originalEntity = this;
		return page;
	}
	
	//- simple constructor
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, URL url) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {}; 
		Object[] values = new Object[] {}; 
		return getFromNewWindow(partClass, params, values, url);
	}

	//- with specified frame index
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(Integer frameIndex, Class<? extends FunctionalPart> partClass, URL url) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {Integer.class}; 
		Object[] values = new Object[] {frameIndex}; 
		return getFromNewWindow(partClass, params, values, url);
	}
		
	// - with specified path to any frame
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Class<? extends FunctionalPart> partClass, URL url) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class}; 
		Object[] values = new Object[] {pathToFrame}; 
		return getFromNewWindow(partClass, params, values, url);
	}
		
	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass, URL url) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class, Long.class}; 
		Object[] values = new Object[] {pathToFrame, timeOutInSec}; 
		return getFromNewWindow(partClass, params, values, url);
	}
		
	//-----------------------------------------------------------------------------------------------------------------------------------------------------		
	//if we need a new page that opens in another window and loads specified url
	//but here we use specified time
	
	//using any accessible (!!!) Page constructor Entity creates page objects
	//Class "SingleWindow" should be first in the list of constructor parameters
	//"params" we specify without "SingleWindow" because it will be added by this method
	//We use some window that has appeared while specified time was passing
	//this window should has page that loaded by specified url
	protected  <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, Class<?>[] params, Object[] values, URL url, long timeOutSec) throws ConcstructTestObjectException
	{
		T page = ObjectFactory.get(partClass, restructureParamArray(params), restructureValueArray(SingleWindow.initNewWindow(nativeSwitcher, url, timeOutSec), values));
		page.originalEntity = this;
		return page;
	}
	
	//- simple constructor
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, URL url, long timeOutSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {}; 
		Object[] values = new Object[] {}; 
		return getFromNewWindow(partClass, params, values, url, timeOutSec);
	}
	
	//- with specified frame index
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(Integer frameIndex, Class<? extends FunctionalPart> partClass, URL url, long timeOutSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {Integer.class}; 
		Object[] values = new Object[] {frameIndex}; 
		return getFromNewWindow(partClass, params, values, url, timeOutSec);
	}
		
	// - with specified path to any frame
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Class<? extends FunctionalPart> partClass, URL url, long timeOutSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class}; 
		Object[] values = new Object[] {pathToFrame}; 
		return getFromNewWindow(partClass, params, values, url, timeOutSec);
	}
		
	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass, URL url, long timeOutSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class, Long.class}; 
		Object[] values = new Object[] {pathToFrame, timeOutInSec}; 
		return getFromNewWindow(partClass, params, values, url, timeOutSec);
	}
	
	@Override  //destroys itself and all page objects
	public void destroy() 
	{
		
		ArrayList<SingleWindow> openedWindows = new ArrayList<SingleWindow>(FunctionalPart.parts.keySet());
		//attempt to destroy page objects
		for (SingleWindow window: openedWindows)
		{	//if some windows were created by this driver instance
			if ((window.getDriverEncapsulation()==driverEncapsulation))
			{
				FunctionalPart.destroyInitedPartsByWindow(window);
				
				if ((window!=nativeWindow)&(window.exists()))
				{	
					window.close();
				}	
			}
		}

		try 
		{
			this.finalize();
		} 
		catch (Throwable e) 
		{
			Log.warning("A problem with destroying of " + this.getClass().getSimpleName() + " instance has been found out! "+e.getMessage(),e);
		}
	}	
	
	//destroys an Entity instance and makes WebDriver quit 
	public void quit() 
	{
		destroy();
		SingleWindow.remove(nativeWindow);
		nativeSwitcher.destroy();
		driverEncapsulation.destroy();
	}
}

