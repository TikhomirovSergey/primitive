package org.primitive.webdriverencapsulations;

import static org.junit.Assert.fail;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.Configuration.FileSystemProperty;
import org.primitive.interfaces.IConfigurable;
import org.primitive.interfaces.IDestroyable;
import org.primitive.interfaces.IExtendedWebDriverEventListener;
import org.primitive.logging.Log;
import org.primitive.logging.Photographer;
import org.primitive.webdriverencapsulations.firing.ExtendedEventFiringWebDriver;
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
	public final class PictureMaker {

		public void takeAPictureOfAFine(String comment)
		{
			Photographer.takeAPictureOfAFine(firingDriver, comment);
		}

		public void takeAPictureOfAFine(WebElement webElement, Color highlight, String comment)
		{
			Photographer.takeAPictureOfAFine(firingDriver, webElement, highlight, comment);				
		}

		public void takeAPictureOfAFine(WebElement webElement, String comment)
		{
			Photographer.takeAPictureOfAFine(firingDriver, webElement, comment);			
		}

		public void takeAPictureOfAnInfo(String comment)
		{
			Photographer.takeAPictureOfAnInfo(firingDriver, comment);
		}

		public void takeAPictureOfAnInfo(WebElement webElement, Color highlight, String comment)
		{
			Photographer.takeAPictureOfAnInfo(firingDriver, webElement, highlight, comment);
		}

		public void takeAPictureOfAnInfo(WebElement webElement, String comment)
		{
			Photographer.takeAPictureOfAnInfo(firingDriver, webElement, comment);			
		}

		public void takeAPictureOfASevere(String comment)
		{
			Photographer.takeAPictureOfASevere(firingDriver, comment);
		}

		public void takeAPictureOfASevere(WebElement webElement, Color highlight, String comment)
		{
			Photographer.takeAPictureOfASevere(firingDriver, webElement, comment);
		}

		public void takeAPictureOfASevere(WebElement webElement, String comment)
		{
			Photographer.takeAPictureOfASevere(firingDriver, webElement, comment);				
		}

		public void takeAPictureOfAWarning(String comment)
		{
			Photographer.takeAPictureOfAWarning(firingDriver, comment);
		}

		public void takeAPictureOfAWarning(WebElement webElement, Color highlight, String comment)
		{		
			Photographer.takeAPictureOfAWarning(firingDriver, webElement,highlight, comment);
		}

		public void takeAPictureOfAWarning(WebElement webElement, String comment)
		{
			Photographer.takeAPictureOfAWarning(firingDriver, webElement, comment);					
		}

	}

	private ExtendedEventFiringWebDriver firingDriver;
	  protected Configuration configuration;
	  
	  protected final static List<WebDriverEncapsulation> driverList = Collections.synchronizedList(new ArrayList<WebDriverEncapsulation>());
	  
	  private Awaiting awaiting;	  
	  private final PictureMaker photoMaker = new PictureMaker();	 
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

	  
	  //actions before web driver will be created	  
	  protected abstract void prepare();	  	  
	  
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
	  
	  //It sets system properties for such web drivers as ChromeDriver, IEDriver according to configuration and default values
	  protected void setSystemPropertyLocally(String property, FileSystemProperty setting, String fileByDefault)
	  {
		  if (System.getProperty(property)==null)
		  {			
			  String pathTo   = setting.getFolder();
			  String fileName = setting.getFile();
			  
			  String fullPath;
			  if (pathTo == null)
			  {
				  fullPath = "";
			  }
			  else
			  {
				  fullPath = pathTo;
			  }
			  
			  if (fileName == null)
			  {
				  fullPath = fullPath + fileByDefault;
			  }
			  else
			  {
				  fullPath = fullPath + fileName; 
			  }

			  System.setProperty(property, fullPath);
		  }		  
	  }
	  
	  //it makes objects of any WebDriver and navigates to specified URL
	  protected void createWebDriver(Class<? extends WebDriver> driverClass, Class<?>[] paramClasses, Object[] values)
	  {
		  try
		  {	
			  WebDriver driver = null;
			  Constructor<?>[] constructors = driverClass.getConstructors();
			  int constructorCount = constructors.length;
			  for (int i=0; i<constructorCount; i++)
			  {  //looking for constructor we need
				 Class<?>[] params = constructors[i].getParameterTypes();  
				 if (Arrays.equals(params, paramClasses))
				 {
					 driver = (WebDriver) constructors[i].newInstance(values);
					 break;
				 }
			  }
			  
			  if (driver!=null) //if instance of specified web driver has been created
			  {
				  Log.message("Getting started with " + driverClass.getSimpleName());
				  if (paramClasses.length>0)
				  {
					  Log.message("Parameters: ");
					  for (int i=0; i<paramClasses.length; i++)
					  {
						  Log.message("- " + paramClasses[i].getSimpleName() + ": " + values[i].toString());
					  }
				  }
				  actoinsAfterWebDriverCreation(driver);
			  }
			  else
			  {
				  throw new NoSuchMethodException("Wrong specified constructor of WebDriver! " + driverClass.getSimpleName());
			  }
		  }
		  catch (Exception e)
		  {
			  actoinsOnConstructFailure(e);
		  }
	  }
	  
	  public void destroy()
	  {
		  firingDriver.unregister(webInnerListener);		  
		  try
		  {
			  firingDriver.quit();
			  driverList.remove(this);
		  }
		  catch (WebDriverException e1)
		  {
			  driverList.remove(this);
			  Log.warning("Some problem has been found while the instance of webdriver was quitted! " + e1.getMessage(), e1);
		  }
		  try 
		  {
			destroyEnclosedObjects();  
			webInnerListener  = null;
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
	  
	  public PictureMaker getPhotograther()
	  {
		  return(photoMaker);
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
		  
		  elementVisibility = new ElementVisibility(firingDriver);
		  
		  webInnerListener = new WebdriverInnerListener();
		  webInnerListener.setElementVisibilityChecker(elementVisibility);	
		  
		  awaiting 			= new Awaiting(firingDriver);	  	  
		  pageFactoryWorker = new PageFactoryWorker(firingDriver);	  
		  scriptExecutor    = new ScriptExecutor(firingDriver);	  
		  frameSupport		= new FrameSupport(firingDriver);	    
		  cookies           = new Cookies(firingDriver);	  
		  timeout           = new TimeOut(firingDriver, configuration);	  
		  logs  			= new BrowserLogs(firingDriver);	  
		  ime				= new Ime(firingDriver);
		  interaction       = new Interaction(firingDriver);
		  
		  windowTimeOuts    = new WindowTimeOuts(configuration);
		  
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
	  }
	  
	  //if attempt to create a new web driver instance has been failed 
	  protected void actoinsOnConstructFailure(Exception e)
	  {
		  Log.error("Attempt to create a new web driver instance has been failed! "+e.getMessage(),e);
		  e.printStackTrace();
		  if (firingDriver!=null)
		  {
			  destroy();
		  }	
		  else
		  {
			  destroyEnclosedObjects();
		  }
		  driverList.remove(this);
		  fail("Cannot create new instance of required webdriver! "+e.getMessage());	  
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
	  }
	  
	  //it goes to another URL
	  public void getTo(String url)
	  {
		  firingDriver.get(url);
	  }
}