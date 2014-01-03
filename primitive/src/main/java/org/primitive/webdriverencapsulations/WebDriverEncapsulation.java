package org.primitive.webdriverencapsulations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.interfaces.IConfigurable;
import org.primitive.interfaces.IDestroyable;
import org.primitive.interfaces.IExtendedWebDriverEventListener;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.firing.ExtendedEventFiringWebDriver;
import org.primitive.webdriverencapsulations.localserver.LocalRemoteServerInstance;
import org.primitive.webdriverencapsulations.ui.WebElementHighLighter;
import org.primitive.webdriverencapsulations.webdrivercomponents.Awaiting;
import org.primitive.webdriverencapsulations.webdrivercomponents.BrowserLogs;
import org.primitive.webdriverencapsulations.webdrivercomponents.Cookies;
import org.primitive.webdriverencapsulations.webdrivercomponents.ElementVisibility;
import org.primitive.webdriverencapsulations.webdrivercomponents.FrameSupport;
import org.primitive.webdriverencapsulations.webdrivercomponents.Ime;
import org.primitive.webdriverencapsulations.webdrivercomponents.Interaction;
import org.primitive.webdriverencapsulations.webdrivercomponents.PageFactoryWorker;
import org.primitive.webdriverencapsulations.webdrivercomponents.ScriptExecutor;
import org.primitive.webdriverencapsulations.webdrivercomponents.TimeOut;


public class WebDriverEncapsulation implements IDestroyable, IConfigurable, WrapsDriver, HasCapabilities
{
	  //get tests started with FireFoxDriver by default.
	  private static ESupportedDrivers defaultSupportedDriver 	= ESupportedDrivers.FIREFOX;
	  private final String chromeBrowser    = DesiredCapabilities.chrome().getBrowserName();
	  private final String internetExplorer = DesiredCapabilities.internetExplorer().getBrowserName();	
	  private final String phantomJS        = DesiredCapabilities.phantomjs().getBrowserName();	
	  
	  private ExtendedEventFiringWebDriver firingDriver;
	  protected Configuration configuration = Configuration.byDefault;
	  	  
	  private Awaiting awaiting;	  
	  private WindowTimeOuts windowTimeOuts;	 
	  private PageFactoryWorker pageFactoryWorker;	  
	  private ScriptExecutor scriptExecutor;	  
	  private FrameSupport frameSupport;	    
	  private Cookies cookies;	  
	  private TimeOut timeout;	  
	  private BrowserLogs logs;	  
	  private Ime ime;
	  private Interaction interaction;
	  private ElementVisibility elementVisibility;
	  private WebdriverInnerListener webInnerListener;
	  private WebElementHighLighter elementHighLighter;
  	  
	  
	  //methods that support constructors functionality:
	  protected void setSystemProprtyByCapabilities(Capabilities capabilities)
	  {
		  String brofserName = capabilities.getBrowserName();			  
		  
		  if (chromeBrowser.equals(brofserName))
		  {
			  ESupportedDrivers.CHROME.setSystemProperty(); 
		  }
		  if (internetExplorer.equals(brofserName))
		  {
			  ESupportedDrivers.INTERNETEXPLORER.setSystemProperty();
		  }
		  if (phantomJS.equals(brofserName))
		  {
			  ESupportedDrivers.PHANTOMJS.setSystemProperty();
		  }	  
	  }
	  
	  private void constructorBody(ESupportedDrivers supporteddriver, Capabilities capabilities)
	  {
		  if (supporteddriver.equals(ESupportedDrivers.REMOTE))
		  {   //if there is RemoteWebDriver and capabilities that require service
			  LocalRemoteServerInstance.startLocally();
			  setSystemProprtyByCapabilities(capabilities);
		  }
		  else
		  {	  
			  supporteddriver.setSystemProperty();	  
		  }	
		  createWebDriver(supporteddriver.getUsingWebDriverClass(), capabilities);
	  }
	  
	  //creates instance by specified driver
	  public WebDriverEncapsulation(ESupportedDrivers supporteddriver)
	  {
		  constructorBody(supporteddriver, supporteddriver.getDefaultCapabilities());
	  }
	  
	  private void constructorBody(ESupportedDrivers supporteddriver, Capabilities capabilities, URL remoteAddress)
	  {
		  if (supporteddriver.equals(ESupportedDrivers.REMOTE))
		  {   //if there is RemoteWebDriver that uses remote service
			  createWebDriver(supporteddriver.getUsingWebDriverClass(), new Class[] {URL.class, Capabilities.class}, new Object[] {remoteAddress, capabilities});
		  }
		  else
		  {	  
			  Log.message("Remote address " + String.valueOf(remoteAddress) + " has been ignored");
			  constructorBody(supporteddriver, capabilities);
		  }  
	  }
	  
