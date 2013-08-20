package org.primitive.webdriverincapsulations;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.logging.Log;
import org.primitive.webdriverincapsulations.factory.ChromeDriverIncapsulation;
import org.primitive.webdriverincapsulations.factory.FirefoxDriverIncapsulation;
import org.primitive.webdriverincapsulations.factory.HtmlUnitDriverIncapsulation;
import org.primitive.webdriverincapsulations.factory.InternetExplorerDriverIncapsulation;
import org.primitive.webdriverincapsulations.factory.OperaDriverIncapsulation;
import org.primitive.webdriverincapsulations.factory.RemoteWebDriverIncapsulation;
import org.primitive.webdriverincapsulations.factory.SafariDriverIncapsulation;


public class WebDriverIncapsulationFactory 
{
	/**
	 * @author s.tihomirov
	 *
	 */
	private static class IncapsulationInterceptor implements MethodInterceptor {

		@Override
		public Object intercept(Object proxy, Method ignored, Object[] args,
				MethodProxy methodProxy) throws Throwable {
			return methodProxy.invokeSuper(proxy, args);
		}

	}
	
	//get tests started with FireFoxDriver by default.
	private static final ESupportedDrivers defaultSupportedDriver 	= ESupportedDrivers.FIREFOX; 
	private static final Capabilities 	 defaultRemoteCapabilities  = DesiredCapabilities.firefox();
		
	private static Class<? extends WebDriverIncapsulation> getSuitableWebDriverClass(ESupportedDrivers webDriverMark)
	{
		Class<? extends WebDriverIncapsulation> suitableClass = null;
		if (webDriverMark == ESupportedDrivers.REMOTE)
		{
			suitableClass = RemoteWebDriverIncapsulation.class;
		}				
		
		if (webDriverMark == ESupportedDrivers.FIREFOX)
		{
			suitableClass = FirefoxDriverIncapsulation.class;
		}
		
		if (webDriverMark == ESupportedDrivers.CHROME)
		{
			suitableClass = ChromeDriverIncapsulation.class;
		}	
		
		if (webDriverMark == ESupportedDrivers.INTERNETEXPLORER)
		{
			suitableClass = InternetExplorerDriverIncapsulation.class;
		}
		
		if (webDriverMark == ESupportedDrivers.OPERA)
		{
			suitableClass = OperaDriverIncapsulation.class;
		}
		
		if (webDriverMark == ESupportedDrivers.SAFARI)
		{
			suitableClass = SafariDriverIncapsulation.class;
		}
		
		if (webDriverMark == ESupportedDrivers.HTMLUNIT)
		{
			suitableClass = HtmlUnitDriverIncapsulation.class;
		}
		
		return suitableClass;					
	}
	
	//the real factory method is there
	@SuppressWarnings("unchecked")
	private static <T extends WebDriverIncapsulation> T initInstance(Class<? extends WebDriverIncapsulation> instanseClass,
																Class<?>[] classParams, Object[] values)
	{
		IncapsulationInterceptor interceptor = new IncapsulationInterceptor();
		Enhancer enh = new Enhancer();
		enh.setCallback(interceptor);
		enh.setSuperclass(instanseClass);
		return (T) enh.create(classParams, values);
	}
	
	//It is the invocation of factory method with manually specified web driver and opening URL
	public static WebDriverIncapsulation initNewInstance(ESupportedDrivers webDriverMark, String url)
	{
		Class<? extends WebDriverIncapsulation> newInstanceClass = getSuitableWebDriverClass(webDriverMark);
		if (webDriverMark != ESupportedDrivers.REMOTE) //if there is web driver that can be started without any capabities
		{
			return initInstance(newInstanceClass, new Class[] {String.class} , new Object[] {url});
		}
		else //else it need to be run with some capabilities. We use capabilities of firefox in this situation
		{
			return initInstance(newInstanceClass, new Class[] {String.class, Capabilities.class} , new Object[] {url,defaultRemoteCapabilities});
		}
	}
	
	//It is the invocation of factory method with manually specified web driver, capabilities and opening URL
	public static WebDriverIncapsulation initNewInstance(ESupportedDrivers webDriverMark, Capabilities capabilities, String url)
	{
		Class<? extends WebDriverIncapsulation> newInstanceClass = getSuitableWebDriverClass(webDriverMark);
		return initInstance(newInstanceClass, new Class[] {String.class, Capabilities.class} , new Object[] {url,capabilities});
	}
	
	//It is the invocation of factory method with manually specified web driver, capabilities, remote address and opening URL
	//It is necessary to know - remote address will be used when RempteWebDriver is created.
	//For any other web driver it will be ignored.
	public static WebDriverIncapsulation initNewInstance(ESupportedDrivers webDriverMark, Capabilities capabilities, String url, URL remoteAdress)
	{
		Class<? extends WebDriverIncapsulation> newInstanceClass = getSuitableWebDriverClass(webDriverMark);
		if (webDriverMark == ESupportedDrivers.REMOTE)
		{	
			return initInstance(newInstanceClass, new Class[] {String.class, Capabilities.class, URL.class} , new Object[] {url,capabilities,remoteAdress});
		}
		else
		{
			Log.message("Remote address " + remoteAdress.toString() + " has been ignored");
			return initInstance(newInstanceClass, new Class[] {String.class, Capabilities.class} , new Object[] {url,capabilities});
		}		
	}
	
	private static Capabilities getCapabilitiesFromConfig(Configuration configuration)
	{
		Capabilities capabilities = configuration.getCapabilities();
		if (capabilities==null)
		{
			capabilities = defaultRemoteCapabilities;
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
	public static WebDriverIncapsulation initNewInstance(Configuration configuration, String url)
	{		
		ESupportedDrivers mark = getSupportedDriverFromConfig(configuration);
		
		Capabilities capabilities = getCapabilitiesFromConfig(configuration);
		
		String remoteAdress = configuration.getWebDriverSettings().getRemoteAddress();
		
		//classes of invocation parameters
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		//values of invocation parameters
		ArrayList<Object> values    = new ArrayList<Object>();
		
		classes.add(Configuration.class); //using configuration
		values.add(configuration);
		
		classes.add(String.class); //opening URL
		values.add(url);
		
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
		return initInstance(getSuitableWebDriverClass(mark), convertToClassArray(classes) , values.toArray());	
	}
	
	//It is the invocation of factory method with parameters that are specified in default configuration
	public static WebDriverIncapsulation initNewInstance(String url)
	{
		return initNewInstance(Configuration.byDefault,url);
	}
}
