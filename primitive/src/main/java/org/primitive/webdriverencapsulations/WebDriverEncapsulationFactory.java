package org.primitive.webdriverencapsulations;

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
import org.primitive.webdriverencapsulations.factory.ChromeDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.FirefoxDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.HtmlUnitDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.InternetExplorerDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.OperaDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.RemoteWebDriverEncapsulation;
import org.primitive.webdriverencapsulations.factory.SafariDriverEncapsulation;


public class WebDriverEncapsulationFactory 
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
		
	private static Class<? extends WebDriverEncapsulation> getSuitableWebDriverClass(ESupportedDrivers webDriverMark)
	{
		Class<? extends WebDriverEncapsulation> suitableClass = null;
		if (webDriverMark == ESupportedDrivers.REMOTE)
		{
			suitableClass = RemoteWebDriverEncapsulation.class;
		}				
		
		if (webDriverMark == ESupportedDrivers.FIREFOX)
		{
			suitableClass = FirefoxDriverEncapsulation.class;
		}
		
		if (webDriverMark == ESupportedDrivers.CHROME)
		{
			suitableClass = ChromeDriverEncapsulation.class;
		}	
		
		if (webDriverMark == ESupportedDrivers.INTERNETEXPLORER)
		{
			suitableClass = InternetExplorerDriverEncapsulation.class;
		}
		
		if (webDriverMark == ESupportedDrivers.OPERA)
		{
			suitableClass = OperaDriverEncapsulation.class;
		}
		
		if (webDriverMark == ESupportedDrivers.SAFARI)
		{
			suitableClass = SafariDriverEncapsulation.class;
		}
		
		if (webDriverMark == ESupportedDrivers.HTMLUNIT)
		{
			suitableClass = HtmlUnitDriverEncapsulation.class;
		}
		
		return suitableClass;					
	}
	
	//the real factory method is there
	@SuppressWarnings("unchecked")
	private static <T extends WebDriverEncapsulation> T initInstance(Class<? extends WebDriverEncapsulation> instanseClass,
																Class<?>[] classParams, Object[] values)
	{
		IncapsulationInterceptor interceptor = new IncapsulationInterceptor();
		Enhancer enh = new Enhancer();
		enh.setCallback(interceptor);
		enh.setSuperclass(instanseClass);
		return (T) enh.create(classParams, values);
	}
	
	//It is the invocation of factory method with manually specified web driver and opening URL
	public static WebDriverEncapsulation initNewInstance(ESupportedDrivers webDriverMark, String url)
	{
		Class<? extends WebDriverEncapsulation> newInstanceClass = getSuitableWebDriverClass(webDriverMark);
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
	public static WebDriverEncapsulation initNewInstance(ESupportedDrivers webDriverMark, Capabilities capabilities, String url)
	{
		Class<? extends WebDriverEncapsulation> newInstanceClass = getSuitableWebDriverClass(webDriverMark);
		return initInstance(newInstanceClass, new Class[] {String.class, Capabilities.class} , new Object[] {url,capabilities});
	}
	
	//It is the invocation of factory method with manually specified web driver, capabilities, remote address and opening URL
	//It is necessary to know - remote address will be used when RempteWebDriver is created.
	//For any other web driver it will be ignored.
	public static WebDriverEncapsulation initNewInstance(ESupportedDrivers webDriverMark, Capabilities capabilities, String url, URL remoteAdress)
	{
		Class<? extends WebDriverEncapsulation> newInstanceClass = getSuitableWebDriverClass(webDriverMark);
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
	public static WebDriverEncapsulation initNewInstance(Configuration configuration, String url)
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
	public static WebDriverEncapsulation initNewInstance(String url)
	{
		return initNewInstance(Configuration.byDefault,url);
	}
}