	  //it makes objects of any WebDriver and navigates to specified URL
	  protected void createWebDriver(Class<? extends WebDriver> driverClass, Class<?>[] paramClasses, Object[] values)
	  {
		  WebDriver driver = null;
		  
		  Constructor<?>[] constructors      = driverClass.getConstructors();
		  Constructor<?> suitableConstructor = null;
		  int constructorCount = constructors.length;
		  for (int i=0; i<constructorCount; i++)
		  {  //looking for constructor we need
			 Class<?>[] params = constructors[i].getParameterTypes();  
			 if (Arrays.equals(params, paramClasses))
			 {
				 suitableConstructor = constructors[i];
				 break;
			 }
		  }
		  
		  if (suitableConstructor==null)
		  {
			  throw new RuntimeException(new NoSuchMethodException("Wrong specified constructor of WebDriver! " + driverClass.getSimpleName()));
		  }
		  
		  try
		  {
			  driver = (WebDriver) suitableConstructor.newInstance(values);
			  actoinsAfterWebDriverCreation(driver);
		  }
		  catch (RuntimeException | InstantiationException | IllegalAccessException | InvocationTargetException e)
		  {
			  if (driver!=null)
			  {
				  driver.quit();
			  }
			  actoinsOnConstructFailure(e);
		  }
	  }
	  
	  protected void createWebDriver(Class<? extends WebDriver> driverClass)
	  {
		 createWebDriver(driverClass, new Class<?>[] {}, new Object[] {});
	  }
	  
	  protected void createWebDriver(Class<? extends WebDriver> driverClass, Capabilities capabilities)
	  {
	  	  createWebDriver(driverClass, new Class<?>[] {Capabilities.class}, new Object[] {capabilities});
	  }	  
	  
	  //constructors are below:
	  //creates instance by specified driver and capabilities
	  public WebDriverEncapsulation(ESupportedDrivers supporteddriver, Capabilities capabilities)
	  {
		  constructorBody(supporteddriver, capabilities);
	  }
	  
	  //creates instance by specified driver, capabilities and remote address
	  public WebDriverEncapsulation(ESupportedDrivers supporteddriver, Capabilities capabilities, URL remoteAddress)
	  {
		  constructorBody(supporteddriver, capabilities, remoteAddress);
	  }
	  
	  //creates instance by specified driver and remote address using default capabilities
	  public WebDriverEncapsulation(ESupportedDrivers supporteddriver,  URL remoteAddress)
	  {
		  constructorBody(supporteddriver, supporteddriver.getDefaultCapabilities(), remoteAddress);  
	  }
	  
