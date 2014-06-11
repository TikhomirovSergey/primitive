package org.primitive.model;

import java.net.URL;

import net.sf.cglib.proxy.MethodInterceptor;

import org.openqa.selenium.Capabilities;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.webdriver.ESupportedDrivers;
import org.primitive.model.abstraction.ModelObjectInterceptor;
import org.primitive.model.interfaces.IDecomposable;
import org.primitive.proxy.EnhancedProxyFactory;
import org.primitive.webdriverencapsulations.SingleWindow;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.primitive.webdriverencapsulations.WindowManager;

//This class should be used for creation of a single page and application model instances
public final class ObjectFactory {
	/**
	 * An interceptor for {@link Entity} inheritor defined by user. Defined for
	 * each thread
	 */
	private final static ThreadLocal<Class<? extends MethodInterceptor>> definedInterceptorForEntities = 
			new ThreadLocal<Class<? extends MethodInterceptor>>();
	/**
	 * An interceptor for {@link FunctionalPart} inheritor defined by user.
	 * Defined for each thread
	 */
	private static ThreadLocal<Class<? extends MethodInterceptor>> definedInteractiveInterceptor = 
			new ThreadLocal<Class<? extends MethodInterceptor>>();
	
	@SuppressWarnings("unchecked")
	private static <T extends MethodInterceptor> T  getInterceptorFromThreadLocal(
			ThreadLocal<Class<? extends MethodInterceptor>> from,
			Class<? extends MethodInterceptor> defaultInterceptorClass) {
		try {
			if (from.get() == null) {
				return (T) defaultInterceptorClass.newInstance();
			}
			return (T) from.get().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static <T extends MethodInterceptor> T getEntityInterceptor() {
		return  getInterceptorFromThreadLocal(
				definedInterceptorForEntities, ModelObjectInterceptor.class);
	}
	
	private static <T extends MethodInterceptor> T getInteractiveInterceptor(){
		return getInterceptorFromThreadLocal(
				definedInteractiveInterceptor, InteractiveInterceptor.class);
	}
	
	private static void resetInterceptor(
			ThreadLocal<Class<? extends MethodInterceptor>> to,
			Class<? extends MethodInterceptor> interceptorClass) {
		to.set(interceptorClass);
	}

	/**
	 * Resets iterceptor class for {@link Entity}
	 */
	public static void resetEntityInterceptor(Class<? extends ModelObjectInterceptor> interceptorClass){
		resetInterceptor(definedInterceptorForEntities, interceptorClass);
	}
	
	/**
	 * Resets iterceptor class for {@link FunctionalPart}
	 */
	public static void resetInteractiveInterceptor(Class<? extends InteractiveInterceptor> interceptorClass){
		resetInterceptor(definedInteractiveInterceptor, interceptorClass);
	}

	// static factory methods. Constructing new instances of some entities
	// creating new WebDruver instances
	// This methods should be closed by inheritors
	private static SingleWindow getFirstBrowserWindow(
			WebDriverEncapsulation driver, Class<?> requiredClass){
		try {
			WindowManager switcher = new WindowManager(driver);
			SingleWindow result = SingleWindow.initWindowByIndex(switcher, 0);
			result.switchToMe();
			return result;
		} catch (Exception e) {
			driver.destroy();
			throw new RuntimeException(e);
		}
	}

	// The same action with the URL to be opened
	private static SingleWindow getFirstBrowserWindow(
			WebDriverEncapsulation driver, Class<?> requiredClass,
			String urlToBeOpened) {
		try {
			driver.getTo(urlToBeOpened);
		} catch (RuntimeException e) {
			driver.destroy();
		}
		return getFirstBrowserWindow(driver, requiredClass);
	}

	// Steps: 1. Creates new web driver instance using specified parameters
	// 2. Loads specified URL
	// 3. Gets new entity instance from first browser window with loaded URL

	// This factory method uses any accessible Entity constructor
	// "SingleWindow" should be first in the list of constructor parameters
	// Argument "params" should be defined without "SingleWindow" class because
	// it will be added by this method
	// Argument "values" should be specified without any "SingleWindow" object
	public static <T extends Entity> T getEntity(Class<T> entityClass,
			ESupportedDrivers supportedDriver, String urlToBeLoaded,
			Class<?>[] params, Object[] values){
		
		SingleWindow statrtWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(supportedDriver), entityClass,
				urlToBeLoaded);
		
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	// the same without any URL
	public static <T extends Entity> T getEntity(Class<T> entityClass,
			ESupportedDrivers supportedDriver, Class<?>[] params,
			Object[] values) {
		
		SingleWindow statrtWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(supportedDriver), entityClass);
		
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	// Here default constructor is used
	public static <T extends Entity> T getEntity(Class<T> entityClass,
			ESupportedDrivers supportedDriver, String urlToBeLoaded){
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(entityClass, supportedDriver, urlToBeLoaded, params,
				values);
	}

	public static <T extends Entity> T getEntity(Class<T> entityClass,
			ESupportedDrivers supportedDriver){
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(entityClass, supportedDriver, params, values);
	}

	// This factory method uses any accessible Entity constructor
	// "SingleWindow" should be first in the list of constructor parameters
	// Argument "params" should be defined without "SingleWindow" class because
	// it will be added by this method
	// Argument "values" should be specified without any "SingleWindow" object
	public static <T extends Entity> T getEntity(Class<T> entityClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			String urlToBeLoaded, Class<?>[] params, Object[] values){
		SingleWindow statrtWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(supportedDriver, capabilities),
				entityClass, urlToBeLoaded);
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	// the same without any URL
	public static <T extends Entity> T getEntity(Class<T> entityClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			Class<?>[] params, Object[] values){
		
		SingleWindow statrtWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(supportedDriver, capabilities),
				entityClass);
		
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	// Here default constructor is used
	public static <T extends Entity> T getEntity(Class<T> entityClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			String urlToBeLoaded) {
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(entityClass, supportedDriver, capabilities,
				urlToBeLoaded, params, values);
	}

	public static <T extends Entity> T getEntity(Class<T> entityClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities){
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(entityClass, supportedDriver, capabilities, params,
				values);
	}

	// This factory method uses any accessible Entity constructor
	// "SingleWindow" should be first in the list of constructor parameters
	// Argument "params" should be defined without "SingleWindow" class because
	// it will be added by this method
	// Argument "values" should be specified without any "SingleWindow" object
	public static <T extends Entity> T getEntity(Class<T> entityClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			String urlToBeLoaded, URL remoteAddress, Class<?>[] params,
			Object[] values) {
		
		SingleWindow statrtWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(supportedDriver, capabilities,
						remoteAddress), entityClass, urlToBeLoaded);
		
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	public static <T extends Entity> T getEntity(Class<T> entityClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			URL remoteAddress, Class<?>[] params, Object[] values){
		
		SingleWindow statrtWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(supportedDriver, capabilities,
						remoteAddress), entityClass);
		
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	// Here default constructor is used
	public static <T extends Entity> T getEntity(Class<T> entityClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			String urlToBeLoaded, URL remoteAddress){
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(entityClass, supportedDriver, capabilities,
				urlToBeLoaded, remoteAddress, params, values);
	}

	public static <T extends Entity> T getEntity(Class<T> entityClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			URL remoteAddress) {
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(entityClass, supportedDriver, capabilities,
				remoteAddress, params, values);
	}

	// Steps: 1. Creates new web driver instance using configuration
	// 2. Loads specified URL
	// 3. Gets new entity instance from first browser window with loaded URL

	// This factory method uses any accessible Entity constructor
	// "SingleWindow" should be first in the list of constructor parameters
	// Argument "params" should be defined without "SingleWindow" class because
	// it will be added by this method
	// Argument "values" should be specified without any "SingleWindow" object
	// Default configuration is used below
	public static <T extends Entity> T getEntity(Class<T> entityClass,
			String url, Class<?>[] params, Object[] values) {
		
		SingleWindow statrtWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(Configuration.byDefault),
				entityClass, url);
		
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	public static <T extends Entity> T getEntity(Class<T> entityClass,
			Class<?>[] params, Object[] values){
		
		SingleWindow statrtWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(Configuration.byDefault),
				entityClass);
		
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	// default constructor is used here
	public static <T extends Entity> T getEntity(Class<T> entityClass,
			String url)  {
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(entityClass, url, params, values);
	}

	public static <T extends Entity> T getEntity(Class<T> entityClass)
			{
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(entityClass, params, values);
	}

	// This factory method uses any accessible Entity constructor
	// "SingleWindow" should be first in the list of constructor parameters
	// Argument "params" should be defined without "SingleWindow" class because
	// it will be added by this method
	// Argument "values" should be specified without any "SingleWindow" object
	// Specified configuration is used below
	public static <T extends Entity> T getEntity(Class<T> entityClass,
			Configuration config, String url, Class<?>[] params, Object[] values)
			 {
		
		SingleWindow statrtWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(config), entityClass, url);
		
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	public static <T extends Entity> T getEntity(Class<T> entityClass,
			Configuration config, Class<?>[] params, Object[] values) {
		
		SingleWindow statrtWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(config), entityClass);
		
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	// default constructor is used here
	public static <T extends Entity> T getEntity(Class<T> entityClass,
			Configuration config, String url){ // with default configuration
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(entityClass, config, url, params, values);
	}

	// default constructor is used here
	public static <T extends Entity> T getEntity(Class<T> entityClass,
			Configuration config) { // with
																			// default
																			// configuration
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(entityClass, config, params, values);
	}

	// -------
	// Steps: 1. Uses existing web driver instance
	// 2. Loads specified URL
	// 3. Gets new entity instance from first browser window with loaded URL
	// Specified configuration is used below
	public static <T extends Entity> T getEntity(WebDriverEncapsulation driver,
			Class<T> entityClass, String url, Class<?>[] params, Object[] values){
		
		SingleWindow statrtWindow = getFirstBrowserWindow(driver, entityClass,
				url);
		
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	public static <T extends Entity> T getEntity(WebDriverEncapsulation driver,
			Class<T> entityClass, Class<?>[] params, Object[] values){
		
		SingleWindow statrtWindow = getFirstBrowserWindow(driver, entityClass);
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	// default constructor is used here
	public static <T extends Entity> T getEntity(WebDriverEncapsulation driver,
			Class<T> entityClass, String url){ // with default configuration
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(driver, entityClass, url, params, values);
	}

	public static <T extends Entity> T getEntity(WebDriverEncapsulation driver,
			Class<T> entityClass) { // with
																			// default
																			// configuration
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(driver, entityClass, params, values);
	}

	// Steps: 1. Uses existing web driver instance and resets with specified
	// configuration
	// 2. Loads specified URL
	// 3. Gets new entity instance from first browser window with loaded URL
	// Specified configuration is used below
	public static <T extends Entity> T getEntity(WebDriverEncapsulation driver,
			Class<T> entityClass, Configuration config, String url,
			Class<?>[] params, Object[] values){
		
		driver.resetAccordingTo(config);
		SingleWindow statrtWindow = getFirstBrowserWindow(driver, entityClass,
				url);
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	public static <T extends Entity> T getEntity(WebDriverEncapsulation driver,
			Class<T> entityClass, Configuration config, Class<?>[] params,
			Object[] values)  {
		
		driver.resetAccordingTo(config);
		SingleWindow statrtWindow = getFirstBrowserWindow(driver, entityClass);
		return EnhancedProxyFactory.getProxy(entityClass,
				restructureParamArrayUsingWindow(params),
				restructureValueArrayUsingWindow(statrtWindow, values), getEntityInterceptor());
	}

	// default constructor is used here
	public static <T extends Entity> T getEntity(WebDriverEncapsulation driver,
			Class<T> entityClass, Configuration config, String url){ // with default configuration
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(driver, entityClass, config, url, params, values);
	}

	public static <T extends Entity> T getEntity(WebDriverEncapsulation driver,
			Class<T> entityClass, Configuration config){ // with default configuration
		Class<?>[] params = new Class<?>[] {};
		Object[] values = new Object[] {};
		return getEntity(driver, entityClass, config, params, values);
	}

	private static Class<?>[] restructureParamArrayUsingWindow(
			Class<?>[] original) {
		Class<?>[] constructParams = new Class<?>[original.length + 1];
		constructParams[0] = SingleWindow.class;
		for (int i = 0; i < original.length; i++) {
			constructParams[i + 1] = original[i];
		}
		return constructParams;
	}

	private static Object[] restructureValueArrayUsingWindow(
			SingleWindow window, Object[] original) {
		Object[] constructValues = new Object[original.length + 1];
		constructValues[0] = window;
		for (int i = 0; i < original.length; i++) {
			constructValues[i + 1] = original[i];
		}
		return constructValues;
	}

	// Creation of any decomposable instance:
	// - using any accessible constructor:
	static <T extends IDecomposable> T get(Class<T> partClass,
			Class<?>[] paramClasses, Object[] paramValues){
		T composable = EnhancedProxyFactory.getProxy(partClass,
				paramClasses, paramValues, getInteractiveInterceptor());
		return composable;
	}

	// There are some situations when the Page instance can be created by itself

	// With a browser window only.
	// - using new web driver instance:
	public static <T extends FunctionalPart> T get(Class<T> partClass,
			ESupportedDrivers supportedDriver, String urlToBeLoaded){
		SingleWindow pageWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(supportedDriver), partClass,
				urlToBeLoaded);
		
		return get(partClass, new Class[] { SingleWindow.class },
				new Object[] { pageWindow });
	}

	public static <T extends FunctionalPart> T get(Class<T> partClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			String urlToBeLoaded) {
		
		SingleWindow pageWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(supportedDriver, capabilities),
				partClass, urlToBeLoaded);
		
		return get(partClass, new Class[] { SingleWindow.class },
				new Object[] { pageWindow });
	}

	public static <T extends FunctionalPart> T get(Class<T> partClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			String urlToBeLoaded, URL remoteAddress){
		
		SingleWindow pageWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(supportedDriver, capabilities,
						remoteAddress), partClass, urlToBeLoaded);
		return get(partClass, new Class[] { SingleWindow.class },
				new Object[] { pageWindow });
	}

	// - using default configuration
	public static <T extends FunctionalPart> T get(Class<T> partClass,
			String urlToBeLoaded) {
		
		SingleWindow pageWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(Configuration.byDefault), partClass,
				urlToBeLoaded);
		return get(partClass, new Class[] { SingleWindow.class },
				new Object[] { pageWindow });
	}

	// - using specified configuration
	public static <T extends FunctionalPart> T get(Class<T> partClass,
			Configuration config, String urlToBeLoaded) {
		
		SingleWindow pageWindow = getFirstBrowserWindow(
				new WebDriverEncapsulation(config), partClass, urlToBeLoaded);
		return get(partClass, new Class[] { SingleWindow.class },
				new Object[] { pageWindow });
	}

}
