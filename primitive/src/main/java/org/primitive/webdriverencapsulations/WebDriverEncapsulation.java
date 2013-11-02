package org.primitive.webdriverencapsulations;

import static org.junit.Assert.fail;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.ImeHandler;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.Configuration.BrowserWindowsTimeOuts;
import org.primitive.configuration.Configuration.FileSystemProperty;
import org.primitive.interfaces.IConfigurable;
import org.primitive.interfaces.IDestroyable;
import org.primitive.interfaces.IExtendedWebDriverEventListener;
import org.primitive.logging.Log;
import org.primitive.logging.Photographer;
import org.primitive.webdriverencapsulations.firing.ExtendedEventFiringWebDriver;


public abstract class WebDriverEncapsulation implements IDestroyable, IConfigurable, WrapsDriver, HasCapabilities
{
	//It provides fast access to keyboard and mouse by page objects
	public final class Interaction extends InnerDestroyable implements HasInputDevices {

		@Override
		protected void destroy() {
			finalizeThis();
		}

		public Keyboard getKeyboard()
		{
			return firingDriver.getKeyboard();				
		}

		public Mouse getMouse()
		{
			return firingDriver.getMouse();			
		}

		public TouchScreen getTouch()
		{
			return firingDriver.getTouch();
		}

	}

	/**
	 * @author s.tihomirov
	 *
	 */
	public final class Ime extends InnerDestroyable implements ImeHandler 
	{

		@Override
		public void activateEngine(String arg0) 
		{
			firingDriver.manage().ime().activateEngine(arg0);
		}

		@Override
		public void deactivate() 
		{
			firingDriver.manage().ime().deactivate();
		}

		@Override
		public String getActiveEngine() {
			return firingDriver.manage().ime().getActiveEngine();
		}

		@Override
		public List<String> getAvailableEngines() 
		{
			return firingDriver.manage().ime().getAvailableEngines();
		}
		
		@Override
		public boolean isActivated() 
		{
			return firingDriver.manage().ime().isActivated();
		}

		@Override
		protected void destroy() 
		{
			finalizeThis();
		}

	}

	/**
	 * @author s.tihomirov
	 *
	 */
	public final class BrowserLogs extends InnerDestroyable implements Logs 
	{
		@Override
		public LogEntries get(String logType) 
		{
			return firingDriver.manage().logs().get(logType);
		}

		@Override
		public Set<String> getAvailableLogTypes() 
		{
			return firingDriver.manage().logs().getAvailableLogTypes();
		}				

		@Override
		protected void destroy() 
		{
			finalizeThis();
		}

	}

	/**
	 * @author s.tihomirov
	 *
	 */
	public final class Cookies extends InnerDestroyable 
	{
		@Override
		protected void destroy() {
			finalizeThis();
		}

		public void addCookie(Cookie cookie)
		{
			firingDriver.manage().addCookie(cookie);
		}

		public void deleteAllCookies()
		{
			firingDriver.manage().deleteAllCookies();
		}

		public void deleteAllCookies(String cookieName)
		{
			firingDriver.manage().deleteCookieNamed(cookieName);
		}

		public void deleteCookie(Cookie cookie)
		{
			firingDriver.manage().deleteCookie(cookie);
		}

		public Cookie getCookieNamed(String cookieName)
		{
			return firingDriver.manage().getCookieNamed(cookieName);
		}

		public Set<Cookie> getCookies()
		{
			return firingDriver.manage().getCookies();
		}
	}

	/**
	 * @author s.tihomirov
	 *
	 */
	public final class TimeOut extends InnerDestroyable implements Timeouts, IConfigurable
	{
		private final TimeUnit defaultTimeUnit = TimeUnit.MILLISECONDS;		
		private final long defaultTimeOut = 20000; //20 seconds
		
		public Timeouts setScriptTimeout(long timeOut, TimeUnit timeUnit)
		{
			try
			{
				return firingDriver.manage().timeouts().setScriptTimeout(timeOut, timeUnit);
			}
			catch (WebDriverException e)
			{
				Log.debug("Setting of a script execution timeout is not supported.",e);
				return null;
			}
		}

