package org.primitive.webdriverencapsulations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.configuration.interfaces.IConfigurable;
import org.primitive.interfaces.IDestroyable;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.firing.ExtendedEventFiringWebDriver;
import org.primitive.webdriverencapsulations.interfaces.IExtendedWebDriverEventListener;
import org.primitive.webdriverencapsulations.localserver.LocalRemoteServerInstance;
import org.primitive.webdriverencapsulations.ui.WebElementHighLighter;
import org.primitive.webdriverencapsulations.webdrivercomponents.Awaiting;
import org.primitive.webdriverencapsulations.webdrivercomponents.DriverLogs;
import org.primitive.webdriverencapsulations.webdrivercomponents.Cookies;
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
	  private DriverLogs logs;	  
	  private Ime ime;
	  private Interaction interaction;
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
		  Constructor<?> suitableConstructor = null;		  
		  try {
			 suitableConstructor = driverClass.getConstructor(paramClasses);
		  } catch (NoSuchMethodException | SecurityException e1) {
			  throw new RuntimeException("Wrong specified or constructor of WebDriver! " + driverClass.getSimpleName(),e1);
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
	  //creates instance by specified driver
	  public WebDriverEncapsulation(ESupportedDrivers supporteddriver)
	  {
		  constructorBody(supporteddriver, supporteddriver.getDefaultCapabilities());
	  }
	  
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
		 
		 if (capabilities.asMap().size()==0)
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
	  
	  //creates instance using externally initiated webdriver
	  public WebDriverEncapsulation(WebDriver externallyInitiatedWebDriver)
	  {
		  actoinsAfterWebDriverCreation(externallyInitiatedWebDriver); 
	  }
	  
	  public WebDriverEncapsulation(WebDriver externallyInitiatedWebDriver, Configuration configuration)
	  {
		  this.configuration = configuration; 
		  actoinsAfterWebDriverCreation(externallyInitiatedWebDriver); 
	  }
	  
	  protected WebDriverEncapsulation()
	  {
		  super();
	  }
	  
	  //other methods:
	  
	  public void destroy()
	  {
		  firingDriver.unregister(webInnerListener);	
		  if (firingDriver==null)
		  {	  
			 return;
		  }
		  try
		  {
			  firingDriver.quit();
		  }
		  catch (WebDriverException e) //it may be already dead
		  {
			  return;
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

	  public Cookies getCookies()
	  {
		  return cookies;
	  }
	  
	  public Ime getIme()
	  {
		  return ime;
	  }
	  
	  public DriverLogs getLogs()
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
		  Log.message("Getting started with " + createdDriver.getClass().getSimpleName());	
		  Log.message("Capabilities are: " + ((HasCapabilities) createdDriver).getCapabilities().asMap().toString());	
		  
		  firingDriver = ExtendedEventFiringWebDriver.newInstance(createdDriver);		  
		  elementHighLighter = new WebElementHighLighter();
		  
		  webInnerListener   = new WebdriverInnerListener(createdDriver, configuration);		  
		  awaiting 			 = new Awaiting(firingDriver);	  	  
		  pageFactoryWorker  = new PageFactoryWorker(firingDriver);	  
		  scriptExecutor     = new ScriptExecutor(firingDriver);	  
		  frameSupport		 = new FrameSupport(firingDriver);	    
		  cookies            = new Cookies(firingDriver);	  
		  timeout            = new TimeOut(firingDriver, configuration);	  
		  logs  			 = new DriverLogs(firingDriver);	  
		  ime				 = new Ime(firingDriver);
		  interaction        = new Interaction(firingDriver);
		  
		  windowTimeOuts     = new WindowTimeOuts(configuration);
		  
		  firingDriver.register(webInnerListener);
		  resetAccordingTo(configuration);
	  }
	  
	  //if attempt to create a new web driver instance has been failed 
	  protected void actoinsOnConstructFailure(Exception e)
	  {
		  Log.error("Attempt to create a new web driver instance has been failed! "+e.getMessage(),e);
		  destroy();
		
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
		  webInnerListener.resetAccordingTo(configuration);
		  windowTimeOuts.resetAccordingTo(configuration);
		  elementHighLighter.resetAccordingTo(configuration);
	  }
	  
	  //it goes to another URL
	  public void getTo(String url)
	  {
		  firingDriver.get(url);
	  }
}