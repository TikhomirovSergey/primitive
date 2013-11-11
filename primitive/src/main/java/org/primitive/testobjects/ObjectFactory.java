package org.primitive.testobjects;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.exceptions.ConcstructTestObjectException;
import org.primitive.testobjects.testobject.TestObject;
import org.primitive.webdriverencapsulations.SingleWindow;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.primitive.webdriverencapsulations.WebDriverEncapsulationFactory;
import org.primitive.webdriverencapsulations.WindowSwitcher;

//This class should be used for creation of a single page and application model instances
public final class ObjectFactory extends TestObject {

	private ObjectFactory(SingleWindow browserWindow)
			throws ConcstructTestObjectException {
		super(browserWindow);
	}

	@Override
	public void destroy() {
		return;
	}

	//static factory methods. Constructing new instances of some entities creating new WebDruver instances
	//This methods should be closed by inheritors	
	private static SingleWindow getFirstBrowserWindow(WebDriverEncapsulation driver, Class<? extends TestObject> requiredClass) throws ConcstructTestObjectException
	{
		try
		{
			WindowSwitcher switcher       = WindowSwitcher.get(driver);
			SingleWindow result = SingleWindow.initWindowByIndex(switcher, 0);
			result.switchToMe();
			return result;
		}
		catch (Exception e)
		{
			throw new ConcstructTestObjectException("Cannot create an instance of the " + requiredClass.getSimpleName(), e);
		}
	}
	
	//Steps: 1. Creates new web driver instance using specified parameters
	//       2. Loads specified URL
	//       3. Gets new entity instance from first browser window with loaded URL
	
	//This factory method uses any accessible Entity constructor
	//"SingleWindow" should be first in the list of constructor parameters 
	//Argument "params" should be defined without "SingleWindow" class because it will be added by this method
	//Argument "values" should be specified without any "SingleWindow" object
	public static <T extends Entity> T getEntity(Class<? extends Entity> entityClass, ESupportedDrivers supportedDriver, String urlToBeLoaded, Class<?>[] params, Object[] values) throws ConcstructTestObjectException
	{
		try
		{
			SingleWindow   statrtWindow   = getFirstBrowserWindow(WebDriverEncapsulationFactory.initNewInstance(supportedDriver, urlToBeLoaded),entityClass);
			return getProxy(entityClass, EntityInterceptor.class, restructureParamArrayUsingWindow(params),  restructureValueArrayUsingWindow(statrtWindow, values));
		}
		catch (ConcstructTestObjectException e)
		{
			throw e;
		}
		catch (Exception e) {
			throw new ConcstructTestObjectException(e.getMessage(), e);
		}
	}
	
	//Here default constructor is used
	public static <T extends Entity> T getEntity(Class<? extends Entity> entityClass, ESupportedDrivers supportedDriver, String urlToBeLoaded) throws ConcstructTestObjectException
	{
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {}; 
		return getEntity(entityClass, supportedDriver, urlToBeLoaded, params, values);
	}
		
	//This factory method uses any accessible Entity constructor
	//"SingleWindow" should be first in the list of constructor parameters 
	//Argument "params" should be defined without "SingleWindow" class because it will be added by this method
	//Argument "values" should be specified without any "SingleWindow" object
	public static <T extends Entity> T getEntity(Class<? extends Entity> entityClass, ESupportedDrivers supportedDriver, Capabilities capabilities, String urlToBeLoaded, Class<?>[] params, Object[] values) throws ConcstructTestObjectException
	{
		try
		{
			SingleWindow   statrtWindow   = getFirstBrowserWindow(WebDriverEncapsulationFactory.initNewInstance(supportedDriver, capabilities, urlToBeLoaded),entityClass);
			return getProxy(entityClass, EntityInterceptor.class, restructureParamArrayUsingWindow(params),  restructureValueArrayUsingWindow(statrtWindow, values));
		}
		catch (ConcstructTestObjectException e)
		{
			throw e;
		}
		catch (Exception e) {
			throw new ConcstructTestObjectException(e.getMessage(), e);
		}
	}
	
	//Here default constructor is used
	public static <T extends Entity> T getEntity(Class<? extends Entity> entityClass, ESupportedDrivers supportedDriver, Capabilities capabilities, String urlToBeLoaded) throws ConcstructTestObjectException
	{
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {}; 
		return getEntity(entityClass, supportedDriver, capabilities, urlToBeLoaded, params, values);
	}
	
	//This factory method uses any accessible Entity constructor
	//"SingleWindow" should be first in the list of constructor parameters 
	//Argument "params" should be defined without "SingleWindow" class because it will be added by this method
	//Argument "values" should be specified without any "SingleWindow" object
	public static <T extends Entity> T getEntity(Class<? extends Entity> entityClass, ESupportedDrivers supportedDriver, Capabilities capabilities, String urlToBeLoaded, URL remoteAddress, Class<?>[] params, Object[] values) throws ConcstructTestObjectException
	{
		try
		{
			SingleWindow   statrtWindow   = getFirstBrowserWindow(WebDriverEncapsulationFactory.initNewInstance(supportedDriver, capabilities, urlToBeLoaded, remoteAddress), entityClass);
			return getProxy(entityClass, EntityInterceptor.class, restructureParamArrayUsingWindow(params),  restructureValueArrayUsingWindow(statrtWindow, values));
		}
		catch (ConcstructTestObjectException e)
		{
			throw e;
		}
		catch (Exception e) {
			throw new ConcstructTestObjectException(e.getMessage(), e);
		}
	}
	