		public Timeouts implicitlyWait(long timeOut, TimeUnit timeUnit)
		{
			try
			{
				return firingDriver.manage().timeouts().implicitlyWait(timeOut, timeUnit);
			}
			catch (WebDriverException e)
			{
				Log.debug("Setting of an implicitly wait timeout is not supported.",e);
				return null;
			}
		}

		public Timeouts  pageLoadTimeout(long timeOut, TimeUnit timeUnit)
		{
			try
			{
				return firingDriver.manage().timeouts().pageLoadTimeout(timeOut, timeUnit);
			}
			catch (WebDriverException e)
			{
				Log.debug("Setting of a page load timeout is not supported.",e);
				return null;
			}
		}
		
		private Long getTimeOutValue(Long longObjParam)
		{
			if (longObjParam == null)
			{
				longObjParam = defaultTimeOut;
			}
			return longObjParam;
		}
		
		
		//set values of time outs according to configuration
		public synchronized void resetAccordingTo(Configuration config)
		{
			TimeUnit settingTimeUnit = config.getWebDriverTimeOuts().getTimeUnit();
			if (settingTimeUnit==null)
			{
				settingTimeUnit = defaultTimeUnit;
			}
			
			Long timeOut = getTimeOutValue(config.getWebDriverTimeOuts().getImplicitlyWaitTimeOut());
			implicitlyWait(timeOut, settingTimeUnit);
						
			timeOut = getTimeOutValue(config.getWebDriverTimeOuts().getScriptTimeOut());
			setScriptTimeout(timeOut, settingTimeUnit);
			
			timeOut = getTimeOutValue(config.getWebDriverTimeOuts().getLoadTimeout());
			pageLoadTimeout(timeOut, settingTimeUnit);
		}
		
		@Override
		protected void destroy() 
		{
			finalizeThis();			
		}

	}

	public final class AlertHandler extends InnerDestroyable implements Alert{		
		private org.openqa.selenium.Alert alert;		
		protected AlertHandler(long secTimeOut) throws NoAlertPresentException
		{
			try
			{
				alert = awaiting.awaitCondition(secTimeOut, ExpectedConditions.alertIsPresent());
			}
			catch (TimeoutException e)
			{
				destroy();
				throw new NoAlertPresentException();
			}
			catch (NoAlertPresentException e)
			{
				destroy();
				throw new NoAlertPresentException();
			}
			catch (AssertionError e) 
			{
				destroy();
				throw new NoAlertPresentException();
			}	
		}
		
		public void dismiss() throws NoAlertPresentException
		{
			try 
			{
				alert.dismiss();
		    } 
			finally
			{
				destroy();
			}
		}
		
		public void accept() throws NoAlertPresentException
		{
			try 
			{
				alert.accept();
		    } 
			finally
			{
				destroy();
			}
		}
		
		public void authenticateUsing(Credentials credentials) throws NoAlertPresentException
		{
			try 
			{
				alert.authenticateUsing(credentials);
		    } 
			catch (NoAlertPresentException e) 
		    {
				destroy();
		        throw e;
		    }
		}
		
		public String getText() throws NoAlertPresentException
		{
			try 
			{
				return(alert.getText());
		    } 
			catch (NoAlertPresentException e) 
		    {
				destroy();
		        throw e;
		    }
		}
		
		public void sendKeys(String text) throws NoAlertPresentException
		{
			try 
			{
				alert.sendKeys(text);
		    } 
			catch (NoAlertPresentException e) 
		    {
				destroy();
		        throw e;
		    }
		}
		
		@Override
		protected void destroy() 
		{
			alert = null;
			finalizeThis();			
		}

		
	}

	/**
	 * @author s.tihomirov
	 *
	 */
	public final class FrameSupport extends InnerDestroyable {

		@Override
		protected void destroy() 
		{
			finalizeThis();
		}

		public void switchTo(int frame)
		{
		   firingDriver.switchTo().frame(frame);
		}

		public void switchTo(String frame)
		{
			firingDriver.switchTo().frame(frame);	
		}

		public void switchTo(String frame, long secTimeOut) throws TimeoutException
		{
			awaiting.awaitCondition(secTimeOut, ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));		  
		}

