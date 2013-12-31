package org.primitive.webdriverencapsulations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
																Class<?>[] classParams, Object[] values)
	{

		Constructor<?>[] constructors = instanseClass.getConstructors();
		int constructorCount = constructors.length;
		for (int i=0; i<constructorCount; i++)
		{  //looking for constructor we need
			Class<?>[] params = constructors[i].getParameterTypes();  
			if (Arrays.equals(params, classParams))
			{
				try {
					return (T) constructors[i].newInstance(values);
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
		}
			  
		throw new RuntimeException(new NoSuchMethodException("Wrong specified constructor of WebDriver encapsulation innstance! " + instanseClass.getSimpleName()));
	}
	
	//It is the invocation of factory method with manually specified web driver
	public static WebDriverEncapsulation initNewInstance(ESupportedDrivers webDriverMark)
	{
		Class<? extends WebDriverEncapsulation> newInstanceClass = EFactoryProducts.findProduct(webDriverMark).getProduct();
		if (webDriverMark != ESupportedDrivers.REMOTE) //if there is web driver that can be started without any capabities
		{
			return initInstance(newInstanceClass, new Class[] {} , new Object[] {});
		}
		else //else it need to be run with some capabilities. We use capabilities of firefox in this situation
		{
			return initInstance(newInstanceClass, new Class[] {Capabilities.class} , new Object[] {webDriverMark.getDefaultCapabilities()});
		}
	}
	
	//It is the invocation of factory method with manually specified web driver, capabilities
	public static WebDriverEncapsulation initNewInstance(ESupportedDrivers webDriverMark, Capabilities capabilities)
	{
		Class<? extends WebDriverEncapsulation> newInstanceClass = EFactoryProducts.findProduct(webDriverMark).getProduct();
		return initInstance(newInstanceClass, new Class[] {Capabilities.class} , new Object[] {capabilities});
	}
	
	//It is the invocation of factory method with manually specified web driver, capabilities, remote address
	//It is necessary to know - remote address will be used when RempteWebDriver is created.
	//For any other web driver it will be ignored.
	public static WebDriverEncapsulation initNewInstance(ESupportedDrivers webDriverMark, Capabilities capabilities, URL remoteAdress)
	{
		Class<? extends WebDriverEncapsulation> newInstanceClass = EFactoryProducts.findProduct(webDriverMark).getProduct();
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
	
	private static Capabilities getCapabilitiesFromConfig(Configuration configuration, ESupportedDrivers mark)
	{
		Capabilities capabilities = configuration.getCapabilities();
		if (capabilities==null)
		{
			capabilities = mark.getDefaultCapabilities();
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
	public static WebDriverEncapsulation initNewInstance(Configuration configuration)
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
		return initInstance(EFactoryProducts.findProduct(mark).getProduct(), convertToClassArray(classes) , values.toArray());	
	}
	
	//It is the invocation of factory method with parameters that are specified in default configuration
	public static WebDriverEncapsulation initNewInstance()
	{
		return initNewInstance(Configuration.byDefault);
	}	
}