	//Here default constructor is used
	public static <T extends Entity> T getEntity(Class<? extends Entity> entityClass, ESupportedDrivers supportedDriver, Capabilities capabilities, String urlToBeLoaded, URL remoteAddress) throws ConcstructTestObjectException
	{
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {}; 
		return getEntity(entityClass, supportedDriver, capabilities, urlToBeLoaded, remoteAddress, params, values);
	}	
	
	//Steps: 1. Creates new web driver instance using configuration
	//       2. Loads specified URL
	//       3. Gets new entity instance from first browser window with loaded URL
	
	//This factory method uses any accessible Entity constructor
	//"SingleWindow" should be first in the list of constructor parameters 
	//Argument "params" should be defined without "SingleWindow" class because it will be added by this method
	//Argument "values" should be specified without any "SingleWindow" object
	//Default configuration is used below	
	public static <T extends Entity> T getEntity(Class<? extends Entity> entityClass, String url, Class<?>[] params, Object[] values) throws ConcstructTestObjectException
	{	
		try
		{
			SingleWindow   statrtWindow   = getFirstBrowserWindow(WebDriverEncapsulationFactory.initNewInstance(url),entityClass);
			return getProxy(entityClass, EntityInterceptor.class, restructureParamArrayUsingWindow(params), restructureValueArrayUsingWindow(statrtWindow, values));
		}
		catch (ConcstructTestObjectException e)
		{
			throw e;
		}
		catch (Exception e) {
			throw new ConcstructTestObjectException(e.getMessage(), e);
		}
	}
	
	//default constructor is used here
	public static <T extends Entity> T getEntity(Class<? extends Entity> entityClass, String url) throws ConcstructTestObjectException
	{	
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {}; 
		return getEntity(entityClass, url, params, values);
	}
	
	
	//This factory method uses any accessible Entity constructor
	//"SingleWindow" should be first in the list of constructor parameters 
	//Argument "params" should be defined without "SingleWindow" class because it will be added by this method
	//Argument "values" should be specified without any "SingleWindow" object
	//Specified configuration is used below	
	public static <T extends Entity> T getEntity(Class<? extends Entity> entityClass, Configuration config, String url, Class<?>[] params, Object[] values) throws ConcstructTestObjectException
	{	
		try
		{
			SingleWindow   statrtWindow   = getFirstBrowserWindow(WebDriverEncapsulationFactory.initNewInstance(config, url),entityClass);
			return getProxy(entityClass, EntityInterceptor.class, restructureParamArrayUsingWindow(params), restructureValueArrayUsingWindow(statrtWindow, values));
		}
		catch (ConcstructTestObjectException e)
		{
			throw e;
		}
		catch (Exception e) {
			throw new ConcstructTestObjectException(e.getMessage(), e);
		}
	}
	
	//default constructor is used here
	public static <T extends Entity> T getEntity(Class<? extends Entity> entityClass, Configuration config, String url) throws ConcstructTestObjectException
	{	//with default configuration
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {}; 
		return getEntity(entityClass, config, url, params, values);
	}	
	
	//-------
	//Steps: 1. Uses existing web driver instance
	//       2. Loads specified URL
	//       3. Gets new entity instance from first browser window with loaded URL
	//Specified configuration is used below	
	public static <T extends Entity> T getEntity(WebDriverEncapsulation driver, Class<? extends Entity> entityClass, String url, Class<?>[] params, Object[] values) throws ConcstructTestObjectException
	{
		SingleWindow   statrtWindow   = getFirstBrowserWindow(driver, entityClass);
		driver.getTo(url);
		return getProxy(entityClass, EntityInterceptor.class, restructureParamArrayUsingWindow(params), restructureValueArrayUsingWindow(statrtWindow, values));
	}
	
	//default constructor is used here
	public static <T extends Entity> T getEntity(WebDriverEncapsulation driver, Class<? extends Entity> entityClass, String url) throws ConcstructTestObjectException
	{	//with default configuration
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {}; 
		return getEntity(driver, entityClass, url, params, values);
	}
	
	//Steps: 1. Uses existing web driver instance and resets with specified configuration
	//       2. Loads specified URL
	//       3. Gets new entity instance from first browser window with loaded URL
	//Specified configuration is used below	
	public static <T extends Entity> T getEntity(WebDriverEncapsulation driver, Class<? extends Entity> entityClass, Configuration config, String url,  Class<?>[] params, Object[] values) throws ConcstructTestObjectException
	{
		driver.resetAccordingTo(config);
		SingleWindow   statrtWindow   = getFirstBrowserWindow(driver, entityClass);
		driver.getTo(url);
		return getProxy(entityClass, EntityInterceptor.class, restructureParamArrayUsingWindow(params), restructureValueArrayUsingWindow(statrtWindow, values));
	}
	
