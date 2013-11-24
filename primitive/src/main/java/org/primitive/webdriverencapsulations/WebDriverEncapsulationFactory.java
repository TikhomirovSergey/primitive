package org.primitive.webdriverencapsulations;

import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.Capabilities;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.production.EFactoryProducts;


public class WebDriverEncapsulationFactory 
{
	//get tests started with FireFoxDriver by default.
	private static final ESupportedDrivers defaultSupportedDriver 	= ESupportedDrivers.FIREFOX; 
	
	//the real factory method is there
	@SuppressWarnings("unchecked")
	private static <T extends WebDriverEncapsulation> T initInstance(Class<? extends WebDriverEncapsulation> instanseClass,
																Class<?>[] classParams, Object[] values) throws Exception
	{

		Constructor<?>[] constructors = instanseClass.getConstructors();
		int constructorCount = constructors.length;
		for (int i=0; i<constructorCount; i++)
		{  //looking for constructor we need
			Class<?>[] params = constructors[i].getParameterTypes();  
			if (Arrays.equals(params, classParams))
			{
				return (T) constructors[i].newInstance(values);
			}
		}
			  
		throw new NoSuchMethodException("Wrong specified constructor of WebDriver encapsulation innstance! " + instanseClass.getSimpleName());
	}
	
	private static WebDriverEncapsulation getTo(WebDriverEncapsulation instance, String appUrl)
	{
		instance.getTo(appUrl);
		return instance;
	}
	
	//It is the invocation of factory method with manually specified web driver and opening URL
	//Application is not defined as URL. It is defined in another way
	public static WebDriverEncapsulation initNewInstance(ESupportedDrivers webDriverMark) throws Exception
	{
		Class<? extends WebDriverEncapsulation> newInstanceClass = EFactoryProducts.getProduct(webDriverMark);
		if (webDriverMark != ESupportedDrivers.REMOTE) //if there is web driver that can be started without any capabities
		{
			return initInstance(newInstanceClass, new Class[] {} , new Object[] {});
		}
		else //else it need to be run with some capabilities. We use capabilities of firefox in this situation
		{
			return initInstance(newInstanceClass, new Class[] {Capabilities.class} , new Object[] {EFactoryProducts.getCapabilities(webDriverMark)});
		}
	}
	
	//It is the invocation of factory method with manually specified web driver and opening URL
	public static WebDriverEncapsulation initNewInstance(ESupportedDrivers webDriverMark, String url) throws Exception
	{
		return getTo(initNewInstance(webDriverMark), url);
	}
	
	//It is the invocation of factory method with manually specified web driver, capabilities and opening URL
	//Application is not defined as URL. It is defined in another way
	public static WebDriverEncapsulation initNewInstance(ESupportedDrivers webDriverMark, Capabilities capabilities) throws Exception
	{
		Class<? extends WebDriverEncapsulation> newInstanceClass = EFactoryProducts.getProduct(webDriverMark);
		return initInstance(newInstanceClass, new Class[] {Capabilities.class} , new Object[] {capabilities});
	}
	
	public static WebDriverEncapsulation initNewInstance(ESupportedDrivers webDriverMark, Capabilities capabilities, String url) throws Exception
	{
		return getTo(initNewInstance(webDriverMark, capabilities), url);
	}
	
	//It is the invocation of factory method with manually specified web driver, capabilities, remote address and opening URL
	//It is necessary to know - remote address will be used when RempteWebDriver is created.
	//For any other web driver it will be ignored.
	//Application is not defined as URL. It is defined in another way
	public static WebDriverEncapsulation initNewInstance(ESupportedDrivers webDriverMark, Capabilities capabilities, URL remoteAdress) throws Exception
	{
		Class<? extends WebDriverEncapsulation> newInstanceClass = EFactoryProducts.getProduct(webDriverMark);
		if (webDriverMark == ESupportedDrivers.REMOTE)
		{	
			return initInstance(newInstanceClass, new Class[] {Capabilities.class, URL.class} , new Object[] {capabilities,remoteAdress});
		}
		else
		{
			Log.message("Remote address " + remoteAdress.toString() + " has been ignored");
			return initInstance(newInstanceClass, new Class[] { Capabilities.class} , new Object[] {capabilities});
		}		
	}
	
