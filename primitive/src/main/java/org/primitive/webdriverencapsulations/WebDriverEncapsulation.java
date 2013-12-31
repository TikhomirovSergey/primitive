package org.primitive.webdriverencapsulations;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.primitive.configuration.Configuration;
import org.primitive.interfaces.IConfigurable;
import org.primitive.interfaces.IDestroyable;
import org.primitive.interfaces.IExtendedWebDriverEventListener;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.firing.ExtendedEventFiringWebDriver;
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


public abstract class WebDriverEncapsulation implements IDestroyable, IConfigurable, WrapsDriver, HasCapabilities
{
	private ExtendedEventFiringWebDriver firingDriver;
	  protected Configuration configuration;
	  
	  protected final static List<WebDriverEncapsulation> driverList = Collections.synchronizedList(new ArrayList<WebDriverEncapsulation>());
	  
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
	  	  
	  protected void constructBodyInGeneral(Class<? extends WebDriver> driverClass)
	  {
		 createWebDriver(driverClass, new Class<?>[] {}, new Object[] {});
	  }
	  
	  protected void constructBodyInGeneral(Class<? extends WebDriver> driverClass, Capabilities capabilities)
	  {
	  	  createWebDriver(driverClass, new Class<?>[] {Capabilities.class}, new Object[] {capabilities});
	  }
	  
	  protected WebDriverEncapsulation(Configuration configuration)
	  {
		  this.configuration = configuration; 
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
	  
	  public void destroy()
	  {
		  firingDriver.unregister(webInnerListener);		  
		  try {
			  firingDriver.quit();
		  }
		  catch (RuntimeException e1) {
			  Log.warning("Some problem has been found while the instance of webdriver was quitted! " + e1.getMessage(), e1);
		  }
		  finally {
			  driverList.remove(this); 
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
		  driverList.add(this);
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
		  driverList.remove(this);
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