	//default constructor is used here
	public static <T extends Entity> T getEntity(WebDriverEncapsulation driver, Class<? extends Entity> entityClass, Configuration config, String url) throws ConcstructTestObjectException
	{	//with default configuration
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {}; 
		return getEntity(driver, entityClass, config, url, params, values);
	}
	
	private static Class<?>[] restructureParamArrayUsingWindow(Class<?>[] original)
	{
		Class<?>[] constructParams = new Class<?> [original.length + 1];
		constructParams[0] = SingleWindow.class;
		for (int i=0; i < original.length; i++)
		{
			constructParams[i+1] = original[i];
		}
		return constructParams;
	}
	
	private static Object[] restructureValueArrayUsingWindow(SingleWindow window, Object[] original)
	{
		Object[] constructValues = new Object[original.length + 1];
		constructValues[0]       = window;
		for (int i=0; i < original.length; i++)
		{
			constructValues[i+1] = original[i];
		}
		return constructValues;
	}	
	
	//Creation of any Page instance:
	//- using any accessible constructor:
	protected static  <T extends FunctionalPart> T getPage(Class<? extends FunctionalPart> partClass, Class<?>[] paramClasses, Object[] paramValues) throws ConcstructTestObjectException
	{
		T page = getProxy(partClass, InteractiveInterceptor.class, paramClasses, paramValues);
		return page;
	}
	
	//There are some situations when the Page instance can be created by itself
	
	//With a browser window only. 
	//- using new web driver instance:
	public static <T extends FunctionalPart> T getPage(Class<? extends FunctionalPart> partClass, ESupportedDrivers supportedDriver, String urlToBeLoaded) throws ConcstructTestObjectException
	{
		try
		{
			SingleWindow   pageWindow   = getFirstBrowserWindow(WebDriverEncapsulationFactory.initNewInstance(supportedDriver, urlToBeLoaded),partClass);
			return  getPage(partClass, new Class[] {SingleWindow.class},  new Object[] {pageWindow});
		}
		catch (ConcstructTestObjectException e)
		{
			throw e;
		}
		catch (Exception e) {
			throw new ConcstructTestObjectException(e.getMessage(), e);
		}		
	}
	
	public static <T extends FunctionalPart> T getPage(Class<? extends FunctionalPart> partClass, ESupportedDrivers supportedDriver,  Capabilities capabilities, String urlToBeLoaded) throws ConcstructTestObjectException
	{
		try
		{
			SingleWindow   pageWindow   = getFirstBrowserWindow(WebDriverEncapsulationFactory.initNewInstance(supportedDriver, capabilities, urlToBeLoaded),partClass);
			return  getPage(partClass, new Class[] {SingleWindow.class},  new Object[] {pageWindow});
		}
		catch (ConcstructTestObjectException e)
		{
			throw e;
		}
		catch (Exception e) {
			throw new ConcstructTestObjectException(e.getMessage(), e);
		}	
	}
	
	public static <T extends FunctionalPart> T getPage(Class<? extends FunctionalPart> partClass, ESupportedDrivers supportedDriver,  Capabilities capabilities, String urlToBeLoaded, URL remoteAddress) throws ConcstructTestObjectException
	{
		try
		{
			SingleWindow   pageWindow   = getFirstBrowserWindow(WebDriverEncapsulationFactory.initNewInstance(supportedDriver, capabilities, urlToBeLoaded, remoteAddress),partClass);
			return  getPage(partClass, new Class[] {SingleWindow.class},  new Object[] {pageWindow});
		}
		catch (ConcstructTestObjectException e)
		{
			throw e;
		}
		catch (Exception e) {
			throw new ConcstructTestObjectException(e.getMessage(), e);
		}	
	}
	
	// - using default configuration
	public static <T extends FunctionalPart> T getPage(Class<? extends FunctionalPart> partClass, String urlToBeLoaded) throws ConcstructTestObjectException
	{
		try
		{
			SingleWindow   pageWindow   = getFirstBrowserWindow(WebDriverEncapsulationFactory.initNewInstance(urlToBeLoaded),partClass);
			return  getPage(partClass, new Class[] {SingleWindow.class},  new Object[] {pageWindow});
		}
		catch (ConcstructTestObjectException e)
		{
			throw e;
		}
		catch (Exception e) {
			throw new ConcstructTestObjectException(e.getMessage(), e);
		}	
	}
	
	// - using specified configuration
	public static <T extends FunctionalPart> T getPage(Class<? extends FunctionalPart> partClass, Configuration config, String urlToBeLoaded) throws ConcstructTestObjectException
	{
		try
		{
			SingleWindow   pageWindow   = getFirstBrowserWindow(WebDriverEncapsulationFactory.initNewInstance(config, urlToBeLoaded),partClass);
			return  getPage(partClass, new Class[] {SingleWindow.class},  new Object[] {pageWindow});
		}
		catch (ConcstructTestObjectException e)
		{
			throw e;
		}
		catch (Exception e) {
			throw new ConcstructTestObjectException(e.getMessage(), e);
		}	
	}	
	
}