	//It is the invocation of factory method with manually specified web driver, capabilities, remote address and opening URL
	//It is necessary to know - remote address will be used when RempteWebDriver is created.
	//For any other web driver it will be ignored.
	public static WebDriverEncapsulation initNewInstance(ESupportedDrivers webDriverMark, Capabilities capabilities, String url, URL remoteAdress) throws Exception
	{
		return getTo(initNewInstance(webDriverMark, capabilities, remoteAdress),url);
	}
	
	private static Capabilities getCapabilitiesFromConfig(Configuration configuration, ESupportedDrivers mark)
	{
		Capabilities capabilities = configuration.getCapabilities();
		if (capabilities==null)
		{
			capabilities = EFactoryProducts.getCapabilities(mark);
		}
		return capabilities;
	}
	
	private static ESupportedDrivers getSupportedDriverFromConfig(Configuration configuration)
	{
		ESupportedDrivers mark = configuration.getWebDriverSettings().getSupoortedWebDriver();
		if (mark==null)
		{
			mark = defaultSupportedDriver;
		}	
		return mark;
	}
	
	private static Class<?>[] convertToClassArray(ArrayList<Class<?>> classList)
	{
		Class<?>[] classesArray = new Class<?>[classList.size()];
		for (Class<?> classParam: classList)
		{
			classesArray[classList.indexOf(classParam)] = classParam;
		}
		return classesArray;
	}
	
	//It is the invocation of factory method with parameters that are specified in configuration
	//Application is not defined as URL. It is defined in another way
	public static WebDriverEncapsulation initNewInstance(Configuration configuration) throws Exception
	{		
		ESupportedDrivers mark = getSupportedDriverFromConfig(configuration);
		
		Capabilities capabilities = getCapabilitiesFromConfig(configuration, mark);
		
		String remoteAdress = configuration.getWebDriverSettings().getRemoteAddress();
		
		//classes of invocation parameters
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		//values of invocation parameters
		ArrayList<Object> values    = new ArrayList<Object>();
		
		classes.add(Configuration.class); //using configuration
		values.add(configuration);
		
		classes.add(Capabilities.class); //capabilities
		values.add(capabilities);
		
		//remote address
		if (remoteAdress!=null)
		{
			if (mark==ESupportedDrivers.REMOTE)
			{
				try
				{
					classes.add(URL.class); //opening URL
					values.add(new URL(remoteAdress));
				}
				catch (MalformedURLException e) 
				{
					throw new IllegalArgumentException("Address of the remote computer " + remoteAdress + " is wrong specified! " + e.getMessage(),e);
				}
			}
			else
			{
				Log.message("Remote address " + remoteAdress.toString() + " has been ignored");
			}
		}		
		return initInstance(EFactoryProducts.getProduct(mark), convertToClassArray(classes) , values.toArray());	
	}
	
	//It is the invocation of factory method with parameters that are specified in configuration
	public static WebDriverEncapsulation initNewInstance(Configuration configuration, String appUrl) throws Exception
	{		
		return getTo(initNewInstance(configuration),appUrl);
	}
	
	//It is the invocation of factory method with parameters that are specified in default configuration
	//Application is not defined as URL. It is defined in another way
	public static WebDriverEncapsulation initNewInstance() throws Exception
	{
		return initNewInstance(Configuration.byDefault);
	}

	//It is the invocation of factory method with parameters that are specified in default configuration
	public static WebDriverEncapsulation initNewInstance(String url) throws Exception
	{
		return initNewInstance(Configuration.byDefault,url);
	}
	
}