	  //creates instance by specified driver and remote address using specified configuration
	  public WebDriverEncapsulation(Configuration configuration)
	  {
		 this.configuration = configuration; 
		 ESupportedDrivers supportedDriver = this.configuration.getWebDriverSettings().getSupoortedWebDriver();
		 if (supportedDriver==null)
		 {
			supportedDriver = defaultSupportedDriver;
		 }
		 
		 Capabilities capabilities = this.configuration.getCapabilities();
		 if (capabilities == null)
		 {
			 capabilities = supportedDriver.getDefaultCapabilities();
		 }
		 
		 String remoteAdress = this.configuration.getWebDriverSettings().getRemoteAddress();
		 if (remoteAdress==null)
		 {
			 constructorBody(supportedDriver, capabilities);
			 return;
		 }
		 
		 try {
			URL remoteUrl = new URL(remoteAdress);
			constructorBody(supportedDriver, capabilities, remoteUrl);
		 } catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage(), e);
		 }
	  }
	  
	  //creates instance by default configuration
	  public WebDriverEncapsulation()
	  {
		 this(Configuration.byDefault);
	  }
	  
	  //other methods:
	  
	  public void destroy()
	  {
		  firingDriver.unregister(webInnerListener);		  
		  try {
			  firingDriver.quit();
		  }
		  catch (RuntimeException e1) {
			  Log.warning("Some problem has been found while the instance of webdriver was quitted! " + e1.getMessage(), e1);
		  }		  
		  try 
		  {
			destroyEnclosedObjects();  
			this.finalize();
		  } 
		  catch (Throwable e) 
		  {
			Log.warning("Some problem has been found while the instance of webdriver was finalized! "+e.getMessage(), e);
		  }
	  }
	  
	  public WebDriver getWrappedDriver()
	  {
		  return(firingDriver);
	  }
	  
	  WindowTimeOuts getWindowTimeOuts()
	  {
		  return(windowTimeOuts);
	  }	  
	  
	  public Awaiting getAwaiting()
	  {
		  return(awaiting);
	  }
	  
	  public WebElementHighLighter getHighlighter()
	  {
		  return(elementHighLighter);
	  }
	  
	  public PageFactoryWorker getPageFactoryWorker()
	  {
		  return(pageFactoryWorker);
	  }
	  
	  public ScriptExecutor getScriptExecutor()
	  {
		  return(scriptExecutor);
	  }		  
	  
	  public FrameSupport getFrameSupport()
	  {
		  return(frameSupport);
	  }		
	  
	  
	  public void resetElementVisibilityTimeOut()
	  {
		  elementVisibility.resetAccordingTo(configuration);
	  }

	  public Cookies getCookies()
	  {
		  return cookies;
	  }
	  
	  public Ime getIme()
	  {
		  return ime;
	  }
	  
	  public BrowserLogs getLogs()
	  {
		  return logs;
	  }	 
	  
	  public TimeOut getTimeOut()
	  {
		  return timeout;
	  }	 
	  
	  public Interaction getInteraction()
	  {
		  return interaction;
	  }
	  
	  public Capabilities getCapabilities()
	  {
		  return firingDriver.getCapabilities();
	  }
	  
	  private void actoinsAfterWebDriverCreation(WebDriver createdDriver)
	  {
		  		  
		  firingDriver = ExtendedEventFiringWebDriver.newInstance(createdDriver);		  
		  
		  elementVisibility  = new ElementVisibility(firingDriver);
		  elementHighLighter = new WebElementHighLighter();
		  
		  webInnerListener = new WebdriverInnerListener();
		  webInnerListener.setElementVisibilityChecker(elementVisibility);	
		  webInnerListener.setHighLighter(elementHighLighter);
		  
		  awaiting 			 = new Awaiting(firingDriver);	  	  
		  pageFactoryWorker  = new PageFactoryWorker(firingDriver);	  
		  scriptExecutor     = new ScriptExecutor(firingDriver);	  
		  frameSupport		 = new FrameSupport(firingDriver);	    
		  cookies            = new Cookies(firingDriver);	  
		  timeout            = new TimeOut(firingDriver, configuration);	  
		  logs  			 = new BrowserLogs(firingDriver);	  
		  ime				 = new Ime(firingDriver);
		  interaction        = new Interaction(firingDriver);
		  
		  windowTimeOuts     = new WindowTimeOuts(configuration);
		  
		  firingDriver.register(webInnerListener);
		  resetAccordingTo(configuration);
	  }
	  
	  private void finalizeInner(IDestroyable nullable)
	  {
		  if (nullable!=null)
		  {
			  nullable.destroy();
		  }
	  }
	  
	  protected void destroyEnclosedObjects()
	  {		  
		  finalizeInner(awaiting);	
		  finalizeInner(pageFactoryWorker);		
		  finalizeInner(scriptExecutor);
		  finalizeInner(frameSupport);
		  finalizeInner(cookies);
		  finalizeInner(ime);
		  finalizeInner(logs);
		  finalizeInner(timeout);
		  finalizeInner(interaction);
		  finalizeInner(webInnerListener);
		  finalizeInner(elementVisibility);
		  finalizeInner(windowTimeOuts);
		  finalizeInner(elementHighLighter);
	  }
	  
	  //if attempt to create a new web driver instance has been failed 
	  protected void actoinsOnConstructFailure(Exception e)
	  {
		  Log.error("Attempt to create a new web driver instance has been failed! "+e.getMessage(),e);
		  if (firingDriver!=null)
		  {
			  destroy();
		  }	
		  else
		  {
			  destroyEnclosedObjects();
		  }
		  throw new RuntimeException(e);
	  }
	  
	  public void registerListener(WebDriverEventListener listener)
	  {
		  firingDriver.register(listener);
	  }
	  
	  public void registerListener(IExtendedWebDriverEventListener listener)
	  {
		  firingDriver.register(listener);
	  }
	  
	  public void unregisterListener(WebDriverEventListener listener)
	  {
		  firingDriver.register(listener);
	  }
	  
	  public void unregisterListener(IExtendedWebDriverEventListener listener)
	  {
		  firingDriver.register(listener);
	  }	 
	  
	  public synchronized void resetAccordingTo(Configuration config)
	  {
		  configuration = config;
		  timeout.resetAccordingTo(configuration);
		  elementVisibility.resetAccordingTo(configuration);
		  windowTimeOuts.resetAccordingTo(configuration);
		  elementHighLighter.resetAccordingTo(configuration);
	  }
	  
	  //it goes to another URL
	  public void getTo(String url)
	  {
		  firingDriver.get(url);
	  }
}