		public void switchTo(WebElement frame)
		{
			firingDriver.switchTo().frame(frame);
		}
	}

	/**
	 * @author s.tihomirov
	 *
	 */
	public final class ScriptExecutor extends InnerDestroyable implements JavascriptExecutor {

		public Object executeScript(String script, Object... args)
		{
			try
			{
				return(firingDriver.executeScript(script, args));
			}
			catch (Exception e)
			{
				Log.warning("JavaScript  " + script + " has not been executed! Error: " + e.getMessage(), e);
				throw e;
			}
		}
		
		public Object executeAsyncScript(String script, Object...  args)
		{
			try
			{
				return(firingDriver.executeAsyncScript(script, args));
			}
			catch (Exception e)
			{
				Log.warning("JavaScript: " + script + " has not been executed! Error:  " + e.getMessage(), e);
				throw e;
			}
		}		
		
		@Override
		protected void destroy() 
		{
			finalizeThis();
		}
	}

	/**
	 * @author s.tihomirov
	 *
	 */
	private abstract class InnerDestroyable {
		
		protected InnerDestroyable()
		{
			super();
		}
		
		protected abstract void destroy();	
		
		protected void finalizeThis()
		{
			try 
			{
				finalize();
			} 
			catch (Throwable e) 
			{
				e.printStackTrace();
			}
		}
	}

	public final class WindowTool extends InnerDestroyable implements Navigation, Window{

		public void to(String link)
		{
			firingDriver.navigate().to(link);
		}
		
		public void forward()
		{
			firingDriver.navigate().forward();	
		}
		
		public void back()
		{
			firingDriver.navigate().back();			
		}
		
		protected void destroy()
		{
			finalizeThis();
		}
		

		@Override
		public void refresh() 
		{
			firingDriver.navigate().refresh();			
		}

		@Override
		public void to(URL url) 
		{
			firingDriver.navigate().to(url);			
		}

		@Override
		public Point getPosition() 
		{
			return firingDriver.manage().window().getPosition();
		}


		@Override
		public Dimension getSize() {
			return firingDriver.manage().window().getSize();
		}


		@Override
		public void maximize() {
			firingDriver.manage().window().maximize();
			
		}

		@Override
		public void setPosition(Point arg0) {
			firingDriver.manage().window().setPosition(arg0);
			
		}

		@Override
		public void setSize(Dimension arg0) {
			firingDriver.manage().window().setSize(arg0);			
		}		
	}

	public final class PageFactoryWorker extends InnerDestroyable
	{
		//To provide work with page factory
		public void initPageFactory(Object page)
		{
			PageFactory.initElements(firingDriver, page);
		}
		
		public <T> T initPageFactory(Class<T> pageClassToProxy)
		{
			return PageFactory.initElements(firingDriver, pageClassToProxy);
		}
		
		public void initPageFactory(FieldDecorator decorator,Object page)
		{
			PageFactory.initElements(decorator, page);
		}
		
		public void initPageFactory(ElementLocatorFactory factory,Object page)
		{
			PageFactory.initElements(factory, page);
		}
		
		protected void destroy()
		{
			finalizeThis();			
		}		
	}

	public final class WindowTimeOuts extends InnerDestroyable implements IConfigurable 
	{
		protected final long defaultTime = 1; //default time we waiting for anything
		protected final long defaultTimeForNewWindow = 30; //we will wait appearance of a new browser window for 30 seconds by default 
		private BrowserWindowsTimeOuts timeOuts;
		
		protected Long getTimeOut(Long possibleTimeOut, long defaultValue)
		{
			if (possibleTimeOut==null)
			{
				return defaultValue;
			}
			else
			{
				return possibleTimeOut;
			}
		}
		
		public synchronized void resetAccordingTo(Configuration config)
		{
			timeOuts = config.getBrowserWindowsTimeOuts();
		}
		
		protected BrowserWindowsTimeOuts getTimeOuts()
		{
			return timeOuts;
		}			

		protected void destroy()
		{
			finalizeThis();				
		}
	}

	public final class PictureMaker extends InnerDestroyable {

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
		
		protected void destroy()
		{
			finalizeThis();			
		}

	}

	public final class Awaiting extends InnerDestroyable
	{
		protected void destroy()
		{
			finalizeThis();
		}

		@SuppressWarnings("unchecked")
		public <T> T awaitCondition(long secTimeOut, ExpectedCondition<?> condition) throws TimeoutException
		{
			return (T) new WebDriverWait(firingDriver,  secTimeOut).until(condition);		
		}	
		
		@SuppressWarnings("unchecked")
		public <T> T awaitCondition(long secTimeOut, long sleepInMillis, ExpectedCondition<?> condition) throws TimeoutException
		{
			return (T) new WebDriverWait(firingDriver, secTimeOut, sleepInMillis).until(condition);		
		}
		
	  }

	  private ExtendedEventFiringWebDriver firingDriver;
	  protected Configuration configuration;
	  
	  protected final static List<WebDriverEncapsulation> driverList = Collections.synchronizedList(new ArrayList<WebDriverEncapsulation>());
	  
	  private final Awaiting awaiting = new Awaiting();	  
	  private final PictureMaker photoMaker = new PictureMaker();	 
	  private final WindowTimeOuts windowTimeOuts = new WindowTimeOuts();	 
	  private final PageFactoryWorker pageFactoryWorker = new PageFactoryWorker();	  
	  private final WindowTool windowTool = new WindowTool();	  
	  private final ScriptExecutor scriptExecutor = new ScriptExecutor();	  
	  private final FrameSupport frameSupport = new FrameSupport();	  
	  private ElementVisibility elementVisibility;	  
	  private WebdriverInnerListener webInnerListener = new WebdriverInnerListener();
	  private final Cookies cookies = new Cookies();	  
	  private final TimeOut timeout = new TimeOut();	  
	  private final BrowserLogs logs = new BrowserLogs();	  
	  private final Ime ime = new Ime();
	  private final Interaction interaction = new Interaction();
	  
	  //actions before web driver will be created	  
	  protected abstract void prepare();	  	  
	  
	  protected void constructBodyInGeneral(String openingURL, Class<? extends WebDriver> driverClass)
	  {
		 createWebDriver(openingURL, driverClass, new Class<?>[] {}, new Object[] {});
	  }
	  
	  protected void constructBodyInGeneral(String openingURL, Class<? extends WebDriver> driverClass, Capabilities capabilities)
	  {
	  	  createWebDriver(openingURL, driverClass, new Class<?>[] {Capabilities.class}, new Object[] {capabilities});
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
	  protected void createWebDriver(String openingURL, Class<? extends WebDriver> driverClass, Class<?>[] paramClasses, Object[] values)
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
				  actoinsAfterWebDriverCreation(driver, openingURL);
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
			elementVisibility = null;
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
	  
	  protected WindowTimeOuts getWindowTimeOuts()
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
	  
	  protected WindowTool getWinwowTool()
	  {
		  return(windowTool);
	  }	  
	  
	  public ScriptExecutor getScriptExecutor()
	  {
		  return(scriptExecutor);
	  }		  
	  
	  public FrameSupport getFrameSupport()
	  {
		  return(frameSupport);
	  }		
	  
	  protected Alert getAlert(long timeToWait) throws NoAlertPresentException
	  {
		  try
		  {
			  return new AlertHandler(timeToWait);
		  }
		  catch (NoAlertPresentException e)
		  {
			  throw e;
		  }
		  
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
	  
	  protected void actoinsAfterWebDriverCreation(WebDriver createdDriver, String URL)
	  {
		  		  
		  firingDriver = ExtendedEventFiringWebDriver.newInstance(createdDriver);		  
		  
		  elementVisibility = new ElementVisibility(awaiting, firingDriver);
		  webInnerListener.setElementVisibilityChecker(elementVisibility);		  
		  
		  firingDriver.register(webInnerListener);
		  firingDriver.get(URL);
		  driverList.add(this);
		  resetAccordingTo(configuration);
	  }
	  
	  private void finalizeInner(InnerDestroyable nullable)
	  {
		  if (nullable!=null)
		  {
			  nullable.destroy();
		  }
	  }
	  
	  protected void destroyEnclosedObjects()
	  {		  
		  finalizeInner(awaiting);	
		  finalizeInner(photoMaker);
		  finalizeInner(windowTimeOuts);	
		  finalizeInner(pageFactoryWorker);	
		  finalizeInner(windowTool);		
		  finalizeInner(scriptExecutor);
		  finalizeInner(frameSupport);
		  finalizeInner(cookies);
		  finalizeInner(ime);
		  finalizeInner(logs);
		  finalizeInner(timeout);
		  finalizeInner(interaction);
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